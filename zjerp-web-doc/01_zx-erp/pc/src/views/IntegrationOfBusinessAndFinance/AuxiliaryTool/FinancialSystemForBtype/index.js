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
                                title: '往来单位编号',
                                dataIndex: 'lswldwWldwbh',
                                key: 'lswldwWldwbh',
                                width: 150,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '单位名称',
                                dataIndex: 'lswldwDwmc',
                                key: 'lswldwDwmc',
                                width: 150,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '单位简称',
                                dataIndex: 'lswldwJc',
                                key: 'lswldwJc',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '地区分类编号',
                                dataIndex: 'lswldwDqbh',
                                key: 'lswldwDqbh',
                                width: 170,
                            }
                        },
                        {
                            table: {
                                title: '统一社会信用代码',
                                dataIndex: 'lswldwSh',
                                key: 'lswldwSh',
                                width: 180,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '数据创建时间',
                                dataIndex: 'lswldwDateTime',
                                key: 'lswldwDateTime',
                                width: 150,
                                format: 'YYYY-MM-DD-HH-MM-SS',
                            }
                        }
                    ]}
                />
            </div>
        )
    }
}

export default index;