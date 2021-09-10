import React, { Component } from "react";
import Contacts from "apih5/pages/contacts"
class ContactsPage extends Component {
    render() {
        return (
            <Contacts
                //左侧树节点按需加载
                //leftTreeDemandLoading
                //左侧树节点按需加载de接口
                //leftTreeDemandLoadingApiName="getSysDepartmentCurrentTree"
                {...this.props}
            />
        );
    }
}

export default ContactsPage;
