import React from "react";

import ItemTable from "../utils/ItemTable";

class ContractTable extends React.Component {

    render() {

        const header = [
            'Contract Id', 'Manager Id', 'Order Id',
            'Client Id', 'Client status', 'Driver Id', 'Driver Status',
            'Payment', 'Actions'
        ]

        const data = this.props.contracts.map((c) => {
            return {
                key: c.id,
                values: [
                    c.id, c.managerId, c.orderId,
                    c.clientId, c.clientStatus,
                    c.driverId, c.driverStatus,
                    c.payment,
                    <>
                        <button onClick={() => this.props.handleApprove(c.id)}>A</button>
                        <button onClick={() => this.props.handleRefuse(c.id)}>R</button>
                        <button onClick={() => this.props.handleComplete(c.id)}>C</button>
                    </>,
                ]
            }
        })

        return (
            <ItemTable
                header={header}
                data={data}
            />
        )
    }
}

export default ContractTable;