import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import { Modal } from "antd";
import { ExportOutlined } from '@ant-design/icons';
const config = {
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
                    antd={{
                        rowKey: 'id',
                        size: 'small',
                        summary: (pageData) => {
                            let total1 = 0;
                            let total2 = 0;
                            let total3 = 0;
                            let total4 = 0;
                            let total5 = 0;
                            let total6 = 0;
                            let total7 = 0;
                            let total8 = 0;
                            pageData.forEach(({ stockAmt, obuAmt, totalAmt, totalAmtAll, oswAmt, otkAmt, vinAmt, thisAmt }) => {
                                total1 += (stockAmt ? stockAmt : 0);
                                total2 += (obuAmt ? obuAmt : 0);
                                total3 += (totalAmt ? totalAmt : 0);
                                total4 += (totalAmtAll ? totalAmtAll : 0);
                                total5 += (oswAmt ? oswAmt : 0);
                                total6 += (otkAmt ? otkAmt : 0);
                                total7 += (vinAmt ? vinAmt : 0);
                                total8 += (thisAmt ? thisAmt : 0);
                            });
                            return (
                                <>
                                    <QnnTable.Summary.Row>
                                        <QnnTable.Summary.Cell index={0} colSpan={3} style={{ textAlign: 'center' }}>??????</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={3}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={4}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={5} style={{ textAlign: 'center' }}>{total1}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={6} style={{ textAlign: 'center' }}>{total2}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={7} style={{ textAlign: 'center' }}>{total3}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={8} style={{ textAlign: 'center' }}>{total4}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={9} style={{ textAlign: 'center' }}>{total5}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={10} style={{ textAlign: 'center' }}>{total6}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={11} style={{ textAlign: 'center' }}>{total7}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={12} style={{ textAlign: 'center' }}>{total8}</QnnTable.Summary.Cell>
                                    </QnnTable.Summary.Row>
                                </>
                            )
                        }
                    }}
                    fetchConfig={{
                        apiName: 'ureportZxSkResInOutStockAllAmt',
                        otherParams: {
                            projectId: jurisdiction
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
                                        var URL = `${ureport}excel?_u=minio:zxskResInOutStockAllAmt.xml&_n=???????????????????????????????????????&access_token=${access_token}&delFlag=0&orgID=${value.orgID ? value.orgID : ''}&resID=${value.resID ? value.resID : ''}&period=${value.period ? value.period : ''}`;
                                        confirm({
                                            content: '??????????????????????',
                                            onOk: () => {
                                                window.open(URL);
                                            }
                                        });
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
                                label: '??????',
                                field: 'orgID',
                                type: 'select',
                                showSearch: true,
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysCompanyProjectList',
                                    otherParams: {
                                        companyId: jurisdiction
                                    }
                                },
                                placeholder: '?????????'
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'select',
                                label: '????????????',
                                field: 'resID',
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
                                placeholder: '?????????'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'month',
                                label: '??????',
                                field: 'period'
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
                                title: '??????',
                                dataIndex: 'index',
                                key: 'index',
                                width: 80,
                                fixed: 'left',
                                render: (data, rowData, index) => {
                                    return index + 1;
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 200,
                                dataIndex: 'resName',
                                key: 'resName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '?????????????????????',
                                dataIndex: 'resCode',
                                key: 'resCode',
                                align: 'center',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????',
                                width: 120,
                                align: 'end',
                                dataIndex: 'stockAmt',
                                key: 'stockAmt'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'spec',
                                key: 'spec',
                                children: [
                                    {
                                        title: '?????????',
                                        dataIndex: 'obuAmt',
                                        align: 'end',
                                        key: 'obuAmt',
                                        width: 130,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'totalAmt',
                                        key: 'totalAmt',
                                        align: 'end',
                                        width: 130,
                                    },
                                    {
                                        title: '???????????????(??????)',
                                        dataIndex: 'totalAmtAll',
                                        key: 'totalAmtAll',
                                        align: 'end',
                                        width: 130,
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'oswAmt',
                                align: 'end',
                                key: 'oswAmt'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'otkAmt',
                                align: 'end',
                                key: 'otkAmt'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???(+)???(-)',
                                dataIndex: 'vinAmt',
                                align: 'end',
                                key: 'vinAmt'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????',
                                dataIndex: 'thisAmt',
                                key: 'thisAmt',
                                align: 'end',
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