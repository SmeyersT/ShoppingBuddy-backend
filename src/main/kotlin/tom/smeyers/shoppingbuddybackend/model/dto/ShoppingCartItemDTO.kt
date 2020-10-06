package tom.smeyers.shoppingbuddybackend.model.dto

import java.time.LocalDateTime

class ShoppingCartItemDTO(
        val id: Long,
        val amount: Int,
        val addedOn: LocalDateTime,
        val isBought: Boolean,
        val isRepeating: Boolean = false,
        val name: String
)
