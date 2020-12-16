import React from "react";
import ItemTable from "../utils/ItemTable";

class OrderTable extends React.Component {

    makeHeader() {

        let header = [
            'Identificator', 'Cargo weight', 'Cargo size',
            'Loading address', 'Shipping address', 'State',
            'Offer Id'
        ]

        if (this.props.actions.delete) {
            header = header.concat('Actions')
        }

        return header;
    }

    makeData() {

        return this.props.orders.map((o) => {

            let item = {
                key: o.id,
                values: [
                    o.id, o.cargoWeight,
                    <>({o.cargoWidth}, {o.cargoLength}, {o.cargoHeight})</>,
                    o.loadingAddress, o.shippingAddress,
                    o.orderState, o.offerId
                ]
            }

            if (this.props.actions.delete) {
                item.values = item.values.concat(
                    <>
                        {(o.orderState !== 'PUBLISHED')
                            ? <button disabled>D</button>
                            : <button onClick={() => this.props.handleDelete(o.id)}>D</button>
                        }
                    </>
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

export default OrderTable;