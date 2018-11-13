package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class InMemoryAuthentication extends WebSecurityConfigurerAdapter {
    @Bean
    BCryptPasswordEncoder myBycryptEncoder(){
        return new BCryptPasswordEncoder();
    }

   /* @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/add", "/edit/**").hasAuthority("OWNER")
                .antMatchers("/ownerorusers").hasAnyAuthority("OWNER")
                .anyRequest().authenticated()
                .and().formLogin();
    }*/

  /*  @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        //comma separates authorities(separate strings)
        auth.inMemoryAuthentication().withUser("steven").password(myBycryptEncoder().encode("apartment$")).authorities("OWNER")
                .and().passwordEncoder(myBycryptEncoder());
    }*/

    @Autowired
    public SSUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception{
        return new SSUserDetailsService(userRepository);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/", "/h2-console/**", "/register").permitAll()
                .antMatchers("/add", "/edit/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").permitAll().permitAll()
                .and()
                .httpBasic();
        http
                .csrf().disable();
        http
                .headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(myBycryptEncoder());
    }



}
