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
            orgID: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId,
            year: null,
            eduLevel: '',
            title: '',
            isWorking: ''
        }
    }
    componentDidMount() {

    }

    render() {
        const { orgID, year, eduLevel, title, isWorking } = this.state;
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
                                    label: '????????????',
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
                                    placeholder: '?????????',
                                    span: 6

                                },

                                {
                                    type: 'select',
                                    label: '??????',
                                    field: 'eduLevel',
                                    optionData: [
                                        { label: '??????', value: '1' },
                                        { label: '??????', value: '0' },
                                        { label: '??????', value: '2' },
                                        { label: '??????', value: '3' },
                                        { label: '??????', value: '4' },
                                        { label: '??????', value: '5' },
                                        { label: '??????', value: '6' },
                                        { label: '??????', value: '7' }
                                    ],
                                    optionConfig: { label: 'label', value: 'value' },
                                    span: 6
                                },
                                {
                                    label: '??????',
                                    type: 'select',
                                    field: 'title',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: '???',
                                            value: '0'
                                        },
                                        {
                                            label: '??????',
                                            value: '1'
                                        },
                                        {
                                            label: '??????',
                                            value: '2'
                                        },
                                        {
                                            label: '??????',
                                            value: '3'
                                        }
                                    ],
                                    placeholder: '?????????',
                                    span: 6
                                },
                                {
                                    type: 'select',
                                    label: '??????????????????',
                                    field: 'isExpert', //?????????????????? ***??????
                                    placeholder: '?????????',
                                    span: 6,
                                    optionData: [
                                        {
                                            label: '???',
                                            value: '0'
                                        },
                                        {
                                            label: '???',
                                            value: '1'
                                        },
                                    ],
                                    optionConfig: {//?????????????????? ??????func
                                        label: 'label',
                                        value: 'value'
                                    },
                                    span: 6
                                },
                                {
                                    type: 'select',
                                    label: '??????????????????????????????',
                                    field: 'isWorking', //?????????????????? ***??????
                                    placeholder: '?????????',
                                    span: 6,
                                    optionData: [
                                        {
                                            label: '???',
                                            value: '0'
                                        },
                                        {
                                            label: '???',
                                            value: '1'
                                        },
                                    ],
                                    optionConfig: {//?????????????????? ??????func
                                        label: 'label',
                                        value: 'value'
                                    },
                                    span: 6
                                },
                                {
                                    type: 'year',
                                    label: '?????? ',
                                    field: 'year', //?????????????????? ***??????
                                    placeholder: '?????????',
                                    required: false,
                                    format: "YYYY-MM-DD",
                                    showTime: false, //???????????????
                                    scope: false, //?????????????????????
                                    span: 6
                                },
                                {
                                    type: 'date',
                                    label: '?????????????????????',
                                    field: 'safeDate,', //?????????????????? ***??????
                                    placeholder: '?????????',
                                    required: false,
                                    format: "YYYY-MM-DD",
                                    showTime: false, //???????????????
                                    scope: false, //?????????????????????
                                    span: 6
                                },
                                {
                                    type: 'date',
                                    label: '?????????????????????',
                                    field: 'safeDate,', //?????????????????? ***??????
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
                                                if (value.orgID) {
                                                    setTimeout(() => {
                                                        this.setState({
                                                            companyId: value.companyId ? value.companyId : null,
                                                            year: value.year ? value.year : null,
                                                            eduLevel: value.eduLevel ? value.eduLevel : null,
                                                            title: value.title ? value.title : null,
                                                            isWorking: value.isWorking ? value.isWorking : null
                                                        })
                                                        this.table.refresh()
                                                    }, 0)
                                                } else {
                                                    Msg.warn('????????????????????????')
                                                }
                                            }}>??????</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    let value = this.formHasTicket.form.getFieldsValue();
                                                    var URL = `${ureport}excel?_u=file:ZxSkResInOutStockAllAmt.xml&url=${domain}&delFlag=0&orgID=${value.orgID ? value.orgID : null}&year=${value.year ? value.year : null}&eduLevel=${value.eduLevel ? value.eduLevel : null}&title=${value.title ? value.title : null}&isWorking=${value.isWorking ? value.isWorking : null}`;
                                                    if (value.orgID) {
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
                    fetchConfig={{
                        apiName: 'getZxSfProjectEmployeeFormList',
                        otherParams: {
                            orgID: orgID,
                            year: year,
                            eduLevel: eduLevel,
                            title: title,
                            isWorking: isWorking
                        }
                    }}
                    actionBtns={[]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSfProjectEmployeeId',
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
                                title: '??????',
                                dataIndex: 'name',
                                align:'center',
                                key: 'name',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'sex',
                                align:'center',
                                key: 'sex',
                                width: 120,
                                render: (data) => {
                                    switch (data) {
                                        case '0':
                                            return '???'
                                        case '1':
                                            return '???'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 120,
                                dataIndex: 'age',
                                align:'center',
                                key: 'age',
                            },
                        },
                        {
                            table: {
                                title: '??????',
                                width: 120,
                                dataIndex: 'stockAmt',
                                align:'center',
                                key: 'stockAmt',
                                render: (data) => {
                                    switch (data) {
                                        case '0':
                                            return '??????'
                                        case '1':
                                            return '??????'
                                        case '2':
                                            return '??????'
                                        case '3':
                                            return '??????'
                                        case '4':
                                            return '??????'
                                        case '5':
                                            return '??????'
                                        case '6':
                                            return '??????'
                                        case '7':
                                            return '??????'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 120,
                                dataIndex: 'eduLevel',
                                align:'center',
                                key: 'eduLevel',
                                render: (data) => {
                                    switch (data) {
                                        case '0':
                                            return '???'
                                        case '1':
                                            return '??????'
                                        case '2':
                                            return '??????'
                                        case '3':
                                            return '??????'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????',
                                width: 120,
                                dataIndex: 'cardNo',
                                align:'center',
                                key: 'cardNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????????????????',
                                width: 120,
                                dataIndex: 'workAge',
                                align:'center',
                                key: 'workAge'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????????????????C??????',
                                width: 120,
                                dataIndex: 'safeCardNo',
                                align:'center',
                                key: 'safeCardNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????????????????',
                                width: 120,
                                dataIndex: 'safeDate',
                                align:'center',
                                key: 'safeDate'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????????????????C??????',
                                width: 120,
                                dataIndex: 'buildCardNo',
                                align:'center',
                                key: 'buildCardNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????????????????',
                                width: 120,
                                dataIndex: 'buildDate',
                                align:'center',
                                key: 'buildDate'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '?????????????????????????????????',
                                width: 120,
                                dataIndex: 'isWorking',
                                align:'center',
                                key: 'isWorking',
                                render: (data) => {
                                    switch (data) {
                                        case '0':
                                            return '???'
                                        case '1':
                                            return '???'
                                    }
                                }
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