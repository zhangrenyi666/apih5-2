import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { message as Msg, Modal, Spin } from 'antd';
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
    isShowRowSelect: true,
    // rowSelection:{
    //     type: "radio",
    //     columnWidth:80
    // }
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
            lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : '',
            visible: false,
            loading: false,
            idPreview: null
        }
    }
    componentDidMount() { }
    FloatMulTwo(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }
    handleCancel = () => {
        this.setState({ visible: false, loading: false });
    }
    render() {
        const { ext1, departmentId, companyId, projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
        const { lockProjectId, loading, idPreview, name } = this.state;
        let { myPublic: { appInfo: { ureport } } } = this.props;
        // ---不看------局-1companyId，公司-2companyId，项目-3.4projectId
        // 右侧：1,2公司，左侧不是全部：如果不是全部，就传左侧的projectId，全部的话用右侧的。
        // 项目- 直接取右边的projectId
        let jurisdiction = departmentId;
        if (lockProjectId !== 'all' && lockProjectId !== '') {
            jurisdiction = lockProjectId;
        } else {
            if (ext1 === '1' || ext1 === '2') {
                jurisdiction = companyId;
            } else if (ext1 === '3' || ext1 === '4') {
                jurisdiction = projectId;
            } else { }
        }
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
                            projectID: jurisdiction
                        }
                    }}
                    // formIsChangeedAlertTextContent={true}
                    method={{
                        disabledFunEdit: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();

                            if (data.length !== 1 || data[0].status === '1') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        willExecuteFunEdit: (obj) => {
                            if (obj.selectedRows[0].status === '1') {
                                obj.btnCallbackFn.clearSelectedRows();
                                Msg.warn('已审核的不能修改!');
                                return false
                            } else {

                            }
                            obj.btnCallbackFn.clearSelectedRows();
                        },
                        disabledFunStatus: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1 || data[0].status === '1') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        onClickFunStatus: (obj) => {
                            const { myFetch } = this.props;
                            if (obj.selectedRows.length === 1) {
                                if (obj.selectedRows[0].status === '1') {
                                    obj.btnCallbackFn.closeDrawer();
                                    obj.btnCallbackFn.clearSelectedRows();
                                    Msg.warn('已审核的数据不能审核!');
                                } else {
                                    confirm({
                                        content: '确定审核选中的数据吗?',
                                        onOk: () => {
                                            myFetch('checkZxSkTtReqPlanList', { id: obj.selectedRows[0].id }).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.table.refresh();
                                                    } else {
                                                        Msg.warn(message);
                                                        this.table.refresh();
                                                    }
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                }
                                            );
                                        }
                                    });
                                }
                            } else {
                                Msg.warn('只能审核一条数据！')
                            }
                        },
                        disabledFunDel: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length > 0) {
                                for (let m = 0; m < data.length; m++) {
                                    if (data[m].status === '1') {
                                        return true;
                                    }
                                }
                                return false
                            } else {
                                return true;
                            }
                        },
                        onClickFunDel: (obj) => {
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

                                                } else {
                                                }
                                                obj.btnCallbackFn.clearSelectedRows();
                                            }
                                        );
                                    }
                                });
                            }

                        },
                        // 预览
                        disabledFunpreview: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1) {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        onClickFunpreview: (obj) => {
                            this.setState({
                                visible: true,
                                loading: true,
                                idPreview: obj.selectedRows[0].id,
                                name: obj.selectedRows[0].projectName
                            })
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
                                            // 右侧：1,2公司，左侧不是全部：如果不是全部，就传左侧的projectId，全部的话用右侧的。
                                            // 项目- 直接取右边的projectId

                                            // if(左侧锁定项目的projectId != all || != 空){
                                            //     传projectId
                                            // } else {
                                            //     if(ext1=1 || ext1=2){
                                            //         项目时，传projectId
                                            //         公司时，传companyId
                                            //     }else {
                                            //         // ext1=3\4时
                                            //         项目时，传projectId
                                            //         *个别需要获取当前项目对应公司的所有项目下拉，此时传companyId
                                            //     }
                                            // }

                                            departmentId: jurisdiction
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
                                        departmentId: jurisdiction
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
                                                type: 'selectByPaging',
                                                // type: 'select',
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
                                                    otherParams: (val) => {
                                                        let vals = val.form.getFieldsValue();
                                                        return {
                                                            orgID: vals.projectID
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
                                                    firstRowIsSearch: false,
                                                    fetchConfig: {
                                                        apiName: "getZxSkResourceMaterialsListNameJoin",
                                                        otherParams: (val) => {
                                                            return {
                                                                orgID: val.qnnTableInstance.props.qnnFormProps.form.getFieldsValue().projectID,
                                                            }
                                                        }
                                                    },
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
                                                                width: 150,
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
                                                                width: 300,
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
                                                        btnCallbackFn.setEditedRowData({
                                                            resCode: itemData.itemData.resCode,
                                                            resName: itemData.itemData.resName,
                                                            unit: itemData.itemData.unit,
                                                            spec: itemData.itemData.spec,
                                                            resID: itemData.itemData.id,
                                                            lossRate: 0,
                                                            designNum: 0,
                                                            totalNum: 0,
                                                            changeNum: 0
                                                        });
                                                    } else {
                                                        btnCallbackFn.setEditedRowData({
                                                            resName: '',
                                                            resID: '',
                                                            unit: '',
                                                            spec: '',
                                                            lossRate: 0,
                                                            designNum: 0,
                                                            totalNum: 0,
                                                            changeNum: 0
                                                        });
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
                                            isInTable: (val, btnInfo) => {
                                                let name = val.qnnTableInstance.props.qnnFormProps.funcCallBackParams.clickCb.rowInfo.name;
                                                if (name === 'detail') {
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
                                                width: 200,
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
                    // actionBtns={[
                    //     {
                    //         name: 'add',
                    //         icon: 'plus',
                    //         type: 'primary',
                    //         label: '新增',
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '取消',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '保存',
                    //                 field: 'addsubmit',
                    //                 fetchConfig: {
                    //                     apiName: 'addZxSkTtReqPlan'
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'edit',
                    //         icon: 'edit',
                    //         type: 'primary',
                    //         label: '修改',
                    //         disabled: 'bind:disabledFunEdit',
                    //         willExecute: 'bind:willExecuteFunEdit',
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '取消',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '保存',
                    //                 fetchConfig: {
                    //                     apiName: 'updateZxSkTtReqPlan'
                    //                 },

                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'diy',
                    //         type: 'primary',
                    //         label: '审核',
                    //         disabled: 'bind:disabledFunStatus',
                    //         onClick: 'bind:onClickFunStatus'
                    //     },
                    //     {
                    //         name: 'diyPreview',
                    //         type: 'primary',
                    //         label: '预览',
                    //         disabled: 'bind:disabledFunpreview',
                    //         onClick: 'bind:onClickFunpreview'
                    //     },
                    //     {
                    //         name: 'diyDel',
                    //         icon: 'delete',
                    //         type: 'danger',
                    //         label: '删除',
                    //         disabled: 'bind:disabledFunDel',
                    //         onClick: 'bind:onClickFunDel'
                    //     }
                    // ]}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            var props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "ImportTax"
                            }
                        }
                    }}

                />
                <Modal
                    width={'90%'}
                    style={{ paddingBottom: '0', top: '0' }}
                    title="预览"
                    visible={this.state.visible}
                    footer={null}
                    onCancel={this.handleCancel}
                    destroyOnClose={this.handleCancel}
                    bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                    centered={true}
                    wrapClassName={'modals'}
                >
                    {
                        idPreview ? <Spin spinning={loading}>
                            <iframe  width='100%' height={window.innerHeight * 0.8} frameBorder={0} onLoad={(obj) => {
                                this.setState({ loading: false });
                            }} src={`${ureport}preview?_u=minio:ZxSkTtReqPlan.preview.xml&_t=1,6&_n=${name}&access_token=${access_token}&id=${idPreview}`}title="byLm"></iframe>
                        </Spin> : ''
                    }

                </Modal>
            </div>
        );
    }
}

export default index;