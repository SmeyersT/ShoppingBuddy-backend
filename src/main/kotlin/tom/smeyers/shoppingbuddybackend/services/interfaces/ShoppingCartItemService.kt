package tom.smeyers.shoppingbuddybackend.services.interfaces

import org.springframework.stereotype.Service
import tom.smeyers.shoppingbuddybackend.model.domain.ShoppingCartItem

@Service
interface ShoppingCartItemService {
    fun getById(id: Long): ShoppingCartItem
}