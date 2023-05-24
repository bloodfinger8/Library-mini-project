package com.group.libraryapp.usecase.user

import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.usecase.user.dto.command.SignInCommand
import com.group.libraryapp.usecase.user.dto.response.UserSignInDto
import com.group.libraryapp.exception.loginFail
import com.group.libraryapp.security.JWTAccessToken
import com.group.libraryapp.security.JWTTokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignInUseCase(
    val authenticationManager: AuthenticationManager,
    val tokenProvider: JWTTokenProvider,
    val userRepository: UserRepository,
) {
    @Transactional
    fun signIn(command: SignInCommand): UserSignInDto {
        val user = findUser(command)
        emailPasswordAuthenticate(command)
        return UserSignInDto.of(user, accessToken(user))
    }

    private fun findUser(command: SignInCommand) =
        userRepository.findByEmail(Email(command.email)) ?: loginFail()

    private fun emailPasswordAuthenticate(command: SignInCommand) {
        SecurityContextHolder.getContext().authentication =
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(command.email, command.password))
    }

    private fun accessToken(user: User) =
        tokenProvider.signAcToken(JWTAccessToken.of(user.id!!, user.email, user.name, companyId = user.company!!.id!!))
}
