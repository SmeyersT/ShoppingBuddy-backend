package tom.smeyers.shoppingbuddybackend.services.interfaces

import tom.smeyers.shoppingbuddybackend.model.domain.ShoppingCart
import tom.smeyers.shoppingbuddybackend.model.domain.ShoppingCartItem

interface ShoppingCartService {
    fun save(shoppingcart: ShoppingCart): ShoppingCart
    fun updateShoppingCart(shoppingcart: ShoppingCart): ShoppingCart
    fun getById(id: Long): ShoppingCart
    fun addItemToCart(shoppingCart: ShoppingCart, item: ShoppingCartItem): ShoppingCart
    fun removeItemFromCart(shoppingcart: ShoppingCart, item: ShoppingCartItem): ShoppingCart
    fun updateCartItem(shoppingcart: ShoppingCart, item: ShoppingCartItem): ShoppingCart
}
