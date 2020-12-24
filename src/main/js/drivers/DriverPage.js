import React from "react";

import axios from "axios";
import DriverTable from "./DriverTable";

class DriverPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            drivers: []
        };
    }

    getDrivers() {
           axios.get(this.props.collectionUrl)
               .then((response) => {
                   console.log(response)
                   this.setState({ drivers: response.data._embedded.driverDatas })
               })
               .catch((error) => {
                   const message = error.response.data
                   alert(`Error: ${message}`);
               })
    }

    componentDidMount() {
        this.getDrivers()
    }

    render() {
        return (
            <div>

                <div className="truckingitemlist">
                    <h2>List of drivers</h2>
                    <DriverTable
                        drivers={this.state.drivers}
                    />
                </div>

            </div>
        )
    }
}

export default DriverPage;