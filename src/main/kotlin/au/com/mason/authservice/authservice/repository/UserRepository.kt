package au.com.mason.authservice.authservice.repository

import au.com.mason.authservice.authservice.domain.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.userName = :userName")
    fun findByUserName(@Param("userName") userName: String): User?
}