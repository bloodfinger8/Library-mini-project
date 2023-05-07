package com.group.libraryapp.usecase

import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserPrincipalDetailsService(
        val userRepository: UserRepository,
): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByEmail(Email(username!!)) ?: throw UsernameNotFoundException("creator not found exception")
        return UserPrincipalDetails(user)
    }
}