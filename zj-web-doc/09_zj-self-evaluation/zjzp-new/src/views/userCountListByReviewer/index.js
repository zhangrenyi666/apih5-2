import React, { Component } from "react";

class index extends Component { 
    render() { 
        const { loginAndLogoutInfo={} } = this.props;
        const { loginInfo } = loginAndLogoutInfo;
        let token = "";
        if(loginInfo){ 
            token = loginInfo.token
        }
        return (
            <div>
                <iframe
                    style={{width:'100%', height:"100vh"}}
                    title="test"
                    // src={`http://weixin.fheb.cn:99/evaluation/yearNuclearByAudit.html?code=${token}`}
                    src={`http://192.168.3.18:8100/evaluation/yearNuclear.html?code=${token}`}
                    frameBorder="0"
                />
            </div>
        );
    }
}

export default index;
