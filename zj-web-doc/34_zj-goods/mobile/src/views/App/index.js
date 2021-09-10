import React,{ Component } from "react";
import App from "apih5/pages/App"
class AppPage extends Component {
    render() {
        return (
            <App {...this.props}
                content={() => {
                    return <div>内容</div>
                }}
            />
        );
    }
}

export default AppPage;
