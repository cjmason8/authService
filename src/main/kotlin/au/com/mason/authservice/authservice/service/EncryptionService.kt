package au.com.mason.authservice.authservice.service

import org.apache.commons.codec.binary.Base64
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Service
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

@Service
@PropertySource("file:\${resources.dir}/required")
class EncryptionService(@Value("\${beta.key}") private val betaKey: String, @Value("\${alpha.vec}") private val alphaKey: String) {

    fun decrypt(encrypted: String?): String? {
        try {
            val iv = IvParameterSpec(betaKey.toByteArray(charset("UTF-8")))
            val skeySpec = SecretKeySpec(alphaKey.toByteArray(charset("UTF-8")), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
            val original = cipher.doFinal(Base64.decodeBase64(encrypted))

            return String(original)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    fun encrypt(value: String): String? {
        try {
            val iv = IvParameterSpec(betaKey.toByteArray(charset("UTF-8")))
            val skeySpec = SecretKeySpec(alphaKey.toByteArray(charset("UTF-8")), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
            val encrypted = cipher.doFinal(value.toByteArray())
            return Base64.encodeBase64String(encrypted)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return null
    }

}