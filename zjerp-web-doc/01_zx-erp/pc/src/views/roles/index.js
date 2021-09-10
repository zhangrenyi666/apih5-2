import React, { Component } from "react";
import Roles from "apih5/pages/roles"
class RolesPage extends Component {
    render() {
        return (
            <Roles
                // 配置为 true 后。选择人员后将会去请求接口重新赋值
                pullPersonOnSaveReRender
                pullPersonK={{
                    value: 'userDeptId',
                }}
                {...this.props}
            />
        );
    }
}

export default RolesPage;
