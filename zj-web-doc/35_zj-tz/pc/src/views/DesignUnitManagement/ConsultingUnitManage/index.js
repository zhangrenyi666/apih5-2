import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { push } from "react-router-redux";
import s from "./style.less";
import { message as Msg } from "antd";
const config = {

    antd: {
        rowKey: function (row) {
            return row.designAdvistoryUnitId
        },
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight * 0.62
        },
    },
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true,
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
    componentDidMount() { }
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
                        apiName: 'getZjTzDesignAdvistoryUnitList',
                        otherParams: {
                            typeId: '2',
                            projectId: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId,
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'designAdvistoryUnitId',
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
                        //     },
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
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 20 }
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
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '设计阶段',
                                field: 'designStageId',
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
                                        itemId: 'sheJiJieDuan'
                                    }
                                },
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 20 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '选定方式',
                                field: 'selectModeId',
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
                                        itemId: 'xuanDiFangShi'
                                    }
                                },
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 20 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '工作内容',
                                field: 'content',
                                type: 'textarea',
                                placeholder: '请输入',
                                required: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '预估成效',
                                field: 'amount1',
                                type: 'number',
                                formatter: value => value ? `${value.replace ? value.replace(/(万|元)/ig, '') : value}万元` : value,
                                parser: value => value ? value.replace(/(万|元)/ig, '') : value,
                                placeholder: '请输入',
                                required: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 20 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '咨询费',
                                field: 'amount2',
                                type: 'number',
                                suffix: '(万元)',
                                formatter: value => value ? `${value.replace ? value.replace(/(万|元)/ig, '') : value}万元` : value,
                                parser: value => value ? value.replace(/(万|元)/ig, '') : value,
                                placeholder: '请输入',
                                // required: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 20 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '咨询单位名称',
                                field: 'designAdvistoryUnitStandardId',
                                type: 'select',
                                showSearch: true,
                                required: true,
                                placeholder: '请输入',
                                optionConfig: {
                                    label: 'unitName',
                                    value: 'designAdvistoryUnitStandardId',
                                    linkageFields: {
                                        "orgCode": "orgCode",
                                        "zjTzQualityList": "zjTzQualityList"
                                    }
                                },
                                fetchConfig: {
                                    apiName: "getZjTzDesignAdvistoryUnitStandardList"
                                },
                                onChange: (val, rowData) => {
                                    if (this.formIn) {
                                        this.formIn.refresh();
                                    }


                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '组织机构代码',
                                field: 'orgCode',
                                disabled: true,
                                addDisabled: true,
                                editDisabled: true,
                                type: 'string',
                                placeholder: '请输入',
                                required: true
                            }
                        },

                        {
                            table: {
                                title: "工程可研阶段",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'total1Amount1',
                                        title: "预估成效（万元）",
                                        width: 100,
                                        noHaveSearchInput: true,
                                        key: 'total1Amount1',
                                        type: "string"
                                    },
                                    {
                                        title: '咨询费（万元）',
                                        dataIndex: 'total1Amount2',
                                        type: "number",
                                        width: 100,
                                        noHaveSearchInput: true,
                                        key: 'total1Amount2',
                                        // render: "bind:_divide::10000::2",
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "初步设计阶段",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'total2Amount1',
                                        title: "预估成效（万元）",
                                        width: 100,
                                        noHaveSearchInput: true,
                                        key: 'total2Amount1',
                                        type: "string"
                                    },
                                    {
                                        title: '咨询费（万元）',
                                        dataIndex: 'total2Amount2',
                                        type: "number",
                                        width: 100,
                                        noHaveSearchInput: true,
                                        key: 'total2Amount2',
                                        // render: "bind:_divide::10000::2",
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "施工图设计阶段",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'total3Amount1',
                                        title: "预估成效（万元）",
                                        width: 100,
                                        noHaveSearchInput: true,
                                        key: 'total3Amount1',
                                        type: "string"
                                    },
                                    {
                                        title: '咨询费（万元）',
                                        dataIndex: 'total3Amount2',
                                        type: "number",
                                        width: 100,
                                        noHaveSearchInput: true,
                                        key: 'total3Amount2',
                                        // render: "bind:_divide::10000::2",
                                    }
                                ]
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
                                            咨询单位资质
                                        </div>
                                    );
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'zjTzQualityList',
                                colStyle: {
                                    paddingLeft: 12
                                },
                                incToForm: true,
                                qnnTableConfig: {
                                    actionBtnsPosition: "top",
                                    antd: {
                                        rowKey: 'typeId',
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
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '主键id',
                                                field: 'typeId',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '专业类型',
                                                dataIndex: 'majorTypeId',
                                                key: 'majorTypeId',
                                                tdEdit: false,
                                                type: 'select',
                                            },
                                            form: {
                                                type: 'select',
                                                field: 'majorTypeId',
                                                placeholder: '请选择',
                                                optionConfig: {
                                                    label: 'itemName',
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'zhuanYeLeiXing'
                                                    }
                                                }
                                            },
                                        },
                                        {
                                            table: {
                                                title: '对应资质',
                                                dataIndex: 'correspondQualityId',
                                                key: 'correspondQualityId',
                                                tdEdit: false,
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                field: 'correspondQualityId',
                                                placeholder: '请选择',
                                                optionConfig: {
                                                    label: 'itemName',
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'duiYingZiZhi'
                                                    }
                                                }
                                            },
                                        },
                                        {
                                            table: {
                                                title: '资质附件',
                                                dataIndex: 'zjTzFileList',
                                                key: 'zjTzFileList',
                                                tdEdit: false,
                                                render: (data, rowData) => {
                                                    if (data) {
                                                        if (rowData.zjTzFileList.length > 0) {
                                                            return rowData.zjTzFileList[0].name
                                                        } else {
                                                            return ''
                                                        }
                                                    } else {
                                                        return ''
                                                    }
                                                },
                                                onClick: (obj) => {
                                                    if (obj.rowData && obj.rowData.zjTzFileList && obj.rowData.zjTzFileList[0] && obj.rowData.zjTzFileList[0].url) {
                                                        window.open(obj.rowData.zjTzFileList[0].url)
                                                    }
                                                }
                                            },
                                            form: {
                                                field: 'zjTzFileList',
                                                type: 'files',
                                                max: 1,
                                                fetchConfig: {
                                                    apiName: window.configs.domain + 'upload',
                                                    otherParams: {
                                                        name: '咨询单位管理'
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
                                        }
                                    ],
                                    actionBtns: [
                                        // {
                                        //     name: "addRow",
                                        //     icon: "plus",
                                        //     type: "primary",
                                        //     label: "新增行"
                                        // },
                                        // {
                                        //     name: 'del',
                                        //     icon: 'delete',
                                        //     type: 'danger',
                                        //     label: '删除'
                                        // }
                                    ]
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '咨询单位项目负责人',
                                field: 'proLeader',
                                type: 'string',
                                placeholder: '请输入',
                                required: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 20 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '咨询单位负责人电话',
                                field: 'proLeaderTel',
                                type: 'phone',
                                placeholder: '请输入',
                                required: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 20 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 16 }
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
                                required: false
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
                                        xs: { span: 20 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 16 }
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
                                        xs: { span: 20 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 16 }
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
                                        name: '咨询单位管理'
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
                                            const { designAdvistoryUnitId, projectName, projectId, subprojectInfoId } = obj.rowData;
                                            obj.props.dispatch(
                                                push(`${mainModule}ConsultingUnitManageDetail/${designAdvistoryUnitId}/${projectName}/${projectId}/${subprojectInfoId}`)
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