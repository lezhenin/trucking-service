package trucking.model;

import javax.persistence.Entity;
import java.util.Collection;

@Entity
public abstract class Contractor extends Person {

    private Contract.Role role;

    public Contractor(
            String firstName, String lastName, String phoneNumber, String email, Contract.Role role
    ) {
        super(firstName, lastName, phoneNumber, email);
        this.role = role;
    }

    public Contractor(Contract.Role role) {
        this.role = role;
    }

    public Contractor() {
        super();
    }

    public void approveContract(Contract contract) throws Exception {
        this.checkParticipation(contract);
        this.checkAgreementStatus(contract);
        contract.setContractorStatus(this.role, Contract.Status.APPROVED);
    }

    public void refuseContract(Contract contract) throws Exception {
        this.checkParticipation(contract);
        this.checkAgreementStatus(contract);
        contract.setContractorStatus(this.role, Contract.Status.REFUSED);
    }

    public void completeContract(Contract contract) throws Exception {
        this.checkParticipation(contract);
        this.checkCompletionStatus(contract);
        contract.setContractorStatus(this.role, Contract.Status.COMPLETED);
    }

    private void checkParticipation(Contract contract) throws Exception {
        Contractor participant = contract.getContractors().get(this.role);
        if (!participant.getId().equals(this.getId())) {
            throw new ModelException("Contractor is not associated with contract");
        }
    }

    private void checkAgreementStatus(Contract contract) throws Exception {
        if (contract.getContractorsStatus().get(this.role) != Contract.Status.NONE) {
            throw new ModelException("Contractor already has approved/refused contract");
        }
    }

    private void checkCompletionStatus(Contract contract) throws Exception {

        Collection<Contract.Status> values = contract.getContractorsStatus().values();
        if (!values.stream().allMatch(s -> s == Contract.Status.APPROVED || s == Contract.Status.COMPLETED)) {
            throw new ModelException("Contractor hasn't approved contract");
        }

        if (contract.getContractorStatus(this.role) == Contract.Status.COMPLETED) {
            throw new ModelException("Contractor already has completed contract");
        }
    }
}
