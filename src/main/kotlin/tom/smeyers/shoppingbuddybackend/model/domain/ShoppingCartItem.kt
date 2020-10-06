package tom.smeyers.shoppingbuddybackend.model.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class ShoppingCartItem(
        @Id
        @GeneratedValue
        var id: Long = 0,
        var amount: Int = 1,
        var addedOn: LocalDateTime,
        var isBought: Boolean,
        var isRepeating: Boolean = false,
        var name: String
)
