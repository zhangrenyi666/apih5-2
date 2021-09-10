import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import moment from 'moment';
import { Button, Row, Col, Modal } from "antd";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    paginationConfig: false,
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
                                    label: '单位名称',
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
                                    label: '项目名称',
                                    field: 'useOrgIDSearch',
                                    type: 'select',
                                    showSearch: true,
                                    hide: () => {
                                        if ((ext1 === '1' || ext1 === '2') && lockProject.projectId && lockProject.projectId === 'all') {
                                            return false
                                        }
                                        return true
                                    },
                                    dependencies: ['companyIdSearch'],
                                    dependenciesReRender: true,
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyProjectList',
                                        otherParams: (obj) => {
                                            if (ext1 === '1' && lockProject.projectId && lockProject.projectId === 'all') {
                                                return {
                                                    companyId: obj?.form?.getFieldValue('companyIdSearch')
                                                }
                                            }
                                            return {
                                                companyId: departmentId
                                            }
                                        }
                                    },
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    type: 'date',
                                    label: '开始时间',
                                    field: 'purDateStart',
                                    span: 8
                                },
                                {
                                    type: 'date',
                                    label: '结束时间',
                                    field: 'purDateEnd',
                                    span: 8
                                },
                                {
                                    label: '管理编号',
                                    field: 'equipNo',
                                    type: 'string',
                                    placeholder: '请输入',
                                    span: 8
                                },
                                {
                                    label: '车辆名称',
                                    field: 'equipName',
                                    type: 'string',
                                    placeholder: '请选择',
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
                                                            !lockID && !value.useOrgIDSearch && filter.push('&useOrgID=' + useOrgID)
                                                            !value.useOrgIDSearch && lockID && filter.push('&useOrgIDLock=' + lockID)
                                                            value.useOrgIDSearch && filter.push('&useOrgIDSearch=' + value.useOrgIDSearch)
                                                            value.companyIdSearch && filter.push('&companyIdSearch=' + value.companyIdSearch)
                                                            filter.push('&selectTypeForJiXieYunShuFlag=只查设备分类=运输机械类的数据')
                                                            value.purDateStart && filter.push('&purDateStart=' + moment(value.purDateStart).valueOf())
                                                            value.purDateEnd && filter.push('&purDateEnd=' + moment(value.purDateEnd).valueOf())
                                                            value.purDate && filter.push('&purDate=' + moment(value.purDate).valueOf())
                                                            value.equipNo && filter.push('&equipNo=' + value.equipNo)
                                                            value.equipName && filter.push('&equipName=' + value.equipName)
                                                            var URL = `${ureport}excel?_u=minio:zxEqEquipTotalListForCar.xml&access_token=${access_token}&delFlag=0&selectTypeFlag=car${filter.join('')}&_n=自有车辆配备情况统计表`;
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
                    pageSize={9999}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxEqEquipListForReport',
                        otherParams: () => {
                            let selectData = this.formHasTicket?.form?.getFieldsValue()
                            return {
                                useOrgID: lockID ? null : selectData?.useOrgIDSearch ? null : useOrgID,
                                useOrgIDLock: selectData?.useOrgIDSearch ? null : lockID,
                                useOrgIDSearch: selectData?.useOrgIDSearch,
                                selectTypeForJiXieYunShuFlag: '只查设备分类=运输机械类的数据',
                                companyIdSearch: selectData?.companyIdSearch,
                                equipName: selectData?.equipName,
                                equipNo: selectData?.equipNo,
                                purDateStart: selectData?.purDateStart ? moment(selectData.purDateStart).valueOf() : null,
                                purDateEnd: selectData?.purDateEnd ? moment(selectData?.purDateEnd).valueOf() : null,
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
                                title: '项目名称',
                                dataIndex: 'useOrgName',
                                key: 'useOrgName',
                                width: 200,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '管理编号',
                                dataIndex: 'equipNo',
                                width: 200,
                                key: 'equipNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '车辆名称',
                                width: 150,
                                dataIndex: 'equipName',
                                key: 'equipName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '型号',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '规格',
                                width: 120,
                                dataIndex: 'model',
                                key: 'model'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '生产厂家',
                                width: 180,
                                dataIndex: 'factory',
                                key: 'factory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '出厂日期',
                                width: 150,
                                dataIndex: 'outFactoryDate',
                                key: 'outFactoryDate',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '验收日期',
                                width: 120,
                                dataIndex: 'acceptanceDate',
                                key: 'acceptanceDate',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '原值（元）',
                                width: 120,
                                dataIndex: 'orginalValue',
                                key: 'orginalValue'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '净值（元）',
                                width: 120,
                                dataIndex: 'leftValue',
                                key: 'leftValue'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '车牌号',
                                width: 120,
                                dataIndex: 'leftValue',
                                key: 'leftValue'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '备注',
                                width: 200,
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