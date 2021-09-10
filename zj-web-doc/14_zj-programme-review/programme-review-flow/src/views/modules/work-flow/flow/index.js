import React,{ Component } from "react";
import { createForm,createFormField } from "rc-form";
import { goBack } from "connected-react-router"; //, push
import {
    Modal,
    Toast,
    List,
    ActivityIndicator,
    Checkbox as Checkbox2,
    SegmentedControl
} from "antd-mobile";
import { Form,Button } from "antd";
import moment from "moment";
import PullPersionMobile from "../../pullPersionMobile";
import PullPersion from "../../pullPersion";
import styles from "./style/index.less";
import QnnForm from "../../qnn-table/qnn-form";
// moment.locale('zh-cn');

new SegmentedControl({ selectedIndex: 0 });
const CheckboxItem = Checkbox2.CheckboxItem;
const alert = Modal.alert;

const FormItem = Form.Item;

//正常使用时的按钮栅格比例
let formTailLayout = {
    labelCol: { span: 5 },
    wrapperCol: { span: 8,offset: 6 }
};
const isMobile = () => {
    if (
        navigator.userAgent.match(/Android/i) ||
        navigator.userAgent.match(/webOS/i) ||
        navigator.userAgent.match(/iPhone/i) ||
        navigator.userAgent.match(/iPad/i) ||
        navigator.userAgent.match(/iPod/i) ||
        navigator.userAgent.match(/BlackBerry/i) ||
        navigator.userAgent.match(/Windows Phone/i)
    ) {
        return true;
    } else {
        return false;
    }
};

class FlowForm extends Component {
    render() {
        const { getFieldsValue } = this.props.form;
        let {
            updateRootBody,
            isInQnnTable,
            flowFormColumn = []
            // flowHistoryList = [],
            // delApiBody
        } = this.props;
        let apiBody = getFieldsValue()["apiBody"] || {};
        const setLinkageField = obj => {
            let { children,form } = obj;
            const { field } = form;
            let fieldName = "";
            if (field.indexOf("apiBody.") !== -1) {
                fieldName = field;
            } else {
                fieldName = field ? "apiBody." + field.toString() : "";
            }
            form.field = fieldName;
            if (children) {
                setLinkageField(children);
            }
            return obj;
        };

        //处理数据
        flowFormColumn = flowFormColumn.map((item = {},i) => {
            const { field,type } = item;
            // const _item = { ...item };
            if (apiBody[field]) {
                //将moment时间格式处理为时间戳  下拉值为数组改为string
                if (type === "datetime") {
                    apiBody[field] = moment(apiBody[field]).valueOf();
                } else if (type === "Cascader" || type === "cascader") {
                    apiBody[field] = apiBody[field].join(",");
                }
            }

            //给所有字段名加上apiBody.
            let fieldName = "";
            if (field && field.indexOf("apiBody.") !== -1) {
                fieldName = field;
            } else {
                fieldName = field ? `apiBody.${field}` : "";
            }
            item.field = fieldName;

            if (type === "linkage") {
                //联动
                item.children = setLinkageField(item.children);
            } else if (type === "qnnForm") {
                apiBody[`${field.replace(/(apiBody.)/g,"")}`] =
                    apiBody[`${field.replace(/(apiBody.)/g,"")}_Block`];
                delete apiBody[`${field.replace(/(apiBody.)/g,"")}_Block`];
            }
            if (!item.formItemLayout) {
                item.formItemLayout = {
                    labelCol: {
                        xs: { span: 7 },
                        sm: { span: 6 }
                    },
                    wrapperCol: {
                        xs: { span: 17 },
                        sm: { span: 18 }
                    }
                }
            }
            // if (item.type === 'qnnForm') {
            //     item.qnnFormConfig.formConfig = item.qnnFormConfig.formConfig.map(item => {
            //         if (!item.formItemLayout) {
            //             item.formItemLayout = {
            //                 labelCol: {
            //                     xs: { span: 7 },
            //                     sm: { span: 6 }
            //                 },
            //                 wrapperCol: {
            //                     xs: { span: 17 },
            //                     sm: { span: 18 }
            //                 }
            //             }
            //         }
            //     })
            // }
            return item;
        });

        let rootBody = { apiBody: JSON.stringify(apiBody) };
        if (apiBody["opinionContent"]) {
            rootBody["opinionContent"] = apiBody["opinionContent"];
        }
        // console.log(rootBody)
        updateRootBody({ ...rootBody });
        const QnnFormConfig = {
            ...this.props,
            form: this.props.form,
            fetch: this.props.myFetch,
            headers: { token: this.props.loginAndLogoutInfo.loginInfo.token },
            formConfig: flowFormColumn,
            style: {
                //   // height:window.innerHeight - 90,
                //   // height:"calc(100vh - 90)",
                paddingBottom: '52px'
            },
            // formItemLayout: {
            //     labelCol: {
            //         xs: { span: 24 },
            //         sm: { span: 3 }
            //     },
            //     wrapperCol: {
            //         xs: { span: 24 },
            //         sm: { span: 20 }
            //     }
            // }, 
        };
        // console.log(flowFormColumn);
        if (flowFormColumn.length > 0) { 
            return (
                <div
                    className={
                        isInQnnTable || isMobile()
                            ? styles.containerByQnnTable
                            : styles.container
                    }
                >
                    <QnnForm {...QnnFormConfig} nodeVars={this.props.nodeVars} />
                    {this.props.children}
                </div>
            );
        } else {
            return <div />;
        }
    }
}

