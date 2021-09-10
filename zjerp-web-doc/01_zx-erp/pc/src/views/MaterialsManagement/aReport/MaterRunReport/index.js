import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import { Modal } from "antd";
import { ExportOutlined } from '@ant-design/icons';
const config = {
    antd: {
        rowKey: 'id',
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight * 0.5
        }
    },
    drawerConfig: {
        width: '1200px'
    },
    searchFormColNum: 2,
    paginationConfig: false,
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
                        apiName: 'ureportZxSkReceivingDynamicList',
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
                                        let URL = `${ureport}excel?_u=minio:zxSkReceivingDynamic.xml&_n=物资动态账&access_token=${access_token}&orgID=${value.orgID ? value.orgID : ''}&resID=${value.resID ? value.resID : ''}&resTypeID=${value.resTypeID ? value.resTypeID : ''}&beginDate=${value.beginDate ? value.beginDate : ''}&endDate=${value.endDate ? value.endDate : ''}`;
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
                                label: '项目名称',
                                field: 'orgID',
                                type: 'select',
                                showSearch: true,
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams: {
                                        departmentId: jurisdiction
                                    }
                                },
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '材料类型',
                                field: 'resTypeID',
                                type: 'select',
                                // spanSearch: 12,
                                // formItemLayoutSearch: {
                                //     labelCol: {
                                //         xs: { span: 24 },
                                //         sm: { span: 12 }
                                //     },
                                //     wrapperCol: {
                                //         xs: { span: 24 },
                                //         sm: { span: 12 }
                                //     }
                                // },
                                showSearch: true,
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id'
                                },
                                fetchConfig: {
                                    apiName: 'getZxSkResCategoryMaterialsListResource',
                                    otherParams: {
                                        "parentOrgID": "1"
                                    }
                                },
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                label: '物资编码',
                                type: 'select',
                                // spanSearch: 12,
                                // formItemLayoutSearch: {
                                //     labelCol: {
                                //         xs: { span: 24 },
                                //         sm: { span: 12 }
                                //     },
                                //     wrapperCol: {
                                //         xs: { span: 24 },
                                //         sm: { span: 12 }
                                //     }
                                // },
                                // type: 'selectByQnnTable',
                                field: 'resID',
                                // dropdownMatchSelectWidth: 900,
                                dependenciesReRender: true,//多个依赖-配置
                                dependencies: ['resTypeID'],
                                optionConfig: {
                                    label: 'resName',
                                    value: 'id'
                                },
                                fetchConfig: {
                                    apiName: 'getZxSkResCategoryMaterialsAllResource',
                                    otherParams: (val) => {
                                        let resTypeID = '0002';
                                        if (val.btnCallbackFn?.form) {
                                            let aa = val.btnCallbackFn.form.getFieldsValue();
                                            if (aa.resTypeID) {
                                                resTypeID = aa.resTypeID;
                                            }
                                        } else {

                                        }
                                        return {
                                            id: resTypeID
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
                                type: 'date',
                                // spanSearch: 12,
                                // formItemLayoutSearch: {
                                //     labelCol: {
                                //         xs: { span: 24 },
                                //         sm: { span: 12 }
                                //     },
                                //     wrapperCol: {
                                //         xs: { span: 24 },
                                //         sm: { span: 12 }
                                //     }
                                // },
                                label: '开始时间',
                                field: 'beginDate'
                            },
                        },
                        {
                            isInTable: false,
                            isInSearch: true,
                            form: {
                                type: 'date',
                                // spanSearch: 12,
                                // formItemLayoutSearch: {
                                //     labelCol: {
                                //         xs: { span: 24 },
                                //         sm: { span: 12 }
                                //     },
                                //     wrapperCol: {
                                //         xs: { span: 24 },
                                //         sm: { span: 12 }
                                //     }
                                // },
                                label: '结束时间',
                                field: 'endDate'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'id',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '日期',
                                dataIndex: 'busDate',
                                key: 'busDate',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资编码',
                                dataIndex: 'resCode',
                                width: 200,
                                tooltip: 23,
                                key: 'resCode'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资名称',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'resName',
                                key: 'resName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '规格',
                                width: 120,
                                dataIndex: 'spec',
                                key: 'spec'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '计量单位',
                                width: 120,
                                dataIndex: 'resUnit',
                                key: 'resUnit'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '凭证号',
                                width: 180,
                                tooltip: 23,
                                dataIndex: 'voucherNo',
                                key: 'voucherNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '摘要',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'outOrgName',
                                key: 'outOrgName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '购入单价',
                                width: 120,
                                dataIndex: 'inPrice',
                                key: 'inPrice'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '收入',
                                dataIndex: 'spec2',
                                key: 'spec2',
                                children: [
                                    {
                                        title: '自行采购',
                                        dataIndex: 'serQty',
                                        key: 'serQty',
                                        width: 130,
                                    },
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
                                        dataIndex: 'unit',
                                        key: 'unit',
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'allInQty',
                                                key: 'allInQty',
                                                width: 120,
                                            },
                                            {
                                                title: '金额(元)',
                                                dataIndex: 'allInAmt',
                                                key: 'allInAmt',
                                                width: 120,
                                            }
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '发出',
                                dataIndex: 'spec1',
                                key: 'spec1',
                                children: [
                                    {
                                        title: '工程耗用',
                                        dataIndex: 'oswQty',
                                        key: 'oswQty',
                                        width: 130,
                                    },
                                    {
                                        title: '调拨',
                                        dataIndex: 'unit',
                                        key: 'unit',
                                        children: [
                                            // {
                                            //     title: '内调',
                                            //     dataIndex: 'otsQty',
                                            //     key: 'otsQty',
                                            //     width: 120,
                                            // },
                                            {
                                                title: '外调',
                                                dataIndex: 'otkQty',
                                                key: 'otkQty',
                                                width: 120,
                                            }
                                        ]
                                    },
                                    {
                                        title: '合计',
                                        dataIndex: 'unit1',
                                        key: 'unit1',
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'allOutQty',
                                                key: 'allOutQty',
                                                width: 120,
                                            },
                                            {
                                                title: '平均单价',
                                                dataIndex: 'oswPrice',
                                                key: 'oswPrice',
                                                width: 120,
                                            },
                                            {
                                                title: '金额(元)',
                                                dataIndex: 'allOutAmt',
                                                key: 'allOutAmt',
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
                                dataIndex: 'stockQty2',
                                key: 'stockQty2',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'vinoutQty',
                                        key: 'vinoutQty',
                                        width: 120,
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'vinoutAmt',
                                        key: 'vinoutAmt',
                                        width: 120,
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '结存',
                                dataIndex: 'stockQty',
                                key: 'stockQty',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'lastQty',
                                        key: 'lastQty',
                                        width: 120,
                                    },
                                    {
                                        title: '平均单价',
                                        dataIndex: 'lastPrice',
                                        key: 'lastPrice',
                                        width: 120,
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'lastAmt',
                                        key: 'lastAmt',
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