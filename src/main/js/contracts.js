import React from "react";
import axios from "axios";

class Contracts extends React.Component {

    constructor(props) {
        super(props);
        this.state = {contracts: []};
    }

    getContracts() {
        axios.get('/api/client/contracts')
            .then((response) => {
                console.log(response)
                this.setState({ contracts: response.data._embedded.contractDatas })
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    deleteContract(id) {
        axios.delete('/api/client/contracts/' + id)
            .then((response) => {
                const contracts = this.state.contracts.filter((c) => c.id !== id)
                this.setState({ contracts: contracts })
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    postContract(contract) {
        axios.post('/api/client/contracts/', contract)
            .then((response) => {
                const contracts = this.state.contracts.concat(response.data)
                this.setState({ contracts: contracts })
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    approveContract(id) {
        axios.get('/api/client/contracts/' + id + '/approve')
            .then((response) => {
                console.log(response)
                const contracts = this.state.contracts.map((c) => {
                    if (c.id === id) {
                        return response.data
                    } else {
                        return c
                    }
                })
                this.setState({ contracts: contracts })
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    refuseContract(id) {
        axios.get('/api/client/contracts/' + id + '/refuse')
            .then((response) => {
                const contracts = this.state.contracts.map((c) => {
                    if (c.id === id) {
                        return response.data
                    } else {
                        return c
                    }
                })
                this.setState({ contracts: contracts })
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    completeContract(id) {
        axios.get('/api/client/contracts/' + id + '/complete')
            .then((response) => {
                const contracts = this.state.contracts.map((c) => {
                    if (c.id === id) {
                        return response.data
                    } else {
                        return c
                    }
                })
                this.setState({ contracts: contracts })
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    componentDidMount() {
        this.getContracts()
    }

    render() {
        return (
            <div>

                <div className="truckingitemlist">
                    <h2>List of contracts</h2>
                    <ContractTable
                        contracts={this.state.contracts}
                        handleDelete={(id) => {this.deleteContract(id)}}
                        handleApprove={(id) => {this.approveContract(id)}}
                        handleRefuse={(id) => {this.refuseContract(id)}}
                        handleComplete={(id) => {this.completeContract(id)}}
                    />
                </div>

            </div>
        )
    }
}

class ContractTable extends React.Component {

    renderHeader() {
        const fields = [
            'Contract Id', 'Manager Id', 'Order Id',
            'Client Id', 'Client status', 'Driver Id', 'Driver Status',
            'Payment', 'Actions'
        ].map((f, id) => { return <th key={id}>{f}</th> })
        return (
            <tr>
                {fields}
            </tr>
        )
    }

    renderBody() {
        return this.props.contracts.map(c =>
            <tr key={c.id}>
                <td>{c.id}</td>
                <td>{c.managerId}</td>
                <td>{c.orderId}</td>
                <td>{c.clientId}</td>
                <td>{c.clientStatus}</td>
                <td>{c.driverId}</td>
                <td>{c.driverStatus}</td>
                <td>{c.payment}</td>
                <td>
                    <button onClick={() => this.props.handleApprove(c.id)}>A</button>
                    <button onClick={() => this.props.handleRefuse(c.id)}>R</button>
                    <button onClick={() => this.props.handleComplete(c.id)}>C</button>
                </td>

            </tr>
        );
    }

    render() {

        return (
            <table>
                <thead>
                {this.renderHeader()}
                </thead>
                <tbody>
                {this.renderBody()}
                </tbody>
            </table>
        )
    }
}

export default Contracts;