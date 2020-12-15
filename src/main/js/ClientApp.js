import React from "react";
import { Route, Switch, Link, BrowserRouter } from 'react-router-dom';

import Orders from './orders'
import Offers from "./offers";
import Contracts from './contracts'

class ClientApp extends React.Component {
    render() {
        return (
                <BrowserRouter basename="/react/client/index">
                <div>
                You are logged in as client:
                <div className="hlist">
                    <ul>
                        <li><Link to="/">Home</Link></li>
                        <li><Link to="/orders">Orders</Link></li>
                        <li><Link to="/offers">Offers</Link></li>
                        <li><Link to="/contracts">Contracts</Link></li>
                    </ul>
                </div>
                <hr />
                <Switch>
                    <Route exact path='/' component={Orders} />
                    <Route path='/orders' component={Orders} />
                    <Route path='/offers' component={Offers} />
                    <Route path='/contracts' component={Contracts} />
                </Switch>
                </div>
            </BrowserRouter>
        )
    }
}

export default ClientApp;