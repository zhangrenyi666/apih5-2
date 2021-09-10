import React from "react";
import Apih5 from "qnn-apih5"
import FlowFormByMaterialManagementContract from './form';
class index extends Apih5 {
    constructor(props) {
        super(props);
        this.state = {
            isFormStatus: false,
            messageCon: ''
        }
        this.props.myFetch('checkRequiredTab', { extensionHistoryId: props.flowData.extensionHistoryId }).then(({ success, message }) => {
            if (success) {
                this.setState({
                    isFormStatus: true
                })
            } else {
                this.setState({
                    isFormStatus: false,
                    messageCon: message
                })
            }
        })
    }
    render() {
        return (
            <div>
                {/* {
                    this.state.isFormStatus ?
                        <div>
                            <FlowFormByMaterialManagementContract  {...this.props} />
                        </div> :
                        <div>{this.state.messageCon}</div>
                } */}
                <FlowFormByMaterialManagementContract  {...this.props} />
            </div>
        )
    }
}
export default index