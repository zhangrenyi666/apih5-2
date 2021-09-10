import React, { Component } from 'react';
import { createForm, createFormField } from 'rc-form';
import { goBack } from 'connected-react-router' //, push
import { Picker, Modal, Toast, List, ActivityIndicator, Checkbox as Checkbox2, SegmentedControl, } from 'antd-mobile';
import { Radio, Select, Upload as AntdUpload, Icon, DatePicker as MDatePicker, InputNumber, Checkbox, Row, Col, Form, Input, Button, Cascader } from 'antd';
import moment from 'moment' 
import Upload from "../upload";
import PullPersionMobile from "../pullPersionMobile";
import PullPersion from "../pullPersion";
import styles from "./style/index.less";
moment.locale('zh-cn');
//联动下拉用的
function filter(inputValue, path) {
    return (path.some(option => (option.label).toLowerCase().indexOf(inputValue.toLowerCase()) > -1));
}

const Option = Select.Option;
new SegmentedControl({ selectedIndex: 0 })
const CheckboxGroup = Checkbox.Group;
const RadioGroup = Radio.Group;
const CheckboxItem = Checkbox2.CheckboxItem;
const alert = Modal.alert
const { TextArea } = Input;

const FormItem = Form.Item;

const formItemLayout = {
    labelCol: {
        xs: { span: 24 },
        sm: { span: 24 },
        md: { span: 6 },

    },
    wrapperCol: {
        xs: { span: 24 },
        sm: { span: 24 },
        md: { span: 15 },
    },
};

