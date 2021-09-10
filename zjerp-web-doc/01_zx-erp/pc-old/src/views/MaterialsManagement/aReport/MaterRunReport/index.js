import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import { Modal } from "antd";
import { ExportOutlined } from '@ant-design/icons';
const config = {
    antd: {
        rowKey: 'id',
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight * 0.5
        }
    },
    drawerConfig: {
        width: '1200px'
    },
    // paginationConfig: {
    //     position: 'bottom'
    // },
    paginationConfig: false,
    firstRowIsSearch: false,
    isShowRowSelect: false
};
const confirm = Modal.confirm;
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }
    render() {
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        let { myPublic: { domain, appInfo: { ureport } }, location: { pathname } } = this.props;
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
                        apiName: 'exportZxSkReceivingDynamicListByPage',
                        otherParams: {
                            // projectId: departmentId
                        }
                    }}
                    topSearchExtendBtns={[
                        {
                            field: "btn1",
                            label: "导出",
                            onClick: (val) => {
                                if (this.table?.btnCallbackFn?.searchForm?.form) {
                                    let value = this.table.btnCallbackFn.searchForm.form.getFieldsValue().searchParams;
                                    let URL = `${ureport}excel?_u=file:zxSkReceivingDynamic.xml&_n=物资动态账&url=${domain}&orgID=${value.orgID ? value.orgID : ''}&resID=${value.resID ? value.resID : ''}&resTypeID=${value.resTypeID ? value.resTypeID : ''}&beginDate=${value.beginDate ? new Date(value.beginDate._d).getTime() : ''}&endDate=${value.endDate ? new Date(value.endDate._d).getTime() : ''}`;
                                    confirm({
                                        content: '确定导出报表吗?',
                                        onOk: () => {
                                            window.open(URL);
                                        }
                                    });
                                }
                            },
                            type: "primary",
                            icon: <ExportOutlined />
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '项目名称',
                                field: 'orgID',
                                type: 'select',
                                showSearch: true,
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
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '材料类型',
                                field: 'resTypeID',
                                type: 'select',
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id'
                                },
                                fetchConfig: {
                                    apiName: 'getZxSkResCategoryMaterialsListResource',
                                    otherParams: {
                                        "parentOrgID": "1"
                                    }
                                },
                                placeholder: '请选择'
                            }
                        },
                        // {
                        //     isInTable: false,
                        //     isInSearch: true,
                        //     form: {
                        //         label: '物资编码',
                        //         type: 'select',
                        //         // type: 'selectByQnnTable',
                        //         field: 'resID',
                        //         // dropdownMatchSelectWidth: 900,
                        //         dependenciesReRender: true,//多个依赖-配置
                        //         dependencies: ['resTypeID'],
                        //         optionConfig: {
                        //             label: 'resName',
                        //             value: 'id'
                        //         },
                        //         fetchConfig: {
                        //             apiName: 'getZxSkResCategoryMaterialsAllResource',
                        //             otherParams: (val) => {
                        //                 let resTypeID = '0002';
                        //                 if (val.btnCallbackFn?.form) {
                        //                     let aa = val.btnCallbackFn.form.getFieldsValue().searchParams;
                        //                     if (aa.resTypeID) {
                        //                         resTypeID = aa.resTypeID;
                        //                     }
                        //                 } else {

                        //                 }
                        //                 return {
                        //                     id: resTypeID
                        //                 }
                        //             }
                        //         },
                        //         // qnnTableConfig: {
                        //         //     antd: {
                        //         //         rowKey: "id"
                        //         //     },
                        //         //     firstRowIsSearch: false,
                        //         //     fetchConfig: {
                        //         //         apiName: "getZxSkResCategoryMaterialsAllResource",
                        //         //         otherParams: (val) => {
                        //         //             console.log(val);
                        //         //             console.log(val);
                        //         //             let resTypeID = '0002';
                        //         //             if (val.btnCallbackFn?.searchForm?.form) {
                        //         //                 let aa = val.btnCallbackFn.searchForm.form.getFieldsValue();
                        //         //                 if (aa.resTypeID) {
                        //         //                     resTypeID = aa.resTypeID;
                        //         //                 }
                        //         //             } else {
    
                        //         //             }
                        //         //             return {
                        //         //                 id: resTypeID
                        //         //             }
                        //         //         }
                        //         //     },
                        //         //     searchBtnsStyle: "inline",
                        //         //     formConfig: [
                        //         //         {
                        //         //             isInForm: false,
                        //         //             isInTable: false,
                        //         //             form: {
                        //         //                 field: 'id',
                        //         //                 type: "string"
                        //         //             }
                        //         //         },
                        //         //         {
                        //         //             isInForm: false,
                        //         //             isInSearch: true,
                        //         //             table: {
                        //         //                 dataIndex: "resCode",
                        //         //                 title: "编号",
                        //         //             },
                        //         //             form: {
                        //         //                 type: "string"
                        //         //             }
                        //         //         },
                        //         //         {
                        //         //             isInForm: false,
                        //         //             isInSearch: true,
                        //         //             table: {
                        //         //                 dataIndex: "resName",
                        //         //                 title: "名称",
                        //         //             },
                        //         //             form: {
                        //         //                 type: "string"
                        //         //             }
                        //         //         },
                        //         //         {
                        //         //             isInForm: false,
                        //         //             isInSearch: true,
                        //         //             table: {
                        //         //                 dataIndex: "spec",
                        //         //                 title: "规格型号"
                        //         //             },
                        //         //             form: {
                        //         //                 type: "string"
                        //         //             }
                        //         //         },
                        //         //         {
                        //         //             isInForm: false,
                        //         //             isInSearch: true,
                        //         //             table: {
                        //         //                 dataIndex: "unit",
                        //         //                 title: "单位"
                        //         //             },
                        //         //             form: {
                        //         //                 type: "string"
                        //         //             }
                        //         //         },
                        //         //         {
                        //         //             isInForm: false,
                        //         //             // isInSearch: true,
                        //         //             table: {
                        //         //                 dataIndex: "refpriceType",
                        //         //                 title: "计价方式"
                        //         //             },
                        //         //             form: {
                        //         //                 type: "string"
                        //         //             }
                        //         //         }
                        //         //     ]
                        //         // },
                        //         placeholder: '请选择'
                        //     }
                        // },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'date',
                                label: '开始时间',
                                field: 'beginDate'
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'date',
                                label: '结束时间',
                                field: 'endDate'
                            }
                        },
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
                                title: '日期',
                                dataIndex: 'busDate',
                                key: 'busDate',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资编码',
                                dataIndex: 'resCode',
                                width: 200,
                                tooltip: 23,
                                key: 'resCode'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资名称',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'resName',
                                key: 'resName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '规格',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '计量单位',
                                width: 120,
                                dataIndex: 'resUnit',
                                key: 'resUnit'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '凭证号',
                                width: 180,
                                tooltip: 23,
                                dataIndex: 'voucherNo',
                                key: 'voucherNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '摘要',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'outOrgName',
                                key: 'outOrgName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '购入单价',
                                width: 120,
                                dataIndex: 'inPrice',
                                key: 'inPrice'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '收入',
                                dataIndex: 'spec',
                                key: 'spec',
                                children: [
                                    {
                                        title: '自行采购',
                                        dataIndex: 'serQty',
                                        key: 'serQty',
                                        width: 130,
                                    },
                                    {
                                        title: '甲供',
                                        dataIndex: 'orsQty',
                                        key: 'orsQty',
                                        width: 130,
                                    },
                                    {
                                        title: '其他',
                                        dataIndex: 'otrQty',
                                        key: 'otrQty',
                                        width: 130,
                                    },
                                    {
                                        title: '预收',
                                        dataIndex: 'obuQty',
                                        key: 'obuQty',
                                        width: 130,
                                    },
                                    {
                                        title: '甲控',
                                        dataIndex: 'ocsQty',
                                        key: 'ocsQty',
                                        width: 130,
                                    },
                                    {
                                        title: '合计',
                                        dataIndex: 'unit',
                                        key: 'unit',
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'allInQty',
                                                key: 'allInQty',
                                                width: 120,
                                            },
                                            {
                                                title: '金额(元)',
                                                dataIndex: 'allInAmt',
                                                key: 'allInAmt',
                                                width: 120,
                                            }
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '发出',
                                dataIndex: 'spec',
                                key: 'spec',
                                children: [
                                    {
                                        title: '工程耗用',
                                        dataIndex: 'oswQty',
                                        key: 'oswQty',
                                        width: 130,
                                    },
                                    {
                                        title: '调拨',
                                        dataIndex: 'unit',
                                        key: 'unit',
                                        children: [
                                            {
                                                title: '内调',
                                                dataIndex: 'otsQty',
                                                key: 'otsQty',
                                                width: 120,
                                            },
                                            {
                                                title: '外调',
                                                dataIndex: 'otkQty',
                                                key: 'otkQty',
                                                width: 120,
                                            }
                                        ]
                                    },
                                    {
                                        title: '合计',
                                        dataIndex: 'unit1',
                                        key: 'unit1',
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'allOutQty',
                                                key: 'allOutQty',
                                                width: 120,
                                            },
                                            {
                                                title: '平均单价',
                                                dataIndex: 'oswPrice',
                                                key: 'oswPrice',
                                                width: 120,
                                            },
                                            {
                                                title: '金额(元)',
                                                dataIndex: 'allOutAmt',
                                                key: 'allOutAmt',
                                                width: 120,
                                            }
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '盈(+)亏(-)',
                                dataIndex: 'stockQty2',
                                key: 'stockQty2',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'vinoutQty',
                                        key: 'vinoutQty',
                                        width: 120,
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'vinoutAmt',
                                        key: 'vinoutAmt',
                                        width: 120,
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '结存',
                                dataIndex: 'stockQty',
                                key: 'stockQty',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'lastQty',
                                        key: 'lastQty',
                                        width: 120,
                                    },
                                    {
                                        title: '平均单价',
                                        dataIndex: 'lastPrice',
                                        key: 'lastPrice',
                                        width: 120,
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'lastAmt',
                                        key: 'lastAmt',
                                        width: 120,
                                    }
                                ]
                            },
                            isInForm: false
                        }
                    ]}
                    actionBtns={[]}
                />
            </div>
        );
    }
}

export default index;