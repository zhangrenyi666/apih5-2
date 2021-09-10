import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import moment from 'moment';
import downLoad from "../modules/download";
import { Modal, Tabs, message as Msg } from 'antd';
const { TabPane } = Tabs;
const confirm = Modal.confirm;
const config1 = {
    antd: {
        rowKey: function (row) {
            return row.detailedId
        },
        size: 'small',
        scroll: {
            y: window.innerHeight - 386
        }
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    isShowRowSelect: false
};
class index extends Component {
    constructor() {
        super();
        this.state = {
            activeKey: '1',
            yearMonth: moment(new Date()).subtract(1, 'months').valueOf(),
            formConfig: null
        }
    }
    tabsCallback = (activeKey) => {
        this.setState({
            activeKey,
            yearMonth: moment(new Date()).subtract(1, 'months').valueOf(),
            formConfig: null
        }, () => {
            if (this.state.activeKey === '2') {
                this.refresh();
            }
        })
    }
    refresh = () => {
        const { yearMonth } = this.state;
        const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        this.props.myFetch('getZjXmJxPeripheryScoreDetailedPeripheryExcelColumn', { auditeeProId: projectId, yearMonth: yearMonth }).then(({ data, success, message }) => {
            if (success) {
                let datas = [{
                    isInTable: false,
                    form: {
                        field: 'detailedId',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    table: {
                        title: '序号',
                        dataIndex: 'index',
                        key: 'index',
                        width: 50,
                        fixed: 'left',
                        render: (data, rowData, index) => {
                            return index + 1;
                        }
                    },
                    isInForm: false
                }];
                data.map((item, index) => {
                    if (item['姓名']) {
                        datas.push({
                            table: {
                                title: '姓名',
                                dataIndex: item['姓名'],
                                key: item['姓名'],
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        })
                    } else if (item['身份证号码']) {
                        datas.push({
                            table: {
                                title: '身份证号码',
                                dataIndex: item['身份证号码'],
                                key: item['身份证号码'],
                                width: 160
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        })
                    } else if (item['打分类别']) {
                        datas.push({
                            table: {
                                title: '打分类别',
                                dataIndex: item['打分类别'],
                                key: item['打分类别'],
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        })
                    } else {
                        for (let key in item) {
                            datas.push({
                                table: {
                                    title: item[key],
                                    dataIndex: item[key],
                                    key: item[key],
                                    width: 100
                                },
                                form: {
                                    type: 'number',
                                    placeholder: '请输入'
                                }
                            })
                        }
                    }
                    return item;
                })
                this.setState({
                    formConfig: datas
                })
            } else {
                Msg.error(message)
            }
        })
    }
    render() {
        const { yearMonth, activeKey, formConfig } = this.state;
        const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <div>
                <Tabs activeKey={activeKey} onChange={this.tabsCallback}>
                    <TabPane tab="任务业绩得分明细表" key="1">
                        {activeKey === '1' ? <div>
                            <QnnForm
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload} //必须返回一个promise
                                //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                                headers={{
                                    token: this.props.loginAndLogoutInfo.loginInfo.token
                                }}
                                wrappedComponentRef={(me) => {
                                    this.form = me;
                                }}
                                style={{ height: '68px', overflow: 'hidden' }}
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
                                        label: '考核月份',
                                        field: 'yearMonth',
                                        type: 'month',
                                        initialValue: yearMonth,
                                        onChange: (val) => {
                                            this.setState({
                                                yearMonth: moment(val).valueOf()
                                            })
                                        },
                                        allowClear:false,
                                        placeholder: '请选择',
                                        span: 8
                                    }
                                ]}
                            />
                            <QnnTable
                                {...this.props}
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => {
                                    this.table = me;
                                }}
                                {...config1}
                                fetchConfig={{
                                    apiName: 'getZjXmJxTaskScoreDetailedTaskExcel',
                                    otherParams: {
                                        auditeeProId: projectId,
                                        yearMonth: yearMonth
                                    }
                                }}
                                actionBtns={[
                                    {
                                        name: 'export',//内置add del
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        label: '导出',
                                        onClick: () => {
                                            const {
                                                loginAndLogoutInfo: {
                                                    loginInfo: { token }
                                                },
                                                myPublic: { domain }
                                            } = this.props;
                                            let body = {
                                                auditeeProId: projectId,
                                                yearMonth: yearMonth,
                                                fileName: '任务业绩得分明细表'
                                            }
                                            let URL = `${domain + "exportZjXmJxTaskScoreDetailedTaskExcel"}`;
                                            confirm({
                                                title: `您确定要导出数据么?`,
                                                content: `取消导出请点击取消按钮。`,
                                                okText: "确认",
                                                cancelText: "取消",
                                                onOk: () => {
                                                    downLoad(URL, body, { token });
                                                },
                                            });
                                        }
                                    }
                                ]}
                                formConfig={[
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'detailedId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '序号',
                                            dataIndex: 'index',
                                            key: 'index',
                                            width: 50,
                                            fixed: 'left',
                                            render: (data, rowData, index) => {
                                                return index + 1;
                                            }
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '姓名',
                                            dataIndex: 'auditeeName',
                                            key: 'auditeeName',
                                            width: 100
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '身份证号码',
                                            dataIndex: 'idNumber',
                                            key: 'idNumber',
                                            width: 160
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '指标名称',
                                            dataIndex: 'costDutyIndex',
                                            key: 'costDutyIndex',
                                            width: 300
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '权重',
                                            dataIndex: 'weightValue',
                                            key: 'weightValue',
                                            width: 100
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '得分',
                                            dataIndex: 'score',
                                            key: 'score',
                                            width: 100
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '任务业绩总得分',
                                            dataIndex: 'taskScore',
                                            key: 'taskScore',
                                            width: 150
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '请输入'
                                        }
                                    }
                                ]}
                            />
                        </div> : null}
                    </TabPane>
                    <TabPane tab="周边业绩得分明细表" key="2">
                        {activeKey === '2' ? <div>
                            <QnnForm
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload} //必须返回一个promise
                                //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                                headers={{
                                    token: this.props.loginAndLogoutInfo.loginInfo.token
                                }}
                                wrappedComponentRef={(me) => {
                                    this.form = me;
                                }}
                                style={{ height: '68px', overflow: 'hidden' }}
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
                                        label: '考核月份',
                                        field: 'yearMonth',
                                        type: 'month',
                                        initialValue: yearMonth,
                                        onChange: (val) => {
                                            this.setState({
                                                yearMonth: moment(val).valueOf(),
                                                formConfig: null
                                            }, () => {
                                                this.refresh();
                                            })
                                        },
                                        allowClear:false,
                                        placeholder: '请选择',
                                        span: 8
                                    }
                                ]}
                            />
                            {formConfig ? <QnnTable
                                {...this.props}
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => {
                                    this.table = me;
                                }}
                                {...config1}
                                fetchConfig={{
                                    apiName: 'getZjXmJxPeripheryScoreDetailedPeripheryExcel',
                                    otherParams: {
                                        auditeeProId: projectId,
                                        yearMonth: yearMonth
                                    }
                                }}
                                actionBtns={[
                                    {
                                        name: 'export',//内置add del
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        label: '导出',
                                        onClick: () => {
                                            const {
                                                loginAndLogoutInfo: {
                                                    loginInfo: { token }
                                                },
                                                myPublic: { domain }
                                            } = this.props;
                                            let body = {
                                                auditeeProId: projectId,
                                                yearMonth: yearMonth,
                                                fileName: '周边业绩得分明细表'
                                            }
                                            let URL = `${domain + "exportZjXmJxPeripheryScoreDetailedPeripheryExcel"}`;
                                            confirm({
                                                title: `您确定要导出数据么?`,
                                                content: `取消导出请点击取消按钮。`,
                                                okText: "确认",
                                                cancelText: "取消",
                                                onOk: () => {
                                                    downLoad(URL, body, { token });
                                                },
                                            });
                                        }
                                    }
                                ]}
                                formConfig={formConfig}
                            /> : null}
                        </div> : null}
                    </TabPane>
                    <TabPane tab="正职得分明细表" key="3">
                        {activeKey === '3' ? <div>
                            <QnnForm
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload} //必须返回一个promise
                                //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                                headers={{
                                    token: this.props.loginAndLogoutInfo.loginInfo.token
                                }}
                                wrappedComponentRef={(me) => {
                                    this.form = me;
                                }}
                                style={{ height: '68px', overflow: 'hidden' }}
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
                                        label: '考核月份',
                                        field: 'yearMonth',
                                        type: 'month',
                                        initialValue: yearMonth,
                                        onChange: (val) => {
                                            this.setState({
                                                yearMonth: moment(val).valueOf()
                                            })
                                        },
                                        allowClear:false,
                                        placeholder: '请选择',
                                        span: 8
                                    }
                                ]}
                            />
                            <QnnTable
                                {...this.props}
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => {
                                    this.table = me;
                                }}
                                {...config1}
                                fetchConfig={{
                                    apiName: 'getZjXmJxPrincipalScoreDetailedPrincipalExcel',
                                    otherParams: {
                                        auditeeProId: projectId,
                                        yearMonth: yearMonth
                                    }
                                }}
                                actionBtns={[
                                    {
                                        name: 'export',//内置add del
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        label: '导出',
                                        onClick: () => {
                                            const {
                                                loginAndLogoutInfo: {
                                                    loginInfo: { token }
                                                },
                                                myPublic: { domain }
                                            } = this.props;
                                            let body = {
                                                auditeeProId: projectId,
                                                yearMonth: yearMonth,
                                                fileName: '正职得分明细表'
                                            }
                                            let URL = `${domain + "exportZjXmJxPrincipalScoreDetailedPrincipalExcel"}`;
                                            confirm({
                                                title: `您确定要导出数据么?`,
                                                content: `取消导出请点击取消按钮。`,
                                                okText: "确认",
                                                cancelText: "取消",
                                                onOk: () => {
                                                    downLoad(URL, body, { token });
                                                },
                                            });
                                        }
                                    }
                                ]}
                                formConfig={[
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'detailedId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '序号',
                                            dataIndex: 'index',
                                            key: 'index',
                                            width: 50,
                                            fixed: 'left',
                                            render: (data, rowData, index) => {
                                                return index + 1;
                                            }
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '姓名',
                                            dataIndex: 'auditeeName',
                                            key: 'auditeeName',
                                            width: 100
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '身份证号码',
                                            dataIndex: 'idNumber',
                                            key: 'idNumber',
                                            width: 160
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '经理打分',
                                            dataIndex: 'managerScore',
                                            key: 'managerScore',
                                            width: 100
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '书记打分',
                                            dataIndex: 'secretaryScore',
                                            key: 'secretaryScore',
                                            width: 100
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '请输入'
                                        }
                                    }
                                ]}
                            />
                        </div> : null}
                    </TabPane>
                    <TabPane tab="综合评价扣分明细表" key="4">
                    {activeKey === '4' ? <div>
                            <QnnForm
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload} //必须返回一个promise
                                //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                                headers={{
                                    token: this.props.loginAndLogoutInfo.loginInfo.token
                                }}
                                wrappedComponentRef={(me) => {
                                    this.form = me;
                                }}
                                style={{ height: '68px', overflow: 'hidden' }}
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
                                        label: '考核月份',
                                        field: 'yearMonth',
                                        type: 'month',
                                        initialValue: yearMonth,
                                        onChange: (val) => {
                                            this.setState({
                                                yearMonth: moment(val).valueOf()
                                            })
                                        },
                                        allowClear:false,
                                        placeholder: '请选择',
                                        span: 8
                                    }
                                ]}
                            />
                            <QnnTable
                                {...this.props}
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => {
                                    this.table = me;
                                }}
                                {...config1}
                                fetchConfig={{
                                    apiName: 'getZjXmJxAssessmentUserScoreCompositeExcel',
                                    otherParams: {
                                        auditeeProId: projectId,
                                        yearMonth: yearMonth
                                    }
                                }}
                                actionBtns={[
                                    {
                                        name: 'export',//内置add del
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        label: '导出',
                                        onClick: () => {
                                            const {
                                                loginAndLogoutInfo: {
                                                    loginInfo: { token }
                                                },
                                                myPublic: { domain }
                                            } = this.props;
                                            let body = {
                                                auditeeProId: projectId,
                                                yearMonth: yearMonth,
                                                fileName: '综合评价扣分明细表'
                                            }
                                            let URL = `${domain + "exportZjXmJxAssessmentUserScoreCompositeExcel"}`;
                                            confirm({
                                                title: `您确定要导出数据么?`,
                                                content: `取消导出请点击取消按钮。`,
                                                okText: "确认",
                                                cancelText: "取消",
                                                onOk: () => {
                                                    downLoad(URL, body, { token });
                                                },
                                            });
                                        }
                                    }
                                ]}
                                formConfig={[
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'detailedId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '序号',
                                            dataIndex: 'index',
                                            key: 'index',
                                            width: 50,
                                            fixed: 'left',
                                            render: (data, rowData, index) => {
                                                return index + 1;
                                            }
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '姓名',
                                            dataIndex: 'auditeeName',
                                            key: 'auditeeName',
                                            width: 100
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '身份证号码',
                                            dataIndex: 'auditeeIdNumber',
                                            key: 'auditeeIdNumber',
                                            width: 160
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '扣分分值',
                                            dataIndex: 'score',
                                            key: 'score',
                                            width: 100
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '请输入'
                                        }
                                    }
                                ]}
                            />
                        </div> : null}
                    </TabPane>
                </Tabs>
            </div>
        );
    }
}

export default index;