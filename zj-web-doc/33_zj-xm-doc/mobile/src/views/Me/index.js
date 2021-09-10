import React,{ Component } from "react"; 
import Me from "apih5/pages/Me"
class MePage extends Component {
    render() {
        return (
            <Me 
                {...this.props}
            />
        );
    }
}
 
export default MePage;
