import React,{ Component } from "react"; 
import Menus from "apih5/pages/menus"
class MenusPage extends Component {
    render() {
        return (
            <Menus 
                {...this.props}
            />
        );
    }
}
 
export default MenusPage;
