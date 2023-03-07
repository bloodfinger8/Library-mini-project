package com.group.libraryapp.usecase.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.util.*

@Service
class JWTTokenService (
    val jwtProperties: JWTProperties
){

    fun signAcToken(token: JWTAccessToken): String {
        return Jwts.builder()
            .setIssuer("jaewoo")
            .claim("id", "")
            .claim("userId", "")
            .claim("name", "")
            .claim("tokenType", "")
            .setExpiration(expiredAt(JWTAccessToken.TTL))
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secret)
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