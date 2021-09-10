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
                        apiName: 'ureportZxSkResInOutStockAllAmtIdle',
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
                                var URL = `${ureport}excel?_u=file:ZxSkResInOutStockAllAmt.xml&url=${domain}&delFlag=0&orgID=${value.orgID ? value.orgID : null}&resID=${value.resID ? value.resID : null}&beginDate=${new Date(value.period ? value.period[0]._d : null).getTime()}&endDate=${new Date(value.period ? value.period[1]._d : null).getTime()}&isFinish=${value.isFinish ? value.isFinish : null}`;
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
                                label: '单位',
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
                                placeholder: '请选择'
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'select',
                                label: '材料类型',
                                field: 'resID',
                                showSearch: true,
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id',
                                    children: 'zxSkResourceMaterialsList'
                                },
                                fetchConfig: {
                                    apiName: 'getZxSkResCategoryMaterialsAll'
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
                            form: {
                                type: 'select',
                                label: '是否完工',
                                field: 'isFinish',
                                initialValue:'0',
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
                                        label: "否",
                                        value: "1"
                                    },
                                    {
                                        label: "是",
                                        value: "2"
                                    }
                                ],
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
                                title: '物资类别',
                                dataIndex: 'resName',
                                key: 'resName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '统计字母或代码',
                                dataIndex: 'resCode',
                                key: 'resCode',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '年初库存量',
                                width: 120,
                                dataIndex: 'stockAmt',
                                key: 'stockAmt'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '累计收入',
                                dataIndex: 'spec',
                                key: 'spec',
                                children: [
                                   {
                                        title: '预收料',
                                        dataIndex: 'obuAmt',
                                        key: 'obuAmt',
                                        width: 130,
                                    },
                                    {
                                        title: '合计',
                                        dataIndex: 'totalAmt',
                                        key: 'totalAmt',
                                        width: 130,
                                    },
                                    {
                                        title: '总收入金额(含税)',
                                        dataIndex: 'totalAmtAll',
                                        key: 'totalAmtAll',
                                        width: 130,
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '累计消费',
                                dataIndex: 'oswAmt',
                                key: 'oswAmt'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '累计拔出',
                                dataIndex: 'otkAmt',
                                key: 'otkAmt'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '盈(+)亏(-)',
                                dataIndex: 'vinAmt',
                                key: 'vinAmt'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '期末库存量',
                                dataIndex: 'thisAmt',
                                key: 'thisAmt'
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