package au.com.mason.authservice.authservice.service

import com.google.gson.Gson
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException

@Service
class AwsSecretsService {

    companion object {
        private val LOGGER: Logger = LogManager.getLogger(AwsSecretsService::class.java)
    }

    private var secretsManagerClient: SecretsManagerClient? = null
    private val gson = Gson()

    @Value("\${aws.secrets.region:ap-southeast-2}")
    private lateinit var region: String

    @Value("\${aws.secrets.access-key:}")
    private lateinit var accessKey: String

    @Value("\${aws.secrets.secret-key:}")
    private lateinit var secretKey: String

    @Value("\${aws.secrets.session-token:}")
    private lateinit var sessionToken: String

    @Value("\${aws.secrets.enabled:true}")
    private var enabled: Boolean = true

    @PostConstruct
    fun init() {
        if (!enabled) {
            LOGGER.info("AWS Secrets Manager is disabled")
            return
        }

        val builder = SecretsManagerClient.builder().region(Region.of(region.trim()))

        if (accessKey.isNotBlank() && secretKey.isNotBlank()) {
            if (sessionToken.isNotBlank()) {
                builder.credentialsProvider(
                    StaticCredentialsProvider.create(
                        AwsSessionCredentials.create(
                            accessKey.trim(),
                            secretKey.trim(),
                            sessionToken.trim()
                        )
                    )
                )
                LOGGER.info("Using AWS session credentials for Secrets Manager")
            } else {
                builder.credentialsProvider(
                    StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey.trim(), secretKey.trim())
                    )
                )
                LOGGER.info("Using AWS basic credentials for Secrets Manager")
            }
        } else {
            builder.credentialsProvider(DefaultCredentialsProvider.create())
            LOGGER.info("Using default AWS credentials provider for Secrets Manager")
        }

        this.secretsManagerClient = builder.build()
        LOGGER.info("AWS Secrets Manager client initialized for region: {}", region)
    }

    @PreDestroy
    fun cleanup() {
        secretsManagerClient?.close()
        LOGGER.info("AWS Secrets Manager client closed")
    }

    /**
     * Retrieves a secret value from AWS Secrets Manager.
     *
     * @param secretName the name or ARN of the secret
     * @return the secret string value
     * @throws SecretsManagerException if the secret cannot be retrieved
     */
    fun getSecretString(secretName: String): String {
        if (!enabled) {
            throw IllegalStateException("AWS Secrets Manager is disabled")
        }

        try {
            val request = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build()

            val response = secretsManagerClient!!.getSecretValue(request)
            val secret = response.secretString()

            LOGGER.info("Successfully retrieved secret: {}", secretName)
            return secret
        } catch (e: SecretsManagerException) {
            LOGGER.error("Failed to retrieve secret: {} - {}", secretName, e.message)
            throw e
        }
    }

    /**
     * Retrieves a secret and parses it as JSON into a Map.
     *
     * @param secretName the name or ARN of the secret
     * @return a map containing the secret's key-value pairs
     * @throws SecretsManagerException if the secret cannot be retrieved
     */
    fun getSecretAsMap(secretName: String): Map<String, String> {
        val secretString = getSecretString(secretName)
        try {
            @Suppress("UNCHECKED_CAST")
            return gson.fromJson(secretString, Map::class.java) as Map<String, String>
        } catch (e: Exception) {
            LOGGER.error("Failed to parse secret as JSON: {}", secretName, e)
            throw IllegalStateException("Secret is not valid JSON: $secretName", e)
        }
    }

    /**
     * Retrieves a specific key from a JSON secret.
     *
     * @param secretName the name or ARN of the secret
     * @param key the key to extract from the JSON secret
     * @return the value associated with the key, or null if not found
     * @throws SecretsManagerException if the secret cannot be retrieved
     */
    fun getSecretValue(secretName: String, key: String): String? {
        val secretMap = getSecretAsMap(secretName)
        return secretMap[key]
    }

    /**
     * Checks if AWS Secrets Manager is enabled.
     *
     * @return true if enabled, false otherwise
     */
    fun isEnabled(): Boolean = enabled
}
