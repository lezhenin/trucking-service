import React from "react";
import axios from "axios";
import ItemTable from "./ItemTable";

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

    render() {

        const header = [
            'Contract Id', 'Manager Id', 'Order Id',
            'Client Id', 'Client status', 'Driver Id', 'Driver Status',
            'Payment', 'Actions'
        ]

        const data = this.props.contracts.map((c) => {
            return {
                key: c.id,
                values: [
                    c.id, c.managerId, c.orderId,
                    c.clientId, c.clientStatus,
                    c.driverId, c.driverStatus,
                    c.payment,
                    <>
                        <button onClick={() => this.props.handleApprove(c.id)}>A</button>
                        <button onClick={() => this.props.handleRefuse(c.id)}>R</button>
                        <button onClick={() => this.props.handleComplete(c.id)}>C</button>
                    </>,
                ]
            }
        })

        return (
            <ItemTable
                header={header}
                data={data}
            />
        )
    }
}


export default Contracts;