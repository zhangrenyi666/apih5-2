import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal, message as Msg, Spin } from "antd";
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
            tableFormConfig: [],
            formData: {
                queryComID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
                queryOrgID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
                queryPeriod: moment(new Date()).format('YYYYMM')
            },
            loading: false
        }
    }
    componentDidMount () {
        this.refresh();
    }
    refresh = () => {
        this.setState({
            loading: true,
            tableFormConfig: []
        })
        const { formData } = this.state;
        this.props.myFetch('exportZxCtOwnerBalanceListRptHead', formData).then(({ data, success, message }) => {
            if (success) {
                if (!data) {
                    data = {};
                }
                this.setState({
                    tableFormConfig: [
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
                                title: `????????????:${data?.orgName || ''}`,
                                dataIndex: 'title_1',
                                key: 'title_1',
                                width: 1410,
                                children: [
                                    {
                                        title: `????????????:${data?.planEndDate || ''}`,
                                        dataIndex: 'title_11',
                                        key: 'title_11',
                                        width: 1410,
                                        children: [
                                            {
                                                title: `????????????:${data?.deprName || ''}`,
                                                dataIndex: 'title_111',
                                                key: 'title_111',
                                                width: 810,
                                                children: [
                                                    {
                                                        title: `????????????:${data?.consultative || ''}`,
                                                        dataIndex: 'title_1111',
                                                        key: 'title_1111',
                                                        width: 810,
                                                        children: [
                                                            {
                                                                title: `????????????:${data?.implementUnit || ''}`,
                                                                dataIndex: 'title_11111',
                                                                key: 'title_11111',
                                                                width: 810,
                                                                children: [
                                                                    {
                                                                        title: '??????',
                                                                        dataIndex: 'index',
                                                                        key: 'index',
                                                                        width: 50,
                                                                        fixed: 'left',
                                                                        render: (data, rowData, index) => {
                                                                            return index + 1;
                                                                        }
                                                                    },
                                                                    {
                                                                        title: '?????????',
                                                                        dataIndex: 'workNo',
                                                                        key: 'workNo',
                                                                        width: 150,
                                                                        fixed: 'left'
                                                                    },
                                                                    {
                                                                        title: '????????????',
                                                                        dataIndex: 'workName',
                                                                        key: 'workName',
                                                                        width: 150,
                                                                        fixed: 'left'
                                                                    },
                                                                    {
                                                                        title: '????????????????????????',
                                                                        dataIndex: 'title_1',
                                                                        key: 'title_1',
                                                                        width: 460,
                                                                        children: [
                                                                            {
                                                                                title: '????????????',
                                                                                dataIndex: 'contrAmt',
                                                                                key: 'contrAmt',
                                                                                width: 100
                                                                            },
                                                                            {
                                                                                title: '??????????????????',
                                                                                dataIndex: 'checkAmt',
                                                                                key: 'checkAmt',
                                                                                width: 120
                                                                            },
                                                                            {
                                                                                title: '????????????(+???-)',
                                                                                dataIndex: 'alterAmt',
                                                                                key: 'alterAmt',
                                                                                width: 120,
                                                                            },
                                                                            {
                                                                                title: '??????????????????',
                                                                                dataIndex: 'gjAmt',
                                                                                key: 'gjAmt',
                                                                                width: 120
                                                                            }
                                                                        ]
                                                                    }
                                                                ]
                                                            }
                                                        ]
                                                    }
                                                ]
                                            },
                                            {
                                                title: `????????????(???):${data?.contractCost || ''}`,
                                                dataIndex: 'title_112',
                                                key: 'title_112',
                                                width: 600,
                                                children: [
                                                    {
                                                        dataIndex: 'title_1121',
                                                        key: 'title_1121',
                                                        width: 600,
                                                        children: [
                                                            {
                                                                title: `?????????????????????(???):${data?.alertAmt || ''}`,
                                                                dataIndex: 'title_11211',
                                                                key: 'title_11211',
                                                                width: 600,
                                                                children: [
                                                                    {
                                                                        title: '???????????????',
                                                                        dataIndex: 'title_2',
                                                                        key: 'title_2',
                                                                        width: 200,
                                                                        children: [
                                                                            {
                                                                                title: '??????(???)',
                                                                                dataIndex: 'balAmts',
                                                                                key: 'balAmts',
                                                                                width: 100
                                                                            },
                                                                            {
                                                                                title: '?????????(%)',
                                                                                dataIndex: 'balAmtsRate',
                                                                                key: 'balAmtsRate',
                                                                                width: 100
                                                                            }
                                                                        ]
                                                                    },
                                                                    {
                                                                        title: '???????????????',
                                                                        dataIndex: 'title_3',
                                                                        key: 'title_3',
                                                                        width: 200,
                                                                        children: [
                                                                            {
                                                                                title: '??????(???)',
                                                                                dataIndex: 'upBalAmt',
                                                                                key: 'upBalAmt',
                                                                                width: 100
                                                                            },
                                                                            {
                                                                                title: '?????????(%)',
                                                                                dataIndex: 'upBalAmtRate',
                                                                                key: 'upBalAmtRate',
                                                                                width: 100
                                                                            }
                                                                        ]
                                                                    },
                                                                    {
                                                                        title: '????????????',
                                                                        dataIndex: 'title_4',
                                                                        key: 'title_4',
                                                                        width: 200,
                                                                        children: [
                                                                            {
                                                                                title: '??????(???)',
                                                                                dataIndex: 'balAmt',
                                                                                key: 'balAmt',
                                                                                width: 100
                                                                            },
                                                                            {
                                                                                title: '?????????(%)',
                                                                                dataIndex: 'balAmtRate',
                                                                                key: 'balAmtRate',
                                                                                width: 100
                                                                            }
                                                                        ]
                                                                    },
                                                                ]
                                                            }
                                                        ]
                                                    }
                                                ]
                                            }
                                        ]
                                    }
                                ]
                            }
                        }
                    ],
                    loading: false
                })
            } else {
                Msg.error(message)
            }
        })
    }
    render () {
        const { departmentId, formData, tableFormConfig, loading } = this.state;
        return (
            <div>
                <Spin spinning={loading}>
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
                                type: 'string',
                                hide: true
                            },
                            {
                                label: '????????????',
                                field: 'queryOrgID',
                                type: 'select',
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams: {
                                        departmentId: departmentId
                                    }
                                },
                                showSearch: true,
                                allowClear: formData?.queryOrgID ? false : true,
                                required: true,
                                span: 8
                            },
                            {
                                label: '??????',
                                field: 'queryPeriod',
                                type: 'month',
                                span: 8
                            },
                            {
                                type: 'component',
                                field: 'component',
                                Component: obj => {
                                    return (
                                        <div style={{ padding: '10px' }}><Button type="primary" onClick={() => {
                                            this.form.form.validateFields().then((value) => {
                                                if (value?.queryPeriod) {
                                                    value.queryPeriod = moment(value.queryPeriod).format('YYYYMM');
                                                }
                                                this.setState({
                                                    formData: value
                                                }, () => {
                                                    this.refresh();
                                                })
                                            })
                                        }}>??????</Button>
                                            <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                confirm({
                                                    content: '??????????????????????',
                                                    onOk: () => {
                                                        let value = this.form.form.getFieldsValue();
                                                        if (value?.queryPeriod) {
                                                            value.queryPeriod = moment(value.queryPeriod).format('YYYYMM');
                                                        }
                                                        let stringData = '';
                                                        for (let key in value) {
                                                            if (value?.[key]) {
                                                                stringData += `&${key}=${value[key]}`;
                                                            }
                                                        }
                                                        let { myPublic: { appInfo: { ureport } } } = this.props;
                                                        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                        let URL = `${ureport}excel?_u=minio:ZxCtOwnerBalanceListRpt.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=?????????????????????(???????????????)??????`;
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
                    {tableFormConfig.length ? <QnnTable
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => {
                            this.table = me;
                        }}
                        {...config}
                        fetchConfig={{
                            apiName: 'exportZxCtOwnerBalanceListRpt',
                            otherParams: {
                                ...formData
                            }
                        }}
                        formConfig={tableFormConfig}
                    /> : null}
                </Spin>
            </div>
        );
    }
}

export default index;