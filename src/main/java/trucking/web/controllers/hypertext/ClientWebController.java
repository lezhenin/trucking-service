package trucking.web.controllers.hypertext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import trucking.web.security.UsernameIdMapper;
import trucking.web.datatransfer.*;
import trucking.web.services.ClientService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientWebController {

    private final ClientService clientService;
    private final UsernameIdMapper usernameIdMapper;

    public ClientWebController(
            @Autowired ClientService clientService,
            @Autowired UsernameIdMapper usernameIdMapper
    ) {
        this.clientService = clientService;
        this.usernameIdMapper = usernameIdMapper;
    }

    @RequestMapping({"/orders"})
    public String orders(Principal principal, Model model) {
        Long id = usernameIdMapper.map(principal);
        List<OrderData> orderDataList = clientService.getOrders(id);
        NewOrderData newOrderData = new NewOrderData();
        OrderData orderData = new OrderData();
        model.addAttribute("orderDataList", orderDataList);
        model.addAttribute("newOrderData", newOrderData);
        model.addAttribute("orderData", orderData);
        return "/client/orders";
    }


    @RequestMapping(value = {"/orders"}, params = {"create"})
    public String createOrder(Principal principal, NewOrderData newOrderData) throws Exception {
        Long id = usernameIdMapper.map(principal);
        clientService.createOrder(id, newOrderData);
        return "redirect:/client/orders";
    }

    @RequestMapping(value = {"/orders"}, params = {"remove"})
    public String removeOrder(Principal principal, long orderId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        clientService.removeOrder(id, orderId);
        return "redirect:/client/orders";
    }

    @RequestMapping({"/contracts"})
    public String contracts(Principal principal, Model model) {
        Long id = usernameIdMapper.map(principal);
        List<ContractData> contractDataList = clientService.getContracts(id);
        ContractData contractData = new ContractData();
        model.addAttribute("contractDataList", contractDataList);
        model.addAttribute("contractData", contractData);
        return "/client/contracts";
    }

    @RequestMapping(value = {"/contracts"}, params = {"update"})
    public String updateContract(Principal principal, long contractId, @RequestParam("update") String action) throws Exception {
        System.out.println(contractId);
        Long id = usernameIdMapper.map(principal);
        //noinspection IfCanBeSwitch
        if (action.equals("approve")) {
            System.out.println("approve");
            clientService.approveContract(id, contractId);
        } else if (action.equals("refuse")) {
            clientService.refuseContract(id, contractId);
        } else if (action.equals("complete")) {
            clientService.completeContract(id, contractId);
        }
        return "redirect:/client/contracts";
    }
}
