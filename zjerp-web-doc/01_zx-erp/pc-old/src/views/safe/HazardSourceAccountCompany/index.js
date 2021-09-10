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
        this.state = {
            companyId:this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.companyId,
            proArea:null,
            doing:null,
            riskFactors:null,
            accident:null,
            lee:null,
            bee:null,
            cee:null,
            dee:null,
            riskLevel:'',
            yfcs:null,
        }
    }
    componentDidMount() {

    }

    render() {
        const { companyId,riskLevel} = this.state;
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
                                    type: 'select',
                                    label: '危险等级',
                                    field: 'riskLevel', //唯一的字段名 ***必传
                                    placeholder: '请选择',
                                    span: 6,
                                    optionData: [//默认选项数据//可为function (props)=>array
                                        {
                                            label: "1",
                                            value: "1"
                                        },
                                        {
                                            label: "2",
                                            value: "2"
                                        },
                                        {
                                            label: "3",
                                            value: "3"
                                        },
                                        {
                                            label: "4",
                                            value: "4"
                                        },
                                        {
                                            label: "5",
                                            value: "5"
                                        },
                                        {
                                            label: "全部",
                                            value: ""
                                        },
                                    ],
                                    onChange: (value, { itemData, ...other }) => {

                                    }
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
                                                            riskLevel: value.riskLevel ? value.riskLevel : '',
                                                        })
                                                        this.table.refresh()
                                                    }, 0)
                                                } else {
                                                    Msg.warn('请选择项目名称！')
                                                }
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    let value = this.formHasTicket.form.getFieldsValue();
                                                    var URL = `${ureport}excel?_u=file:ZxSkResInOutStockAllAmt.xml&url=${domain}&delFlag=0&companyId=${value.companyId ? value.companyId : null}&riskLevel=${ value.riskLevel ? value.riskLevel : ''}`;
                                                    if (value.companyId) {
                                                        window.open(URL);
                                                    } else {
                                                        Msg.warn('请选择项目名称！')
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
                        apiName: 'getZxSfHazardRoomAttUreportFormComList',
                        otherParams: {
                            companyId: companyId ,
                            riskLevel:riskLevel
                        }
                    } }
                    actionBtns={[]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSfHazardRoomAttId',
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
                                title: '过程区域',
                                dataIndex: 'proArea',
                                key: 'proArea'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '行为（活动）或设备\环境',
                                dataIndex: 'doing',
                                key: 'doing',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '危险因素',
                                width: 120,
                                dataIndex: 'riskFactors',
                                key: 'riskFactors'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '可导致伤害（事故）',
                                dataIndex: 'accident',
                                key: 'accident',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '作业条件危险性评价',
                                dataIndex: 'zytjwxxpj',
                                key: 'zytjwxxpj',
                                children:[
                                    {
                                        title: 'L',
                                        dataIndex: 'lee',
                                        key: 'lee',
                                        width: 130,
                                    },
                                    {
                                        title: 'E',
                                        dataIndex: 'bee',
                                        key: 'bee',
                                        width: 130,
                                    },
                                    {
                                        title: 'C',
                                        dataIndex: 'cee',
                                        key: 'cee',
                                        width: 130,
                                    },
                                    {
                                        title: 'D',
                                        dataIndex: 'dee',
                                        key: 'dee',
                                        width: 130,
                                    },
                                ]
                            },
                        },
                        {
                            table: {
                                title: '风险等级',
                                dataIndex: 'riskLevel',
                                key: 'riskLevel'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '预防措施',
                                dataIndex: 'yfcs',
                                key: 'yfcs'
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