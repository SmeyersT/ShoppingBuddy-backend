package tom.smeyers.shoppingbuddybackend.model.dto

import java.time.LocalDateTime

class UserDTO(
        val id: Long,
        val firstName: String,
        val lastName: String,
        val email: String,
        val createdOn: LocalDateTime,
        val imgUrl: String,
        val personalShoppingCart: ShoppingCartDTO
)
