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
    // 获取树-数据
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
    // 获取明细-树-数据
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
                            label: '新增',
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
                                    label: '取消',
                                },
                                {
                                    name: 'submitDiy',
                                    type: 'primary',
                                    field: 'submit',
                                    label: '保存',
                                    onClick: (obj) => {
                                        this.props.myFetch('addZxCrHalfYearCreditEva', obj._formData).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    // 新增接口，返回个主键id
                                                    this.setState({
                                                        zxCrHalfYearCreditEvaId: data.zxCrHalfYearCreditEvaId,
                                                        period: data.period,
                                                        orgName: data.orgName
                                                    })
                                                    // 获取树数据
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
                            label: '修改',
                            onClick: (obj) => {
                                if (obj.selectedRows[0].auditStatus === '1') {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('已审核的数据不能修改')
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
                                        // 获取树数据
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
                                        this.props.myFetch('updateZxCrHalfYearCreditEva', obj._formData).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    // 获取树数据
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
                            label: '审核',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {
                                const { myFetch } = this.props;
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].status === '1') {
                                        obj.btnCallbackFn.closeDrawer();
                                        this.table.clearSelectedRows();
                                        Msg.warn('已审核的不能审核!');
                                    } else {
                                        confirm({
                                            content: '确定审核选中的数据吗?',
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
                            title: "明细",
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
                                                        key: 'chargeMan',
                                                        onClick: (val) => {
                                                            // 公司季度信用评价
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
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '退场日期',
                                                        format: 'YYYY-MM-DD',
                                                        dataIndex: 'outDate',
                                                        key: 'outDate'
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
                                                        title: '工程所属项目个数',
                                                        width: 160,
                                                        dataIndex: 'projectNum',
                                                        key: 'projectNum'
                                                    },
                                                    isInForm: false
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
                                                    isInForm: false
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
                                title: '机构名称',
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
                                        // 获取树数据
                                        this.getTreeData(obj.rowData.zxCrHalfYearCreditEvaId);
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
                />
                <Modal
                    width={document.documentElement.clientWidth * 0.9}
                    style={{ top: '0' }}
                    title="公司季度信用评价"
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
                                            title: '项目名称',
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
                                            initialValue: '北京建筑分公司',
                                            addDisabled: true,
                                            editDisabled: true,
                                            placeholder: '请输入',
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
                                            title: '协作单位名称',
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
                                            placeholder: '请选择',
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
                                            title: '协作单位负责人',
                                            dataIndex: 'chargeMan',
                                            width: 160,
                                            key: 'chargeMan'
                                        },
                                        form: {
                                            field: 'chargeMan',
                                            type: 'string',
                                            placeholder: '请输入',
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
                                            title: '负责人联系电话',
                                            width: 160,
                                            dataIndex: 'chargeManPhone',
                                            key: 'chargeManPhone'
                                        },
                                        form: {
                                            field: 'chargeManPhone',
                                            type: 'phone',
                                            placeholder: '请输入',
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
                                            title: '专业类别代码',
                                            width: 200,
                                            dataIndex: 'catCode',
                                            key: 'catCode'
                                        },
                                        form: {
                                            field: 'catCode',
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled: true,
                                            placeholder: '请输入',
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
                                            title: '专业类别',
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
                                            title: '分类代码',
                                            dataIndex: 'resCode',
                                            key: 'resCode'
                                        },
                                        form: {
                                            field: 'resCode',
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled: true,
                                            placeholder: '请输入',
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
                                            title: '分类名称',
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
                                            dependenciesReRender: true,//多个依赖-配置
                                            dependencies: ['parentID', 'catID'],
                                            fetchConfig: {//parentID应该存在列表接口里面--待验证
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
                                            title: '承建工程合同额(万元)',
                                            width: 200,
                                            dataIndex: 'contractAmt',
                                            key: 'contractAmt'
                                        },
                                        form: {
                                            field: 'contractAmt',
                                            type: 'number',
                                            required: true,
                                            placeholder: '请输入',
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
                                            title: '考核总得分',
                                            dataIndex: 'totalScore',
                                            key: 'totalScore'
                                        },
                                        form: {
                                            field: 'totalScore',
                                            type: 'number',
                                            addDisabled: true,
                                            editDisabled: true,
                                            placeholder: '请输入',
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
                                            title: '考核期次',
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
                                                    itemId: 'qiCi'//问张启明
                                                },
                                            },
                                            placeholder: '请输入',
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
                                            title: '考核日期',
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
                                            title: '进场日期',
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
                                            title: '退场日期',
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
                                            title: '考核标准',
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
                                            optionData: [//问张启明
                                                {
                                                    label: '严重不良行为考核表',
                                                    value: '0'
                                                },
                                                {
                                                    label: '打分考核表',
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
                                            title: '填报人',
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
                                            title: '审核人',
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
                                            title: '评审状态',
                                            width: 100,
                                            fixed: 'right',
                                            dataIndex: 'auditStatus',
                                            key: 'auditStatus',
                                            render: (data) => {
                                                if (data) {
                                                    return data === '0' ? '未审核' : '已审核'
                                                } else {
                                                    return '未审核'
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
                                            optionData: [//问张启明
                                                {
                                                    label: "未审核",
                                                    value: "0"
                                                },
                                                {
                                                    label: "已审核",
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
                                            label: '附件',
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
                                                        严重不良行为考核表
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
                                                                        label: '主键id',
                                                                        field: 'zxCrProjectEvaluationBadId',
                                                                        hide: true
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '评价内容',
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
                                                                        title: '评价细目',
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
                                                                        title: '是否存在严重不良行为',
                                                                        width: 100,
                                                                        dataIndex: 'isBad',
                                                                        key: 'isBad',
                                                                        type: 'select'
                                                                    },
                                                                    form: {
                                                                        type: 'select',
                                                                        field: 'isBad',
                                                                        optionConfig: {
                                                                            label: 'label', //默认 label
                                                                            value: 'value'
                                                                        },
                                                                        optionData: [//问张启明
                                                                            {
                                                                                label: '否',
                                                                                value: '0'
                                                                            },
                                                                            {
                                                                                label: '是',
                                                                                value: '1'
                                                                            }
                                                                        ]
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '备注',
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
                                                        打分考核表
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
                                                                        label: '主键id',
                                                                        field: 'zxCrProjectEvaluationScoreId',
                                                                        hide: true
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '评价内容',
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
                                                                        title: '评价细目',
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
                                                                        title: '标准分值',
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
                                                                        title: '项目减分',
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
                                                                        title: '项目得分',
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
                                                                        title: '备注',
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
                    title="公司季度信用评价明细"
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
                                        label: '项目名称',
                                        field: 'orgName',
                                        type: 'string',
                                        initialValue: '北京建筑分公司',
                                        disabled: true,
                                        placeholder: '请输入',
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
                                        label: '协作单位名称',
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
                                        placeholder: '请选择',
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
                                        label: '协作单位负责人',
                                        field: 'chargeMan',
                                        disabled: true,
                                        type: 'string',
                                        placeholder: '请输入',
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
                                        label: '负责人联系电话',
                                        disabled: true,
                                        field: 'chargeManPhone',
                                        type: 'phone',
                                        placeholder: '请输入',
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
                                        label: '专业类别代码',
                                        disabled: true,
                                        field: 'catCode',
                                        type: 'string',
                                        placeholder: '请输入',
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
                                        label: '专业类别',
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
                                        label: '分类代码',
                                        field: 'resCode',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '请输入',
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
                                        label: '分类名称',//???有数据后--需要验证
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
                                        dependenciesReRender: true,//多个依赖-配置
                                        dependencies: ['parentID', 'catID'],
                                        fetchConfig: {//parentID应该存在列表接口里面--待验证
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
                                        label: '承建工程合同额(万元)',
                                        field: 'contractAmt',
                                        type: 'number',
                                        required: true,
                                        disabled: true,
                                        placeholder: '请输入',
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
                                        label: '考核总得分',
                                        field: 'totalScore',
                                        type: 'number',
                                        disabled: true,
                                        placeholder: '请输入',
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
                                        label: '考核期次',
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
                                                itemId: 'qiCi'//问张启明
                                            },
                                        },
                                        placeholder: '请输入',
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
                                        label: '考核日期',
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
                                        label: '进场日期',
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
                                        label: '退场日期',
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
                                        label: '考核标准',
                                        disabled: true,
                                        type: 'select',
                                        field: 'checkStandard',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [//问张启明
                                            {
                                                label: '严重不良行为考核表',
                                                value: '0'
                                            },
                                            {
                                                label: '打分考核表',
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
                                        label: '填报人',
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
                                        label: '审核人',
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
                                        label: '评审状态',
                                        type: 'select',
                                        disabled: true,
                                        field: 'auditStatus',
                                        hide: true,
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [//问张启明
                                            {
                                                label: "未审核",
                                                value: "0"
                                            },
                                            {
                                                label: "已审核",
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
                                        label: '附件',
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
                                                    严重不良行为考核表
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
                                                                    label: '主键id',
                                                                    field: 'zxCrProjectEvaluationBadId',
                                                                    hide: true
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '评价内容',
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
                                                                    title: '评价细目',
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
                                                                    title: '是否存在严重不良行为',
                                                                    width: 100,
                                                                    dataIndex: 'isBad',
                                                                    key: 'isBad',
                                                                    type: 'select'
                                                                },
                                                                form: {
                                                                    type: 'select',
                                                                    field: 'isBad',
                                                                    optionConfig: {
                                                                        label: 'label', //默认 label
                                                                        value: 'value'
                                                                    },
                                                                    optionData: [//问张启明
                                                                        {
                                                                            label: '否',
                                                                            value: '0'
                                                                        },
                                                                        {
                                                                            label: '是',
                                                                            value: '1'
                                                                        }
                                                                    ]
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '备注',
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
                                                    打分考核表
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
                                                                    label: '主键id',
                                                                    field: 'zxCrProjectEvaluationScoreId',
                                                                    hide: true
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '评价内容',
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
                                                                    title: '评价细目',
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
                                                                    title: '标准分值',
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
                                                                    title: '项目减分',
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
                                                                    title: '项目得分',
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
                                                                    title: '备注',
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