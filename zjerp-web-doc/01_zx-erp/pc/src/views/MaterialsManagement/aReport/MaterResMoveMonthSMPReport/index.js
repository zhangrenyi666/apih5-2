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
    paginationConfig:false,
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
                        apiName: 'ureportZxSkResMoveMonthSMPList',
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
                                        var URL = `${ureport}excel?_u=minio:zxSkResMoveMonthSMP.ureport.xml&access_token=${access_token}&delFlag=0&_n=??????????????????????????????${moment(new Date()).format('YYYY-MM-DD')}&orgID=${value.orgID ? value.orgID :''}&resID=${value.resID ? value.resID :''}&period=${value.period ? value.period : ''}`;
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
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams: {
                                        departmentId: jurisdiction
                                    }
                                },
                                placeholder: '?????????',
                                children: [{
                                    field: 'resID'
                                }]
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'select',
                                label: '????????????',
                                field: 'resID',
                                showSearch: true,
                                dependenciesReRender: true,//????????????-??????
                                dependencies: ['orgID'],
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id'
                                },
                                fetchConfig: {
                                    apiName: 'getZxSkResCategoryMaterialsListResource',
                                    otherParams: (val) => {
                                        let orgIDVal = '';
                                        if (val.btnCallbackFn?.form) {
                                            let aa = val.btnCallbackFn.form.getFieldsValue();
                                            orgIDVal = aa.orgID;
                                        } else {

                                        }
                                        return {
                                            parentOrgID: orgIDVal
                                        }
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
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSkResMoveMonthSMPId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'resCode',
                                key: 'resCode',
                                width: 200,
                                // tooltip: 23
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'resName',
                                width: 200,
                                // tooltip: 23,
                                key: 'resName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'stock1',
                                key: 'stock1',
                                children: [
                                    {
                                        title: '??????????????????',
                                        width: 200,
                                        dataIndex: 'stockQty',
                                        key: 'stockQty'
                                    },
                                    {
                                        title: '?????????????????????????????????',
                                        dataIndex: 'stockPrice',
                                        key: 'stockPrice',
                                        width: 200,
                                    },
                                    {
                                        title: '???????????????????????????',
                                        width: 200,
                                        dataIndex: 'stockAmt',
                                        key: 'stockAmt'
                                    }
                                ]
                            }
                        },

                        {
                            table: {
                                title: '??????',
                                dataIndex: 'spec',
                                key: 'spec',
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'orsQty',
                                        key: 'orsQty',
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
                                        dataIndex: 'serQty',
                                        key: 'serQty',
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
                                        dataIndex: 'ocsQty',
                                        key: 'ocsQty',
                                        width: 130,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'resUnit',
                                        key: 'resUnit',
                                        children: [
                                            {
                                                title: '??????',
                                                dataIndex: 'inQty',
                                                key: 'inQty',
                                                width: 120,
                                            },
                                            {
                                                title: '????????????(???)',
                                                dataIndex: 'inPrice',
                                                key: 'inPrice',
                                                width: 120,
                                            },
                                            {
                                                title: '??????(???)',
                                                dataIndex: 'inAmt',
                                                key: 'inAmt',
                                                width: 120,
                                            }
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'spec',
                                key: 'spec',
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'oswQty',
                                        key: 'oswQty',
                                        width: 130,
                                    },
                                    {
                                        title: '?????????????????????',
                                        dataIndex: 'oswPrice',
                                        key: 'oswPrice',
                                        width: 130,
                                    },
                                    {
                                        title: '???????????????',
                                        dataIndex: 'oswAmt',
                                        key: 'oswAmt',
                                        width: 130,
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'spec',
                                key: 'spec',
                                children: [
                                    // {
                                    //     title: '????????????',
                                    //     dataIndex: 'trsQty',
                                    //     key: 'trsQty',
                                    //     width: 120,
                                    // },
                                    {
                                        title: '??????',
                                        dataIndex: 'otkQty',
                                        key: 'otkQty',
                                        width: 120,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'unit1',
                                        key: 'unit1',
                                        children: [
                                            {
                                                title: '??????',
                                                dataIndex: 'otkQtys',
                                                key: 'otkQtys',
                                                width: 120,
                                            },
                                            {
                                                title: '?????????????????????',
                                                dataIndex: 'otkPrice',
                                                key: 'otkPrice',
                                                width: 120,
                                            },
                                            {
                                                title: '??????(???)',
                                                dataIndex: 'outAmt',
                                                key: 'outAmt',
                                                width: 120,
                                            }
                                        ]
                                    }
                                ]
                            }
                        },

                        {
                            table: {
                                title: '???(+)???(-)',
                                dataIndex: 'spce',
                                key: 'spce',
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'vinQty',
                                        key: 'vinQty',
                                        width: 120,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'vinAmt',
                                        key: 'vinAmt',
                                        width: 120,
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'stockQty',
                                key: 'stockQty',
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'thisQty',
                                        key: 'thisQty',
                                        width: 120,
                                    },
                                    {
                                        title: '????????????',
                                        dataIndex: 'thisPrice',
                                        key: 'thisPrice',
                                        width: 120,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'thisAmt',
                                        key: 'thisAmt',
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