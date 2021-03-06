import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal } from "antd";
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: 'xmid',
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
            xmid: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
            formData: {
                xmid: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
                gsid: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
            }
        }
    }
    render () {
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { departmentId, formData, xmid } = this.state;
        return (
            <div>
                <QnnForm
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                        token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    data={formData}
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
                            field: 'gsid',
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
                            disabled:curCompany?.ext1??===??'3'??||??curCompany?.ext1??===??'4' || xmid ? true : false,
                            showSearch: true,
                            allowClear: false,
                            span: 6
                        },
                        {
                            label: '????????????',
                            field: 'xmid',
                            type: 'select',
                            optionConfig: {
                                label: 'departmentName',
                                value: 'departmentId'
                            },
                            parent: 'gsid',
                            fetchConfig: {
                                apiName: 'getSysProjectList',
                                params: {
                                    departmentId: 'gsid'
                                }
                            },
                            disabled:curCompany?.ext1??===??'3'??||??curCompany?.ext1??===??'4' || xmid ? true : false,
                            showSearch: true,
                            allowClear: formData?.xmid ? false : true,
                            span: 6
                        },
                        {
                            label: '????????????',
                            field: 'htlb',
                            type: 'select',
                            optionConfig: {
                                label: 'label',
                                value: 'value'
                            },
                            optionData: [
                                {
                                    label: '?????????????????????',
                                    value: 'P2'
                                },
                                {
                                    label: '???????????????????????????',
                                    value: 'P2C'
                                },
                                {
                                    label: '??????????????????',
                                    value: 'P5ZL'
                                },
                                {
                                    label: '??????????????????',
                                    value: 'P5CG'
                                },
                                {
                                    label: '?????????????????????',
                                    value: 'P5C'
                                },
                                {
                                    label: '??????????????????',
                                    value: 'P4ZL'
                                },
                                {
                                    label: '??????????????????',
                                    value: 'P4CG'
                                },
                                {
                                    label: '??????????????????',
                                    value: 'P4C'
                                },
                                {
                                    label: '???????????????',
                                    value: 'P8'
                                },
                                {
                                    label: '?????????????????????',
                                    value: 'P8C'
                                },
                                {
                                    label: '???????????????',
                                    value: 'P9'
                                },
                                {
                                    label: '?????????????????????',
                                    value: 'P9C'
                                }
                            ],
                            showSearch: true,
                            span: 6
                        },
                        {
                            label: '????????????',
                            field: 'htzt',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName',
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'heTongZhuangTai'
                                }
                            },
                            span: 6
                        },
                        {
                            label: '????????????',
                            field: 'htlx',
                            type: 'select',
                            optionConfig: {
                                label: 'label',
                                value: 'value'
                            },
                            optionData: [
                                {
                                    label: '?????????',
                                    value: '?????????'
                                },
                                {
                                    label: '????????????',
                                    value: '????????????'
                                }
                            ],
                            span: 6
                        },
                        {
                            label: '????????????',
                            field: 'wzlb',
                            type: 'select',
                            optionConfig: {
                                label: 'catName',
                                value: 'id'
                            },
                            fetchConfig: {
                                apiName: 'getZxSkResCategoryMaterialsList',
                                otherParams: {
                                    parentID: '0002'
                                }
                            },
                            span: 6
                        },
                        {
                            label: '????????????',
                            field: 'htje',
                            type: 'number',
                            range: true,
                            span: 6
                        },
                        {
                            label: '??????????????????',
                            field: 'approvalTime',
                            type: 'rangeDate',
                            span: 6
                        },
                        // {
                        //     label: '???????????????',
                        //     field: 'sfjzz',
                        //     type: 'select',
                        //     optionConfig: {
                        //         label: 'label',
                        //         value: 'value'
                        //     },
                        //     optionData: [
                        //         {
                        //             label: '????????????',
                        //             value: '????????????'
                        //         },
                        //         {
                        //             label: '???',
                        //             value: '???'
                        //         }
                        //     ],
                        //     span: 6
                        // },
                        {
                            label: '????????????',
                            field: 'yfmc',
                            type: 'string',
                            span: 6
                        },
                        // {
                        //     label: '???????????????????????????',
                        //     field: 'yfhtxcfzr',
                        //     type: 'string',
                        //     span: 9
                        // },
                        {
                            label: '???????????????????????????',
                            field: 'yfdwfddbr',
                            type: 'string',
                            span: 6
                        },
                        {
                            label: '?????????????????????',
                            field: 'yfhtqdr',
                            type: 'string',
                            span: 6
                        },
                        {
                            type: 'component',
                            field: 'component',
                            Component: obj => {
                                return (
                                    <div style={{ padding: '10px' }}><Button type="primary" onClick={() => {
                                        let value = this.form.form.getFieldsValue();
                                        if (value?.approvalTime?.length) {
                                            value.startDate = moment(value.approvalTime[0]).startOf('date').valueOf();
                                            value.endDate = moment(value.approvalTime[1]).endOf('date').valueOf();
                                        }
                                        if (value?.htje?.length) {
                                            if (value.htje[0] > value.htje[1]) {
                                                value.htjeks = value.htje[1];
                                                value.htjejs = value.htje[0];
                                            }else{
                                                value.htjeks = value.htje[0];
                                                value.htjejs = value.htje[1];
                                            }
                                        }
                                        delete value.htje;
                                        delete value.approvalTime;
                                        this.setState({
                                            formData: value
                                        })
                                    }}>??????</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            confirm({
                                                content: '??????????????????????',
                                                onOk: () => {
                                                    let value = this.form.form.getFieldsValue();
                                                    if (value?.approvalTime?.length) {
                                                        value.startDate = moment(value.approvalTime[0]).startOf('date').valueOf();
                                                        value.endDate = moment(value.approvalTime[1]).endOf('date').valueOf();
                                                    }
                                                    if (value?.htje?.length) {
                                                        if (value.htje[0] > value.htje[1]) {
                                                            value.htjeks = value.htje[1];
                                                            value.htjejs = value.htje[0];
                                                        }else{
                                                            value.htjeks = value.htje[0];
                                                            value.htjejs = value.htje[1];
                                                        }
                                                    }
                                                    delete value.htje;
                                                    delete value.approvalTime;
                                                    let stringData = '';
                                                    for (let key in value) {
                                                        if (value?.[key]) {
                                                            stringData += `&${key}=${value[key]}`;
                                                        }
                                                    }
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    let URL = `${ureport}excel?_u=minio:????????????????????????.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=????????????????????????`;
                                                    window.open(URL);
                                                }
                                            })
                                        }}>??????</Button></div>
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
                        apiName: 'getForeignBusinessContractAccountByUReport',
                        otherParams: {
                            ...formData
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'xmid',
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
                                dataIndex: 'xmmc',
                                key: 'xmmc',
                                width: 200,
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
                                dataIndex: 'htbh',
                                key: 'htbh',
                                width: 200,
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
                                dataIndex: 'htmc',
                                key: 'htmc',
                                width: 200,
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
                                dataIndex: 'htlb',
                                key: 'htlb',
                                width: 150,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    {
                                        label: '?????????????????????',
                                        value: 'P2'
                                    },
                                    {
                                        label: '???????????????????????????',
                                        value: 'P2C'
                                    },
                                    {
                                        label: '??????????????????',
                                        value: 'P5ZL'
                                    },
                                    {
                                        label: '??????????????????',
                                        value: 'P5CG'
                                    },
                                    {
                                        label: '?????????????????????',
                                        value: 'P5C'
                                    },
                                    {
                                        label: '??????????????????',
                                        value: 'P4ZL'
                                    },
                                    {
                                        label: '??????????????????',
                                        value: 'P4CG'
                                    },
                                    {
                                        label: '??????????????????',
                                        value: 'P4C'
                                    },
                                    {
                                        label: '???????????????',
                                        value: 'P8'
                                    },
                                    {
                                        label: '?????????????????????',
                                        value: 'P8C'
                                    },
                                    {
                                        label: '???????????????',
                                        value: 'P9'
                                    },
                                    {
                                        label: '?????????????????????',
                                        value: 'P9C'
                                    }
                                ],
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'htlx',
                                key: 'htlx',
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
                                dataIndex: 'jfmc',
                                key: 'jfmc',
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
                                dataIndex: 'yfmc',
                                key: 'yfmc',
                                width: 200
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '???????????????????????????',
                                dataIndex: 'yfdwfddbr',
                                key: 'yfdwfddbr',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '?????????????????????',
                                dataIndex: 'yfhtqdr',
                                key: 'yfhtqdr',
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
                                dataIndex: 'htje',
                                key: 'htje',
                                width: 120
                            },
                            form: {
                                type: 'number',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'htkgrq',
                                key: 'htkgrq',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'htjgrq',
                                key: 'htjgrq',
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
                                dataIndex: 'jszt',
                                key: 'jszt',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'scjsrq',
                                key: 'scjsrq',
                                width: 120
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????????????????',
                                dataIndex: 'xmsbgssprq',
                                key: 'xmsbgssprq',
                                width: 120
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????????????????',
                                dataIndex: 'htpstgrq',
                                key: 'htpstgrq',
                                width: 120
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'htqdsj',
                                key: 'htqdsj',
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
                                dataIndex: 'fbnr',
                                key: 'fbnr',
                                width: 200
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'bz',
                                key: 'bz',
                                width: 200
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