import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg } from "antd";
const config = {
    antd: {
        rowKey: function (row) {
            return row.iecsCBSID
        },
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
            optionData: [],
            orgID: ''
        }
    }
    componentDidMount() {
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        this.props.myFetch('getSysProjectBySelect', {departmentId:departmentId}).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        optionData: data,
                        orgID: data.length ? data[0].departmentId : ''
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    render() {
        const { optionData, orgID } = this.state;
        // const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <div>
                {optionData.length ? <QnnForm
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload} //必须返回一个promise
                    //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                    headers={{
                        token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    formItemLayout={{
                        labelCol: {
                            xs: { span: 7 },
                            sm: { span: 7 }
                        },
                        wrapperCol: {
                            xs: { span: 17 },
                            sm: { span: 17 }
                        }
                    }}
                    formConfig={[
                        {
                            label: '工程项目',
                            field: 'departmentId',
                            type: 'select',
                            optionConfig: {
                                label: 'departmentName',
                                value: 'departmentId',
                            },
                            optionData: optionData,
                            initialValue: orgID,
                            onChange: (val) => {
                                this.setState({
                                    orgID: ""
                                }, () => {
                                    this.setState({
                                        orgID: val
                                    })
                                })
                            },
                            allowClear: false,
                            showSearch: true,
                            placeholder: '请选择',
                            span: 8
                        }
                    ]}
                /> : null}
                {orgID ? <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxEqIecsCBSList',
                        otherParams: {
                            cbsType: '4',
                            orgID: orgID
                        }
                    }}
                    actionBtns={[
                        {
                            name: 'add',//内置add del
                            icon: 'plus',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '新增',
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                },
                                {
                                    name: 'submit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '保存',//提交数据并且关闭右边抽屉 
                                    fetchConfig: {//ajax配置
                                        apiName: 'addZxEqIecsCBS',
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',//内置add del
                            icon: 'edit',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '修改',
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                },
                                {
                                    name: 'submit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '保存',//提交数据并且关闭右边抽屉 
                                    fetchConfig: {//ajax配置
                                        apiName: 'updateZxEqIecsCBS',
                                    },
                                    onClick: (obj) => {
                                        obj.btnCallbackFn.clearSelectedRows();
                                    }
                                }
                            ]
                        },
                        {
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZxEqIecsCBS',
                            },
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'iecsCBSID',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'cbsType',
                                type: 'string',
                                initialValue: '4',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'orgID',
                                type: 'string',
                                initialValue: orgID,
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '内部单位编号',
                                dataIndex: 'code',
                                key: 'code',
                                filter: true,
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '内部单位名称',
                                dataIndex: 'name',
                                key: 'name',
                                filter: true,
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remark',
                                key: 'remark'
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入'
                            }
                        }
                    ]}
                /> : null}
            </div>
        );
    }
}

export default index;