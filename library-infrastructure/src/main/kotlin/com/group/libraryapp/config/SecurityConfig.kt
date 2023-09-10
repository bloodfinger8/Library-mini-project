package com.group.libraryapp.config

import com.group.libraryapp.filter.CustomLoggingFilter
import com.group.libraryapp.filter.ExceptionHandlerFilter
import com.group.libraryapp.security.AccessTokenAuthenticationFilter
import com.group.libraryapp.security.JWTTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(jwtTokenProvider: JWTTokenProvider) {
    private val accessTokenAuthenticationFilter = AccessTokenAuthenticationFilter(jwtTokenProvider)

    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http.httpBasic { it.disable() }
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(logFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(ExceptionHandlerFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterAt(accessTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .headers { headers -> headers.frameOptions { it.sameOrigin() } }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource =
        UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration(
                "/**",
                CorsConfiguration().apply {
                    allowedOrigins = listOf("*")
                    allowedHeaders = listOf("*")
                    allowedMethods = listOf("*")
                    allowCredentials = true
                }
            )
        }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun logFilter(): CustomLoggingFilter? {
        val filter = CustomLoggingFilter()
        filter.setIncludeQueryString(true)
        filter.setIncludePayload(true)
        filter.setMaxPayloadLength(1024 * 500)
        filter.setIncludeHeaders(true)
        filter.setAfterMessagePrefix("REQUEST DATA : ")
        return filter
    }
}
