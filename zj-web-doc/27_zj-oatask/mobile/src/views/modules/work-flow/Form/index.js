import React,{ Component } from "react";
import { goBack } from "connected-react-router";
import { Flex,NavBar,Icon,Toast } from "antd-mobile";
import { Form,Input,Tabs,Timeline,Icon as IconByAntd,Spin,Modal,Button } from "antd";
import moment from "moment";
import {
    FlowSelectModal,
    actionFlow,
    clickFlowButton,
    FlowButtonGroup,
    FlowForm
} from "../flow";
import styles from "./style.less";
import QnnForm from "../../qnn-table/qnn-form";
import $ from "jquery";
import ntkoBrowser from '../ntko/officecontrol/ntkobackground.min.js';
const TabPane = Tabs.TabPane; 

class Home extends Component {
    static getDerivedStateFromProps(props,state) {
        let { workId = "add",flowId,title = "title" } = props.match.params;
        let { isInQnnTable,rowData,workFlowConfig } = props;
        /* 
            pc端就算rowData存在也未必是流程编辑
            因为 可能会从一个数据列表里面选择某条数据然后发起流程
            这时候的发起会带有rowData 但是只是创建流程 并不是发起
            所以只要是流程数据必须得代用flowId
            注意在下面：componentDidMount和componentDidUpdate中也需要注意**
        */
        if (isInQnnTable && rowData && rowData.flowId) {
            //这是处理流程
            workId = rowData.workId;
            title = rowData.title;
            flowId = rowData.flowId;
        } else if (isInQnnTable) {
            //新增流程时候
            flowId = props.flowId;
            title = workFlowConfig.title;
        }
        //移动端新增时title不是处理时候的title
        if (workId === "add") {
            title = workFlowConfig.title;
        }
        let obj = {
            ...state,
            ...props,
            // isNeedClassifyConfig: (!state.flowFormColumn.length) ? true : (!state.isNeedClassifyConfig && props.isNeedClassifyConfig ? true : false),
            // flowFormColumn: _column, 
            workId, //为add即是新增流程  为其他则是从列表点击进入的
            flowId,
            title: title //流程标题字段title
        };
        return obj;
    }

