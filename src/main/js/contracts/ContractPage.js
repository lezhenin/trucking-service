import React from "react";

import ContractTable from "./ContractTable";

import axios from "axios";


class ContractPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {contracts: []};
    }

    getContracts() {
        axios.get(this.props.collectionUrl)
            .then((response) => {
                console.log(response)
                this.setState({ contracts: response.data._embedded.contractDatas })
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    deleteContract(id) {
        axios.delete(`${this.props.collectionUrl}/${id}`)
            .then((response) => {
                const contracts = this.state.contracts.filter((c) => c.id !== id)
                this.setState({ contracts: contracts })
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    postContract(contract) {
        axios.post(this.props.collectionUrl, contract)
            .then((response) => {
                const contracts = this.state.contracts.concat(response.data)
                this.setState({ contracts: contracts })
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    approveContract(id) {
        axios.get(`${this.props.collectionUrl}/${id}/approve`)
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
            .catch(function (error) {
                console.log(error);
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


export default ContractPage;