import { Tooltip } from "antd";
import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
const config = {

    antd: {
        rowKey: 'zxCrCustomerExtAttrId',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : '',
            lockProjectName: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectName : '',
        }
    }
    componentDidMount() { }
    render() {
        const { ext1, departmentId, departmentName, companyName, companyId, projectId, projectName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { lockProjectId, lockProjectName } = this.state;
        let jurisdiction = departmentId;
        let jurisdictionName = departmentName;
        if (lockProjectId !== 'all' && lockProjectId !== '') {
            jurisdiction = lockProjectId;
            jurisdictionName = lockProjectName;
        } else {
            if (ext1 === '1' || ext1 === '2') {
                jurisdiction = companyId;
                jurisdictionName = companyName;
            } else if (ext1 === '3' || ext1 === '4') {
                jurisdiction = projectId;
                jurisdictionName = projectName;
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
                        apiName: 'getZxCrCustomerExtAttrList',
                        otherParams: {
                            type: 'eqr',
                            projectId: jurisdiction
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxCrCustomerExtAttrId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'projectId',
                                type: 'string',
                                initialValue: jurisdiction,
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'projectName',
                                type: 'string',
                                initialValue: jurisdictionName,
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'customerNO',
                                key: 'customerNO',
                                filter: true
                            },
                            isInForm: false,
                            form: {
                                type: 'string',
                                field: 'customerNO'
                            }
                        },
                        {
                            table: {
                                title: '????????????(??????)??????',
                                dataIndex: 'customerName',
                                key: 'customerName',
                                width: 200,
                                onClick: 'detail',
                                render: (data, rowData) => {
                                    return <Tooltip title={data}>
                                        <div style={{ color: 'rgb(24 174 255)', width: '200px', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>{data}</div>
                                        {/* <span style={{color:'rgb(24 174 255)'}}>{data}</span> */}
                                    </Tooltip>
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'kind',
                                key: 'kind',
                                type: 'select',
                            },
                            isInForm: false,
                            form: {
                                field: 'kind',
                                type: 'select',
                                optionData: [
                                    {
                                        label: "??????",
                                        value: "1",
                                    },
                                    {
                                        label: "??????",
                                        value: "0",
                                    }
                                ],
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'relat',
                                key: 'relat',
                                type: 'select',
                            },
                            isInForm: false,
                            form: {
                                type: 'select',
                                optionData: [
                                    {
                                        label: "???",
                                        value: "0",
                                    },
                                    {
                                        label: "???",
                                        value: "1",
                                    }
                                ],
                            }
                        },
                        {
                            table: {
                                title: '?????????',
                                dataIndex: 'linkmanName',
                                key: 'linkmanName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'linkmanTel',
                                key: 'linkmanTel'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'creditStanding',
                                key: 'creditStanding',
                                type: 'select'
                            },
                            isInForm: false,
                            form: {
                                type: 'select',
                                field: 'creditStanding',
                                optionData: [
                                    {
                                        label: "???",
                                        value: "004",
                                    },
                                    {
                                        label: "???",
                                        value: "003",
                                    },
                                    {
                                        label: "???",
                                        value: "002",
                                    },
                                    {
                                        label: "?????????",
                                        value: "001",//??????????????????????????????001
                                    }
                                ],
                            }
                        },
                        {
                            table: {
                                title: '????????????????????????',
                                dataIndex: 'pricinpalTel',
                                key: 'pricinpalTel'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'pricinpalFax',
                                key: 'pricinpalFax'
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'component',
                                field: 'diySK1',
                                Component: obj => {
                                    return (
                                        <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                            ????????????
                                        </div>
                                    );
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????????????????",
                                field: 'relat',
                                type: 'radio',
                                initialValue: '0',
                                optionData: [
                                    {
                                        label: "???",
                                        value: "0",
                                    },
                                    {
                                        label: "???",
                                        value: "1",
                                    }
                                ],
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????",
                                field: 'kind',
                                type: 'select',
                                optionData: [
                                    {
                                        label: "??????",
                                        value: "1",
                                    },
                                    {
                                        label: "??????",
                                        value: "0",
                                    }
                                ],
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????????????????",
                                field: 'personInChargeIdNumber',
                                type: 'identity',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },

                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "?????????????????????",
                                required: true,
                                field: 'organizationCode',
                                type: 'string',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                                dependencies: ["relat"],
                                hide: (obj) => {
                                    if (obj.form.getFieldValue('relat') === '0') {//?????????????????????-??????
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????(??????)??????",
                                required: true,
                                field: 'organizationCode',//?????????????????????????????????????????????????????????
                                type: 'string',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                                dependencies: ["relat"],
                                hide: (obj) => {
                                    if (obj.form.getFieldValue('relat') === '1') {//????????????(??????)??????-??????
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????",
                                required: true,
                                field: 'customerNO',
                                type: 'string',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????(??????)??????",
                                field: 'customerName',
                                required: true,
                                type: 'string',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                required: true,
                                field: 'legalRepresentative',
                                type: 'string',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "???????????????",
                                field: 'legalPersonClient',
                                type: 'string',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????????????????",
                                field: 'registrationTime',
                                type: 'date',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????????????????",
                                field: 'unitRegistrationArea',
                                type: 'string',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????????????????",
                                field: 'businessLicenseNumber',
                                type: 'string',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "???????????????",
                                required: true,
                                field: 'registeredCapital',
                                type: 'string',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????????????????",
                                field: 'qualificationCertificateNo',
                                type: 'string',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????????????????",
                                field: 'scopeAndGradeOfQualification',
                                type: 'string',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "???????????????????????????",
                                field: 'safetyProductionLicenseNo',
                                type: 'string',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                field: 'natureOfBusiness',
                                type: 'string',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                field: 'creditStanding',
                                type: 'select',
                                optionData: [
                                    {
                                        label: "???",
                                        value: "004",
                                    },
                                    {
                                        label: "???",
                                        value: "003",
                                    },
                                    {
                                        label: "???",
                                        value: "002",
                                    },
                                    {
                                        label: "?????????",
                                        value: "001",//??????????????????????????????001
                                    }
                                ],
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????????????????",
                                field: 'pricinpalPostCode',
                                type: 'string',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????????????????",
                                field: 'mobilePhoneOfLegalEntity',
                                type: 'phone',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????????????????",
                                field: 'pricinpalTel',
                                type: 'phone',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????????????????",
                                field: 'pricinpalFax',
                                type: 'string',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },

                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                field: 'companyType',
                                type: 'string',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????????????????",
                                field: 'pricinpalAddr',
                                type: 'textarea',
                                spanForm: 8,
                                autoSize: {
                                    minRows: 1,
                                    maxRows: 3
                                },
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },

                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????????????????",
                                field: 'experienceOfInhouseCollaboration',
                                type: 'textarea',
                                spanForm: 8,
                                autoSize: {
                                    minRows: 1,
                                    maxRows: 3
                                },
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????????????????",
                                field: 'projectInService',
                                type: 'textarea',
                                spanForm: 8,
                                autoSize: {
                                    minRows: 1,
                                    maxRows: 3
                                },
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'component',
                                field: 'diySK1',
                                Component: obj => {
                                    return (
                                        <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                            ?????????????????????
                                        </div>
                                    );
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????",
                                field: 'linkmanName',
                                type: 'string',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????",
                                field: 'linkmanTel',
                                type: 'string',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????",
                                field: 'fixedLineTelephoneOfMerchants',
                                type: 'phone',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????",
                                field: 'faxOfMerchants',
                                type: 'string',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '??????',
                                field: 'fileList',
                                type: 'files',
                                required: true,
                                fetchConfig: {
                                    apiName: 'upload'
                                }
                            }
                        }
                    ]}
                    actionBtns={[]}
                />
            </div>
        );
    }
}

export default index;