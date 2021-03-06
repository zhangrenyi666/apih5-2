import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import s from "./style.less";
import { Modal } from "antd";
import downLoad from "../../modules/download";
const confirm = Modal.confirm;
const config = {
    fetchConfig: {
        apiName: 'getZjTzDesignAdvistoryUnitRecordTotalList',
        otherParams: {
            typeId:'1'
        }
    },
    antd: {
        rowKey: function (row) {
            return row.designAdvistoryUnitRecordId
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
    isShowRowSelect: false,
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
        this.state = {}
    }
    componentDidMount() {}
    render() {
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
                                field: 'designAdvistoryUnitRecordId',
                                type: 'string',
                                hide:true,
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
                            table: {
                                title: '????????????',
                                dataIndex: 'evaluateOrderName',
                                width:100,
                                key: 'evaluateOrderName'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '??????????????????',
                                width:120,
                                dataIndex: 'unitName',
                                key: 'unitName',
                                filter:true,
                            },
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
                                    apiName:"getZjTzDesignAdvistoryUnitStandardList"
                                },
                            },
                        },
                        {   
                            table: {
                                title: '????????????',
                                dataIndex: 'correspondQualityName',
                                key: 'correspondQualityName'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '??????????????????',
                                width:120,
                                dataIndex: 'projectName',
                                key: 'projectName'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '???????????????',
                                dataIndex: 'subprojectName',
                                width:120,
                                key: 'subprojectName'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '????????????????????????',
                                width:140,
                                dataIndex: 'selectModeName',
                                key: 'selectModeName'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '??????????????????',
                                dataIndex: 'designStageName',
                                width:120,
                                key: 'designStageName'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '????????????????????????',
                                width:140,
                                dataIndex: 'content',
                                key: 'content'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '??????????????????/??????<br>????????????',
                                width:140,
                                dataIndex: 'amount1',
                                key: 'amount1'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '?????????<br>????????????',
                                width:100,
                                dataIndex: 'amount2',
                                key: 'amount2'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '??????',
                                dataIndex: 'remarks',
                                tooltip:20,
                                key: 'remarks'
                            },
                            isInForm:false
                        }
                    ]}
                    method={{
                        exportClick: () => {
                            const {
                                loginAndLogoutInfo: {
                                    loginInfo: { token }
                                },
                                myPublic: { domain }
                            } = this.props;
                            let body = {
                                fileName: '???????????????????????????',
                                typeId: '1'
                            }
                            let URL = `${domain + "reportZjTzDesignAdvistoryUnitRecordTotalList"}`;
                            confirm({
                                content: '??????????????????????',
                                onOk: () => {
                                    downLoad(URL, body, { token });
                                }
                            });
                        },
                        shouQuan:(obj) => {
                            // if (obj.selectedRows.length === 1) {
                            //     this.setState({
                            //         visibleSend: true,
                            //         designAdvistoryUnitRecordId:obj.selectedRows[0].designAdvistoryUnitRecordId
                            //     })
                            // } else {
                            //     Msg.warn('????????????????????????')
                            // }
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