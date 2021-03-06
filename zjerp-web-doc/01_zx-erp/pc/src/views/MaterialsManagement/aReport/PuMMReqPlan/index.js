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
                        apiName: 'ureportZxPuMMReqPlan',
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
                                        var URL = `${ureport}excel?_u=minio:zxPuMMReqPlan.ureport.xml&_n=?????????????????????????????????&access_token=${access_token}&projectID=${value.projectID ? value.projectID : ''}&resCateID=${value.resCateID ? value.resCateID : ''}&resID=${value.resID ? value.resID : ''}&period=${value.period ? value.period : ''}&type=${value.type ? value.type : ''}`;
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
                                field: 'projectID',
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
                                field: 'resCateID',
                                type: 'select',
                                showSearch: true,
                                parent: 'projectID',
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id'
                                },
                                fetchConfig: {
                                    apiName: 'getZxSkResCategoryMaterialsListResource',
                                    params: {
                                        parentOrgID: 'projectID'
                                    }
                                },
                                placeholder: '?????????'
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '????????????',
                                parent: 'resCateID',
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
                                        apiName: "getZxSkResourceMaterialsListNameJoinResource",
                                        params: {
                                            id: 'resCateID'
                                        }
                                    },
                                    searchBtnsStyle: "inline",
                                    formConfig: [
                                        {
                                            isInForm: false,
                                            isInTable: false,
                                            table: {
                                                dataIndex: "id",
                                                title: "id",
                                            },
                                            form: {
                                                field: 'id',
                                                type: "string"
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            table: {
                                                dataIndex: "resCode",
                                                title: "??????",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            table: {
                                                dataIndex: "resName",
                                                title: "??????",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            table: {
                                                dataIndex: "spec",
                                                title: "????????????",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            table: {
                                                dataIndex: "unit",
                                                title: "??????",
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
                                type: 'month',
                                label: '??????',
                                field: 'period'
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'select',
                                label: '????????????',
                                field: 'type',
                                allowClear: false,
                                initialValue: '0',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "??????",
                                        value: "0"
                                    },
                                    {
                                        label: "??????",
                                        value: "1"
                                    }
                                ]
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'zxPuMMReqPlanId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 200,
                                dataIndex: 'projectName',
                                key: 'projectName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 200,
                                dataIndex: 'cbsName',
                                key: 'cbsName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'resCateName',
                                width: 200,
                                key: 'resCateName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 200,
                                dataIndex: 'resCode',
                                key: 'resCode'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'resName',
                                key: 'resName',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'unit',
                                key: 'unit'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'spec',
                                key: 'spec'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'price',
                                key: 'price'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '?????????????????????',
                                dataIndex: 'purNum',
                                key: 'purNum',
                                width: 120
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '??????',
                                dataIndex: 'totalMoney',
                                key: 'totalMoney'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'nextMonthNum',
                                key: 'nextMonthNum'
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