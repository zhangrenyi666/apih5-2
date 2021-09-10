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
                        apiName: 'ureportZxSkWornOutListIdle',
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
                                label: '公司名称',
                                field: 'departmentId',//???
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
                            },
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
                            },
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
                                dataIndex: 'orgName',
                                key: 'orgName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '单据编号',
                                dataIndex: 'billNo',
                                width: 200,
                                tooltip: 23,
                                key: 'billNo'
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
                                title: '单位',
                                dataIndex: 'unit,',
                                key: 'unit,',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '数量',
                                width: 120,
                                dataIndex: 'stockQty,',
                                key: 'stockQty,'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '拟处理金额(万元)',
                                width: 180,
                                tooltip: 23,
                                dataIndex: 'originalAmt',
                                key: 'originalAmt'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '上报审批日期',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'reportDate',
                                key: 'reportDate'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '评审通过日期',
                                width: 120,
                                dataIndex: 'modify_time',
                                key: 'modify_time'
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