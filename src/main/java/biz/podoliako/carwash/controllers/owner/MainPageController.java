package biz.podoliako.carwash.controllers.owner;

import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.services.OrderService;
import biz.podoliako.carwash.services.StatisticService;
import biz.podoliako.carwash.view.OrderInBox;
import biz.podoliako.carwash.view.OrderedService;
import biz.podoliako.carwash.view.owner.AdminMainPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/owner")
public class MainPageController {

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/main")
    public String main(HttpSession session,
                       Model model){

        try {
          User user = (User) session.getAttribute("CurrentCarWashUser");

          AdminMainPage adminMainPage =  new AdminMainPage();
          adminMainPage.setCarWashesStatistic(statisticService.getStatisticForAdminMainPage(user.getOwnerId()));

          model.addAttribute("adminMainPage", adminMainPage);

        }catch (Exception e){
            System.out.println("Ooops... exception:" + e.getStackTrace());
        }

        return "owner/main";
    }

    @RequestMapping(value = "/currentOrders/{carWashId}", method = RequestMethod.GET)
    public String currentOrders(@PathVariable String carWashId,
                                HttpSession session,
                                Model model){

        try {
            Integer id = Integer.valueOf(carWashId);

            /* Добавить проверку на достаточность прав для просротра этих данных из прямой ссылки*/

            List<OrderInBox> orderInBoxList = orderService.getCurrentOrdersInAllBoxes(id);
            model.addAttribute("orderInBoxList", orderInBoxList);

            return "owner/currentOrders";

        }catch (NumberFormatException e){
            model.addAttribute("globalError", "Не коректный id мойки '" + carWashId + "'");
            return "owner/main";
        }


    }
}
