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
                        apiName: 'filtrateZxSkMosResMovStatRep',
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
                                label: '单位',
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
                                type: 'halfYear',
                                label: '月度',
                                field: 'resCode'
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                label: '是否完工项目',
                                field: 'resourceId',
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    { label: "全部", value: "0" },
                                    { label: "否", value: "1" },
                                    { label: "是", value: "2" }
                                ],
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
                                fixed: 'left',
                                render: (data, rowData, index) => {
                                    return index + 1;
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资名称',
                                width:260,
                                dataIndex: 'resName',
                                key: 'resName',
                                fixed: 'left',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '统计字母或代码',
                                dataIndex: 'code',
                                key: 'code',
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
                                title: '年初库存量',
                                width: 120,
                                dataIndex: 'stockQty',
                                key: 'stockQty'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '年初至本季累计收入量',
                                dataIndex: 'spec1',
                                key: 'spec1',
                                children: [
                                    {
                                        title: '合计',
                                        dataIndex: 'unit3',
                                        key: 'unit3',
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'qty',
                                                key: 'qty',
                                                width: 130,
                                            },
                                            {
                                                title: '不含税单价',
                                                dataIndex: 'priceNoTax',
                                                key: 'priceNoTax',
                                                width: 130,
                                            },
                                            {
                                                title: '含税单价',
                                                dataIndex: 'price',
                                                key: 'price',
                                                width: 130,
                                            },
                                            {
                                                title: '不含税金额',
                                                dataIndex: 'amtNoTax',
                                                key: 'amtNoTax',
                                                width: 130,
                                            },
                                            {
                                                title: '含税金额',
                                                dataIndex: 'amt',
                                                key: 'amt',
                                                width: 130,
                                            }
                                        ]
                                    },
                                    {
                                        title: '自行采购',
                                        dataIndex: 'serQty',
                                        key: 'serQty',
                                        width: 130,
                                    },
                                    {
                                        title: '甲购',
                                        dataIndex: 'orsQty',
                                        key: 'orsQty',
                                        width: 130,
                                    },
                                    {
                                        title: '预收',
                                        dataIndex: 'obuQty',
                                        key: 'obuQty',
                                        width: 130,
                                    },
                                    {
                                        title: '其他',
                                        dataIndex: 'otrQty',
                                        key: 'otrQty',
                                        width: 130,
                                    },
                                    {
                                        title: '甲控',
                                        dataIndex: 'ocsQty',
                                        key: 'ocsQty',
                                        width: 130,
                                    },
                                ]
                            }
                        },
                        {
                            table: {
                                title: '累计消耗量',
                                dataIndex: 'oswQty',
                                key: 'oswQty'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '累计拔出量',
                                dataIndex: 'otkQty',
                                key: 'otkQty'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '盈(+)<br/>亏(-)',
                                dataIndex: 'vinQty',
                                key: 'vinQty'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '期末库存量',
                                dataIndex: 'endQty',
                                key: 'endQty'
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