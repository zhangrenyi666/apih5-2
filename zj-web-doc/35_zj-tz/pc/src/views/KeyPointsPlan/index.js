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
                                title: '??????',
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
                                placeholder: '?????????',
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
                                title: '????????????',
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
                                    collectApi: "appGetSysFrequentContactsList",  //??????????????????     ??????????????????[{xx:xxx,...}]
                                    collectApiByAdd: "appAddSysFrequentContacts", //??????????????????   ?????????????????????[{xx:xxx,...}]
                                    collectApiByDel: "appRemoveSysFrequentContacts", //??????????????????
                                    search: true,
                                    searchPlaceholder: '????????????????????????',
                                    // searchApi:'getSysDepartmentUserAllTree',  //??????????????????api  [string]
                                    searchParamsKey: 'search',//???????????????K ?????????'searchText'   [string]
                                    searchOtherParams: { pageSize: 999 }//????????????????????????  [object]

                                }
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'registerDate',
                                key: 'registerDate',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                field: 'registerDate',
                                type: 'date',
                                initialValue: new Date(),
                                placeholder: '?????????',
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
                                title: '?????????',
                                dataIndex: 'registerPerson',
                                key: 'registerPerson'
                            },
                            form: {
                                field: 'registerPerson',
                                type: 'string',
                                label: '?????????',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
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
                                title: '????????????',
                                dataIndex: 'content',
                                key: 'content'
                            },
                            form: {
                                field: 'content',
                                type: 'textarea',
                                label: '????????????',
                                required: true,
                                placeholder: '?????????'
                            },
                        },
                        {
                            table: {
                                title: '??????',
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
                                        label: "?????????",
                                        value: "0"
                                    },
                                    {
                                        label: "?????????",
                                        value: "1"
                                    },
                                    {
                                        label: "?????????",
                                        value: "2"
                                    },
                                    {
                                        label: "?????????????????????",
                                        value: "4"
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '???????????????????????????',
                                dataIndex: 'homeShow',
                                filter: true,
                                width: 160,
                                key: 'homeShow',
                                render: (data) => {
                                    if (data === '0') {
                                        return '???'
                                    } else {
                                        return '???'
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
                                        label: "???",
                                        value: "0"
                                    },
                                    {
                                        label: "???",
                                        value: "1"
                                    }
                                ]
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '??????',
                                field: 'zjTzFileList',
                                showDownloadIcon: true,//????????????????????????
                                onPreview: "bind:_docFilesByOfficeUrl",//365??????
                                
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '?????????????????????????????????'
                                    }
                                }
                            }
                        }
                    ]}
                    method={{
                        editClick: (obj) => {
                            if (obj.selectedRows[0].releaseId === '1') {
                                obj.btnCallbackFn.closeDrawer();
                                Msg.warn('????????????????????????!');
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
                                        title: "?????????????????????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
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
                                    Msg.warn('?????????????????????');
                                }
                            } else {
                                Msg.warn('???????????????');
                            }
                        },
                        filestExport: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                confirm({
                                    title: "??????????????????????",
                                    okText: "??????",
                                    cancelText: "??????",
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
                                Msg.warn('???????????????');
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
                                    Msg.warn('????????????????????????!');
                                    this.table.clearSelectedRows();
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
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
                                Msg.warn('???????????????!');
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
                                    Msg.warn('???????????????????????????????????????????????????')
                                } else {
                                    if (arry.length > 0) {
                                        Msg.warn('????????????????????????????????????')
                                    } else {
                                        confirm({
                                            title: "????????????????",
                                            okText: "??????",
                                            cancelText: "??????",
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
                                Msg.warn('???????????????!');
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
                                    Msg.warn('????????????????????????????????????????????????')
                                } else {
                                    if (arry.length > 0) {
                                        Msg.warn('?????????/????????????????????????????????????')
                                    } else {
                                        confirm({
                                            title: "????????????????",
                                            okText: "??????",
                                            cancelText: "??????",
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
                                Msg.warn('???????????????!');
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