import React, { Component } from 'react';
import { createForm } from 'rc-form';
import { goBack } from 'connected-react-router'
import { Toast } from 'antd-mobile';
import { FlowSelectModal, clickFlowButton } from "../flow";
class FlowSelectModalPage extends Component {
    constructor(props) {
        super(props);
        // getSubmitData
        // hiddenDialog
        // closeActivity
        // showToast
        //getStarFlowData//获取流程数据
    }
    componentWillMount() {
        // const { myPublic: { androidApi } } = this.props; 
        // androidApi && androidApi.showToast("123456"); 

        document.querySelector("#root").style.background = "transparent"
    }
    componentDidMount() { 
        this.openFlow()
    }
    
    openFlow = () => {
        const { dispatch } = this.props;  
        

        





        const updateDataStr = androidApi ? androidApi.getUpdateData() : JSON.stringify({})
        const updateData = JSON.parse(updateDataStr);
        const { apiFlowId, workId, nodeVars, flowVars, flowNode: { nodeId, trackId }, apiName, apiBody, apiType, opinionContent, title } = updateData
        this.rootBody = {
            selectAuthorNewFlag: "1",
            nodeVars,
            flowVars,
            workId,
            nodeId,
            trackId,
            apiName,
            apiBody,
            apiType,
            opinionContent,
            title,
            apiFlowId
        }
        const actionDataStr = androidApi ? androidApi.getActionData() : JSON.stringify({}); 
        // androidApi.showToast(actionDataStr)
        const actionData = JSON.parse(actionDataStr) || {};
        clickFlowButton(actionData, {
            dispatch,
            actionFlow: this.actionFlow,
            onCancel:() => { if (androidApi) { androidApi.hiddenDialog() } },
            form:this.props.form
        })

    }
    componentWillUnmount() {
        document.querySelector("#root").style.background = ""
    }
    actionFlow = (actionData) => {
        const { myFetch, form, dispatch, myPublic: { androidApi } } = this.props
        const { setFieldsValue, getFieldValue } = form;
        const operateObj = getFieldValue("operateObj")
        let curNextNodes = getFieldValue("curNextNodes")
        const { operateObj: newOperateObj = {}, actionNextNodes = [], selectId, visible = true, close = false } = actionData
        const actionBody = { 
            ...this.rootBody,
            actionData: JSON.stringify({
                ...operateObj,
                ...newOperateObj,
                nextNodes: actionNextNodes,
            }),
        }
        Toast.loading("loading...", 0)
        myFetch("actionFlow", actionBody).then(({ success, message, data }) => {
            Toast.hide()
            if (success) {
                const { operate, operateClazz, operateText, operateFlag, nextNodes: callbackNextNodes } = data
                if (!selectId) {
                    curNextNodes = callbackNextNodes
                } else {
                    curNextNodes = actionNextNodes.map((item, index) => {
                        if (item.selectId === selectId) {
                            for (let i = 0; i < callbackNextNodes.length; i++) {
                                if (callbackNextNodes[i].selectId == selectId) {
                                    item.authors = callbackNextNodes[i].authors
                                    break;
                                }
                            }
                        }
                        return item
                    })
                }
                if (close) {
                    if (androidApi) {
                        androidApi.closeStartActivity()
                    } else {
                        dispatch(goBack())
                    }
                } else {
                    setFieldsValue({
                        operateObj: {
                            operate,
                            operateClazz,
                            operateText,
                            operateFlag,
                        },
                        curNextNodes,
                        visible
                    })
                }
            } else {
                Toast.fail(message, 2, () => {
                    androidApi && androidApi.closeStartActivity()
                });
            }
        })
    }
    render() {
        const { getFieldDecorator } = this.props.form;
        const { myPublic: { androidApi } } = this.props
        getFieldDecorator("operateObj", { initialValue: {} })
        getFieldDecorator("visible", { initialValue: false })
        getFieldDecorator("curNextNodes", { initialValue: [] })
        return (
            <div style={{ height: "100vh" }}> 
                <FlowSelectModal {...this.props} actionFlow={this.actionFlow} onCancel={() => { if (androidApi) { androidApi.hiddenDialog() } }} />
            </div>
        )

    }
}
export default createForm()(FlowSelectModalPage)