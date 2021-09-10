import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { Modal, message as Msg } from 'antd';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.id
        },
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
        this.state = {

        }
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
                        apiName: 'getZxSaProjectSetList',
                        otherParams:{
                            contrType:"mater",
                            orgID:departmentId
                        }
                    }}
                    actionBtns={[
                        {
                            name: 'add',//内置add del
                            icon: 'plus',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '新增',
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
                                        apiName: 'addZxSaProjectSet',
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',//内置add del
                            icon: 'edit',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '修改',
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                },
                                {
                                    name: 'diysubmit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '保存',//提交数据并且关闭右边抽屉 
                                    field:'diysubmit',
                                    // fetchConfig: {//ajax配置
                                    //     apiName: 'updateZxSaProjectSet',
                                    // },
                                    onClick:(obj) => {
                                        let setBtnsLoading = obj.btnCallbackFn.setBtnsLoading;
                                        setBtnsLoading('add', 'diysubmit');
                                        let itemList = [];
                                        if(obj?._formData?.itemList?.length){
                                            obj._formData.itemList.map((item) => {
                                                if(!item.quoteFlag2 || String(item.quoteFlag2) === '0'){
                                                    itemList.push(item);
                                                }
                                                return item;
                                            })
                                        }
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
                                },
                            ]
                        },
                        {
                            name: 'Diydel',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            disabled:(obj) => {
                                if(obj.btnCallbackFn.getSelectedRows().length){
                                    return false;
                                }else{
                                    return true;
                                }
                            },
                            // fetchConfig: {//ajax配置
                            //     apiName: 'batchDeleteUpdateZxSaProjectSet',
                            //     otherParams:{
                            //         contrTypeFlag3:'3'
                            //     }
                            // },
                            onClick:(obj) => {//内置的按钮一般不需要传
                                confirm({
                                    content: '确定删除数据吗?',
                                    onOk: () => {
                                        obj.selectedRows = obj.selectedRows.map((item) => {
                                            item.contrTypeFlag2 = '2';
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
                        }
                    ]}
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
                                field: 'contrTypeFlag2',
                                type: 'string',
                                initialValue:'2',
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
                                initialValue: 'mater',
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
                                required:true,
                                editDisabled:true,
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
                                hide:true,
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
                                                disabled: String(record.quoteFlag2) !== '0'
                                            }),
                                        }
                                    },
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
                                            isInTable: false,
                                            form: {
                                                label: '禁用',
                                                field: 'quoteFlag2',
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
                                                        label: '垫资费',
                                                        value: '垫资费'
                                                    },
                                                    {
                                                        label: '奖罚金',
                                                        value: '奖罚金'
                                                    },
                                                    {
                                                        label: '其他款项',
                                                        value: '其他款项'
                                                    },
                                                    {
                                                        label: '运杂费',
                                                        value: '运杂费'
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
                                            label: "新增行"
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