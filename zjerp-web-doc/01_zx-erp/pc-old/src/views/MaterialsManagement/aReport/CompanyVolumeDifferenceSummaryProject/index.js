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
                        apiName: 'getStockDifMonthFormList',
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
                                var URL = '';
                                if (pathname === '/zjerp/CompanyVolumeDifferenceSummarytouzi') {//投资事业部
                                    URL = `${ureport}excel?_u=file:zxStockDifMonth.xml&url=${domain}&period=${value.period ? new Date(value.period._d).getTime() : ''}`;
                                } else {
                                    URL = `${ureport}excel?_u=file:zxStockDifMonth.xml&url=${domain}&period=${value.period ? new Date(value.period._d).getTime() : ''}`;
                                }
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
                                label: '公司',
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
                                        departmentId:departmentId
                                    }
                                },
                                placeholder: '请选择'
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '季度',
                                field: 'period',
                                type: 'halfYear',
                                allowClear: true,
                                placeholder: '请选择'
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: () => {
                                if (pathname === '/zjerp/CompanyVolumeDifferenceSummarytouzi') {
                                    return true
                                } else {
                                    return false
                                }
                            },
                            form: {
                                label: '项目所属事业部',
                                field: 'period1',
                                type: 'select',
                                optionConfig: {
                                    label: "label",
                                    value: "value",
                                },
                                optionData: [
                                    { label: "公路市政事业部", value: "0" },
                                    { label: "铁路轨道事业部", value: "1" },
                                    { label: "城市房建事业部", value: "2" }
                                ],
                                allowClear: true,
                                placeholder: '请选择',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    }
                                }
                            },
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
                                align: 'center',
                                render: (val, rowData, index) => {
                                    return <span>{index + 1}</span>
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'resCode',
                                key: 'resCode',
                                width: 140,
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '实际消耗金额(万元)',
                                dataIndex: 'resName1',
                                key: 'resName1',
                                children: [
                                    {
                                        title: '本季',
                                        dataIndex: 'resName2',
                                        key: 'resName2',
                                        children: [
                                            {
                                                title: '1',
                                                dataIndex: 'thisQty',
                                                key: 'thisQty',
                                                width: 160,
                                            },
                                        ]
                                    },
                                    {
                                        title: '自开工累计',
                                        dataIndex: 'thisPrice1',
                                        key: 'thisPrice1',
                                        children: [
                                            {
                                                title: '2',
                                                dataIndex: 'thisPrice',
                                                key: 'thisPrice',
                                                width: 160,
                                            },
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '设计用量金额(万元)',//???
                                dataIndex: 'resName1',
                                key: 'resName1',
                                children: [
                                    {
                                        title: '本季',
                                        dataIndex: 'resName2',
                                        key: 'resName2',
                                        children: [
                                            {
                                                title: '3',
                                                dataIndex: 'thisQty',
                                                key: 'thisQty',
                                                width: 160,
                                            },
                                        ]
                                    },
                                    {
                                        title: '自开工累计',
                                        dataIndex: 'thisPrice1',
                                        key: 'thisPrice1',
                                        children: [
                                            {
                                                title: '4',
                                                dataIndex: 'thisPrice',
                                                key: 'thisPrice',
                                                width: 160,
                                            },
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '理论消耗金额(万元)',//???
                                dataIndex: 'resName1',
                                key: 'resName1',
                                children: [
                                    {
                                        title: '本季',
                                        dataIndex: 'resName2',
                                        key: 'resName2',
                                        children: [
                                            {
                                                title: '5',
                                                dataIndex: 'thisQty',
                                                key: 'thisQty',
                                                width: 160,
                                            },
                                        ]
                                    },
                                    {
                                        title: '自开工累计',
                                        dataIndex: 'thisPrice1',
                                        key: 'thisPrice1',
                                        children: [
                                            {
                                                title: '6',
                                                dataIndex: 'thisPrice',
                                                key: 'thisPrice',
                                                width: 160,
                                            },
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '与设计用量金额对比(节+超-)',
                                dataIndex: 'designQty1',
                                key: 'designQty1',
                                children: [
                                    {
                                        title: '本季节超金额(万元)',
                                        dataIndex: 'designQty2',
                                        key: 'designQty2',
                                        children: [
                                            {
                                                title: '7=3-1',
                                                dataIndex: 'designQty',
                                                key: 'designQty',
                                                width: 160,
                                            },
                                        ]
                                    },
                                    {
                                        title: '本季节超率(%)',
                                        dataIndex: 'design1',
                                        key: 'design1',
                                        children: [
                                            {
                                                title: '8=7/3',
                                                dataIndex: 'design',
                                                key: 'design',
                                                width: 160,
                                            },
                                        ]
                                    },
                                    {
                                        title: '开累节超金额(万元)',
                                        dataIndex: 'sunHaoLv1',
                                        key: 'sunHaoLv1',
                                        children: [
                                            {
                                                title: '9=4-2',
                                                dataIndex: 'sunHaoLv',
                                                key: 'sunHaoLv',
                                                width: 160,
                                            },
                                        ]
                                    },
                                    {
                                        title: '开累节超率(%)',
                                        dataIndex: 'deJcQty1',
                                        key: 'deJcQty1',
                                        children: [
                                            {
                                                title: '10=9/4',
                                                dataIndex: 'deJcAmt',
                                                key: 'deJcAmt',
                                                width: 160,
                                            }
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '与理论消耗金额对比(节+超-)',
                                dataIndex: 'designQty1',
                                key: 'designQty1',
                                children: [
                                    {
                                        title: '本季节超金额(万元)',
                                        dataIndex: 'designQty2',
                                        key: 'designQty2',
                                        children: [
                                            {
                                                title: '11=5-1',
                                                dataIndex: 'designQty',
                                                key: 'designQty',
                                                width: 160,
                                            },
                                        ]
                                    },
                                    {
                                        title: '本季节超率(%)',
                                        dataIndex: 'design1',
                                        key: 'design1',
                                        children: [
                                            {
                                                title: '12=11/3',
                                                dataIndex: 'design',
                                                key: 'design',
                                                width: 160,
                                            },
                                        ]
                                    },
                                    {
                                        title: '开累节超金额(万元)',
                                        dataIndex: 'sunHaoLv1',
                                        key: 'sunHaoLv1',
                                        children: [
                                            {
                                                title: '13=6-2',
                                                dataIndex: 'sunHaoLv',
                                                key: 'sunHaoLv',
                                                width: 160,
                                            },
                                        ]
                                    },
                                    {
                                        title: '开累节超率(%)',
                                        dataIndex: 'deJcQty1',
                                        key: 'deJcQty1',
                                        children: [
                                            {
                                                title: '14=13/4',
                                                dataIndex: 'deJcAmt',
                                                key: 'deJcAmt',
                                                width: 160,
                                            }
                                        ]
                                    }
                                ]
                            }
                        }
                    ]}
                    actionBtns={[]}
                />
            </div>
        );
    }
}

export default index;