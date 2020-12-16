import React from "react";

import ItemTable from "../utils/ItemTable";

class DriverTable extends React.Component {

    makeHeader() {
        return [
            'Driver Id', 'First Name', 'Last Name',
            'Vehicle model', 'Max cargo weight', 'Max cargo size'
        ]
    }

    makeData() {
        return this.props.drivers.map((d) => {
            const v = d.vehicle
            return {
                key: d.id,
                values: [
                    d.id, d.firstName, d.lastName, v.model, v.maxCargoWeight,
                    `(${v.maxCargoLength}, ${v.maxCargoWidth}, ${v.maxCargoHeight})`
                ]
            }
        })
    }

    render() {
        return (
            <ItemTable
                header={this.makeHeader()}
                data={this.makeData()}
            />
        )
    }
}

export default DriverTable;