import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from 'antd';
import PurchaeApplicatin from './form';
import Operation from '../../MaterialsManagement/ImportPriceLimitChange/operation';
import DeetailForm from './deetailForm';
import moment from 'moment';
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
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : ''
        }
    }
    componentDidMount() { }
    // 单据编号
    getDjbhFun(val) {
        const { myFetch } = this.props;
        myFetch('getZxSkPurchaseNo', {
            orgID: val
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    if (this.table && this.table.qnnForm && this.table.qnnForm.form) {
                        this.table.qnnForm.form.setFieldsValue({
                            billNo: data
                        })
                    }
                } else {
                }
            }
        );
    }
    render() {
        const { ext1, departmentId, companyName, companyId, projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { lockProjectId } = this.state;
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
                        apiName: 'getZxSkPurchaseList',
                        otherParams: {
                            orgID: jurisdiction
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
                                field: 'workId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '单据编号',
                                onClick: 'detail',
                                dataIndex: 'billNo',
                                key: 'billNo',
                                filter: true,
                                fixed: 'left',
                                width: 230,
                                render: (data, rowData) => {
                                    let color = '';
                                    if (rowData.auditStatus === '1') {
                                        color = '';
                                    } else if (rowData.auditStatus === '0') {
                                        color = 'red';
                                    }
                                    return <a style={{ color: color }}>{data}</a>
                                }
                            },
                            form: {
                                field: 'billNo',
                                type: 'string',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
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
                                type: 'string',
                                field: 'companyName',
                                initialValue: companyName,
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'companyId',
                                initialValue: companyId,
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '公司名称',//???
                                width: 200,
                                dataIndex: 'comName',
                                key: 'comName'
                            },
                            form: {
                                type: 'string',
                                field: 'comName',
                                addDisabled: true,
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
                                type: 'string',
                                field: 'comID',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                width: 200,
                                dataIndex: 'orgID',
                                key: 'orgID',
                                filter: true,
                                type: 'select',
                                fieldsConfig: {
                                    type: 'select',
                                    field: 'orgID2',
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
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
                                field: 'orgID',
                                required: true,
                                type: 'select',
                                showSearch: true,
                                editDisabled: true,
                                optionConfig: {
                                    label: 'orgName',
                                    value: 'orgID',
                                    linkageFields: {
                                        comID: 'companyId',
                                        comName: 'companyName',
                                        orgName: 'orgName'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getZxCtContractListByOrgId',
                                    otherParams: {
                                        contrStatus: "1",
                                        orgID: jurisdiction
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
                                onChange: (val) => {
                                    if (this.table && this.table.qnnForm && this.table.qnnForm.form) {
                                        this.getDjbhFun(val);
                                    }
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'orgName',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '发起人',
                                dataIndex: 'beginPer',
                                key: 'beginPer',
                            },
                            form: {
                                type: 'string',
                                field: 'beginPer',
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: () => {
                                    return this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
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
                                title: '填报日期',
                                dataIndex: 'reportDate',
                                key: 'reportDate',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                field: 'reportDate',
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
                                title: '购置金额（万元）',
                                width: 160,
                                dataIndex: 'purchaseAmt',
                                key: 'purchaseAmt',
                            },
                            form: {
                                type: 'number',
                                field: 'purchaseAmt',
                                required: true,
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
                                title: '评审状态',
                                width: 100,
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                render: (data) => {
                                    if (data === '0') {
                                        return '待提交';
                                    } else if (data === '1') {
                                        return '审核中';
                                    } else if (data === '2') {
                                        return '评审通过';
                                    } else if (data === '3') {
                                        return '退回';
                                    } else if (data === '-1') {
                                        return '未审核';
                                    } else {
                                        return '--';
                                    }
                                }
                            },

                            form: {
                                type: 'string',
                                field: 'apih5FlowStatus',
                                hide: true,
                                // optionConfig: {
                                //     label: 'label',
                                //     value: 'value',
                                // },
                                // optionData: [
                                //     {
                                //         label: "未审核",
                                //         value: "0"
                                //     },
                                //     {
                                //         label: "已审核",
                                //         value: "1"
                                //     }
                                // ],
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '评审内容',
                                dataIndex: 'approval',
                                key: 'approval',
                            },
                            form: {
                                type: 'textarea',
                                field: 'approval',
                                spanForm: 24,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        // {
                        //     isInTable: false,
                        //     form: {
                        //         label: '附件',
                        //         field: 'fileList',
                        //         type: 'files',
                        //         fetchConfig: {
                        //             apiName: 'upload'
                        //         },
                        //         spanForm: 12,
                        //         formItemLayoutForm: {
                        //             labelCol: {
                        //                 xs: { span: 24 },
                        //                 sm: { span: 6 }
                        //             },
                        //             wrapperCol: {
                        //                 xs: { span: 24 },
                        //                 sm: { span: 18 }
                        //             }
                        //         }
                        //     }
                        // }
                    ]}
                    method={{
                        disabledFunEdit: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1 || data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        willExecuteFunEdit: (obj) => {
                            if (obj.selectedRows[0].apih5FlowStatus === '1' || obj.selectedRows[0].apih5FlowStatus === '2') {
                                obj.btnCallbackFn.clearSelectedRows();
                                Msg.warn('请选择未审核的数据!');
                                return false
                            } else {

                            }
                            obj.btnCallbackFn.clearSelectedRows();
                        },
                        disabledFunDel: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length > 0) {
                                for (let m = 0; m < data.length; m++) {
                                    if (data[m].apih5FlowStatus === '1' || data[m].apih5FlowStatus === '2') {
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
                                if (obj.selectedRows[0].apih5FlowStatus === '1' || obj.selectedRows[0].apih5FlowStatus === '2') {
                                    //存在已审核的数据
                                    arry.push(obj.selectedRows[m].apih5FlowStatus);
                                }
                            }
                            if (arry.length > 0) {
                                Msg.warn('请选择未审核的数据！')
                            } else {
                                confirm({
                                    content: '确定删除选中的数据吗?',
                                    onOk: () => {
                                        myFetch('batchDeleteUpdateZxSkPurchase', obj.selectedRows).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    this.table.refresh();
                                                } else {
                                                }
                                            }
                                        );
                                    }
                                });
                            }
                            obj.btnCallbackFn.clearSelectedRows();
                        },
                        disabledFunStatus: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && data[0].apih5FlowStatus === '-1') {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        disabledFunDetail: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && (data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2')) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        disabledFunJinDuSearch: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && (data[0].apih5FlowStatus !== '-1' && data[0].id.length <= 32)) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }}
                    componentsKey={{
                        PurchaeApplicatin: (obj) => {
                            return <PurchaeApplicatin {...obj} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
                        },
                        DeetailForm: (obj) => {
                            return <DeetailForm {...obj} flowData={obj.selectedRows[0]} />
                        },
                        Operation: (props) => {
                            return <Operation apiName={'openFlowByReport'} detailApiName={'getZxSkPurchaseDetail'} flowId={'zxSkPurchaseWorkId'} {...props} />
                        }
                    }}
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
                    //                     apiName: 'addZxSkPurchase'
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
                    //                     apiName: 'updateZxSkPurchase'
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'diyDel',
                    //         icon: 'delete',
                    //         type: 'danger',
                    //         label: '删除',
                    //         disabled: 'bind:disabledFunDel',
                    //         onClick: 'bind:onClickFunDel'
                    //     },
                    //     {
                    //         drawerTitle: "周转材料购置申请",
                    //         name: 'Component',
                    //         type: 'primary',
                    //         label: '评审申请',
                    //         disabled: 'bind:disabledFunStatus',
                    //         Component: 'PurchaeApplicatin'
                    //     },
                    //     {
                    //         drawerTitle: "详情",
                    //         name: 'Component',
                    //         type: 'primary',
                    //         label: '详情',
                    //         disabled: 'bind:disabledFunDetail',
                    //         Component: 'DeetailForm'
                    //     },
                    //     {
                    //         name: 'Component',
                    //         type: 'primary',
                    //         label: '进度查询',
                    //         drawerTitle: '进度查询',
                    //         disabled: 'bind:disabledFunJinDuSearch',
                    //         Component: 'Operation'
                    //     },
                    // ]}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            var props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "PurchaseApplication"
                            }
                        }
                    }}
                />
            </div>
        );
    }
}

export default index;