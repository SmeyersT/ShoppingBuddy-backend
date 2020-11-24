package tom.smeyers.shoppingbuddybackend.model.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class ShoppingCartItem(
        @Id
        @GeneratedValue
        var id: Long = 0,
        var isBought: Boolean,
        var name: String
)
