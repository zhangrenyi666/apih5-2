import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { push } from "react-router-redux";
import s from "./style.less";
import { message as Msg } from "antd";
const config = {
    fetchConfig: {
        apiName: 'getZjTzDesignAdvistoryUnitStandardListForHard',
    },
    antd: {
        rowKey: function (row) {
            return row.designAdvistoryUnitStandardId
        },
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight * 0.65
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
            proNameVal: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectShortName : '',
            proNameId: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId : '',
            realName: props.loginAndLogoutInfo.loginInfo.userInfo.realName
        }
    }
    componentDidMount() { }
    render() {
        const { realName, proNameId } = this.state;
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
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'designAdvistoryUnitStandardId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'projectId',
                                type: 'string',
                                hide: true,
                                initialValue: proNameId
                            }
                        },
                        {
                            table: {
                                title: '单位名称',
                                dataIndex: 'unitName',
                                key: 'unitName',
                                filter: true,
                                onClick: 'detail',
                                fixed: 'left'
                            },
                            form: {
                                field: 'unitName',
                                required: true,
                                type: 'string',
                                placeholder: '请输入'
                            },
                        },
                        {
                            table: {
                                title: '组织机构代码',
                                width: 140,
                                dataIndex: 'orgCode',
                                key: 'orgCode'
                            },
                            form: {
                                field: 'orgCode',
                                type: 'string',
                                required: true,
                                placeholder: '请输入'
                            },
                        },
                        {
                            table: {
                                title: '单位类型',
                                width: 100,
                                dataIndex: 'unitTypeName',
                                key: 'unitTypeName'
                            },
                            form: {
                                field: 'unitTypeId',
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
                                        itemId: 'danWeiLeiXing'
                                    }
                                },
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 20 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 18 }
                                    }
                                },
                            },
                        },
                        {
                            table: {
                                title: '业务类型',
                                dataIndex: 'majorTypeName',
                                width: 200,
                                key: 'majorTypeName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '资质等级',
                                dataIndex: 'correspondQualityName',
                                width: 100,
                                key: 'correspondQualityName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '内外部单位',
                                dataIndex: 'inOutUnitName',
                                width: 100,
                                key: 'inOutUnitName'
                            },
                            form: {
                                field: 'inOutUnitId',
                                type: 'select',
                                required: true,
                                placeholder: '请选择...',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'neiWaiBuDanWei'
                                    }
                                },
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 20 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 18 }
                                    }
                                },
                            },
                        },
                        {
                            isInTable: this.props.loginAndLogoutInfo.loginInfo.userInfo.ext1 === '1' ? true : false,
                            table: {
                                title: '所属管理库',
                                width: 120,
                                dataIndex: 'libraryName',
                                key: 'libraryName'
                            },
                            isInForm: false,
                            form: {
                                field: 'libraryId',
                                type: 'select',
                                initialValue: 'suoshuku',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'suoShuKu'
                                    }
                                }
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
                                            资质列表
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
                                                tdEdit: true,
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
                                                tdEdit: true,
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
                                                title: '登记用户',
                                                dataIndex: 'registeredUser',
                                                key: 'registeredUser',
                                                tdEdit: false,
                                                render: () => {
                                                    return realName
                                                }
                                            },
                                            form: {
                                                field: 'registeredUser',
                                                type: 'string',
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '资质附件',
                                                dataIndex: 'zjTzFileList',
                                                key: 'zjTzFileList',
                                                tdEdit: true,
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
                                                        name: '设计咨询单位管理'
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
                                type: 'textarea',
                                label: '备注',
                                field: 'remarks',
                                placeholder: '请输入',
                                required: false,
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
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
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
                                        xs: { span: 20 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
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
                                        name: '设计咨询单位标准化'
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
                            isInTable: this.props.loginAndLogoutInfo.loginInfo.userInfo.ext1 === '1' ? true : false,
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
                                            return '<a>设置所属库</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { designAdvistoryUnitStandardId } = obj.rowData;
                                            let ll = '';
                                            if (obj.rowData.libraryId) {
                                                ll = obj.rowData.libraryId;
                                            } else {
                                                ll = 'suoshuku';
                                            }
                                            obj.props.dispatch(
                                                push(`${mainModule}unitMangeStandardDetail/${designAdvistoryUnitStandardId}/${ll}`)
                                            )

                                        },
                                    }
                                ]
                            }
                        }
                    ]}
                    method={{
                        editClick: (obj) => {
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