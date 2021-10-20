package com.warehousemanagementsystem.warehousemanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @GetMapping(value="/logout")
    public String logoutPage (HttpServletRequest request) {
        HttpSession session =request.getSession() ;
        session.invalidate();
        return "redirect:/";
    }
    @RequestMapping(value = { "/login", "/" })
    public String login(@RequestParam(required=false) String message, Model model, HttpServletRequest request) {
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

}
