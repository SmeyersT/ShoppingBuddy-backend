package tom.smeyers.shoppingbuddybackend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tom.smeyers.shoppingbuddybackend.model.domain.ShoppingCart
import tom.smeyers.shoppingbuddybackend.model.dto.ShoppingCartDTO
import tom.smeyers.shoppingbuddybackend.services.interfaces.ShoppingCartService

@RestController
@RequestMapping("/api/shoppingCart")
class ShoppingListController {

    private final val logger: Logger = LoggerFactory.getLogger(ShoppingListController::class.java)

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var shoppingCartService: ShoppingCartService

    @PostMapping("/updateShoppingCart")
    fun updateShoppingCart(@RequestBody shoppingCartDTO: ShoppingCartDTO): ResponseEntity<ShoppingCartDTO> {
        logger.info("updateShoppingCart method called.")
        val shoppingCart = objectMapper.convertValue(shoppingCartDTO, ShoppingCart::class.java)
        val updatedShoppingCart = shoppingCartService.updateShoppingCart(shoppingCart)
        return ResponseEntity.ok(objectMapper.convertValue(updatedShoppingCart, ShoppingCartDTO::class.java))
    }

}
