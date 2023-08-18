package au.com.mason.authservice.authservice.service

import au.com.mason.authservice.authservice.domain.SessionToken
import au.com.mason.authservice.authservice.domain.User
import au.com.mason.authservice.authservice.repository.SessionTokenRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SessionTokenService(private val sessionTokenRepository: SessionTokenRepository) {

    fun validateToken(token: String): SessionToken? {
        val sessionToken = sessionTokenRepository.getByToken(token)

        if (sessionToken != null && LocalDateTime.now().isBefore(sessionToken.getExpiryDateTime())) {
            return sessionToken
        }

        return null
    }

    fun createSessionToken(user: User): SessionToken {
        val sessionToken = SessionToken(user = user)
        sessionTokenRepository.save(sessionToken)

        return sessionToken
    }
}