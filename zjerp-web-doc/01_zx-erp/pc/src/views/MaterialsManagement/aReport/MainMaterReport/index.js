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
                            label: "??????",
                            onClick: (val) => {
                                if (val) {
                                    let value = val.searchData;
                                    value.then((data) => {
                                        let value = data;
                                        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                        var URL = `${ureport}excel?_u=minio:zxSkMosResMovStatRep.xml&_n=???????????????????????????????????????&type=1&access_token=${access_token}&orgID=${value.orgID ? value.orgID : ''}&monthly=${value.monthly ? value.monthly : ''}`;
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
                                label: '??????',
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
                                placeholder: '?????????'
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'month',
                                label: '??????',
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
                                title: '??????',
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
                                title: '????????????',
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
                                title: '?????????????????????',
                                dataIndex: 'serialNumber',
                                key: 'serialNumber',
                                width: 180,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 120,
                                dataIndex: 'unit',
                                key: 'unit'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????',
                                width: 120,
                                dataIndex: 'stockQty',
                                key: 'stockQty'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????????????????',
                                dataIndex: 'spec1',
                                key: 'spec1',
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'unit3',
                                        key: 'unit3',
                                        children: [
                                            {
                                                title: '??????',
                                                dataIndex: 'inQty',
                                                key: 'inQty',
                                                width: 130,
                                            },
                                            {
                                                title: '???????????????',
                                                dataIndex: 'inPriceNoTax',
                                                key: 'inPriceNoTax',
                                                width: 130,
                                            },
                                            {
                                                title: '????????????',
                                                dataIndex: 'inPrice',
                                                key: 'inPrice',
                                                width: 130,
                                            },
                                            {
                                                title: '???????????????',
                                                dataIndex: 'inAmtNoTax',
                                                key: 'inAmtNoTax',
                                                width: 130,
                                            },
                                            {
                                                title: '????????????',
                                                dataIndex: 'inAmt',
                                                key: 'inAmt',
                                                width: 130,
                                            }
                                        ]
                                    },
                                    {
                                        title: '????????????',
                                        dataIndex: 'serQty',
                                        key: 'serQty',
                                        width: 130,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'orsQty',
                                        key: 'orsQty',
                                        width: 130,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'obuQty',
                                        key: 'obuQty',
                                        width: 130,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'otrQty',
                                        key: 'otrQty',
                                        width: 130,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'ocsQty',
                                        key: 'ocsQty',
                                        width: 130,
                                    },
                                ]
                            }
                        },
                        {
                            table: {
                                title: '???????????????',
                                dataIndex: 'oswQty',
                                key: 'oswQty'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????',
                                dataIndex: 'otkQty',
                                key: 'otkQty'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???(+)<br/>???(-)',
                                dataIndex: 'vinQty',
                                key: 'vinQty'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????',
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