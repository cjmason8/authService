package au.com.mason.authservice.authservice.dto

import au.com.mason.authservice.authservice.domain.ApplicationType
import au.com.mason.authservice.authservice.domain.Role

data class CreateUserDto(val userName: String, val password: String, val applicationType: ApplicationType, val roles: List<Role>)
