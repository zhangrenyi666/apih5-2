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
                                    label: '????????????',
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
                                    placeholder: '?????????',
                                    span: 6
                                },
                                {
                                    type: 'select',
                                    label: '????????????',
                                    field: 'riskLevel', //?????????????????? ***??????
                                    placeholder: '?????????',
                                    span: 6,
                                    optionData: [//??????????????????//??????function (props)=>array
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
                                            label: "??????",
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
                                                    Msg.warn('????????????????????????')
                                                }
                                            }}>??????</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    let value = this.formHasTicket.form.getFieldsValue();
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    
                                                    var URL = `${ureport}excel?_u=minio:zxSfHazardRoomAttCom.ureport.xml&access_token=${access_token}&_n=???????????????????????????.xlsx&delFlag=0&companyId=${value.companyId ? value.companyId : null}&riskLevel=${ value.riskLevel ? value.riskLevel : ''}`;
                                                    if (value.companyId) {
                                                        window.open(URL);
                                                    } else {
                                                        Msg.warn('????????????????????????')
                                                    }
                                                }}>??????</Button></div>
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
                                title: '??????',
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
                                title: '????????????',
                                dataIndex: 'proArea',
                                key: 'proArea'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????????????????/??????',
                                dataIndex: 'doing',
                                key: 'doing',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 120,
                                dataIndex: 'riskFactors',
                                key: 'riskFactors'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????????????????',
                                dataIndex: 'accident',
                                key: 'accident',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????????????????',
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
                                title: '????????????',
                                dataIndex: 'riskLevel',
                                key: 'riskLevel'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
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