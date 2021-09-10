import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal, Row, Col } from "antd";
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
                                    label: '单位名称',
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
                                    label: '文化程度',
                                    type: 'select',
                                    field: 'eduLevel',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: "博士",
                                            value: "0"
                                        },
                                        {
                                            label: "硕士",
                                            value: "1"
                                        },
                                        {
                                            label: "本科",
                                            value: "2"
                                        },
                                        {
                                            label: "大专",
                                            value: "3"
                                        },
                                        {
                                            label: "中专",
                                            value: "4"
                                        },
                                        {
                                            label: "高中",
                                            value: "5"
                                        },
                                        {
                                            label: "初中",
                                            value: "6"
                                        },
                                        {
                                            label: "小学",
                                            value: "7"
                                        }
                                    ],
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    label: '职称',
                                    type: 'select',
                                    field: 'title',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'resCode'
                                    },
                                    optionData: [
                                        {
                                            label: '无',
                                            resCode: '01'
                                        },
                                        {
                                            label: '初级',
                                            resCode: '02'
                                        },
                                        {
                                            label: '中级',
                                            resCode: '03'
                                        },
                                        {
                                            label: '高级',
                                            resCode: '04'
                                        }
                                    ],
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    type: 'string',
                                    label: '姓名',
                                    field: 'name',
                                    span: 8
                                },
                                {
                                    type: 'select',
                                    label: '岗位',
                                    field: 'pos',
                                    span: 8,
                                    optionConfig: {
                                        label: 'jobName',
                                        value: 'jobName'
                                    },
                                    fetchConfig: {
                                        apiName: 'getZxEqMachineJobsList',
                                    },
                                },
                                {
                                    type: 'date',
                                    label: '填报起始时间',
                                    field: 'bzDateStart',
                                    span: 8
                                },
                                {
                                    type: 'date',
                                    label: '填报终止时间',
                                    field: 'bzDateEnd',
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
                                                            value.title && filter.push('&title=' + value.title)
                                                            value.bzDateStart && filter.push('&bzDateStart=' + new Date(value.bzDateStart._d).getTime())
                                                            value.bzDateEnd && filter.push('&bzDateEnd=' + new Date(value.bzDateEnd._d).getTime())
                                                            value.name && filter.push('&name=' + value.name)
                                                            value.pos && filter.push('&pos=' + value.pos)
                                                            value.eduLevel && filter.push('&eduLevel=' + value.eduLevel)
                                                            var URL = `${ureport}excel?_u=minio:zxEqEemployeeList.xml&access_token=${access_token}&delFlag=0${filter.join('')}&_n=机械设备人员表`;
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
                        apiName: 'ureportZxEqEemployeeListIdle',
                        otherParams: async () => {
                            let selectData = await this.formHasTicket?.form?.getFieldsValue()
                            return {
                                orgID: lockID ? null : selectData?.orgIDSearch ? null : orgID,
                                orgIDLock: selectData?.orgIDSearch ? null : lockID,
                                orgIDSearch: selectData?.orgIDSearch,
                                comIDSearch: selectData?.comIDSearch,
                                title: selectData?.title,
                                name: selectData?.name,
                                pos: selectData?.pos,
                                eduLevel: selectData?.eduLevel,
                                bzDateStart: selectData?.bzDateStart ? moment(selectData.bzDateStart).valueOf() : null,
                                bzDateEnd: selectData?.bzDateEnd ? moment(selectData.bzDateEnd).valueOf() : null,
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
                                dataIndex: 'projectName',
                                key: 'projectName',
                                width: 200,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '姓名',
                                dataIndex: 'name',
                                width: 200,
                                key: 'name'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '年龄',
                                width: 150,
                                dataIndex: 'age',
                                key: 'age'
                            },
                            isInForm: false
                        },  
                        {
                            table: {
                                title: '文化程度',
                                width: 120,
                                dataIndex: 'eduLevel',
                                key: 'eduLevel',
                                render: (h) => {
                                    if (h === '0') {
                                        return '博士'
                                    } else if (h === '1') {
                                        return '硕士'
                                    } else if (h === '2') {
                                        return '本科'
                                    } else if (h === '3') {
                                        return '大专'
                                    } else if (h === '4') {
                                        return '中专'
                                    } else if (h === '5') {
                                        return '高中'
                                    } else if (h === '6') {
                                        return '初中'
                                    } else if (h === '7') {
                                        return '小学'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '职称',
                                width: 120,
                                dataIndex: 'title',
                                key: 'title',
                                render: (h) => {
                                    if (h === '01') {
                                        return '无'
                                    } else if (h === '02') {
                                        return '初级'
                                    } else if (h === '03') {
                                        return '中级'
                                    } else if (h === '04') {
                                        return '高级'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '所在岗位',
                                width: 200,
                                dataIndex: 'pos',
                                key: 'pos'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '本岗工龄',
                                width: 120,
                                dataIndex: 'posAge',
                                key: 'posAge'
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '备注',
                                width: 120,
                                dataIndex: 'remark',
                                key: 'remark'
                            },
                            isInForm: false
                        },

                    ]}
                />
            </div>
        );
    }
}

export default index;