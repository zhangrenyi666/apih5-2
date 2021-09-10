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
                                    label: '学历',
                                    field: 'eduLevel',
                                    optionData: [
                                        { label: '博士', value: '1' },
                                        { label: '本科', value: '0' },
                                        { label: '初中', value: '2' },
                                        { label: '大专', value: '3' },
                                        { label: '高中', value: '4' },
                                        { label: '硕士', value: '5' },
                                        { label: '小学', value: '6' },
                                        { label: '中专', value: '7' }
                                    ],
                                    optionConfig: { label: 'label', value: 'value' },
                                    span: 6
                                },
                                {
                                    label: '职称',
                                    type: 'select',
                                    field: 'title',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: '无',
                                            value: '0'
                                        },
                                        {
                                            label: '初级',
                                            value: '1'
                                        },
                                        {
                                            label: '中级',
                                            value: '2'
                                        },
                                        {
                                            label: '高级',
                                            value: '3'
                                        }
                                    ],
                                    placeholder: '请选择',
                                    span: 6
                                },
                                {
                                    type: 'select',
                                    label: '是否为注安师',
                                    field: 'isExpert', //唯一的字段名 ***必传
                                    placeholder: '请选择',
                                    span: 6,
                                    optionData: [
                                        {
                                            label: '否',
                                            value: '0'
                                        },
                                        {
                                            label: '是',
                                            value: '1'
                                        },
                                    ],
                                    optionConfig: {//下拉选项配置 可为func
                                        label: 'label',
                                        value: 'value'
                                    },
                                    span: 6
                                },
                                {
                                    type: 'select',
                                    label: '是否从事专职安全工作',
                                    field: 'isWorking', //唯一的字段名 ***必传
                                    placeholder: '请选择',
                                    span: 6,
                                    optionData: [
                                        {
                                            label: '否',
                                            value: '0'
                                        },
                                        {
                                            label: '是',
                                            value: '1'
                                        },
                                    ],
                                    optionConfig: {//下拉选项配置 可为func
                                        label: 'label',
                                        value: 'value'
                                    },
                                    span: 6
                                },
                                {
                                    type: 'year',
                                    label: '年份 ',
                                    field: 'year', //唯一的字段名 ***必传
                                    placeholder: '请选择',
                                    required: false,
                                    format: "YYYY-MM-DD",
                                    showTime: false, //不显示时间
                                    scope: false, //是否可选择范围
                                    span: 6
                                },
                                {
                                    type: 'date',
                                    label: '证书到期日期起',
                                    field: 'safeDate,', //唯一的字段名 ***必传
                                    placeholder: '请选择',
                                    required: false,
                                    format: "YYYY-MM-DD",
                                    showTime: false, //不显示时间
                                    scope: false, //是否可选择范围
                                    span: 6
                                },
                                {
                                    type: 'date',
                                    label: '证书到期日期至',
                                    field: 'safeDate,', //唯一的字段名 ***必传
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
                                                    Msg.warn('请选择单位名称！')
                                                }
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    let value = this.formHasTicket.form.getFieldsValue();
                                                    var URL = `${ureport}excel?_u=file:ZxSkResInOutStockAllAmt.xml&url=${domain}&delFlag=0&orgID=${value.orgID ? value.orgID : null}&year=${value.year ? value.year : null}&eduLevel=${value.eduLevel ? value.eduLevel : null}&title=${value.title ? value.title : null}&isWorking=${value.isWorking ? value.isWorking : null}`;
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
                                align:'center',
                                key: 'name',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '性别',
                                dataIndex: 'sex',
                                align:'center',
                                key: 'sex',
                                width: 120,
                                render: (data) => {
                                    switch (data) {
                                        case '0':
                                            return '男'
                                        case '1':
                                            return '女'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '年龄',
                                width: 120,
                                dataIndex: 'age',
                                align:'center',
                                key: 'age',
                            },
                        },
                        {
                            table: {
                                title: '学历',
                                width: 120,
                                dataIndex: 'stockAmt',
                                align:'center',
                                key: 'stockAmt',
                                render: (data) => {
                                    switch (data) {
                                        case '0':
                                            return '本科'
                                        case '1':
                                            return '博士'
                                        case '2':
                                            return '初中'
                                        case '3':
                                            return '大专'
                                        case '4':
                                            return '高中'
                                        case '5':
                                            return '硕士'
                                        case '6':
                                            return '小学'
                                        case '7':
                                            return '中专'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '职称',
                                width: 120,
                                dataIndex: 'eduLevel',
                                align:'center',
                                key: 'eduLevel',
                                render: (data) => {
                                    switch (data) {
                                        case '0':
                                            return '无'
                                        case '1':
                                            return '初级'
                                        case '2':
                                            return '中级'
                                        case '3':
                                            return '高级'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '注安师证号',
                                width: 120,
                                dataIndex: 'cardNo',
                                align:'center',
                                key: 'cardNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '从事安全工作累计年数',
                                width: 120,
                                dataIndex: 'workAge',
                                align:'center',
                                key: 'workAge'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '交通部安全证书编号（C类）',
                                width: 120,
                                dataIndex: 'safeCardNo',
                                align:'center',
                                key: 'safeCardNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '发证日期至到期日期',
                                width: 120,
                                dataIndex: 'safeDate',
                                align:'center',
                                key: 'safeDate'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '建设部安全证书编号（C类）',
                                width: 120,
                                dataIndex: 'buildCardNo',
                                align:'center',
                                key: 'buildCardNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '发证日期至到期日期',
                                width: 120,
                                dataIndex: 'buildDate',
                                align:'center',
                                key: 'buildDate'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '是否正从事专职安全工作',
                                width: 120,
                                dataIndex: 'isWorking',
                                align:'center',
                                key: 'isWorking',
                                render: (data) => {
                                    switch (data) {
                                        case '0':
                                            return '否'
                                        case '1':
                                            return '是'
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