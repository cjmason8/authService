package au.com.mason.authservice.authservice.controller

import au.com.mason.authservice.authservice.domain.SessionToken
import au.com.mason.authservice.authservice.dto.LoginDto
import au.com.mason.authservice.authservice.dto.LoginResponseDto
import au.com.mason.authservice.authservice.service.SessionTokenService
import au.com.mason.authservice.authservice.service.UserApplicationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(val userApplicationService: UserApplicationService, val sessionTokenService: SessionTokenService) {

//    val logger = logger("LoginController")

    @PostMapping(value = ["/login"])
    fun login(@RequestBody loginDto: LoginDto): LoginResponseDto {
//        logger.info("Got in here!!")
        val userApplication = userApplicationService.validateUser(loginDto)

        return if (userApplication != null) {
            val sessionToken: SessionToken = sessionTokenService.createSessionToken(userApplication.user)
            LoginResponseDto("success",userApplication.user.userName, sessionToken.token, userApplication.userApplicationRoles.joinToString { it -> "\'$it\'" })
        } else {
            LoginResponseDto("failed",loginDto.userName)
        }
    }
}