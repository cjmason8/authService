package au.com.mason.authservice.authservice.domain

import jakarta.persistence.*

@Table(name = "userapplicationroles")
@Entity
data class UserApplicationRole(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "userApplicationId")
    val userApplication: UserApplication,
    @Enumerated(EnumType.STRING)
    val role: Role
) {

}
