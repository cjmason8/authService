package au.com.mason.authservice.authservice.config

import au.com.mason.authservice.authservice.service.EncryptionService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
class DatabaseConfig(
    private val encryptionService: EncryptionService,
    @Value("\${db.driver}") private val databaseDriver: String,
    @Value("\${db.url}") private val databaseUrl: String,
    @Value("\${db.user}") private val databaseUser: String,
    @Value("\${db.pass}") private val databasePassword: String) {

    @Bean
    fun dataSource(): DataSource {
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.driverClassName(databaseDriver)
        dataSourceBuilder.url(databaseUrl)
        dataSourceBuilder.username(databaseUser)
        dataSourceBuilder.password(encryptionService.decrypt(databasePassword))
        return dataSourceBuilder.build()
   }
}
