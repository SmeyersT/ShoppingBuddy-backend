package tom.smeyers.shoppingbuddybackend.model.dto

class ShoppingCartDTO(
        val id: Long,
        val isRepeating: Boolean,
        val items: MutableList<ShoppingCartItemDTO>,
        val isPersonalCart: Boolean
)
