package com.group.libraryapp.security

import io.jsonwebtoken.*
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter

@Component
class JWTTokenProvider (val jwtProperties: JWTProperties) {
    fun signAcToken(token: JWTAccessToken): String =
         Jwts.builder()
            .setSubject("library-app")
            .claim("email", token.email.email)
            .claim("name", token.name)
            .claim("userType", token.userType)
            .setExpiration(expiredAt(JWTAccessToken.TTL))
            .signWith(getSigningKey(),SignatureAlgorithm.HS256)
            .compact()

    fun accessTokenFromSigned(token: String?): Claims =
        Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token).body


    private fun getSigningKey(): Key =
        SecretKeySpec(DatatypeConverter.parseBase64Binary(jwtProperties.secret), SignatureAlgorithm.HS256.jcaName)

    private fun expiredAt(ttl: Int): Date {
        val expiration = Date()
        var msec = expiration.time
        msec = msec / 1000 * 1000 + ttl
        expiration.time = msec
        return expiration
    }
}