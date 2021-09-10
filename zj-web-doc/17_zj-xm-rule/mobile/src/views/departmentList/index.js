import React, { Component } from "react";
import MyList from "../modules/MList";
import { NavBar } from "antd-mobile";
import { push } from 'connected-react-router';
import s from './style.less';
class Index extends Component {
    render() {
        const { dispatch, myPublic:{ appInfo:{ mainModule } } } = this.props;
        return (
            <div className={s.root}>
                <NavBar
                    style={{ width: "100%" }}
                    mode="dark"
                >
                    部门列表
                </NavBar>
                <div
                    style={{
                        height: document.documentElement.clientHeight - 45
                    }}
                >
                    <MyList
                        loginAndLogoutInfo={this.props.loginAndLogoutInfo}
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        searchKey="objectName"
                        fetchConfig={{
                            apiName: 'getZjXmOaDepartmentListForWeChat'
                        }}
                        Item={props => {
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            return (
                                <div
                                    className={s.center}
                                    key={index}
                                    style={{
                                        borderLeft: `3px solid ${index % 2 === 0 ? "#ff4000" : "#ff9900"}`
                                    }}
                                    onClick={() => {
                                        dispatch(push(`${mainModule}Classification/${item.objectUserKey}/0`))
                                    }}
                                >
                                    <div className={s.top}>{item.objectName}</div>
                                </div>
                            );
                        }}
                    />
                </div>
            </div>
        )
    }
}
export default Index;