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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/owner/client/")
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
            redirectAttributes.addFlashAttribute("globalMsg", "Клиент + " + client.getName() + " создан успешно (#"+ id + ")");
            return "redirect:owner/client/all";

        }catch (Exception e){
            throw new GlobalRuntimeExeption(GeneralUtils.stackTraceToString(e));
        }
    }

    private Client setIntoClientDateOfCretationAndCreatedBy(Client client, User user){
        client.setDateOfCreation(new Date());
        client.setCreatedBy(user);
        return  client;
    }
}
