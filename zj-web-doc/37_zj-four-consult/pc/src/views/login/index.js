import React,{ Component } from "react";
import Login from "../logins"
import User from '../../apih5_modules/User';

class LoginPage extends Component {

    componentDidMount() {

        const {
            myPublic,
            loginAndLogoutInfo: { logoutInfo }, 
        } = this.props;
        const { appInfo } = myPublic;
        let { loginType } = appInfo;
        if (logoutInfo) {
            loginType = logoutInfo.loginType;
        }

        let loginByWwLogin = User.loginByWwLogin.bind(this);
        if (loginType === "5") {
            loginByWwLogin("form"); 
        }
    }

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
