import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import s from "./style.less";
import Tree from "../../modules/tree";
import { message as Msg, Modal, Spin } from 'antd';
import QnnForm from "../../modules/qnn-form";
const confirm = Modal.confirm;
const config = {

    // infoAlert: function (selectedRows) {
    //     return '已选择 ' + selectedRows.length + '项';
    // },
    // getRowSelection: function (obj) {
    //     return {
    //         //设置某行为禁止选中
    //         getCheckboxProps: record => ({
    //             name: record.name,
    //             // disabled: record.flowStatus === '审核中', // Column configuration not to be checked
    //         }),
    //     }
    // },
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
        rowKey: 'zxCrJYearCreditEvaItemId',
        size: 'small'
    },
    isShowRowSelect: false,
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
    fetchConfig: {
        apiName: 'getZxCrHalfYearCreditEvaList',
    },
    antd: {
        rowKey: 'zxCrHalfYearCreditEvaId',
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
            // activeIndex: 0,
            visibleSendDataDetail: false,
            loadingSendDataDetail: false,
            visibleSendData: false,
            loadingSendData: false,
            name: '',
            nameTwo: '',
            defaultExpandedKeys: [],
            defaultExpandedKeysTwo: [],
            zxCrJYearCreditEvaId: '',
            treeData: null,
            period: '',
            periodTwo: '',
            orgNameTwo: '',
            QnnTableItemData: [],
            treeDataTwo: null,
            idTwo: '',
            catIDzhiTwo: '',
            resIDzhiTwo: '',
            parentIDTwo: '',
            lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : ''
        }
    }
    // 获取树-数据
    getTreeData(val) {
        const { myFetch } = this.props;
        myFetch('getZxCrColCategoryTreeShu', {
            zxCrJYearCreditEvaId: val
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        treeData: data,
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
        const { ext1, departmentId, companyId, projectId,companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { lockProjectId } = this.state;
        let jurisdiction = departmentId;
        if (lockProjectId !== 'all' && lockProjectId !== '') {
            jurisdiction = lockProjectId;
        } else {
            if (ext1 === '1' || ext1 === '2') {
                jurisdiction = companyId;
            } else if (ext1 === '3' || ext1 === '4') {
                jurisdiction = projectId;
            } else { }
        }
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
                    antd={{
                        rowKey: 'zxCrJYearCreditEvaId',
                        size: 'small',
                        // onRow: (record,index) => {
                        //     return {
                        //         // onClick: this.clickRow.bind(this,record.no)
                        //         onClick: () => {
                        //             this.setState({
                        //                 activeIndex: (index)//获取点击行的索引
                        //             })
                        //         }
                        //     };
                        // },
                        // rowClassName: (record, index) => {//record代表表格行的内容，index代表行索引
                        //     //判断索引相等时添加行的高亮样式
                        //     let _this = this;
                        //     let clasName = 'ant-table-row-level-' + this.state.activeIndex;
                        //     $('.' + clasName).css('background', 'red !important');
                        //     return ''
                        //     // return index === this.state.activeIndex ? $('[data-row-key=1ETLAKICE00G080012AC00008BB6DA6B]').children().css("background","red !important") : "";
                        // }
                    }}
                    fetchConfig={{
                        apiName: 'getZxCrJYearCreditEvaList'
                    }}
                    actionBtns={[
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
                            onClick: (obj) => {
                                obj.btnCallbackFn.setActiveKey('0');
                                this.setState({
                                    name: '',
                                    defaultExpandedKeys: []
                                })
                            },
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    field: 'cancel',
                                    label: '取消',
                                },
                                {
                                    name: 'submitDiy',
                                    type: 'primary',
                                    field: 'submit',
                                    label: '保存',
                                    onClick: (obj) => {
                                        this.props.myFetch('addZxCrJYearCreditEva', obj._formData).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    // 新增接口，返回个主键id
                                                    this.setState({
                                                        zxCrJYearCreditEvaId: data.zxCrJYearCreditEvaId,
                                                        period: data.period,
                                                        orgName: data.orgName
                                                    })
                                                    // 获取树数据
                                                    this.getTreeData(data.zxCrJYearCreditEvaId);
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
                            label: '修改',
                            willExecute: (obj) => {
                                if (obj.selectedRows[0].auditStatus === '1') {
                               
                                    Msg.warn('已审核的数据不能修改');
                                    return false
                                } else {
                                    this.setState({
                                        treeData: null,
                                        defaultExpandedKeys: [],
                                        QnnTableItemData: [],
                                        name: ''
                                    }, () => {
                                        obj.btnCallbackFn.setActiveKey('0');
                                        this.setState({
                                            zxCrJYearCreditEvaId: obj.selectedRows[0].zxCrJYearCreditEvaId,
                                            period: obj.selectedRows[0].period,
                                            orgName: obj.selectedRows[0].orgName
                                        })
                                        // 获取树数据
                                        this.getTreeData(obj.selectedRows[0].zxCrJYearCreditEvaId);
                                    })
                                }
                                this.table.clearSelectedRows();

                            },
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    field: 'cancel',
                                    label: '取消'
                                },
                                {
                                    name: 'submitEdit',
                                    type: 'primary',
                                    field: 'submit',
                                    label: '保存',
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
                                        this.props.myFetch('updateZxCrJYearCreditEva', obj._formData).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    // 获取树数据
                                                    this.getTreeData(obj._formData.zxCrJYearCreditEvaId);
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
                            label: '删除',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {
                                const { myFetch } = this.props;
                                let arry = [];
                                for (let m = 0; m < obj.selectedRows.length; m++) {
                                    if (obj.selectedRows[m].auditStatus === '1') {
                                        //存在已审核的数据
                                        arry.push(obj.selectedRows[m].auditStatus);
                                    }
                                }
                                if (arry.length > 0) {
                                    Msg.warn('请选择未审核的数据！')
                                } else {
                                    confirm({
                                        content: '确定删除选中的数据吗?',
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZxCrJYearCreditEva', obj.selectedRows).then(
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
                            label: '审核',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {
                                const { myFetch } = this.props;
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].auditStatus === '1') {
                                        obj.btnCallbackFn.closeDrawer();
                                        this.table.clearSelectedRows();
                                        Msg.warn('已审核的不能审核!');
                                    } else {
                                        confirm({
                                            content: '确定审核选中的数据吗?',
                                            onOk: () => {
                                                myFetch('updateZxCrJYearCreditEvaAuditStatus', obj.selectedRows[0]).then(
                                                    ({ data, success, message }) => {
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
                                } else {
                                    Msg.warn('只能审核一条数据！')
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
                            title: "基础信息",
                            content: {
                                formConfig: [
                                    {
                                        type: "string",
                                        label: "主键ID",
                                        field: "zxCrJYearCreditEvaId",
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
                                        label: '机构名称',
                                        field: 'orgName',
                                        disabled: true,
                                        type: 'string',
                                        initialValue: companyName,
                                        span: 8
                                    },
                                    {
                                        label: '评价期次',
                                        type: 'halfYear',
                                        field: 'period',
                                        editDisabled: true,
                                        allowClear: false,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '评价日期',
                                        type: 'date',
                                        field: 'dateTime',
                                        initialValue: () => {
                                            return moment(new Date()).format('YYYY-MM-DD')
                                        },
                                        span: 8
                                    },
                                    {
                                        label: '备注',
                                        type: 'textarea',
                                        field: 'remarks',
                                        autoSize: {
                                            minRows: 1,
                                            maxRows: 3
                                        },
                                        span: 16,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 12 },
                                                sm: { span: 4 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 12 },
                                                sm: { span: 20 }
                                            }
                                        }
                                    },
                                    {
                                        label: '附件',
                                        field: 'fileList',
                                        type: 'files',
                                        fetchConfig: {
                                            apiName: 'upload'
                                        },
                                        span: 8,
                                        accept: '.xlsx'
                                    }
                                ]
                            }
                        },
                        {
                            field: "tableqd",
                            name: "tableqd",
                            title: "明细",
                            disabled: (obj) => {
                                var rowData = obj.clickCb.rowData;
                                if (rowData && rowData.zxCrJYearCreditEvaId) {
                                    return false;
                                } else if (this.state.zxCrJYearCreditEvaId != '') {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            content: props => {
                                let drawerTitile = props.Pstate.drawerDetailTitle;
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
                                            modalType="common" //common  drawer  抽屉出现方式或者普通的
                                            visible
                                            selectModal="0" //0不可选  1单选(默认)  2多选（暂不可用）
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
                                                    name: ''
                                                }, () => {
                                                    this.setState({
                                                        defaultExpandedKeys: node.bsid.split(','),
                                                        name: node.name
                                                    })
                                                    const { myFetch } = this.props;
                                                    myFetch('getZxCrJYearCreditEvaItemInit', {
                                                        period: this.state.period,
                                                        catID: node.catID,
                                                        resID: node.resID,
                                                        parentID: node.parentID,
                                                        zxCrJYearCreditEvaId: this.state.zxCrJYearCreditEvaId,
                                                        orgName: this.state.orgName,
                                                        departmentId: jurisdiction ? jurisdiction : '',
                                                        projectId: projectId ? projectId : '',
                                                        companyId: companyId ? companyId : ''
                                                    }).then(
                                                        ({ data, success, message }) => {
                                                            if (success) {
                                                                // ？？？
                                                                this.setState({
                                                                    // QnnTableItemData: drawerTitile === '详情' ? data.map((item) => {
                                                                    //     item.zxCrJYearCreditEvaItemId = null;
                                                                    //     return item
                                                                    // }) : data
                                                                    QnnTableItemData: data
                                                                })
                                                            } else {
                                                                Msg.error(message)
                                                            }
                                                        }
                                                    );
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
                                        <QnnForm
                                            fetch={this.props.myFetch}
                                            upload={this.props.myUpload}
                                            headers={{
                                                token: this.props.loginAndLogoutInfo.loginInfo.token
                                            }}
                                            wrappedComponentRef={(me) => {
                                                this.inputForm = me;
                                            }}
                                            formItemLayout={{
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 6 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 16 }
                                                }
                                            }}
                                            formConfig={[
                                                {
                                                    type: 'qnnTable',
                                                    field: 'zxItemList',
                                                    incToForm: true,
                                                    initialValue: this.state.QnnTableItemData,
                                                    qnnTableConfig: {
                                                        antd: {
                                                            rowKey: 'zxCrJYearCreditEvaItemId',
                                                            size: 'small'
                                                        },
                                                        ...configItem,
                                                        tableTdEdit: drawerTitile === '详情' ? false : true,
                                                        formConfig: [
                                                            {
                                                                isInTable: false,
                                                                form: {
                                                                    label: '主键id',
                                                                    field: 'zxCrJYearCreditEvaItemId',
                                                                    hide: true
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '组织机构代码',
                                                                    width: 150,
                                                                    tooltip: 23,
                                                                    dataIndex: 'orgCertificate',
                                                                    key: 'orgCertificate'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '协作单位名称',
                                                                    width: 160,
                                                                    tooltip: 23,
                                                                    dataIndex: 'customerName',
                                                                    key: 'customerName'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '工程所属公司',
                                                                    dataIndex: 'projectName',
                                                                    key: 'projectName'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '进场日期',
                                                                    format: 'YYYY-MM-DD',
                                                                    dataIndex: 'inDate',
                                                                    key: 'inDate'
                                                                },
                                                                form: {
                                                                    type: 'date'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '退场日期',
                                                                    format: 'YYYY-MM-DD',
                                                                    dataIndex: 'outDate',
                                                                    key: 'outDate'
                                                                },
                                                                form: {
                                                                    type: 'date'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '承建工程合同额(万元)',
                                                                    dataIndex: 'contractAmt',
                                                                    key: 'contractAmt'
                                                                },
                                                                form: {
                                                                    type: 'number'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '工程所属项目个数',
                                                                    dataIndex: 'projectNum',
                                                                    key: 'projectNum'
                                                                },
                                                                form: {
                                                                    type: 'number'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '参建工程所属公司个数',
                                                                    dataIndex: 'compNum',
                                                                    key: 'compNum'
                                                                },
                                                                form: {
                                                                    type: 'number'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '评价次数',
                                                                    dataIndex: 'checkNum',
                                                                    key: 'checkNum'
                                                                },
                                                                form: {
                                                                    type: 'number'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '局信用评价得分(上半年)',
                                                                    dataIndex: 'firstSoce',
                                                                    key: 'firstSoce',
                                                                    onClick: (val) => {
                                                                        this.setState({
                                                                            visibleSendData: true,
                                                                            loadingSendData: false
                                                                        })
                                                                    }
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '局评价等级(上半年)',
                                                                    dataIndex: 'firstLevel',
                                                                    key: 'firstLevel'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '局信用评价得分(下半年)',
                                                                    dataIndex: 'secondScore',
                                                                    key: 'secondScore'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '局评价等级(下半年)',
                                                                    dataIndex: 'secondLevel',
                                                                    key: 'secondLevel'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '加减分项得分',
                                                                    dataIndex: 'scoreOfAdditionSubtraction',
                                                                    key: 'scoreOfAdditionSubtraction',
                                                                    tdEdit: drawerTitile === '详情' ? false : true,
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                                        if (typeof (colVal) === 'number' && colVal >= 0) {
                                                                            clearTimeout(this.tdEditedTimer);
                                                                            this.tdEditedTimer = setTimeout(async () => {
                                                                                const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                                                const newRowData = {
                                                                                    ...rowData
                                                                                }
                                                                                if (rowData.revisedScoresOfRelevantDepartments && rowData.informedDuringInspection) {
                                                                                    newRowData.lastScore = Number((Number(rowData.firstSoce) + Number(rowData.secondScore)) * 0.5) + Number(rowData.revisedScoresOfRelevantDepartments) + Number(rowData.informedDuringInspection) + Number(colVal);
                                                                                }
                                                                                await tableBtnCallbackFn.setEditedRowData({
                                                                                    ...newRowData
                                                                                });
                                                                            }, 600)
                                                                        }
                                                                    }
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '相关部门（单位）修正分值',
                                                                    dataIndex: 'revisedScoresOfRelevantDepartments',
                                                                    key: 'revisedScoresOfRelevantDepartments',
                                                                    tdEdit: drawerTitile === '详情' ? false : true,
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                                        if (typeof (colVal) === 'number' && colVal >= 0) {
                                                                            clearTimeout(this.tdEditedTimer);
                                                                            this.tdEditedTimer = setTimeout(async () => {
                                                                                const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                                                const newRowData = {
                                                                                    ...rowData
                                                                                }
                                                                                if (rowData.scoreOfAdditionSubtraction && rowData.informedDuringInspection) {
                                                                                    newRowData.lastScore = Number((Number(rowData.firstSoce) + Number(rowData.secondScore)) * 0.5) + Number(rowData.scoreOfAdditionSubtraction) + Number(rowData.informedDuringInspection) + Number(colVal);
                                                                                }
                                                                                await tableBtnCallbackFn.setEditedRowData({
                                                                                    ...newRowData
                                                                                });
                                                                            }, 600)
                                                                        }
                                                                    }
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '在省市级工程质量安全监督机构及相关部门的检查中被通报',
                                                                    dataIndex: 'informedDuringInspection',
                                                                    key: 'informedDuringInspection',
                                                                    tdEdit: drawerTitile === '详情' ? false : true,
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                                        if (typeof (colVal) === 'number' && colVal >= 0) {
                                                                            clearTimeout(this.tdEditedTimer);
                                                                            this.tdEditedTimer = setTimeout(async () => {
                                                                                const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                                                const newRowData = {
                                                                                    ...rowData
                                                                                }
                                                                                if (rowData.scoreOfAdditionSubtraction && rowData.revisedScoresOfRelevantDepartments) {
                                                                                    newRowData.lastScore = Number((Number(rowData.firstSoce) + Number(rowData.secondScore)) * 0.5) + Number(rowData.scoreOfAdditionSubtraction) + Number(rowData.revisedScoresOfRelevantDepartments) + Number(colVal);
                                                                                }
                                                                                await tableBtnCallbackFn.setEditedRowData({
                                                                                    ...newRowData
                                                                                });
                                                                            }, 600)
                                                                        }
                                                                    }
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '有无直接降为D的行为',
                                                                    dataIndex: 'dLevel',
                                                                    key: 'dLevel'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '最终信用评价得分',
                                                                    dataIndex: 'lastScore',
                                                                    key: 'lastScore'
                                                                },
                                                                form: {
                                                                    type: 'number'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '信用评价等级',
                                                                    dataIndex: 'lastLevel',
                                                                    key: 'lastLevel'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '备注',
                                                                    dataIndex: 'remarks',
                                                                    key: 'remarks'
                                                                }
                                                            },
                                                        ],
                                                        actionBtns: [
                                                            {
                                                                name: 'diysubmit',
                                                                type: 'primary',
                                                                label: '保存',
                                                                field: 'addsubmit',
                                                                onClick: (val) => {
                                                                    const { myFetch } = this.props;
                                                                    let value = this.inputForm.form.getFieldsValue();
                                                                    if (value.zxItemList.length > 0) {
                                                                        myFetch('updateZxCrJYearCreditEvaItemAll', value.zxItemList).then(
                                                                            ({ data, success, message }) => {
                                                                                if (success) {
                                                                                    Msg.success(message);
                                                                                } else {
                                                                                    Msg.error(message)
                                                                                }
                                                                            }
                                                                        );
                                                                    } else {
                                                                        Msg.warn('表格没有数据，无法保存！')
                                                                    }

                                                                },
                                                                hide: () => {
                                                                    if (drawerTitile === '详情') {
                                                                        return true
                                                                    } else {
                                                                        return false
                                                                    }
                                                                }
                                                            }
                                                        ]
                                                    }
                                                }
                                            ]}
                                        />
                                    </div>
                                </div>;
                            }
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxCrJYearCreditEvaId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '序号',
                                dataIndex: 'no',
                                key: 'no',
                                fixed: 'left',
                                width: 50,
                                render: (data, rowData, index) => {
                                    return index + 1;
                                }
                            },
                            form: {
                                field: 'no',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '机构名称',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: '25%',
                                onClick: 'detail',
                                willExecute: (obj) => {
                                    this.setState({
                                        treeData: null,
                                        defaultExpandedKeys: [],
                                        QnnTableItemData: [],
                                        name: ''
                                    }, () => {
                                        obj.btnCallbackFn.setActiveKey('0');
                                        this.setState({
                                            zxCrJYearCreditEvaId: obj.rowData.zxCrJYearCreditEvaId,
                                            period: obj.rowData.period,
                                            orgName: obj.rowData.orgName
                                        })
                                        // 获取树数据
                                        this.getTreeData(obj.rowData.zxCrJYearCreditEvaId);
                                    })

                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '评价期次',
                                dataIndex: 'period',
                                key: 'period',
                                filter: true,
                                width: 150,
                                // render:(data,rowData) => {
                                //     if(data){
                                //         return moment(data).month() === 0 ? (moment(data).format('YYYY') + '/上半年') : (moment(data).format('YYYY') + '/下半年');
                                //     }else{
                                //         return null;
                                //     }
                                // },
                                render: (data) => {
                                    return data ? (moment(data).format('YYYY') + '/' + (moment(data).format('MM') === '12' ? '下半年' : '上半年')) : ''
                                },
                            },
                            isInForm: false,
                            form: {
                                type: 'halfYear',
                                field: 'period',
                                allowClear: false,
                                placeholder: '请选择',
                                span: 8
                            },
                        },
                        {
                            table: {
                                title: '评价日期',
                                dataIndex: 'dateTime',
                                key: 'dateTime',
                                format: 'YYYY-MM-DD',
                                width: 100
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '审核状态',
                                dataIndex: 'auditStatus',
                                key: 'auditStatus',
                                width: 100,
                                render: (data) => {
                                    if (data === '0') {
                                        return '未审核';
                                    } else if (data === '1') {
                                        return '已审核';
                                    } else {
                                        return ''
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '备注',
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
                    title="公司半年信用评价"
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
                                    this.tableModal = me;
                                }}
                                {...configModal}
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
                                            title: '机构名称',
                                            dataIndex: 'orgName',
                                            key: 'orgName',
                                            width: '25%',
                                            onClick: (obj) => {
                                                this.setState({
                                                    visibleSendDataDetail: true,
                                                    loadingSendDataDetail: false,
                                                    zxCrHalfYearCreditEvaId: obj.rowData.zxCrHalfYearCreditEvaId,
                                                    defaultExpandedKeysTwo: [],
                                                    periodTwo: obj.rowData.period,
                                                    orgName: this.state.orgNameTwo,
                                                    nameTwo: ''
                                                })
                                            }
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '评价期次',
                                            dataIndex: 'period',
                                            key: 'period',
                                            filter: true,
                                            width: 150,
                                            render: (data) => {
                                                return data ? (moment(data).format('YYYY') + '/' + (moment(data).format('MM') === '12' ? '下半年' : '上半年')) : ''
                                            },
                                        },
                                        isInForm: false,
                                        form: {
                                            type: 'halfYear',
                                            field: 'period',
                                            allowClear: false,
                                            placeholder: '请选择',
                                            span: 8
                                        },
                                    },
                                    {
                                        table: {
                                            title: '评价日期',
                                            dataIndex: 'dateTime',
                                            key: 'dateTime',
                                            format: 'YYYY-MM-DD',
                                            width: 100
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '审核状态',
                                            dataIndex: 'auditStatus',
                                            key: 'auditStatus',
                                            width: 100,
                                            render: (data) => {
                                                if (data === '0') {
                                                    return '未审核';
                                                } else {
                                                    return '已审核';
                                                }
                                            }
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '备注',
                                            width: '20%',
                                            dataIndex: 'remarks',
                                            key: 'remarks',
                                            tooltip: 23,
                                        },
                                        isInForm: false
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
                    title="公司半年信用评价明细"
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
                            <div className={s.rootModal}>
                                <div className={s.rootlModal}
                                    ref={(me) => {
                                        if (me) {
                                            this.leftDom = me;
                                        }
                                    }}>
                                    <div
                                        className={s.hrModal}
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
                                    {this.state.treeDataTwo ? <Tree
                                        selectText={false}
                                        modalType="common" //common  drawer  抽屉出现方式或者普通的
                                        visible
                                        selectModal="0" //0不可选  1单选(默认)  2多选（暂不可用）
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
                                        defaultExpandedKeys={this.state.defaultExpandedKeysTwo}
                                        rightMenuOption={[]}
                                        nodeClick={(node) => {
                                            this.setState({
                                                nameTwo: '',
                                                catIDzhiTwo: '',
                                                resIDzhiTwo: '',
                                            }, () => {
                                                this.setState({
                                                    defaultExpandedKeysTwo: node.bsid.split(','),
                                                    nameTwo: node.name,
                                                    idTwo: node.id,
                                                    catIDzhiTwo: node.catID,
                                                    resIDzhiTwo: node.resID,
                                                    parentIDTwo: node.parentID
                                                })
                                            })

                                        }}
                                        data={this.state.treeDataTwo}
                                        keys={{
                                            label: "name",
                                            value: "id",
                                            children: "childrenList"
                                        }}
                                    /> : null}
                                </div>
                                <div className={s.rootrModal}>
                                    {this.state.nameTwo ? <QnnTable
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
                                                period: this.state.periodTwo,
                                                catID: this.state.catIDzhiTwo,
                                                resID: this.state.resIDzhiTwo,
                                                parentID: this.state.parentIDTwo,
                                                zxCrHalfYearCreditEvaId: this.state.zxCrHalfYearCreditEvaId,
                                                orgName: this.state.orgNameTwo,
                                                departmentId: jurisdiction ? jurisdiction : '',
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
                                                    label: '主键id',
                                                    field: 'zxCrHalfYearCreditEvaItemId',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '组织机构代码',
                                                    width: 150,
                                                    tooltip: 23,
                                                    dataIndex: 'orgCertificate',
                                                    key: 'orgCertificate'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '协作单位名称',
                                                    width: 160,
                                                    tooltip: 23,
                                                    dataIndex: 'customerName',
                                                    key: 'customerName'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '协作单位负责人',
                                                    width: 160,
                                                    tooltip: 23,
                                                    dataIndex: 'chargeMan',
                                                    key: 'chargeMan'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '工程所属项目',
                                                    width: 160,
                                                    dataIndex: 'projectName',
                                                    key: 'projectName'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '进场日期',
                                                    format: 'YYYY-MM-DD',
                                                    dataIndex: 'inDate',
                                                    key: 'inDate',

                                                },
                                                isInForm: false,
                                                form: {
                                                    type: 'date'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '退场日期',
                                                    format: 'YYYY-MM-DD',
                                                    dataIndex: 'outDate',
                                                    key: 'outDate'
                                                },
                                                isInForm: false,
                                                form: {
                                                    type: 'date'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '承建工程合同额(万元)',
                                                    width: 200,
                                                    dataIndex: 'contractAmt',
                                                    key: 'contractAmt'
                                                },
                                                isInForm: false,
                                                form: {
                                                    type: 'number'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '工程所属项目个数',
                                                    width: 160,
                                                    dataIndex: 'projectNum',
                                                    key: 'projectNum'
                                                },
                                                isInForm: false,
                                                form: {
                                                    type: 'number'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '评价次数',
                                                    dataIndex: 'checkNum',
                                                    key: 'checkNum'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '公司信用评价得分(第一次)',
                                                    width: 160,
                                                    dataIndex: 'firstSoce',
                                                    key: 'firstSoce',

                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '评价等级(第一次)',
                                                    width: 160,
                                                    dataIndex: 'firstLevel',
                                                    key: 'firstLevel'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '公司信用评价得分（第二次）',
                                                    width: 180,
                                                    dataIndex: 'secondScore',
                                                    key: 'secondScore'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '评价等级(第二次)',
                                                    width: 160,
                                                    dataIndex: 'secondLevel',
                                                    key: 'secondLevel'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '有无直接降为D的行为',
                                                    width: 160,
                                                    dataIndex: 'dLevel',
                                                    key: 'dLevel'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '最终信用评价得分',
                                                    width: 130,
                                                    dataIndex: 'lastScore',
                                                    key: 'lastScore'
                                                },
                                                isInForm: false,
                                                form: {
                                                    type: 'number'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '信用评价等级',
                                                    width: 130,
                                                    dataIndex: 'lastLevel',
                                                    key: 'lastLevel'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '备注',
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
                        </div>
                    </Spin>
                </Modal>
            </div>
        );
    }
}

export default index;