    constructor(props) {
        super(props);
        let { isInQnnTable,rowData } = props;
        let {
            workId = "add",
            flowId,
            title = "title"
        } = this.props.match.params;
        //isInQnnTable存在肯定是用的qnn-table rowData不存在是新增
        if (isInQnnTable && rowData) {
            //这是处理流程
            workId = rowData.workId;
            title = rowData.title;
            flowId = rowData.flowId;
        } else if (isInQnnTable) {
            //新增流程时候
            workId = "add";
            flowId = props.flowId;
            title = props.title;
        }

        let editDocCdnAddress = this.props.editDocCdnAddress || "http://cdn.apih5.com/ntko";
        // let editDocCdnAddress = this.props.editDocCdnAddress || "http://192.168.3.3:8000";
        this.state = {
            flowFormColumn: [],
            flowButtons: [],
            formConfig: this.props.formConfig, //表单项配置
            workId, //为add即是新增流程  为其他则是从列表点击进入的
            flowId,
            apiNameByAdd: this.props.apiNameByAdd,
            apiNameByUpdate: this.props.apiNameByUpdate,
            apiNameByGet: props.apiNameByGet,
            title: title, //流程标题字段title
            loading: true,

            //以下四个属性只需会有一个参数 值为数据  需要返回新的数据
            //申请时提交的参数自定义格式化
            createParamsFormat:
                props.createParamsFormat ||
                function (obj) {
                    return obj;
                },
            //打开流程时提交的参数自定义格式化
            openParamsFormat:
                props.openParamsFormat ||
                function (obj) {
                    return obj;
                },
            //打开流程时获得的数据自定义格式化
            flowDataFormat:
                props.flowDataFormat ||
                function (obj) {
                    return obj;
                },
            fieldsCURD:
                props.fieldsCURD ||
                function (obj) {
                    return obj;
                },
            flowImgLoading: true,
            flowImgShow: true,

            //是否需要归类处理数据
            isNeedClassifyConfig: true,

            //是否存在在线编辑的的文档
            isHaveDoc: this.props.isHaveDoc, // true,
            // isHaveDoc: true, // ,

            //文档编辑器静态目录
            editDocCdnAddress: editDocCdnAddress,

            //文档最大数量
            docMaxNumber: this.props.docMaxNumber || 9999999999,

            //文档编辑器页面地址(相对文档编辑器静态目录)
            editDocAddress: editDocCdnAddress + (this.props.editDocAddress || "/editindex.html"),

            //红头模板
            //注意 id必須是1開頭的數字** id必须是number类型  最小值11  
            redModels: this.props.redModels || [],
            // redModels: [
            //     {
            //         id: 11, //用于文档页面菜单按钮排序和点击后区使用哪个模板使用
            //         name: "黑龙江省交通运输厅文件", //菜单按钮名字
            //         url: "http://localhost:8000/red2.doc"
            //     },
            //     {
            //         id: 12, //用于文档页面菜单按钮排序和点击后区使用哪个模板使用
            //         name: "模板二", //菜单按钮名字
            //         url: "http://localhost:8000/red1.doc"
            //     }
            // ],

            //上传完后返回的数据 array
            uploadRes: null,
            // uploadRes: [
            //     {
            //         url: 'http://114.115.200.165:88/apizxlw/downloadFile?filePath=upload/1DD7RULOD0043400A8C0000019CB1587.docx&downName=222.docx',
            //         name: "222.docx"
            //     }
            // ]

            //处理时提交的参数自定义格式化
            // processParamsFormat:
            //   props.processParamsFormat ||
            //   function(obj) {
            //     return obj;
            //   }

            //追加放上来的节点  提交时候需要拆出去提交
            pushNodeData: []
        };
        this.rootBody = {
            apiTitle: props.apiTitle,
            selectAuthorNewFlag: "1"
        };
        this.actionFlow = actionFlow.bind(this);
        this.clickFlowButton = clickFlowButton.bind(this);
        this.delApiBody = this.delApiBody.bind(this);
    }
    updateRootBody = newRootBody => {
        this.rootBody = {
            ...this.rootBody,
            ...newRootBody
        };
    };

    componentDidMount() {
        // const { isHaveDoc } = this.state;
        // if (isHaveDoc) {
        //     ntkoBrowser = require('../ntko/officecontrol/ntkobackground.min.js');
        // }
        // console.log(ntkoBrowser)

        //在这需要执行一遍 componentDidUpdate 可能会不执行
        let { workId = "add" } = this.state;
        const { isInQnnTable,rowData } = this.props;
        if (isInQnnTable) {
            if (rowData && rowData.flowId) {
                // workId = rowData ? rowData.workId : 'add';
                workId = rowData.workId;
            }
        } 
        if (workId === "add") {
            this.openFlow(this.props.formConfig.map(item => {
                if (item.disabled) {
                    item.disabled = false;
                }
                return item;
            }));
        } else {
            this.openFlowByPre();
        }
    }

