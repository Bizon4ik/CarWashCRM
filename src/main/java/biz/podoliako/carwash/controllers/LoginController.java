package biz.podoliako.carwash.controllers;

import biz.podoliako.carwash.services.AuthorizationService;
import biz.podoliako.carwash.models.entity.Role;
import biz.podoliako.carwash.models.pojo.UserExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class LoginController {

    @Autowired
    AuthorizationService authorizationService;

    @RequestMapping(value ={"/login", "/"}, method = RequestMethod.GET)
    public String login() {
        return "/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginValidation(@RequestParam("login") String login,
                                  @RequestParam("password") String password,
                                  HttpSession session,
                                  Model model) throws SQLException {
        String result = "";

        UserExt userExt = authorizationService.getUser(login, password);

        if (userExt == null) {
            model.addAttribute("incorrectLogin", "Неверное имя пользователя и/или пароль");
            result = "/login";

        }else if(userExt.getRole() == Role.owner){
            authorizationService.setUserSession(userExt, session);
            result = "redirect:/owner/main";
        }else if(userExt.getRole() == Role.administrator) {
            authorizationService.setUserSession(userExt, session);

            if (userExt.getCarWashPermissionSet().size() == 1) {
                authorizationService.setChoosenCarWashSession(userExt.getCarWashPermissionSet().iterator().next(), session);
            }else {
                System.out.println("Administrator has permission for several CarWashes");
            }
            result = "redirect:/admin/main";
        }

        return  result;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session){

        session.removeAttribute("CurrentCarWashUser");
        session.removeAttribute("ChoosenCarWashId");

        return "redirect:/login";
    }

}
