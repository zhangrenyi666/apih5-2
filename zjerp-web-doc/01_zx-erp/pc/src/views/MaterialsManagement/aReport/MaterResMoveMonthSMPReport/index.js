import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import { Modal } from "antd";
import moment from 'moment';
import { ExportOutlined } from '@ant-design/icons';
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig:false,
    firstRowIsSearch: false,
    isShowRowSelect: false
};
const confirm = Modal.confirm;
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : ''
        }
    }
    render() {
        let { myPublic: { appInfo: { ureport } } } = this.props;
        const { ext1, departmentId, companyId, projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { lockProjectId } = this.state;
        let jurisdiction = departmentId;
        if (lockProjectId !== 'all' && lockProjectId !== '') {
            jurisdiction = lockProjectId;
        } else {
            if (ext1 === '1' || ext1 === '2') {
                jurisdiction = companyId;
            } else if (ext1 === '3' || ext1 === '4') {
                jurisdiction = projectId;
            } else { }
        }
        return (
            <div>
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
                        apiName: 'ureportZxSkResMoveMonthSMPList',
                        otherParams: {
                            projectId: jurisdiction
                        }
                    }}
                    topSearchExtendBtns={[
                        {
                            field: "btn1",
                            label: "导出",
                            onClick: (val) => {
                                if (val) {
                                    let value = val.searchData;
                                    value.then((data) => {
                                        let value = data;
                                        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                        var URL = `${ureport}excel?_u=minio:zxSkResMoveMonthSMP.ureport.xml&access_token=${access_token}&delFlag=0&_n=物资收发存汇总月报表${moment(new Date()).format('YYYY-MM-DD')}&orgID=${value.orgID ? value.orgID :''}&resID=${value.resID ? value.resID :''}&period=${value.period ? value.period : ''}`;
                                        confirm({
                                            content: '确定导出报表吗?',
                                            onOk: () => {
                                                window.open(URL);
                                            }
                                        });
                                    })
                                }
                            },
                            type: "primary",
                            icon: <ExportOutlined />
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '单位名称',
                                field: 'orgID',
                                type: 'select',
                                showSearch: true,
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams: {
                                        departmentId: jurisdiction
                                    }
                                },
                                placeholder: '请选择',
                                children: [{
                                    field: 'resID'
                                }]
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'select',
                                label: '材料类型',
                                field: 'resID',
                                showSearch: true,
                                dependenciesReRender: true,//多个依赖-配置
                                dependencies: ['orgID'],
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id'
                                },
                                fetchConfig: {
                                    apiName: 'getZxSkResCategoryMaterialsListResource',
                                    otherParams: (val) => {
                                        let orgIDVal = '';
                                        if (val.btnCallbackFn?.form) {
                                            let aa = val.btnCallbackFn.form.getFieldsValue();
                                            orgIDVal = aa.orgID;
                                        } else {

                                        }
                                        return {
                                            parentOrgID: orgIDVal
                                        }
                                    }
                                },
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'month',
                                label: '期次',
                                field: 'period'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSkResMoveMonthSMPId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '物资编号',
                                dataIndex: 'resCode',
                                key: 'resCode',
                                width: 200,
                                // tooltip: 23
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资名称',
                                dataIndex: 'resName',
                                width: 200,
                                // tooltip: 23,
                                key: 'resName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '上月结存',
                                dataIndex: 'stock1',
                                key: 'stock1',
                                children: [
                                    {
                                        title: '上月结存数量',
                                        width: 200,
                                        dataIndex: 'stockQty',
                                        key: 'stockQty'
                                    },
                                    {
                                        title: '上月结存平均单价（元）',
                                        dataIndex: 'stockPrice',
                                        key: 'stockPrice',
                                        width: 200,
                                    },
                                    {
                                        title: '上月结存金额（元）',
                                        width: 200,
                                        dataIndex: 'stockAmt',
                                        key: 'stockAmt'
                                    }
                                ]
                            }
                        },

                        {
                            table: {
                                title: '收入',
                                dataIndex: 'spec',
                                key: 'spec',
                                children: [
                                    {
                                        title: '甲供',
                                        dataIndex: 'orsQty',
                                        key: 'orsQty',
                                        width: 130,
                                    },
                                    {
                                        title: '其它',
                                        dataIndex: 'otrQty',
                                        key: 'otrQty',
                                        width: 130,
                                    },
                                    {
                                        title: '自购',
                                        dataIndex: 'serQty',
                                        key: 'serQty',
                                        width: 130,
                                    },
                                    {
                                        title: '预收',
                                        dataIndex: 'obuQty',
                                        key: 'obuQty',
                                        width: 130,
                                    },
                                    {
                                        title: '甲控',
                                        dataIndex: 'ocsQty',
                                        key: 'ocsQty',
                                        width: 130,
                                    },
                                    {
                                        title: '合计',
                                        dataIndex: 'resUnit',
                                        key: 'resUnit',
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'inQty',
                                                key: 'inQty',
                                                width: 120,
                                            },
                                            {
                                                title: '平均单价(元)',
                                                dataIndex: 'inPrice',
                                                key: 'inPrice',
                                                width: 120,
                                            },
                                            {
                                                title: '金额(元)',
                                                dataIndex: 'inAmt',
                                                key: 'inAmt',
                                                width: 120,
                                            }
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '工程耗用',
                                dataIndex: 'spec',
                                key: 'spec',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'oswQty',
                                        key: 'oswQty',
                                        width: 130,
                                    },
                                    {
                                        title: '平均单价（元）',
                                        dataIndex: 'oswPrice',
                                        key: 'oswPrice',
                                        width: 130,
                                    },
                                    {
                                        title: '金额（元）',
                                        dataIndex: 'oswAmt',
                                        key: 'oswAmt',
                                        width: 130,
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '调拨',
                                dataIndex: 'spec',
                                key: 'spec',
                                children: [
                                    // {
                                    //     title: '加工改制',
                                    //     dataIndex: 'trsQty',
                                    //     key: 'trsQty',
                                    //     width: 120,
                                    // },
                                    {
                                        title: '调拨',
                                        dataIndex: 'otkQty',
                                        key: 'otkQty',
                                        width: 120,
                                    },
                                    {
                                        title: '合计',
                                        dataIndex: 'unit1',
                                        key: 'unit1',
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'otkQtys',
                                                key: 'otkQtys',
                                                width: 120,
                                            },
                                            {
                                                title: '平均单价（元）',
                                                dataIndex: 'otkPrice',
                                                key: 'otkPrice',
                                                width: 120,
                                            },
                                            {
                                                title: '金额(元)',
                                                dataIndex: 'outAmt',
                                                key: 'outAmt',
                                                width: 120,
                                            }
                                        ]
                                    }
                                ]
                            }
                        },

                        {
                            table: {
                                title: '盈(+)亏(-)',
                                dataIndex: 'spce',
                                key: 'spce',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'vinQty',
                                        key: 'vinQty',
                                        width: 120,
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'vinAmt',
                                        key: 'vinAmt',
                                        width: 120,
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '本月结存',
                                dataIndex: 'stockQty',
                                key: 'stockQty',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'thisQty',
                                        key: 'thisQty',
                                        width: 120,
                                    },
                                    {
                                        title: '平均单价',
                                        dataIndex: 'thisPrice',
                                        key: 'thisPrice',
                                        width: 120,
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'thisAmt',
                                        key: 'thisAmt',
                                        width: 120,
                                    }
                                ]
                            },
                            isInForm: false
                        }
                    ]}
                    actionBtns={[]}
                />
            </div>
        );
    }
}

export default index;