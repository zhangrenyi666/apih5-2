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
    searchFormColNum: 2,
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
                        apiName: 'ureportZxSkReceivingDynamicList',
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
                                        let URL = `${ureport}excel?_u=minio:zxSkReceivingDynamic.xml&_n=???????????????&access_token=${access_token}&orgID=${value.orgID ? value.orgID : ''}&resID=${value.resID ? value.resID : ''}&resTypeID=${value.resTypeID ? value.resTypeID : ''}&beginDate=${value.beginDate ? value.beginDate : ''}&endDate=${value.endDate ? value.endDate : ''}`;
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
                                // spanSearch: 12,
                                // formItemLayoutSearch: {
                                //     labelCol: {
                                //         xs: { span: 24 },
                                //         sm: { span: 12 }
                                //     },
                                //     wrapperCol: {
                                //         xs: { span: 24 },
                                //         sm: { span: 12 }
                                //     }
                                // },
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
                                label: '????????????',
                                type: 'select',
                                // spanSearch: 12,
                                // formItemLayoutSearch: {
                                //     labelCol: {
                                //         xs: { span: 24 },
                                //         sm: { span: 12 }
                                //     },
                                //     wrapperCol: {
                                //         xs: { span: 24 },
                                //         sm: { span: 12 }
                                //     }
                                // },
                                // type: 'selectByQnnTable',
                                field: 'resID',
                                // dropdownMatchSelectWidth: 900,
                                dependenciesReRender: true,//????????????-??????
                                dependencies: ['resTypeID'],
                                optionConfig: {
                                    label: 'resName',
                                    value: 'id'
                                },
                                fetchConfig: {
                                    apiName: 'getZxSkResCategoryMaterialsAllResource',
                                    otherParams: (val) => {
                                        let resTypeID = '0002';
                                        if (val.btnCallbackFn?.form) {
                                            let aa = val.btnCallbackFn.form.getFieldsValue();
                                            if (aa.resTypeID) {
                                                resTypeID = aa.resTypeID;
                                            }
                                        } else {

                                        }
                                        return {
                                            id: resTypeID
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
                                type: 'date',
                                // spanSearch: 12,
                                // formItemLayoutSearch: {
                                //     labelCol: {
                                //         xs: { span: 24 },
                                //         sm: { span: 12 }
                                //     },
                                //     wrapperCol: {
                                //         xs: { span: 24 },
                                //         sm: { span: 12 }
                                //     }
                                // },
                                label: '????????????',
                                field: 'beginDate'
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'date',
                                // spanSearch: 12,
                                // formItemLayoutSearch: {
                                //     labelCol: {
                                //         xs: { span: 24 },
                                //         sm: { span: 12 }
                                //     },
                                //     wrapperCol: {
                                //         xs: { span: 24 },
                                //         sm: { span: 12 }
                                //     }
                                // },
                                label: '????????????',
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
                                title: '??????',
                                dataIndex: 'busDate',
                                key: 'busDate',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'resCode',
                                width: 200,
                                tooltip: 23,
                                key: 'resCode'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'resName',
                                key: 'resName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 120,
                                dataIndex: 'spec',
                                key: 'spec'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 120,
                                dataIndex: 'resUnit',
                                key: 'resUnit'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '?????????',
                                width: 180,
                                tooltip: 23,
                                dataIndex: 'voucherNo',
                                key: 'voucherNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'outOrgName',
                                key: 'outOrgName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 120,
                                dataIndex: 'inPrice',
                                key: 'inPrice'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'spec2',
                                key: 'spec2',
                                children: [
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
                                        dataIndex: 'otrQty',
                                        key: 'otrQty',
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
                                        dataIndex: 'unit',
                                        key: 'unit',
                                        children: [
                                            {
                                                title: '??????',
                                                dataIndex: 'allInQty',
                                                key: 'allInQty',
                                                width: 120,
                                            },
                                            {
                                                title: '??????(???)',
                                                dataIndex: 'allInAmt',
                                                key: 'allInAmt',
                                                width: 120,
                                            }
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'spec1',
                                key: 'spec1',
                                children: [
                                    {
                                        title: '????????????',
                                        dataIndex: 'oswQty',
                                        key: 'oswQty',
                                        width: 130,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'unit',
                                        key: 'unit',
                                        children: [
                                            // {
                                            //     title: '??????',
                                            //     dataIndex: 'otsQty',
                                            //     key: 'otsQty',
                                            //     width: 120,
                                            // },
                                            {
                                                title: '??????',
                                                dataIndex: 'otkQty',
                                                key: 'otkQty',
                                                width: 120,
                                            }
                                        ]
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'unit1',
                                        key: 'unit1',
                                        children: [
                                            {
                                                title: '??????',
                                                dataIndex: 'allOutQty',
                                                key: 'allOutQty',
                                                width: 120,
                                            },
                                            {
                                                title: '????????????',
                                                dataIndex: 'oswPrice',
                                                key: 'oswPrice',
                                                width: 120,
                                            },
                                            {
                                                title: '??????(???)',
                                                dataIndex: 'allOutAmt',
                                                key: 'allOutAmt',
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
                                dataIndex: 'stockQty2',
                                key: 'stockQty2',
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'vinoutQty',
                                        key: 'vinoutQty',
                                        width: 120,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'vinoutAmt',
                                        key: 'vinoutAmt',
                                        width: 120,
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'stockQty',
                                key: 'stockQty',
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'lastQty',
                                        key: 'lastQty',
                                        width: 120,
                                    },
                                    {
                                        title: '????????????',
                                        dataIndex: 'lastPrice',
                                        key: 'lastPrice',
                                        width: 120,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'lastAmt',
                                        key: 'lastAmt',
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