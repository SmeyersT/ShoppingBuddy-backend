package tom.smeyers.shoppingbuddybackend.services.implementations

import javassist.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tom.smeyers.shoppingbuddybackend.config.security.JwtTokenProvider
import tom.smeyers.shoppingbuddybackend.model.domain.User
import tom.smeyers.shoppingbuddybackend.repository.UserRepository
import tom.smeyers.shoppingbuddybackend.services.interfaces.UserService
import javax.transaction.Transactional

@Transactional
@Service
class UserServiceImpl : UserService {

    @Autowired
    private lateinit var userRepo: UserRepository

    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider

    override fun save(user: User): User {
        return userRepo.save(user)
    }

    override fun getUserById(id: Long): User {
        return userRepo.findById(id).get()
    }

    @Throws(NotFoundException::class)
    override fun getByEmail(email: String): User {
        return userRepo.findByEmail(email)?: throw NotFoundException("Unable to find user with email $email: User not found.")
    }

    override fun getCurrentUser(token: String): User {
        return getByEmail(tokenProvider.getUsername(token))
    }
}
