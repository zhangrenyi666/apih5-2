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
            y: document.documentElement.clientHeight * 0.6
        },
    },
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            proNameId: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId : '',
            proNameVal: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectName : '',
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
                            typeId: '1',
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
                        {
                            isInTable: false,
                            form: {
                                field: 'projectName',
                                hide: true,
                                type: 'string',
                                placeholder: '?????????',
                                initialValue: proNameVal
                            },
                        },
                        {
                            isInTable: ext1 === '4' ? true : false,
                            table: {
                                title: '????????????',
                                dataIndex: 'renew1',
                                key: 'renew1',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '?????????';
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
                                title: '????????????',
                                dataIndex: 'renew2',
                                key: 'renew2',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '?????????';
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
                                title: '????????????',
                                dataIndex: 'renew3',
                                key: 'renew3',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '?????????';
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
                                title: '????????????',
                                dataIndex: 'renew4',
                                key: 'renew4',
                                width: 100,
                                fixed: 'left',
                                render: (data) => {
                                    if (data === '0') {
                                        return '?????????';
                                    } else {
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
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
                                placeholder: '?????????',
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
                                title: '???????????????',
                                filter: true,
                                dataIndex: 'subprojectInfoId',
                                key: 'subprojectInfoId',
                                type: 'select'
                            },
                            form: {
                                type: "select",
                                showSearch: true,
                                label: "???????????????",
                                field: "subprojectInfoId",
                                placeholder: "?????????",
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
                                label: '????????????',
                                field: 'designStageId',
                                type: 'select',
                                placeholder: '?????????',
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
                                label: '????????????',
                                field: 'selectModeId',
                                type: 'select',
                                placeholder: '?????????',
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
                                label: '????????????',
                                field: 'content',
                                type: 'textarea',
                                placeholder: '?????????',
                                required: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '??????????????????/??????',
                                field: 'amount1',
                                type: 'number',
                                formatter: value => value ? `${value.replace ? value.replace(/(???|???)/ig, '') : value}??????` : value,
                                parser: value => value ? value.replace(/(???|???)/ig, '') : value,
                                placeholder: '?????????',
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
                                label: '?????????',
                                field: 'amount2',
                                type: 'number',
                                suffix: '(??????)',
                                formatter: value => value ? `${value.replace ? value.replace(/(???|???)/ig, '') : value}??????` : value,
                                parser: value => value ? value.replace(/(???|???)/ig, '') : value,
                                placeholder: '?????????',
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
                                label: '??????????????????',
                                field: 'designAdvistoryUnitStandardId',
                                type: 'select',
                                showSearch: true,
                                required: true,
                                placeholder: '?????????',
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
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '??????????????????',
                                field: 'orgCode',
                                disabled: true,
                                addDisabled: true,
                                editDisabled: true,
                                type: 'string',
                                placeholder: '?????????',
                                required: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
                                field: 'typeId',
                                type: 'select',
                                placeholder: '?????????',
                                required: true,
                                initialValue: '1',
                                hide: true,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'danWeiLeiXing'
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: "??????????????????",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'total1Amount1',
                                        title: "???????????????????????????",
                                        width: 100,
                                        noHaveSearchInput: true,
                                        key: 'total1Amount1',
                                        type: "string"
                                    },
                                    {
                                        title: '???????????????????????????',
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
                                title: "??????????????????",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'total2Amount1',
                                        title: "???????????????????????????",
                                        width: 100,
                                        noHaveSearchInput: true,
                                        key: 'total2Amount1',
                                        type: "string"
                                    },
                                    {
                                        title: '???????????????????????????',
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
                                title: "?????????????????????",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'total3Amount1',
                                        title: "???????????????????????????",
                                        width: 100,
                                        noHaveSearchInput: true,
                                        key: 'total3Amount1',
                                        type: "string"
                                    },
                                    {
                                        title: '???????????????????????????',
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
                                            ??????????????????
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
                                                label: '??????id',
                                                field: 'typeId',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'majorTypeId',
                                                key: 'majorTypeId',
                                                tdEdit: false,
                                                type: 'select',
                                            },
                                            form: {
                                                type: 'select',
                                                field: 'majorTypeId',
                                                placeholder: '?????????',
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
                                                title: '????????????',
                                                dataIndex: 'correspondQualityId',
                                                key: 'correspondQualityId',
                                                tdEdit: false,
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                field: 'correspondQualityId',
                                                placeholder: '?????????',
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
                                                title: '????????????',
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
                                                    apiName: window.configs.domain + 'upload'
                                                },
                                                showDownloadIcon: true,//????????????????????????
                                                onPreview: "bind:_docFilesByOfficeUrl",//365??????
                                                
                                                onChange: (val, rowData) => {
                                                    if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                                        Msg.warn('???????????????rar???zip???7z??????????????????')
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
                                    actionBtns: []
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '???????????????????????????',
                                field: 'proLeader',
                                type: 'string',
                                placeholder: '?????????',
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
                                label: '???????????????????????????',
                                field: 'proLeaderTel',
                                type: 'phone',
                                placeholder: '?????????',
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
                        // {
                        //     isInTable: false,
                        //     form: {
                        //         label: '????????????',
                        //         field: 'evaluateOrderId',
                        //         type: 'select',
                        //         addDisabled: true,
                        //         editDisabled:true,
                        //         placeholder: '?????????',
                        //         optionConfig: {
                        //             label: 'itemName',
                        //             value: 'itemId'
                        //         },
                        //         fetchConfig: {
                        //             apiName:"getBaseCodeSelect",
                        //             otherParams: {
                        //                 itemId: 'pingJiaDengJi'
                        //             }
                        //         }
                        //     }
                        // },
                        {
                            isInTable: false,
                            form: {
                                type: 'textarea',
                                label: '??????',
                                field: 'remarks',
                                placeholder: '?????????',
                                required: false
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'createTime',
                                type: 'date',
                                label: '????????????',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
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
                                label: '????????????',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
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
                                label: '??????',
                                field: 'zjTzFileList',
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '??????????????????'
                                    }
                                },
                                showDownloadIcon: true,//????????????????????????
                                onPreview: "bind:_docFilesByOfficeUrl",//365??????
                                
                                onChange: (val, rowData) => {
                                    if (val && val[0] && val[0].name && (val[0].name.split('.')[1] === 'rar' || val[0].name.split('.')[1] === 'zip' || val[0].name.split('.')[1] === '7z')) {
                                        Msg.warn('???????????????rar???zip???7z??????????????????')
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
                                title: "??????",
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
                                            return '<a>????????????</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { designAdvistoryUnitId, projectName, projectId, subprojectInfoId } = obj.rowData;

                                            obj.props.dispatch(
                                                push(`${mainModule}unitMangeDetail/${designAdvistoryUnitId}/${proNameVal}/${projectId}/${subprojectInfoId}`)
                                            )
                                        },
                                    }
                                ]
                            }
                        }
                    ]}
                    method={{
                        addClick: (obj) => {
                            if (proNameId) {
                                if (proNameId === 'all') {
                                    obj.btnCallbackFn.closeDrawer();
                                    obj.btnCallbackFn.msg.warning('???????????????????????????');
                                } else {
                                    obj.btnCallbackFn.setActiveKey('0');
                                }
                            } else {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.msg.warning('???????????????????????????');
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