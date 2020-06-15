package trucking.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ContractParticipant extends Person {

    private Contract.ParticipantRole role;

    public ContractParticipant(
            String firstName, String lastName, String phoneNumber, String email, Contract.ParticipantRole role
    ) {
        super(firstName, lastName, phoneNumber, email);
        this.role = role;
    }

    public ContractParticipant(Contract.ParticipantRole role) {
        this.role = role;
    }

    public ContractParticipant() {
        super();
    }

    public void approveContract(Contract contract) throws Exception {
        this.checkParticipation(contract);
        this.checkAgreementStatus(contract);
        contract.setParticipantStatus(this.role, Contract.Status.APPROVED);
    }

    public void refuseContract(Contract contract) throws Exception {
        this.checkParticipation(contract);
        this.checkAgreementStatus(contract);
        contract.setParticipantStatus(this.role, Contract.Status.REFUSED);
    }

    public void completeContract(Contract contract) throws Exception {
        this.checkParticipation(contract);
        this.checkCompletionStatus(contract);
        contract.setParticipantStatus(this.role, Contract.Status.COMPLETED);
    }

    private void checkParticipation(Contract contract) throws Exception {
        ContractParticipant participant = contract.getAllParticipants().get(this.role);
        if (!participant.getId().equals(this.getId())) {
            throw new Exception();
        }
    }

    private void checkAgreementStatus(Contract contract) throws Exception {
        if (contract.getAllParticipantStatus().get(this.role) != Contract.Status.NONE) {
            throw new Exception();
        }
    }

    private void checkCompletionStatus(Contract contract) throws Exception {

        Collection<Contract.Status> values = contract.getAllParticipantStatus().values();
        if (!values.stream().allMatch(s -> s == Contract.Status.APPROVED || s == Contract.Status.COMPLETED)) {
            throw new Exception();
        }

        if (contract.getParticipantStatus(this.role) == Contract.Status.COMPLETED) {
            throw new Exception();
        }
    }
}
