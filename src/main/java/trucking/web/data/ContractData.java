package trucking.web.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import trucking.core.model.*;


public class ContractData {

    @JsonProperty
    private final Long id;

    @JsonProperty
    private final Long orderId;
    @JsonProperty
    private final Long clientId;
    @JsonProperty
    private final Long driverId;
    @JsonProperty
    private final Long managerId;

    @JsonProperty
    private final Contract.Status clientStatus;
    @JsonProperty
    private final Contract.Status driverStatus;

    @JsonProperty
    private final int payment;

    public ContractData(Long id, Long orderId, Long clientId, Long driverId, Long managerId, Contract.Status clientStatus, Contract.Status driverStatus, int payment) {
        this.id = id;
        this.orderId = orderId;
        this.clientId = clientId;
        this.driverId = driverId;
        this.managerId = managerId;
        this.clientStatus = clientStatus;
        this.driverStatus = driverStatus;
        this.payment = payment;
    }


}
