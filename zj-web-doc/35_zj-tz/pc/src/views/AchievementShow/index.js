import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";
import { message as Msg, Modal, Tooltip, Row, Col } from "antd";
import downLoad from "../modules/download";
const confirm = Modal.confirm;
const config = {

    antd: {
        rowKey: function (row) {
            return row.resultShowId
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
            resultShowId: '',
            companyId: props.loginAndLogoutInfo.loginInfo.userInfo.companyList[0].companyId,
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
    componentDidMount () {

    }
    render () {
        const { resultShowId, companyId, departmentList, curCompanyId, curCompanyName } = this.state;
        const { realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
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
                        apiName: 'getZjTzBrandResultShowList',
                        otherParams: {
                            companyId: projectId
                        }
                    }}
                    {...config}
                    desc={ext1 === '1' ? '?????????????????????????????????????????????????????????????????????????????????????????????' : null}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'resultShowId',
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
                            isInTable: false,
                            form: {
                                label: '????????????',
                                field: 'projectList',
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: inivi,
                                type: "treeSelect",
                                treeSelectOption: {
                                    selectType: '1',
                                    maxNumber: 1,
                                    fetchConfig: {
                                        apiName: 'getSysDepartmentUserAllTree',
                                    },
                                    search: true,
                                    useCollect: true,
                                    collectApi: "appGetSysFrequentContactsList",  //??????????????????     ??????????????????[{xx:xxx,...}]
                                    collectApiByAdd: "appAddSysFrequentContacts", //??????????????????   ?????????????????????[{xx:xxx,...}]
                                    collectApiByDel: "appRemoveSysFrequentContacts", //??????????????????
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
                                tooltip: 23,
                                width: 260,
                                dataIndex: 'title',
                                onClick: 'detail',
                                key: 'title'
                            },
                            form: {
                                type: 'string',
                                field: 'title',
                                placeholder: '?????????',
                                required: true,
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
                                width: 100,
                                dataIndex: 'getSubjectId',
                                key: 'getSubjectId',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'huoDeZhuTi'
                                    }
                                },
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
                                required: true
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 100,
                                dataIndex: 'getTime',
                                key: 'getTime',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
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
                                required: true
                            }
                        },
                        // {
                        //     isInTable:false,
                        //     form: {
                        //         label:'??????',
                        //         field:'bz',
                        //         type: 'textarea'
                        //     }
                        // },
                        {
                            table: {
                                title: '??????????????????',
                                width: 200,
                                tooltip: 18,
                                dataIndex: 'resultUnit',
                                key: 'resultUnit'
                            },
                            form: {
                                required: true,
                                type: "string"
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: "component",
                                field: "component1",
                                Component: obj => {
                                    return (
                                        <Row style={{color:'red',fontSize:'16px'}}>
                                            <Col span={3}></Col>
                                            <Col span={21} style={{paddingLeft:'12px'}}>?????????????????????????????????</Col>
                                        </Row>
                                    );
                                }
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 100,
                                dataIndex: 'resultTypeName',
                                key: 'resultTypeName'
                            },
                            form: {
                                type: 'select',
                                field: 'resultTypeId',
                                placeholder: '?????????',
                                required: true,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'chengGuoLeiXing'
                                    }
                                },
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
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 100,
                                dataIndex: 'resultLevelId',
                                key: 'resultLevelId',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                required: true,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'chengGuoJiBie'
                                    }
                                },
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
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 400,
                                dataIndex: 'content',
                                key: 'content',
                                render: (data) => {
                                    if (data) {
                                        data = data.replace(/(\n)/g, "");
                                        data = data.replace(/(\t)/g, "");
                                        data = data.replace(/(\r)/g, "");
                                        data = data.replace(/<\/?[^>]*>/g, "");
                                        data = data.replace(/\s*/g, "");
                                        data = data.replace(/&nbsp;/g, "");
                                        return <div>
                                            <Tooltip title={data}>
                                                <div style={{ maxWidth: '380px', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>{data}</div>
                                            </Tooltip>

                                        </div>
                                    } else {
                                        return ''
                                    }

                                }
                            },
                            form: {
                                type: "richtext",
                                label: "????????????",
                                field: "content",
                                fetchConfig: {
                                    uploadUrl: window.configs.domain + 'upload'
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
                                type: 'date',
                                field: 'createTime',
                                initialValue: new Date(),
                                placeholder: '?????????',
                                addDisabled: true,
                                editDisabled: true,
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
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'createUserName',
                                type: 'string',
                                label: '?????????',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
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
                                initialValue: realName
                            },
                        },


                        {
                            table: {
                                title: '??????',
                                width: 100,
                                dataIndex: 'releaseName',
                                key: 'releaseName'
                            },
                            isInForm: false,
                            form: {
                                type: "select",
                                field: "releaseId",
                                hide: true,
                                optionData: [
                                    {
                                        label: "?????????",
                                        value: "0"
                                    },
                                    {
                                        label: "??????",
                                        value: "1"
                                    }
                                ]
                            },
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
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '????????????-????????????'
                                    }
                                },
                                showDownloadIcon: true,//????????????????????????
                                onPreview: "bind:_docFilesByOfficeUrl",//365??????

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
                        faBuClick: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {

                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].releaseId === '1') {
                                        aa.push(obj.selectedRows[i].releaseId);
                                    }
                                }
                                if (aa.length > 0) {
                                    Msg.warn('?????????????????????????????????');
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
                                        onOk: () => {
                                            myFetch('batchReleaseZjTzBrandResultShow', obj.selectedRows).then(
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
                                Msg.warn('???????????????');
                            }
                        },
                        cheHuiClick: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].releaseId === '0') {
                                        aa.push(obj.selectedRows[i].releaseId);
                                    }
                                }
                                if (aa.length > 0) {
                                    Msg.warn('?????????????????????????????????');
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
                                        onOk: () => {
                                            myFetch('batchRecallZjTzBrandResultShow', obj.selectedRows).then(
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
                                Msg.warn('???????????????');
                            }

                        },
                        guangErGaoZhi: (obj) => {
                            this.table.clearSelectedRows();
                            if (obj.selectedRows.length > 0) {
                                if ((obj.selectedRows.length === 1)) {
                                    if (obj.selectedRows[0].releaseId === '1') {
                                        confirm({
                                            title: "?????????????????????????????????",
                                            okText: "??????",
                                            cancelText: "??????",
                                            onOk: () => {
                                                this.props.myFetch('toHomeShowZjTzBrandResultShow', obj.selectedRows[0]).then(
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
                                        Msg.warn('???????????????????????????????????????')
                                    }

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
                                        myFetch('batchExportZjTzBrandResultShowFile', obj.selectedRows).then(
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
                                            myFetch('batchDeleteUpdateZjTzBrandResultShow', obj.selectedRows).then(
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
                        exportOnclick: (obj) => {
                            const {
                                loginAndLogoutInfo: {
                                    loginInfo: { token }
                                },
                                myPublic: { domain }
                            } = this.props;
                            let body = {
                                fileName: '????????????????????????????????????????????????',
                                companyId: projectId
                            }
                            let URL = `${domain + "exportZjTzBrandResultShowList"}`;
                            confirm({
                                content: '??????????????????????',
                                centered: true,
                                onOk: () => {
                                    downLoad(URL, body, { token });
                                }
                            });
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