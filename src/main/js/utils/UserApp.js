import * as React from "react";
import {Link, Redirect, Route, Switch} from "react-router-dom";

class UserApp extends React.Component {

    constructor(props) {
        super(props);
    }

    renderLinks() {
        return (
            <div>
                You are logged in as {this.props.role}:
                {this.props.pages.map((p) => {
                    return <Link to={`${this.props.role}/${p.path}`}>{p.name}</Link>
                })}
                <a href="/logout">LogOut</a>
                <hr />
            </div>
        )
    }

    renderBody() {
        return (
            <div>
                <Switch>
                    <Route exact path={`/${this.props.role}`}>
                        <Redirect to={`/${this.props.role}/${this.props.defaultPath}`} />
                    </Route>
                    {this.props.pages.map((p) => {
                        return (
                            <Route path={`${this.props.role}/${p.path}`}>
                                {p.component}
                            </Route>
                        )
                    })}
                </Switch>
            </div>
        )
    }

    render() {
        return (
            <>
                {this.renderLinks()}
                {this.renderBody()}
            </>
        )
    }
}