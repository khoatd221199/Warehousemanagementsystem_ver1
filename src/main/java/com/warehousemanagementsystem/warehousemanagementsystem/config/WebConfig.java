package com.warehousemanagementsystem.warehousemanagementsystem.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;


@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
	@Autowired
	private AccountAuthenticationSuccessHandler successHandler;

	@Autowired
	private UserDetailsService jwtUserDetailsService;


//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).
//			withUser("1").password("$2a$10$oG3sVfv6MWsU2XZOPIZMkup/JbOUHoTNC1XMbJFLFSzET1Ya41Qzu").authorities("ROLE_ADMIN").and().withUser("2").password("$2a$10$oG3sVfv6MWsU2XZOPIZMkup/JbOUHoTNC1XMbJFLFSzET1Ya41Qzu").authorities("ROLE_USER");
////		auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()).withUser("kai").password("123456").roles("USER");
//	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());

	}
	
	@Autowired
	private AccountAuthenticationFailureHandler customAuthenticationFailureHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {


		http.authorizeRequests().antMatchers("/admin/**","/403").hasAuthority("ROLE_ADMIN").antMatchers("/user/**").hasAuthority("ROLE_USER");
		// Cấu hình concurrent session
		http.sessionManagement()
		.invalidSessionUrl("/login?message=timeout")

		.maximumSessions(1).expiredUrl("/login?message=max_session");
		//cái này cho ai cà chớn
//		.maximumSessions(1).expiredUrl("/login?message=max_session").maxSessionsPreventsLogin(true);

		// Cấu hình cho Login Form.
		http.authorizeRequests().and().formLogin()//
				.loginProcessingUrl("/j_spring_security_login")//
				.loginPage("/login")//
//				.defaultSuccessUrl("/user")//
				.successHandler(successHandler)
				.failureHandler(customAuthenticationFailureHandler)
				.usernameParameter("username")//
				.passwordParameter("password");
				// Cấu hình cho Logout Page.

//		http.sessionManagement().maximumSessions(1).expiredUrl("/login?expired");

	}
}
