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
                        apiName: 'ureportZxSkCustomerOutRes',
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
                                        var URL = `${ureport}excel?_u=minio:zxSkCustomerOutRes.ureport.xml&_n=???????????????&access_token=${access_token}&orgID=${value.orgID ? value.orgID : ''}&outOrgID=${value.outOrgID ? value.outOrgID : ''}&secondResTypeID=${value.secondResTypeID ? value.secondResTypeID : ''}&resID=${value.resID ? value.resID : ''}&resTypeID=${value.resTypeID ? value.resTypeID : ''}&beginDate=${value.beginDate ? value.beginDate : ''}&endDate=${value.endDate ? value.endDate : ''}`;
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
                                placeholder: '?????????',
                                children: [
                                    {
                                        field: 'resID'
                                    }
                                ]
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '???????????????',
                                field: 'outOrgID',
                                type: 'selectByPaging',
                                optionConfig: {
                                    label: 'customerName',
                                    value: 'zxCrCustomerNewId',
                                },
                                fetchConfig: {
                                    apiName: 'getZxCrCustomerNewList',
                                    otherParams: {
                                        limit: 10,
                                        page: 1
                                    }
                                },
                                showSearch: true,
                                placeholder: '?????????'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '????????????',
                                field: 'resTypeID',
                                dependenciesReRender: true,//????????????-??????
                                dependencies: ['orgID'],
                                type: 'select',
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id',
                                },

                                fetchConfig: {
                                    apiName: 'getZxSkResCategoryMaterialsListResourceNotRevolve',
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
                                placeholder: '?????????',
                                onChange: (val, obj) => {
                                    obj.form.setFieldsValue({ secondResTypeID: '', resID: '' });
                                }
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '????????????',
                                field: 'secondResTypeID',
                                dependenciesReRender: true,//????????????-??????
                                dependencies: ['resTypeID', 'orgID'],
                                type: 'select',
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id',
                                },
                                fetchConfig: {
                                    apiName: 'getZxSkResCategoryMaterialsListResourceNotRevolveSecondResTypeID',
                                    otherParams: (val) => {
                                        let resTypeIDVal = '';
                                        let orgIDVal = '';
                                        if (val.btnCallbackFn?.form) {
                                            let aa = val.btnCallbackFn.form.getFieldsValue();
                                            resTypeIDVal = aa.resTypeID;
                                            orgIDVal = aa.orgID;
                                        } else {

                                        }
                                        return {
                                            resTypeID: resTypeIDVal,
                                            parentOrgID: orgIDVal
                                        }
                                    }
                                },
                                placeholder: '?????????',
                                onChange: (val, obj) => {
                                    obj.form.setFieldsValue({ resID: '' });
                                }
                            }
                        },

                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '????????????',
                                field: 'resID',
                                type: 'selectByQnnTable',
                                dependenciesReRender: true,//????????????-??????
                                dependencies: ['resTypeID', 'secondResTypeID'],
                                optionConfig: {
                                    value: 'id',
                                    label: "resCode"
                                },
                                dropdownMatchSelectWidth: 850,
                                qnnTableConfig: {
                                    antd: { rowKey: "id" },
                                    fetchConfig: {
                                        apiName: "getZxSkResourceMaterialsListNameJoinResource",
                                        otherParams: (val) => {
                                            let resTypeIDVal = '';
                                            let secondResTypeIDVal = '';
                                            if (val.props.qnnFormProps?.form) {
                                                let aa = val.props.qnnFormProps.form.getFieldsValue();
                                                resTypeIDVal = aa.resTypeID;
                                                secondResTypeIDVal = aa.secondResTypeID;
                                            } else {

                                            }
                                            return {
                                                id: secondResTypeIDVal || resTypeIDVal
                                            }
                                        }
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
                                            table: {
                                                dataIndex: "unit",
                                                title: "??????",
                                                width: 100
                                            },
                                            form: { type: 'string' }
                                        },
                                        {
                                            isInForm: false,
                                            table: {
                                                dataIndex: "resStyle",
                                                title: "????????????",
                                                width: 100
                                            },
                                            form: { type: 'string' }
                                        },
                                    ]
                                },
                                placeholder: '?????????'
                            }
                        },

                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'date',
                                label: '????????????',
                                field: 'beginDate'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'date',
                                label: '????????????',
                                field: 'endDate'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSkCustomerOutResId',
                                type: 'string',
                                hide: true,
                            }
                        },

                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                width: 200,
                                tooltip: 23
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '?????????',
                                dataIndex: 'outOrgName',
                                width: 200,
                                tooltip: 23,
                                key: 'outOrgName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'busDate',
                                key: 'busDate',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'billNo',
                                key: 'billNo',
                                width: 200,
                                tooltip: 23
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 160,
                                dataIndex: 'resourceName',
                                key: 'resourceName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 180,
                                tooltip: 23,
                                dataIndex: 'resCode',
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
                                title: '????????????',
                                width: 160,
                                dataIndex: 'spec',
                                key: 'spec'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 160,
                                dataIndex: 'resUnit',
                                key: 'resUnit'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 160,
                                dataIndex: 'inQty',
                                key: 'inQty'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????',
                                width: 160,
                                dataIndex: 'inPrice',
                                key: 'inPrice'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????',
                                width: 160,
                                dataIndex: 'resAllFee',
                                key: 'resAllFee'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '?????????????????????',
                                width: 160,
                                dataIndex: 'inPriceNoTax',
                                key: 'inPriceNoTax'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '?????????????????????',
                                width: 160,
                                dataIndex: 'resAllFeeNoTax',
                                key: 'resAllFeeNoTax'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 160,
                                dataIndex: 'inAmt',
                                key: 'inAmt'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 160,
                                dataIndex: 'asmaterialSource',
                                key: 'asmaterialSource'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 160,
                                dataIndex: 'precollecte',
                                key: 'precollecte'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????',
                                width: 160,
                                dataIndex: 'purchType',
                                key: 'purchType'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'contractNo',
                                key: 'contractNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 160,
                                dataIndex: 'remarks',
                                key: 'remarks'
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