package com.group.libraryapp.config

import com.group.libraryapp.security.AccessTokenAuthenticationFilter
import com.group.libraryapp.security.CustomLoggingFilter
import com.group.libraryapp.security.ExceptionHandlerFilter
import com.group.libraryapp.security.JWTTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.*

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfig(jwtTokenProvider: JWTTokenProvider): WebSecurityConfigurerAdapter() {
    private val accessTokenAuthenticationFilter = AccessTokenAuthenticationFilter(jwtTokenProvider)

    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable()
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(logFilter(), UsernamePasswordAuthenticationFilter::class.java)
                .addFilterBefore(ExceptionHandlerFilter(), UsernamePasswordAuthenticationFilter::class.java)
                .addFilterAt(accessTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
                .headers().frameOptions().sameOrigin()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = Argon2PasswordEncoder()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource =
        UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**",
                CorsConfiguration().apply {
                    allowedOrigins = listOf("*")
                    allowedHeaders = listOf("*")
                    allowedMethods = listOf("*")
                    allowCredentials = true
                }
            )
        }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager =
        super.authenticationManagerBean()

    fun logFilter(): CustomLoggingFilter =
         CustomLoggingFilter(hashSetOf("/ping"), hashSetOf("/sign-up")).apply {
            setIncludeQueryString(true)
            setIncludePayload(true)
            setMaxPayloadLength(10240)
            setIncludeHeaders(true)
        }
}