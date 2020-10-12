package tom.smeyers.shoppingbuddybackend.repository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tom.smeyers.shoppingbuddybackend.model.domain.User

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}
