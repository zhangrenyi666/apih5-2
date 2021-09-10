import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { push } from "react-router-redux";
import { message as Msg, Modal } from "antd";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey:'id',
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }
    componentDidMount() {
        
    }
    render() {
        const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
    
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
                        apiName: 'getZjTzInvXmyyqkMonthlyReportListBasicData',
                        otherParams: {
                            projectId:projectId
                        }
                    }}
                    
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'id',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                width: 300,
                                tooltip:23,
                                title: '项目编号',
                                dataIndex: 'proNum',
                                key: 'proNum'
                            },
                        },
                        {
                            table: {
                                title: '管理单位',
                                filter: true,
                                width:300,
                                dataIndex: 'comname',
                                key: 'comname',
                                // type: 'select'
                            },
                            form: {
                                field: 'comname',
                                type: 'string',
                                showSearch: true, 
                                addDisabled: true,
                                disabled: true,
                                editDisabled:true, 
                                // optionConfig: {
                                //     label: 'projectName',
                                //     value: 'projectId'
                                // },
                                // fetchConfig: {
                                //     apiName: "getZjTzProManageList"
                                // }
                            },
                        },
                        {
                            table: {
                                title: '项目名称',
                                width:300,
                                dataIndex: 'proName',
                                key: 'proName'
                            }
                        },
                        {
                            table: {
                                title: '年月',
                                dataIndex: 'periodValue',
                                key: 'periodValue'
                            },
                            form: {
                                type: 'string',
                                field: 'periodValue'
                            }
                        },
                        {
                            table: {
                                title: '填报日期',
                                // filter:true,
                                format:'YYYY-MM-DD',
                                dataIndex: 'createDate',
                                key: 'createDate'
                            },
                            form: {
                                type: 'date',
                                field: 'createDate'
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: "操作",
                                fixed: 'right',
                                dataIndex: 'action',
                                key: 'action',
                                align: "center",
                                noHaveSearchInput: true,
                                showType: "tile",
                                width: 120,
                                btns: [
                                    {
                                        name: 'PolicyDetail',
                                        render: (rowData) => {
                                            return '<a>明细</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { proId,periodValue, proName } = obj.rowData;
                                            obj.props.dispatch(
                                                push(`${mainModule}ProjectInvestmentYyDetail/${proId}/${periodValue}/${proName ? proName :null}`)
                                            )
                                        },
                                    }
                                ]
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;