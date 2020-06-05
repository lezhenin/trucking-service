package trucking.web.controllers.hypertext;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import trucking.core.model.Client;
import trucking.core.model.Manager;
import trucking.core.model.Order;
import trucking.core.repository.ClientRepository;
import trucking.core.repository.ContractRepository;
import trucking.core.repository.ManagerRepository;
import trucking.core.repository.OrderRepository;
import trucking.web.UsernameIdMapper;
import trucking.web.data.DataObjectMapper;
import trucking.web.data.NewOrderData;
import trucking.web.data.OrderData;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manager")
public class MangerWebController {

    private final OrderRepository orderRepository;
    private final ManagerRepository managerRepository;
    private final ContractRepository contractRepository;

    private final UsernameIdMapper usernameIdMapper;

    public MangerWebController(OrderRepository orderRepository, ManagerRepository managerRepository, ContractRepository contractRepository, UsernameIdMapper usernameIdMapper) {
        this.orderRepository = orderRepository;
        this.managerRepository = managerRepository;
        this.contractRepository = contractRepository;
        this.usernameIdMapper = usernameIdMapper;
    }

    @RequestMapping({"/orders"})
    public String orders(Principal principal, Model model) {

        Long id = usernameIdMapper.map(principal);
        Manager manager = managerRepository.findById(id).get();

        List<Order> orders = manager.getOrders();
        List<OrderData> orderDataList = orders.stream().map(DataObjectMapper::dataFromOrder).collect(Collectors.toList());

        System.out.println(orders.size());
        System.out.println(orderDataList.size());

        model.addAttribute("orderDataList", orderDataList);
        return "/manager/orders";
    }


}
