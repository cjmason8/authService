package au.com.mason.authservice.authservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthserviceApplication

fun main(args: Array<String>) {
	runApplication<AuthserviceApplication>(*args)
}
