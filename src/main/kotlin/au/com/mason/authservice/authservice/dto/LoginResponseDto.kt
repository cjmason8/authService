package au.com.mason.authservice.authservice.dto

data class LoginResponseDto(val loginStatus: String, val user: String, val token: String? = null, val roles: String? = null)
