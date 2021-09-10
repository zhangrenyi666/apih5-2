import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import { Modal } from "antd";
import { ExportOutlined } from '@ant-design/icons';
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
                        apiName: 'ureportZxSkStockTransferRptNewIdle',
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
                                var URL = `${ureport}excel?_u=file:ReceivingDynamic.xml&url=${domain}&departmentId=${value.departmentId ? value.departmentId:null}&materialType=${value.materialType ? value.materialType :null}&resCode=${value.resCode ? value.resCode :null}&inDate=${new Date(value.inDate ? value.inDate._d:null).getTime()}&outDate=${new Date(value.outDate ? value.outDate._d :null).getTime()}`;
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
                            form:{
                                label: '机构名称',
                                field: 'departmentId',
                                type: 'select',
                                required:true,
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect'
                                },
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                label: '季度名称',
                                required:true,
                                field: 'ddd',
                                type: 'halfYear',
                                placeholder: '请选择'
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
                                title: '序号',
                                dataIndex: 'index',
                                key: 'index',
                                width: 80,
                                align:'center',
                                render: (data, rowData, index) => {
                                    return index + 1;
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '类别',
                                dataIndex: 'num',
                                key: 'num'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资名称',
                                dataIndex: 'resName',
                                width: 200,
                                key: 'resName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '特征字符',
                                width: 200,
                                dataIndex: 'resType',
                                key: 'resType'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '计量单位',
                                dataIndex: 'danwei',
                                key: 'danwei',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'spec',
                                        key: 'spec',
                                        width: 130,
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'unit',
                                        key: 'unit',
                                        width: 130,
                                    } 
                                ]
                            }
                        },
                        {
                            table: {
                                title: '本季期初库存',
                                dataIndex: 'danwei',
                                key: 'danwei',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'qcinQty',
                                        key: 'qcinQty',
                                        width: 130,
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'qcinAmt',
                                        key: 'qcinAmt',
                                        width: 130,
                                    } 
                                ]
                            }
                        },
                        {
                            table: {
                                title: '本季收入',
                                dataIndex: 'danwei',
                                key: 'danwei',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'c4Qty',
                                        key: 'c4Qty',
                                        width: 130,
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'c4Amt',
                                        key: 'c4Amt',
                                        width: 130,
                                    },
                                    {
                                        title: '其中',
                                        dataIndex: 'qizhong',
                                        key: 'qizhong',
                                        children: [
                                            {
                                                title: '甲供物资',
                                                dataIndex: 'jiagong',
                                                key: 'jiagong',
                                                width: 120,
                                                children: [
                                                    {
                                                    title: '数量',
                                                    dataIndex: 'c1Qty',
                                                    key: 'c1Qty',
                                                    width: 120
                                                    },
                                                    {
                                                        title: '金额',
                                                        dataIndex: 'c1Amt',
                                                        key: 'c1Amt',
                                                        width: 120, 
                                                    }
                                            ]
                                            },
                                            {
                                                title: '境外工程的国内采购',
                                                dataIndex: 'rithinQty',
                                                key: 'rithinQty',
                                                width: 120,
                                                children: [
                                                    {
                                                        title: '数量',
                                                        dataIndex: 's1',
                                                        key: 's1',
                                                        width: 120
                                                    },
                                                    {
                                                        title: '金额',
                                                        dataIndex: 's2',
                                                        key: 's2',
                                                        width: 120
                                                    }
                                                ]
                                            }
                                        ]
                                    },
                                    {
                                        title: '本季发出',
                                        dataIndex: 'unit1',
                                        key: 'unit1',
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'f3outQty',
                                                key: 'f3outQty',
                                                width: 120,
                                            },
                                            {
                                                title: '金额',
                                                dataIndex: 'f3outAmt',
                                                key: 'f3outAmt',
                                                width: 120,
                                            },
                                            {
                                                title: '占本期营业额的比重（%）',
                                                dataIndex: 's3',
                                                key: 's3',
                                                width: 120,
                                            }
                                        ]
                                    }
                                ]
                            }
                        },

                        {
                            table: {
                                title: '本季期末库存',
                                dataIndex: 'stockQty2',
                                key: 'stockQty2',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'curQty',
                                        key: 'curQty',
                                        width: 120,
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'curAmt',
                                        key: 'curAmt',
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