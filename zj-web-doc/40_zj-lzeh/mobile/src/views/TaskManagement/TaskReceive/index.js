import React, { Component } from "react";
import s from "./index.less"
import { NavBar, Icon, Toast, Button, Flex, Modal } from "antd-mobile"
import { Table } from 'antd';
import QnnForm from "../../modules/qnn-form";
import { push } from "react-router-redux";

class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            bordered: false,
            loading: false,
            size: 'small',
            showHeader: true,
            selectedRows: [],
            selectData: [],
            scroll: undefined,
            hasData: true,
            dataArr: [],
            showModal: false,
            detailViewData: {},
            modalType: 'detail'
        };
    }
    componentDidMount() {
        this.getData()
    }
    getAndroidApiUserInfo = (field) => {
        if (this.props.myPublic.androidApi?.getStrUserLoginInfo?.()) {
            return JSON.parse(this.props.myPublic.androidApi?.getStrUserLoginInfo?.()).userInfo?.[field]
        } else {
            return null
        }
    }
    //获取所有数据
    getData() {
        this.props.myFetch('getZjLzehTempTaskManageTreeList', {
            sort: 1,
            implementPerson: this.getAndroidApiUserInfo('realName')
        }).then(({ data, success, message }) => {
            if (success) {
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        data[i].key = data[i].zjLzehTempTaskManageId
                        if (data[i].children.length > 0) {
                            for (var j = 0; j < data[i].children.length; j++) {
                                data[i].children[j].key = data[i].children[j].zjLzehTempTaskManageId
                            }
                        }
                    }

                    this.setState({ dataArr: data, current: 1 })
                } else {
                    this.setState({ dataArr: [] })
                }

            } else {
                Toast.fail(message, 1);
            }
        })
    }
    //创建子任务
    addForChildren() {
        let rowData = this.state.selectData
        if (rowData.length === 1 && rowData[0].status === '1' && rowData[0].allotPerson !== this.getAndroidApiUserInfo('realName')) {
            return <Button
                size={'small'}
                type="primary" inline
                onClick={() => {
                    this.setState({
                        detailViewData: rowData[0],
                        showModal: true,
                        modalType: 'add'
                    })
                }}
            >创建子任务</Button>
        } else {
            return <Button
                size={'small'}
                type="primary" inline
                disabled={true}
            >创建子任务</Button>
        }
    }

    //完成任务提交
    saveButton() {
        let rowData = this.state.selectData

        const NodeTree = new NodeTreeFunc(rowData)
        const currentName = this.getAndroidApiUserInfo('realName')
        const implementPerson = rowData[0]?.implementPerson

        function NodeTreeFunc(rowData) {
            this.rowData = rowData
            this.isCommitOk = true
            this.getRowDataChildrenStatusIsOkFunc = (children) => {
                for (let i = 0; i < children.length; i++) {
                    if (children[i].status !== '4') {
                        this.isCommitOk = false
                        if (children.length && children[i].children) {
                            this.getRowDataChildrenStatusIsOkFunc(children[i].children)
                        }
                    }
                }
            }
            if (this.rowData.length) {
                this.getRowDataChildrenStatusIsOkFunc(this.rowData[0].children)
            }
        }
        if (rowData.length === 1 && rowData[0].status === '1' && NodeTree.isCommitOk && currentName === implementPerson) {
            return <Button
                size={'small'}
                type="primary" inline
                onClick={() => {
                    this.setState({
                        detailViewData: rowData[0],
                        showModal: true,
                        modalType: 'submit'
                    })
                }}
            >完成任务提交</Button>;
        } else {
            return <Button
                size={'small'}
                type="primary" inline
                disabled={true}
            >完成任务提交</Button>
        }
    }
    //退回按钮
    returnStatusDisabled() {
        let rowData = this.state.selectData

        const NodeTree = new NodeTreeFunc(rowData)

        function NodeTreeFunc(rowData) {
            this.rowData = rowData
            this.isCommitOk = true
            this.getRowDataChildrenStatusIsOkFunc = (children) => {
                for (let i = 0; i < children.length; i++) {
                    if (children[i].status !== '4') {
                        this.isCommitOk = false
                        if (children.length && children[i].children) {
                            this.getRowDataChildrenStatusIsOkFunc(children[i].children)
                        }
                    }
                }
            }
            if (this.rowData.length) {
                this.getRowDataChildrenStatusIsOkFunc(this.rowData[0].children)
            }
        }

        if (rowData.length === 1 && rowData[0].status === '3' && rowData[0].allotPerson === this.getAndroidApiUserInfo('realName') && NodeTree.isCommitOk) {
            return <Button
                size={'small'}
                type="primary" inline
                onClick={() => {
                    this.props.myFetch('updateZjLzehTempTaskManage', { ...rowData[0], status: '1' }).then(({ success, message }) => {
                        if (success) {
                            this.getData()
                            this.setState({
                                selectData: [],
                                selectedRows: [],
                            })
                            Toast.success('任务提交成功', 1);
                        } else {
                            Toast.fail(message, 1);
                        }
                    })
                }}
            >退回</Button>
        } else {
            return <Button
                size={'small'}
                type="primary" inline
                disabled={true}
            >退回</Button>
        }
    }
    //任务分配按钮
    taskAllocationDisabled() {
        let rowData = this.state.selectData
        if (rowData.length === 1 && (rowData[0].status === '0')) {
            return <Button
                size={'small'}
                type="primary" inline
                onClick={() => {
                    this.props.myFetch('updateZjLzehTempTaskManage', { ...rowData[0], status: '1' }).then(({ success, message }) => {
                        if (success) {
                            this.getData()
                            this.setState({
                                selectData: [],
                                selectedRows: [],
                            })
                            Toast.success('任务分配成功', 1);
                        } else {
                            Toast.fail(message, 1);
                        }
                    })
                }}
            >任务分配</Button>
        } else {
            return <Button
                size={'small'}
                type="primary" inline
                disabled={true}
            >任务分配</Button>
        }
    }
    //任务完成确认按钮
    submittedDisabled() {
        let rowData = this.state.selectData

        const NodeTree = new NodeTreeFunc(rowData)

        function NodeTreeFunc(rowData) {
            this.rowData = rowData
            this.isCommitOk = true
            this.getRowDataChildrenStatusIsOkFunc = (children) => {
                for (let i = 0; i < children.length; i++) {
                    if (children[i].status !== '4') {
                        this.isCommitOk = false
                        if (children.length && children[i].children) {
                            this.getRowDataChildrenStatusIsOkFunc(children[i].children)
                        }
                    }
                }
            }
            if (this.rowData.length) {
                this.getRowDataChildrenStatusIsOkFunc(this.rowData[0].children)
            }
        }

        if (rowData.length === 1 && rowData[0].status === '3' && rowData[0].allotPerson === this.getAndroidApiUserInfo('realName') && NodeTree.isCommitOk) {
            return <Button
                size={'small'}
                type="primary" inline
                onClick={() => {

                    this.props.myFetch('updateZjLzehTempTaskManage', { ...rowData[0], status: '4' }).then(({ success, message }) => {
                        if (success) {
                            this.getData()
                            this.setState({
                                selectData: [],
                                selectedRows: [],
                            })
                            Toast.success('任务提交成功', 1);
                        } else {
                            Toast.fail(message, 1);
                        }
                    })
                }}
            >任务完成确认</Button>
        } else {
            return <Button
                size={'small'}
                type="primary" inline
                disabled={true}
            >任务完成确认</Button>
        }
    }
    render() {
        const {
            mainModule
        } = this.props.myPublic.appInfo;

        const columns = [
            {
                title: '任务名称',
                dataIndex: 'taskName',
                width: 200,
                render: (text, data) => <div
                    style={{ color: 'rgb(24,144,255' }}
                    onClick={() => {
                        this.setState({
                            detailViewData: data,
                            showModal: true,
                            modalType: 'detail'
                        })
                    }}
                >{text}</div>,
            },
            {
                title: '分配人名',
                dataIndex: 'allotPerson',
                width: 100,
            },
            {
                title: '分配对象',
                dataIndex: 'implementPerson',
                width: 100,
            },
            {
                title: '状态',
                dataIndex: 'status',
                width: 100,
                render: (h) => {
                    if (h === '0') {
                        return '未分配'
                    } else if (h === '1') {
                        return '已分配'
                    } else if (h === '2') {
                        return '收回'
                    } else if (h === '3') {
                        return '已提交'
                    } else if (h === '4') {
                        return '已确认'
                    }
                }

            },
        ];
        const { dataArr, current, detailViewData, modalType } = this.state;
        const tableColumns = columns.map(item => ({ ...item, ellipsis: true }));
        return (
            <div className={s.root}>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            // this.props.dispatch(push(`${mainModule}App`));
                            this.props.myPublic?.androidApi?.closeActivity?.() || this.props.dispatch(push(`${mainModule}App`));
                            // this.props.history.goBack()
                        }}
                    >
                        {"任务接收"}
                    </NavBar>
                </div>
                <div className={s.center}>
                    <Table
                        {...this.state}
                        pagination={{ position: 'bottomRight' }}
                        columns={tableColumns}
                        dataSource={dataArr ? dataArr : null}
                        scroll={{ x: '500px', y: '63vh' }}
                        rowSelection={{
                            onChange: (record, data) => {
                                this.setState({
                                    selectedRows: record,
                                    selectData: data
                                })
                            },
                            selectedRowKeys: this.state.selectedRows
                        }}
                        pagination={{
                            position: 'bottom', current, onChange: (a) => {
                                this.setState({ current: a })
                            }
                        }}
                    />
                </div>
                <Flex direction={'row'} style={{ height: 40, position: 'absolute', bottom: 50, width: '100%', padding: '0px 20px 0px 20px' }} justify={'between'}>
                    {this.addForChildren()}
                    {this.returnStatusDisabled()}
                    {this.taskAllocationDisabled()}
                </Flex>
                <Flex direction={'row'} style={{ height: 40, position: 'absolute', bottom: 10, width: '100%', padding: '0px 20px 0px 20px' }} justify={'between'}>
                    {this.saveButton()}
                    {this.submittedDisabled()}
                </Flex>
                <Modal
                    visible={this.state.showModal}
                    transparent={true}
                    style={{ width: window.innerWidth, position: 'fixed', top: '0' }}
                >
                    <div style={{ height: window.innerHeight, width: '100%' }}>
                        <QnnForm
                            style={{ height: '70vh', width: '140%' }}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo }}
                            wrappedComponentRef={(me) => {
                                this.table = me;
                            }}
                            fetchConfig={() => {
                                if (modalType !== 'add') {
                                    return {
                                        apiName: 'getZjLzehTempTaskManageDetail',
                                        otherParams: { zjLzehTempTaskManageId: detailViewData.zjLzehTempTaskManageId }
                                    }
                                } return {}

                            }}
                            formConfig={[
                                {
                                    field: "zjLzehTempTaskManageId",
                                    type: 'string',
                                    hide: true
                                },
                                {
                                    label: '任务名称',
                                    field: "taskName",
                                    type: 'string',
                                    disabled: modalType === 'add' ? false : true,
                                    required: true,
                                },
                                {
                                    label: '任务描述',
                                    field: "taskDescribe",
                                    type: 'string',
                                    disabled: modalType === 'add' ? false : true,
                                    required: true,

                                },
                                {
                                    label: '分配人名',
                                    field: "allotPerson",
                                    type: 'string',
                                    disabled: true,
                                    initialValue: this.getAndroidApiUserInfo('userId'),
                                },
                                {
                                    label: '开始日期',
                                    field: "beginDate",
                                    type: 'date',
                                    disabled: modalType === 'add' ? false : true,
                                    required: true,
                                },
                                {
                                    label: '要求完成日期',
                                    field: "requireComplateDate",
                                    type: 'date',
                                    required: true,
                                    disabled: modalType === 'add' ? false : true,
                                },
                                {
                                    label: '实际完成日期',
                                    field: "realCompalteDate",
                                    type: 'date',
                                    required: true,
                                    // disabled: modalType === 'submit' ? false : true,
                                    hide: modalType === 'submit' ? false : true
                                },
                                {
                                    label: '分配对象',
                                    field: "launcherList",
                                    type: 'treeSelect',
                                    disabled: modalType === 'add' ? false : true,
                                    required: true,
                                    treeSelectOption: {
                                        help: true,
                                        fetchConfig: {
                                            apiName: 'getSysDepartmentUserAllTree',
                                        },
                                        search: false,
                                        maxNumber: '1',
                                        selectType: '2',

                                    },
                                },
                                {
                                    label: '附件',
                                    field: 'fileList',
                                    type: 'files',
                                    fetchConfig: {
                                        apiName: window.configs.domain + 'upload'
                                    },
                                    disabled: modalType === 'detail' ? true : detailViewData.status === '4' || detailViewData.status === '3' ? true : false
                                },
                                {
                                    label: '完成情况说明',
                                    field: "complateExplain",
                                    type: 'string',
                                    // disabled: modalType === 'submit' ? false : true,
                                    hide: modalType === 'submit' ? false : true
                                },
                                {
                                    label: '状态',
                                    field: "status",
                                    type: 'select',
                                    disabled: true,
                                    hide: true,
                                    optionConfig: { label: 'label', value: 'value' },
                                    optionData: [
                                        { label: '未分配', value: '0' },
                                        { label: '已分配', value: '1' },
                                        { label: '收回', value: '2' },
                                        { label: '已提交', value: '3' },
                                        { label: '已确认', value: '4' },
                                    ]
                                }
                            ].map(item => {
                                return {
                                    ...item,
                                    labelStyle: { textAlign: "left", fontSize: '13px' },
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 8 },
                                            sm: { span: 8 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 20 },
                                            sm: { span: 20 }
                                        }
                                    },
                                }
                            })}
                        />
                        <Flex direction={'row'} style={{ width: '100%', position: 'absolute', bottom: 30, height: 30 }} justify={'around'}>
                            <Button size={'small'} type="primary"
                                onClick={() => { this.setState({ showModal: false }) }}
                            >关闭</Button>
                            {modalType === 'detail' ? null : <Button size={'small'} type="primary"
                                disabled={modalType === 'detail' ? true : false}
                                onClick={() => {
                                    if (modalType === 'add') {
                                        let tableChildren = this.table.form.getFieldsValue()
                                        if (!tableChildren.taskName) {
                                            Toast.fail('请填写任务名称', 1)
                                            return
                                        }
                                        if (!tableChildren.taskDescribe) {
                                            Toast.fail('请填写任务描述', 1)
                                            return
                                        }
                                        if (!tableChildren.beginDate) {
                                            Toast.fail('请填写开始日期', 1)
                                            return
                                        }
                                        if (!tableChildren.requireComplateDate) {
                                            Toast.fail('请填写要求完成日期', 1)
                                            return
                                        }
                                        if (!tableChildren.launcherList) {
                                            Toast.fail('请填写分配对象', 1)
                                            return
                                        }
                                        tableChildren.status = '0'
                                        tableChildren.allotPerson = this.getAndroidApiUserInfo('realName')
                                        tableChildren.allotPersonId = this.getAndroidApiUserInfo('userId')
                                        tableChildren.parentId = detailViewData.zjLzehTempTaskManageId
                                        this.props.myFetch('addZjLzehTempTaskManage', tableChildren).then(({ data, success, message }) => {
                                            if (success) {
                                                this.setState({
                                                    showModal: false,
                                                    selectedRows: [],
                                                    selectData: [],
                                                })
                                                this.getData()
                                            } else {
                                                Toast.fail(message, 1);
                                            }
                                        })
                                    } else if (modalType === 'submit') {
                                        let params = detailViewData
                                        params.realCompalteDate = this.table.form.getFieldsValue().realCompalteDate
                                        if (!params.realCompalteDate) {
                                            Toast.fail('请选择实际完成日期', 1)
                                            return
                                        }
                                        params.fileList = this.table.form.getFieldsValue().fileList
                                        params.complateExplain = this.table.form.getFieldsValue().complateExplain
                                        params.status = '3'
                                        params.allotPerson = detailViewData.allotPerson
                                        params.allotPersonId = detailViewData.allotPersonId
                                        params.zjLzehTempTaskManageId = detailViewData.zjLzehTempTaskManageId
                                        this.props.myFetch('updateZjLzehTempTaskManage', params).then(({ data, success, message }) => {
                                            if (success) {
                                                this.setState({
                                                    showModal: false,
                                                    selectedRows: [],
                                                    selectData: [],
                                                })
                                                this.getData()
                                            } else {
                                                Toast.fail(message, 1);
                                            }
                                        })
                                    }
                                }}
                            >保存</Button>}

                        </Flex>
                    </div>

                </Modal>
            </div>
        );
    }
}
export default index;
