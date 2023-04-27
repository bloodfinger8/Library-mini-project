package com.group.libraryapp.usecase.user

import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.command.SignUpCommand
import com.group.libraryapp.exception.signUpFail
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignUpUseCase (
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
){
    @Transactional
    fun signUp(command: SignUpCommand): User {
        emailDuplicateCheck(command)
        return userRepository.save(User.create(Email(command.email), passwordEncoder.encode(command.password), command.name))
    }

    private fun emailDuplicateCheck(command: SignUpCommand) {
        if(userRepository.existsByEmail(Email(command.email))) signUpFail(command.email);
    }
}