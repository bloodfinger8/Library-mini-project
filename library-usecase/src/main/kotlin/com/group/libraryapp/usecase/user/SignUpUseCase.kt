package com.group.libraryapp.usecase.user

import com.group.libraryapp.domain.company.Company
import com.group.libraryapp.domain.company.CompanyRepository
import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.factory.UserFactory
import com.group.libraryapp.exception.companyNotFoundFail
import com.group.libraryapp.exception.signUpFail
import com.group.libraryapp.usecase.user.dto.command.SignUpCommand
import com.group.libraryapp.usecase.user.dto.response.SignUpDto
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignUpUseCase(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val companyRepository: CompanyRepository
) {
    @Transactional
    fun signUp(command: SignUpCommand): SignUpDto {
        emailDuplicateCheck(command)
        val user = saveUser(command, findCompany(command))
        return SignUpDto.from(user)
    }

    private fun findCompany(command: SignUpCommand) =
        companyRepository.findById(command.companyId) ?: companyNotFoundFail(command.companyId)

    private fun saveUser(
        command: SignUpCommand,
        company: Company
    ): User =
        userRepository.save(
            UserFactory.create(
                Email(command.email),
                passwordEncoder.encode(command.password),
                command.name,
                company = company
            )
        )

    private fun emailDuplicateCheck(command: SignUpCommand) {
        if (userRepository.existsByEmail(Email(command.email))) signUpFail(command.email)
    }
}
