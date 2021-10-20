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

//	@GetMapping(value = "/admin")
//	public String managerProductPage(Model model, HttpServletRequest request) {
//		return "1";
//	}


	@GetMapping(value = "/admin/quan-ly-tai-khoan")
	public String managerEmployeePage(Model model, HttpServletRequest request) {
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

	@GetMapping(value = "/admin/tao-tai-khoan")
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


	@PostMapping(value = "/admin/luu-tai-khoan")
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
				return "redirect:/admin/quan-ly-tai-khoan";
			}
			else if(userRegisterResponseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)){
				return "redirect:/admin/quan-ly-tai-khoan";
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return "redirect:/login";
	}


	@GetMapping(value = {"/admin/chinh-sua-tai-khoan/{username}"})
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
	@GetMapping(value = {"/admin/chinh-sua-tai-khoan/sua-mat-khau/{username}"})
	public String editPassword(@PathVariable String username ,Model model, HttpServletRequest request) {
		try{
		Employee employee = new Employee();
		employee.setUsername(username);
		model.addAttribute("employee", employee);

//	model.addAttribute("username", username);
		return "admin/doi-mat-khau";


	} catch (Exception e) {
		System.out.println("fail");
	}
		return "redirect:/login";
	}
	@PostMapping(value = "/admin/saveedit")
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

				return "redirect:/admin/quan-ly-tai-khoan";
			}
			else if(userRegisterResponseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)){
				return "redirect:/admin/quan-ly-tai-khoan";
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return "redirect:/login";
	}
	@PostMapping(value = "/admin/savepassword")
	public String saveNewPass(@ModelAttribute("employee") Employee employee, HttpServletRequest request){
		try {
			HttpHeaders headers = new HttpHeaders();
			HttpSession session = request.getSession();
			String token = (String)session.getAttribute("Token");
			headers.set("Authorization",token);
			HttpEntity<Employee> requestEntity = new HttpEntity<Employee>( employee,headers);
			ResponseEntity<Employee> userRegisterResponseEntity = restTemplate.exchange(url+"/api/employee/editpassword/"+employee.getUsername(),
					HttpMethod.PUT, requestEntity, Employee.class);
			if(userRegisterResponseEntity.getStatusCode().equals(HttpStatus.OK)){
				return "redirect:/admin/quan-ly-tai-khoan";
			}
			else if(userRegisterResponseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)){
				return "redirect:/admin/quan-ly-tai-khoan";
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


	@GetMapping("/admin/403")
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




}
