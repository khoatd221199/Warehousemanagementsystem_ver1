package com.warehousemanagementsystem.warehousemanagementsystem.service;
import com.warehousemanagementsystem.warehousemanagementsystem.dto.Account;
import com.warehousemanagementsystem.warehousemanagementsystem.dto.Token;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.warehousemanagementsystem.warehousemanagementsystem.service.RestTemplateExchange.restTemplate;
import static com.warehousemanagementsystem.warehousemanagementsystem.service.RestTemplateExchange.url;


@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	ObjectFactory<HttpSession> httpSessionFactory;

	@Autowired
	private PasswordEncoder bcryptEncoder;





	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Hoàng Phúc
		HttpEntity<Account> requestAcc= new HttpEntity<Account>(new Account(request.getParameter("username"),request.getParameter("password")));

		ResponseEntity<Token> authenticationResponse = restTemplate.exchange(url+"/api/account/authenticate",
				HttpMethod.POST, requestAcc, Token.class);
		if (authenticationResponse.getStatusCode().equals(HttpStatus.OK)) {
			String role = authenticationResponse.getBody().getRoleid();
			String token = "Bearer " + authenticationResponse.getBody().getToken();

//			HttpServletRequest request=null;
			HttpSession session = httpSessionFactory.getObject();
			session.setAttribute("Token", token);
			System.out.printf(token);

			String a = (String) session.getAttribute("Token");
			System.out.println("A"+a);
		}
		HttpHeaders headers = new HttpHeaders();
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("Token");
		headers.set("Authorization", token);
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<Account> u= restTemplate.exchange(url +"/api/employee/checklogin/"+username,
				HttpMethod.GET,requestEntity, Account.class);
		System.out.println(u.getBody().getRolename());
		session.setAttribute("Account",u.getBody());
		if (u == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		UserDetails user1 = User.withUsername(u.getBody().getUsername()).password(u.getBody().getPassword()).authorities(u.getBody().getRoleid()).build();
		return user1;

	}




}