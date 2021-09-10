import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Spin, Row, Col, Drawer, Button, Modal } from 'antd';
import { PullRequestOutlined } from '@ant-design/icons';
const confirm = Modal.confirm;
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
        }
    }

    render() {
        const { loading } = this.state;
        return (
            <Spin spinning={loading}>
                <Row>
                    <Col span={12}>
                        <QnnForm
                            fetch={this.props.myFetch}
                            wrappedComponentRef={(me) => {
                                this.table = me;
                            }}
                            formConfig={[
                                {
                                    label: '【当前选中单位】',
                                    field: "lsbzdwdwmc",
                                    type: 'string',
                                    disabled: true,
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 6 },
                                            sm: { span: 6 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 18 },
                                            sm: { span: 18 }
                                        }
                                    },
                                    span: 18
                                },
                                {
                                    type: 'component',
                                    field: 'component',
                                    Component: obj => {
                                        return (
                                            <div style={{ marginRight: 13, marginTop: 10, textAlign: "right" }}>
                                                <Button type="primary"
                                                    icon={<span key={0} style={{ paddingRight: 10 }}><PullRequestOutlined /></span>}
                                                    onClick={() => {
                                                        let selectRow = this.tableOne.getSelectedRows()
                                                        if (selectRow.length === 0) {
                                                            Msg.warn('请选择核算单位')
                                                            return
                                                        }
                                                        this.setState({ showDrawer: true })
                                                    }}>挂接</Button>
                                            </div>
                                        );
                                    },
                                    span: 6
                                },
                                {
                                    type: 'qnnTable',
                                    field: 'table1',
                                    qnnTableConfig: {
                                        fetchConfig: {
                                            apiName: 'getZxSaFiBzdwMainList'
                                        },
                                        antd: {
                                            rowKey: 'lsbzdwdwbh',
                                            size: 'small'
                                        },
                                        paginationConfig: false,
                                        isShowRowSelect: true,
                                        wrappedComponentRef: (me) => {
                                            this.tableOne = me;
                                        },
                                        rowSelection: {
                                            type: 'radio',
                                            onChange: async (selectedRowKey, selectedData) => {
                                                await this.table.form.setFieldsValue({
                                                    lsbzdwdwmc: selectedData[0]?.lsbzdwdwmc,
                                                })
                                                this.tableTwo.refresh()
                                            }
                                        },
                                        formConfig: [
                                            {
                                                isInTable: false,
                                                form: {
                                                    field: 'lsbzdwdwbh',
                                                    type: 'string'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '核算单位',
                                                    dataIndex: 'lsbzdwdwmc',
                                                    key: 'lsbzdwdwmc',
                                                    width: 360,
                                                    fixed: 'left',
                                                    onClick: ({
                                                        rowData,
                                                        qnnTableInstance: {
                                                            getVTableData,
                                                            setTableData,
                                                            getExpandedRowsKey,
                                                            expandNode,
                                                            tool: { message }
                                                        }
                                                    }) => {
                                                        let expandedRowsKey = getExpandedRowsKey();
                                                        let parentID = rowData.lsbzdwdwnm;
                                                        let tableData = getVTableData()
                                                        if (expandedRowsKey.includes(parentID)) {
                                                            expandNode(parentID, "close");
                                                            return;
                                                        }
                                                        message.loading('loading', 1)
                                                        this.props.myFetch('getZxSaFiBzdwChildList', {
                                                            lsbzdwdwnm: parentID
                                                        })
                                                            .then((res) => {
                                                                message.destroy()
                                                                if (res.success) {
                                                                    var childrenData = res.data;
                                                                    if (!childrenData.length) {
                                                                        Msg.warn("该节点没有子集数据")
                                                                        return;
                                                                    }
                                                                    var loopFn = function (data) {
                                                                        for (var i = 0; i < data.length; i++) {
                                                                            if (data[i].lsbzdwdwnm === parentID) {
                                                                                data[i].children = childrenData;
                                                                            } else if (data[i].children) {
                                                                                data[i].children = loopFn(data[i].children)
                                                                            }
                                                                        }
                                                                        return data;
                                                                    }
                                                                    tableData = loopFn([...tableData]);
                                                                    expandNode(rowData.lsbzdwdwbh, 'expand');
                                                                    setTableData([...tableData]);
                                                                } else {
                                                                    Msg.error(res.message)
                                                                }
                                                            })
                                                    }
                                                }
                                            }
                                        ]
                                    }
                                }
                            ]}
                        />
                    </Col>
                    <Col span={12} style={{ paddingLeft: 10 }}>
                        <QnnForm
                            fetch={this.props.myFetch}
                            formConfig={[
                                {
                                    type: 'component',
                                    field: 'component',
                                    Component: obj => {
                                        return (
                                            <div style={{ marginTop: 10, marginRight: 13, textAlign: "right" }}>
                                                <Button type="primary"
                                                    style={{ marginBottom: 13 }}
                                                    onClick={() => {
                                                        let SelectedRowsData = this.tableTwo.getSelectedRows()
                                                        if (SelectedRowsData.length === 0) {
                                                            Msg.warn('请选择项目名称')
                                                            return
                                                        }
                                                        confirm({
                                                            content: '确定取消挂接吗?',
                                                            onOk: () => {
                                                                this.props.myFetch('batchDeleteUpdateZxSaBzdwHookup', SelectedRowsData).then(
                                                                    ({ success, message }) => {
                                                                        if (success) {
                                                                            this.tableTwo.clearSelectedRows()
                                                                            this.tableTwo.refresh();
                                                                        } else {
                                                                            Msg.error(message)
                                                                        }
                                                                    }
                                                                );
                                                            }
                                                        })
                                                    }}>取消挂接</Button>
                                            </div>
                                        );
                                    },
                                    span: 24
                                },
                                {
                                    type: 'qnnTable',
                                    field: 'table1',
                                    qnnTableConfig: {
                                        fetchConfig: {
                                            apiName: 'getZxSaBzdwHookupBylsbzdwDwbh',
                                            otherParams: () => {
                                                return {
                                                    lsbzdwDwbh: this.tableOne?.getSelectedRows()?.[0]?.lsbzdwdwbh,
                                                }
                                            }
                                        },
                                        wrappedComponentRef: (me) => {
                                            this.tableTwo = me;
                                        },
                                        antd: {
                                            rowKey: 'departmentId',
                                            size: 'small'
                                        },
                                        paginationConfig: false,
                                        formConfig: [
                                            {
                                                table: {
                                                    title: '项目名称',
                                                    dataIndex: 'projectName',
                                                    key: 'projectName',
                                                    width: 360,
                                                    fixed: 'left',
                                                }
                                            }
                                        ]
                                    }
                                }
                            ]}
                        />
                    </Col>
                </Row>
                <Drawer
                    title="挂接项目"
                    placement="right"
                    closable={true}
                    onClose={() => { this.setState({ showDrawer: false }) }}
                    visible={this.state.showDrawer}
                    destroyOnClose={true}
                    width={'80%'}
                >
                    <Row>
                        <Col span={11} style={{ paddingTop: 35 }}>
                            <QnnTable
                                fetch={this.props.myFetch}
                                wrappedComponentRef={(me) => {
                                    this.drawerOne = me;
                                }}
                                fetchConfig={{
                                    apiName: 'getZxCtContractOrgNotInHookUp'
                                }}
                                antd={{
                                    rowKey: 'id',
                                    size: 'small'
                                }}
                                paginationConfig={false}
                                pageSize={20}
                                rowSelection={{
                                    type: 'radio',
                                }}
                                actionBtnsContainerStyle={{
                                    textAlign: 'right'
                                }}
                                formConfig={[
                                    {
                                        isInTable: false,
                                        form: {
                                            dataIndex: 'orgID',
                                            key: 'orgID',
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            dataIndex: 'id',
                                            key: 'id',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '未挂接项目',
                                            dataIndex: 'orgName',
                                            key: 'orgName',
                                            width: 360,
                                            filter: true,
                                            fieldsConfig: { type: 'string' },
                                        }
                                    }
                                ]}

                            />
                        </Col>
                        <Col span={2} >
                            <div style={{ paddingTop: 100, textAlign: 'center', marginTop: 45 }}>
                                <div>
                                    <Button type="primary"
                                        style={{
                                            marginBottom: 100
                                        }}
                                        onClick={async () => {
                                            if (this.drawerOne.getSelectedRows().length === 0) {
                                                Msg.warn('请选择未关联项目')
                                                return
                                            }
                                            let selectRow = await this.drawerOne.getSelectedRows()[0] //选中的未关联数据
                                            let relevanceData = await this.drawerTwo.getTableData() //已关联项目
                                            let notAssociatedData = await this.drawerOne.getTableData() //未关联项目
                                            //删除左侧选中数据
                                            for (var i = 0; i < notAssociatedData.length; i++) {
                                                if (notAssociatedData[i].id === selectRow.id) {
                                                    notAssociatedData.splice(i, 1)
                                                    this.drawerOne.setTableData(notAssociatedData)
                                                    this.drawerOne.clearSelectedRows()
                                                }
                                            }
                                            let newSelectRow = {}
                                            newSelectRow.departmentId = selectRow.orgID
                                            newSelectRow.id = selectRow.id
                                            newSelectRow.projectName = selectRow.orgName
                                            //添加右侧数据
                                            await relevanceData.push(newSelectRow)
                                            this.drawerTwo.setTableData(relevanceData)
                                        }}
                                    > {`>`} </Button>
                                </div>
                                <div>
                                    <Button type="primary"
                                        onClick={async () => {
                                            if (this.drawerTwo.getSelectedRows().length === 0) {
                                                Msg.warn('请选择已选项目')
                                            }
                                            let selectRow = await this.drawerTwo.getSelectedRows()[0] //选中的已关联数据
                                            let relevanceData = await this.drawerTwo.getTableData() //已关联项目
                                            // 删除右侧选中数据
                                            for (var i = 0; i < relevanceData.length; i++) {
                                                if (relevanceData[i]?.zxSaBzdwHookupId === selectRow?.zxSaBzdwHookupId) {
                                                    relevanceData.splice(i, 1)
                                                    this.drawerTwo.setTableData(relevanceData)
                                                    this.drawerTwo.clearSelectedRows()
                                                }
                                            }
                                            let newSelectRow = {}
                                            newSelectRow.orgID = selectRow.departmentId
                                            newSelectRow.id = selectRow.departmentId
                                            newSelectRow.orgName = selectRow.projectName
                                            let notAssociatedData = await this.drawerOne.getTableData() //未关联项目
                                            await notAssociatedData.unshift(newSelectRow)
                                            this.drawerOne.setTableData(notAssociatedData)
                                        }}
                                    >{`<`}</Button>
                                </div>
                            </div>
                        </Col>
                        <Col span={11}>
                            <QnnTable
                                fetch={this.props.myFetch}
                                wrappedComponentRef={(me) => {
                                    this.drawerTwo = me;
                                }}
                                fetchConfig={{
                                    apiName: 'getZxSaBzdwHookupBylsbzdwDwbh',
                                    otherParams: () => {
                                        return {
                                            lsbzdwDwbh: this.tableOne?.getSelectedRows()?.[0]?.lsbzdwdwbh,
                                        }
                                    }
                                }}
                                antd={{
                                    rowKey: 'zxSaBzdwHookupId',
                                    size: 'small'
                                }}
                                paginationConfig={false}
                                rowSelection={{
                                    type: 'radio',
                                    getCheckboxProps: record => ({
                                        name: record.projectName,
                                        disabled: record.remarks === '1',
                                    }),
                                }}
                                actionBtnsContainerStyle={{
                                    textAlign: 'right'
                                }}
                                actionBtns={[
                                    {
                                        name: 'diyDel5',
                                        type: 'dashed5',
                                        label: '取消',
                                        field: 'diyDel',
                                        onClick: () => {
                                            this.setState({ showDrawer: false })
                                        },
                                    },
                                    {
                                        name: 'diyDel6',
                                        type: 'primary',
                                        label: '保存',
                                        field: 'diyDel7',
                                        onClick: () => {
                                            let params = this.tableOne.getSelectedRows()[0]
                                            let List = this.drawerTwo.getTableData()
                                            for (var i = 0; i < List.length; i++) {
                                                List[i].remarks = '1'
                                                List[i].lsbzdwdwbh = this.tableOne.getSelectedRows()[0].lsbzdwdwbh
                                            }
                                            params.zxSaBzdwHookupList = List
                                            this.props.myFetch('batchSaveZxSaBzdwHookupBylsbzdwDwbh', params)
                                                .then(({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message)
                                                        this.tableTwo.refresh()
                                                        this.setState({ showDrawer: false })
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                })
                                        },
                                    }
                                ]}
                                formConfig={[
                                    {
                                        isInTable: false,
                                        form: {
                                            dataIndex: 'departmentId',
                                            key: 'departmentId',
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            dataIndex: 'id',
                                            key: 'id',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '已选项目名称',
                                            dataIndex: 'projectName',
                                            key: 'projectName',
                                            width: 360,
                                            fixed: 'left',
                                        }
                                    }
                                ]}
                            />
                        </Col>
                    </Row>
                </Drawer>
            </Spin>
        );
    }
}
export default index;