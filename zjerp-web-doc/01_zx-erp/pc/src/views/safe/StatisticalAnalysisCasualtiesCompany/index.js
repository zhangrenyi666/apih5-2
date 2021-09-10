import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, message as Msg, Row, Col, Table } from "antd";
const config = {
    antd: {
        rowKey: function (row) {
            return row.id
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    isShowRowSelect: false
};


class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            companyId:this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.companyId,
            year: null,
            a1: '',
            a2: '',
            a3: '',
            a4: '',
            a5: '',
            a6: '',
            a7: '',
            a8: '',
            a9: '',
            a10: '',
            a11: '',
            a12: '',
            a13: '',
            a14: '',
            a15: '',
            a16: '',
        }
    }
    componentDidMount() {

    }
    render() {
        const { companyId, year } = this.state;
        // let { myPublic: { domain, appInfo: { ureport } } } = this.props;
        return (
            <div>
                <Row>
                    <Col span={24}>
                        <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            wrappedComponentRef={(me) => {
                                this.formHasTicket = me;
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
                                    label: '填表单位（公章）',
                                    field: 'companyId',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'companyName',
                                        value: 'companyId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyBySelect'
                                    },
                                    placeholder: '请选择',
                                    span: 6
                                },
                                {
                                    type: 'year',
                                    label: '年份',
                                    field: 'year', //唯一的字段名 ***必传
                                    placeholder: '请选择',
                                    required: false,
                                    format: "YYYY",
                                    showTime: false, //不显示时间
                                    scope: false, //是否可选择范围
                                    span: 6
                                },
                                {
                                    type: 'component',
                                    field: 'aa',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'start', padding: '10px' }}><Button type="primary" onClick={() => {
                                                let value = this.formHasTicket.form.getFieldsValue();
                                                if (value.companyId) {
                                                    setTimeout(() => {
                                                        this.setState({
                                                            companyId: value.companyId ? value.companyId : null,
                                                            year: value.year ? value.year : '',
                                                        })
                                                        this.table.refresh()
                                                    }, 0)
                                                } else {
                                                    Msg.warn('请选择单位名称！')
                                                }
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    let value = this.formHasTicket.form.getFieldsValue();
                                                    let { myPublic: {  appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;

                                                    var URL = `${ureport}excel?_u=minio:zxSfEquManageItemForm.ureport.xml&access_token=${access_token}&_n=伤亡事故情况统计分析表（公司）.xlsx&delFlag=0&companyId=${value.companyId ? value.companyId : null}&year=${value.year ? new Date().getTime(value.year) : ''}`;
                                                    if (value.companyId) {
                                                        window.open(URL);
                                                    } else {
                                                        Msg.warn('请选择单位名称！')
                                                    }
                                                }}>导出</Button></div>
                                        );
                                    },
                                    span: 24
                                }
                            ]}
                        />
                    </Col>
                </Row>
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
                        apiName: 'getZxSfAccidentItemFormComList',
                        otherParams: {
                            companyId: companyId,
                            year: year
                        }
                    }}
                    antd={
                        {
                            rowKey: (row) => {
                                return row.documentId
                            },
                            summary: (pageData) => {
                                let totalA8 = 0
                                let totalA9 = 0
                                let totalA10 = 0
                                let totalA11 = 0
                                let totalA12 = 0
                                let totalA13 = 0
                                let totalA14 = 0
                                let totalA15 = 0

                                pageData.forEach(({ a8, a9,a10,a11,a12,a13,a14,a15}) => {
                                    totalA8 += +a8;
                                    totalA9 += +a9;
                                    totalA10 += +a10;
                                    totalA11 += +a11;
                                    totalA12 += +a12;
                                    totalA13 += +a13;
                                    totalA14 += +a14;
                                    totalA15 += +a15;
                                });

                                return (
                                    <Table.Summary.Row>

                                        <Table.Summary.Cell index={0} colSpan={3}>合计</Table.Summary.Cell>
                                        {/* 此处合并两个单元格 */}

                                        <Table.Summary.Cell index={3} align={'center'} ></Table.Summary.Cell>
                                        <Table.Summary.Cell index={4} align={'center'}></Table.Summary.Cell>
                                        <Table.Summary.Cell index={5} align={'center'}></Table.Summary.Cell>
                                        <Table.Summary.Cell index={6} align={'center'}></Table.Summary.Cell>
                                        <Table.Summary.Cell index={7} align={'center'}></Table.Summary.Cell>
                                        <Table.Summary.Cell index={8} align={'center'}>{totalA8}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={9} align={'center'}>{totalA9}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={10} align={'center'}>{totalA10}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={11} align={'center'}>{totalA11}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={12} align={'center'}>{totalA12}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={13} align={'center'}>{totalA13}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={14} align={'center'}>{totalA14}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={15} align={'center'}>{totalA15}</Table.Summary.Cell>
                                        <Table.Summary.Cell index={16} align={'center'}></Table.Summary.Cell>
                                    </Table.Summary.Row>
                                )
                            }
                        }
                    }
                    actionBtns={[]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSfAccidentItemId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '序号',
                                dataIndex: 'index',
                                align:'center',
                                key: 'index',
                                width: 100,
                                fixed: 'left',
                                render: (data, rowData, index) => {
                                    return index + 1;
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '发生事故时间',
                                dataIndex: 'a1',
                                align:'center',
                                key: 'a1',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '事故地点',
                                dataIndex: 'a2',
                                key: 'a2',
                                align:'center',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '事故单位名称',
                                width: 120,
                                dataIndex: 'a3',
                                align:'center',
                                key: 'a3'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '工程分类及等级、建设类型',
                                width: 120,
                                dataIndex: 'a4',
                                align:'center',
                                key: 'a4'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '事故原因',
                                width: 120,
                                dataIndex: 'a5',
                                align:'center',
                                key: 'a5'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '事故类别',
                                width: 120,
                                dataIndex: 'a6',
                                align:'center',
                                key: 'a6'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '伤亡人数',
                                dataIndex: 'swrs',
                                key: 'swrs',
                                children: [
                                    {
                                        title: '伤亡人员姓名',
                                        dataIndex: 'a7',
                                        align:'center',
                                        key: 'a7',
                                        width: 130,
                                    },
                                    {
                                        title: '死亡',
                                        dataIndex: 'a8',
                                        align:'center',
                                        key: 'a8',
                                        width: 130,
                                    },
                                    {
                                        title: '重伤',
                                        dataIndex: 'a9',
                                        align:'center',
                                        key: 'a9',
                                        width: 130,
                                    },
                                    {
                                        title: '轻伤',
                                        dataIndex: 'a10',
                                        align:'center',
                                        key: 'a10',
                                        width: 130,
                                    },
                                    {
                                        title: '非本企业人员',
                                        dataIndex: 'fbqyry',
                                        align:'center',
                                        key: 'fbqyry',
                                        width: 130,
                                        children: [
                                            {
                                                title: '死亡',
                                                dataIndex: 'a11',
                                                align:'center',
                                                key: 'a11',
                                                width: 130,
                                            },
                                            {
                                                title: '重伤',
                                                dataIndex: 'a12',
                                                align:'center',
                                                key: 'a12',
                                                width: 130,
                                            },
                                            {
                                                title: '轻伤',
                                                dataIndex: 'a13',
                                                align:'center',
                                                key: 'a13',
                                                width: 130,
                                            },
                                        ]
                                    },
                                ]
                            },
                        },
                        {
                            table: {
                                title: '直接经济损失（万元）',
                                dataIndex: 'a14',
                                align:'center',
                                key: 'a14',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '损失工作日（工日）',
                                dataIndex: 'a15',
                                align:'center',
                                key: 'a15'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '事故结果批复单位',
                                dataIndex: 'a16',
                                align:'center',
                                key: 'a16'
                            },
                            isInForm: false
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;