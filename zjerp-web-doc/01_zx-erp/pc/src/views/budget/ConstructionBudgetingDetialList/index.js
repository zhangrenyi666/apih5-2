import React, { Component } from 'react'
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-form";
import { message as Msg, Modal,  Col, Row, } from 'antd';
import { push } from "react-router-redux";
const confirm = Modal.confirm;

const config = {
    antd: {
        rowKey: 'zxBuBudgetDetailsId',
        size: 'small',
    },
    drawerConfig: {
        width: '1200px'
    },
    // paginationConfig: {
    //     position: 'bottom'
    // },
    paginationConfig: false,
    firstRowIsSearch: false,
    isShowRowSelect: false,
    pageSize: 9999
}

// const layout = {
//     labelCol: { span: 8 },
//     wrapperCol: { span: 16 },
// };

class index extends Component {
    constructor(props) {
        super(props)
        this.state = {
            isModalVisible: false,
        }
        // this.props = this.props.props
        this.childrenData = {
            orgID: props.router.location.query.orgID,
            zxBuBudgetBookId: props.router.location.query.zxBuBudgetBookId,
            period: props.router.location.query.period,
            status: props.router.location.query.status,
        }
    }

    floatAccuracyFunc = (arg1, arg2) => {

        let m = 0
        let r1 = arg1.toString().split(".")[1] ? arg1.toString().split(".")[1].length : 0
        let r2 = arg1.toString().split(".")[1] ? arg1.toString().split(".")[1].length : 0
        m = Math.pow(10, Math.max(r1, r2))
        return (arg1 * m + arg2 * m) / m
    }

    modelContentType = ''
    modelTitle = ''
    checkedRowData = null
    showModeFunc = (rowData, haveRightList, index) => {
        return <a onClick={() => {
            this.modelTitle = rowData.budgetElement
            this.checkedRowData = rowData

            // 判断处最特殊的 17 
            if (index === 17) {
                this.modelContentType = '17'
            } else {
                // 如果不是 17 可以分为两类 (一列 或者 两列)
                if (haveRightList) {
                    // 两列
                    this.modelContentType = 'twoColumns'
                } else {
                    // 一列
                    this.modelContentType = 'oneColumn'
                }
            }
            if (this.childrenData.status === '1') {
                confirm({
                    title: '预算已确认,不能再执行编辑操作',
                    content: '',
                    icon: false,
                    okText: "确认",
                    onOk: () => {
                        return false
                    }
                })
            } else {
                this.setState({
                    isModalVisible: true
                })
            }
        }}>编制</a>
    }

