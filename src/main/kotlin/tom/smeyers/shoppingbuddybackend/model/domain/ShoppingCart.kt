package tom.smeyers.shoppingbuddybackend.model.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class ShoppingCart(
        @Id
        @GeneratedValue
        var id: Long = 0,
        var isRepeating: Boolean,
        @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
        @JoinColumn(name = "shopping_cart_id")
        var items: MutableList<ShoppingCartItem> = mutableListOf<ShoppingCartItem>(),
        var isPersonalCart: Boolean
)
