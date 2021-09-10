import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import s from "./style.less";
import { message as Msg, Modal } from "antd";
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
            xs: { span: 11 },
            sm: { span: 11 }
        },
        wrapperCol: {
            xs: { span: 13 },
            sm: { span: 13 }
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
                        apiName: 'getZxEqEquipFactRealList',
                        otherParams:{
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
                                tableField: "ActualEquipmentAcceptanceLedger"
                            }
                        }
                    }}
                    method={{
                        editDisabled: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && (data[0].auditStatus === '' || data[0].auditStatus === '0')) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        auditDisabled:(obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && (data[0].auditStatus === '' || data[0].auditStatus === '0')) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        auditOnClick:(obj) => {
                            confirm({
                                content: '确定审核该条数据吗?',
                                onOk: () => {
                                    obj.selectedRows[0].auditStatus = '1';
                                    this.props.myFetch('auditZxEqEquipFactReal', ...obj.selectedRows).then(
                                        ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                obj.btnCallbackFn.refresh();
                                                obj.btnCallbackFn.clearSelectedRows();
                                            } else {
                                                Msg.error(message);
                                            }
                                        }
                                    );
                                }
                            });
                        },
                        auditeeDisabled:(obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && data[0].auditStatus === '1') {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        auditeeOnClick:(obj) => {
                            confirm({
                                content: '确定反审核该条数据吗?',
                                onOk: () => {
                                    obj.selectedRows[0].auditStatus = '0';
                                    this.props.myFetch('auditZxEqEquipFactReal', ...obj.selectedRows).then(
                                        ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                obj.btnCallbackFn.refresh();
                                                obj.btnCallbackFn.clearSelectedRows();
                                            } else {
                                                Msg.error(message);
                                            }
                                        }
                                    );
                                }
                            });
                        },
                        delDisabled:(obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length && !data.filter(item => item.auditStatus === '1').length) {
                                return false;
                            } else {
                                return true;
                            }
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
                                field: 'comID',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'orgName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'depID',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInForm:false,
                            table: {
                                title: '所属单位',
                                dataIndex: 'comName',
                                key: 'comName',
                                filter: true,
                                width: 150,
                                fixed: 'left',
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '使用单位',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: 150,
                                tooltip: 15,
                                fixed: 'left',
                                filter: true,
                                fieldsConfig: {
                                    type: 'select',
                                    field: 'orgIDSearch',
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyProject',
                                        otherParams: {
                                            departmentId: departmentId
                                        }
                                    },
                                }
                            },
                            form: {
                                field:'orgID',
                                type: 'select',
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
                                allowClear: false,
                                showSearch: true,
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '期次',
                                dataIndex: 'periodDate',
                                key: 'periodDate',
                                width: 100,
                                format: 'YYYY-MM',
                                filter: true,
                            },
                            form: {
                                type: 'month',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '记录人',
                                dataIndex: 'reporter',
                                key: 'reporter',
                                width: 100,
                            },
                            form: {
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: '测试',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '部门',
                                dataIndex: 'depName',
                                key: 'depName',
                                width: 100,
                            },
                            form: {
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '记录时间',
                                dataIndex: 'reportDate',
                                key: 'reportDate',
                                width: 100,
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '自有设备数量',
                                dataIndex: 'source1',
                                key: 'source1',
                                width: 120
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '外租设备数量',
                                dataIndex: 'source2',
                                key: 'source2',
                                width: 120
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '协作队伍数量',
                                dataIndex: 'source3',
                                key: 'source3',
                                width: 120
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '审核状态',
                                dataIndex: 'auditStatus',
                                key: 'auditStatus',
                                width: 100,
                                render: (data) => {
                                    if (data === '' || data === '0') {
                                        return '未审核';
                                    } else {
                                        return '已审核';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '复制期次',
                                field: 'copyPeriod',
                                type: 'string',
                                // type: 'select',
                                // optionConfig: {//下拉选项配置
                                //     label: 'period',
                                //     value: 'id',
                                //     linkageFields: {
                                //         itemList: 'itemList',
                                //     }
                                // },
                                // fetchConfig: {
                                //     apiName: 'getZxEqEquipFactListForCopy'
                                // },
                                placeholder: '请选择',
                                // allowClear: false,
                                // showSearch: true,
                                // addDisabled: true,
                                // editDisabled: true,
                                // editShow:false,
                                // detailShow:false,
                                hide:true,
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '复制期次',
                                field: 'copyPeriodDate',
                                type: 'month',
                                placeholder: '请选择',
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8
                            }
                        },
                        // {
                        //     isInTable:false,
                        //     form: {
                        //         label:'复制上一期',
                        //         field:'isUse',
                        //         type: 'radio',
                        //         optionData:[
                        //             {
                        //                 label:'否',
                        //                 value:'0'
                        //             },
                        //             {
                        //                 label:'是',
                        //                 value:'1'
                        //             }
                        //         ],
                        //         spanForm: 8
                        //     }
                        // },
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
                                    isShowRowSelect:false,
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
                                                title: '序号',
                                                dataIndex: 'index',
                                                key: 'index',
                                                width: 80,
                                                fixed: 'left',
                                                render: (data, rowData, index) => {
                                                    return index + 1;
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '机械名称',
                                                dataIndex: 'equipID',
                                                key: 'equipID',
                                                width: 200,
                                                fixed: 'left',
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {//下拉选项配置
                                                    label: 'catName',
                                                    value: 'id'
                                                },
                                                fetchConfig: {
                                                    apiName: 'getZxEqResCategoryItemList',
                                                    otherParams:{
                                                        isGroup:'1'
                                                    }
                                                },
                                                allowClear: false,
                                                showSearch: true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '规格',
                                                dataIndex: 'spec',
                                                key: 'spec',
                                                width: 150
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '生产厂家',
                                                dataIndex: 'factory',
                                                key: 'factory',
                                                width: 150
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '车牌号',
                                                dataIndex: 'carNo',
                                                key: 'carNo',
                                                width: 150
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '供应商',
                                                dataIndex: 'supplier',
                                                key: 'supplier',
                                                width: 150
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '进场日期',
                                                dataIndex: 'inDate',
                                                key: 'inDate',
                                                width: 150,
                                                format: 'YYYY-MM-DD'
                                            },
                                            form: {
                                                type: 'date',
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '设备来源',
                                                dataIndex: 'source',
                                                key: 'source',
                                                width: 150,
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
                                                        label: "租赁",
                                                        value: "2"
                                                    },
                                                    {
                                                        label: "协作队伍自带",
                                                        value: "3"
                                                    },
                                                    {
                                                        label: "自购",
                                                        value: "1"
                                                    }
                                                ],
                                                allowClear: false,
                                                showSearch: true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '是否退场',
                                                dataIndex: 'isOut',
                                                key: 'isOut',
                                                width: 100,
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
                                                allowClear: false,
                                                showSearch: true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: "<div>验收设备名称<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'realEquipName',
                                                key: 'realEquipName',
                                                width: 150,
                                                tdEdit:true
                                            },
                                            form: {
                                                type: 'selectByQnnTable',
                                                optionConfig: {//下拉选项配置
                                                    label: 'equipName',
                                                    value: 'equipName'
                                                },
                                                // fetchConfig: {
                                                //     apiName: 'getZxEqEquipListForRealFact',
                                                //     otherParams:(obj) => {
                                                //         return {
                                                //             orgID: obj?.props?.form?.getFieldValue?.('orgID'),
                                                //             source: obj?.rowData?.source,
                                                //             equipScrap:'checkequip'
                                                //         }
                                                //     }
                                                // },
                                                dropdownMatchSelectWidth: 800,
                                                qnnTableConfig: {
                                                    antd: {
                                                        rowKey: "id"
                                                    },
                                                    fetchConfig: {
                                                        apiName: 'getZxEqEquipListForRealFact',
                                                        otherParams:(obj) => {
                                                            return {
                                                                orgID: obj?.props?.qnnFormProps?.form?.getFieldValue?.('orgID'),
                                                                source: obj?.rowData?.source,
                                                                equipScrap:'checkequip'
                                                            }
                                                        }
                                                    },
                                                    searchBtnsStyle: "inline",
                                                    formConfig: [
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "equipNo",
                                                                title: "设备编号",
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
                                                                dataIndex: "equipName",
                                                                title: "设备名称",
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
                                                                dataIndex: "spec",
                                                                title: "规格",
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
                                                                dataIndex: "model",
                                                                title: "型号",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        }
                                                    ]
                                                },
                                                onChange: (val, obj, btnCallbackFn) => {
                                                    btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                        editRowData.realEquipID = obj.itemData.id;
                                                        editRowData.realEquipName = obj.itemData.equipName;
                                                        editRowData.realEquipNo = obj.itemData.equipNo;
                                                        editRowData.realUnit = obj.itemData.spec;
                                                        editRowData.realSpec = obj.itemData.model;
                                                        btnCallbackFn.setEditedRowData({ ...editRowData });
                                                    });
                                                },
                                                allowClear: false,
                                                showSearch: true,
                                                required:true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '验收设备编码',
                                                field: 'realEquipNo',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '验收设备名称',
                                                field: 'realEquipID',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '规格',
                                                dataIndex: 'realUnit',
                                                key: 'realUnit',
                                                width: 100
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '型号',
                                                dataIndex: 'realSpec',
                                                key: 'realSpec',
                                                width: 100
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        }
                                    ]
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '备注',
                                field: 'remark',
                                type: 'textarea',
                                placeholder: '请输入',
                                spanForm: 21,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 4 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 20 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "附件",
                                field: "fileList",
                                type: "files",
                                initialValue: [],
                                fetchConfig: {
                                    apiName: "upload"
                                },
                                spanForm: 21,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 4 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 20 }
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