import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { Tabs, Tag, message as Msg, Tooltip, Modal } from 'antd';
import FlowFormByzjSjTechnicalServiceAdd from './formAdd';
import FlowFormByzjSjTechnicalServiceEdit from './formEdit';
import Operation from './operation';
const { TabPane } = Tabs;
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.serviceId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    // searchBtnsStyle:'inline'
}
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            key: '1'
        }
    }
    callback = (key) => {
        this.setState({ key });
        if (key === '1') {
            if (this.table1) {
                this.table1.refresh();
            }
        } else if (key === '2') {
            if (this.table2) {
                this.table2.refresh();
            }
        }
    }
    render() {
        const { key } = this.state;
        const { ext1, realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        return (
            <div>
                <Tabs activeKey={key} onChange={this.callback}>
                    <TabPane tab={'我发出的'} key={'1'}>
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table1 = me;
                            }}
                            actionBtns={{
                                apiName: "getSysMenuBtn",
                                otherParams: function (obj) {
                                    var props = obj.Pprops;
                                    let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                                    return {
                                        menuParentId: curRouteData._value,
                                        tableField: "TechnicalServices"
                                    }
                                }
                            }}
                            fetchConfig={{
                                apiName: 'pcGetZjSjConsultTechnicalServiceFlowList',
                                otherParams: {
                                    selectType: '1',
                                    ext2: ext1
                                }
                            }}
                            {...config}
                            tabs={[
                                {
                                    field: "diy1",
                                    name: "diy1",
                                    title: "基础信息",
                                    content: props => {
                                        let _props = {
                                            btnCallbackFn: props.btnCallbackFn,
                                            isInQnnTable: props.isInQnnTable,
                                            tableFns: props.tableFns,
                                            initialValues: props.initialValues
                                        };
                                        if (props.clickCb.rowInfo.name === 'detail') {
                                            return <FlowFormByzjSjTechnicalServiceEdit {...this.props} {..._props} noButton={true} />;
                                        } else if (props.clickCb.rowInfo.name === 'adddiy') {
                                            return <FlowFormByzjSjTechnicalServiceAdd {...this.props} {..._props} />;
                                        } else if (props.clickCb.rowInfo.name === 'edit') {
                                            return <FlowFormByzjSjTechnicalServiceEdit {...this.props} {..._props} />;
                                        }
                                    }
                                },
                                {
                                    field: "diy2",
                                    name: "diy2",
                                    title: "操作历史",
                                    disabled: (obj) => {
                                        if (obj.clickCb.rowInfo.name === 'adddiy') {
                                            return true;
                                        } else if (obj.initialValues && !obj.initialValues.workId) {
                                            return true;
                                        } {
                                            return false;
                                        }
                                    },
                                    content: props => {
                                        return <Operation apiName={'openFlow'} {...props} />;
                                    }
                                }
                            ]}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'serviceId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: '申请人',
                                        dataIndex: 'userName',
                                        key: 'userName',
                                        width: 100,
                                        fixed: 'left'
                                    },
                                    form: {
                                        type: "string",
                                        placeholder: "请输入"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: '申请单位',
                                        dataIndex: 'deptName',
                                        key: 'deptName',
                                        width: 200,
                                        render: (data) => {
                                            if (data && data.indexOf(',') !== -1) {
                                                data = data.split(',').join('→');
                                            }
                                            return (
                                                <Tooltip title={data}>
                                                    <span>{data}</span>
                                                </Tooltip>
                                            );
                                        }
                                    },
                                    form: {
                                        type: "string",
                                        placeholder: "请输入"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: '服务类型',
                                        dataIndex: 'questionTypeName',
                                        key: 'questionTypeName',
                                        width: 150,
                                        tooltip: 15,
                                    },
                                    form: {
                                        field: 'questionTypeId',
                                        label: '服务类型',
                                        type: 'cascader',
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId',
                                            children: 'children'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeTree',
                                            otherParams: {
                                                itemId: 'wenTiLeiXing'
                                            }
                                        },
                                        placeholder: '请选择',
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: '服务标题',
                                        dataIndex: 'title',
                                        key: 'title',
                                        willExecute: (obj) => {
                                            obj.btnCallbackFn.setActiveKey('0');
                                        },
                                        onClick: 'detail',
                                        width: 150,
                                        tooltip: 15,
                                    },
                                    form: {
                                        type: "string",
                                        placeholder: "请输入"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: '服务内容',
                                        dataIndex: 'content',
                                        key: 'content',
                                        width: 150,
                                        tooltip: 15,
                                    },
                                    form: {
                                        type: "string",
                                        placeholder: "请输入"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: '发布时间',
                                        dataIndex: 'sendTime',
                                        key: 'sendTime',
                                        width: 150,
                                        format: 'YYYY-MM-DD HH:mm:ss'
                                    },
                                    form: {
                                        type: "date",
                                        format: 'YYYY-MM-DD HH:mm:ss',
                                        placeholder: "请选择"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: '解决状态',
                                        dataIndex: 'solveFlag',
                                        key: 'solveFlag',
                                        width: 100,
                                        fixed: 'right',
                                        render: (data) => {
                                            let color = '';
                                            let content = '';
                                            if (data === '2') {
                                                color = 'green';
                                                content = '已解决';
                                            } else if (data === '1') {
                                                color = 'grey';
                                                content = '解决中';
                                            } else if (data === '0') {
                                                color = 'red';
                                                content = '未解决';
                                            }
                                            return (
                                                <Tag style={{ width: '100%', textAlign: 'center' }} color={color}>
                                                    {content}
                                                </Tag>
                                            )
                                        }
                                    },
                                    form: {
                                        type: "select",
                                        placeholder: "请选择",
                                        optionData: [
                                            //默认选项数据
                                            {
                                                label: "未解决",
                                                value: "0"
                                            },
                                            {
                                                label: "解决中",
                                                value: "1"
                                            },
                                            {
                                                label: "已解决",
                                                value: "2"
                                            }
                                        ]
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: '解决时间',
                                        dataIndex: 'solveTime',
                                        key: 'solveTime',
                                        width: 120,
                                        format: 'YYYY-MM-DD',
                                        fixed: 'right',
                                    },
                                    form: {
                                        type: "date",
                                        placeholder: "请选择"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: '流程状态',
                                        dataIndex: 'apih5FlowStatus',
                                        key: 'apih5FlowStatus',
                                        width: 100,
                                        fixed: 'right',
                                        render: (data) => {
                                            if (data === '0') {
                                                return '已发起';
                                            } else if (data === '1') {
                                                return '审核中';
                                            } else if (data === '2') {
                                                return '审核结束';
                                            } else if (data === '3') {
                                                return '退回';
                                            } else if (data === '4') {
                                                return '未发起';
                                            }
                                        }
                                    },
                                    form: {
                                        type: "select",
                                        placeholder: "请选择",
                                        optionData: [
                                            {
                                                label: "未发起",
                                                value: "4"
                                            },
                                            {
                                                label: "已发起",
                                                value: "0"
                                            },
                                            {
                                                label: "审核中",
                                                value: "1"
                                            },
                                            {
                                                label: "审核结束",
                                                value: "2"
                                            },
                                            {
                                                label: "退回",
                                                value: "3"
                                            }
                                        ]
                                    }
                                },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: "操作",
                                //         dataIndex: 'action',
                                //         key: 'action',
                                //         align: "center",
                                //         width: 80,
                                //         fixed: 'right',
                                //         showType: "tile",
                                //         btns: [
                                //             {
                                //                 name: 'edit',
                                //                 render: function (obj) {
                                //                     if (obj.rowData.apih5FlowStatus === '4') {
                                //                         return '<a>修改</a>';
                                //                     } else {
                                //                         return '';
                                //                     }
                                //                 },
                                //                 formBtns: []
                                //             }
                                //         ]
                                //     }
                                // }
                            ]}
                            method={{
                                adddiyOclick: (obj) => {
                                    obj.btnCallbackFn.setActiveKey('0');
                                    obj.btnCallbackFn.closeDrawer(true);
                                },
                                editOclick: (obj) => {
                                    console.log(obj);
                                    if(obj.selectedRows[0].apih5FlowStatus !== '4'){
                                        Msg.error('已发起的数据不可修改!');
                                        obj.btnCallbackFn.closeDrawer(false);
                                    }
                                    obj.btnCallbackFn.setActiveKey('0');
                                    obj.btnCallbackFn.clearSelectedRows();
                                },
                                buttonHide: () => {
                                    return key === '1' ? false : true;
                                },
                                delDisabled: (obj) => {
                                    if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                },
                                delOclick: (obj) => {
                                    confirm({
                                        title: `您确定要删除数据么?`,
                                        content: `取消删除请点击取消按钮。`,
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            this.props.myFetch('batchDeleteUpdateZjSjConsultTechnicalServiceFlow', obj.selectedRows).then(({ success, message, data }) => {
                                                if (success) {
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                    obj.btnCallbackFn.refresh();
                                                } else {
                                                    Msg.error(message);
                                                }
                                            });
                                        },
                                    });
                                }
                            }}
                        />
                    </TabPane>
                    <TabPane tab={'全部'} key={'2'}>
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table2 = me;
                            }}
                            fetchConfig={{
                                apiName: 'pcGetZjSjConsultTechnicalServiceFlowList',
                                otherParams: {
                                    selectType: '0',
                                    ext2: ext1
                                }
                            }}
                            actionBtns={{
                                apiName: "getSysMenuBtn",
                                otherParams: function (obj) {
                                    var props = obj.Pprops;
                                    let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                                    return {
                                        menuParentId: curRouteData._value,
                                        tableField: "TechnicalServices"
                                    }
                                }
                            }}
                            {...config}
                            tabs={[
                                {
                                    field: "diy1",
                                    name: "diy1",
                                    title: "基础信息",
                                    content: props => {
                                        let _props = {
                                            btnCallbackFn: props.btnCallbackFn,
                                            isInQnnTable: props.isInQnnTable,
                                            tableFns: props.tableFns,
                                            initialValues: props.initialValues
                                        };
                                        if (props.clickCb.rowInfo.name === 'detail') {
                                            return <FlowFormByzjSjTechnicalServiceEdit {...this.props} {..._props} noButton={true} />;
                                        } else if (props.clickCb.rowInfo.name === 'adddiy') {
                                            return <FlowFormByzjSjTechnicalServiceAdd {...this.props} {..._props} />;
                                        }
                                    }
                                },
                                {
                                    field: "diy2",
                                    name: "diy2",
                                    title: "操作历史",
                                    disabled: (obj) => {
                                        if (obj.clickCb.rowInfo.name === 'adddiy') {
                                            return true;
                                        } else if (obj.initialValues && !obj.initialValues.workId) {
                                            return true;
                                        } {
                                            return false;
                                        }
                                    },
                                    content: props => {
                                        return <Operation apiName={'openFlowByReport'} {...props} />;
                                    }
                                }
                            ]}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'serviceId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: '申请人',
                                        dataIndex: 'userName',
                                        key: 'userName',
                                        width: 100,
                                        fixed: 'left'
                                    },
                                    form: {
                                        type: "string",
                                        placeholder: "请输入"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: '申请单位',
                                        dataIndex: 'deptName',
                                        key: 'deptName',
                                        width: 200,
                                        render: (data) => {
                                            if (data && data.indexOf(',') !== -1) {
                                                data = data.split(',').join('→');
                                            }
                                            return (
                                                <Tooltip title={data}>
                                                    <span>{data}</span>
                                                </Tooltip>
                                            );
                                        }
                                    },
                                    form: {
                                        type: "string",
                                        placeholder: "请输入"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: '服务类型',
                                        dataIndex: 'questionTypeName',
                                        key: 'questionTypeName',
                                        width: 150,
                                        tooltip: 15,
                                    },
                                    form: {
                                        field: 'questionTypeId',
                                        label: '服务类型',
                                        type: 'cascader',
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId',
                                            children: 'children'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeTree',
                                            otherParams: {
                                                itemId: 'wenTiLeiXing'
                                            }
                                        },
                                        placeholder: '请选择',
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: '服务标题',
                                        dataIndex: 'title',
                                        key: 'title',
                                        willExecute: (obj) => {
                                            obj.btnCallbackFn.setActiveKey('0');
                                        },
                                        onClick: 'detail',
                                        width: 150,
                                        tooltip: 15,
                                    },
                                    form: {
                                        type: "string",
                                        placeholder: "请输入"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: '服务内容',
                                        dataIndex: 'content',
                                        key: 'content',
                                        width: 150,
                                        tooltip: 15,
                                    },
                                    form: {
                                        type: "string",
                                        placeholder: "请输入"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: '发布时间',
                                        dataIndex: 'sendTime',
                                        key: 'sendTime',
                                        width: 150,
                                        format: 'YYYY-MM-DD HH:mm:ss'
                                    },
                                    form: {
                                        type: "date",
                                        format: 'YYYY-MM-DD HH:mm:ss',
                                        placeholder: "请选择"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: '解决状态',
                                        dataIndex: 'solveFlag',
                                        key: 'solveFlag',
                                        width: 100,
                                        fixed: 'right',
                                        render: (data) => {
                                            let color = '';
                                            let content = '';
                                            if (data === '2') {
                                                color = 'green';
                                                content = '已解决';
                                            } else if (data === '1') {
                                                color = 'grey';
                                                content = '解决中';
                                            } else if (data === '0') {
                                                color = 'red';
                                                content = '未解决';
                                            }
                                            return (
                                                <Tag style={{ width: '100%', textAlign: 'center' }} color={color}>
                                                    {content}
                                                </Tag>
                                            )
                                        }
                                    },
                                    form: {
                                        type: "select",
                                        placeholder: "请选择",
                                        optionData: [
                                            //默认选项数据
                                            {
                                                label: "未解决",
                                                value: "0"
                                            },
                                            {
                                                label: "解决中",
                                                value: "1"
                                            },
                                            {
                                                label: "已解决",
                                                value: "2"
                                            }
                                        ]
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: '解决时间',
                                        dataIndex: 'solveTime',
                                        key: 'solveTime',
                                        width: 120,
                                        format: 'YYYY-MM-DD',
                                        fixed: 'right',
                                    },
                                    form: {
                                        type: "date",
                                        placeholder: "请选择"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: '流程状态',
                                        dataIndex: 'apih5FlowStatus',
                                        key: 'apih5FlowStatus',
                                        width: 100,
                                        fixed: 'right',
                                        render: (data) => {
                                            if (data === '0') {
                                                return '已发起';
                                            } else if (data === '1') {
                                                return '审核中';
                                            } else if (data === '2') {
                                                return '审核结束';
                                            } else if (data === '3') {
                                                return '退回';
                                            } else if (data === '4') {
                                                return '未发起';
                                            }
                                        }
                                    },
                                    form: {
                                        type: "select",
                                        placeholder: "请选择",
                                        optionData: [
                                            {
                                                label: "未发起",
                                                value: "4"
                                            },
                                            {
                                                label: "已发起",
                                                value: "0"
                                            },
                                            {
                                                label: "审核中",
                                                value: "1"
                                            },
                                            {
                                                label: "审核结束",
                                                value: "2"
                                            },
                                            {
                                                label: "退回",
                                                value: "3"
                                            }
                                        ]
                                    }
                                }
                            ]}
                            method={{
                                adddiyOclick: (obj) => {
                                    obj.btnCallbackFn.setActiveKey('0');
                                    obj.btnCallbackFn.closeDrawer(true);
                                },
                                editOclick: (obj) => {
                                    obj.btnCallbackFn.setActiveKey('0');
                                    obj.btnCallbackFn.clearSelectedRows();
                                },
                                buttonHide: () => {
                                    return key === '1' ? false : true;
                                },
                                delDisabled: (obj) => {
                                    if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                },
                                delOclick: (obj) => {
                                    this.props.myFetch('getSysRoleUserList', { roleId: "1EBD38FT8001BE330F0A000014967257" }).then(({ success, message, data }) => {
                                        if (success) {
                                            if (data.filter(item => item.label === realName).length) {
                                                confirm({
                                                    title: `您确定要删除数据么?`,
                                                    content: `取消删除请点击取消按钮。`,
                                                    okText: "确认",
                                                    cancelText: "取消",
                                                    onOk: () => {
                                                        this.props.myFetch('batchDeleteUpdateZjSjConsultTechnicalServiceFlow', obj.selectedRows).then((delObj) => {
                                                            if (delObj.success) {
                                                                obj.btnCallbackFn.clearSelectedRows();
                                                                obj.btnCallbackFn.refresh();
                                                            } else {
                                                                Msg.error(delObj.message);
                                                            }
                                                        });
                                                    },
                                                });
                                            } else {
                                                Msg.error('无权限删除别人发起的数据！');
                                            }
                                        } else {
                                            Msg.error(message);
                                        }
                                    });
                                }
                            }}
                        />
                    </TabPane>
                </Tabs>
            </div>
        );
    }
}

export default index;