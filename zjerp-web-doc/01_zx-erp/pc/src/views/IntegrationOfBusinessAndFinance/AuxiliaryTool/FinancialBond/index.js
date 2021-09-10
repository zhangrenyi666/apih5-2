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
                                title: '编号',
                                dataIndex: 'qwzqzwbBh',
                                key: 'qwzqzwbBh',
                                width: 170,
                            }
                        },
                        {
                            table: {
                                title: '单位内码',
                                dataIndex: 'qwzqzwbDwnm',
                                key: 'qwzqzwbDwnm',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '对方单位编号',
                                dataIndex: 'dFdwbh',
                                key: 'dFdwbh',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '款项性质内码',
                                dataIndex: 'kxxznm',
                                key: 'kxxznm',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '款项性质名称',
                                dataIndex: 'kxxzmc',
                                key: 'kxxzmc',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '债务债权余额',
                                dataIndex: 'qwzqzwyebYe',
                                key: 'qwzqzwyebYe',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '摘要',
                                dataIndex: 'zy',
                                key: 'zy',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '合同编号',
                                dataIndex: 'htbh',
                                key: 'htbh',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '合同名称',
                                dataIndex: 'htmc',
                                key: 'htmc',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '往来单位名称',
                                dataIndex: 'dfdwmc',
                                key: 'dfdwmc',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '项目编号',
                                dataIndex: 'xmbh',
                                key: 'xmbh',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'xmmc',
                                key: 'xmmc',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '金额',
                                dataIndex: 'je',
                                key: 'je',
                                width: 120,
                            }
                        }
                    ]}
                />
            </div>
        )
    }
}

export default index;