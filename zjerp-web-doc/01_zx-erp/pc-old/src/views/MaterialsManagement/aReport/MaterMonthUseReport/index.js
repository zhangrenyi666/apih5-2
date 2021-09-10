import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import { Modal } from "antd";
import moment from 'moment';
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
                        apiName: 'exportZxSkResMoveMonthMPListByPage',
                        otherParams: {
                            projectId: departmentId
                        }
                    }}
                    topSearchExtendBtns={[
                        {
                            field: "btn1",
                            label: "导出",
                            onClick: (val) => {
                                let value = this.table.btnCallbackFn.searchForm.form.getFieldsValue().searchParams;
                                let URL = `${ureport}excel?_u=file:zxSkResMoveMonthMP.xml&_n=物资收发存月报表&url=${domain}&orgID=${value.orgID ? value.orgID : ''}&resTypeID=${value.resTypeID ? value.resTypeID : ''}&period=${value.period ? new Date(value.period).getTime() : ''}`;
                                confirm({
                                    content: '确定导出报表吗?',
                                    onOk: () => {
                                        window.open(URL);
                                    }
                                });
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
                                // initialValue: () => {
                                //     return departmentId
                                // },
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
                                showSearch: true,
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
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'month',
                                label: '期次',
                                field: 'period',
                                // initialValue: () => {
                                //     return moment(new Date())
                                // },
                            },
                        },
                        // {
                        //     isInTable: false,
                        //     isInSearch: true,
                        //     form: {
                        //         type: 'select',
                        //         label: '是否完工',
                        //         field: 'isFinish',
                        //         // initialValue: () => {
                        //         //     return '0'
                        //         // },
                        //         optionConfig: {
                        //             label: 'label',
                        //             value: 'value',
                        //         },
                        //         optionData: [
                        //             {
                        //                 label: "全部",
                        //                 value: "0"
                        //             },
                        //             {
                        //                 label: "是",
                        //                 value: "1"
                        //             },
                        //             {
                        //                 label: "否",
                        //                 value: "2"
                        //             }
                        //         ],
                        //     }
                        // },
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSkResMoveMonthMPId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '物资编码',
                                dataIndex: 'resCode',
                                key: 'resCode'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资名称',
                                dataIndex: 'resName',
                                key: 'resName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资规格',
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
                                dataIndex: 'unit',
                                key: 'unit'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '上月结存',
                                dataIndex: 'spec',
                                key: 'spec',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'stockQty',
                                        key: 'stockQty',
                                        width: 130,
                                    },
                                    {
                                        title: '平均单价(元)',
                                        dataIndex: 'stockPrice',
                                        key: 'stockPrice',
                                        width: 130,
                                    },
                                    {
                                        title: '金额(元)',
                                        dataIndex: 'stockAmt',
                                        key: 'stockAmt',
                                        width: 130,
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '本月收入',
                                dataIndex: 'resName',
                                key: 'resName',
                                children: [
                                    {
                                        title: '甲供',
                                        width: 120,
                                        dataIndex: 'orsQty',
                                        key: 'orsQty',
                                    },
                                    {
                                        title: '其他',
                                        width: 120,
                                        dataIndex: 'otrQty',
                                        key: 'otrQty',
                                    },
                                    {
                                        title: '自购',
                                        width: 120,
                                        dataIndex: 'serQty',
                                        key: 'serQty',
                                    },
                                    {
                                        title: '预收',
                                        width: 120,
                                        dataIndex: 'obuQty',
                                        key: 'obuQty',
                                    },
                                    {
                                        title: '甲控',
                                        width: 120,
                                        dataIndex: 'ocsQty',
                                        key: 'ocsQty',
                                    },
                                    {
                                        title: '合计',
                                        dataIndex: 'resName',
                                        key: 'resName',
                                        children: [
                                            {
                                                title: '数量',
                                                width: 120,
                                                dataIndex: 'inQty',
                                                key: 'inQty',
                                            },
                                            {
                                                title: '平均单价(元)',
                                                width: 120,
                                                dataIndex: 'inPrice',
                                                key: 'inPrice',
                                            },
                                            {
                                                title: '金额(元)',
                                                width: 120,
                                                dataIndex: 'inAmt',
                                                key: 'inAmt',
                                            }
                                        ]
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '工程耗用',
                                dataIndex: 'resName',
                                key: 'resName',
                                children: [
                                    {
                                        title: '数量',
                                        width: 120,
                                        dataIndex: 'oswQty',
                                        key: 'oswQty',
                                    },
                                    {
                                        title: '平均单价(元)',
                                        width: 120,
                                        dataIndex: 'oswPrice',
                                        key: 'oswPrice',
                                    },
                                    {
                                        title: '金额',
                                        width: 120,
                                        dataIndex: 'oswAmt',
                                        key: 'oswAmt',
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '调拨',
                                dataIndex: 'resName',
                                key: 'resName',
                                children: [
                                    {
                                        title: '加工改制',
                                        width: 120,
                                        dataIndex: 'trsQty',
                                        key: 'trsQty',
                                    },
                                    {
                                        title: '调拨',
                                        width: 120,
                                        dataIndex: 'otkQty',
                                        key: 'otkQty',
                                    },
                                    {
                                        title: '合计',
                                        dataIndex: 'resName',
                                        key: 'resName',
                                        children: [
                                            {
                                                title: '数量',
                                                width: 120,
                                                dataIndex: 'otkQtys',
                                                key: 'otkQtys',
                                            },
                                            {
                                                title: '平均单价(元)',
                                                width: 120,
                                                dataIndex: 'otkPrice',
                                                key: 'otkPrice',
                                            },
                                            {
                                                title: '金额',
                                                width: 120,
                                                dataIndex: 'outAmt',
                                                key: 'outAmt',
                                            }
                                        ]
                                    },

                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '盈(+)亏(-)',
                                dataIndex: 'resName',
                                key: 'resName',
                                children: [
                                    {
                                        title: '数量',
                                        width: 120,
                                        dataIndex: 'vinQty',
                                        key: 'vinQty',
                                    },
                                    {
                                        title: '金额(元)',
                                        width: 120,
                                        dataIndex: 'vinAmt',
                                        key: 'vinAmt',
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '本月结存',
                                dataIndex: 'resName',
                                key: 'resName',
                                children: [
                                    {
                                        title: '数量',
                                        width: 120,
                                        dataIndex: 'thisQtys',
                                        key: 'thisQtys',
                                    },
                                    {
                                        title: '平均单价(元)',
                                        width: 120,
                                        dataIndex: 'thisProce',
                                        key: 'thisProce',
                                    },
                                    {
                                        title: '金额(元)',
                                        width: 120,
                                        dataIndex: 'thisAmts',
                                        key: 'thisAmts',
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