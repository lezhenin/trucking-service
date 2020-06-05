package trucking.web.controllers.hypertext;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import trucking.core.model.Client;
import trucking.core.model.Order;
import trucking.core.repository.ClientRepository;
import trucking.core.repository.ContractRepository;
import trucking.core.repository.OrderRepository;
import trucking.web.UsernameIdMapper;
import trucking.web.data.DataObjectMapper;
import trucking.web.data.NewOrderData;
import trucking.web.data.OrderData;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/client")
public class ClientWebController {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ContractRepository contractRepository;

    private final UsernameIdMapper usernameIdMapper;

    public ClientWebController(OrderRepository orderRepository, ClientRepository clientRepository, ContractRepository contractRepository, UsernameIdMapper usernameIdMapper) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.contractRepository = contractRepository;
        this.usernameIdMapper = usernameIdMapper;
    }

    @RequestMapping({"/orders"})
    public String orders(Principal principal, Model model) {
        Long id = usernameIdMapper.map(principal);
        Client client = clientRepository.findById(id).get();

        List<Order> orders = client.getOrders();
        List<OrderData> orderDataList = orders.stream().map(DataObjectMapper::dataFromOrder).collect(Collectors.toList());

        NewOrderData newOrderData = new NewOrderData();
        OrderData orderData = new OrderData();

        model.addAttribute("orderDataList", orderDataList);
        model.addAttribute("newOrderData", newOrderData);
        model.addAttribute("orderData", orderData);
        return "/client/orders";
    }


    @RequestMapping(value={"/orders"}, params={"create"})
    public String createOrder(Principal principal, NewOrderData newOrderData) {
        Long id = usernameIdMapper.map(principal);
        Client client = clientRepository.findById(id).get();
        Order order = DataObjectMapper.orderFromData(newOrderData, client);
        orderRepository.save(order);
        client.createOrder(order);
        return "redirect:/client/orders";
    }

    @RequestMapping(value={"/orders"}, params={"remove"})
    public String removeOrder(Principal principal, OrderData orderData) throws Exception {
        Long id = usernameIdMapper.map(principal);
        Client client = clientRepository.findById(id).get();
        Order order = orderRepository.findById(orderData.getId()).get();
        client.removeOrder(order);
        return "redirect:/client/orders";
    }
}
