package fi.tuni.tamk.tiko.bmb.blogbackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                // Enable public GET requests to everybody.
                .antMatchers(HttpMethod.GET, "/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/**").permitAll()//.hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/**").permitAll()//.hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/posts/*/comment").permitAll()
                .antMatchers(HttpMethod.POST, "/api/posts/*/like").permitAll()
                .antMatchers(HttpMethod.POST, "/api/**").permitAll();//.hasRole("ADMIN")
                //.anyRequest().authenticated();
                //.and()
                //.csrf().disable().cors().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("user"))
                .roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("admin"))
                .roles("ADMIN");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
