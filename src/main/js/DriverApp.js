import React from "react";
import {
    Link,
    Route,
    Switch,
    Redirect
} from 'react-router-dom';

import Orders from './Orders'
import Offers from "./offers";
import Contracts from './Contracts'

class DriverApp extends React.Component {
    render() {

        const apiUrl = "/api/driver"

        const orderActions = {
            create: false,
            delete: false
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
                    <Route path='/driver/orders'>
                        <Orders
                            collectionUrl={`${apiUrl}/orders`}
                            actions={orderActions}
                        />
                    </Route>
                    <Route path='/driver/offers'>
                        <Offers collectionUrl={`${apiUrl}/offers`} />
                    </Route>
                    <Route path='/driver/contracts'>
                        <Contracts collectionUrl={`${apiUrl}/contracts`} />
                    </Route>
                </Switch>
            </div>
        )
    }
}

export default DriverApp;