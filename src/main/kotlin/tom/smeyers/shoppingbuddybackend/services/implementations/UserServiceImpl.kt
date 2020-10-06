package tom.smeyers.shoppingbuddybackend.services.implementations

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tom.smeyers.shoppingbuddybackend.model.domain.User
import tom.smeyers.shoppingbuddybackend.repository.UserRepository
import tom.smeyers.shoppingbuddybackend.services.interfaces.UserService
import javax.transaction.Transactional

@Transactional
@Service
class UserServiceImpl : UserService {

    @Autowired
    private lateinit var userRepo: UserRepository

    override fun save(user: User): User {
        return userRepo.save(user)
    }

    override fun findUserById(id: Long): User {
        return userRepo.findById(id).get()
    }
}
