package web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;


@EnableWebSecurity
@Configuration
public class SecurityConfig2 extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    /**
     * сервис, с помощью которого тащим пользователя
     */
    private final LoginSuccessHandler successUserHandler;

    /**
     * класс, в котором описана логика перенаправления пользователей по ролям
     */

    public SecurityConfig2(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, LoginSuccessHandler successUserHandler) {
        this.userDetailsService = userDetailsService;
        this.successUserHandler = successUserHandler;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); /** конфигурация для прохождения аутентификации*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/addUser").hasRole("ADMIN")
                .antMatchers("/{id}/admin")
                .hasRole("ADMIN")
                .antMatchers("/allUsers").hasRole("ADMIN")
                .antMatchers("/{id}").hasRole("ADMIN")
                .antMatchers("/page/user").hasRole("USER")
                .anyRequest().authenticated()
                .and().formLogin()
                .successHandler(successUserHandler)
                .and()
                .logout()
                .permitAll()
                .invalidateHttpSession(true)
                .clearAuthentication(true);

    }

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}