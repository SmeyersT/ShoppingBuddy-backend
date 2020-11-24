package tom.smeyers.shoppingbuddybackend.services.implementations

import javassist.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tom.smeyers.shoppingbuddybackend.model.domain.ShoppingCart
import tom.smeyers.shoppingbuddybackend.model.domain.ShoppingCartItem
import tom.smeyers.shoppingbuddybackend.repository.ShoppingCartRepository
import tom.smeyers.shoppingbuddybackend.services.interfaces.ShoppingCartService
import javax.transaction.Transactional

@Transactional
@Service
class ShoppingCartServiceImpl : ShoppingCartService {

    @Autowired
    private lateinit var shoppingCartRepo: ShoppingCartRepository

    override fun save(shoppingCart: ShoppingCart): ShoppingCart {
        return shoppingCartRepo.save(shoppingCart)
    }

    @Throws(NotFoundException::class)
    override fun getById(id: Long): ShoppingCart {
        val cart = shoppingCartRepo.findById(id)
        return if(cart.isPresent) {
            cart.get()
        }
        else throw NotFoundException("Unable to find shoppingCart with id: $id.")
    }

    @Throws(NotFoundException::class)
    override fun updateShoppingCart(shoppingcart: ShoppingCart): ShoppingCart {
        val shoppingCartToUpdate = shoppingCartRepo.findById(shoppingcart.id)
        if(shoppingCartToUpdate.isPresent) {
            return save(shoppingcart)
        }
        else {
            throw NotFoundException("Shoppingcart with id: ${shoppingcart.id} not found.")
        }
    }

    override fun addItemToCart(shoppingCart: ShoppingCart, item: ShoppingCartItem): ShoppingCart {
        shoppingCart.items.add(item)
        return save(shoppingCart)
    }

    override fun removeItemFromCart(shoppingcart: ShoppingCart, item: ShoppingCartItem): ShoppingCart {
        shoppingcart.items.remove(item)
        return save(shoppingcart)
    }

    override fun updateCartItem(shoppingcart: ShoppingCart, item: ShoppingCartItem): ShoppingCart {
        val itemIndex = shoppingcart.items.find { it.id == item.id }
        shoppingcart.items[shoppingcart.items.indexOf(itemIndex)] = item
        return save(shoppingcart)
    }
}
