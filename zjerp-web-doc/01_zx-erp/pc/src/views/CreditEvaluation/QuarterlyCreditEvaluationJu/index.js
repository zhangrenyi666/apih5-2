import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Modal } from 'antd';
import moment from 'moment';
import s from "./style.less";
import Tree from "../../modules/tree";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'zxCrJYearCreditEvaId',
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
            ext1: curCompany?.ext1,
            masterID: '',
            defaultExpandedKeys: [],
            zxCrJYearCreditEvaId: '',
            period: '',
            customerId: null,
            listMasterIDss: null,
            customerIdss: null,
            catIDss: null,
            isLocked: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? true : false) : false,
        }
    }
    render() {
        const { companyId, companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { isLocked, ext1 } = this.state
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
                        apiName: 'getZxCrJYearCreditEvaList',
                        otherParams: {
                            orgID: companyId
                        }
                    }}
                    drawerShowToggle={(obj) => {
                        let { drawerIsShow } = obj;
                        if (!drawerIsShow) {
                            this.setState({ listMasterIDss: null, customerIdss: null, catIDss: null })
                            obj.btnCallbackFn.refresh();
                        }
                    }}
                    formConfig={[
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: 200,
                                onClick: 'detail',
                                filter: true,
                                fieldsConfig: { type: 'string' }
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'period',
                                key: 'period',
                                filter: true,
                                width: 150,
                                fieldsConfig: { type: 'year' },
                                render: (data) => {
                                    return data ? moment(data).format('YYYY') : ''
                                },
                            },
                        },
                        {
                            table: {
                                title: '???????????????',
                                dataIndex: 'checkDate',
                                key: 'checkDate',
                                format: 'YYYY-MM-DD',
                                width: 100
                            },
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
                                    } else if (data === '1') {
                                        return '?????????';
                                    } else {
                                        return ''
                                    }
                                }
                            },
                        }
                    ]}
                    method={{
                        //??????????????????
                        disabledForAdd: () => {
                            if (ext1 !== '1' || isLocked) return true
                            return false
                        },
                        funForAddSubmit: () => {
                            this.setState({
                                defaultExpandedKeys: []
                            })
                        },
                        //??????????????????
                        hideForAdd: (obj) => {
                            if (obj.btnCallbackFn.getActiveKey() === "1" || obj?.btnCallbackFn?.form?.getFieldValue?.('zxCrJYearCreditEvaId')) return true
                            return false
                        },
                        //????????????????????????
                        addSuccessCb: (obj) => {
                            obj._formData.period = moment(obj._formData.period).valueOf()
                            obj._formData.checkDate = moment(obj._formData.checkDate).valueOf()
                            this.props.myFetch('addZxCrJYearCreditEva', obj._formData).then(
                                async ({ data, success, message }) => {
                                    if (success) {
                                        await this.table.setDeawerValues(data)
                                        this.props.myFetch('getZxCrColCategoryTreeShu', { zxCrJYearCreditEvaId: data.zxCrJYearCreditEvaId })
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
                            if (data.length !== 1 || data[0].auditStatus === '1') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        //??????????????????????????????
                        editListSubmitCb: (obj) => {
                            obj.btnCallbackFn.clearSelectedRows();
                            this.props.myFetch('updateZxCrJYearCreditEva', obj._formData).then(
                                ({ data, success, message }) => {
                                    if (success) {
                                        this.table.setDeawerValues(data)
                                        // ???????????????
                                        this.props.myFetch('getZxCrColCategoryTreeShu', { zxCrJYearCreditEvaId: obj._formData.zxCrJYearCreditEvaId })
                                            .then(({ data, success }) => {
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
                                if (data[i].auditStatus === '1') {
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
                                    this.props.myFetch('batchDeleteUpdateZxCrJYearCreditEva', data).then(
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
                        disabledForStatus: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1 || data[0].auditStatus === '1') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        //????????????
                        updateZxCrJYearCreditEvaAuditStatus: (obj) => {
                            const { myFetch } = this.props;
                            let data = obj.btnCallbackFn.getSelectedRows();
                            confirm({
                                content: '???????????????????????????????',
                                onOk: () => {
                                    myFetch('updateZxCrJYearCreditEvaAuditStatus', data[0]).then(
                                        ({ success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.table.refresh();
                                                this.table.clearSelectedRows();
                                            } else {
                                                Msg.error(message);
                                            }
                                        }
                                    );
                                }
                            });

                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            var props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "QuarterlyCreditEvaluationJu"
                            }
                        }
                    }}
                    tabsWillChange={(obj, canChange) => {
                        this.table.getQnnForm().getValues().then(val => {
                            this.props.myFetch('getZxCrColCategoryTreeShu', { zxCrJYearCreditEvaId: val.zxCrJYearCreditEvaId })
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
                                        field: "zxCrJYearCreditEvaId",
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
                                        span: 12
                                    },
                                    {
                                        label: '???????????????',
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
                                        type: 'year',
                                        field: 'period',
                                        required: true,
                                        editDisabled: true,
                                        allowClear: false,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '??????',
                                        type: 'textarea',
                                        field: 'remarks',
                                        span: 12
                                    },
                                    {
                                        label: '??????',
                                        field: 'zxErpFileList',
                                        type: 'files',
                                        span: 12,
                                        fetchConfig: {
                                            apiName: 'upload'
                                        },
                                    },
                                ]
                            }
                        },
                        {
                            field: "tableqd",
                            name: "tableqd",
                            title: "??????",
                            disabled: function (obj) {
                                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxCrJYearCreditEvaId"))
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
                                                    catIDss: null,
                                                    listMasterIDss: null
                                                }, () => {
                                                    this.props.myFetch('getZxCrJYearCreditEvaItemList', {
                                                        masterID: this.table.form.getFieldValue('zxCrJYearCreditEvaId'),
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
                                                apiName: 'getZxCrJYearCreditEvaItemList',
                                                otherParams: {
                                                    masterID: this.table.form.getFieldValue('zxCrJYearCreditEvaId')
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
                                                            rowKey: 'zxCrJYearCreditEvaItemId',
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
                                                                    let params = obj.qnnTableInstance.getTableData()
                                                                    this.props.myFetch('updateZxCrJYearCreditEvaItemAll', params)
                                                                        .then(({ success, message }) => {
                                                                            if (success) {
                                                                                Msg.success(message)
                                                                                this.props.myFetch('getZxCrJYearCreditEvaItemList', {
                                                                                    masterID: this.table.form.getFieldValue('zxCrJYearCreditEvaId'),
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
                                                                    field: 'zxCrJYearCreditEvaItemId',
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
                                                                    title: '??????????????????',
                                                                    width: 130,
                                                                    dataIndex: 'companyScore',
                                                                    key: 'companyScore'
                                                                },
                                                                isInForm: false
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    width: 180,
                                                                    dataIndex: 'changeScore',
                                                                    key: 'changeScore',
                                                                    tdEdit: (obj) => {
                                                                        if (obj?.dlevel === '1' || obj?.lastLevel === 'D' || obj.props.actionBtns[0].hide) {
                                                                            return false
                                                                        }
                                                                        return true
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    placeholder: '?????????',
                                                                    field: 'changeScore',
                                                                    max: 5,
                                                                    min: -5,
                                                                    autoClearOver: true,
                                                                    onChange: (val, obj) => {
                                                                        let rowData = obj.qnnTableInstance.getEditedRowDataSync()
                                                                        if (!val || typeof (val) !== 'number') {
                                                                            rowData.lastScore = rowData.companyScore ? rowData.companyScore : 0
                                                                            obj.qnnTableInstance.setEditedRowData(rowData)
                                                                        }
                                                                        rowData.lastScore = ((rowData.companyScore ? rowData.companyScore : 0) + val).toFixed(1)  //?????????????????????
                                                                        obj.qnnTableInstance.setEditedRowData(rowData)
                                                                    }
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '?????????????????????',
                                                                    width: 160,
                                                                    dataIndex: 'lastScore',
                                                                    key: 'lastScore'
                                                                },
                                                                isInForm: false
                                                            },
                                                            {
                                                                table: {
                                                                    title: '???????????????',
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
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????????????????',
                                                                    width: 160,
                                                                    dataIndex: 'companyNum',
                                                                    key: 'companyNum',
                                                                    onClick: (obj) => {
                                                                        let { customerId, masterID, catID } = obj.rowData
                                                                        this.setState({
                                                                            customerIdss: customerId,
                                                                            catIDss: catID,
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
                                                            apiName: 'getCompanyListByCustomer',
                                                            otherParams: () => {
                                                                return {
                                                                    periodYear: this.table.form.getFieldValue('period') ? moment(this.table.form.getFieldValue('period')).format('YYYY') : null,
                                                                    masterID: this.state.listMasterIDss,
                                                                    customerId: this.state.customerIdss,
                                                                    catID: this.state.catIDss
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
                                                                    dataIndex: 'companyName',
                                                                    key: 'companyName',
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????????????????',
                                                                    dataIndex: 'customerName',
                                                                    key: 'customerName',
                                                                    width: 200,
                                                                }
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
                                                                    dataIndex: 'companyScore',
                                                                    key: 'companyScore',
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