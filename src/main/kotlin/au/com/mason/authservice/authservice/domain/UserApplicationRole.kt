package au.com.mason.authservice.authservice.domain

import jakarta.persistence.*

@Table(name = "userapplicationroles")
@Entity
data class UserApplicationRole(
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "userapplicationroles_seq")
    @SequenceGenerator(name = "userapplicationroles_seq", sequenceName = "userapplicationroles_seq", allocationSize = 1)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "userApplicationId")
    val userApplication: UserApplication,
    @Enumerated(EnumType.STRING)
    val role: Role
) {

}
