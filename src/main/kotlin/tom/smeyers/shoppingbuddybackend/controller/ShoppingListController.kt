package tom.smeyers.shoppingbuddybackend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import javassist.NotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tom.smeyers.shoppingbuddybackend.model.domain.ShoppingCart
import tom.smeyers.shoppingbuddybackend.model.domain.ShoppingCartItem
import tom.smeyers.shoppingbuddybackend.model.dto.ShoppingCartDTO
import tom.smeyers.shoppingbuddybackend.model.dto.ShoppingCartWithItemWrapper
import tom.smeyers.shoppingbuddybackend.services.interfaces.ShoppingCartItemService
import tom.smeyers.shoppingbuddybackend.services.interfaces.ShoppingCartService

@RestController
@RequestMapping("/api/shoppingCart")
class ShoppingListController {

    private final val logger: Logger = LoggerFactory.getLogger(ShoppingListController::class.java)

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var shoppingCartService: ShoppingCartService

    @Autowired
    private lateinit var shoppingCartItemService: ShoppingCartItemService

    @PostMapping("/updateShoppingCart")
    fun updateShoppingCart(@RequestBody shoppingCartDTO: ShoppingCartDTO): ResponseEntity<ShoppingCartDTO> {
        logger.info("updateShoppingCart method called.")
        val shoppingCart = objectMapper.convertValue(shoppingCartDTO, ShoppingCart::class.java)
        val updatedShoppingCart = shoppingCartService.updateShoppingCart(shoppingCart)
        return ResponseEntity.ok(objectMapper.convertValue(updatedShoppingCart, ShoppingCartDTO::class.java))
    }

    @PostMapping("/addCartItem")
    fun addShoppingCartItem(@RequestHeader("Authorization") authHeader: String, @RequestBody shoppingCartAndItemWrapper: ShoppingCartWithItemWrapper): ResponseEntity<ShoppingCartDTO> {
        logger.info("addCartItem method called.")
        return try {
            val cart = shoppingCartService.getById(shoppingCartAndItemWrapper.shoppingCart.id)
            val item = objectMapper.convertValue(shoppingCartAndItemWrapper.shoppingCartItem, ShoppingCartItem::class.java)

            val shoppingCart = shoppingCartService.addItemToCart(cart, item)
            ResponseEntity.ok(objectMapper.convertValue(shoppingCart, ShoppingCartDTO::class.java))
        } catch (ex: NotFoundException) {
            ResponseEntity.badRequest().build<ShoppingCartDTO>()
        }
    }

    @PostMapping("/removeCartItem")
    fun removeShoppingCartItem(@RequestHeader("Authorization") authHeader: String, @RequestBody shoppingCartAndItemWrapper: ShoppingCartWithItemWrapper): ResponseEntity<ShoppingCartDTO> {
        logger.info("removeCartItem method called.")
        return try {
            val cart = shoppingCartService.getById(shoppingCartAndItemWrapper.shoppingCart.id)
            val item = shoppingCartItemService.getById(shoppingCartAndItemWrapper.shoppingCartItem.id)

            val shoppingCart = shoppingCartService.removeItemFromCart(cart, item)
            ResponseEntity.ok(objectMapper.convertValue(shoppingCart, ShoppingCartDTO::class.java))
        } catch (ex: NotFoundException) {
            ResponseEntity.badRequest().build<ShoppingCartDTO>()
        }
    }


}
