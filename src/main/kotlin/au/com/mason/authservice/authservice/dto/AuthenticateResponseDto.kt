package au.com.mason.authservice.authservice.dto

data class AuthenticateResponseDto(val tokenStatus: String, val user: String? = null)
