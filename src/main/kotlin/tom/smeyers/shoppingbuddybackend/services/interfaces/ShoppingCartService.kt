package tom.smeyers.shoppingbuddybackend.services.interfaces

import tom.smeyers.shoppingbuddybackend.model.domain.ShoppingCart

interface ShoppingCartService {
    fun save(shoppingcart: ShoppingCart): ShoppingCart
    fun updateShoppingCart(shoppingcart: ShoppingCart): ShoppingCart
}
