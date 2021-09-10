import React, { Component } from "react";
import { NavBar, Icon } from "antd-mobile";
import { push } from 'react-router-redux';
import FlowFormByzjSjTechnicalServiceAdd from './formAdd';
class Index extends Component {
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}TechnicalServices/0`));
                        }}
                    >
                        {"技术服务申请"}
                    </NavBar>
                </div>
                <div style={{ height: document.documentElement.clientHeight - 45 }}>
                    <FlowFormByzjSjTechnicalServiceAdd {...this.props} />
                </div>
            </div>
        );
    }
}
export default Index;