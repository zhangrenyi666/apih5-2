import { Button, Tooltip } from "antd";
import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal, Spin } from 'antd';
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
    isShowRowSelect: true
};
const configBh = {
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visibleBjdh: false,
            loadingBjdh: false,
            organizationCodeData: '',
            searchTheCustomerNameData: '',
            relatData: '',
            kindData: '',
            personInChargeIdNumberData: '',
            lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : '',
            lockProjectName: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectName : '',
        }
    }
    componentDidMount() { }
    handleCancelOther = () => {
        this.setState({ loadingBjdh: false, visibleBjdh: false });
    }
    render() {
        const { visibleBjdh, loadingBjdh, lockProjectName, lockProjectId } = this.state;
        const { ext1, departmentId, departmentName, companyName, companyId, projectId, projectName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                            type: 'ots',
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
                                field: 'companyId',
                                type: 'string',
                                initialValue: companyId,
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'companyName',
                                type: 'string',
                                initialValue: companyName,
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
                                width: 200,
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
                                dataIndex: 'searchTheCustomerName',
                                key: 'searchTheCustomerName',
                                width: 200,
                                onClick: 'detail',
                                filter: true,
                                render: (data, rowData) => {
                                    return <Tooltip title={data}>
                                        <div style={{ color: 'rgb(24 174 255)', width: '190px', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>{data}</div>
                                    </Tooltip>
                                }
                            },
                            isInForm: false,
                            form: {
                                type: 'string',
                                field: 'customerName'
                            }
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
                                        value: "0",
                                    },
                                    {
                                        label: "??????",
                                        value: "1",
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
                                width: 160,
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
                                field: 'diySK_4',
                                Component: obj => {
                                    return (
                                        <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                            ??????????????????
                                        </div>
                                    );
                                },
                                dependencies: ['btnCompent'],
                                hide: (obj) => {
                                    if (obj.form.getFieldValue('btnCompent') === 'detail' || obj.form.getFieldValue('btnCompent') === 'edit') {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'component',
                                field: 'diySK_a',
                                Component: obj => {
                                    return (
                                        <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                            ??????????????????
                                        </div>
                                    );
                                },
                                dependencies: ['btnCompent'],
                                hide: (obj) => {
                                    if (obj.form.getFieldValue('btnCompent') === 'add') {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????????????????",
                                field: 'relat',
                                type: 'radio',
                                editDisabled: true,
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
                                onChange: () => {
                                    this.table.qnnForm.form.setFieldsValue({
                                        changeRelat: 'change',
                                        jiansuo: 'click',
                                        // ????????????
                                        customerNO: '',
                                        searchTheCustomerName: '',
                                        organizationCode: '',
                                        legalRepresentative: '',
                                        legalPersonClient: '',
                                        registrationTime: '',
                                        unitRegistrationArea: '',
                                        businessLicenseNumber: '',
                                        registeredCapital: '',
                                        qualificationCertificateNo: '',
                                        scopeAndGradeOfQualification: '',
                                        safetyProductionLicenseNo: '',
                                        natureOfBusiness: '',
                                        creditStanding: '',
                                        pricinpalPostCode: '',
                                        mobilePhoneOfLegalEntity: '',
                                        pricinpalTel: '',
                                        pricinpalFax: '',
                                        companyType: '',
                                        pricinpalAddr: '',
                                        experienceOfInhouseCollaboration: '',
                                        projectInService: '',
                                        linkmanName: '',
                                        linkmanTel: '',
                                        fixedLineTelephoneOfMerchants: '',
                                        faxOfMerchants: '',
                                        fileList: '',
                                        customerName: ''
                                    })
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????",
                                field: 'kind',
                                initialValue: '0',
                                editDisabled: true,
                                required: true,
                                type: 'select',
                                optionData: [
                                    {
                                        label: "??????",
                                        value: "0",
                                    },
                                    {
                                        label: "??????",
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
                                onChange: () => {
                                    this.table.qnnForm.form.setFieldsValue({
                                        jiansuo: 'click',
                                        // ????????????
                                        customerNO: '',
                                        searchTheCustomerName: '',
                                        organizationCode: '',
                                        legalRepresentative: '',
                                        legalPersonClient: '',
                                        registrationTime: '',
                                        unitRegistrationArea: '',
                                        businessLicenseNumber: '',
                                        registeredCapital: '',
                                        qualificationCertificateNo: '',
                                        scopeAndGradeOfQualification: '',
                                        safetyProductionLicenseNo: '',
                                        natureOfBusiness: '',
                                        creditStanding: '',
                                        pricinpalPostCode: '',
                                        mobilePhoneOfLegalEntity: '',
                                        pricinpalTel: '',
                                        pricinpalFax: '',
                                        companyType: '',
                                        pricinpalAddr: '',
                                        experienceOfInhouseCollaboration: '',
                                        projectInService: '',
                                        linkmanName: '',
                                        linkmanTel: '',
                                        fixedLineTelephoneOfMerchants: '',
                                        faxOfMerchants: '',
                                        fileList: '',
                                        customerName: ''
                                    })
                                }
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
                                field: 'searchTheCustomerName',//?????????????????????????????????????????????????????????
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
                                required:true,
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
                                type: 'component',
                                field: 'diySK_o',
                                Component: obj => {
                                    return (
                                        <div style={{ margin: '11px 60px 11px 126px', float: 'right' }}>
                                            <Button type='primary' onClick={() => {
                                                // ????????????-????????????
                                                let val1 = this.table.qnnForm.form.getFieldsValue();
                                                if (val1.relat === '1') {//????????????-???
                                                    if (val1.kind === '1') {//??????
                                                        // if (val1.personInChargeIdNumber) {//?????????--????????????000003131
                                                        if (val1.searchTheCustomerName) {//????????????????????????
                                                            this.setState({
                                                                visibleBjdh: true,
                                                                searchTheCustomerNameData: val1.searchTheCustomerName,
                                                                relatData: val1.relat,
                                                                kindData: val1.kind ? val1.kind : null,
                                                                personInChargeIdNumberData: val1.personInChargeIdNumber ? val1.personInChargeIdNumber : null
                                                            });
                                                            this.table.qnnForm.form.setFieldsValue({
                                                                jiansuo: 'unClick'
                                                            })
                                                        } else {
                                                            Msg.warn('?????????????????????(??????)?????????')
                                                        }
                                                        // } else {
                                                        //     Msg.warn('?????????????????????')
                                                        // }
                                                    } else {//??????
                                                        if (val1.searchTheCustomerName) {//??????????????????
                                                            this.setState({
                                                                visibleBjdh: true,
                                                                searchTheCustomerNameData: val1.searchTheCustomerName,
                                                                relatData: val1.relat,
                                                                kindData: val1.kind ? val1.kind : null,
                                                                personInChargeIdNumberData: val1.personInChargeIdNumber ? val1.personInChargeIdNumber : null
                                                            });
                                                            this.table.qnnForm.form.setFieldsValue({
                                                                jiansuo: 'unClick'
                                                            })
                                                        } else {
                                                            Msg.warn('?????????????????????(??????)?????????')
                                                        }
                                                    }
                                                } else {
                                                    if (val1.kind === '1') {//??????
                                                        if (val1.personInChargeIdNumber) {//?????????
                                                            this.setState({
                                                                visibleBjdh: true,
                                                                organizationCodeData: val1.organizationCode,
                                                                relatData: val1.relat,
                                                                kindData: val1.kind ? val1.kind : null,
                                                                personInChargeIdNumberData: val1.personInChargeIdNumber ? val1.personInChargeIdNumber : null
                                                            });
                                                            this.table.qnnForm.form.setFieldsValue({
                                                                jiansuo: 'unClick'
                                                            })
                                                        } else {
                                                            Msg.warn('?????????????????????')
                                                        }
                                                    } else {//??????
                                                        if (val1.organizationCode) {//?????????????????????
                                                            this.setState({
                                                                visibleBjdh: true,
                                                                organizationCodeData: val1.organizationCode,
                                                                relatData: val1.relat,
                                                                kindData: val1.kind ? val1.kind : null,
                                                                personInChargeIdNumberData: val1.personInChargeIdNumber ? val1.personInChargeIdNumber : null
                                                            });
                                                            this.table.qnnForm.form.setFieldsValue({
                                                                jiansuo: 'unClick'
                                                            })
                                                        } else {
                                                            Msg.warn('?????????????????????????????????')
                                                        }
                                                    }
                                                }
                                            }}>??????</Button>
                                        </div>

                                    );
                                },
                                spanForm: 16,
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
                                dependencies: ['btnCompent'],
                                hide: (obj) => {
                                    if (obj.form.getFieldValue('btnCompent') === 'detail' || obj.form.getFieldValue('btnCompent') === 'edit') {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'component',
                                field: 'diySK_r',
                                Component: obj => {
                                    return (
                                        <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                            ??????????????????
                                        </div>
                                    );
                                },
                                dependencies: ['btnCompent'],
                                hide: (obj) => {
                                    if (obj.form.getFieldValue('btnCompent') === 'detail' || obj.form.getFieldValue('btnCompent') === 'edit') {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "jiansuo",
                                hide: true,
                                field: 'jiansuo',
                                type: 'string',
                                initialValue: (obj) => {
                                    if (obj.Pstate.drawerDetailTitle === '??????') {
                                        return 'click'
                                    } else if (obj.Pstate.drawerDetailTitle === '??????') {
                                        return 'unClick'
                                    } else {
                                        return 'click'
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "btnCompent",
                                hide: true,
                                field: 'btnCompent',
                                type: 'string',
                                initialValue: (obj) => {
                                    if (obj.Pstate.drawerDetailTitle === '??????') {
                                        return 'detail'
                                    } else if (obj.Pstate.drawerDetailTitle === '??????') {
                                        return 'edit'
                                    } else {
                                        return 'add'
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "changeRelat",
                                hide: true,
                                field: 'changeRelat',
                                type: 'string',
                                initialValue: (obj) => {
                                    return 'unchange'
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
                            }
                        },
                        // {
                        //     isInTable: false,
                        //     form: {
                        //         label: "????????????(??????)??????",
                        //         field: 'customerName',
                        //         // required: true,
                        //         type: 'string',
                        //         spanForm: 8,
                        //         formItemLayoutForm: {
                        //             labelCol: {
                        //                 xs: { span: 24 },
                        //                 sm: { span: 10 }
                        //             },
                        //             wrapperCol: {
                        //                 xs: { span: 24 },
                        //                 sm: { span: 14 }
                        //             }
                        //         },
                        //         condition: [
                        //             {
                        //                 regex: {
                        //                     changeRelat: 'change',
                        //                 },
                        //                 action: 'hide'
                        //             },
                        //             {
                        //                 regex: { jiansuo: 'click', },
                        //                 action: 'disabled'
                        //             },

                        //         ],
                        //     }
                        // },
                        {
                            isInTable: false,
                            form: {
                                label: "?????????????????????",
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
                                condition: [
                                    {
                                        regex: { relat: '0', },
                                        action: 'hide'
                                    },
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },

                                ],
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????(??????)??????",
                                // required: true,
                                field: 'searchTheCustomerName',//?????????????????????????????????????????????????????????
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
                                required:true,
                                condition: [
                                    {
                                        regex: { relat: '1', },
                                        action: 'hide'
                                    },
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },

                                ],
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                // required: true,
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "???????????????",
                                // required: true,
                                field: 'registeredCapital',
                                type: 'number',
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
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
                                },
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????????????????",
                                field: 'pricinpalAddr',
                                type: 'textarea',
                                spanForm: 24,
                                autoSize: {
                                    minRows: 1,
                                    maxRows: 3
                                },
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                },
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'component',
                                field: 'diySK_3',
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????",
                                field: 'linkmanTel',
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????",
                                field: 'fixedLineTelephoneOfMerchants',
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'component',
                                field: 'abc',
                                Component: (obj) => {
                                    return <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                        ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                                </div>
                                }
                            },
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
                                },
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
                                condition: [
                                    {
                                        regex: { jiansuo: 'click', },
                                        action: 'disabled'
                                    },
                                ],

                            }
                        }
                    ]}
                    actionBtns={[
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '??????',
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '??????',
                                },
                                {
                                    name: 'diysubmit',
                                    type: 'primary',
                                    label: '??????',
                                    field: 'addsubmit',
                                    onClick: (val) => {
                                        const { myFetch } = this.props;
                                        val._formData.customerName = val._formData.searchTheCustomerName;
                                        myFetch('addZxCrCustomerExtAttr', val._formData).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.table.refresh();
                                                    this.table.closeDrawer();
                                                    this.table.clearSelectedRows();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',//??????add del
                            icon: 'edit',//icon
                            type: 'primary',//??????  ?????? primary  [primary dashed danger]
                            label: '??????',
                            formBtns: [
                                {
                                    name: 'cancel', //??????????????????
                                    type: 'dashed',//??????  ?????? primary
                                    label: '??????',
                                },
                                {
                                    name: 'submitEdit',
                                    type: 'primary',
                                    label: '??????',
                                    onClick: (val) => {
                                        const { myFetch } = this.props;
                                        val._formData.customerName = val._formData.searchTheCustomerName;
                                        myFetch('updateZxCrCustomerExtAttr', val._formData).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.table.refresh();
                                                    this.table.closeDrawer();
                                                    this.table.clearSelectedRows();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
                                    }
                                }
                            ]
                        },
                        // ??????
                        {
                            name: 'del',
                            icon: 'delete',
                            type: 'danger',
                            label: '??????',
                            field: "del",
                            fetchConfig: {
                                apiName: 'batchDeleteUpdateZxCrCustomerExtAttr',
                            },
                        },
                    ]}
                />
                <Modal
                    width='1200px'
                    style={{ top: '0' }}
                    title="????????????"
                    visible={visibleBjdh}
                    footer={null}
                    onCancel={this.handleCancelOther}
                    bodyStyle={{ width: '1200px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelOther}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingBjdh}>
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.tableBh = me;
                            }}
                            {...configBh}
                            antd={{
                                rowKey: 'zxCrCustomerExtAttrId',
                                size: 'small'
                            }}
                            fetchConfig={() => {
                                if (this.state.relatData === '0') {
                                    return {
                                        apiName: 'getZxCrCustomerExtAttrAll',
                                        otherParams: {
                                            organizationCode: this.state.organizationCodeData,
                                            // entryName: '???????????????',
                                            relat: this.state.relatData ? this.state.relatData : null,
                                            kind: this.state.kindData ? this.state.kindData : null,
                                            personInChargeIdNumber: this.state.personInChargeIdNumberData ? this.state.personInChargeIdNumberData : null
                                        }
                                    }
                                } else if (this.state.relatData === '1') {
                                    return {
                                        apiName: 'getZxCrCustomerExtAttrDetailRetrieval',
                                        otherParams: {
                                            searchTheCustomerName: this.state.searchTheCustomerNameData,
                                            // entryName: '???????????????',
                                            relat: this.state.relatData ? this.state.relatData : null,
                                            kind: this.state.kindData ? this.state.kindData : null,
                                            personInChargeIdNumber: this.state.personInChargeIdNumberData ? this.state.personInChargeIdNumberData : null
                                        }
                                    }
                                } else {
                                    return {}
                                }

                            }}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'zxCrCustomerExtAttrId',//???
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'entryName',
                                        width: 400,
                                        key: 'entryName'
                                    },
                                    isInForm: false
                                },
                                {
                                    isInTable: this.state.relatData === '1' ? true : false,
                                    table: {
                                        title: '?????????????????????',
                                        width: 400,
                                        dataIndex: 'organizationCode',
                                        key: 'organizationCode'
                                    },
                                    isInForm: false
                                },
                                {
                                    isInTable: this.state.relatData === '1' ? false : true,
                                    table: {
                                        title: '????????????(??????)??????',
                                        width: 400,
                                        dataIndex: 'searchTheCustomerName',
                                        key: 'searchTheCustomerName'
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        width: 300,
                                        dataIndex: 'type',
                                        key: 'type',
                                        type: 'select'
                                    },
                                    isInForm: false,
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //?????? label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'keShangLeiXing'
                                            }
                                        },
                                    }
                                }
                            ]}
                            actionBtns={[
                                {
                                    name: 'diy',
                                    type: 'dashed',
                                    label: '??????',
                                    onClick: () => {
                                        this.setState({
                                            loadingBjdh: false,
                                            visibleBjdh: false
                                        })
                                    }
                                },
                                {
                                    name: 'duysubmit',
                                    type: 'primary',
                                    label: '??????',
                                    disabled: "bind:_actionBtnNoSelected",
                                    onClick: (obj) => {
                                        if (obj.selectedRows.length === 1) {
                                            this.table.qnnForm.form.setFieldsValue({
                                                ...obj.selectedRows[0],
                                                visibleBjdh: false,
                                                loadingBjdh: false
                                            })
                                            this.setState({
                                                loadingBjdh: false,
                                                visibleBjdh: false
                                            })
                                        } else {
                                            Msg.warn('????????????????????????')
                                        }

                                    }
                                }
                            ]}
                        />
                    </Spin>
                </Modal>
            </div>
        );
    }
}

export default index;