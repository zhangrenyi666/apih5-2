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
                        apiName: 'ureportZxSkResMoveMonthMPList',
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
                                        let URL = `${ureport}excel?_u=minio:zxSkResMoveMonthMP.xml&_n=????????????????????????&access_token=${access_token}&orgID=${value.orgID ? value.orgID : ''}&resTypeID=${value.resTypeID ? value.resTypeID : ''}&period=${value.period ? value.period : ''}`;
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
                                placeholder: '?????????'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'month',
                                label: '??????',
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
                        //         label: '????????????',
                        //         field: 'isFinish',
                        //         optionConfig: {
                        //             label: 'label',
                        //             value: 'value',
                        //         },
                        //         optionData: [
                        //             {
                        //                 label: "??????",
                        //                 value: "0"
                        //             },
                        //             {
                        //                 label: "???",
                        //                 value: "1"
                        //             },
                        //             {
                        //                 label: "???",
                        //                 value: "2"
                        //             }
                        //         ],
                        //         initialValue: () => {
                        //             return '0'
                        //         },
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
                                title: '????????????',
                                dataIndex: 'resCode',
                                key: 'resCode'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'resName',
                                key: 'resName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 120,
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
                                title: '????????????',
                                dataIndex: 'spec1',
                                key: 'spec1',
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'stockQty',
                                        key: 'stockQty',
                                        width: 130,
                                    },
                                    {
                                        title: '????????????(???)',
                                        dataIndex: 'stockPrice',
                                        key: 'stockPrice',
                                        width: 130,
                                    },
                                    {
                                        title: '??????(???)',
                                        dataIndex: 'stockAmt',
                                        key: 'stockAmt',
                                        width: 130,
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'resName2',
                                key: 'resName2',
                                children: [
                                    {
                                        title: '??????',
                                        width: 120,
                                        dataIndex: 'orsQty',
                                        key: 'orsQty',
                                    },
                                    {
                                        title: '??????',
                                        width: 120,
                                        dataIndex: 'otrQty',
                                        key: 'otrQty',
                                    },
                                    {
                                        title: '??????',
                                        width: 120,
                                        dataIndex: 'serQty',
                                        key: 'serQty',
                                    },
                                    {
                                        title: '??????',
                                        width: 120,
                                        dataIndex: 'obuQty',
                                        key: 'obuQty',
                                    },
                                    {
                                        title: '??????',
                                        width: 120,
                                        dataIndex: 'ocsQty',
                                        key: 'ocsQty',
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'resN3ame',
                                        key: 'resN3ame',
                                        children: [
                                            {
                                                title: '??????',
                                                width: 120,
                                                dataIndex: 'inQty',
                                                key: 'inQty',
                                            },
                                            {
                                                title: '????????????(???)',
                                                width: 120,
                                                dataIndex: 'inPrice',
                                                key: 'inPrice',
                                            },
                                            {
                                                title: '??????(???)',
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
                                title: '????????????',
                                dataIndex: 'res1Name',
                                key: 'res1Name',
                                children: [
                                    {
                                        title: '??????',
                                        width: 120,
                                        dataIndex: 'oswQty',
                                        key: 'oswQty',
                                    },
                                    {
                                        title: '????????????(???)',
                                        width: 120,
                                        dataIndex: 'oswPrice',
                                        key: 'oswPrice',
                                    },
                                    {
                                        title: '??????',
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
                                title: '??????',
                                dataIndex: 'resName5',
                                key: 'resName5',
                                children: [
                                    // {
                                    //     title: '????????????',
                                    //     width: 120,
                                    //     dataIndex: 'trsQty',
                                    //     key: 'trsQty',
                                    // },
                                    {
                                        title: '??????',
                                        width: 120,
                                        dataIndex: 'otkQty',
                                        key: 'otkQty',
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'resName7',
                                        key: 'resName7',
                                        children: [
                                            {
                                                title: '??????',
                                                width: 120,
                                                dataIndex: 'otkQtys',
                                                key: 'otkQtys',
                                            },
                                            {
                                                title: '????????????(???)',
                                                width: 120,
                                                dataIndex: 'otkPrice',
                                                key: 'otkPrice',
                                            },
                                            {
                                                title: '??????',
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
                                title: '???(+)???(-)',
                                dataIndex: 'resName8',
                                key: 'resName8',
                                children: [
                                    {
                                        title: '??????',
                                        width: 120,
                                        dataIndex: 'vinQty',
                                        key: 'vinQty',
                                    },
                                    {
                                        title: '??????(???)',
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
                                title: '????????????',
                                dataIndex: 'resName9',
                                key: 'resName9',
                                children: [
                                    {
                                        title: '??????',
                                        width: 120,
                                        dataIndex: 'thisQtys',
                                        key: 'thisQtys',
                                    },
                                    {
                                        title: '????????????(???)',
                                        width: 120,
                                        dataIndex: 'thisProce',
                                        key: 'thisProce',
                                    },
                                    {
                                        title: '??????(???)',
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