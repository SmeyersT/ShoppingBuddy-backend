package tom.smeyers.shoppingbuddybackend.services.implementations

import javassist.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tom.smeyers.shoppingbuddybackend.model.domain.ShoppingCartItem
import tom.smeyers.shoppingbuddybackend.repository.ShoppingCartItemRepository
import tom.smeyers.shoppingbuddybackend.services.interfaces.ShoppingCartItemService
import javax.transaction.Transactional

@Transactional
@Service
class ShoppingCartItemServiceImpl : ShoppingCartItemService {

    @Autowired
    private lateinit var shoppingCartItemRepo: ShoppingCartItemRepository


    @Throws(NotFoundException::class)
    override fun getById(id: Long): ShoppingCartItem {
        val item = shoppingCartItemRepo.findById(id)
        return if(item.isPresent) {
            item.get()
        }
        else throw NotFoundException("Unable to find shoppingCartItem with id: $id.")
    }
}