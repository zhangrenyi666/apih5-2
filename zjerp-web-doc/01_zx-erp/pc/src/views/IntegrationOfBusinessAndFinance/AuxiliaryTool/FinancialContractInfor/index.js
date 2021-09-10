import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    paginationConfig: {
        position: 'bottom'
    },
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
        }
    }
    render() {
        const { departmentId } = this.state;
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxSaProjectSettleAuditList',
                        otherParams: { orgID: departmentId }
                    }}
                    drawerShowToggle={(obj) => {
                        let { drawerIsShow } = obj;
                        if (!drawerIsShow) {
                            obj.btnCallbackFn.refresh();
                            obj.btnCallbackFn.clearSelectedRows()
                        }
                    }}
                    formConfig={[
                        {
                            table: {
                                title: '合同ID',
                                dataIndex: 'zjxmhtbNm',
                                key: 'zjxmhtbNm',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '合同编号',
                                dataIndex: 'zjxmhtbBh',
                                key: 'zjxmhtbBh',
                                width: 170,
                            }
                        },
                        {
                            table: {
                                title: '合同名称',
                                dataIndex: 'zjxmhtbHtmc',
                                key: 'zjxmhtbHtmc',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '合同金额',
                                dataIndex: 'zjxmhtbJe',
                                key: 'zjxmhtbJe',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '单位内码',
                                dataIndex: 'zjxmhtbDwnm',
                                key: 'zjxmhtbDwnm',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '项目内码',
                                dataIndex: 'zjxmhtbXmnm',
                                key: 'zjxmhtbXmnm',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'zjxmhtbXmmc',
                                key: 'zjxmhtbXmmc',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '开累金额',
                                dataIndex: 'klzfje',
                                key: 'klzfje',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '计税方式',
                                dataIndex: 'jsff',
                                key: 'jsff',
                                width: 120,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    { label: '一般计税方法', value: '1' },
                                    { label: '简易计税方法', value: '2' }
                                ],

                            }
                        },
                        {
                            table: {
                                title: '税率',
                                dataIndex: 'zjxmhtbSl',
                                key: 'zjxmhtbSl',
                                width: 120,
                            }
                        },
                        {
                            table: {
                                title: '合同状态',
                                dataIndex: 'htzt',
                                key: 'htzt',
                                width: 100,
                                render: (h) => {
                                    if (h === '1') {
                                        return '执行中'
                                    } else if (h === '3') {
                                        return '终止'
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '发票类型',
                                dataIndex: 'fplx',
                                key: 'fplx',
                                width: 100,
                                render: (h) => {
                                    if (h === '0') {
                                        return '增值税专用发票'
                                    } else if (1 === '3') {
                                        return '增值税普通发票'
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '收支方向',
                                dataIndex: 'szfx',
                                key: 'szfx',
                                width: 100,
                                render: (h) => {
                                    if (h === '01') {
                                        return '收入'
                                    } else if (1 === '02') {
                                        return '支出'
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '重点合同',
                                dataIndex: 'sfzd',
                                key: 'sfzd',
                                width: 100,
                                render: (h) => {
                                    if (h === '0') {
                                        return '否'
                                    } else if (1 === '1') {
                                        return '是'
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '制式合同',
                                dataIndex: 'sfzs',
                                key: 'sfzs',
                                width: 100,
                                render: (h) => {
                                    if (h === '0') {
                                        return '否'
                                    } else if (1 === '1') {
                                        return '是'
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '预付款比例(%)',
                                dataIndex: 'yfkbl',
                                key: 'yfkbl',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '进度款比例(%)',
                                dataIndex: 'jdkbl',
                                key: 'jdkbl',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '竣工款比例(%)',
                                dataIndex: 'jgkbl',
                                key: 'jgkbl',
                                width: 150,
                            }
                        },
                    ]}
                />
            </div>
        )
    }
}

export default index;