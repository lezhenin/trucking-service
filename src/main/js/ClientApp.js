import React from "react";
import {
    Route,
    Switch,
    Link,
    Redirect
} from 'react-router-dom';

import OrderPage from './order/OrderPage'
import OfferPage from "./offers/OfferPage";
import ContractPage from './contracts/ContractPage'

class ClientApp extends React.Component {
    render() {

        const apiUrl = "/api/client"

        const orderActions = {
            create: true,
            delete: true
        }

        const offerActions = {
            accept: true,
            create: false
        }

        return (
                <div>
                You are logged in as client:
                <div className="hlist">
                    <ul>
                        <li><Link to="/client/orders">Orders</Link></li>
                        <li><Link to="/client/offers">Offers</Link></li>
                        <li><Link to="/client/contracts">Contracts</Link></li>
                    </ul>
                </div>
                <hr />
                <Switch>
                    <Route exact path='/client'>
                        <Redirect to={'/client/orders'} />
                    </Route>
                    <Route path='/client/orders'>
                        <OrderPage
                            collectionUrl={`${apiUrl}/orders`}
                            actions={orderActions}
                        />
                    </Route>
                    <Route path='/client/offers'>
                        <OfferPage
                            collectionUrl={`${apiUrl}/offers`}
                            actions={offerActions}
                        />
                    </Route>
                    <Route path='/client/contracts'>
                        <ContractPage
                            collectionUrl={`${apiUrl}/contracts`}
                        />
                    </Route>
                </Switch>
                </div>
        )
    }
}

export default ClientApp;