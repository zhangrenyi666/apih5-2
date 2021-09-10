import React,{ Component } from "react"; 
import Add from "apih5/pages/Add"
class AddPage extends Component {
    render() {
        return (
            <Add 
                {...this.props}
            />
        );
    }
}
 
export default AddPage;
