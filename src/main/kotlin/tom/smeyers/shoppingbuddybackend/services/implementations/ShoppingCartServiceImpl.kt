package tom.smeyers.shoppingbuddybackend.services.implementations

import javassist.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tom.smeyers.shoppingbuddybackend.model.domain.ShoppingCart
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

    override fun updateShoppingCart(shoppingcart: ShoppingCart): ShoppingCart {
        val shoppingCartToUpdate = shoppingCartRepo.findById(shoppingcart.id)
        if(shoppingCartToUpdate.isPresent) {
            return save(shoppingcart)
        }
        else {
            throw NotFoundException("Shoppingcart with id: ${shoppingcart.id} not found.")
        }
        //todo: handle shoppingCart not found
    }
}
