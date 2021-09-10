import React, { Component } from "react";
import { push } from "react-router-redux";
import s from "./style.less";
import QnnForm from "../modules/qnn-table/qnn-form";
import { message as Msg, Spin } from "antd";

class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            thousandCheckId: props.match.params.thousandCheckId || '',
            releaseId: props.match.params.releaseId || '',
            loadingSend: false,
            actionBtnsVal:[],
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
                    actionBtnsVal:data
                })
            } else {
            }
        })
    }
    render() {
        const { thousandCheckId,actionBtnsVal } = this.state;
        return (
            <div className={s.root}>
                <Spin spinning={this.state.loadingSend}>
                    <QnnForm
                        {...this.props} 
                        fetchConfig={{
                            apiName: 'getZjTzThousandDeductList',
                            otherParams: {
                                thousandCheckId:thousandCheckId
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
                                label: '外层主键id',
                                field: 'thousandCheckId',
                                hide: true
                            },
                            {
                                type: 'qnnTable',
                                field: 'zjTzThousandDeductList',
                                incToForm: true,
                                qnnTableConfig: {
                                    actionBtnsPosition: "top",
                                    antd: {
                                        rowKey: 'thousandDeductId',
                                        size: 'small',
                                        scroll:{
                                            y:document.documentElement.clientHeight*0.65
                                        }
                                    },
                                    drawerConfig: {
                                        width: '1000px'
                                    },
                                    limit: 999,
                                    curPage:1,
                                    paginationConfig: false,
                                    firstRowIsSearch: false,
                                    isShowRowSelect: false,
                                    formConfig:[
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '主键id',
                                                field: 'thousandDeductId',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '评价项目',
                                                dataIndex: 'evalPro',
                                                key: 'evalPro',
                                                render: (text, rowData, index) => {
                                                    return {
                                                        children: <div>{text}</div>,
                                                        props: {
                                                            rowSpan: Number(rowData.count)
                                                        },
                                                    };
                                                },
                                            }
                                        },
                                        {
                                            table: {
                                                title: '评价指标',
                                                dataIndex: 'evalIndex',
                                                key: 'evalIndex'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '评价内容及扣分标准',
                                                dataIndex: 'evalContent',
                                                tooltip: 80,
                                                width:'35%',
                                                key: 'evalContent'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '标准配分',
                                                width:80,
                                                dataIndex: 'score',
                                                key: 'score'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '扣分',
                                                dataIndex: 'deduct',
                                                key: 'deduct',
                                                width: 120,
                                                tdEdit: (obj) => {
                                                    if (this.props.loginAndLogoutInfo.loginInfo.userInfo.ext1 === '1') {
                                                        if (this.state.releaseId === '1') {
                                                            return false   
                                                        } else {
                                                            return true
                                                        } 
                                                    } else {
                                                        return false
                                                    } 
                                                },
                                            },
                                            form: {
                                                type: "number",
                                                placeholder: "请输入...",
                                                onFocus: (val,obj) => {
                                                    if (obj.record.deduct >= 0) {
                                                        if (parseInt(obj.record.deduct) === Number(obj.record.deduct)) {
                                                            if (obj.record.deduct <= obj.record.score) {

                                                            } else {
                                                                Msg.error("扣分应小于标准配分");
                                                            }
                                                        } else {
                                                            Msg.error("请输入整数");
                                                        }
                                                    } else {
                                                        Msg.error("扣分不能为负数");
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '扣分说明',
                                                dataIndex: 'deductDesc',
                                                key: 'deductDesc',
                                                width:150,
                                                tdEdit: (obj) => {
                                                    if (this.props.loginAndLogoutInfo.loginInfo.userInfo.ext1 === '1') {
                                                        if (this.state.releaseId === '1') {
                                                            return false   
                                                        } else {
                                                            return true
                                                        } 
                                                    } else {
                                                        return false
                                                    } 
                                                },
                                            },
                                            form: {
                                                type: "textarea",
                                                placeholder: "请输入...",
                                                autoSize: {
                                                    minRows: 1,
                                                    maxRows: 4
                                                },
                                            }
                                        }
                                    ],
                                    method:{
                                        save: () => {
                                            let value = this.formmm.form.getFieldsValue();
                                            for (var j = 0; j < value.zjTzThousandDeductList.length;j++){
                                                value.zjTzThousandDeductList[j].actualScore = value.zjTzThousandDeductList[j].score - value.zjTzThousandDeductList[j].deduct;
                                            }
                                            this.props.myFetch("saveZjTzThousandCheckAllDeduct", value).then(({ success, data, message }) => {
                                                if (success) {
                                                    Msg.success(message)
                                                    this.setState({
                                                        loadingSend:false
                                                    })
                                                    this.formmm.refresh();
                                                } else {
                                                }
                                            })
                                        },
                                        goBack: () => {
                                            const { mainModule } = this.props.myPublic.appInfo;
                                            this.props.dispatch(
                                                push(`${mainModule}ThousandthSystemInspection`)
                                            )
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