const formTailLayout = {
    labelCol: { span: 6 },
    wrapperCol: { span: 8, offset: 6 },
};
const isMobile = () => {
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

// getSubmitData
// hiddenDialog
// closeActivity
// showToast
//getStarFlowData//获取流程数据
class FlowFormItem extends Component {
    componentDidMount() {
        if (this.props.onChange) {
            this.props.onChange(this.props.value)
        }
    }
    normFile = (e) => {
        const { fileList } = e
        let newFileList = fileList.map((item) => {
            if (item.response && item.response.success) {

                return {
                    ...item,
                    url: item.response.data.url,
                    thumbUrl: item.response.data.thumbUrl,
                }
            }
            return { ...item }
        })

        return newFileList;
    }

    render() {
        const token = this.props.loginAndLogoutInfo.loginInfo.token;
        const { data, form: { getFieldDecorator, setFieldsValue, getFieldValue } } = this.props
        const { label, type, max = 999999, min = 0, must, count = 1000, rows = 4, dataIndex, disabled = false, placeholder, typeList = [], selectOptions = [], initialValue = "" } = data
        // console.log(data)
        const fieldName = "apiBody." + dataIndex.toString();
        //请假流程特殊处理
        const isLengthTime = dataIndex === 'lengthTime' ? true : false;
        let itemJsx;
        switch (type) {
            case "hidden":
                itemJsx = getFieldDecorator(fieldName, {
                    initialValue: initialValue,
                    rules: [{ required: must }],
                })(<input type="hidden" />)
                break;
            case "text":
                itemJsx = (
                    <FormItem
                        {...formItemLayout}
                        label={<span style={{ color: "#666" }}>{must ? <span> {label}</span> : label}</span>}
                    >
                        {getFieldDecorator(fieldName, {
                            initialValue: initialValue,
                            rules: [{
                                required: must, message: '必填',
                            }],
                        })(
                            <Input disabled={disabled} placeholder={placeholder} />
                        )}
                    </FormItem>
                )
                break;
            case "number":
                itemJsx = (
                    <FormItem
                        {...formItemLayout}
                        label={<span style={{ color: "#666" }}>{must ? <span>{label}</span> : label}</span>}
                    >
                        {getFieldDecorator(fieldName, {
                            initialValue: isLengthTime ? this.props.lengthTime : initialValue,
                            rules: [{
                                required: must, message: '必填',
                            }],
                        })(
                            <InputNumber min={min} max={max} style={{ width: "100%" }} disabled={disabled} placeholder={placeholder} />
                        )}
                    </FormItem>
                )
                break;
            case "textarea":
                itemJsx = (
                    <FormItem
                        {...formItemLayout}
                        label={<span style={{ color: "#666" }}>{must ? <span>{label}</span> : label}</span>}
                    >
                        {getFieldDecorator(fieldName, {
                            initialValue: initialValue,
                            rules: [{
                                required: must, message: '必填',
                            }],
                            normalize: (v) => {
                                return v && v.replace(/<br\/>/g, '\r\n');
                            }
                        })(
                            <TextArea count={disabled ? 0 : count} rows={rows} disabled={disabled} placeholder={placeholder} />
                        )}
                    </FormItem>
                )
                break;
            case "picker":
                getFieldDecorator(fieldName, { rules: [{ required: must }] })
                itemJsx = (<List key={dataIndex} renderHeader={() => <label style={{ color: "#333" }}>{must ? <span>{label}</span> : null}{label}</label>}>
                    <Picker
                        disabled={disabled}
                        value={[getFieldValue(fieldName)]}
                        onChange={value => { setFieldsValue({ [fieldName]: value[0] }); }}
                        data={typeList.map(({ id: value, title: label }) => { return { value, label } })} cols={1} >
                        <List.Item className={"myPicker"}>{""}</List.Item>
                    </Picker>
                </List>)

                break;
            case "date":
                itemJsx = (<FormItem
                    {...formItemLayout}
                    label={<span style={{ color: "#666" }}>{must ? <span>{label}</span> : label}</span>}
                >
                    {
                        getFieldDecorator(fieldName, {
                            // initialValue:   moment(),
                            rules: [{ required: must, message: "必填" }]
                        })(
                            <MDatePicker
                                placeholder={placeholder}
                                style={{ width: "100%" }}
                                disabled={disabled}
                                format="YYYY-MM-DD HH:mm:ss"
                                showTime={{ defaultValue: moment('00:00:00', 'HH:mm:ss') }}
                                onOk={(v) => {
                                    if (!v) {
                                        setFieldsValue({
                                            [fieldName]: moment()
                                        })
                                    }
                                }}
                            />
                        )
                    }

                </FormItem>)

                break;
            case "radioGroup":
                itemJsx = (
                    <FormItem
                        {...formItemLayout}
                        label={<span style={{ color: "#666" }}>{must ? <span>{label}</span> : label}</span>}
                    >
                        {getFieldDecorator(fieldName, {
                            rules: [{
                                required: must, message: '必填',
                            }],
                        })(
                            <RadioGroup
                                disabled={disabled}
                                style={{ width: "100%" }}>
                                <Row>
                                    {typeList.map((item, index) => {
                                        return <Col key={index} md={6} span={8}><Radio value={item.id}>{item.title}</Radio></Col>
                                    })}
                                </Row>
                            </RadioGroup>
                        )}
                    </FormItem>
                )

                break;
            case "checkboxGroup":
                getFieldDecorator(fieldName, { initialValue: "", rules: [{ required: must }] })

                itemJsx = (
                    <FormItem
                        {...formItemLayout}
                        label={<span style={{ color: "#666" }}>{must ? <span>{label}</span> : label}</span>}
                    >
                        <CheckboxGroup
                            disabled={disabled}
                            style={{ width: "100%" }}
                            value={getFieldValue(fieldName).split(",")}
                            onChange={value => { setFieldsValue({ [fieldName]: value.join(",") }); }}
                        >
                            <Row>
                                {typeList.map((item, index) => {
                                    return <Col key={index} md={6} span={8}><Checkbox value={item.id}>{item.title}</Checkbox></Col>
                                })}
                            </Row>
                        </CheckboxGroup>
                    </FormItem>
                )
                break;
            case "select":
                itemJsx = (
                    <FormItem
                        {...formItemLayout}
                        label={<span style={{ color: "#666" }}>{must ? <span>{label}</span> : label}</span>}
                    >
                        {getFieldDecorator(fieldName, {
                            initialValue: [],
                            rules: [{ required: must, message: '必填' }],
                        })(
                            <Select placeholder={placeholder} style={{ width: '100%' }} disabled={disabled} >
                                {
                                    selectOptions.map(({ label, value }, i) => {
                                        return <Option style={{ width: '100%' }} key={i} value={value}>{label}</Option>
                                    })
                                }
                            </Select>
                        )}
                    </FormItem>
                )
                break;
            case "Cascader":
                itemJsx = (
                    <FormItem
                        {...formItemLayout}

                        label={<span style={{ color: "#666" }}>{must ? <span>{label}</span> : label}</span>}
                    >
                        {getFieldDecorator(fieldName, {
                            initialValue: [],
                            rules: [{ required: must, message: '必填' }],
                        })(
                            <Cascader
                                style={{ width: '100%' }}
                                disabled={disabled}
                                options={selectOptions}
                                placeholder={placeholder}
                                showSearch={{ filter }}
                            />
                        )}
                    </FormItem>
                )
                break;
            case "upload":
                itemJsx = (
                    <FormItem
                        {...formItemLayout}
                        label={<span style={{ color: "#666" }}>{must ? <span>{label}</span> : label}</span>}
                    >
                        {getFieldDecorator(fieldName, {
                            initialValue: [],
                            rules: [{ required: must, message: '必填' }],
                        })(
                            <Upload
                                fieldName={fieldName}
                                token={token}
                                edit={!disabled} //是否可编辑
                                fetchConfig={{ apiName: 'upload' }}
                            />
                        )}
                    </FormItem>
                )
                break;
            case "files":

                itemJsx = (
                    <FormItem
                        {...formItemLayout}
                        label={<span style={{ color: "#666" }}>{must ? <span style={{ color: "#ff5b05" }}>*{label}</span> : label}</span>}
                    >
                        <div className="dropbox">
                            {getFieldDecorator(fieldName, {
                                valuePropName: 'fileList',
                                getValueFromEvent: this.normFile,
                            })(
                                <AntdUpload.Dragger
                                    showUploadList={
                                        {
                                            showRemoveIcon: !disabled
                                        }
                                    }
                                    key='0'
                                    disabled={disabled}
                                    data={{ fieldName }}
                                    listType="picture"
                                    headers={{ token }}
                                    multiple={true}
                                    name="files"
                                    action={`${window.configs.domain.api}upload`}>
                                    <p className="ant-upload-drag-icon">
                                        <Icon type="inbox" />
                                    </p>
                                    <p className="ant-upload-text">点击或者拖放文件到该区域即可上传</p>
                                    <p className="ant-upload-hint">支持单个或多个文件上传</p>
                                </AntdUpload.Dragger>
                            )}
                        </div>
                    </FormItem>
                )
                break;
            default:
                itemJsx = <div />
                break;
        }
        return itemJsx
    }
}
class FlowForm extends Component {
    static Item = FlowFormItem
    render() {
        const { getFieldsValue } = this.props.form;
        const { updateRootBody, flowFormColumn = [] } = this.props
        const columnJsx = flowFormColumn.map((item, index) => {
            if (item) {
                return <FlowFormItem key={index} {...this.props} data={item} />
            }
        })
        const apiBody = getFieldsValue()["apiBody"] || {}
        //处理数据 
        flowFormColumn.map((item = {}, i) => {
            const { dataIndex, type } = item;
            if (apiBody[dataIndex]) {
                //将moment时间格式处理为时间戳  下拉值为数组改为string 
                if (type === 'date') {
                    apiBody[dataIndex] = moment(apiBody[dataIndex]).valueOf()
                } else if (type === 'Cascader') {
                    apiBody[dataIndex] = apiBody[dataIndex].join(',')
                }
            }
            return dataIndex;
        })

        // console.log(apiBody)
        let rootBody = { apiBody: JSON.stringify(apiBody) }
        if (apiBody["opinionContent"]) {
            rootBody["opinionContent"] = apiBody["opinionContent"]
        }
        updateRootBody({ ...rootBody })
        return <div className={styles.container} style={{ background: isMobile() ? 'white' : '#f8f8f8' }}>
            {columnJsx}
        </div>
    }
}
const FlowButtonGroup = (props) => {
    let { flowButtons, clickFlowButton = () => { }, backTop = () => { }, createOpenFlow = () => { }, refresh = () => { } } = props
    flowButtons = flowButtons || [];
    const len = flowButtons.length || 0;
    const btns = flowButtons.map((item, index) => {
        let style = { right: (64 * (len - 1 - index) + 20) + "px" }, label = "...", pcLabel = "...";
        const { buttonClass } = item
        switch (buttonClass) {
            case "backTop":
                style["background"] = "#fff"
                style["color"] = "#108ee9"
                label = < div style={{
                    width: '28px',
                    height: '28px',
                    background: 'url(http://static.apih5.cn/icon/arrowup.svg) center center /  28px 28px no-repeat'
                }} />
                break;
            case "refresh":
                style["background"] = "#fff"
                style["color"] = "#108ee9"
                label = "刷"
                pcLabel = "刷新"
                break;
            case "goBack":
                style["background"] = "#fff"
                style["color"] = "greenyellow"
                label = "返"
                pcLabel = "返回"
                break;
            case "createOpenFlow":
                style["background"] = "green"
                label = "创"
                pcLabel = "创建"
                break;
            case "com.horizon.wf.action.ActionReaded":
                label = "阅"
                pcLabel = "已阅"
                break;
            case "com.horizon.wf.action.ActionReject":
                style["background"] = "red"
                label = "退"
                pcLabel = "退回"
                break;
            case "com.horizon.wf.action.ActionSubmitForReject":
                style["background"] = "greenyellow"
                label = "回"
                pcLabel = "退回提交"
                break;
            case "com.horizon.wf.action.ActionSubmit":
                style["background"] = "orange"
                label = "提"
                pcLabel = "提交"
                break;
            case "com.horizon.wf.action.ActionSave":
                style["background"] = "#108ee9"
                label = "存"
                pcLabel = "保存"
                break;
            default:
                style["background"] = "darkgrey"
                label = "未"
                pcLabel = "取消"
                break;
        }
        if (isMobile()) {
            return <div key={index} onClick={(e) => {
                e.preventDefault();
                clickFlowButton(item, {
                    backTop,
                    createOpenFlow,
                    refresh
                })
            }} className={styles.moreBtn} style={style}>{label}</div>
        } else {
            return <Button key={index} style={{ marginLeft: '8px' }} type="primary" onClick={(e) => {
                e.preventDefault();
                clickFlowButton(item, {
                    backTop,
                    createOpenFlow,
                    refresh
                })
            }} >
                {pcLabel}
            </Button>
        }

    })
    if (isMobile()) {
        return btns
    } else {
        return <FormItem {...formTailLayout}>
            {
                btns
            }
        </FormItem>

    }

}
const clickFlowButton = function (item = {}, option = {}) {
    const { dispatch } = this.props
    const { buttonId, buttonClass, buttonName } = item
    const { backTop = () => { }, createOpenFlow = () => { }, refresh = () => { } } = option
    const operateObj = {
        operate: buttonId,
        operateClazz: buttonClass,
        operateText: buttonName,
        operateFlag: 1
    }
    switch (buttonClass) {
        case "backTop":
            backTop()
            break;
        case "refresh":
            refresh()
            break;
        case "goBack":
            dispatch(goBack())
            break;
        case "createOpenFlow":
            createOpenFlow()
            break;
        case "com.horizon.wf.action.ActionSave":
            alert('暂存', '', [
                { text: '取消', onPress: () => console.log('cancel') },
                { text: '确定', onPress: () => { actionFlow.bind(this)({ operateObj, visible: false, close: true }) } },
            ])
            break;
        case "com.horizon.wf.action.ActionReaded":
            alert('阅读', '', [
                { text: '取消', onPress: () => console.log('cancel') },
                { text: '确定', onPress: () => { actionFlow.bind(this)({ operateObj, visible: false, close: true }) } },
            ])
            break;
        case "com.horizon.wf.action.ActionReject":
            alert('退回', '确定退回么？', [
                { text: '取消', onPress: () => console.log('cancel') },
                { text: '确定', onPress: () => { actionFlow.bind(this)({ operateObj, visible: false, close: true }) } },
            ])
            break;
        case "com.horizon.wf.action.ActionSubmitForReject":
            alert('退回提交', '确定退回提交么？', [
                { text: '取消', onPress: () => console.log('cancel') },
                { text: '确定', onPress: () => { actionFlow.bind(this)({ operateObj, visible: false, close: true }) } },
            ])
            break;
        case "com.horizon.wf.action.ActionSubmit":
            actionFlow.bind(this)({ operateObj, visible: true })
            break;
        default:
            alert("未知")
            break;
    }
}
const actionFlow = function (actionData) { 
    const { form, dispatch, match, myFetch } = this.props
    const { myPublic = {} } = this.props;
    const { androidApi } = myPublic;
    const { setFieldsValue, getFieldValue, setFields } = form;
    const operateObj = getFieldValue("operateObj")
    let curNextNodes = getFieldValue("curNextNodes") || []; 
    const { params: { flag = "" } } = match;
    const { validateFieldsAndScroll } = this.props.form;
    validateFieldsAndScroll((err, values) => {
        if (!err) {

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
                Toast.hide();
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
                        if (flag) {
                            window.location.href = 'http://weixin.fheb.cn/ZJOA/initDocWorkListMsg.do?docType=1&flg=1';
                            return;
                        }
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
                        // setTimeout(()=>{
                        //     setFields({
                        //         operateObj: {
                        //             operate,
                        //             operateClazz,
                        //             operateText,
                        //             operateFlag,
                        //         },
                        //         curNextNodes,
                        //         visible
                        //     })

                        // }, 2000)
                        
                        
                    }
                } else {
                    Toast.fail(message, 2, () => {
                        androidApi && androidApi.closeStartActivity()
                    });
                }
            })


        }
    })


}
class FlowSelectModal extends Component {
    componentWillMount() {
        const { getFieldDecorator } = this.props.form;
        getFieldDecorator("visible", { initialValue: false })
        getFieldDecorator("operateObj", { initialValue: {} })
        getFieldDecorator("curNextNodes", { initialValue: [] })
    }
    onClose = () => {
        if (this.props.onCancel) {
            this.props.onCancel()
        } else {
            const { setFieldsValue } = this.props.form;
            setFieldsValue({ visible: false })
        }
    }
    onWrapTouchStart = (e) => {
        // fix touch to scroll background page on iOS
        if (!/iPhone|iPod|iPad/i.test(navigator.userAgent)) {
            return;
        }
        const pNode = closest(e.target, '.am-modal-content');
        if (!pNode) {
            e.preventDefault();
        }
    }
    render() {
        const { myFetch } = this.props
        const { actionFlow: _actionFlow } = this.props
        const { getFieldValue, validateFields, setFieldsValue, } = this.props.form;
        const visible = getFieldValue("visible")
        const curNextNodes = getFieldValue("curNextNodes") || [];
        return (<Modal
            visible={visible}
            transparent
            maskClosable={false}
            title="请选择流程"
            footer={[
                { text: '取消', onPress: () => { this.onClose(); } },
                {
                    text: '确定', onPress: () => {
                        alert('提交', '确定么？', [
                            { text: '取消', onPress: () => console.log('cancel') },
                            {
                                text: '确定', onPress: () => {
                                    _actionFlow({ actionNextNodes: curNextNodes, visible: false, close: true });
                                }
                            },
                        ])
                    }
                }]}
            wrapProps={{ onTouchStart: this.onWrapTouchStart }}
            style={{
                maxWidth: '500px'
            }}
        >
            <List>
                {curNextNodes.map((item, index) => {
                    const { selectId, selectName, isSelect, authors, isDone, nodeGroup } = item
                    const checked = isSelect === "true"
                    return (<CheckboxItem
                        checked={checked}
                        onChange={() => {
                            if (JSON.stringify(authors) === "{}") {
                                validateFields((err) => {
                                    if (!err) {
                                        let actionNextNodes = curNextNodes.map((item3) => {
                                            if (item3.selectId === selectId) {
                                                if (nodeGroup) {
                                                    item3.isSelect = "true"
                                                } else {
                                                    item3.isSelect = item3.isSelect === "true" ? "false" : "true"
                                                }
                                            } else {
                                                if (nodeGroup) {
                                                    item3.isSelect = "false"
                                                }
                                            }
                                            return item3
                                        })
                                        _actionFlow({ actionNextNodes, selectId })
                                    }
                                })
                            } else {
                                validateFields((err) => {
                                    if (!err) {
                                        let actionNextNodes = curNextNodes.map((item3) => {
                                            if (item3.selectId === selectId) {
                                                if (nodeGroup) {
                                                    item3.isSelect = "true"
                                                } else {
                                                    item3.isSelect = item3.isSelect !== "true" || curNextNodes.length === 1 ? "true" : "false"
                                                }
                                            } else {
                                                if (nodeGroup) {
                                                    item3.isSelect = "false"
                                                }
                                            }
                                            return item3
                                        })
                                        setFieldsValue({
                                            curNextNodes: actionNextNodes
                                        })
                                    }
                                })
                            }
                        }}
                        key={selectId}>
                        <span>{isDone === "true" ? <span style={{ color: "#108ee9" }}>{"[已]"}</span> : ""}{selectName}{}</span>
                        {checked && authors && JSON.stringify(authors) !== "{}" ?
                            <List.Item.Brief>
                                <WarpSegmentedPull
                                    myFetch={myFetch}
                                    value={authors}
                                    onChange={value => {
                                        validateFields((err) => {
                                            if (!err) {
                                                let actionNextNodes = curNextNodes.map((item3, index3) => {
                                                    if (item3.selectId === selectId) {
                                                        for (const key in authors) {
                                                            item3.authors[key] = {
                                                                ...item3.authors[key],
                                                                selectAuthorNewApih5: value[key].selectAuthorNewApih5
                                                            }
                                                        }
                                                    }
                                                    return item3
                                                })
                                                setFieldsValue({
                                                    curNextNodes: actionNextNodes
                                                })
                                            }
                                        })
                                    }} />
                            </List.Item.Brief> : null}
                    </CheckboxItem>)
                })}
            </List>
        </Modal >)
    }
}
class SegmentedPull extends Component {
    constructor(props) {
        super(props);
        const { value } = this.props
        this.values = [{ authorId: "Author", authorName: "主办" }, { authorId: "SecondAuthor", authorName: "协办" }, { authorId: "Reader", authorName: "读者" }]
        let selectedIndex = 0
        for (let i = 0; i < this.values.length; i++) {
            let { authorId } = this.values[i]
            if (value[authorId] && value[authorId].isSelect === "true") {
                selectedIndex = i
                break
            }
        }
        // console.log(selectedIndex)
        this.state = {
            selectedIndex,
            treeData: [],
            loading: false,
        };
    }
    onChange = (selectedIndex) => {
        this.setState({
            selectedIndex
        })
    }
    componentDidMount() {
        if (this.props.onChange) {
            this.props.onChange(this.props.value)
        }
        // this.loadTree()
    }
    loadTree = () => {//加载树形结构
        const { myFetch } = this.props
        myFetch("getSysDepartmentUserAllTree").then(({ success, code, message, data }) => {
            if (success) {
                this.setState({ treeData: [data], loading: false })
            }
        })
    }
    render() {
        const { selectedIndex, loading } = this.state
        const { getFieldDecorator } = this.props.form;
        const { value, myFetch } = this.props
        return (
            loading ?
                <ActivityIndicator
                    text="Loading..."
                /> :
                <div>
                    <SegmentedControl2 onChange={this.onChange} value={value} selectedIndex={selectedIndex} />
                    {this.values.map(({ authorId }, index) => {
                        if (value[authorId]) { 
                            return (
                                <div key={index} style={{ display: selectedIndex === index ? "block" : "none" }}>
                                    {isMobile() ?
                                        getFieldDecorator(authorId + "[selectAuthorNewApih5]", {
                                            valuePropName: "defaultValue",
                                            initialValue: value[authorId]["selectAuthorNewApih5"] || [],
                                        })(<PullPersionMobile 
                                            search={value[authorId].initAuthorApih5SearchApi}
                                            searchParamsKey={'search'}
                                            searchApi={value[authorId].initAuthorApih5SearchApi ? value[authorId].initAuthorApih5SearchApi : "getSysDepartmentUserAllTree"}
                                            myFetch={myFetch}
                                            selectType={"2"}
                                            edit={value[authorId].isSelect === "true"}
                                            treeData={value[authorId].initAuthorApih5}
                                        />)
                                        : getFieldDecorator(authorId + "[selectAuthorNewApih5]", {
                                            valuePropName: "defaultValue",
                                            initialValue: value[authorId]["selectAuthorNewApih5"] || [],
                                        })(<PullPersion
                                            search={value[authorId].initAuthorApih5SearchApi}
                                            searchParamsKey={'search'}
                                            searchApi={value[authorId].initAuthorApih5SearchApi ? value[authorId].initAuthorApih5SearchApi : "getSysDepartmentUserAllTree"}
                                            myFetch={myFetch}
                                            selectType={"2"}
                                            edit={value[authorId].isSelect === "true"}
                                            treeData={value[authorId].initAuthorApih5}
                                        />)
                                    }

                                </div>
                            )
                        } else {
                            return null
                        }
                    })}
                </div >
        )
    }
}
const WarpSegmentedPull = createForm({
    mapPropsToFields: (props) => {
        return {
            Author: createFormField(props.value.Author),
            SecondAuthor: createFormField(props.value.SecondAuthor),
            Reader: createFormField(props.value.Reader),
        };
    },
    onValuesChange: (props, changedValues, allValues) => {
        if (props.onChange) {
            props.onChange(allValues)
        }
    }
})(SegmentedPull)

