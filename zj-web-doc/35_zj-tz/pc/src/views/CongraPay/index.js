import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import { Button, message as Msg, Row, Col, Spin } from "antd";
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
            dataTime:(new Date()).getTime(),
            loading:false,
            loadingSearch:false
        }
    }
    componentDidMount() {
        
    }
    render() {
        const { dataTime } = this.state;
        return (
            <div>
                <Spin tip="Loading..." spinning={this.state.loading}>
                <Spin spinning={this.state.loadingSearch}><Row><Col span={8}><QnnForm
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
                            label: '计量截止日期',
                            field: 'dataTime',
                            type: 'date',
                            initialValue: () => {
                                return new Date()
                            },
                            placeholder: '请选择',
                            span: 24,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 14 }
                                }
                            }
                        }
                    ]}
                /></Col>
                        <Col span={16}>
                            <div style={{ padding: '11px 11px 11px 71px' }}>
                                <Button type="primary" onClick={() => {
                                    let value = this.formHasTicket.form.getFieldsValue();
                                    if (value.dataTime) {
                                        this.setState({
                                            dataTime:new Date(value.dataTime).getTime()
                                        }, () => {
                                            this.table.refresh();
                                        })
                                    } else {
                                        Msg.warn('请选择计量截止日期！')
                                    }
                                }}>查询</Button>
                            </div>
                        </Col>
                    </Row></Spin>
                
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    // fetchConfig={{
                    //     apiName: 'getZjTzCompSupReportList',
                    //     otherParams: {
                    //         dataTime:dataTime
                    //     }
                    // }}
                        data={[]}
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
                            isInForm: false,
                            table: {
                                width: 80,
                                align: 'center',
                                title: '序号', //表头标题
                                dataIndex: 'no', //表格里面的字段
                                key: 'no',//表格的唯一key    
                                render: (data,rows,index) => {
                                    return index + 1;
                                }
                            },
                        },
                        {
                            table: {
                                title: '管理单位',
                                dataIndex: 'supClassName',
                                key: 'supClassName'
                            },
                            isInForm: false,
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'registerPerson',
                                key: 'registerPerson'
                            },
                            isInForm: false,
                        },
                        {
                            table: {
                                title: "合同价及变更金额",
                                dataIndex: "age",
                                children: [
                                    {
                                        title: "原合同",
                                        dataIndex: "supDeptId",
                                        width: 100,
                                    },
                                    {
                                        title: "修正",
                                        dataIndex: "supDeptId",
                                        width: 100,
                                    },
                                    {
                                        title: "变更",
                                        dataIndex: "supDeptId",
                                        width: 100,
                                    },
                                    {
                                        title: "变更后",
                                        dataIndex: "supDeptId",
                                        width: 100,
                                    }
                                ]
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: "累计计量金额",
                                dataIndex: "age",
                                children: [
                                    {
                                        title: "原合同",
                                        dataIndex: "supDeptId",
                                        width: 100,
                                    },
                                    {
                                        title: "修正",
                                        dataIndex: "supDeptId",
                                        width: 100,
                                    },
                                    {
                                        title: "变更",
                                        dataIndex: "supDeptId",
                                        width: 100,
                                    },
                                    {
                                        title: "变更后",
                                        dataIndex: "supDeptId",
                                        width: 100,
                                    }
                                ]
                            },
                            isInForm:false
                        },
                    ]}
                    />
                </Spin>
            </div>
        );
    }
}

export default index;