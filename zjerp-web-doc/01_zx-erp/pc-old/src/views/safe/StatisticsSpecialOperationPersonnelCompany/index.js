import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, message as Msg, Row, Col } from "antd";
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
        // name sex opProjName sendName cardNo checkDate orgName
        this.state = {
            companyId:this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.companyId,
            name:'',
            sex:'',
            opProjName:'',
            sendName:'',
            cardNo:'',
            checkDate:'',
            orgName:'',
            endDate:null
        }
    }
    componentDidMount() {

    }
    render() {
        const { companyId, opProjName, beginDate, endDate } = this.state;
        let { myPublic: { domain, appInfo: { ureport } } } = this.props;
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
                                    label: '公司名称',
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
                                    type: 'date',
                                    label: '截止时间 ',
                                    field: 'endDate', //唯一的字段名 ***必传
                                    placeholder: '请选择',
                                    required: false,
                                    format: "YYYY-MM-DD",
                                    showTime: false, //不显示时间
                                    scope: false, //是否可选择范围
                                    span: 6
                                },
                                {
                                    type: 'select',
                                    label: '准操作项目',
                                    field: 'opProjName', //唯一的字段名 ***必传
                                    span:6,
                                    multiple: true,
                                    placeholder: '请选择',
                                    optionData: [
                                      { label: '电工作业', value: '0' },
                                      { label: "金属焊接切割作业", value: '1' },
                                      { label: '起重机械含电梯作业', value: '2' },
                                      { label: "企业内机动车辆驾驶", value: '3' },
                                      { label: '登高架设作业', value: '4' },
                                      { label: "锅炉作业", value: '5' },
                                      { label: '压力容器操作', value: '6' },
                                      { label: "爆破作业", value: '7' },
                                      { label: '其他', value: '8' },
                                    ],
                                    optionConfig: {
                                      label: 'label',
                                      value: 'value',
                                    },
                                    placeholder: '请选择',
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
                                                            opProjName: value.opProjName.length ? value.opProjName.join(',') : '',
                                                            endDate:value.endDate? value.endDate :''
                                                        })
                                                        this.table.refresh()
                                                    }, 0)
                                                } else {
                                                    Msg.warn('请选择单位名称！')
                                                }
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    let value = this.formHasTicket.form.getFieldsValue();
                                                    var URL = `${ureport}excel?_u=file:ZxSkResInOutStockAllAmt.xml&url=${domain}&delFlag=0&companyId=${value.companyId ? value.companyId : null}&opProjName=${value.riskLevel ? value.riskLevel : ''}&endDate=${value.endDate? value.endDate :''}`;
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
                    fetchConfig={ {
                        apiName: 'getZxSfSpecialEmpItemDetailFormComList',
                        otherParams: {
                            companyId: companyId,
                            opProjName: opProjName,
                            beginDate: beginDate,
                            endDate: endDate,
                        }
                    } }
                    actionBtns={[]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSfSpecialEmpItemId',
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
                            isInForm: false
                        },
                        {
                            table: {
                                title: '姓名',
                                dataIndex: 'name',
                                key: 'name'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '性别',
                                dataIndex: 'sex',
                                key: 'sex',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '准操作项目',
                                width: 120,
                                dataIndex: 'opProjName',
                                key: 'opProjName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '发证机关',
                                dataIndex: 'sendName',
                                key: 'sendName',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '证件号码',
                                dataIndex: 'cardNo',
                                key: 'cardNo',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '复审日期',
                                dataIndex: 'checkDate',
                                key: 'checkDate'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '所在项目名称',
                                dataIndex: 'orgName',
                                key: 'orgName'
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