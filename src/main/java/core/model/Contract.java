package core.model;

import core.repository.RepositoryItem;

import java.util.EnumMap;
import java.util.EnumSet;


public class Contract extends RepositoryItem {

    static class Participant extends RepositoryItem {
        private final ParticipantRole type;

        public Participant(ParticipantRole type) {
            this.type = type;
        }

        public void approveContract(Contract contract) throws Exception {
            checkParticipation(contract);
            checkAgreement(contract);
            contract.agreementMap.put(this.type, true);
        }

        public void refuseContract(Contract contract) throws Exception {
            checkParticipation(contract);
            checkAgreement(contract);
            contract.agreementMap.put(this.type, false);
        }

        public void completeContract(Contract contract) throws Exception {
            checkParticipation(contract);
            checkCompletion(contract);
            contract.completionSet.add(this.type);
        }


        private void checkCompletion(Contract contract) throws Exception {
            if (contract.completionSet.contains(this.type)) {
                throw new Exception();
            }
            for (ParticipantRole role: ParticipantRole.values()) {
                if (
                        !contract.agreementMap.containsKey(role) ||
                        !contract.agreementMap.get(role)
                ) {
                    throw new Exception();
                }
            }
        }

        private void checkAgreement(Contract contract) throws Exception {
            if (contract.agreementMap.containsKey(this.type)) {
                throw new Exception();
            }
        }

        private void checkParticipation(Contract contract) throws Exception {
            if (contract.participants.get(this.type).getId() != this.getId()) {
                throw new Exception();
            }
        }
    }

    enum ParticipantRole {
        CLIENT,
        DRIVER
    }

    private final Order order;
    private final Driver driver;
    private final Manager manager;

    private final EnumMap<ParticipantRole, Participant> participants = new EnumMap<>(ParticipantRole.class);
    private final EnumMap<ParticipantRole, Boolean> agreementMap = new EnumMap<>(ParticipantRole.class);
    private final EnumSet<ParticipantRole> completionSet = EnumSet.noneOf(ParticipantRole.class);

    private final int payment;

    public Contract(Order order, Driver driver, Manager manager, int payment) {

        this.order = order;
        this.driver = driver;
        this.manager = manager;
        this.payment = payment;

        this.participants.put(ParticipantRole.CLIENT, order.getClient());
        this.participants.put(ParticipantRole.DRIVER, driver);

    }

    public Order getOrder() {
        return order;
    }

    public Driver getDriver() {
        return driver;
    }

    public Manager getManager() {
        return manager;
    }

    public int getPayment() {
        return payment;
    }

    public boolean isComplete() {
        for (ParticipantRole role: ParticipantRole.values()) {
            if (!this.completionSet.contains(role)) {
                return false;
            }
        }
        return  true;
    }


}


