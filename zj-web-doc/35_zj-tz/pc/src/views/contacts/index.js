import React, { Component } from "react";
import Contacts from "apih5/pages/contacts"
class ContactsPage extends Component {
    render() {
        return (
            <Contacts
                //左侧树节点按需加载
                // leftTreeDemandLoading
                //左侧树节点按需加载de接口
                // leftTreeDemandLoadingApiName="getSysDepartmentCurrentTree"
                // 左侧树节点 解除右键菜单
                removeTreeNodeRightMenu
                {...this.props}
            />
        );
    }
}

export default ContactsPage;