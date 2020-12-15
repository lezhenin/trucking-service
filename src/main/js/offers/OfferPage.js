import React from "react";
import axios from "axios";

import OfferFilter from "./OfferFilter";
import OfferTable from "./OfferTable";
import OfferForm from "./OfferForm";

class OfferPage extends React.Component {

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

export default OfferPage;