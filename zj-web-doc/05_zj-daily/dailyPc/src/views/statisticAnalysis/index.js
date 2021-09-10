import React, { Component } from 'react';
import QnnTable from '../modules/qnn-table';
import QnnForm from "../modules/qnn-table/qnn-form";
import { Form } from '@ant-design/compatible';
import '@ant-design/compatible/assets/index.css';
import { DatePicker, Button, message as Msg, Modal, Spin } from 'antd';
import moment from "moment";
const config = {
    fetchConfig: {
        apiName: "totalZjDailyProFillList",
    },
    antd: {
        rowKey: "proFillId",
        size: "small"
    },
    drawerConfig: {
        width: 1000
    },
    formConfig: [
        {
            isInTable: false,
            form: {
                field: 'proFillId',
                type: 'string',
                placeholder: '请输入',
                hide: true
            }
        },
        {
            isInTable: false,
            form: {
                field: 'proInfoId',
                type: 'string',
                placeholder: '请输入',
                hide: true
            }
        },
        {
            isInTable: false,
            form: {
                field: 'proDetailId',
                type: 'string',
                placeholder: '请输入',
                hide: true
            }
        },
        {
            table: {
                title: '项目名称',
                dataIndex: 'proName',
                key: 'proName',
                width: 200,
                fixed: "left",
                tooltip: 12
            },
            form: {
                type: 'string',
                placeholder: '请输入',
                hide: true
            },
        },
        {
            table: {
                title: '收费站',
                dataIndex: 'tollStation',
                key: 'tollStation',
                width: 100,
                fixed: "left",
                tooltip: 6
            },
            form: {
                type: 'string',
                placeholder: '请输入',
                hide: true
            },
        },
        {
            isInTable: false,
            form: {
                label: '通车日期',
                field: 'openTime',
                type: 'date',
                placeholder: '请选择',
                hide: true
            }
        },
        {
            isInTable: false,
            form: {
                label: '股权比例(%)',
                field: 'equityRatio',
                type: 'number',
                placeholder: '请输入',
                hide: true
            },
        },
        {
            table: {
                title: '填报日期',
                dataIndex: 'fillDate',
                key: 'fillDate',
                format: "YYYY-MM-DD",
                width: 100,
                fixed: "left",
            },
            form: {
                label: '填报日期',
                field: 'fillDate',
                type: 'date',
                placeholder: '请选择',
                hide: true
            }
        },
        {
            isInTable: false,
            form: {
                label: '门架ETC计费金额',
                field: 'cash1',
                type: 'number',
                placeholder: '请输入',
                hide: true,
            }
        },
        {
            isInTable: false,
            form: {
                label: '门架CPC计费金额',
                field: 'cash2',
                type: 'number',
                placeholder: '请输入',
                hide: true,
            }
        },
        {
            isInTable: false,
            form: {
                label: '入口车次客车',
                field: 'entr1',
                type: 'number',
                placeholder: '请输入',
                hide: true,
            }
        },
        {
            isInTable: false,
            form: {
                label: '入口车次货车',
                field: 'entr2',
                type: 'number',
                placeholder: '请输入',
                hide: true,
            }
        },
        {
            isInTable: false,
            form: {
                label: '出口车次客车',
                field: 'export1',
                type: 'number',
                placeholder: '请输入',
                hide: true,
            }
        },
        {
            isInTable: false,
            form: {
                label: '出口车次货车',
                field: 'export2',
                type: 'number',
                placeholder: '请输入',
                hide: true,
            }
        },
        {
            table: {
                title: '门架ETC计费金额(万元)',
                dataIndex: 'cash1',
                key: 'cash1',
                // width: 100,
                render: (data) => {
                    if (data) {
                        return (data / 10000).toFixed(2);
                    } else {
                        return (0).toFixed(2);
                    }
                }
            },
            form: {
                type: 'number',
                placeholder: '请输入',
                hide: true
            },
        },
        {
            table: {
                title: '门架CPC计费金额(万元)',
                dataIndex: 'cash2',
                key: 'cash2',
                render: (data) => {
                    if (data) {
                        return (data / 10000).toFixed(2);
                    } else {
                        return (0).toFixed(2);
                    }
                }
            },
            form: {
                type: 'number',
                placeholder: '请输入',
                hide: true
            },
        },
        {
            table: {
                title: '当日合计收入(万元)',
                dataIndex: 'cashAllTotal',
                key: 'cashAllTotal',
                render: (data) => {
                    if (data) {
                        return (data / 10000).toFixed(2);
                    } else {
                        return (0).toFixed(2);
                    }
                }
            },
            form: {
                type: 'number',
                placeholder: '请输入',
                hide: true
            },
        },
        {
            table: {
                title: '本年度拆分累计收入(万元)',
                dataIndex: 'ext2',
                key: 'ext2',
                render: (data) => {
                    if (data) {
                        return (data / 10000).toFixed(2);
                    } else {
                        return (0).toFixed(2);
                    }
                }
            },
            form: {
                type: 'number',
                placeholder: '请输入',
                hide: true
            },
        },
        {
            table: {
                title: '本年度拆分日均收入(万元)',
                dataIndex: 'ext3',
                key: 'ext3',
                render: (data) => {
                    if (data) {
                        return (data / 10000).toFixed(2);
                    } else {
                        return (0).toFixed(2);
                    }
                }
            },
            form: {
                type: 'number',
                placeholder: '请输入',
                hide: true
            },
        },
        {
            table: {
                title: '工可日收入(万元)',
                dataIndex: 'ext1',
                key: 'ext1',
                render: (data) => {
                    if (data) {
                        return (data / 10000).toFixed(2);
                    } else {
                        return (0).toFixed(2);
                    }
                }
            },
            form: {
                label: '工可日收入(元)',
                type: 'number',
                min: 0,
                precision: 2,
                required: true,
                placeholder: '请输入',
            }
        },
        {
            table: {
                title: '实际/工可(%)',
                dataIndex: 'ext4',
                key: 'ext4',
                // render:(data,rowData) => {
                //     return (rowData.ext3/rowData.ext1).toFixed(2);
                // },
                width: 80
            },
            form: {
                type: 'string',
                placeholder: '请输入',
                hide: true
            },
        },
        {
            table: {
                title: '当日入口车次',
                dataIndex: 'entrTotal',
                key: 'entrTotal',
                // width: 100,
            },
            form: {
                type: 'number',
                placeholder: '请输入',
                hide: true
            },
        },
        {
            table: {
                title: '当日出口车次',
                dataIndex: 'exportTotal',
                key: 'exportTotal',
                // width: 100,
            },
            form: {
                type: 'number',
                placeholder: '请输入',
                hide: true
            },
        },
        {
            table: {
                title: '当日绿通车',
                dataIndex: '当日绿通车',
                width: 160,
                children: [
                    {
                        width: 80,
                        title: '车次',
                        dataIndex: 'green1',
                    },
                    {
                        width: 80,
                        title: '减免金额',
                        dataIndex: 'green2',
                    }
                ]
            },
            isInForm: false
        },
        {
            isInTable: false,
            form: {
                field: 'green1',
                type: 'number',
                placeholder: '请输入',
                hide: true
            }
        },
        {
            isInTable: false,
            form: {
                field: 'green2',
                type: 'number',
                placeholder: '请输入',
                hide: true
            }
        },
        {
            table: {
                title: '当日合计车流量',
                dataIndex: 'allTotal',
                key: 'allTotal',
                // width: 100,
            },
            form: {
                type: 'number',
                placeholder: '请输入',
                hide: true
            },
        },
        {
            table: {
                title: '发送状态',
                dataIndex: 'sendFlag',
                width: 80,
                fixed: "right",
                render: (data) => {
                    if (data === '0') {
                        return '未发送';
                    } else if (data === '1') {
                        return '已发送';
                    } else {
                        return '-';
                    }
                }
            },
            isInForm: false
        },
        {
            table: {
                title: '备注',
                dataIndex: 'remarks',
                key: 'remarks',
                width:100,
                tooltip:6,
                fixed: "right"
            },
            isInForm: false
        },
        {
            isInForm: false,
            table: {
                fixed: "right",
                width: 80,
                title: '操作',
                showType: "tile",
                dataIndex: 'action',
                btns: [
                    {
                        name: "edit",
                        render: function (obj) {
                            if (obj.rowData.sendFlag === '1') {
                                return '';
                            } else {
                                return '修改';
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
                                label: '提交',//提交数据并且关闭右边抽屉
                                fetchConfig: {//ajax配置  ---可为func
                                    apiName: 'updateZjDailyProFill',
                                }
                            }
                        ],
                    }
                ]
            }
        }
    ]
}
class Page extends Component {
    state = { loading: false, visible: false, visibles: false, toData: moment(new Date).valueOf(), modalLoading: false }
    handleSubmit = e => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (values.datetime) {
                const date = moment(values.datetime).valueOf();
                this.setState({ modalLoading: true, visible: true, toData: date })
            } else {
                Msg.error('请选择时间')
            }
        });
    };
    export = _ => {
        this.props.form.validateFields((err, values) => {
            if (values.datetime) {
                let { myPublic: { domain, appInfo: { ureport } } } = this.props;
                const date = moment(values.datetime).valueOf();
                window.location.href = `${ureport}excel?_u=file:zjDailyProFillList.xml&url=${domain}&fillDate=${date}`
                // this.setState({ modalLoading: true,visible: true,toData: date })
            } else {
                Msg.error('请选择时间')
            }
        });
    };
    send = _ => {
        if (this.table.getSelectedRows().length) {
            for (let i = 0; i < this.table.getSelectedRows().length; i++) {
                if (this.table.getSelectedRows()[i].sendFlag === '1') {
                    Msg.error('已发送数据无需发送！');
                    this.table.clearSelectedRows();
                    break;
                } else if (i === (this.table.getSelectedRows().length - 1)) {
                    this.setState({
                        visibles: true
                    })
                }
            }
        } else {
            Msg.error('请选择发送数据！');
        }
    };
    handleCancel = () => {
        this.setState({ visible: false });
    }
    render() {
        let { myPublic: { domain, appInfo: { ureport } } } = this.props;
        return (
            <div style={{ height: '100%', overflow: "hidden" }} id="yiQingDiaoChaPro">
                <Form layout="inline" onSubmit={this.handleSubmit}>
                    <Form.Item label="导出（预览）日期">
                        {this.props.form.getFieldDecorator('datetime', {
                            initialValue: moment(new Date)
                        })(
                            <DatePicker format="YYYY-MM-DD" />,
                        )}
                    </Form.Item>

                    <Form.Item>
                        <Button type="primary" htmlType="submit" loading={this.state.loading}>
                            预览
                        </Button>
                    </Form.Item>

                    <Form.Item>
                        <Button type="primary" onClick={this.export}>
                            导出
                        </Button>
                    </Form.Item>

                    <Form.Item>
                        <Button type="primary" onClick={this.send}>
                            发送
                        </Button>
                    </Form.Item>

                </Form>

                <QnnTable
                    form={this.props.form}
                    history={this.props.history}
                    match={this.props.match}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    // headers={{ token: token }}
                    wrappedComponentRef={(me) => { this.table = me }}
                    method={{}}
                    componentsKey={{}}
                    {...config}
                />

                {this.state.visible ? <Modal
                    width={'90%'}
                    style={{ paddingBottom: '0', top: '0' }}
                    title="报表预览"
                    visible={this.state.visible}
                    footer={null}
                    onCancel={this.handleCancel}
                    bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                    centered={true}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={this.state.modalLoading}>
                        <iframe title="myf" width='100%' height={window.innerHeight * 0.8} frameBorder={0} onLoad={(obj) => {
                            this.setState({ modalLoading: false })
                        }} src={`${ureport}preview?_u=file:zjDailyProFillList.xml&url=${domain}&fillDate=${this.state.toData}`}></iframe>
                    </Spin>
                </Modal> : null}

                {this.state.visibles ? <div>
                    <Modal
                        width={'40%'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="发送"
                        visible={this.state.visibles}
                        footer={null}
                        onCancel={this.handleCancel}
                        bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                        centered={true}
                        closable={false}
                        maskClosable={false}
                        wrapClassName={'modals'}
                    >
                        <QnnForm
                            form={this.props.form}
                            history={this.props.history}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //必须返回一个promise
                            //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            formConfig={[
                                {
                                    label: '人员',
                                    field: 'sendPersonList',
                                    type: 'treeSelect',
                                    required: true,
                                    treeSelectOption: {
                                        selectType: "2",
                                        fetchConfig: {//配置后将会去请求下拉选项数据
                                            apiName: 'getSysDepartmentUserAllTree'
                                        }
                                    }
                                }
                            ]}
                            btns={[
                                {
                                    name: "cancel",
                                    type: "dashed",
                                    label: "取消",
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visibles: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "确定",
                                    onClick: (obj) => {
                                        let body = this.table.getSelectedRows().map((item) => {
                                            item.sendPersonList = obj.values.sendPersonList;
                                            return item;
                                        })
                                        obj.btnfns.myFetch('sendZjDailyProFill', body, ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.setState({ visibles: false });
                                                this.table.clearSelectedRows();
                                                this.table.refresh();
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
            </div>)
    }
}
export default Form.create()(Page);