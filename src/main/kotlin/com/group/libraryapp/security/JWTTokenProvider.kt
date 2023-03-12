package com.group.libraryapp.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter

@Component
class JWTTokenProvider (
    val jwtProperties: JWTProperties
){

    fun signAcToken(token: JWTAccessToken): String {
        val hS256 = SignatureAlgorithm.HS256
        val signingKey = SecretKeySpec(DatatypeConverter.parseBase64Binary(jwtProperties.secret), hS256.jcaName)

        return Jwts.builder()
            .setSubject("JaeWoo")
            .claim("email", token.email)
            .claim("name", token.name)
            .claim("tokenType", token.userType)
            .setExpiration(expiredAt(JWTAccessToken.TTL))
            .signWith(signingKey,hS256)
            .compact()
    }

    fun accessTokenFromSigned() {

    }






    private fun expiredAt(ttl: Int): Date {
        val expiration = Date()
        var msec = expiration.time
        msec = msec / 1000 * 1000 + ttl
        expiration.time = msec
        return expiration
    }
}