package au.com.mason.authservice.authservice.repository

import au.com.mason.authservice.authservice.domain.SessionToken
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SessionTokenRepository : CrudRepository<SessionToken, Long> {
    @Query("SELECT st FROM SessionToken st WHERE st.token = :token")
    fun getByToken(@Param("token") token: String): SessionToken?
}