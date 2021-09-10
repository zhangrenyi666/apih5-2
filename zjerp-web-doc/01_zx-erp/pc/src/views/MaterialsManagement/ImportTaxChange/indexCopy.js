import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { Avatar, message as Msg, Modal, Spin } from 'antd';
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
const configBh = {
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visibleBjdh: false,
            loadingBjdh: false,
            idItem: '',
            lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : '',
            visible: false,
            loading: false,
            idPreview: null,
            name: null
        }
    }
    componentDidMount() { }
    FloatMulTwo(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }
    handleCancelChange = () => {
        this.setState({ loadingBjdh: false, visibleBjdh: false });
    }
    handleCancel = () => {
        this.setState({ visible: false, loading: false });
    }
    render() {
        const { visibleBjdh, loadingBjdh, idItem, lockProjectId, loading, idPreview, name } = this.state;
        const { ext1, departmentId, companyId, projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        let { myPublic: { appInfo: { ureport } } } = this.props;
        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
        let jurisdiction = departmentId;
        if (lockProjectId !== 'all' && lockProjectId !== '') {
            jurisdiction = lockProjectId;
        } else {
            if (ext1 === '1' || ext1 === '2') {
                jurisdiction = companyId;
            } else if (ext1 === '3' || ext1 === '4') {
                jurisdiction = projectId;
            } else {

            }
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
                            table: {
                                title: '计划编号',

                                dataIndex: 'projectNumber',
                                key: 'projectNumber',
                                filter: true
                            },
                            form: {
                                field: 'projectNumber',
                                type: 'string',
                                // editDisabled:true,
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
                                title: '项目名称',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                onClick: 'detail',
                                filter: true,
                                fieldsConfig: {
                                    field: 'orgID2',
                                    type: 'select',
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
                                }
                            },
                            form: {
                                field: 'projectID',
                                required: true,
                                type: 'select',
                                editDisabled: true,
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
                                        // this.table.qnnForm.qnnSetState({
                                        //     id:null
                                        // })
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '日期',
                                width: 120,
                                dataIndex: 'createDate',
                                format: 'YYYY-MM-DD',
                                key: 'createDate',
                                filter: true,
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field: 'timeList'
                                }
                            },
                            form: {
                                type: 'date',
                                required: true,
                                editDisabled: true,
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
                                editDisabled: true,
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
                                                fieldConfig: {
                                                    disabled: ({ record }) => {
                                                        if (record.addData === 'true') {
                                                            return true
                                                        } else {
                                                            return false
                                                        }
                                                    },
                                                }
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
                                                tdEdit: true,
                                                fieldConfig: {
                                                    disabled: ({ record }) => {
                                                        if (record.addData === 'true') {
                                                            return true
                                                        } else {
                                                            return false
                                                        }
                                                    },
                                                }

                                            },
                                            form: {
                                                type: 'selectByQnnTable',
                                                label: '',
                                                field: 'resCode',
                                                required: true,
                                                allowClear: false,
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
                                                    searchBtnsStyle: "inline",
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
                                                            // isInSearch:true,
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
                                                onChange: (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        const itemData = thisProps;
                                                        tableBtnCallbackFn.setEditedRowData({
                                                            resCode: itemData.itemData.resCode,
                                                            resName: itemData.itemData.resName,
                                                            unit: itemData.itemData.unit,
                                                            spec: itemData.itemData.spec,
                                                            lossRate: 0,
                                                            designNum: 0,
                                                            totalNum: 0
                                                        });
                                                    } else {
                                                        tableBtnCallbackFn.setEditedRowData({
                                                            resName: '',
                                                            unit: '',
                                                            spec: '',
                                                            lossRate: 0,
                                                            designNum: 0,
                                                            totalNum: 0
                                                        });
                                                    }
                                                },
                                            }
                                        },
                                        {
                                            table: {
                                                title: '物资名称',
                                                width: 200,
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
                                                // tdEdit: true,
                                                fieldConfig: {
                                                    disabled: ({ record }) => {
                                                        if (record.addData === 'true') {
                                                            return true
                                                        } else {
                                                            return false
                                                        }
                                                    },
                                                }

                                            },
                                            form: {
                                                type: 'number',
                                                field: 'lossRate',

                                                // precision: 2,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal >= 0 && typeof (colVal) === 'number') {
                                                        // 获取编辑行的值的 标准方案
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }
                                                        if (rowData.designNum) {
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
                                                // tdEdit: true,
                                                fieldConfig: {
                                                    disabled: ({ record }) => {
                                                        if (record.addData === 'true') {
                                                            return true
                                                        } else {
                                                            return false
                                                        }
                                                    },
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'designNum',
                                                min: 0,
                                                precision: 3,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal >= 0 && typeof (colVal) === 'number') {
                                                        // 获取编辑行的值的 标准方案
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }
                                                        if (rowData.lossRate) {
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
                                                // tdEdit: true,
                                                fieldConfig: {
                                                    disabled: ({ record }) => {
                                                        if (record.addData === 'true') {
                                                            return true
                                                        } else {
                                                            return false
                                                        }
                                                    },
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'totalNum',
                                                precision: 2,

                                            }
                                        },
                                        {
                                            // 感觉没啥用了，暂时注释
                                            // isInTable: (val) => {
                                            //     if (val.qnnFormProps.funcCallBackParams.props.Pstate.drawerDetailTitle === '编辑' || val.qnnFormProps.funcCallBackParams.props.Pstate.drawerDetailTitle === '详情') {
                                            //         return true
                                            //     } else {
                                            //         return false
                                            //     }
                                            // },
                                            table: {
                                                title: '变更量',
                                                width: 100,
                                                dataIndex: 'changeNum',
                                                key: 'changeNum',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'changeNum',
                                                precision: 3,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '备注',
                                                width: 120,
                                                tooltip: 23,
                                                dataIndex: 'remark',
                                                key: 'remark',
                                                tdEdit: true,
                                                fieldConfig: {
                                                    disabled: ({ record }) => {
                                                        if (record.addData === 'true') {
                                                            return true
                                                        } else {
                                                            return false
                                                        }
                                                    },
                                                }
                                            },
                                            form: {
                                                type: 'textarea',
                                                field: 'remark',
                                                autoSize: {
                                                    minRows: 1,
                                                    maxRows: 3
                                                }
                                            }
                                        },
                                        {//暂时注释-没有后台接口
                                            table: {
                                                title: '明细',
                                                dataIndex: 'warnFlag',
                                                key: 'warnFlag',
                                                align: 'center',
                                                fixed: 'right',
                                                width: 50,
                                                render: (data) => {
                                                    return <Avatar shape="square" size={20} src={require('../../../imgs/file.png')} />;
                                                },
                                                onClick: (obj) => {
                                                    this.setState({
                                                        visibleBjdh: true,
                                                        idItem: obj.rowData.id
                                                    });
                                                }
                                            },
                                            isInForm: false
                                        },
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "新增",
                                        },
                                        //2021/01/20 张
                                        // {
                                        //     name: "del",
                                        //     icon: 'delete',
                                        //     type: 'danger',
                                        //     label: "删除",
                                        // }
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
                                editDisabled: true,
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
                            // isInForm: false,
                            form: {
                                type: 'string',
                                field: 'status',
                                hide: true
                            }
                        }
                    ]}
                    method={{
                        disabledFunChange: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1 || data[0].status !== '1') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        willExecuteFunChange: (obj) => {
                            if (obj.selectedRows[0].status === '1') {

                            } else {
                                Msg.warn('请选择已审核的数据变更！')
                                obj.btnCallbackFn.clearSelectedRows();
                                return false
                            }
                            obj.btnCallbackFn.clearSelectedRows();
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
                    // actionBtns={[
                    //     {
                    //         name: 'edit',
                    //         icon: 'edit',
                    //         type: 'primary',
                    //         label: '变更',
                    //         disabled: 'bind:disabledFunChange',
                    //         willExecute: 'bind:willExecuteFunChange',
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
                    //                     apiName: 'updateZxSkTtReqPlanCheckOver'
                    //                 }
                    //             }
                    //         ]
                    //     }
                    // ]}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            var props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "ImportTaxChange"
                            }
                        }
                    }}
                />
                <Modal
                    width='1200px'
                    style={{ top: '0' }}
                    title="明细"
                    visible={visibleBjdh}
                    footer={null}
                    onCancel={this.handleCancelChange}
                    bodyStyle={{
                        width: '1200px'
                    }}
                    centered={true}
                    destroyOnClose={this.handleCancelChange}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingBjdh}>
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.tableBh = me;
                            }}
                            {...configBh}
                            antd={{
                                rowKey: 'zxSkHttreqPlanItemId',
                                size: 'small',
                                scroll: {
                                    y: document.documentElement.clientHeight * 0.6
                                }
                            }}
                            fetchConfig={() => {
                                return {
                                    apiName: 'getZxSkHttreqPlanItemList',
                                    otherParams: {
                                        ttReqPlanItemID: idItem
                                    }
                                }
                            }}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'zxSkHttreqPlanItemId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '操作人',
                                        dataIndex: 'oper',
                                        key: 'oper'
                                    }
                                },
                                {
                                    table: {
                                        title: '操作时间',
                                        dataIndex: 'opTime',
                                        key: 'opTime',
                                        format: 'YYYY-MM-DD'
                                    }
                                },
                                {
                                    table: {
                                        title: '原始量',
                                        dataIndex: 'sourceNum',
                                        key: 'sourceNum',
                                    }
                                },
                                {
                                    table: {
                                        title: '变更量',
                                        dataIndex: 'changeNum',
                                        key: 'changeNum',
                                    }
                                },
                                {
                                    table: {
                                        title: '操作',
                                        dataIndex: 'op',
                                        key: 'op',
                                    }
                                }
                            ]}
                            actionBtns={[]}
                        />
                    </Spin>
                </Modal>
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
                            <iframe width='100%' height={window.innerHeight * 0.8} frameBorder={0} onLoad={(obj) => {
                                this.setState({ loading: false })
                            }} src={`${ureport}preview?_u=minio:ZxSkTtReqPlanChange.preview.xml&_t=1,6&_n=${name}&access_token=${access_token}&id=${idPreview}
                            `} title="byLm"></iframe>
                        </Spin> : ''
                    }

                </Modal>
            </div>
        );
    }
}

export default index;