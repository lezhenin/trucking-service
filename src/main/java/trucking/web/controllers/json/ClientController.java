package trucking.web.controllers.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import trucking.web.datatransfer.OfferData;
import trucking.web.security.UsernameIdMapper;
import trucking.web.datatransfer.ContractData;
import trucking.web.datatransfer.NewOrderData;
import trucking.web.datatransfer.OrderData;
import trucking.web.services.ClientService;

import java.security.Principal;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Controller
@ResponseBody
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;
    private final UsernameIdMapper usernameIdMapper;

    private OrderData addLinksToOrder(Principal principal, OrderData order) throws Exception {
        Link selfLink = linkTo(methodOn(ClientController.class)
                .getOrder(principal, order.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(ClientController.class)
                .getOrders(principal)).withRel("orders");
        Link offersLink = linkTo(methodOn(ClientController.class)
                .getOffers(principal, order.getId())).withRel("offers");
        return order.add(selfLink, allLink, offersLink);
    }

    private OfferData addLinksToOffer(Principal principal, OfferData offer) throws Exception {
        Link selfLink = linkTo(methodOn(ClientController.class)
                .getOffer(principal, offer.getId())).withSelfRel();
        Link acceptLink = linkTo(methodOn(ClientController.class)
                .acceptOffer(principal, offer.getId())).withRel("accept");
        Link orderLink = linkTo(methodOn(ClientController.class)
                .getOrder(principal, offer.getOrderId())).withRel("order");
        return offer.add(selfLink, acceptLink, orderLink);
    }

    private ContractData addLinksToContract(Principal principal, ContractData contract) throws Exception {
        Link selfLink = linkTo(methodOn(ClientController.class)
                .getContract(principal, contract.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(ClientController.class)
                .getContracts(principal)).withRel("contracts");
        Link approveLink = linkTo(methodOn(ClientController.class)
                .approveContract(principal, contract.getId())).withRel("approve");
        Link refuseLink = linkTo(methodOn(ClientController.class)
                .refuseContract(principal, contract.getId())).withRel("refuse");
        Link completeLink = linkTo(methodOn(ClientController.class)
                .completeContract(principal, contract.getId())).withRel("complete");
        Link orderLink = linkTo(methodOn(ClientController.class)
                .getOrder(principal, contract.getOrderId())).withRel("order");
        return contract.add(selfLink, allLink, approveLink, refuseLink, completeLink, orderLink);
    }

    public ClientController(
            @Autowired ClientService clientService,
            @Autowired UsernameIdMapper usernameIdMapper
    ) {
        this.clientService = clientService;
        this.usernameIdMapper = usernameIdMapper;
    }

    @GetMapping("/orders")
    CollectionModel<OrderData> getOrders(Principal principal) throws Exception {
        Long id = usernameIdMapper.map(principal);
        List<OrderData> orders = clientService.getOrders(id);
        for (OrderData order: orders) {
            addLinksToOrder(principal, order);
        }

        Link selfLink = linkTo(methodOn(ClientController.class).getOrders(principal)).withSelfRel();
        return CollectionModel.of(orders, selfLink);
    }

    @GetMapping("/orders/{orderId}")
    OrderData getOrder(Principal principal, @PathVariable Long orderId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        OrderData order = clientService.getOrder(id, orderId).get();
        return addLinksToOrder(principal, order);
    }

    @PostMapping("/orders")
    OrderData createOrder(Principal principal, @RequestBody NewOrderData orderData) throws Exception {
        Long id = usernameIdMapper.map(principal);
        OrderData order = clientService.createOrder(id, orderData);
        return addLinksToOrder(principal, order);
    }

    @DeleteMapping("/orders/{orderId}")
    void removeOrder(Principal principal, @PathVariable Long orderId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        clientService.removeOrder(id, orderId);
    }

    @GetMapping("/offers")
    CollectionModel<OfferData> getOffers(Principal principal, @RequestParam Long orderId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        List<OfferData> offers = clientService.getOffers(id, orderId);
        for (OfferData offer: offers) {
            addLinksToOffer(principal, offer);
        }
        Link selfLink = linkTo(methodOn(ClientController.class).getOffers(principal, orderId)).withSelfRel();
        return CollectionModel.of(offers, selfLink);
    }

    @GetMapping("/offers/{offerId}")
    OfferData getOffer(Principal principal, @PathVariable Long offerId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        OfferData offer = clientService.getOffer(id, offerId).get();
        return addLinksToOffer(principal, offer);
    }

    @GetMapping("/offers/{offerId}/accept")
    OfferData acceptOffer(Principal principal, @PathVariable Long offerId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        return clientService.acceptOffer(id, offerId);
    }

    @GetMapping("/contracts")
    CollectionModel<ContractData> getContracts(Principal principal) throws Exception {
        Long id = usernameIdMapper.map(principal);
        List<ContractData> contracts = clientService.getContracts(id);
        for (ContractData contract: contracts) {
            addLinksToContract(principal, contract);
        }

        Link selfLink = linkTo(methodOn(ClientController.class).getContracts(principal)).withSelfRel();
        return CollectionModel.of(contracts, selfLink);
    }

    @GetMapping("/contracts/{contractId}")
    ContractData getContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        ContractData contract = clientService.getContract(id, contractId).get();
        return addLinksToContract(principal, contract);
    }

    @GetMapping("/contracts/{contractId}/approve")
    ContractData approveContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        ContractData contract = clientService.approveContract(id, contractId);
        return addLinksToContract(principal, contract);
    }

    @GetMapping("/contracts/{contractId}/refuse")
    ContractData refuseContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        ContractData contract = clientService.refuseContract(id, contractId);
        return addLinksToContract(principal, contract);
    }

    @GetMapping("/contracts/{contractId}/complete")
    ContractData completeContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        ContractData contract = clientService.completeContract(id, contractId);
        return addLinksToContract(principal, contract);
    }
    
}
