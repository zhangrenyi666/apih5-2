import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from '../../modules/qnn-table/qnn-form';
import s from "./style.less";
import { message as Msg, Modal } from "antd";
const config = {
    antd: {
        rowKey: function (row) {
            return row.zxSfPlanTargetId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 11 },
            sm: { span: 11 }
        },
        wrapperCol: {
            xs: { span: 13 },
            sm: { span: 13 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visible: false,
            rowData: null
        }
    }
    render() {
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        // const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { visible, rowData } = this.state;
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
                    fetchConfig={{
                        apiName: 'getZxSfPlanTargetList',
                        // otherParams: {
                        //     projectId: projectId
                        // }
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
                                        apiName: 'addZxSfPlanTarget',
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',//内置add del
                            icon: 'edit',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '修改',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && data[0].isimported === '0') {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
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
                                        apiName: 'updateZxSfPlanTarget',
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
                                apiName: 'batchDeleteUpdateZxSfPlanTarget',
                            }
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSfPlanTargetId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'orgName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '年份',
                                dataIndex: 'year',
                                key: 'year',
                                filter: true,
                                width: 150,
                                tooltip: 15,
                                fixed: 'left',
                                type: "select",

                                // onClick: 'detail'
                            },
                            form: {
                                type: 'select',
                                required: true,
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "2020",
                                        value: "0"
                                    },
                                    {
                                        label: "2019",
                                        value: "1"
                                    }
                                ],
                                allowClear: false,
                                showSearch: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '单位名称',
                                dataIndex: 'orgID',
                                key: 'orgID',
                                filter: true,
                                width: 150,
                                tooltip: 15,
                                fixed: 'left',
                                type: "select",
                            },
                            form: {
                                type: 'select',
                                required: true,
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId',
                                    linkageFields: {
                                        orgName: 'departmentName'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getSysCompanyProject',
                                    otherParams: {
                                        departmentId: departmentId
                                    }
                                },
                                allowClear: false,
                                showSearch: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '因工死亡率(‰)',
                                dataIndex: 'deadRate',
                                key: 'deadRate',
                                width: 100,
                                align: 'center'
                            },
                            form: {
                                field: 'deadRate',
                                type: 'number',
                                spanForm: 8,
                                initialValue: 0,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '重伤率(‰)',
                                dataIndex: 'injuresRate',
                                key: 'injuresRate',
                                width: 100,
                                align: 'center'
                            },
                            form: {
                                field: 'injuresRate',
                                type: 'number',
                                spanForm: 8,
                                initialValue: 0,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '轻伤率(‰)',
                                dataIndex: 'slightlyRate',
                                key: 'slightlyRate',
                                width: 100,
                                align: 'center'
                            },
                            form: {
                                field: 'slightlyRate',
                                type: 'number',
                                spanForm: 8,
                                initialValue: 0,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '隐患整改率(%)',
                                dataIndex: 'hidChageRate',
                                key: 'hidChageRate',
                                width: 100,
                                align: 'center'
                            },
                            form: {
                                field: 'hidChageRate',
                                type: 'number',
                                spanForm: 8,
                                initialValue: 0,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "备注",
                                field: 'remarks',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8,
                            }
                        }
                    ]}
                />
                {
                    visible ? <div>
                        <Modal
                            width={'500px'}
                            style={{ paddingBottom: '0', top: '0' }}
                            title="审核"
                            visible={visible}
                            footer={null}
                            bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                            centered={true}
                            closable={false}
                            maskClosable={false}
                            wrapClassName={'replyData'}
                        >
                            <QnnForm
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload} //必须返回一个promise
                                //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                                headers={{
                                    token: this.props.loginAndLogoutInfo.loginInfo.token
                                }}
                                data={rowData}
                                formConfig={[
                                    {
                                        type: "string",
                                        label: "主键id",
                                        field: "zxSfPlanTargetId", //唯一的字段名 ***必传
                                        hide: true
                                    },
                                    {
                                        type: "select",
                                        label: "单位名称",
                                        field: "orgID", //唯一的字段名 ***必传
                                        disabled: true,
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            {
                                                label: "购置单位一",
                                                value: "0"
                                            },
                                            {
                                                label: "购置单位二",
                                                value: "1"
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: "请选择",
                                    },
                                    {
                                        type: "string",
                                        label: "年份",
                                        field: "year", //唯一的字段名 ***必传
                                        required: true,
                                        placeholder: "请输入",
                                    },
                                    {
                                        type: "string",
                                        label: "因工死亡率(‰)",
                                        field: "deadRate", //唯一的字段名 ***必传
                                        required: true,
                                        placeholder: "请输入",
                                    },
                                    {
                                        type: "string",
                                        label: "重伤率(‰)",
                                        field: "injuresRate", //唯一的字段名 ***必传
                                        required: true,
                                        placeholder: "请输入",
                                    }
                                ]}
                                btns={[
                                    {
                                        name: "cancel",
                                        type: "dashed",
                                        label: "取消",
                                        field: 'cancel',
                                        isValidate: false,
                                        onClick: (obj) => {
                                            this.setState({ visible: false });
                                        }
                                    },
                                    {
                                        name: "submit",
                                        type: "primary",
                                        label: "确定",
                                        field: 'submit',
                                        onClick: (obj) => {
                                            obj.btnfns.fetchByCb('auditZxSfPlanTarget', obj.values, ({ data, success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.table.refresh();
                                                    this.setState({ visible: false });
                                                } else {
                                                    Msg.error(message);
                                                }
                                            });
                                        }
                                    }
                                ]}
                            />
                        </Modal>
                    </div> : null
                }
            </div>
        );
    }
}

export default index;