const FlowButtonGroup = props => {
    let {
        flowButtons,
        clickFlowButton = () => { },
        backTop = () => { },
        createOpenFlow = () => { },
        refresh = () => { },
        isInQnnTable,

    } = props;
    flowButtons = flowButtons || [];
    const len = flowButtons.length || 0;
    const btns = flowButtons.map((item,index) => {
        let style = { right: 64 * (len - 1 - index) + 20 + "px" },
            // label = "...",
            pcLabel = "...";
        const { buttonClass } = item;
        switch (buttonClass) {
            case "backTop":
                style["background"] = "#fff";
                style["color"] = "#108ee9";
                // label = (
                //   <div
                //     style={{
                //       width: "28px",
                //       height: "28px",
                //       background:
                //         "url(http://cdn.apih5.com/icon/arrowup.svg) center center /  28px 28px no-repeat"
                //     }}
                //   />
                // );
                break;
            case "refresh":
                style["background"] = "#fff";
                style["color"] = "#108ee9";
                // label = "刷";
                pcLabel = "刷新";
                break;
            case "goBack":
                style["background"] = "#fff";
                style["color"] = "greenyellow";
                // label = "返";
                pcLabel = "返回";
                break;
            case "createOpenFlow":
                style["background"] = "green";
                // label = "创";
                pcLabel = "发起";
                break;
            case "com.horizon.wf.action.ActionReaded":
                // label = "阅";
                pcLabel = "已阅";
                break;
            case "com.horizon.wf.action.ActionReject":
                style["background"] = "red";
                // label = "退";
                pcLabel = "退回";
                break;
            case "com.horizon.wf.action.ActionSubmitForReject":
                style["background"] = "greenyellow";
                // label = "回";
                pcLabel = "退回提交";
                break;
            case "com.horizon.wf.action.ActionSubmit":
                style["background"] = "orange";
                // label = "提";
                pcLabel = "提交";
                break;
            case "com.horizon.wf.action.ActionSave":
                style["background"] = "#108ee9";
                pcLabel = "保存";
                break;
            case "preFlow":
                style["background"] = "#108ee9";
                pcLabel = "在线预览";
                break;

            default:
                style["background"] = "darkgrey";
                // label = "未";
                pcLabel = "取消";
                break;
        }
        return (
            <Button
                key={index}
                style={{ marginLeft: index === 0 ? "0px" : "8px" }}
                type="primary"
                onClick={e => {
                    e.preventDefault();
                    clickFlowButton(item,{
                        backTop,
                        createOpenFlow,
                        refresh
                    });
                }}
            >
                {pcLabel}
            </Button>
        );
    });
    //按钮布局在移动的 pc端 和qnn-table中都不一样
    formTailLayout = props.formTailLayout || formTailLayout;
    if (isInQnnTable) {
        return (
            <FormItem {...formTailLayout} className={styles.qnnTableFlowBtn}>
                {btns}
            </FormItem>
        );
    }

    if (isMobile()) {
        return <div className={styles.mobileBtnContainer}>{btns}</div>;
    } else {
        return (
            <FormItem {...formTailLayout} className={styles.pcBtnsContainer}>
                {btns}
            </FormItem>
        );
    }
};
const clickFlowButton = function (item = {},option = {}) {
    const { dispatch } = this.props;
    const { buttonId,buttonClass,buttonName,printUrl } = item;
    const {
        backTop = () => { },
        createOpenFlow = () => { },
        refresh = () => { }
    } = option;
    const operateObj = {
        operate: buttonId,
        operateClazz: buttonClass,
        operateText: buttonName,
        operateFlag: 1
    };
    switch (buttonClass) {
        case "backTop":
            backTop();
            break;
        case "refresh":
            refresh();
            break;
        case "goBack":
            dispatch(goBack());
            break;
        case "createOpenFlow":
            createOpenFlow();
            break;
        case "com.horizon.wf.action.ActionSave":
            alert("暂存","",[
                { text: "取消",onPress: () => console.log("cancel") },
                {
                    text: "确定",
                    onPress: () => {
                        actionFlow.bind(this)({
                            operateObj,
                            visible: false,
                            close: true
                        });
                    }
                }
            ]);
            break;
        case "com.horizon.wf.action.ActionReaded":
            alert("阅读","",[
                { text: "取消",onPress: () => console.log("cancel") },
                {
                    text: "确定",
                    onPress: () => {
                        actionFlow.bind(this)({
                            operateObj,
                            visible: false,
                            close: true
                        });
                    }
                }
            ]);
            break;
        case "com.horizon.wf.action.ActionReject":
            alert("退回","确定退回么？",[
                { text: "取消",onPress: () => console.log("cancel") },
                {
                    text: "确定",
                    onPress: () => {
                        actionFlow.bind(this)({
                            operateObj,
                            visible: false,
                            close: true
                        });
                    }
                }
            ]);
            break;
        case "com.horizon.wf.action.ActionSubmitForReject":
            alert("退回提交","确定退回提交么？",[
                { text: "取消",onPress: () => console.log("cancel") },
                {
                    text: "确定",
                    onPress: () => {
                        actionFlow.bind(this)({
                            operateObj,
                            visible: false,
                            close: true
                        });
                    }
                }
            ]);
            break;
        case "com.horizon.wf.action.ActionSubmit":
            actionFlow.bind(this)({ operateObj,visible: true });
            break;

        case "preFlow":
            window.location.href = printUrl;
            break;
        default:
            alert("未知");
            break;
    }
};
const actionFlow = function (actionData,callback) {
    const {
        form,
        dispatch,
        match,
        myFetch,
        isInQnnTable,
        myPublic = {},
        btnCallbackFn = {},
        actionParamsFormat = obj => obj,
        workFlowConfig: { pushNode },
    } = this.props;
    const { closeDrawer,refresh } = btnCallbackFn;
    const { androidApi } = myPublic;
    const { setFieldsValue,getFieldValue,validateFieldsAndScroll } = form;
    const { pushNodeData = [] } = this.state;
    const operateObj = getFieldValue("operateObj");
    let curNextNodes = getFieldValue("curNextNodes") || [];
    const {
        params: { flag = "" }
    } = match;
    validateFieldsAndScroll((err,values) => {
        if (!err) {
            const {
                operateObj: newOperateObj = {},
                selectId,
                visible = true,
                close = false
            } = actionData;

            let { actionNextNodes = [] } = actionData;

            //需要删除掉追加的按钮不然后台报系统异常
            if (pushNodeData.length) {
                actionNextNodes.splice(pushNodeData.length,actionNextNodes.length);
            }

            const actionBody = {
                ...this.rootBody,
                actionData: JSON.stringify({
                    ...operateObj,
                    ...newOperateObj,
                    nextNodes: actionNextNodes
                })
            };
            Toast.loading("loading...",0);
            const newBody = actionParamsFormat(actionBody,{ ...this.props });
            myFetch("actionFlow",newBody).then(
                ({ success,message,data }) => {
                    Toast.hide();
                    if (success) {


                        const {
                            operate,
                            operateClazz,
                            operateText,
                            operateFlag,
                            nextNodes: callbackNextNodes
                        } = data;

                        if (!selectId) {
                            curNextNodes = callbackNextNodes;
                        } else {
                            curNextNodes = actionNextNodes.map(
                                (item,index) => {
                                    if (item.selectId === selectId) {
                                        for (
                                            let i = 0;
                                            i < callbackNextNodes.length;
                                            i++
                                        ) {
                                            if (
                                                callbackNextNodes[i]
                                                    .selectId === selectId
                                            ) {
                                                item.authors =
                                                    callbackNextNodes[
                                                        i
                                                    ].authors;
                                                break;
                                            }
                                        }
                                    }
                                    return item;
                                }
                            );
                        }
                        if (close) {
                            if (flag) {
                                window.location.href =
                                    "http://weixin.fheb.cn/ZJOA/initDocWorkListMsg.do?docType=1&flg=1";
                                return;
                            }
                            //需要关闭的话必须先执行回调不然关闭后回调没法执行了
                            //回调
                            if (callback) {
                                callback(data);
                            }
                            if (androidApi) {
                                androidApi.closeStartActivity();
                            } else if (isInQnnTable) {
                                closeDrawer(false,() => {
                                    refresh();
                                });
                            } else {
                                dispatch(goBack());
                            }
                        } else {
                            const _value = {
                                operateObj: {
                                    operate,
                                    operateClazz,
                                    operateText,
                                    operateFlag: "1"
                                },
                                curNextNodes: curNextNodes.concat(pushNodeData),
                                visible
                            };
                            setFieldsValue({ ..._value });
                        }

                        //正常回调
                        if (callback) {
                            callback(data);
                        }

                        if (pushNode && !pushNodeData.length) {
                            //请求追加数据
                            this.getPushNodeData.bind(this)(data);
                        }

                    } else {
                        Toast.fail(message,2,() => {
                            androidApi && androidApi.closeStartActivity();
                        });
                    }
                }
            );
        }
    });
};
class FlowSelectModal extends Component {
    componentWillMount() {
        // const { getFieldDecorator } = this.props.form;
        // getFieldDecorator("visible", { initialValue: false })(<Input hidden />)
        // getFieldDecorator("operateObj", { initialValue: {} })
        // getFieldDecorator("curNextNodes", { initialValue: [] })
    }
    onClose = () => {
        if (this.props.onCancel) {
            this.props.onCancel();
        } else {
            const { setFieldsValue } = this.props.form;
            setFieldsValue({ visible: false });
        }
    };
    // onWrapTouchStart = e => {
    //   if (!/iPhone|iPod|iPad/i.test(navigator.userAgent)) {
    //     return;
    //   }
    //   const pNode = closest(e.target, ".am-modal-content");
    //   if (!pNode) {
    //     e.preventDefault();
    //   }
    // };
    render() {
        const { actionFlow: _actionFlow,myFetch,form = {},
            // workFlowConfig: { pushNode },pushNodeData,
            // flowId,workId 
            submitFlowCallback
        } = this.props;
        const { getFieldValue,validateFields,setFieldsValue } = form;
        const visible = getFieldValue("visible");
        let curNextNodes = getFieldValue("curNextNodes") || [];
        return (
            <Modal
                wrapClassName={"w-flowModal"}
                className="workFlow-myModal"
                visible={visible}
                maskClosable={false}
                title="请选择流程"
                footer={[
                    {
                        text: "取消",
                        onPress: () => {
                            this.onClose();
                        }
                    },
                    {
                        text: "确定",
                        onPress: () => {
                            const { setFieldsValue } = this.props.form;
                            //判断是否选人
                            let selectPersoned = false;
                            for (let i = 0; i < curNextNodes.length; i++) {
                                let {
                                    Author = {},
                                    SecondAuthor = {},
                                    Reader = {}
                                } = curNextNodes[i].authors;
                                let isSelectA =
                                    Author.selectAuthorNewApih5 &&
                                    Author.selectAuthorNewApih5.length;
                                let isSelectS =
                                    SecondAuthor.selectAuthorNewApih5 &&
                                    SecondAuthor.selectAuthorNewApih5.length;
                                let isSelectR =
                                    Reader.selectAuthorNewApih5 &&
                                    Reader.selectAuthorNewApih5.length;
                                if (isSelectA || isSelectS || isSelectR) {
                                    //选择了人
                                    selectPersoned = true;
                                }
                            }
                            //结束时nodeId = End(n)不需要选择人也行
                            const endExg = /^End(\d{1,9})$/;
                            const { nodeId } = curNextNodes.filter(item => { return (item.isSelect === "true" || item.isSelect === true) })[0];
                            if (!selectPersoned && !endExg.test(nodeId)) {
                                Toast.info("请选择审批人员");
                                return;
                            }
                            setFieldsValue({ visible: false });
                            alert("提交","确定么？",[
                                {
                                    text: "取消",
                                    onPress: () => {
                                        setFieldsValue({ visible: true });
                                    }
                                },
                                {
                                    text: "确定",
                                    onPress: () => {
                                        // let _pushNodeData = [];
                                        // let _curNextNodes = [...curNextNodes];
                                        // if (pushNode) {
                                        //     //只提交原本的节点  
                                        //     _curNextNodes = curNextNodes.filter((_,i) => i < (curNextNodes.length - pushNodeData.length));

                                        //     //只提交追加的节点
                                        //     _pushNodeData = curNextNodes.filter((_,i) => i >= (curNextNodes.length - pushNodeData.length));
                                        // }

                                        // console.log('curNextNodes：',_curNextNodes)
                                        // console.log('_pushNodeData：',_pushNodeData)

                                        _actionFlow({
                                            actionNextNodes: curNextNodes,
                                            visible: false,
                                            close: true,
                                            operateObj: {
                                                operateFlag: "0"
                                            }
                                        },(res) => {
                                            if (submitFlowCallback) {
                                                submitFlowCallback({ props: this.props,res: res })
                                            }
                                        });
                                    }
                                }
                            ]);
                        }
                    }
                ]}
                // wrapProps={{ onTouchStart: this.onWrapTouchStart }}
                style={{
                    maxWidth: "500px"
                }}
            >
                <List>
                    {curNextNodes.map((item,index) => {
                        const {
                            selectId,
                            selectName,
                            isSelect,
                            authors,
                            isDone,
                            nodeGroup
                        } = item;
                        const checked = isSelect === "true";
                        return (
                            <CheckboxItem
                                checked={checked}
                                onChange={() => {
                                    if (JSON.stringify(authors) === "{}") {
                                        validateFields(err => {
                                            if (!err) {
                                                let actionNextNodes = curNextNodes.map(
                                                    item3 => {
                                                        if (
                                                            item3.selectId ===
                                                            selectId
                                                        ) {
                                                            if (nodeGroup) {
                                                                item3.isSelect =
                                                                    "true";
                                                            } else {
                                                                item3.isSelect =
                                                                    item3.isSelect ===
                                                                        "true"
                                                                        ? "false"
                                                                        : "true";
                                                            }
                                                        } else {
                                                            if (nodeGroup) {
                                                                item3.isSelect =
                                                                    "false";
                                                            }
                                                        }
                                                        return item3;
                                                    }
                                                );
                                                _actionFlow({
                                                    actionNextNodes,
                                                    selectId
                                                });
                                            }
                                        });
                                    } else {
                                        validateFields(err => {
                                            if (!err) {
                                                let actionNextNodes = curNextNodes.map(
                                                    item3 => {
                                                        if (
                                                            item3.selectId ===
                                                            selectId
                                                        ) {
                                                            if (nodeGroup) {
                                                                item3.isSelect =
                                                                    "true";
                                                            } else {
                                                                item3.isSelect =
                                                                    item3.isSelect !==
                                                                        "true" ||
                                                                        curNextNodes.length ===
                                                                        1
                                                                        ? "true"
                                                                        : "false";
                                                            }
                                                        } else {
                                                            if (nodeGroup) {
                                                                item3.isSelect =
                                                                    "false";
                                                            }
                                                        }
                                                        return item3;
                                                    }
                                                );
                                                setFieldsValue({
                                                    curNextNodes: actionNextNodes
                                                });
                                            }
                                        });
                                    }
                                }}
                                key={selectId}
                            >
                                <span>
                                    {isDone === "true" ? (
                                        <span style={{ color: "#108ee9" }}>
                                            {"[已]"}
                                        </span>
                                    ) : (
                                            ""
                                        )}
                                    {selectName}
                                    {}
                                </span>
                                {checked &&
                                    authors &&
                                    JSON.stringify(authors) !== "{}" ? (
                                        <List.Item.Brief>
                                            <WarpSegmentedPull
                                                myFetch={myFetch}
                                                value={authors}
                                                nodeName={item.nodeName}
                                                onChange={value => {
                                                    validateFields(err => {
                                                        if (!err) {
                                                            let actionNextNodes = curNextNodes.map(
                                                                (item3,index3) => {
                                                                    if (
                                                                        item3.selectId ===
                                                                        selectId
                                                                    ) {
                                                                        for (const key in authors) {
                                                                            item3.authors[
                                                                                key
                                                                            ] = {
                                                                                ...item3
                                                                                    .authors[
                                                                                key
                                                                                ],
                                                                                selectAuthorNewApih5:
                                                                                    value[
                                                                                        key
                                                                                    ]
                                                                                        .selectAuthorNewApih5
                                                                            };
                                                                        }
                                                                    }
                                                                    return item3;
                                                                }
                                                            );
                                                            setFieldsValue({
                                                                curNextNodes: actionNextNodes
                                                            });
                                                        }
                                                    });
                                                }}
                                            />
                                        </List.Item.Brief>
                                    ) : null}
                            </CheckboxItem>
                        );
                    })}
                </List>
            </Modal>
        );
    }
}
class SegmentedPull extends Component {
    constructor(props) {
        super(props);
        const { value } = this.props;
        this.values = [
            { authorId: "Author",authorName: "办理人" },
            { authorId: "SecondAuthor",authorName: "代理人" },
            { authorId: "Reader",authorName: "传阅" }
        ];
        let selectedIndex = 0;
        for (let i = 0; i < this.values.length; i++) {
            let { authorId } = this.values[i];
            if (value[authorId] && value[authorId].isSelect === "true") {
                selectedIndex = i;
                break;
            }
        }
        // console.log(selectedIndex)
        this.state = {
            selectedIndex,
            treeData: [],
            loading: false,
            nodeName: props.nodeName,
            data: [],

            nmByinitialValueGetEd: false,
            nmByTreeDataGetEd: false,


            dbByinitialValueGetEd: false,
            dbByTreeDataGetEd: false,
            // datas: [],
            // flag: true
        };
    }
    onChange = selectedIndex => {
        this.setState({
            selectedIndex
        });
    };
    componentDidMount() {
        if (this.props.onChange) {
            this.props.onChange(this.props.value);
        }
    }

