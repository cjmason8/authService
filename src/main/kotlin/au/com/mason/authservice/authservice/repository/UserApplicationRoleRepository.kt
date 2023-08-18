package au.com.mason.authservice.authservice.repository

import au.com.mason.authservice.authservice.domain.UserApplicationRole
import org.springframework.data.repository.CrudRepository

interface UserApplicationRoleRepository : CrudRepository<UserApplicationRole, Long> {
}