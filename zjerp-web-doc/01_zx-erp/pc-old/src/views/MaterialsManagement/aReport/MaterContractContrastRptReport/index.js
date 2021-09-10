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
                        apiName: 'ureportZxCtContractContrastRptIdle',
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
                                var URL = `${ureport}excel?_u=file:ReceivingDynamic.xml&url=${domain}&departmentId=${value.departmentId ? value.departmentId:null}&materialType=${value.materialType ? value.materialType :null}&resCode=${value.resCode ? value.resCode :null}&inDate=${new Date(value.inDate ? value.inDate._d:null).getTime()}&outDate=${new Date(value.outDate ? value.outDate._d :null).getTime()}`;
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
                            form:{
                                label: '单位名称',
                                field: 'departmentId',
                                type: 'select',
                                showSearch: true,
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect'
                                },
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                label: '合同名称',
                                field: 'fff',
                                type: 'string',
                                placeholder: '请输入...'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                label: '供货单位',
                                field: 'fffd',
                                type: 'string',
                                placeholder: '请输入...'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                label: '材料类别',
                                field: 'resCateID',
                                type: 'select',
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id'
                                },
                                fetchConfig: {
                                    apiName: 'getZxSkResCategoryMaterialsListResourceNotRevolve'
                                },
                                placeholder: '请选择',
                                children: [
                                    { field: 'resID' }
                                ]
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                label: '材料编码',
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
                                            return {
                                                id: obj.qnnFormProps?.qnnformData?.qnnFormProps?.rowData?.resID,
                                            }
                                        }
                                    },
                                    searchBtnsStyle: "inline",
                                    formConfig:[
                                        {
                                            isInForm: false,
                                            isInTable: false,
                                            table: {
                                                dataIndex: "id",
                                                title: "id",
                                            },
                                            form: {
                                                field: 'id',
                                                type: "string"
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            table: {
                                                dataIndex: "resCode",
                                                title: "编号",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            table: {
                                                dataIndex: "resName",
                                                title: "名称",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            table: {
                                                dataIndex: "spec",
                                                title: "规格型号",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            table: {
                                                dataIndex: "unit",
                                                title: "单位",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        }
                                    ]
                                },
                                placeholder: '请选择'
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
                                title: '序号',
                                dataIndex: 'index',
                                key: 'index',
                                width: 50,
                                fixed: 'left',
                                render: (data, rowData, index) => {
                                    return index + 1;
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'firstName',
                                key: 'firstName',
                                width: 200,
                                tooltip: 23
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '合同编号',
                                dataIndex: 'contractNo',
                                width: 200,
                                key: 'contractNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '供货单位',
                                dataIndex: 'secondName',
                                key: 'secondName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资大类',
                                dataIndex: 'workType',
                                key: 'workType',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资编码',
                                width: 120,
                                dataIndex: 'workNo',
                                key: 'workNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资名称',
                                width: 120,
                                tooltip: 23,
                                dataIndex: 'workName',
                                key: 'workName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '规格型号',
                                width: 120,
                                tooltip: 23,
                                dataIndex: 'spec',
                                key: 'spec'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '计量单位',
                                width: 120,
                                dataIndex: 'unit',
                                key: 'unit'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资合同单价',
                                width: 120,
                                dataIndex: 'price',
                                key: 'price'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资合同数量',
                                width: 120,
                                dataIndex: 'qty',
                                key: 'qty'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资合同金额',
                                width: 120,
                                dataIndex: 'contractSum',
                                key: 'contractSum'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资采购单价',
                                width: 120,
                                dataIndex: 'inPrice',
                                key: 'inPrice'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资采购数量',
                                width: 120,
                                dataIndex: 'inQty',
                                key: 'inQty'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资采购金额',
                                width: 120,
                                dataIndex: 'inAmt',
                                key: 'inAmt'
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