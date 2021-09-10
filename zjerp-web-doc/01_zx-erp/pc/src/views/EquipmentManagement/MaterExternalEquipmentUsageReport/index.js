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
            orgID: (curCompany?.ext1 === '1' || curCompany?.ext1 === '2') ? curCompany?.companyId : curCompany?.projectId,
            lockProject
        }
    }
    render() {
        let { myPublic: { appInfo: { ureport } } } = this.props;
        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
        const { departmentId, ext1, lockID, orgID, lockProject } = this.state;
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
                                    field: 'comIDSearch',
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
                                    field: 'orgIDSearch',
                                    type: 'select',
                                    showSearch: true,
                                    hide: () => {
                                        if ((ext1 === '1' || ext1 === '2') && lockProject.projectId && lockProject.projectId === 'all') {
                                            return false
                                        }
                                        return true
                                    },
                                    dependencies: ['comIDSearch'],
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
                                                    companyId: obj?.form?.getFieldValue('comIDSearch')
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
                                    type: 'month',
                                    label: '统计开始期次',//缺少字段
                                    field: 'inDate',
                                    span: 8
                                },
                                {
                                    type: 'month',
                                    label: '统计结束期次',//缺少字段
                                    field: 'outDate',
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
                                                            !lockID && !value.orgIDSearch && filter.push('&orgID=' + orgID)
                                                            !value.orgIDSearch && lockID && filter.push('&orgIDLock=' + lockID)
                                                            value.orgIDSearch && filter.push('&orgIDSearch=' + value.orgIDSearch)
                                                            value.comIDSearch && filter.push('&comIDSearch=' + value.comIDSearch)
                                                            value.inDate && filter.push('&inDate=' + moment(value.inDate).valueOf())
                                                            value.outDate && filter.push('&outDate=' + moment(value.outDate).valueOf())
                                                            var URL = `${ureport}excel?_u=minio:租用外部机械设备使用情况表.xml&access_token=${access_token}&delFlag=0${filter.join('')}&_n=租用外部机械设备使用情况表`;
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
                        apiName: 'getZxEqOuterEquipListForCar',
                        otherParams: () => {
                            let selectData = this.formHasTicket?.form?.getFieldsValue()
                            return {
                                orgID: lockID ? null : selectData?.orgIDSearch ? null : orgID,
                                orgIDLock: selectData?.orgIDSearch ? null : lockID,
                                orgIDSearch: selectData?.orgIDSearch,
                                comIDSearch: selectData?.comIDSearch,
                                inDate: selectData?.inDate ? moment(selectData.inDate).valueOf() : null,
                                outDate: selectData?.outDate ? moment(selectData?.outDate).valueOf() : null
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
                                title: '公司名称',
                                dataIndex: 'comName',
                                key: 'comName   ',
                                width: 200
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'orgName',
                                width: 200,
                                key: 'orgName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '设备名称',
                                width: 200,
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
                                title: '功率KW',
                                width: 180,
                                dataIndex: 'power',
                                key: 'power'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '生产厂家',
                                width: 120,
                                dataIndex: 'outfactory',
                                key: 'outfactory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '出厂日期',
                                width: 120,
                                dataIndex: 'outfactoryDate',
                                key: 'outfactoryDate',
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
                                title: '使用地点',
                                width: 120,
                                dataIndex: 'place',
                                key: 'place',
                                type: 'select',
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName', //默认 label
                                    value: 'itemId',
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'shengfenjianchengdaima'
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '租赁期限（月）',
                                width: 120,
                                dataIndex: 'leaseLimit',
                                key: 'leaseLimit'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '使用时间',
                                dataIndex: 'A',
                                key: 'A',
                                children: [
                                    {
                                        title: '起始时间',
                                        dataIndex: 'inDateStr',
                                        key: 'inDateStr',
                                        width: 120,
                                    },
                                    {
                                        title: '结束时间',
                                        dataIndex: 'outDateStr',
                                        key: 'outDateStr',
                                        width: 120,
                                    },

                                ]
                            }
                        },
                        {
                            table: {
                                title: '租赁价格（元/月）',
                                width: 160,
                                dataIndex: 'leaseprice',
                                key: 'leaseprice'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '租赁公司/机主姓名',
                                width: 180,
                                dataIndex: 'supOperator',
                                key: 'supOperator'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '备注',
                                width: 150,
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