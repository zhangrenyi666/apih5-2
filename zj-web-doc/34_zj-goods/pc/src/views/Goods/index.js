import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";

import moment from 'moment';
import { Divider, Modal, message as Msg, Spin } from 'antd';
import s from "./style.less";
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: 'goodsId',
        size: "small"
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: false
};

class index extends Component {
    constructor() {
        super();
        this.state = {
            loading: false,
            visible: false,
            visibleSend: false,
            loadingSend:false
        }
    }
    componentDidMount() {

    }
    handleCancel = () => {
        this.setState({ visible: false,loading:false });
    }
    handleCancelSend = () => {
        this.setState({ visibleSend: false,loadingSend:false });
    }
    render() {
        return (
            <div className={s.root}>
                <div>
                    <QnnTable
                        {...this.props}
                        fetch={this.props.myFetch} 
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        fetchConfig={{
                            apiName: 'getZjGoodsList'
                        }}
                        wrappedComponentRef={(me) => {
                            this.table = me;
                        }}
                        {...config}
                        actionBtns={[
                            {
                                name: 'diyAdd',
                                icon: 'plus',
                                type: 'primary',
                                label: '导入',
                                onClick: () => {
                                    this.setState({
                                        visible:true
                                    })
                                }
                            },
                            {
                                name: 'diyAddsend',
                                icon: 'plus',
                                type: 'primary',
                                label: '发送',
                                onClick: () => {
                                    this.setState({
                                        visibleSend:true
                                    })
                                }
                            },
                        ]}
                        formConfig={[
                            {
                                isInTable: false,
                                form: {
                                    field: 'goodsId',
                                    hide: true,
                                    type: 'string'
                                }
                            },
                            {
                                table: {
                                    title: '单位',
                                    dataIndex: 'unit',
                                    key: 'unit'
                                },
                                isInForm:false
                            },
                            {
                                table: {
                                    title: '姓名',
                                    dataIndex: 'name',
                                    key: 'name'
                                },
                                isInForm:false
                            },
                            {
                                table: {
                                    title: '年份',
                                    dataIndex: 'yearName',
                                    key: 'yearName'
                                },
                                isInForm:false
                            },
                            {
                                table: {
                                    title: '金额',
                                    dataIndex: 'money',
                                    key: 'money'
                                },
                                isInForm:false
                            }, 
                           
                            {
                                table: {
                                    title: '小组长',
                                    dataIndex: 'header',
                                    key: 'header'
                                },
                                isInForm:false
                            },
                            {
                                table: {
                                    title: '发送时间',
                                    dataIndex: 'sendTime',
                                    key: 'sendTime',
                                    render: function (data) {
                                        return data ? moment(data).format('YYYY-MM-DD'):''
                                    }
                                },
                                isInForm:false
                            },
                            {
                                table: {
                                    title: '发送状态',
                                    dataIndex: 'sendFlag',
                                    key: 'sendFlag',
                                    render: function (data) {
                                        if (data === '0') {
                                            return '未发送'
                                        } else if (data === '1'){
                                            return '已发送'
                                        } else {
                                            return '发送失败'
                                        }
                                        
                                    }
                                },
                                isInForm:false
                            },
                            {
                                table: {
                                    title: '导入时间',
                                    dataIndex: 'createTime',
                                    key: 'createTime',
                                    render: function (data) {
                                        return data ? moment(data).format('YYYY-MM-DD'):''
                                    }
                                },
                                isInForm:false
                            }
                        ]}
                        
                    />
                </div>
                <div>
                    <Modal
                        width='450px'
                        style={{ top: '0' }}
                        title="导入"
                        visible={this.state.visible}
                        footer={null}
                        onCancel={this.handleCancel}
                        bodyStyle={{ width:'450px'}}
                        centered={true}
                        destroyOnClose={this.handleCancel}
                        wrapClassName={'modals'}
                    >
                        <Spin spinning={this.state.loading}>
                        <QnnForm
                            {...this.props} 
                                match={this.props.match}
                                fetch={this.props.myFetch} 
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                formConfig={[
                                    {
                                        label: '年份',
                                        type: 'select',
                                        field: 'yearId',
                                        required: true,
                                        format:'YYYY',
                                        placeholder: '请选择',
                                        fetchConfig: {
                                            apiName: "getZjGoodsYearList"
                                        },
                                        optionConfig: {
                                            label: 'year',
                                            value: 'yearId',
                                        },
                                    },
                                    {
                                        label: '附件',
                                        required: true,
                                        field: 'zjGoodsList',
                                        type: 'files',
                                        fetchConfig: {
                                            apiName: window.configs.domain + 'upload'
                                        }
                                    }
                                ]}
                                btns={[
                                    // {
                                    //     name: 'cancel',
                                    //     type: 'dashed',
                                    //     label: '取消',
                                    //     onClick: (obj) => {
                                    //         this.setState({
                                    //             visible:false
                                    //         })
                                    //     }
                                    // },
                                    {
                                        name: 'submit',
                                        type: 'primary',
                                        label: '提交',
                                        onClick: (obj) => {
                                            this.setState({
                                                loading:true
                                            })
                                            this.props.myFetch('batchInputZjGoods',obj.values).then(
                                                ({ success,data }) => {
                                                    if (success) {
                                                        Msg.success('成功导入' + data + '人');
                                                        this.table.refresh();
                                                        this.setState({
                                                            visible: false,
                                                            loading:false
                                                        })
                                                    } else {
                                                        Msg.error('导入失败')
                                                    }
                                                }
                                            );
                                            
                                        }
                                    }
                            ]}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 4 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 20 }
                                }
                            }}
                            />
                            </Spin>
                    </Modal>
                    <Modal
                        width='450px'
                        style={{ top: '0' }}
                        title="发送"
                        visible={this.state.visibleSend}
                        footer={null}
                        onCancel={this.handleCancelSend}
                        bodyStyle={{ width:'450px'}}
                        centered={true}
                        destroyOnClose={this.handleCancelSend}
                        wrapClassName={'modals'}
                    >
                        <Spin spinning={this.state.loadingSend}>
                        <QnnForm
                            {...this.props} 
                                match={this.props.match}
                                fetch={this.props.myFetch} 
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                formConfig={[
                                    {
                                        label: '年份',
                                        type: 'select',
                                        field: 'yearId',
                                        required: true,
                                        format:'YYYY',
                                        placeholder: '请选择',
                                        fetchConfig: {
                                            apiName: "getZjGoodsYearList"
                                        },
                                        optionConfig: {
                                            label: 'year',
                                            value: 'yearId',
                                        },
                                    },
                                ]}
                                btns={[
                                    // {
                                    //     name: 'cancel',
                                    //     type: 'dashed',
                                    //     label: '取消',
                                    //     onClick: (obj) => {
                                    //         this.setState({
                                    //             visibleSend:false
                                    //         })
                                    //     }
                                    // },
                                    {
                                        name: 'submit',
                                        type: 'primary',
                                        label: '提交',
                                        onClick: (obj) => {
                                            this.setState({
                                                loadingSend:true
                                            })
                                            this.props.myFetch('getZjGoodsSendNum',obj.values).then(
                                                ({ success,data }) => {
                                                    if (success) {
                                                        confirm({
                                                            title: '确定发送'+data+'人?',
                                                            content: ``,
                                                            okText: "确认",
                                                            cancelText: "取消",
                                                            centered:true,
                                                            onOk: () => {
                                                                this.props.myFetch("sendZjGoods",obj.values).then(
                                                                    ({ success,code,message,data }) => {
                                                                        if (success) {
                                                                            Msg.warn('有'+data+'人发送失败')
                                                                            this.table.refresh();
                                                                            this.setState({
                                                                                visibleSend: false,
                                                                                loadingSend:false
                                                                            })
                                                                        }
                                                                    }
                                                                );
                                                            },
                                                            onCancel: () => {
                                                                this.setState({
                                                                    // visibleSend: false,
                                                                    loadingSend:false
                                                                })
                                                            }
                                                        });
                                                        
                                                    } else {
                                                        Msg.error('查询失败')
                                                    }
                                                }
                                            );
                                            
                                        }
                                    }
                            ]}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 4 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 20 }
                                }
                            }}
                            />
                            </Spin>
                    </Modal>
                </div>
            </div>
        );
    }
}

export default index;