import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { message as Msg, Modal } from "antd";
const confirm = Modal.confirm;

class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
        }
    }
    componentDidMount() {

    }
    render() {
        const { realName, curCompany: { projectShortName, projectId } } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    fetchConfig={{
                        "apiName": "getZjTzRunFileList",
                        otherParams: {
                            projectId: projectId
                        }
                    }}
                    {...window.ZjTzRunFile}
                    formConfig={[
                        {
                            "isInTable": false,
                            "form": {
                                "field": "runFileId",
                                "type": "string",
                                "placeholder": "请输入",
                                "hide": true
                            },
                            "_id": "0",
                            "_edit": false
                        },
                        {
                            "table": {
                                "title": "项目名称",
                                dataIndex: "projectId",
                                key: 'projectId',
                                width: 200,
                                filter: true,
                                type: 'select'
                            },
                            form: {
                                field: 'projectId',
                                "label": "项目名称",
                                type: 'select',
                                showSearch: true,
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: () => {
                                    return projectId
                                },
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProManageList"
                                },
                                placeholder: '请选择',
                            },
                        },
                        {
                            "table": {
                                "title": "文件类型",
                                width: 130,
                                "dataIndex": "fileForm",
                                "type": "select",
                                filter: true
                            },
                            "form": {
                                "type": "select",
                                required: true,
                                "field": "fileForm",
                                "placeholder": "请输入",
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'wenJianLeiXingFile'
                                    }
                                },
                            },
                        },
                        {
                            "table": {
                                "title": "文件类别",
                                width: 130,
                                "dataIndex": "fileType",
                                "type": "select",
                                filter: true
                            },
                            "form": {
                                "type": "select",
                                required: true,
                                "field": "fileType",
                                "placeholder": "请输入",
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'wenJianLeiBie'
                                    }
                                },
                            },
                        },
                        {
                            "isInForm": true,
                            "table": {
                                "title": "文号",
                                filter: true,
                                width: 120,
                                onClick: 'detail',
                                "dataIndex": "referenceNumber"
                            },
                            "_id": "5",
                            "_edit": false,
                            "form": {
                                "type": "string",
                                required: true,
                                "label": "文号",
                                "field": "referenceNumber",
                                "placeholder": "请输入"
                            },
                        },
                        {
                            "isInForm": false,
                            "table": {
                                "title": "文件名称",
                                filter: true,
                                width: 140,
                                tooltip: 16,
                                "dataIndex": "fileName"
                            },
                            "_id": "7",
                            "_edit": false,
                            "form": {
                                type: 'string',
                                field: 'fileName'
                            }
                        },
                        {
                            "isInTable": false,
                            "form": {
                                "type": "string",
                                required: true,
                                "label": "文件名称",
                                "field": "fileName",
                                "placeholder": "请输入"
                            },
                            "_id": "8",
                            "_edit": false
                        },
                        {
                            "isInForm": false,
                            "table": {
                                "title": "发布时间",
                                filter: true,
                                width: 120,
                                "dataIndex": "releaseDate",
                                format: 'YYYY-MM-DD',
                            },
                            "_id": "9",
                            "_edit": false,
                            "form": {
                                type: 'date',
                                field: 'releaseDate'
                            }
                        },
                        {
                            "isInTable": false,
                            "form": {
                                "type": "date",
                                required: true,
                                "label": "发布时间",
                                "field": "releaseDate",
                                "placeholder": "请输入"
                            },
                            "_id": "10",
                            "_edit": false
                        },
                        {
                            "isInForm": false,
                            "table": {
                                "title": "是否有效",
                                filter: true,
                                width: 100,
                                "dataIndex": "effectFlag",
                                type: 'select',
                            },
                            "_id": "11",
                            "_edit": false,
                            "form": {
                                "type": "select",
                                required: true,
                                "label": "是否有效",
                                "field": "effectFlag",
                                "placeholder": "请输入",
                                "optionConfig": {
                                    label: 'label',
                                    value: 'value'
                                },
                                "optionData": [
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
                            "isInTable": false,
                            "form": {
                                "type": "select",
                                required: true,
                                "label": "是否有效",
                                "field": "effectFlag",
                                "placeholder": "请输入",
                                "optionConfig": {
                                    label: 'label',
                                    value: 'value'
                                },
                                "optionData": [
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
                            "_id": "12",
                            "_edit": false
                        },
                        {
                            "isInForm": false,
                            "table": {
                                "title": "主要内容或变化说明",
                                width: 200,
                                tooltip: 23,
                                "dataIndex": "content"
                            },
                            "_id": "13",
                            "_edit": false,
                            "form": {}
                        },
                        {
                            "isInTable": false,
                            "form": {
                                "type": "textarea",
                                required: true,
                                "label": "主要内容或变化说明",
                                "field": "content",
                                "placeholder": "请输入"
                            },
                            "_id": "14",
                            "_edit": false
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '附件',
                                required: true,
                                field: 'zjTzFileList',
                                type: 'files',
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '运营文件管理'
                                    }
                                }
                            }
                        }
                    ]}
                    method={{
                        addClick: (obj) => {
                            if (projectId === 'all') {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.clearSelectedRows();
                                Msg.warn('请选择右上角项目！')
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
                                        myFetch('batchExportZjTzRunFile', obj.selectedRows).then(
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
                        editClick: () => {
                            this.table.clearSelectedRows();
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