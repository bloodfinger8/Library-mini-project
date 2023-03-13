package com.group.libraryapp.config

import com.group.libraryapp.security.AccessTokenAuthenticationFilter
import com.group.libraryapp.security.CustomLoggingFilter
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
                .cors(Customizer.withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(logFilter(), UsernamePasswordAuthenticationFilter::class.java)
                .addFilterAt(accessTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
                .headers().frameOptions().sameOrigin()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return Argon2PasswordEncoder()
    }


    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        //todo -> 환경변수파일로 cors 값 처리
        configuration.allowedOrigins = mutableListOf("http://localhost:8080")
        configuration.allowedMethods = Collections.singletonList("*")
        configuration.allowedHeaders = Collections.singletonList("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source;
    }

    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    fun logFilter(): CustomLoggingFilter {
        val ignores: MutableSet<String> = HashSet()
        ignores.add("/ping")
        val sensitive: MutableSet<String> = HashSet()
        sensitive.add("/sign-up")
        val filter = CustomLoggingFilter(ignores, sensitive)
        filter.setIncludeQueryString(true)
        filter.setIncludePayload(true)
        filter.setMaxPayloadLength(10240)
        filter.setIncludeHeaders(true)
        return filter
    }
}