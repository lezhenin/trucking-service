package trucking.web.controllers.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import trucking.model.Contract;
import trucking.model.Driver;
import trucking.model.Order;
import trucking.repository.ContractRepository;
import trucking.repository.DriverRepository;
import trucking.web.datatransfer.*;
import trucking.web.security.UsernameIdMapper;
import trucking.web.services.DriverService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Controller
@ResponseBody
@RequestMapping("/api/driver")
public class DriverController {

    private final DriverService driverService;
    private final UsernameIdMapper usernameIdMapper;

    public DriverController(
            @Autowired DriverService driverService,
            @Autowired UsernameIdMapper usernameIdMapper
    ) {
        this.driverService = driverService;
        this.usernameIdMapper = usernameIdMapper;
    }

    private OrderData addLinksToOrder(Principal principal, OrderData order) throws Exception {
        Link selfLink = linkTo(methodOn(DriverController.class)
                .getOrder(principal, order.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(DriverController.class)
                .getOrders(principal)).withRel("orders");
        Link offersLink = linkTo(methodOn(DriverController.class)
                .getOffers(principal, order.getId())).withRel("offers");
        return order.add(selfLink, allLink, offersLink);
    }

    private OfferData addLinksToOffer(Principal principal, OfferData offer) throws Exception {
        Link selfLink = linkTo(methodOn(DriverController.class)
                .getOffer(principal, offer.getId())).withSelfRel();
        Link orderLink = linkTo(methodOn(DriverController.class)
                .getOrder(principal, offer.getOrderId())).withRel("order");
        return offer.add(selfLink, orderLink);
    }

    private ContractData addLinksToContract(Principal principal, ContractData contract) throws Exception {
        Link selfLink = linkTo(methodOn(DriverController.class)
                .getContract(principal, contract.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(DriverController.class)
                .getContracts(principal)).withRel("contracts");
        Link approveLink = linkTo(methodOn(DriverController.class)
                .approveContract(principal, contract.getId())).withRel("approve");
        Link refuseLink = linkTo(methodOn(DriverController.class)
                .refuseContract(principal, contract.getId())).withRel("refuse");
        Link completeLink = linkTo(methodOn(DriverController.class)
                .completeContract(principal, contract.getId())).withRel("complete");
        Link orderLink = linkTo(methodOn(DriverController.class)
                .getOrder(principal, contract.getOrderId())).withRel("order");
        return contract.add(selfLink, allLink, approveLink, refuseLink, completeLink, orderLink);
    }

    @GetMapping("/orders")
    CollectionModel<OrderData> getOrders(Principal principal) throws Exception {
        Long id = usernameIdMapper.map(principal);
        List<OrderData> orders = driverService.getOrders(id);
        for (OrderData order: orders) {
            addLinksToOrder(principal, order);
        }
        Link selfLink = linkTo(methodOn(DriverController.class).getOrders(principal)).withSelfRel();
        return CollectionModel.of(orders, selfLink);
    }

    @GetMapping("/orders/{orderId}")
    OrderData getOrder(Principal principal, @PathVariable Long orderId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        OrderData order = driverService.getOrder(id, orderId).get();
        return addLinksToOrder(principal, order);
    }

    @GetMapping("/offers")
    CollectionModel<OfferData> getOffers(Principal principal, @RequestParam Long orderId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        List<OfferData> offers = driverService.getOffers(id, orderId);
        for (OfferData offer: offers) {
            addLinksToOffer(principal, offer);
        }
        Link selfLink = linkTo(methodOn(DriverController.class).getOffers(principal, orderId)).withSelfRel();
        return CollectionModel.of(offers, selfLink);
    }

    @GetMapping("/offers/{offerId}")
    OfferData getOffer(Principal principal, @PathVariable Long offerId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        OfferData offer = driverService.getOffer(id, offerId).get();
        return addLinksToOffer(principal, offer);
    }

    @PostMapping("/offers")
    OfferData createOffer(Principal principal, @RequestBody NewOfferData newOfferData) throws Exception {
        Long id = usernameIdMapper.map(principal);
        OfferData offer = driverService.createOffer(id, newOfferData);
        return addLinksToOffer(principal, offer);
    }

    @GetMapping("/contracts")
    CollectionModel<ContractData> getContracts(Principal principal) throws Exception {
        Long id = usernameIdMapper.map(principal);
        List<ContractData> contracts = driverService.getContracts(id);
        for (ContractData contract: contracts) {
            addLinksToContract(principal, contract);
        }
        Link selfLink = linkTo(methodOn(DriverController.class).getContracts(principal)).withSelfRel();
        return CollectionModel.of(contracts, selfLink);
    }

    @GetMapping("/contracts/{contractId}")
    ContractData getContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        ContractData contract = driverService.getContract(id, contractId).get();
        return addLinksToContract(principal, contract);
    }

    @GetMapping("/contracts/{contractId}/approve")
    ContractData approveContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        ContractData contract = driverService.approveContract(id, contractId);
        return addLinksToContract(principal, contract);
    }

    @GetMapping("/contracts/{contractId}/refuse")
    ContractData refuseContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        ContractData contract = driverService.refuseContract(id, contractId);
        return addLinksToContract(principal, contract);
    }

    @GetMapping("/contracts/{contractId}/complete")
    ContractData completeContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        ContractData contract = driverService.completeContract(id, contractId);
        return addLinksToContract(principal, contract);
    }
    
}
