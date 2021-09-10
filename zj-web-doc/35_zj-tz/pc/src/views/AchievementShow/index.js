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
                    desc={ext1 === '1' ? '发布：使数据全平台用户可见；广而告之：将已发布的数据展示至首页' : null}
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
                                label: '填报单位',
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
                                    collectApi: "appGetSysFrequentContactsList",  //查询收藏人员     接受后台参数[{xx:xxx,...}]
                                    collectApiByAdd: "appAddSysFrequentContacts", //新增收藏人员   传给后台的参数[{xx:xxx,...}]
                                    collectApiByDel: "appRemoveSysFrequentContacts", //删除收藏人员
                                    searchPlaceholder: '姓名、账号、电话',
                                    // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                                    searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                    searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]

                                }
                            },
                        },
                        {
                            table: {
                                title: '成果名称',
                                tooltip: 23,
                                width: 260,
                                dataIndex: 'title',
                                onClick: 'detail',
                                key: 'title'
                            },
                            form: {
                                type: 'string',
                                field: 'title',
                                placeholder: '请输入',
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
                                title: '获得主体',
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
                                title: '获得时间',
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
                        //         label:'备注',
                        //         field:'bz',
                        //         type: 'textarea'
                        //     }
                        // },
                        {
                            table: {
                                title: '成果权属单位',
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
                                            <Col span={21} style={{paddingLeft:'12px'}}>备注：填报获得主体全称</Col>
                                        </Row>
                                    );
                                }
                            }
                        },
                        {
                            table: {
                                title: '成果类型',
                                width: 100,
                                dataIndex: 'resultTypeName',
                                key: 'resultTypeName'
                            },
                            form: {
                                type: 'select',
                                field: 'resultTypeId',
                                placeholder: '请选择',
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
                                title: '成果级别',
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
                                title: '内容简介',
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
                                label: "内容简介",
                                field: "content",
                                fetchConfig: {
                                    uploadUrl: window.configs.domain + 'upload'
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '登记日期',
                                type: 'date',
                                field: 'createTime',
                                initialValue: new Date(),
                                placeholder: '请选择',
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
                                label: '登记人',
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
                                initialValue: realName
                            },
                        },


                        {
                            table: {
                                title: '状态',
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
                                        label: "未发布",
                                        value: "0"
                                    },
                                    {
                                        label: "发布",
                                        value: "1"
                                    }
                                ]
                            },
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
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '专项活动-成果展示'
                                    }
                                },
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示

                            }
                        }
                    ]}
                    method={{
                        editClick: (obj) => {
                            if (obj.selectedRows[0].releaseId === '1') {
                                obj.btnCallbackFn.closeDrawer();
                                Msg.warn('已发布的不能修改!');
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
                                    Msg.warn('已发布的消息不能发布！');
                                } else {
                                    confirm({
                                        title: "确定发布么?",
                                        okText: "确认",
                                        cancelText: "取消",
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
                                Msg.warn('请选择数据');
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
                                    Msg.warn('未发布的消息不能撤回！');
                                } else {
                                    confirm({
                                        title: "确定撤回么?",
                                        okText: "确认",
                                        cancelText: "取消",
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
                                Msg.warn('请选择数据');
                            }

                        },
                        guangErGaoZhi: (obj) => {
                            this.table.clearSelectedRows();
                            if (obj.selectedRows.length > 0) {
                                if ((obj.selectedRows.length === 1)) {
                                    if (obj.selectedRows[0].releaseId === '1') {
                                        confirm({
                                            title: "确定广而告之到首页么？",
                                            okText: "确认",
                                            cancelText: "取消",
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
                                        Msg.warn('未发布的消息不能广而告之！')
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
                                    Msg.warn('已发布的不能删除!');
                                    this.table.clearSelectedRows();
                                } else {
                                    confirm({
                                        title: "确定删除么?",
                                        okText: "确认",
                                        cancelText: "取消",
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
                                Msg.warn('请选择数据!');
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
                                fileName: '一公局集团投资项目获奖情况统计表',
                                companyId: projectId
                            }
                            let URL = `${domain + "exportZjTzBrandResultShowList"}`;
                            confirm({
                                content: '确定导出数据吗?',
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