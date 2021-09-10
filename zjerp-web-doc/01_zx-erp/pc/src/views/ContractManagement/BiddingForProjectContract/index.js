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
                projectId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
                companyId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
            }
        }
    }
    render() {
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { departmentId, formData, projectId } = this.state;
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
                            span: 8
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
                            showSearch:true,
                            allowClear: formData?.projectId ? false : true,
                            span: 8
                        },
                        {
                            label: '乙方名称',
                            field: 'secondID',
                            type: 'selectByPaging',
                            optionConfig: {
                                label: "customerName",
                                value: "zxCrCustomerExtAttrId",
                            },
                            fetchConfig: {
                                apiName: 'getZxCrCustomerExtAttrEngineeringList',
                                searchKey: 'customerName',
                                otherParams: {
                                    orgID: departmentId
                                }
                            },
                            span: 8
                        },
                        {
                            label: '时间',
                            field: 'approvalTime',
                            type: 'rangeDate',
                            span: 8
                        },
                        {
                            type: 'component',
                            field: 'component',
                            Component: obj => {
                                return (
                                    <div style={{ padding: '10px' }}><Button type="primary" onClick={() => {
                                        let value = this.form.form.getFieldsValue();
                                        if (value?.approvalTime?.length) {
                                            value.approvalStartTime = moment(value.approvalTime[0]).startOf('date').valueOf();
                                            value.approvalEndTime = moment(value.approvalTime[1]).endOf('date').valueOf();
                                        }
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
                                                    if (value?.approvalTime?.length) {
                                                        value.approvalStartTime = moment(value.approvalTime[0]).startOf('date').valueOf();
                                                        value.approvalEndTime = moment(value.approvalTime[1]).endOf('date').valueOf();
                                                    }
                                                    delete value.approvalTime;
                                                    let stringData = '';
                                                    for(let key in value){
                                                        if(value?.[key]){
                                                            stringData += `&${key}=${value[key]}`;
                                                        }
                                                    }
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    let URL = `${ureport}excel?_u=minio:工程合同(招标)统计表.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=工程合同（招标）统计表`;
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
                        apiName: 'psGetZxGcsgCtContrApplyBiddingByUReport',
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
                                title: '序号',
                                dataIndex: 'index',
                                key: 'index',
                                width: 50,
                                fixed: 'left',
                                render: (data, rowData, index) => {
                                    return index + 1;
                                }
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
                                key: 'projectName',
                                width: 150,
                                fixed: 'left'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同编号',
                                dataIndex: 'contractNo',
                                key: 'contractNo',
                                width: 150,
                                fixed: 'left'
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
                                key: 'contractName',
                                width: 150,
                                fixed: 'left'
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
                                key: 'secondName',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '乙方单位法定代表人',
                                dataIndex: 'chargePerson',
                                key: 'chargePerson',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '乙方合同签订人',
                                dataIndex: 'agent',
                                key: 'agent',
                                width: 120
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
                                key: 'contractCost',
                                width: 120
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        // {
                        //     table: {
                        //         title: '合同评审通过日期',
                        //         dataIndex: 'approvalTime',
                        //         key: 'approvalTime',
                        //         format: 'YYYY-MM-DD',
                        //         width: 130
                        //     },
                        //     form: {
                        //         type: 'date',
                        //         placeholder: '请输入'
                        //     }
                        // },
                        {
                            table: {
                                title: '合同签订日期',
                                dataIndex: 'approvalTime',
                                key: 'approvalTime',
                                format: 'YYYY-MM-DD',
                                width: 120
                            },
                            form: {
                                type: 'date',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '分包内容',
                                dataIndex: 'content',
                                key: 'content',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同所属事业部',
                                dataIndex: 'contractDepart',
                                key: 'contractDepart',
                                type: 'select',
                                width: 150
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
                                title: '局是否评审',
                                dataIndex: 'kong',
                                key: 'kong',
                                width: 100,
                                render: (data) => {
                                    if (data === '1') {
                                        return '是';
                                    }else{
                                        return '否';
                                    }
                                }
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '协作单位选择',
                                dataIndex: 'title_1',
                                key: 'title_1',
                                width: 1130,
                                children: [
                                    {
                                        title: '选择方式',
                                        dataIndex: 'bidType',
                                        key: 'bidType',
                                        width: 100,
                                        render: (data) => {
                                            if (data === 'public') {
                                                return '公开招标';
                                            } else if (data === 'invited') {
                                                return '邀请招标';
                                            } else if (data === 'other') {
                                                return '其他';
                                            }else{
                                                return '未知';
                                            }
                                        }
                                    },
                                    {
                                        title: '是否通过云电商进行招标',
                                        dataIndex: 'isBiddedOnCloud',
                                        key: 'isBiddedOnCloud',
                                        width: 180,
                                        render: (data) => {
                                            if (data === '0') {
                                                return '否';
                                            } else if (data === '1') {
                                                return '是';
                                            }
                                        }
                                    },
                                    {
                                        title: '云电商招标方案编号',
                                        dataIndex: 'cloudBidNo',
                                        key: 'cloudBidNo',
                                        width: 150,
                                    },
                                    {
                                        title: '组织招标主体',
                                        dataIndex: 'bidOrgType',
                                        key: 'bidOrgType',
                                        width: 120,
                                        render: (data) => {
                                            if (data === 'com') {
                                                return '公司';
                                            } else if (data === 'contr') {
                                                return '总承包部';
                                            } else if (data === 'pro') {
                                                return '项目部';
                                            }else{
                                                return '未知';
                                            }
                                        }
                                    },
                                    {
                                        title: '项目组织',
                                        dataIndex: 'title_11',
                                        key: 'title_11',
                                        width: 480,
                                        children: [
                                            {
                                                title: '各单位主管部门是否参与',
                                                dataIndex: 'isAllDepartJoin',
                                                key: 'isAllDepartJoin',
                                                width: 180,
                                                render: (data) => {
                                                    if (data === '0') {
                                                        return '否';
                                                    } else if (data === '1') {
                                                        return '是';
                                                    }
                                                }
                                            },
                                            {
                                                title: '参与方式',
                                                dataIndex: 'joinType',
                                                key: 'joinType',
                                                width: 100,
                                                render: (data) => {
                                                    if (data === 'live') {
                                                        return '现场';
                                                    } else if (data === 'site') {
                                                        return '网上';
                                                    } else if (data === 'notJoin') {
                                                        return '未参与';
                                                    }else{
                                                        return '未知';
                                                    }
                                                }
                                            },
                                            {
                                                title: '各单位主管部门是否参与评标',
                                                dataIndex: 'isDepartJoinBid',
                                                key: 'isDepartJoinBid',
                                                width: 200,
                                                render: (data) => {
                                                    if (data === '0') {
                                                        return '否';
                                                    } else if (data === '1') {
                                                        return '是';
                                                    }
                                                }
                                            }
                                        ]
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remarks',
                                key: 'remarks',
                                width: 150
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入'
                            }
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;