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
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            orgID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            tranContext: null,
            tranContextList: []
        }
    }
    componentDidMount() {
        this.props.myFetch('getBaseCodeSelect', { itemId: 'jiaoYuLeiXing' }).then(res => {
            this.setState({ tranContextList: res.data })
        })
    }
    renderContent = (value, row, index) => {
        const obj = {
            children: '合计',
            props: {},
        };
        if (index === 1) {
            obj.props.colSpan = 0;
        }
        return obj;
    };

    render() {
        const { orgID, tranContext } = this.state;
        // let { myPublic: {  appInfo: {  } } } = this.props;
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
                                    label: '项目名称',
                                    field: 'orgID',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'projectName',
                                        value: 'projectId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysProjectBySelect'
                                    },
                                    placeholder: '请选择',
                                    span: 6
                                },
                                {
                                    type: 'select',
                                    label: '教育类型',
                                    field: 'tranContext',
                                    allowClear: false,
                                    optionConfig: {
                                        label: 'itemName',
                                        value: 'itemId'
                                    },
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'jiaoYuLeiXing'
                                        }
                                    },
                                    span: 6
                                },
                                {
                                    type: 'component',
                                    field: 'aa',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'start', padding: '10px' }}><Button type="primary" onClick={() => {
                                                let value = this.formHasTicket.form.getFieldsValue();
                                                if (value.orgID) {
                                                    setTimeout(() => {
                                                        this.setState({
                                                            orgID: value.orgID ? value.orgID : null,
                                                            tranContext: value.tranContext ? value.tranContext : '',
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

                                                    var URL = `${ureport}excel?_u=minio:zxSfEduItem.ureport.xml&access_token=${access_token}&_n=安全教育花名册.xlsx&delFlag=0&orgID=${value.orgID ? value.orgID : null}&tranContext=${value.tranContext ? value.tranContext : ''
                                                        }`;
                                                    if (value.orgID) {
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
                    fetchConfig={orgID ? {
                        apiName: 'getZxSfEduUReportFormList',
                        otherParams: {
                            orgID: orgID,
                            tranContext: tranContext
                        }
                    } : {}}
                    actionBtns={[]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSfEduItemId',
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
                                title: '姓名',
                                dataIndex: 'name',
                                key: 'name',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '工种',
                                dataIndex: 'empType',
                                key: 'empType',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '身份证号',
                                width: 120,
                                dataIndex: 'idCard',
                                key: 'idCard',
                            },
                        },
                        {
                            table: {
                                title: '教育类型',
                                width: 120,
                                dataIndex: 'tranContext',
                                key: 'tranContext',
                                render: (data) => {
                                    let showData = ''
                                    this.state.tranContextList.map(item => {
                                        if (item.itemId === data) {
                                            return showData = item.itemName
                                        } else {
                                            return false
                                        }
                                    })
                                    return showData
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '备注',
                                width: 120,
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