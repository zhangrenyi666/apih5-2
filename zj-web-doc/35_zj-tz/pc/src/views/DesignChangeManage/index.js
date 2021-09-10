import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { push } from "react-router-redux";
import s from "./style.less";
import { message as Msg } from "antd";
const config = {

    antd: {
        rowKey: function (row) {
            return row.designChangeId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1100px'
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
            proNameVal: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectShortName : '',
            proNameId: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId : '',
            realName: props.loginAndLogoutInfo.loginInfo.userInfo.realName
        }
    }
    componentDidMount() {
    }
    render() {
        const { realName, proNameId, proNameVal } = this.state;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
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
                        apiName: 'getZjTzDesignChangeList',
                        otherParams: {
                            projectId: proNameId
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'designChangeId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        // {
                        //     isInTable: false,
                        //     form: {
                        //         field: 'projectId',
                        //         type: 'string',
                        //         hide: true,
                        //         initialValue:proNameId
                        //     }
                        // },
                        {
                            isInTable: false,
                            form: {
                                field: 'projectName',
                                hide: true,
                                type: 'string',
                                placeholder: '请输入',
                                initialValue: () => {
                                    return proNameVal
                                }
                            },
                        },
                        // {   
                        //     table: {
                        //         title: '项目名称',
                        //         dataIndex: 'projectName',
                        //         key: 'projectName',
                        //         filter:true,
                        //         fixed: 'left'
                        //     },
                        //     form: {
                        //         field: 'projectName',
                        //         required: true,
                        //         addDisabled: true,
                        //         disabled: true,
                        //         editDisabled:true,
                        //         type: 'string',
                        //         placeholder: '请输入',
                        //         initialValue: () => {
                        //             return proNameVal
                        //         },
                        //         formItemLayout: {
                        //             labelCol: {
                        //                 xs: { span: 21 },
                        //                 sm: { span: 3  }
                        //             },
                        //             wrapperCol: {
                        //                 xs: { span: 21 },
                        //                 sm: { span: 21 }
                        //             }
                        //         }
                        //     },
                        // },
                        {
                            isInTable: ext1 === '4' ? true : false,
                            table: {
                                title: '是否更新',
                                dataIndex: 'renew1',
                                key: 'renew1',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '有更新';
                                    } else {
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable: ext1 === '3' ? true : false,
                            table: {
                                title: '是否更新',
                                dataIndex: 'renew2',
                                key: 'renew2',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '有更新';
                                    } else {
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable: ext1 === '2' ? true : false,
                            table: {
                                title: '是否更新',
                                dataIndex: 'renew3',
                                key: 'renew3',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '有更新';
                                    } else {
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable: ext1 === '1' ? true : false,
                            table: {
                                title: '是否更新',
                                dataIndex: 'renew4',
                                key: 'renew4',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '有更新';
                                    } else {
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'projectId',
                                key: 'projectId',
                                fixed: 'left',
                                type: 'select',
                                filter: true,
                            },
                            form: {
                                field: 'projectId',
                                type: 'select',
                                showSearch: true,
                                initialValue: proNameId,
                                required: true,
                                addDisabled: true,
                                disabled: true,
                                editDisabled: true,
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProManageList"
                                },
                                placeholder: '请输入',
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
                                title: '子项目名称',
                                filter: true,
                                dataIndex: 'subprojectInfoId',
                                key: 'subprojectInfoId',
                                type: 'select'
                            },
                            form: {
                                type: "select",
                                showSearch: true,
                                label: "子项目名称",
                                field: "subprojectInfoId",
                                placeholder: "请输入",
                                optionConfig: {
                                    label: 'subprojectName',
                                    value: 'subprojectInfoId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProSubprojectInfoList",
                                    otherParams: {
                                        projectId: proNameId
                                    }
                                },
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '变更次数',
                                width: 120,
                                dataIndex: 'changeNum',
                                key: 'changeNum'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '<div>变更总金额<br>（万元）</div>',
                                width: 120,
                                dataIndex: 'changeAmount',
                                key: 'changeAmount'
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '设计阶段',
                                field: 'designStageId',
                                type: 'select',
                                placeholder: '请选择',
                                required: true,
                                editDisabled: true,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'sheJiJieDuan'
                                    }
                                },
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '变更等级',
                                field: 'changeLevelId',
                                type: 'select',
                                placeholder: '请选择',
                                required: true,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'bianGengDengJi'
                                    }
                                },
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
                                label: '是否有动态设计机制',
                                field: 'dynamicId',
                                required: true,
                                type: 'select',
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
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 7 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 17 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '变更性质',
                                field: 'changeNatureId',
                                type: 'select',
                                placeholder: '请输入',
                                required: true,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'bianGengXingZhi'
                                    }
                                },
                                // hide: (obj) => {
                                //     var salaryType = obj.form.getFieldValue('changeLevelId');
                                //     if (salaryType) {
                                //         if (salaryType === '1' || salaryType === '2') {
                                //             return true 
                                //         } else {
                                //             return false
                                //         }
                                //     } else {
                                //         return true
                                //     }

                                // },
                                // dependencies: ["changeLevelId"],
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '设计变更名称',
                                field: 'designChangeName',
                                type: 'textarea',
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
                                label: '设计变更编号',
                                field: 'designChangeNumber',
                                type: 'string',
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
                                label: '变更增减金额',
                                field: 'changeAmount',
                                type: 'number',
                                formatter: value => value ? `${value.replace ? value.replace(/(万|元)/ig, '') : value}万元` : value,
                                parser: value => value ? value.replace(/(万|元)/ig, '') : value,
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
                                label: '变更时间',
                                field: 'changeDate',
                                type: 'date',
                                placeholder: '请选择',
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
                                type: 'radio',
                                label: '是否完成内部审查流程',
                                field: 'innerCheckId',
                                optionData:[
                                    {
                                        label:'否',
                                        value:'2'
                                    },
                                    {
                                        label:'是',
                                        value:'1'
                                    }
                                ],
                                required: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'radio',
                                label: '是否完成合法合规流程',
                                field: 'legalId',
                                optionData:[
                                    {
                                        label:'否',
                                        value:'2'
                                    },
                                    {
                                        label:'是',
                                        value:'1'
                                    }
                                ],
                                required: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '设计内容简要描述',
                                field: 'content',
                                type: 'textarea',
                                required: true,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'textarea',
                                label: '备注',
                                field: 'remarks',
                                placeholder: '请输入',
                                required: false,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'createTime',
                                type: 'date',
                                label: '创建日期',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
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
                                },
                                initialValue: () => {
                                    return new Date()
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'createUserName',
                                type: 'string',
                                label: '创建用户',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
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
                                },
                                initialValue: realName
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
                                        name: '设计变更管理'
                                    }
                                },
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                },
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
                            },
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
                                width: 100,
                                btns: [
                                    {
                                        name: 'PolicyDetail',
                                        render: (rowData) => {
                                            return '<a>详细信息</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { designChangeId, projectName, projectId, subprojectInfoId } = obj.rowData;
                                            obj.props.dispatch(
                                                push(`${mainModule}DesignChangeManageDetail/${designChangeId}/${projectName}/${projectId}/${subprojectInfoId}`)
                                            )
                                        },
                                    }
                                ]
                            }
                        }
                    ]}
                    method={{
                        addClick: (obj) => {
                            // "onClick": "bind:addClick",
                            if (proNameId) {
                                if (proNameId === 'all') {
                                    obj.btnCallbackFn.closeDrawer();
                                    obj.btnCallbackFn.msg.warning('请选择右上角项目！');
                                } else {
                                    obj.btnCallbackFn.setActiveKey('0');
                                }
                            } else {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.msg.warning('请选择右上角项目！');
                            }
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