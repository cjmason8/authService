package au.com.mason.authservice.authservice.controller

import au.com.mason.authservice.authservice.dto.LoginDto
import au.com.mason.authservice.authservice.dto.UserNameExistsResponseDto
import au.com.mason.authservice.authservice.service.UserApplicationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserNameExistsController(private val userApplicationService: UserApplicationService) {

    @GetMapping(value = ["/userNameExists"], produces = ["application/json"], consumes = ["application/json"])
    fun validate(@RequestBody loginDto: LoginDto): UserNameExistsResponseDto {
        return if (userApplicationService.userNameExists(loginDto.userName)) {
            UserNameExistsResponseDto("true")
        }
        else {
            UserNameExistsResponseDto("false")
        }
    }
}