    //获取匿名数据
    //@type<string>  (initialValue | treeData)
    refresh = (values,type) => {
        const { nmByinitialValueGetEd,nmByTreeDataGetEd } = this.state;
        const { myFetch } = this.props;
        const arrayBParams = Array.isArray(values) ? values : [values];
        if (!arrayBParams || !arrayBParams.length || (JSON.stringify(arrayBParams[0]) === "{}")) {
            return;
        }
        if (type === "initialValue" && !nmByinitialValueGetEd) {
            this.setState({ nmByinitialValueGetEd: true })
            myFetch("getZjPrExpertAnonymousListByUserList",{ userList: arrayBParams,typeFlag: "1" }).then(
                ({ success,message,data }) => {
                    if (success) {
                        this.setState({
                            // datas: data,
                            nmByinitialValueGetEd: true,
                            [`nmBy${type}`]: data
                        })
                    } else {
                        Toast.fail(message)
                    }
                }
            );
        } else if (type === "treeData" && !nmByTreeDataGetEd) {
            this.setState({ nmByTreeDataGetEd: true })
            myFetch("getZjPrExpertAnonymousListByUserList",{ userList: arrayBParams,typeFlag: "1" }).then(
                ({ success,message,data }) => {
                    if (success) {
                        this.setState({
                            [`nmBy${type}`]: data
                        })
                    } else {
                        Toast.fail(message)
                    }
                }
            );
        }

    }

