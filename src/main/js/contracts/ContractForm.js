import React from "react";

class ContractForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            orderId: 0,
            driverId: 0,
            payment: 1000
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;
        this.setState({
            [name]: value
        });
    }

    handleSubmit(event) {
        event.preventDefault();
        this.props.handleCreate(this.state)
    }

    render() {
        return (

            <form onSubmit={(e) => this.handleSubmit(e)}>

                <fieldset>

                    <div>
                        <label>Order Id:</label>
                        <input
                            name="orderId"
                            type="number"
                            value={this.state.orderId}
                            onChange={(e) => this.handleChange(e)}
                        />
                    </div>

                    <div>
                        <label>Driver Id:</label>
                        <input
                            name="driverId"
                            type="number"
                            value={this.state.driverId}
                            onChange={(e) => this.handleChange(e)}
                        />
                    </div>

                    <div>
                        <label>Payment:</label>
                        <input
                            name="payment"
                            type="number"
                            value={this.state.payment}
                            onChange={(e) => this.handleChange(e)}
                        />
                    </div>

                    <div className="submit">
                        <button type="submit" name="create">Create Contract</button>
                    </div>

                </fieldset>

            </form>

        )
    }

}

export default ContractForm;