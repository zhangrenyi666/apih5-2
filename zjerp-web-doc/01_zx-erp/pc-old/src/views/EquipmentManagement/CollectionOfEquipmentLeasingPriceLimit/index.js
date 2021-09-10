import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import s from "./style.less";
import { message as Msg, Modal } from "antd";
import FlowFormByCollectionOfEquipmentLeasingPriceLimit from './form';
import Operation from './operation';
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
    isShowRowSelect:true
};
class index extends Component {
    render() {
        const { departmentId, departmentName, companyId, companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { realName, ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        return (
            <div className={s.root}>
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
                        apiName: 'getZxEqEquipLimitPriceList',
                        otherParams: {
                            orgID: departmentId
                        }
                    }}
                    actionBtns={[
                        {
                            name: 'add',//内置add del
                            icon: 'plus',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '新增',
                            hide: () => {
                                if (ext1 === '3' || ext1 === '4') {
                                    return true;
                                } else {
                                    return false;
                                }
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
                                        apiName: 'addZxEqEquipLimitPrice',
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',//内置add del
                            icon: 'edit',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '修改',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && (data[0].apih5FlowStatus === '0' || data[0].apih5FlowStatus === '-1' || data[0].apih5FlowStatus === '3')) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            hide: () => {
                                if (ext1 === '3' || ext1 === '4') {
                                    return true;
                                } else {
                                    return false;
                                }
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
                                        apiName: 'updateZxEqEquipLimitPrice',
                                    },
                                    onClick: (obj) => {
                                        obj.btnCallbackFn.clearSelectedRows();
                                    }
                                }
                            ]
                        },
                        {
                            name: 'copy',//内置add del
                            icon: 'plus',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '复制',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            hide: () => {
                                if (ext1 === '3' || ext1 === '4') {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            onClick: (obj) => {
                                if (obj.selectedRows.length === 1) {
                                    confirm({
                                        content: '确定复制该条数据吗?',
                                        onOk: () => {
                                            this.props.myFetch('copyZxEqEquipLimitPrice', ...obj.selectedRows).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        obj.btnCallbackFn.refresh();
                                                        obj.btnCallbackFn.clearSelectedRows();
                                                    } else {
                                                        Msg.error(message);
                                                    }
                                                }
                                            );
                                        }
                                    });
                                } else {
                                    Msg.error('请选择一条数据复制!');
                                }
                            }
                        },
                        {
                            name: 'Component',
                            type: 'primary',
                            label: '发起评审',
                            drawerTitle: '发起评审',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && (data[0].apih5FlowStatus === '' || data[0].apih5FlowStatus === '-1')) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            hide: () => {
                                if (ext1 === '3' || ext1 === '4') {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            Component: (props) => {
                                let flowData = props.selectedRows[0];
                                this.table.btnCallbackFn.clearSelectedRows();
                                return <FlowFormByCollectionOfEquipmentLeasingPriceLimit {...this.props} btnCallbackFn={this.table.btnCallbackFn} isInQnnTable={props.isInQnnTable} flowData={{ ...flowData }} />;
                            }
                        },
                        {
                            name: 'Component',
                            type: 'primary',
                            label: '进度查询',
                            drawerTitle: '进度查询',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && data[0].apih5FlowStatus !== '' && data[0].apih5FlowStatus !== '-1') {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            hide: () => {
                                if (ext1 === '3' || ext1 === '4') {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            Component: (props) => {
                                return <Operation apiName={'openFlowByReport'} {...props} />
                            }
                        },
                        {
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (!data.filter(item => item.apih5FlowStatus === '1' || item.apih5FlowStatus === '2').length) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            hide: () => {
                                if (ext1 === '3' || ext1 === '4') {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZxEqEquipLimitPrice',
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
                                field: 'workId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'orgID',
                                initialValue: departmentId,
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'comID',
                                initialValue: companyId,
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'orgName',
                                initialValue: departmentName,
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '评审编号',
                                dataIndex: 'applyNo',
                                key: 'applyNo',
                                filter: true,
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                required: true,
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '公司名称',
                                dataIndex: 'comName',
                                key: 'comName',
                                filter: true
                            },
                            form: {
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: () => {
                                    return companyName;
                                },
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '数据期次',
                                dataIndex: 'periodDate',
                                key: 'periodDate',
                                render: (data) => {
                                    if (data) {
                                        return moment(data).format('YYYY') + `${moment(data).month() < 6 ? '/上半年' : '/下半年'}`;
                                    } else {
                                        return '';
                                    }
                                }
                            },
                            form: {
                                type: 'halfYear',
                                allowClear: false,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '审核状态',
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                initialValue: '-1',
                                optionData: [
                                    {
                                        label: '未审核',
                                        value: '-1'
                                    },
                                    {
                                        label: '待提交',
                                        value: '0'
                                    },
                                    {
                                        label: '审核中',
                                        value: '1'
                                    },
                                    {
                                        label: '审核通过',
                                        value: '2'
                                    },
                                    {
                                        label: '退回',
                                        value: '3'
                                    }
                                ],
                                addShow: false,
                                editShow: false,
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: '-1',
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '填报人',
                                dataIndex: 'reporter',
                                key: 'reporter'
                            },
                            form: {
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: () => {
                                    return realName;
                                },
                                spanForm: 8
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
                                initialValue: new Date(),
                                placeholder: '请选择',
                                spanForm: 8
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
                                    wrappedComponentRef: (me) => {
                                        this.tableOne = me;
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
                                                field: 'equipName',
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
                                                title: '机种分类',
                                                dataIndex: 'resCatalogID',
                                                key: 'resCatalogID',
                                                width: 150,
                                                fixed: 'left',
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: "<div>设备名称<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'equipName',
                                                key: 'equipName',
                                                width: 150,
                                                fixed: 'left',
                                                tdEdit: true
                                            },
                                            form: {
                                                field:'equipID',
                                                type: 'selectByQnnTable',
                                                optionConfig: {//下拉选项配置
                                                    label: 'catName',
                                                    value: 'id'
                                                },
                                                dropdownMatchSelectWidth: 800,
                                                qnnTableConfig: {
                                                    antd: {
                                                        rowKey: "id"
                                                    },
                                                    fetchConfig: {
                                                        apiName: "getZxEqResCategoryItemList"
                                                    },
                                                    searchBtnsStyle: "inline",
                                                    formConfig: [
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "catCode",
                                                                title: "编号",
                                                                width: 100
                                                            },
                                                            form: {
                                                                type: 'string'
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "catName",
                                                                title: "名称",
                                                                width: 100
                                                            },
                                                            form: {
                                                                type: 'string'
                                                            }
                                                        }
                                                    ]
                                                },
                                                onChange: async(val, obj, btnCallbackFn) => {
                                                    btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                        console.log("getEditedRowData:", editRowData)
                                                        if (obj?.itemData?.length) {
                                                            editRowData.equipNo = obj.itemData[0].catCode;
                                                            editRowData.equipName = obj.itemData[0].catName;
                                                            editRowData.resCatalogID = obj.itemData[0].parentName;
                                                        }
                                                        btnCallbackFn.setEditedRowData({ ...editRowData });
                                                    });
                                                },
                                                required: true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '设备编码',
                                                dataIndex: 'equipNo',
                                                key: 'equipNo',
                                                width: 150
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '所在省份',
                                                dataIndex: 'province',
                                                key: 'province',
                                                width: 150,
                                                tdEdit: true,
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'xingzhengquhuadaima'
                                                    }
                                                },
                                                showSearch: true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '厂家',
                                                dataIndex: 'factory',
                                                key: 'factory',
                                                width: 150,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '规格型号',
                                                dataIndex: 'spec',
                                                key: 'spec',
                                                width: 150,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '工作时间',
                                                dataIndex: 'workTime',
                                                key: 'workTime',
                                                width: 150,
                                                tdEdit: true,
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {//下拉选项配置
                                                    label: 'label',
                                                    value: 'value'
                                                },
                                                optionData: [
                                                    {
                                                        label: '单班',
                                                        value: '0'
                                                    },
                                                    {
                                                        label: '双班',
                                                        value: '1'
                                                    }
                                                ],
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '租赁情况',
                                                dataIndex: 'rentContent',
                                                key: 'rentContent',
                                                width: 150,
                                                tdEdit: true,
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {//下拉选项配置
                                                    label: 'label',
                                                    value: 'value'
                                                },
                                                optionData: [
                                                    {
                                                        label: '六个月以下',
                                                        value: '0'
                                                    },
                                                    {
                                                        label: '六个月及以上',
                                                        value: '1'
                                                    }
                                                ],
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '燃油情况',
                                                dataIndex: 'ranyouQingkuang',
                                                key: 'ranyouQingkuang',
                                                width: 150,
                                                tdEdit: true,
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {//下拉选项配置
                                                    label: 'label',
                                                    value: 'value'
                                                },
                                                optionData: [
                                                    {
                                                        label: '甲方承担',
                                                        value: '0'
                                                    },
                                                    {
                                                        label: '乙方承担',
                                                        value: '1'
                                                    }
                                                ],
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '租赁限价(元/台.月)',
                                                dataIndex: 'rentPrice',
                                                key: 'rentPrice',
                                                width: 150,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '是否含司机',
                                                dataIndex: 'isDriver',
                                                key: 'isDriver',
                                                width: 150,
                                                tdEdit: true,
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'label',
                                                    value: 'value',
                                                },
                                                optionData: [
                                                    {
                                                        label: "否",
                                                        value: "0"
                                                    },
                                                    {
                                                        label: "是",
                                                        value: "1"
                                                    }
                                                ],
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '备注',
                                                dataIndex: 'remark',
                                                key: 'remark',
                                                width: 150,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
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
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remark',
                                key: 'remark'
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 2 },
                                        sm: { span: 2 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 22 },
                                        sm: { span: 22 }
                                    }
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