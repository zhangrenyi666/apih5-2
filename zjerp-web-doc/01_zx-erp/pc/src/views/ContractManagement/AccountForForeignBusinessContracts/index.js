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
                            label: '公司名称',
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
                            disabled:curCompany?.ext1 === '3' || curCompany?.ext1 === '4' || xmid ? true : false,
                            showSearch: true,
                            allowClear: false,
                            span: 6
                        },
                        {
                            label: '项目名称',
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
                            disabled:curCompany?.ext1 === '3' || curCompany?.ext1 === '4' || xmid ? true : false,
                            showSearch: true,
                            allowClear: formData?.xmid ? false : true,
                            span: 6
                        },
                        {
                            label: '合同类别',
                            field: 'htlb',
                            type: 'select',
                            optionConfig: {
                                label: 'label',
                                value: 'value'
                            },
                            optionData: [
                                {
                                    label: '工程施工类合同',
                                    value: 'P2'
                                },
                                {
                                    label: '工程施工类补充协议',
                                    value: 'P2C'
                                },
                                {
                                    label: '物资租赁合同',
                                    value: 'P5ZL'
                                },
                                {
                                    label: '物资采购合同',
                                    value: 'P5CG'
                                },
                                {
                                    label: '物资类补充协议',
                                    value: 'P5C'
                                },
                                {
                                    label: '设备租赁合同',
                                    value: 'P4ZL'
                                },
                                {
                                    label: '设备采购合同',
                                    value: 'P4CG'
                                },
                                {
                                    label: '设备补充协议',
                                    value: 'P4C'
                                },
                                {
                                    label: '其它类合同',
                                    value: 'P8'
                                },
                                {
                                    label: '其它类补充协议',
                                    value: 'P8C'
                                },
                                {
                                    label: '附属类合同',
                                    value: 'P9'
                                },
                                {
                                    label: '附属类补充协议',
                                    value: 'P9C'
                                }
                            ],
                            showSearch: true,
                            span: 6
                        },
                        {
                            label: '合同状态',
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
                            label: '合同类型',
                            field: 'htlx',
                            type: 'select',
                            optionConfig: {
                                label: 'label',
                                value: 'value'
                            },
                            optionData: [
                                {
                                    label: '原合同',
                                    value: '原合同'
                                },
                                {
                                    label: '补充协议',
                                    value: '补充协议'
                                }
                            ],
                            span: 6
                        },
                        {
                            label: '物资类别',
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
                            label: '合同金额',
                            field: 'htje',
                            type: 'number',
                            range: true,
                            span: 6
                        },
                        {
                            label: '合同签订日期',
                            field: 'approvalTime',
                            type: 'rangeDate',
                            span: 6
                        },
                        // {
                        //     label: '是否局资质',
                        //     field: 'sfjzz',
                        //     type: 'select',
                        //     optionConfig: {
                        //         label: 'label',
                        //         value: 'value'
                        //     },
                        //     optionData: [
                        //         {
                        //             label: '中国交建',
                        //             value: '中国交建'
                        //         },
                        //         {
                        //             label: '局',
                        //             value: '局'
                        //         }
                        //     ],
                        //     span: 6
                        // },
                        {
                            label: '乙方名称',
                            field: 'yfmc',
                            type: 'string',
                            span: 6
                        },
                        // {
                        //     label: '乙方合同现场负责人',
                        //     field: 'yfhtxcfzr',
                        //     type: 'string',
                        //     span: 9
                        // },
                        {
                            label: '乙方单位法定代表人',
                            field: 'yfdwfddbr',
                            type: 'string',
                            span: 6
                        },
                        {
                            label: '乙方合同签订人',
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
                                    }}>查询</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            confirm({
                                                content: '确定导出数据吗?',
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
                                                    let URL = `${ureport}excel?_u=minio:对外经营合同台账.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=对外经营合同台帐`;
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
                                dataIndex: 'xmmc',
                                key: 'xmmc',
                                width: 200,
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
                                dataIndex: 'htbh',
                                key: 'htbh',
                                width: 200,
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
                                dataIndex: 'htmc',
                                key: 'htmc',
                                width: 200,
                                fixed: 'left'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同类别',
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
                                        label: '工程施工类合同',
                                        value: 'P2'
                                    },
                                    {
                                        label: '工程施工类补充协议',
                                        value: 'P2C'
                                    },
                                    {
                                        label: '物资租赁合同',
                                        value: 'P5ZL'
                                    },
                                    {
                                        label: '物资采购合同',
                                        value: 'P5CG'
                                    },
                                    {
                                        label: '物资类补充协议',
                                        value: 'P5C'
                                    },
                                    {
                                        label: '设备租赁合同',
                                        value: 'P4ZL'
                                    },
                                    {
                                        label: '设备采购合同',
                                        value: 'P4CG'
                                    },
                                    {
                                        label: '设备补充协议',
                                        value: 'P4C'
                                    },
                                    {
                                        label: '其它类合同',
                                        value: 'P8'
                                    },
                                    {
                                        label: '其它类补充协议',
                                        value: 'P8C'
                                    },
                                    {
                                        label: '附属类合同',
                                        value: 'P9'
                                    },
                                    {
                                        label: '附属类补充协议',
                                        value: 'P9C'
                                    }
                                ],
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '合同类型',
                                dataIndex: 'htlx',
                                key: 'htlx',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '甲方名称',
                                dataIndex: 'jfmc',
                                key: 'jfmc',
                                width: 200
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '乙方名称',
                                dataIndex: 'yfmc',
                                key: 'yfmc',
                                width: 200
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '乙方单位法定代表人',
                                dataIndex: 'yfdwfddbr',
                                key: 'yfdwfddbr',
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
                                dataIndex: 'yfhtqdr',
                                key: 'yfhtqdr',
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
                                dataIndex: 'htje',
                                key: 'htje',
                                width: 120
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同开工日期',
                                dataIndex: 'htkgrq',
                                key: 'htkgrq',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同交工日期',
                                dataIndex: 'htjgrq',
                                key: 'htjgrq',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '结算状态',
                                dataIndex: 'jszt',
                                key: 'jszt',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '首次结算日期',
                                dataIndex: 'scjsrq',
                                key: 'scjsrq',
                                width: 120
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '项目上报公司审批日期',
                                dataIndex: 'xmsbgssprq',
                                key: 'xmsbgssprq',
                                width: 120
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同评审通过日期',
                                dataIndex: 'htpstgrq',
                                key: 'htpstgrq',
                                width: 120
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同签订日期',
                                dataIndex: 'htqdsj',
                                key: 'htqdsj',
                                width: 120
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '分包内容',
                                dataIndex: 'fbnr',
                                key: 'fbnr',
                                width: 200
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'bz',
                                key: 'bz',
                                width: 200
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