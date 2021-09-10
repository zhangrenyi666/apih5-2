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
            personInChargeIdNumberData: ''
        }
    }
    componentDidMount() { }
    handleCancelBjdh = () => {
        this.setState({ loadingBjdh: false, visibleBjdh: false });
    }
    render() {
        const { companyId, companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { visibleBjdh, loadingBjdh } = this.state;
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
                            projectId: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId
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
                                initialValue:companyId,
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'companyName',
                                type: 'string',
                                initialValue:companyName,
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'projectId',
                                type: 'string',
                                initialValue:this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId,
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'projectName',
                                type: 'string',
                                initialValue:this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentName,
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '编号',
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
                                title: '注册单位(个人)名称',
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
                                title: '性质',
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
                                        label: "单位",
                                        value: "0",
                                    },
                                    {
                                        label: "个人",
                                        value: "1",
                                    }
                                ],
                            }
                        },
                        {
                            table: {
                                title: '内部单位',
                                dataIndex: 'relat',
                                key: 'relat',
                                type: 'select',
                            },
                            isInForm: false,
                            form: {
                                type: 'select',
                                optionData: [
                                    {
                                        label: "否",
                                        value: "0",
                                    },
                                    {
                                        label: "是",
                                        value: "1",
                                    }
                                ],
                            }
                        },
                        {
                            table: {
                                title: '联系人',
                                dataIndex: 'linkmanName',
                                key: 'linkmanName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '联系电话',
                                dataIndex: 'linkmanTel',
                                key: 'linkmanTel'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '客商信誉',
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
                                        label: "优",
                                        value: "004",
                                    },
                                    {
                                        label: "良",
                                        value: "003",
                                    },
                                    {
                                        label: "好",
                                        value: "002",
                                    },
                                    {
                                        label: "不合格",
                                        value: "001",//数据库没查到，先给的001
                                    }
                                ],
                            }
                        },
                        {
                            table: {
                                title: '法人单位联系电话',
                                width: 160,
                                dataIndex: 'pricinpalTel',
                                key: 'pricinpalTel'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '法人单位传真',
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
                                            客商标记信息
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
                                            客商基本信息
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
                                label: "是否内部单位",
                                field: 'relat',
                                type: 'radio',
                                editDisabled: true,
                                initialValue: '0',
                                optionData: [
                                    {
                                        label: "否",
                                        value: "0",
                                    },
                                    {
                                        label: "是",
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
                                        // 清空表单
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
                                label: "性质",
                                field: 'kind',
                                initialValue: '0',
                                editDisabled: true,
                                required: true,
                                type: 'select',
                                optionData: [
                                    {
                                        label: "单位",
                                        value: "0",
                                    },
                                    {
                                        label: "个人",
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
                                        // 清空表单
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
                                label: "负责人身份证",
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
                                label: "组织机构代码证",
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
                                    if (obj.form.getFieldValue('relat') === '0') {//组织机构代码证-显示
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
                                label: "注册单位(个人)名称",
                                field: 'searchTheCustomerName',//是否根据“是否内部单位”，控制显示隐藏
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
                                    if (obj.form.getFieldValue('relat') === '1') {//注册单位(个人)名称-显示
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
                                                // 提示文字-需要判断
                                                let val1 = this.table.qnnForm.form.getFieldsValue();
                                                if (val1.relat === '1') {//内部单位-是
                                                    if (val1.kind === '1') {//个人
                                                        // if (val1.personInChargeIdNumber) {//身份证--问题编号000003131
                                                            if (val1.searchTheCustomerName) {//注册单位个人信息
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
                                                                Msg.warn('请输入注册单位(个人)名称！')
                                                            }
                                                        // } else {
                                                        //     Msg.warn('请填写身份证！')
                                                        // }
                                                    } else {//单位
                                                        if (val1.searchTheCustomerName) {//注册单位信息
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
                                                            Msg.warn('请输入注册单位(个人)名称！')
                                                        }
                                                    }
                                                } else {
                                                    if (val1.kind === '1') {//个人
                                                        if (val1.personInChargeIdNumber) {//身份证
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
                                                            Msg.warn('请填写身份证！')
                                                        }
                                                    } else {//单位
                                                        if (val1.organizationCode) {//组织机构代码证
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
                                                            Msg.warn('请输入组织机构代码证！')
                                                        }
                                                    }
                                                }
                                            }}>检索</Button>
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
                                            客商基本信息
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
                                    if (obj.Pstate.drawerDetailTitle === '详情') {
                                        return 'click'
                                    } else if (obj.Pstate.drawerDetailTitle === '编辑') {
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
                                    if (obj.Pstate.drawerDetailTitle === '详情') {
                                        return 'detail'
                                    } else if (obj.Pstate.drawerDetailTitle === '编辑') {
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
                                label: "编号",
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
                        //         label: "注册单位(个人)名称",
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
                                label: "组织机构代码证",
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
                                label: "注册单位(个人)名称",
                                // required: true,
                                field: 'searchTheCustomerName',//是否根据“是否内部单位”，控制显示隐藏
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
                                label: "法人代表",
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
                                label: "法人委托人",
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
                                label: "单位注册时间",
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
                                label: "单位注册地区",
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
                                label: "营业执照编号",
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
                                label: "注册资本金",
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
                                label: "资质证书编号",
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
                                label: "资质范围等级",
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
                                label: "安全生产许可证编号",
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
                                label: "经营范围",
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
                                label: "客商信誉",
                                field: 'creditStanding',
                                type: 'select',
                                optionData: [
                                    {
                                        label: "优",
                                        value: "004",
                                    },
                                    {
                                        label: "良",
                                        value: "003",
                                    },
                                    {
                                        label: "好",
                                        value: "002",
                                    },
                                    {
                                        label: "不合格",
                                        value: "001",//数据库没查到，先给的001
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
                                label: "法人单位邮编",
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
                                label: "法人单位手机",
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
                                label: "法人单位联系电话",
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
                                label: "法人单位传真",
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
                                label: "公司类型",
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
                                label: "局内协作经历",
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
                                label: "正在服务项目",
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
                                label: "法人单位通讯地址",
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
                                            客商联系人信息
                                        </div>
                                    );
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "姓名",
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
                                label: "手机",
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
                                label: "固话",
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
                                label: "传真",
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
                                        提示：请上传合同当事人身份证、营业执照、法人授权委托书、资质证书、安全生产许可证、施工经历及评价等资料
                                </div>
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '附件',
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
                            label: '新增',
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'diysubmit',
                                    type: 'primary',
                                    label: '保存',
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
                            name: 'edit',//内置add del
                            icon: 'edit',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '修改',
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                },
                                {
                                    name: 'submitEdit',
                                    type: 'primary',
                                    label: '保存',
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
                        // 删除
                        {
                            name: 'del',
                            icon: 'delete',
                            type: 'danger',
                            label: '删除',
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
                    title="选择客商"
                    visible={visibleBjdh}
                    footer={null}
                    onCancel={this.handleCancelBjdh}
                    bodyStyle={{ width: '1200px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelBjdh}
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
                                            // entryName: '建筑分公司',
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
                                            // entryName: '建筑分公司',
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
                                        title: '隶属机构',
                                        dataIndex: 'entryName',
                                        key: 'entryName'
                                    },
                                    isInForm: false
                                },
                                {
                                    isInTable: this.state.relatData === '1' ? true : false,
                                    table: {
                                        title: '组织机构代码证',
                                        dataIndex: 'organizationCode',
                                        key: 'organizationCode'
                                    },
                                    isInForm: false
                                },
                                {
                                    isInTable: this.state.relatData === '1' ? false : true,
                                    table: {
                                        title: '注册单位(个人)名称',
                                        dataIndex: 'searchTheCustomerName',
                                        key: 'searchTheCustomerName'
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '客商类型',
                                        dataIndex: 'type',
                                        key: 'type',
                                        type: 'select'
                                    },
                                    isInForm: false,
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
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
                                    label: '取消',
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
                                    label: '保存',
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
                                            Msg.warn('请选择一条数据！')
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