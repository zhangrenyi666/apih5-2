import React,{ Component } from "react"; 
import Roles from "apih5/pages/roles"
class RolesPage extends Component {
    render() {
        return (
            <Roles 
                {...this.props}
            />
        );
    }
}
 
export default RolesPage;
