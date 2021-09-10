import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal, message as Msg, Drawer } from "antd";
import s from './style.less';
import ProjectConstructionContractManagement from './indexHT';
import EngineeringSettlement from '../EngineeringSettlement';
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: 'departmentId',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    limit: 999999999,
    curPage: 1,
    paginationConfig: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        const { departmentId, departmentName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        this.state = {
            formData: {
                departmentId: departmentId,
                departmentName: departmentName,
                deptFlag:ext1,
                periodTime: moment(new Date()).valueOf(),
                businessDivisionFlag: '1'
            },
            visible:false,
            visibles:false,
            drawerData:null
        }
    }
    onClose = () => {
        this.setState({
            visible:false,
            visibles:false
        })
    }
    render() {
        const { departmentId, departmentName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        const { formData, visible, visibles, drawerData } = this.state;
        console.log(this.props)
        return (
            <div className={s.root}>
                <QnnForm
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                        token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    wrappedComponentRef={(me) => {
                        this.form = me;
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
                            type: 'month',
                            label: '期次',
                            initialValue: new Date(),
                            allowClear: false,
                            field: 'periodTime',
                            span: 6
                        },
                        {
                            label: '单位名称',
                            field: 'departmentId',
                            type: 'select',
                            showSearch: true,
                            allowClear: false,
                            optionConfig: {
                                label: 'companyName',
                                value: 'companyId',
                                linkageFields: {
                                    departmentName: 'companyName',
                                    deptFlag:'departmentFlag'
                                }
                            },
                            initialValue: departmentId,
                            fetchConfig: {
                                apiName: 'getSysCompanyBySelect',
                                otherParams: {
                                    departmentId: departmentId
                                }
                            },
                            span: 6
                        },
                        {
                            field: 'departmentName',
                            type: 'string',
                            initialValue: departmentName,
                            hide: true,
                        },
                        {
                            field: 'deptFlag',
                            type: 'string',
                            initialValue: ext1,
                            hide: true,
                        },
                        {
                            label: '分类',
                            field: 'businessDivisionFlag',
                            type: 'select',
                            initialValue: '1',
                            optionConfig: {
                                label: 'label',
                                value: 'value',
                            },
                            optionData: [
                                {
                                    label: "分子公司",
                                    value: "1"
                                },
                                {
                                    label: "事业部",
                                    value: "2"
                                }
                            ],
                            span: 6
                        },
                        {
                            type: 'component',
                            field: 'component',
                            Component: obj => {
                                return (
                                    <div style={{ padding: '10px' }}><Button type="primary" onClick={() => {
                                        let value = this.form.form.getFieldsValue();
                                        value.periodTime = moment(value.periodTime).valueOf();
                                        this.setState({
                                            formData: value
                                        })
                                    }}>查询</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            confirm({
                                                content: '确定导出数据吗?',
                                                onOk: () => {
                                                    let value = this.form.form.getFieldsValue();
                                                    value.periodTime = moment(value.periodTime).valueOf();
                                                    this.props.myFetch('exportZxSaCentralizedSettlement', value).then(
                                                        ({ success, message, data }) => {
                                                            if (success) {
                                                                window.open(data);
                                                            } else {
                                                                Msg.error(message);
                                                            }
                                                        }
                                                    );
                                                }
                                            })
                                        }}>导出</Button></div>
                                );
                            },
                            span: 6
                        }
                    ]}
                />
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
                        apiName: 'getZxSaSettleControlLedger',
                        otherParams: { ...formData },
                        success: (obj) => {
                            if (obj.data && obj.data.length) {
                                let qnnSetState = this.table.qnnSetState;
                                qnnSetState({
                                    expandedRowKeys: [],
                                }, () => {
                                    qnnSetState({
                                        expandedRowKeys: [obj.data[0].departmentId]
                                    })
                                })
                            }
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'departmentId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '单位名称',
                                dataIndex: 'departmentName',
                                width: 150,
                                key: 'departmentName',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '工程类合同月度执行率',
                                dataIndex: 'title_1',
                                key: 'title_1',
                                width: 360,
                                children: [
                                    {
                                        title: '本月应执行合同份数',
                                        dataIndex: 'thisMonthContractNum',
                                        key: 'thisMonthContractNum',
                                        width: 100,
                                        render: (data, rowData) => {
                                            return <a onClick={() => {
                                                this.setState({
                                                    visible:true,
                                                    drawerData:rowData
                                                })
                                            }}>{data}</a>;
                                        }
                                    },
                                    {
                                        title: '本月结算合同份数',
                                        dataIndex: 'thisMonthSettleContractNum',
                                        key: 'thisMonthSettleContractNum',
                                        width: 100,
                                        render: (data, rowData) => {
                                            return <a onClick={() => {
                                                this.setState({
                                                    visibles:true,
                                                    drawerData:rowData
                                                })
                                            }}>{data}</a>;
                                        }
                                    },
                                    {
                                        title: '指标值%',
                                        dataIndex: 'thisMonthIndexValue',
                                        key: 'thisMonthIndexValue',
                                        width: 80
                                    },
                                    {
                                        title: '指标是否合格',
                                        dataIndex: 'thisMonthIndexValueQualifiedFlag',
                                        key: 'thisMonthIndexValueQualifiedFlag',
                                        width: 80,
                                        render: (data) => {
                                            if (data === '0') {
                                                return '否';
                                            } else if (data === '1') {
                                                return '是';
                                            } else {
                                                return '';
                                            }
                                        }
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '工程类合同月度执行率项目合格率',
                                dataIndex: 'title_2',
                                key: 'title_2',
                                width: 370,
                                children: [
                                    {
                                        title: '本月执行率合格在建、主体完工项目数量',
                                        dataIndex: 'thisMonthQualifiedConstructionProjectNum',
                                        key: 'thisMonthQualifiedConstructionProjectNum',
                                        width: 160
                                    },
                                    {
                                        title: '本月在建、主体完工项目总数量',
                                        dataIndex: 'thisMonthConstructionAllProjectNum',
                                        key: 'thisMonthConstructionAllProjectNum',
                                        width: 130
                                    },
                                    {
                                        title: '指标值%',
                                        dataIndex: 'thisMonthQualifiedIndexValue',
                                        key: 'thisMonthQualifiedIndexValue',
                                        with: 80
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '归档项目个数比',
                                dataIndex: 'title_3',
                                key: 'title_3',
                                width: 410,
                                children: [
                                    {
                                        title: '本月仍有应执行合同的归档项目数量',
                                        dataIndex: 'thisMonthHaveFileProjectNum',
                                        key: 'thisMonthHaveFileProjectNum',
                                        width: 150
                                    },
                                    {
                                        title: '本月在建、主体完工项目、交工、归档总数量',
                                        dataIndex: 'thisMonthConstructionFileAllNum',
                                        key: 'thisMonthConstructionFileAllNum',
                                        width: 180
                                    },
                                    {
                                        title: '指标值%',
                                        dataIndex: 'ratioOfArchiveItemsIndexValue',
                                        key: 'ratioOfArchiveItemsIndexValue',
                                        width: 80
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '累计结算成本比',
                                dataIndex: 'title_4',
                                key: 'title_4',
                                width: 740,
                                children: [
                                    {
                                        title: '开累结算计入成本金额(万元)',
                                        dataIndex: 'totalAmt',
                                        key: 'totalAmt',
                                        width: 130
                                    },
                                    {
                                        title: '库存金额(万元)',
                                        dataIndex: 'stockAmt',
                                        key: 'stockAmt',
                                        width: 80
                                    },
                                    {
                                        title: '财务账面成本(万元)',
                                        dataIndex: 'bookAmt',
                                        key: 'bookAmt',
                                        width: 110
                                    },
                                    {
                                        title: '已发生未入账的成本(万元)',
                                        dataIndex: 'unrecordAmt',
                                        key: 'unrecordAmt',
                                        width: 150
                                    },
                                    {
                                        title: '研发支出费用(万元)',
                                        dataIndex: 'rdAmt',
                                        key: 'rdAmt',
                                        width: 110
                                    },
                                    {
                                        title: '指标值%',
                                        dataIndex: 'totalSettleIndexValue',
                                        key: 'totalSettleIndexValue',
                                        width: 80
                                    },
                                    {
                                        title: '指标是否合格',
                                        dataIndex: 'totalSettleQualifiedFlag',
                                        key: 'totalSettleQualifiedFlag',
                                        width: 80,
                                        render: (data) => {
                                            if (data === '0') {
                                                return '否';
                                            } else if (data === '1') {
                                                return '是';
                                            } else {
                                                return '';
                                            }
                                        }
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '累计结算成本比合格率',
                                dataIndex: 'title_5',
                                key: 'title_5',
                                width: 460,
                                children: [
                                    {
                                        title: '本月合格的在建、主体完工、交工、归档项目数量',
                                        dataIndex: 'thisMonthQualifiedConstructionFileAllNum',
                                        key: 'thisMonthQualifiedConstructionFileAllNum',
                                        width: 200
                                    },
                                    {
                                        title: '本月在建、主体完工、交工、归档总数量',
                                        dataIndex: 'thisMonthConstructionFileTotalNum',
                                        key: 'thisMonthConstructionFileTotalNum',
                                        width: 180
                                    },
                                    {
                                        title: '指标值%',
                                        dataIndex: 'totalSettleQualifiedValue',
                                        key: 'totalSettleQualifiedValue',
                                        width: 80
                                    }
                                ]
                            },
                        }
                    ]}
                />
                {
                    visible ? <Drawer
                        title={'本月应执行合同份数'}
                        placement="right"
                        onClose={this.onClose}
                        visible={visible}
                        width={window.innerWidth * 0.8}
                        bodyStyle={{ height: window.innerHeight - 155 }}
                        className='DrawerQnnTable'
                        footer={<div style={{ textAlign: 'right', paddingRight: '20px' }}><Button type="dashed" onClick={() => {
                            this.setState({
                                visible: false
                            })
                        }}>取消</Button></div>}
                    >
                        <ProjectConstructionContractManagement {...this.props}/>
                    </Drawer> : null
                }
                {
                    visibles ? <Drawer
                        title={'本月结算合同份数'}
                        placement="right"
                        onClose={this.onClose}
                        visible={visibles}
                        width={window.innerWidth * 0.8}
                        bodyStyle={{ height: window.innerHeight - 155 }}
                        className='DrawerQnnTable'
                        footer={<div style={{ textAlign: 'right', paddingRight: '20px' }}><Button type="dashed" onClick={() => {
                            this.setState({
                                visibles: false
                            })
                        }}>取消</Button></div>}
                    >
                        <EngineeringSettlement {...this.props}/>
                    </Drawer> : null
                }
            </div>
        );
    }
}

export default index;