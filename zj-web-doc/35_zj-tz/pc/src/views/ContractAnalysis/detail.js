import React, { Component } from "react";
import s from "./style.less";
import QnnForm from "../modules/qnn-table/qnn-form";
import { Spin } from "antd";
import $ from 'jquery';
import { goBack } from "connected-react-router";

class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            pppTermId: props.match.params.pppTermId || '',
            statusId: props.match.params.statusId || '',
            loadingSend: false,
            actionBtnsVal: [],
            projectName:props.match.params.apih5FlowStatus || ''
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
        const { pppTermId, actionBtnsVal, statusId, isWorkId,projectName } = this.state;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        let { myPublic: { domain,appInfo: { ureport } } } = this.props;
        return (
            <div className={s.root}>
                <Spin spinning={this.state.loadingSend}>
                    <QnnForm
                        {...this.props}
                        fetchConfig={{
                            apiName: 'getZjTzPppTermReplyList',
                            otherParams: {
                                pppTermId: pppTermId
                            }
                        }}
                        fetch={this.props.myFetch}
                        wrappedComponentRef={(me) => {
                            this.formmm = me;
                        }}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        formConfig={[
                            {
                                label: '主键id',
                                field: 'codePid',
                                hide: true
                            },
                            {
                                label: '主键id',
                                field: 'pppTermId',
                                initialValue:pppTermId,
                                hide: true
                            },
                            {
                                type: 'qnnTable',
                                field: 'zjTzPppTermReplyList',
                                incToForm: true,
                                qnnTableConfig: {
                                    actionBtnsPosition: "top",
                                    antd: {
                                        rowKey: 'pppTermReplyId',
                                        size: 'small',
                                        scroll: {
                                            y: document.documentElement.clientHeight * 0.65
                                        },
                                        rowClassName: (record,val) => {
                                            if (record.changeFlag1 ==='1') {
                                                $('textarea[field="numberContent"]').each((i) => {
                                                    if (val === i) {
                                                         $('textarea[field="numberContent"]').eq(i).css('color','red')
                                                    }
                                                })
                                            }
                                            if (record.changeFlag2 ==='1') {
                                                $('textarea[field="term"]').each((i) => {
                                                    if (val === i) {
                                                         $('textarea[field="term"]').eq(i).css('color','red')
                                                    }
                                                })
                                            }
                                            if (record.changeFlag3 ==='1') {
                                                $('div[field="riskFlag"]').each((i) => {
                                                    if (val === i) {
                                                         $('div[field="riskFlag"]').eq(i).css('color','red')
                                                    }
                                                })
                                            }
                                            if (record.changeFlag4 ==='1') {
                                                $('textarea[field="measure"]').each((i) => {
                                                    if (val === i) {
                                                         $('textarea[field="measure"]').eq(i).css('color','red')
                                                    }
                                                })
                                            }
                                            if (record.changeFlag5 ==='1') {
                                                $('div[field="negotiateFlag"]').each((i) => {
                                                    if (val === i) {
                                                         $('div[field="negotiateFlag"]').eq(i).css('color','red')
                                                    }
                                                })
                                            }
                                            if (record.changeFlag6 ==='1') {
                                                $('input[field="deptAndLeader"]').each((i) => {
                                                    if (val === i) {
                                                         $('input[field="deptAndLeader"]').eq(i).css('color','red')
                                                    }
                                                })
                                            }
                                            if (record.changeFlag7 ==='1') {
                                                $('textarea[field="implement"]').each((i) => {
                                                    if (val === i) {
                                                         $('textarea[field="implement"]').eq(i).css('color','red')
                                                    }
                                                })
                                            }
                                            return '';
                                        }
                                    },
                                    drawerConfig: {
                                        width: '1000px'
                                    },
                                    limit: 999,
                                    curPage: 1,
                                    paginationConfig: false,
                                    firstRowIsSearch: false,
                                    isShowRowSelect: false,
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '主键id',
                                                field: 'pppTermReplyId',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '上报状态',
                                                field: 'statusId',
                                                initialValue: statusId,
                                                hide: true
                                            },
                                        },
                                        {
                                            table: {
                                                title: '分析重点',
                                                // tooltip: 80,
                                                width: 200,
                                                dataIndex: 'analysisKey',
                                                key: 'analysisKey',
                                                tdEdit: false,
                                                render: (text, rowData, index) => {
                                                    // 需要返回同一个项目有几个指标
                                                    return {
                                                        children: <div>{text}</div>,
                                                        props: {
                                                            rowSpan: Number(rowData.count)
                                                        },
                                                    };
                                                },
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '分析条款',
                                                dataIndex: 'keyTerm',
                                                tooltip:23,
                                                width: 200,
                                                tdEdit: false,
                                                key: 'keyTerm',
                                                render: (data) => {
                                                    return <div style={{ color: 'red' }}>{data}</div>
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '重点分析内容',
                                                tdEdit: false,
                                                dataIndex: 'keyAnalysisContent',
                                                tooltip:23,
                                                width: 200,
                                                key: 'keyAnalysisContent'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '条款编号与内容',
                                                width: 200,
                                                tooltip:18,
                                                dataIndex: 'numberContent',
                                                key: 'numberContent',
                                                tdEdit: (obj) => {
                                                    if (ext1 === '4' || ext1 === '3') {
                                                        if (statusId === "0" || statusId === "2") {
                                                            return true
                                                        } else {
                                                            return false
                                                        }

                                                    } else {
                                                        return false
                                                    }
                                                },
                                                fieldsConfig: {
                                                    type: 'textarea',
                                                    field: 'numberContent',
                                                    autoSize: {
                                                        minRows: 1,
                                                        maxRows: 4
                                                    }
                                                },
                                                render: (val,rowData) => {
                                                    if (rowData.changeFlag1 === '1') {
                                                        return <div style={{ color: 'red' }}>{val}</div>
                                                    } else {
                                                        return val
                                                    }
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '条款分析',
                                                width: 200,
                                                tooltip:15,
                                                dataIndex: 'term',
                                                key: 'term',
                                                tdEdit: (obj) => {
                                                    if (ext1 === '4' || ext1 === '3') {
                                                        if (statusId === "0" || statusId === "2") {
                                                            return true
                                                        } else {
                                                            return false
                                                        }

                                                    } else {
                                                        return false
                                                    }
                                                },
                                                fieldsConfig: {
                                                    type: 'textarea',
                                                    field: 'term',
                                                    autoSize: {
                                                        minRows: 1,
                                                        maxRows: 4
                                                    }
                                                },
                                                render: (val,rowData) => {
                                                    if (rowData.changeFlag2 === '1') {
                                                        return <div style={{ color: 'red' }}>{val}</div>
                                                    } else {
                                                        return val
                                                    }
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '是否存在风险',
                                                width: 120,
                                                dataIndex: 'riskFlag',
                                                key: 'riskFlag',
                                                tdEdit: (obj) => {
                                                    if (ext1 === '4' || ext1 === '3') {
                                                        if (statusId === "0" || statusId === "2") {
                                                            return true
                                                        } else {
                                                            return false
                                                        }

                                                    } else {
                                                        return false
                                                    }
                                                },
                                                // type: "select",
                                                fieldsConfig:{
                                                    type: "select",
                                                    field: 'riskFlag',
                                                    optionData: [
                                                        {
                                                            label: "是",
                                                            value: "是"
                                                        },
                                                        {
                                                            label: "否",
                                                            value: "否"
                                                        }
                                                    ]
                                                },
                                                render: (val,rowData) => {
                                                    if (rowData.changeFlag3 === '1') {
                                                        return <div style={{ color: 'red' }}>{val}</div>
                                                    } else {
                                                        return val
                                                    }
                                                }
                                            },
                                            isInForm: false,
                                            form: {
                                                type: "select",
                                                optionData: [
                                                    {
                                                        label: "是",
                                                        value: "是"
                                                    },
                                                    {
                                                        label: "否",
                                                        value: "否"
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            table: {
                                                title: '应对措施',
                                                width: 200,
                                                tooltip:15,
                                                dataIndex: 'measure',
                                                key: 'measure',
                                                tdEdit: (obj) => {
                                                    if (ext1 === '4' || ext1 === '3') {
                                                        if (statusId === "0" || statusId === "2") {
                                                            return true
                                                        } else {
                                                            return false
                                                        }

                                                    } else {
                                                        return false
                                                    }
                                                },
                                                fieldsConfig: {
                                                    type: "textarea",
                                                    autoSize: {
                                                        minRows: 1,
                                                        maxRows: 4
                                                    }
                                                },
                                                render: (val,rowData) => {
                                                    if (rowData.changeFlag4 === '1') {
                                                        return <div style={{ color: 'red' }}>{val}</div>
                                                    } else {
                                                        return val
                                                    }
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '是否需要二次谈判',
                                                width: 140,
                                                dataIndex: 'negotiateFlag',
                                                key: 'negotiateFlag',
                                                tdEdit: (obj) => {
                                                    if (ext1 === '4' || ext1 === '3') {
                                                        if (statusId === "0" || statusId === "2") {
                                                            return true
                                                        } else {
                                                            return false
                                                        }

                                                    } else {
                                                        return false
                                                    }
                                                },
                                                fieldsConfig: {
                                                    type: "select",
                                                    optionData: [
                                                        {
                                                            label: "是",
                                                            value: "是"
                                                        },
                                                        {
                                                            label: "否",
                                                            value: "否"
                                                        }
                                                    ]
                                                },
                                                // type:'select'
                                                render: (val,rowData) => {
                                                    if (rowData.changeFlag5 === '1') {
                                                        return <div style={{ color: 'red' }}>{val}</div>
                                                    } else {
                                                        return val
                                                    }
                                                }
                                            },
                                            isInForm: false,
                                            form: {
                                                type: "select",
                                                optionData: [
                                                    {
                                                        label: "是",
                                                        value: "是"
                                                    },
                                                    {
                                                        label: "否",
                                                        value: "否"
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            table: {
                                                title: '责任部门与责任人',
                                                width: 140,
                                                dataIndex: 'deptAndLeader',
                                                key: 'deptAndLeader',
                                                tdEdit: (obj) => {
                                                    if (ext1 === '4' || ext1 === '3') {
                                                        if (statusId === "0" || statusId === "2") {
                                                            return true
                                                        } else {
                                                            return false
                                                        }

                                                    } else {
                                                        return false
                                                    }
                                                },
                                                fieldsConfig: {
                                                    type: "string"
                                                },
                                                render: (val,rowData) => {
                                                    if (rowData.changeFlag6 === '1') {
                                                        return <div style={{ color: 'red' }}>{val}</div>
                                                    } else {
                                                        return val
                                                    }
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '措施落实情况',
                                                width: 200,
                                                tooltip:15,
                                                dataIndex: 'implement',
                                                key: 'implement',
                                                tdEdit: (obj) => {
                                                    if (ext1 === '4' || ext1 === '3') {
                                                        if (statusId === "0" || statusId === "2") {
                                                            return true
                                                        } else {
                                                            return false
                                                        }

                                                    } else {
                                                        return false
                                                    }
                                                },
                                                fieldsConfig: {
                                                    type: "textarea",
                                                    field: 'implement',
                                                    placeholder: "请输入...",
                                                    autoSize: {
                                                        minRows: 1,
                                                        maxRows: 4
                                                    }
                                                },
                                                render: (val,rowData) => {
                                                    if (rowData.changeFlag7 === '1') {
                                                        return <div style={{ color: 'red' }}>{val}</div>
                                                    } else {
                                                        return val
                                                    }
                                                }
                                            },
                                            isInForm: false
                                        }
                                    ],
                                    method: {
                                        save: () => {
                                            let value = this.formmm.form.getFieldsValue();
                                            this.props.myFetch("saveZjTzPppTermAllReply", value).then(({ success, data, message }) => {
                                                if (success) {
                                                    this.setState({
                                                        loadingSend: false
                                                    })
                                                    this.formmm.refresh();
                                                } else {
                                                }
                                            })
                                        },
                                        goBack: (obj) => {
                                            this.props.dispatch(
                                                goBack()
                                            )
                                        },
                                        hideClick: () => {
                                            if (statusId === '1') {
                                                return true
                                            } else {
                                                return false
                                            }
                                        },
                                        styles: (obj) => {
                                            
                                        },
                                        exportClick: (obj) => {
                                            window.open(ureport+'excel?_u=file:zjTzPppTermReply.xml&_n='+projectName+'PPP合同条款分析&url='+domain+'&pppTermId='+pppTermId);
                                        }
                                    },
                                    actionBtns: actionBtnsVal
                                }
                            }
                        ]}
                        btns={[

                        ]}
                    />

                </Spin>

            </div>
        );
    }
}

export default index;