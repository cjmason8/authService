package au.com.mason.authservice.authservice.repository

import au.com.mason.authservice.authservice.domain.ApplicationType
import au.com.mason.authservice.authservice.domain.UserApplication
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserApplicationRepository : CrudRepository<UserApplication, Long> {
    @Query("SELECT ua FROM UserApplication ua WHERE ua.user.userName = :userName AND ua.user.password = :password AND ua.applicationType = :applicationType")
    fun validateUser(@Param("userName") userName: String, @Param("password") password: String, @Param("applicationType") applicationType: ApplicationType): UserApplication?
}