package biz.podoliako.carwash.controllers.owner;


import biz.podoliako.carwash.models.entity.CarBrand;
import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.models.pojo.UserExt;
import biz.podoliako.carwash.services.CarBrandService;
import biz.podoliako.carwash.services.exeption.GlobalRuntimeExeption;
import biz.podoliako.carwash.services.utils.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("owner/carbrand")
public class CarBrandController {

    @Autowired
    CarBrandService carBrandService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addCarBrandGet(Model model){
        try {
            model.addAttribute("carBrand", new CarBrand());
            return  "owner/carbrand/add";
        }catch (Exception e){
            System.out.println("message = " + e.getMessage());
            throw new GlobalRuntimeExeption(GeneralUtils.stackTraceToString(e));
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addCarBrandPost(@Valid @ModelAttribute("carBrand") CarBrand carBrand,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  HttpSession session){
        try {
            if (bindingResult.hasErrors()){
                return  "owner/carbrand/add";
            }
            carBrand.setCreatedBy((User) session.getAttribute("CurrentCarWashUser"));
            carBrandService.addCarBrad(carBrand);
            redirectAttributes.addFlashAttribute("globalMsg", "АвтоБренд "+ carBrand.getName().trim().toUpperCase() +" добавлен");
            return "redirect:/owner/carbrand/all";
        } catch (Exception e){
            System.out.println("message = " + e.getMessage());
            throw new GlobalRuntimeExeption(GeneralUtils.stackTraceToString(e));
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allCarBrandsPost(HttpSession session,
                                   Model model) {
        UserExt userExt = (UserExt) session.getAttribute("CurrentCarWashUser");

        try {
            List<CarBrand> carBrandList = carBrandService.getAllCarBrands(userExt.getOwnerId());
            model.addAttribute("carBrandList", carBrandList);
        } catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
        }


        return "owner/carbrand/allCarBrands";
    }

}
