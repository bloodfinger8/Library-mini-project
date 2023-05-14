package com.group.libraryapp.usecase

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.util.UserRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipalDetails(val user: User) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return AuthorityUtils.createAuthorityList(UserRole.ROLE_USER)
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.email.name()
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
