import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import { Modal } from "antd";
import moment from 'moment';
import { ExportOutlined } from '@ant-design/icons';
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: false,
    firstRowIsSearch: false,
    isShowRowSelect: false
};
let length = 0;
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
                        apiName: 'ureportZxSkTurnoverStatTotalRpt',
                        otherParams: {
                            projectId: jurisdiction
                        },
                        success: (val) => {
                            length = val.response.data ? val.response.data.length : 0;
                        }
                    }}
                    topSearchExtendBtns={[
                        {
                            field: "btn1",
                            label: "??????",
                            onClick: (val) => {
                                if (val) {
                                    let value = val.searchData;
                                    value.then((data) => {
                                        let value = data;
                                        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                        var URL = `${ureport}excel?_u=minio:zxSkTurnoverStatTotalRpt.ureport.xml&access_token=${access_token}&_n=??????????????????????????????${moment(new Date()).format('YYYY-MM-DD')}&orgID=${value.departmentId ? value.departmentId : ''}&resID=${value.resID ? value.resID : ''}&yearPeriod=${value.yearPeriod ? value.yearPeriod : ''}&beginMonth=${value.beginMonth ? value.beginMonth : ''}&endMonth=${value.endMonth ? value.endMonth : ''}`;
                                        confirm({
                                            content: '??????????????????????',
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
                                label: '????????????',
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
                                        departmentId: jurisdiction
                                    }
                                },
                                placeholder: '?????????'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '????????????',
                                field: 'resID',
                                type: 'selectByQnnTable',
                                optionConfig: {
                                    value: 'id',
                                    label: "resCode"
                                },
                                dropdownMatchSelectWidth: 850,
                                qnnTableConfig: {
                                    antd: { rowKey: "id" },
                                    fetchConfig: {
                                        apiName: "getZxSkTurnoverInResourceList"
                                    },
                                    searchBtnsStyle: "inline",
                                    formConfig: [
                                        {
                                            isInForm: false,
                                            isInSearch: true,
                                            table: {
                                                dataIndex: "resCode",
                                                title: "??????",
                                                width: 150
                                            },
                                            form: { type: 'string' }
                                        },
                                        {
                                            isInForm: false,
                                            isInSearch: true,
                                            table: {
                                                dataIndex: "resName",
                                                title: "??????",
                                                width: 100
                                            },
                                            form: { type: 'string' }
                                        },
                                        {
                                            isInForm: false,
                                            isInSearch: true,
                                            table: {
                                                dataIndex: "spec",
                                                title: "????????????",
                                                width: 100
                                            },
                                            form: { type: 'string' }
                                        },
                                        {
                                            isInForm: false,
                                            isInSearch: true,
                                            table: {
                                                dataIndex: "resUnit",
                                                title: "????????????",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            // isInSearch:true,
                                            table: {
                                                dataIndex: "pricingManner",
                                                title: "????????????",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            // isInSearch:true,
                                            table: {
                                                dataIndex: "planningAuthorities",
                                                title: "????????????",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        }
                                    ]
                                },
                                placeholder: '?????????'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'year',
                                label: '??????',
                                field: 'yearPeriod'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'select',
                                label: '????????????',
                                field: 'beginMonth',
                                span: 8,
                                allowClear: false,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                },
                                optionData: [
                                    { itemName: '1???', itemId: -1123200000 },
                                    { itemName: '2???', itemId: 1555200000 },
                                    { itemName: '3???', itemId: 4060800000 },
                                    { itemName: '4???', itemId: 6739200000 },
                                    { itemName: '5???', itemId: 9331200000 },
                                    { itemName: '6???', itemId: 12009600000 },
                                    { itemName: '7???', itemId: 14601600000 },
                                    { itemName: '8???', itemId: 17280000000 },
                                    { itemName: '9???', itemId: 19958400000 },
                                    { itemName: '10???', itemId: 22550400000 },
                                    { itemName: '11???', itemId: 25228800000 },
                                    { itemName: '12???', itemId: 27820800000 },
                                ]
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'select',
                                label: '????????????',
                                field: 'endMonth',
                                span: 8,
                                allowClear: false,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                },
                                optionData: [
                                    { itemName: '1???', itemId: -1123200000 },
                                    { itemName: '2???', itemId: 1555200000 },
                                    { itemName: '3???', itemId: 4060800000 },
                                    { itemName: '4???', itemId: 6739200000 },
                                    { itemName: '5???', itemId: 9331200000 },
                                    { itemName: '6???', itemId: 12009600000 },
                                    { itemName: '7???', itemId: 14601600000 },
                                    { itemName: '8???', itemId: 17280000000 },
                                    { itemName: '9???', itemId: 19958400000 },
                                    { itemName: '10???', itemId: 22550400000 },
                                    { itemName: '11???', itemId: 25228800000 },
                                    { itemName: '12???', itemId: 27820800000 },
                                ]
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSkTurnoverStatTotalRptId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: ' resCode',
                                width: 160,
                                tooltip: 23,
                                key: ' resCode',
                                render: (text, row, index) => {
                                    if (length > 1 && index >= length - 1) {
                                        return {
                                            children: <div style={{ textAlign: 'center' }}>??????</div>,
                                            props: {
                                                colSpan: 2
                                            },
                                        };
                                    }
                                    return <span>{row.resCode}</span>;
                                },
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'resName',
                                width: 200,
                                tooltip: 23,
                                key: 'resName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'invoiceNo',
                                key: 'invoiceNo',
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'lastInAmt',
                                        key: 'lastInAmt',
                                        width: 130
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'lastremainAmt',
                                        key: 'lastremainAmt',
                                        width: 130
                                    },
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'invoiceNo',
                                key: 'invoiceNo',
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'inAmt',
                                        key: 'inAmt',
                                        width: 130
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'invoiceNo',
                                key: 'invoiceNo',
                                children: [
                                    {
                                        title: '????????????',
                                        dataIndex: 'shareAmts',
                                        key: 'shareAmts',
                                        width: 130
                                    },
                                    {
                                        title: '????????????',
                                        dataIndex: 'shareAmtTotal',
                                        key: 'shareAmtTotal',
                                        width: 130
                                    },
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'invoiceNo',
                                key: 'invoiceNo',
                                children: [
                                    {
                                        title: '????????????',
                                        dataIndex: 'allBuyAmt',
                                        key: 'allBuyAmt',
                                        width: 130
                                    },
                                    {
                                        title: '????????????',
                                        dataIndex: 'allCurrentAmt',
                                        key: 'allCurrentAmt',
                                        width: 130
                                    },
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'invoiceNo',
                                key: 'invoiceNo',
                                children: [
                                    {
                                        title: '????????????',
                                        dataIndex: 'allOrigAmt',
                                        key: 'allOrigAmt',
                                        width: 130
                                    },
                                    {
                                        title: '????????????',
                                        dataIndex: 'allRemainAmt',
                                        key: 'allRemainAmt',
                                        width: 130
                                    },
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'invoiceNo',
                                key: 'invoiceNo',
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'thisInAmt',
                                        key: 'thisInAmt',
                                        width: 130
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'thisBadAmt',
                                        key: 'thisBadAmt',
                                        width: 130
                                    },
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