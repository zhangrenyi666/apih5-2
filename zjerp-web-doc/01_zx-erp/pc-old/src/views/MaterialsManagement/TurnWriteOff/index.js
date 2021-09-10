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
            departmentId: '',
            materialType: '',
            resCode: '',
            inDate: null,
            outDate: null
        }
    }
    componentDidMount() {

    }
    render() {
        const { departmentId, materialType, resCode,inDate,outDate } = this.state;
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
                                    label: '单位名称',
                                    field: 'materialType',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'catName',
                                        value: 'id',
                                        children: 'zxSkResourceMaterialsList'
                                    },
                                    fetchConfig: {
                                        apiName: 'getZxSkResCategoryMaterialsAll'
                                    },
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    label: '物资编码',
                                    field: 'departmentId',//???
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysProjectBySelect'
                                    },
                                    placeholder: '请选择',
                                    span: 8
                                },
                                
                                {
                                    type: 'date',
                                    label: '开始时间',
                                    field: 'inDate',
                                    span: 8
                                },
                                {
                                    type: 'date',
                                    label: '结束时间',
                                    field: 'outDate',
                                    span: 8
                                },
                                {
                                    type: 'component',
                                    field: 'aa',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'center', padding: '10px' }}><Button type="primary" onClick={() => {
                                                let value = this.formHasTicket.form.getFieldsValue();
                                                if (value.departmentId) {
                                                    this.setState({
                                                        departmentId: '',
                                                        materialType: '',
                                                        resCode: '',
                                                        inDate: null,
                                                        outDate: null
                                                    }, () => {
                                                        this.setState({
                                                            departmentId: value.departmentId ? value.departmentId:null,
                                                            materialType: value.materialType ? value.materialType :null,
                                                            resCode: value.resCode ? value.resCode :null,
                                                            inDate: value.inDate ? new Date(value.inDate._d).getTime():null,
                                                            outDate: value.outDate ? new Date(value.outDate._d).getTime() :null,
                                                        }, () => {
                                                            this.table.refresh();
                                                        })
                                                    })
                                                } else {
                                                    Msg.warn('请选择项目名称！')
                                                }
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    let value = this.formHasTicket.form.getFieldsValue();
                                                    var URL = `${ureport}excel?_u=file:ReceivingDynamic.xml&url=${domain}&departmentId=${value.departmentId ? value.departmentId:null}&materialType=${value.materialType ? value.materialType :null}&resCode=${value.resCode ? value.resCode :null}&inDate=${new Date(value.inDate ? value.inDate._d:null).getTime()}&outDate=${new Date(value.outDate ? value.outDate._d :null).getTime()}`;
                                                    if (value.departmentId) {
                                                        window.open(URL);
                                                    } else {
                                                        Msg.warn('请选择筛选条件！')
                                                    }
                                                }}>导出</Button></div>
                                        );
                                    },
                                    span: 8
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
                    fetchConfig={departmentId ? {
                        apiName: 'getZxEqEquipZiYouCLForm',
                        otherParams: {
                            companyID: '1',
                            departmentId: departmentId,
                            materialType: materialType,
                            resCode: resCode,
                            inDate: inDate,
                            outDate:outDate
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
                        }, {
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
                                title: '项目名称',
                                dataIndex: 'ownerOrgName',
                                key: 'ownerOrgName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '冲账单编号',
                                dataIndex: 'manageOrgID',
                                width: 200,
                                tooltip: 23,
                                key: 'manageOrgID'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '发票号',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'equipName',
                                key: 'equipName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '开票日期',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资来源',
                                width: 120,
                                dataIndex: 'model',
                                key: 'model'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '预收单编号',
                                width: 180,
                                tooltip: 23,
                                dataIndex: 'factory',
                                key: 'factory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资编码',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'outFactoryDate',
                                key: 'outFactoryDate'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资名称',
                                width: 120,
                                dataIndex: 'acceptanceDate',
                                key: 'acceptanceDate'
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '物资规格',
                                width: 120,
                                dataIndex: 'originvalue',
                                key: 'originvalue'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '单位',
                                width: 120,
                                dataIndex: 'originvalue',
                                key: 'originvalue'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '数量',
                                width: 120,
                                dataIndex: 'originvalue',
                                key: 'originvalue'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '含税单价',
                                width: 120,
                                dataIndex: 'originvalue',
                                key: 'originvalue'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '不含税单价',
                                width: 120,
                                dataIndex: 'originvalue',
                                key: 'originvalue'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '原值',
                                width: 120,
                                dataIndex: 'originvalue',
                                key: 'originvalue'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '累计摊销',
                                width: 120,
                                dataIndex: 'originvalue',
                                key: 'originvalue'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '净值',
                                width: 120,
                                dataIndex: 'originvalue',
                                key: 'originvalue'
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