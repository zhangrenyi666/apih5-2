import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import s from "./style.less";
import { message as Msg, Modal, Spin, Tooltip } from "antd";
const confirm = Modal.confirm;
const config = {

    antd: {
        rowKey: function (row) {
            return row.compSupReportId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },
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
            visibleSend: false,
            loadingSend: false,
            compSupReportId: '',
            companyId: props.loginAndLogoutInfo.loginInfo.userInfo.companyList[0].companyId,
            companyName: props.loginAndLogoutInfo.loginInfo.userInfo.companyList[0].companyName,
            proNameId: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId : '',
        }
    }
    componentDidMount() {

    }
    handleCancelSend = () => {
        this.setState({ visibleSend: false, loadingSend: false });
    }
    render() {
        const { visibleSend, loadingSend, compSupReportId, companyId, proNameId } = this.state;
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
                    fetchConfig={{
                        apiName: 'getZjTzCompSupReportList',
                        otherParams: {
                            projectId: proNameId
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'compSupReportId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'companyId',
                                type: 'string',
                                initialValue: companyId,
                                hide: true,
                            }
                        },
                        {
                            isInSearch: false,
                            table: {
                                title: '标题',
                                dataIndex: 'title',
                                key: 'title',
                                width: 300,
                                tooltip: 23,
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                field: 'title',
                                placeholder: '请输入',
                                required: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '检查日期',
                                type: 'date',
                                required: true,
                                field: 'checkDate',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '督查分类',
                                dataIndex: 'supClassId',
                                key: 'supClassId',
                                type: 'select',
                                filter: true
                            },
                            form: {
                                field: 'supClassId',
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'duChaFenLei'
                                    }
                                },
                                required: true,
                                placeholder: '请选择',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                            },
                        },
                        {
                            table: {
                                title: '督查部门',
                                dataIndex: 'supDeptId',
                                key: 'supDeptId',
                                type: 'select',
                                filter: true
                            },
                            form: {
                                field: 'supDeptId',
                                type: 'select',
                                required: true,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'duChaBuMen'
                                    }
                                },
                                placeholder: '请选择',

                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                            },
                        },
                        {
                            table: {
                                title: '督查对象',
                                dataIndex: 'projectId',
                                key: 'projectId',
                                type: 'select',
                                filter: true
                            },
                            form: {
                                label: '督查对象',
                                field: 'projectId',
                                type: 'select',
                                showSearch: true,
                                required: true,
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProManageList"
                                },
                                placeholder: '请选择',
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                },
                            },
                        },

                        {
                            table: {
                                title: '检查日期',
                                dataIndex: 'checkDate',
                                width: 120,
                                format: 'YYYY-MM-DD',
                                key: 'checkDate',
                                filter: true
                            },
                            isInForm: false,
                            form: {
                                type: 'date',
                                field: 'checkDate'
                            }
                        },
                        {
                            table: {
                                title: '登记用户',
                                dataIndex: 'registerPerson',
                                key: 'registerPerson',
                                width: 100,
                            },
                            isInForm: false
                        },
                        {
                            // table: {
                            //     title: '整改下达状态',
                            //     dataIndex: 'correctiveName',
                            //     key: 'correctiveName',
                            //     width: 120,
                            //     filter:true
                            // },
                            table: {
                                title: '下达状态',
                                dataIndex: 'correctiveId',
                                key: 'correctiveId',
                                width: 120,
                                type: 'select',
                                filter: true
                            },
                            isInForm: false,
                            form: {
                                type: 'select',
                                field: 'correctiveId',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    {
                                        label: "未下达",
                                        value: "0"
                                    },
                                    {
                                        label: "已下达",
                                        value: "1"
                                    },
                                    {
                                        label: "已落实",
                                        value: "2"
                                    }
                                ]
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
                                            督察要点通报内容（分表扬、建议、整改、处罚四个方面依次填写）
                                        </div>
                                    );
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'zjTzSupContentList',
                                colStyle: {
                                    paddingLeft: 12
                                },
                                incToForm: true,
                                qnnTableConfig: {
                                    actionBtnsPosition: "top",
                                    antd: {
                                        rowKey: 'supContentId',
                                        size: 'small'
                                    },
                                    drawerConfig: {
                                        width: '1000px'
                                    },
                                    limit: 999,
                                    curPage: 1,
                                    paginationConfig: false,
                                    firstRowIsSearch: false,
                                    isShowRowSelect: true,
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 4 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 20 }
                                        }
                                    },
                                    wrappedComponentRef: (me) => {
                                        this.zjTzSupContentListTable = me;
                                    },
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '主键id',
                                                field: 'supContentId',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            table: {
                                                width: 80,
                                                align: 'center',
                                                title: '序号', //表头标题
                                                dataIndex: 'no', //表格里面的字段
                                                key: 'no',//表格的唯一key    
                                                render: (data, rows, index) => {
                                                    return index + 1;
                                                }
                                            },
                                        },
                                        {
                                            table: {
                                                title: '类型',
                                                width: 80,
                                                dataIndex: 'typeId',
                                                key: 'typeId',
                                                tdEdit: true,
                                                type: 'select'
                                            },
                                            form: {
                                                label: '类型',
                                                type: 'select',
                                                field: 'typeId',
                                                allowClear: false,
                                                placeholder: '请选择',
                                                optionConfig: {
                                                    label: 'itemName',
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'leiXing'
                                                    }
                                                }
                                            },
                                        },
                                        {
                                            table: {
                                                title: '细目',
                                                width: 300,
                                                tooltip: 23,
                                                dataIndex: 'detail',
                                                key: 'detail',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'textarea',
                                                autoSize: {
                                                    minRows: 1,
                                                    maxRows: 4
                                                },
                                                field: 'detail',
                                                placeholder: '请输入',

                                            },
                                        },
                                        {
                                            table: {
                                                title: '是否需要整改',
                                                width: 100,
                                                dataIndex: 'needCorrectiveId',
                                                key: 'needCorrectiveId',
                                                tdEdit: true,
                                                fieldsConfig: {
                                                    type: 'select',
                                                    field: 'needCorrectiveId',
                                                    optionData: [
                                                        {
                                                            label: "是",
                                                            value: "1"
                                                        },
                                                        {
                                                            label: "否",
                                                            value: "0"
                                                        }
                                                    ],
                                                    disabled: ({ record }) => {
                                                        if (record.typeId === "1") {
                                                            return true
                                                        }
                                                        return false;
                                                    }
                                                },
                                                type: 'select'
                                            },
                                            form: {
                                                label: '是否需要整改',
                                                field: 'needCorrectiveId',
                                                type: 'select',
                                                placeholder: '请输入',
                                                optionData: [
                                                    {
                                                        label: "是",
                                                        value: "1"
                                                    },
                                                    {
                                                        label: "否",
                                                        value: "0"
                                                    }
                                                ]
                                            },
                                        },
                                        {
                                            table: {
                                                title: '整改落实情况',
                                                width: 300,
                                                tooltip: 23,
                                                dataIndex: 'correctiveCase',
                                                key: 'correctiveCase',
                                                tdEdit: false
                                            },
                                            form: {
                                                label: '整改落实情况',
                                                field: 'correctiveCase',
                                                type: 'textarea',
                                                autoSize: {
                                                    minRows: 1,
                                                    maxRows: 4
                                                },
                                                placeholder: '请输入'
                                            }
                                        }
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "新增行"
                                        },
                                        {
                                            name: 'del',
                                            icon: 'delete',
                                            type: 'danger',
                                            label: '删除'
                                        }
                                    ]
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '检查方附件',
                                field: 'zjTzFileList',
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '投资事业部-综合督查报告'
                                    }
                                },
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '项目方附件',
                                field: 'replyFileList',
                                addDisabled: true,
                                editDisabled: true,
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '投资事业部-综合督查报告'
                                    }
                                },
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'registerDate',
                                type: 'date',
                                label: '登记日期',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                                initialValue: () => {
                                    return new Date()
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'registerPerson',
                                type: 'string',
                                label: '登记用户',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                                initialValue: (obj) => {
                                    return obj.loginAndLogoutInfo.loginInfo.userInfo.realName
                                }
                            },
                        }
                    ]}
                    method={{
                        editClick: (obj) => {
                            if ((obj.selectedRows.length === 1)) {
                                if (obj.selectedRows[0].correctiveId === '0') {
                                    this.table.clearSelectedRows();
                                } else {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('已下达/已落实的不能修改!');
                                    this.table.clearSelectedRows();
                                }
                            } else {
                                Msg.warn('请选择一条数据');
                            }
                        },
                        cheHuiClick: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                let bb = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].correctiveId === '0' || obj.selectedRows[i].correctiveId === '2') {
                                        bb.push(obj.selectedRows[i].correctiveId);
                                    }
                                }
                                if (bb.length > 0) {
                                    Msg.warn('未整改下达的消息不能撤回！');
                                } else {
                                    confirm({
                                        title: "确定撤回么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchRecallZjTzCompSupReport', obj.selectedRows).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.table.refresh();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    });
                                }
                            } else {
                                Msg.warn('请选择数据');
                            }

                        },
                        zhengGaiXiaDa: (obj) => {
                            this.table.clearSelectedRows();
                            if (obj.selectedRows.length > 0) {
                                if ((obj.selectedRows.length === 1)) {
                                    if (obj.selectedRows[0].correctiveId === '0' || obj.selectedRows[0].correctiveId === '2') {
                                        this.setState({
                                            visibleSend: true,
                                            compSupReportId: obj.selectedRows[0].compSupReportId
                                        });
                                    } else {
                                        Msg.warn('已整改下达的消息不能整改下达！')
                                    }

                                } else {
                                    Msg.warn('请选择一条数据');
                                }
                            } else {
                                Msg.warn('请选择数据');
                            }

                        },
                        filestExport: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                confirm({
                                    title: "确定导出附件么?",
                                    okText: "确认",
                                    cancelText: "取消",
                                    onOk: () => {
                                        myFetch('batchExportZjTzCompSupReportFile', obj.selectedRows).then(
                                            ({ success, message, data }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    window.location.href = data;
                                                    this.table.refresh();
                                                    this.table.clearSelectedRows();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
                                    }
                                })
                            } else {
                                Msg.warn('请选择数据');
                            }
                        },
                        delClick: (obj) => {
                            const { myFetch } = obj.props;
                            let bb = [];
                            if (obj.selectedRows.length > 0) {
                                for (var i = 0; i < obj.selectedRows.length; i++) {

                                    if (obj.selectedRows[i].correctiveId === '1') {
                                        bb.push(obj.selectedRows[i].correctiveId);
                                    }
                                }
                                if (bb.length > 0) {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('已整改下达的不能删除!');
                                    this.table.clearSelectedRows();
                                } else {
                                    confirm({
                                        title: "确定删除么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZjTzCompSupReport', obj.selectedRows).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.table.refresh();
                                                        this.table.clearSelectedRows();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    })
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
                    fieldsValueChange={({ form, name }, changedFields, allValues) => {
                        if (changedFields && changedFields[0] && changedFields[0].name[0] === 'zjTzSupContentList') {
                            if (allValues && allValues.zjTzSupContentList) {
                                let contList = allValues.zjTzSupContentList;
                                for (let m = 0; m < contList.length; m++) {
                                    if (contList[m].typeId === '1') {
                                        contList[m].needCorrectiveId = '0';
                                    }
                                }
                                this.zjTzSupContentListTable.qnnSetState({
                                    data: allValues.zjTzSupContentList
                                })
                            }

                        }
                    }}
                />
                <Modal
                    width='600px'
                    style={{ top: '0' }}
                    title="下达"
                    visible={visibleSend}
                    footer={null}
                    onCancel={this.handleCancelSend}
                    bodyStyle={{ width: '600px' }}
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
                                    type: 'string',
                                    field: "compSupReportId",
                                    initialValue: compSupReportId,
                                    hide: true
                                },
                                {
                                    field: 'contentDesc',
                                    type: 'textarea',
                                    label: "内容描述",//???
                                    required: true,
                                    placeholder: '请输入'
                                },
                                // {
                                //     label: '发送给',
                                //     field: 'personList',
                                //     type: 'treeSelect',
                                //     // required: true,
                                //     treeSelectOption: {
                                //         help: true,
                                //         maxNumber: 1,
                                //         selectType: '2',
                                //         fetchConfig: {
                                //             apiName: 'getSysDepartmentUserAllTree',
                                //         },
                                //         useCollect:true,
                                //         collectApi:"appGetSysFrequentContactsList",  //查询收藏人员     接受后台参数[{xx:xxx,...}]
                                //         collectApiByAdd:"appAddSysFrequentContacts", //新增收藏人员   传给后台的参数[{xx:xxx,...}]
                                //         collectApiByDel:"appRemoveSysFrequentContacts", //删除收藏人员
                                //         search: true,
                                //         searchPlaceholder: '姓名、账号、电话',
                                //         searchParamsKey: 'search',
                                //         searchOtherParams: { pageSize: 999 }
                                //     }
                                // },
                            ]}
                            btns={[
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                    isValidate: false,
                                    onClick: () => {
                                        this.setState({
                                            visibleSend: false,
                                            loadingSend: false
                                        })
                                    }
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    onClick: (obj) => {
                                        this.setState({
                                            loadingSend: true
                                        })
                                        this.props.myFetch('correctiveZjTzCompSupReport', obj.values).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.table.refresh();
                                                    this.setState({
                                                        visibleSend: false,
                                                        loadingSend: false
                                                    })
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );

                                    }
                                }
                            ]}
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