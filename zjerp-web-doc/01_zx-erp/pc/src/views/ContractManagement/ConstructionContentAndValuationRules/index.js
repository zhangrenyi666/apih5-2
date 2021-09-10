import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal } from "antd";
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: 'ccWorksId',
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
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            formData: {}
        }
    }
    render () {
        const { departmentId, formData } = this.state;
        const { departmentName, companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                            label: '合同编号',
                            field: 'contractNo',
                            type: 'selectByPaging',
                            optionConfig: {
                                label: 'contractNo',
                                value: 'ctContractId',
                                linkageFields: {
                                    contractName: 'ctContractId'
                                }
                            },
                            fetchConfig: {
                                apiName: 'getZxGcsgCtContractList',
                                searchKey: 'contractNo',
                                otherParams: {
                                    orgID: departmentId,
                                    companyName:companyName,
                                    projectName:departmentName
                                }
                            },
                            required: true,
                            onChange: (val, obj) => {
                                if (!val) {
                                    obj.form.setFieldsValue({
                                        contractName: null
                                    })
                                }
                            },
                            span: 8
                        },
                        {
                            label: '合同名称',
                            field: 'contractName',
                            type: 'selectByPaging',
                            optionConfig: {
                                label: 'contractName',
                                value: 'ctContractId',
                                linkageFields: {
                                    contractNo: 'ctContractId'
                                }
                            },
                            fetchConfig: {
                                apiName: 'getZxGcsgCtContractList',
                                searchKey: 'contractName',
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
                            required: true,
                            span: 8
                        },
                        {
                            type: 'component',
                            field: 'component',
                            Component: obj => {
                                return (
                                    <div style={{ padding: '10px' }}><Button type="primary" onClick={() => {
                                        this.form.form.validateFields().then((value) => {
                                            let ctContractId;
                                            if (value?.contractNo) {
                                                ctContractId = value.contractNo;
                                            } else if (value?.contractName) {
                                                ctContractId = value.contractName;
                                            }
                                            this.setState({
                                                formData: {
                                                    ctContractId: ctContractId
                                                }
                                            })
                                        })
                                    }}>查询</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            confirm({
                                                content: '确定导出数据吗?',
                                                onOk: () => {
                                                    let value = this.form.form.getFieldsValue();
                                                    let ctContractId = '';
                                                    if (value?.contractNo) {
                                                        ctContractId = value.contractNo;
                                                    } else if (value?.ctContractName) {
                                                        ctContractId = value.ctContractName;
                                                    }
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    let URL = `${ureport}excel?_u=minio:施工内容及计价规则表.ureport.xml&access_token=${access_token}&delFlag=0&ctContractId=${ctContractId}&_n=施工内容及计价规则表`;
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
                        apiName: 'getZxGcsgCcWorksListProcessByUReport',
                        otherParams: {
                            ...formData
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'ccWorksId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '清单编号',
                                dataIndex: 'workNo',
                                key: 'workNo'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '清单名称',
                                dataIndex: 'workName',
                                key: 'workName'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '计价规则',
                                dataIndex: 'ruleName',
                                key: 'ruleName'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '工序名称',
                                dataIndex: 'processName',
                                key: 'processName'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '施工内容',
                                dataIndex: 'content',
                                key: 'content'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '工序编码',
                                dataIndex: 'processNo',
                                key: 'processNo'
                            },
                            form: {
                                type: 'string',
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