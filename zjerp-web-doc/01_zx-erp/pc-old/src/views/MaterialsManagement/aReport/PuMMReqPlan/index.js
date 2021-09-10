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
                        apiName: 'ureportZxPuMMReqPlanIdle',
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
                                var URL = `${ureport}excel?_u=file:ZxPuMMReqPlan.xml&url=${domain}&orgID=${value.orgID ? value.orgID : null}&resID=${value.resID ? value.resID : null}&resCateID=${value.resCateID ? value.resCateID : null}&beginDate=${new Date(value.period ? value.period[0]._d : null).getTime()}&endDate=${new Date(value.period ? value.period[1]._d : null).getTime()}&type=${value.type ? value.type : null}`;
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
                                field: 'orgID',
                                type: 'select',
                                showSearch: true,
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams: { departmentId: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId }
                                },
                                placeholder: '请选择',
                                children: [
                                    { field: 'resCateID' }
                                ]
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                label: '物资类别',
                                field: 'resCateID',
                                type: 'select',
                                showSearch: true,
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id'
                                },
                                dependencies: ['orgID'],
                                fetchConfig: {
                                    apiName: 'getZxSkResCategoryMaterialsListResourceNotRevolve',
                                    otherParams: (val) => {
                                        let orgIDVal = '';
                                        if (this.formHasTicket?.form) {
                                            let aa = this.formHasTicket.form.getFieldsValue();
                                            orgIDVal = aa.orgID;
                                        } else {
                                            orgIDVal = '';
                                        }
                                        return {
                                            parentOrgID: orgIDVal
                                        }
                                    }
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
                            isInSearch: true,
                            form:{
                                type: 'halfYear',
                                label: '期次',
                                field: 'period'
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form:{
                                type: 'select',
                                label: '显示层级',
                                field: 'type',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "大类",
                                        value: "0"
                                    },
                                    {
                                        label: "明细",
                                        value: "1"
                                    }
                                ]
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
                                title: '公司名称',
                                dataIndex: 'projectName',
                                key: 'projectName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资类别',
                                dataIndex: 'resCateName',
                                width: 200,
                                key: 'resCateName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资编号',
                                width: 200,
                                dataIndex: 'resCode',
                                key: 'resCode'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资名称',
                                dataIndex: 'resName',
                                key: 'resName',
                                width: 120,
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
                                title: '规格型号',
                                width: 180,
                                dataIndex: 'spec',
                                key: 'spec'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '单价',
                                width: 200,
                                dataIndex: 'price',
                                key: 'price'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '本月需用物资量',
                                width: 120,
                                dataIndex: 'purNum',
                                key: 'purNum'
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '金额',
                                width: 120,
                                dataIndex: 'totalMoney',
                                key: 'totalMoney'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '下月预估用量',
                                width: 120,
                                dataIndex: 'nextMonthNum',
                                key: 'nextMonthNum'
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