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
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: false
};
const confirm = Modal.confirm;
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }
    render() {
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        let { myPublic: { domain, appInfo: { ureport } }, location: { pathname } } = this.props;
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
                        apiName: 'ureportZxSkReceivingDynamicIdle',
                        otherParams: {
                            projectId: departmentId
                        }
                    }}
                    topSearchExtendBtns={[
                        {
                            field: "btn1",
                            label: "导出",
                            onClick: (val) => {
                                let value = this.table.btnCallbackFn.searchForm.form.getFieldsValue().searchParams;
                                var URL = `${ureport}excel?_u=file:zxSkcustomerOutRes.xml&url=${domain}&orgID=${value.departmentId ? value.departmentId : null}&appOrgID=${value.appOrgID ? value.appOrgID : null}&resID=${value.resID ? value.resID : null}&resTypeID=${value.resTypeID ? value.resTypeID : null}&beginDate=${new Date(value.beginDate ? value.beginDate._d : null).getTime()}&endDate=${new Date(value.endDate ? value.endDate._d : null).getTime()}`;
                                confirm({
                                    content: '确定导出报表吗?',
                                    onOk: () => {
                                        window.open(URL);
                                    }
                                });
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
                                field: 'departmentId',
                                type: 'select',
                                showSearch: true,
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams: { orgID: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId }
                                },
                                placeholder: '请选择',
                                children: [
                                    {
                                        field:'resID'
                                    }
                                ]
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '供应商名称',
                                field: 'appOrgID',
                                type: 'selectByPaging',
                                optionConfig: {
                                    label: 'customerName',
                                    value: 'zxCrCustomerNewId',
                                },
                                fetchConfig: {
                                    apiName: 'getZxCrCustomerNewList',
                                },
                                showSearch: true,
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '二级分类',
                                field: 'resID',
                                parent: 'departmentId',
                                type: 'select',
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id',
                                },
                                
                                fetchConfig: {
                                    apiName: 'getZxSkResCategoryMaterialsListResourceNotRevolve',
                                    params: {
                                        parentOrgID: 'departmentId'
                                    }
                                },
                                placeholder: '请选择',
                                children: [
                                    {
                                        field:'resTypeID'
                                    }
                                ]
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '物资类别',
                                field: 'resID',
                                parent: 'departmentId',
                                type: 'select',
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id',
                                },
                                
                                fetchConfig: {
                                    apiName: 'getZxSkResCategoryMaterialsListResourceNotRevolve',
                                    params: {
                                        parentOrgID: 'departmentId'
                                    }
                                },
                                placeholder: '请选择',
                                children: [
                                    {
                                        field:'resTypeID'
                                    }
                                ]
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '物资编码',
                                parent: 'resID',
                                field: 'resTypeID',
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
                                        otherParams: (obj) => {
                                            console.log(obj);
                                            return {
                                                id: obj.qnnFormProps?.qnnformData?.qnnFormProps?.rowData?.resID,
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
                                field: 'endDate',
                                initialValue: new Date()
                            }
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
                                width: 120,
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
                                width: 120,
                                dataIndex: 'spec',
                                key: 'spec'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '计量单位',
                                width: 120,
                                dataIndex: 'resUnit',
                                key: 'resUnit'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '数量',
                                width: 120,
                                dataIndex: 'inQty',
                                key: 'inQty'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '含税采购单价',
                                width: 120,
                                dataIndex: 'inPrice',
                                key: 'inPrice'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '含税采购金额',
                                width: 120,
                                dataIndex: 'resAllFee',
                                key: 'resAllFee'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '不含税采购单价',
                                width: 120,
                                dataIndex: 'inPriceNoTax',
                                key: 'inPriceNoTax'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '不含税采购金额',
                                width: 120,
                                dataIndex: 'resAllFeeNoTax',
                                key: 'resAllFeeNoTax'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '入账金额',
                                width: 120,
                                dataIndex: 'inAmt',
                                key: 'inAmt'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '市场来源',
                                width: 120,
                                dataIndex: 'asmaterialSource',
                                key: 'asmaterialSource'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '是否预收',
                                width: 120,
                                dataIndex: 'precollecte',
                                key: 'precollecte'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '是否有合同',
                                width: 120,
                                dataIndex: 'purchType',
                                key: 'purchType'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '合同编号',
                                width: 120,
                                dataIndex: 'contractNo',
                                key: 'contractNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '备注',
                                width: 120,
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