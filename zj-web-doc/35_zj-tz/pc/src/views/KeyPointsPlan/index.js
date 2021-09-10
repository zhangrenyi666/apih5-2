import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import moment from 'moment';
import s from "./style.less";
import { message as Msg, Modal, Spin } from "antd";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.implementPointId
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
        console.log(props.loginAndLogoutInfo.loginInfo.userInfo);
        this.state = {
            implementPointId: '',
            companyId: props.loginAndLogoutInfo.loginInfo.userInfo.companyList[0].companyId,
            companyName: props.loginAndLogoutInfo.loginInfo.userInfo.companyList[0].companyName,
            curCompanyId: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.value : '',
            curCompanyName: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.label : '',
            departmentList: [
                {
                    label: props.loginAndLogoutInfo.loginInfo.userInfo.departmentList[0].departmentName,
                    value: props.loginAndLogoutInfo.loginInfo.userInfo.departmentList[0].departmentId,
                }
            ]
        }
    }
    componentDidMount() {

    }
    render() {
        const { implementPointId, companyId, companyName, departmentList, curCompanyId, curCompanyName } = this.state;
        let exT1 = this.props.loginAndLogoutInfo.loginInfo.userInfo.ext1;
        let inivi = [];
        inivi = departmentList;
  
        const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                        apiName: 'getZjTzBrandImplementPointList',
                        otherParams: {
                            companyId: projectId
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'implementPointId',
                                type: 'string',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'companyId',
                                type: 'string',
                                hide: true,
                                initialValue: companyId
                            }
                        },
                        {
                            table: {
                                title: '年度',
                                dataIndex: 'yearDate',
                                onClick: 'detail',
                                key: 'yearDate',
                                filter: true,
                                render: (data) => {
                                    if (data) {
                                        return moment(data).format('YYYY')
                                    }
                                    return ''
                                }
                            },
                            form: {
                                type: 'year',
                                field: 'yearDate',
                                placeholder: '请输入',
                                required: true,
                                editDisabled: true,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '所属机构',
                                dataIndex: 'companyName',
                                key: 'companyName'
                            },
                            form: {
                                field: 'projectList',
                                required: true,
                                editDisabled: true,
                                addDisabled: true,
                                initialValue: inivi,
                                type: "treeSelect",
                                treeSelectOption: {
                                    selectType: '1',
                                    maxNumber: 1,
                                    fetchConfig: {
                                        apiName: 'getSysDepartmentUserAllTree',
                                    },
                                    useCollect: true,
                                    collectApi: "appGetSysFrequentContactsList",  //查询收藏人员     接受后台参数[{xx:xxx,...}]
                                    collectApiByAdd: "appAddSysFrequentContacts", //新增收藏人员   传给后台的参数[{xx:xxx,...}]
                                    collectApiByDel: "appRemoveSysFrequentContacts", //删除收藏人员
                                    search: true,
                                    searchPlaceholder: '姓名、账号、电话',
                                    // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                                    searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                    searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]

                                }
                            },
                        },
                        {
                            table: {
                                title: '登记日期',
                                dataIndex: 'registerDate',
                                key: 'registerDate',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                field: 'registerDate',
                                type: 'date',
                                initialValue: new Date(),
                                placeholder: '请选择',
                                addDisabled: true,
                                editDisabled: true,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '登记人',
                                dataIndex: 'registerPerson',
                                key: 'registerPerson'
                            },
                            form: {
                                field: 'registerPerson',
                                type: 'string',
                                label: '登记人',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 21 }
                                    }
                                },
                                initialValue: (obj) => {
                                    return obj.loginAndLogoutInfo.loginInfo.userInfo.realName
                                }
                            },
                        },
                        {
                            table: {
                                title: '内容简介',
                                dataIndex: 'content',
                                key: 'content'
                            },
                            form: {
                                field: 'content',
                                type: 'textarea',
                                label: '内容简介',
                                required: true,
                                placeholder: '请输入'
                            },
                        },
                        {
                            table: {
                                title: '状态',
                                filter: true,
                                dataIndex: 'releaseId',
                                key: 'releaseId',
                                type: 'select'
                            },
                            isInForm: false,
                            form: {
                                type: 'select',
                                field: 'releaseId',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    {
                                        label: "未上报",
                                        value: "0"
                                    },
                                    {
                                        label: "已上报",
                                        value: "1"
                                    },
                                    {
                                        label: "被退回",
                                        value: "2"
                                    },
                                    {
                                        label: "投资事业部录入",
                                        value: "4"
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '是否在首页广而告之',
                                dataIndex: 'homeShow',
                                filter: true,
                                width: 160,
                                key: 'homeShow',
                                render: (data) => {
                                    if (data === '0') {
                                        return '否'
                                    } else {
                                        return '是'
                                    }
                                }
                            },
                            isInForm: false,
                            form: {
                                type: "select",
                                field: "homeShow",
                                hide: true,
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
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '附件',
                                field: 'zjTzFileList',
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '项目、专项实施方案要点'
                                    }
                                }
                            }
                        }
                    ]}
                    method={{
                        editClick: (obj) => {
                            if (obj.selectedRows[0].releaseId === '1') {
                                obj.btnCallbackFn.closeDrawer();
                                Msg.warn('已上报的不能修改!');
                                this.table.clearSelectedRows();
                            } else {
                                this.table.clearSelectedRows();
                            }
                        },
                        guangErGaoZhi: (obj) => {
                            this.table.clearSelectedRows();
                            if (obj.selectedRows.length > 0) {
                                if ((obj.selectedRows.length === 1)) {
                                    
                                    confirm({
                                        title: "确定广而告之到首页么？",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            this.props.myFetch('toHomeShowZjTzBrandImplementPoint', obj.selectedRows[0]).then(
                                                ({ success, message, data }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.table.refresh();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    })
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
                                        myFetch('batchExportZjTzBrandImplementPointFile', obj.selectedRows).then(
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
                            let aa = [];
                            if (obj.selectedRows.length > 0) {
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].releaseId === '1') {
                                        aa.push(obj.selectedRows[i].releaseId);
                                    }
                                }
                                if (aa.length > 0) {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('已上报的不能删除!');
                                    this.table.clearSelectedRows();
                                } else {
                                    confirm({
                                        title: "确定删除么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZjTzBrandImplementPoint', obj.selectedRows).then(
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
                        },
                        shangBao: (obj) => {
                            const { myFetch } = obj.props;
                            let arry = [];
                            let tzsyblr = '0';
                            if (obj.selectedRows.length > 0) {
                                for (let j = 0; j < obj.selectedRows.length; j++) {
                                    if (obj.selectedRows[j].releaseId === '1') {
                                        arry.push(obj.selectedRows[j])
                                    } else { }
                                    if (obj.selectedRows[j].releaseId === '4') {
                                        tzsyblr = '4';
                                    }
                                }
                                if (tzsyblr === '4') {
                                    Msg.warn('由投资事业部录入的数据不可以上报！')
                                } else {
                                    if (arry.length > 0) {
                                        Msg.warn('已上报的数据不允许上报！')
                                    } else {
                                        confirm({
                                            title: "确定上报么?",
                                            okText: "确认",
                                            cancelText: "取消",
                                            onOk: () => {
                                                myFetch('batchReleaseZjTzBrandImplementPoint', obj.selectedRows).then(
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
                                }

                            } else {
                                Msg.warn('请选择数据!');
                            }
                        },
                        tuiHui: (obj) => {
                            const { myFetch } = obj.props;
                            let arry = [];
                            let tzsyblr = '0';
                            if (obj.selectedRows.length > 0) {
                                for (let j = 0; j < obj.selectedRows.length; j++) {
                                    if (obj.selectedRows[j].releaseId === '2' || obj.selectedRows[j].releaseId === '0') {
                                        arry.push(obj.selectedRows[j])
                                    } else { }
                                    if (obj.selectedRows[j].releaseId === '4') {
                                        tzsyblr = '4';
                                    }
                                }
                                if (tzsyblr === '4') {
                                    Msg.warn('由投资事业部录入的数据不可退回！')
                                } else {
                                    if (arry.length > 0) {
                                        Msg.warn('已上报/被退回的数据不允许退回！')
                                    } else {
                                        confirm({
                                            title: "确定退回么?",
                                            okText: "确认",
                                            cancelText: "取消",
                                            onOk: () => {
                                                myFetch('batchRecallZjTzBrandImplementPoint', obj.selectedRows).then(
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
                />
            </div>
        );
    }
}

export default index;