    //获取待办数量
    //@type<string>  (initialValue | treeData)
    refreshByDB = (values,type) => {
        const { dbByinitialValueGetEd,dbByTreeDataGetEd } = this.state;
        const { myFetch } = this.props;
        const arrayBParams = Array.isArray(values) ? values : [values];
        if (!arrayBParams || !arrayBParams.length || (JSON.stringify(arrayBParams[0]) === "{}")) {
            return;
        }
        if (type === "initialValue" && !dbByinitialValueGetEd) {
            this.setState({ dbByinitialValueGetEd: true })
            myFetch("getZjPrExpertAnonymousListByUserList",{ userList: arrayBParams,typeFlag: "0" }).then(
                ({ success,message,data }) => {
                    if (success) {
                        this.setState({
                            // datas: data,
                            dbByinitialValueGetEd: true,
                            [`dbBy${type}`]: data
                        })
                    } else {
                        Toast.fail(message)
                    }
                }
            );
        } else if (type === "treeData" && !dbByTreeDataGetEd) {
            this.setState({ dbByTreeDataGetEd: true })
            myFetch("getZjPrExpertAnonymousListByUserList",{ userList: arrayBParams,typeFlag: "0" }).then(
                ({ success,message,data }) => {
                    if (success) {
                        this.setState({
                            [`dbBy${type}`]: data
                        })
                    } else {
                        Toast.fail(message)
                    }
                }
            );
        }

    }



