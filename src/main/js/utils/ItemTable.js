import * as React from "react";

class ItemTable extends React.Component {

    constructor(props) {
        super(props);
    }

    renderHeader() {
        const columns = this.props.header.map((f, id) => {
            return <th key={id}>{f}</th>
        })
        return (
            <tr>
                {columns}
            </tr>
        )
    }

    renderBody() {
        return this.props.data.map(data => {
            const row = data.values.map((value, id) => {
                return <td key={id}>{value}</td>
            })
            return (
                <tr key={data.key}>
                    {row}
                </tr>
            )
        });
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

export default ItemTable;