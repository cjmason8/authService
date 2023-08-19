package au.com.mason.authservice.authservice.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.UUID

@Table(name = "sessiontokens")
@Entity
data class SessionToken(
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "sessiontokens_seq")
    @SequenceGenerator(name = "sessiontokens_seq", sequenceName = "sessiontokens_seq", allocationSize = 1)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "userid")
    val user: User,
    @Column
    val token: String = UUID.randomUUID().toString(),
    @Column(name = "expirydatetime")
    val expiryDateTime: Timestamp = Timestamp.valueOf(LocalDateTime.now().plusMinutes(120))
) {
    fun getExpiryDateTime(): LocalDateTime {
        return expiryDateTime.toLocalDateTime()
    }
}
