package au.com.mason.authservice.authservice.domain

import jakarta.persistence.*

@Table(name = "users")
@Entity
data class User(
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
    var id: Long? = null,
    @Column(name = "username") val userName: String,
    @Column val password: String,
)
{
    override fun toString(): String {
        return userName
    }
}
