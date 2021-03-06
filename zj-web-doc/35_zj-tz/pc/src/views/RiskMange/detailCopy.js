import React, { Component } from "react";
import { push } from "react-router-redux";
import s from "./style.less";
import QnnForm from "../modules/qnn-table/qnn-form";
import { message as Msg, Spin, Tooltip } from "antd";
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            riskId: props.match.params.riskId || '',
            releaseId: props.match.params.releaseId || '',
            loadingSend: false,
            actionBtnsVal: [],
            projectId: props.match.params.projectId,
            projectName: props.match.params.projectName,
        }
    }
    componentDidMount() {
        var props1 = this.props;
        let curRouteData = props1.routerInfo.routeData[props1.routerInfo.curKey];
        props1.myFetch("getSysMenuBtn", {
            menuParentId: curRouteData._value,
            tableField: "projectInfo"
        }).then(({ success, data, message }) => {
            if (success) {
                this.setState({
                    actionBtnsVal: data
                })
            } else {
            }
        })
    }
    render() {
        const { riskId, actionBtnsVal, releaseId, projectId, projectName } = this.state;
        let { myPublic: { domain, appInfo: { ureport } } } = this.props;
        return (
            <div className={s.root}>
                <Spin spinning={this.state.loadingSend}>

                    <QnnForm
                        {...this.props}
                        fetchConfig={{
                            apiName: 'getZjTzRiskDetailList',
                            otherParams: {
                                riskId: riskId,
                                projectId: projectId
                            }
                        }}
                        fetch={this.props.myFetch}
                        wrappedComponentRef={(me) => {
                            this.formmm = me;
                        }}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        formConfig={[
                            {
                                label: '??????id',
                                field: 'codePid',
                                hide: true
                            },
                            {
                                label: '????????????id',
                                field: 'riskId',
                                hide: true
                            },
                            {
                                type: 'qnnTable',
                                field: 'zjTzRiskDetailList',
                                incToForm: true,
                                qnnTableConfig: {
                                    wrappedComponentRef: (qnnTable) => this.myQnnTable = qnnTable,
                                    actionBtnsPosition: "top",
                                    antd: {
                                        rowKey: 'riskDetailId',
                                        size: 'small',
                                        scroll: {
                                            y: document.documentElement.clientHeight * 0.65
                                        }
                                    },
                                    rowDisabledCondition: (rowData, props) => {
                                        return rowData.mainFlag === 'main'
                                    },
                                    drawerConfig: {
                                        width: '1000px'
                                    },
                                    limit: 999,
                                    curPage: 1,
                                    paginationConfig: false,
                                    firstRowIsSearch: false,
                                    isShowRowSelect: true,
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'riskDetailId',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'colourFlag',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },

                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'mainFlag',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'riskId',
                                                type: 'string',
                                                initialValue: riskId,
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '??????????????????',
                                                type: 'select',
                                                addDisabled: true,
                                                initialValue: '8',
                                                field: 'typeId',
                                                editDisabled: true,
                                                optionConfig: {
                                                    label: 'itemName',
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'suoSuFengXianLeiBie'
                                                    }
                                                },
                                                required: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????????????????',
                                                dataIndex: 'number',
                                                width: 200,
                                                fixed: 'left',
                                                tdEdit: true,
                                                key: 'number',
                                                render: (text, rowData) => {
                                                    return <Tooltip title={text}><div style={{ color: rowData.colourFlag }}>{text}</div></Tooltip>
                                                },
                                                fieldsConfig: {
                                                    type: 'string',
                                                    field: 'riskName',
                                                    disabled: ({ record }) => {
                                                        if (releaseId === '1') {
                                                            return true
                                                        } else {
                                                            if (record.addFlag != "1") {
                                                                return true;
                                                            } else {
                                                                return false
                                                            }
                                                        }

                                                    },
                                                    style: (val, obj) => {
                                                        let color = '';
                                                        let aa = this.formmm.form.getFieldsValue().zjTzRiskDetailList;
                                                        for (var m = 0; m < aa.length; m++) {
                                                            for (var k = 0; k < aa[m].children.length; k++) {
                                                                if (aa[m].children[k].riskName === val) {
                                                                    color = aa[m].children[k].colourFlag;
                                                                }
                                                            }
                                                        }
                                                        return {
                                                            color: color
                                                        };
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'riskName',
                                                placeholder: '?????????',
                                                required: true,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                tdEdit: true,
                                                width: 100,
                                                tooltip: 23,
                                                dataIndex: 'managLever',
                                                key: 'managLever',
                                                fieldsConfig: {
                                                    type: 'textarea',
                                                    field: 'managLever',
                                                    autoSize: { minRow: 1 },
                                                    disabled: ({ record }) => {
                                                        if (releaseId === '1') {
                                                            return true
                                                        } else {
                                                            if (record.addFlag != "1") {
                                                                return true;
                                                            } else {
                                                                return false
                                                            }
                                                        }

                                                    }
                                                }
                                            },
                                            form: {
                                                label: '????????????',
                                                type: 'textarea',
                                                field: 'managLever',
                                                placeholder: '?????????'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????????????????',
                                                width: 130,
                                                tooltip: 23,
                                                tdEdit: true,
                                                dataIndex: 'applicableItemType',
                                                key: 'applicableItemType',
                                                fieldsConfig: {
                                                    autoSize: { minRow: 1 },
                                                    type: 'textarea',
                                                    field: 'applicableItemType',
                                                    disabled: ({ record }) => {
                                                        if (releaseId === '1') {
                                                            return true
                                                        } else {
                                                            if (record.addFlag != "1") {
                                                                return true;
                                                            } else {
                                                                return false
                                                            }
                                                        }
                                                    }
                                                }
                                            },
                                            form: {
                                                label: '??????????????????',
                                                type: 'textarea',
                                                field: 'applicableItemType',
                                                placeholder: '?????????'
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '??????',
                                                type: 'textarea',
                                                field: 'remarks',
                                                placeholder: '?????????'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????????????????',
                                                tdEdit: true,
                                                width: 120,
                                                dataIndex: 'existRiskFlag',
                                                type: "select",
                                                fieldsConfig: {
                                                    type: "select",
                                                    field: "existRiskFlag",
                                                    optionData: [
                                                        {
                                                            label: "???",
                                                            value: "0"
                                                        },
                                                        {
                                                            label: "???",
                                                            value: "1"
                                                        }
                                                    ],
                                                    disabled: () => {
                                                        if (releaseId === '1') {
                                                            return true
                                                        } else {
                                                            return false
                                                        }
                                                    }
                                                },

                                                key: 'existRiskFlag'
                                            },
                                            form: {
                                                type: "select",
                                                field: "existRiskFlag",
                                                optionData: [
                                                    {
                                                        label: "???",
                                                        value: "0"
                                                    },
                                                    {
                                                        label: "???",
                                                        value: "1"
                                                    }
                                                ],
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????????????????',
                                                width: 180,
                                                tooltip: 23,
                                                dataIndex: 'specificDesc',
                                                tdEdit: true,
                                                key: 'specificDesc',
                                                fieldsConfig: {
                                                    type: "textarea",
                                                    autoSize: { minRow: 1, maxRow: 4 },
                                                    field: "specificDesc",
                                                    disabled: ({ record }) => {
                                                        if (releaseId === '1') {
                                                            return true
                                                        } else {
                                                            if (record.existRiskFlag === '0') {
                                                                return true;
                                                            } else {
                                                                return false
                                                            }
                                                        }
                                                    }
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'solution',
                                                tdEdit: true,
                                                width: 140,
                                                tooltip: 23,
                                                fieldsConfig: {
                                                    field: 'solution',
                                                    type: 'textarea',
                                                    autoSize: {
                                                        minRows: 1,
                                                        maxRows: 4
                                                    },
                                                    disabled: ({ record }) => {
                                                        if (releaseId === '1') {
                                                            return true
                                                        } else {
                                                            if (record.existRiskFlag === '0') {
                                                                return true;
                                                            } else {
                                                                return false
                                                            }
                                                        }
                                                    }
                                                },
                                                key: 'solution',
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '??????????????????',
                                                dataIndex: 'planTime',
                                                key: 'planTime',
                                                tdEdit: true,
                                                width: 140,
                                                format: 'YYYY-MM-DD',
                                                fieldsConfig: {
                                                    field: 'planTime',
                                                    type: 'date',
                                                    disabled: ({ record }) => {
                                                        if (releaseId === '1') {
                                                            return true
                                                        } else {
                                                            if (record.existRiskFlag === '0') {
                                                                return true;
                                                            } else {
                                                                return false
                                                            }
                                                        }
                                                    }
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '??????????????????',
                                                dataIndex: 'actualTime',
                                                tdEdit: true,
                                                width: 140,
                                                format: 'YYYY-MM-DD',
                                                fieldsConfig: {
                                                    field: 'actualTime',
                                                    type: 'date',
                                                    disabled: ({ record }) => {
                                                        if (releaseId === '0') {
                                                            return true
                                                        } else {
                                                            if (record.existRiskFlag === '0') {//??????????????????
                                                                return true;
                                                            } else {
                                                                return false
                                                            }
                                                        }
                                                    }
                                                },
                                                key: 'actualTime',
                                            },
                                            isInForm: false
                                        },
                                        
                                        {
                                            table: {
                                                title: '?????????????????????',
                                                dataIndex: 'uncompletedAnalysis',
                                                tdEdit: true,
                                                width: 180,
                                                tooltip: 23,
                                                fieldsConfig: {
                                                    type: "string",
                                                    field: "uncompletedAnalysis",
                                                    disabled: ({ record }) => {
                                                        if (releaseId === '0') {
                                                            return true
                                                        } else {
                                                            if (record.existRiskFlag === '0') {
                                                                return true;
                                                            } else {
                                                                return false
                                                            }
                                                        }
                                                    }
                                                },
                                                key: 'uncompletedAnalysis',
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '?????????',
                                                width: 180,
                                                tooltip: 23,
                                                dataIndex: 'personInCharge',
                                                tdEdit: true,
                                                key: 'personInCharge',
                                                fieldsConfig: {
                                                    type: "string",
                                                    field: "personInCharge",
                                                    disabled: ({ record }) => {
                                                        if (releaseId === '1') {
                                                            return true
                                                        } else {
                                                            if (record.existRiskFlag === '0') {
                                                                return true;
                                                            } else {
                                                                return false
                                                            }
                                                        }
                                                    }
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                width: 180,
                                                tooltip: 23,
                                                dataIndex: 'resultAnalysis',
                                                tdEdit: true,
                                                key: 'resultAnalysis',
                                                fieldsConfig: {
                                                    type: "string",
                                                    field: "resultAnalysis",
                                                    disabled: ({ record }) => {
                                                        if (releaseId === '0') {
                                                            return true
                                                        } else {
                                                            if (record.existRiskFlag === '0') {//???-????????????
                                                                return false
                                                            } else {//????????????
                                                                if (record.actualTime) {
                                                                    return false;
                                                                } else {
                                                                    return true
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                width:520,
                                                dataIndex: 'zjTzFileList',
                                                key: 'zjTzFileList',
                                                tdEdit: true,
                                                fieldsConfig: {
                                                    field: 'zjTzFileList',
                                                    type: 'files',
                                                    max: 1,
                                                    fetchConfig: {
                                                        apiName: window.configs.domain + 'upload'
                                                    },
                                                    showDownloadIcon: true,//????????????????????????
                                                    onPreview: "bind:_docFilesByOfficeUrl",//365??????
                                                    disabled: ({ record }) => {
                                                        if (releaseId === '1') {
                                                            return true
                                                        } else {//??????000002685-9.27??????
                                                            // if (record.existRiskFlag === '0') {
                                                            //     return true;
                                                            // } else {
                                                            return false
                                                            // }
                                                        }
                                                    }
                                                },
                                                render: (data, rowData) => {
                                                    if (data) {
                                                        if (rowData.zjTzFileList.length > 0) {
                                                            return <div onClick={() => { }}>
                                                                <a target={'_blank'} href={rowData.zjTzFileList[0].url}>{rowData.zjTzFileList[0].name}</a>
                                                            </div>
                                                        } else {
                                                            return ''
                                                        }
                                                    } else {
                                                        return ''
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
                                                        name: '????????????'
                                                    }
                                                },
                                                showDownloadIcon: true,//????????????????????????
                                                onPreview: "bind:_docFilesByOfficeUrl",//365??????
                                                
                                            }
                                        }
                                    ],
                                    method: {
                                        save: (obj) => {
                                            let value = this.formmm.form.getFieldsValue();
                                            obj.btnCallbackFn.setBtnsLoading("add", "diySave");
                                            this.setState({
                                                loadingSend:true
                                            })
                                            this.props.myFetch("saveZjTzRiskAllDetail", value).then(({ success, code, message }) => {
                                                obj.btnCallbackFn.setBtnsLoading("remove", "diySave")
                                                if (success) {
                                                    this.setState({
                                                        loadingSend:false
                                                    })
                                                    obj.btnCallbackFn.successMsg(message);
                                                    this.formmm.refresh();
                                                } else {
                                                    obj.btnCallbackFn.errMsg(message, code)
                                                }
                                            })
                                        },
                                        goBack: (obj) => {
                                            const { mainModule } = this.props.myPublic.appInfo;
                                            this.props.dispatch(
                                                push(`${mainModule}RiskMange`)
                                            )
                                        },
                                        addOtherRisk: (obj) => {
                                            const newRowData = {
                                                addFlag: "1",
                                                riskDetailId: Math.random().toFixed("3")
                                            };
                                            const newTableData = obj.state.data.map(item => {
                                                let children = item.children || [];
                                                if (item['typeId'] === "8") {
                                                    //??????????????????????????? 
                                                    return {
                                                        ...item,
                                                        children: children.concat([{
                                                            ...newRowData,
                                                            typeId: item['typeId'],
                                                            typeName: item['typeName'],
                                                        }])
                                                    }
                                                } else {
                                                    // Msg.warn('?????????????????????????????????')
                                                    return item;
                                                }
                                            });

                                            //??????????????????????????????????????? 
                                            this.myQnnTable.qnnSetState({
                                                expandedRowKeys: obj.state.data.filter(item => item['typeId'] === '8').map(item => item['riskDetailId'])
                                            }, () => {
                                                //??????????????????
                                                this.formmm.setValues({
                                                    zjTzRiskDetailList: newTableData
                                                });
                                            })
                                        },
                                        delClick: ({ btnCallbackFn, selectedRows, state: { data = [] } }) => {
                                            let aa = [];
                                            let obj = {}
                                            let arrResult = []
                                            let copyData = this.formmm.form.getFieldsValue().zjTzRiskDetailList;
                                            if (selectedRows.length > 0) {
                                                for (var i = 0; i < selectedRows.length; i++) {
                                                    if (selectedRows[i].mainFlag === 'main') {
                                                        aa.push(selectedRows[i].mainFlag);
                                                    }
                                                }
                                                if (aa.length > 0) {
                                                    btnCallbackFn.closeDrawer();
                                                    Msg.warn('????????????????????????!');
                                                    btnCallbackFn.clearSelectedRows();
                                                } else {

                                                    if (selectedRows.length > 0) {
                                                        selectedRows.forEach(function (item, index) {
                                                            if (item) {
                                                                obj[item.riskDetailId] = true;
                                                            }
                                                        })
                                                    }
                                                    if (data.length > 0) {
                                                        for (var m = 0; m < data.length; m++) {
                                                            for (var k = 0; k < data[m].children.length; k++) {
                                                                if (!obj[data[m].children[k].riskDetailId]) {
                                                                    arrResult.push(data[m].children[k])
                                                                }
                                                            }
                                                        }
                                                    }

                                                    for (var j = 0; j < copyData.length; j++) {
                                                        copyData[j].children = [];
                                                        for (var g = 0; g < arrResult.length; g++) {
                                                            if (copyData[j].typeName === arrResult[g].typeName) {
                                                                copyData[j].children.push(arrResult[g])
                                                            }
                                                        }

                                                    }
                                                    btnCallbackFn.clearSelectedRows();
                                                    btnCallbackFn.setState({
                                                        data: copyData
                                                    }, () => {
                                                        btnCallbackFn.refresh()
                                                    })
                                                }
                                            } else {
                                                Msg.warn('???????????????!');
                                            }
                                        },
                                        hideClick: () => {
                                            if (releaseId === '1') {
                                                return true
                                            } else {
                                                return false
                                            }
                                        },
                                        exportClick: (obj) => {
                                            window.open(ureport + 'excel?_u=file:zjTzRiskDetail.xml&_n=' + projectName + '????????????&url=' + domain + '&riskId=' + riskId);
                                        }
                                    },
                                    actionBtns: actionBtnsVal
                                }
                            }
                        ]}
                        btns={[]}
                    />

                </Spin>

            </div>
        );
    }
}

export default index;