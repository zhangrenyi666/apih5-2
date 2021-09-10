import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
class index extends Component {
    render() {
        // const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...window._ProjectManage}
                    componentsKey={{
                        component1:() => {
                            return (
                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                    项目基础信息
                            </div>
                            );
                        },
                        component2:() => {
                            return (
                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                    项目机构性质
                            </div>
                            );
                        },
                        component3:() => {
                            return (
                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                    项目关键时间
                            </div>
                            );
                        }
                    }}
                />
            </div>
        );
    }
}

export default index;