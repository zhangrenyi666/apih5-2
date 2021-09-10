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
                        apiName: 'getUreportZxSkTurnoverInList',
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
                                var URL = `${ureport}excel?_u=file:zxSkTurnoverInList.xml&url=${domain}&orgID=${value.orgID ? value.orgID : null}&beginDate=${new Date(value.beginDate ? value.beginDate._d : null).getTime()}&endDate=${new Date(value.endDate ? value.endDate._d : null).getTime()}`;
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
                                label: '机构名称',
                                field: 'orgID',
                                type: 'select',
                                showSearch: true,
                                optionConfig: {
                                    label: 'orgName',
                                    value: 'iecmOrgID'
                                },
                                fetchConfig: {
                                    apiName: 'getZxEqIecmOrgList'
                                },
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'date',
                                label: '开始时间',
                                field: 'beginDate'
                            }
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
                                title: '项目名称',
                                dataIndex: 'orgName',
                                width: 200,
                                tooltip: 23,
                                key: 'orgName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '供应商',
                                dataIndex: 'outOrgName',
                                width: 200,
                                tooltip: 23,
                                key: 'outOrgName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '业务日期',
                                dataIndex: 'busDate',
                                key: 'busDate',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '单据编号',
                                dataIndex: 'billNo',
                                width: 200,
                                tooltip: 23,
                                key: 'billNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资编码',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'resCode',
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
                                title: '规格型号',
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
                                title: '数量',
                                dataIndex: 'inQty',
                                key: 'inQty'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '含税采购单价(元)',
                                width:140,
                                dataIndex: 'inPrice',
                                key: 'inPrice'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '含税采购金额(万元)',
                                width: 150,
                                dataIndex: 'inAmtTotal',
                                key: 'inAmtTotal'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '不含税采购单价(元)',
                                width: 170,
                                dataIndex: 'inPriceNoTax',
                                key: 'inPriceNoTax'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '不含税采购金额(万元)',
                                width: 170,
                                dataIndex: 'inAmtNoTax',
                                key: 'inAmtNoTax'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '市场来源',
                                width: 120,
                                dataIndex: 'materialSource',
                                key: 'materialSource'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '是否预收',
                                width: 120,
                                dataIndex: 'precollecte',
                                key: 'precollecte'
                            },
                            isInForm: false
                        },
                    ]}
                    actionBtns={[]}
                />
            </div>
        );
    }
}

export default index;