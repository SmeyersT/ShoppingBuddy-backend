package tom.smeyers.shoppingbuddybackend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.stereotype.Controller
import tom.smeyers.shoppingbuddybackend.model.dto.ShoppingCartDTO
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping
import tom.smeyers.shoppingbuddybackend.model.domain.ShoppingCart
import tom.smeyers.shoppingbuddybackend.model.dto.ShoppingCartWithItemWrapper
import tom.smeyers.shoppingbuddybackend.model.dto.WebSocketMessage
import tom.smeyers.shoppingbuddybackend.services.interfaces.ShoppingCartItemService
import tom.smeyers.shoppingbuddybackend.services.interfaces.ShoppingCartService

@Controller
class ShoppingListWebSocketController {

    private final val logger: Logger = LoggerFactory.getLogger(ShoppingListWebSocketController::class.java)

    @Autowired
    private lateinit var shoppingCartService: ShoppingCartService

    @Autowired
    private lateinit var shoppingCartItemService: ShoppingCartItemService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MessageMapping("/shoppingList/{shoppingCartId}")
    @SendTo("/topic/shoppingLists/{shoppingCartId}")
    @Throws(Exception::class)
    fun updateShoppingCart(@Payload message: String, @DestinationVariable("shoppingCartId") shoppingCartId: String): String {
        logger.info("updateShoppingCart called. Message: $message")
        val cartDTO = objectMapper.readValue(message, ShoppingCartDTO::class.java)
        val cart = objectMapper.convertValue(cartDTO, ShoppingCart::class.java)
        val savedCart = shoppingCartService.updateShoppingCart(cart)
        print(savedCart.toString())
        return objectMapper.writeValueAsString(objectMapper.convertValue(savedCart, ShoppingCartDTO::class.java))
    }

    @MessageMapping("/shoppingList/{shoppingCartId}/remove")
    @SendTo("/topic/shoppingLists/{shoppingCartId}")
    @Throws(Exception::class)
    fun removeShoppingCartItem(@Payload message: String, @DestinationVariable("shoppingCartId") shoppingCartId: String): String {
        logger.info("removeShoppingCartItem called. Message: $message")
        val wrapper = objectMapper.readValue(message, ShoppingCartWithItemWrapper::class.java)
        val cart = shoppingCartService.getById(wrapper.shoppingCart.id)
        val cartItem = shoppingCartItemService.getById(wrapper.shoppingCartItem.id)
        val savedCart = shoppingCartService.removeItemFromCart(cart, cartItem)
        return objectMapper.writeValueAsString(objectMapper.convertValue(savedCart, ShoppingCartDTO::class.java))
    }

    @MessageMapping("/shoppingList/{shoppingCartId}/update")
    @SendTo("/topic/shoppingLists/{shoppingCartId}")
    @Throws(Exception::class)
    fun updateShoppingCartItem(@Payload message: String, @DestinationVariable("shoppingCartId") shoppingCartId: String): String {
        logger.info("updateShoppingCartItem called. Message: $message")
        val wrapper = objectMapper.readValue(message, ShoppingCartWithItemWrapper::class.java)
        val cart = shoppingCartService.getById(wrapper.shoppingCart.id)
        val cartItem = shoppingCartItemService.getById(wrapper.shoppingCartItem.id)
        cartItem.isBought = wrapper.shoppingCartItem.isBought
        cartItem.name = wrapper.shoppingCartItem.name
        val savedCart = shoppingCartService.updateCartItem(cart, cartItem)
        return objectMapper.writeValueAsString(objectMapper.convertValue(savedCart, ShoppingCartDTO::class.java))
    }

}
