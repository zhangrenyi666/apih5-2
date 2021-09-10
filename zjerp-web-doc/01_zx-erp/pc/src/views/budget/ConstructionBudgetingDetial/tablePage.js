import React, { Component } from 'react'
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-form";
import { message as Msg, Modal, Button, InputNumber, Col, Row, Form, Input, } from 'antd';
import { push } from "react-router-redux";
import s from "./style.less";
const confirm = Modal.confirm;

const config = {
    antd: {
        rowKey: 'zxBuBudgetDetailsId',
        size: 'small',
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    paginationConfig: false,
    firstRowIsSearch: false,
    isShowRowSelect: false,
    pageSize: 9999
}

const layout = {
    labelCol: { span: 8 },
    wrapperCol: { span: 16 },
};

class TablePage extends Component {
    constructor(props) {
        super(props)
        this.state = {
            isModalVisible: false,
        }
        this.propsData = this.props.propsData
        this.childrenData = this.props.childrenData
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

            this.setState({
                isModalVisible: true
            })
        }}>编制</a>
    }


    ModalRender = () => {
        const listOf17 = [
            {
                type: 'number',
                label: '签约合同价',
                field: 'qyhtj',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, listOf17, 'hj')
                }
            },
            {
                type: 'number',
                label: '变更索赔金额',
                field: 'bgspje',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, listOf17, 'hj')
                }
            },
            {
                type: 'number',
                label: '奖（罚）金',
                field: 'jfj',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, listOf17, 'hj')
                }
            },
            {
                type: 'number',
                label: '材料调差',
                field: 'cldc',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, listOf17, 'hj')
                }
            },
            {
                type: 'number',
                label: '其他',
                field: 'qt',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, listOf17, 'hj')
                }
            },
            {
                type: 'number',
                label: '合计',
                field: 'hj',
                disabled: true,
                placeholder: '请输入',
                required: false,
                disabled: true
            },
        ]

        const oneColumnList = [
            {
                type: 'number',
                label: '已完工程',
                field: 'ywgc',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, oneColumnList, 'hj')
                }
            },
            {
                type: 'number',
                label: '剩余工程',
                field: 'sygc',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, oneColumnList, 'hj')
                }
            },
            {
                type: 'number',
                label: '合计',
                field: 'hj',
                disabled: true,
                placeholder: '请输入',
                required: false,
            },
        ]

        const twoColumnList = [
            {
                type: 'number',
                label: '已完进项税金',
                field: 'ywjx',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, twoColumnList, 'hj')
                }
            },
            {
                type: 'number',
                label: '剩余进项税金',
                field: 'syjx',
                placeholder: '请输入',
                required: false,
                onChange: async (_, obj) => {
                    calculateTotal(obj, twoColumnList, 'hj')
                }
            },
            {
                type: 'number',
                label: '合计',
                field: 'hj',
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
                                fetch={this.propsData.myFetch}
                                upload={this.propsData.myUpload}
                                headers={{ token: this.propsData.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => { this.form17Left = me }}
                                method={{}}
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
                                formConfig={listOf17}
                            />
                        </Col>
                        <Col span={12}>
                            <div>剩余工程</div>
                            <QnnForm
                                fetch={this.propsData.myFetch}
                                upload={this.propsData.myUpload}
                                headers={{ token: this.propsData.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => { this.form17Right = me }}
                                method={{}}
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
                                formConfig={listOf17}
                            />
                        </Col>
                    </Row>
                </div>
            case 'oneColumn':
                return <div>
                    <QnnForm
                        fetch={this.propsData.myFetch}
                        upload={this.propsData.myUpload}
                        headers={{ token: this.propsData.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => { this.formOne = me }}
                        method={{}}
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
                                fetch={this.propsData.myFetch}
                                upload={this.propsData.myUpload}
                                headers={{ token: this.propsData.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => { this.formTwoLeft = me }}
                                method={{}}
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
                                fetch={this.propsData.myFetch}
                                upload={this.propsData.myUpload}
                                headers={{ token: this.propsData.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => { this.formTwoRight = me }}
                                method={{}}
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
        const { orgID, zxBuBudgetBookId } = this.childrenData
        return (
            <div>
                <QnnTable
                    {...this.propsData}
                    fetch={this.propsData.myFetch}
                    upload={this.propsData.myUpload}
                    headers={{ token: this.propsData.loginAndLogoutInfo.loginInfo.token }}
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
                            orgID:orgID
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
                                dataIndex: 'needDeduct',
                                key: 'needDeduct',
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
                                    const { mainModule } = this.propsData.myPublic.appInfo;
                                    switch (index) {
                                        case 1:
                                            // 工程量清单内工程
                                            return <a onClick={() => {
                                                this.propsData.dispatch(
                                                    push(`${mainModule}ConstructionBudgetingProjectCost?orgID=${orgID}&zxBuBudgetBookId=${zxBuBudgetBookId}`)
                                                )
                                            }}>编辑</a>
                                        case 3:
                                            // 现场文明施工费
                                            return <a onClick={() => {
                                                this.propsData.dispatch(
                                                    push(`${mainModule}ConstructionBudgetingSiteCost?orgID=${orgID}&zxBuBudgetBookId=${zxBuBudgetBookId}`)
                                                )
                                            }}>编辑</a>
                                        case 4:
                                            // 临时设施费
                                            return <a onClick={() => {
                                                this.propsData.dispatch(
                                                    push(`${mainModule}ConstructionBudgetingFacilityCost?orgID=${orgID}&zxBuBudgetBookId=${zxBuBudgetBookId}`)
                                                )
                                            }}>编辑</a>
                                        case 5:
                                            // 经理部管理费
                                            return <a onClick={() => {
                                                this.propsData.dispatch(
                                                    push(`${mainModule}ConstructionBudgetingIntendanceCost?orgID=${orgID}&zxBuBudgetBookId=${zxBuBudgetBookId}`)
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
                                this.props.childGoBack()
                            }
                        }
                    ]}
                />
                {/* 点击编制弹窗 */}
                <Modal title={this.modelTitle} visible={this.state.isModalVisible} onOk={this.handleOk} onCancel={this.handleCancel}>
                    {this.ModalRender()}
                </Modal>
            </div >
        )
    }
}
export default TablePage
