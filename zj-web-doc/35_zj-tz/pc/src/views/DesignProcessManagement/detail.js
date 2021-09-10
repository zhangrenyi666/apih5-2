import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";
import { push } from "react-router-redux";
import QnnForm from "../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Checkbox, Tag, Spin } from "antd";
import HasTicketMoneyForm from './hasTicketMoneyForm';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.partManageId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1100px'
    },
    limit: 999999,
    curPage: 1,
    paginationConfig: false,
    firstRowIsSearch: false,
    isShowRowSelect: true,
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 21 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            designFlowId: props.match.params.designFlowId || '',
            visibleSend: false,
            loadingSend: false,
            partManageId: '',
            fujianLook: [],
            rowDataList: []
        }
    }
    onChangeRadio = (data, rowData, e) => {
        if (e.target.checked) {
            rowData.bidPartId = '1';
        } else {
            rowData.bidPartId = '0';
        }
        this.props.myFetch('updateZjTzPartManage', rowData).then(
            ({ success, message }) => {
                if (success) {
                    Msg.success(message);
                    this.tableGM.refresh();
                } else {
                    Msg.error(message);
                    this.tableGM.refresh();
                }
            }
        );

    }
    onChangeCheckBox = (data, rowData, e) => {
        if (e.target.checked) {
            rowData.buId = '1';
        } else {
            rowData.buId = '0';
        }
        this.props.myFetch('updateZjTzPartManage', rowData).then(
            ({ success, message }) => {
                if (success) {
                    Msg.success(message);
                    this.tableGM.refresh();
                } else {
                    Msg.error(message);
                    this.tableGM.refresh();
                }
            }
        );
    }
    handleCancelSend = () => {
        this.setState({ visibleSend: false, loadingSend: false });
    }
    render() {
        const { designFlowId, visibleSend, loadingSend } = this.state;
        let me = this;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        return (
            <div className={s.root}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    fetchConfig={{
                        apiName: 'getZjTzPartManageList',
                        otherParams: {
                            designFlowId: designFlowId
                        }
                    }}
                    wrappedComponentRef={(me) => {
                        this.tableGM = me;
                    }}
                    componentsKey={{
                        HasTicketMoneyForm: (obj) => {
                            let delAttr = ["id"];
                            return <HasTicketMoneyForm {...obj} {...this.props} designFlowId={designFlowId} />
                        }
                    }}
                    method={{
                        goBack: (obj) => {
                            const { mainModule } = obj.props.myPublic.appInfo;
                            obj.props.dispatch(
                                push(`${mainModule}DesignProcessManagement`)
                            )
                        },
                        lockClick: (obj) => {
                            const { myFetch } = obj.props;
                            let unSameKindArry = [];
                            let SameKindArry = [];
                            let name = '';
                            let apiName = '';
                            if (obj.selectedRows.length > 0) {
                                for (var j = 0; j < obj.selectedRows.length; j++) {
                                    if (obj.selectedRows[j].lockFlag === '0') {
                                        unSameKindArry.push(obj.selectedRows[j]);

                                    } else {
                                        SameKindArry.push(obj.selectedRows[j]);
                                    }
                                }
                                if (unSameKindArry.length > 0) {
                                    name = '确定锁定么？';
                                    apiName = 'batchLockUpdateZjTzPartManage';
                                }
                                if (SameKindArry.length > 0) {
                                    name = '确定解锁么？';
                                    apiName = 'batchClearUpdateZjTzPartManage';
                                }
                                if ((obj.selectedRows.length === unSameKindArry.length) || (obj.selectedRows.length === SameKindArry.length)) {
                                    confirm({
                                        title: name,
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch(apiName, obj.selectedRows).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.tableGM.refresh();
                                                        this.tableGM.clearSelectedRows();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    })
                                } else {
                                    Msg.warn('只能选择相同类型数据!');
                                }
                            } else {
                                Msg.warn('请选择数据!');
                            }
                        },
                        delClick: (obj) => {
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].lockFlag === '0') {
                                        // 未锁定
                                        confirm({
                                            title: "确定删除么?",
                                            okText: "确认",
                                            cancelText: "取消",
                                            onOk: () => {
                                                myFetch('batchDeleteUpdateZjTzPartManage', obj.selectedRows).then(
                                                    ({ success, message }) => {
                                                        if (success) {
                                                            Msg.success(message);
                                                            this.tableGM.refresh();
                                                            this.tableGM.clearSelectedRows();
                                                        } else {
                                                            Msg.error(message)
                                                        }
                                                    }
                                                );
                                            }
                                        })
                                    } else {
                                        Msg.warn('数据已锁定，不能删除!');
                                    }

                                } else {
                                    Msg.warn('只能选择一条数据!');
                                }
                            } else {
                                Msg.warn('请选择数据!');
                            }

                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: function (obj) {
                            var props = obj.Pprops;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "projectInfo"
                            }
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'partManageId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'designFlowId',
                                type: 'string',
                                initialValue: designFlowId,
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '序号',
                                dataIndex: 'orderNum',
                                key: 'orderNum',
                                width: 60,
                                align: 'center',
                                defaultSortOrder: 'descend',
                                fixed: 'left',
                                render: (data, rowData) => {
                                    if (rowData.colorFlag === 'white') {
                                        return data
                                    } else {
                                        return (
                                            <Tag style={{ width: '100%', textAlign: 'center' }} color={rowData.colorFlag} key={data}>
                                                {data}
                                            </Tag>
                                        )
                                    }

                                }
                            },
                            isInForm: false,
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '设计环节名称',
                                onClick: 'detail',
                                dataIndex: 'designPartName',
                                key: 'designPartName',
                                tdEdit: true,
                                fieldsConfig: {
                                    type: 'string',
                                    placeholder: '请输入',
                                    required: true,
                                    disabled: ({ record }) => {
                                        if (ext1 === '1') {
                                            return true
                                        } else {
                                            if (record.lockFlag === "1") {
                                                return true
                                            }
                                            return false;
                                        }

                                    }
                                },
                                tdEditCb: (obj) => {
                                    obj.oldRowData.designPartName = obj.newRowData.designPartName;
                                    this.props.myFetch('updateZjTzPartManage', obj.oldRowData).then(
                                        ({ success, message }) => {
                                            if (success) {
                                                // Msg.success(message);
                                                this.tableGM.refresh();
                                            } else {
                                                Msg.error(message);
                                                this.tableGM.refresh();
                                            }
                                        }
                                    );
                                }
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '中标时环节',
                                dataIndex: 'bidPartName',
                                align: 'center',
                                width: 130,
                                key: 'bidPartName',
                                tdEdit: false,
                                render: (data, rowData) => {
                                    if (ext1 === '1') {
                                        // 投资事业部权限
                                        return <Checkbox disabled checked={false}></Checkbox>
                                    } else {
                                        // 项目公司权限
                                        if (rowData.lockFlag === '1') {
                                            if (rowData.bidPartId === '0') {
                                                return <Checkbox disabled checked={false}></Checkbox>
                                            } else {
                                                return <Checkbox disabled checked={true}></Checkbox>
                                            }
                                        } else {
                                            if (rowData.bidPartId === '0') {
                                                return <Checkbox checked={false} onChange={this.onChangeRadio.bind(this, data, rowData)}></Checkbox>
                                            } else {
                                                return <Checkbox checked={true} onChange={this.onChangeRadio.bind(this, data, rowData)}></Checkbox>
                                            }
                                        }
                                    }

                                }
                            },
                            isInForm: false,
                            form: {
                                type: 'radio',
                                addDisabled: true,
                                editDisabled: true,
                                required: true,
                                field: 'bidPartId',
                                placeholder: '请输入',
                                optionData: [
                                    {
                                        label: "否",
                                        value: "0"
                                    },
                                    {
                                        label: "是",
                                        value: "1"
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '事业部介入',
                                dataIndex: 'buName',
                                key: 'buName',
                                width: 130,
                                align: 'center',
                                tdEdit: false,
                                render: (data, rowData) => {
                                    if (ext1 === '1') {
                                        // 投资事业部权限
                                        if (rowData.lockFlag === '1') {
                                            if (rowData.buId === '0') {
                                                return <Checkbox disabled checked={false}></Checkbox>
                                            } else {
                                                return <Checkbox disabled checked={true}></Checkbox>
                                            }
                                        } else {
                                            if (rowData.buId === '0') {
                                                return <Checkbox checked={false} onChange={this.onChangeCheckBox.bind(this, data, rowData)}></Checkbox>
                                            } else {
                                                return <Checkbox checked={true} onChange={this.onChangeCheckBox.bind(this, data, rowData)}></Checkbox>
                                            }
                                        }
                                    } else {
                                        // 项目公司权限
                                        return <Checkbox disabled checked={false}></Checkbox>
                                    }


                                }
                            },
                            isInForm: false,
                            form: {
                                type: 'radio',
                                addDisabled: true,
                                editDisabled: true,
                                required: true,
                                field: 'buId',
                                placeholder: '请输入',
                                optionData: [
                                    {
                                        label: "否",
                                        value: "0"
                                    },
                                    {
                                        label: "是",
                                        value: "1"
                                    }
                                ],
                            },
                        },
                        {
                            table: {
                                title: '计划完成日期',
                                dataIndex: 'planDate',
                                key: 'planDate',
                                tdEdit: true,
                                width: 140,
                                format: 'YYYY-MM-DD',
                                fieldsConfig: {
                                    field: 'planDate',
                                    type: 'date',
                                    label: '创建日期',
                                    disabled: ({ record }) => {
                                        if (ext1 === '1') {
                                            // 投资事业部权限
                                            return true
                                        } else {
                                            // 项目公司权限
                                            if (record.lockFlag === "1") {
                                                return true
                                            }
                                            return false;
                                        }

                                    }
                                },
                                tdEditCb: (obj) => {
                                    obj.oldRowData.planDate = obj.newRowData.planDate;
                                    this.props.myFetch('updateZjTzPartManage', obj.oldRowData).then(
                                        ({ success, message }) => {
                                            if (success) {
                                                // Msg.success(message);
                                                this.tableGM.refresh();
                                            } else {
                                                Msg.error(message);
                                                this.tableGM.refresh();
                                            }
                                        }
                                    );
                                }
                            },
                            form: {
                                type: 'date',
                                addDisabled: true,
                                editDisabled: true,
                                field: 'planDate',
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '实际完成日期',
                                format: 'YYYY-MM-DD',
                                dataIndex: 'actualDate',
                                width: 140,
                                key: 'actualDate'
                            },
                            form: {
                                type: 'date',
                                addDisabled: true,
                                editDisabled: true,
                                field: 'actualDate',
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '锁定状态',
                                dataIndex: 'lockFlag',
                                width: 100,
                                key: 'lockFlag',
                                render: (data) => {
                                    if (data === '0') {
                                        return '未锁定'
                                    } else {
                                        return '锁定'
                                    }
                                }
                            },
                            isInForm: false,
                        },
                        {
                            table: {
                                title: '证明材料附件',
                                dataIndex: 'fileFlag',
                                width: 140,
                                key: 'fileFlag',
                                tdEdit: ext1 === '1' ? false : true,
                                render: (data) => {
                                    if (data) {
                                        if (data === '0') {
                                            return '未上传'
                                        } else {
                                            return '已上传'
                                        }
                                    } else {
                                        return ''
                                    }
                                },
                                onClick: (obj) => {
                                    this.setState({
                                        partManageId: obj.rowData.partManageId,
                                        fujianLook: obj.rowData.zjTzFileList,
                                        rowDataList: obj.rowData
                                    }, () => {
                                        if (obj.rowData.zjTzFileList.length > 0) {
                                            this.setState({
                                                visibleSend: true,
                                            })
                                        } else {
                                            Msg.warn('当前数据没有上传附件！')
                                        }
                                    })
                                },
                                tdEditCb: (obj) => {
                                    obj.oldRowData.zjTzFileList = obj.newRowData.zjTzFileList;
                                    this.props.myFetch('updateZjTzPartManage', obj.oldRowData).then(
                                        ({ success, message }) => {
                                            if (success) {
                                                // Msg.success(message);
                                                this.tableGM.refresh();
                                            } else {
                                                Msg.error(message);
                                                this.tableGM.refresh();
                                            }
                                        }
                                    );
                                }
                            },
                            form: {
                                field: 'zjTzFileList',
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '设计流程管理'
                                    }
                                },
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                
                                onChange: (val, rowData) => {
                                    if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                        Msg.warn('不允许上传rar、zip、7z格式的文件！')
                                        setTimeout(() => {
                                            this.table.qnnForm.form.setFieldsValue({
                                                zjTzFileList: []
                                            })
                                        }, 200)
                                    }
                                }
                            }
                        },
                        {
                            isInForm: false,
                            isInTable: ext1 === '1' ? false : true,
                            table: {
                                title: "操作",
                                fixed: 'right',
                                dataIndex: 'action',
                                key: 'action',
                                align: "center",
                                noHaveSearchInput: true,
                                showType: "tile",
                                width: 80,
                                btns: [
                                    {
                                        name: 'crDetail',
                                        render: (rowData) => {
                                            if (rowData.rowData.lockFlag === '1') {
                                                return '';
                                            } else {
                                                return '<a>插入</a>';
                                            }

                                        },
                                        onClick: (obj) => {
                                            this.props.myFetch('insertZjTzPartManage', { partManageId: obj.rowData.partManageId }).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.tableGM.refresh();
                                                    } else {
                                                        Msg.error(message);
                                                        this.tableGM.refresh();
                                                    }
                                                }
                                            );
                                        },
                                    }
                                ]
                            }
                        }
                    ]}
                />
                <Modal
                    width='800px'
                    style={{ top: '0' }}
                    title="证明材料"
                    visible={visibleSend}
                    footer={null}
                    onCancel={this.handleCancelSend}
                    bodyStyle={{ width: '800px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelSend}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingSend}>
                        <QnnForm
                            {...this.props}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            formConfig={[
                                {
                                    type: "qnnTable",
                                    field: "qnnTableKP",
                                    qnnTableConfig: {
                                        data: me.state.fujianLook,
                                        drawerConfig: {
                                            width: "800px"
                                        },
                                        actionBtnsPosition: "top",
                                        antd: {
                                            rowKey: function (row) {
                                                return row.partManageId;
                                            },
                                            size: 'small'
                                        },
                                        paginationConfig: {
                                            position: 'bottom'
                                        },
                                        firstRowIsSearch: false,
                                        isShowRowSelect: false,
                                        wrappedComponentRef: (me) => {
                                            this.tableFJ = me;
                                        },
                                        actionBtns: [],
                                        formConfig: [
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'string',
                                                    field: "partManageId",
                                                    initialValue: me.state.partManageId,
                                                    hide: true
                                                },
                                            },
                                            {
                                                table: {
                                                    title: '证明材料文件名',
                                                    dataIndex: 'name',
                                                    key: 'name'
                                                },
                                                isInForm: false,
                                            },
                                            {
                                                table: {
                                                    title: '附件',
                                                    dataIndex: 'url',
                                                    key: 'url',
                                                    render: (data) => {
                                                        return <div onClick={() => {
                                                            // window.location.href = data
                                                        }}><a target={'_blank'} href={data}>查看附件</a></div>
                                                    }
                                                },
                                                isInForm: false,
                                            },
                                            {
                                                isInForm: false,
                                                isInTable: ext1 === '1' ? false : true,
                                                table: {
                                                    title: "操作",
                                                    fixed: 'right',
                                                    dataIndex: 'action',
                                                    key: 'action',
                                                    align: "center",
                                                    noHaveSearchInput: true,
                                                    showType: "tile",
                                                    width: 80,
                                                    btns: [
                                                        {
                                                            name: 'dityDel',
                                                            field: 'dellBtn',
                                                            render: function (rowData) {
                                                                return '<a>删除</a>';
                                                            },
                                                            onClick: (obj) => {
                                                                // debugger
                                                                let arry = me.state.fujianLook;
                                                                let arryNew = [];
                                                                for (var i = 0; i < arry.length; i++) {
                                                                    if (arry[i].name === obj.rowData.name) {

                                                                    } else {
                                                                        arryNew.push(arry[i]);
                                                                    }
                                                                }
                                                                me.state.rowDataList.zjTzFileList = arryNew;
                                                                this.props.myFetch('updateZjTzPartManage', me.state.rowDataList).then(
                                                                    ({ success, message }) => {
                                                                        if (success) {
                                                                            Msg.success(message);
                                                                            this.tableGM.refresh();
                                                                            this.setState({
                                                                                visibleSend: false
                                                                            })
                                                                        } else {
                                                                            Msg.error(message);
                                                                        }
                                                                    }
                                                                );
                                                            }
                                                        }
                                                    ]
                                                }
                                            }
                                        ]
                                    }
                                }
                            ]}
                            btns={[]}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 4 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 20 }
                                }
                            }}
                        />
                    </Spin>
                </Modal>
            </div>
        );
    }
}

export default index;