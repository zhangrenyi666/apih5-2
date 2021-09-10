import React,{ Component } from "react"; 
import FlowType from "apih5/pages/FlowType"
class FlowTypePage extends Component {
    render() {
        return (
            <FlowType 
                {...this.props}
            />
        );
    }
}
 
export default FlowTypePage;
