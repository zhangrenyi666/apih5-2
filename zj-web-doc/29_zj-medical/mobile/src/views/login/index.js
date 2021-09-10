import React,{ Component } from "react"; 
import Login from "apih5/pages/login"
class LoginPage extends Component {
    render() {
        return (
            <Login 
                {...this.props}
                getBGUrl={(name) => {
                    if (name.indexOf('http://') >= 0 || name.indexOf('https://') >= 0) {
                        return name;
                    } else {
                        return require(`./img/${name}`);
                    }
                }}
            />
        );
    }
}
 
export default LoginPage;
