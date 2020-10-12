package tom.smeyers.shoppingbuddybackend.config.security

object SecurityConstants {
    const val EXPIRATION_TIME: Long =  432000000 // 5 days
    const val TOKEN_PREFIX = "Bearer "
    const val HEADER_STRING = "Authorization"
}