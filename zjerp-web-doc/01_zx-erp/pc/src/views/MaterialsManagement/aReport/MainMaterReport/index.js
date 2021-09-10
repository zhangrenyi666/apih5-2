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
    paginationConfig: false,
    firstRowIsSearch: false,
    isShowRowSelect: false
};
const confirm = Modal.confirm;
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : ''
        }
    }
    render() {
        let { myPublic: { appInfo: { ureport } } } = this.props;
        const { ext1, departmentId, companyId, projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { lockProjectId } = this.state;
        let jurisdiction = departmentId;
        if (lockProjectId !== 'all' && lockProjectId !== '') {
            jurisdiction = lockProjectId;
        } else {
            if (ext1 === '1' || ext1 === '2') {
                jurisdiction = companyId;
            } else if (ext1 === '3' || ext1 === '4') {
                jurisdiction = projectId;
            } else { }
        }
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
                        apiName: 'ureportZxSkMosResMovStatRep',
                        otherParams: {
                            projectId: jurisdiction,
                            type: "1"
                        }
                    }}
                    topSearchExtendBtns={[
                        {
                            field: "btn1",
                            label: "导出",
                            onClick: (val) => {
                                if (val) {
                                    let value = val.searchData;
                                    value.then((data) => {
                                        let value = data;
                                        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                        var URL = `${ureport}excel?_u=minio:zxSkMosResMovStatRep.xml&_n=主要物资收、发、存统计报表&type=1&access_token=${access_token}&orgID=${value.orgID ? value.orgID : ''}&monthly=${value.monthly ? value.monthly : ''}`;
                                        confirm({
                                            content: '确定导出报表吗?',
                                            onOk: () => {
                                                window.open(URL);
                                            }
                                        });
                                    })
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
                                label: '单位',
                                field: 'orgID',
                                type: 'select',
                                searchRequied: true,
                                required: true,
                                showSearch: true,
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams: {
                                        departmentId: jurisdiction
                                    }
                                },
                                placeholder: '请选择'
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'month',
                                label: '月度',
                                field: 'monthly'
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
                                dataIndex: 'orderNo',
                                key: 'orderNo',
                                width: 80,
                                align: 'center',
                                fixed: 'left'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资名称',
                                width: 260,
                                dataIndex: 'resName',
                                key: 'resName',
                                fixed: 'left',
                                render: (data, rowData, index) => {
                                    if (index === 0 || index === 1) {
                                        return data
                                    } else {
                                        if (data && data.substr(0, 1) === '(') {
                                            return <div style={{ marginLeft: '31px' }}>{data}</div>
                                        } else {
                                            return data
                                        }

                                    }

                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '统计字母或代码',
                                dataIndex: 'serialNumber',
                                key: 'serialNumber',
                                width: 180,
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
                                                dataIndex: 'inQty',
                                                key: 'inQty',
                                                width: 130,
                                            },
                                            {
                                                title: '不含税单价',
                                                dataIndex: 'inPriceNoTax',
                                                key: 'inPriceNoTax',
                                                width: 130,
                                            },
                                            {
                                                title: '含税单价',
                                                dataIndex: 'inPrice',
                                                key: 'inPrice',
                                                width: 130,
                                            },
                                            {
                                                title: '不含税金额',
                                                dataIndex: 'inAmtNoTax',
                                                key: 'inAmtNoTax',
                                                width: 130,
                                            },
                                            {
                                                title: '含税金额',
                                                dataIndex: 'inAmt',
                                                key: 'inAmt',
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
                                        title: '其它',
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
                                dataIndex: 'endStockQty',
                                key: 'endStockQty'
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