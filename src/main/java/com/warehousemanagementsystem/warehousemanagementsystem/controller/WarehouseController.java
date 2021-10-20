package com.warehousemanagementsystem.warehousemanagementsystem.controller;

import com.warehousemanagementsystem.warehousemanagementsystem.dto.Employee;
import com.warehousemanagementsystem.warehousemanagementsystem.dto.Role;
import com.warehousemanagementsystem.warehousemanagementsystem.dto.Store;
import com.warehousemanagementsystem.warehousemanagementsystem.dto.Warehouse;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.warehousemanagementsystem.warehousemanagementsystem.service.RestTemplateExchange.restTemplate;
import static com.warehousemanagementsystem.warehousemanagementsystem.service.RestTemplateExchange.url;

@Controller

public class WarehouseController {
    @GetMapping(value = "/admin/quan-ly-kho-hang")
    public String managerWarehousePage(Model model, HttpServletRequest request) {
        try {

            HttpHeaders headers = new HttpHeaders();
            HttpSession session = request.getSession();
            String token = (String) session.getAttribute("Token");
            headers.set("Authorization", token);
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
            ResponseEntity<Warehouse[]> warehouse = restTemplate.exchange(url+ "/api/warehouse/list",
                    HttpMethod.GET,requestEntity,Warehouse[].class);
            if(token == null ) {
                return "redirect:/logout";
            }
            if (warehouse.getStatusCode().equals(HttpStatus.OK)) {


                model.addAttribute("warehouse",warehouse.getBody());
                System.out.println("okne");
                return "admin/quan-ly-kho-hang";
            }
        } catch (Exception e) {
            System.out.println("fail");
        }
        return "redirect:/login";
    }

//    @GetMapping(value = "/tao-kho-hang")
//    public String createStore(Model model){
//        Warehouse warehouse = new Warehouse();
//        model.addAttribute("warehouse", warehouse);
//        return "admin/tao-kho-hang";
//    }

    @GetMapping(value = "/admin/tao-kho-hang")
    public String createStore(Model model, HttpServletRequest request) {
        try {
            Warehouse warehouse = new Warehouse();
            model.addAttribute("warehouse", warehouse);
            HttpHeaders headers = new HttpHeaders();
            HttpSession session = request.getSession();
            String token = (String) session.getAttribute("Token");
            headers.set("Authorization", token);
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
            ResponseEntity<Store[]> store = restTemplate.exchange(url+ "/api/store/list",
                    HttpMethod.GET,requestEntity,Store[].class);
            if(token == null ) {
                return "redirect:/logout";
            }
            if (store.getStatusCode().equals(HttpStatus.OK)) {
                model.addAttribute("Store",store.getBody());
                return "admin/tao-kho-hang";

            }

        } catch (Exception e) {
            System.out.println("fail");
        }
        return "redirect:/login";
    }

    @PostMapping(value = "/admin/luu-kho-hang")
    public String saveUser(@ModelAttribute("warehouse") Warehouse warehouse, Model model, HttpServletRequest request){

            HttpHeaders headers = new HttpHeaders();
            HttpSession session = request.getSession();
            String token = (String)session.getAttribute("Token");
            headers.set("Authorization",token);
            HttpEntity<Warehouse> requestEntity = new HttpEntity<Warehouse>(warehouse,headers);
            ResponseEntity<Warehouse> userRegisterResponseEntity = restTemplate.exchange(url+"/api/warehouse/create",
                    HttpMethod.POST, requestEntity, Warehouse.class);

            if(userRegisterResponseEntity.getStatusCode().equals(HttpStatus.OK)){
                return "redirect:/admin/quan-ly-kho-hang";
            }
            else if(userRegisterResponseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)){
                return "redirect:/admin/quan-ly-kho-hang";
            } else if(userRegisterResponseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                model.addAttribute("statusError","Mã kho đã có, vui lòng nhập mã khác!");
                return "admin/tao-kho-hang";
            }else {
                return "redirect:/login";
            }


    }

    @GetMapping(value = {"/admin/chi-tiet-kho-hang/{warehouseid}"})
    public String listOrderDetail(@PathVariable String warehouseid , Model model, HttpServletRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpSession session = request.getSession();
            String token = (String) session.getAttribute("Token");
            headers.set("Authorization", token);
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
            ResponseEntity<Warehouse> warehouseDetail = restTemplate.exchange(url+"/api/warehouse/detail/"+warehouseid,
                    HttpMethod.GET,requestEntity,Warehouse.class);
            ResponseEntity<Employee[]> employee = restTemplate.exchange(url+"/api/employee/listwithwarehouseid/"+warehouseid,
                    HttpMethod.GET,requestEntity,Employee[].class);
            if (warehouseDetail.getStatusCode().equals(HttpStatus.OK)) {
//				EmployeeRequest employee = ordersDetail.getBody();
//				model.addAttribute("employee", employee);
                model.addAttribute("warehouse",warehouseDetail.getBody());
                model.addAttribute("employee",employee.getBody());
                //model.addAttribute("dateDebut",storeDetail.getBody().getBirthday());
                // System.out.println(ordersDetail.getBody().getBirthday());
                return "admin/chi-tiet-kho-hang";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/login";
    }
    @GetMapping(value = {"/admin/quan-ly-kho-hang/chinh-sua-kho-hang/{warehouseid}"})
    public String editWarehouse(@PathVariable String warehouseid , Model model, HttpServletRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpSession session = request.getSession();
            String token = (String) session.getAttribute("Token");
            headers.set("Authorization", token);
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
            ResponseEntity<Warehouse> warehouseDetail = restTemplate.exchange(url+"/api/warehouse/detail/"+warehouseid,
                    HttpMethod.GET,requestEntity,Warehouse.class);
            ResponseEntity<Store[]> store = restTemplate.exchange(url+ "/api/store/list",
                    HttpMethod.GET,requestEntity,Store[].class);
            if (warehouseDetail.getStatusCode().equals(HttpStatus.OK) && store.getStatusCode().equals(HttpStatus.OK)) {
//				EmployeeRequest employee = ordersDetail.getBody();
//				model.addAttribute("employee", employee);
                model.addAttribute("warehouse",warehouseDetail.getBody());
                model.addAttribute("Store",store.getBody());
                //model.addAttribute("dateDebut",storeDetail.getBody().getBirthday());
                // System.out.println(ordersDetail.getBody().getBirthday());
                return "admin/chinh-sua-kho-hang";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/login";
    }
    @PostMapping(value = "admin/quan-ly-kho-hang/saveedit")
    public String eidtWarehouse(@ModelAttribute("warehouse") Warehouse warehouse, HttpServletRequest request){
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpSession session = request.getSession();
            String token = (String)session.getAttribute("Token");
            headers.set("Authorization",token);
            HttpEntity<Warehouse> requestEntity = new HttpEntity<Warehouse>( warehouse,headers);
            ResponseEntity<Warehouse> userRegisterResponseEntity = restTemplate.exchange(url+"/api/warehouse/edit/"+warehouse.getWarehouseid(),
                    HttpMethod.PUT, requestEntity, Warehouse.class);
            if(userRegisterResponseEntity.getStatusCode().equals(HttpStatus.OK)){
                return "redirect:/admin/quan-ly-kho-hang";
            }
            else if(userRegisterResponseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)){
                return "redirect:/admin/quan-ly-kho-hang";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/login";
    }

}
