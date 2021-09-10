import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { push } from "react-router-redux";
import s from "./style.less";

const config = {
    antd: {
        rowKey: function (row) {
            return row.codeId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            codePid: props.match.params.codeId || '',
        }
    }
    componentDidMount() {}
    render() {
        const { codePid } = this.state;
        return (
            <div className={s.root}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch} 
		            upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    fetchConfig={{
                        apiName: 'getZjTzBaseCodeList',
                        otherParams: {
                            interfaceFlag: '1',
                            codePid:codePid
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'codeId',
                                type: 'string',
                                hide:true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'codePid',
                                type: 'string',
                                hide: true,
                                initialValue:codePid
                            }
                        },
                        {   
                            table: {
                                title: '字典ID',
                                dataIndex: 'itemId',
                                key: 'itemId',
                                filter:true
                            },
                            form: {
                                field: 'itemId',
                                required: true,
                                disabled: true,
                                editDisabled:true,
                                type:'string',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3  }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            },
                        },
                        {   
                            table: {
                                title: '设计环节名称',
                                dataIndex: 'itemName',
                                key: 'itemName'
                            },
                            form: {
                                field: 'itemName',
                                required: true,
                                type: 'string',
                                placeholder: '请输入',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3  }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'ext1',
                                key: 'ext1',
                            },
                            form: {
                                type: 'textarea',
                                label: '备注',
                                field: 'ext1', 
                                placeholder: '请输入',
                                required: false,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3  }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        }
                    ]}
                    method={{
                        editClick:(obj) => {
                            this.table.clearSelectedRows();
                        },
                        goBack: (obj) => {
                            const { mainModule } = obj.props.myPublic.appInfo;
                            obj.props.dispatch(
                                push(`${mainModule}AddMuBan`)
                            )
                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: function (obj) {
                            var props = obj.Pprops;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "projectInfo"
                            }
                        }
                    }}
                />
            </div>
        );
    }
}

export default index;