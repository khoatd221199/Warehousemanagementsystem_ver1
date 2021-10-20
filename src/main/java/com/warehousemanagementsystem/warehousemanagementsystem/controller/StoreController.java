package com.warehousemanagementsystem.warehousemanagementsystem.controller;


import com.warehousemanagementsystem.warehousemanagementsystem.dto.Store;
import com.warehousemanagementsystem.warehousemanagementsystem.dto.Warehouse;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.warehousemanagementsystem.warehousemanagementsystem.service.RestTemplateExchange.restTemplate;
import static com.warehousemanagementsystem.warehousemanagementsystem.service.RestTemplateExchange.url;

@Controller
public class StoreController {



    @GetMapping(value = "/admin/quan-ly-cua-hang")
    public String managerProductPage(Model model, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpSession session = request.getSession();
        String token = (String) session.getAttribute("Token");
        headers.set("Authorization", token);
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Store[]> store = restTemplate.exchange(url+ "/api/store/list",
                HttpMethod.GET,requestEntity,Store[].class);
        if (store.getStatusCode().equals(HttpStatus.OK)) {
            model.addAttribute("stores",store.getBody());
            System.out.println("okne");
            return "/admin/quan-ly-cua-hang";
        }
        return "redirect:/login";
    }

    @GetMapping(value = "/admin/tao-cua-hang")
    public String createStore(Model model){
        Store store = new Store();
        model.addAttribute("store", store);
        return "admin/tao-cua-hang";
    }

    @GetMapping(value = {"/admin/quan-ly-cua-hang/chi-tiet-cua-hang/{storeid}"})
    public String listOrderDetail(@PathVariable String storeid , Model model, HttpServletRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpSession session = request.getSession();
            String token = (String) session.getAttribute("Token");
            headers.set("Authorization", token);
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
            ResponseEntity<Store> storeDetail = restTemplate.exchange(url+"/api/store/detail/"+storeid,
                    HttpMethod.GET,requestEntity,Store.class);
            ResponseEntity<Warehouse[]> warehouse = restTemplate.exchange(url+"/api/warehouse/listwithstoreid/"+storeid,
                    HttpMethod.GET,requestEntity,Warehouse[].class);
            if (storeDetail.getStatusCode().equals(HttpStatus.OK) && warehouse.getStatusCode().equals(HttpStatus.OK)) {
//				EmployeeRequest employee = ordersDetail.getBody();
//				model.addAttribute("employee", employee);
                model.addAttribute("store",storeDetail.getBody());
                model.addAttribute("warehouse",warehouse.getBody());
                //model.addAttribute("dateDebut",storeDetail.getBody().getBirthday());
               // System.out.println(ordersDetail.getBody().getBirthday());
                return "admin/chi-tiet-cua-hang";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fail";
    }
    @GetMapping(value = {"/admin/quan-ly-cua-hang/chinh-sua-cua-hang/{storeid}"})
    public String editStore(@PathVariable String storeid , Model model, HttpServletRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpSession session = request.getSession();
            String token = (String) session.getAttribute("Token");
            headers.set("Authorization", token);
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
            ResponseEntity<Store> storeDetail = restTemplate.exchange(url+"/api/store/detail/"+storeid,
                    HttpMethod.GET,requestEntity,Store.class);
            if (storeDetail.getStatusCode().equals(HttpStatus.OK)) {
//				EmployeeRequest employee = ordersDetail.getBody();
//				model.addAttribute("employee", employee);
                model.addAttribute("store",storeDetail.getBody());
                //model.addAttribute("dateDebut",storeDetail.getBody().getBirthday());
                // System.out.println(ordersDetail.getBody().getBirthday());
                return "admin/chinh-sua-cua-hang";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fail";
    }
    @PostMapping(value = "/admin/saveStore")
    public String saveUser(@ModelAttribute("store") Store store, HttpServletRequest request){
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpSession session = request.getSession();
            String token = (String)session.getAttribute("Token");
            headers.set("Authorization",token);
            HttpEntity<Store> requestEntity = new HttpEntity<Store>(store,headers);
            store.setUsername((String) session.getAttribute("username"));
            ResponseEntity<Store> userRegisterResponseEntity = restTemplate.exchange(url+"/api/store/create",
                    HttpMethod.POST, requestEntity, Store.class);

            if(userRegisterResponseEntity.getStatusCode().equals(HttpStatus.OK)){
                return "redirect:/admin/quan-ly-cua-hang";
            }
            else if(userRegisterResponseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)){
                return "redirect:/admin/quan-ly-cua-hang";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/login";
    }

    @PostMapping(value = "/admin/saveeditstore")
    public String eidttUser(@ModelAttribute("store") Store store, HttpServletRequest request){
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpSession session = request.getSession();
            String token = (String)session.getAttribute("Token");
            headers.set("Authorization",token);
            HttpEntity<Store> requestEntity = new HttpEntity<Store>( store,headers);
            ResponseEntity<Store> userRegisterResponseEntity = restTemplate.exchange(url+"/api/store/edit/"+store.getStoreid(),
                    HttpMethod.PUT, requestEntity, Store.class);
            if(userRegisterResponseEntity.getStatusCode().equals(HttpStatus.OK)){
                return "redirect:/admin/quan-ly-cua-hang";
            }
            else if(userRegisterResponseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)){
                return "redirect:/admin/quan-ly-cua-hang";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/login";
    }
}