    loadTree = () => {
        //加载树形结构
        const { myFetch } = this.props;
        myFetch("getSysDepartmentUserAllTree").then(
            ({ success,code,message,data }) => {
                if (success) {
                    this.setState({ treeData: [data],loading: false });
                }
            }
        );
    };
    render() {
        const { selectedIndex,loading,
            nmByinitialValue = [],nmBytreeData = [],dbByinitialValue = [],dbBytreeData = [],
            nmByinitialValueGetEd,nmByTreeDataGetEd,dbByinitialValueGetEd,dbByTreeDataGetEd,
            nodeName,
        } = this.state;
        const { getFieldDecorator } = this.props.form;
        const { value,myFetch } = this.props;

        return loading ? (
            <ActivityIndicator text="Loading..." />
        ) : (
                <div>
                    <SegmentedControl2
                        onChange={this.onChange}
                        value={value}
                        selectedIndex={selectedIndex}
                    />
                    {this.values.map(({ authorId },index) => {
                        if (value[authorId]) {

                            if (nodeName === '公司专家（三位）') {
                                if (value[authorId]["selectAuthorNewApih5"] && !nmByinitialValueGetEd) {
                                    this.refresh(value[authorId]["selectAuthorNewApih5"],"initialValue");
                                }
                                if (value[authorId]["initAuthorApih5"] && !nmByTreeDataGetEd) {
                                    this.refresh(value[authorId]["initAuthorApih5"],"treeData");
                                }
                            }

                            if (nodeName === '事业部施工部负责人') {
                                if (value[authorId]["selectAuthorNewApih5"] && !dbByinitialValueGetEd) {
                                    this.refreshByDB(value[authorId]["selectAuthorNewApih5"],"initialValue");
                                }
                                if (value[authorId]["initAuthorApih5"] && !dbByTreeDataGetEd) {
                                    this.refreshByDB(value[authorId]["initAuthorApih5"],"treeData");
                                }
                            }


                            return (
                                <div
                                    key={index}
                                    style={{
                                        display:
                                            selectedIndex === index
                                                ? "block"
                                                : "none"
                                    }}
                                >
                                    {isMobile()
                                        ? getFieldDecorator(
                                            authorId + "[selectAuthorNewApih5]",
                                            {
                                                valuePropName: "defaultValue",
                                                initialValue:
                                                    value[authorId][
                                                    "selectAuthorNewApih5"
                                                    ] || []
                                            }
                                        )(
                                            <PullPersionMobile
                                                search={
                                                    value[authorId]
                                                        .initAuthorApih5SearchApi
                                                }
                                                searchParamsKey={"search"}
                                                searchApi={
                                                    value[authorId]
                                                        .initAuthorApih5SearchApi
                                                        ? value[authorId]
                                                            .initAuthorApih5SearchApi
                                                        : "getSysDepartmentUserAllTree"
                                                }
                                                myFetch={myFetch}
                                                selectType={"2"}
                                                edit={
                                                    value[authorId].isSelect ===
                                                    "true"
                                                }
                                                treeData={
                                                    value[authorId]
                                                        .initAuthorApih5
                                                }
                                                useCollect //开启收藏功能
                                                collectApi="appGetSysFrequentContactsList" //查询收藏人员  接受后台参数[{xx:xxx,...}]
                                                collectApiByAdd="appAddSysFrequentContacts" //新增收藏人员  传给后台的参数[{xx:xxx,...}]
                                                collectApiByDel="appRemoveSysFrequentContacts" //删除收藏人员  传给后台的参数[{xx:xxx,...}]
                                            />
                                        )
                                        : getFieldDecorator(
                                            authorId + "[selectAuthorNewApih5]",
                                            {
                                                valuePropName: "defaultValue",
                                                initialValue: nodeName === '公司专家（三位）' ? nmByinitialValue :
                                                    (nodeName === '事业部施工部负责人' ? dbByinitialValue : (value[authorId][
                                                        "selectAuthorNewApih5"
                                                    ] || [])),
                                                onChange: (vals) => {
                                                    //这块引发原因是初始值居然是值的问题...
                                                    if (nodeName === '公司专家（三位）') {
                                                        this.setState({
                                                            nmByinitialValue: vals
                                                        })
                                                    }
                                                    if (nodeName === '事业部施工部负责人') {
                                                        this.setState({
                                                            dbByinitialValue: vals
                                                        })
                                                    }
                                                }
                                            }
                                        )(
                                            <PullPersion
                                                search={
                                                    value[authorId]
                                                        .initAuthorApih5SearchApi
                                                }
                                                searchParamsKey={"search"}

                                                searchApi={
                                                    value[authorId]
                                                        .initAuthorApih5SearchApi
                                                        ? value[authorId]
                                                            .initAuthorApih5SearchApi
                                                        : "getSysDepartmentUserAllTree"
                                                }
                                                myFetch={myFetch}
                                                selectType={"2"}
                                                edit={
                                                    value[authorId].isSelect ===
                                                    "true"
                                                }
                                                treeData={
                                                    nodeName === '公司专家（三位）' ? nmBytreeData : (nodeName === '事业部施工部负责人' ? dbBytreeData : value[authorId].initAuthorApih5)
                                                }
                                                // treeData={
                                                //     value[authorId]
                                                //         .initAuthorApih5
                                                // }
                                                useCollect //开启收藏功能
                                                collectApi="appGetSysFrequentContactsList" //查询收藏人员  接受后台参数[{xx:xxx,...}]
                                                collectApiByAdd="appAddSysFrequentContacts" //新增收藏人员  传给后台的参数[{xx:xxx,...}]
                                                collectApiByDel="appRemoveSysFrequentContacts" //删除收藏人员  传给后台的参数[{xx:xxx,...}]
                                            />
                                        )}
                                </div>
                            );
                        } else {
                            return null;
                        }
                    })}
                </div>
            );
    }
}
const WarpSegmentedPull = createForm({
    mapPropsToFields: props => {
        return {
            Author: createFormField(props.value.Author),
            SecondAuthor: createFormField(props.value.SecondAuthor),
            Reader: createFormField(props.value.Reader)
        };
    },
    onValuesChange: (props,changedValues,allValues) => {
        if (props.onChange) {
            props.onChange(allValues);
        }
    }
})(SegmentedPull);

