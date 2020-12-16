import React from "react";

class OrderForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            cargoWeight: 10,
            cargoWidth: 10,
            cargoLength: 10,
            cargoHeight: 10,
            loadingAddress: '',
            shippingAddress: '',
            deadline: 'yesterday'
        }
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

    handleSubmit(event) {
        console.log(this.state)
        event.preventDefault();
        this.props.handleCreate(this.state)
    }

    render() {
        return (

            <form onSubmit={(e) => this.handleSubmit(e)}>

                <fieldset>

                    <div>
                        <label>Cargo weight:</label>
                        <input
                            name="cargoWeight"
                            type="number"
                            value={this.state.cargoWeight}
                            onChange={(e) => this.handleChange(e)}
                        />
                    </div>

                    <div>
                        <label>Cargo size:</label>
                        <input
                            name="cargoWidth"
                            type="number"
                            size="2"
                            value={this.state.cargoWidth}
                            onChange={(e) => this.handleChange(e)}
                        />
                        <input
                            name="cargoLength"
                            type="number"
                            size="2"
                            value={this.state.cargoLength}
                            onChange={(e) => this.handleChange(e)}
                        />
                        <input
                            name="cargoHeight"
                            type="number"
                            size="2"
                            value={this.state.cargoHeight}
                            onChange={(e) => this.handleChange(e)}
                        />
                    </div>

                    <div>
                        <label>Loading Address:</label>
                        <input
                            name="loadingAddress"
                            type="text"
                            value={this.state.loadingAddress}
                            onChange={(e) => this.handleChange(e)}
                        />
                    </div>

                    <div>
                        <label>Shipping Address:</label>
                        <input
                            name="shippingAddress"
                            type="text"
                            value={this.state.shippingAddress}
                            onChange={(e) => this.handleChange(e)}
                        />
                    </div>

                    <div className="submit">
                        <button type="submit" name="create">Create Order</button>
                    </div>

                </fieldset>

            </form>

        )
    }

}

export default OrderForm;