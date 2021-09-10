import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Row, Col, Modal } from "antd";
import moment from 'moment';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    paginationConfig: false,
    pageSize: 99,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            ext1: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.ext1,
            lockID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId && lockProject?.projectId !== 'all') ? lockProject.projectId : null,
            useOrgID: (curCompany?.ext1 === '1' || curCompany?.ext1 === '2') ? curCompany?.companyId : curCompany?.projectId,
            lockProject
        }
    }
    render() {
        let { myPublic: { appInfo: { ureport } } } = this.props;
        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
        const { departmentId, ext1, lockID, useOrgID, lockProject } = this.state;
        return (
            <div>
                <Row>
                    <Col span={24}>
                        <QnnForm
                            fetch={this.props.myFetch}
                            wrappedComponentRef={(me) => {
                                this.formHasTicket = me;
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
                                    label: '公司名称',
                                    field: 'companyIdSearch',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'companyName',
                                        value: 'companyId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyBySelect',
                                        otherParams: { departmentId }
                                    },
                                    placeholder: '请选择',
                                    span: 8,
                                    hide: ext1 === '1' && lockProject.projectId && lockProject.projectId === 'all' ? false : true
                                },
                                {
                                    type: 'month',
                                    label: '开始时间',
                                    field: 'regdateStart',
                                    span: 8
                                },
                                {
                                    type: 'month',
                                    label: '结束时间',
                                    field: 'regdateEnd',
                                    span: 8
                                },
                                {
                                    label: '设备分类',
                                    field: 'resCatalogID',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'catName',
                                        value: 'id',
                                    },
                                    fetchConfig: {
                                        apiName: "getZxEqResCategoryList",
                                        otherParams: {
                                            parentID: '0003',
                                            isGroup: '1'
                                        }
                                    },
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    label: '机械名称',
                                    type: 'string',
                                    field: 'equipName',
                                    placeholder: '请输入',
                                    span: 8
                                },
                                {
                                    type: 'component',
                                    field: 'aa',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'center', padding: '10px' }}><Button type="primary" onClick={() => {
                                                this.table.refresh();
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    confirm({
                                                        content: '确定导出数据吗?',
                                                        onOk: () => {
                                                            let value = this.formHasTicket.form.getFieldsValue();
                                                            let filter = []
                                                            !lockID  && filter.push('&orgID=' + useOrgID)
                                                            lockID && filter.push('&orgIDLock=' + lockID)
                                                            value.companyIdSearch && filter.push('&companyIdSearch=' + value.companyIdSearch)
                                                            value.resCatalogID && filter.push('&resCatalogID=' + value.resCatalogID)
                                                            value.equipName && filter.push('&equipName=' + value.equipName)
                                                            value.regdateStart && filter.push('&regdateStart=' + moment(value.regdateStart).valueOf())
                                                            value.regdateEnd && filter.push('&regdateEnd=' + moment(value.regdateEnd).valueOf())
                                                            var URL = `${ureport}excel?_u=minio:机械车辆使用情况报表.xml&access_token=${access_token}&delFlag=0${filter.join('')}&_n=机械车辆使用情况报表(公司)`;
                                                            window.open(URL);
                                                        }
                                                    })
                                                }}>导出</Button></div>
                                        );
                                    },
                                    span: 8
                                }
                            ]}
                        />
                    </Col>
                </Row>
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
                        apiName: 'getZxEqEWorkListForReport',
                        otherParams: () => {
                            let selectData = this.formHasTicket?.form?.getFieldsValue()
                            return {
                                orgID: lockID ? null : useOrgID,
                                orgIDLock: lockID ? lockID : null,
                                companyIdSearch: selectData?.companyIdSearch,
                                resCatalogID: selectData?.resCatalogID,
                                equipName: selectData?.equipName,
                                regdateStart: selectData?.regdateStart ? moment(selectData?.regdateStart).valueOf() : null,
                                regdateEnd: selectData?.regdateEnd ? moment(selectData?.regdateEnd).valueOf() : null
                            }
                        }
                    }}
                    formConfig={[
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
                                title: '分类',
                                dataIndex: 'resCatalogName',
                                key: 'resCatalogName',
                                width: 150
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '管理编号',
                                dataIndex: 'equipNo',
                                width: 170,
                                key: 'equipNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '机械名称',
                                width: 180,
                                dataIndex: 'equipName',
                                key: 'equipName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '技术规格',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '日历台日',
                                width: 120,
                                dataIndex: 'calendarNumDay',
                                key: 'calendarNumDay'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '完好情况',
                                dataIndex: 'A',
                                key: 'A',
                                children: [
                                    {
                                        title: '完好台日',
                                        dataIndex: 'wellDays',
                                        key: 'wellDays',
                                        width: 130,
                                    },
                                    {
                                        title: '完好率',
                                        dataIndex: 'wellPercen',
                                        key: 'wellPercen',
                                        width: 130,
                                    },

                                ]
                            }
                        },
                        {
                            table: {
                                title: '运转情况',
                                dataIndex: 'B',
                                key: 'B',
                                children: [
                                    {
                                        title: '运转台日',
                                        dataIndex: 'runDay',
                                        key: 'runDay',
                                        width: 130,
                                    },
                                    {
                                        title: '运转台时',
                                        dataIndex: 'runHour',
                                        key: 'runHour',
                                        width: 130,
                                    },

                                ]
                            }
                        },
                        {
                            table: {
                                title: '利用率',
                                width: 130,
                                dataIndex: 'runDayPercen',
                                key: 'runDayPercen'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '行驶里程',
                                width: 130,
                                dataIndex: 'loadMiles',
                                key: 'loadMiles'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '油耗情况（升）',
                                dataIndex: 'C',
                                key: 'C',
                                children: [
                                    {
                                        title: '汽油',
                                        dataIndex: 'gasoline',
                                        key: 'gasoline',
                                        width: 130,
                                    },
                                    {
                                        title: '柴油',
                                        dataIndex: 'diesel',
                                        key: 'diesel',
                                        width: 130,
                                    },

                                ]
                            }
                        },
                        {
                            table: {
                                title: '电消耗（度）',
                                width: 130,
                                dataIndex: 'consumption',
                                key: 'consumption'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '备注',
                                width: 180,
                                dataIndex: 'remark',
                                key: 'remark'
                            },
                            isInForm: false
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;