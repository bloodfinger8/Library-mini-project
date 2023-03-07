package com.group.libraryapp.usecase.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("jwt")
class JWTProperties {
    lateinit var secret: String
}
