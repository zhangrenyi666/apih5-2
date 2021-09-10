import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Tabs, Radio, Tree, Button, Modal, Drawer } from 'antd';
// import { DownOutlined } from '@ant-design/icons';
import { CloseOutlined, ExclamationCircleOutlined } from '@ant-design/icons';
import Maximize from "../common/Maximize"
import Comment from '../../comment'
import SelectFilesDownLoad from '../common/SelectFilesDownLoad'
const { confirm } = Modal
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isModalVisible: false,
            taskManageId: '',
            visible: false,
            defaultWidth: '70%',
            selectedRowsData: null,
            taskName: null,
            beginDate: null,
            requireComplateDate: null
        }
    }
    showModal = () => {
        this.setState({
            isModalVisible: true
        })
    };
    handleOk = () => {
        this.setState({
            isModalVisible: false
        })
    };

    handleCancel = () => {
        this.setState({
            isModalVisible: false
        })
    };
    onClose = () => {
        this.setState({
            visible: false,
        });
    }
    onCommitTaskFunc = () => {
        this.table3.form.validateFields().then(val => {
            const params = { ...this.table3.form.getFieldsValue() }
            this.props.myFetch('updateZjLzehTempTaskManage', { ...params, status: '3' }).then(({ success, message, data }) => {
                if (success) {
                    this.table.refresh()
                    this.table.clearSelectedRows();
                    Msg.success('完成任务提交成功!')
                } else {
                    Msg.error(message)
                }
            })
            this.setState({
                visible: false,
            });
        })
    }
    changeMaxWidthFunc = (status, minWidth) => {
        if (status === 'max') {
            this.setState({
                defaultWidth: 100 + '%'
            })
        } else {
            this.setState({
                defaultWidth: minWidth
            })
        }

    }

    CloseIcon = (<span title="关闭" onClick={() => { }}>
        <CloseOutlined style={{ fontSize: '20px', margin: '-2px' }} />
    </span>)

    getRowDataChildrenStatusIsOkFunc = (child) => {
        console.log(child)
    }
    getCurrentTimeIsOneDay(currentTime, requireComplateDate) {
        const currentTimeObj = {
            year: currentTime.getFullYear(),
            month: currentTime.getMonth() + 1,
            day: currentTime.getDate()
        }
        const requireComplateDateObj = {
            year: requireComplateDate.getFullYear(),
            month: requireComplateDate.getMonth() + 1,
            day: requireComplateDate.getDate()
        }

        if (requireComplateDateObj.year < currentTimeObj.year) {
            return 'red'
        } else if (requireComplateDateObj.year === currentTimeObj.year) {
            if (requireComplateDateObj.month < currentTimeObj.month) {
                return 'red'
            } else if (requireComplateDateObj.month === currentTimeObj.month) {
                if (requireComplateDateObj.day === currentTimeObj.day) {
                    return 'bule'
                } else if (requireComplateDateObj.day < currentTimeObj.day) {
                    return 'red'
                } else {
                    return 'common'
                }
            } else {
                return 'common'
            }
        } else {
            return 'common'
        }
    }

    render() {
        const { isModalVisible, taskManageId, visible, defaultWidth, selectedRowsData, taskName, beginDate, requireComplateDate } = this.state
        return (
            <div>
                <QnnForm
                    wrappedComponentRef={(me) => {
                        this.formOne = me;
                    }}
                    formItemLayout={{
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 12 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 12 }
                        }
                    }}
                    formConfig={
                        [
                            {
                                type: 'string',
                                label: '任务名称',
                                field: 'taskName', //唯一的字段名 ***必传
                                placeholder: '请选择',
                                required: false,
                                span: 6
                            },
                            {
                                type: 'date',
                                label: '开始日期',
                                field: 'beginDate', //唯一的字段名 ***必传
                                placeholder: '请选择',
                                required: false,
                                format: "YYYY-MM-DD",
                                showTime: false, //不显示时间
                                scope: false, //是否可选择范围
                                span: 6
                            },
                            {
                                type: 'date',
                                label: '要求完成日期',
                                field: 'requireComplateDate', //唯一的字段名 ***必传
                                placeholder: '请选择',
                                required: false,
                                format: "YYYY-MM-DD",
                                showTime: false, //不显示时间
                                scope: false, //是否可选择范围
                                span: 6
                            },
                            {
                                type: 'component',
                                field: 'diy',
                                span: 6,
                                //第一种，推荐定义方式 需要将componentsKey对象传到qnn-form
                                Component: "myDiyComponent",

                                //第二种自定义组件方式
                                Component: obj => {
                                    return (
                                        <div style={{ height: '100%', display: 'flex', alignItems: 'center' }}>
                                            <Button type="primary" onClick={() => {
                                                this.table.clearSelectedRows();
                                                this.formOne.getValues().then(val => {
                                                    setTimeout(() => {
                                                        this.setState({
                                                            taskName: val.taskName, beginDate: val.beginDate, requireComplateDate: val.requireComplateDate
                                                        })
                                                        // this.table.refresh()
                                                    })
                                                    // new common
                                                    // if (!this.table.getTableData().length) {
                                                    //     Msg.warning('没有符合条件的数据！')
                                                    // }

                                                    if (!this.table.state.data.length) {
                                                        Msg.warning('没有符合条件的数据！')
                                                    }
                                                })
                                            }}>查询</Button>
                                        </div>
                                    );
                                }
                            }
                        ]
                    }
                ></QnnForm>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    antd={
                        {
                            rowKey: 'zjLzehTempTaskManageId',
                            size: 'small'
                        }
                    }
                    actionBtns={[
                        {
                            field: 'commitTask',
                            name: 'commitTask',//内置add del
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '完成任务提交',
                            drawerTitle: '完成任务提交',
                            disabled: 'bind:taskCommitDisabled',
                            onClick: (obj) => {
                                this.setState({
                                    visible: true,
                                    selectedRowsData: obj.selectedRows[0]
                                })
                            }
                        },
                        {
                            field: 'addChildTask',
                            name: 'add',//内置add del
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '创建子任务',
                            disabled: 'bind:createClildrenTaskDisabled',
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    onClick: (obj) => {
                                        this.table.clearSelectedRows();
                                    },
                                    fetchConfig: {
                                        apiName: 'addZjLzehTempTaskManage',
                                        otherParams: 'bind:getParentIdFunc',
                                    }
                                }
                            ]
                        },
                        {
                            field: 'reutrnTask',
                            name: 'returnTask',//内置add del
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '退回',
                            disabled: 'bind:returnStatusDisabled',
                            onClick: (obj) => {
                                confirm({
                                    icon: <ExclamationCircleOutlined />,
                                    content: '确定退回吗？',
                                    onOk: () => {
                                        this.props.myFetch('updateZjLzehTempTaskManage', { ...obj.selectedRows[0], status: '1' }).then(({ success, message, data }) => {
                                            if (success) {
                                                obj.btnCallbackFn.refresh()
                                                this.table.clearSelectedRows();
                                                Msg.success('退回成功!')
                                            } else {
                                                Msg.error(message)
                                            }
                                        })
                                    },
                                });
                            }
                        },
                        {
                            field: 'edit',
                            name: 'edit',//内置add del
                            icon: 'edit',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '修改',
                            disabled: 'bind:isChildrenTaskNoCommitDistributionConfirmFunc',
                            onClick: (obj) => {
                                this.table.clearSelectedRows();

                            },
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    fetchConfig: {
                                        apiName: 'updateZjLzehTempTaskManage'
                                    }
                                }
                            ]
                        },
                        {
                            field: 'dlel',
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            disabled: 'bind:isChildrenTaskNoCommitDistributionConfirmFunc',
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZjLzehTempTaskManage',
                            }
                        },
                        {
                            field: 'taskAllocation',
                            name: 'taskAllocation',//内置add del
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '任务分配',
                            disabled: 'bind:taskAllocationDisabled',
                            onClick: (obj) => {
                                confirm({
                                    icon: <ExclamationCircleOutlined />,
                                    content: '确定任务分配吗？',
                                    onOk: () => {
                                        this.props.myFetch('updateZjLzehTempTaskManage', { ...obj.selectedRows[0], status: '1' }).then(({ success, message, data }) => {
                                            if (success) {
                                                obj.btnCallbackFn.refresh()
                                                this.table.clearSelectedRows();
                                                Msg.success('任务分配成功!')
                                            } else {
                                                Msg.error(message)
                                            }
                                        })
                                    },
                                });
                            }
                        },
                        {
                            field: 'TaskCompletionConfirmation',
                            name: 'TaskCompletionConfirmation',//内置add del
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '任务完成确认',
                            disabled: 'bind:submittedDisabled',
                            onClick: (obj) => {
                                confirm({
                                    icon: <ExclamationCircleOutlined />,
                                    content: '确定任务完成吗？',
                                    onOk: () => {
                                        this.props.myFetch('updateZjLzehTempTaskManage', { ...obj.selectedRows[0], status: '4' }).then(({ success, message, data }) => {
                                            if (success) {
                                                obj.btnCallbackFn.refresh()
                                                this.table.clearSelectedRows();
                                                Msg.success('任务完成确认成功!')
                                            } else {
                                                Msg.error(message)
                                            }
                                        })
                                    },
                                });
                            }
                        },
                    ]}
                    rowSelection={{
                        checkStrictly:true
                    }}
                    isShowRowSelect={true}
                    fetchConfig={
                        {
                            apiName: 'getZjLzehTempTaskManageTreeList',
                            otherParams: {
                                implementPerson: this.props.loginAndLogoutInfo.loginInfo.userInfo.realName,
                                sort: 1,
                                taskName, beginDate, requireComplateDate
                                // implementPersonId: '9999999999'
                            },
                            success: (res) => {
                                const { data } = res
                                if (!data.length) {
                                    Msg.warning('没有符合条件的数据！')
                                }
                            }
                        }
                    }
                    method={{
                        taskCommitDisabled: (obj) => {
                            const rowData = obj.btnCallbackFn.getSelectedRows();
                            const currentName = this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                            const implementPerson = rowData[0]?.implementPerson

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

                            if (rowData.length === 1 && rowData[0].status === '1' && NodeTree.isCommitOk && currentName === implementPerson) {
                                return false;
                            } else {
                                return true
                            }
                        },
                        submittedDisabled: (obj) => {
                            let rowData = obj.btnCallbackFn.getSelectedRows();
                            const rowDataName = rowData[0]?.allotPerson
                            const currentName = this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
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

                            if (rowData.length === 1 && rowData[0].status === '3' && rowDataName === currentName && NodeTree.isCommitOk) {
                                return false;
                            } else {
                                return true
                            }
                        },
                        createClildrenTaskDisabled: (obj) => {
                            const rowData = obj.btnCallbackFn.getSelectedRows();
                            const rowDataName = rowData[0]?.allotPerson
                            const currentName = this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                            if (rowData.length === 1) {
                                let status = null
                                // 如果 是子任务的话 只有在未分配状态下 才能创建新的子任务
                                if (rowData[0].parentId && rowData[0].status === '0') {
                                    if (rowDataName === currentName) {
                                        status = true
                                    } else {
                                        status = false;
                                    }
                                } else if (!rowData[0].parentId && rowData[0].status === '1') {
                                    // 如果是 主任务的话 已分配状态也可以的 创建子任务
                                    status = false;
                                } else if (rowData[0].parentId && rowData[0].implementPerson === currentName && rowData[0].status === '1') {
                                    status = false;
                                } else {
                                    status = true
                                }
                                return status
                            } else {
                                return true
                            }
                        },
                        taskAllocationDisabled: (obj) => {
                            let rowData = obj.btnCallbackFn.getSelectedRows();
                            if (rowData.length === 1 && (rowData[0].status === '0')) {
                                return false;
                            } else {
                                return true
                            }
                        },
                        isChildrenTaskNoCommitDistributionConfirmFunc: (obj) => {
                            let rowData = obj.btnCallbackFn.getSelectedRows();
                            // && rowData[0].parentId
                            if (rowData.length === 1 && rowData[0].status === '0') {
                                return false;
                            } else {
                                return true
                            }
                        },
                        returnStatusDisabled: (obj) => {
                            const rowData = obj.btnCallbackFn.getSelectedRows();
                            const NodeTree = new NodeTreeFunc(rowData)
                            const currentName = this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                            let allotPerson = ''
                            if (rowData.length) {
                                allotPerson = rowData[0].allotPerson
                            } else {
                                allotPerson = ''
                            }
                            // console.log(rowData)
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

                            // 当前选中的条数 只能有一条 且 状态必须是已提交 且 分配人必须是当前登录人 才能点击退回
                            if (rowData.length === 1 && rowData[0].status === '3' && NodeTree.isCommitOk && currentName === allotPerson) {
                                return false;
                            } else {
                                return true
                            }
                        },
                        getParentIdFunc: (obj) => {
                            return { parentId: obj.btnCallbackFn.getSelectedRows()[0].zjLzehTempTaskManageId }
                        },
                    }}

                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zjLzehTempTaskManageId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'parentId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '任务名称',
                                dataIndex: 'taskName',
                                key: 'taskName',
                                width: 150,
                                onClick: 'detail',
                                render: (val, rowData) => {
                                    const curDateTime = new Date()
                                    const requireComplateDate = rowData.requireComplateDate
                                    switch (this.getCurrentTimeIsOneDay(curDateTime, new Date(requireComplateDate))) {
                                        case 'red':
                                            return <div style={{ color: '#f81d22' }}>{val}</div>
                                        case 'bule':
                                            return <div style={{ color: '#1890ff' }}>{val}</div>
                                        case 'common':
                                            return <div style={{ color: '#000000D9' }}>{val}</div>
                                    }
                                }
                            },
                            form: {
                                field: 'taskName',
                                type: 'string',
                                required: true,
                                editDisabled: true, //修改禁用
                            }
                        },
                        {
                            table: {
                                title: '任务描述',
                                dataIndex: 'taskDescribe',
                                key: 'taskDescribe',
                                width: 150,
                                render: (val, rowData) => {
                                    const curDateTime = new Date()
                                    const requireComplateDate = rowData.requireComplateDate
                                    switch (this.getCurrentTimeIsOneDay(curDateTime, new Date(requireComplateDate))) {
                                        case 'red':
                                            return <div style={{ color: '#f81d22' }}>{val}</div>
                                        case 'bule':
                                            return <div style={{ color: '#1890ff' }}>{val}</div>
                                        case 'common':
                                            return <div style={{ color: '#000000D9' }}>{val}</div>
                                    }
                                }
                            },
                            form: {
                                field: 'taskDescribe',
                                type: 'string',
                                required: true,
                                editDisabled: true, //修改禁用
                            }
                        },
                        {
                            table: {
                                title: '分配人名',
                                dataIndex: 'allotPerson',
                                key: 'allotPerson',
                                width: 150,
                                render: (val, rowData) => {
                                    const curDateTime = new Date()
                                    const requireComplateDate = rowData.requireComplateDate
                                    switch (this.getCurrentTimeIsOneDay(curDateTime, new Date(requireComplateDate))) {
                                        case 'red':
                                            return <div style={{ color: '#f81d22' }}>{val}</div>
                                        case 'bule':
                                            return <div style={{ color: '#1890ff' }}>{val}</div>
                                        case 'common':
                                            return <div style={{ color: '#000000D9' }}>{val}</div>
                                    }
                                }
                            },
                            form: {
                                field: 'allotPerson',
                                type: 'string',
                                addDisabled: true,//新增禁用
                                editDisabled: true, //修改禁用
                                initialValue: this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                            }
                        },
                        {
                            table: {
                                title: '开始日期',
                                dataIndex: 'beginDate',
                                key: 'beginDate',
                                width: 150,
                                render: (val, rowData) => {
                                    if (val) {
                                        const time = new Date(val);
                                        const y = time.getFullYear();
                                        const m = time.getMonth() + 1 < 10 ? '0' + (time.getMonth() + 1) : time.getMonth() + 1;
                                        const d = time.getDate() < 10 ? '0' + time.getDate() : time.getDate();
                                        const curDateTime = new Date()
                                        const requireComplateDate = rowData.requireComplateDate
                                        switch (this.getCurrentTimeIsOneDay(curDateTime, new Date(requireComplateDate))) {
                                            case 'red':
                                                return <div style={{ color: '#f81d22' }}>{`${y}-${m}-${d}`}</div>
                                            case 'bule':
                                                return <div style={{ color: '#1890ff' }}>{`${y}-${m}-${d}`}</div>
                                            case 'common':
                                                return <div style={{ color: '#000000D9' }}>{`${y}-${m}-${d}`}</div>
                                        }
                                    } else {
                                        return ''
                                    }
                                }
                            },
                            form: {
                                field: 'beginDate',
                                type: 'date',
                                required: true,
                                // disabled: (obj) => {
                                //     console.log(obj.tableFns.getSelectedRows()[0].parentId)
                                //     if (obj.record.parentId) {
                                //         return false
                                //     } else {
                                //         return true
                                //     }
                                // }, //修改禁用
                            }
                        },
                        {
                            table: {
                                title: '要求完成日期',
                                dataIndex: 'requireComplateDate',
                                key: 'requireComplateDate',
                                width: 150,
                                render: (val, rowData) => {

                                    const time = new Date(val);
                                    const y = time.getFullYear();
                                    const m = time.getMonth() + 1 < 10 ? '0' + (time.getMonth() + 1) : time.getMonth() + 1;
                                    const d = time.getDate() < 10 ? '0' + time.getDate() : time.getDate();
                                    const curDateTime = new Date()
                                    const requireComplateDate = rowData.requireComplateDate
                                    switch (this.getCurrentTimeIsOneDay(curDateTime, new Date(requireComplateDate))) {
                                        case 'red':
                                            return <div style={{ color: '#f81d22' }}>{`${y}-${m}-${d}`}</div>
                                        case 'bule':
                                            return <div style={{ color: '#1890ff' }}>{`${y}-${m}-${d}`}</div>
                                        case 'common':
                                            return <div style={{ color: '#000000D9' }}>{`${y}-${m}-${d}`}</div>
                                    }
                                }
                            },
                            form: {
                                field: 'requireComplateDate',
                                type: 'date',
                                required: true,
                                // disabled: (obj) => {
                                //     console.log(obj)
                                //     if (obj.record.parentId) {
                                //         return false
                                //     } else {
                                //         return true
                                //     }
                                // },
                            }
                        },
                        {
                            table: {
                                title: '分配对象',
                                dataIndex: 'launcherList',
                                key: 'launcherList',
                                width: 150,
                                // type: 'treeSelect',
                                render: (val, rowData) => {
                                    const curDateTime = new Date()
                                    const requireComplateDate = rowData.requireComplateDate
                                    switch (this.getCurrentTimeIsOneDay(curDateTime, new Date(requireComplateDate))) {
                                        case 'red':
                                            return <div style={{ color: '#f81d22' }}>{rowData.implementPerson}</div>
                                        case 'bule':
                                            return <div style={{ color: '#1890ff' }}>{rowData.implementPerson}</div>
                                        case 'common':
                                            return <div style={{ color: '#000000D9' }}>{rowData.implementPerson}</div>
                                    }
                                }
                            },
                            form: {
                                type: 'treeSelect',
                                required: true,
                                treeSelectOption: {
                                    help: true,
                                    fetchConfig: {
                                        apiName: 'getSysDepartmentUserAllTree',
                                    },
                                    search: false,
                                    maxNumber: '1',
                                    selectType: '2',

                                }
                            }
                        },
                        {
                            table: {
                                title: '实际完成日期',
                                dataIndex: 'realCompalteDate',
                                key: 'realCompalteDate',
                                width: 150,
                                render: (val, rowData) => {
                                    if (val) {
                                        const time = new Date(val);
                                        const y = time.getFullYear();
                                        const m = time.getMonth() + 1 < 10 ? '0' + (time.getMonth() + 1) : time.getMonth() + 1;
                                        const d = time.getDate() < 10 ? '0' + time.getDate() : time.getDate();
                                        const curDateTime = new Date()
                                        const requireComplateDate = rowData.requireComplateDate
                                        switch (this.getCurrentTimeIsOneDay(curDateTime, new Date(requireComplateDate))) {
                                            case 'red':
                                                return <div style={{ color: '#f81d22' }}>{`${y}-${m}-${d}`}</div>
                                            case 'bule':
                                                return <div style={{ color: '#1890ff' }}>{`${y}-${m}-${d}`}</div>
                                            case 'common':
                                                return <div style={{ color: '#000000D9' }}>{`${y}-${m}-${d}`}</div>
                                        }
                                    } else {
                                        return ''
                                    }
                                }
                            },
                            form: {
                                field: 'realCompalteDate',
                                type: 'date',
                                // hide: true
                                hide: (obj) => {
                                    if (obj.clickCb.rowInfo.name === 'detail' && (obj.clickCb.rowData.status === '3' || obj.clickCb.rowData.status === '4')) {
                                        return false
                                    } else {
                                        return true
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '附件',
                                dataIndex: 'fileList',
                                key: 'fileList',
                                width: 180,
                                render: (val, rowData) => {
                                    if (val.length) {
                                        return <SelectFilesDownLoad dataList={val} />
                                    } else {
                                        const curDateTime = new Date()
                                        const requireComplateDate = rowData.requireComplateDate
                                        switch (this.getCurrentTimeIsOneDay(curDateTime, new Date(requireComplateDate))) {
                                            case 'red':
                                                return <div style={{ color: '#f81d22' }}>{'无附件'}</div>
                                            case 'bule':
                                                return <div style={{ color: '#1890ff' }}>{'无附件'}</div>
                                            case 'common':
                                                return <div style={{ color: '#000000D9' }}>{'无附件'}</div>
                                        }
                                    }
                                }
                            },
                            form: {

                                label: '附件上传',
                                field: "fileList",
                                type: 'files',
                            }
                        },
                        {
                            table: {
                                title: '完成情况说明',
                                dataIndex: 'complateExplain',
                                key: 'complateExplain',
                                width: 150,
                                render: (val, rowData) => {
                                    const curDateTime = new Date()
                                    const requireComplateDate = rowData.requireComplateDate
                                    switch (this.getCurrentTimeIsOneDay(curDateTime, new Date(requireComplateDate))) {
                                        case 'red':
                                            return <div style={{ color: '#f81d22' }}>{val}</div>
                                        case 'bule':
                                            return <div style={{ color: '#1890ff' }}>{val}</div>
                                        case 'common':
                                            return <div style={{ color: '#000000D9' }}>{val}</div>
                                    }
                                }
                            },
                            form: {
                                field: 'complateExplain',
                                type: 'string',
                                // hide: true
                                hide: (obj) => {
                                    if (obj.clickCb.rowInfo.name === 'detail' && (obj.clickCb.rowData.status === '3' || obj.clickCb.rowData.status === '4')) {
                                        return false
                                    } else {
                                        return true
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '状态',
                                dataIndex: 'status',
                                key: 'status',
                                width: 150,
                                render: (val, rowData) => {
                                    let data = ''
                                    switch (val) {
                                        case '0':
                                            data = '未分配'
                                            break
                                        case '1':
                                            data = '已分配'
                                            break
                                        case '2':
                                            data = '收回'
                                            break
                                        case '3':
                                            data = '已提交 '
                                            break
                                        case '4':
                                            data = '已确认 '
                                            break
                                    }
                                    const curDateTime = new Date()
                                    const requireComplateDate = rowData.requireComplateDate
                                    switch (this.getCurrentTimeIsOneDay(curDateTime, new Date(requireComplateDate))) {
                                        case 'red':
                                            return <div style={{ color: '#f81d22' }}>{data}</div>
                                        case 'bule':
                                            return <div style={{ color: '#1890ff' }}>{data}</div>
                                        case 'common':
                                            return <div style={{ color: '#000000D9' }}>{data}</div>
                                    }
                                }
                            },
                            form: {
                                field: 'status',
                                type: 'select',
                                optionData: [
                                    {
                                        label: "未分配",
                                        value: '0'
                                    },
                                    {
                                        label: "已分配",
                                        value: '1'
                                    },
                                    {
                                        label: "收回",
                                        value: '2'
                                    },
                                    {
                                        label: "已提交",
                                        value: '3'
                                    },
                                    {
                                        label: "已确认",
                                        value: '4'
                                    },
                                ],
                                initialValue: '0',
                                hide: true
                                // hide: (obj) => {
                                //     if(obj.clickCb.rowInfo.field==='addChildTask'){
                                //         return true
                                //     }else{
                                //         return false
                                //     }
                                // }
                            }
                        },
                        {
                            table: {
                                title: '沟通交流',
                                dataIndex: 'gt',
                                key: 'gt',
                                width: 150,
                                render: () => {
                                    return <span style={{ cursor: 'pointer ', color: '#1890ff' }} >{'>>>'}</span>
                                },
                                onClick: (obj) => {
                                    setTimeout(() => {
                                        this.setState({
                                            taskManageId: obj.rowData.zjLzehTempTaskManageId
                                        })
                                        this.showModal()
                                    }, 0)
                                }
                            },
                            isInForm: false
                        },
                    ]}
                ></QnnTable>
                <Modal title="沟通交流" width={'70%'} visible={isModalVisible} onOk={this.handleOk} onCancel={this.handleCancel} footer={null}>
                    {isModalVisible ? <Comment currentLogin={this.props.loginAndLogoutInfo.loginInfo.userInfo} propVal={this.props} taskManageId={taskManageId} /> : null}
                </Modal>
                <Drawer
                    title={<Maximize props={this} title={'完成任务提交'} minWidth={'70%'} />}
                    placement="right"
                    width={defaultWidth}
                    closeIcon={this.CloseIcon}
                    onClose={this.onClose}
                    visible={visible}
                    destroyOnClose={true}
                    footer={
                        <div
                            style={{
                                textAlign: 'right',
                            }}
                        >
                            <Button onClick={this.onClose} style={{ marginRight: 8 }}>
                                取消
                          </Button>
                            <Button onClick={this.onCommitTaskFunc} type="primary">
                                保存
                          </Button>
                        </div>
                    }
                >
                    <QnnForm
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => {
                            this.table3 = me;
                        }}
                        antd={
                            {
                                rowKey: 'zjLzehTempTaskManageId', //zjLzehTempTaskManageId
                                size: 'small'
                            }
                        }
                        // fetchConfig={
                        //     {
                        //         apiName: 'getZjLzehTempTaskManageTreeList',
                        //         otherParams: {
                        //             implementPerson: this.props.loginAndLogoutInfo.loginInfo.userInfo.userId
                        //             // implementPersonId: '9999999999'
                        //         }
                        //     }
                        // }
                        initialValues={selectedRowsData}
                        formConfig={[
                            {
                                field: 'zjLzehTempTaskManageId',
                                type: 'string',
                                hide: true,
                            },
                            {
                                field: 'allotPersonId',
                                type: 'string',
                                hide: true,
                            },
                            {
                                field: 'parentId',
                                type: 'string',
                                hide: true,
                            },
                            {
                                label: '任务名称',
                                field: 'taskName',
                                type: 'string',
                                disabled: true,
                            },
                            {
                                label: '任务描述',
                                field: 'taskDescribe',
                                type: 'string',
                                disabled: true,
                            },
                            {
                                label: '分配人名',
                                field: 'allotPerson',
                                type: 'string',
                                disabled: true,
                                initialValue: this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                                // initialValue: this.props.loginAndLogoutInfo.loginInfo.userInfo.userId
                            },
                            {
                                label: '开始日期',
                                field: 'beginDate',
                                type: 'date',
                                disabled: true,
                            },
                            {
                                label: '要求完成日期',
                                field: 'requireComplateDate',
                                type: 'date',
                                disabled: true,
                            },
                            {
                                label: '分配对象',
                                type: 'treeSelect',
                                field: 'launcherList',
                                disabled: true,
                                treeSelectOption: {
                                    help: true,
                                    fetchConfig: {
                                        apiName: 'getSysDepartmentUserAllTree',
                                    },
                                    search: true,
                                    maxNumber: '1',
                                    selectType: '2',
                                }
                            },
                            {
                                label: '实际完成日期',
                                field: 'realCompalteDate',
                                type: 'date',
                                required: true,
                            },
                            {
                                label: '附件上传',
                                field: "fileList",
                                type: 'files',
                            },
                            {
                                label: '完成情况说明',
                                field: 'complateExplain',
                                type: 'string',
                            },
                            {
                                label: '状态',
                                field: 'status',
                                type: 'select',
                                disabled: true,
                                optionData: [
                                    {
                                        label: "未分配",
                                        value: '0'
                                    },
                                    {
                                        label: "已分配",
                                        value: '1'
                                    },
                                    {
                                        label: "收回",
                                        value: '2'
                                    },
                                    {
                                        label: "已提交",
                                        value: '3'
                                    },
                                    {
                                        label: "已确认",
                                        value: '4'
                                    },
                                ],
                                initialValue: '0',

                            },
                        ]}
                    ></QnnForm>
                </Drawer>
            </div>
        )
    }
}
export default index;