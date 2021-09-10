import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Button } from 'antd';
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            applyZhan: 'false'
        }
    }
    componentDidMount() {

    }
    render() {
        const { tabs, ...other } = this.props;
        return (
            <div>
                <QnnForm
                    {...other}
                    match={this.props.match}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.formHasTicket = me;
                    }}
                    formConfig={[
                        {
                            field: 'zxCrCustomerInfoId',
                            type: 'string',
                            hide: true,
                        },
                        {
                            label: '统一社会信用代码',
                            field: "orgCertificate",
                            type: 'string',
                            disabled: true,
                            span: 8
                        },
                        {
                            type: 'component',
                            field: 'diySK_3',
                            Component: obj => {
                                return (
                                    <div style={{ margin: '11px 60px 11px 80px', float: 'left' }}>
                                        <Button type='primary' onClick={() => {
                                            this.setState({
                                                applyZhan: 'true'
                                            })

                                        }}>检索</Button>
                                        <Button style={{ marginLeft: '10px' }} type='primary' disabled={this.state.applyZhan === 'true' ? false : true} onClick={() => {


                                        }}>申请占号</Button>
                                    </div>
                                );
                            },
                            span: 8
                        },
                        {
                            label: '是否战略供应商',
                            field: "strategicSupplier",
                            type: 'select',
                            placeholder: '请输入',
                            span: 8,
                            required: true,
                            optionConfig: {
                                label: 'label', //默认 label
                                value: 'value'
                            },
                            optionData: [
                                {
                                    label: '否',
                                    value: '0'
                                },
                                {
                                    label: '是',
                                    value: '1'
                                }
                            ]
                        },
                        {
                            label: '协作单位名称',
                            field: "customerName",
                            required: true,
                            type: 'string',
                            span: 8,
                        },
                        {
                            label: '状态',
                            field: "auditStatus",
                            type: 'string',
                            span: 8,
                            disabled: true,
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'zhuangTai'
                                }
                            },
                        },
                        {
                            label: '注册资本金(元)',
                            field: "regMoney",
                            type: 'number',
                            span: 8,
                            disabled: true
                        },
                        {
                            label: '实缴资本金(元)',
                            field: "realRegMoney",
                            type: 'number',
                            disabled: true,
                            span: 8
                        },
                        {
                            label: '企业性质',
                            field: "businessType",
                            type: 'select',
                            placeholder: '请输入',
                            span: 8,
                            optionConfig: {
                                label: 'itemName',
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'qiYeXingZhi'
                                }
                            }
                        },
                        {
                            label: '法定代表人',
                            field: "corparation",
                            type: 'string',
                            span: 8
                        },
                        {
                            label: '法定代表人身份证号码',
                            field: "pricinpalIDCard",
                            type: 'string',
                            span: 8
                        },
                        {
                            label: '法定代表人电话',
                            field: "pricinpalMobile",
                            type: 'phone',
                            span: 8
                        },
                        {
                            label: '企业详细地址',
                            field: "pricinpalAddr",
                            type: 'textarea',
                            autoSize: {
                                minRows: 1,
                                maxRows: 3
                            },
                            span: 8
                        },
                        {
                            label: '营业执照注册号',
                            field: "licenceNO",
                            type: 'string',
                            span: 8
                        },
                        {
                            label: '营业执照有效期至',
                            field: "licenceDate",
                            type: 'date',
                            span: 8
                        },
                        {
                            label: '企业资质证书编号',
                            field: "qualificateNo",
                            type: 'string',
                            span: 8
                        },
                        {
                            label: '企业资质证书有效期至',
                            field: "qualificateDate",
                            type: 'date',
                            span: 8
                        },
                        {
                            label: '企业税务登记证号',
                            field: "taxRegNo",
                            type: 'string',
                            span: 8
                        },
                        {
                            label: '企业税务登记有效期至',
                            field: "taxRegDate",
                            type: 'date',
                            span: 8
                        },
                        {
                            label: '安全生产许可证编码',
                            field: "safeCode",
                            type: 'string',
                            span: 8
                        },
                        {
                            label: '安全生产许可证有效期至',
                            field: 'safeBookDate',
                            type: 'date',
                            placeholder: '请选择',
                            span: 8,
                        },
                        {
                            label: '所在省份',
                            field: 'province',
                            type: 'select',//???
                            placeholder: '请选择',
                            span: 8,
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'suoZaiShengFen'
                                }
                            },
                        },
                        {
                            label: '所在区域',
                            field: "area",
                            type: 'select',//???
                            placeholder: '请选择',
                            span: 8,
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'suoZaiQuYu'
                                }
                            },
                        },
                        {
                            label: '纳税人类别',
                            field: 'taxpayerType',
                            type: 'select',
                            placeholder: '请输入',
                            span: 8,
                            optionConfig: {
                                label: 'itemName',
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'naShuiRenLeiBie'
                                }
                            }
                        },
                        {
                            label: '纳税人识别号',
                            field: 'taxpayerNum',
                            type: 'string',
                            span: 8
                        },
                        {
                            label: '占号维护单位',
                            field: 'comOrgName',
                            type: 'string',
                            disabled: true,
                            span: 8
                        },
                        {
                            label: '开户行名称',
                            field: 'bankName',
                            type: 'string',
                            span: 8
                        },
                        {
                            label: '开户行账号',
                            field: 'bankAccount',
                            type: 'string',
                            span: 8
                        },
                        {
                            label: '银行授信额度',
                            field: 'creditLineAmt',
                            type: 'number',
                            span: 8
                        },
                        {
                            label: '推荐单位',
                            field: 'referenceOrg',
                            type: 'string',
                            disabled: true,
                            span: 8
                        },
                        {
                            label: '证照是否过期',
                            field: 'dateStatus',
                            type: 'select',//???
                            disabled: true,
                            span: 8,
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'zhengZhaoShiFouGuoQi'
                                }
                            },
                        },
                        {
                            label: '是否需要复审',
                            field: 'isNeedfushen',
                            type: 'select',//???
                            disabled: true,
                            span: 8
                        },
                        {
                            label: '复核状态',
                            field: 'fuheStatus',
                            type: 'select',
                            disabled: true,
                            span: 8,
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'fuHeZhuangTai'
                                }
                            },
                        },
                        {
                            label: '复审状态',
                            field: 'fushenStatus',
                            type: 'select',
                            disabled: true,
                            span: 8,
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'fuShenZhuangTai'
                                }
                            },
                        },
                        {
                            label: '推荐人姓名',
                            field: 'referenceName',
                            type: 'string',
                            span: 8
                        },
                        {
                            label: '推荐人职务',
                            field: 'referencePost',
                            type: 'string',
                            span: 8
                        },
                        {
                            label: '推荐人联系电话',
                            field: 'referencePhone',
                            type: 'string',
                            span: 8
                        },
                        {
                            label: '曾用名',
                            field: 'usedNames',
                            type: 'string',
                            span: 8
                        },
                        {
                            label: '黑名单',
                            field: 'isBlack',
                            type: 'select',
                            span: 8,
                            optionConfig: {
                                label: 'label',
                                value: 'value'
                            },
                            optionData: [
                                {
                                    label: '否',
                                    value: '0'
                                },
                                {
                                    label: '是',
                                    value: '1'
                                }
                            ]
                        }
                    ]}
                    btns={[]}
                    tailFormItemLayout={{
                        wrapperCol: {
                            xs: {
                                span: 24,
                                offset: 0
                            },
                            sm: {
                                span: 24,
                                offset: 8
                            }
                        }
                    }}
                />
                <div style={{ position: 'absolute', bottom: 0, background: 'white', textAlign: 'end', width: '100%', padding: '10px 16px', borderTop: '1px solid rgb(232, 232, 232)' }}>
                    <Button type="dashed" style={{ marginRight: '8px' }} onClick={() => {
                        this.props.btnCallbackFn.closeDrawer();
                    }}>返回</Button>
                    <Button type="primary" onClick={() => {
                        let value = this.formHasTicket.form.getFieldsValue();
                        this.props.myFetch("addZxCrCustomerInfo", value).then(({ success, data, message }) => {
                            if (success) {
                                Msg.success(message);
                                this.props.btnCallbackFn.refresh();
                                this.props.btnCallbackFn.closeDrawer();
                            } else {
                                Msg.error(message);
                            }
                        })

                    }}>保存</Button>
                </div>
            </div>
        );
    }
}
export default index;
