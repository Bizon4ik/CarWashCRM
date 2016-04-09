package biz.podoliako.carwash.controllers.owner;

import biz.podoliako.carwash.models.entity.Category;
import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.services.CategoryService;
import biz.podoliako.carwash.services.exeption.GlobalRuntimeExeption;
import biz.podoliako.carwash.services.utils.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/owner/category")
@SessionAttributes("CurrentCarWashUser")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addGet(Model model){
        Category category = new Category();
        model.addAttribute("category", category);
        return "owner/category/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPost(@Valid @ModelAttribute("category") Category category,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          HttpSession session) {
        try {
            if (bindingResult.hasErrors()){
                System.out.println("in error");
                return "owner/category/add";
            }
            category.setCreatedBy((User) session.getAttribute("CurrentCarWashUser"));
            category = categoryService.persist(category);

            redirectAttributes.addFlashAttribute("globalMsg", "Категория \"" + category.getName() + "\" создана");
            return "redirect:/owner/category/all";
        }catch (Exception e){
            System.out.println("message = " + e.getMessage());
            throw new GlobalRuntimeExeption(GeneralUtils.stackTraceToString(e));
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allCategoryGet (Model model ) {
        try {
            List<Category> categoryList = categoryService.findAll();
            model.addAttribute("categoryList", categoryList);
            return "owner/category/all";
        } catch (Exception e) {
            System.out.println("message = " + e.getMessage());
            throw new GlobalRuntimeExeption(GeneralUtils.stackTraceToString(e));
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteGet(@PathVariable("id") String id,
                            RedirectAttributes redirectAttributes) {
        try {
            Category category = categoryService.validateId(id);
            categoryService.markDeleted(category);
            redirectAttributes.addFlashAttribute("globalMsg", "Категория успешно удалена");
            return "redirect:/owner/category/all";
        }catch (NumberFormatException e ){
            redirectAttributes.addFlashAttribute("globalError", "Не коректный id категории для удаления");
            return "redirect:/owner/category/all";
        }catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("globalError", "Вы пытаетесь удалить не существующую категорию");
            return "redirect:/owner/category/all";
        }catch (Exception e){
            System.out.println("message = " +  e.getMessage());
            throw new GlobalRuntimeExeption(GeneralUtils.stackTraceToString(e));
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateClient(@PathVariable("id") String id,
                               RedirectAttributes redirectAttributes,
                               Model model){
        try {
            Category category = categoryService.validateId(id);

            model.addAttribute("category", category);
            model.addAttribute("title", "update");
            return "owner/category/add";

        }catch (IllegalArgumentException e ){
            redirectAttributes.addFlashAttribute("globalError", "Не коректный id категории для редактирования");
            return "redirect:/owner/category/all";
        }catch (Exception e){
            System.out.println("message = " +  e.getMessage());
            throw new GlobalRuntimeExeption(GeneralUtils.stackTraceToString(e));
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String updateClient_Post(@Valid @ModelAttribute("category") Category category,
                                    BindingResult bindingResult,
                                    @PathVariable("id") String id,
                                    RedirectAttributes redirectAttributes){
        try {
            if (bindingResult.hasErrors()){
                 return "owner/category/add";
            }
            Category categoryOld = categoryService.validateId(id);
            category.setId(categoryOld.getId());
            category = categoryService.update(category);

            redirectAttributes.addFlashAttribute("globalMsg", "Категория \"" + category.getName() + "\" была оновленная успешно");

            return "redirect:/owner/category/all";
        }catch (IllegalArgumentException e ){
            redirectAttributes.addFlashAttribute("globalError", "Не коректный id категории для редактирования");
            return "redirect:/owner/category/all";
        }catch (Exception e){
            System.out.println("message = " +  e.getMessage());
            throw new GlobalRuntimeExeption(GeneralUtils.stackTraceToString(e));
        }
    }


}
