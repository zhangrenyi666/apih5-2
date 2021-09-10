import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from 'antd';
import PurchaeApplicatin from './form';
import Operation from '../../MaterialsManagement/ImportPriceLimitChange/operation';
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
        this.state = {}
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
        const { departmentId, companyName, companyId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                            orgID: departmentId,
                            // selectType: "1"
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
                                            departmentId: departmentId
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
                                        comName: 'companyName'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getZxCtContractList',
                                    otherParams: {
                                        contrStatus: "1",
                                        orgID: departmentId
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
                            table: {
                                title: '发起人',
                                width: 100,
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
                        {
                            isInTable: false,
                            form: {
                                label: '附件',
                                field: 'fileList',
                                type: 'files',
                                fetchConfig: {
                                    apiName:'upload'
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
                        }
                    ]}
                    actionBtns={[
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
                            willExecute: (val) => {
                                val.btnCallbackFn.setBtnsDisabled('remove', 'addsubmit');
                                val.btnCallbackFn.setBtnsLoading('remove', 'addsubmit');
                            },
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'diysubmit',
                                    type: 'primary',
                                    label: '保存',
                                    field: 'addsubmit',
                                    onClick: (val) => {
                                        const { myFetch } = this.props;
                                        val.btnCallbackFn.setBtnsDisabled('add', 'addsubmit');
                                        val.btnCallbackFn.setBtnsLoading('add', 'addsubmit');
                                        myFetch('addZxSkPurchase', val._formData).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.table.refresh();
                                                    this.table.closeDrawer();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
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
                                if (obj.selectedRows[0].apih5FlowStatus === '1' || obj.selectedRows[0].apih5FlowStatus === '2') {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('请选择未审核的数据!');
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
                                    name: 'diySubmit',
                                    type: 'primary',
                                    label: '保存',
                                    onClick: (val) => {
                                        const { myFetch } = this.props;
                                        myFetch('updateZxSkPurchase', val._formData).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.table.refresh();
                                                    this.table.closeDrawer();
                                                    this.table.clearSelectedRows();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
                                    }
                                }
                            ]
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
                                                        this.table.clearSelectedRows();
                                                    } else {
                                                    }
                                                }
                                            );
                                        }
                                    });
                                }
                            }
                        },

                        {
                            drawerTitle: "周转材料购置申请",
                            name: 'Component',
                            type: 'primary',
                            label: '评审申请',
                            disabled: "bind:_actionBtnNoSelected",
                            Component: (obj) => {
                                this.table.clearSelectedRows();
                                if (obj.selectedRows[0].workId != "") {
                                    obj.btnCallbackFn.closeDrawer();
                                    obj.btnCallbackFn.msg.error('已发起审批的不可再发起！');
                                    return <div />
                                }
                                if (obj.selectedRows.length !== 1) {
                                    obj.btnCallbackFn.closeDrawer();
                                    obj.btnCallbackFn.msg.error('只能选择一条数据！');
                                    return <div />
                                }
                                return <PurchaeApplicatin {...obj} flowData={obj.selectedRows[0]} />
                            }
                        },
                        {
                            name: 'Component',
                            type: 'primary',
                            label: '进度查询',
                            drawerTitle: '进度查询',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && data[0].workId) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            Component: (props) => {
                                return <Operation apiName={'openFlowByReport'} detailApiName={'getZxSkPurchaseDetail'} flowId={'zxSkPurchaseWorkId'} {...props} />
                            }
                        },
                    ]}

                />
            </div>
        );
    }
}

export default index;