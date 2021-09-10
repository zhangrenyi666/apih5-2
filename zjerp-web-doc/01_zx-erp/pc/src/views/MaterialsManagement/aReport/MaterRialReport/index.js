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
                            label: "导出",
                            onClick: (val) => {
                                if (val) {
                                    let value = val.searchData;
                                    value.then((data) => {
                                        let value = data;
                                        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                        var URL = `${ureport}excel?_u=minio:zxSkCustomerOutRes.ureport.xml&_n=收料单台账&access_token=${access_token}&orgID=${value.orgID ? value.orgID : ''}&outOrgID=${value.outOrgID ? value.outOrgID : ''}&secondResTypeID=${value.secondResTypeID ? value.secondResTypeID : ''}&resID=${value.resID ? value.resID : ''}&resTypeID=${value.resTypeID ? value.resTypeID : ''}&beginDate=${value.beginDate ? value.beginDate : ''}&endDate=${value.endDate ? value.endDate : ''}`;
                                        confirm({
                                            content: '确定导出报表吗?',
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
                                label: '单位名称',
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
                                placeholder: '请选择',
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
                                label: '供应商名称',
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
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '物资类别',
                                field: 'resTypeID',
                                dependenciesReRender: true,//多个依赖-配置
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
                                placeholder: '请选择',
                                onChange: (val, obj) => {
                                    obj.form.setFieldsValue({ secondResTypeID: '', resID: '' });
                                }
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '二级分类',
                                field: 'secondResTypeID',
                                dependenciesReRender: true,//多个依赖-配置
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
                                placeholder: '请选择',
                                onChange: (val, obj) => {
                                    obj.form.setFieldsValue({ resID: '' });
                                }
                            }
                        },

                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '物资编号',
                                field: 'resID',
                                type: 'selectByQnnTable',
                                dependenciesReRender: true,//多个依赖-配置
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
                                                title: "编号",
                                                width: 150
                                            },
                                            form: { type: 'string' }
                                        },
                                        {
                                            isInForm: false,
                                            isInSearch: true,
                                            table: {
                                                dataIndex: "resName",
                                                title: "名称",
                                                width: 100
                                            },
                                            form: { type: 'string' }
                                        },
                                        {
                                            isInForm: false,
                                            isInSearch: true,
                                            table: {
                                                dataIndex: "spec",
                                                title: "规格型号",
                                                width: 100
                                            },
                                            form: { type: 'string' }
                                        },
                                        {
                                            isInForm: false,
                                            table: {
                                                dataIndex: "unit",
                                                title: "单位",
                                                width: 100
                                            },
                                            form: { type: 'string' }
                                        },
                                        {
                                            isInForm: false,
                                            table: {
                                                dataIndex: "resStyle",
                                                title: "计价方式",
                                                width: 100
                                            },
                                            form: { type: 'string' }
                                        },
                                    ]
                                },
                                placeholder: '请选择'
                            }
                        },

                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'date',
                                label: '开始时间',
                                field: 'beginDate'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'date',
                                label: '结束时间',
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
                                title: '项目名称',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                width: 200,
                                tooltip: 23
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '供应商',
                                dataIndex: 'outOrgName',
                                width: 200,
                                tooltip: 23,
                                key: 'outOrgName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '业务日期',
                                dataIndex: 'busDate',
                                key: 'busDate',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '单据编号',
                                dataIndex: 'billNo',
                                key: 'billNo',
                                width: 200,
                                tooltip: 23
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资大类',
                                width: 160,
                                dataIndex: 'resourceName',
                                key: 'resourceName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资编码',
                                width: 180,
                                tooltip: 23,
                                dataIndex: 'resCode',
                                key: 'resCode'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资名称',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'resName',
                                key: 'resName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '规格型号',
                                width: 160,
                                dataIndex: 'spec',
                                key: 'spec'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '计量单位',
                                width: 160,
                                dataIndex: 'resUnit',
                                key: 'resUnit'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '数量',
                                width: 160,
                                dataIndex: 'inQty',
                                key: 'inQty'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '含税采购单价',
                                width: 160,
                                dataIndex: 'inPrice',
                                key: 'inPrice'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '含税采购金额',
                                width: 160,
                                dataIndex: 'resAllFee',
                                key: 'resAllFee'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '不含税采购单价',
                                width: 160,
                                dataIndex: 'inPriceNoTax',
                                key: 'inPriceNoTax'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '不含税采购金额',
                                width: 160,
                                dataIndex: 'resAllFeeNoTax',
                                key: 'resAllFeeNoTax'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '入账金额',
                                width: 160,
                                dataIndex: 'inAmt',
                                key: 'inAmt'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '市场来源',
                                width: 160,
                                dataIndex: 'asmaterialSource',
                                key: 'asmaterialSource'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '是否预收',
                                width: 160,
                                dataIndex: 'precollecte',
                                key: 'precollecte'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '是否有合同',
                                width: 160,
                                dataIndex: 'purchType',
                                key: 'purchType'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '合同编号',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'contractNo',
                                key: 'contractNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '备注',
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