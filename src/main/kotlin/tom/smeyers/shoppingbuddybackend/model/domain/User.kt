package tom.smeyers.shoppingbuddybackend.model.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
        @Id
        @GeneratedValue
        var id: Long = 0,
        var firstName: String,
        var lastName: String,
        var email: String,
        var createdOn: LocalDateTime,
        var imgUrl: String,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(referencedColumnName = "id", unique = true)
        var personalShoppingCart: ShoppingCart = ShoppingCart(0, false, LocalDateTime.now(), mutableListOf<ShoppingCartItem>(), true)
)
