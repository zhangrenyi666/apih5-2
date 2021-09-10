import React, { Component } from 'react';
import { goBack } from 'connected-react-router'
import { Flex, NavBar, Icon, Toast } from 'antd-mobile';
import { Form } from 'antd'
import moment from 'moment';
import { FlowSelectModal, actionFlow, clickFlowButton, FlowButtonGroup, FlowForm } from "../flow";
import styles from "./style.less";

class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            flowFormColumn: [],
            flowButtons: [],
            formConfig: this.props.formConfig,//表单项配置
            workId: this.props.match.params.workId, //为add即是新增流程  为其他则是从列表点击进入的
            apiNameByAdd: this.props.apiNameByAdd,
            apiNameByUpdate: this.props.apiNameByUpdate,
            apiNameByGet: this.props.apiNameByGet,
            title: this.props.title || 'title',//流程标题字段title
        }
        this.rootBody = {
            selectAuthorNewFlag: "1",
        }
        this.actionFlow = actionFlow.bind(this)
        this.clickFlowButton = clickFlowButton.bind(this)
    }
    updateRootBody = (newRootBody) => {
        this.rootBody = {
            ...this.rootBody,
            ...newRootBody,
        }
    }
    componentDidMount() {
        // const { getFieldDecorator } = this.props.form; 
        // getFieldDecorator("operateObj", { initialValue: {} })
        // getFieldDecorator("curNextNodes", { initialValue: [] })

        const { workId } = this.state;
        if (workId === 'add') {
            this.openFlow();
        } else {
            //处理 
            this.openFlowByPre()
        }

    }
    openFlow() {
        //新增
        //需要将optionConent 删除
        const { formConfig } = this.state;
        const _column = formConfig.filter((item) => {
            let { addShow = true } = item;
            return addShow;
        })
        this.setState({
            flowFormColumn: _column,
            flowButtons: [{ buttonClass: "createOpenFlow" }]
        })
    }

    openFlowByPre() {//处理
        const { formConfig, apiNameByGet, apiNameByUpdate } = this.state;
        const _column = formConfig.map((item) => {
            item.disabled = true;
            return item;
        })

        const { myFetch, form, match: { params: { flowId = "", workId = "", title } } } = this.props
        const { setFieldsValue } = form;
        const body = {
            flowId,
            workId,
            apiName: apiNameByGet,
            apiType: 'POST',
            title:window.decodeURI(title)
        }
        Toast.loading("正在加载数据")
        myFetch("openFlow", body).then(({ success, data }) => {
            Toast.hide()
            if (success) {
                const { apiData: apiDataStr, nodeVars, flowVars, flowNode: { nodeId, trackId }, flowButtons } = data
                const apiData = apiDataStr ? JSON.parse(apiDataStr) : {}
                this.updateRootBody({
                    workId,
                    flowId,
                    nodeId,
                    trackId,
                    nodeVars,
                    flowVars,
                    apiName: apiNameByUpdate,
                    apiType: "POST",
                    title:window.decodeURI(title),
                })
                const apiBody = {}
                const opinions = flowVars.opinionFieldName && flowVars.opinionFieldName.split("_").map((item) => {
                    let itemArr = item.split("|")
                    return {
                        dataIndex: itemArr[0],
                        type: "textarea",
                        label: itemArr[1],
                        disabled: true
                    }
                })
                let flowFormColumn;
                if (opinions) {
                    flowFormColumn = [].concat([], _column, opinions)
                } else {
                    flowFormColumn = [].concat([], _column)
                }
                // let flowFormColumn = [].concat([], _column, opinions)
                flowFormColumn = flowFormColumn.map((item) => {
                    let { dataIndex, type, disabled } = item;
                    if (type === 'date') {
                        //格式化为moent格式  
                        apiBody["apiBody." + dataIndex] = apiData[dataIndex] ? moment(apiData[dataIndex]) : moment('1970');
                    } else {
                        apiBody["apiBody." + dataIndex] = apiData[dataIndex];
                    }

                    disabled = type !== "upload" ? disabled : (nodeVars && nodeVars.fileOperationFlag === "1" ? false : true)

                    return {
                        ...item,
                        disabled
                    }
                })
                if (nodeVars && nodeVars.opinionField) {
                    flowFormColumn.push({
                        dataIndex: "opinionContent",
                        type: "textarea",
                        label: "您的意见",
                        placeholder: '请输入...'
                    })
                }
                this.setState({
                    flowFormColumn,
                    flowButtons
                }, () => {
                    setFieldsValue({
                        ...apiBody
                    })
                })
            }
        })
    }

    isMobile = () => {
        if (navigator.userAgent.match(/Android/i)
            || navigator.userAgent.match(/webOS/i)
            || navigator.userAgent.match(/iPhone/i)
            || navigator.userAgent.match(/iPad/i)
            || navigator.userAgent.match(/iPod/i)
            || navigator.userAgent.match(/BlackBerry/i)
            || navigator.userAgent.match(/Windows Phone/i)
        ) {
            return true;
        }
        else {
            return false;
        }
    }
    createOpenFlow = () => {
        const { apiNameByUpdate, apiNameByAdd, title } = this.state;
        const { myFetch, form, match: { params: { flowId = "" } }, formConfig } = this.props
        const { validateFields } = form
        validateFields((err, values) => {
            //将moment时间格式处理为时间戳
            let _apiBody = values["apiBody"];
            let _title = '';
            if (Array.isArray(title)) {
                //是数组需要拼接
                for (let i = 0; i < title.length; i++) {
                    let _tit = _apiBody[title[i]] || title[i];
                    _title += `${_tit}  `
                }
            }else{
                _title =  _apiBody[title] || title;
            }

            for (let i = 0, j = formConfig.length; i < j; i++) {
                if (formConfig[i].type === 'date') {
                    if (_apiBody[formConfig[i].dataIndex]) {
                        let _dataIndex = formConfig[i].dataIndex;
                        _apiBody[_dataIndex] = moment(_apiBody[_dataIndex]).valueOf()
                    }
                }
            }
            values["apiBody"] = _apiBody;
            if (!err) {
                const body = {
                    title: _title,
                    flowId,
                    apiNameByCreate: apiNameByUpdate,
                    apiName: apiNameByAdd,
                    apiType: "POST",
                    apiBody: JSON.stringify(values["apiBody"])
                } 
                Toast.loading("正在创建流程", 0);
                myFetch("createOpenFlow", body).then(({ success, message, data }) => {
                    Toast.hide()
                    if (success) {
                        Toast.success("创建流程成功")
                        const { workId, nodeVars, flowVars, flowNode: { nodeId, trackId }, flowButtons, apiName, apiType, apiBody, title } = data
                        this.updateRootBody({
                            nodeVars,
                            flowId, 
                            flowVars,
                            workId,
                            nodeId,
                            trackId,
                            apiName,
                            apiType,
                            apiBody,
                            title:_title
                        })
                        const { buttonId, buttonClass, buttonName } = flowButtons[0]
                        const operateObj = {
                            operate: buttonId,
                            operateClazz: buttonClass,
                            operateText: buttonName,
                            operateFlag: 1
                        }
                        this.actionFlow({ operateObj })
                    } else {
                        Toast.fail(message, 2);
                    }
                })
            }
        })
    }

    render() {
        const { dispatch } = this.props
        const { flowButtons, flowFormColumn } = this.state;
        if (this.isMobile()) {
            return (
                <Flex className={styles.root} direction={"column"}>
                    {
                        this.isMobile() ? <NavBar
                            style={{ width: "100%" }}
                            mode="dark"
                            icon={<Icon type="left" />}
                            onLeftClick={() => {
                                dispatch(goBack())
                            }}
                        >{"审批申请"}</NavBar> : null
                    }
                    <FlowForm {...this.props} updateRootBody={this.updateRootBody} flowFormColumn={flowFormColumn} />
                    <FlowButtonGroup {...this.props} clickFlowButton={this.clickFlowButton} createOpenFlow={this.createOpenFlow} flowButtons={flowButtons} />
                    <FlowSelectModal {...this.props} actionFlow={this.actionFlow} />
                </Flex >
            )
        } else {
            return (
                <div className={styles.pcroot}>
                    {

                        this.isMobile() ? <NavBar
                            style={{ width: "100%" }}
                            mode="dark"
                            icon={<Icon type="left" />}
                            onLeftClick={() => {
                                dispatch(goBack())
                            }}
                        >{"审批申请"}</NavBar> : null
                    }
                    <FlowForm {...this.props} updateRootBody={this.updateRootBody} flowFormColumn={flowFormColumn} />
                    <FlowButtonGroup {...this.props} clickFlowButton={this.clickFlowButton} createOpenFlow={this.createOpenFlow} flowButtons={flowButtons} />
                    <FlowSelectModal {...this.props} actionFlow={this.actionFlow} />
                </div>
            )
        }

    }
}

const WrappedDynamicRule = Form.create()(Home);
export default WrappedDynamicRule 