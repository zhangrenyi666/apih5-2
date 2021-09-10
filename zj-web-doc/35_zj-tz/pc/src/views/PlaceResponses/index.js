import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import { push } from "react-router-redux";
import { message as Msg, Modal } from 'antd';
import s from "./style.less";
const config = {
    fetchConfig: {
        apiName: 'getZjTzPolicyLocalReplyList',
        otherParams: {
            replyFlag: '0'
        }
    },
    antd: {
        rowKey: function (row) {
            return row.policyReplyId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
    },

    firstRowIsSearch: false,
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 4 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 20 }
        }
    }
}
class index extends Component {
    constructor() {
        super();
        this.state = {
            visible: false,
            replyData: []
        }
    }
    render() {
        const { visible, replyData } = this.state;
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
                    method={{
                        willExecuteClick: (obj) => {
                            obj.btnCallbackFn.clearSelectedRows();
                            if (obj.selectedRows.length === 1) {
                                this.setState({
                                    replyData: obj.selectedRows,
                                    visible: true
                                });
                            } else {
                                Msg.error('请选择一条数据！');
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
                                field: 'policyId',
                                type: 'string',
                                hide: true,
                            }
                        }, {
                            isInTable: false,
                            form: {
                                field: 'policyReplyId',
                                type: 'string',
                                hide: true,
                            }
                        }, {
                            isInTable: false,
                            form: {
                                label: '登记项目',
                                field: 'projectShortName',
                                type: 'string',
                                placeholder: '请输入',
                                required: true,
                                addDisabled: true,
                                editDisabled: true
                            }
                        }, {
                            isInSearch: false,
                            table: {
                                title: '标题',
                                dataIndex: 'title',
                                key: 'title',
                                width: 200,
                                onClick: 'detail',
                                fixed: 'left'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                required: true,
                            }
                        }, {
                            isInSearch: false,
                            isInForm: false,
                            table: {
                                title: '最近回复内容',
                                dataIndex: 'replyInfo',
                                key: 'replyInfo',
                                width: 150,
                                fixed: 'left'
                            }
                        }, {
                            isInSearch: false,
                            table: {
                                title: '文号',
                                dataIndex: 'symbolNo',
                                key: 'symbolNo',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                            },
                        }, {
                            table: {
                                title: '所属省份',
                                dataIndex: 'provinceId',
                                key: 'provinceId',
                                width: 100,
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
                                        itemId: 'xingzhengquhuadaima'
                                    }
                                },
                                placeholder: '请输入'
                            },
                        }, {
                            table: {
                                title: '发文部门',
                                dataIndex: 'departmentName',
                                key: 'departmentName',
                                width: 100,
                            },
                            form: {
                                field: 'departmentName',
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                            },
                        }, {
                            table: {
                                title: '系统发布日期',
                                dataIndex: 'sysDate',
                                key: 'sysDate',
                                format: 'YYYY-MM-DD',
                                width: 120,
                            },
                            form: {
                                type: 'date',
                                required: true,
                                placeholder: '请输入',
                            },
                        },
                        {
                            table: {
                                title: '原文发布日期',
                                dataIndex: 'releaseDate',
                                key: 'releaseDate',
                                format: 'YYYY-MM-DD',
                                width: 120,
                            },
                            form: {
                                required: true,
                                type: 'date',
                                placeholder: '请输入',
                            },
                        },
                        {
                            table: {
                                title: '登记用户',
                                dataIndex: 'registerUser',
                                key: 'registerUser',
                                width: 100,
                            },
                            form: {
                                required: true,
                                type: 'string',
                                placeholder: '请输入',
                            },
                        },
                        {
                            table: {
                                title: '是否有效文件',
                                dataIndex: 'effectiveName',
                                key: 'effectiveName',
                                width: 120,
                            },
                            form: {
                                type: "radio",
                                required: true,
                                label: "是否有效文件",
                                field: "effectiveId",
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
                                title: '分析报告',
                                dataIndex: 'report',
                                key: 'report',
                                width: 150,
                            },
                            form: {
                                type: 'textarea',
                                required: true,
                                placeholder: '请输入',
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '附件',
                                field: 'zjTzFileList',
                                type: 'files',
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '地方政策回复'
                                    }
                                }
                            }
                        },
                        {
                            isInForm: false,
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
                                        name: 'PolicyDetail',
                                        render: (rowData) => {
                                            return '<a>回复记录</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { policyReplyId } = obj.rowData;
                                            obj.props.dispatch(
                                                push(`${mainModule}PlaceResponsesDetail/${policyReplyId}`)
                                            )
                                        },
                                    }
                                ]
                            }
                        }
                    ]}
                />
                {visible ? <div>
                    <Modal
                        width={'40%'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="回复"
                        visible={visible}
                        footer={null}
                        onCancel={this.handleCancel}
                        bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                        centered={true}
                        closable={false}
                        maskClosable={false}
                        wrapClassName={'PlansToPush'}
                    >
                        <QnnForm
                            form={this.props.form}
                            history={this.props.history}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //必须返回一个promise
                            //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 7 },
                                    sm: { span: 7 }
                                },
                                wrapperCol: {
                                    xs: { span: 17 },
                                    sm: { span: 17 }
                                }
                            }}
                            formConfig={[
                                {
                                    type: "textarea",
                                    label: "应对预案及其效果",
                                    field: "replyInfo", //唯一的字段名 ***必传
                                    required: true,
                                    placeholder: "请输入",
                                },
                                {
                                    label: '附件',
                                    field: 'zjTzPolicyLocalReplyFileList',
                                    type: 'files',
                                    showDownloadIcon: true,//是否显示下载按钮
                                    onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                    
                                    fetchConfig: {
                                        apiName: window.configs.domain + 'upload',
                                        otherParams: {
                                            name: '地方政策回复'
                                        }
                                    }
                                }
                            ]}
                            btns={[
                                {
                                    name: "cancel",
                                    type: "dashed",
                                    label: "取消",
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visible: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "确定",
                                    onClick: (obj) => {
                                        replyData[0].replyInfo = obj.values.replyInfo;
                                        replyData[0].zjTzPolicyLocalReplyFileList = obj.values.zjTzPolicyLocalReplyFileList;
                                        obj.btnfns.fetchByCb('updateZjTzPolicyLocalReply', ...replyData, ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.setState({ visible: false });
                                                this.table.refresh();
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    }
                                }
                            ]}
                        />
                    </Modal>
                </div> : null}
            </div>
        );
    }
}

export default index;