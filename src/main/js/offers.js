import React from "react";
import axios from "axios";

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

    renderHeader() {
        const fields = [
            'Identificator',
            'Order Id', 'Driver Id', 'Client id',
            'Price', 'Accepted'
        ].map((f, id) => { return <th key={id}>{f}</th> })
        return (
            <tr>
                {fields}
            </tr>
        )
    }

    renderBody() {
        return this.props.offers.map(o =>
            <tr key={o.id}>
                <td>{o.id}</td>
                <td>{o.orderId}</td>
                <td>{o.driverId}</td>
                <td>{o.clientId}</td>
                <td>{o.price}</td>
                <td>{o.accepted}</td>
            </tr>
        );
    }

    render() {

        return (
            <table>
                <thead>
                {this.renderHeader()}
                </thead>
                <tbody>
                {this.renderBody()}
                </tbody>
            </table>
        )
    }
}

export default Offers;