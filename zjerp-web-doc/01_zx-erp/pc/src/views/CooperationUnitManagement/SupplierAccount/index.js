
import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
const config = {
    
    antd: {
        rowKey: 'zxCrCustomerExtAttrId',
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
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }
    componentDidMount() { }
    render() {
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
                        apiName: 'getZxCrCustomerNewList'
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxCrCustomerExtAttrId',//???
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '供应商编号',
                                dataIndex: 'customerNo',
                                key: 'customerNO',
                                width:180,
                                filter: true
                            },
                            isInForm: false,
                            form: {
                                type: 'string',
                                field:'customerNO'
                            }
                        },
                        {
                            table: {
                                title: '供应商名称',
                                dataIndex: 'customerName',
                                key: 'customerName',
                                width: 200,
                                filter:true
                            },
                            isInForm: false,
                            form: {
                                type: 'string',
                                field:'customerName'
                            }
                        },
                        {
                            table: {
                                title: '法人',
                                dataIndex: 'corparation',
                                key: 'corparation'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '组织机构代码证',
                                dataIndex: 'orgCertificate',
                                width:160,
                                key: 'orgCertificate'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '省份',
                                dataIndex: 'province',
                                key: 'province'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '更新时间',
                                dataIndex: 'editTime',
                                key: 'editTime',
                                format:'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '注册日期',
                                dataIndex: 'regDate',
                                key: 'regDate',
                                format:'YYYY-MM-DD'
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