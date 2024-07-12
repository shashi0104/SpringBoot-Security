package com.shashi.config;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig { 
	@Autowired
	DataSource datasource;
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) ->
				requests.requestMatchers("/h2-console/**").permitAll()
				.anyRequest().authenticated()); 
		http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
 		http.headers(headers->headers
										.frameOptions(options->options.sameOrigin()));
		http.csrf(csrf->csrf.disable());
		return http.build();
	}  
	@Bean
	UserDetailsService userDetailsService() {
		UserDetails user1= User.withUsername("user1")
//												.password("{noop}password1")
												.password(encoder().encode("password1"))
												.roles("USER")
												.build();
												
		UserDetails admin= User.withUsername("admin")
//												.password("{noop}adminPass")
												.password(encoder().encode("adminPass"))
												.roles("ADMIN")
												.build();
		
		JdbcUserDetailsManager manager=new JdbcUserDetailsManager(datasource); 
		manager.createUser(user1);
		manager.createUser(admin);
		return manager;
//		return new InMemoryUserDetailsManager(user1,admin);
	}
	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
}

