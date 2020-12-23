import React from "react";

import ContractTable from "./ContractTable";

import axios from "axios";
import OrderForm from "../order/OrderForm";
import ContractForm from "./ContractForm";


class ContractPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {contracts: []};
    }

    getContracts() {
        axios.get(this.props.collectionUrl)
            .then((response) => {
                this.setState({ contracts: response.data._embedded.contractDatas })
            })
            .catch((error) => {
                const message = error.response.data
                alert(`Error: ${message}`);
            })
    }

    deleteContract(id) {
        axios.delete(`${this.props.collectionUrl}/${id}`)
            .then((response) => {
                const contracts = this.state.contracts.filter((c) => c.id !== id)
                this.setState({ contracts: contracts })
            })
            .catch((error) => {
                const message = error.response.data
                alert(`Error: ${message}`);
            })
    }

    postContract(contract) {
        axios.post(this.props.collectionUrl, contract)
            .then((response) => {
                const contracts = this.state.contracts.concat(response.data)
                this.setState({ contracts: contracts })
            })
            .catch((error) => {
                const message = error.response.data
                alert(`Error: ${message}`);
            })
    }

    approveContract(id) {
        axios.get(`${this.props.collectionUrl}/${id}/approve`)
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
            .catch((error) => {
                const message = error.response.data
                alert(`Error: ${message}`);
            })
    }

    refuseContract(id) {
        axios.get(`${this.props.collectionUrl}/${id}/refuse`)
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
            .catch((error) => {
                const message = error.response.data
                alert(`Error: ${message}`);
            })
    }

    completeContract(id) {
        axios.get(`${this.props.collectionUrl}/${id}/complete`)
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
            .catch((error) => {
                const message = error.response.data
                alert(`Error: ${message}`);
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
                        actions={this.props.actions}
                        handleDelete={(id) => {this.deleteContract(id)}}
                        handleApprove={(id) => {this.approveContract(id)}}
                        handleRefuse={(id) => {this.refuseContract(id)}}
                        handleComplete={(id) => {this.completeContract(id)}}
                    />
                </div>

                {this.props.actions.create &&
                <div>
                    <h2>Create new contract</h2>
                    <ContractForm
                        handleCreate={(c) => {this.postContract(c)}}
                    />
                </div>
                }

            </div>
        )
    }
}


export default ContractPage;