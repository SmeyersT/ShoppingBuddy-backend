package tom.smeyers.shoppingbuddybackend.controller

import tom.smeyers.shoppingbuddybackend.config.security.JwtTokenProvider
import javassist.NotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tom.smeyers.shoppingbuddybackend.helper.GoogleHelper
import tom.smeyers.shoppingbuddybackend.model.domain.User
import tom.smeyers.shoppingbuddybackend.services.interfaces.UserService
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/login")
class AuthenticationController {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var googleHelper: GoogleHelper

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    private val logger: Logger = LoggerFactory.getLogger(AuthenticationController::class.java)

    @CrossOrigin
    @PostMapping("/authorize")
    fun requestAuthorization(@RequestHeader("Authorization") authHeader: String): ResponseEntity<String> {
        logger.info("requestAuthorization method called.")
        val token = googleHelper.verifyGoogleToken(authHeader.substring(7))
        if(token == null){
            logger.error("Token is invalid.")
            return ResponseEntity.badRequest().build()
        }
        val email = token.payload.email

        return try{
            val foundUser = userService.getByEmail(email)
            ResponseEntity(jwtTokenProvider.createToken(email),HttpStatus.OK)
        } catch (ex: NotFoundException) {
            val firstName = token.payload["given_name"] as String
            val lastName = token.payload["family_name"] as String
            val picture = token.payload["picture"] as String
            val newUser = User(firstName = firstName, lastName = lastName, email = email, imgUrl = picture, createdOn = LocalDateTime.now())
            userService.save(newUser)
            logger.info("New user created with email $email and name $firstName $lastName.")
            val jwt = jwtTokenProvider.createToken(email)
            ResponseEntity(jwt, HttpStatus.OK)
        }
    }

    @CrossOrigin
    @PostMapping("/refresh")
    fun refreshJWT(@RequestHeader("Authorization") authHeader: String): ResponseEntity<Any> {
        val token = authHeader.substring(7)
        if (jwtTokenProvider.validateToken(token)) {
            return try {
                val name = jwtTokenProvider.getUsername(token)
                val jwt = jwtTokenProvider.createToken(name)
                logger.info("token refreshed")
                ResponseEntity(jwt, HttpStatus.OK)

            } catch (exception: Exception){
                return ResponseEntity.badRequest().build()
            }

        }
        return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }


}