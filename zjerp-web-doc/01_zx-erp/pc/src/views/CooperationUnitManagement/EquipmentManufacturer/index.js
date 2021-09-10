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
                            type: 'eqf',
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
                                title: '编号',
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
                                        value: "1",
                                    },
                                    {
                                        label: "个人",
                                        value: "0",
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
                                field: 'diySK1',
                                Component: obj => {
                                    return (
                                        <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                            客商信息
                                        </div>
                                    );
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "是否内部单位",
                                field: 'relat',
                                type: 'radio',
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
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "性质",
                                field: 'kind',
                                type: 'select',
                                optionData: [
                                    {
                                        label: "单位",
                                        value: "1",
                                    },
                                    {
                                        label: "个人",
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
                                required: true,
                                field: 'organizationCode',//是否根据“是否内部单位”，控制显示隐藏
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
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "注册单位(个人)名称",
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
                                label: "法人代表",
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
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "注册资本金",
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
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "法人单位通讯地址",
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
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "手机",
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
                                label: "固话",
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
                            }
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