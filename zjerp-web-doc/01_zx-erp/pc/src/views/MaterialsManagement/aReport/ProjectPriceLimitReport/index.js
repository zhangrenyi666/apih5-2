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
            } else {

            }
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
                        apiName: 'ureportZxSkLimitPriceRepIdle',
                        otherParams: {
                            companyId: jurisdiction,
                            // orgID:jurisdiction
                        }
                    }}
                    topSearchExtendBtns={[
                        {
                            field: "btn1",
                            label: "??????",
                            onClick: (val) => {
                                if (this.table?.btnCallbackFn?.searchForm?.form) {
                                    let value = this.table.btnCallbackFn.searchForm.form.getFieldsValue().searchParams;
                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                    var URL = `${ureport}excel?_u=minio:zxSkLimitPriceRepIdle.ureport.xml&access_token=${access_token}&_n='????????????????????????'&projectId=${value.projectId ? value.projectId : ''}&province=${value.province ? value.province : ''}&periodTime=${value.periodTime ? new Date(value.periodTime._d).getTime() : ''}&resTypeID=${value.resTypeID ? value.resTypeID : ''}&workId=${value.workId ? value.workId : ''}`;

                                    confirm({
                                        content: '??????????????????????',
                                        onOk: () => {
                                            window.open(URL);
                                        }
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
                                label: '????????????',
                                field: 'projectId',
                                type: 'select',
                                allowClear: true,
                                showSearch: true,
                                initialValue: () => {
                                    let extVal = this.props.loginAndLogoutInfo.loginInfo.userInfo.ext1;
                                    let departmentIdVal = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId;
                                    // ?????????2--??????3???4--admin 1
                                    if (extVal === '2' || extVal === '1' || extVal === '') {
                                        return ''
                                    } else {
                                        return departmentIdVal
                                    }
                                },
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams: { departmentId: jurisdiction }
                                },
                                placeholder: '?????????',
                                spanForm: 6
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '??????',
                                field: 'periodTime',
                                type: 'halfYear',
                                placeholder: '?????????',
                                spanForm: 6
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '??????',
                                type: 'select',
                                field: 'province',
                                showSearch: true,
                                optionConfig: {
                                    label: 'itemName', //?????? label
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'yezhuhetongtaizhangshengfen'
                                    }
                                },
                                spanForm: 6
                            },
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
                                        parentOrgID: "1"
                                    }
                                },
                                placeholder: '?????????',
                                spanForm: 6,
                                onChange: (val, obj) => {
                                    obj.form.setFieldsValue({
                                        workId: ''
                                    })
                                }
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '????????????',
                                type: 'selectByQnnTable',
                                field: 'workId',
                                dependenciesReRender: true,//????????????-??????
                                dependencies: ['resTypeID'],
                                dropdownMatchSelectWidth: 900,
                                optionConfig: {
                                    label: 'resCode',
                                    value: 'id'
                                },
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: "id"
                                    },
                                    firstRowIsSearch: false,
                                    fetchConfig: {
                                        apiName: 'getZxSkResourceMaterialsListNameJoinResource',
                                        otherParams: (val) => {
                                            let resTypeIDVal = '';
                                            if (val.props.qnnFormProps?.form) {
                                                let aa = val.props.qnnFormProps.form.getFieldsValue();
                                                resTypeIDVal = aa.resTypeID;
                                            } else {
                                                resTypeIDVal = '';
                                            }
                                            return {
                                                id: resTypeIDVal,
                                            }
                                        }
                                    },
                                    searchBtnsStyle: "inline",
                                    formConfig: [
                                        {
                                            isInForm: false,
                                            isInTable: false,
                                            form: {
                                                field: 'id',
                                                type: "string"
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            isInSearch: true,
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
                                            isInSearch: true,
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
                                            isInSearch: true,
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
                                            isInSearch: true,
                                            table: {
                                                dataIndex: "unit",
                                                title: "??????",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            // isInSearch:true,
                                            table: {
                                                dataIndex: "refpriceType",
                                                title: "????????????",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        }
                                    ]
                                },
                                spanForm: 6
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
                            isInTable: false,
                            form: {
                                field: 'orgID',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'period',
                                key: 'period',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                tooltip: 23,
                                width: 200,
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'resourceName',
                                key: 'resourceName',
                                width: 160
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'resourceNo',
                                key: 'resourceNo',
                                width: 160
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'workName',
                                key: 'workName',
                                tooltip: 23,
                                width: 200
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 160
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'unit',
                                key: 'unit',
                                width: 160
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'province',
                                key: 'province',
                                width: 160
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????(??????)',
                                dataIndex: 'price',
                                key: 'price',
                                width: 160
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