import React from "react";
import axios from "axios";
import ItemTable from "./ItemTable";

class Orders extends React.Component {

    constructor(props) {
        super(props);
        this.state = {orders: []};
    }

    getOrders() {
        axios.get('/api/client/orders')
            .then((response) => {
                console.log(response)
                this.setState({ orders: response.data._embedded.orderDatas })
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    deleteOrder(id) {
        axios.delete('/api/client/orders/' + id)
            .then((response) => {
                const orders = this.state.orders.filter((o) => o.id !== id)
                this.setState({ orders: orders })
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    postOrder(order) {
        axios.post('/api/client/orders/', order)
            .then((response) => {
                const orders = this.state.orders.concat(response.data)
                this.setState({ orders: orders })
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    componentDidMount() {
        this.getOrders()
    }

    render() {
        return (
            <div>

                <div className="truckingitemlist">
                    <h2>List of orders</h2>
                    <OrderTable
                        orders={this.state.orders}
                        handleDelete={(id) => {this.deleteOrder(id)}}
                    />
                </div>

                <div>
                    <h2>Create new order</h2>
                    <OrderForm
                        handleCreate={(o) => {this.postOrder(o)}}
                    />
                </div>

            </div>
        )
    }
}


class OrderTable extends React.Component {

    render() {
        const header = [
            'Identificator', 'Cargo weight', 'Cargo size',
            'Loading address', 'Shipping address', 'State',
            'Offer Id', 'Actions'
        ]

        const data = this.props.orders.map((o) => {
            return {
                key: o.id,
                values: [
                    o.id, o.cargoWeight,
                    <>({o.cargoWidth}, {o.cargoLength}, {o.cargoHeight})</>,
                    o.loadingAddress, o.shippingAddress,
                    o.orderState, o.offerId,
                    <>{(o.orderState !== 'PUBLISHED')
                        ? <button disabled>D</button>
                        : <button onClick={() => this.props.handleDelete(o.id)}>D</button>
                    }</>,
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

class OrderForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            cargoWeight: 10,
            cargoWidth: 10,
            cargoLength: 10,
            cargoHeight: 10,
            loadingAddress: '',
            shippingAddress: '',
            deadline: 'yesterday'
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;
        this.setState({
            [name]: value
        });

        console.log(this.state)
    }

    handleSubmit(event) {
        console.log(this.state)
        event.preventDefault();
        this.props.handleCreate(this.state)
    }

    render() {
        return (

            <form onSubmit={(e) => this.handleSubmit(e)}>

                <fieldset>

                    <div>
                        <label>Cargo weight:</label>
                        <input
                            name="cargoWeight"
                            type="number"
                            value={this.state.cargoWeight}
                            onChange={(e) => this.handleChange(e)}
                        />
                    </div>

                    <div>
                        <label>Cargo size:</label>
                        <input
                            name="cargoWidth"
                            type="number"
                            size="2"
                            value={this.state.cargoWidth}
                            onChange={(e) => this.handleChange(e)}
                        />
                        <input
                            name="cargoLength"
                            type="number"
                            size="2"
                            value={this.state.cargoLength}
                            onChange={(e) => this.handleChange(e)}
                        />
                        <input
                            name="cargoHeight"
                            type="number"
                            size="2"
                            value={this.state.cargoHeight}
                            onChange={(e) => this.handleChange(e)}
                        />
                    </div>

                    <div>
                        <label>Loading Address:</label>
                        <input
                            name="loadingAddress"
                            type="text"
                            value={this.state.loadingAddress}
                            onChange={(e) => this.handleChange(e)}
                        />
                    </div>

                    <div>
                        <label>Shipping Address:</label>
                        <input
                            name="shippingAddress"
                            type="text"
                            value={this.state.shippingAddress}
                            onChange={(e) => this.handleChange(e)}
                        />
                    </div>

                    <div className="submit">
                        <button type="submit" name="create">Create Order</button>
                    </div>

                </fieldset>

            </form>

        )
    }

}

export default Orders;