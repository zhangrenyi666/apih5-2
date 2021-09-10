import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Modal } from 'antd';
import { push } from "react-router-redux";
const config = {
    antd: {
        rowKey: 'socialSecurityManagementDetailId',
        size: 'small'
    },
    drawerConfig: {
        width: '900px'
    },
    isShowRowSelect: true,
    paginationConfig: {
        position: 'bottom'
    },
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visibleBjdh: false
        }
    }
    handleCancelChange = () => {
        this.setState({ visibleBjdh: false });
    }
    render() {
        const { clickNodeId, clickNodeName, tabId, tabName, zhujiandetail, dod } = this.props;//父子组件
        const { visibleBjdh } = this.state;
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.tableOne = me;
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZjXmSalarySocialSecurityManagementDetailList',
                        otherParams: {
                            wageOfProjectId: clickNodeId,
                            socialSecurityManagementId: zhujiandetail,//外层主键
                            socialSecurityType: tabId
                        }
                    }}
                    formItemLayout={{
                        labelCol: {
                            xs: { span: 4 },
                            sm: { span: 3 }
                        },
                        wrapperCol: {
                            xs: { span: 20 },
                            sm: { span: 21 }
                        }
                    }}
                    actionBtns={[
                        {
                            name: 'goback',
                            type: 'dashed',
                            label: '返回',
                            isValidate: false,
                            onClick: (obj) => {
                                const { mainModule } = this.props.myPublic.appInfo;
                                this.props.dispatch(
                                    push(`${mainModule}SocialSecurityMaintenance`)
                                )
                            }
                        },
                        {
                            name: 'diy',
                            type: 'primary',
                            label: '导入',
                            onClick: () => {
                                this.setState({
                                    visibleBjdh: true
                                })
                            },
                            disabled: () => {
                                return dod === 'can' ? false : true
                            }
                        },
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
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    field: 'addsubmit',
                                    fetchConfig: {
                                        apiName: 'addZjXmSalarySocialSecurityManagementDetail'
                                    }
                                }
                            ],
                            disabled: () => {
                                return dod === 'can' ? false : true
                            }
                        },
                        {
                            name: 'del',
                            icon: 'del',
                            type: 'danger',
                            label: '删除',
                            fetchConfig: {
                                apiName: 'batchDeleteUpdateZjXmSalarySocialSecurityManagementDetail'
                            },
                            disabled: () => {
                                return dod === 'can' ? false : true
                            }
                        },
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'socialSecurityManagementDetailId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '姓名',
                                dataIndex: 'name',
                                key: 'name',
                                width: 200,
                                tooltip: 20,
                                align: 'center',
                            },
                            form: {
                                label: '',
                                field: 'name',
                                required: true,
                                type: 'selectByQnnTable',
                                optionConfig: {
                                    value: 'realName',
                                    label: "realName"
                                },
                                dropdownMatchSelectWidth: 550,
                                qnnTableConfig: {
                                    antd: { rowKey: "wageOfProjectId" },
                                    fetchConfig: {
                                        apiName: "getZjXmSalarySocialSecurityManagementDetailListAdd",
                                        otherParams: {
                                            wageOfProjectId: clickNodeId
                                        }
                                    },
                                    formConfig: [
                                        {
                                            isInForm: false,
                                            isInSearch: true,
                                            table: {
                                                dataIndex: "realName",
                                                title: "姓名",
                                                width: 150,
                                                filter: true
                                            },
                                            form: { type: 'string' }
                                        },
                                        {
                                            isInForm: false,
                                            isInSearch: true,
                                            table: {
                                                dataIndex: "idNumber",
                                                title: "身份证号",
                                                width: 220,
                                                filter: true
                                            },
                                            form: { type: 'string' }
                                        },
                                        {
                                            isInForm: false,
                                            isInSearch: true,
                                            table: {
                                                dataIndex: "userTypeName",
                                                title: "发放范围",
                                                width: 100
                                            },
                                            form: { type: 'string' }
                                        }
                                    ]
                                },
                                placeholder: '请选择',
                                onChange: (obj, rowData) => {
                                    if (obj) {
                                        // 校验一下
                                        let params = {
                                            extensionId: rowData.itemData.extensionId,
                                            socialSecurityManagementId: zhujiandetail
                                        };
                                        this.props.myFetch('checkAddZjXmSalarySocialSecurityManagementDetail', params).then(({ data, success, message }) => {
                                            if (success) {
                                                if (data?.check === false) {
                                                    Msg.warn(data.message);
                                                    rowData.form.setFieldsValue({
                                                        idNum: null,
                                                        issueScopeName: null,
                                                        issueScope: null,
                                                        extensionId: null,
                                                        userKey: null,
                                                        deptId: null,
                                                        deptName: null,
                                                        // companyPensionInsurancePayable: null,
                                                        // personalProvidentFundPayable: null,
                                                        total: null
                                                    })
                                                } else {
                                                    rowData.form.setFieldsValue({
                                                        idNum: rowData.itemData.idNumber,
                                                        issueScopeName: rowData.itemData.userTypeName,
                                                        issueScope: rowData.itemData.userType,
                                                        extensionId: rowData.itemData.extensionId,
                                                        userKey: rowData.itemData.userKey,
                                                        // deptId: rowData.itemData.departmentId,//???
                                                        // deptId: rowData.itemData.wageOfProjectId,//???
                                                        wageOfProjectId: rowData.itemData.wageOfProjectId,
                                                        deptName: rowData.itemData.departmentName
                                                    })
                                                }
                                            } else {
                                                Msg.error(message)
                                            }
                                        })

                                    } else {
                                        rowData.form.setFieldsValue({
                                            idNum: null,
                                            issueScopeName: null
                                        })
                                    }

                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'wageOfProjectId',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'deptName',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'userKey',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'extensionId',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'issueScope',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'socialSecurityManagementId',
                                initialValue: zhujiandetail,
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'socialSecurityType',
                                initialValue: tabId,
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'socialSecurityTypeName',
                                initialValue: tabName,
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '身份证号',
                                width: 180,
                                tooltip: 18,
                                dataIndex: 'idNum',
                                key: 'idNum',
                                align: 'center',
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                                addDisabled: true,
                                editDisabled: true
                            }
                        },
                        {
                            table: {
                                title: '发放范围',
                                dataIndex: 'issueScopeName',
                                key: 'issueScopeName',
                                width: 100,
                                align: 'center',
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                                addDisabled: true,
                                editDisabled: true
                            }
                        },
                        {
                            table: {
                                title: '养老保险费',
                                children: [
                                    {
                                        title: '单位应缴',
                                        dataIndex: 'companyPensionInsurancePayable',
                                        key: 'companyPensionInsurancePayable',
                                    },
                                    {
                                        title: '个人应缴',
                                        dataIndex: 'personalPensionInsurancePayable',
                                        key: 'personalPensionInsurancePayable',
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'component',
                                field: 'diyJE1',
                                Component: obj => {
                                    return (
                                        <div style={{ width: "100%", backgroundColor: '#fafafa', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                            养老保险费
                                        </div>
                                    );
                                }
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '单位应缴',
                                dataIndex: 'companyPensionInsurancePayable',
                                key: 'companyPensionInsurancePayable',
                            },
                            form: {
                                type: 'number',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 4 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 18 }
                                    }
                                },
                                required: true,
                                onBlur: (val, obj) => {
                                    let allValue = obj.form.getFieldsValue();
                                    // const personal1 = allValue.companyPensionInsurancePayable ? allValue.companyPensionInsurancePayable : 0;//d-y
                                    const personal2 = allValue.personalPensionInsurancePayable ? allValue.personalPensionInsurancePayable : 0;//g-y
                                    const personal3 = allValue.companyUnemploymentInsurancePayable ? allValue.companyUnemploymentInsurancePayable : 0;//d-s
                                    const personal4 = allValue.personalUnemploymentInsurancePayable ? allValue.personalUnemploymentInsurancePayable : 0;//g-s
                                    const personal5 = allValue.companyMedicalInsurancePayable ? allValue.companyMedicalInsurancePayable : 0;//d-yl
                                    const personal6 = allValue.personalMedicalInsurancePayable ? allValue.personalMedicalInsurancePayable : 0;//g-yl
                                    const personal7 = allValue.companyEnterpriseAnnuityPayable ? allValue.companyEnterpriseAnnuityPayable : 0;//d-q
                                    const personal8 = allValue.personalEnterpriseAnnuityPayable ? allValue.personalEnterpriseAnnuityPayable : 0;//g-q
                                    const personal9 = allValue.companyIIndustrialInjuryPayable ? allValue.companyIIndustrialInjuryPayable : 0;//gs
                                    const personal10 = allValue.companyChildBearingTaxPayable ? allValue.companyChildBearingTaxPayable : 0;//sy

                                    let lenData = personal2 + personal3 + personal4 + personal5 + personal6 + personal7 + personal8 + personal9 + personal10 + obj.value;

                                    let lenDataPersonal = personal2 + personal4 + personal6 + personal8;//个人总计

                                    let lenDataCompany = personal3 + personal5 + personal7 + personal9 + personal10 + obj.value;//单位总计

                                    obj.form.setFieldsValue({
                                        total: Number((lenData).toFixed(2)),
                                        companyTotal: Number((lenDataCompany).toFixed(2)),//单位总计
                                        personalTotal: Number((lenDataPersonal).toFixed(2)),//个人总计
                                    })
                                }
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '个人应缴',
                                dataIndex: 'personalPensionInsurancePayable',
                                key: 'personalPensionInsurancePayable',
                            },
                            form: {
                                type: 'number',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 4 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 18 }
                                    }
                                },
                                required: true,
                                onBlur: (val, obj) => {
                                    let allValue = obj.form.getFieldsValue();
                                    const personal1 = allValue.companyPensionInsurancePayable ? allValue.companyPensionInsurancePayable : 0;//d-y
                                    // const personal2 = allValue.personalPensionInsurancePayable ? allValue.personalPensionInsurancePayable : 0;//g-y
                                    const personal3 = allValue.companyUnemploymentInsurancePayable ? allValue.companyUnemploymentInsurancePayable : 0;//d-s
                                    const personal4 = allValue.personalUnemploymentInsurancePayable ? allValue.personalUnemploymentInsurancePayable : 0;//g-s
                                    const personal5 = allValue.companyMedicalInsurancePayable ? allValue.companyMedicalInsurancePayable : 0;//d-yl
                                    const personal6 = allValue.personalMedicalInsurancePayable ? allValue.personalMedicalInsurancePayable : 0;//g-yl
                                    const personal7 = allValue.companyEnterpriseAnnuityPayable ? allValue.companyEnterpriseAnnuityPayable : 0;//d-q
                                    const personal8 = allValue.personalEnterpriseAnnuityPayable ? allValue.personalEnterpriseAnnuityPayable : 0;//g-q
                                    const personal9 = allValue.companyIIndustrialInjuryPayable ? allValue.companyIIndustrialInjuryPayable : 0;//gs
                                    const personal10 = allValue.companyChildBearingTaxPayable ? allValue.companyChildBearingTaxPayable : 0;//sy

                                    let lenDataPersonal = obj.value + personal4 + personal6 + personal8;//个人总计

                                    let lenDataCompany = personal3 + personal5 + personal7 + personal9 + personal10 + personal1;//单位总计

                                    obj.form.setFieldsValue({
                                        total: Number((personal1 + personal3 + personal4 + personal5 + personal6 + personal7 + personal8 + personal9 + personal10 + obj.value).toFixed(2)),
                                        companyTotal: Number((lenDataCompany).toFixed(2)),//单位总计
                                        personalTotal: Number((lenDataPersonal).toFixed(2)),//个人总计
                                    })
                                }
                            }
                        },
                        {
                            table: {
                                title: '失业保险费',
                                children: [
                                    {
                                        title: '单位应缴',
                                        dataIndex: 'companyUnemploymentInsurancePayable',
                                        key: 'companyUnemploymentInsurancePayable',
                                    },
                                    {
                                        title: '个人应缴',
                                        dataIndex: 'personalUnemploymentInsurancePayable',
                                        key: 'personalUnemploymentInsurancePayable',
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'component',
                                field: 'diyJE7',
                                Component: obj => {
                                    return (
                                        <div style={{ width: "100%", backgroundColor: '#fafafa', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                            失业保险费
                                        </div>
                                    );
                                }
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '单位应缴',
                                dataIndex: 'companyUnemploymentInsurancePayable',
                                key: 'companyUnemploymentInsurancePayable',
                            },
                            form: {
                                type: 'number',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 4 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 18 }
                                    }
                                },
                                required: true,
                                onBlur: (val, obj) => {
                                    let allValue = obj.form.getFieldsValue();
                                    const personal1 = allValue.companyPensionInsurancePayable ? allValue.companyPensionInsurancePayable : 0;//d-y
                                    const personal2 = allValue.personalPensionInsurancePayable ? allValue.personalPensionInsurancePayable : 0;//g-y
                                    // const personal3 = allValue.companyUnemploymentInsurancePayable ? allValue.companyUnemploymentInsurancePayable : 0;//d-s
                                    const personal4 = allValue.personalUnemploymentInsurancePayable ? allValue.personalUnemploymentInsurancePayable : 0;//g-s
                                    const personal5 = allValue.companyMedicalInsurancePayable ? allValue.companyMedicalInsurancePayable : 0;//d-yl
                                    const personal6 = allValue.personalMedicalInsurancePayable ? allValue.personalMedicalInsurancePayable : 0;//g-yl
                                    const personal7 = allValue.companyEnterpriseAnnuityPayable ? allValue.companyEnterpriseAnnuityPayable : 0;//d-q
                                    const personal8 = allValue.personalEnterpriseAnnuityPayable ? allValue.personalEnterpriseAnnuityPayable : 0;//g-q
                                    const personal9 = allValue.companyIIndustrialInjuryPayable ? allValue.companyIIndustrialInjuryPayable : 0;//gs
                                    const personal10 = allValue.companyChildBearingTaxPayable ? allValue.companyChildBearingTaxPayable : 0;//sy

                                    let lenDataPersonal = personal2 + personal4 + personal6 + personal8;//个人总计

                                    let lenDataCompany = obj.value + personal5 + personal7 + personal9 + personal10 + personal1;//单位总计

                                    obj.form.setFieldsValue({
                                        total: Number((personal2 + personal1 + personal4 + personal5 + personal6 + personal7 + personal8 + personal9 + personal10 + obj.value).toFixed(2)),
                                        companyTotal: Number((lenDataCompany).toFixed(2)),//单位总计
                                        personalTotal: Number((lenDataPersonal).toFixed(2)),//个人总计
                                    })
                                }
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '个人应缴',
                                dataIndex: 'personalUnemploymentInsurancePayable',
                                key: 'personalUnemploymentInsurancePayable',
                            },
                            form: {
                                type: 'number',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 4 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 18 }
                                    }
                                },
                                required: true,
                                onBlur: (val, obj) => {
                                    let allValue = obj.form.getFieldsValue();
                                    const personal1 = allValue.companyPensionInsurancePayable ? allValue.companyPensionInsurancePayable : 0;//d-y
                                    const personal2 = allValue.personalPensionInsurancePayable ? allValue.personalPensionInsurancePayable : 0;//g-y
                                    const personal3 = allValue.companyUnemploymentInsurancePayable ? allValue.companyUnemploymentInsurancePayable : 0;//d-s
                                    // const personal4 = allValue.personalUnemploymentInsurancePayable ? allValue.personalUnemploymentInsurancePayable : 0;//g-s
                                    const personal5 = allValue.companyMedicalInsurancePayable ? allValue.companyMedicalInsurancePayable : 0;//d-yl
                                    const personal6 = allValue.personalMedicalInsurancePayable ? allValue.personalMedicalInsurancePayable : 0;//g-yl
                                    const personal7 = allValue.companyEnterpriseAnnuityPayable ? allValue.companyEnterpriseAnnuityPayable : 0;//d-q
                                    const personal8 = allValue.personalEnterpriseAnnuityPayable ? allValue.personalEnterpriseAnnuityPayable : 0;//g-q
                                    const personal9 = allValue.companyIIndustrialInjuryPayable ? allValue.companyIIndustrialInjuryPayable : 0;//gs
                                    const personal10 = allValue.companyChildBearingTaxPayable ? allValue.companyChildBearingTaxPayable : 0;//sy

                                    let lenDataPersonal = personal2 + obj.value + personal6 + personal8;//个人总计

                                    let lenDataCompany = personal3 + personal5 + personal7 + personal9 + personal10 + personal1;//单位总计

                                    obj.form.setFieldsValue({
                                        total: Number((personal2 + personal1 + personal3 + personal5 + personal6 + personal7 + personal8 + personal9 + personal10 + obj.value).toFixed(2)),
                                        companyTotal: Number((lenDataCompany).toFixed(2)),//单位总计
                                        personalTotal: Number((lenDataPersonal).toFixed(2)),//个人总计
                                    })
                                }
                            }
                        },
                        {
                            table: {
                                title: '医疗保险费',
                                children: [
                                    {
                                        title: '单位应缴',
                                        dataIndex: 'companyMedicalInsurancePayable',
                                        key: 'companyMedicalInsurancePayable',
                                    },
                                    {
                                        title: '个人应缴',
                                        dataIndex: 'personalMedicalInsurancePayable',
                                        key: 'personalMedicalInsurancePayable',
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'component',
                                field: 'diyJE6',
                                Component: obj => {
                                    return (
                                        <div style={{ width: "100%", backgroundColor: '#fafafa', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                            医疗保险费
                                        </div>
                                    );
                                }
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '单位应缴',
                                dataIndex: 'companyMedicalInsurancePayable',
                                key: 'companyMedicalInsurancePayable',
                            },
                            form: {
                                type: 'number',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 4 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 18 }
                                    }
                                },
                                required: true,
                                onBlur: (val, obj) => {
                                    let allValue = obj.form.getFieldsValue();
                                    const personal1 = allValue.companyPensionInsurancePayable ? allValue.companyPensionInsurancePayable : 0;//d-y
                                    const personal2 = allValue.personalPensionInsurancePayable ? allValue.personalPensionInsurancePayable : 0;//g-y
                                    const personal3 = allValue.companyUnemploymentInsurancePayable ? allValue.companyUnemploymentInsurancePayable : 0;//d-s
                                    const personal4 = allValue.personalUnemploymentInsurancePayable ? allValue.personalUnemploymentInsurancePayable : 0;//g-s
                                    // const personal5 = allValue.companyMedicalInsurancePayable ? allValue.companyMedicalInsurancePayable : 0;//d-yl
                                    const personal6 = allValue.personalMedicalInsurancePayable ? allValue.personalMedicalInsurancePayable : 0;//g-yl
                                    const personal7 = allValue.companyEnterpriseAnnuityPayable ? allValue.companyEnterpriseAnnuityPayable : 0;//d-q
                                    const personal8 = allValue.personalEnterpriseAnnuityPayable ? allValue.personalEnterpriseAnnuityPayable : 0;//g-q
                                    const personal9 = allValue.companyIIndustrialInjuryPayable ? allValue.companyIIndustrialInjuryPayable : 0;//gs
                                    const personal10 = allValue.companyChildBearingTaxPayable ? allValue.companyChildBearingTaxPayable : 0;//sy

                                    let lenDataPersonal = personal2 + personal4 + personal6 + personal8;//个人总计

                                    let lenDataCompany = personal3 + obj.value + personal7 + personal9 + personal10 + personal1;//单位总计

                                    obj.form.setFieldsValue({
                                        total: Number((personal2 + personal1 + personal3 + personal4 + personal6 + personal7 + personal8 + personal9 + personal10 + obj.value).toFixed(2)),
                                        companyTotal: Number((lenDataCompany).toFixed(2)),//单位总计
                                        personalTotal: Number((lenDataPersonal).toFixed(2)),//个人总计
                                    })
                                }
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '个人应缴',
                                dataIndex: 'personalMedicalInsurancePayable',
                                key: 'personalMedicalInsurancePayable',
                            },
                            form: {
                                type: 'number',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 4 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 18 }
                                    }
                                },
                                required: true,
                                onBlur: (val, obj) => {
                                    let allValue = obj.form.getFieldsValue();
                                    const personal1 = allValue.companyPensionInsurancePayable ? allValue.companyPensionInsurancePayable : 0;//d-y
                                    const personal2 = allValue.personalPensionInsurancePayable ? allValue.personalPensionInsurancePayable : 0;//g-y
                                    const personal3 = allValue.companyUnemploymentInsurancePayable ? allValue.companyUnemploymentInsurancePayable : 0;//d-s
                                    const personal4 = allValue.personalUnemploymentInsurancePayable ? allValue.personalUnemploymentInsurancePayable : 0;//g-s
                                    const personal5 = allValue.companyMedicalInsurancePayable ? allValue.companyMedicalInsurancePayable : 0;//d-yl
                                    // const personal6 = allValue.personalMedicalInsurancePayable ? allValue.personalMedicalInsurancePayable : 0;//g-yl
                                    const personal7 = allValue.companyEnterpriseAnnuityPayable ? allValue.companyEnterpriseAnnuityPayable : 0;//d-q
                                    const personal8 = allValue.personalEnterpriseAnnuityPayable ? allValue.personalEnterpriseAnnuityPayable : 0;//g-q
                                    const personal9 = allValue.companyIIndustrialInjuryPayable ? allValue.companyIIndustrialInjuryPayable : 0;//gs
                                    const personal10 = allValue.companyChildBearingTaxPayable ? allValue.companyChildBearingTaxPayable : 0;//sy

                                    let lenDataPersonal = personal2 + personal4 + obj.value + personal8;//个人总计

                                    let lenDataCompany = personal3 + personal5 + personal7 + personal9 + personal10 + personal1;//单位总计

                                    obj.form.setFieldsValue({
                                        total: Number((personal2 + personal1 + personal3 + personal5 + personal4 + personal7 + personal8 + personal9 + personal10 + obj.value).toFixed(2)),
                                        companyTotal: Number((lenDataCompany).toFixed(2)),//单位总计
                                        personalTotal: Number((lenDataPersonal).toFixed(2)),//个人总计
                                    })
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'component',
                                field: 'diyJE5',
                                Component: obj => {
                                    return (
                                        <div style={{ width: "100%", backgroundColor: '#fafafa', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                            企业年金
                                        </div>
                                    );
                                }
                            }
                        },
                        {
                            table: {
                                title: '企业年金',
                                children: [
                                    {
                                        title: '单位应缴',
                                        dataIndex: 'companyEnterpriseAnnuityPayable',
                                        key: 'companyEnterpriseAnnuityPayable',
                                    },
                                    {
                                        title: '个人应缴',
                                        dataIndex: 'personalEnterpriseAnnuityPayable',
                                        key: 'personalEnterpriseAnnuityPayable',
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '单位应缴',
                                dataIndex: 'companyEnterpriseAnnuityPayable',
                                key: 'companyEnterpriseAnnuityPayable',
                            },
                            form: {
                                type: 'number',
                                spanForm: 12,
                                required: true,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 4 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 18 }
                                    }
                                },
                                onBlur: (val, obj) => {
                                    let allValue = obj.form.getFieldsValue();
                                    const personal1 = allValue.companyPensionInsurancePayable ? allValue.companyPensionInsurancePayable : 0;//d-y
                                    const personal2 = allValue.personalPensionInsurancePayable ? allValue.personalPensionInsurancePayable : 0;//g-y
                                    const personal3 = allValue.companyUnemploymentInsurancePayable ? allValue.companyUnemploymentInsurancePayable : 0;//d-s
                                    const personal4 = allValue.personalUnemploymentInsurancePayable ? allValue.personalUnemploymentInsurancePayable : 0;//g-s
                                    const personal5 = allValue.companyMedicalInsurancePayable ? allValue.companyMedicalInsurancePayable : 0;//d-yl
                                    const personal6 = allValue.personalMedicalInsurancePayable ? allValue.personalMedicalInsurancePayable : 0;//g-yl
                                    // const personal7 = allValue.companyEnterpriseAnnuityPayable ? allValue.companyEnterpriseAnnuityPayable : 0;//d-q
                                    const personal8 = allValue.personalEnterpriseAnnuityPayable ? allValue.personalEnterpriseAnnuityPayable : 0;//g-q
                                    const personal9 = allValue.companyIIndustrialInjuryPayable ? allValue.companyIIndustrialInjuryPayable : 0;//gs
                                    const personal10 = allValue.companyChildBearingTaxPayable ? allValue.companyChildBearingTaxPayable : 0;//sy

                                    let lenDataPersonal = personal2 + personal4 + personal6 + personal8;//个人总计

                                    let lenDataCompany = personal3 + personal5 + obj.value + personal9 + personal10 + personal1;//单位总计

                                    obj.form.setFieldsValue({
                                        total: Number((personal2 + personal1 + personal3 + personal5 + personal6 + personal4 + personal8 + personal9 + personal10 + obj.value).toFixed(2)),
                                        companyTotal: Number((lenDataCompany).toFixed(2)),//单位总计
                                        personalTotal: Number((lenDataPersonal).toFixed(2)),//个人总计
                                    })
                                }
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '个人应缴',
                                dataIndex: 'personalEnterpriseAnnuityPayable',
                                key: 'personalEnterpriseAnnuityPayable',
                            },
                            form: {
                                type: 'number',
                                spanForm: 12,
                                required: true,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 4 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 18 }
                                    }
                                },
                                onBlur: (val, obj) => {
                                    let allValue = obj.form.getFieldsValue();
                                    const personal1 = allValue.companyPensionInsurancePayable ? allValue.companyPensionInsurancePayable : 0;//d-y
                                    const personal2 = allValue.personalPensionInsurancePayable ? allValue.personalPensionInsurancePayable : 0;//g-y
                                    const personal3 = allValue.companyUnemploymentInsurancePayable ? allValue.companyUnemploymentInsurancePayable : 0;//d-s
                                    const personal4 = allValue.personalUnemploymentInsurancePayable ? allValue.personalUnemploymentInsurancePayable : 0;//g-s
                                    const personal5 = allValue.companyMedicalInsurancePayable ? allValue.companyMedicalInsurancePayable : 0;//d-yl
                                    const personal6 = allValue.personalMedicalInsurancePayable ? allValue.personalMedicalInsurancePayable : 0;//g-yl
                                    const personal7 = allValue.companyEnterpriseAnnuityPayable ? allValue.companyEnterpriseAnnuityPayable : 0;//d-q
                                    // const personal8 = allValue.personalEnterpriseAnnuityPayable ? allValue.personalEnterpriseAnnuityPayable : 0;//g-q
                                    const personal9 = allValue.companyIIndustrialInjuryPayable ? allValue.companyIIndustrialInjuryPayable : 0;//gs
                                    const personal10 = allValue.companyChildBearingTaxPayable ? allValue.companyChildBearingTaxPayable : 0;//sy

                                    let lenDataPersonal = personal2 + personal4 + personal6 + obj.value;//个人总计

                                    let lenDataCompany = personal3 + personal5 + personal7 + personal9 + personal10 + personal1;//单位总计

                                    obj.form.setFieldsValue({
                                        total: Number((personal2 + personal1 + personal3 + personal5 + personal6 + personal7 + personal4 + personal9 + personal10 + obj.value).toFixed(2)),
                                        companyTotal: Number((lenDataCompany).toFixed(2)),//单位总计
                                        personalTotal: Number((lenDataPersonal).toFixed(2)),//个人总计
                                    })
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'component',
                                field: 'diyJE4',
                                Component: obj => {
                                    return (
                                        <div style={{ width: "100%", backgroundColor: '#fafafa', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                            工伤
                                        </div>
                                    );
                                }
                            }
                        },
                        {
                            table: {
                                title: '工伤',
                                children: [
                                    {
                                        title: '单位应缴',
                                        dataIndex: 'companyIIndustrialInjuryPayable',
                                        key: 'companyIIndustrialInjuryPayable',
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '单位应缴',
                                dataIndex: 'companyIIndustrialInjuryPayable',
                                key: 'companyIIndustrialInjuryPayable',
                            },
                            form: {
                                type: 'number',
                                required: true,
                                onBlur: (val, obj) => {
                                    let allValue = obj.form.getFieldsValue();
                                    const personal1 = allValue.companyPensionInsurancePayable ? allValue.companyPensionInsurancePayable : 0;//d-y
                                    const personal2 = allValue.personalPensionInsurancePayable ? allValue.personalPensionInsurancePayable : 0;//g-y
                                    const personal3 = allValue.companyUnemploymentInsurancePayable ? allValue.companyUnemploymentInsurancePayable : 0;//d-s
                                    const personal4 = allValue.personalUnemploymentInsurancePayable ? allValue.personalUnemploymentInsurancePayable : 0;//g-s
                                    const personal5 = allValue.companyMedicalInsurancePayable ? allValue.companyMedicalInsurancePayable : 0;//d-yl
                                    const personal6 = allValue.personalMedicalInsurancePayable ? allValue.personalMedicalInsurancePayable : 0;//g-yl
                                    const personal7 = allValue.companyEnterpriseAnnuityPayable ? allValue.companyEnterpriseAnnuityPayable : 0;//d-q
                                    const personal8 = allValue.personalEnterpriseAnnuityPayable ? allValue.personalEnterpriseAnnuityPayable : 0;//g-q
                                    // const personal9 = allValue.companyIIndustrialInjuryPayable ? allValue.companyIIndustrialInjuryPayable : 0;//gs
                                    const personal10 = allValue.companyChildBearingTaxPayable ? allValue.companyChildBearingTaxPayable : 0;//sy

                                    let lenDataPersonal = personal2 + personal4 + personal6 + personal8;//个人总计

                                    let lenDataCompany = personal3 + personal5 + personal7 + obj.value + personal10 + personal1;//单位总计

                                    obj.form.setFieldsValue({
                                        total: Number((personal2 + personal1 + personal3 + personal5 + personal6 + personal7 + personal8 + personal4 + personal10 + obj.value).toFixed(2)),
                                        companyTotal: Number((lenDataCompany).toFixed(2)),//单位总计
                                        personalTotal: Number((lenDataPersonal).toFixed(2)),//个人总计
                                    })
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'component',
                                field: 'diyJE3',
                                Component: obj => {
                                    return (
                                        <div style={{ width: "100%", backgroundColor: '#fafafa', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                            生育
                                        </div>
                                    );
                                }
                            }
                        },
                        {
                            table: {
                                title: '生育',
                                children: [
                                    {
                                        title: '单位应缴',
                                        dataIndex: 'companyChildBearingTaxPayable',
                                        key: 'companyChildBearingTaxPayable',
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '单位应缴',
                                dataIndex: 'companyChildBearingTaxPayable',
                                key: 'companyChildBearingTaxPayable',
                            },
                            form: {
                                type: 'number',
                                required: true,
                                onBlur: (val, obj) => {
                                    let allValue = obj.form.getFieldsValue();
                                    const personal1 = allValue.companyPensionInsurancePayable ? allValue.companyPensionInsurancePayable : 0;//d-y
                                    const personal2 = allValue.personalPensionInsurancePayable ? allValue.personalPensionInsurancePayable : 0;//g-y
                                    const personal3 = allValue.companyUnemploymentInsurancePayable ? allValue.companyUnemploymentInsurancePayable : 0;//d-s
                                    const personal4 = allValue.personalUnemploymentInsurancePayable ? allValue.personalUnemploymentInsurancePayable : 0;//g-s
                                    const personal5 = allValue.companyMedicalInsurancePayable ? allValue.companyMedicalInsurancePayable : 0;//d-yl
                                    const personal6 = allValue.personalMedicalInsurancePayable ? allValue.personalMedicalInsurancePayable : 0;//g-yl
                                    const personal7 = allValue.companyEnterpriseAnnuityPayable ? allValue.companyEnterpriseAnnuityPayable : 0;//d-q
                                    const personal8 = allValue.personalEnterpriseAnnuityPayable ? allValue.personalEnterpriseAnnuityPayable : 0;//g-q
                                    const personal9 = allValue.companyIIndustrialInjuryPayable ? allValue.companyIIndustrialInjuryPayable : 0;//gs
                                    // const personal10 = allValue.companyChildBearingTaxPayable ? allValue.companyChildBearingTaxPayable : 0;//sy

                                    let lenDataPersonal = personal2 + personal4 + personal6 + personal8;//个人总计

                                    let lenDataCompany = personal3 + personal5 + personal7 + personal9 + obj.value + personal1;//单位总计

                                    obj.form.setFieldsValue({
                                        total: Number((personal2 + personal1 + personal3 + personal5 + personal6 + personal7 + personal8 + personal9 + personal4 + obj.value).toFixed(2)),
                                        companyTotal: Number((lenDataCompany).toFixed(2)),//单位总计
                                        personalTotal: Number((lenDataPersonal).toFixed(2)),//个人总计
                                    })
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'component',
                                field: 'diyJE2',
                                Component: obj => {
                                    return (
                                        <div style={{ width: "100%", backgroundColor: '#fafafa', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px' }}>
                                            总计
                                        </div>
                                    );
                                }
                            }
                        },
                        {
                            table: {
                                title: '个人应缴总和',
                                dataIndex: 'personalTotal',
                                key: 'personalTotal'
                            },
                            form: {
                                type: 'number',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 4 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 12 }
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '单位应缴总和',
                                dataIndex: 'companyTotal',
                                key: 'companyTotal'
                            },
                            form: {
                                type: 'number',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 4 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 12 }
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '总计',
                                dataIndex: 'total',
                                key: 'total',
                            },
                            form: {
                                type: 'number',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 4 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 14 }
                                    }
                                },
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                showType: 'tile',
                                width: 80,
                                title: "操作",
                                key: "action",
                                fixed: 'right',
                                btns: [
                                    {
                                        name: "edit",
                                        render: function () {
                                            return "<a>修改</a>";
                                        },
                                        disabled: () => {
                                            return dod === 'can' ? false : true
                                        },
                                        formBtns: [
                                            {
                                                name: "cancel",
                                                type: "dashed",
                                                label: "取消"
                                            },
                                            {
                                                name: "submit",
                                                type: "primary",
                                                label: "提交",
                                                fetchConfig: {
                                                    apiName: "updateZjXmSalarySocialSecurityManagementDetail"
                                                }
                                            }
                                        ]
                                    }
                                ]
                            }
                        }
                    ]}
                />
                {visibleBjdh ? <Modal
                    width='500px'
                    style={{ top: '0' }}
                    title={'导入'}
                    visible={visibleBjdh}
                    footer={null}
                    onCancel={this.handleCancelChange}
                    bodyStyle={{ width: '500px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelChange}
                    wrapClassName={'modals'}
                >
                    <QnnForm
                        {...this.props}
                        match={this.props.match}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        formConfig={[
                            {
                                label: '附件',
                                field: 'fileList',
                                required: true,
                                type: 'files',
                                fetchConfig: {
                                    apiName: "upload"
                                }
                            },

                        ]}
                        btns={[
                            {
                                name: 'cancel',
                                type: 'dashed',
                                label: '取消',
                                isValidate: false,
                                onClick: () => {
                                    this.setState({
                                        visibleBjdh: false
                                    })
                                }
                            },
                            {
                                name: 'submit',
                                type: 'primary',
                                label: '保存',
                                onClick: (obj) => {
                                    obj.values.socialSecurityManagementId = zhujiandetail;
                                    obj.values.socialSecurityType = tabId;
                                    obj.values.socialSecurityTypeName = tabName;
                                    this.props.myFetch('importZjXmSalarySocialSecurityManagementDetail', obj.values).then(
                                        ({ success, message, data }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.setState({
                                                    visibleBjdh: false
                                                }, () => {
                                                    this.tableOne.refresh();
                                                })
                                            } else {
                                                Msg.error(message);
                                                this.setState({
                                                    visibleBjdh: false
                                                })
                                            }
                                        }
                                    );

                                }
                            }
                        ]}
                        formItemLayout={{
                            labelCol: {
                                xs: { span: 4 },
                                sm: { span: 4 }
                            },
                            wrapperCol: {
                                xs: { span: 20 },
                                sm: { span: 20 }
                            }
                        }}
                    />
                </Modal> : null}
            </div>
        );
    }
}

export default index;