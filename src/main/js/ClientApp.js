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

        const contractActions = {
            create: false,
            delete: false,
            approve: true,
            refuse: true,
            complete: true
        }

        return (
                <div>

                    <div>
                        You are logged in as client:
                        <Link to="/client/orders">Orders</Link>
                        <Link to="/client/offers">Offers</Link>
                        <Link to="/client/contracts">Contracts</Link>
                        <a href="/logout">LogOut</a>
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
                            actions={contractActions}
                        />
                    </Route>
                </Switch>
                </div>
        )
    }
}

export default ClientApp;