    //操作文档的方法集合
    docFns() {
        let { editDocAddress,uploadRes = [],redModels = [],editDocCdnAddress } = this.state;
        const {
            loginAndLogoutInfo: {
                loginInfo: {
                    token,
                    userInfo: {
                        realName,
                        // userId,
                        // userKey
                    }
                }
            },
            myPublic: {
                domain,
                appInfo: {
                    name
                }
            }
        } = this.props;
        const _this = this;
        if (!uploadRes) {
            uploadRes = []
        }

        //给子页面传递数据 用json方式传不过去 必须编码后传输
        //前五个参数固定(token、用户名、上传地址、项目名、红头模板)
        //后续不可再这里面直接添加参数 （如果必须在这添加参数需要到cdn文件/editindex.html中改对应的接收索引）
        let childrenData = [token,realName,`${domain}ntkoUploadOffice`,name,escape(JSON.stringify(redModels))];

        return {
            //判断插件是否安装 
            isInitEd(cb) {
                let ntkoed = ntkoBrowser.ExtensionInstalled();
                if (ntkoed) {
                    //在父页面定义的跨浏览器插件应用程序关闭事件响应方法，且方法名不能自定义，必须是ntkoCloseEvent
                    function ntkoCloseEvent() {
                        // console.log("跨浏览器插件应用程序窗口已关闭")
                        // if (CLOSEVENT) alert("跨浏览器插件应用程序窗口已关闭!");
                    }

                    //在父页面定义的用于接收子页面回传值的方法，方法名可以自定义，定义后的方法名需要在子页面中通过window.external.SetReturnValueToParentPage进行注册
                    function OnData(type,data,oldUrl) {
                        switch (type) {
                            //新增文档
                            case "uploadResByAddDoc":
                                uploadRes.push(JSON.parse(data).data);
                                _this.setState({
                                    uploadRes: uploadRes
                                })
                                break

                            //编辑文档
                            case "uploadResByEditDoc":
                                _this.setState({
                                    uploadRes: uploadRes.map(item => {
                                        if (item.url === oldUrl) {
                                            item = JSON.parse(data).data;
                                        }
                                        return item;
                                    })
                                })
                                break
                            default:
                                break;
                        }
                    }
                    window.ntkoCloseEvent = ntkoCloseEvent;
                    window.OnData = OnData;

                    cb();
                } else {
                    //提示用户安装 
                    Modal.warning({
                        title: <div>您的浏览器未安装插件！请点击下面【下载插件】进行下载安装。 <br /><sub>(注意：请使用Chrome浏览器)</sub></div>,
                        content: <a href={`${editDocCdnAddress}/ntko.exe`}>下载插件 (15.8MB)</a>,
                    });
                }
            },

            //创建文档
            createDoc() {
                this.isInitEd(() => {
                    let url = `${editDocAddress}?cmd=1&t=${new Date().getTime()}`;

                    //父页面往子页面传值
                    //将token和用户信息传入文档窗口页面
                    window.ntkoSendDataToChildtext = function (ab) {
                        ntkoBrowser.ntkoSendDataToChild(url,childrenData);
                    };

                    //打开窗口
                    ntkoBrowser.openWindow(url);
                });
            },

            //打开文档
            openDoc(data) {
                this.isInitEd(() => {
                    let url = `${editDocAddress}?cmd=2&t=${new Date().getTime()}`;

                    //第四个参数为点击需要打开的文档数据(json串也无法传入)只能将附件某些数据传入
                    childrenData.push(data.url);
                    //父页面往子页面传值
                    //将token和用户信息传入文档窗口页面
                    window.ntkoSendDataToChildtext = function (ab) {
                        ntkoBrowser.ntkoSendDataToChild(url,childrenData);
                    };

                    //打开窗口
                    ntkoBrowser.openWindow(url);

                });
            }

        }

    }

    componentDidUpdate(prevProps,prevState) {
        let _prop = this.props;
        let { workId = "add",isNeedClassifyConfig } = this.state;
        const { isInQnnTable,rowData } = _prop;
        if (isInQnnTable) {
            if (rowData && rowData.flowId) {
                workId = rowData.workId;
            }
        }
        if (isNeedClassifyConfig) {
            if (workId === "add") {
                this.openFlow(_prop.formConfig.map(item => {
                    if (item.disabled) {
                        item.disabled = false;
                    }
                    return item;
                }));
            } else {
                this.openFlowByPre();
            }
        }
    }
    openFlow(_formConfig) {
        //新增
        //需要将optionConent 删除 
        const { webTitle } = this.props;
        let formConfig = this.state.formConfig || _formConfig;
        let navTitle = "";
        if (webTitle && typeof webTitle === "function") {
            navTitle = webTitle(this.props);
        } else if (webTitle) {
            navTitle = webTitle;
        } else {
            navTitle = "审批申请";
        }

        const _column = formConfig
            .filter(item => {
                let { addShow = true } = item;
                return addShow;
            })
            .map(item => {
                let { initialValue,hide } = item;
                // if (item.disabled === true) {
                //     item.disabled = false;
                // }
                if (item.qnnDisabled === true) {
                    item.disabled = true;
                }
                if (typeof initialValue === "function") {
                    item.initialValue = initialValue({
                        ...this.props
                    });
                }
                if (typeof hide === "function") {
                    item.hide = hide({
                        ...this.props
                    });
                }
                return item;
            });
        if (this.props.changeIsNeedClassifyConfig) {
            this.props.changeIsNeedClassifyConfig();
        }
        this.setState({
            loading: false,
            navTitle,
            flowFormColumn: _column,
            flowButtons: [{ buttonClass: "createOpenFlow" }],
            isNeedClassifyConfig: false,
            startSettingFlag: "1"
        });
    }

