import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-form";
import { message as Msg, Spin, Row, Col } from 'antd';
import s from "./style.less";
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            loading: false,
            queryProjectID: '',
            queryPeriodDate: new Date().getTime(),
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            status: '0'
        }
    }

    leftTableChangeVal = ''
    rightTableChangeVal = ''
    detailListID = ''

    resGjLossCoefficientJoin1 = {}
    resGjConCoefficientJoin1 = {}
    resGjLossCoefficientJoin2 = {}
    resGjConCoefficientJoin2 = {}

    FloatMulTwo(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }

    render() {
        const { departmentId } = this.state
        const { queryProjectID, queryPeriodDate, loading } = this.state
        return (
            <div>
                {/* 检索部分 */}
                <div>

                    <QnnForm
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => { this.form = me }}
                        method={{}}
                        componentsKey={{}}
                        formConfig={[
                            {
                                type: 'select',
                                label: '项目名称:',
                                field: 'projectName',
                                placeholder: '请选择',
                                required: false,
                                span: 6,
                                fetchConfig: {
                                    apiName: 'getZxBuProjectTypeCheckOver',
                                    otherParams: {
                                        orgID: departmentId,
                                    }
                                },
                                optionConfig: {
                                    label: 'orgName',
                                    value: 'orgID',
                                },
                                formItemLayoutForm: {
                                    labelCol: {
                                        sm: { span: 16 }
                                    },
                                    wrapperCol: {
                                        sm: { span: 16 }
                                    }
                                },
                                allowClear: false,
                                onChange: (val) => {
                                    this.setState({
                                        queryProjectID: val
                                    })
                                    this.tableOne.clearSelectedRows()
                                    this.tableQD.clearSelectedRows()
                                    this.leftTableChangeVal = ''
                                }
                            },
                            {
                                type: 'month',
                                label: '期次',
                                field: 'periodDate',
                                placeholder: '请选择',
                                allowClear: false,
                                required: false,
                                span: 6,
                                formItemLayoutForm: {
                                    labelCol: {
                                        sm: { span: 16 }
                                    },
                                    wrapperCol: {
                                        sm: { span: 16 }
                                    }
                                },
                                initialValue: new Date().getTime(),
                                onChange: (val) => {
                                    this.setState({
                                        queryPeriodDate: val
                                    })
                                    this.tableOne.clearSelectedRows()
                                    this.tableQD.clearSelectedRows()
                                    this.leftTableChangeVal = ''
                                }
                            },
                        ]}
                    />

                </div>
                {/* 表格部分 */}
                <Row>
                    <Col span={11}>
                        <Spin spinning={loading}>
                            <QnnTable
                                fetch={this.props.myFetch}
                                myFetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => { this.tableOne = me }}
                                method={{}}
                                paginationConfig={false}
                                pageSize={9999}
                                componentsKey={{}}
                                fetchConfig={{
                                    apiName: "getZxBuWorksResNoName",
                                    otherParams: {
                                        orgID: this.state.queryProjectID,
                                        parentID: '-1',
                                        limit: 9999,
                                        periodDate: this.state.queryPeriodDate
                                    },
                                    success: async ({ data, success, message }) => {
                                        if (success && this.state.queryProjectID) {
                                            this.props.myFetch('checkZxBuBudgetBook', {
                                                orgID: this.state.queryProjectID,
                                                periodDate: this.state.queryPeriodDate
                                            }).then(res => {
                                                if (res.success && !(res.data)) {
                                                    this.setState({
                                                        status: '0'
                                                    })
                                                } else {
                                                    this.setState({
                                                        status: '1'
                                                    })
                                                }
                                            })
                                        }
                                    }
                                }}
                                antd={{
                                    rowKey: 'id'
                                }}
                                rowSelection={{
                                    type: 'radio',
                                    onChange: async (selectedRowKey, selectedData, delKey) => {

                                        this.leftTableChangeVal = selectedRowKey.length ? selectedRowKey[0] : ''
                                        this.detailListID = selectedRowKey.length ? selectedData[0].id : ''

                                        this.tableQD.clearSelectedRows()

                                        this.tableQD.getTableData().map(item => {
                                            this.tableQD.setRowDataById(item.stockVOID, {
                                                ...item,
                                                gjLossCoefficient1:null,
                                                gjConCoefficient1:null,
                                                scenePrice1:null,
                                                gjLossCoefficient2:null,
                                                gjConCoefficient2:null,
                                                scenePrice3:null,
                                            })
                                            return true
                                            // rightTableDataList[item.stockVOID] = {
                                            //     gjLossCoefficient1: this.tableQD.getTdRef({
                                            //         field: 'gjLossCoefficient1',
                                            //         rowId: item.stockVOID
                                            //     }),
                                            //     gjConCoefficient1: this.tableQD.getTdRef({
                                            //         field: 'gjConCoefficient1',
                                            //         rowId: item.stockVOID
                                            //     }),
                                            //     scenePrice1: this.tableQD.getTdRef({
                                            //         field: 'scenePrice1',
                                            //         rowId: item.stockVOID
                                            //     }),
                                            //     gjLossCoefficient2: this.tableQD.getTdRef({
                                            //         field: 'gjLossCoefficient2',
                                            //         rowId: item.stockVOID
                                            //     }),
                                            //     gjConCoefficient2: this.tableQD.getTdRef({
                                            //         field: 'gjConCoefficient2',
                                            //         rowId: item.stockVOID
                                            //     }),
                                            //     // scenePrice2: this.tableQD.getTdRef({
                                            //     //     field: 'scenePrice2',
                                            //     //     rowId: item.stockVOID
                                            //     // }),
                                            //     scenePrice3: this.tableQD.getTdRef({
                                            //         field: 'scenePrice3',
                                            //         rowId: item.stockVOID
                                            //     }),
                                            //     // scenePrice: this.tableQD.getTdRef({
                                            //     //     field: 'scenePrice',
                                            //     //     rowId: item.stockVOID
                                            //     // }),
                                            // }
                                        })

                                        // Object.keys(rightTableDataList).map(item => {
                                        //     Object.keys(rightTableDataList[item]).map(itemm => {
                                        //         rightTableDataList[item][itemm] && rightTableDataList[item][itemm].setTdData(null)
                                        //     })
                                        // })

                                        // 设置 右侧表格选中
                                        if (selectedData.length && selectedData[0].voidJoin) {
                                            let list = []
                                            selectedData[0].voidJoin.split(',').map(async (item, index) => {
                                                list.push(...this.tableQD.getTableData().filter(ele => ele.stockVOID === item))
                                            })

                                            list.map((item, index) => {
                                                let rowData = this.tableQD.getRowDataById(item.stockVOID)
                                                if (selectedData[0].resGjLossCoefficientJoin && selectedData[0].resGjConCoefficientJoin) {
                                                    if (selectedData[0].resGjLossCoefficientJoin.split(',')[index] && selectedData[0].resGjConCoefficientJoin.split(',')[index]) {
                                                        rowData = {
                                                            ...rowData,
                                                            gjConCoefficient1: selectedData[0].resGjConCoefficientJoin.split(',')[index],
                                                            gjLossCoefficient1: selectedData[0].resGjLossCoefficientJoin.split(',')[index],
                                                            scenePrice1: (+selectedData[0].resGjLossCoefficientJoin.split(',')[index] * +selectedData[0].resGjConCoefficientJoin.split(',')[index] * +rowData['scenePrice']).toFixed(2),
                                                        }
                                                        if (selectedData[0].resGjLossCoefficientJoin2.split(',')[index] && selectedData[0].resGjConCoefficientJoin2.split(',')[index]) {
                                                            rowData = {
                                                                ...rowData,
                                                                gjConCoefficient2: selectedData[0].resGjConCoefficientJoin2.split(',')[index],

                                                                gjLossCoefficient2: selectedData[0].resGjLossCoefficientJoin2.split(',')[index],
                                                                scenePrice3: (+selectedData[0].resGjLossCoefficientJoin2.split(',')[index] * +selectedData[0].resGjConCoefficientJoin2.split(',')[index] * +rowData['scenePrice2']).toFixed(2),
                                                            }
                                                        }
                                                    } else {
                                                        if (this.resGjLossCoefficientJoin1[this.tableOne.getSelectedRowsKey()] && this.resGjConCoefficientJoin1[this.tableOne.getSelectedRowsKey()]) {
                                                            rowData = {
                                                                ...rowData,
                                                                gjLossCoefficient1: this.resGjLossCoefficientJoin1[this.tableOne.getSelectedRowsKey()][index],
                                                                gjConCoefficient1: this.resGjConCoefficientJoin1[this.tableOne.getSelectedRowsKey()][index],
                                                                scenePrice1: (+this.resGjLossCoefficientJoin1[this.tableOne.getSelectedRowsKey()][index] * +this.resGjConCoefficientJoin1[this.tableOne.getSelectedRowsKey()][index] * +rowData['scenePrice']).toFixed(2)
                                                            }
                                                        }
                                                        if (this.resGjLossCoefficientJoin2[this.tableOne.getSelectedRowsKey()] && this.resGjConCoefficientJoin2[this.tableOne.getSelectedRowsKey()]) {
                                                            rowData = {
                                                                ...rowData,
                                                                gjLossCoefficient2: this.resGjLossCoefficientJoin2[this.tableOne.getSelectedRowsKey()][index],
                                                                gjConCoefficient2: this.resGjConCoefficientJoin2[this.tableOne.getSelectedRowsKey()][index],
                                                                scenePrice3: (+this.resGjLossCoefficientJoin2[this.tableOne.getSelectedRowsKey()][index] * +this.resGjConCoefficientJoin2[this.tableOne.getSelectedRowsKey()][index] * +rowData['scenePrice2']).toFixed(2)
                                                            }
                                                        }
                                                    }
                                                } else {

                                                    if (this.resGjLossCoefficientJoin1[this.tableOne.getSelectedRowsKey()] && this.resGjConCoefficientJoin1[this.tableOne.getSelectedRowsKey()]) {
                                                        rowData = {
                                                            ...rowData,
                                                            gjLossCoefficient1: this.resGjLossCoefficientJoin1[this.tableOne.getSelectedRowsKey()][index],
                                                            gjConCoefficient1: this.resGjConCoefficientJoin1[this.tableOne.getSelectedRowsKey()][index],
                                                            scenePrice1: (+this.resGjLossCoefficientJoin1[this.tableOne.getSelectedRowsKey()][index] * +this.resGjConCoefficientJoin1[this.tableOne.getSelectedRowsKey()][index] * +rowData['scenePrice']).toFixed(2)
                                                        }
                                                    }

                                                    if (this.resGjLossCoefficientJoin2[this.tableOne.getSelectedRowsKey()] && this.resGjConCoefficientJoin2[this.tableOne.getSelectedRowsKey()]) {
                                                        rowData = {
                                                            ...rowData,
                                                            gjLossCoefficient2: this.resGjLossCoefficientJoin2[this.tableOne.getSelectedRowsKey()][index],
                                                            gjConCoefficient2: this.resGjConCoefficientJoin2[this.tableOne.getSelectedRowsKey()][index],
                                                            scenePrice3: (+this.resGjLossCoefficientJoin2[this.tableOne.getSelectedRowsKey()][index] * +this.resGjConCoefficientJoin2[this.tableOne.getSelectedRowsKey()][index] * +rowData['scenePrice2']).toFixed(2)
                                                        }
                                                    }

                                                }
                                                this.tableQD.setRowDataById(item.stockVOID, rowData)
                                                return true
                                            })

                                            this.tableQD.setSelectedRows(list)
                                        } else {
                                            this.tableQD.refresh()
                                        }
                                    },
                                    getCheckboxProps: record => ({
                                        // name:record.name,
                                        disabled: record.isLeaf === 0,
                                    }),
                                }}
                                rowClassName={(record, index) => {
                                    return record.isLeaf === 0 ? s.backgrounde6 : ''
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
                                            title: '清单编号',
                                            dataIndex: 'workNo',
                                            key: 'workNo',
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '清单名称',
                                            dataIndex: 'workName',
                                            key: 'workName',
                                            align: 'center'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '数量',
                                            dataIndex: 'contractQty',
                                            key: 'contractQty',
                                            align: 'center',
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '单位',
                                            dataIndex: 'unit',
                                            key: 'unit',
                                            align: 'center',
                                            tooltip: 23,
                                            width: 50,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '材料编号',
                                            dataIndex: 'resCodeJoin',
                                            key: 'resCodeJoin',
                                            align: 'center',
                                            // textOverflow: "lineFeed",
                                            // render: (val, obj, rowData) => {
                                            //     return val && (val.split(',').length && val.split(',').sort().join(','))
                                            // }

                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '材料名称',
                                            dataIndex: 'resNameJoin',
                                            key: 'resNameJoin',
                                            align: 'center',
                                            // textOverflow: "lineFeed",
                                        },
                                        isInForm: false
                                    }
                                ]}
                            />
                        </Spin>
                    </Col>
                    <Col span={1}></Col>
                    <Col span={12}>
                        <Spin spinning={loading}>
                            <QnnTable
                                {...this.props}
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => {
                                    this.tableQD = me;
                                }}
                                paginationConfig={false}
                                pageSize={9999}
                                fetchConfig={{
                                    apiName: "getZxBuStockPriceItemResAll",
                                    otherParams: {
                                        orgID: queryProjectID,
                                        periodDate: queryPeriodDate
                                    }
                                }}
                                antd={{
                                    rowKey: 'stockVOID'
                                }}
                                rowSelection={{
                                    type: 'checkbox',
                                    hideSelectAll: true,
                                    onSelect: (record, selected, selectedRowKeys, selectedRow) => {
                                        if (!this.leftTableChangeVal) {
                                            Msg.error("没有选择项目叶子清单!")
                                            this.tableQD.clearSelectedRows()
                                        } else {
                                            const { queryProjectID } = this.state
                                            // const gjLossCoefficient1 = !selectedRow.gjLossCoefficient1 ? 1 : selectedRow.gjLossCoefficient1
                                            // //折算系数
                                            // const gjConCoefficient1 = !selectedRow.gjConCoefficient1 ? 1 : selectedRow.gjConCoefficient1
                                            // const price = this.FloatMulTwo(!selectedRow.scenePrice ? 0 : selectedRow.scenePrice, this.FloatMulTwo(gjLossCoefficient1, gjConCoefficient1))

                                            const params = {
                                                //项目id
                                                orgID: queryProjectID,
                                                //项目清单id
                                                projWorkID: this.detailListID,
                                                //材料所在主表id
                                                stockPriceID: record.stockPriceID,
                                                //材料名称
                                                resName: record.resName,
                                                //材料编号
                                                resCode: record.resCode,
                                                //材料单位
                                                spec: record.spec,
                                                //材料规格型号
                                                unit: record.unit,
                                                //分类
                                                businessType: record.businessType,
                                                //损耗系数
                                                gjLossCoefficient1: record.gjLossCoefficient1 == null ? 1 : record.gjLossCoefficient1,
                                                //耗损系数默认1
                                                gjLossCoefficient2: 1,
                                                //折算系数
                                                gjConCoefficient1: record.gjConCoefficient1 == null ? 1 : record.gjConCoefficient1,
                                                //折算系数默认1
                                                gjConCoefficient2: 1,
                                                scenePrice1: record.scenePrice.toFixed(2),
                                                scenePrice2: record.scenePrice2,
                                                //单价 
                                                // scenePrice1: record.scenePrice1
                                                periodDate: new Date(this.state.queryPeriodDate).getTime()
                                            }
                                            if (selected) {
                                                this.setState({
                                                    loading: true
                                                }, () => {
                                                    this.props.myFetch('relevanceZxBuWorksToStockPrice', params).then(res => {
                                                        let selectList = {
                                                            resCode: [],
                                                            resName: [],
                                                            voidJoin: []
                                                        }
                                                        selectedRow.map((item, index) => {
                                                            selectList['resCode'].push(item.resCode)
                                                            selectList['resName'].push(item.resName)
                                                            selectList['voidJoin'].push(item.stockVOID)
                                                            return true
                                                        })
                                                        const listData = this.tableOne.getTableData().map(item => {
                                                            if (this.tableOne.getSelectedRowsKey()[0] === item.id) {
                                                                return {
                                                                    ...item,
                                                                    resCodeJoin: selectList['resCode'].join(','),
                                                                    resNameJoin: selectList['resName'].join(','),
                                                                    voidJoin: selectList['voidJoin'].join(','),
                                                                }
                                                            } else {
                                                                return item
                                                            }
                                                        })

                                                        this.tableOne.setTableData(listData)
                                                        this.setState({
                                                            loading: false
                                                        })
                                                    })
                                                })
                                            } else {
                                                this.setState({
                                                    loading: true
                                                }, () => {
                                                    this.props.myFetch('removeRelevanceZxBuWorksToStockPrice', params).then(res => {
                                                        let selectList = {
                                                            resCode: [],
                                                            resName: [],
                                                            voidJoin: []
                                                        }
                                                        selectedRow.map((item, index) => {
                                                            selectList['resCode'].push(item.resCode)
                                                            selectList['resName'].push(item.resName)
                                                            selectList['voidJoin'].push(item.stockVOID)
                                                            return true
                                                        })
                                                        const listData = this.tableOne.getTableData().map(item => {
                                                            if (this.tableOne.getSelectedRowsKey()[0] === item.id) {
                                                                return {
                                                                    ...item,
                                                                    resCodeJoin: selectList['resCode'].join(','),
                                                                    resNameJoin: selectList['resName'].join(','),
                                                                    voidJoin: selectList['voidJoin'].join(','),
                                                                }
                                                            } else {
                                                                return item
                                                            }
                                                        })

                                                        this.tableOne.setTableData(listData)
                                                        this.setState({
                                                            loading: false
                                                        })
                                                    })
                                                })
                                            }
                                        }
                                    },
                                    getCheckboxProps: ({ disabled: this.state.status === '1' })
                                }}
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
                                        isInTable: false,
                                        form: {
                                            field: 'stockVOID',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'zxBuStockPriceItemId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '材料编号',
                                            dataIndex: 'resCode',
                                            key: 'resCode',

                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '材料名称',
                                            dataIndex: 'resName',
                                            key: 'resName',
                                            align: 'center'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '数量',
                                            dataIndex: 'qty',
                                            key: 'qty',
                                            align: 'center',
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '单位',
                                            dataIndex: 'unit',
                                            key: 'unit',
                                            align: 'center',
                                            tooltip: 23,
                                            width: 50,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '规格型号',
                                            dataIndex: 'spec',
                                            key: 'spec',
                                            align: 'center',
                                            width: 100,
                                            tooltip: 23
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '已完工程',
                                            children: [
                                                {
                                                    title: '损耗系数',
                                                    dataIndex: 'gjLossCoefficient1',
                                                    key: 'gjLossCoefficient1',
                                                    width: 150,
                                                    align: "center",
                                                    tdEdit: () => {
                                                        return this.state.status !== '1'
                                                    },
                                                    fieldConfig: {
                                                        type: "number",
                                                        field: 'gjLossCoefficient1',
                                                        onChange: async (value, obj) => {
                                                            const rowData = await obj.qnnTableInstance.getEditedRowData()
                                                            if (rowData.gjLossCoefficient1 && rowData.gjConCoefficient1 && rowData.scenePrice) {
                                                                rowData.scenePrice1 = (+rowData.gjLossCoefficient1 * +rowData.gjConCoefficient1 * +rowData.scenePrice).toFixed(2)
                                                            } else {
                                                                rowData.scenePrice1 = (+rowData.scenePrice).toFixed(2)
                                                            }
                                                            await obj.qnnTableInstance.setEditedRowData({
                                                                ...rowData
                                                            })

                                                            let isCheck = false
                                                            const selectData = await obj.qnnTableInstance.getSelectedRows()
                                                            selectData.map(item => {
                                                                if (item.stockVOID === obj.rowData.stockVOID) {
                                                                    isCheck = true
                                                                }
                                                                return true
                                                            })
                                                            console.log(this.leftTableChangeVal, isCheck)
                                                            // 判断左右表格 是否选中
                                                            if (this.leftTableChangeVal && isCheck) {
                                                                this.resGjLossCoefficientJoin1[this.tableOne.getSelectedRowsKey()] || (this.resGjLossCoefficientJoin1[this.tableOne.getSelectedRowsKey()] = [])

                                                                this.tableOne.getSelectedRows()[0].voidJoin.split(',').map((item, index) => {
                                                                    if (item === rowData.stockVOID) {
                                                                        this.resGjLossCoefficientJoin1[this.tableOne.getSelectedRowsKey()][index] = value
                                                                    }
                                                                    return true
                                                                })

                                                                if (rowData.gjLossCoefficient1 && rowData.gjConCoefficient1) {
                                                                    const params = {
                                                                        projWorkID: this.leftTableChangeVal,
                                                                        stockVOID: rowData.stockVOID,
                                                                        gjLossCoefficient1: +rowData.gjLossCoefficient1,
                                                                        gjConCoefficient1: +rowData.gjConCoefficient1,
                                                                        gjLossCoefficient2: +rowData.gjLossCoefficient2,
                                                                        gjConCoefficient2: +rowData.gjConCoefficient2
                                                                    }
                                                                     await this.props.myFetch('updateZxBuWorksToStockPriceCoe', params)

                                                                }
                                                            } else {
                                                                Msg.warning('请选择叶子节点')
                                                            }
                                                        }
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    title: '折算系数',
                                                    dataIndex: 'gjConCoefficient1',
                                                    key: 'gjConCoefficient1',
                                                    width: 150,
                                                    align: "center",
                                                    tdEdit: () => {
                                                        return this.state.status !== '1'
                                                    },
                                                    fieldConfig: {
                                                        type: "number",
                                                        field: 'gjConCoefficient1',
                                                        onChange: async (value, obj) => {
                                                            const rowData = await obj.qnnTableInstance.getEditedRowData()
                                                            if (rowData.gjLossCoefficient1 && rowData.gjConCoefficient1 && rowData.scenePrice) {
                                                                rowData.scenePrice1 = (+rowData.gjLossCoefficient1 * +rowData.gjConCoefficient1 * +rowData.scenePrice).toFixed(2)
                                                            } else {

                                                                rowData.scenePrice1 = (+rowData.scenePrice).toFixed(2)
                                                            }
                                                            await obj.qnnTableInstance.setEditedRowData({
                                                                ...rowData
                                                            })

                                                            let isCheck = false
                                                            const selectData = await obj.qnnTableInstance.getSelectedRows()
                                                            selectData.map(item => {
                                                                if (item.stockVOID === obj.rowData.stockVOID) {
                                                                    isCheck = true
                                                                }
                                                                return true
                                                            })
                                                            // 判断左右表格 是否选中
                                                            if (this.leftTableChangeVal && isCheck) {
                                                                this.resGjConCoefficientJoin1[this.tableOne.getSelectedRowsKey()] || (this.resGjConCoefficientJoin1[this.tableOne.getSelectedRowsKey()] = [])

                                                                this.tableOne.getSelectedRows()[0].voidJoin.split(',').map((item, index) => {
                                                                    if (item === rowData.stockVOID) {
                                                                        this.resGjConCoefficientJoin1[this.tableOne.getSelectedRowsKey()][index] = value
                                                                    }
                                                                    return true
                                                                })
                                                                if (rowData.gjLossCoefficient1 && rowData.gjConCoefficient1) {

                                                                    const params = {
                                                                        projWorkID: this.leftTableChangeVal,
                                                                        stockVOID: rowData.stockVOID,
                                                                        gjLossCoefficient1: +rowData.gjLossCoefficient1,
                                                                        gjConCoefficient1: +rowData.gjConCoefficient1,
                                                                        gjLossCoefficient2: +rowData.gjLossCoefficient2,
                                                                        gjConCoefficient2: +rowData.gjConCoefficient2
                                                                    }
                                                                    await this.props.myFetch('updateZxBuWorksToStockPriceCoe', params)

                                                                }
                                                            } else {
                                                                Msg.warning('请选择叶子节点')
                                                            }
                                                        }
                                                    },
                                                    isInForm: false
                                                },
                                                {

                                                    title: '材料基价',
                                                    dataIndex: 'scenePrice',
                                                    key: 'scenePrice',
                                                    align: 'center',
                                                    width: 100,
                                                },
                                                {
                                                    title: '单价',
                                                    dataIndex: 'scenePrice1',
                                                    key: 'scenePrice1',
                                                    align: 'center',
                                                    width: 100,
                                                },
                                            ]
                                        }
                                    },
                                    {
                                        table: {
                                            title: '剩余工程',
                                            children: [
                                                {
                                                    title: '损耗系数',
                                                    dataIndex: 'gjLossCoefficient2',
                                                    key: 'gjLossCoefficient2',
                                                    width: 150,
                                                    align: "center",
                                                    tdEdit: () => {
                                                        return this.state.status !== '1'
                                                    },
                                                    fieldConfig: {
                                                        type: "number",
                                                        field: 'gjLossCoefficient2',
                                                        onChange: async (value, obj) => {
                                                            const rowData = await obj.qnnTableInstance.getEditedRowData()
                                                            if (rowData.gjLossCoefficient1 && rowData.gjConCoefficient1 && rowData.scenePrice) {
                                                                rowData.scenePrice3 = (+rowData.gjLossCoefficient2 * +rowData.gjConCoefficient2 * +rowData.scenePrice2).toFixed(2)
                                                            } else {
                                                                rowData.scenePrice3 = (+rowData.scenePrice2).toFixed(2)
                                                            }
                                                            await obj.qnnTableInstance.setEditedRowData({
                                                                ...rowData
                                                            })
                                                            let isCheck = false
                                                            const selectData = await obj.qnnTableInstance.getSelectedRows()
                                                            selectData.map(item => {
                                                                if (item.stockVOID === obj.rowData.stockVOID) {
                                                                    isCheck = true
                                                                }
                                                                return true
                                                            })
                                                            // 判断左右表格 是否选中
                                                            if (this.leftTableChangeVal && isCheck) {
                                                                this.resGjLossCoefficientJoin2[this.tableOne.getSelectedRowsKey()] || (this.resGjLossCoefficientJoin2[this.tableOne.getSelectedRowsKey()] = [])

                                                                this.tableOne.getSelectedRows()[0].voidJoin.split(',').map((item, index) => {
                                                                    if (item === rowData.stockVOID) {
                                                                        this.resGjLossCoefficientJoin2[this.tableOne.getSelectedRowsKey()][index] = value
                                                                    }
                                                                    return true
                                                                })
                                                                if (rowData.gjLossCoefficient2 && rowData.gjConCoefficient2) {
                                                                    const params = {
                                                                        projWorkID: this.leftTableChangeVal,
                                                                        stockVOID: rowData.stockVOID,
                                                                        gjLossCoefficient1: +rowData.gjLossCoefficient1,
                                                                        gjConCoefficient1: +rowData.gjConCoefficient1,
                                                                        gjLossCoefficient2: +rowData.gjLossCoefficient2,
                                                                        gjConCoefficient2: +rowData.gjConCoefficient2
                                                                    }
                                                                    await this.props.myFetch('updateZxBuWorksToStockPriceCoe', params)

                                                                }
                                                            } else {
                                                                Msg.warning('请选择叶子节点')
                                                            }
                                                        }
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    title: '折算系数',
                                                    dataIndex: 'gjConCoefficient2',
                                                    key: 'gjConCoefficient2',
                                                    width: 150,
                                                    align: "center",
                                                    tdEdit: () => {
                                                        return this.state.status !== '1'
                                                    },
                                                    fieldConfig: {
                                                        type: "number",
                                                        field: 'gjConCoefficient2',
                                                        onChange: async (value, obj, row) => {
                                                            const rowData = await obj.qnnTableInstance.getEditedRowData()
                                                            if (rowData.gjLossCoefficient1 && rowData.gjConCoefficient1 && rowData.scenePrice) {
                                                                rowData.scenePrice3 = (+rowData.gjLossCoefficient2 * +rowData.gjConCoefficient2 * +rowData.scenePrice2).toFixed(2)
                                                            } else {
                                                                rowData.scenePrice3 = (+rowData.scenePrice3).toFixed(2)
                                                            }
                                                            await obj.qnnTableInstance.setEditedRowData({
                                                                ...rowData
                                                            })

                                                            let isCheck = false
                                                            const selectData = await obj.qnnTableInstance.getSelectedRows()
                                                            selectData.map(item => {
                                                                if (item.stockVOID === obj.rowData.stockVOID) {
                                                                    isCheck = true
                                                                }
                                                                return true
                                                            })

                                                            // 判断左右表格 是否选中
                                                            if (this.leftTableChangeVal && isCheck) {
                                                                this.resGjConCoefficientJoin2[this.tableOne.getSelectedRowsKey()] || (this.resGjConCoefficientJoin2[this.tableOne.getSelectedRowsKey()] = [])

                                                                this.tableOne.getSelectedRows()[0].voidJoin.split(',').map((item, index) => {
                                                                    if (item === rowData.stockVOID) {
                                                                        this.resGjConCoefficientJoin2[this.tableOne.getSelectedRowsKey()][index] = value
                                                                    }
                                                                    return true
                                                                })

                                                                if (rowData.gjLossCoefficient2 && rowData.gjConCoefficient2) {
                                                                    const params = {
                                                                        projWorkID: this.leftTableChangeVal,
                                                                        stockVOID: rowData.stockVOID,
                                                                        gjLossCoefficient1: +rowData.gjLossCoefficient1,
                                                                        gjConCoefficient1: +rowData.gjConCoefficient1,
                                                                        gjLossCoefficient2: +rowData.gjLossCoefficient2,
                                                                        gjConCoefficient2: +rowData.gjConCoefficient2
                                                                    }
                                                                    await this.props.myFetch('updateZxBuWorksToStockPriceCoe', params)
                                                                }
                                                            } else {
                                                                Msg.warning('请选择叶子节点')
                                                            }
                                                        }
                                                    },
                                                    isInForm: false
                                                },
                                                {

                                                    title: '材料基价',
                                                    dataIndex: 'scenePrice2',
                                                    key: 'scenePrice2',
                                                    align: 'center',
                                                    width: 100,
                                                },
                                                {
                                                    title: '单价',
                                                    dataIndex: 'scenePrice3',
                                                    key: 'scenePrice3',
                                                    align: 'center',
                                                    width: 100,
                                                },
                                            ]
                                        }
                                    },
                                    // {
                                    //     isInTable: false,
                                    //     table: {
                                    //         title: '分类',
                                    //         dataIndex: 'businessType',
                                    //         key: 'businessType',
                                    //     },
                                    //     isInForm: false
                                    // },
                                ]}
                            />
                        </Spin>
                    </Col>
                </Row>
            </div>

        )
    }
}
export default index