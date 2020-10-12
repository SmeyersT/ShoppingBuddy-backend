package tom.smeyers.shoppingbuddybackend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tom.smeyers.shoppingbuddybackend.model.dto.UserDTO
import tom.smeyers.shoppingbuddybackend.services.interfaces.UserService


@RestController
@RequestMapping("/api/user")
class UserController {

    private final val logger: Logger = LoggerFactory.getLogger(UserController::class.java)

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var userService: UserService

    @GetMapping("/getCurrentUser")
    fun getCurrentUser(@RequestHeader("Authorization") authHeader: String) : ResponseEntity<UserDTO> {
        logger.info("getCurrentUser method called.")
        val user = userService.getCurrentUser(authHeader.substring(7))
        val currentUserDTO = objectMapper.convertValue(user, UserDTO::class.java)
        return ResponseEntity.ok(currentUserDTO)
    }

}
