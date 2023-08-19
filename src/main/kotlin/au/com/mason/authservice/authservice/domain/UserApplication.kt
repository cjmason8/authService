package au.com.mason.authservice.authservice.domain

import jakarta.persistence.*

@Table(name = "userapplications")
@Entity
data class UserApplication(
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "userapplications_seq")
    @SequenceGenerator(name = "userapplications_seq", sequenceName = "userapplications_seq", allocationSize = 1)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "userid")
    val user: User,
    @Enumerated(EnumType.STRING)
    @Column(name="applicationtype")
    val applicationType: ApplicationType,
    @OneToMany(mappedBy = "userapplication")
    val userApplicationRoles: List<UserApplicationRole> = mutableListOf()
) {
    override fun toString(): String {
        return user.userName + " " + applicationType
    }
}
