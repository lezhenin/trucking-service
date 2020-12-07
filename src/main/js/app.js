import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';

import '../resources/static/css/stsm.css'

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {orders: []};
    }

    componentDidMount() {
        console.log("mount")
        axios.get('/api/client/orders')
            .then((response) => {
                console.log(this)
                console.log(response.data);
                this.setState({
                    orders: response.data
                })
            })
            .catch(function (error) {
                // handle error
                console.log(error);
            })
            .then(function () {
                // always executed
            });
    }

    render() {
        return (
            <OrderList orders={this.state.orders}/>
        )
    }
}

class OrderList extends React.Component {
    render() {
        const orders = this.props.orders.map(o =>
            <Order key={o.id} order={o}/>
        );
        return (
            <table>
                <thead>
                <tr>
                    <th>Identificator</th>
                    <th>Cargo weight</th>
                    {/*<th>Cargo size</th>*/}
                    <th>Loading address</th>
                    <th>Shipping address</th>
                    <th>State</th>
                </tr>
                </thead>
                <tbody>
                {orders}
                </tbody>
            </table>
        )
    }
}

class Order extends React.Component{
    render() {
        return (
            <tr>
                <td>{this.props.order.id}</td>
                <td>{this.props.order.cargoWeight}</td>
                <td>{this.props.order.loadingAddress}</td>
                <td>{this.props.order.shippingAddress}</td>
                <td>{this.props.order.orderState}</td>
            </tr>
        )
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('react')
)