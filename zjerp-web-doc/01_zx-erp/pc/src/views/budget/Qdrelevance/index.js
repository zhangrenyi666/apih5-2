import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-form";
import { message as Msg, Spin, Row, Col, Button, Modal } from 'antd';
import s from "./style.less";
const { confirm } = Modal;
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            loading: false,
            queryProjectID: '',
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId
        }
    }

    leftTableChangeVal = ''
    rightTableChangeVal = ''
    detailListID = ''

    FloatMulTwo(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }

    render() {
        const { departmentId } = this.state
        const { queryProjectID, loading } = this.state
        return (
            <div>
                <div style={{ display: 'flex', justifyContent: 'flex-end' }}>
                    <Button
                        style={{ marginRight: "20px" }}
                        type="primary"
                        onClick={async () => {

                            confirm({
                                title: '确定要自动关联吗?自动关联将关联所有数据!',
                                content: '',
                                onOk: () => {
                                    if (this.state.queryProjectID) {
                                        this.setState({
                                            loading: true
                                        }, async () => {
                                            const { success, message } = await this.props.myFetch('automaticLinkZxBuYgjResTechnics', { orgID: this.state.queryProjectID })
                                            this.setState({
                                                loading: false
                                            })

                                            if (success) {
                                                this.tableOne.refresh()
                                                Msg.success('自动关联成功!')
                                            } else {
                                                Msg.error(message)
                                            }
                                        })
                                    } else {
                                        Msg.warning('请选择项目!')
                                    }
                                },
                                onCancel() { },
                            })

                        }}
                    >自动关联</Button>

                    <Button
                        type="primary"
                        onClick={async () => {
                            // 是否清除/自动关联
                            confirm({
                                title: '确定要取消关联吗?取消关联将删除所有的关联数据!',
                                content: '',
                                onOk: () => {
                                    if (this.state.queryProjectID) {
                                        this.setState({
                                            loading: true
                                        }, async () => {
                                            let params = {}
                                            if (this.detailListID) {
                                                params = {
                                                    orgID: this.state.queryProjectID,
                                                    billID: this.detailListID
                                                }
                                            } else {
                                                params = {
                                                    orgID: this.state.queryProjectID,
                                                }
                                            }
                                            const { success, message } = await this.props.myFetch('cancelLinkZxBuYgjResTechnics', params)
                                            this.setState({
                                                loading: false
                                            })
                                            if (success) {
                                                this.tableOne.refresh()
                                                Msg.success('取消关联成功!')
                                            } else {
                                                Msg.error(message)
                                            }
                                        })
                                    } else {
                                        Msg.warning('请选择项目!')
                                    }
                                },
                                onCancel() { },
                            })
                        }}
                    >取消关联</Button>
                </div>
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
                                label: '工程项目',
                                field: 'projectName',
                                placeholder: '请选择',
                                required: false,
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
                                allowClear: false,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                                onChange: (val) => {
                                    this.setState({
                                        queryProjectID: val
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
                                    apiName: "getZxBuWorksNoName",
                                    otherParams: {
                                        orgID: queryProjectID,
                                        parentID: '-1'
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

                                        // 设置 右侧表格选中
                                        if (selectedData.length && selectedData[0].workNoJoin) {
                                            let list = []
                                            selectedData[0].workNoJoin.split(',').map(async (item, index) => {

                                                list.push(...this.tableQD.getTableData().filter(ele => ele.workNo === item))

                                                // rightTableData[index] = {
                                                //     gjLossCoefficient1: this.tableQD.getTdRef({
                                                //         field: 'gjLossCoefficient1',
                                                //         rowId: item
                                                //     }),
                                                //     gjConCoefficient1: this.tableQD.getTdRef({
                                                //         field: 'gjConCoefficient1',
                                                //         rowId: item
                                                //     }),
                                                //     scenePrice1: this.tableQD.getTdRef({
                                                //         field: 'scenePrice1',
                                                //         rowId: item
                                                //     }),
                                                //     scenePrice: this.tableQD.getTdRef({
                                                //         field: 'scenePrice',
                                                //         rowId: item
                                                //     }),
                                                // }
                                            })

                                            // rightTableData.map((item, index) => {
                                            //     if (rightTableData[index]['gjLossCoefficient1'] && rightTableData[index]['gjConCoefficient1'] && rightTableData[index]['scenePrice1']) {
                                            //         rightTableData[index]['gjLossCoefficient1'].setTdData(selectedData[0].resGjLossCoefficientJoin.split(',')[index])
                                            //         rightTableData[index]['gjConCoefficient1'].setTdData(selectedData[0].resGjConCoefficientJoin.split(',')[index])
                                            //         rightTableData[index]['scenePrice1'].setTdData(
                                            //             +selectedData[0].resGjLossCoefficientJoin.split(',')[index] * +selectedData[0].resGjConCoefficientJoin.split(',')[index] * +rightTableData[index]['scenePrice'].getTdData()
                                            //         )
                                            //     }
                                            // })

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
                                            // align: 'left',
                                            render: (data, rowData) => {
                                                return <div style={{ textIndent: (rowData.treeNode.length - 4) * 2 + 'px' }}>
                                                    {data}
                                                </div>;
                                            }
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
                                        isInTable: false,
                                        table: {
                                            title: '关联id',
                                            dataIndex: 'workIDJoin',
                                            key: 'workIDJoin'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '数据库清单编号',
                                            dataIndex: 'workNoJoin',
                                            key: 'workNoJoin',
                                            align: 'center',
                                            // textOverflow: "lineFeed",
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '数据库清单名称',
                                            dataIndex: 'workNameJoin',
                                            key: 'workNameJoin',
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
                                    apiName: "getZxBuWorksTreeListAll",
                                    otherParams: {
                                        orgID: "0",
                                    }
                                }}
                                antd={{
                                    rowKey: 'zxBuWorksId'
                                }}
                                rowSelection={{
                                    hideSelectAll: true,
                                    type: 'checkbox',
                                    onSelect: (record, selected, selectedRowKeys, selectedRow) => {

                                        if (!this.leftTableChangeVal) {
                                            Msg.error("没有选择项目叶子清单!")
                                            this.tableQD.clearSelectedRows()
                                        } else {
                                            const params = {
                                                //项目id
                                                orgID: this.state.queryProjectID,
                                                //项目清单id
                                                billID: this.detailListID,

                                                myBillID: record.zxBuWorksId
                                            }

                                            if (selected) {
                                                this.setState({
                                                    loading: true
                                                }, () => {
                                                    this.props.myFetch('relevanceZxBuYgjResTechnics', params).then(res => {
                                                        let selectList = {
                                                            workNo: [],
                                                            workName: []
                                                        }
                                                        selectedRow.map((item, index) => {
                                                            selectList['workNo'].push(item.workNo)
                                                            selectList['workName'].push(item.workName)
                                                            return true
                                                        })

                                                        const listData = this.tableOne.getTableData().map(item => {
                                                            if (this.tableOne.getSelectedRowsKey()[0] === item.id) {
                                                                return {
                                                                    ...item,
                                                                    workNoJoin: selectList['workNo'].join(','),
                                                                    workNameJoin: selectList['workName'].join(',')
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
                                                    this.props.myFetch('removeRelevanceZxBuYgjResTechnics', params).then(res => {
                                                        let selectList = {
                                                            workNo: [],
                                                            workName: []
                                                        }
                                                        selectedRow.map((item, index) => {
                                                            selectList['workNo'].push(item.workNo)
                                                            selectList['workName'].push(item.workName)
                                                            return true
                                                        })
                                                        const listData = this.tableOne.getTableData().map(item => {
                                                            if (this.tableOne.getSelectedRowsKey()[0] === item.id) {
                                                                return {
                                                                    ...item,
                                                                    workNoJoin: selectList['workNo'].join(','),
                                                                    workNameJoin: selectList['workName'].join(',')
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
                                    getCheckboxProps: record => ({
                                        // name:record.name,
                                        disabled: record.isLeaf === 0,
                                    }),
                                }}
                                actionBtns={[]}
                                rowClassName={(record, index) => {
                                    return record.isLeaf === 0 ? s.backgrounde6 : ''
                                }}
                                formConfig={[
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'zxBuWorksId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '数据库清单编号',
                                            dataIndex: 'workNo',
                                            key: 'workNo',
                                            render: (data, rowData) => {
                                                return <div style={{ textIndent: (rowData.treeNode.length - 4) * 2 + 'px' }}>
                                                    {/* <Checkbox id={rowData.zxBuWorksId} name="itemR"
                                                        // defaultChecked={this.inputForm.form?.getFieldsValue()?.qdNo?.indexOf(rowData.workNo) != -1 && this.inputForm.form?.getFieldsValue()?.qdNo?.indexOf(rowData.workNo) != null ? true : false}
                                                        style={{ visibility: rowData.isLeaf === 0 ? 'hidden' : '', padding: '0px 10px 0px 0px' }}
                                                        onChange={this.onChangeRadioALL.bind(this, rowData)} /> */}
                                                    {data}
                                                </div>;
                                            }
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '数据库清单名称',
                                            dataIndex: 'workName',
                                            key: 'workName',
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
                                            width: 15,
                                        },
                                        isInForm: false
                                    },
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