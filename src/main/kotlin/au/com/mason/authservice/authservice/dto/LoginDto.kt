package au.com.mason.authservice.authservice.dto

import au.com.mason.authservice.authservice.domain.ApplicationType

data class LoginDto(val userName: String, val password: String, val applicationType: ApplicationType)
