import React from "react";

class OfferFilter extends React.Component {

    constructor(props) {
        super(props);
        this.state = {orderId: 0}
    }

    handleSubmit(event) {
        console.log(this.state)
        event.preventDefault();
        this.props.handleSubmit(this.state.orderId)
    }

    handleChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;
        this.setState({
            [name]: value
        });

        console.log(this.state)
    }

    render() {
        return (
            <form onSubmit={(e) => this.handleSubmit(e)}>
                <div className="ofilter">
                    <label>Order Id:</label>
                    <input
                        name="orderId"
                        type="number"
                        value={this.state.orderId}
                        onChange={(e) => this.handleChange(e)}
                    />
                    <button type="submit">Search offers</button>
                </div>
            </form>
        )
    }
}

export default OfferFilter;