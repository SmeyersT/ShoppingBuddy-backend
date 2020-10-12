package tom.smeyers.shoppingbuddybackend.config.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter
import tom.smeyers.shoppingbuddybackend.CustomException
import tom.smeyers.shoppingbuddybackend.config.security.JwtTokenProvider
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenFilter(private val jwtTokenProvider: JwtTokenProvider) : OncePerRequestFilter() {

    private val skipUrls: Set<String> = HashSet(listOf("/h2-console/**", "/", "/api/login/**"))
    private val pathMatcher = AntPathMatcher()

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, filterChain: FilterChain) {
        val token = jwtTokenProvider.resolveToken(httpServletRequest)
        try {
            if (jwtTokenProvider.validateToken(token)) {
                val auth = jwtTokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = auth
            }
        } catch (ex: CustomException) {
            SecurityContextHolder.clearContext()
            httpServletResponse.sendError(ex.httpStatus.value(), ex.message)
            return
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }



    @Throws(ServletException::class)
    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return skipUrls.stream().anyMatch { p: String? -> pathMatcher.match(p!!, request.servletPath) }
    }

}