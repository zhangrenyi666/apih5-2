import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from '../../modules/qnn-table/qnn-form';
import { message as Msg, Modal, Spin } from 'antd';
import moment from 'moment';
import s from "./style.less";
import Tree from "../../modules/tree";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'zxCrHalfYearCreditEvaId',
        size: 'small'
    },
    drawerConfig: {
        width: '1300px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 12 },
            sm: { span: 8 }
        },
        wrapperCol: {
            xs: { span: 12 },
            sm: { span: 16 }
        }
    }
};
const configItem = {
    antd: {
        rowKey: 'zxCrHalfYearCreditEvaItemId',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 12 },
            sm: { span: 12 }
        },
        wrapperCol: {
            xs: { span: 12 },
            sm: { span: 12 }
        }
    }
};
const configModal = {

    antd: {
        rowKey: 'zxCrProjectEvaluationId',
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight * 0.6
        }
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visibleSendDataDetail: false,
            loadingSendDataDetail: false,
            visibleSendData: false,
            loadingSendData: false,
            name: '',
            nameTwo: '',
            id: '',
            catIDzhi: '',
            resIDzhi: '',
            parentID: '',
            defaultExpandedKeys: [],
            defaultExpandedKeysTwo: [],
            zxCrHalfYearCreditEvaId: '',
            treeData: null,
            treeDataTwo: null,
            period: '',
            dataOk: [],
            dataNoOk: [],
            zxCrProjectEvaluationId: '',
            zxCrHalfYearCreditEvaItemId: ''
        }
    }
    // ?????????-??????
    getTreeData(val) {
        const { myFetch } = this.props;
        myFetch('getZxCrColCategoryTreeShu', {
            zxCrHalfYearCreditEvaId: val
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        treeData: data
                    })
                } else {
                    Msg.warn(message)
                }
            }
        );
    }
    // ????????????-???-??????
    getTreeDataTwo(val) {
        const { myFetch } = this.props;
        myFetch('getZxCrColCategoryTreeShu', {
            zxCrHalfYearCreditEvaId: val//???
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        treeDataTwo: data
                    })
                } else {
                    Msg.warn(message)
                }
            }
        );
    }
    handleCancelSend = () => {
        this.setState({ visibleSendData: false, loadingSendData: false });
    }
    handleCancelSendDetail = () => {
        this.setState({ visibleSendDataDetail: false, loadingSendDataDetail: false });
    }
    render() {
        const { visibleSendData, loadingSendData, visibleSendDataDetail, loadingSendDataDetail } = this.state;
        const { companyId, companyName, departmentId, departmentName, projectId, projectName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <div>
                <QnnTable
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxCrHalfYearCreditEvaList',
                        otherParams: {
                            orgID: companyId
                        }
                    }}
                    actionBtns={[
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '??????',
                            onClick: (obj) => {
                                obj.btnCallbackFn.setActiveKey('0');
                                this.setState({
                                    name: '',
                                    zxCrHalfYearCreditEvaId: '',
                                    defaultExpandedKeys: []
                                })
                            },
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    field: 'cancel',
                                    label: '??????',
                                },
                                {
                                    name: 'submitDiy',
                                    type: 'primary',
                                    field: 'submit',
                                    label: '??????',
                                    onClick: (obj) => {
                                        this.props.myFetch('addZxCrHalfYearCreditEva', obj._formData).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    // ??????????????????????????????id
                                                    this.setState({
                                                        zxCrHalfYearCreditEvaId: data.zxCrHalfYearCreditEvaId,
                                                        period: data.period,
                                                        orgName: data.orgName
                                                    })
                                                    // ???????????????
                                                    this.getTreeData(data.zxCrHalfYearCreditEvaId);
                                                    obj.btnCallbackFn.setActiveKey('1');
                                                    this.table.refresh();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
                                    },
                                    hide: (obj) => {
                                        var index = obj.btnCallbackFn.getActiveKey();
                                        if (index === "1") {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                }
                            ]
                        },
                        {
                            name: 'edit',
                            icon: 'edit',
                            type: 'primary',
                            label: '??????',
                            onClick: (obj) => {
                                if (obj.selectedRows[0].auditStatus === '1') {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('??????????????????????????????')
                                } else {
                                    this.setState({
                                        treeData: null,
                                        defaultExpandedKeys: [],
                                        name: ''
                                    }, () => {
                                        obj.btnCallbackFn.setActiveKey('0');
                                        this.setState({
                                            zxCrHalfYearCreditEvaId: obj.selectedRows[0].zxCrHalfYearCreditEvaId,
                                            period: obj.selectedRows[0].period,
                                            orgName: obj.selectedRows[0].orgName
                                        })
                                        // ???????????????
                                        this.getTreeData(obj.selectedRows[0].zxCrHalfYearCreditEvaId);
                                    })
                                }
                                this.table.clearSelectedRows();
                            },
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    field: 'cancel',
                                    label: '??????'
                                },
                                {
                                    name: 'submitEdit',
                                    type: 'primary',
                                    field: 'submit',
                                    label: '??????',
                                    hide: (obj) => {
                                        var index = obj.btnCallbackFn.getActiveKey();
                                        if (index === "1") {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    onClick: (obj) => {
                                        obj.btnCallbackFn.clearSelectedRows();
                                        this.props.myFetch('updateZxCrHalfYearCreditEva', obj._formData).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    // ???????????????
                                                    this.getTreeData(obj._formData.zxCrHalfYearCreditEvaId);
                                                    obj.btnCallbackFn.setActiveKey('1');
                                                    this.table.refresh();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );

                                    }
                                }
                            ]
                        },
                        {
                            name: 'diyDel',
                            icon: 'delete',
                            type: 'danger',
                            label: '??????',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {
                                const { myFetch } = this.props;
                                let arry = [];
                                for (let m = 0; m < obj.selectedRows.length; m++) {
                                    if (obj.selectedRows[m].auditStatus === '1') {
                                        //????????????????????????
                                        arry.push(obj.selectedRows[m].auditStatus);
                                    }
                                }
                                if (arry.length > 0) {
                                    Msg.warn('??????????????????????????????')
                                } else {
                                    confirm({
                                        content: '???????????????????????????????',
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZxCrHalfYearCreditEva', obj.selectedRows).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        this.table.refresh();
                                                        this.table.clearSelectedRows();
                                                    } else {
                                                    }
                                                }
                                            );
                                        }
                                    });
                                }
                            }
                        },
                        {
                            name: 'diy',
                            type: 'primary',
                            label: '??????',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {
                                const { myFetch } = this.props;
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].status === '1') {
                                        obj.btnCallbackFn.closeDrawer();
                                        this.table.clearSelectedRows();
                                        Msg.warn('????????????????????????!');
                                    } else {
                                        confirm({
                                            content: '???????????????????????????????',
                                            onOk: () => {
                                                myFetch('updateZxCrHalfYearCreditEvaAuditStatus', obj.selectedRows[0]).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            this.table.refresh();
                                                            this.table.clearSelectedRows();
                                                        } else {
                                                        }
                                                    }
                                                );
                                            }
                                        });
                                    }
                                } else {
                                    Msg.warn('???????????????????????????')
                                }
                            }
                        }
                    ]}
                    drawerShowToggle={(obj) => {
                        let { drawerIsShow } = obj;
                        if (!drawerIsShow) {
                            obj.btnCallbackFn.clearSelectedRows();
                        }
                    }}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "????????????",
                            content: {
                                formConfig: [
                                    {
                                        type: "string",
                                        label: "??????ID",
                                        field: "zxCrHalfYearCreditEvaId",
                                        hide: true
                                    },
                                    {
                                        type: "string",
                                        label: "",
                                        field: "orgID",
                                        initialValue: companyId,
                                        hide: true
                                    },
                                    {
                                        label: '????????????',
                                        field: 'orgName',
                                        disabled: true,
                                        type: 'string',
                                        initialValue: companyName,
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        type: 'halfYear',
                                        field: 'period',
                                        editDisabled: true,
                                        allowClear: false,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        type: 'date',
                                        field: 'dateTime',
                                        initialValue: () => {
                                            return moment(new Date()).format('YYYY-MM-DD')
                                        },
                                        span: 8
                                    },
                                    {
                                        label: '??????',
                                        type: 'textarea',
                                        field: 'remarks',
                                        autoSize: {
                                            minRows: 1,
                                            maxRows: 3
                                        },
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 12 },
                                                sm: { span: 3 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 12 },
                                                sm: { span: 21 }
                                            }
                                        }
                                    }
                                ]
                            }
                        },
                        {
                            field: "tableqd",
                            name: "tableqd",
                            title: "??????",
                            disabled: (obj) => {
                                var rowData = obj.clickCb.rowData;
                                if (rowData && rowData.zxCrHalfYearCreditEvaId) {
                                    return false;
                                } else if (this.state.zxCrHalfYearCreditEvaId != '') {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            content: props => {
                                return <div className={s.root}>
                                    <div className={s.rootl}
                                        ref={(me) => {
                                            if (me) {
                                                this.leftDom = me;
                                            }
                                        }}>
                                        <div
                                            className={s.hr}
                                            ref={(me) => {
                                                if (me) {
                                                    let _this = this;
                                                    function moveFn(e) {
                                                        let conDomLeft = document.getElementsByClassName('ant-drawer-content-wrapper')[0].offsetLeft;
                                                        _this.leftDom.style['flex-basis'] = e.pageX - conDomLeft + 'px'
                                                    }
                                                    me.addEventListener('mousedown', (e) => {
                                                        this.onDragStartPos = e.pageX;
                                                        document.addEventListener('mousemove', moveFn, false)
                                                    }, false);
                                                    document.addEventListener('mouseup', (e) => {
                                                        document.removeEventListener('mousemove', moveFn, false)
                                                    }, false)
                                                }
                                            }}
                                        ></div>
                                        {this.state.treeData ? <Tree
                                            selectText={false}
                                            modalType="common" //common  drawer  ?????????????????????????????????
                                            visible
                                            selectModal="0" //0?????????  1??????(??????)  2????????????????????????
                                            myFetch={this.props.myFetch}
                                            upload={this.props.myUpload}
                                            btnShow={false}
                                            disabled={true}
                                            draggable={false}
                                            nodeRender={nodeData => {
                                                return (
                                                    <span>
                                                        {nodeData["name"]}
                                                    </span>
                                                );
                                            }}
                                            treeProps={{
                                                showLine: true
                                            }}
                                            defaultExpandedKeys={this.state.defaultExpandedKeys}
                                            rightMenuOption={[]}
                                            nodeClick={(node) => {
                                                this.setState({
                                                    name: '',
                                                    id: '',
                                                    catIDzhi: '',
                                                    resIDzhi: '',
                                                    parentID: ''
                                                }, () => {
                                                    this.setState({
                                                        defaultExpandedKeys: node.bsid.split(','),
                                                        name: node.name,
                                                        id: node.id,
                                                        catIDzhi: node.catID,
                                                        resIDzhi: node.resID,
                                                        parentID: node.parentID
                                                    })
                                                })

                                            }}
                                            data={this.state.treeData}
                                            keys={{
                                                label: "name",
                                                value: "id",
                                                children: "childrenList"
                                            }}
                                        /> : null}
                                    </div>
                                    <div className={s.rootr}>
                                        {this.state.name ? <QnnTable
                                            history={this.props.history}
                                            match={this.props.match}
                                            fetch={this.props.myFetch}
                                            myFetch={this.props.myFetch}
                                            upload={this.props.myUpload}
                                            headers={{
                                                token: this.props.loginAndLogoutInfo.loginInfo.token
                                            }}
                                            fetchConfig={{
                                                apiName: 'getZxCrHalfYearCreditEvaItemListInit',
                                                otherParams: {
                                                    period: this.state.period,
                                                    catID: this.state.catIDzhi,
                                                    resID: this.state.resIDzhi,
                                                    parentID: this.state.parentID,
                                                    zxCrHalfYearCreditEvaId: this.state.zxCrHalfYearCreditEvaId,
                                                    orgName: this.state.orgName,
                                                    departmentId: departmentId ? departmentId : '',
                                                    projectId: projectId ? projectId : '',
                                                    companyId: companyId ? companyId : ''
                                                }
                                            }}
                                            {...configItem}
                                            wrappedComponentRef={(me) => {
                                                this.tableSK = me;
                                            }}
                                            isShowRowSelect={false}
                                            formConfig={[
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '??????id',
                                                        field: 'zxCrHalfYearCreditEvaItemId',
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????????????????',
                                                        width: 150,
                                                        tooltip: 23,
                                                        dataIndex: 'orgCertificate',
                                                        key: 'orgCertificate'
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '??????????????????',
                                                        width: 160,
                                                        tooltip: 23,
                                                        dataIndex: 'customerName',
                                                        key: 'customerName'
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '?????????????????????',
                                                        width: 160,
                                                        tooltip: 23,
                                                        dataIndex: 'chargeMan',
                                                        key: 'chargeMan',
                                                        onClick: (val) => {
                                                            // ????????????????????????
                                                            this.setState({
                                                                visibleSendData: true,
                                                                loadingSendData: false,
                                                                zxCrHalfYearCreditEvaItemId: val.rowData.zxCrHalfYearCreditEvaItemId
                                                            })
                                                        }
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '??????????????????',
                                                        width: 160,
                                                        dataIndex: 'projectName',
                                                        key: 'projectName'
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        format: 'YYYY-MM-DD',
                                                        dataIndex: 'inDate',
                                                        key: 'inDate',

                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        format: 'YYYY-MM-DD',
                                                        dataIndex: 'outDate',
                                                        key: 'outDate'
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '?????????????????????(??????)',
                                                        width: 200,
                                                        dataIndex: 'contractAmt',
                                                        key: 'contractAmt'
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '????????????????????????',
                                                        width: 160,
                                                        dataIndex: 'projectNum',
                                                        key: 'projectNum'
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'checkNum',
                                                        key: 'checkNum'
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '????????????????????????(?????????)',
                                                        width: 160,
                                                        dataIndex: 'firstSoce',
                                                        key: 'firstSoce',

                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '????????????(?????????)',
                                                        width: 160,
                                                        dataIndex: 'firstLevel',
                                                        key: 'firstLevel'
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '???????????????????????????????????????',
                                                        width: 180,
                                                        dataIndex: 'secondScore',
                                                        key: 'secondScore'
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '????????????(?????????)',
                                                        width: 160,
                                                        dataIndex: 'secondLevel',
                                                        key: 'secondLevel'
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '??????????????????D?????????',
                                                        width: 160,
                                                        dataIndex: 'dLevel',
                                                        key: 'dLevel'
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '????????????????????????',
                                                        width: 130,
                                                        dataIndex: 'lastScore',
                                                        key: 'lastScore'
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '??????????????????',
                                                        width: 130,
                                                        dataIndex: 'lastLevel',
                                                        key: 'lastLevel'
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'remarks',
                                                        key: 'remarks'
                                                    },
                                                    isInForm: false
                                                },
                                            ]}
                                            actionBtns={[]}
                                        /> : null}

                                    </div>
                                </div>;
                            }
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxCrHalfYearCreditEvaId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: '25%',
                                onClick: 'detail',
                                willExecute: (obj) => {
                                    obj.btnCallbackFn.setActiveKey('0');
                                    this.setState({
                                        treeData: null,
                                        defaultExpandedKeys: [],
                                        name: ''
                                    }, () => {
                                        this.setState({
                                            zxCrHalfYearCreditEvaId: obj.rowData.zxCrHalfYearCreditEvaId,
                                            period: obj.rowData.period,
                                            orgName: obj.rowData.orgName
                                        })
                                        // ???????????????
                                        this.getTreeData(obj.rowData.zxCrHalfYearCreditEvaId);
                                    })

                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'period',
                                key: 'period',
                                filter: true,
                                width: 150,
                                // render:(data,rowData) => {
                                //     if(data){
                                //         return moment(data).month() === 0 ? (moment(data).format('YYYY') + '/?????????') : (moment(data).format('YYYY') + '/?????????');
                                //     }else{
                                //         return null;
                                //     }
                                // },
                                render: (data) => {
                                    return data ? (moment(data).format('YYYY') + '/' + (moment(data).format('MM') === '12' ? '?????????' : '?????????')) : ''
                                },
                            },
                            isInForm: false,
                            form: {
                                type: 'halfYear',
                                field: 'period',
                                allowClear: false,
                                placeholder: '?????????',
                                span: 8
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'dateTime',
                                key: 'dateTime',
                                format: 'YYYY-MM-DD',
                                width: 100
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'auditStatus',
                                key: 'auditStatus',
                                width: 100,
                                render: (data) => {
                                    if (data === '0') {
                                        return '?????????';
                                    } else {
                                        return '?????????';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: '20%',
                                dataIndex: 'remarks',
                                key: 'remarks',
                                tooltip: 23,
                            },
                            isInForm: false
                        }
                    ]}
                />
                <Modal
                    width={document.documentElement.clientWidth * 0.9}
                    style={{ top: '0' }}
                    title="????????????????????????"
                    visible={visibleSendData}
                    footer={null}
                    onCancel={this.handleCancelSend}
                    bodyStyle={{ width: '100%' }}
                    centered={true}
                    destroyOnClose={this.handleCancelSend}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingSendData}>
                        <div style={{ height: document.documentElement.clientHeight * 0.7 }}>
                            <QnnTable
                                {...this.props}
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => {
                                    this.table = me;
                                }}
                                drawerShowToggle={(obj) => {
                                    let { drawerIsShow } = obj;
                                    if (!drawerIsShow) {
                                        obj.btnCallbackFn.refresh();
                                    }
                                }}
                                {...configModal}
                                fetchConfig={{
                                    apiName: 'getZxCrProjectEvaluationList',
                                    otherParams: {
                                        catID: this.state.id
                                    }
                                }}
                                formConfig={[
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'zxCrProjectEvaluationId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            width: 200,
                                            tooltip: 23,
                                            fixed: 'left',
                                            dataIndex: 'orgName',
                                            key: 'orgName',
                                            onClick: (obj) => {
                                                if (obj.rowData.checkStandard === '0') {
                                                    this.setState({
                                                        dataNoOk: obj.rowData.projectEvaluationBadList,
                                                        visibleSendDataDetail: true,
                                                        loadingSendDataDetail: false,
                                                        zxCrProjectEvaluationId: obj.rowData.zxCrProjectEvaluationId
                                                    })
                                                } else if (obj.rowData.checkStandard === '1') {
                                                    this.setState({
                                                        dataOk: obj.rowData.projectEvaluationScoreList,
                                                        visibleSendDataDetail: true,
                                                        loadingSendDataDetail: false,
                                                        zxCrProjectEvaluationId: obj.rowData.zxCrProjectEvaluationId
                                                    })
                                                } else {
                                                    this.setState({
                                                        dataNoOk: [],
                                                        dataOk: []
                                                    })
                                                }
                                            }
                                        },
                                        form: {
                                            field: 'orgName',
                                            type: 'string',
                                            initialValue: '?????????????????????',
                                            addDisabled: true,
                                            editDisabled: true,
                                            placeholder: '?????????',
                                            spanForm: 8,
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 14 }
                                                }
                                            }
                                        },
                                    },
                                    {
                                        table: {
                                            title: '??????????????????',
                                            dataIndex: 'customerId',
                                            key: 'customerId',
                                            width: 200,
                                            type: 'select'
                                        },
                                        form: {
                                            field: 'customerId',
                                            required: true,
                                            type: 'select',
                                            showSearch: true,
                                            optionConfig: {
                                                label: 'customerName',
                                                value: 'orgCertificate'
                                            },
                                            fetchConfig: {
                                                apiName: 'getZxCrCustomerInfoListAll'
                                            },
                                            placeholder: '?????????',
                                            spanForm: 8,
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 14 }
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '?????????????????????',
                                            dataIndex: 'chargeMan',
                                            width: 160,
                                            key: 'chargeMan'
                                        },
                                        form: {
                                            field: 'chargeMan',
                                            type: 'string',
                                            placeholder: '?????????',
                                            spanForm: 8,
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 14 }
                                                }
                                            }
                                        },
                                    },
                                    {
                                        table: {
                                            title: '?????????????????????',
                                            width: 160,
                                            dataIndex: 'chargeManPhone',
                                            key: 'chargeManPhone'
                                        },
                                        form: {
                                            field: 'chargeManPhone',
                                            type: 'phone',
                                            placeholder: '?????????',
                                            spanForm: 8,
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 14 }
                                                }
                                            }
                                        },
                                    },
                                    {
                                        table: {
                                            title: '??????????????????',
                                            width: 200,
                                            dataIndex: 'catCode',
                                            key: 'catCode'
                                        },
                                        form: {
                                            field: 'catCode',
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled: true,
                                            placeholder: '?????????',
                                            spanForm: 8,
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 14 }
                                                }
                                            }
                                        },
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'catID',
                                            key: 'catID',
                                            type: 'select',
                                        },
                                        form: {
                                            type: 'select',
                                            field: 'catID',
                                            required: true,
                                            optionConfig: {
                                                label: 'name',
                                                value: 'id',
                                                linkageFields: {
                                                    catCode: 'catCode',
                                                    // catId:'id'
                                                },
                                                children: 'zxCrColCategoryList'
                                            },
                                            fetchConfig: {
                                                apiName: 'getZxCrProjectEvaluationListCatName',
                                            },
                                            spanForm: 8,
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 14 }
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'resCode',
                                            key: 'resCode'
                                        },
                                        form: {
                                            field: 'resCode',
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled: true,
                                            placeholder: '?????????',
                                            spanForm: 8,
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 14 }
                                                }
                                            }
                                        },
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            type: 'string',
                                            hide: true,
                                            field: 'parentID'
                                        },
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'resName',
                                            key: 'resName',
                                        },
                                        form: {
                                            type: 'select',
                                            required: true,
                                            field: 'resID',
                                            // optionConfig: {
                                            //     label: 'resName',
                                            //     value: 'categoryID',
                                            //     linkageFields: {
                                            //         resCode: "resCode"
                                            //     }
                                            // },
                                            // fetchConfig: {
                                            //     apiName: 'getZxCrProjectEvaluationListResName',
                                            // },
                                            optionConfig: {
                                                label: 'resName',
                                                value: 'id'
                                            },
                                            dependenciesReRender: true,//????????????-??????
                                            dependencies: ['parentID', 'catID'],
                                            fetchConfig: {//parentID??????????????????????????????--?????????
                                                apiName: 'getZxCrProjectEvaluationListResName',
                                                otherParams: (val) => {
                                                    let parentIDVal = '';
                                                    let catIDVal = '';
                                                    if (val.btnCallbackFn?.form) {
                                                        let aa = val.btnCallbackFn.form.getFieldsValue();
                                                        parentIDVal = aa.parentID;
                                                        catIDVal = aa.catID;
                                                    } else {
                                                        parentIDVal = '';
                                                        catIDVal = '';
                                                    }
                                                    return {
                                                        parentID: parentIDVal,
                                                        catID: catIDVal
                                                    }
                                                }
                                            },
                                            spanForm: 8,
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 14 }
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '?????????????????????(??????)',
                                            width: 200,
                                            dataIndex: 'contractAmt',
                                            key: 'contractAmt'
                                        },
                                        form: {
                                            field: 'contractAmt',
                                            type: 'number',
                                            required: true,
                                            placeholder: '?????????',
                                            spanForm: 8,
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 14 }
                                                }
                                            }
                                        },
                                    },
                                    {
                                        table: {
                                            title: '???????????????',
                                            dataIndex: 'totalScore',
                                            key: 'totalScore'
                                        },
                                        form: {
                                            field: 'totalScore',
                                            type: 'number',
                                            addDisabled: true,
                                            editDisabled: true,
                                            placeholder: '?????????',
                                            spanForm: 8,
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 14 }
                                                }
                                            }
                                        },
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'period',
                                            key: 'period',
                                            type: 'select'
                                        },
                                        form: {
                                            field: 'period',
                                            type: 'select',
                                            required: true,
                                            optionConfig: {
                                                label: 'itemName',
                                                value: 'itemId'
                                            },
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'qiCi'//????????????
                                                },
                                            },
                                            placeholder: '?????????',
                                            spanForm: 8,
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 14 }
                                                }
                                            }
                                        },
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'checkDate',
                                            key: 'checkDate',
                                            format: 'YYYY-MM-DD'
                                        },
                                        form: {
                                            type: 'date',
                                            field: 'checkDate',
                                            spanForm: 8,
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 14 }
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'inDate',
                                            key: 'inDate',
                                            format: 'YYYY-MM-DD'
                                        },
                                        form: {
                                            type: 'date',
                                            field: 'inDate',
                                            spanForm: 8,
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 14 }
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'outDate',
                                            key: 'outDate',
                                            format: 'YYYY-MM-DD'
                                        },
                                        form: {
                                            type: 'date',
                                            field: 'outDate',
                                            spanForm: 8,
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 14 }
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'checkStandard',
                                            key: 'checkStandard',
                                            type: 'select',
                                        },
                                        form: {
                                            type: 'select',
                                            field: 'checkStandard',
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value'
                                            },
                                            optionData: [//????????????
                                                {
                                                    label: '???????????????????????????',
                                                    value: '0'
                                                },
                                                {
                                                    label: '???????????????',
                                                    value: '1'
                                                }
                                            ],
                                            spanForm: 8,
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 14 }
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '?????????',
                                            width: 120,
                                            dataIndex: 'preparer',
                                            key: 'preparer'
                                        },
                                        form: {
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled: true,
                                            field: 'preparer',
                                            initialValue: () => {
                                                return this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                                            },
                                            spanForm: 8,
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 14 }
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '?????????',
                                            width: 120,
                                            dataIndex: 'auditor',
                                            key: 'auditor'
                                        },
                                        form: {
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled: true,
                                            field: 'auditor',
                                            initialValue: () => {
                                                return this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                                            },
                                            spanForm: 8,
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 14 }
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            width: 100,
                                            fixed: 'right',
                                            dataIndex: 'auditStatus',
                                            key: 'auditStatus',
                                            render: (data) => {
                                                if (data) {
                                                    return data === '0' ? '?????????' : '?????????'
                                                } else {
                                                    return '?????????'
                                                }
                                            }
                                        },
                                        isInForm: false,
                                        form: {
                                            type: 'select',
                                            field: 'auditStatus',
                                            hide: true,
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value',
                                            },
                                            optionData: [//????????????
                                                {
                                                    label: "?????????",
                                                    value: "0"
                                                },
                                                {
                                                    label: "?????????",
                                                    value: "1"
                                                }
                                            ],
                                            spanForm: 8,
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 14 }
                                                }
                                            }
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '??????',
                                            field: 'fileList',
                                            type: 'files',
                                            fetchConfig: {
                                                apiName: 'upload'
                                            },
                                            spanForm: 8,
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 10 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 14 }
                                                }
                                            }
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            type: 'component',
                                            field: 'diySK1',
                                            Component: obj => {
                                                return (
                                                    <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                                        ???????????????????????????
                                                    </div>
                                                );
                                            },
                                            dependencies: ['checkStandard'],
                                            hide: (obj) => {
                                                let checkStandard = obj.form.getFieldValue('checkStandard');
                                                if (checkStandard) {
                                                    if (checkStandard === '0') {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                } else {
                                                    return true
                                                }
                                            }
                                        },
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            type: 'component',
                                            field: 'projectEvaluationBadList',
                                            Component: obj => {
                                                return (
                                                    <div
                                                        style={{ width: "100%", padding: '10px' }}
                                                    >
                                                        <QnnTable
                                                            history={this.props.history}
                                                            match={this.props.match}
                                                            fetch={this.props.myFetch}
                                                            myFetch={this.props.myFetch}
                                                            upload={this.props.myUpload}
                                                            headers={{
                                                                token: this.props.loginAndLogoutInfo.loginInfo.token
                                                            }}
                                                            antd={{
                                                                rowKey: 'zxCrProjectEvaluationBadId',
                                                                size: 'small'
                                                            }}
                                                            {...configItem}
                                                            wrappedComponentRef={(me) => {
                                                                this.tableNoOk = me;
                                                            }}
                                                            isShowRowSelect={false}
                                                            data={this.state.dataNoOk}
                                                            formConfig={[
                                                                {
                                                                    isInTable: false,
                                                                    form: {
                                                                        label: '??????id',
                                                                        field: 'zxCrProjectEvaluationBadId',
                                                                        hide: true
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '????????????',
                                                                        width: 150,
                                                                        tooltip: 23,
                                                                        dataIndex: 'evalContent',
                                                                        key: 'evalContent'
                                                                    },
                                                                    form: {
                                                                        type: 'string',
                                                                        field: 'evalContent'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '????????????',
                                                                        width: 150,
                                                                        tooltip: 23,
                                                                        dataIndex: 'scoreItem',
                                                                        key: 'scoreItem'
                                                                    },
                                                                    form: {
                                                                        type: 'string',
                                                                        field: 'scoreItem'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '??????????????????????????????',
                                                                        width: 100,
                                                                        dataIndex: 'isBad',
                                                                        key: 'isBad',
                                                                        type: 'select'
                                                                    },
                                                                    form: {
                                                                        type: 'select',
                                                                        field: 'isBad',
                                                                        optionConfig: {
                                                                            label: 'label', //?????? label
                                                                            value: 'value'
                                                                        },
                                                                        optionData: [//????????????
                                                                            {
                                                                                label: '???',
                                                                                value: '0'
                                                                            },
                                                                            {
                                                                                label: '???',
                                                                                value: '1'
                                                                            }
                                                                        ]
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '??????',
                                                                        dataIndex: 'remarks',
                                                                        width: 120,
                                                                        key: 'remarks'
                                                                    },
                                                                    form: {
                                                                        type: 'textarea',
                                                                        field: 'remarks',
                                                                        autoSize: {
                                                                            minRows: 1,
                                                                            maxRows: 3
                                                                        }
                                                                    }
                                                                }
                                                            ]}
                                                            actionBtns={[

                                                            ]}
                                                        />
                                                    </div>
                                                );
                                            },
                                            dependencies: ['checkStandard'],
                                            hide: (obj) => {
                                                let checkStandard = obj.form.getFieldValue('checkStandard');
                                                if (checkStandard) {
                                                    if (checkStandard === '0') {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                } else {
                                                    return true
                                                }
                                            }
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            type: 'component',
                                            field: 'diySK1',
                                            Component: obj => {
                                                return (
                                                    <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                                        ???????????????
                                                    </div>
                                                );
                                            },
                                            dependencies: ['checkStandard'],
                                            hide: (obj) => {
                                                let checkStandard = obj.form.getFieldValue('checkStandard');
                                                if (checkStandard) {
                                                    if (checkStandard === '1') {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                } else {
                                                    return true
                                                }
                                            }
                                        },
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            type: 'component',
                                            field: 'projectEvaluationScoreList',
                                            Component: obj => {
                                                return (
                                                    <div
                                                        style={{ width: "100%", padding: '10px' }}
                                                    >
                                                        <QnnTable
                                                            history={this.props.history}
                                                            match={this.props.match}
                                                            fetch={this.props.myFetch}
                                                            myFetch={this.props.myFetch}
                                                            upload={this.props.myUpload}
                                                            headers={{
                                                                token: this.props.loginAndLogoutInfo.loginInfo.token
                                                            }}
                                                            antd={{
                                                                rowKey: 'zxCrProjectEvaluationScoreId',
                                                                size: 'small'
                                                            }}
                                                            {...configItem}
                                                            wrappedComponentRef={(me) => {
                                                                this.tableOk = me;
                                                            }}
                                                            isShowRowSelect={false}
                                                            data={this.state.dataOk}
                                                            formConfig={[
                                                                {
                                                                    isInTable: false,
                                                                    form: {
                                                                        label: '??????id',
                                                                        field: 'zxCrProjectEvaluationScoreId',
                                                                        hide: true
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '????????????',
                                                                        width: 150,
                                                                        tooltip: 23,
                                                                        dataIndex: 'evalContent',
                                                                        key: 'evalContent'
                                                                    },
                                                                    form: {
                                                                        type: 'string',
                                                                        field: 'evalContent'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '????????????',
                                                                        width: 150,
                                                                        tooltip: 23,
                                                                        dataIndex: 'scoreItem',
                                                                        key: 'scoreItem'
                                                                    },
                                                                    form: {
                                                                        type: 'string',
                                                                        field: 'scoreItem'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '????????????',
                                                                        width: 150,
                                                                        dataIndex: 'standardScore',
                                                                        key: 'standardScore'
                                                                    },
                                                                    form: {
                                                                        type: 'number',
                                                                        field: 'standardScore'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '????????????',
                                                                        width: 100,
                                                                        dataIndex: 'deductScore',
                                                                        key: 'deductScore'
                                                                    },
                                                                    form: {
                                                                        type: 'number',
                                                                        field: 'deductScore'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '????????????',
                                                                        width: 150,
                                                                        dataIndex: 'getScore',
                                                                        key: 'getScore'
                                                                    },
                                                                    form: {
                                                                        type: 'number',
                                                                        field: 'getScore'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '??????',
                                                                        dataIndex: 'remarks',
                                                                        width: 120,
                                                                        key: 'remarks'
                                                                    },
                                                                    form: {
                                                                        type: 'textarea',
                                                                        field: 'remarks',
                                                                        autoSize: {
                                                                            minRows: 1,
                                                                            maxRows: 3
                                                                        }
                                                                    }
                                                                }
                                                            ]}
                                                            actionBtns={[

                                                            ]}
                                                        />
                                                    </div>
                                                );
                                            },
                                            dependencies: ['checkStandard'],
                                            hide: (obj) => {
                                                let checkStandard = obj.form.getFieldValue('checkStandard');
                                                if (checkStandard) {
                                                    if (checkStandard === '1') {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                } else {
                                                    return true
                                                }
                                            }
                                        }
                                    }
                                ]}
                                actionBtns={[]}
                            />
                        </div>
                    </Spin>
                </Modal>
                <Modal
                    width={document.documentElement.clientWidth * 0.95}
                    style={{ top: '0' }}
                    title="??????????????????????????????"
                    visible={visibleSendDataDetail}
                    footer={null}
                    onCancel={this.handleCancelSendDetail}
                    bodyStyle={{ width: '100%' }}
                    centered={true}
                    destroyOnClose={this.handleCancelSendDetail}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingSendDataDetail}>
                        <div style={{ height: document.documentElement.clientHeight * 0.75 }}>
                            <QnnForm
                                {...this.props}
                                match={this.props.match}
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => {
                                    this.formHasTicket = me;
                                }}
                                fetchConfig={{
                                    apiName: 'getZxCrProjectEvaluationDetail',
                                    otherParams: {
                                        zxCrProjectEvaluationId: this.state.zxCrProjectEvaluationId
                                    }
                                }}
                                formConfig={[
                                    {
                                        field: 'zxCrProjectEvaluationId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        label: '????????????',
                                        field: 'orgName',
                                        type: 'string',
                                        initialValue: '?????????????????????',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        label: '??????????????????',
                                        field: 'customerId',
                                        disabled: true,
                                        required: true,
                                        type: 'select',
                                        showSearch: true,
                                        optionConfig: {
                                            label: 'customerName',
                                            value: 'orgCertificate'
                                        },
                                        fetchConfig: {
                                            apiName: 'getZxCrCustomerInfoListAll'
                                        },
                                        placeholder: '?????????',
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        label: '?????????????????????',
                                        field: 'chargeMan',
                                        disabled: true,
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        label: '?????????????????????',
                                        disabled: true,
                                        field: 'chargeManPhone',
                                        type: 'phone',
                                        placeholder: '?????????',
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        label: '??????????????????',
                                        disabled: true,
                                        field: 'catCode',
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        label: '????????????',
                                        disabled: true,
                                        type: 'select',
                                        field: 'catID',
                                        required: true,
                                        optionConfig: {
                                            label: 'name',
                                            value: 'id'
                                        },
                                        fetchConfig: {
                                            apiName: 'getZxCrProjectEvaluationListCatName',
                                        },
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        label: '????????????',
                                        field: 'resCode',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        type: 'string',
                                        hide: true,
                                        field: 'parentID'
                                    },
                                    {
                                        label: '????????????',//???????????????--????????????
                                        type: 'select',
                                        // type: 'string',
                                        required: true,
                                        disabled: true,
                                        // field: 'resName',
                                        field: 'resID',
                                        optionConfig: {
                                            label: 'resName',
                                            value: 'id'
                                        },
                                        dependenciesReRender: true,//????????????-??????
                                        dependencies: ['parentID', 'catID'],
                                        fetchConfig: {//parentID??????????????????????????????--?????????
                                            apiName: 'getZxCrProjectEvaluationListResName',
                                            otherParams: (val) => {
                                                let parentIDVal = '';
                                                let catIDVal = '';
                                                if (val.btnCallbackFn?.form) {
                                                    let aa = val.btnCallbackFn.form.getFieldsValue();
                                                    parentIDVal = aa.parentID;
                                                    catIDVal = aa.catID;
                                                } else {
                                                    parentIDVal = '';
                                                    catIDVal = '';
                                                }
                                                return {
                                                    parentID: parentIDVal,
                                                    catID: catIDVal
                                                }
                                            }
                                        },
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        label: '?????????????????????(??????)',
                                        field: 'contractAmt',
                                        type: 'number',
                                        required: true,
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        label: '???????????????',
                                        field: 'totalScore',
                                        type: 'number',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        label: '????????????',
                                        field: 'period',
                                        type: 'select',
                                        disabled: true,
                                        required: true,
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'qiCi'//????????????
                                            },
                                        },
                                        placeholder: '?????????',
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        label: '????????????',
                                        type: 'date',
                                        disabled: true,
                                        field: 'checkDate',
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        label: '????????????',
                                        type: 'date',
                                        disabled: true,
                                        field: 'inDate',
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        label: '????????????',
                                        type: 'date',
                                        disabled: true,
                                        field: 'outDate',
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        label: '????????????',
                                        disabled: true,
                                        type: 'select',
                                        field: 'checkStandard',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [//????????????
                                            {
                                                label: '???????????????????????????',
                                                value: '0'
                                            },
                                            {
                                                label: '???????????????',
                                                value: '1'
                                            }
                                        ],
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        label: '?????????',
                                        type: 'string',
                                        disabled: true,
                                        field: 'preparer',
                                        initialValue: () => {
                                            return this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                                        },
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        label: '?????????',
                                        type: 'string',
                                        disabled: true,
                                        field: 'auditor',
                                        initialValue: () => {
                                            return this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                                        },
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        label: '????????????',
                                        type: 'select',
                                        disabled: true,
                                        field: 'auditStatus',
                                        hide: true,
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [//????????????
                                            {
                                                label: "?????????",
                                                value: "0"
                                            },
                                            {
                                                label: "?????????",
                                                value: "1"
                                            }
                                        ],
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        label: '??????',
                                        disabled: true,
                                        field: 'fileList',
                                        type: 'files',
                                        fetchConfig: {
                                            apiName: 'upload'
                                        },
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 10 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 14 }
                                            }
                                        }
                                    },
                                    {
                                        type: 'component',
                                        field: 'diySK1',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                                    ???????????????????????????
                                                </div>
                                            );
                                        },
                                        dependencies: ['checkStandard'],
                                        hide: (obj) => {
                                            let checkStandard = obj.form.getFieldValue('checkStandard');
                                            if (checkStandard) {
                                                if (checkStandard === '0') {
                                                    return false;
                                                } else {
                                                    return true;
                                                }
                                            } else {
                                                return true
                                            }
                                        }
                                    },
                                    {
                                        type: 'component',
                                        field: 'projectEvaluationBadList',
                                        Component: obj => {
                                            return (
                                                <div
                                                    style={{ width: "100%", padding: '10px' }}
                                                >
                                                    <QnnTable
                                                        history={this.props.history}
                                                        match={this.props.match}
                                                        fetch={this.props.myFetch}
                                                        myFetch={this.props.myFetch}
                                                        upload={this.props.myUpload}
                                                        headers={{
                                                            token: this.props.loginAndLogoutInfo.loginInfo.token
                                                        }}
                                                        antd={{
                                                            rowKey: 'zxCrProjectEvaluationBadId',
                                                            size: 'small'
                                                        }}
                                                        {...configItem}
                                                        wrappedComponentRef={(me) => {
                                                            this.tableNoOk = me;
                                                        }}
                                                        isShowRowSelect={false}
                                                        data={this.state.dataNoOk}
                                                        formConfig={[
                                                            {
                                                                isInTable: false,
                                                                form: {
                                                                    label: '??????id',
                                                                    field: 'zxCrProjectEvaluationBadId',
                                                                    hide: true
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    width: 150,
                                                                    tooltip: 23,
                                                                    dataIndex: 'evalContent',
                                                                    key: 'evalContent'
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    field: 'evalContent'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    width: 150,
                                                                    tooltip: 23,
                                                                    dataIndex: 'scoreItem',
                                                                    key: 'scoreItem'
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    field: 'scoreItem'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????????????????????????????',
                                                                    width: 100,
                                                                    dataIndex: 'isBad',
                                                                    key: 'isBad',
                                                                    type: 'select'
                                                                },
                                                                form: {
                                                                    type: 'select',
                                                                    field: 'isBad',
                                                                    optionConfig: {
                                                                        label: 'label', //?????? label
                                                                        value: 'value'
                                                                    },
                                                                    optionData: [//????????????
                                                                        {
                                                                            label: '???',
                                                                            value: '0'
                                                                        },
                                                                        {
                                                                            label: '???',
                                                                            value: '1'
                                                                        }
                                                                    ]
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????',
                                                                    dataIndex: 'remarks',
                                                                    width: 120,
                                                                    key: 'remarks'
                                                                },
                                                                form: {
                                                                    type: 'textarea',
                                                                    field: 'remarks',
                                                                    autoSize: {
                                                                        minRows: 1,
                                                                        maxRows: 3
                                                                    }
                                                                }
                                                            }
                                                        ]}
                                                        actionBtns={[

                                                        ]}
                                                    />
                                                </div>
                                            );
                                        },
                                        dependencies: ['checkStandard'],
                                        hide: (obj) => {
                                            let checkStandard = obj.form.getFieldValue('checkStandard');
                                            if (checkStandard) {
                                                if (checkStandard === '0') {
                                                    return false;
                                                } else {
                                                    return true;
                                                }
                                            } else {
                                                return true
                                            }
                                        }
                                    },
                                    {
                                        type: 'component',
                                        field: 'diySK1',
                                        Component: obj => {
                                            return (
                                                <div style={{ width: "100%", backgroundColor: '#eeeded', height: '32px', lineHeight: '32px', borderRadius: '2px', paddingLeft: '10px', marginBottom: '10px', marginTop: '10px' }}>
                                                    ???????????????
                                                </div>
                                            );
                                        },
                                        dependencies: ['checkStandard'],
                                        hide: (obj) => {
                                            let checkStandard = obj.form.getFieldValue('checkStandard');
                                            if (checkStandard) {
                                                if (checkStandard === '1') {
                                                    return false;
                                                } else {
                                                    return true;
                                                }
                                            } else {
                                                return true
                                            }
                                        }
                                    },
                                    {
                                        type: 'component',
                                        field: 'projectEvaluationScoreList',
                                        Component: obj => {
                                            return (
                                                <div
                                                    style={{ width: "100%", padding: '10px' }}
                                                >
                                                    <QnnTable
                                                        history={this.props.history}
                                                        match={this.props.match}
                                                        fetch={this.props.myFetch}
                                                        myFetch={this.props.myFetch}
                                                        upload={this.props.myUpload}
                                                        headers={{
                                                            token: this.props.loginAndLogoutInfo.loginInfo.token
                                                        }}
                                                        antd={{
                                                            rowKey: 'zxCrProjectEvaluationScoreId',
                                                            size: 'small'
                                                        }}
                                                        {...configItem}
                                                        wrappedComponentRef={(me) => {
                                                            this.tableOk = me;
                                                        }}
                                                        isShowRowSelect={false}
                                                        data={this.state.dataOk}
                                                        formConfig={[
                                                            {
                                                                isInTable: false,
                                                                form: {
                                                                    label: '??????id',
                                                                    field: 'zxCrProjectEvaluationScoreId',
                                                                    hide: true
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    width: 150,
                                                                    tooltip: 23,
                                                                    dataIndex: 'evalContent',
                                                                    key: 'evalContent'
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    field: 'evalContent'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    width: 150,
                                                                    tooltip: 23,
                                                                    dataIndex: 'scoreItem',
                                                                    key: 'scoreItem'
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    field: 'scoreItem'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    width: 150,
                                                                    dataIndex: 'standardScore',
                                                                    key: 'standardScore'
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    field: 'standardScore'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    width: 100,
                                                                    dataIndex: 'deductScore',
                                                                    key: 'deductScore'
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    field: 'deductScore'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    width: 150,
                                                                    dataIndex: 'getScore',
                                                                    key: 'getScore'
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    field: 'getScore'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????',
                                                                    dataIndex: 'remarks',
                                                                    width: 120,
                                                                    key: 'remarks'
                                                                },
                                                                form: {
                                                                    type: 'textarea',
                                                                    field: 'remarks',
                                                                    autoSize: {
                                                                        minRows: 1,
                                                                        maxRows: 3
                                                                    }
                                                                }
                                                            }
                                                        ]}
                                                        actionBtns={[

                                                        ]}
                                                    />
                                                </div>
                                            );
                                        },
                                        dependencies: ['checkStandard'],
                                        hide: (obj) => {
                                            let checkStandard = obj.form.getFieldValue('checkStandard');
                                            if (checkStandard) {
                                                if (checkStandard === '1') {
                                                    return false;
                                                } else {
                                                    return true;
                                                }
                                            } else {
                                                return true
                                            }
                                        }
                                    },


                                ]}
                                btns={[]}
                                tailFormItemLayout={{
                                    wrapperCol: {
                                        xs: {
                                            span: 24,
                                            offset: 0
                                        },
                                        sm: {
                                            span: 24,
                                            offset: 8
                                        }
                                    }
                                }}
                            />
                        </div>
                    </Spin>
                </Modal>
            </div>
        );
    }
}

export default index;