package tom.smeyers.shoppingbuddybackend.exceptions

import org.springframework.http.HttpStatus

class CustomException(override val message: String, val httpStatus: HttpStatus) : RuntimeException()