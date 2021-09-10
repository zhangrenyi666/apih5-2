import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal } from "antd";
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: 'ctContrApplyId',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    pageSize: 99999999,
    paginationConfig: false,
    formItemLayout: {
        labelCol: {
            xs: { span: 3 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 21 },
            sm: { span: 21 }
        }
    },
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            projectId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
            formData: {
                companyId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
                projectId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId
            }
        }
    }
    render() {
        const { departmentId, formData, projectId } = this.state;
        const { departmentName, companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <div>
                <QnnForm
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                        token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    wrappedComponentRef={(me) => {
                        this.form = me;
                    }}
                    data={formData}
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
                            label: '所属事业部',
                            field: 'contractDepart',
                            type: 'select',
                            optionConfig: {
                                label: 'label',
                                value: 'value',
                            },
                            optionData: [
                                {
                                    label: "公路市政事业部",
                                    value: "glsz"
                                },
                                {
                                    label: "铁路轨道事业部",
                                    value: "tlgd"
                                },
                                {
                                    label: "城市房建事业部",
                                    value: "csfj"
                                }
                            ],
                            span: 6
                        },
                        {
                            label: '公司名称',
                            field: 'companyId',
                            type: 'select',
                            optionConfig: {
                                label: 'companyName',
                                value: 'companyId'
                            },
                            fetchConfig: {
                                apiName: 'getSysCompanyBySelectByDept',
                                otherParams: {
                                    departmentId: departmentId
                                }
                            },
                            disabled:curCompany?.ext1 === '3' || curCompany?.ext1 === '4' || projectId ? true : false,
                            showSearch:true,
                            allowClear: false,
                            span: 6
                        },
                        {
                            label: '项目名称',
                            field: 'projectId',
                            type: 'select',
                            optionConfig: {
                                label: 'departmentName',
                                value: 'departmentId'
                            },
                            parent: 'companyId',
                            fetchConfig: {
                                apiName: 'getSysProjectList',
                                params: {
                                    departmentId: 'companyId'
                                }
                            },
                            disabled:curCompany?.ext1 === '3' || curCompany?.ext1 === '4' || projectId ? true : false,
                            showSearch: true,
                            allowClear: formData?.projectId ? false : true,
                            span: 6
                        },
                        {
                            label: '合同编号',
                            field: 'contractNo',
                            type: 'selectByPaging',
                            optionConfig: {
                                label: 'contractNo',
                                value: 'ctContrApplyId',
                                linkageFields: {
                                    contractName: 'ctContrApplyId'
                                }
                            },
                            fetchConfig: {
                                apiName: 'psGetZxGcsgCtContrApplyList',
                                searchKey:'contractNo',
                                otherParams: {
                                    orgID: departmentId,
                                    companyName:companyName,
                                    projectName:departmentName
                                }
                            },
                            onChange: (val, obj) => {
                                if (!val) {
                                    obj.form.setFieldsValue({
                                        contractName: null
                                    })
                                }
                            },
                            span: 6
                        },
                        {
                            label: '合同名称',
                            field: 'contractName',
                            type: 'selectByPaging',
                            optionConfig: {
                                label: 'contractName',
                                value: 'ctContrApplyId',
                                linkageFields: {
                                    contractNo: 'ctContrApplyId'
                                }
                            },
                            fetchConfig: {
                                apiName: 'psGetZxGcsgCtContrApplyList',
                                searchKey:'contractName',
                                otherParams: {
                                    orgID: departmentId,
                                    companyName:companyName,
                                    projectName:departmentName
                                }
                            },
                            onChange: (val, obj) => {
                                if (!val) {
                                    obj.form.setFieldsValue({
                                        contractNo: null
                                    })
                                }
                            },
                            span: 6
                        },
                        {
                            label: '上报日期',
                            field: 'reportTime',
                            type: 'rangeDate',
                            span: 6
                        },
                        {
                            label: '审批日期',
                            field: 'approvalTime',
                            type: 'rangeDate',
                            span: 6
                        },
                        {
                            type: 'component',
                            field: 'component',
                            Component: obj => {
                                return (
                                    <div style={{ padding: '10px' }}><Button type="primary" onClick={() => {
                                        let value = this.form.form.getFieldsValue();
                                        if (value?.reportTime?.length) {
                                            value.reportStartTime = moment(value.reportTime[0]).startOf('date').valueOf();
                                            value.reportEndTime = moment(value.reportTime[1]).endOf('date').valueOf();
                                        }
                                        if (value?.approvalTime?.length) {
                                            value.approvalStartTime = moment(value.approvalTime[0]).startOf('date').valueOf();
                                            value.approvalEndTime = moment(value.approvalTime[1]).endOf('date').valueOf();
                                        }
                                        if (value?.contractNo) {
                                            value.ctContrApplyId = value.contractNo;
                                        } else if (value?.contractName) {
                                            value.ctContrApplyId = value.contractName;
                                        }
                                        delete value.contractNo;
                                        delete value.contractName;
                                        delete value.reportTime;
                                        delete value.approvalTime;
                                        this.setState({
                                            formData: value
                                        })
                                    }}>查询</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            confirm({
                                                content: '确定导出数据吗?',
                                                onOk: () => {
                                                    let value = this.form.form.getFieldsValue();
                                                    if (value?.reportTime?.length) {
                                                        value.reportStartTime = moment(value.reportTime[0]).startOf('date').valueOf();
                                                        value.reportEndTime = moment(value.reportTime[1]).endOf('date').valueOf();
                                                    }
                                                    if (value?.approvalTime?.length) {
                                                        value.approvalStartTime = moment(value.approvalTime[0]).startOf('date').valueOf();
                                                        value.approvalEndTime = moment(value.approvalTime[1]).endOf('date').valueOf();
                                                    }
                                                    if (value?.contractNo) {
                                                        value.ctContrApplyId = value.contractNo;
                                                    } else if (value?.contractName) {
                                                        value.ctContrApplyId = value.contractName;
                                                    }
                                                    delete value.contractNo;
                                                    delete value.contractName;
                                                    delete value.reportTime;
                                                    delete value.approvalTime;
                                                    let stringData = '';
                                                    for(let key in value){
                                                        if(value?.[key]){
                                                            stringData += `&${key}=${value[key]}`;
                                                        }
                                                    }
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    let URL = `${ureport}excel?_u=minio:合同评审台账(工程类).ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=合同评审台账(工程类)`;
                                                    window.open(URL);
                                                }
                                            })
                                        }}>导出</Button></div>
                                );
                            },
                            span: 6
                        }
                    ]}
                />
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
                        apiName: 'psGetZxGcsgCtContrApplyListByUReport',
                        otherParams: {
                            ...formData
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'ctContrApplyId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '合同编号',
                                dataIndex: 'contractNo',
                                key: 'contractNo'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'projectName',
                                key: 'projectName'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '甲方名称',
                                dataIndex: 'firstName',
                                key: 'firstName'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '乙方名称',
                                dataIndex: 'secondName',
                                key: 'secondName'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同名称',
                                dataIndex: 'contractName',
                                key: 'contractName'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同金额(万元)',
                                dataIndex: 'contractCost',
                                key: 'contractCost'
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '所属事业部',
                                dataIndex: 'contractDepart',
                                key: 'contractDepart',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "公路市政事业部",
                                        value: "glsz"
                                    },
                                    {
                                        label: "铁路轨道事业部",
                                        value: "tlgd"
                                    },
                                    {
                                        label: "城市房建事业部",
                                        value: "csfj"
                                    }
                                ],
                            }
                        },
                        {
                            table: {
                                title: '批复情况',
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "未审核",
                                        value: "-1"
                                    },
                                    {
                                        label: "待提交",
                                        value: "0"
                                    },
                                    {
                                        label: "审核中",
                                        value: "1"
                                    },
                                    {
                                        label: "已完成",
                                        value: "2"
                                    },
                                    {
                                        label: "已退回",
                                        value: "3"
                                    }
                                ],
                            }
                        },
                        {
                            table: {
                                title: '上报局时间',
                                dataIndex: 'reportTime',
                                key: 'reportTime',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '审批时间',
                                dataIndex: 'approvalTime',
                                key: 'approvalTime',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '审批建议或意见',
                                dataIndex: 'opinionField',
                                key: 'opinionField',
                                render:(data) => {
                                    if(data){
                                        data = data.replace(/<br\/>/g, "\n");
                                    }else{
                                        return '';
                                    }
                                }
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入'
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;