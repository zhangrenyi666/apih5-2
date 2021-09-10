import React from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from "antd";
import PersonInfo from './personInfo'
import Apih5 from "qnn-apih5"
import WillExecute from './willExecute'

const config = {
    antd: {
        rowKey: 'zjXmSalaryEmployApprovalId',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 3 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 21 },
            sm: { span: 21 }
        }
    }
};
class index extends Apih5 {
    constructor(props) {
        super(props);
        this.state = {
            modalShowStatus: false,
            extensionHistoryId: ''
        }
    }
    render() {
        const { companyId, companyName, projectId, projectName, departmentName, departmentId } = this.apih5.getUserInfo('curCompany')
        const { realName } = this.apih5.getUserInfo()
        const orgId = this.apih5.getOrgId()
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZjXmSalaryEmployApprovalList',
                        otherParams: {
                            topId: orgId,
                            approvalFlag: 'employ'
                        }
                    }}
                    actionBtns={[
                        {
                            name: 'addDiy',//内置add del
                            icon: 'plus',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '新增',
                            onClick: (obj) => {
                                if (projectId) {
                                    obj.qnnTableInstance.btnAction({
                                        btnConfig: {
                                            name: "add",
                                            drawerTitle: "",
                                            formBtns: [
                                                {
                                                    name: 'cancel', //关闭右边抽屉
                                                    type: 'dashed',//类型  默认 primary
                                                    label: '取消',
                                                },
                                                {
                                                    name: 'diySubmit',//内置add del
                                                    type: 'primary',//类型  默认 primary
                                                    label: '保存',//提交数据并且关闭右边抽屉 
                                                    onClick: async (obj) => {
                                                        const { data, success, message } = await this.props.myFetch('addZjXmSalaryEmployApproval', {
                                                            ...obj._formData,
                                                            companyId: companyId || null,
                                                            companyName: companyName || null,
                                                            projectId: projectId || null,
                                                            projectName: projectName || null,
                                                            departmentName: departmentName || null,
                                                            departmentId: departmentId || null,
                                                            topId: orgId,
                                                            approvalFlag: 'employ'
                                                        })

                                                        if (success) {
                                                            Msg.success('保存成功!')

                                                            this.setState({
                                                                primaryKey: data.zjXmSalaryEmployApprovalId
                                                            }, () => {
                                                                obj.btnCallbackFn.refresh();
                                                                obj.btnCallbackFn.setActiveKey('1');
                                                            })
                                                        } else {
                                                            Msg.error(message)
                                                        }
                                                    },
                                                    hide: (obj) => {
                                                        let index = obj.btnCallbackFn.getActiveKey();
                                                        if (index === "1") {
                                                            return true;
                                                        } else {
                                                            return false;
                                                        }
                                                    },
                                                }
                                            ]
                                        },
                                        attrBindInfo: {
                                            rowData: {
                                                ...obj.rowData
                                            }
                                        }
                                    })
                                } else {
                                    Msg.warning('请将右上角的当前项目切换为项目类型!')
                                }
                            },

                        },
                        {
                            name: 'Component',
                            type: 'primary',
                            label: '发起评审',
                            drawerTitle: '发起评审',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && !data[0].workId) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            Component: (props) => {
                                let flowData = props?.btnCallbackFn?.getSelectedRows?.()?.[0];

                                return <WillExecute {...this.props} type={'salary'} btnCallbackFn={props.qnnTableInstance} isInQnnTable={true} flowData={{ ...flowData }} />
                                // if (flowData && flowData.contentLength > 0) {
                                //     return <FlowFormByMaterialManagementContract  {...this.props} type={'salary'} btnCallbackFn={props.qnnTableInstance} isInQnnTable={true} flowData={{ ...flowData }} />;
                                // } else {
                                //     return <div style={{ fontSize: '20px', padding: '10px' }}>请填写详细数据!</div>
                                // }
                            }
                        },
                        {
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZjXmSalaryEmployApproval',
                            }
                        }
                    ]}
                    rowSelection={{
                        type: 'check',
                        getCheckboxProps: record => ({
                            // name:record.name,
                            disabled: record.apih5FlowStatus === '1' || record.apih5FlowStatus === '2',
                        }),
                    }}
                    drawerShowToggle={(args) => {
                        if (!args.drawerIsShow) {
                            this.table.refresh()
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'id',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'extensionHistoryId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '标题',
                                dataIndex: 'titleName',
                                key: 'titleName',
                                filter: true,
                                width: 200,
                                onClick: 'detail'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '申报时间',
                                dataIndex: 'declareTime',
                                key: 'declareTime',
                                width: 100,
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '报审单位',
                                dataIndex: 'applyForUnitName',
                                key: 'applyForUnitName',
                                width: 100
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '当前审批位置',
                                dataIndex: 'approvalLocation',
                                key: 'approvalLocation',
                                width: 120
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '审批状态',
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                width: 100,
                                type: 'select'
                            },
                            form: {
                                field: "apih5FlowStatus",
                                type: "select",
                                placeholder: "请选择...",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'liuChengZhuangTai'
                                    }
                                },
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                },
                            }
                        },
                        {
                            table: {
                                title: '审批时间',
                                dataIndex: 'approvalTime',
                                key: 'approvalTime',
                                width: 100,
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            isInForm: false,
                            table: {
                                showType: "tile", //出来的样式 bubble（气泡）  tile（平铺） 默认bubble  （0.6.15版本中将该属性移动到table属性下，也可写到table同级）
                                width: 110,
                                title: "操作",
                                key: "action", //操作列名称
                                fixed: "right", //固定到右边
                                align: "center",
                                btns: [
                                    {
                                        name: "edit", // 内置name有【add,  del, edit, detail, Component, form】
                                        label: "修改",
                                        onClick: (obj) => {
                                            this.setState({
                                                primaryKey: obj.rowData.zjXmSalaryEmployApprovalId
                                            })
                                        },
                                        disabled: (args) => {
                                            return args.rowData.apih5FlowStatus === '1' || args.rowData.apih5FlowStatus === '2'
                                        },
                                        formBtns: [
                                            {
                                                name: 'cancel', //关闭右边抽屉
                                                type: 'dashed',//类型  默认 primary
                                                label: '取消',
                                            },
                                            {
                                                name: 'submit',//内置add del
                                                type: 'primary',//类型  默认 primary
                                                label: '保存',//提交数据并且关闭右边抽屉 
                                                fetchConfig: {//ajax配置
                                                    apiName: 'updateZjXmSalaryEmployApproval',
                                                    otherParams: (obj) => {
                                                        return {
                                                            approvalFlag: "employ",
                                                            zjXmSalaryEmployApprovalId: this.state.primaryKey
                                                        }
                                                    }
                                                },
                                                //如果tab面是第一个显示,否则不显示
                                                hide: (obj) => {
                                                    let index = obj.btnCallbackFn.getActiveKey();
                                                    if (index === "1") {
                                                        return true;
                                                    } else {
                                                        return false;
                                                    }
                                                },
                                            }
                                        ]
                                    },
                                ]
                            }
                        },
                    ]}
                    onTabsChange={(tabKey, qnnFormInstance, arg) => {
                        if (tabKey === '1' && qnnFormInstance.clickCb.rowInfo.name !== 'add') {
                            this.setState({
                                primaryKey: arg.rowData.zjXmSalaryEmployApprovalId
                            })
                        }
                    }}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "基础信息",
                            disabled: (obj) => {
                                return (obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : obj.btnCallbackFn.form.getFieldValue("zxBuStockPriceId")
                            },
                            content: {
                                formConfig: [
                                    {
                                        label: '标题',
                                        field: 'titleName',
                                        type: 'string',
                                        required: true,
                                        placeholder: '请选择'
                                    },
                                    {
                                        label: '报审单位',
                                        type: 'string',
                                        field: 'applyForUnitName',
                                        addDisabled: true,
                                        editDisabled: true,
                                        initialValue: departmentName,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 6 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 18 },
                                                sm: { span: 18 }
                                            }
                                        },
                                        placeholder: '请输入'
                                    },
                                    {
                                        label: '报审单位',
                                        type: 'string',
                                        field: 'applyForUnitCode',
                                        initialValue: departmentId,
                                        hide: true
                                    },
                                    {
                                        label: '报审人',
                                        field: 'applyForUser',
                                        type: 'string',
                                        addDisabled: true,
                                        editDisabled: true,
                                        initialValue: realName,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 6 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 18 },
                                                sm: { span: 18 }
                                            }
                                        },
                                        placeholder: '请输入'
                                    },
                                    {
                                        label: "附件",
                                        field: "fileList",
                                        type: "files",
                                        initialValue: [],
                                        fetchConfig: {
                                            apiName: window.configs.domain + "upload"
                                        }
                                    }
                                ]
                            }
                        },
                        {
                            field: "tableqd",
                            name: "qnnTable",
                            title: "明细",
                            disabled: function (obj) {
                                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxCrJYearCreditEvaId"))
                            },
                            content: {
                                wrappedComponentRef: (me) => {
                                    this.buttomTable = me
                                },
                                antd: {
                                    rowKey: 'extensionHistoryId'
                                },
                                fetchConfig: {
                                    apiName: 'getZjXmSalaryUserExtensionHistoryList',
                                    otherParams: () => {
                                        return {
                                            zjXmSalaryEmployApprovalId: this.state.primaryKey
                                        }
                                    }
                                },
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '主键id',
                                            field: 'zjXmSalaryEmployApprovalId',
                                            hide: true
                                        }
                                    },
                                    // extensionHistoryId
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '人员表id',
                                            field: 'extensionHistoryId',
                                            hide: true
                                        }
                                    },
                                    {
                                        table: {
                                            title: 'No.',
                                            dataIndex: 'index',
                                            key: 'index',
                                            width: 50,
                                            fixed: 'left',
                                            render: (data, rowData, index) => {
                                                return index + 1;
                                            }
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '姓名',
                                            dataIndex: 'realName',
                                            key: 'realName',
                                            width: 100,
                                            // tdEdit: true
                                            onClick: (obj) => {
                                                this.setState({
                                                    extensionHistoryId: obj.rowData.extensionHistoryId
                                                }, () => {
                                                    this.setState({
                                                        modalShowStatus: 'detail',
                                                    })
                                                })
                                            }
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                        },
                                    },
                                    {
                                        table: {
                                            title: '性别',
                                            dataIndex: 'gender',
                                            key: 'gender',
                                            width: 100,
                                            // tdEdit: true,
                                            type: 'select'
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value'
                                            },
                                            optionData: [
                                                {
                                                    label: '男',
                                                    value: '0'
                                                },
                                                {
                                                    label: '女',
                                                    value: '1'
                                                }
                                            ],
                                            placeholder: '请选择',
                                        },
                                    },
                                    {
                                        table: {
                                            title: '身份证号',
                                            dataIndex: 'idNumber',
                                            key: 'idNumber',
                                            width: 150,
                                            // tdEdit: true
                                        },
                                        form: {
                                            type: 'identity',
                                            placeholder: '请输入',
                                        },
                                    },
                                    {
                                        table: {
                                            title: '岗位',
                                            dataIndex: 'positionName',
                                            key: 'positionName',
                                            width: 100,
                                            // tdEdit: true
                                        },
                                        form: {
                                            type: 'identity',
                                            placeholder: '请输入',
                                        },
                                    },
                                    // {
                                    //     table: {
                                    //         title: '岗位',
                                    //         dataIndex: 'positionName',
                                    //         key: 'positionName',
                                    //         width: 100,
                                    //         type: 'select'
                                    //     },
                                    //     form: {
                                    //         type: "select",
                                    //         optionConfig: {
                                    //             label: "itemName",
                                    //             value: "itemId",
                                    //             children: 'children'
                                    //         },
                                    //         fetchConfig: {
                                    //             apiName: "getBaseCodeTree",
                                    //             otherParams: {
                                    //                 itemId: "gangWeiGuanLi",
                                    //             },
                                    //         },
                                    //         placeholder: "请选择",

                                    //     }
                                    // },
                                    {
                                        table: {
                                            title: '岗位等级',
                                            dataIndex: 'levelSalaryId',
                                            key: 'levelSalaryId',
                                            width: 150,
                                            type: "cascader",
                                        },
                                        form: {
                                            type: "cascader",
                                            fetchConfig: {
                                                apiName: "getZjXmSalaryPositionLevelSalarySelect",
                                                otherParams: {
                                                    itemId: 'gongrenzhongzhong'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'label', //默认 label
                                                value: 'value',//
                                                children: 'showData',
                                            },
                                        }
                                    },
                                    {
                                        table: {
                                            title: '岗薪',
                                            dataIndex: 'salaryId',
                                            key: 'salaryId',
                                            width: 100,
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '请输入',
                                        },
                                    },
                                    {
                                        isInForm: false,
                                        table: {
                                            showType: "tile", //出来的样式 bubble（气泡）  tile（平铺） 默认bubble  （0.6.15版本中将该属性移动到table属性下，也可写到table同级）
                                            width: 110,
                                            title: "操作",
                                            key: "action", //操作列名称
                                            fixed: "right", //固定到右边
                                            align: "center",
                                            btns: [
                                                {
                                                    name: "diyEdit", // 内置name有【add,  del, edit, detail, Component, form】
                                                    label: "修改",
                                                    onClick: (obj) => {
                                                        this.setState({
                                                            extensionHistoryId: obj.rowData.extensionHistoryId
                                                        }, () => {
                                                            this.setState({
                                                                modalShowStatus: 'edit',
                                                            })
                                                        })
                                                    },
                                                },
                                            ]
                                        }
                                    }
                                ],

                                actionBtns: [
                                    {
                                        name: "diyAdd",
                                        icon: "plus",
                                        type: "primary",
                                        label: "新增行",
                                        onClick: async (obj) => {
                                            this.setState({
                                                modalShowStatus: 'add'
                                            })
                                        }
                                    },
                                    {
                                        name: 'delDiy',
                                        icon: 'delete',
                                        type: 'danger',
                                        label: '删除',
                                        disabled: "bind:_actionBtnNoSelected",
                                        onClick: async (obj) => {
                                            let params = []
                                            obj.qnnTableInstance.getSelectedRows().map((item, index) => {
                                                params[index] = {
                                                    extensionHistoryId: item.extensionHistoryId
                                                }
                                                return true
                                            })
                                            const {  success, message } = await this.props.myFetch('batchDeleteZjXmSalaryUserExtensionHistoryEmploy', params)
                                            if (success) {
                                                this.buttomTable.refresh()
                                                Msg.success('删除成功!')
                                            } else {
                                                Msg.error(message)
                                            }
                                        },
                                    }
                                ]
                            }
                        }
                    ]}
                />
                <PersonInfo
                    propsData={this.props}
                    modalShowStatus={this.state.modalShowStatus}
                    extensionHistoryId={this.state.extensionHistoryId}
                    primaryKey={this.state.primaryKey}
                    closeCb={(val, formData) => {
                        if (val === 'close') {
                            this.setState({
                                modalShowStatus: ''
                            }, async () => {
                                this.buttomTable.setTableData(this.buttomTable.getTableData().concat(formData))
                                this.buttomTable.refresh()
                            })
                        } else {
                            if (formData) {
                                this.setState({
                                    extensionHistoryId: formData.extensionHistoryId,

                                }, () => {
                                    this.setState({
                                        modalShowStatus: val
                                    })
                                })
                                this.buttomTable.setTableData(this.buttomTable.getTableData().concat(formData))
                            } else {
                                this.setState({
                                    modalShowStatus: val
                                }, () => {
                                    if (val === 'saveSuccess') {

                                        this.buttomTable.refresh()
                                    }
                                })
                            }
                        }
                    }}
                    tabsDataFunc={(data) => {

                    }}
                />
            </div>
        );
    }
}

export default index;