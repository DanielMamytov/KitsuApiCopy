package com.example.kitsuapi

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.kitsuapi.ui.AnimeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.example.kitsuapi.ui.screen.AnimeScreen
import com.example.kitsuapi.ui.screen.RegistrationScreen
import com.example.kitsuapi.ui.screen.AuthMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: AnimeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current
            val auth = remember { FirebaseAuth.getInstance() }
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var confirmPassword by remember { mutableStateOf("") }
            var errorMessage by remember { mutableStateOf<String?>(null) }
            var isLoading by remember { mutableStateOf(false) }
            var isAuthenticated by remember { mutableStateOf(auth.currentUser != null) }
            var authMode by remember { mutableStateOf(AuthMode.Register) }

            if (isAuthenticated) {
                AnimeScreen(viewModel = viewModel)
            } else {
                RegistrationScreen(
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword,
                    authMode = authMode,
                    errorMessage = errorMessage,
                    isLoading = isLoading,
                    onEmailChange = {
                        email = it
                        errorMessage = null
                    },
                    onPasswordChange = {
                        password = it
                        errorMessage = null
                    },
                    onConfirmPasswordChange = {
                        confirmPassword = it
                        errorMessage = null
                    },
                    onAuthModeChange = {
                        authMode = it
                        errorMessage = null
                    },
                    onRegisterClick = {
                        val validationError = validateFields(
                            email = email,
                            password = password,
                            confirmPassword = confirmPassword,
                            authMode = authMode,
                        )
                        if (validationError != null) {
                            errorMessage = validationError
                            return@RegistrationScreen
                        }

                        isLoading = true
                        if (authMode == AuthMode.Register) {
                            auth.createUserWithEmailAndPassword(email.trim(), password)
                                .addOnCompleteListener { task ->
                                    isLoading = false
                                    if (task.isSuccessful) {
                                        isAuthenticated = true
                                        Toast.makeText(
                                            context,
                                            "Регистрация успешна!",
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    } else {
                                        errorMessage = task.exception?.localizedMessage
                                            ?: "Не удалось зарегистрироваться"
                                    }
                                }
                        } else {
                            auth.signInWithEmailAndPassword(email.trim(), password)
                                .addOnCompleteListener { task ->
                                    isLoading = false
                                    if (task.isSuccessful) {
                                        isAuthenticated = true
                                        Toast.makeText(
                                            context,
                                            "Вход выполнен!",
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    } else {
                                        errorMessage = task.exception?.localizedMessage
                                            ?: "Не удалось войти"
                                    }
                                }
                        }
                    },
                )
            }
        }
    }
}

private fun validateFields(
    email: String,
    password: String,
    confirmPassword: String,
    authMode: AuthMode,
): String? {
    val trimmedEmail = email.trim()
    return when {
        trimmedEmail.isBlank() -> "Введите email"
        !Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches() -> "Некорректный email"
        password.length < 6 -> "Пароль должен быть не меньше 6 символов"
        authMode == AuthMode.Register && password != confirmPassword -> "Пароли не совпадают"
        else -> null
    }
}