class SegmentedControl2 extends Component {
    constructor(props) {
        super(props);
        this.state = {
            selectedIndex: 0,
            treeData: [],
            loading: false,
        };
    }
    onChange = (selectedIndex) => {
        this.props.onChange(selectedIndex)
    }
    render() {
        const { value, selectedIndex } = this.props
        const _values = [{ authorId: "Author", authorName: "主办" }, { authorId: "SecondAuthor", authorName: "协办" }, { authorId: "Reader", authorName: "读者" }]
        return (
            <div className="am-segment" role="tablist">
                {_values.map(({ authorId, authorName }, index) => {
                    if (value[authorId]) {
                        const selected = selectedIndex === index
                        const { isFree } = value[authorId]
                        const disabled = isFree !== "true"
                        return (
                            <div key={index} onClick={(e) => { e.preventDefault(); !disabled && this.onChange(index) }} style={disabled ? { opacity: .5 } : {}} className={selected ? "am-segment-item am-segment-item-selected" : "am-segment-item"}>
                                <div className="am-segment-item-inner"></div>{authorName}
                            </div>
                        )
                    } else {
                        return null
                    }
                })}
            </div>
        )
    }
}
const closest = function (el, selector) {
    const matchesSelector = el.matches || el.webkitMatchesSelector || el.mozMatchesSelector || el.msMatchesSelector;
    while (el) {
        if (matchesSelector.call(el, selector)) {
            return el;
        }
        el = el.parentElement;
    }
    return null;
}


export {
    actionFlow,
    clickFlowButton,
    FlowButtonGroup,
    FlowSelectModal,
    FlowForm
}