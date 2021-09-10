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
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            tableFormConfig: [],
            formData: {
                queryComID:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
                queryOrgID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
                queryPeriod: moment(new Date()).format('YYYYMM')
            },
            loading: false
        }
    }
    componentDidMount() {
        this.refresh();
    }
    refresh = () => {
        this.setState({
            loading: true,
            tableFormConfig: []
        })
        const { formData } = this.state;
        this.props.myFetch('exportZxSaStockSettleTotalRptColumn', formData).then(
            ({ success, message, data }) => {
                if (success) {
                    let tableFormConfig = [
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
                                title: '物资大类',
                                dataIndex: 'catName',
                                width: 150,
                                key: 'catName',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '物资编码',
                                dataIndex: 'workNo',
                                width: 150,
                                key: 'workNo',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '物资名称',
                                dataIndex: 'workName',
                                width: 150,
                                key: 'workName',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '规格型号',
                                dataIndex: 'spec',
                                width: 100,
                                key: 'spec'
                            }
                        },
                        {
                            table: {
                                title: '单位',
                                dataIndex: 'unit',
                                width: 100,
                                key: 'unit'
                            }
                        },
                        {
                            table: {
                                title: '物资采购合同总量',
                                dataIndex: 'title_1',
                                width: 600,
                                key: 'title_1',
                                children: [
                                    {
                                        title: '原合同清单',
                                        dataIndex: 'title_11',
                                        key: 'title_11',
                                        width: 300,
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'qty',
                                                key: 'qty',
                                                width: 100
                                            },
                                            {
                                                title: '平均单价',
                                                dataIndex: 'price',
                                                key: 'price',
                                                width: 100
                                            },
                                            {
                                                title: '金额(元)',
                                                dataIndex: 'amt',
                                                key: 'amt',
                                                width: 100
                                            }
                                        ]
                                    },
                                    {
                                        title: '变更后合同清单',
                                        dataIndex: 'title_12',
                                        key: 'title_12',
                                        width: 300,
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'changeQty',
                                                key: 'changeQty',
                                                width: 100
                                            },
                                            {
                                                title: '平均单价',
                                                dataIndex: 'changePrice',
                                                key: 'changePrice',
                                                width: 100
                                            },
                                            {
                                                title: '金额(元)',
                                                dataIndex: 'changeContractSum',
                                                key: 'changeContractSum',
                                                width: 100
                                            }
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '物资结算汇总',
                                dataIndex: 'title_2',
                                width: 500,
                                key: 'title_2',
                                children: [
                                    {
                                        title: '本月结算',
                                        dataIndex: 'title_21',
                                        key: 'title_21',
                                        width: 200,
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'thisQty',
                                                key: 'thisQty',
                                                width: 100
                                            },
                                            {
                                                title: '金额(元)',
                                                dataIndex: 'thisAmt',
                                                key: 'thisAmt',
                                                width: 100
                                            }
                                        ]
                                    },
                                    {
                                        title: '累计结算',
                                        dataIndex: 'title_22',
                                        key: 'title_22',
                                        width: 300,
                                        children: [
                                            {
                                                title: '数量',
                                                dataIndex: 'totalQty',
                                                key: 'totalQty',
                                                width: 100
                                            },
                                            {
                                                title: '平均单价',
                                                dataIndex: 'totalPrice',
                                                key: 'totalPrice',
                                                width: 100
                                            },
                                            {
                                                title: '金额(元)',
                                                dataIndex: 'totalAmt',
                                                key: 'totalAmt',
                                                width: 100
                                            }
                                        ]
                                    },
                                ]
                            }
                        }
                    ];
                    data.map((item, index) => {
                        tableFormConfig.push(
                            {
                                table: {
                                    title: `${item.secondName}`,
                                    dataIndex: `title_3${index}`,
                                    width: item.child.length * 500,
                                    key: `title_3${index}`,
                                    children: item.child.map((items) => {
                                        return {
                                            title: `${items.contractNo}`,
                                            dataIndex: `title_31${index}`,
                                            key: `title_31${index}`,
                                            width: 500,
                                            children: [
                                                {
                                                    title: '本月结算',
                                                    dataIndex: `title_311${index}`,
                                                    key: `title_311${index}`,
                                                    width: 200,
                                                    children: [
                                                        {
                                                            title: '数量',
                                                            dataIndex: `${items.thisQty1}`,
                                                            key: `${items.thisQty1}`,
                                                            width: 100
                                                        },
                                                        {
                                                            title: '金额(元)',
                                                            dataIndex: `${items.thisAmt1}`,
                                                            key: `${items.thisAmt1}`,
                                                            width: 100
                                                        }
                                                    ]
                                                },
                                                {
                                                    title: '累计结算',
                                                    dataIndex: `title_312${index}`,
                                                    key: `title_312${index}`,
                                                    width: 300,
                                                    children: [
                                                        {
                                                            title: '数量',
                                                            dataIndex: `${items.totalQty1}`,
                                                            key: `${items.totalQty1}`,
                                                            width: 100
                                                        },
                                                        {
                                                            title: '平均单价',
                                                            dataIndex: `${items.totalPrice1}`,
                                                            key: `${items.totalPrice1}`,
                                                            width: 100
                                                        },
                                                        {
                                                            title: '金额(元)',
                                                            dataIndex: `${items.totalAmt1}`,
                                                            key: `${items.totalAmt1}`,
                                                            width: 100
                                                        }
                                                    ]
                                                }
                                            ]
                                        }
                                    })
                                }
                            }
                        )
                        return item;
                    })
                    this.setState({
                        tableFormConfig,
                        loading: false
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    render() {
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
                                label: '公司',
                                field: 'queryComID',
                                type: 'string',
                                hide: true
                            },
                            {
                                label: '项目名称',
                                field: 'queryOrgID',
                                type: 'select',
                                showSearch: true,
                                allowClear: formData?.queryOrgID ? false : true,
                                required:true,
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
                                span: 8
                            },
                            {
                                type: 'month',
                                label: '期次',
                                allowClear: false,
                                required:true,
                                field: 'queryPeriod',
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
                                        }}>查询</Button>
                                            <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                confirm({
                                                    content: '确定导出数据吗?',
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
                                                        let URL = `${ureport}excel?_u=minio:ZxSaStockSettleTotalRptRow.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=物资采购结算台账(项目)报表`;
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
                            apiName: 'exportZxSaStockSettleTotalRptRow',
                            otherParams: { ...formData }
                        }}
                        formConfig={tableFormConfig}
                    /> : null}
                </Spin>
            </div>
        );
    }
}

export default index;