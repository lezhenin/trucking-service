import React from "react";
import axios from "axios";
import ItemTable from "./ItemTable";

class Offers extends React.Component {

    constructor(props) {
        super(props);
        this.state = {offers: []};
    }

    getOffers(orderId) {
        axios.get('/api/client/offers?orderId=' + orderId)
            .then((response) => {
                console.log(response)
                this.setState({ offers: response.data._embedded.offerDatas })
            })
            .catch((error) => {
                this.setState({ offers: [] })
                console.log(error);
            })
    }

    render() {
        return (
            <div>

                <div className="truckingitemlist">
                    <h2>List of offers</h2>
                    <OfferFilter
                        handleSubmit={(orderId) => {this.getOffers(orderId)}}
                    />
                    <OfferTable
                        offers={this.state.offers}
                    />
                </div>

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

class OfferTable extends React.Component {

    render() {
        const header = [
            'Identificator', 'Order Id',
            'Driver Id', 'Client id',
            'Price', 'Accepted'
        ]

        const data = this.props.offers.map((o) => {
            return {
                key: o.id,
                values: [
                    o.id, o.orderId,
                    o.driverId, o.clientId,
                    o.price,
                    o.accepted ? 'true' : 'false'
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

export default Offers;