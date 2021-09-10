import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
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
    formItemLayout: {
        labelCol: {
            xs: { span: 3 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 21 },
            sm: { span: 21 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {

        }
    }
    render() {
        // const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                        apiName: '',
                        otherParams: {
                            
                        }
                    }}
                    actionBtns={[
                        {
                            name: "add",
                            type: "primary",
                            label: "归档",
                            onClick: (obj) => {
                                
                            }
                        },
                        {
                            name: "del",
                            icon: "delete",
                            type: "danger",
                            label: "删除",
                            fetchConfig: {
                                apiName: "",
                            },
                        }
                    ]}
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
                            isInForm:false,
                            table: {
                                title: '序号',
                                dataIndex: 'index',
                                key: 'index',
                                width:50,
                                render:(data,rowData,index) => {
                                    return index + 1;
                                }
                            }
                        },
                        {
                            isInForm:false,
                            table: {
                                title: '文件标题',
                                dataIndex: '1',
                                key: '1',
                                filter: true,
                                width:300,
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInForm:false,
                            table: {
                                title: '发起人',
                                dataIndex: '2',
                                key: '2',
                                width:100
                            }
                        },
                        {
                            isInForm:false,
                            table: {
                                title: '发起时间',
                                dataIndex: '3',
                                key: '3',
                                width:120,
                                format:'YYYY-MM-DD HH:mm:ss'
                            }
                        },
                        {
                            isInForm:false,
                            table: {
                                title: '任务接收时间',
                                dataIndex: '4',
                                key: '4',
                                width:120,
                                format:'YYYY-MM-DD HH:mm:ss'
                            }
                        },
                        {
                            isInForm:false,
                            table: {
                                title: '上级活动',
                                dataIndex: '5',
                                key: '5',
                                width:100
                            }
                        },
                        {
                            isInForm:false,
                            table: {
                                title: '任务递交人',
                                dataIndex: '6',
                                key: '6',
                                width:100
                            }
                        },
                        {
                            isInForm:false,
                            table: {
                                title: '当前参与活动',
                                dataIndex: '7',
                                key: '7',
                                width:120
                            }
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;