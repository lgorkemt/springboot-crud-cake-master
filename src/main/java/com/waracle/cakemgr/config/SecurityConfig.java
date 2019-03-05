package com.waracle.cakemgr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("scott").password("{noop}tiger").authorities("ROLE_USER" ,"ROLE_ADMIN")
                .and()
                .withUser("john").password("{noop}simple").authorities("ROLE_USER")
                .and()
                .withUser("pamela").password("{noop}manager").authorities("ROLE_MANAGER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.authorizeRequests()
                .antMatchers("/ui/showFormForUpdate*").hasAnyRole( "ADMIN", "MANAGER")
                .antMatchers("/ui/save*").hasAnyRole( "ADMIN", "MANAGER")
                .antMatchers("/ui/delete*").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/ui/showFormForAdd*").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/cakes/**").hasRole("ADMIN")
                .antMatchers("/ui/list*").permitAll()
                .antMatchers("/ui/onlyForManagers*").hasAnyRole(  "MANAGER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/showMyLoginPage")
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");

    }
}