import React, { Component } from "react";
import QnnTable from '../modules/qnn-table';
import moment from 'moment';
const config = {
    antd: {
        rowKey: function (row) {
            return row.scoreId
        },
        size: 'small',
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    isShowRowSelect: false
}
class Index extends Component {
    render() {
        const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <div>
                <QnnTable
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZjXmJxAssessmentUserScoreListByAuditee',
                        otherParams: {
                            projectId: projectId
                        }
                    }}
                    formConfig={[
                        {
                            table: {
                                title: '年月',
                                dataIndex: 'yearMonth',
                                key: 'yearMonth',
                                filter: true,
                                width: 100,
                                fixed:'left',
                                render: (data) => {
                                    return moment(data).format('YYYY-MM');
                                }
                            },
                            form: {
                                type: 'month',
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '考核标题',
                                dataIndex: 'assessmentTitle',
                                key: 'assessmentTitle',
                                filter: true,
                                width: 200,
                                fixed:'left',
                                tooltip:12
                            },
                            form: {
                                type: 'string',
                                placeholder: "请输入"
                            }
                        },
                        {
                            table: {
                                title: '修改时间',
                                dataIndex: 'modifyTime',
                                key: 'modifyTime',
                                format: 'YYYY-MM-DD',
                                width: 100
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '任务考核得分及权重',
                                dataIndex: 'taskStr',
                                key: 'taskStr',
                                width: 150
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '周边考核得分及权重',
                                dataIndex: 'peripheryStr',
                                key: 'peripheryStr',
                                width: 150
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '正职考核得分及权重',
                                dataIndex: 'principalStr',
                                key: 'principalStr',
                                width: 150
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '综合评价(扣分)',
                                dataIndex: 'compositeTotalScore',
                                key: 'compositeTotalScore',
                                width: 120
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '系统自动扣分',
                                dataIndex: 'systemTotalScore',
                                key: 'systemTotalScore',
                                width: 120
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '综合得分',
                                dataIndex: 'totalScore',
                                key: 'totalScore',
                                width: 100
                            },
                            isInForm: false
                        }
                    ]}
                />
            </div>
        );
    }
}
export default Index;