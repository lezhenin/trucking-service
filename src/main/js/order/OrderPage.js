import React from "react";

import OrderTable from "./OrderTable";
import OrderForm from "./OrderForm";

import axios from "axios";

class OrderPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            orders: []
        };
    }

    getOrders(viewOption) {
        const url = viewOption
            ? `${this.props.collectionUrl}?view=${viewOption}`
            : this.props.collectionUrl

        axios.get(url)
            .then((response) => {
                const data = response.data
                const orders = data.hasOwnProperty('_embedded')
                    ? data._embedded.orderDatas
                    : []
                this.setState({ orders: orders })
            })
            .catch((error) => {
                const message = error.response.data
                alert(`Error: ${message}`);
            })
    }

    deleteOrder(id) {
        axios.delete(`${this.props.collectionUrl}/${id}`)
            .then((response) => {
                const orders = this.state.orders.filter((o) => o.id !== id)
                this.setState({ orders: orders })
            })
            .catch((error) => {
                const message = error.response.data
                alert(`Error: ${message}`);
            })
    }

    postOrder(order) {
        axios.post(this.props.collectionUrl, order)
            .then((response) => {
                const orders = this.state.orders.concat(response.data)
                this.setState({ orders: orders })
            })
            .catch((error) => {
                const message = error.response.data
                alert(`Error: ${message}`);
            })
    }

    componentDidMount() {
        this.getOrders(null)
    }

    render() {
        return (
            <div>

                <div className="truckingitemlist">
                    <h2>List of orders</h2>

                    {this.props.viewOptions &&
                        <div className="vbs">
                            {this.props.viewOptions.map((o) => {
                                return <button onClick={() => {this.getOrders(o)}}>{o}</button>
                            })}
                        </div>
                    }

                    <OrderTable
                        orders={this.state.orders}
                        actions={this.props.actions}
                        handleDelete={(id) => {this.deleteOrder(id)}}
                    />
                </div>

                {this.props.actions.create &&
                    <div>
                        <h2>Create new order</h2>
                        <OrderForm
                            handleCreate={(o) => {this.postOrder(o)}}
                        />
                    </div>
                }

            </div>
        )
    }
}

export default OrderPage;