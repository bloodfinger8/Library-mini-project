package com.group.libraryapp.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("jwt")
class JWTProperties {
    lateinit var secret: String
}
