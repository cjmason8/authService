package au.com.mason.authservice.authservice.config

import au.com.mason.authservice.authservice.service.AwsSecretsService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
class DatabaseConfig(
    private val awsSecretsService: AwsSecretsService,
    @Value("\${db.driver}") private val databaseDriver: String,
    @Value("\${db.url}") private val databaseUrl: String,
    @Value("\${env:local}") private val environment: String) {

    @Bean
    fun dataSource(): DataSource {
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.driverClassName(databaseDriver)
        dataSourceBuilder.url(databaseUrl)
        
        val secretName = if (environment.equals("prd", ignoreCase = true))
            "prod-database-credentials"
        else
            "local-database-credentials"
        
        dataSourceBuilder.username(awsSecretsService.getSecretValue(secretName, "USER_NAME"))
        dataSourceBuilder.password(awsSecretsService.getSecretValue(secretName, "PASSWORD"))
        
        return dataSourceBuilder.build()
   }
}
