package tom.smeyers.shoppingbuddybackend.model.dto

import tom.smeyers.shoppingbuddybackend.model.domain.ShoppingCartItem
import java.time.LocalDateTime
import javax.persistence.OneToMany

class ShoppingCartDTO(
        val id: Long,
        val isRepeating: Boolean,
        val addedOn: LocalDateTime,
        val items: MutableList<ShoppingCartItemDTO>,
        val isPersonalCart: Boolean
)
