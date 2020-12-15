import React from "react";
import ItemTable from "../ItemTable";

class OfferTable extends React.Component {

    makeHeader() {

        let header = [
            'Identificator', 'Order Id',
            'Driver Id', 'Client id',
            'Price', 'Accepted'
        ]

        if (this.props.actions.accept) {
            header = header.concat('Actions')
        }

        return header;
    }

    makeData() {

        return this.props.offers.map((o) => {

            let item = {
                key: o.id,
                values: [
                    o.id, o.orderId,
                    o.driverId, o.clientId,
                    o.price,
                    o.accepted ? 'true' : 'false'
                ]
            }

            if (this.props.actions.accept) {
                item.values = item.values.concat(
                    <button onClick={() => this.props.handleAccept(o.id)}>A</button>
                )
            }

            return item
        })

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

export default OfferTable;