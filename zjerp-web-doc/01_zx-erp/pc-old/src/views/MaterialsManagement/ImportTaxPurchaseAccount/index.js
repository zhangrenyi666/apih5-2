import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { Modal } from 'antd';
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
const configItem = {
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: false,
    actionBtnsPosition: "top",
    firstRowIsSearch: false,
    isShowRowSelect: true
};
// const dataArry = [
//     {
//         id: 1,
//         title: "课程 1",
//         children: [
//             { id: 4, title: "课程 1-1" },
//             {
//                 id: 5,
//                 title: "课程 1-2",
//                 children: [
//                     { id: 6, title: "课程 1-2-1" },
//                     { id: 7, title: "课程 1-2-2" },
//                 ],
//             },
//         ],
//     },
//     { id: 2, title: "课程 2" },
//     { id: 3, title: "课程 3" },
// ];
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
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
        // let newArry = [];
        // for (let m = 0; m < dataArry.length; m++) {
        //     newArry.push({
        //         id: dataArry[0].id,
        //         title: dataArry[m].title
        //     });
        //     if (dataArry[m].children) {
        //         let two = dataArry[m].children;
        //         for (let n = 0; n < two.length; n++) {
        //             newArry.push({
        //                 id: two[n].id,
        //                 title: two[n].title
        //             });

        //             if (two[n].children) {
        //                 let three = two[n].children;
        //                 for (let j = 0; j < three.length; j++) {
        //                     newArry.push({
        //                         id: three[j].id,
        //                         title: three[j].title
        //                     });
        //                 }
        //             }
        //         }
        //     }
        // }
        // console.log(newArry);
        // // console.log(newArry.sort((a, b) => a.id- b.id));
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
                        apiName: 'getZxSkShopGoodsList',
                        otherParams: {
                            projectID: departmentId
                        }
                    }}
                    {...config}
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
                                title: '项目名称',
                                dataIndex: 'orgID',
                                key: 'orgID',
                                type: 'select',
                                filter: true,
                                fieldsConfig: {
                                    field: 'orgID2',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId',
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
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId',
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
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
                                }
                            },
                        },
                        {
                            table: {
                                title: '填写日期',
                                dataIndex: 'reportDate',
                                key: 'reportDate',
                                onClick: 'detail',
                                render: (data) => {
                                    return data ? moment(data).format('YYYY-MM-DD') : null
                                }
                            },
                            form: {
                                type: 'date',
                                required: true,
                                field: 'reportDate',
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
                                dataIndex: 'reporter',
                                key: 'reporter',
                            },
                            form: {
                                type: 'string',
                                field: 'reporter',
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
                                field: 'zxSkShopGoodsItemList',
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
                                                title: '物资编码',
                                                dataIndex: 'resCode',
                                                width: 180,
                                                key: 'resCode',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'selectByQnnTable',
                                                label: '',
                                                required: true,
                                                allowClear: false,
                                                field: 'resCode',
                                                //parent: 'resourceId',
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
                                                        apiName: "getZxSkResourceMaterialsListNotRevolving"
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
                                                            isInForm: false, isInSearch: true,
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
                                                            resCode: itemData.itemData[0].resCode,
                                                            resName: itemData.itemData[0].resName,
                                                            unit: itemData.itemData[0].unit,
                                                            spec: itemData.itemData[0].spec,
                                                            price: itemData.itemData[0].price,
                                                            production: '',
                                                            inDate: undefined,
                                                            inQty: 0,
                                                            whOrgID: '',
                                                            place: '',
                                                            customerID: '',
                                                            receClerk: ''
                                                        });
                                                    } else {
                                                        tableBtnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                            tableBtnCallbackFn.setEditedRowData({
                                                                ...editRowData,
                                                                resID: '',
                                                                resName: '',
                                                                unit: '',
                                                                spec: '',
                                                                production: '',
                                                                inDate: undefined,
                                                                inQty: 0,
                                                                whOrgID: '',
                                                                place: '',
                                                                customerID: '',
                                                                receClerk: ''
                                                            });
                                                        })
                                                    }

                                                },
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'resID',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '物资名称',
                                                width: 150,
                                                dataIndex: 'resName',
                                                key: 'resName',
                                                tooltip: 10,
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'resName'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '规格型号',
                                                dataIndex: 'spec',
                                                key: 'spec',
                                                width: 80,
                                                tooltip: 10,
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'spec'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '产地或品牌',
                                                dataIndex: 'production',
                                                key: 'production',
                                                width: 120,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'production'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '计量单位',
                                                dataIndex: 'unit',
                                                key: 'unit',
                                                width: 80,
                                                tooltip: 10,
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'unit'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '进货日期',
                                                dataIndex: 'inDate',
                                                key: 'inDate',
                                                format: 'YYYY-MM-DD',
                                                width: 160,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'date',
                                                field: 'inDate'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '进货数量',
                                                width: 100,
                                                dataIndex: 'inQty',
                                                key: 'inQty',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'inQty',
                                                precision: 3
                                            }
                                        },
                                        {
                                            table: {
                                                title: '存放地点',
                                                // dataIndex: 'place',
                                                // key: 'place',
                                                dataIndex: 'whOrgID',
                                                key: 'whOrgID',
                                                type: 'select',
                                                width: 200,
                                                tdEdit: true
                                            },
                                            form: {
                                                field: 'whOrgID',
                                                type: 'selectByPaging',
                                                optionConfig: {
                                                    label: 'warehouseName',
                                                    value: 'id'
                                                },
                                                fetchConfig: {
                                                    apiName: "getZxSkWarehouseList",
                                                    otherParams: async () => {
                                                        const vals = await this.table.btnCallbackFn.qnnForm.getValues(false);
                                                        return {
                                                            parentOrgID: vals.orgID,
                                                            limit: 999,
                                                            page: 1
                                                        }
                                                    }
                                                }
                                            },
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                type: 'string',
                                                field: 'place',
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                type: 'string',
                                                field: 'customerName',
                                                hide: true,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '供应商名称',
                                                width: 200,
                                                // dataIndex: 'customerName',
                                                // key: 'customerName',
                                                dataIndex: 'customerID',
                                                key: 'customerID',
                                                type: 'select',
                                                tdEdit: true
                                            },
                                            form: {
                                                // type: 'select',
                                                type: 'selectByPaging',
                                                field: 'customerID',
                                                optionConfig: {
                                                    label: 'customerName',
                                                    value: 'zxCrCustomerNewId'
                                                },
                                                fetchConfig: {
                                                    apiName: "getZxCrCustomerNewList",
                                                    otherParams: async () => {
                                                        const vals = await this.table.btnCallbackFn.qnnForm.getValues(false);
                                                        return {
                                                            parentOrgID: vals.orgID,
                                                            limit: 999,
                                                            page: 1
                                                        }
                                                    }
                                                },
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        const itemData = thisProps;
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            // 获取编辑行的值的 标准方案
                                                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                            const newRowData = {
                                                                ...rowData
                                                            }
                                                            newRowData.customerName = itemData.itemData.customerName;
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
                                                title: '收料员',
                                                width: 120,
                                                dataIndex: 'receClerk',
                                                key: 'receClerk',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'receClerk'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '备注',
                                                width: 120,
                                                tooltip: 23,
                                                dataIndex: 'remarks',
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
                                        }
                                    ],
                                    actionBtns: [{
                                        name: "addRow",
                                        icon: "plus",
                                        type: "primary",
                                        label: "新增",
                                    },
                                    {
                                        name: "del",
                                        icon: 'delete',
                                        type: 'danger',
                                        label: "删除"
                                    }]
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
                                    field: 'addsubmit',
                                    fetchConfig: {
                                        apiName: 'addZxSkShopGoods'
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
                                        apiName: 'updateZxSkShopGoods'
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
                                confirm({
                                    content: '确定删除选中的数据吗?',
                                    onOk: () => {
                                        myFetch('batchDeleteUpdateZxSkShopGoods', obj.selectedRows).then(
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
                    ]}

                />
            </div>
        );
    }
}

export default index;