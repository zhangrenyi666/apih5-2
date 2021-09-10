import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { message as Msg, Modal } from 'antd';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true
};
const configItem = {
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    actionBtnsPosition: "top",
    firstRowIsSearch: false,
    isShowRowSelect: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: 0
        }
    }
    componentDidMount() { }
    FloatMulTwo(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }
    render() {
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                        apiName: 'getZxSkTtReqPlanList',
                        otherParams: {
                            projectID: departmentId
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
                                field: 'companyId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'projectName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'companyName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '计划编号',
                                width: 200,
                                dataIndex: 'projectNumber',
                                key: 'projectNumber',
                                filter: true
                            },
                            form: {
                                field: 'projectNumber',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '项目名称',
                                width: 200,
                                dataIndex: 'projectName',
                                key: 'projectName',
                                // type: 'select',
                                filter: true,
                                fieldsConfig: {
                                    type: 'select',
                                    field: 'orgID2',
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId',
                                        linkageFields: {
                                            projectName: 'projectName',
                                            companyName: 'companyName',
                                            companyId: 'companyId'
                                        }
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysProjectBySelect',
                                        otherParams: {
                                            departmentId: departmentId
                                        }
                                    },
                                }
                            },
                            form: {
                                field: 'projectID',
                                required: true,
                                type: 'select',
                                showSearch: true,
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId',
                                    linkageFields: {
                                        projectName: 'projectName',
                                        companyName: 'companyName',
                                        companyId: 'companyId'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams: {
                                        departmentId: departmentId
                                    }
                                },
                                placeholder: '请选择',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                                onChange: (val, rowData) => {
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkTtReqPlanItemList']);
                                        // this.table.qnnForm.qnnSetState({
                                        //     id:null
                                        // })
                                    }
                                }
                            },
                        },
                        {
                            // isInSearch:true,
                            table: {
                                title: '日期',
                                width: 120,
                                onClick: 'detail',
                                dataIndex: 'createDate',
                                key: 'createDate',
                                filter: true,
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field: 'timeList'
                                },
                                render: (data) => {
                                    return data ? moment(data).format('YYYY-MM-DD') : null
                                }
                            },
                            form: {
                                type: 'date',
                                required: true,
                                field: 'createDate',
                                initialValue: () => {
                                    return moment(new Date()).format('YYYY-MM-DD')
                                },
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '编制人',
                                dataIndex: 'aurhorizedPersonnel',
                                key: 'aurhorizedPersonnel',
                                filter: true
                            },
                            form: {
                                type: 'string',
                                field: 'aurhorizedPersonnel',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'zxSkTtReqPlanItemList',
                                incToForm: true,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'id',
                                        size: 'small'
                                    },
                                    ...configItem,
                                    tableTdEdit: true,
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '主键id',
                                                field: 'id',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: "<div>分部分项<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'cbsID',
                                                width: 200,
                                                key: 'cbsID',
                                                type: 'select',
                                                tdEdit: true,
                                            },
                                            form: {
                                                type: 'selectByPaging',
                                                field: 'cbsID',
                                                required: true,
                                                showSearch: true,
                                                optionConfig: {
                                                    label: 'name',
                                                    value: 'iecsCBSID'
                                                },
                                                fetchConfig: {
                                                    apiName: "getZxEqIecsCBSOrgId",
                                                    otherParams: async (val) => {
                                                        console.log(val);
                                                        console.log(this.table);
                                                        console.log(this.table.props);
                                                        // console.log(this.table.getTableData());
                                                        const vals = await this.table.btnCallbackFn.qnnForm.getValues(false);
                                                        
                                                        return {
                                                            orgID: vals.projectID,
                                                        }
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: "<div>物资编码<span style='color: #ff4d4f'>*</span></div>",
                                                width: 180,
                                                tooltip: 23,
                                                dataIndex: 'resCode',
                                                key: 'resCode',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'selectByQnnTable',
                                                label: '',
                                                field: 'resCode',
                                                allowClear: false,
                                                required: true,
                                                dropdownMatchSelectWidth: 900,
                                                optionConfig: {
                                                    label: 'resCode',
                                                    value: 'id'
                                                },
                                                qnnTableConfig: {
                                                    antd: {
                                                        rowKey: "id"
                                                    },
                                                    // selectType:"checkbox", //默认radio单选
                                                    firstRowIsSearch: false,
                                                    fetchConfig: {
                                                        apiName: "getZxSkResourceMaterialsListNameJoin",
                                                        otherParams: (val) => {
                                                            return {
                                                                orgID: this.table.btnCallbackFn.qnnForm.form.getFieldValue('projectID'),
                                                            }
                                                        }
                                                    },
                                                    // firstRowIsSearch:true,
                                                    // searchBtnsStyle: "inline",
                                                    formConfig: [
                                                        {
                                                            isInForm: false,
                                                            isInTable: false,
                                                            form: {
                                                                field: 'id',
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "resCode",
                                                                title: "编号",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "resName",
                                                                title: "名称",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "spec",
                                                                title: "规格型号",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "unit",
                                                                title: "单位",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            // isInSearch: true,
                                                            table: {
                                                                dataIndex: "refpriceType",
                                                                title: "计价方式",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        }
                                                    ]
                                                },
                                                onChange: (colVal, thisProps, btnCallbackFn) => {
                                                    if (colVal) {
                                                        const itemData = thisProps;
                                                        btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                            btnCallbackFn.setEditedRowData({
                                                                ...editRowData,
                                                                resCode: itemData.itemData[0].resCode,
                                                                resName: itemData.itemData[0].resName,
                                                                unit: itemData.itemData[0].unit,
                                                                spec: itemData.itemData[0].spec,
                                                                resID: itemData.itemData[0].id,
                                                                lossRate: 0,
                                                                designNum: 0,
                                                                totalNum: 0,
                                                                changeNum: 0
                                                            });
                                                        })
                                                    } else {
                                                        btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                            btnCallbackFn.setEditedRowData({
                                                                ...editRowData,
                                                                resName: '',
                                                                resID: '',
                                                                unit: '',
                                                                spec: '',
                                                                lossRate: 0,
                                                                designNum: 0,
                                                                totalNum: 0,
                                                                changeNum: 0
                                                            });
                                                        })
                                                    }

                                                },
                                            }
                                        },
                                        {
                                            table: {
                                                title: '物资名称',
                                                width: 150,
                                                tooltip: 23,
                                                dataIndex: 'resName',
                                                key: 'resName'
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'resName'
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                type: 'string',
                                                field: 'resID'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '计量单位',
                                                dataIndex: 'unit',
                                                key: 'unit',
                                                width: 80,
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'unit'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '规格型号',
                                                width: 100,
                                                dataIndex: 'spec',
                                                key: 'spec'
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'spec'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '损耗率',
                                                dataIndex: 'lossRate',
                                                width: 100,
                                                key: 'lossRate',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'lossRate',
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {

                                                    if (typeof (colVal) === 'number' && colVal >= 0) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                            const newRowData = {
                                                                ...rowData
                                                            }
                                                            if (rowData.designNum >= 0) {
                                                                newRowData.totalNum = Number(this.FloatMulTwo(colVal, rowData.designNum)) + Number(rowData.designNum);
                                                            }
                                                            await tableBtnCallbackFn.setEditedRowData({
                                                                ...newRowData
                                                            });
                                                        }, 600)
                                                    }

                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '编号图纸设计量',
                                                dataIndex: 'designNum',
                                                key: 'designNum',
                                                width: 120,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'designNum',
                                                min: 0,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (typeof (colVal) === 'number' && colVal >= 0) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        // 设置编辑行值方法
                                                        // 节流设置
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            // 获取编辑行的值的 标准方案
                                                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                            const newRowData = {
                                                                ...rowData
                                                            }
                                                            if (rowData.lossRate >= 0) {
                                                                newRowData.totalNum = Number(this.FloatMulTwo(rowData.lossRate, colVal)) + Number(colVal);
                                                            }
                                                            await tableBtnCallbackFn.setEditedRowData({
                                                                ...newRowData
                                                            });
                                                        }, 600)
                                                    }

                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '总需用计划量',
                                                dataIndex: 'totalNum',
                                                width: 120,
                                                key: 'totalNum',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'totalNum',
                                                min: 0,
                                                precision: 2,
                                            }
                                        },
                                        {
                                            // isInTable: (val,btnInfo) => {
                                            //     let name = val.qnnTableInstance.props.qnnFormProps.funcCallBackParams.clickCb.rowInfo.name;
                                            //     if (name === 'detail') {
                                            //         return true
                                            //     } else {
                                            //         return false
                                            //     }
                                            // },
                                            isInTable: (val) => {
                                                if (val.qnnFormProps.funcCallBackParams.props.Pstate.drawerDetailTitle === '详情') {
                                                    return true
                                                } else {
                                                    return false
                                                }
                                            },
                                            table: {
                                                title: '变更量',
                                                width: 100,
                                                dataIndex: 'changeNum',
                                                key: 'changeNum',
                                                precision: 2,
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '备注',
                                                width: 120,
                                                tooltip: 23,
                                                dataIndex: 'remark',
                                                key: 'remark',
                                                tdEdit: true,
                                            },
                                            form: {
                                                type: 'textarea',
                                                field: 'remark',
                                                autoSize: {
                                                    minRows: 1,
                                                    maxRows: 3
                                                }
                                            }
                                        }
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "新增",
                                        },
                                        {
                                            name: "del",
                                            icon: 'delete',
                                            type: 'danger',
                                            label: "删除",
                                        }
                                    ]
                                }
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                width: 300,
                                tooltip: 23,
                                dataIndex: 'remark',
                                key: 'remark',
                            },
                            form: {
                                type: 'textarea',
                                field: 'remark',
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '审核',
                                width: 100,
                                dataIndex: 'status',
                                key: 'status',
                                render: (data) => {
                                    if (data) {
                                        return data === '0' ? '未审核' : '已审核'
                                    } else {
                                        return '未审核'
                                    }
                                }
                            },
                            form: {
                                type: 'string',
                                field: 'status',
                                hide: true
                            }
                        }
                    ]}
                    actionBtns={[
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    field: 'addsubmit',
                                    fetchConfig: {
                                        apiName: 'addZxSkTtReqPlan'
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',
                            icon: 'edit',
                            type: 'primary',
                            label: '修改',
                            onClick: (obj) => {
                                if (obj.selectedRows[0].status === '1') {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('已审核的不能修改!');
                                } else {

                                }
                                this.table.clearSelectedRows();
                            },
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    fetchConfig: {
                                        apiName: 'updateZxSkTtReqPlan'
                                    }
                                }
                            ]
                        },
                        {
                            name: 'diy',
                            type: 'primary',
                            label: '审核',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {
                                const { myFetch } = this.props;
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].status === '1') {
                                        obj.btnCallbackFn.closeDrawer();
                                        this.table.clearSelectedRows();
                                        Msg.warn('已审核的数据不能审核!');
                                    } else {
                                        confirm({
                                            content: '确定审核选中的数据吗?',
                                            onOk: () => {
                                                myFetch('checkZxSkTtReqPlanList', { id: obj.selectedRows[0].id }).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            Msg.warn(message);
                                                            this.table.refresh();
                                                            this.table.clearSelectedRows();
                                                        } else {
                                                            Msg.warn(message);
                                                            this.table.refresh();
                                                            this.table.clearSelectedRows();
                                                        }
                                                    }
                                                );
                                            }
                                        });
                                    }
                                } else {
                                    Msg.warn('只能审核一条数据！')
                                }
                            }
                        },
                        {
                            name: 'diyDel',
                            icon: 'delete',
                            type: 'danger',
                            label: '删除',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {
                                const { myFetch } = this.props;
                                let arry = [];
                                for (let m = 0; m < obj.selectedRows.length; m++) {
                                    if (obj.selectedRows[m].status === '1') {
                                        //存在已审核的数据
                                        arry.push(obj.selectedRows[m].status);
                                    }
                                }
                                if (arry.length > 0) {
                                    Msg.warn('请选择未审核的数据！')
                                } else {
                                    confirm({
                                        content: '确定删除选中的数据吗?',
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZxSkTtReqPlan', obj.selectedRows).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        this.table.refresh();
                                                        this.table.clearSelectedRows();
                                                    } else {
                                                    }
                                                }
                                            );
                                        }
                                    });
                                }

                            }
                        }
                    ]}

                />
            </div>
        );
    }
}

export default index;