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
                        apiName: 'ureportZxSkTurnoverInReport',
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
                                        var URL = `${ureport}excel?_u=minio:zxSkTurnoverIn.ureport.xml&_n=周转材料入库台账&access_token=${access_token}&orgId=${value.orgId ? value.orgId : ''}&beginDate=${value.beginDate ? value.beginDate : ''}&endDate=${value.endDate ? value.endDate : ''}`;
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
                                label: '机构名称',
                                field: 'orgId',
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
                                field: 'zxSkTurnoverInReportId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'orgName',
                                width: 200,
                                key: 'orgName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '供应商',
                                dataIndex: 'outOrgName',
                                width: 200,
                                key: 'outOrgName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '业务日期',
                                dataIndex: 'busdate',
                                key: 'busdate'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '单据编号',
                                dataIndex: 'billNo',
                                width: 400,
                                key: 'billNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资编码',
                                width: 200,
                                dataIndex: 'resCode',
                                key: 'resCode'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资名称',
                                width: 200,
                                dataIndex: 'resName',
                                key: 'resName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '规格型号',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 120,
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
                                dataIndex: 'inQty',
                                key: 'inQty'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '含税采购单价(元)',
                                width: 140,
                                dataIndex: 'inPrice',
                                key: 'inPrice'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '含税采购金额(万元)',
                                width: 150,
                                dataIndex: 'inAmtTotal',
                                key: 'inAmtTotal'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '不含税采购单价(元)',
                                width: 170,
                                dataIndex: 'inPriceNoTax',
                                key: 'inPriceNoTax'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '不含税采购金额(万元)',
                                width: 170,
                                dataIndex: 'inAmtNoTax',
                                key: 'inAmtNoTax'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '市场来源',
                                width: 120,
                                dataIndex: 'materialSource',
                                key: 'materialSource'
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
                    ]}
                    actionBtns={[]}
                />
            </div>
        );
    }
}

export default index;