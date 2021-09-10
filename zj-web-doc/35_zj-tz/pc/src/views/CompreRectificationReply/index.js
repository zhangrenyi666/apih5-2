import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";
import { message as Msg, Modal } from "antd";
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
            companyId: props.loginAndLogoutInfo.loginInfo.userInfo.companyList[0].companyId,
            companyName: props.loginAndLogoutInfo.loginInfo.userInfo.companyList[0].companyName
        }
    }
    componentDidMount() {

    }
    render() {
        const { companyId } = this.state;
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
                        apiName: 'getZjTzCompSupReportReplyList',
                        otherParams: {
                            projectId: projectId
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
                                disabled: true,
                                editDisabled: true,
                                addDisabled: true,
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
                                label: '检查日期',
                                type: 'date',
                                required: true,
                                field: 'checkDate',
                                disabled: true,
                                editDisabled: true,
                                addDisabled: true,
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
                                label: '督查分类',
                                field: 'supClassId',
                                disabled: true,
                                editDisabled: true,
                                addDisabled: true,
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
                                editDisabled: true,
                                addDisabled: true,
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
                                type: 'select'
                            },
                            form: {
                                label: '督查对象',
                                field: 'projectId',
                                type: 'selectByPaging',
                                required: true,
                                editDisabled: true,
                                addDisabled: true,
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
                                key: 'checkDate'
                            },
                            isInForm: false
                        },
                        // {
                        //     table: {
                        //         title: '是否需要整改',
                        //         dataIndex: 'correctiveName',
                        //         key: 'correctiveName',
                        //         // width: 100,
                        //     },
                        //     isInForm: false
                        // },
                        {
                            table: {
                                title: '下达说明',
                                dataIndex: 'contentDesc',
                                key: 'contentDesc',
                                width: 140,
                                tooltip: 23,
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '发布对象',
                                dataIndex: 'correctiveUserName',
                                key: 'correctiveUserName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '下达状态',
                                dataIndex: 'correctiveName',
                                key: 'correctiveName',
                                width: 120,
                            },
                            isInForm: false
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
                                // dependencies: ["correctiveDate","correctiveCase"],
                                // diyRules: "bind:_tableValidator", 
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
                                    isShowRowSelect: false,
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
                                        this.tables = me;
                                    },
                                    rowDisabledCondition: (rowData, props) => {
                                        return rowData.typeId === '1' || rowData.needCorrectiveId === '0'
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
                                                dataIndex: 'typeId',
                                                key: 'typeId',
                                                tdEdit: false,
                                                type: 'select',
                                            },
                                            form: {
                                                label: '类型',
                                                type: 'select',
                                                field: 'typeId',
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
                                                dataIndex: 'needCorrectiveId',
                                                key: 'needCorrectiveId',
                                                fetchConfig: {
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
                                                // tdEdit: false,
                                                type: 'select',
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
                                                title: '整改完成时间',
                                                dataIndex: 'correctiveDate',
                                                key: 'correctiveDate',
                                                format: 'YYYY-MM-DD',
                                                tdEdit: true
                                            },
                                            form: {
                                                label: '整改完成时间',
                                                field: 'correctiveDate',
                                                type: 'date',
                                                placeholder: '请选择'
                                            }
                                        },

                                        {
                                            table: {
                                                title: '整改落实情况',
                                                width: 300,
                                                tooltip: 23,
                                                dataIndex: 'correctiveCase',
                                                key: 'correctiveCase',
                                                tdEdit: true
                                            },
                                            form: {
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

                                    ]
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '检查方附件',
                                field: 'zjTzFileList',
                                disabled: true,
                                editDisabled: true,
                                addDisabled: true,
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '投资事业部-项目整改落实回复'
                                    }
                                },
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '',
                                field: 'otherTYpe',
                                hide: true,
                                type: 'string',
                                initialValue: '2'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '项目方附件',
                                field: 'replyFileList',
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '投资事业部-项目整改落实回复'
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
                        isCanSubmit: ({ params }, callback) => {
                            let arry = params.zjTzSupContentList;
                            let submitFlag = 'ok';
                            for (let i = 0; i < arry.length; i++) {
                                // 1=是
                                if (arry[i].needCorrectiveId === '1') {
                                    if (arry[i].correctiveCase && arry[i].correctiveDate) { } else {
                                        submitFlag = 'unOk';
                                        break;
                                    }
                                } else {

                                }

                            }
                            if (submitFlag === 'ok') {
                                callback(true);
                            } else {
                                Msg.error('表格内必填项未填写，不能保存！');
                                callback(false);
                            }
                        },
                        reply: () => {
                            this.table.clearSelectedRows();
                        },
                        // 上报---
                        shangBaoClick: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            console.log(obj.selectedRows[0].compSupReportId);
                            confirm({
                                title: "确定上报么?",
                                okText: "确认",
                                cancelText: "取消",
                                onOk: () => {
                                    myFetch('reportZjTzCompSupReport', { compSupReportId: obj.selectedRows[0].compSupReportId }).then(
                                        ({ data, success, message }) => {
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
                        },
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