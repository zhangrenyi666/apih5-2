import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import { Modal } from "antd";
import moment from 'moment';
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
                        apiName: 'ureportZxSkTurnResAmo',
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
                            form: {
                                label: '项目名称',
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
                            form: {
                                label: '物资编码',
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
                                        apiName: "getZxSkTurnoverInResourceList"
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
                                            isInSearch: true,
                                            table: {
                                                dataIndex: "resUnit",
                                                title: "计量单位",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            // isInSearch:true,
                                            table: {
                                                dataIndex: "pricingManner",
                                                title: "计价方式",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            // isInSearch:true,
                                            table: {
                                                dataIndex: "planningAuthorities",
                                                title: "编制机构",
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
                            form: {
                                type: 'halfYear',
                                label: '期次',
                                field: 'yearBeginDate'
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
                                title: '物资编号',
                                dataIndex: 'resCode',
                                key: 'resCode'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资名称',
                                dataIndex: 'resName',
                                width: 200,
                                tooltip: 23,
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
                                dataIndex: 'resUnit',
                                key: 'resUnit',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '数量',
                                width: 120,
                                dataIndex: 'buyQty',
                                key: 'buyQty'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '购入单价',
                                width: 180,
                                tooltip: 23,
                                dataIndex: 'buyPrice',
                                key: 'buyPrice'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '原值',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'buyAmt',
                                key: 'buyAmt'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '本期摊销金额',
                                width: 120,
                                dataIndex: 'shareAmt',
                                key: 'shareAmt'
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '累计摊销金额',
                                width: 120,
                                dataIndex: 'allShareAmt',
                                key: 'allShareAmt'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '期末净值',
                                width: 120,
                                dataIndex: 'currentAmt',
                                key: 'currentAmt'
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