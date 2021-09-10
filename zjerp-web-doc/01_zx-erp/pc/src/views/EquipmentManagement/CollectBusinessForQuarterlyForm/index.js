import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal, Row, Col, message as Msg, } from "antd";
import moment from 'moment';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'zxEqEquipLimitPriceId',
        size: 'small'
    },
    paginationConfig: false,
    isShowRowSelect: false,
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            orgID: '',
            purDate: '',
            Data: []
        }
    }
    componentDidMount() {
        this.getData()
    }
    getData() {
        const orgID = this.formHasTicket?.form?.getFieldValue('orgID')
        const purDate = this.formHasTicket?.form?.getFieldValue('purDate')
        this.props.myFetch('getZxEqEquipListForRunQuarterly', { companyId: orgID, purDate })
            .then(({ data, success, message }) => {
                if (success) {
                    let newData = [];
                    data.map((item) => {
                        newData.push({ typeName: '总台数(台)', count: item.countIn, In: item.countIn, out: item.countOut, key: item?.departmentId + '总台数' })
                        newData.push({ typeName: '总功率(kw)', count: item.powerIn, In: item.powerIn, out: item.powerOut, key: item?.departmentId + '总功率' })
                        newData.push({ typeName: '固定资产净值(万元)', count: item.leftIn, In: item.leftIn, out: item.leftOut, key: item?.departmentId + '固定资产净值' })
                        newData.push({ typeName: '固定资产原值(万元)', count: item.orgIn, In: item.orgIn, out: item.orgOut, key: item?.departmentId + '固定资产原值' })
                        return newData;
                    })
                    this.setState({ Data: newData })
                } else {
                    Msg.error(message)
                }
            })
    }
    render() {
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        let { myPublic: {appInfo: { ureport } } } = this.props;
        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
        return (
            <div>
                <Row>
                    <Col span={24}>
                        <QnnForm
                            wrappedComponentRef={(me) => {
                                this.formHasTicket = me;
                            }}
                            fetch={this.props.myFetch}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 6 },
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    xs: { span: 18 },
                                    sm: { span: 18 }
                                }
                            }}
                            formConfig={[
                                {
                                    label: '公司',
                                    field: 'orgID',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'companyName',
                                        value: 'companyId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyProject',
                                        otherParams: {
                                            departmentId
                                        }
                                    },
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    label: '年度',
                                    field: 'purDate',
                                    type: 'year',
                                    placeholder: '请选择',
                                    span: 8,
                                    initialValue: new Date()
                                },
                                {
                                    type: 'component',
                                    field: 'aa',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'center', padding: '10px' }}><Button type="primary" onClick={() => {
                                                this.getData()
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    confirm({
                                                        content: '确定导出数据吗?',
                                                        onOk: () => {
                                                            let value = this.formHasTicket.form.getFieldsValue();
                                                            let filter = []
                                                            value.orgID && filter.push('&orgID=' + value.orgID)
                                                            value.purDate && filter.push('&purDate=' + moment(value.purDate).valueOf())
                                                            var URL = `${ureport}excel?_u=minio:zxEqEquipListForRunQuarterly.xml&access_token=${access_token}&delFlag=0${filter.join('')}&_n=运营管理业务采集季报`;
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
                    pageSize={9999}
                    data={this.state.Data}
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
                                title: '指标',
                                dataIndex: 'abcTypeName3',
                                key: 'abcTypeName',
                                render: function (data, rowData, index) {
                                    const obj = {
                                        children: '自有施工机械设备状况',
                                        props: {},
                                    };
                                    if (index % 4 === 0) {
                                        obj.props.rowSpan = 4;
                                    } else {
                                        obj.props.rowSpan = 0;
                                    }
                                    return obj;
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '项目',
                                dataIndex: 'typeName',
                                colSpan: 0,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '数值',
                                dataIndex: 'swrs',
                                align: 'center',
                                key: 'swrs',
                                children: [
                                    {
                                        title: '合计',
                                        dataIndex: 'count',
                                        width: 100,
                                    },
                                    {
                                        title: '境内',
                                        dataIndex: 'In',
                                        width: 100,
                                    },
                                    {
                                        title: '境外',
                                        dataIndex: 'out',
                                        width: 100,
                                    },
                                ]
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