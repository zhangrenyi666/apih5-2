import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { push } from "react-router-redux";
import { message as Msg, Modal } from "antd";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.id
        },
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
        this.state = {
            id: '',
        }
    }
    componentDidMount() {
        
    }
    handleCancelSend = () => {
        this.setState({ visibleSend: false, loadingSend: false });
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
                        apiName: 'getZjTzInvXmhgqkMonthlyReportListBasicData',
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
                                title: '????????????',
                                dataIndex: 'proNum',
                                key: 'proNum'
                            },
                        },
                        {
                            table: {
                                title: '????????????',
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
                                title: '????????????',
                                width:300,
                                dataIndex: 'proName',
                                key: 'proName'
                            }
                        },
                        {
                            table: {
                                title: '??????',
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
                                title: '????????????',
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
                                title: "??????",
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
                                            return '<a>??????</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { proId,periodValue, proName } = obj.rowData;
                                            obj.props.dispatch(
                                                push(`${mainModule}ProjectInvestmentHgDetail/${proId}/${periodValue}/${proName ? proName :null}`)
                                                // push(`${mainModule}ProjectInvestmentHgDetail/ab0f8330c0f14a7c99f8682526f20367/201502/${proName ? proName :null}`)
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