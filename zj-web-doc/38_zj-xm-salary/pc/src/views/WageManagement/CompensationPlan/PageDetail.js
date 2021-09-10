import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
import { push } from "react-router-redux";
import Apih5 from "qnn-apih5"
const config = {
    antd: {
        rowKey: 'compensationPlanDetailId',
        size: 'small'
    },
    isShowRowSelect: true,
    paginationConfig: {
        position: 'bottom'
    },
};
class index extends Apih5 {
    constructor(props) {
        super(props);
        this.state = {}
    }
    render() {
        const { compensationPlanId } = this.props.match.params;
        // const orgId = this.apih5.getOrgId();
        console.log('compensationPlanId-----', compensationPlanId);
        // console.log('orgId---------', orgId);
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
                    formItemLayout={{
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 6 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 18 }
                        }
                    }}
                    fetchConfig={{
                        apiName: 'getZjXmSalaryCompensationPlanDetailList',
                        otherParams: {
                            compensationPlanId: compensationPlanId,
                            // orgId: orgId
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
                                    push(`${mainModule}CompensationPlan`)
                                )
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
                                        apiName: 'addZjXmSalaryCompensationPlanDetail'
                                    }
                                }
                            ]
                        },
                        {
                            name: 'del',
                            icon: 'del',
                            type: 'danger',
                            label: '删除',
                            fetchConfig: {
                                apiName: 'batchDeleteUpdateZjXmSalaryCompensationPlanDetail'
                            }
                        },
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'compensationPlanDetailId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'calculationTypeName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'taxCalculationMethodName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'dataSourcesName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'payName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'compensationPlanId',//主表id
                                type: 'string',
                                initialValue: compensationPlanId,
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'payTypeName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'payCode',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '薪酬分类',
                                dataIndex: 'payTypeName',
                                key: 'payTypeName',
                            },
                            form: {
                                type: 'select',
                                required: true,
                                editDisabled: true,
                                field: 'payTypeId',
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'xinChouFenLei'
                                    }
                                },
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                    linkageFields: {
                                        payTypeName: 'itemName'
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '薪酬项',
                                dataIndex: 'payName',
                                key: 'payName'
                            },
                            form: {
                                type: 'select',
                                required: true,
                                editDisabled: true,
                                field: 'salaryItemDictionaryId',
                                dependenciesReRender: true,//多个依赖-配置
                                dependencies: ['payTypeId'],
                                placeholder: '请选择',
                                fetchConfig: {
                                    apiName: 'getZjXmSalarySalaryItemDictionaryListStatus',
                                    params: {
                                        payTypeId: 'payTypeId'
                                    }
                                },
                                optionConfig: {
                                    value: 'salaryItemDictionaryId',
                                    label: 'payName',
                                    linkageFields: {
                                        calculationTypeId: 'calculateTypeId',
                                        includedTotalFlag: 'includedTotalFlag',
                                        payName: 'payName',
                                        calculationTypeName: 'calculateTypeName',
                                        payCode: 'payCode'
                                    }
                                },
                                onChange: (val, rowData) => {
                                    this.props.myFetch('checkZjXmSalaryCompensationPlanDetail', rowData.form.getFieldsValue()).then(({ data, success, message }) => {
                                        if (success) {
                                            if (data?.check === false) {
                                                Msg.warn(data.checkMessage + '  请重新选择！');
                                                setTimeout(() => {
                                                    rowData.form.setFieldsValue({
                                                        salaryItemDictionaryId: null,
                                                        calculationTypeId: null,
                                                        includedTotalFlag: null
                                                    })
                                                }, 200)
                                            } else {

                                            }
                                        } else {
                                            Msg.error(message)
                                        }
                                    })
                                }
                            }
                        },
                        {
                            table: {
                                title: '计算类型',
                                dataIndex: 'calculationTypeId',
                                key: 'calculationTypeId',
                                type: 'select',
                            },
                            form: {
                                field: 'calculationTypeId',
                                type: 'select',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请选择',
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'faxqJiSuanLeiXing'
                                    }
                                },
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                    linkageFields: {
                                        calculationTypeName: 'itemName'
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '计税方式',
                                dataIndex: 'taxCalculationMethodName',
                                key: 'taxCalculationMethodName'
                            },
                            form: {
                                field: 'taxCalculationMethodId',
                                type: 'select',
                                required: true,
                                placeholder: '请选择',
                                dependenciesReRender: true,//多个依赖-配置
                                dependencies: ['calculationTypeId', 'salaryItemDictionaryId'],
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                    linkageFields: {
                                        taxCalculationMethodName: 'itemName'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getZjXmSalaryCompensationPlanDetailTaxCalculationMethod',
                                    otherParams: (val) => {
                                        let xcx = '';
                                        let jslx = '';
                                        if (val.btnCallbackFn?.form) {
                                            let aa = val.btnCallbackFn.form.getFieldsValue();
                                            xcx = aa.calculationTypeId;
                                            jslx = aa.salaryItemDictionaryId;
                                        } else {
                                            xcx = '';
                                            jslx = '';
                                        }
                                        return {
                                            itemId: xcx,
                                            salaryItemDictionaryId: jslx
                                        }
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '数据来源',//???
                                dataIndex: 'dataSourcesId',
                                key: 'dataSourcesId',
                                type: 'select',
                            },
                            form: {
                                field: 'dataSourcesId',
                                type: 'select',
                                required: true,
                                placeholder: '请选择',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                    linkageFields: {
                                        dataSourcesName: 'itemName'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'faxqShuJuLaiYuan'
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '公式',
                                dataIndex: 'formula',
                                key: 'formula'
                            },
                            form: {
                                field: 'formula',
                                type: 'formula',
                                required: true,
                                dependenciesReRender: true,//多个依赖-配置
                                dependencies: ['salaryItemDictionaryId'],
                                placeholder: '请选择',
                                optionConfig: {
                                    label: 'label',
                                    value: 'field'
                                },
                                fetchConfig: {
                                    apiName: 'getExpressionTabData',
                                    otherParams: (obj) => {
                                        console.log(obj.clickCb.rowInfo.name);
                                        return {
                                            compensationPlanId: compensationPlanId,
                                            flag: obj.clickCb.rowInfo.name === 'add' ? true : false
                                        }
                                    },
                                    params: {
                                        salaryItemDictionaryIdAdd: 'salaryItemDictionaryId'
                                    }
                                },
                                condition: [
                                    {
                                        regex: {
                                            dataSourcesId: ['!', '2'],
                                        },
                                        action: ['hide'],
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '是否显示',
                                dataIndex: 'showFlag',
                                key: 'showFlag',
                                type: 'select',
                            },
                            form: {
                                field: 'showFlag',
                                type: 'select',
                                required: true,
                                placeholder: '请选择',
                                optionConfig: {
                                    value: 'value',
                                    label: 'name'
                                },
                                optionData: [
                                    {
                                        value: '0',
                                        name: '否'
                                    },
                                    {
                                        value: '1',
                                        name: '是'
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '工资条显示',
                                dataIndex: 'payslipDisplayFlag',
                                key: 'payslipDisplayFlag',
                                type: 'select',
                            },
                            form: {
                                field: 'payslipDisplayFlag',
                                type: 'select',
                                required: true,
                                placeholder: '请选择',
                                optionConfig: {
                                    value: 'value',
                                    label: 'name'
                                },
                                optionData: [
                                    {
                                        value: '0',
                                        name: '单独显示'
                                    },
                                    {
                                        value: '1',
                                        name: '是'
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '是否纳入薪酬总额',
                                dataIndex: 'includedTotalFlag',
                                key: 'includedTotalFlag',
                                type: 'select',
                            },
                            form: {
                                field: 'includedTotalFlag',
                                type: 'select',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请选择',
                                optionConfig: {
                                    value: 'value',
                                    label: 'name'
                                },
                                optionData: [
                                    {
                                        value: '0',
                                        name: '否'
                                    },
                                    {
                                        value: '1',
                                        name: '是'
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '排序',
                                dataIndex: 'sort',
                                key: 'sort',
                            },
                            form: {
                                type: 'number'
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
                                                    apiName: "updateZjXmSalaryCompensationPlanDetail"
                                                }
                                            }
                                        ]
                                    }
                                ]
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;