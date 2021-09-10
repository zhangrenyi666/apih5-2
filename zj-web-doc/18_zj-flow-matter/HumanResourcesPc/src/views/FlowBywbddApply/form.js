import React,{
    Component
} from "react";
import {
    Form
} from "../modules/work-flow";
import { Divider,Button,notification } from 'antd';
import { PlusCircleOutlined,DeleteOutlined } from "@ant-design/icons"
import $ from "jquery"
import moment from "moment";

class index extends Component {


    state = {
        //家庭主要成员   --暂时无实际意义  实际可以写为一个number  
        memberList: [
            {
                _id: "memberList0"
            }
        ],
        getDataEd: false,
        memberListMax: 4,

        //表单数据
        formValues: {},
        //流程数据
        flowData: undefined
    }

    qnnForm = undefined;

    add = () => {
        this.DrawerPageScroll = $(`.ant-spin-container > div`).eq(2).scrollTop();

        const { flowData = {} } = this.state;
        if (flowData) {
            const { flowButtons = [] } = flowData;
            if (flowButtons.length) {
                notification.error({
                    message: "当前环境不可编辑！"
                })
                return;
            }
        }
        this.qnnForm.getValues(false,(vals) => {

            const newApiBody = {
                ...vals.apiBody
            }
            const newMemberList = this.state.memberList.map((item,index) => {
                //数据也需要跟着转  
                //一旦有了删除操作后就需要对 数据进行 下标归位了  
                newApiBody[`memberList${index}`] = vals.apiBody[`${item._id}`];
                //列表数据下标归零
                return {
                    ...item,
                    _id: `memberList${index}`
                }
            }).concat({
                _id: `memberList${this.state.memberList.length}`
            });

            this.setState({
                memberList: newMemberList,
                formValues: {
                    apiBody: {
                        ...newApiBody
                    }
                }
            });
        })
    }

