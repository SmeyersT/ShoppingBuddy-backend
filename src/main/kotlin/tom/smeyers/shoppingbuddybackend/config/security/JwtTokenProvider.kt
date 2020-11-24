package tom.smeyers.shoppingbuddybackend.config.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import tom.smeyers.shoppingbuddybackend.exceptions.CustomException
import tom.smeyers.shoppingbuddybackend.config.security.SecurityConstants.EXPIRATION_TIME
import tom.smeyers.shoppingbuddybackend.config.security.SecurityConstants.HEADER_STRING
import tom.smeyers.shoppingbuddybackend.config.security.SecurityConstants.TOKEN_PREFIX
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider {

    private val validityInMilliseconds: Long = EXPIRATION_TIME
    private val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    @Autowired
    private lateinit var myUserDetails: MyUserDetails

    fun createToken(username: String/*, roles: List<Role>**/): String {
        val claims: Claims = Jwts.claims().setSubject(username)
                //claims["auth"] = roles.stream().map { s: Role -> SimpleGrantedAuthority(s.authority) }.filter { obj: SimpleGrantedAuthority? -> Objects.nonNull(obj) }.collect(Collectors.toList())
        val now = Date()
        val validity = Date(now.time + validityInMilliseconds)
        return Jwts.builder() //
                .setClaims(claims) //
                .setIssuedAt(now)//
                .setExpiration(validity) //
                .signWith(secretKey) //
                .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails = myUserDetails.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUsername(token: String): String {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body.subject
    }

    fun resolveToken(req: HttpServletRequest): String {
        val bearerToken = req.getHeader(HEADER_STRING)
        return if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            bearerToken.substring(7)
        } else req.getParameter("token")
    }

    fun validateToken(token: String?): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
            true
        } catch (e: JwtException) {
            throw CustomException("Expired or invalid JWT token", HttpStatus.FORBIDDEN)
        } catch (e: IllegalArgumentException) {
            throw CustomException("Expired or invalid JWT token", HttpStatus.FORBIDDEN)
        }
    }

}
