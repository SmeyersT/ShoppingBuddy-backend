package tom.smeyers.shoppingbuddybackend.config.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tom.smeyers.shoppingbuddybackend.model.domain.User
import tom.smeyers.shoppingbuddybackend.services.interfaces.UserService


@Service
@Transactional
class MyUserDetails : UserDetailsService {
    @Autowired
    private lateinit var userService: UserService

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User? = userService.getByEmail(username)
        return if (user != null) {
            org.springframework.security.core.userdetails.User //
                    .withUsername(username) //
                    .password("")
                    .authorities(mutableListOf())
                    .accountExpired(false) //
                    .accountLocked(false) //
                    .credentialsExpired(false) //
                    .disabled(false) //
                    .build()
        } else {
            throw UsernameNotFoundException("User '$username' not found.")
        }
    }

}