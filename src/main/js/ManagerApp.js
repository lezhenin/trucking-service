import React from "react";
import {
    Route,
    Switch,
    Link,
    Redirect, NavLink
} from 'react-router-dom';

import OrderPage from './order/OrderPage'
import OfferPage from "./offers/OfferPage";
import ContractPage from './contracts/ContractPage'
import DriverPage from "./drivers/DriverPage";

class ManagerApp extends React.Component {

    render() {

        console.log("mamaget")

        const apiUrl = "/api/manager"

        const orderActions = {
            create: false,
            delete: false
        }

        const offerActions = {
            accept: false,
            create: false
        }

        const contractActions = {
            create: true,
            delete: true,
            approve: false,
            refuse: false,
            complete: true
        }

        return (
            <div>

                <div className="lline">
                    You are logged in as manager:
                    <Link to="/manager/orders">Orders</Link>
                    <Link to="/manager/offers">Offers</Link>
                    <Link to="/manager/contracts">Contracts</Link>
                    <Link to="/manager/drivers">Drivers</Link>
                    <a href="/logout">LogOut</a>
                </div>

                <hr />

                <Switch>
                    <Route exact path='/manager'>
                        <Redirect to={'/manager/contracts'} />
                    </Route>
                    <Route path='/manager/orders'>
                        <OrderPage
                            collectionUrl={`${apiUrl}/orders`}
                            actions={orderActions}
                        />
                    </Route>
                    <Route path='/manager/offers'>
                        <OfferPage
                            collectionUrl={`${apiUrl}/offers`}
                            actions={offerActions}
                        />
                    </Route>
                    <Route path='/manager/contracts'>
                        <ContractPage
                            collectionUrl={`${apiUrl}/contracts`}
                            actions={contractActions}
                        />
                    </Route>
                    <Route path='/manager/drivers'>
                        <DriverPage
                            collectionUrl={`${apiUrl}/drivers`}
                        />
                    </Route>
                </Switch>
            </div>
        )
    }
}

export default ManagerApp;