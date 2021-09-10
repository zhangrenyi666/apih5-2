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
                                    label: '机构名称',
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
                                                    Msg.warn('请选择单位名称！')
                                                }
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    let value = this.formHasTicket.form.getFieldsValue();
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;

                                                    var URL = `${ureport}excel?_u=minio:zxSfEquManageItemForm.ureport.xml&access_token=${access_token}&_n=特种设备台账（公司）.xlsx&delFlag=0&companyId=${value.companyId ? value.companyId : null}&endDate=${value.endDate? new Date().getTime(value.endDate) :''}`;
                                                    if (value.companyId) {
                                                        if (value.endDate) {
                                                            window.open(URL);
                                                        } else {
                                                            Msg.warn('请选择截止时间！')
                                                        }
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
                        //         title: '序号',
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
                                title: '所属单位',
                                dataIndex: 'companyName',
                                key: 'companyName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '设备编号',
                                dataIndex: 'resCode',
                                key: 'resCode',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '名称型号',
                                width: 120,
                                dataIndex: 'resName',
                                key: 'resName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '特种设备型式实验合格证',
                                dataIndex: 'tzsbxssyhgz',
                                key: 'tzsbxssyhgz',
                                children:[
                                    {
                                        title: '证书编号',
                                        dataIndex: 'cardNo',
                                        key: 'cardNo',
                                        width: 130,
                                    },
                                    {
                                        title: '发证单位',
                                        dataIndex: 'sendCardUnit',
                                        key: 'sendCardUnit',
                                        width: 130,
                                    },
                                ]
                            },
                        },
                        {
                            table: {
                                title: '设备操作人员',
                                dataIndex: 'sbczry',
                                key: 'sbczry',
                                children:[
                                    {
                                        title: '姓名',
                                        dataIndex: 'empName',
                                        key: 'empName',
                                        width: 130,
                                    },
                                    {
                                        title: '证书编号',
                                        dataIndex: 'empCardNo',
                                        key: 'empCardNo',
                                        width: 130,
                                    },
                                    {
                                        title: '有效期',
                                        dataIndex: 'empValidDate',
                                        key: 'empValidDate',
                                        width: 130,
                                    },
                                ]
                            },
                        },
                        {
                            table: {
                                title: '设备管理人员',
                                dataIndex: 'equipManager',
                                key: 'equipManager'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '安全管理人员',
                                dataIndex: 'safeManager',
                                key: 'safeManager'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '所在地',
                                dataIndex: 'equipAddress',
                                key: 'equipAddress'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '备注',
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