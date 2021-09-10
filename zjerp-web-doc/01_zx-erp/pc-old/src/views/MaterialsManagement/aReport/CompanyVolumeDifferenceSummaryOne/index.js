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
        let { myPublic: { domain, appInfo: { ureport } } } = this.props;
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
                                var URL = `${ureport}excel?_u=file:zxStockDifMonth.xml&url=${domain}&period=${value.period ? new Date(value.period._d).getTime() : ''}`;
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
                                label: '公司',
                                field: 'orgID',
                                type: 'select',
                                showSearch: true,
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect'
                                },
                                placeholder: '请选择'
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                label: '季度',
                                field: 'period',
                                type: 'halfYear',
                                allowClear: true,
                                placeholder: '请选择'
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'id',//???
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '物资名称',
                                dataIndex: 'resName',
                                key: 'resName',
                                width: 140,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资编码',
                                dataIndex: 'resCode',
                                key: 'resCode',
                                width: 140,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '规格型号',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 140,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '计量单位',
                                dataIndex: 'unit',
                                key: 'unit',
                                width: 140,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '类型',
                                dataIndex: 'mtType',
                                key: 'mtType',
                                width: 140,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '局定额损耗率(%)',
                                dataIndex: 'mtType',//???
                                key: 'mtType',
                                width: 140,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '本季物资实际消耗(万元)',
                                dataIndex: 'resName1',
                                key: 'resName1',
                                children: [
                                    {
                                        title: '实际消耗数量',
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
                                        title: '采购加权单价(元)',
                                        dataIndex: 'thisPrice1',
                                        key: 'thisPrice1',
                                        children: [
                                            {
                                                title: '2=3/1',
                                                dataIndex: 'thisPrice',
                                                key: 'thisPrice',
                                                width: 160,
                                            },
                                        ]
                                    },
                                    {
                                        title: '实际消耗金额(元)',
                                        dataIndex: 'thisAmt1',
                                        key: 'thisAmt1',
                                        children: [
                                            {
                                                title: '3',
                                                dataIndex: 'thisAmt',
                                                key: 'thisAmt',
                                                width: 160,
                                            },
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '本季量差核算情况',
                                dataIndex: 'designQty1',
                                key: 'designQty1',
                                children: [
                                    {
                                        title: '设计用量',
                                        dataIndex: 'designQty2',
                                        key: 'designQty2',
                                        children: [
                                            {
                                                title: '4',
                                                dataIndex: 'designQty',
                                                key: 'designQty',
                                                width: 160,
                                            },
                                        ]
                                    },
                                    {
                                        title: '设计用量金额',
                                        dataIndex: 'design1',
                                        key: 'design1',
                                        children: [
                                            {
                                                title: '5',
                                                dataIndex: 'design',
                                                key: 'design',
                                                width: 160,
                                            },
                                        ]
                                    },
                                    {
                                        title: '理论消耗量',
                                        dataIndex: 'sunHaoLv1',
                                        key: 'sunHaoLv1',
                                        children: [
                                            {
                                                title: '6',
                                                dataIndex: 'sunHaoLv',
                                                key: 'sunHaoLv',
                                                width: 160,
                                            },
                                        ]
                                    },
                                    {
                                        title: '与设计用量对比(节+超-)',
                                        dataIndex: 'deJcQty1',
                                        key: 'deJcQty1',
                                        children: [
                                            {
                                                title: '节超数量',
                                                dataIndex: 'deJcQty2',
                                                key: 'deJcQty2',
                                                children: [
                                                    {
                                                        title: '7=4-1',
                                                        dataIndex: 'deJcQty',
                                                        key: 'deJcQty',
                                                        width: 160,
                                                    },
                                                ]
                                            },
                                            {
                                                title: '节超金额(元)',
                                                dataIndex: 'deJcAmt1',
                                                key: 'deJcAmt1',
                                                children: [
                                                    {
                                                        title: '8',
                                                        dataIndex: 'deJcAmt',
                                                        key: 'deJcAmt',
                                                        width: 160,
                                                    },
                                                ]
                                            },
                                            {
                                                title: '节超率(%)',
                                                dataIndex: 'deJclv1',
                                                key: 'deJclv1',
                                                children: [
                                                    {
                                                        title: '9=8/5',
                                                        dataIndex: 'deJclv',
                                                        key: 'deJclv',
                                                        width: 160,
                                                    },
                                                ]
                                            },
                                        ]
                                    },
                                    {
                                        title: '与理论用量对比(节+超-)',
                                        dataIndex: 'llJcQty1',
                                        key: 'llJcQty1',
                                        children: [
                                            {
                                                title: '节超数量',
                                                dataIndex: 'llJcQty2',
                                                key: 'llJcQty2',
                                                children: [
                                                    {
                                                        title: '10=6-1',
                                                        dataIndex: 'llJcQty',
                                                        key: 'llJcQty',
                                                        width: 160,
                                                    },
                                                ]
                                            },
                                            {
                                                title: '节超金额',
                                                dataIndex: 'llJcAmt2',
                                                key: 'llJcAmt2',
                                                children: [
                                                    {
                                                        title: '11=10*2/10000',
                                                        dataIndex: 'llJcAmt',
                                                        key: 'llJcAmt',
                                                        width: 160,
                                                    },
                                                ]
                                            },
                                            {
                                                title: '节超率(%)',
                                                dataIndex: 'llJclv1',
                                                key: 'llJclv1',
                                                children: [
                                                    {
                                                        title: '12=11/5',
                                                        dataIndex: 'llJclv',
                                                        key: 'llJclv',
                                                        width: 160,
                                                    },
                                                ]
                                            },
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '开工累计量差核算情况',
                                dataIndex: 'designQty1',
                                key: 'designQty1',
                                children: [
                                    {
                                        title: '开累实际消耗金额',
                                        dataIndex: 'llJclv1',
                                        key: 'llJclv1',
                                        children: [
                                            {
                                                title: '13',
                                                dataIndex: 'llJclv',
                                                key: 'llJclv',
                                                width: 160,
                                            },
                                        ]
                                    },
                                    {
                                        title: '开累设计用量金额',
                                        dataIndex: 'llJclv1',
                                        key: 'llJclv1',
                                        children: [
                                            {
                                                title: '14',
                                                dataIndex: 'llJclv',
                                                key: 'llJclv',
                                                width: 160,
                                            },
                                        ]
                                    },
                                    {
                                        title: '与设计用量对比(节+超-)',
                                        dataIndex: 'llJclv1',
                                        key: 'llJclv1',
                                        children: [
                                            {
                                                title: '节超数量',
                                                dataIndex: 'llJclv',
                                                key: 'llJclv',
                                                children: [
                                                    {
                                                        title: '15',
                                                        dataIndex: 'llJclv',
                                                        key: 'llJclv',
                                                        width: 160
                                                    }
                                                ]
                                            },
                                            {
                                                title: '节超金额',
                                                dataIndex: 'llJclv',
                                                key: 'llJclv',
                                                children: [
                                                    {
                                                        title: '16',
                                                        dataIndex: 'llJclv',
                                                        key: 'llJclv',
                                                        width: 160
                                                    }
                                                ]
                                            },
                                            {
                                                title: '节超率',
                                                dataIndex: 'llJclv',
                                                key: 'llJclv',
                                                children: [
                                                    {
                                                        title: '17=16/14',
                                                        dataIndex: 'llJclv',
                                                        key: 'llJclv',
                                                        width: 160
                                                    }
                                                ]
                                            },
                                        ]
                                    },
                                    {
                                        title: '与理论用量对比(节+超-)',
                                        dataIndex: 'llJclv1',
                                        key: 'llJclv1',
                                        children: [
                                            {
                                                title: '节超数量',
                                                dataIndex: 'llJclv',
                                                key: 'llJclv',
                                                children: [
                                                    {
                                                        title: '18',
                                                        dataIndex: 'llJclv',
                                                        key: 'llJclv',
                                                        width: 160
                                                    }
                                                ]
                                            },
                                            {
                                                title: '节超金额',
                                                dataIndex: 'llJclv',
                                                key: 'llJclv',
                                                children: [
                                                    {
                                                        title: '19',
                                                        dataIndex: 'llJclv',
                                                        key: 'llJclv',
                                                        width: 160
                                                    }
                                                ]
                                            },
                                            {
                                                title: '节超率',
                                                dataIndex: 'llJclv',
                                                key: 'llJclv',
                                                children: [
                                                    {
                                                        title: '20=19/14',
                                                        dataIndex: 'llJclv',
                                                        key: 'llJclv',
                                                        width: 160
                                                    }
                                                ]
                                            },
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