package au.com.mason.authservice.authservice.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.UUID

@Table(name = "sessionTokens")
@Entity
data class SessionToken(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "userId")
    val user: User,
    @Column
    val token: String = UUID.randomUUID().toString(),
    @Column
    val expiryDateTime: Timestamp = Timestamp.valueOf(LocalDateTime.now().plusMinutes(120))
) {
    fun getExpiryDateTime(): LocalDateTime {
        return expiryDateTime.toLocalDateTime()
    }
}
