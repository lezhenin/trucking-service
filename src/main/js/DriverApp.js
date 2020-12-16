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

        return (
            <div>
                You are logged in as client:
                <div className="hlist">
                    <ul>
                        <li><Link to="/driver/orders">Orders</Link></li>
                        <li><Link to="/driver/offers">Offers</Link></li>
                        <li><Link to="/driver/contracts">Contracts</Link></li>
                    </ul>
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
                        <ContractPage collectionUrl={`${apiUrl}/contracts`} />
                    </Route>
                </Switch>
            </div>
        )
    }
}

export default DriverApp;