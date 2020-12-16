import React from 'react';
import ReactDOM from 'react-dom';
// import BrowserRouter from "react-router-dom";
// import Switch from "react-router-dom";
// import Route from "react-router-dom";
// import Redirect from "react-router-dom";

import { Route, Switch, Redirect, BrowserRouter } from 'react-router-dom';

import ClientApp from "./ClientApp";
import DriverApp from "./DriverApp";
import ManagerApp from "./ManagerApp";

import axios from "axios";

class App extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            user: {
                username: '',
                role: ''
            }
        }
    }

    componentDidMount() {
        axios.get('/user')
            .then((response) => {
                console.log(response)
                this.setState({ user: {
                    username: response.data.username,
                        role: response.data.role
                }})
            })
            .catch((error) => {
                console.log(error);
            })
    }

    render() {

        let path = null;
        if (this.state.user.role === 'ROLE_CLIENT') {
            path = '/client'
        } else if (this.state.user.role === 'ROLE_DRIVER') {
            path = '/driver'
        } else if (this.state.user.role === 'ROLE_MANAGER') {
            path = '/manager'
        }

        console.log(this.state)
        console.log(path)

        return path && (
            <BrowserRouter>
                <Switch>
                    <Route exact path="/">
                        <Redirect to={path} />
                    </Route>
                    <Route path="/client">
                        <ClientApp />
                    </Route>
                    <Route path="/manager">
                        <ManagerApp />
                    </Route>
                    <Route path="/driver">
                        <DriverApp />
                    </Route>
                </Switch>
            </BrowserRouter>
            )
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('react')
)