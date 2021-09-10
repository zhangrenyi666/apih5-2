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
    // searchFormColNum: 4,
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
                        apiName: 'ureportZxSkResourceOut',
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
                                        var URL = `${ureport}excel?_u=minio:zxSkResourceOut.ureport.xml&_n=项目物资出库台账&access_token=${access_token}&orgID=${value.orgID ? value.orgID : ''}&outType=${value.outType ? value.outType : ''}&inOrgName=${value.inOrgName ? value.inOrgName : ''}&resID=${value.resID ? value.resID : ''}&resTypeID=${value.resTypeID ? value.resTypeID : ''}&beginDate=${value.beginDate ? value.beginDate : ''}&endDate=${value.endDate ? value.endDate : ''}`;
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
                    formItemLayoutSearch={{
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 16 }
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '项目名称',
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
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '出库类型',
                                field: 'outType',
                                type: 'select',
                                allowClear: false,
                                initialValue: '全部',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "全部",
                                        value: "全部"
                                    },
                                    {
                                        label: "领料",
                                        value: "领料"
                                    },
                                    {
                                        label: "对内调拨",
                                        value: "对内调拨"
                                    },
                                    {
                                        label: "对外调拨",
                                        value: "对外调拨"
                                    }
                                ],
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
                            },
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
                            isInSearch: true,
                            form: {
                                type: 'string',
                                label: '领料/调拨单位',
                                field: 'inOrgName'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '物资类别',
                                field: 'resTypeID',
                                type: 'select',
                                showSearch: true,
                                parent: 'orgID',
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id'
                                },
                                fetchConfig: {
                                    apiName: 'getZxSkResCategoryMaterialsListResource',
                                    params: {
                                        parentOrgID: 'orgID'
                                    }
                                },
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '物资编码',
                                // type: 'select',
                                field: 'resID',
                                parent: 'resTypeID',
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
                                        params: {
                                            id: "resTypeID"
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
                            form: {
                                field: 'zxSkResourceOutId',
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
                    actionBtns={[]}
                />
            </div>
        );
    }
}

export default index;