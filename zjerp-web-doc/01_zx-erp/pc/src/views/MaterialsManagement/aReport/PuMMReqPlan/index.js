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
                        apiName: 'ureportZxPuMMReqPlan',
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
                                        var URL = `${ureport}excel?_u=minio:zxPuMMReqPlan.ureport.xml&_n=物资月度需用计划汇总表&access_token=${access_token}&projectID=${value.projectID ? value.projectID : ''}&resCateID=${value.resCateID ? value.resCateID : ''}&resID=${value.resID ? value.resID : ''}&period=${value.period ? value.period : ''}&type=${value.type ? value.type : ''}`;
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
                                field: 'projectID',
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
                                label: '物资类别',
                                field: 'resCateID',
                                type: 'select',
                                showSearch: true,
                                parent: 'projectID',
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id'
                                },
                                fetchConfig: {
                                    apiName: 'getZxSkResCategoryMaterialsListResource',
                                    params: {
                                        parentOrgID: 'projectID'
                                    }
                                },
                                placeholder: '请选择'
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '物资编码',
                                parent: 'resCateID',
                                field: 'resID',
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
                                            id: 'resCateID'
                                        }
                                    },
                                    searchBtnsStyle: "inline",
                                    formConfig: [
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
                            form: {
                                type: 'month',
                                label: '期次',
                                field: 'period'
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'select',
                                label: '显示层级',
                                field: 'type',
                                allowClear: false,
                                initialValue: '0',
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
                                field: 'zxPuMMReqPlanId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                width: 200,
                                dataIndex: 'projectName',
                                key: 'projectName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '分部分项',
                                width: 200,
                                dataIndex: 'cbsName',
                                key: 'cbsName'
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
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '计量单位',
                                dataIndex: 'unit',
                                key: 'unit'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '规格型号',
                                dataIndex: 'spec',
                                key: 'spec'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '单价',
                                dataIndex: 'price',
                                key: 'price'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '本月需用物资量',
                                dataIndex: 'purNum',
                                key: 'purNum',
                                width: 120
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '金额',
                                dataIndex: 'totalMoney',
                                key: 'totalMoney'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '下月预估用量',
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