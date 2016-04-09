package biz.podoliako.carwash.controllers.owner;

import biz.podoliako.carwash.models.entity.Client;
import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.services.ClientService;
import biz.podoliako.carwash.services.exeption.GlobalRuntimeExeption;
import biz.podoliako.carwash.services.impl.ClientServiceImpl;
import biz.podoliako.carwash.services.utils.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/owner/clients/")
public class ClientController {

    @Autowired
    ClientService clientService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addClientGet(Model model){
        Client client = new Client();

        model.addAttribute("client", client);
        return "owner/clients/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addClientPost (@Valid @ModelAttribute("client") Client client,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session,
                                 Model model){

        try {
            if (bindingResult.hasErrors()){
                return "owner/clients/add";
            }

            client = setIntoClientDateOfCretationAndCreatedBy(client,
                                                (User) session.getAttribute("CurrentCarWashUser"));

            Long id = clientService.saveClient(client);
            redirectAttributes.addFlashAttribute("globalMsg", "Клиент " + client.getName() + " создан успешно (#"+ id + ")");
            return "redirect:/owner/clients/all";

        }catch (Exception e){
            System.out.println("message = " +  e.getMessage());
            throw new GlobalRuntimeExeption(GeneralUtils.stackTraceToString(e));
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allClientsGet(Model model){
        List<Client> clientList = clientService.getAllClientsOrdered();
        model.addAttribute("clientList", clientList);
        return "owner/clients/all";

    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteClient(@PathVariable("id") String id,
                               RedirectAttributes redirectAttributes,
                               Model model){
        try {
            Client client = clientService.validateId(id);
            clientService.remove(client);
            redirectAttributes.addFlashAttribute("globalMsg", "Клиент успешно удален");
            return "redirect:/owner/clients/all";
        }catch (NumberFormatException e ){
            model.addAttribute("globalError", "Не коректный id клиента для удаления");
            List<Client> clientList = clientService.getAllClientsOrdered();
            model.addAttribute("clientList", clientList);
            return "owner/clients/all";
        }catch (IllegalArgumentException e){
            model.addAttribute("globalError", "Вы пытаетесь удалить не существующего клиента");
            List<Client> clientList = clientService.getAllClientsOrdered();
            model.addAttribute("clientList", clientList);
            return "owner/clients/all";
        }catch (Exception e){
            System.out.println("message = " +  e.getMessage());
            throw new GlobalRuntimeExeption(GeneralUtils.stackTraceToString(e));
        }

    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateClient(@PathVariable("id") String id,
                               Model model){
        try {
            Client client = clientService.validateId(id);

            model.addAttribute("client", client);
            model.addAttribute("title", "update");
            return "owner/clients/add";

        }catch (NumberFormatException e ){
            model.addAttribute("globalError", "Не коректный id клиента для редактирования");
            List<Client> clientList = clientService.getAllClientsOrdered();
            model.addAttribute("clientList", clientList);
            return "owner/clients/all";
        }catch (IllegalArgumentException e){
            model.addAttribute("globalError", "Вы пытаетесь обновить не существующего клиента");
            List<Client> clientList = clientService.getAllClientsOrdered();
            model.addAttribute("clientList", clientList);
            return "owner/clients/all";
        }catch (Exception e){
            System.out.println("message = " +  e.getMessage());
            throw new GlobalRuntimeExeption(GeneralUtils.stackTraceToString(e));
        }
    }

    @RequestMapping(value = "/update/{idString}", method = RequestMethod.POST)
    public String updateClient_Post(@PathVariable("idString") String idString,
                                    @Valid @ModelAttribute Client client,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes,
                                    Model model){
        try {
            Client clientValidId = clientService.validateId(idString);
            client.setId(clientValidId.getId());

            if (bindingResult.hasErrors()){
                model.addAttribute("title", "update");
                return "owner/clients/add";
            }

            client = clientService.update(client);

            System.out.println("client = " +  client);
            System.out.println("ID = " + idString);

            redirectAttributes.addFlashAttribute("globalMsg", "Пользователь #"+client.getId() + " успешно обновлен");
            return "redirect:/owner/clients/all";

        }catch (NumberFormatException e ){
            model.addAttribute("globalError", "Не коректный id клиента для редактирования");
            List<Client> clientList = clientService.getAllClientsOrdered();
            model.addAttribute("clientList", clientList);
            return "owner/clients/all";
        }catch (IllegalArgumentException e){
            model.addAttribute("globalError", "Вы пытаетесь обновить не существующего клиента");
            List<Client> clientList = clientService.getAllClientsOrdered();
            model.addAttribute("clientList", clientList);
            return "owner/clients/all";
        }catch (Exception e){
            System.out.println("message = " +  e.getMessage());
            throw new GlobalRuntimeExeption(GeneralUtils.stackTraceToString(e));
        }

    }


    private Client setIntoClientDateOfCretationAndCreatedBy(Client client, User user){
        client.setDateOfCreation(new Date());
        client.setCreatedBy(user);
        return  client;
    }
}
