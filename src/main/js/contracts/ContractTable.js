import React from "react";

import ItemTable from "../utils/ItemTable";

class ContractTable extends React.Component {

    makeHeader() {

        let header = [
            'Contract Id', 'Manager Id', 'Order Id',
            'Client Id', 'Client status', 'Driver Id', 'Driver Status',
            'Payment'
        ]

        const hasActions = this.props.actions.delete ||
                           this.props.actions.approve ||
                           this.props.actions.refuse ||
                           this.props.actions.complete

        if (hasActions) {
            header = header.concat('Actions')
        }

        return header;
    }

    makeData() {
        return this.props.contracts.map((c) => {
            let item = {
                key: c.id,
                values: [
                    c.id, c.managerId, c.orderId,
                    c.clientId, c.clientStatus,
                    c.driverId, c.driverStatus,
                    c.payment
                ]
            }

            const buttons = this.makeButtons(c);
            if (buttons.length > 0) {
                item.values.push(
                    <>{buttons}</>
                )
            }

            return item;
        })
    }

    makeButtons(c) {
        let buttons = []

        if (this.props.actions.delete) {
            buttons.push(
                <button onClick={() => this.props.handleDelete(c.id)}>D</button>
            )
        }

        if (this.props.actions.approve) {
            buttons.push(
                <button onClick={() => this.props.handleApprove(c.id)}>A</button>
            )
        }

        if (this.props.actions.refuse) {
            buttons.push(
                <button onClick={() => this.props.handleRefuse(c.id)}>R</button>
            )
        }

        if (this.props.actions.complete) {
            buttons.push(
                <button onClick={() => this.props.handleComplete(c.id)}>C</button>
            )
        }

        return buttons
    }


    render() {
        return (
            <ItemTable
                header={this.makeHeader()}
                data={this.makeData()}
            />
        )
    }
}

export default ContractTable;