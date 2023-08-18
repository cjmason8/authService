package au.com.mason.authservice.authservice.service

import au.com.mason.authservice.authservice.domain.User
import au.com.mason.authservice.authservice.domain.UserApplication
import au.com.mason.authservice.authservice.domain.UserApplicationRole
import au.com.mason.authservice.authservice.dto.CreateUserDto
import au.com.mason.authservice.authservice.dto.LoginDto
import au.com.mason.authservice.authservice.repository.UserApplicationRepository
import au.com.mason.authservice.authservice.repository.UserApplicationRoleRepository
import au.com.mason.authservice.authservice.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserApplicationService(private val userRepository: UserRepository, private val userApplicationRepository: UserApplicationRepository, private val userApplicationRoleRepository: UserApplicationRoleRepository) {

    fun validateUser(loginDto: LoginDto): UserApplication? {
        return userApplicationRepository.validateUser(loginDto.userName, loginDto.password, loginDto.applicationType)
    }

    fun createUser(createUserDto: CreateUserDto): User {
        var user = userRepository.findByUserName(createUserDto.userName)

        if (user == null) {
            val newUser = User(userName = createUserDto.userName, password = createUserDto.password)
            userRepository.save(newUser)
            user = newUser
        }

        val userApplication = UserApplication(user = user, applicationType = createUserDto.applicationType)
        userApplicationRepository.save(userApplication)

        createUserDto.roles.forEach(
            { role -> userApplicationRoleRepository.save(UserApplicationRole(userApplication = userApplication, role = role)) })

        return user
    }

    fun userNameExists(userName: String): Boolean {
        return userRepository.findByUserName(userName) != null
    }
}