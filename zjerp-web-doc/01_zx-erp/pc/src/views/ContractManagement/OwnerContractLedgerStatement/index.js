import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal } from "antd";
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: 'id',
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
            formData: {
                queryComID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId
            }
        }
    }
    render () {
        const { departmentId, formData } = this.state;
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
                            label: '????????????',
                            field: 'queryComID',
                            type: 'select',
                            optionConfig: {
                                label: 'companyName',
                                value: 'companyId'
                            },
                            fetchConfig: {
                                apiName: 'getSysCompanyBySelect',
                                otherParams: {
                                    departmentId: departmentId
                                }
                            },
                            showSearch: true,
                            allowClear: false,
                            required: true,
                            span: 8
                        },
                        {
                            label: '????????????',
                            field: 'queryProjType',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //?????? label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'gongChengLeiBie'
                                }
                            },
                            span: 8
                        },
                        {
                            label: '??????????????????',
                            field: 'queryProjQuality',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //?????? label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'xiangMuCanJianXingZhi'
                                }
                            },
                            span: 8
                        },
                        {
                            label: '????????????',
                            field: 'queryDate',
                            type: 'rangeDate',
                            span: 8
                        },
                        {
                            type: 'component',
                            field: 'component',
                            Component: obj => {
                                return (
                                    <div style={{ padding: '10px' }}><Button type="primary" onClick={() => {
                                        this.form.form.validateFields().then((value) => {
                                            if (value?.queryDate?.length) {
                                                value.queryBeginDate = moment(value.queryDate[0]).startOf('date').format('YYYY-MM-DD');
                                                value.queryEndDate = moment(value.queryDate[1]).endOf('date').format('YYYY-MM-DD');
                                            }
                                            delete value.queryDate;
                                            this.setState({
                                                formData: value
                                            })
                                        })
                                    }}>??????</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            confirm({
                                                content: '??????????????????????',
                                                onOk: () => {
                                                    let value = this.form.form.getFieldsValue();
                                                    if (value?.queryDate?.length) {
                                                        value.queryBeginDate = moment(value.queryDate[0]).startOf('date').format('YYYY-MM-DD');
                                                        value.queryEndDate = moment(value.queryDate[1]).endOf('date').format('YYYY-MM-DD');
                                                    }
                                                    delete value.queryDate;
                                                    let stringData = '';
                                                    for (let key in value) {
                                                        if (value?.[key]) {
                                                            stringData += `&${key}=${value[key]}`;
                                                        }
                                                    }
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    let URL = `${ureport}excel?_u=minio:ZxCtContractP1JuRpt.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=????????????????????????`;
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
                        apiName: 'exportZxCtContractP1JuRpt',
                        otherParams: {
                            ...formData
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
                                title: '??????',
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
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: 150,
                                fixed: 'left',
                                render:(data) => {
                                    return <xmp>{data}</xmp>;
                                }
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????????????????',
                                dataIndex: 'subject',
                                key: 'subject',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????????????????',
                                dataIndex: 'locationInDeprFormula',
                                key: 'locationInDeprFormula',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'firstName',
                                key: 'firstName',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'projType',
                                key: 'projType',
                                width: 100,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'gongChengLeiBie'
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'queryProjQuality',
                                key: 'queryProjQuality',
                                width: 100,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName', //?????? label
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'xiangMuCanJianXingZhi'
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '???????????????',
                                dataIndex: 'location',
                                key: 'location',
                                width: 120
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????(??????)',
                                dataIndex: 'contractCost',
                                key: 'contractCost',
                                width: 120
                            },
                            form: {
                                type: 'number',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????(???)',
                                dataIndex: 'csTimeLimit',
                                key: 'csTimeLimit',
                                width: 120
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????/??????????????????',
                                dataIndex: 'actualDate',
                                key: 'actualDate',
                                width: 200
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'designUnit',
                                key: 'designUnit',
                                width: 120
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'consultative',
                                key: 'consultative',
                                width: 120
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'signatureDate',
                                key: 'signatureDate',
                                width: 100
                            },
                            form: {
                                type: 'date',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????????????????(??????)',
                                dataIndex: 'balAmt',
                                key: 'balAmt',
                                width: 140
                            },
                            form: {
                                type: 'number',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????(???????????????????????????)',
                                dataIndex: 'delayDate',
                                key: 'delayDate',
                                width: 160
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'projectManager',
                                key: 'projectManager',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;