    falseData = [
        {
            serialNumber: '一',
            budgetElement: '工程量清单费用/施工成本',
            budgetElementFirstFree: '41418468',
            needDeduct: '37718182',
            cz: '-3700286',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 1
        },
        {
            serialNumber: '(一)',
            budgetElement: '工程量清单内工程',
            budgetElementFirstFree: '41418468',
            needDeduct: '37718182',
            cz: '-3700286',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 2
        },
        {
            serialNumber: '(二)',
            budgetElement: '变更新增工程量',
            budgetElementFirstFree: '0',
            needDeduct: '0',
            cz: '0',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 3
        },
        {
            serialNumber: '二',
            budgetElement: '现场文明施工费',
            budgetElementFirstFree: '0',
            needDeduct: '0',
            cz: '0',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 4
        },
        {
            serialNumber: '三',
            budgetElement: '临时设施费',
            budgetElementFirstFree: '0',
            needDeduct: '10989',
            cz: '10989',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 5
        },
        {
            serialNumber: '四',
            budgetElement: '经理部管理费',
            budgetElementFirstFree: '0',
            needDeduct: '0',
            cz: '0',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 6
        },
        {
            serialNumber: '五',
            budgetElement: '安全生产费',
            budgetElementFirstFree: '0',
            needDeduct: '0',
            cz: '0',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 7
        },
        {
            serialNumber: '六',
            budgetElement: '税金(销项税金)',
            budgetElementFirstFree: '0',
            needDeduct: '0',
            cz: '0',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 8
        },
        {
            serialNumber: '(一)',
            budgetElement: '销项税金',
            budgetElementFirstFree: '0',
            needDeduct: '0',
            cz: '0',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 9
        },
        {
            serialNumber: '(二)',
            budgetElement: '进项税金',
            budgetElementFirstFree: '111201',
            needDeduct: '349',
            cz: '-110852',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 10
        },
        {
            serialNumber: '(三)',
            budgetElement: '实际税负',
            budgetElementFirstFree: '-0.02%',
            needDeduct: '-0%',
            cz: '0.02%',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 11
        },
        {
            serialNumber: '七',
            budgetElement: '财务费用',
            budgetElementFirstFree: '0',
            needDeduct: '0',
            cz: '0',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 12
        },
        {
            serialNumber: '八',
            budgetElement: '暂列金额',
            budgetElementFirstFree: '0',
            needDeduct: '0',
            cz: '0',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 13
        },
        {
            serialNumber: '九',
            budgetElement: '计日工',
            budgetElementFirstFree: '0',
            needDeduct: '0',
            cz: '0',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 14
        },
        {
            serialNumber: '十',
            budgetElement: '中国交建/公司管理费',
            budgetElementFirstFree: '0',
            needDeduct: '0',
            cz: '0',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 15
        },
        {
            serialNumber: '十一',
            budgetElement: '其他费用',
            budgetElementFirstFree: '0',
            needDeduct: '0',
            cz: '0',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 16
        },
        {
            serialNumber: '',
            budgetElement: '合计（元）',
            budgetElementFirstFree: '41418468',
            needDeduct: '37729171',
            cz: '-3689297',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 17
        },
        {
            serialNumber: '',
            budgetElement: '合同收入',
            budgetElementFirstFree: '0',
            needDeduct: '0',
            cz: '0',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 18
        },
        {
            serialNumber: '',
            budgetElement: '切块费用（元）',
            budgetElementFirstFree: '-41418468',
            needDeduct: '-37729171',
            cz: '',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 19
        },
        {
            serialNumber: '',
            budgetElement: '切块比例（%）',
            budgetElementFirstFree: '0',
            needDeduct: '0',
            cz: '',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 20
        },
        {
            serialNumber: '',
            budgetElement: '增收节支预算',
            budgetElementFirstFree: '0',
            needDeduct: '0',
            cz: '0',
            needDeduct1: 'e',
            zxBuBudgetDetailsId: 21
        },
    ]
    ModalRender = () => {
        const listOf17Left = [
            {
                type: 'number',
                label: '签约合同价',
                field: 'finConPrice',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, listOf17Left, 'fintotal')
                }
            },
            {
                type: 'number',
                label: '变更索赔金额',
                field: 'finChgAmount',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, listOf17Left, 'fintotal')
                }
            },
            {
                type: 'number',
                label: '奖（罚）金',
                field: 'finAwdPenalty',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, listOf17Left, 'fintotal')
                }
            },
            {
                type: 'number',
                label: '材料调差',
                field: 'finMtladjustment',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, listOf17Left, 'fintotal')
                }
            },
            {
                type: 'number',
                label: '其他',
                field: 'finOther',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, listOf17Left, 'fintotal')
                }
            },
            {
                type: 'number',
                label: '合计',
                field: 'fintotal',
                disabled: true,
                placeholder: '请输入',
                required: false,
            },
        ]
        const listOf17Right = [
            {
                type: 'number',
                label: '签约合同价',
                field: 'remConPrice',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, listOf17Right, 'remtotal')
                }
            },
            {
                type: 'number',
                label: '变更索赔金额',
                field: 'remChgAmount',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, listOf17Right, 'remtotal')
                }
            },
            {
                type: 'number',
                label: '奖（罚）金',
                field: 'remAwdPenalty',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, listOf17Right, 'remtotal')
                }
            },
            {
                type: 'number',
                label: '材料调差',
                field: 'remMtladjustment',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, listOf17Right, 'remtotal')
                }
            },
            {
                type: 'number',
                label: '其他',
                field: 'remOther',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, listOf17Right, 'remtotal')
                }
            },
            {
                type: 'number',
                label: '合计',
                field: 'remtotal',
                disabled: true,
                placeholder: '请输入',
                required: false,
            },
        ]
        const oneColumnList = [
            {
                type: 'number',
                label: '已完工程',
                field: 'finCurr',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, oneColumnList, 'budgetElementCurrFree')
                }
            },
            {
                type: 'number',
                label: '剩余工程',
                field: 'remCurr',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, oneColumnList, 'budgetElementCurrFree')
                }
            },
            {
                type: 'number',
                label: '合计',
                field: 'budgetElementCurrFree',
                disabled: true,
                placeholder: '请输入',
                required: false,
            },
        ]

        const twoColumnList = [
            {
                type: 'number',
                label: '已完进项税金',
                field: 'finTaxPrice',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, twoColumnList, 'currJtFee')
                }
            },
            {
                type: 'number',
                label: '剩余进项税金',
                field: 'remTaxPrice',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, twoColumnList, 'currJtFee')
                }
            },
            {
                type: 'number',
                label: '合计',
                field: 'currJtFee',
                disabled: true,
                placeholder: '请输入',
                required: false,
            },
        ]

        const calculateTotal = async (obj, list, total) => {
            // 入参 list : 计算对应的 columnsList,  total : 需要合计的字段名称
            let newFormData = {}
            let totalValue = 0

            const formData = await obj.fns.getValues()

            list.map(item => {
                if (item.field !== total) {
                    totalValue = formData[item.field] ? this.floatAccuracyFunc(totalValue, +formData[item.field]) : totalValue
                }
                return true
            })

            newFormData = { ...formData }
            newFormData[total] = totalValue

            await obj.fns.setValues(newFormData)
        }

        switch (this.modelContentType) {
            case '17':
                return <div>
                    <Row>
                        <Col span={12}>
                            <div>已完工程</div>
                            <QnnForm
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => { this.form17Left = me }}
                                method={{}}
                                data={this.checkedRowData}
                                formItemLayout={{
                                    labelCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    }
                                }}
                                formConfig={listOf17Left}
                            />
                        </Col>
                        <Col span={12}>
                            <div>剩余工程</div>
                            <QnnForm
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => { this.form17Right = me }}
                                method={{}}
                                data={this.checkedRowData}
                                formItemLayout={{
                                    labelCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    }
                                }}
                                formConfig={listOf17Right}
                            />
                        </Col>
                    </Row>
                </div>
            case 'oneColumn':
                return <div>
                    <QnnForm
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => { this.formOne = me }}
                        method={{}}
                        data={this.checkedRowData}
                        formItemLayout={{
                            labelCol: {
                                xs: { span: 12 },
                                sm: { span: 12 }
                            },
                            wrapperCol: {
                                xs: { span: 12 },
                                sm: { span: 12 }
                            }
                        }}
                        formConfig={oneColumnList}
                    />
                </div>
            case 'twoColumns':
                return <div>
                    <Row>
                        <Col span={12}>
                            <QnnForm
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => { this.formTwoLeft = me }}
                                method={{}}
                                data={this.checkedRowData}
                                formItemLayout={{
                                    labelCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    }
                                }}
                                formConfig={oneColumnList}
                            />
                        </Col>
                        <Col span={12}>
                            <QnnForm
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => { this.formTwoRight = me }}
                                method={{}}
                                data={this.checkedRowData}
                                formItemLayout={{
                                    labelCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    }
                                }}
                                formConfig={twoColumnList}
                            />
                        </Col>
                    </Row>
                </div>
            default:
                break
        }
    }

    handleOk = async () => {
        let params = null
        // this.checkedRowData 这个是当前点击 行 的数据
        switch (this.modelContentType) {
            case '17':
                params = {
                    ...await this.form17Left.getValues(),
                    ...await this.form17Right.getValues(),
                    budgetElementCurrFree: this.floatAccuracyFunc((await this.form17Left.getValues()).fintotal, (await this.form17Right.getValues()).remtotal)
                }
                break
            case 'oneColumn':
                params = {
                    ...await this.formOne.getValues(),
                }
                break
            case 'twoColumns':
                params = {
                    ...await this.formTwoLeft.getValues(),
                    ...await this.formTwoRight.getValues(),
                }
                break
            default:
                break
        }

        const { success, message } = await this.props.myFetch('updateZxBuBudgetDetails', { ...this.checkedRowData, ...params, serialNumber: +this.checkedRowData.serialNumber })
        if (success) {
            Msg.success('保存成功!')
            this.table.refresh()
        } else {
            Msg.error(message)
        }
        // params 获取 当前modelFrom的数据
        this.setState({
            isModalVisible: false
        })
    }
    handleCancel = () => {
        this.setState({
            isModalVisible: false
        })
    }

    render() {
        const { orgID, zxBuBudgetBookId, period, status } = this.childrenData
        return (
            <div>
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
                            budgetBookID: zxBuBudgetBookId,
                            period,
                            orgID
                        }
                    }}

                    // data={this.falseData}
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
                                    if (index < 16) {
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
                                title: '费用项目',
                                dataIndex: 'budgetElement',
                                key: 'budgetElement',
                                align: 'center',
                                render: (text, row, index) => {
                                    if (index < 16) {
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
                                title: '标后预算费用(元)',
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
                                title: '施工预算费用(元)',
                                width: 200,
                                dataIndex: 'budgetElementCurrFree',
                                key: 'budgetElementCurrFree',
                                align: 'center'
                            }
                        },
                        {
                            table: {
                                title: '差值',
                                width: 200,
                                dataIndex: 'difference',
                                key: 'difference',
                                align: 'center'
                            }
                        },
                        {
                            table: {
                                title: '编制',
                                dataIndex: 'needDeduct1',
                                key: 'needDeduct1',
                                align: 'center',
                                render: (val, rowData, index) => {
                                    const { mainModule } = this.props.myPublic.appInfo;
                                    switch (index) {
                                        case 1:
                                            // 工程量清单内工程
                                            return <a onClick={() => {
                                                this.props.dispatch(
                                                    push(`${mainModule}ConstructionBudgetingProjectCost?orgID=${orgID}&zxBuBudgetBookId=${zxBuBudgetBookId}&status=${status}`)
                                                )
                                            }}>编辑</a>
                                        case 3:
                                            // 现场文明施工费
                                            return <a onClick={() => {
                                                this.props.dispatch(
                                                    push(`${mainModule}ConstructionBudgetingSiteCost?orgID=${orgID}&zxBuBudgetBookId=${zxBuBudgetBookId}&status=${status}`)
                                                )
                                            }}>编辑</a>
                                        case 4:
                                            // 临时设施费
                                            return <a onClick={() => {
                                                this.props.dispatch(
                                                    push(`${mainModule}ConstructionBudgetingFacilityCost?orgID=${orgID}&zxBuBudgetBookId=${zxBuBudgetBookId}&status=${status}`)
                                                )
                                            }}>编辑</a>
                                        case 5:
                                            // 经理部管理费
                                            return <a onClick={() => {
                                                this.props.dispatch(
                                                    push(`${mainModule}ConstructionBudgetingIntendanceCost?orgID=${orgID}&zxBuBudgetBookId=${zxBuBudgetBookId}&status=${status}`)
                                                )
                                            }}>编辑</a>
                                        case 6:
                                            return this.showModeFunc(rowData, true)
                                        case 7:
                                            return this.showModeFunc(rowData, false)
                                        case 11:
                                            return this.showModeFunc(rowData, false)
                                        case 12:
                                            return this.showModeFunc(rowData, false)
                                        case 13:
                                            return this.showModeFunc(rowData, false)
                                        case 14:
                                            return this.showModeFunc(rowData, true)
                                        case 15:
                                            return this.showModeFunc(rowData, true)
                                        case 17:
                                            return this.showModeFunc(rowData, true, 17)
                                        case 20:
                                            return this.showModeFunc(rowData, true)
                                        default:
                                            return ''
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
                            onClick: (obj) => {
                                const { mainModule } = this.props.myPublic.appInfo;
                                this.props.dispatch(
                                    push(`${mainModule}ConstructionBudgetingDetial?orgID=${orgID}&zxBuBudgetBookId=${zxBuBudgetBookId}`)
                                )
                            }
                        }
                    ]}
                />
                {/* 点击编制弹窗 */}
                <Modal title={this.modelTitle} visible={this.state.isModalVisible} onOk={this.handleOk} onCancel={this.handleCancel} destroyOnClose={true}>
                    {this.ModalRender()}
                </Modal>
            </div >
        )
    }
}
export default index
