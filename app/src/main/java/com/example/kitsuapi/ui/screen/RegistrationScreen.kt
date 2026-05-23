package com.example.kitsuapi.ui.screen

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class AuthMode {
    Register,
    Login,
}

@Composable
fun RegistrationScreen(
    email: String,
    password: String,
    confirmPassword: String,
    authMode: AuthMode,
    errorMessage: String?,
    isLoading: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onAuthModeChange: (AuthMode) -> Unit,
    onRegisterClick: () -> Unit,
) {
    val transition = rememberInfiniteTransition(label = "anime-bg")
    val alpha by transition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "alpha",
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF21143A),
                        Color(0xFF4B2176),
                        Color(0xFFEF7CB5),
                    )
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alpha),
            colors = CardDefaults.cardColors(containerColor = Color(0xCC1F1033)),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = "Kitsu ✨",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFFFD7F3),
                )
                Text(
                    text = if (authMode == AuthMode.Register)
                        "Создай аккаунт в аниме стиле"
                    else
                        "Войди в свой аниме аккаунт",
                    color = Color(0xFFEAC4FF),
                    fontSize = 16.sp,
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    label = { Text("Email") },
                    singleLine = true,
                    colors = fieldColors(),
                    modifier = Modifier.fillMaxWidth(),
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = { Text("Пароль") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    colors = fieldColors(),
                    modifier = Modifier.fillMaxWidth(),
                )

                if (authMode == AuthMode.Register) {
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = onConfirmPasswordChange,
                        label = { Text("Повтори пароль") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        colors = fieldColors(),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                if (!errorMessage.isNullOrBlank()) {
                    Text(
                        text = errorMessage,
                        color = Color(0xFFFFB4C4),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                Button(
                    onClick = onRegisterClick,
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65CA8)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                ) {
                    Text(
                        when {
                            isLoading && authMode == AuthMode.Register -> "Регистрация..."
                            isLoading && authMode == AuthMode.Login -> "Вход..."
                            authMode == AuthMode.Register -> "Зарегистрироваться"
                            else -> "Войти"
                        }
                    )
                }

                TextButton(
                    onClick = {
                        onAuthModeChange(
                            if (authMode == AuthMode.Register) AuthMode.Login else AuthMode.Register
                        )
                    },
                    enabled = !isLoading,
                    modifier = Modifier.align(Alignment.End),
                ) {
                    Text(
                        text = if (authMode == AuthMode.Register)
                            "Уже есть аккаунт? Войти"
                        else
                            "Нет аккаунта? Зарегистрироваться",
                        color = Color(0xFFFFD7F3),
                    )
                }
            }
        }
    }
}

@Composable
private fun fieldColors() = TextFieldDefaults.colors(
    focusedContainerColor = Color(0x802D1A49),
    unfocusedContainerColor = Color(0x802D1A49),
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedLabelColor = Color(0xFFF5CDFE),
    unfocusedLabelColor = Color(0xFFD4B3ED),
    focusedIndicatorColor = Color(0xFFFF9BD3),
    unfocusedIndicatorColor = Color(0xFFAF7CCF),
)
