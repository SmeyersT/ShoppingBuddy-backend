package tom.smeyers.shoppingbuddybackend.services.interfaces

import org.springframework.stereotype.Service
import tom.smeyers.shoppingbuddybackend.model.domain.User

@Service
interface UserService {
    fun save(user: User): User
    fun getUserById(id: Long): User
    fun getByEmail(email: String): User
    fun getCurrentUser(token: String): User
}
