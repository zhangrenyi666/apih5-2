import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import moment from 'moment';
import downLoad from "../modules/download";
import { Modal } from 'antd'
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.summaryId
        },
        size: 'small',
        scroll: {
            y: window.innerHeight - 340
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
            yearMonth: moment(new Date()).subtract(1, 'months').valueOf()
        }
    }
    render() {
        const { yearMonth } = this.state;
        const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <div>
                <QnnForm
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
                            label: '考核年月',
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
                    {...config}
                    fetchConfig={{
                        apiName: 'getZjXmJxMonthlyAssessmentSummaryList',
                        otherParams: {
                            projectId: projectId,
                            summaryType: '2',
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
                                    projectId: projectId,
                                    summaryType: '2',
                                    yearMonth: yearMonth,
                                    fileName:'月度考核汇总末位人员'
                                }
                                let URL = `${domain + "exportLastZjXmJxMonthlyAssessmentSummary"}`;
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
                                field: 'summaryId',
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
                                title: '项目名称',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                width: 200,
                                tooltip: 12,
                                onClick: 'detail',
                                fixed: 'left',
                                filter: true,
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '姓名',
                                dataIndex: 'realName',
                                key: 'realName',
                                width: 100,
                                fixed: 'left',
                                filter: true,
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '组内排序',
                                dataIndex: 'groupSort',
                                key: 'groupSort',
                                width: 80,
                                fixed: 'left',
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '身份证号码',
                                dataIndex: 'idNumber',
                                key: 'idNumber',
                                width: 160,
                                filter: true,
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '部门',
                                dataIndex: 'deptName',
                                key: 'deptName',
                                width: 120,
                                tooltip: 6,
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '岗位',
                                dataIndex: 'position',
                                key: 'position',
                                width: 180,
                                tooltip: 6,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'jobType'
                                    }
                                },
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '岗位类别(末位人员)',
                                dataIndex: 'lastPositionType',
                                key: 'lastPositionType',
                                width: 250,
                                tooltip: 6,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "局聘及公司聘项目副职",
                                        value: "1"
                                    },
                                    {
                                        label: "局聘及公司聘副总师及部门负责人",
                                        value: "2"
                                    },
                                    {
                                        label: "局聘及公司聘副部长及其他人员",
                                        value: "3"
                                    },
                                    {
                                        label: "所有项目聘人员",
                                        value: "4"
                                    }
                                ],
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '人员类别',
                                dataIndex: 'userType',
                                key: 'userType',
                                width: 100,
                                type: 'select',
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "局聘",
                                        value: "1"
                                    },
                                    {
                                        label: "公司聘",
                                        value: "2"
                                    },
                                    {
                                        label: "项目聘",
                                        value: "3"
                                    }
                                ],
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '考核月份',
                                dataIndex: 'yearMonth',
                                key: 'yearMonth',
                                format: 'YYYY-MM',
                                width: 100
                            },
                            form: {
                                type: 'month',
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '任务业绩考核',
                                dataIndex: 'taskScore',
                                key: 'taskScore',
                                width: 120
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '周边业绩考核',
                                dataIndex: 'peripheryScore',
                                key: 'peripheryScore',
                                width: 120
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '项目正职评价',
                                dataIndex: 'principalScore',
                                key: 'principalScore',
                                width: 120
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '综合管理评价扣分',
                                dataIndex: 'compositeScore',
                                key: 'compositeScore',
                                width: 150
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '月度考核得分',
                                dataIndex: 'totalScore',
                                key: 'totalScore',
                                width: 120
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '是否为本月末位人员',
                                dataIndex: 'monthlyLastPerson',
                                key: 'monthlyLastPerson',
                                width: 150,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "否",
                                        value: "0"
                                    },
                                    {
                                        label: "是",
                                        value: "1"
                                    }
                                ],
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remarks',
                                key: 'remarks',
                                width: 150
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入'
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;