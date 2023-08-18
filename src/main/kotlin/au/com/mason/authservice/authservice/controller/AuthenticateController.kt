package au.com.mason.authservice.authservice.controller

import au.com.mason.authservice.authservice.dto.AuthenticateResponseDto
import au.com.mason.authservice.authservice.service.SessionTokenService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticateController(private val sessionTokenService: SessionTokenService) {

    @GetMapping(value = ["/authenticate/{token}"], produces = ["application/json"])
    fun authenticate(@PathVariable token: String): AuthenticateResponseDto {
        val sessionToken = sessionTokenService.validateToken(token);

        return if (sessionToken != null) {
            AuthenticateResponseDto("valid", sessionToken.user.userName)
        } else {
            AuthenticateResponseDto("invalid")
        }
    }
}