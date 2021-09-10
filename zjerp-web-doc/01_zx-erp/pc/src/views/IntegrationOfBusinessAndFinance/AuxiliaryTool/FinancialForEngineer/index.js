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
                                title: '工程项目内码',
                                dataIndex: 'zjgcxmNm',
                                key: 'zjgcxmNm',
                                width: 150,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '单位内码',
                                dataIndex: 'zjgcxmDwnm',
                                key: 'zjgcxmDwnm',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '项目编号',
                                dataIndex: 'zjgcxmXmbh',
                                key: 'zjgcxmXmbh',
                                width: 150,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '往来单位名称',
                                dataIndex: 'zjgcxmWldwmc',//缺少字段
                                key: 'zjgcxmWldwmc',
                                width: 170,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'zjgcxmXmmc',
                                key: 'zjgcxmXmmc',
                                width: 180,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '最后修改时间',
                                dataIndex: 'zjxmhtb_Modifiedtime',
                                key: 'zjxmhtb_Modifiedtime',
                                width: 180,
                            }
                        }
                    ]}
                />
            </div>
        )
    }
}

export default index;