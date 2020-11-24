package tom.smeyers.shoppingbuddybackend.config.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var userDetailsService: MyUserDetails

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()

        http.headers().frameOptions().disable()

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)

        http.authorizeRequests()
                .antMatchers("/api/login/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/app/**").authenticated()
                .antMatchers("/topic/**").authenticated()
                .antMatchers("/socket/**").authenticated()
                .anyRequest().authenticated()

        http.formLogin().disable()

        http.addFilterBefore(JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)

    }

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }


}