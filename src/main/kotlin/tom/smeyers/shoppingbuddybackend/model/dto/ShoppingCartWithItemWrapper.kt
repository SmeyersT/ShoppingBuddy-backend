package tom.smeyers.shoppingbuddybackend.model.dto

import java.time.LocalDateTime

class ShoppingCartWithItemWrapper(
        val shoppingCart: ShoppingCartDTO,
        val shoppingCartItem: ShoppingCartItemDTO
)