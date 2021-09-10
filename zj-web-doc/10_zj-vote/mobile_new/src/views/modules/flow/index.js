import React, { Component } from 'react';
import { createForm, createFormField } from 'rc-form';
import { goBack } from 'connected-react-router'
import { Picker, InputItem, TextareaItem, Modal, Toast, List, ActivityIndicator, DatePicker, Checkbox as Checkbox2, SegmentedControl, } from 'antd-mobile';
import { Radio, Checkbox, Row, Col } from 'antd';
import moment from 'moment'
import Upload from "../upload";
import PullPersionMobile from "../pullPersionMobile";
import styles from "./style/index.less";
new SegmentedControl({ selectedIndex: 0 })
const CheckboxGroup = Checkbox.Group;
const RadioGroup = Radio.Group;
const CheckboxItem = Checkbox2.CheckboxItem;
const alert = Modal.alert
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
    render() {
        const { loginAndLogoutInfo: { loginInfo: { token } }, data, form: { getFieldDecorator, getFieldError, setFieldsValue, getFieldValue } } = this.props
        const { label, type, must, mode, count = 1000, rows = 4, format, dataIndex, disabled = false, placeholder, typeList = [] } = data
        const fieldName = "apiBody." + dataIndex.toString()
        let itemJsx;
        switch (type) {
            case "hidden":
                itemJsx = getFieldDecorator(fieldName, {
                    initialValue: "",
                    rules: [{ required: must }],
                })(<input type="hidden" />)
                break;
            case "text":
                itemJsx = (<List key={dataIndex} renderHeader={() => <label style={{ color: "#333" }}>{must ? <span style={{ color: "#ff5b05" }}>*</span> : null}{label}</label>}>
                    {getFieldDecorator(fieldName, {
                        initialValue: "",
                        rules: [{ required: must }],
                    })(
                        <InputItem
                            disabled={disabled}
                            error={!!getFieldError(fieldName)}
                            onErrorClick={() => { Toast.info(label + "必填", 1); }}
                            placeholder={placeholder}
                        ></InputItem>
                    )}
                </List>)
                break;
            case "number":
                itemJsx = (<List key={dataIndex} renderHeader={() => <label style={{ color: "#333" }}>{must ? <span style={{ color: "#ff5b05" }}>*</span> : null}{label}</label>}>
                    {getFieldDecorator(fieldName, {
                        initialValue: 0,
                        rules: [{ required: must }],
                    })(
                        <InputItem
                            type="digit"
                            disabled={disabled}
                            error={!!getFieldError(fieldName)}
                            onErrorClick={() => { Toast.info(label + "必填", 1); }}
                            placeholder={placeholder}
                        ></InputItem>
                    )}
                </List>)
                break;
            case "textarea":
                itemJsx = (<List key={dataIndex} renderHeader={() => <label style={{ color: "#333" }}>{must ? <span style={{ color: "#ff5b05" }}>*</span> : null}{label}</label>}>
                    {getFieldDecorator(fieldName, {
                        initialValue: "",
                        rules: [{ required: must }],
                    })(
                        <TextareaItem
                            rows={rows}
                            count={disabled ? 0 : count}
                            disabled={disabled}
                            error={!!getFieldError(fieldName)}
                            onErrorClick={() => { Toast.info(label + "必填", 1); }}
                            placeholder={placeholder}
                        ></TextareaItem>
                    )}
                </List>)
                break;
            case "picker":
                getFieldDecorator(fieldName, { rules: [{ required: must }] })
                itemJsx = (<List key={dataIndex} renderHeader={() => <label style={{ color: "#333" }}>{must ? <span style={{ color: "#ff5b05" }}>*</span> : null}{label}</label>}>
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
                getFieldDecorator(fieldName, { initialValue: new Date().getTime(), rules: [{ required: must }] })
                itemJsx = (<List key={dataIndex} renderHeader={() => <label style={{ color: "#333" }}>{must ? <span style={{ color: "#ff5b05" }}>*</span> : null}{label}</label>}>
                    <DatePicker
                        locale={
                            {
                                okText: "确定", dismissText: "取消"
                            }
                        }
                        disabled={disabled}
                        mode={mode}
                        format={format}
                        value={new Date(getFieldValue(fieldName))}
                        onChange={value => { setFieldsValue({ [fieldName]: value.getTime() }); }}
                        data={typeList.map(({ id: value, title: label }) => { return { value, label } })} cols={1} >
                        <List.Item className={"myPicker"}>{""}</List.Item>
                    </DatePicker>
                </List>)
                break;
            case "radioGroup":
                itemJsx = (<List key={dataIndex} renderHeader={() => <label style={{ color: "#333" }}>{must ? <span style={{ color: "#ff5b05" }}>*</span> : null}{label}</label>}>
                    {getFieldDecorator(fieldName, {
                        initialValue: "",
                        rules: [{ required: must }],
                    })(
                        <RadioGroup
                            disabled={disabled}
                            style={{ width: "100%", padding: "10px 0px  10px 15px" }}>
                            <Row>
                                {typeList.map((item, index) => {
                                    return <Col key={index} span={8}><Radio value={item.id}>{item.title}</Radio></Col>
                                })}
                            </Row>
                        </RadioGroup>
                    )}
                </List>)
                break;
            case "checkboxGroup":
                getFieldDecorator(fieldName, { initialValue: "", rules: [{ required: must }] })
                itemJsx = (<List key={dataIndex} renderHeader={() => <label style={{ color: "#333" }}>{must ? <span style={{ color: "#ff5b05" }}>*</span> : null}{label}</label>}>
                    <CheckboxGroup
                        disabled={disabled}
                        style={{ width: "100%", padding: "10px 0px  10px 15px" }}
                        value={getFieldValue(fieldName).split(",")}
                        onChange={value => { setFieldsValue({ [fieldName]: value.join(",") }); }}
                    >
                        <Row>
                            {typeList.map((item, index) => {
                                return <Col key={index} span={8}><Checkbox value={item.id}>{item.title}</Checkbox></Col>
                            })}
                        </Row>
                    </CheckboxGroup>
                </List>)
                break;
            case "upload":
                itemJsx = (<List key={dataIndex} renderHeader={() => <label style={{ color: "#333" }}>{must ? <span style={{ color: "#ff5b05" }}>*</span> : null}{label}</label>}>
                    <div style={{ padding: "0 15px" }}>
                        {getFieldDecorator(fieldName, {
                            initialValue: [],
                            rules: [{ required: must }],
                        })(
                            <Upload
                                token={token}
                                edit={!disabled} //是否可编辑
                                fetchConfig={{ apiName: 'upload' }}
                            />
                        )}
                    </div>
                </List>)
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
            return <FlowFormItem key={index} {...this.props} data={item} />
        })
        const apiBody = getFieldsValue()["apiBody"] || {}
        let rootBody = { apiBody: JSON.stringify(apiBody) }
        if (apiBody["opinionContent"]) {
            rootBody["opinionContent"] = apiBody["opinionContent"]
        }
        updateRootBody({ ...rootBody })
        return <div className={styles.container}>
            {columnJsx}
        </div>
    }
}
const FlowButtonGroup = (props) => {
    let { flowButtons, clickFlowButton = () => { }, backTop = () => { }, createOpenFlow = () => { }, refresh = () => { } } = props
    flowButtons = flowButtons || []
    const len = flowButtons.length || 0
    return flowButtons.map((item, index) => {
        let style = { right: (64 * (len - 1 - index) + 20) + "px" }, label = "...";
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
                break;
            case "goBack":
                style["background"] = "#fff"
                style["color"] = "greenyellow"
                label = "返"
                break;
            case "createOpenFlow":
                style["background"] = "green"
                label = "创"
                break;
            case "com.horizon.wf.action.ActionReaded":
                label = "阅"
                break;
            case "com.horizon.wf.action.ActionReject":
                style["background"] = "red"
                label = "退"
                break;
            case "com.horizon.wf.action.ActionSubmitForReject":
                style["background"] = "greenyellow"
                label = "回"
                break;
            case "com.horizon.wf.action.ActionSubmit":
                style["background"] = "orange"
                label = "提"
                break;
            case "com.horizon.wf.action.ActionSave":
                style["background"] = "#108ee9"
                label = "存"
                break;
            default:
                style["background"] = "darkgrey"
                label = "未"
                break;
        }
        return <div key={index} onClick={(e) => {
            e.preventDefault();
            clickFlowButton(item, {
                backTop,
                createOpenFlow,
                refresh
            })
        }} className={styles.moreBtn} style={style}>{label}</div>
    })
}
const clickFlowButton = function (item = {}, option = {}) {
    const { dispatch, myPublic: { androidApi } } = this.props
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
        const { myFetch, actionFlow: _actionFlow } = this.props
        const { getFieldValue, validateFields, setFieldsValue, } = this.props.form;
        const visible = getFieldValue("visible")
        const curNextNodes = getFieldValue("curNextNodes")
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
        const { selectedIndex, treeData, loading } = this.state
        const { getFieldDecorator, getFieldsValue, getFieldValue } = this.props.form;
        const { value } = this.props
        return (
            loading ?
                <ActivityIndicator
                    text="Loading..."
                /> :
                <div>
                    <SegmentedControl2 onChange={this.onChange} value={value} selectedIndex={selectedIndex} />
                    {this.values.map(({ authorId }, index) => {
                        // console.log('Author', value[authorId].initAuthorApih5)
                        if (value[authorId]) {
                            return (
                                <div key={index} style={{ display: selectedIndex === index ? "block" : "none" }}>
                                    {
                                        getFieldDecorator(authorId + "[selectAuthorNewApih5]", {
                                            valuePropName: "defaultValue",
                                            initialValue: value[authorId]["selectAuthorNewApih5"] || [],
                                        })(<PullPersionMobile
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