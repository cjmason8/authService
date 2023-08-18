package au.com.mason.authservice.authservice.controller

import au.com.mason.authservice.authservice.dto.CreateUserDto
import au.com.mason.authservice.authservice.dto.CreateUserResponseDto
import au.com.mason.authservice.authservice.service.UserApplicationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userApplicationService: UserApplicationService) {

    @PostMapping(value = ["/userApplication"], produces = ["application/json"], consumes = ["application/json"])
    fun login(@RequestBody createUserDto: CreateUserDto): CreateUserResponseDto {
        val user = userApplicationService.createUser(createUserDto)

        return if (user != null) {
            CreateUserResponseDto("success")
        } else {
            CreateUserResponseDto("failed")
        }
    }
}