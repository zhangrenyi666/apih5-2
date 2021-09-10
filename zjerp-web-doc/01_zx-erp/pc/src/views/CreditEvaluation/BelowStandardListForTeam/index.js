import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Row, Col, Modal, message as Msg, Spin } from "antd";
import moment from 'moment';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    paginationConfig: false,
    pageSize: 999,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props)
        this.state = {
            isNeedClassifyData: true,
            visible: false,
            loading: false,
            filter: []
        }
    }
    handleCancel = () => {
        this.setState({ visible: false, loading: false });
    }
    render() {
        let { myPublic: { appInfo: { ureport } } } = this.props;
        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
        const { isNeedClassifyData, loading } = this.state
        return (
            <div>
                <Row>
                    <Col span={24}>
                        <QnnForm
                            fetch={this.props.myFetch}
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
                                    type: 'year',
                                    label: '年度',
                                    field: 'period',
                                    required: true,
                                    span: 8,
                                    initialValue: moment(new Date()).endOf('year')
                                },
                                {
                                    label: '专业类别',
                                    type: 'select',
                                    field: 'catID',
                                    required: true,
                                    optionConfig: {
                                        label: 'catName',
                                        value: 'id',
                                    },
                                    fetchConfig: {
                                        apiName: 'getZxCrProjectEvaluationListCatName',
                                    },
                                    span: 8
                                },
                                {
                                    type: 'component',
                                    field: 'aa',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'center', padding: '10px' }}><Button type="primary" onClick={() => {
                                                let value = this.formHasTicket.form.getFieldsValue();
                                                if (!value.period) {
                                                    Msg.warn("请选择年度")
                                                    return
                                                }
                                                if (!value.catID) {
                                                    Msg.warn("请选择专业类别")
                                                    return
                                                }
                                                this.table.refresh();
                                                this.setState({ isNeedClassifyData: false }, () => {
                                                    this.setState({ isNeedClassifyData: true })
                                                })
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    confirm({
                                                        content: '确定导出数据吗?',
                                                        onOk: () => {
                                                            let value = this.formHasTicket.form.getFieldsValue();
                                                            let filter = []
                                                            value.catID && filter.push('&catID=' + value.catID)
                                                            value.period && filter.push('&period=' + moment(value.period).valueOf())
                                                            var URL = `${ureport}excel?_u=minio:ZxCrJYearDCreditEvaItemReports.ureport.xml&access_token=${access_token}${filter.join('')}&_n=协作队伍不合格名录`;
                                                            window.open(URL);
                                                        }
                                                    })
                                                }}>导出</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    let value = this.formHasTicket.form.getFieldsValue();
                                                    let filter = []
                                                    value.catID && filter.push('&catID=' + value.catID)
                                                    value.period && filter.push('&period=' + moment(value.period).valueOf())
                                                    this.setState({
                                                        visible: true,
                                                        loading: true,
                                                        filter: filter
                                                    })
                                                }}>打印</Button>
                                            </div>
                                        );
                                    },
                                    span: 8
                                }
                            ]}
                        />
                    </Col>
                </Row>
                {isNeedClassifyData ?
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
                            apiName: 'getDZxCrJYearCreditEvaItemReports',
                            otherParams: () => {
                                let selectData = this.formHasTicket?.form?.getFieldsValue()
                                return {
                                    catID: selectData?.catID,
                                    period: selectData?.period ? moment(selectData.period).valueOf() : null
                                }
                            }
                        }}
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
                                    title: '协作单位名称',
                                    dataIndex: 'customerName',
                                    key: 'customerName',
                                    width: 150,
                                }
                            },
                            {
                                table: {
                                    title: '组织机构代码',
                                    dataIndex: 'orgCertificate',
                                    key: 'orgCertificate',
                                    width: 200,
                                }
                            },
                            {
                                table: {
                                    title: '专业类别',
                                    dataIndex: 'catName',
                                    key: 'catName',
                                    width: 200,
                                }
                            },
                            {
                                table: {
                                    title: '法定代表人',
                                    dataIndex: 'corparation',
                                    key: 'corparation',
                                    width: 200,
                                }
                            },
                            {
                                table: {
                                    title: '信用评价结果',
                                    dataIndex: 'title_2',
                                    key: 'title_2',
                                    children: [
                                        {
                                            title: moment(this.formHasTicket?.form?.getFieldValue?.('period')).format('YYYY') - 2,
                                            dataIndex: 'firstYearLevel',
                                            key: 'firstYearLevel',
                                            width: 110
                                        },
                                        {
                                            title: moment(this.formHasTicket?.form?.getFieldValue?.('period')).format('YYYY') - 1,
                                            dataIndex: 'secondYearLevel',
                                            key: 'secondYearLevel',
                                            width: 120
                                        },
                                        {
                                            title: moment(this.formHasTicket?.form?.getFieldValue?.('period')).format('YYYY'),
                                            dataIndex: 'thirdYearLevel',
                                            key: 'thirdYearLevel',
                                            width: 150
                                        },
                                    ]
                                },
                            },
                            {
                                table: {
                                    title: '直接降D行为',
                                    dataIndex: 'dLevel',
                                    key: 'dLevel',
                                    width: 200,
                                    render: (h) => {
                                        if (h === '1') {
                                            return '是'
                                        }
                                    }
                                }
                            },
                        ]}
                    /> : null
                }
                <Modal
                    width={'90%'}
                    style={{ paddingBottom: '0', top: '0' }}
                    title="打印"
                    visible={this.state.visible}
                    footer={null}
                    onCancel={this.handleCancel}
                    destroyOnClose={this.handleCancel}
                    bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                    centered={true}
                    wrapClassName={'modals'}
                >
                    {
                        <Spin spinning={loading}>
                            <iframe title="myf" width='100%' height={window.innerHeight * 0.8} frameBorder={0} onLoad={() => {
                                this.setState({ loading: false })
                            }} src={`${ureport}preview?_u=minio:ZxCrJYearDCreditEvaItemReports.ureport.xml&_t=1,6&access_token=${access_token}${this.state.filter.join('')}
                            `}></iframe>
                        </Spin>
                    }

                </Modal>
            </div>
        );
    }
}

export default index;