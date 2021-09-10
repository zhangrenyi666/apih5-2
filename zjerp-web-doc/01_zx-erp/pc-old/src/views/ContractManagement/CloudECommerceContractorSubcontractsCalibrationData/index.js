import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from '../../modules/qnn-table/qnn-form';
import { message as Msg, Modal } from "antd";
import s from './style.less';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.id
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
            visible: false
        }
    }
    render() {
        // const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { visible } = this.state;
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
                        apiName: 'getZxCtCloudEcoList'
                    }}
                    actionBtns={[
                        {
                            name: 'export',//内置add del
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '导出',
                            onClick:() => {
                                confirm({
                                    content: '确定导出数据吗?',
                                    onOk: () => {
                                        this.props.myFetch('', {}).then(({ success, message, data }) => {
                                            if (success) {
                                                Msg.success(message);
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    }
                                });
                            }
                        },
                        // {
                        //     name: 'Import',//内置add del
                        //     type: 'primary',//类型  默认 primary  [primary dashed danger]
                        //     label: '导入',
                        //     onClick:() => {
                        //         this.setState({
                        //             visible:true
                        //         })
                        //     }
                        // },
                        {
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZxCtCloudEco',
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
                            table: {
                                title: '序号',
                                dataIndex: 'index',
                                key: 'index',
                                fixed:'left',
                                width:50,
                                render:(data,rowData,index) => {
                                    return index + 1;
                                }
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '方案编号',
                                dataIndex: 'schemeNo',
                                key: 'schemeNo',
                                fixed:'left',
                                width:150,
                                filter:true,
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '方案名称',
                                dataIndex: 'schemeName',
                                key: 'schemeName',
                                fixed:'left',
                                filter:true,
                                width:150
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '工程类别',
                                dataIndex: 'projectType',
                                key: 'projectType',
                                width:100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '执行完成时间',
                                dataIndex: 'doFinishTime',
                                key: 'doFinishTime',
                                width:120,
                                format:'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '方案状态',
                                dataIndex: 'schemeStatus',
                                key: 'schemeStatus',
                                width:100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '三级单位',
                                dataIndex: 'thirdUnit',
                                key: 'thirdUnit',
                                width:100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '包件编号',
                                dataIndex: 'packageNo',
                                key: 'packageNo',
                                filter: true,
                                width:100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '包件名称',
                                dataIndex: 'packageName',
                                key: 'packageName',
                                filter: true,
                                width:100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '定价金额',
                                dataIndex: 'scaledAmount',
                                key: 'scaledAmount',
                                width:100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '投标单位',
                                dataIndex: 'winningUnit',
                                key: 'winningUnit',
                                width:100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '中标情况',
                                dataIndex: 'winRemark',
                                key: 'winRemark',
                                filter:true,
                                width:100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '包件需求单位',
                                dataIndex: 'demandUnit',
                                key: 'demandUnit',
                                width:120
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '社会统一信用代码证号',
                                dataIndex: 'orgCertificate',
                                key: 'orgCertificate',
                                width:180
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '是否匹配',
                                dataIndex: 'enable',
                                key: 'enable',
                                width:100,
                                filter:true,
                                render:(data) => {
                                    if(data === '1'){
                                        return '是';
                                    }else{
                                        return '否';
                                    }
                                }
                            },
                            form: {
                                type: 'radio',
                                optionData:[
                                    {
                                        label:'否',
                                        value:'0'
                                    },
                                    {
                                        label:'是',
                                        value:'1'
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remark',
                                key: 'remark',
                                width:150,
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入'
                            }
                        }
                    ]}
                />
                {visible ? <div>
                    <Modal
                        width={'500px'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="导入"
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
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 3 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 21 },
                                    sm: { span: 21 }
                                }
                            }}
                            formConfig={[
                                {
                                    label: '附件',
                                    field: 'fileList',
                                    type: 'files',
                                    required: true,
                                    fetchConfig: {
                                        apiName: 'upload'
                                    }
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
                                        obj.btnfns.fetchByCb('batchInputZxCtCloudEco', obj.values, ({ data, success, message }) => {
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
                </div> : null}
            </div>
        );
    }
}

export default index;