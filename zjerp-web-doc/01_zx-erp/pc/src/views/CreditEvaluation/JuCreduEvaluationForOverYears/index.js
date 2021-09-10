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
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props)
        this.state = {
            visible: false,
            loading: false,
            filter: [],
            isNeedClassifyData: true
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
                                    type: 'year',
                                    label: '年度',
                                    field: 'period',
                                    required: true,
                                    span: 8,
                                    initialValue: new Date()
                                },
                                {
                                    label: '协作单位名称',
                                    field: 'customerId',
                                    type: 'string',
                                    span: 8
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
                                                            value.customerId && filter.push('&customerId=' + value.customerId)
                                                            value.catID && filter.push('&catID=' + value.catID)
                                                            value.period && filter.push('&period=' + moment(value.period).valueOf())
                                                            var URL = `${ureport}excel?_u=minio:ZxCrOverYearsJYearCreditEvaItemReports.ureport.xml&access_token=${access_token}${filter.join('')}&_n=局历年信用评价报表`;
                                                            window.open(URL);
                                                        }
                                                    })
                                                }}>导出</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    let value = this.formHasTicket.form.getFieldsValue();
                                                    let filter = []
                                                    value.customerId && filter.push('&customerId=' + value.customerId)
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
                        wrappedComponentRef={(me) => {
                            this.table = me;
                        }}
                        {...config}
                        fetchConfig={{
                            apiName: 'getOverYearsZxCrJYearCreditEvaItemReports',
                            otherParams: () => {
                                let selectData = this.formHasTicket?.form?.getFieldsValue()
                                return {
                                    catID: selectData?.catID,
                                    customerId: selectData?.customerId,
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
                                    title: moment(this.formHasTicket?.form?.getFieldValue?.('period')).format('YYYY') - 2,
                                    dataIndex: 'title_1',
                                    key: 'title_1',
                                    children: [
                                        {
                                            title: '局评级等级',
                                            dataIndex: 'firstYearLevel',
                                            key: 'firstYearLevel',
                                            width: 110
                                        },
                                        {
                                            title: '被评价项目个数',
                                            dataIndex: 'firstProjectNum',
                                            key: 'firstProjectNum',
                                            width: 120
                                        },
                                        {
                                            title: '被评价下属单位个数',
                                            dataIndex: 'firstCompanyNum',
                                            key: 'firstCompanyNum',
                                            width: 150
                                        },
                                    ]
                                },
                            },
                            {
                                table: {
                                    title: moment(this.formHasTicket?.form?.getFieldValue?.('period')).format('YYYY') - 1,
                                    dataIndex: 'title_2',
                                    key: 'title_2',
                                    children: [
                                        {
                                            title: '局评级等级',
                                            dataIndex: 'secondYearLevel',
                                            key: 'secondYearLevel',
                                            width: 110
                                        },
                                        {
                                            title: '被评价项目个数',
                                            dataIndex: 'secondProjectNum',
                                            key: 'secondProjectNum',
                                            width: 120
                                        },
                                        {
                                            title: '被评价下属单位个数',
                                            dataIndex: 'secondCompanyNum',
                                            key: 'secondCompanyNum',
                                            width: 150
                                        },
                                    ]
                                },
                            },
                            {
                                table: {
                                    title: moment(this.formHasTicket?.form?.getFieldValue?.('period')).format('YYYY'),
                                    dataIndex: 'title_3',
                                    key: 'title_3',
                                    children: [
                                        {
                                            title: '局评级等级',
                                            dataIndex: 'thirdYearLevel',
                                            key: 'thirdYearLevel',
                                            width: 110
                                        },
                                        {
                                            title: '被评价项目个数',
                                            dataIndex: 'thirdProjectNum',
                                            key: 'thirdProjectNum',
                                            width: 120
                                        },
                                        {
                                            title: '被评价下属单位个数',
                                            dataIndex: 'thirdCompanyNum',
                                            key: 'thirdCompanyNum',
                                            width: 150
                                        },
                                    ]
                                },
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
                            }} src={`${ureport}preview?_u=minio:ZxCrOverYearsJYearCreditEvaItemReports.ureport.xml&_t=1,6&access_token=${access_token}${this.state.filter.join('')}
                            `}></iframe>
                        </Spin>
                    }

                </Modal>
            </div>
        );
    }
}

export default index;