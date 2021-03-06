import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal } from "antd";
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: 'ctPriceSysId',
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
                            label: '????????????',
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
                            label: '????????????',
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
                            required: true,
                            onChange: (val, obj) => {
                                if (!val) {
                                    obj.form.setFieldsValue({
                                        contractNo: null
                                    })
                                }
                            },
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
                                    }}>??????</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            confirm({
                                                content: '??????????????????????',
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
                                                    let URL = `${ureport}excel?_u=minio:?????????????????????.ureport.xml&access_token=${access_token}&delFlag=0&ctContractId=${ctContractId}&_n=?????????????????????`;
                                                    window.open(URL);
                                                }
                                            })
                                        }}>??????</Button></div>
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
                        apiName: 'getZxGcsgCtPriceSysAndItemByUReport',
                        otherParams: {
                            ...formData
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'ctPriceSysId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'workNo',
                                key: 'workNo',
                                width: 150,
                                fixed: 'left'
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'workName',
                                key: 'workName',
                                width: 150,
                                fixed: 'left'
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'workUnit',
                                key: 'workUnit',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'amt1',
                                key: 'amt1',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '?????????',
                                dataIndex: 'amt2',
                                key: 'amt2',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'amt3',
                                key: 'amt3',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '???????????????',
                                dataIndex: 'priceNoTax',
                                key: 'priceNoTax',
                                width: 100
                            },
                            form: {
                                type: 'number',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????????????????/??????',
                                dataIndex: 'bhPriceNoTax',
                                key: 'bhPriceNoTax',
                                width: 180
                            },
                            form: {
                                type: 'number',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'yzPriceNoTax',
                                key: 'yzPriceNoTax',
                                width: 120
                            },
                            form: {
                                type: 'number',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '??????(????????????/??????-???????????????)',
                                dataIndex: 'dbPrice',
                                key: 'dbPrice',
                                width: 220
                            },
                            form: {
                                type: 'number',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'processNo',
                                key: 'processNo',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'processName',
                                key: 'processName',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '?????????',
                                dataIndex: 'rgf',
                                key: 'rgf',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'zzcl',
                                key: 'zzcl',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'jxsb',
                                key: 'jxsb',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'remark',
                                key: 'remark',
                                width: 150
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '?????????'
                            }
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;