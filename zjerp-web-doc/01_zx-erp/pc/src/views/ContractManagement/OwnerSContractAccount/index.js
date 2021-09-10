import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal, Drawer, Row, Col, Button, Tabs } from 'antd';
import { FullscreenOutlined, FullscreenExitOutlined } from '@ant-design/icons';
import TableOne from './tableOne';
import TableTow from './tableTow';
import TableThree from './tableThree';
import TableFour from './tableFour';
import TableFive from './tableFive';
import TableSix from './tableSix';
import TableSeven from './tableSeven';
import s from './style.less';
import moment from 'moment';
// import { ImportOutlined } from '@ant-design/icons';
const confirm = Modal.confirm;
const { TabPane } = Tabs;
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    drawerConfig: {
        width: window.innerWidth * 0.8
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 10 },
            sm: { span: 10 }
        },
        wrapperCol: {
            xs: { span: 14 },
            sm: { span: 14 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.onClose = this.onClose.bind(this);
        this.fullScreenStateChange = this.fullScreenStateChange.bind(this);
        this.onRef = this.onRef.bind(this);
        this.tabsCallback = this.tabsCallback.bind(this);
        this.handleCancelClick = this.handleCancelClick.bind(this);
        this.handleSubmitClick = this.handleSubmitClick.bind(this);
        this.state = {
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            visible: false,
            drawerDetailTitle: '操作',
            fullScreenState: false,
            activeKey: '1',
            objJson: null,
            contrStatus: '',
            loading: false,
            tabsDisabled: true,
            ButtonDisabled: false
        }
    }
    onClose () {
        this.setState({
            visible: false,
        })
        this.table.clearSelectedRows();
    }
    fullScreenStateChange () {
        this.setState({
            fullScreenState: !this.state.fullScreenState
        })
    }
    tabsCallback (activeKey) {
        this.setState({
            activeKey
        })
    }
    onRef (ref) {
        this.childOne = ref
    }
    handleCancelClick () {
        this.setState({
            visible: false
        })
        this.table.clearSelectedRows();
    }
    handleSubmitClick () {
        const { objJson } = this.state;
        this.childOne.qnnForm.form.validateFields().then((val) => {
            let valueLength = this.childOne.qnnForm.props.formConfig.map((item) => {
                if (item.type === 'date' && val[item.field]) {
                    val[item.field] = moment(val[item.field]).valueOf();
                }
                if (item.type === 'checkbox' && val[item.field]) {
                    val[item.field] = val[item.field][0];
                }
                return item;
            })
            if (valueLength.length === this.childOne.qnnForm.props.formConfig.length) {
                if (val?.id) {
                    this.setState({
                        loading: true
                    })
                    this.props.myFetch('updateZxCtContract', val).then(({ success, message, data }) => {
                        if (success) {
                            Msg.success(message);
                            objJson.rowData = data;
                            this.setState({
                                objJson: objJson,
                                activeKey: '2',
                                loading: false
                            }, () => {
                                this.table.refresh();
                            })
                        } else {
                            Msg.error(message);
                            this.setState({
                                loading: false
                            })
                        }
                    });
                } else {
                    this.setState({
                        loading: true
                    })
                    this.props.myFetch('addZxCtContract', val).then(({ success, message, data }) => {
                        if (success) {
                            Msg.success(message);
                            objJson.rowData = data;
                            this.setState({
                                objJson: objJson,
                                activeKey: '2',
                                loading: false,
                                ButtonDisabled: true,
                                tabsDisabled: false
                            }, () => {
                                this.table.refresh();
                            })
                        } else {
                            Msg.error(message);
                            this.setState({
                                loading: false
                            })
                        }
                    });
                }
            }
        }).catch(errs => {
            this.childOne.qnnForm.form.scrollToField(errs?.errorFields?.[0]?.name);
        });
    }
    render () {
        const { departmentId, visible, drawerDetailTitle, fullScreenState, activeKey, objJson, contrStatus, loading, tabsDisabled, ButtonDisabled } = this.state;
        return (
            <div className={s.root}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxCtContractList',
                        otherParams: {
                            orgID: departmentId,
                            // contractType:'P1'
                        }
                    }}
                    drawerShowToggle={(obj) => {
                        let { drawerIsShow } = obj;
                        if (!drawerIsShow) {
                            obj.btnCallbackFn.clearSelectedRows();
                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            let props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "OwnerSContractAccount"
                            }
                        }
                    }}
                    method={{
                        addDIYClick: (obj) => {
                            obj.selectedRows = [];
                            this.setState({
                                activeKey: '1',
                                drawerDetailTitle: '新增',
                                objJson: obj,
                                contrStatus: '',
                                tabsDisabled: true,
                                ButtonDisabled: false,
                                visible: true
                            })
                        },
                        editDIYDisabled: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        editDIYClick: (obj) => {
                            this.setState({
                                activeKey: '1',
                                drawerDetailTitle: '编辑',
                                objJson: obj,
                                contrStatus: obj.selectedRows[0].contrStatus,
                                tabsDisabled: false,
                                ButtonDisabled: false,
                                visible: true
                            })
                        },
                        auditDisabled: (obj) => {
                            let rowData = obj.btnCallbackFn.getSelectedRows();
                            if (rowData.length === 1 && (rowData[0].contrStatus === '' || rowData[0].contrStatus === '0')) {
                                return false;
                            } else {
                                return true
                            }
                        },
                        auditClick: (obj) => {
                            confirm({
                                content: '确定审核该条数据吗?',
                                onOk: () => {
                                    let _formData = obj.selectedRows[0];
                                    _formData.contrStatus = '1';
                                    this.props.myFetch('updateZxCtContractContrStatus', _formData).then(({ success, message, data }) => {
                                        if (success) {
                                            Msg.success(message);
                                            obj.btnCallbackFn.refresh();
                                            obj.btnCallbackFn.clearSelectedRows();
                                        } else {
                                            Msg.error(message);
                                        }
                                    });
                                }
                            });
                        },
                        auditeeDisabled: (obj) => {
                            let rowData = obj.btnCallbackFn.getSelectedRows();
                            if (rowData.length === 1 && rowData[0].contrStatus === '1') {
                                return false;
                            } else {
                                return true
                            }
                        },
                        auditeeClick: (obj) => {
                            confirm({
                                content: '确定审核该条数据吗?',
                                onOk: () => {
                                    let _formData = obj.selectedRows[0];
                                    _formData.contrStatus = '0';
                                    this.props.myFetch('updateZxCtContractContrStatus', _formData).then(({ success, message, data }) => {
                                        if (success) {
                                            Msg.success(message);
                                            obj.btnCallbackFn.refresh();
                                            obj.btnCallbackFn.clearSelectedRows();
                                        } else {
                                            Msg.error(message);
                                        }
                                    });
                                }
                            });
                        },
                        delDisabled: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length) {
                                return false;
                            } else {
                                return true;
                            }
                        }
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
                                title: '合同序号',
                                dataIndex: 'index',
                                key: 'index',
                                fixed: 'left',
                                width: 50,
                                render: (data, rowData, index) => {
                                    return index + 1;
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                filter: true,
                                fixed: 'left',
                                width: 150,
                                fieldsConfig: {
                                    type: 'select',
                                    field: 'remarks',
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
                                    },
                                    showSearch: true,
                                    fetchConfig: {
                                        apiName: 'getSysProjectBySelect',
                                        otherParams: {
                                            departmentId: departmentId
                                        }
                                    },
                                },
                                onClick: (obj) => {
                                    obj.rowData.contrStatus = '1';
                                    this.setState({
                                        activeKey: '1',
                                        drawerDetailTitle: '详情',
                                        objJson: obj,
                                        contrStatus: obj.rowData.contrStatus,
                                        tabsDisabled: false,
                                        visible: true
                                    })
                                },
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '项目全称',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                filter: true,
                                fixed: 'left',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '项目所在地',
                                dataIndex: 'projectLocationName',
                                key: 'projectLocationName',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '对下标准合同编码',
                                dataIndex: 'contractNo',
                                key: 'contractNo',
                                width: 150,
                                filter: true,
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '含税合同金额(万元)',
                                dataIndex: 'contractCost',
                                key: 'contractCost',
                                width: 120
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同开工日期',
                                dataIndex: 'actualStartDate',
                                key: 'actualStartDate',
                                width: 100,
                                format: "YYYY-MM-DD"
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '合同竣工日期',
                                dataIndex: 'actualEndDate',
                                key: 'actualEndDate',
                                width: 100,
                                format: "YYYY-MM-DD"
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '合同状态',
                                dataIndex: 'contractStatus',
                                key: 'contractStatus',
                                width: 100,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName', //默认 label
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'heTongZhuangTai'
                                    }
                                },
                                placeholder: '请选择'
                            }
                        },
                        // {
                        //     isInForm: false,
                        //     table: {
                        //         title: '清单导入',
                        //         dataIndex: 'action',
                        //         key: "action",//操作列名称
                        //         align: 'center',
                        //         fixed: 'right',
                        //         showType: "tile",
                        //         width: 100,
                        //         btns: [
                        //             {
                        //                 label: <span style={{ fontWeight: "800", fontSize: 18 }} title="导入" key={"rowImportClick"}> <ImportOutlined key={"rowImportClick"}/></span>,
                        //                 type: 'primary', //primary dashed danger 
                        //                 onClick: "bind:rowImportClick"
                        //             },
                        //         ]
                        //     }
                        // }
                    ]}
                />
                {
                    visible ? <Drawer
                        title={<Row justify="space-between">
                            <Col>{drawerDetailTitle}</Col>
                            <Col style={{ marginRight: 52, fontSize: 20, cursor: "pointer" }} title="切换全屏/非全屏" onClick={this.fullScreenStateChange}>{fullScreenState ? <FullscreenExitOutlined /> : <FullscreenOutlined />}</Col>
                        </Row>}
                        placement="right"
                        onClose={this.onClose}
                        visible={visible}
                        width={fullScreenState ? window.innerWidth : window.innerWidth * 0.8}
                        footer={activeKey === '1' && contrStatus !== '1' ? <div style={{ textAlign: 'right', paddingRight: '5px' }}>
                            <Button type="dashed" onClick={this.handleCancelClick} style={{ marginRight: '10px' }}>取消</Button>
                            <Button type="primary" onClick={this.handleSubmitClick} loading={loading} disabled={ButtonDisabled}>保存</Button>
                        </div> : null}
                        className={'OwnerSContractAccount'}
                    >
                        <Tabs activeKey={activeKey} onChange={this.tabsCallback}>
                            <TabPane tab="合同信息" key="1">
                                {activeKey === '1' ? <TableOne onRef={this.onRef} drawerDetailTitle={drawerDetailTitle} {...this.props} {...objJson} /> : null}
                            </TabPane>
                            <TabPane tab="清单" key="2" disabled={tabsDisabled}>
                                {activeKey === '2' ? <TableTow drawerDetailTitle={drawerDetailTitle} {...this.props} {...objJson} /> : null}
                            </TabPane>
                            <TabPane tab="合同变更" key="3" disabled={tabsDisabled}>
                                {activeKey === '3' ? <TableThree drawerDetailTitle={drawerDetailTitle} {...this.props} {...objJson} /> : null}
                            </TabPane>
                            <TabPane tab="索赔" key="4" disabled={tabsDisabled}>
                                {activeKey === '4' ? <TableFour drawerDetailTitle={drawerDetailTitle} {...this.props} {...objJson} /> : null}
                            </TabPane>
                            <TabPane tab="产值" key="5" disabled={tabsDisabled}>
                                {activeKey === '5' ? <TableFive drawerDetailTitle={drawerDetailTitle} {...this.props} {...objJson} /> : null}
                            </TabPane>
                            <TabPane tab="计量" key="6" disabled={tabsDisabled}>
                                {activeKey === '6' ? <TableSix drawerDetailTitle={drawerDetailTitle} {...this.props} {...objJson} /> : null}
                            </TabPane>
                            <TabPane tab="信用评价" key="7" disabled={tabsDisabled}>
                                {activeKey === '7' ? <TableSeven drawerDetailTitle={drawerDetailTitle} {...this.props} {...objJson} /> : null}
                            </TabPane>
                        </Tabs>
                    </Drawer> : null
                }
            </div>
        );
    }
}

export default index;