import * as React from "react";

class ItemTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            items: []
        }
    }

    // addItem(item) {
    //     const items = this.state.contracts.concat(item)
    //     this.setState({ items: items })
    // }
    //
    // updateItem(item) {
    //     const items = this.state.items.map((i) => {
    //         if (i.id === item.id) {
    //             return item
    //         } else {
    //             return i
    //         }
    //     })
    //     this.setState({ items: items })
    // }
    //
    // removeItem(id) {
    //     const items = this.state.items.filter((i) => i.id !== id)
    //     this.setState({ items: items })
    // }

    renderHeader() {
        const columns = this.props.header.map((f, id) => {
            return <th key={columns}>{f}</th>
        })
        return (
            <tr>
                {columns}
            </tr>
        )
    }

    renderBody() {
        return this.state.items.map(i => {
            const row = this.props.row(i).map((value, id) => {
                return <th key={id}>{value}</th>
            })
            return (
                <tr key={i.id}>
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