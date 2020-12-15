import React from "react";
import axios from "axios";
import ItemTable from "./ItemTable";

class Orders extends React.Component {

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
                console.log(response)
                this.setState({ orders: response.data._embedded.orderDatas })
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    deleteOrder(id) {
        axios.delete(`${this.props.collectionUrl}/${id}`)
            .then((response) => {
                const orders = this.state.orders.filter((o) => o.id !== id)
                this.setState({ orders: orders })
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    postOrder(order) {
        axios.post(this.props.collectionUrl, order)
            .then((response) => {
                const orders = this.state.orders.concat(response.data)
                this.setState({ orders: orders })
            })
            .catch(function (error) {
                console.log(error);
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

                    {this.props.viewOptions && this.props.viewOptions.map((o) => {
                        return <button onClick={() => {this.getOrders(o)}}>{o}</button>
                    })}

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