    openFlowByPre() {
        // console.log('处理')
        //处理
        const {
            formConfig,
            apiNameByGet,
            apiNameByUpdate,
            workId,
            flowId,
            title,
            openParamsFormat,
            flowDataFormat,
            fieldsCURD
        } = this.state;
        if (typeof flowId === "function") {
            flowId(this.props);
        }
        const newFormConfig = [...formConfig];
        const _column = newFormConfig.map(item => {
            item.disabled = true;
            return item;
        });

        const { myFetch,form,noForm } = this.props;
        const { setFieldsValue } = form;
        const body = {
            flowId,
            workId,
            apiName: apiNameByGet,
            apiType: "POST",
            title: window.decodeURI(title)
        };
        const newBody = openParamsFormat(body,{ ...this.props });
        // Toast.loading("正在加载数据");
        myFetch("openFlow",newBody).then(({ success,data,message }) => {
            // Toast.hide();
            if (success) {
                const {
                    apiData: apiDataStr,
                    nodeVars,
                    flowVars,
                    flowNode: { nodeId,trackId },
                    flowButtons,
                    flowHistoryList,
                    flowWebUrl
                } = data;
                const apiData = apiDataStr ? JSON.parse(apiDataStr) : {};
                // console.log('apiData：', apiData)
                // const apih5FlowStatus = apiData.apih5FlowStatus; //等于2是流程结束了
                // const accountId = nodeVars ? nodeVars.accountId : ""; //用于获取预览网址
                // const serialNumber = apiData.serialNumber; //用于获取预览网址

                

                //直接打开选人不操作别的
                if(noForm){
                    const { apiFlowId, workId, nodeVars, flowVars, flowNode: { nodeId, trackId }, apiName, apiBody, apiType, opinionContent, title } = data
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
                   

                    let subBtnInfo = flowButtons.filter(item=>{
                        return item.buttonClass === "com.horizon.wf.action.ActionSubmit"
                    })[0] || {};

                    let actionData = {
                        buttonId:subBtnInfo.buttonId,
                        buttonClass:subBtnInfo.buttonClass,
                        buttonName:subBtnInfo.buttonName,
                    };  

                    clickFlowButton.bind(this)(actionData, {
                        dispatch:this.props.dispatch,
                        actionFlow: this.actionFlow,
                        onCancel:() => { console.log('关闭') },
                        form:this.props.form

                    })

                    this.setState({ 
                        isNeedClassifyConfig: false,
                    })

                    return;
                }



                this.updateRootBody({
                    workId,
                    flowId,
                    nodeId,
                    trackId,
                    nodeVars,
                    flowVars,
                    apiName: apiNameByUpdate,
                    apiType: "POST",
                    title: window.decodeURI(title)
                });

                let formEditFlag = false; //是否可编辑
                if (nodeVars && nodeVars.formEditFlag === "1") {
                    formEditFlag = true;
                }
                const apiBody = {};
                const opinions =
                    flowVars.opinionFieldName &&
                    flowVars.opinionFieldName.split("_").map(item => {
                        let itemArr = item.split("|");
                        return {
                            field: itemArr[0],
                            type: "textarea",
                            label: itemArr[1],
                            voice: true,
                            disabled: true,
                            opinionField: true //领导意见框标识 下面会用到
                        };
                    });
                let attachmentListName = flowVars.attachmentListName
                    ? flowVars.attachmentListName.split("_").map(item => {
                        let itemArr = item.split("|");
                        return {
                            field: itemArr[0],
                            fieldName: itemArr[0],
                            //   type: "cameraByNoName",
                            type: "camera",
                            cameraConfig: {
                                type: "camera", //类型
                                showName: false //显示文件名字  默认false
                            },
                            label: itemArr[1],
                            disabled: true,
                            fetchConfig: {
                                //配置后将会去请求下拉选项数据
                                apiName: window.configs.domain + "upload"
                            }
                        };
                    })
                    : [];

                if (nodeVars && nodeVars.fileOperationFlag === "1") {
                    attachmentListName.push({
                        // type: "cameraByNoName",
                        type: "camera",
                        cameraConfig: {
                            type: "camera", //类型
                            showName: false //显示文件名字  默认false
                        },
                        field: "zlAttachmentList",
                        fieldName: "zlAttachmentList",
                        label: "上传附件",
                        fetchConfig: {
                            //配置后将会去请求下拉选项数据
                            apiName: window.configs.domain + "upload"
                        }
                    });
                }
                let flowFormColumn;
                if (opinions) {
                    flowFormColumn = [].concat(
                        [],
                        _column,
                        attachmentListName,
                        opinions
                    );
                } else {
                    flowFormColumn = [].concat([],_column,attachmentListName);
                }



                flowFormColumn = flowFormColumn.map(item => {
                    let {
                        field,
                        type,
                        disabled,
                        initialValue,
                        hide,
                        opinionField
                    } = item; //, treeNodeOption = {}
                    field = field ? field.replace(/(apiBody.)/g,"") : "";
                    if (type === "datetime") {
                        //格式化为moent格式
                        apiBody["apiBody." + field] = apiData[field]
                            ? moment(apiData[field])
                            : moment("1970");
                    } else if (type === "textarea") {
                        apiBody["apiBody." + field] = apiData[field]
                            ? apiData[field].replace(/(<br\/>)/g,"\r\n")
                            : null;
                    } else if (type === "linkage") {
                        //联动类型
                        const forLinkage = _item => {
                            let _dField = _item.form.field.replace(
                                /(apiBody.)/g,
                                ""
                            );
                            if (_item.form.field.indexOf("apiBody.") === -1) {
                                apiBody["apiBody." + _item.form.field] =
                                    apiData[_dField] || "";
                            } else {
                                apiBody[_item.form.field] =
                                    apiData[_dField] || "";
                            }
                            if (_item.children) {
                                forLinkage(_item.children);
                            }
                        };
                        forLinkage(item.children);
                    } else {
                        apiBody["apiBody." + field] = apiData[field];
                    }
                    if (typeof initialValue === "function") {
                        item.initialValue = initialValue({
                            ...this.props
                        });
                    }
                    if (typeof hide === "function") {
                        item.hide = hide({
                            ...this.props
                        });
                    }
                    disabled =
                        type !== "upload"
                            ? disabled
                            : nodeVars && nodeVars.fileOperationFlag === "1"
                                ? false
                                : true;
                    if (formEditFlag && !opinionField) {
                        //设置为能编辑
                        disabled = false;
                    }
                    return {
                        ...item,
                        disabled
                    };
                });

                //根据后台返回的字段判断哪个字段需要变为意见栏字段
                //已办是无需能变编辑（才是采用flowButtons字段判断该字段存在即为待办）
                if (nodeVars && nodeVars.opinionField && flowButtons && flowButtons.length) {
                    // console.log(nodeVars.opinionField)
                    let curOptionDomId = `apiBody.opinionContent`; //可输入的框
                    //异步一下
                    setTimeout(() => {
                        let curOptionDom = document.getElementById(
                            curOptionDomId
                        );
                        let opdOpt =
                            apiBody[`apiBody.${nodeVars.opinionField}`];
                        if (opdOpt) {
                            opdOpt = opdOpt.replace(/\r\n/gi,"<br/>");
                            $(curOptionDom).before(
                                `<div style="line-height:1.3;color:#777;font-size:14px;margin-bottom:6px">${opdOpt}</div>`
                            ); //以前输入的内容
                        }
                    },200);

                    // flowFormColumn = flowFormColumn.map(item => {
                    //     let { field } = item; 
                    //     if (field === nodeVars.opinionField) { 
                    //         item.disabled = false;  
                    //         item.field = "opinionContent";
                    //         item.placeholder = "请输入..."; 
                    //     }
                    //     return item;
                    // });
                    flowFormColumn = flowFormColumn.map(item => {
                        let { field } = item;
                        field = field ? field.replace('apiBody.','') : field;
                        if (field === nodeVars.opinionField) {
                            item.disabled = false;
                            item.field = "apiBody.opinionContent";
                            item.placeholder = "请输入...";
                        }
                        if (field === nodeVars.opinionFieldTwo) {
                            item.disabled = false;
                            item.placeholder = "请输入...";
                        }
                        return item;
                    });
                }

                flowFormColumn = fieldsCURD(flowFormColumn,data,{
                    ...this.props
                });
                if (this.props.changeIsNeedClassifyConfig) {
                    this.props.changeIsNeedClassifyConfig();
                }
                this.setState(
                    {
                        flowFormColumn: flowFormColumn,
                        flowButtons: flowButtons && flowButtons.reverse(),
                        flowHistoryList,
                        flowWebUrl,
                        navTitle: "审批处理",
                        isNeedClassifyConfig: false,
                        startSettingFlag: nodeVars && nodeVars.startSettingFlag
                    },
                    () => {
                        let _fData = QnnForm.sFormatData(
                            apiBody,
                            flowFormColumn
                        );
                        let _newFData = flowDataFormat(
                            _fData,
                            { ...this.props },
                            data
                        ); 

                        //不设置延时的话值会一闪就没了  只要pc端出现的问题
                        // setTimeout(() => {
                            this.setState({
                                loading: false
                            });
                            // console.log('_newFData', _newFData);
                            setFieldsValue(_newFData);
                        // },500); 
                    }
                ); 
            } else {
                Toast.fail(message);
            }
        });
    }

