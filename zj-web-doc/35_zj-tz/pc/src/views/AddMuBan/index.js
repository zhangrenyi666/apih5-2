import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { push } from "react-router-redux";
import s from "./style.less";

const config = {
    fetchConfig: {
        apiName: 'getZjTzBaseCodeList',
        otherParams: {
            typeId: 'SheJiLiuChengHuanJieMuBan',
            interfaceFlag:'0'
        }
    },
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
        }
    }
    componentDidMount() {}
    render() {
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
                                title: '工程类别',
                                dataIndex: 'itemName',
                                key: 'itemName',
                                filter:true
                            },
                            form: {
                                field: 'itemName',
                                required: true,
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
                                width: 80,
                                btns: [
                                    {
                                        name: 'PolicyDetail',
                                        render: (rowData) => {
                                            return '<a>添加环节</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { codeId } = obj.rowData;
                                            obj.props.dispatch(
                                                push(`${mainModule}AddMuBanDetail/${codeId}`)
                                            )
                                        },
                                    }
                                ]
                            }
                        }
                    ]}
                    method={{
                        editClick:(obj) => {
                            this.table.clearSelectedRows();
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