import React from 'react';
import ReactDOM from 'react-dom';

import Orders from './orders'
import Contracts from './contracts'

import ClientApp from "./ClientApp";

import { Route, Switch, Redirect, Link } from 'react-router-dom';

import { BrowserRouter } from 'react-router-dom';

class App extends React.Component {
    render() {
        return (
            <BrowserRouter basename="/react/client/index">
                <div>
                You are logged in as client:
                <div className="hlist">
                    <ul>
                        <li><Link to="/">Home</Link></li>
                        <li><Link to="/orders">Orders</Link></li>
                        <li><Link to="/contracts">Contracts</Link></li>
                    </ul>
                </div>
                <hr />
                <Switch>
                    <Route exact path='/' component={Orders} />
                    <Route path='/orders' component={Orders} />
                    <Route path='/contracts' component={Contracts} />
                </Switch>
                </div>
            </BrowserRouter>
        )
    }
}

ReactDOM.render(
    <ClientApp />,
    document.getElementById('react')
)