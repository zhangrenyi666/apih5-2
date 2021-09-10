import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { push } from "react-router-redux";
import { Col, message as Msg, Modal, Row, InputNumber } from 'antd';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'zxBuBudgetDetailsId',
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight * 0.6
        }
    },
    drawerConfig: {
        width: '1200px'
    },
    firstRowIsSearch: false,
    isShowRowSelect: false,
    pageSize: 99999999,
    paginationConfig: false,
    viewRowSize:999
};
class AfterBudgetingDetial extends Component {
    constructor(props) {
        super(props);
        this.state = {
            orgID: props.router.location.query.orgID,
            budgetBookID: props.router.location.query.zxBuBudgetBookId,
            projectName: ''
        }
    }

    componentDidMount() {
        const { orgID } = this.state
        this.props.myFetch('getZxCtContractDetailByOrgID', { orgID, contrStatus: '1' }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        projectName: data.projectName
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }

    render() {
        const { orgID, projectName, budgetBookID } = this.state
        return (
            <div>
                <Row>
                    <Col span={24} style={{ textAlign: "center" }}>
                        <h2>
                            标后预算编制-{projectName}
                        </h2>
                    </Col>
                </Row>
                <Row>
                    <Col span={24}>
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table = me;
                            }}
                            {...config}
                            drawerShowToggle={(obj) => {
                                let { drawerIsShow } = obj;
                                if (!drawerIsShow) {
                                    obj.btnCallbackFn.refresh();
                                }
                            }}
                            fetchConfig={{
                                apiName: 'getZxBuBudgetDetailsList',
                                otherParams: {
                                    budgetBookID: budgetBookID,
                                    orgID: orgID
                                }
                            }}
                            formConfig={[
                                {
                                    isInTable: false,
                                    isInForm: false,
                                    form: {
                                        field: 'zxBuBudgetDetailsId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    isInForm: false,
                                    form: {
                                        field: 'budgetBookID',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    isInForm: false,
                                    form: {
                                        field: 'budgetType',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    isInForm: false,
                                    form: {
                                        field: 'budgetElementType',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    isInForm: false,
                                    form: {
                                        field: 'budgetElementUnit',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '序号',
                                        dataIndex: 'serialNumber',
                                        key: 'serialNumber',
                                        align: 'center',
                                        render: (text, row, index) => {
                                            if (index < 13) {
                                                return <span>{text}</span>;
                                            }
                                            return {
                                                children: <span>{row.budgetElement}</span>,
                                                props: {
                                                    colSpan: 2,
                                                },
                                            };
                                        },
                                    }
                                },
                                {
                                    table: {
                                        title: '费用名称',
                                        dataIndex: 'budgetElement',
                                        key: 'budgetElement',
                                        align: 'center',
                                        render: (text, row, index) => {
                                            if (index < 13) {
                                                return <span>{text}</span>;
                                            }
                                            return {
                                                children: <span>{text}</span>,
                                                props: {
                                                    colSpan: 0,
                                                },
                                            };
                                        },
                                    }
                                },
                                {
                                    table: {
                                        title: '费用(元)',
                                        dataIndex: 'budgetElementFirstFree',
                                        key: 'budgetElementFirstFree',
                                        align: 'center',
                                        render: (val, rowData) => {
                                            if (rowData.serialNumber === '6-3') {
                                                return val + '%'
                                            } else {
                                                return val
                                            }
                                        }
                                    }
                                },
                                {
                                    table: {
                                        title: '费用所占建筑安装工程费比例(%)',
                                        width: 200,
                                        dataIndex: 'proportion',
                                        key: 'proportion',
                                        align: 'center'
                                    }
                                },
                                {
                                    table: {
                                        title: '编制',
                                        dataIndex: 'needDeduct1',
                                        key: 'needDeduct1',
                                        align: 'center',
                                        render: (val, rowData) => {
                                            if (rowData.serialNumber === '6'
                                                || rowData.serialNumber === '6-1'
                                                || rowData.serialNumber === '6-2'
                                                || rowData.serialNumber === '6-3'
                                                || rowData.budgetElement === '标后预算费用合计(元)'
                                                || rowData.budgetElement === '建筑安装工程费(元)'
                                                || rowData.budgetElement === '单位预留费用(元)'
                                                || rowData.budgetElement === '标后预算切块比例(%)') {
                                                return '';
                                            } else if (rowData.serialNumber === '1') {
                                                return <a onClick={(obj) => {
                                                    const { mainModule } = this.props.myPublic.appInfo;
                                                    const { orgID, budgetBookID } = this.state;
                                                    const zxBuBudgetBookId = budgetBookID;
                                                    this.props.dispatch(
                                                        push(`${mainModule}AfterBudgetingProjectCost?orgID=${orgID}&zxBuBudgetBookId=${zxBuBudgetBookId}`)
                                                    )
                                                }}>编辑</a>;
                                            } else if (rowData.serialNumber === '2') {
                                                return <a onClick={(obj) => {
                                                    const { mainModule } = this.props.myPublic.appInfo;
                                                    const { orgID, budgetBookID } = this.state;
                                                    const zxBuBudgetBookId = budgetBookID;
                                                    this.props.dispatch(
                                                        push(`${mainModule}AfterBudgetingSiteCost?orgID=${orgID}&zxBuBudgetBookId=${zxBuBudgetBookId}`)
                                                    )
                                                }}>编辑</a>;
                                            } else if (rowData.serialNumber === '3') {
                                                return <a onClick={(obj) => {
                                                    const { mainModule } = this.props.myPublic.appInfo;
                                                    const { orgID, budgetBookID } = this.state;
                                                    const zxBuBudgetBookId = budgetBookID;
                                                    this.props.dispatch(
                                                        push(`${mainModule}AfterBudgetingFacilityCost?orgID=${orgID}&zxBuBudgetBookId=${zxBuBudgetBookId}`)
                                                    )
                                                }}>编辑</a>;
                                            } else if (rowData.serialNumber === '4') {
                                                return <a onClick={(obj) => {
                                                    const { mainModule } = this.props.myPublic.appInfo;
                                                    const { orgID, budgetBookID } = this.state;
                                                    const zxBuBudgetBookId = budgetBookID;
                                                    this.props.dispatch(
                                                        push(`${mainModule}AfterBudgetingIntendanceCost?orgID=${orgID}&zxBuBudgetBookId=${zxBuBudgetBookId}`)
                                                    )
                                                }}>编辑</a>;
                                            } else if (rowData.serialNumber === '5') {
                                                return <a onClick={() => {
                                                    this.newData = rowData;
                                                    const { budgetElement, budgetElementFirstFree, firstJtFee } = rowData
                                                    confirm({
                                                        title: <h3>{budgetElement}</h3>,
                                                        content:
                                                            <div>
                                                                <Row>
                                                                    <span>请输入预算费用:</span>
                                                                    <InputNumber
                                                                        defaultValue={budgetElementFirstFree ? budgetElementFirstFree : 0}
                                                                        style={{ margin: '0px 10px', width: "200px" }}
                                                                        onBlur={(v) => { this.newData.budgetElementFirstFree = v.target.value }}
                                                                        placeholder="请输入..." />
                                                                </Row>
                                                                <Row style={{ marginTop: "10px" }}>
                                                                    <span>请输入进项税金:</span>
                                                                    <InputNumber
                                                                        defaultValue={firstJtFee ? firstJtFee : 0}
                                                                        style={{ margin: '0px 10px', width: "200px" }}
                                                                        onBlur={(v) => { this.newData.firstJtFee = v.target.value }}
                                                                        placeholder="请输入..." />
                                                                </Row>
                                                            </div>,
                                                        icon: false,
                                                        okText: "确认",
                                                        cancelText: "取消",
                                                        onOk: () => {
                                                            this.props.myFetch('updateZxBuBudgetDetails', this.newData).then(
                                                                ({ success, message, data }) => {
                                                                    if (success) {
                                                                        Msg.success(message);
                                                                        this.table.refresh();
                                                                    } else {
                                                                        Msg.error(message);
                                                                    }
                                                                });
                                                        }
                                                    })
                                                }}
                                                >编辑</a>
                                            } else if (rowData.serialNumber === '7'
                                                || rowData.serialNumber === '8'
                                                || rowData.serialNumber === '9'
                                                || rowData.serialNumber === '10'
                                                || rowData.budgetElement === '中标合同价(元)') {
                                                return <a onClick={() => {
                                                    this.newData = rowData;
                                                    const { budgetElement, budgetElementFirstFree } = rowData
                                                    confirm({
                                                        title: <h3>{budgetElement}</h3>,
                                                        content:
                                                            <div>
                                                                <span>请输入预算费用:</span>
                                                                <InputNumber
                                                                    defaultValue={budgetElementFirstFree ? budgetElementFirstFree : 0}
                                                                    style={{ margin: '0px 10px', width: "200px" }}
                                                                    onBlur={(v) => { this.newData.budgetElementFirstFree = v.target.value }}
                                                                    placeholder="请输入..." />
                                                            </div>,
                                                        icon: false,
                                                        okText: "确认",
                                                        cancelText: "取消",
                                                        onOk: () => {
                                                            this.props.myFetch('updateZxBuBudgetDetails', this.newData).then(
                                                                ({ success, message, data }) => {
                                                                    if (success) {
                                                                        Msg.success(message);
                                                                        this.table.refresh();
                                                                    } else {
                                                                        Msg.error(message);
                                                                    }
                                                                });
                                                        }
                                                    })
                                                }}
                                                >编辑</a>
                                            }
                                        }
                                    }
                                },
                            ]}
                            actionBtns={[
                                {
                                    name: 'goback',
                                    type: 'dashed',
                                    label: '返回',
                                    isValidate: false,
                                    onClick: () => {
                                        const { mainModule } = this.props.myPublic.appInfo;
                                        this.props.dispatch(
                                            push(`${mainModule}AfterBudgeting`)
                                        )
                                    }
                                }
                            ]}
                        />
                    </Col>
                </Row>
            </div>
        );
    }
}

export default AfterBudgetingDetial;