import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { Modal, message as Msg } from 'antd';
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
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId
        }
    }
    render() {
        const { departmentId } = this.state;
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
                        apiName: 'getZxSaProjectSetList',
                        otherParams:{
                            contrType:"P6",
                            orgID:departmentId
                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            let props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "MachineryLeasingSettlementAndPaymentSetting"
                            }
                        }
                    }}
                    method={{
                        diysubmitOnclick:(obj) => {
                            let setBtnsLoading = obj.btnCallbackFn.setBtnsLoading;
                            setBtnsLoading('add', 'diysubmit');
                            let itemList = [];
                            if (obj?._formData?.itemList?.length) {
                                const nullRows = obj._formData.itemList.filter(item => !item.workName || !item.workType);
                                if (nullRows.length) {
                                    Msg.error("请填写明细必填项!");
                                    setBtnsLoading('remove', 'diysubmit');
                                } else {
                                    obj._formData.itemList.map((item) => {
                                        if (!item.quoteFlag3 || String(item.quoteFlag3) === '0') {
                                            itemList.push(item);
                                        }
                                        return item;
                                    })
                                    obj._formData.itemList = itemList;
                                    this.props.myFetch('updateZxSaProjectSet', obj._formData).then(({ success, message, data }) => {
                                        if (success) {
                                            setBtnsLoading('remove', 'diysubmit');
                                            obj.btnCallbackFn.closeDrawer(false);
                                            obj.btnCallbackFn.clearSelectedRows();
                                            obj.btnCallbackFn.refresh();
                                        } else {
                                            setBtnsLoading('remove', 'diysubmit');
                                            Msg.error(message);
                                        }
                                    });
                                }
                            } else {
                                this.props.myFetch('updateZxSaProjectSet', obj._formData).then(({ success, message, data }) => {
                                    if (success) {
                                        setBtnsLoading('remove', 'diysubmit');
                                        obj.btnCallbackFn.closeDrawer(false);
                                        obj.btnCallbackFn.clearSelectedRows();
                                        obj.btnCallbackFn.refresh();
                                    } else {
                                        setBtnsLoading('remove', 'diysubmit');
                                        Msg.error(message);
                                    }
                                });
                            }
                        },
                        diydelDisabled:(obj) => {
                            if (obj.btnCallbackFn.getSelectedRows().length) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        diydelOnClick:(obj) => {//内置的按钮一般不需要传
                            confirm({
                                content: '确定删除数据吗?',
                                onOk: () => {
                                    obj.selectedRows = obj.selectedRows.map((item) => {
                                        item.contrTypeFlag3 = '3';
                                        return item;
                                    });
                                    this.props.myFetch('batchDeleteUpdateZxSaProjectSet', obj.selectedRows).then(({ success, message, data }) => {
                                        if (success) {
                                            obj.btnCallbackFn.clearSelectedRows();
                                            obj.btnCallbackFn.refresh();
                                        } else {
                                            Msg.error(message);
                                            obj.btnCallbackFn.clearSelectedRows();
                                        }
                                    });
                                }
                            });
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
                                field: 'contrTypeFlag3',
                                type: 'string',
                                initialValue:'3',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'comID',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'comName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'contrType',
                                type: 'string',
                                initialValue: 'P6',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label:'项目名称',
                                field:'orgID',
                                type: 'select',
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId',
                                    linkageFields:{
                                        orgName:'departmentName',
                                        comID:'companyId',
                                        comName:'companyName'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams:{
                                        departmentId:departmentId
                                    }
                                },
                                showSearch:true,
                                required:true,
                                editShow:false,
                                detailShow:false,
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                filter: true,
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                addShow:false,
                                editDisabled:true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'itemList',
                                incToForm: true,
                                qnnTableConfig: {
                                    actionBtnsPosition: "top",
                                    antd: {
                                        rowKey: 'id',
                                        size: 'small'
                                    },
                                    paginationConfig: {
                                        position: 'bottom'
                                    },
                                    getRowSelection: function (obj) {
                                        return {
                                            //设置某行为禁止选中
                                            getCheckboxProps: record => ({
                                                name:record.name,
                                                disabled: String(record.quoteFlag3) !== '0'
                                            }),
                                        }
                                    },
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
                                            isInTable: false,
                                            form: {
                                                label: '禁用',
                                                field: 'quoteFlag3',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '主表id',
                                                field: 'mainID',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '项目id',
                                                field: 'orgID',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '序号',
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
                                                title: '编号',
                                                dataIndex: 'workNo',
                                                key: 'workNo',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: `<div>支付项类型<span style='color: #ff4d4f'>*</span></div>`,
                                                dataIndex: 'workType',
                                                key: 'workType',
                                                type:'select',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'label',
                                                    value: 'value'
                                                },
                                                optionData: [
                                                    {
                                                        label: '伙食费',
                                                        value: '伙食费'
                                                    },
                                                    {
                                                        label: '奖罚',
                                                        value: '奖罚'
                                                    },
                                                    {
                                                        label: '进退场费',
                                                        value: '进退场费'
                                                    },
                                                    {
                                                        label: '其他款项',
                                                        value: '其他款项'
                                                    }
                                                ],
                                                required:true,
                                                placeholder: '请选择'
                                            }
                                        },
                                        {
                                            table: {
                                                title: `<div>支付项名称<span style='color: #ff4d4f'>*</span></div>`,
                                                dataIndex: 'workName',
                                                key: 'workName',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                required:true,
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '单位',
                                                dataIndex: 'unit',
                                                key: 'unit',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '备注',
                                                dataIndex: 'remark',
                                                key: 'remark',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        }
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "新增行",
                                            addRowDefaultData:{
                                                quoteFlag3:0
                                            }
                                        },
                                        {
                                            name: 'del',
                                            icon: 'delete',
                                            type: 'danger',
                                            label: '删除'
                                        }
                                    ]
                                }
                            }
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;