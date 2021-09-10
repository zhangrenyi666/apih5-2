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
            orgID: '',
            whOrgID: '',
        }
    }
    componentDidMount() {

    }
    render() {
        const { orgID, whOrgID, resourceId } = this.state;
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
                                    label: '单位名称',
                                    field: 'orgID',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'orgName',
                                        value: 'iecmOrgID'
                                    },
                                    fetchConfig: {
                                        apiName: 'getZxEqIecmOrgList'
                                    },
                                    placeholder: '请选择',
                                    span: 5
                                },
                                {
                                    label: '材料类型',
                                    field: 'orgID',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'orgName',
                                        value: 'iecmOrgID'
                                    },
                                    fetchConfig: {
                                        apiName: 'getZxEqIecmOrgList'
                                    },
                                    placeholder: '请选择',
                                    span: 5
                                },
                                {
                                    label: '期次',
                                    field: 'orgID',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'orgName',
                                        value: 'iecmOrgID'
                                    },
                                    fetchConfig: {
                                        apiName: 'getZxEqIecmOrgList'
                                    },
                                    placeholder: '请选择',
                                    span: 5
                                },
                                {
                                    label: '是否完工',
                                    field: 'resourceId',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        { label: "全部", value: "0" },
                                        { label: "否", value: "1" },
                                        { label: "是", value: "2" }
                                    ],
                                    placeholder: '请选择',
                                    span: 5,
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 7 },
                                            sm: { span: 12 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 17 },
                                            sm: { span: 12 }
                                        }
                                    }
                                },
                                {
                                    type: 'component',
                                    field: 'aa',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'center', padding: '10px' }}><Button type="primary" onClick={() => {
                                                let value = this.formHasTicket.form.getFieldsValue();
                                        
                                                if (value.orgID) {
                                                    if (value.whOrgID) {
                                                        this.setState({
                                                            orgID: '',
                                                            whOrgID: '',
                                                            resourceId: ''
                                                        }, () => {
                                                            this.setState({
                                                                orgID: value.orgID,
                                                                whOrgID: value.whOrgID,
                                                                resourceId: value.resourceId
                                                            }, () => {
                                                                this.table.refresh();
                                                            })
                                                        })
                                                    } else {
                                                        Msg.warn('请选择仓库！')
                                                    }
                                                } else {
                                                    Msg.warn('请选择工程项目！')
                                                }
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    // const { ext1,userId,curCompany } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
                                                    // var URL = `${ureport}excel?_u=file:zjTzExecutivePersonnel.xml&url=${domain}&userId=${userId}&ext1=${ext1}&projectId=${curCompany.projectId}`;
                              
                                                    // window.open(URL);
                                                }}>导出</Button></div>
                                        );
                                    },
                                    span: 4
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
                    fetchConfig={orgID && whOrgID ? {
                        apiName: 'getZxSkStockDataList',
                        otherParams: {
                            // companyID: '1',
                            orgID: orgID,
                            whOrgID: whOrgID,
                            resourceId: resourceId
                        }
                    } : {}}
                    actionBtns={[]}
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
                                title: '物资编码',
                                dataIndex: 'projectId',
                                key: 'projectId'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资名称',
                                dataIndex: 'projectId',
                                key: 'projectId'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资规格',
                                dataIndex: 'resourceID',
                                key: 'resourceID',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '计量单位',
                                width: 120,
                                dataIndex: 'resName',
                                key: 'resName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '上月结存',
                                dataIndex: 'spec',
                                key: 'spec',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'unit',
                                        key: 'unit',
                                        width: 130,
                                    },
                                    {
                                        title: '平均单价(元)',
                                        dataIndex: 'unit',
                                        key: 'unit',
                                        width: 130,
                                    },
                                    {
                                        title: '金额(元)',
                                        dataIndex: 'unit',
                                        key: 'unit',
                                        width: 130,
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '本月收入',
                                dataIndex: 'resName',
                                key: 'resName',
                                children: [
                                    {
                                        title: '甲供',
                                        width: 120,
                                        dataIndex: 'resName',
                                        key: 'resName',
                                    },
                                    {
                                        title: '其他',
                                        width: 120,
                                        dataIndex: 'resName',
                                        key: 'resName',
                                    },
                                    {
                                        title: '自购',
                                        width: 120,
                                        dataIndex: 'resName',
                                        key: 'resName',
                                    },
                                    {
                                        title: '预收',
                                        width: 120,
                                        dataIndex: 'resName',
                                        key: 'resName',
                                    },
                                    {
                                        title: '甲控',
                                        width: 120,
                                        dataIndex: 'resName',
                                        key: 'resName',
                                    },
                                    {
                                        title: '合计',
                                        dataIndex: 'resName',
                                        key: 'resName',
                                        children: [
                                            {
                                                title: '数量',
                                                width: 120,
                                                dataIndex: 'resName',
                                                key: 'resName',
                                            },
                                            {
                                                title: '平均单价(元)',
                                                width: 120,
                                                dataIndex: 'resName',
                                                key: 'resName',
                                            },
                                            {
                                                title: '金额(元)',
                                                width: 120,
                                                dataIndex: 'resName',
                                                key: 'resName',
                                            }
                                        ]
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '工程耗用',
                                dataIndex: 'resName',
                                key: 'resName',
                                children: [
                                    {
                                        title: '数量',
                                        width: 120,
                                        dataIndex: 'resName',
                                        key: 'resName',
                                    },
                                    {
                                        title: '平均单价(元)',
                                        width: 120,
                                        dataIndex: 'resName',
                                        key: 'resName',
                                    },
                                    {
                                        title: '金额',
                                        width: 120,
                                        dataIndex: 'resName',
                                        key: 'resName',
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '调拨',
                                dataIndex: 'resName',
                                key: 'resName',
                                children: [
                                    {
                                        title: '加工改制',
                                        width: 120,
                                        dataIndex: 'resName',
                                        key: 'resName',
                                    },
                                    {
                                        title: '调拨',
                                        width: 120,
                                        dataIndex: 'resName',
                                        key: 'resName',
                                    },
                                    {
                                        title: '合计',
                                        dataIndex: 'resName',
                                        key: 'resName',
                                        children: [
                                            {
                                                title: '数量',
                                                width: 120,
                                                dataIndex: 'resName',
                                                key: 'resName',
                                            },
                                            {
                                                title: '平均单价(元)',
                                                width: 120,
                                                dataIndex: 'resName',
                                                key: 'resName',
                                            },
                                            {
                                                title: '金额',
                                                width: 120,
                                                dataIndex: 'resName',
                                                key: 'resName',
                                            }
                                        ]
                                    },
                                    
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '盈(+)亏(-)',
                                dataIndex: 'resName',
                                key: 'resName',
                                children: [
                                    {
                                        title: '数量',
                                        width: 120,
                                        dataIndex: 'resName',
                                        key: 'resName',
                                    },
                                    {
                                        title: '金额(元)',
                                        width: 120,
                                        dataIndex: 'resName',
                                        key: 'resName',
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '本月结存',
                                dataIndex: 'resName',
                                key: 'resName',
                                children: [
                                    {
                                        title: '数量',
                                        width: 120,
                                        dataIndex: 'resName',
                                        key: 'resName',
                                    },
                                    {
                                        title: '平均单价(元)',
                                        width: 120,
                                        dataIndex: 'resName',
                                        key: 'resName',
                                    },
                                    {
                                        title: '金额(元)',
                                        width: 120,
                                        dataIndex: 'resName',
                                        key: 'resName',
                                    }
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