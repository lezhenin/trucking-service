import React from "react";
import axios from "axios";
import ItemTable from "./ItemTable";

class Offers extends React.Component {

    constructor(props) {
        super(props);
        this.state = {offers: [] };
        this.currentOrderId = null;
    }

    getOffers(orderId) {
        axios.get(`${this.props.collectionUrl}?orderId=${orderId}`)
            .then((response) => {
                console.log(response)
                this.setState({ offers: response.data._embedded.offerDatas })
            })
            .catch((error) => {
                this.setState({ offers: [] })
                console.log(error);
            })
    }

    postOffer(offer) {
        axios.post(this.props.collectionUrl, offer)
            .then((response) => {
                if (this.currentOrderId === offer.orderId) {
                    const offers = this.state.offers.concat(response.data)
                    this.setState({offers: offers})
                }
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    acceptOffer(id) {
        axios.get(`${this.props.collectionUrl}/${id}/accept`)
            .then((response) => {
                const offers = this.state.offers.map((o) => {
                    return (o.id === id) ? response.data : o
                })
                this.setState({ offers: offers })
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    render() {
        return (
            <div>

                <div className="truckingitemlist">
                    <h2>List of offers</h2>
                    <OfferFilter
                        handleSubmit={(orderId) => {
                            this.currentOrderId = orderId;
                            this.getOffers(orderId)}
                        }
                    />
                    <OfferTable
                        offers={this.state.offers}
                        actions={this.props.actions}
                        handleAccept={(id) => this.acceptOffer(id)}
                    />
                </div>

                {this.props.actions.create &&
                <div>
                    <h2>Create new order</h2>
                    <OfferForm
                        handleCreate={(o) => {this.postOffer(o)}}
                    />
                </div>
                }

            </div>
        )
    }
}

class OfferFilter extends React.Component {

    constructor(props) {
        super(props);
        this.state = {orderId: 0}
    }

    handleSubmit(event) {
        console.log(this.state)
        event.preventDefault();
        this.props.handleSubmit(this.state.orderId)
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

    render() {
        return (
        <form onSubmit={(e) => this.handleSubmit(e)}>
            <label>Order Id:</label>
            <input
                name="orderId"
                type="number"
                value={this.state.orderId}
                onChange={(e) => this.handleChange(e)}
            />
        </form>
        )
    }
}

class OfferForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            price: 1000,
            orderId: 0,
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;
        this.setState({
            [name]: value
        });
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
                        <label>Order Id:</label>
                        <input
                            name="orderId"
                            type="number"
                            value={this.state.orderId}
                            onChange={(e) => this.handleChange(e)}
                        />
                    </div>

                    <div>
                        <label>Price:</label>
                        <input
                            name="price"
                            type="number"
                            value={this.state.price}
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

export default Offers;