    del = (index) => {
        this.DrawerPageScroll = $(`.ant-spin-container > div`).eq(2).scrollTop();

        const { flowData = {} } = this.state;
        if (flowData) {
            const { flowButtons = [] } = flowData;
            if (flowButtons.length) {
                notification.error({
                    message:"当前环境不可编辑！"
                })
                return;
            }
        }  

        this.qnnForm.getValues(false,(vals) => {
            // console.log(index,memberList, { 
            //     ...vals.apiBody,
            //     [`memberList${index}`]: undefined 
            // })
            const apiBody = vals.apiBody;
            const memberList = [];
            for (const key in apiBody) {
                if (/^(memberList)\d+/ig.test(key)) {
                    memberList.push({
                        _id: key,
                        ...apiBody[key]
                    })
                }
            }
            memberList.splice(index,1);
            apiBody[`memberList${index}`] = undefined;

            // console.log(memberList);
            // console.log(apiBody)

            this.setState({
                memberList: memberList,
                formValues: {
                    apiBody: apiBody
                }
            },() => {
                this.qnnForm.setValues({
                    apiBody: apiBody
                })
            });

        })
    }
    getConfig = () => {
        return {
            formType: "descriptions",
            descriptionsConfig: {
                column: 8,
                layout: "horizontal",
                bordered: true
            },
            workFlowConfig: {
                title: ["name","createTimeq","外部调入（出）审批"],
                apiNameByAdd: "addZjFlowTranOuter",
                apiNameByUpdate: "updateZjFlowTranOuter",
                apiNameByGet: "getZjFlowTranOuterDetails",
                flowId: "wbddApply",
                todo: "todoByTran",
                hasTodo: "hasTodoByTran",
            },
            formTailLayout: {
                wrapperCol: {
                    sm: {
                        // span: 12,
                        offset: 12
                    }
                }
            },
            //qnn-form配置
            formConfig: [
                {
                    type: 'component',
                    field: 'diyJJJ',
                    formItem: true,
                    Component: obj => {
                        return (
                            <Divider style={{ fontSize: '22px',color: '#545456',height: '38px',lineHeight: '38px',margin: '16px 0 0 0',textAlign: 'center' }} orientation="middle">外部调入（出）审批表</Divider>
                        );
                    }
                },
                {
                    label: '上报单位',
                    type: 'string',
                    field: 'reportUnit',
                    placeholder: '请输入...',
                    span: 12,
                    formItem: true,
                    style: {
                        border: 'none'
                    },
                    formItemLayout: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 3 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 21 }
                        }
                    },
                },
                {
                    label: '时间',
                    type: 'date',
                    required: true,
                    field: 'reportDate',
                    placeholder: '请输入...',
                    span: 12,
                    formItem: true,
                    style: {
                        border: 'none'
                    },
                    initialValue: () => {
                        return new Date().getTime()
                    },
                    formItemLayout: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 18 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 6 }
                        }
                    },
                },

                {
                    type: "string",
                    label: "workId",
                    field: "workId",
                    hide: function () {
                        return true
                    },
                    initialValue: function (obj) {
                        return obj.match.params["workId"];
                    }
                },
                {
                    type: "string",
                    label: "",
                    field: "outerId",
                    hide: function () {
                        return true
                    },
                },
                {
                    type: "string",
                    label: "",
                    field: "createTimeq",
                    hide: function () {
                        return true
                    },
                    initialValue: () => {
                        return moment(new Date()).format('YYYY-MM-DD')
                    }
                },

                {
                    label: '姓名',
                    type: 'string',
                    field: 'name',
                    required: true,
                    placeholder: '请输入...'
                },
                {
                    label: '性别',
                    type: 'select',
                    field: 'sex',
                    placeholder: '请输入...',
                    optionData: [{ label: "男",value: "0" },{ label: "女",value: "1" }]
                },
                {
                    label: '出生年月日',
                    type: 'date',
                    field: 'birth',
                    placeholder: '请输入...',
                    span: 2,
                },
                {
                    label: '民族',
                    type: 'string',
                    field: 'nation',
                    placeholder: '请输入...',
                    span: 2,
                },
                {
                    label: '文化程度',
                    type: 'string',
                    field: 'education',
                    placeholder: '请输入...'
                },
                {
                    label: '婚姻状况',
                    type: 'select',
                    field: 'maritalStatus',
                    placeholder: '请输入...',
                    optionData: [{ label: "已婚",value: "0" },{ label: "未婚",value: "1" }]
                },
                {
                    label: '参加工作时间',
                    type: 'date',
                    field: 'workTime',
                },
                {
                    label: '政治面貌',
                    type: 'string',
                    field: 'politicsStatus'
                },
                {
                    label: '籍贯',
                    type: 'string',
                    field: 'nativePlace',
                    span: 2,
                },
                {
                    label: '职业资格',
                    type: 'string',
                    field: 'qualification',
                    span: 2,
                },
                {
                    label: '职务',
                    type: 'string',
                    field: 'duty',
                },
                {
                    label: '职称',
                    type: 'string',
                    field: 'professional',
                },
                {
                    label: '现工作单位',
                    type: 'string',
                    field: 'currentUnit',
                    span: 4,
                },
                {
                    label: '拟调单位',
                    type: 'string',
                    field: 'toUnit',
                    span: 4,
                },
                {
                    label: '户口所在地',
                    type: 'string',
                    field: 'registeredPlace',
                    span: 2,
                },
                {
                    label: '联系电话',
                    type: 'string',
                    field: 'phone',
                    span: 2,
                },
                {
                    label: '家庭住址',
                    type: 'string',
                    field: 'address',
                    span: 4,
                },
                {
                    type: 'string',
                    label: '第一学历',
                    field: 'firstList',
                    placeholder: '请输入',
                    span: 8,
                    tdStyle: {},
                    children: {
                        descriptionsConfig: {
                            column: 7,
                            layout: "vertical",
                        },
                        formConfig: [
                            {
                                label: '开始时间',
                                type: 'date',
                                field: 'firstStartTime',
                            },
                            {
                                label: '结束时间',
                                type: 'date',
                                field: 'firstEndTime',
                            },
                            {
                                label: '毕业院校',
                                type: 'string',
                                field: 'firstGraduateSchool',
                                placeholder: '请输入...',
                            },
                            {
                                label: '专业',
                                type: 'string',
                                field: 'firstMajor',
                                placeholder: '请输入...',
                            },
                            {
                                label: '学制',
                                type: 'string',
                                field: 'firstLength',
                                placeholder: '请输入...',
                            },
                            {
                                label: '学位',
                                type: 'string',
                                field: 'firstDegree',
                                placeholder: '请输入...',
                            },
                            {
                                label: '是否统分',
                                type: 'select',
                                field: 'firstUnified',
                                placeholder: '请输入...',
                                optionData: [{ label: "是",value: "0" },{ label: "否",value: "1" }]
                            },
                        ]
                    }
                },
                {
                    type: 'string',
                    label: '最高学历',
                    field: 'highList',
                    placeholder: '请输入',
                    span: 8,
                    tdStyle: {},
                    children: {
                        descriptionsConfig: {
                            column: 7,
                            layout: "vertical",
                        },
                        formConfig: [
                            {
                                label: '开始时间',
                                type: 'date',
                                field: 'highStartTime',
                            },
                            {
                                label: '结束时间',
                                type: 'date',
                                field: 'highEndTime',
                            },
                            {
                                label: '毕业院校',
                                type: 'string',
                                field: 'highGraduateSchool',
                                placeholder: '请输入...',
                            },
                            {
                                label: '专业',
                                type: 'string',
                                field: 'highMajor',
                                placeholder: '请输入...',
                            },
                            {
                                label: '学制',
                                type: 'string',
                                field: 'highLength',
                                placeholder: '请输入...',
                            },
                            {
                                label: '学位',
                                type: 'string',
                                field: 'highDegree',
                                placeholder: '请输入...',
                            },
                            {
                                label: '是否统分',
                                type: 'select',
                                field: 'highUnified',
                                placeholder: '请输入...',
                                optionData: [{ label: "是",value: "0" },{ label: "否",value: "1" }]
                            },
                        ]
                    }
                },
                {
                    label: '工作简历',
                    type: 'textarea',
                    field: 'workExperience',
                    placeholder: '请输入...',
                    span: 8,
                    autoSize: {
                        minRows: 2,
                        // maxRows: 10
                    }
                },
                {
                    label: '调动理由',
                    type: 'textarea',
                    field: 'tranReason',
                    placeholder: '请输入...',
                    span: 8,
                    autoSize: {
                        minRows: 2,
                        // maxRows: 10
                    }
                },
                {
                    label: '调入岗位',
                    type: 'textarea',
                    field: 'toPost',
                    placeholder: '请输入...',
                    span: 8,
                    autoSize: {
                        minRows: 2,
                        // maxRows: 10
                    }
                },

                {
                    label: '爱人姓名',
                    type: 'string',
                    field: 'loverName',
                    placeholder: '请输入...',
                    span: 2,
                },
                {
                    label: '工作单位',
                    type: 'string',
                    field: 'loverUnit',
                    placeholder: '请输入...',
                    span: 2,
                },
                {
                    label: '户口所在地',
                    type: 'string',
                    field: 'loverRegisteredPlace',
                    placeholder: '请输入...',
                    span: 2,
                },
                {
                    label: '是否随调',
                    type: 'select',
                    field: 'loverTranFlag',
                    span: 2,
                    optionData: [{ label: "是",value: "0" },{ label: "否",value: "1" }]
                },
                {
                    type: "qnnForm",
                    field: "itemList",//不显示
                    formItem: true,
                    label: "明细",
                    textObj: {
                        add: "添加",
                        del: "删除"
                    },
                    canAddForm: true,
                    qnnFormConfig: {
                        formItemLayoutForm: {
                            labelCol: {
                                xs: { span: 6 },
                                sm: { span: 6 }
                            },
                            wrapperCol: {
                                xs: { span: 18 },
                                sm: { span: 18 }
                            }
                        },
                        formConfig: [
                            {
                                label: "类型",
                                type: 'select',
                                field: 'typeId',
                                // formItem:true,
                                required: true,
                                placeholder: '请选择',
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'YingJiWeiJiHeTongWaiLeiXing'
                                    }
                                },
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                },
                            },
                            {
                                type: "textarea",
                                label: "主要内容",
                                // formItem:true,
                                required: true,
                                placeholder: '请输入',
                                field: "content"
                            }
                        ]
                    }
                },
                {
                    type: 'string',
                    label: '家庭主要成员',
                    field: 'memberList',
                    placeholder: '请输入',
                    span: 8,
                    tdStyle: {},
                    children: {
                        descriptionsConfig: {
                            column: 7,
                            layout: "vertical",
                        },
                        formConfig: [
                            {
                                label: '家庭主要成员姓名',
                                type: 'string',
                                field: 'memberName',
                                placeholder: '请输入...',
                            },
                            {
                                label: '单位或学校',
                                type: 'string',
                                field: 'unitSchool',
                                placeholder: '请输入...',
                            },
                            {
                                label: '职务',
                                type: 'string',
                                field: 'memberDuty',
                                placeholder: '请输入...',
                            }
                        ]
                    }
                },
                
                ...this.state.memberList.map((item,index) => {
                    return {
                        key: item._id,
                        type: 'string',
                        label: <div key={item._id + index}>
                            {`家庭主要成员${index + 1}`}
                            <Button
                                key={item._id + index}
                                disabled={1 === this.state.memberList.length}
                                onClick={() => {
                                    this.del(index)
                                }} type="link" danger icon={<DeleteOutlined />}>
                                删除
                            </Button>
                        </div>,
                        // field: `memberList${index}`,
                        field: `${item._id}`,
                        placeholder: '请输入',
                        span: 8,
                        tdStyle: {},
                        children: {
                            descriptionsConfig: {
                                column: 7,
                                layout: "vertical",
                            },
                            formConfig: [
                                {
                                    label: '家庭主要成员姓名',
                                    type: 'string',
                                    field: 'memberName',
                                    key: item._id + 'memberName',
                                    placeholder: '请输入...',
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 4 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 20 }
                                        }
                                    },
                                },
                                {
                                    label: '单位或学校',
                                    type: 'string',
                                    field: 'unitSchool',
                                    key: item._id + 'unitSchool',
                                    placeholder: '请输入...',
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 4 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 20 }
                                        }
                                    },
                                },
                                {
                                    label: '职务',
                                    type: 'string',
                                    field: 'memberDuty',
                                    placeholder: '请输入...',
                                    key: item._id + 'memberDuty',
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 4 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 20 }
                                        }
                                    },
                                },

                            ]
                        }
                    }
                }),

                {
                    type: 'Component',
                    // label: '添加家庭主要成员',
                    field: 'addMemberList',
                    placeholder: '请输入',
                    span: 8,
                    Component: (obj) => {
                        const { memberListMax,memberList } = this.state;
                        let disabled = memberListMax === memberList.length;
                        return <Button
                            disabled={disabled}
                            type="primary" onClick={this.add} icon={<PlusCircleOutlined />}>
                            添加家庭主要成员
                      </Button>
                    }
                },

                {
                    label: '函调原单位邮寄地址',
                    type: 'string',
                    field: 'origSendAddress',
                    placeholder: '请输入...',
                    span: 3
                },
                {
                    label: '函调原单位人力资源部联系人',
                    type: 'string',
                    field: 'origLinkman',
                    placeholder: '请输入...',
                    span: 3
                },
                {
                    label: '联系电话',
                    type: 'string',
                    field: 'origPhone',
                    placeholder: '请输入...',
                    span: 2
                },
                {
                    type: "textarea",
                    label: "单位领导意见",
                    field: "opinionField1",
                    addShow: false,
                    placeholder: "请输入",
                    opinionField: true,
                    span: 8,
                    autoSize: {
                        minRows: 2,
                        // maxRows: 10
                    }
                },
                {
                    type: "textarea",
                    label: "人力资源部意见",
                    field: "opinionField2",
                    opinionField: true,
                    addShow: false,
                    placeholder: "请输入",
                    span: 8,
                    autoSize: {
                        minRows: 2,
                        // maxRows: 10
                    }
                },
                {
                    type: "textarea",
                    label: "集团董事长/集团总经理",
                    field: "opinionField3",
                    opinionField: true,
                    addShow: false,
                    placeholder: "请输入",
                    span: 8,
                    autoSize: {
                        minRows: 2,
                        // maxRows: 10
                    }
                },
                {
                    type: "textarea",
                    label: "部门负责人",
                    field: "opinionField4",
                    opinionField: true,
                    addShow: false,
                    placeholder: "请输入",
                    span: 8,
                    autoSize: {
                        minRows: 2,
                        // maxRows: 10
                    }
                },
                {
                    label: '附件',
                    field: 'fileList',
                    type: 'files',
                    fetchConfig: {
                        apiName: window.configs.domain + 'upload',
                    },
                    span: 8,
                },
                {
                    type: "textarea",
                    label: "备注",
                    field: "remarks",
                    qnnDisabled: true,
                    placeholder: "请输入",
                    initialValue: `1、此表用于公司外部的调入（出）使用，一式两份，公司机关及调入（出）单位各执一份；\n2、“调入（出）岗位”一栏，填写入职单位岗位信息；\n3、“上报单位审批意见”需主管领导亲笔签字；\n4、反正面打印。`,
                    span: 8,
                    autoSize: {
                        minRows: 2
                    }
                }
            ]
        }
    }

    render() {
        const {
            isInQnnTable,
        } = this.props;
        const config = this.getConfig();
        return (<div
            key={this.state.memberList.length}
            ref={(me) => {
                if (me) {
                    this.pageRef = me;
                    setTimeout(() => {
                        $(`.ant-spin-container > div`).eq(2).scrollTop(this.DrawerPageScroll);
                    },300)
                }
            }}
            style={
                {
                    height: isInQnnTable ? "" : "100vh"
                }
            } >

            <Form
                {...this.props}
                {...config}
                {...this.props.workFlowConfig}
                {...config.workFlowConfig}
                wrappedComponentRef={(me) => {
                    if (me) {
                        setTimeout(() => {
                            this.qnnForm = me.qnnForm;
                            this.qnnForm?.setValues && this.qnnForm.setValues(this.state.formValues)
                        },600)
                    }
                }}
                flowData={this.state.flowData}
                openFlowDataParms={(resData,props,flowData) => {
                    const memberList = [];
                    for (const key in resData) {
                        if (/^(memberList)\d+/ig.test(key)) {
                            memberList.push({
                                _id: key,
                                ...resData[key]
                            })
                        }
                    }
                    !this.state.getDataEd && this.setState({
                        memberList: memberList,
                        getDataEd: true,
                        flowData
                    })
                    return resData
                }}
                btnsCURD={({ btns,flowData }) => {
                    
                    var url = this.props.match.url;
					var myPublic = this.props.myPublic.appInfo.mainModule;
					let { myPublic: { domain,appInfo: { ureport } } } = this.props;
					if (url === `${myPublic}todoByTran`) {
						if (flowData && flowData.flowNode.nodeId === 'Node8') {
							var printUrl = `${ureport}excel?_u=file:reportZjFlowTranOuter.xml&url=${domain}&workId=${flowData.workId}`
							btns.push({ buttonClass: "exprot",buttonFun: null,buttonId: "exprot",buttonName: "导出",icon: null,printUrl: printUrl });
						}
					} else {
						if (flowData && flowData.flowNode.nodeId === 'Node6' || flowData && flowData.flowNode.nodeId === 'Node8') {
							var printUrl = `${ureport}excel?_u=file:reportZjFlowTranOuter.xml&url=${domain}&workId=${flowData.workId}`
							btns.push({ buttonClass: "exprot",buttonFun: null,buttonId: "exprot",buttonName: "导出",icon: null,printUrl: printUrl });
						}
					}
                    return btns;
                }}
                fieldsCURD={(fields,flowData,props) => {
                    let list = eval('(' + flowData.apiData + ')');
                    var fields = fields.map((item) => {
                        let { field } = item;
                        if (list.apih5FlowStatus === '0') {
                            if (item.children) {
                                let inFormConfig = item.children.formConfig;
                                for (var i = 0; i < inFormConfig.length; i++) {
                                    inFormConfig[i].disabled = false;
                                }
                            } else {
                                if (field === 'remarks' || field === 'opinionField4' || field === 'opinionField3' || field === 'opinionField2' || field === 'opinionField1') {
                                    item.disabled = true;
                                } else {
                                    item.disabled = false;
                                }
                            }
                        } else {
                            // item.disabled = true;
                        }
                        return item;
                    });
                    return fields
                }}
            />  </div>
        );
    }
}
export default index;