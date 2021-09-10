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
            year: moment(new Date()).valueOf()
        }
    }
    render() {
        const { year } = this.state;
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
                            label: '考核年份',
                            field: 'year',
                            type: 'year',
                            initialValue: year,
                            onChange: (val) => {
                                this.setState({
                                    year: moment(val).valueOf()
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
                        apiName: 'getZjXmJxAnnualAssessmentSummaryList',
                        otherParams: {
                            projectId: projectId,
                            year: year
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
                                    year: year,
                                    fileName:'年度项目员工考核评分汇总'
                                }
                                let URL = `${domain + "exportRankZjXmJxAnnualAssessmentSummary"}`;
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
                                title: '考核年份',
                                dataIndex: 'year',
                                key: 'year',
                                format: 'YYYY',
                                width: 100
                            },
                            form: {
                                type: 'year',
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '1月得分',
                                dataIndex: 'januaryScore',
                                key: 'januaryScore',
                                width: 100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '2月得分',
                                dataIndex: 'februaryScore',
                                key: 'februaryScore',
                                width: 100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '3月得分',
                                dataIndex: 'marchScore',
                                key: 'marchScore',
                                width: 100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '4月得分',
                                dataIndex: 'aprilScore',
                                key: 'aprilScore',
                                width: 100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '5月得分',
                                dataIndex: 'mayScore',
                                key: 'mayScore',
                                width: 100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '6月得分',
                                dataIndex: 'juneScore',
                                key: 'juneScore',
                                width: 100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '7月得分',
                                dataIndex: 'julyScore',
                                key: 'julyScore',
                                width: 100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '8月得分',
                                dataIndex: 'augustScore',
                                key: 'augustScore',
                                width: 100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '9月得分',
                                dataIndex: 'septemberScore',
                                key: 'septemberScore',
                                width: 100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '10月得分',
                                dataIndex: 'octoberScore',
                                key: 'octoberScore',
                                width: 100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '11月得分',
                                dataIndex: 'novemberScore',
                                key: 'novemberScore',
                                width: 100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '12月得分',
                                dataIndex: 'decemberScore',
                                key: 'decemberScore',
                                width: 100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '年度平均分',
                                dataIndex: 'averageScore',
                                key: 'averageScore',
                                width: 100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '列为末位人员的月份',
                                dataIndex: 'lastPersonMonth',
                                key: 'lastPersonMonth',
                                width: 150,
                                tooltip:10
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
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