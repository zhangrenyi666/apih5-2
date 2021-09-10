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
                                title: '类别编号',
                                dataIndex: 'lshsxmLbbh',
                                key: 'lshsxmLbbh',
                                width: 150,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '项目类型',
                                dataIndex: 'lshsxmXmlx',
                                key: 'lshsxmXmlx',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '单位编号',
                                dataIndex: 'lshsxmDwbh',
                                key: 'lshsxmDwbh',
                                width: 150,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '类别名称',
                                dataIndex: 'lshsxmLbmc',
                                key: 'lshsxmLbmc',
                                width: 170,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '项目编号',
                                dataIndex: 'lshsxmXmbh',
                                key: 'lshsxmXmbh',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'lshsxmXmmc',
                                key: 'lshsxmXmmc',
                                width: 180,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '明细',
                                dataIndex: 'lshsxmMx',
                                key: 'lshsxmMx',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '分级码',
                                dataIndex: 'lshsxmFjm',
                                key: 'lshsxmFjm',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '级数',
                                dataIndex: 'lshsxmJs',
                                key: 'lshsxmJs',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: 'GS写入时间',
                                dataIndex: 'jstime',
                                key: 'jstime',
                                width: 150,
                                format: 'YYYY-MM-DD-HH-MM-SS',
                            }
                        },
                        {
                            table: {
                                title: '有效状态',
                                dataIndex: 'gsvalid',
                                key: 'gsvalid',
                                width: 150,
                            }
                        }
                    ]}
                />
            </div>
        )
    }
}

export default index;