import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { message as Msg, Modal } from 'antd';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'zxSkStockDifMonthId',
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
    paginationConfig: false,
    actionBtnsPosition: "top",
    firstRowIsSearch: false,
    isShowRowSelect: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }
    componentDidMount() {

    }
    render() {
        const { departmentId,companyId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                    fetchConfig={{
                        apiName: 'getZxSkStockDifMonthList',
                        otherParams: {
                            projectID: departmentId
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSkStockDifMonthId',
                                type: 'string',
                                hide: true,
                            }
                        },
                    
                        
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'comID',
                                initialValue: companyId,
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '公司名称',
                                onClick: 'detail',
                                dataIndex: 'comName',
                                key: 'comName'
                            },
                            form: {
                                type: 'string',
                                field: 'comName',
                                addDisabled: true,
                                editDisabled: true,
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
                            table: {
                                title: '项目名称',
                                dataIndex: 'projectID',
                                key: 'projectID',
                                type: 'select'
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
                                        projectName: 'departmentName',
                                        comName:'companyName'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    // otherParams: {
                                    //     contrStatus: "1",
                                    //     orgID: departmentId
                                    // }
                                    otherParams: {
                                        departmentId: departmentId
                                    }
                                },
                                placeholder: '请选择',
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
                                },
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'projectName',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '期次',
                                dataIndex: 'periodDate',
                                key: 'periodDate',
                                format: 'YYYY-MM'
                            },
                            form: {
                                type: 'month',
                                required:true,
                                field: 'periodDate',
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
                            table: {
                                title: '填报人',
                                width: 120,
                                dataIndex: 'reporter',
                                key: 'reporter'
                            },
                            form: {
                                type: 'string',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                field: 'reporter',
                                initialValue: () => {
                                    return this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                                },
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
                                title: '填报日期',
                                width: 120,
                                dataIndex: 'reportDate',
                                format: 'YYYY-MM-DD',
                                key: 'reportDate',
                                filter: true
                            },
                            form: {
                                type: 'date',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                field: 'reportDate',
                                initialValue: () => {
                                    return moment(new Date()).format('YYYY-MM-DD')
                                },
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
                            table: {
                                title: '评审状态',
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
                                type: 'select',
                                field: 'status',
                                hide: true,
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "未审核",
                                        value: "0"
                                    },
                                    {
                                        label: "已审核",
                                        value: "1"
                                    }
                                ],
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
                            form: {
                                type: 'qnnTable',
                                field: 'zxSkStockDifMonthItemList',
                                incToForm: true,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'zxSkStockDifMonthItemId',
                                        size: 'small'
                                    },
                                    ...configItem,
                                    tableTdEdit: true,
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '主键id',
                                                field: 'zxSkStockDifMonthItemId',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '类型',
                                                dataIndex: 'mtType',
                                                width: 160,
                                                key: 'mtType',
                                                type: 'select',
                                                tdEdit: true
                                            },
                                            form: {
                                                field: 'mtType',
                                                required:true,
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'label',
                                                    value: 'value',
                                                },
                                                optionData: [
                                                    {
                                                        label: "原材料",
                                                        value: "1"
                                                    },
                                                    {
                                                        label: "自拌混合料",
                                                        value: "2"
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            table: {
                                                title: '物资大类',
                                                dataIndex: 'categoryName',
                                                width: 200,
                                                key: 'categoryName',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'selectByPaging',
                                                field: 'categoryID',
                                                required:true,
                                                optionConfig: {
                                                    label: 'catName',
                                                    value: 'id'
                                                },
                                                fetchConfig: {
                                                    apiName: "getZxSkResCategoryMaterialsListResource",
                                                    otherParams: async () => {
                                                        const vals = await this.table.btnCallbackFn.qnnForm.getValues(false);
                                                        return {
                                                            parentOrgID: vals.projectID,
                                                        }
                                                    }
                                                },
                                                onChange: (val, thisProps, btnCallbackFn) => {
                                                    if (val) {
                                                        btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                            editRowData.categoryID = val;
                                                            const itemData = thisProps;
                                                            editRowData.categoryName = itemData.itemData.catName;
                                                            btnCallbackFn.setEditedRowData({
                                                                ...editRowData,
                                                            });
                                                        });
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '物资编码',
                                                dataIndex: 'resCode',
                                                width: 180,
                                                key: 'resCode',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'selectByQnnTable',
                                                label: '',
                                                field: 'resCode',
                                                required:true,
                                                parent: 'categoryID',
                                                optionConfig: {
                                                    label: 'resCode',
                                                    value: 'id'
                                                },
                                                dropdownMatchSelectWidth: 900,
                                                qnnTableConfig: {
                                                    antd: {
                                                        rowKey: "id"
                                                    },
                                                    firstRowIsSearch: false,
                                                    fetchConfig: {
                                                        apiName: "getZxSkResourceMaterialsListNameJoinResource",
                                                        otherParams: (obj) => {
                                                            return {
                                                                id: obj.qnnFormProps?.qnnformData?.qnnFormProps?.rowData?.categoryID,
                                                                parentOrgID:''
                                                            }
                                                        }
                                                    },
                                                    searchBtnsStyle: "inline",
                                                    formConfig: [
                                                        {
                                                            isInForm: false,
                                                            isInTable:false,
                                                            form: {
                                                                field:'id',
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch:true,
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
                                                            isInSearch:true,
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
                                                            isInSearch:true,
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
                                                            isInSearch:true,
                                                            table: {
                                                                dataIndex: "unit",
                                                                title: "单位",
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
                                                            resCode: itemData.itemData[0].resCode,
                                                            resName: itemData.itemData[0].resName,
                                                            unit: itemData.itemData[0].unit,
                                                            spec: itemData.itemData[0].spec,
                                                            price: itemData.itemData[0].price,
                                                            thisPrice: 0,
                                                                thisQty: 0,
                                                                designQty: 0,
                                                                sunHaoLv: 0
                                                        });
                                                    }else {
                                                        tableBtnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                            tableBtnCallbackFn.setEditedRowData({
                                                                ...editRowData,
                                                                resID: '',
                                                                resName: '',
                                                                unit: '',
                                                                spec: '',
                                                                thisPrice: 0,
                                                                thisQty: 0,
                                                                designQty: 0,
                                                                sunHaoLv: 0
                                                            });
                                                        })
                                                    }

                                                },
                                            }
                                        },
                                        {
                                            isInTable:false,
                                            form: {
                                                type: 'string',
                                                field: 'categoryName'
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
                                                title: '单位',
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
                                                title: '本月实际采购加权平均价',
                                                dataIndex: 'thisPrice',
                                                width: 150,
                                                key: 'thisPrice',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'thisPrice',
                                                precision: 6
                                            }
                                        },
                                        {
                                            table: {
                                                title: '本月实际消耗量',
                                                dataIndex: 'thisQty',
                                                width: 150,
                                                key: 'thisQty',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'thisQty',
                                                precision: 2
                                            }
                                        },
                                        {
                                            table: {
                                                title: '设计用量',
                                                dataIndex: 'designQty',
                                                width: 120,
                                                key: 'designQty',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'designQty'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '局定额损耗率(%)',
                                                dataIndex: 'sunHaoLv',
                                                width: 120,
                                                key: 'sunHaoLv',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'sunHaoLv'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '备注',
                                                dataIndex: 'remarks',
                                                width: 120,
                                                key: 'remarks',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'textarea',
                                                field: 'remarks',
                                                autoSize: {
                                                    minRows: 1,
                                                    maxRows: 3
                                                }
                                            }
                                        },
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
                                    fetchConfig: {
                                        apiName: 'addZxSkStockDifMonth'
                                    },
                                    field: 'addsubmit'
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
                                        apiName: 'updateZxSkStockDifMonth'
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
                                    if (obj.selectedRows[0].billStatus === '1') {
                                        obj.btnCallbackFn.closeDrawer();
                                        this.table.clearSelectedRows();
                                        Msg.warn('已审核的不能审核!');
                                    } else {
                                        obj.selectedRows[0].companyID = '1';
                                        confirm({
                                            content: '确定审核选中的数据吗?',
                                            onOk: () => {
                                                myFetch('checkZxSkStockDifMonth', obj.selectedRows[0]).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            Msg.warn(message)
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
                            name: 'diy',
                            type: 'primary',
                            label: '反审核',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {
                                const { myFetch } = this.props;
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].billStatus === '0') {
                                        obj.btnCallbackFn.closeDrawer();
                                        this.table.clearSelectedRows();
                                        Msg.warn('未审核的不能反审核!');
                                    } else {
                                        obj.selectedRows[0].companyID = '1';
                                        confirm({
                                            content: '确定反审核选中的数据吗?',
                                            onOk: () => {
                                                myFetch('counterCheckZxSkStockDifMonth', obj.selectedRows[0]).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            Msg.warn(message)
                                                            this.table.refresh();
                                                            this.table.clearSelectedRows();
                                                        } else {
                                                            Msg.warn(message);
                                                            this.table.clearSelectedRows();
                                                        }
                                                    }
                                                );
                                            }
                                        });

                                    }
                                } else {
                                    Msg.warn('只能反审核一条数据！')
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
                                            myFetch('batchDeleteUpdateZxSkStockDifMonth', obj.selectedRows).then(
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