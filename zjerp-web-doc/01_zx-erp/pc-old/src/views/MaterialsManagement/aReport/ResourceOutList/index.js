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
                        apiName: 'ureportZxSkResourceOutIdle',
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
                                var URL = `${ureport}excel?_u=file:ReceivingDynamic.xml&url=${domain}&departmentId=${value.departmentId ? value.departmentId : null}&materialType=${value.materialType ? value.materialType : null}&resCode=${value.resCode ? value.resCode : null}&inDate=${new Date(value.inDate ? value.inDate._d : null).getTime()}&outDate=${new Date(value.outDate ? value.outDate._d : null).getTime()}`;
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
                                label: '项目名称',
                                field: 'departmentId',
                                type: 'select',
                                showSearch: true,
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams: {
                                        departmentId: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId
                                    }
                                },
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                label: '出库类型',
                                field: 'cklx',
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "全部",
                                        value: "0"
                                    },
                                    {
                                        label: "领料",
                                        value: "1"
                                    },
                                    {
                                        label: "对内调拨",
                                        value: "2"
                                    },
                                    {
                                        label: "对外调拨",
                                        value: "3"
                                    }
                                ],
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                type: 'date',
                                label: '开始时间',
                                field: 'inDate'
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                type: 'date',
                                label: '结束时间',
                                field: 'outDate'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                type: 'string',
                                label: '领料/调拨单位',
                                field: 'ddd'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                label: '物资类别',
                                field: 'materialType',
                                type: 'select',
                                showSearch: true,
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id',
                                    children: 'zxSkResourceMaterialsList'
                                },
                                fetchConfig: {
                                    apiName: 'getZxSkResCategoryMaterialsAll'
                                },
                                placeholder: '请选择',
                                children: [
                                    {
                                        field:'resCode'
                                    }
                                ]
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                label: '物资编码',
                                type: 'select',
                                field: 'resCode',
                                parent: 'materialType',
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id'
                                },
                                fetchConfig: {
                                    apiName: 'getZxSkResCategoryMaterialsListResource',
                                    params: {
                                        parentOrgID: "materialType"
                                    }
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
                                align: 'center',
                                render: (val, rowData, index) => {
                                    return <span>{index + 1}</span>
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '出库类型',
                                dataIndex: 'outType',
                                key: 'outType'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '出库日期',
                                dataIndex: 'busDate',
                                width: 200,
                                tooltip: 23,
                                key: 'busDate'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '领料/调拨单位',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'inOrgName',
                                key: 'inOrgName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资类别',
                                dataIndex: 'resourceName',
                                key: 'resourceName',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资编号',
                                width: 120,
                                dataIndex: 'resCode',
                                key: 'resCode'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资名称',
                                width: 180,
                                tooltip: 23,
                                dataIndex: 'resName',
                                key: 'resName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '规格',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'spec',
                                key: 'spec'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '单位',
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
                                dataIndex: 'outQty',
                                key: 'outQty'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '出库单价',
                                width: 120,
                                dataIndex: 'stdPrice',
                                key: 'stdPrice'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '金额',
                                width: 120,
                                dataIndex: 'outAmt',
                                key: 'outAmt'
                            },
                            isInForm: false
                        }
                    ]}
                    actionBtns={[
                        // {
                        //     name: 'add',
                        //     icon: 'plus',
                        //     type: 'primary',
                        //     label: '新增',
                        //     formBtns: [
                        //         {
                        //             name: 'cancel',
                        //             type: 'dashed',
                        //             label: '取消',
                        //         },
                        //         {
                        //             name: 'submit',
                        //             type: 'primary',
                        //             label: '保存',
                        //             field: 'addsubmit',
                        //             fetchConfig: {
                        //                 apiName: 'addZxSkStockTransferMaterialRequisition'
                        //             }
                        //         }
                        //     ]
                        // },
                    ]}
                />
            </div>
        );
    }
}

export default index;