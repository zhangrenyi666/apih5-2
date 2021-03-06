import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Modal } from 'antd';
import QuarterlyCreditEvaluationForm from './form';
import moment from 'moment';
import s from "./style.less";
import Tree from "../../modules/tree";
import DeetailForm from './detail';
import Operation from './operation';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'zxCrHalfYearCreditEvaId',
        size: 'small'
    },
    drawerConfig: {
        width: '70%'
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
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            masterID: '',
            ext1: curCompany?.ext1,
            ItemcatID: '',
            defaultExpandedKeys: [],
            period: '',
            customerId: null,
            listMasterIDss: null,
            customerIdss: null,
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            isLocked: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? true : false) : false,
        }
    }
    render() {
        const { companyId, companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { departmentId, isLocked, ext1 } = this.state
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxCrHalfYearCreditEvaList',
                        otherParams: {
                            orgID: departmentId
                        }
                    }}
                    drawerShowToggle={(obj) => {
                        let { drawerIsShow } = obj;
                        if (!drawerIsShow) {
                            this.setState({ listMasterIDss: null, customerIdss: null, ItemcatID: null })
                            obj.btnCallbackFn.refresh();
                        }
                    }}
                    componentsKey={{
                        QuarterlyCreditEvaluationForm: (obj) => {
                            return <QuarterlyCreditEvaluationForm
                                {...this.props}
                                isInQnnTable={obj.isInQnnTable}
                                btnCallbackFn={obj.btnCallbackFn}
                                flowData={obj.qnnTableInstance.getSelectedRows()[0]}
                            />
                        },
                        //??????
                        DeetailForm: (obj) => {
                            return <DeetailForm isInQnnTable={obj.isInQnnTable}
                                {...this.props}
                                flowData={obj.qnnTableInstance.getSelectedRows()[0]}
                            />
                        },
                        //????????????
                        Operation: (obj) => {
                            return <Operation
                                {...this.props}
                                btnCallbackFn={obj.btnCallbackFn}
                                apiName={'openFlowByReport'}
                            />
                        },
                    }}
                    formConfig={[
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'companyName',
                                key: 'companyName',
                                width: 200,
                                onClick: 'detail',
                                filter: true,
                                fieldsConfig: { type: 'string' }
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'reviewCode',
                                key: 'reviewCode',
                                width: 200,
                                filter: true,
                                fieldsConfig: { type: 'string' }
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'period',
                                key: 'period',
                                width: 150,
                                filter: true,
                                fieldsConfig: { type: 'halfYear' },
                                render: (data) => {
                                    return data ? (moment(data).format('YYYY') + '/' + (moment(data).format('MM') === '12' ? '?????????' : '?????????')) : ''
                                },
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'checkDate',
                                key: 'checkDate',
                                format: 'YYYY-MM-DD',
                                width: 100
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                width: 100,
                                filter: true,
                                fieldsConfig: {
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        { label: '?????????', value: '0' },
                                        { label: '?????????', value: '1' },
                                        { label: '????????????', value: '2' },
                                        { label: '??????', value: '3' },
                                        { label: '?????????', value: '-1' },
                                    ]
                                },
                                render: (data) => {
                                    if (data === '0') {
                                        return '?????????';
                                    } else if (data === '1') {
                                        return '?????????';
                                    } else if (data === '2') {
                                        return '????????????';
                                    } else if (data === '3') {
                                        return '??????';
                                    } else if (data === '-1') {
                                        return '?????????';
                                    }
                                }
                            },
                        }
                    ]}
                    method={{
                        //??????????????????
                        disabledForAdd: () => {
                            if (ext1 !== '2' || isLocked) return true
                            return false
                        },
                        funForAddSubmit: () => {
                            this.setState({
                                defaultExpandedKeys: []
                            })
                        },
                        //????????????????????????
                        disabledFunDetail: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && (data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2')) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        //??????????????????
                        hideForAdd: (obj) => {
                            if (obj.btnCallbackFn.getActiveKey() === "1" || obj?.btnCallbackFn?.form?.getFieldValue?.('zxCrHalfYearCreditEvaId')) return true
                            return false
                        },
                        //????????????????????????
                        disabledFunComponent: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && data[0].workId) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        //????????????????????????
                        addSuccessCb: (obj) => {
                            obj._formData.period = moment(obj._formData.period).valueOf()
                            obj._formData.checkDate = moment(obj._formData.checkDate).valueOf()
                            this.props.myFetch('addZxCrHalfYearCreditEva', obj._formData).then(
                                async ({ data, success, message }) => {
                                    if (success) {
                                        await this.table.setDeawerValues(data)
                                        this.props.myFetch('getZxCrColCategoryTreeShu', { zxCrHalfYearCreditEvaId: data.zxCrHalfYearCreditEvaId })
                                            .then(({ data, success, message }) => {
                                                if (success) {
                                                    this.table.form.setFieldsValue({ treeData: data })
                                                } else {
                                                    Msg.error(message)
                                                }
                                            })
                                            .then(() => {
                                                obj.btnCallbackFn.setActiveKey('1');
                                            })
                                    } else {
                                        Msg.error(message)
                                    }
                                }
                            );
                        },
                        //??????????????????
                        disabledFunEdit: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1 || data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        //??????????????????????????????
                        editListSubmitCb: (obj) => {
                            obj.btnCallbackFn.clearSelectedRows();
                            this.props.myFetch('updateZxCrHalfYearCreditEva', obj._formData).then(
                                ({ data, success, message }) => {
                                    if (success) {
                                        this.table.setDeawerValues(data)
                                        // ???????????????
                                        this.props.myFetch('getZxCrColCategoryTreeShu', { zxCrHalfYearCreditEvaId: obj._formData.zxCrHalfYearCreditEvaId })
                                            .then(({ data, success, message }) => {
                                                if (success) {
                                                    this.table.form.setFieldsValue({ treeData: data })
                                                }
                                            })
                                            .then(() => {
                                                obj.btnCallbackFn.setActiveKey('1');
                                                this.table.refresh();
                                            })
                                    } else {
                                        Msg.error(message)
                                    }
                                }
                            );
                        },
                        //??????????????????
                        disabledForDelete: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            let disabledLength = 0
                            for (var i = 0; i < data.length; i++) {
                                if (data[i].apih5FlowStatus === '1' || data[i].apih5FlowStatus === '2') {
                                    disabledLength++
                                }
                            }
                            if (disabledLength > 0 || data.length === 0) return true
                        },
                        //??????????????????
                        cbForDelete: (obj) => {
                            confirm({
                                content: '???????????????????????????????',
                                onOk: () => {
                                    let data = obj.btnCallbackFn.getSelectedRows();
                                    this.props.myFetch('batchDeleteUpdateZxCrHalfYearCreditEva', data).then(
                                        ({ success }) => {
                                            if (success) {
                                                this.table.refresh();
                                                this.table.clearSelectedRows();
                                            }
                                        }
                                    )
                                }
                            })
                        },
                        //??????????????????
                        disabledForFlow: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1 || data[0].apih5FlowStatus !== '-1') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            var props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "QuarterlyCreditEvaluation"
                            }
                        }
                    }}
                    tabsWillChange={(obj, canChange) => {
                        this.table.getQnnForm().getValues().then(val => {
                            this.props.myFetch('getZxCrColCategoryTreeShu', { zxCrHalfYearCreditEvaId: val.zxCrHalfYearCreditEvaId })
                                .then(({ data, success, message }) => {
                                    if (success) {
                                        this.table.form.setFieldsValue({ treeData: data })
                                        canChange(true);
                                    } else {
                                        Msg.error(message)
                                    }
                                })
                        })
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
                                        type: "qnnTable",
                                        incToForm: true,
                                        field: "treeData",
                                        hide: true,
                                        qnnTableConfig: {
                                            antd: {
                                                rowKey: 'id',
                                                size: 'small'
                                            },
                                        }
                                    },
                                    {
                                        type: "string",
                                        field: "companyId",
                                        initialValue: companyId,
                                        hide: true
                                    },
                                    {
                                        label: '????????????',
                                        field: 'companyName',
                                        disabled: true,
                                        type: 'string',
                                        initialValue: companyName,
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: 'reviewCode',
                                        type: 'string',
                                        span: 12
                                    },
                                    {
                                        label: '??????????????????',
                                        type: 'date',
                                        field: 'checkDate',
                                        initialValue: () => {
                                            return moment(new Date()).format('YYYY-MM-DD')
                                        },
                                        required: true,
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        type: 'halfYear',
                                        field: 'period',
                                        editDisabled: true,
                                        allowClear: false,
                                        placeholder: '?????????',
                                        span: 12,
                                        required: true
                                    },
                                    {
                                        label: '??????',
                                        type: 'textarea',
                                        field: 'remarks',
                                        span: 12
                                    },
                                ]
                            }
                        },
                        {
                            field: "tableqd",
                            name: "tableqd",
                            title: "??????",
                            disabled: function (obj) {
                                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxCrHalfYearCreditEvaId"))
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
                                        <Tree
                                            selectText={false}
                                            modalType="common" //common  drawer  ?????????????????????????????????
                                            visible
                                            selectModal="0" //0?????????  1??????(??????)  2????????????????????????
                                            myFetch={this.props.myFetch}
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
                                                    defaultExpandedKeys: node.bsid.split(','),
                                                    masterID: node.id,
                                                    catID: node.catID,
                                                    customerIdss: null,
                                                    ItemcatID: null,
                                                    listMasterIDss: null
                                                }, () => {
                                                    this.props.myFetch('getZxCrHalfYearCreditEvaItemList', {
                                                        masterID: this.table.form.getFieldValue('zxCrHalfYearCreditEvaId'),
                                                        catID: node.catID,
                                                    }).then(({ data, success }) => {
                                                        if (success) {
                                                            this.tableSK.setTableData(data)
                                                            this.tableSK.setPaging({ page: 1 })
                                                        }
                                                    })
                                                    this.tableSK2.refresh()
                                                })

                                            }}
                                            data={this.table.form.getFieldValue('treeData')}
                                            keys={{
                                                label: "name",
                                                value: "id",
                                                children: "childrenList"
                                            }}
                                        />
                                    </div>
                                    <div className={s.rootr}>
                                        <QnnForm
                                            fetch={this.props.myFetch}
                                            fetchConfig={{
                                                apiName: 'getZxCrHalfYearCreditEvaItemList',
                                                otherParams: {
                                                    masterID: this.table.form.getFieldValue('zxCrHalfYearCreditEvaId')
                                                },
                                                success: ({ data, success }) => {
                                                    if (success) {
                                                        this.tableSK.setTableData(data)
                                                    }
                                                }
                                            }}
                                            formConfig={[
                                                {
                                                    type: 'qnnTable',
                                                    field: 'tableOne',
                                                    incToForm: true,
                                                    qnnTableConfig: {
                                                        antd: {
                                                            rowKey: 'zxCrHalfYearCreditEvaItemId',
                                                            size: 'small'
                                                        },
                                                        paginationConfig: {
                                                            position: 'bottom'
                                                        },
                                                        isShowRowSelect: false,
                                                        wrappedComponentRef: (me) => {
                                                            this.tableSK = me;
                                                        },
                                                        actionBtns: [
                                                            {
                                                                name: 'diyButton',
                                                                type: 'primary',
                                                                label: '??????',
                                                                field: "diyButton",
                                                                hide: props.clickCb.rowInfo.name === 'detail',
                                                                onClick: (obj) => {
                                                                    let params = obj.qnnTableInstance.getTableAllData()
                                                                    this.props.myFetch('batchUpdateZxCrHalfYearCreditEvaItem', params)
                                                                        .then(({ success, message }) => {
                                                                            if (success) {
                                                                                Msg.success(message)
                                                                                this.props.myFetch('getZxCrHalfYearCreditEvaItemList', {
                                                                                    masterID: this.table.form.getFieldValue('zxCrHalfYearCreditEvaId'),
                                                                                    catID: this.state.catID
                                                                                }).then(({ data, success }) => {
                                                                                    if (success) {
                                                                                        this.tableSK.setTableData(data)
                                                                                    }
                                                                                })
                                                                            } else {
                                                                                Msg.error(message)
                                                                            }
                                                                        })
                                                                }
                                                            }
                                                        ],
                                                        formConfig: [
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
                                                                    width: 200,
                                                                    tooltip: 12,
                                                                    dataIndex: 'customerName',
                                                                    key: 'customerName'
                                                                },
                                                                isInForm: false
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????????????????',
                                                                    width: 200,
                                                                    tooltip: 23,
                                                                    dataIndex: 'orgCertificate',
                                                                    key: 'orgCertificate'
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
                                                                    title: '??????????????????',
                                                                    width: 130,
                                                                    dataIndex: 'projectScore',
                                                                    key: 'projectScore'
                                                                },
                                                                isInForm: false
                                                            },
                                                            {
                                                                table: {
                                                                    title: '???????????????',
                                                                    width: 180,
                                                                    dataIndex: 'changeScore',
                                                                    key: 'changeScore',
                                                                    tdEdit: (obj) => {
                                                                        if (obj?.dlevel === '1' || obj.props.actionBtns[0].hide) {
                                                                            return false
                                                                        }
                                                                        return true
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    placeholder: '?????????',
                                                                    field: 'changeScore',
                                                                    max: 3,
                                                                    min: -3,
                                                                    autoClearOver: true,
                                                                    onChange: (val, obj) => {
                                                                        let rowData = obj.qnnTableInstance.getEditedRowDataSync()
                                                                        if (!val) {
                                                                            rowData.companyScore = rowData.projectScore ? rowData.projectScore : 0
                                                                            obj.qnnTableInstance.setEditedRowData(rowData)
                                                                            return
                                                                        }
                                                                        rowData.companyScore = (rowData.projectScore ? rowData.projectScore : 0) + rowData.changeScore //??????????????????
                                                                        obj.qnnTableInstance.setEditedRowData(rowData)
                                                                    }
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????????????????',
                                                                    width: 160,
                                                                    dataIndex: 'companyScore',
                                                                    key: 'companyScore',
                                                                    render: (h) => {
                                                                        return Math.round(h * 100) / 100
                                                                    }
                                                                },
                                                                isInForm: false
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????????????????',
                                                                    dataIndex: 'checkNum',
                                                                    key: 'checkNum',
                                                                    width: 130,
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
                                                                    title: '??????????????????D?????????',
                                                                    width: 160,
                                                                    dataIndex: 'dlevel',
                                                                    key: 'dlevel',
                                                                    render: (h) => {
                                                                        if (h === '1') {
                                                                            return '???'
                                                                        } else {
                                                                            return '???'
                                                                        }
                                                                    }
                                                                },
                                                                isInForm: false
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????????????????',
                                                                    width: 160,
                                                                    dataIndex: 'projectNum',
                                                                    key: 'projectNum',
                                                                    onClick: (obj) => {
                                                                        let { customerId, masterID, catID } = obj.rowData
                                                                        this.setState({
                                                                            customerIdss: customerId,
                                                                            ItemcatID: catID,
                                                                            listMasterIDss: masterID
                                                                        }, () => {
                                                                            this.tableSK2.refresh()
                                                                        })
                                                                    },
                                                                    fixed: 'right'
                                                                },
                                                            },
                                                        ]
                                                    }
                                                },
                                                {
                                                    type: "component",
                                                    field: "component7",
                                                    Component: obj => {
                                                        return (
                                                            <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                                ??????????????????
                                                            </div>
                                                        );
                                                    }
                                                },
                                                {
                                                    type: 'qnnTable',
                                                    field: 'tableTwo',
                                                    qnnTableConfig: {
                                                        wrappedComponentRef: (me) => {
                                                            this.tableSK2 = me;
                                                        },
                                                        antd: {
                                                            rowKey: 'zxCrProjectEvaluationId',
                                                            size: 'small'
                                                        },
                                                        paginationConfig: false,
                                                        fetchConfig: {
                                                            apiName: 'getProjectListByCreditEvaItem',
                                                            otherParams: () => {
                                                                return {
                                                                    masterID: this.state.listMasterIDss,
                                                                    customerId: this.state.customerIdss,
                                                                    catID: this.state.ItemcatID
                                                                }

                                                            },
                                                        },
                                                        formConfig: [
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
                                                                    title: '??????????????????',
                                                                    width: 200,
                                                                    tooltip: 23,
                                                                    fixed: 'left',
                                                                    dataIndex: 'projectName',
                                                                    key: 'projectName',
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????????????????',
                                                                    dataIndex: 'customerName',
                                                                    key: 'customerName',
                                                                    width: 200,
                                                                },
                                                            },
                                                            {
                                                                table: {
                                                                    title: '?????????????????????(??????)',
                                                                    width: 170,
                                                                    dataIndex: 'contractAmt',
                                                                    key: 'contractAmt'
                                                                },
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????????????????',
                                                                    dataIndex: 'projectScore',
                                                                    key: 'projectScore',
                                                                    width: 120,
                                                                },
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????????????????',
                                                                    dataIndex: 'checkNum',
                                                                    key: 'checkNum',
                                                                    width: 120,
                                                                },
                                                            },
                                                            {
                                                                table: {
                                                                    title: '???????????????D??????',
                                                                    width: 150,
                                                                    dataIndex: 'dlevel',
                                                                    key: 'dlevel',
                                                                    render: (h) => {
                                                                        if (h === '1') {
                                                                            return '???'
                                                                        } else {
                                                                            return '???'
                                                                        }
                                                                    }
                                                                },
                                                            },
                                                        ]
                                                    }
                                                }
                                            ]}
                                        />
                                    </div>
                                </div>;
                            }
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;