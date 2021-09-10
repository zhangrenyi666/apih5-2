import React,{ Component } from "react"; 
import BaseCode from "apih5/pages/BaseCode"
class BaseCodePage extends Component {
    render() {
        return (
            <BaseCode 
                {...this.props}
            />
        );
    }
}
 
export default BaseCodePage;
