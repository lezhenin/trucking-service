package trucking.web.controllers.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import trucking.web.security.UsernameIdMapper;
import trucking.web.datatransfer.*;
import trucking.web.services.ManagerService;

import java.security.Principal;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@ResponseBody
@RequestMapping("/api/manager")
public class ManagerController {

    private final ManagerService managerService;
    private final UsernameIdMapper usernameIdMapper;

    public ManagerController(
            @Autowired ManagerService managerService,
            @Autowired UsernameIdMapper usernameIdMapper
    ) {
        this.managerService = managerService;
        this.usernameIdMapper = usernameIdMapper;
    }

    @GetMapping("/contracts")
    CollectionModel<ContractData> getContracts(Principal principal) throws Exception {
        Long id = usernameIdMapper.map(principal);
        List<ContractData> contracts = managerService.getContracts(id);
        for (ContractData contract: contracts) {
            addLinksToContract(principal, contract);
        }
        Link selfLink = linkTo(methodOn(ManagerController.class).getContracts(principal)).withSelfRel();
        return CollectionModel.of(contracts, selfLink);
    }

    @GetMapping("/contracts/{contractId}")
    ContractData getContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        ContractData contract = managerService.getContract(id, contractId).get();
        return addLinksToContract(principal, contract);
    }

    @PostMapping("/contracts")
    ContractData createContract(Principal principal, @RequestBody NewContractData contractData) throws Exception {
        Long id = usernameIdMapper.map(principal);
        ContractData contract = managerService.createContract(id, contractData);
        return addLinksToContract(principal, contract);
    }

    @DeleteMapping("/contracts/{contractId}")
    void removeContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        managerService.removeContract(id, contractId);
    }

    @GetMapping("/contracts/{contractId}/complete")
    ContractData completeContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        ContractData contract = managerService.completeContract(id, contractId);
        return addLinksToContract(principal, contract);
    }

    @GetMapping("/orders")
    CollectionModel<OrderData> getOrders() throws Exception {
        List<OrderData> orders = managerService.getOrders();
        for (OrderData order: orders) {
            addLinksToOrder(order);
        }
        Link selfLink = linkTo(methodOn(ManagerController.class).getOrders()).withSelfRel();
        return CollectionModel.of(orders, selfLink);
    }

    @GetMapping("/orders/{orderId}")
    OrderData getOrder(@PathVariable Long orderId) throws Exception {
        OrderData order = managerService.getOrder(orderId).get();
        return addLinksToOrder(order);
    }

    @GetMapping("/offers")
    CollectionModel<OfferData> getOffers(@RequestParam Long orderId) throws Exception {
        List<OfferData> offers = managerService.getOffers(orderId);
        for (OfferData offer: offers) {
            addLinksToOffer(offer);
        }
        Link selfLink = linkTo(methodOn(ManagerController.class).getOffers(orderId)).withSelfRel();
        return CollectionModel.of(offers, selfLink);
    }

    @GetMapping("/offers/{offerId}")
    OfferData getOffer(@PathVariable Long offerId) throws Exception {
        OfferData offer = managerService.getOffer(offerId).get();
        return addLinksToOffer(offer);
    }

    @GetMapping("/drivers")
    CollectionModel<DriverData> getDrivers() {
        List<DriverData> drivers = managerService.getDrivers();
        for (DriverData driver: drivers) {
            addLinksToDriver(driver);
        }
        Link selfLink = linkTo(methodOn(ManagerController.class).getDrivers()).withSelfRel();
        return CollectionModel.of(drivers, selfLink);
    }

    @GetMapping("/drivers/{driverId}")
    DriverData getDriver(@PathVariable Long driverId) {
        DriverData driver = managerService.getDriver(driverId).get();
        return addLinksToDriver(driver);
    }

    private DriverData addLinksToDriver(DriverData driver) {
        Link selfLink = linkTo(methodOn(ManagerController.class)
                .getDriver(driver.getId())).withSelfRel();
        return driver.add(selfLink);
    }

    private OrderData addLinksToOrder(OrderData order) throws Exception {
        Link selfLink = linkTo(methodOn(ManagerController.class)
                .getOrder(order.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(ManagerController.class)
                .getOrders()).withRel("orders");
        Link offersLink = linkTo(methodOn(ManagerController.class)
                .getOffers(order.getId())).withRel("offers");
        return order.add(selfLink, allLink, offersLink);
    }

    private OfferData addLinksToOffer(OfferData offer) throws Exception {
        Link selfLink = linkTo(methodOn(ManagerController.class)
                .getOffer(offer.getId())).withSelfRel();
        Link orderLink = linkTo(methodOn(ManagerController.class)
                .getOrder(offer.getOrderId())).withRel("order");
        return offer.add(selfLink, orderLink);
    }

    private ContractData addLinksToContract(Principal principal, ContractData contract) throws Exception {
        Link selfLink = linkTo(methodOn(ManagerController.class)
                .getContract(principal, contract.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(ManagerController.class)
                .getContracts(principal)).withRel("contracts");
        Link completeLink = linkTo(methodOn(ManagerController.class)
                .completeContract(principal, contract.getId())).withRel("complete");
        Link orderLink = linkTo(methodOn(ManagerController.class)
                .getOrder(contract.getOrderId())).withRel("order");
        return contract.add(selfLink, allLink, completeLink, orderLink);
    }
}
