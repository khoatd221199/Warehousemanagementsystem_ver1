package com.warehousemanagementsystem.warehousemanagementsystem.controller;


import com.warehousemanagementsystem.warehousemanagementsystem.dto.Employee;

import com.warehousemanagementsystem.warehousemanagementsystem.dto.Role;
import com.warehousemanagementsystem.warehousemanagementsystem.dto.Warehouse;
import org.springframework.http.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.warehousemanagementsystem.warehousemanagementsystem.service.RestTemplateExchange.restTemplate;
import static com.warehousemanagementsystem.warehousemanagementsystem.service.RestTemplateExchange.url;


@Controller
public class EmployeeController {


//	@GetMapping(value = { "/login", "/" })
//	public String showLoginForm() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//			return "login";
//		}
//		return "redirect:/admin";
//	}
	@GetMapping(value="/logout")
	public String logoutPage (HttpServletRequest request) {
		HttpSession session =request.getSession() ;
		session.invalidate();
		return "redirect:/";
	}
	@RequestMapping(value = { "/login", "/" })
	public String login(@RequestParam(required=false) String message,Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();

		String b = (String) session.getAttribute("textsession");

		System.out.println("test session" + b);
		if (message != null && !message.isEmpty()) {
			if (message.equals("timeout")) {
				model.addAttribute("message", "Time out");
			}
			if (message.equals("max_session")) {
				model.addAttribute("message", "This accout has been login from another device!");
			}
			if (message.equals("logout")) {
				model.addAttribute("message", "Logout!");
			}
			if (message.equals("error")) {
				model.addAttribute("message", "Login Failed!");
			}
		}
		return "login/login-page";
	}

//	@GetMapping(value = "/admin")
//	public String managerProductPage(Model model, HttpServletRequest request) {
//		return "1";
//	}

	@GetMapping(value = "/admin")
	public String managerProductPage(Model model, HttpServletRequest request) {
		try {
            Employee productDTO = new Employee();
			HttpHeaders headers = new HttpHeaders();
			HttpSession session = request.getSession();
			String token = (String) session.getAttribute("Token");
			String a = "tesst Session";
			session.setAttribute("textsession", a);
			System.out.println("tesst token" + token);
			headers.set("Authorization", token);
			HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
			ResponseEntity<Employee[]> employee = restTemplate.exchange(url+ "/api/employee/list",
					HttpMethod.GET,requestEntity,Employee[].class);
			if(token == null ) {
				return "redirect:/logout";
			}
				if (employee.getStatusCode().equals(HttpStatus.OK)) {


				model.addAttribute("employees",employee.getBody());
				System.out.println("okne");
				return "admin/quan-ly-tai-khoan";

			}

		} catch (Exception e) {
			System.out.println("fail");
		}
		return "redirect:/login";
	}

	@GetMapping(value = "/tao-tai-khoan")
	public String createAccount(Model model, HttpServletRequest request) {
		try {
			Employee employee = new Employee();

			model.addAttribute("employee", employee);
			HttpHeaders headers = new HttpHeaders();
			HttpSession session = request.getSession();
			String token = (String) session.getAttribute("Token");



			headers.set("Authorization", token);
			HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
			ResponseEntity<Role[]> role = restTemplate.exchange(url+ "/api/role/list",
					HttpMethod.GET,requestEntity,Role[].class);
			ResponseEntity<Warehouse[]> warehouse = restTemplate.exchange(url+ "/api/warehouse/list",
					HttpMethod.GET,requestEntity,Warehouse[].class);
			if(token == null ) {
				return "redirect:/logout";
			}
			if (role.getStatusCode().equals(HttpStatus.OK) && warehouse.getStatusCode().equals(HttpStatus.OK)) {


				model.addAttribute("Role",role.getBody());
				model.addAttribute("Warehouse",warehouse.getBody());

				return "admin/tao-tai-khoan";

			}

		} catch (Exception e) {
			System.out.println("fail");
		}
		return "redirect:/login";
	}


