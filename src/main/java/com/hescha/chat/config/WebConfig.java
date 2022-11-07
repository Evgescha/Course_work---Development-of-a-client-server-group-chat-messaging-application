package com.hescha.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ApplicationContext context;


    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/img/**", "/fonts/**",
                        "/j_spring_security_check", "/webfonts/**", "/favicon.ico").permitAll()
                .antMatchers("/login", "/j _spring_security_check", "/registration").anonymous()
//                .antMatchers("**/**").hasRole("ADMIN")
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/login")
                .loginProcessingUrl("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID");

        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
