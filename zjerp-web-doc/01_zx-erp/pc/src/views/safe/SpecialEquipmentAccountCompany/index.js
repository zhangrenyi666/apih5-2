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
            endDate:null,
            companyName:'',
            resCode:'',
            resName:'',
            cardNo:'',
            sendCardUnit:'',
            empName:'',
            empCardNo:'',
            empValidDate:'',
            equipManager:'',
            safeManager:'',
            equipAddress:'',
            remarks:''
        }
    }
    componentDidMount() {

    }
    render() {
        const { companyId, endDate } = this.state;
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
                                    type: 'date',
                                    label: '???????????? ',
                                    field: 'endDate', //?????????????????? ***??????
                                    placeholder: '?????????',
                                    required: false,
                                    format: "YYYY-MM-DD",
                                    showTime: false, //???????????????
                                    scope: false, //?????????????????????
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
                                                            endDate:value.endDate? value.endDate :''
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

                                                    var URL = `${ureport}excel?_u=minio:zxSfEquManageItemForm.ureport.xml&access_token=${access_token}&_n=??????????????????????????????.xlsx&delFlag=0&companyId=${value.companyId ? value.companyId : null}&endDate=${value.endDate? new Date().getTime(value.endDate) :''}`;
                                                    if (value.companyId) {
                                                        if (value.endDate) {
                                                            window.open(URL);
                                                        } else {
                                                            Msg.warn('????????????????????????')
                                                        }
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
                    fetchConfig={{
                        apiName: 'getZxSfEquManageItemUreportFormList',
                        otherParams: {
                            companyId: companyId,
                            endDate: endDate,
                        }
                    } }
                    actionBtns={[]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSfEquManageItemId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        // {
                        //     table: {
                        //         title: '??????',
                        //         dataIndex: 'index',
                        //         key: 'index',
                        //         width: 50,
                        //         fixed: 'left',
                        //         render: (data, rowData, index) => {
                        //             return index + 1;
                        //         }
                        //     },
                        //     isInForm: false
                        // },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'companyName',
                                key: 'companyName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'resCode',
                                key: 'resCode',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 120,
                                dataIndex: 'resName',
                                key: 'resName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '?????????????????????????????????',
                                dataIndex: 'tzsbxssyhgz',
                                key: 'tzsbxssyhgz',
                                children:[
                                    {
                                        title: '????????????',
                                        dataIndex: 'cardNo',
                                        key: 'cardNo',
                                        width: 130,
                                    },
                                    {
                                        title: '????????????',
                                        dataIndex: 'sendCardUnit',
                                        key: 'sendCardUnit',
                                        width: 130,
                                    },
                                ]
                            },
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'sbczry',
                                key: 'sbczry',
                                children:[
                                    {
                                        title: '??????',
                                        dataIndex: 'empName',
                                        key: 'empName',
                                        width: 130,
                                    },
                                    {
                                        title: '????????????',
                                        dataIndex: 'empCardNo',
                                        key: 'empCardNo',
                                        width: 130,
                                    },
                                    {
                                        title: '?????????',
                                        dataIndex: 'empValidDate',
                                        key: 'empValidDate',
                                        width: 130,
                                    },
                                ]
                            },
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'equipManager',
                                key: 'equipManager'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'safeManager',
                                key: 'safeManager'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '?????????',
                                dataIndex: 'equipAddress',
                                key: 'equipAddress'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'remarks',
                                key: 'remarks'
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