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
                        //详细
                        DeetailForm: (obj) => {
                            return <DeetailForm isInQnnTable={obj.isInQnnTable}
                                {...this.props}
                                flowData={obj.qnnTableInstance.getSelectedRows()[0]}
                            />
                        },
                        //进度查询
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
                                title: '公司名称',
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
                                title: '评价编号',
                                dataIndex: 'reviewCode',
                                key: 'reviewCode',
                                width: 200,
                                filter: true,
                                fieldsConfig: { type: 'string' }
                            },
                        },
                        {
                            table: {
                                title: '评价期次',
                                dataIndex: 'period',
                                key: 'period',
                                width: 150,
                                filter: true,
                                fieldsConfig: { type: 'halfYear' },
                                render: (data) => {
                                    return data ? (moment(data).format('YYYY') + '/' + (moment(data).format('MM') === '12' ? '下半年' : '上半年')) : ''
                                },
                            },
                        },
                        {
                            table: {
                                title: '评价日期',
                                dataIndex: 'checkDate',
                                key: 'checkDate',
                                format: 'YYYY-MM-DD',
                                width: 100
                            },
                        },
                        {
                            table: {
                                title: '审核状态',
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
                                        { label: '待提交', value: '0' },
                                        { label: '审核中', value: '1' },
                                        { label: '审核完成', value: '2' },
                                        { label: '退回', value: '3' },
                                        { label: '未审核', value: '-1' },
                                    ]
                                },
                                render: (data) => {
                                    if (data === '0') {
                                        return '待提交';
                                    } else if (data === '1') {
                                        return '审核中';
                                    } else if (data === '2') {
                                        return '审核完成';
                                    } else if (data === '3') {
                                        return '退回';
                                    } else if (data === '-1') {
                                        return '未审核';
                                    }
                                }
                            },
                        }
                    ]}
                    method={{
                        //新增保存按钮
                        disabledForAdd: () => {
                            if (ext1 !== '2' || isLocked) return true
                            return false
                        },
                        funForAddSubmit: () => {
                            this.setState({
                                defaultExpandedKeys: []
                            })
                        },
                        //详细按钮禁用筛选
                        disabledFunDetail: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && (data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2')) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        //新增保存按钮
                        hideForAdd: (obj) => {
                            if (obj.btnCallbackFn.getActiveKey() === "1" || obj?.btnCallbackFn?.form?.getFieldValue?.('zxCrHalfYearCreditEvaId')) return true
                            return false
                        },
                        //进度查询按钮禁用
                        disabledFunComponent: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && data[0].workId) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        //新增保存按钮回调
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
                        //修改按钮禁用
                        disabledFunEdit: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1 || data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        //修改清单保存按钮回调
                        editListSubmitCb: (obj) => {
                            obj.btnCallbackFn.clearSelectedRows();
                            this.props.myFetch('updateZxCrHalfYearCreditEva', obj._formData).then(
                                ({ data, success, message }) => {
                                    if (success) {
                                        this.table.setDeawerValues(data)
                                        // 获取树数据
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
                        //删除按钮禁用
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
                        //删除按钮回调
                        cbForDelete: (obj) => {
                            confirm({
                                content: '确定删除选中的数据吗?',
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
                        //流程按钮禁用
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
                            title: "基础信息",
                            content: {
                                formConfig: [
                                    {
                                        type: "string",
                                        label: "主键ID",
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
                                        label: '公司名称',
                                        field: 'companyName',
                                        disabled: true,
                                        type: 'string',
                                        initialValue: companyName,
                                        span: 12
                                    },
                                    {
                                        label: '评价编号',
                                        field: 'reviewCode',
                                        type: 'string',
                                        span: 12
                                    },
                                    {
                                        label: '公司评价日期',
                                        type: 'date',
                                        field: 'checkDate',
                                        initialValue: () => {
                                            return moment(new Date()).format('YYYY-MM-DD')
                                        },
                                        required: true,
                                        span: 12
                                    },
                                    {
                                        label: '评价期次',
                                        type: 'halfYear',
                                        field: 'period',
                                        editDisabled: true,
                                        allowClear: false,
                                        placeholder: '请选择',
                                        span: 12,
                                        required: true
                                    },
                                    {
                                        label: '备注',
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
                            title: "明细",
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
                                            modalType="common" //common  drawer  抽屉出现方式或者普通的
                                            visible
                                            selectModal="0" //0不可选  1单选(默认)  2多选（暂不可用）
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
                                                                label: '保存',
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
                                                                    label: '主键id',
                                                                    field: 'zxCrHalfYearCreditEvaItemId',
                                                                    hide: true
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '协作单位名称',
                                                                    width: 200,
                                                                    tooltip: 12,
                                                                    dataIndex: 'customerName',
                                                                    key: 'customerName'
                                                                },
                                                                isInForm: false
                                                            },
                                                            {
                                                                table: {
                                                                    title: '组织机构代码',
                                                                    width: 200,
                                                                    tooltip: 23,
                                                                    dataIndex: 'orgCertificate',
                                                                    key: 'orgCertificate'
                                                                },
                                                                isInForm: false
                                                            },
                                                            {
                                                                table: {
                                                                    title: '协作单位负责人',
                                                                    width: 160,
                                                                    tooltip: 23,
                                                                    dataIndex: 'chargeMan',
                                                                    key: 'chargeMan',
                                                                },
                                                                isInForm: false
                                                            },
                                                            {
                                                                table: {
                                                                    title: '承建工程合同额(万元)',
                                                                    width: 200,
                                                                    dataIndex: 'contractAmt',
                                                                    key: 'contractAmt'
                                                                },
                                                                isInForm: false
                                                            },
                                                            {
                                                                table: {
                                                                    title: '项目考核得分',
                                                                    width: 130,
                                                                    dataIndex: 'projectScore',
                                                                    key: 'projectScore'
                                                                },
                                                                isInForm: false
                                                            },
                                                            {
                                                                table: {
                                                                    title: '公司加减分',
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
                                                                    placeholder: '请选择',
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
                                                                        rowData.companyScore = (rowData.projectScore ? rowData.projectScore : 0) + rowData.changeScore //公司考核得分
                                                                        obj.qnnTableInstance.setEditedRowData(rowData)
                                                                    }
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '公司考核得分',
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
                                                                    title: '项目评审次数',
                                                                    dataIndex: 'checkNum',
                                                                    key: 'checkNum',
                                                                    width: 130,
                                                                },
                                                                isInForm: false
                                                            },
                                                            {
                                                                table: {
                                                                    title: '公司评价等级',
                                                                    width: 130,
                                                                    dataIndex: 'lastLevel',
                                                                    key: 'lastLevel'
                                                                },
                                                                isInForm: false
                                                            },
                                                            {
                                                                table: {
                                                                    title: '有无直接降为D的行为',
                                                                    width: 160,
                                                                    dataIndex: 'dlevel',
                                                                    key: 'dlevel',
                                                                    render: (h) => {
                                                                        if (h === '1') {
                                                                            return '有'
                                                                        } else {
                                                                            return '无'
                                                                        }
                                                                    }
                                                                },
                                                                isInForm: false
                                                            },
                                                            {
                                                                table: {
                                                                    title: '工程所属项目个数',
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
                                                                工程所属项目
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
                                                                    title: '工程所属项目',
                                                                    width: 200,
                                                                    tooltip: 23,
                                                                    fixed: 'left',
                                                                    dataIndex: 'projectName',
                                                                    key: 'projectName',
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '协作单位名称',
                                                                    dataIndex: 'customerName',
                                                                    key: 'customerName',
                                                                    width: 200,
                                                                },
                                                            },
                                                            {
                                                                table: {
                                                                    title: '承建工程合同额(万元)',
                                                                    width: 170,
                                                                    dataIndex: 'contractAmt',
                                                                    key: 'contractAmt'
                                                                },
                                                            },
                                                            {
                                                                table: {
                                                                    title: '项目考核得分',
                                                                    dataIndex: 'projectScore',
                                                                    key: 'projectScore',
                                                                    width: 120,
                                                                },
                                                            },
                                                            {
                                                                table: {
                                                                    title: '项目评审次数',
                                                                    dataIndex: 'checkNum',
                                                                    key: 'checkNum',
                                                                    width: 120,
                                                                },
                                                            },
                                                            {
                                                                table: {
                                                                    title: '有无直接降D行为',
                                                                    width: 150,
                                                                    dataIndex: 'dlevel',
                                                                    key: 'dlevel',
                                                                    render: (h) => {
                                                                        if (h === '1') {
                                                                            return '有'
                                                                        } else {
                                                                            return '无'
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