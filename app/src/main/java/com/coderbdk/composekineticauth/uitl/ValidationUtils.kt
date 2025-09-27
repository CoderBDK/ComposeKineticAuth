package com.coderbdk.composekineticauth.uitl

import android.util.Patterns
import androidx.compose.ui.graphics.Color

object ValidationUtils {
    private val UPPERCASE_REGEX = ".*[A-Z].*".toRegex()
    private val NUMBER_REGEX = ".*[0-9].*".toRegex()
    private val SPECIAL_CHAR_REGEX = ".*[!@#$%^&*].*".toRegex()
    private val NAME_REGEX = "^[a-zA-Z\\s]{2,50}$".toRegex()
    private val VERIFICATION_CODE_REGEX = "^[0-9]{4,6}$".toRegex()

    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun calculatePasswordStrength(password: String): PasswordStrength {
        return when {
            password.length >= 8 &&
                    UPPERCASE_REGEX.containsMatchIn(password) &&
                    NUMBER_REGEX.containsMatchIn(password) &&
                    SPECIAL_CHAR_REGEX.containsMatchIn(password) -> PasswordStrength.STRONG

            password.length >= 6 -> PasswordStrength.MEDIUM
            else -> PasswordStrength.WEAK
        }
    }

    fun isPasswordValid(password: String): Boolean {
        return calculatePasswordStrength(password) == PasswordStrength.STRONG
    }
    fun isValidName(name: String): Boolean {
        return name.isNotEmpty() && NAME_REGEX.matches(name.trim())
    }

    fun isValidVerificationCode(code: String): Boolean {
        return code.isNotEmpty() && VERIFICATION_CODE_REGEX.matches(code.trim())
    }
}

enum class PasswordStrength(val color: Color, val value: String, val steps: Int) {
    WEAK(Color.Red, "Weak", 1),
    MEDIUM(Color.Yellow, "Medium", 2),
    STRONG(Color.Green, "Strong", 3)
}