	@PostMapping(value = "/luu-nhan-vien")
	public String saveUser(@ModelAttribute("employee") Employee employee, HttpServletRequest request){
		try {
			HttpHeaders headers = new HttpHeaders();
			HttpSession session = request.getSession();
			String token = (String)session.getAttribute("Token");
			headers.set("Authorization",token);
			HttpEntity<Employee> requestEntity = new HttpEntity<Employee>( employee,headers);
			ResponseEntity<Employee> userRegisterResponseEntity = restTemplate.exchange(url+"/api/employee/create",
					HttpMethod.POST, requestEntity, Employee.class);
			if(userRegisterResponseEntity.getStatusCode().equals(HttpStatus.OK)){
				return "redirect:/admin";
			}
			else if(userRegisterResponseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)){
				return "redirect:/admin";
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return "redirect:/login";
	}


	@GetMapping(value = {"/chinh-sua-tai-khoan/{username}"})
	public String listOrderDetail(@PathVariable String username ,Model model, HttpServletRequest request) {
		try {
			HttpHeaders headers = new HttpHeaders();
			HttpSession session = request.getSession();
			String token = (String) session.getAttribute("Token");
			headers.set("Authorization", token);
			HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
			ResponseEntity<Role[]> role = restTemplate.exchange(url+ "/api/role/list",
					HttpMethod.GET,requestEntity,Role[].class);
			ResponseEntity<Warehouse[]> warehouse = restTemplate.exchange(url+ "/api/warehouse/list",
					HttpMethod.GET,requestEntity,Warehouse[].class);
			ResponseEntity<Employee> ordersDetail = restTemplate.exchange(url+"/api/employee/detail/"+username,
					HttpMethod.GET,requestEntity,Employee.class);
			if (ordersDetail.getStatusCode().equals(HttpStatus.OK) && role.getStatusCode().equals(HttpStatus.OK) && warehouse.getStatusCode().equals(HttpStatus.OK)) {
//				EmployeeRequest employee = ordersDetail.getBody();
//				model.addAttribute("employee", employee);

				model.addAttribute("Role",role.getBody());
				model.addAttribute("Warehouse",warehouse.getBody());
				model.addAttribute("emp",ordersDetail.getBody());
				model.addAttribute("dateDebut",ordersDetail.getBody().getBirthday());
				System.out.println(ordersDetail.getBody().getBirthday());
				return "admin/chinh-sua-tai-khoan";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/login";
	}

	@PostMapping(value = "/saveedit")
	public String eidttUser(@ModelAttribute("employee") Employee employee, HttpServletRequest request){
		try {
			HttpHeaders headers = new HttpHeaders();
			HttpSession session = request.getSession();
			String token = (String)session.getAttribute("Token");
			headers.set("Authorization",token);
			HttpEntity<Employee> requestEntity = new HttpEntity<Employee>( employee,headers);
			ResponseEntity<Employee> userRegisterResponseEntity = restTemplate.exchange(url+"/api/employee/edit/"+employee.getUsername(),
					HttpMethod.PUT, requestEntity, Employee.class);
			if(userRegisterResponseEntity.getStatusCode().equals(HttpStatus.OK)){
				return "redirect:/admin";
			}
			else if(userRegisterResponseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)){
				return "redirect:/admin";
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return "redirect:/login";
	}
//	@GetMapping(value = { "/login", "/" })
//	public String showLoginForm() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//			return "login";
//		}
//		return "redirect:/admin";
//	}


	@GetMapping("/403")
	public String user(Model model, HttpServletRequest request) {
		 HttpSession session =request.getSession();
		 String a = (String) session.getAttribute("Token");
		 model.addAttribute("token",session.getAttribute("phuc"));
		if(a == null ) {
			return "redirect:/logout";
		}
		System.out.println(a);
		return "redirect:/logout";
	}

//
//
//	@RequestMapping("/admin")
//	public String admin1(Model model, HttpServletRequest request) {
//		HttpSession session =request.getSession();
//		String abc = (String) session.getAttribute("abc");
//		System.out.println(abc);
//		String a = (String) session.getAttribute("Token");
//		System.out.println(a);
//		return "user";
//	}


//	@GetMapping(value = "/admin/listne")
//	public String managerProductPage(Model model, HttpServletRequest request) {
//		try {
//
//			Account1 productDTO = new Account1();
//			HttpHeaders headers = new HttpHeaders();
//			HttpSession session = request.getSession();
//			String token = (String) session.getAttribute("Token");
//			String abc = (String) session.getAttribute("abc");
//			System.out.println(abc);
//			headers.set("Authorization", token);
//			HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
//			ResponseEntity<Account1[]> products = restTemplate.exchange(url+"/apine/listuser",
//					HttpMethod.GET,requestEntity,Account1[].class);
//			if (products.getStatusCode().equals(HttpStatus.OK)) {
//				System.out.println(products.getBody());
//
//			}
//		} catch (Exception e) {
//			return "redirect:/403";
//		}
//		return "redirect:/";
//	}

}
