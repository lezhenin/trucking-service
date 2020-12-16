import React from "react";
import {
    Link,
    Route,
    Switch,
    Redirect
} from 'react-router-dom';

import OrderPage from './order/OrderPage'
import OfferPage from "./offers/OfferPage";
import ContractPage from './contracts/ContractPage'

class DriverApp extends React.Component {
    render() {

        const apiUrl = "/api/driver"

        const orderActions = {
            create: false,
            delete: false
        }

        const offerActions = {
            accept: false,
            create: true
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
                    You are logged in as driver:
                    <Link to="/driver/orders">Orders</Link>
                    <Link to="/driver/offers">Offers</Link>
                    <Link to="/driver/contracts">Contracts</Link>
                </div>

                <hr />

                <Switch>
                    <Route exact path='/driver'>
                        <Redirect to={'/driver/orders'} />
                    </Route>
                    <Route exact path='/driver/orders'>
                        <OrderPage
                            collectionUrl={`${apiUrl}/orders`}
                            actions={orderActions}
                            viewOptions={['accepted', 'available']}
                        />
                    </Route>
                    <Route path='/driver/offers'>
                        <OfferPage
                            collectionUrl={`${apiUrl}/offers`}
                            actions={offerActions}
                        />
                    </Route>
                    <Route path='/driver/contracts'>
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

export default DriverApp;