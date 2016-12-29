package com.prestamosprima.codingtask.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.prestamosprima.codingtask.domain.Customer;
import com.prestamosprima.codingtask.service.CustomerDAO;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomerDAO customerDAO;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**").csrf().disable();

		http.authorizeRequests().antMatchers("/register").permitAll().anyRequest().authenticated();

		http.httpBasic();
	}

	// TODO Apparently not needed
	// @Autowired
	// public void configureGlobal(AuthenticationManagerBuilder auth) throws
	// Exception {
	// auth.userDetailsService(userDetailsService());
	// }

	@Override
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				Customer customer = customerDAO.findByName(username);
				if (customer != null) {
					return new User(customer.getName(), customer.getPassword(), true, true, true, true,
							AuthorityUtils.createAuthorityList("USER"));
				} else {
					throw new UsernameNotFoundException("could not find customer '" + username + "'");
				}
			}

		};
	}
}