class SegmentedControl2 extends Component {
    constructor(props) {
        super(props);
        this.state = {
            selectedIndex: 0,
            treeData: [],
            loading: false
        };
    }
    onChange = selectedIndex => {
        this.props.onChange(selectedIndex);
    };
    render() {
        const { value,selectedIndex } = this.props;
        const _values = [
            { authorId: "Author",authorName: "办理人" },
            { authorId: "SecondAuthor",authorName: "代理人" },
            { authorId: "Reader",authorName: "传阅" }
        ];
        return (
            <div className="am-segment" role="tablist">
                {_values.map(({ authorId,authorName },index) => {
                    if (value[authorId]) {
                        const selected = selectedIndex === index;
                        const { isFree } = value[authorId];
                        const disabled = isFree !== "true";
                        return (
                            <div
                                key={index}
                                onClick={e => {
                                    e.preventDefault();
                                    !disabled && this.onChange(index);
                                }}
                                style={disabled ? { opacity: 0.5 } : {}}
                                className={
                                    selected
                                        ? "am-segment-item am-segment-item-selected"
                                        : "am-segment-item"
                                }
                            >
                                <div className="am-segment-item-inner" />
                                {authorName}
                            </div>
                        );
                    } else {
                        return null;
                    }
                })}
            </div>
        );
    }
}
// const closest = function(el, selector) {
//   const matchesSelector =
//     el.matches ||
//     el.webkitMatchesSelector ||
//     el.mozMatchesSelector ||
//     el.msMatchesSelector;
//   while (el) {
//     if (matchesSelector.call(el, selector)) {
//       return el;
//     }
//     el = el.parentElement;
//   }
//   return null;
// };

export {
    actionFlow,
    clickFlowButton,
    FlowButtonGroup,
    FlowSelectModal,
    FlowForm
};
