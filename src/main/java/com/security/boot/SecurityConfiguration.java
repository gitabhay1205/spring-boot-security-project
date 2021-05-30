package com.security.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	//we override this method for Authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			
		//here we are using inmemory authentication so now spring boot will not create default user
		auth.inMemoryAuthentication()
		.withUser("abhay")
		.password("abhay")
		.roles("USER")
		.and()
		.withUser("foo")
		.password("foo")
		.roles("ADMIN");
		}
	
	//we override this method for Authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//order of matcher should always be from most restrictive to least restrictive
		http.authorizeRequests()
		.antMatchers("/admin").hasRole("ADMIN")
		.antMatchers("/user").hasAnyRole("USER","ADMIN")
		.antMatchers("/home").permitAll()   //permits acccess to every role
		.and().formLogin();
	}
	
	@Bean
	public PasswordEncoder getencoder() {
		 // Bean of PasswordEncoder is necessary as we cannot give passowrd as String text
		//but here we are returning No pass encdoer bean which actually does no encoding it return password as string only
		//ideally we should not use noOp encoder 
		return NoOpPasswordEncoder.getInstance();
	}
}
