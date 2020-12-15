import React from "react";

class OfferForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            price: 1000,
            orderId: 0,
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
        console.log(this.state)
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
                        <label>Price:</label>
                        <input
                            name="price"
                            type="number"
                            value={this.state.price}
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

export default OfferForm;