import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../../modules/qnn-table/qnn-form";
import { Button, Modal, message as Msg } from "antd";
const { confirm } = Modal;
const config = {
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
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            formData: {
                queryComID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
                queryPeriod: new Date()
            },
            queryPeriod: moment(new Date()).format('YYYY') + '0' + moment(new Date()).quarter(),
            summary:null
        }
    }
    componentDidMount(){
        this.refresh();
    }
    refresh = () => {
        const { formData } = this.state;
        this.props.myFetch('exportZxSkStockDifMonthQtySumComJiduHead', formData).then(({ success, message, data }) => {
            if (success) {
                this.setState({
                    summary: data
                })
            } else {
                Msg.error(message);
            }
        });
    }
    render () {
        const { departmentId, formData, queryPeriod, summary } = this.state;
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
                            showSearch: true,
                            allowClear: false,
                            required: true,
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
                            span: 8
                        },
                        {
                            type: 'quarter',
                            label: '??????',
                            allowClear: false,
                            required: true,
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
                                                if (moment(value.queryPeriod).quarter() === 1) {
                                                    value.queryPeriod = moment(value.queryPeriod).format('YYYY01');
                                                } else if (moment(value.queryPeriod).quarter() === 2) {
                                                    value.queryPeriod = moment(value.queryPeriod).format('YYYY02');
                                                } else if (moment(value.queryPeriod).quarter() === 3) {
                                                    value.queryPeriod = moment(value.queryPeriod).format('YYYY03');
                                                } else if (moment(value.queryPeriod).quarter() === 4) {
                                                    value.queryPeriod = moment(value.queryPeriod).format('YYYY04');
                                                }
                                            }
                                            this.setState({
                                                formData: value,
                                                queryPeriod: value.queryPeriod,
                                                summary:null
                                            },() => {
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
                                                        if (moment(value.queryPeriod).quarter() === 1) {
                                                            value.queryPeriod = moment(value.queryPeriod).format('YYYY01');
                                                        } else if (moment(value.queryPeriod).quarter() === 2) {
                                                            value.queryPeriod = moment(value.queryPeriod).format('YYYY02');
                                                        } else if (moment(value.queryPeriod).quarter() === 3) {
                                                            value.queryPeriod = moment(value.queryPeriod).format('YYYY03');
                                                        } else if (moment(value.queryPeriod).quarter() === 4) {
                                                            value.queryPeriod = moment(value.queryPeriod).format('YYYY04');
                                                        }
                                                    }
                                                    let stringData = '';
                                                    for (let key in value) {
                                                        if (value?.[key]) {
                                                            stringData += `&${key}=${value[key]}`;
                                                        }
                                                    }
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    let URL = `${ureport}excel?_u=minio:ZxSkStockDifMonthQtySumComJidu.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=?????????????????????1????????????????????????`;
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
                {summary ? <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    antd={{
                        rowKey: 'id',
                        size: 'small',
                        summary: (pageData) => {
                            // let totalb1 = 0;
                            // let totalb2 = 0;
                            // let totalb3 = 0;
                            // let totalb4 = 0;
                            // let totalb5 = 0;
                            // let totalb6 = 0;
                            // let totalb7 = 0;
                            // let totalb8 = 0;
                            // let totalb9 = 0;
                            // let totalb10 = 0;
                            // let totalb11 = 0;
                            // let totalb12 = 0;
                            // let totalb13 = 0;
                            // let totalb14 = 0;
                            // let totalb15 = 0;
                            // let totalb16 = 0;
                            // let totalb17 = 0;
                            // let totalb18 = 0;
                            // let totalb19 = 0;
                            // let totalb20 = 0;
                            // pageData.forEach(({ b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19, b20 }) => {
                            //     totalb1 += b1 ? b1 : 0;
                            //     totalb2 += b2 ? b2 : 0;
                            //     totalb3 += b3 ? b3 : 0;
                            //     totalb4 += b4 ? b4 : 0;
                            //     totalb5 += b5 ? b5 : 0;
                            //     totalb6 += b6 ? b6 : 0;
                            //     totalb7 += b7 ? b7 : 0;
                            //     totalb8 += b8 ? b8 : 0;
                            //     totalb9 += b9 ? b9 : 0;
                            //     totalb10 += b10 ? b10 : 0;
                            //     totalb11 += b11 ? b11 : 0;
                            //     totalb12 += b12 ? b12 : 0;
                            //     totalb13 += b13 ? b13 : 0;
                            //     totalb14 += b14 ? b14 : 0;
                            //     totalb15 += b15 ? b15 : 0;
                            //     totalb16 += b16 ? b16 : 0;
                            //     totalb17 += b17 ? b17 : 0;
                            //     totalb18 += b18 ? b18 : 0;
                            //     totalb19 += b19 ? b19 : 0;
                            //     totalb20 += b20 ? b20 : 0;
                            // });
                            return (
                                <>
                                    <QnnTable.Summary.Row>
                                        <QnnTable.Summary.Cell index={0}>??????</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={1}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={2}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={3}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={4}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={5}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={6}>{summary?.b1}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={7}>{summary?.b2}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={8}>{summary?.b3}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={9}>{summary?.b4}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={10}>{summary?.b5}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={11}>{summary?.b6}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={12}>{summary?.b7}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={13}>{summary?.b8}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={14}>{summary?.b9}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={15}>{summary?.b10}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={16}>{summary?.b11}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={17}>{summary?.b12}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={18}>{summary?.b13}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={19}>{summary?.b14}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={20}>{summary?.b15}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={21}>{summary?.b16}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={22}>{summary?.b17}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={23}>{summary?.b18}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={24}>{summary?.b19}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={25}>{summary?.b20}</QnnTable.Summary.Cell>
                                    </QnnTable.Summary.Row>
                                </>
                            )
                        }
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'exportZxSkStockDifMonthQtySumComJidu',
                        otherParams: { ...formData, queryPeriod }
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
                                title: '????????????',
                                dataIndex: 'resName',
                                width: 150,
                                key: 'resName',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'resCode',
                                width: 150,
                                key: 'resCode',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'spec',
                                width: 100,
                                key: 'spec',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'unit',
                                width: 100,
                                key: 'unit',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'mtType',
                                width: 100,
                                key: 'mtType',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????%',
                                dataIndex: 'sunHaoLv',
                                width: 120,
                                key: 'sunHaoLv',
                                fixed: 'left',
                                render:(data) => {
                                    if(data !== '0E-8'){
                                        return data;
                                    }
                                    return '0';
                                }
                            }
                        },
                        {
                            table: {
                                title: '????????????????????????(??????)',
                                dataIndex: 'title_1',
                                key: 'title_1',
                                width: 380,
                                children: [
                                    {
                                        title: '??????????????????',
                                        dataIndex: 'title_11',
                                        key: 'title_11',
                                        width: 120,
                                        children: [
                                            {
                                                title: '1',
                                                dataIndex: 'b1',
                                                key: 'b1',
                                                width: 120
                                            },
                                        ]
                                    },
                                    {
                                        title: '??????????????????(???)',
                                        dataIndex: 'title_12',
                                        key: 'title_12',
                                        width: 130,
                                        children: [
                                            {
                                                title: '2=3/1',
                                                dataIndex: 'b2',
                                                key: 'b2',
                                                width: 130
                                            },
                                        ]
                                    },
                                    {
                                        title: '??????????????????',
                                        dataIndex: 'title_13',
                                        key: 'title_13',
                                        width: 130,
                                        children: [
                                            {
                                                title: '3',
                                                dataIndex: 'b3',
                                                key: 'b3',
                                                width: 130
                                            },
                                        ]
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '????????????????????????',
                                dataIndex: 'title_2',
                                key: 'title_2',
                                width: 1010,
                                children: [
                                    {
                                        title: '????????????',
                                        dataIndex: 'title_21',
                                        key: 'title_21',
                                        width: 120,
                                        children: [
                                            {
                                                title: '4',
                                                dataIndex: 'b4',
                                                key: 'b4',
                                                width: 100
                                            },
                                        ]
                                    },
                                    {
                                        title: '??????????????????',
                                        dataIndex: 'title_22',
                                        key: 'title_22',
                                        width: 130,
                                        children: [
                                            {
                                                title: '5',
                                                dataIndex: 'b5',
                                                key: 'b5',
                                                width: 130
                                            },
                                        ]
                                    },
                                    {
                                        title: '???????????????',
                                        dataIndex: 'title_24',
                                        key: 'title_24',
                                        width: 120,
                                        children: [
                                            {
                                                title: '6',
                                                dataIndex: 'b6',
                                                key: 'b6',
                                                width: 120
                                            },
                                        ]
                                    },
                                    {
                                        title: '?????????????????????(???+???-)',
                                        dataIndex: 'title_26',
                                        key: 'title_26',
                                        width: 320,
                                        children: [
                                            {
                                                title: '????????????',
                                                dataIndex: 'title_261',
                                                key: 'title_261',
                                                width: 100,
                                                children: [
                                                    {
                                                        title: '7=4-1',
                                                        dataIndex: 'b7',
                                                        key: 'b7',
                                                        width: 100
                                                    }
                                                ]
                                            },
                                            {
                                                title: '????????????',
                                                dataIndex: 'title_262',
                                                key: 'title_262',
                                                width: 120,
                                                children: [
                                                    {
                                                        title: '8',
                                                        dataIndex: 'b8',
                                                        key: 'b8',
                                                        width: 120
                                                    }
                                                ]
                                            },
                                            {
                                                title: '?????????(%)',
                                                dataIndex: 'title_263',
                                                key: 'title_263',
                                                width: 100,
                                                children: [
                                                    {
                                                        title: '9=8/5',
                                                        dataIndex: 'b9',
                                                        key: 'b9',
                                                        width: 100
                                                    }
                                                ]
                                            },
                                        ]
                                    },
                                    {
                                        title: '?????????????????????(???+???-)',
                                        dataIndex: 'title_27',
                                        key: 'title_27',
                                        width: 320,
                                        children: [
                                            {
                                                title: '????????????',
                                                dataIndex: 'title_271',
                                                key: 'title_271',
                                                width: 100,
                                                children: [
                                                    {
                                                        title: '10=6-1',
                                                        dataIndex: 'b10',
                                                        key: 'b10',
                                                        width: 100
                                                    }
                                                ]
                                            },
                                            {
                                                title: '????????????',
                                                dataIndex: 'title_272',
                                                key: 'title_272',
                                                width: 120,
                                                children: [
                                                    {
                                                        title: '11=10*2/10000',
                                                        dataIndex: 'b11',
                                                        key: 'b11',
                                                        width: 120
                                                    }
                                                ]
                                            },
                                            {
                                                title: '?????????(%)',
                                                dataIndex: 'title_273',
                                                key: 'title_273',
                                                width: 100,
                                                children: [
                                                    {
                                                        title: '12=11/5',
                                                        dataIndex: 'b12',
                                                        key: 'b12',
                                                        width: 100
                                                    }
                                                ]
                                            },
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '??????????????????????????????',
                                dataIndex: 'title_3',
                                width: 900,
                                key: 'title_3',
                                children: [
                                    {
                                        title: '????????????????????????',
                                        dataIndex: 'title_31',
                                        key: 'title_31',
                                        width: 130,
                                        children: [
                                            {
                                                title: '13',
                                                dataIndex: 'b13',
                                                key: 'b13',
                                                width: 130
                                            },
                                        ]
                                    },
                                    {
                                        title: '????????????????????????',
                                        dataIndex: 'title_32',
                                        key: 'title_32',
                                        width: 130,
                                        children: [
                                            {
                                                title: '14',
                                                dataIndex: 'b14',
                                                key: 'b14',
                                                width: 130
                                            },
                                        ]
                                    },
                                    {
                                        title: '?????????????????????(???+???-)',
                                        dataIndex: 'title_35',
                                        key: 'title_35',
                                        width: 320,
                                        children: [
                                            {
                                                title: '????????????',
                                                dataIndex: 'title_351',
                                                key: 'title_351',
                                                width: 100,
                                                children: [
                                                    {
                                                        title: '15',
                                                        dataIndex: 'b15',
                                                        key: 'b15',
                                                        width: 100
                                                    }
                                                ]
                                            },
                                            {
                                                title: '????????????(???)',
                                                dataIndex: 'title_352',
                                                key: 'title_352',
                                                width: 120,
                                                children: [
                                                    {
                                                        title: '16',
                                                        dataIndex: 'b16',
                                                        key: 'b16',
                                                        width: 120
                                                    }
                                                ]
                                            },
                                            {
                                                title: '?????????(%)',
                                                dataIndex: 'title_353',
                                                key: 'title_353',
                                                width: 100,
                                                children: [
                                                    {
                                                        title: '17=16/14',
                                                        dataIndex: 'b17',
                                                        key: 'b17',
                                                        width: 100
                                                    }
                                                ]
                                            },
                                        ]
                                    },
                                    {
                                        title: '?????????????????????(???+???-)',
                                        dataIndex: 'title_36',
                                        key: 'title_36',
                                        width: 320,
                                        children: [
                                            {
                                                title: '????????????',
                                                dataIndex: 'title_361',
                                                key: 'title_361',
                                                width: 100,
                                                children: [
                                                    {
                                                        title: '18',
                                                        dataIndex: 'b18',
                                                        key: 'b18',
                                                        width: 100
                                                    }
                                                ]
                                            },
                                            {
                                                title: '????????????',
                                                dataIndex: 'title_362',
                                                key: 'title_362',
                                                width: 120,
                                                children: [
                                                    {
                                                        title: '19',
                                                        dataIndex: 'b19',
                                                        key: 'b19',
                                                        width: 120
                                                    }
                                                ]
                                            },
                                            {
                                                title: '?????????(%)',
                                                dataIndex: 'title_363',
                                                key: 'title_363',
                                                width: 100,
                                                children: [
                                                    {
                                                        title: '20=19/14',
                                                        dataIndex: 'b20',
                                                        key: 'b20',
                                                        width: 100
                                                    }
                                                ]
                                            },
                                        ]
                                    }
                                ]
                            }
                        },
                    ]}
                /> : null}
            </div>
        );
    }
}

export default index;