    isMobile = () => {
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

    //删除传入数组中每个对象属性中的apiBody.
    delApiBody = (formConfig,d = "del") => {
        let _formConfig = [...formConfig].map(item => {
            let _item = {
                ...item
            };
            switch (item.type) {
                case "linkage":
                    const forLinkage = _item => {
                        let _form = {
                            ..._item.form,
                            field:
                                d === "del"
                                    ? _item.form.field.replace(
                                        /(apiBody.)/g,
                                        ""
                                    )
                                    : `apiBody.${_item.form.field}`
                        };
                        _item = { ..._item,form: { ..._form } };
                        if (_item.children) {
                            _item.children = forLinkage(_item.children);
                        }
                        return _item;
                    };
                    let _newItem = forLinkage(item.children);
                    _item.children = _newItem;
                    break;
                default:
                    _item.field =
                        d === "del"
                            ? item.field.replace(/(apiBody.)/g,"")
                            : `apiBody.${item.field}`;
            }
            return _item;
        });
        return _formConfig;
    };

    getPushNodeData = (res) => {
        const {
            myFetch,
            workFlowConfig: { pushNode },
            form
        } = this.props;
        const {
            flowId,
        } = this.state;
        const { getFieldValue,setFieldsValue } = form;
        if (typeof flowId === "function") {
            flowId(this.props);
        }
        if (pushNode) {
            //需要去请求别的下一步分支  然后选人  
            Toast.loading('loading',0)
            myFetch("getBaseFlowStartSettingListByFlow",{
                apih5FlowId: flowId,
                apih5WorkId: res.workId
            }).then(({ success,message,data }) => {
                Toast.hide();
                if (success) {
                    let curNextNodes = getFieldValue('curNextNodes');
                    curNextNodes = curNextNodes.concat(data);
                    setFieldsValue({
                        curNextNodes
                    })
                    this.setState({
                        pushNodeData: data
                    })
                } else {
                    Toast.fail(message,2);
                }
            });

        }
    }

    createOpenFlow = () => {
        const {
            apiNameByUpdate,
            apiNameByAdd,
            title,
            flowId,
            createParamsFormat
            // flowFormColumn
        } = this.state;
        const {
            myFetch,
            form,
            apiTitle,
            formConfig,
            btnCallbackFn = {},
            isInQnnTable,
            // workFlowConfig: { pushNode }
        } = this.props;
        if (typeof flowId === "function") {
            flowId(this.props);
        }

        const { refresh } = btnCallbackFn;
        const { validateFields } = form;
        validateFields((err,values) => {
            //将moment时间格式处理为时间戳
            let _apiBody = values["apiBody"];
            let _title = "";
            if (Array.isArray(title)) {
                //是数组需要拼接
                for (let i = 0; i < title.length; i++) {
                    let _tit = _apiBody[title[i]] || title[i];
                    _title += `${i === 0 ? "" : "-"}${_tit}`;
                }
            } else if (typeof title === "function") {
                _title = title(_apiBody);
            } else {
                _title = _apiBody[title] || title;
            }
            for (let i = 0,j = formConfig.length; i < j; i++) {
                let _field = formConfig[i].field.replace(/(apiBody.)/g,"");
                if (formConfig[i].type === "datetime") {
                    if (_apiBody[_field]) {
                        _apiBody[_field] = moment(_apiBody[_field]).valueOf();
                    }
                }
            }
            let _formConfig = this.delApiBody(formConfig);
            let _newApiBody = QnnForm.sFormatData(_apiBody,_formConfig,"get");
            values["apiBody"] = _newApiBody;
            if (!err) {
                const body = {
                    title: _title,
                    flowId,
                    apiNameByCreate: apiNameByUpdate,
                    apiName: apiNameByAdd,
                    apiType: "POST",
                    apiTitle: apiTitle,
                    apiBody: JSON.stringify(values["apiBody"])
                };
                const newBody = createParamsFormat(body,{ ...this.props });
                Toast.loading("正在创建流程",0);
                myFetch("createOpenFlow",newBody).then(
                    ({ success,message,data }) => {
                        Toast.hide();
                        if (success) {
                            //将按钮全部删除
                            this.setState({
                                flowButtons: []
                            });

                            Toast.success("创建流程成功");
                            if (isInQnnTable) {
                                refresh();
                            }

                            const {
                                workId,
                                nodeVars,
                                flowVars,
                                flowNode: { nodeId,trackId },
                                flowButtons,
                                apiName,
                                apiType,
                                apiBody,
                                title
                            } = data;
                            this.updateRootBody({
                                nodeVars,
                                flowId: data.flowId,
                                flowVars,
                                workId,
                                nodeId,
                                trackId,
                                apiName,
                                apiType,
                                apiBody,
                                title //: _title
                            });
                            const {
                                buttonId,
                                buttonClass,
                                buttonName
                            } = flowButtons[0];
                            const operateObj = {
                                operate: buttonId,
                                operateClazz: buttonClass,
                                operateText: buttonName,
                                operateFlag: 1,
                            };

                            this.actionFlow({ operateObj });
                        } else {
                            Toast.fail(message,2);
                        }
                    }
                );
            }
        });
    };
    callback(key) {
        // console.log(key);
    }
    setFlowImgShow() {
        this.setState({
            flowImgShow: !this.state.flowImgShow
        });
    }
    render() {
        const { dispatch,isInQnnTable } = this.props;
        const {
            flowButtons,
            flowFormColumn,
            flowHistoryList,
            navTitle,
            flowWebUrl,
            workId,
            flowId,
            flowImgLoading,
            loading,

            uploadRes,
            isHaveDoc,
            docMaxNumber
        } = this.state;
        const { getFieldDecorator } = this.props.form;
        getFieldDecorator("visible",{ initialValue: false })(<Input hidden />);
        getFieldDecorator("operateObj",{ initialValue: {} });
        getFieldDecorator("curNextNodes",{ initialValue: [] });


        const renderFlowHistory =
            flowHistoryList && flowHistoryList.length ? (
                <TabPane tab="操作历史" key="2">
                    <div
                        className={styles.flowHistory}
                        style={
                            !this.isMobile()
                                ? {
                                    height: "100%"
                                }
                                : {
                                    height: "90vh",
                                    overflowY: "scroll",
                                    paddingBottom: "10vh"
                                }
                        }
                    >
                        <Timeline style={{ padding: "16px" }}>
                            {flowHistoryList.map(
                                (
                                    {
                                        historyFlag,
                                        realName,
                                        nodeName,
                                        comments,
                                        actionTime
                                    },
                                    idnex
                                ) => {
                                    return (
                                        <Timeline.Item
                                            dot={
                                                historyFlag === "1" ? (
                                                    ""
                                                ) : (
                                                        <IconByAntd type="clock-circle-o" />
                                                    )
                                            }
                                            key={idnex}
                                        >
                                            <div>
                                                {nodeName}：{realName}
                                            </div>
                                            <div>{comments}</div>
                                            {actionTime ? (
                                                <div>
                                                    到达时间：
                                                    {moment(actionTime).format(
                                                        "YYYY-MM-DD HH:mm:ss"
                                                    )}
                                                </div>
                                            ) : (
                                                    ""
                                                )}
                                        </Timeline.Item>
                                    );
                                }
                            )}
                        </Timeline>

                        {flowWebUrl ? (
                            <div>
                                <Spin spinning={flowImgLoading}>
                                    <iframe
                                        onLoad={() => {
                                            this.setState({
                                                flowImgLoading: false
                                            });
                                        }}
                                        src={flowWebUrl}
                                        title="流程图"
                                        frameBorder="0"
                                        className={styles.flowIframe}
                                    />
                                </Spin>
                            </div>
                        ) : (
                                ""
                            )}
                    </div>
                </TabPane>
            ) : null;


        //新增文档的按钮
        const addDocBtn = (<div style={{ marginTop: "16px" }}>
            <Button onClick={() => { this.docFns().createDoc() }} icon="plus">新增文档</Button>
        </div>);

        //文档dom
        const officeDom = (<div id="flow-doc" className={styles.flowDocContainer}>
            {
                uploadRes ? <div>{
                    uploadRes.map((item,i) => {
                        return <div key={i}><a onClick={() => {
                            //打开文档
                            this.docFns().openDoc(item)
                        }}>{item.name}</a></div>
                    })
                }</div> : null
            }
            {
                uploadRes ? ((uploadRes.length < docMaxNumber) ? addDocBtn : null) : addDocBtn
            }
        </div>);

        const FlowCon = (
            <div>
                <FlowForm
                    {...this.props}
                    updateRootBody={this.updateRootBody}
                    flowFormColumn={flowFormColumn}
                    flowHistoryList={flowHistoryList}
                    delApiBody={this.delApiBody}
                >
                    {isHaveDoc ? officeDom : null}
                </FlowForm>

                <FlowButtonGroup
                    {...this.props}
                    clickFlowButton={this.clickFlowButton}
                    createOpenFlow={this.createOpenFlow}
                    flowButtons={flowButtons}
                />
                <FlowSelectModal {...this.props} flowId={flowId} workId={workId} actionFlow={this.actionFlow} pushNodeData={this.state.pushNodeData} />
            </div >
        );

        if (this.isMobile()) {
            return (
                <Spin spinning={loading}>
                    <Flex
                        className={`${styles.root} ${styles.rootByMobile}`}
                        direction={"column"}
                    >
                        <NavBar
                            style={{ width: "100%" }}
                            mode="dark"
                            icon={<Icon type="left" />}
                            onLeftClick={() => {
                                dispatch(goBack());
                            }}
                        >
                            {navTitle}
                        </NavBar>

                        {workId === "add" ? (
                            <div className={styles.FlowConByAdd}>{FlowCon}</div>
                        ) : (
                                <Tabs
                                    defaultActiveKey="1"
                                    onChange={this.callback}
                                    className={styles.myTable}
                                    style={{
                                        width: "100%",
                                        background: "#fff"
                                    }}
                                >
                                    <TabPane tab="详情" key="1">
                                        <div className={styles.FlowConByMobilePre}>
                                            {FlowCon}
                                        </div>
                                    </TabPane>
                                    {renderFlowHistory}
                                </Tabs>
                            )}
                    </Flex>
                </Spin>
            );
        } else {
            //pc端
            return (
                <Spin spinning={loading}>
                    <div
                        className={`${styles.pcroot} ${
                            isInQnnTable ? styles.pcrootByInQnnTable : null
                            }`}
                    >
                        {workId === "add" ? (
                            FlowCon
                        ) : (
                                <Tabs defaultActiveKey="1" onChange={this.callback}>
                                    <TabPane
                                        tab="详情"
                                        key="1"
                                        style={{ height: "100%" }}
                                    >
                                        {FlowCon}
                                    </TabPane>
                                    {renderFlowHistory}
                                </Tabs>
                            )}
                    </div>
                </Spin>
            );
        }
    }
}

const WrappedDynamicRule = Form.create({
    onValuesChange: (props,changedValues,allValues) => {
        if (props.fieldsValueChange) {
            props.fieldsValueChange(props,changedValues,allValues);
        }
    }
})(Home);
export default WrappedDynamicRule;
