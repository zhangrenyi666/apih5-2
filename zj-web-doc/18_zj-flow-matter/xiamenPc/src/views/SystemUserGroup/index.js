import React,{ Component } from "react"; 
import SystemUserGroupPage from "apih5/pages/SystemUserGroup"
class SystemUserGroup extends Component {
    render() {
        return (
            <SystemUserGroupPage 
                {...this.props}
            />
        );
    }
}
 
export default SystemUserGroup;
