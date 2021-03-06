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
                    <TabPane tab={'ĉċċşç'} key={'1'}>
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
                                    title: "ċşçĦäżĦĉŻ",
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
                                    title: "ĉä½ċċ²",
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
                                        title: 'ç³èŻ·äşş',
                                        dataIndex: 'userName',
                                        key: 'userName',
                                        width: 100,
                                        fixed: 'left'
                                    },
                                    form: {
                                        type: "string",
                                        placeholder: "èŻ·è?ċ?"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: 'ç³èŻ·ċä½',
                                        dataIndex: 'deptName',
                                        key: 'deptName',
                                        width: 200,
                                        render: (data) => {
                                            if (data && data.indexOf(',') !== -1) {
                                                data = data.split(',').join('â');
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
                                        placeholder: "èŻ·è?ċ?"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: 'ĉċĦçħğċ',
                                        dataIndex: 'questionTypeName',
                                        key: 'questionTypeName',
                                        width: 150,
                                        tooltip: 15,
                                    },
                                    form: {
                                        field: 'questionTypeId',
                                        label: 'ĉċĦçħğċ',
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
                                        placeholder: 'èŻ·éĉİ',
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: 'ĉċĦĉ é˘',
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
                                        placeholder: "èŻ·è?ċ?"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: 'ĉċĦċċ?ı',
                                        dataIndex: 'content',
                                        key: 'content',
                                        width: 150,
                                        tooltip: 15,
                                    },
                                    form: {
                                        type: "string",
                                        placeholder: "èŻ·è?ċ?"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: 'ċċ¸ĉĥé´',
                                        dataIndex: 'sendTime',
                                        key: 'sendTime',
                                        width: 150,
                                        format: 'YYYY-MM-DD HH:mm:ss'
                                    },
                                    form: {
                                        type: "date",
                                        format: 'YYYY-MM-DD HH:mm:ss',
                                        placeholder: "èŻ·éĉİ"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: 'è§£ċ³çĥĉ',
                                        dataIndex: 'solveFlag',
                                        key: 'solveFlag',
                                        width: 100,
                                        fixed: 'right',
                                        render: (data) => {
                                            let color = '';
                                            let content = '';
                                            if (data === '2') {
                                                color = 'green';
                                                content = 'ċ·²è§£ċ³';
                                            } else if (data === '1') {
                                                color = 'grey';
                                                content = 'è§£ċ³ä¸­';
                                            } else if (data === '0') {
                                                color = 'red';
                                                content = 'ĉŞè§£ċ³';
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
                                        placeholder: "èŻ·éĉİ",
                                        optionData: [
                                            //éğè?¤ééĦıĉ°ĉ?
                                            {
                                                label: "ĉŞè§£ċ³",
                                                value: "0"
                                            },
                                            {
                                                label: "è§£ċ³ä¸­",
                                                value: "1"
                                            },
                                            {
                                                label: "ċ·²è§£ċ³",
                                                value: "2"
                                            }
                                        ]
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: 'è§£ċ³ĉĥé´',
                                        dataIndex: 'solveTime',
                                        key: 'solveTime',
                                        width: 120,
                                        format: 'YYYY-MM-DD',
                                        fixed: 'right',
                                    },
                                    form: {
                                        type: "date",
                                        placeholder: "èŻ·éĉİ"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: 'ĉµç¨çĥĉ',
                                        dataIndex: 'apih5FlowStatus',
                                        key: 'apih5FlowStatus',
                                        width: 100,
                                        fixed: 'right',
                                        render: (data) => {
                                            if (data === '0') {
                                                return 'ċ·²ċèµ·';
                                            } else if (data === '1') {
                                                return 'ċ?Ħĉ ¸ä¸­';
                                            } else if (data === '2') {
                                                return 'ċ?Ħĉ ¸çğĉ';
                                            } else if (data === '3') {
                                                return 'éċ';
                                            } else if (data === '4') {
                                                return 'ĉŞċèµ·';
                                            }
                                        }
                                    },
                                    form: {
                                        type: "select",
                                        placeholder: "èŻ·éĉİ",
                                        optionData: [
                                            {
                                                label: "ĉŞċèµ·",
                                                value: "4"
                                            },
                                            {
                                                label: "ċ·²ċèµ·",
                                                value: "0"
                                            },
                                            {
                                                label: "ċ?Ħĉ ¸ä¸­",
                                                value: "1"
                                            },
                                            {
                                                label: "ċ?Ħĉ ¸çğĉ",
                                                value: "2"
                                            },
                                            {
                                                label: "éċ",
                                                value: "3"
                                            }
                                        ]
                                    }
                                },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: "ĉä½",
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
                                //                         return '<a>äż?ĉı</a>';
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
                                        Msg.error('ċ·²ċèµ·çĉ°ĉ?ä¸ċŻäż?ĉı!');
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
                                        title: `ĉ¨çĦ?ċ?èĤċ é¤ĉ°ĉ?äı?`,
                                        content: `ċĉĥċ é¤èŻ·çıċğċĉĥĉé??`,
                                        okText: "çĦ?è?¤",
                                        cancelText: "ċĉĥ",
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
                    <TabPane tab={'ċ¨é¨'} key={'2'}>
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
                                    title: "ċşçĦäżĦĉŻ",
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
                                    title: "ĉä½ċċ²",
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
                                        title: 'ç³èŻ·äşş',
                                        dataIndex: 'userName',
                                        key: 'userName',
                                        width: 100,
                                        fixed: 'left'
                                    },
                                    form: {
                                        type: "string",
                                        placeholder: "èŻ·è?ċ?"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: 'ç³èŻ·ċä½',
                                        dataIndex: 'deptName',
                                        key: 'deptName',
                                        width: 200,
                                        render: (data) => {
                                            if (data && data.indexOf(',') !== -1) {
                                                data = data.split(',').join('â');
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
                                        placeholder: "èŻ·è?ċ?"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: 'ĉċĦçħğċ',
                                        dataIndex: 'questionTypeName',
                                        key: 'questionTypeName',
                                        width: 150,
                                        tooltip: 15,
                                    },
                                    form: {
                                        field: 'questionTypeId',
                                        label: 'ĉċĦçħğċ',
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
                                        placeholder: 'èŻ·éĉİ',
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: 'ĉċĦĉ é˘',
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
                                        placeholder: "èŻ·è?ċ?"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: 'ĉċĦċċ?ı',
                                        dataIndex: 'content',
                                        key: 'content',
                                        width: 150,
                                        tooltip: 15,
                                    },
                                    form: {
                                        type: "string",
                                        placeholder: "èŻ·è?ċ?"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: 'ċċ¸ĉĥé´',
                                        dataIndex: 'sendTime',
                                        key: 'sendTime',
                                        width: 150,
                                        format: 'YYYY-MM-DD HH:mm:ss'
                                    },
                                    form: {
                                        type: "date",
                                        format: 'YYYY-MM-DD HH:mm:ss',
                                        placeholder: "èŻ·éĉİ"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: 'è§£ċ³çĥĉ',
                                        dataIndex: 'solveFlag',
                                        key: 'solveFlag',
                                        width: 100,
                                        fixed: 'right',
                                        render: (data) => {
                                            let color = '';
                                            let content = '';
                                            if (data === '2') {
                                                color = 'green';
                                                content = 'ċ·²è§£ċ³';
                                            } else if (data === '1') {
                                                color = 'grey';
                                                content = 'è§£ċ³ä¸­';
                                            } else if (data === '0') {
                                                color = 'red';
                                                content = 'ĉŞè§£ċ³';
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
                                        placeholder: "èŻ·éĉİ",
                                        optionData: [
                                            //éğè?¤ééĦıĉ°ĉ?
                                            {
                                                label: "ĉŞè§£ċ³",
                                                value: "0"
                                            },
                                            {
                                                label: "è§£ċ³ä¸­",
                                                value: "1"
                                            },
                                            {
                                                label: "ċ·²è§£ċ³",
                                                value: "2"
                                            }
                                        ]
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: 'è§£ċ³ĉĥé´',
                                        dataIndex: 'solveTime',
                                        key: 'solveTime',
                                        width: 120,
                                        format: 'YYYY-MM-DD',
                                        fixed: 'right',
                                    },
                                    form: {
                                        type: "date",
                                        placeholder: "èŻ·éĉİ"
                                    }
                                },
                                {
                                    isInSearch: true,
                                    table: {
                                        title: 'ĉµç¨çĥĉ',
                                        dataIndex: 'apih5FlowStatus',
                                        key: 'apih5FlowStatus',
                                        width: 100,
                                        fixed: 'right',
                                        render: (data) => {
                                            if (data === '0') {
                                                return 'ċ·²ċèµ·';
                                            } else if (data === '1') {
                                                return 'ċ?Ħĉ ¸ä¸­';
                                            } else if (data === '2') {
                                                return 'ċ?Ħĉ ¸çğĉ';
                                            } else if (data === '3') {
                                                return 'éċ';
                                            } else if (data === '4') {
                                                return 'ĉŞċèµ·';
                                            }
                                        }
                                    },
                                    form: {
                                        type: "select",
                                        placeholder: "èŻ·éĉİ",
                                        optionData: [
                                            {
                                                label: "ĉŞċèµ·",
                                                value: "4"
                                            },
                                            {
                                                label: "ċ·²ċèµ·",
                                                value: "0"
                                            },
                                            {
                                                label: "ċ?Ħĉ ¸ä¸­",
                                                value: "1"
                                            },
                                            {
                                                label: "ċ?Ħĉ ¸çğĉ",
                                                value: "2"
                                            },
                                            {
                                                label: "éċ",
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
                                                    title: `ĉ¨çĦ?ċ?èĤċ é¤ĉ°ĉ?äı?`,
                                                    content: `ċĉĥċ é¤èŻ·çıċğċĉĥĉé??`,
                                                    okText: "çĦ?è?¤",
                                                    cancelText: "ċĉĥ",
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
                                                Msg.error('ĉ ĉéċ é¤ċĞäşşċèµ·çĉ°ĉ?ïĵ');
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