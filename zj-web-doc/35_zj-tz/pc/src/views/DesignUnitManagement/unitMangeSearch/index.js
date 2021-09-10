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
                                title: '评价等级',
                                dataIndex: 'evaluateOrderName',
                                width:100,
                                key: 'evaluateOrderName'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '设计单位全称',
                                width:120,
                                dataIndex: 'unitName',
                                key: 'unitName',
                                filter:true,
                            },
                            form: {
                                label: '设计单位名称',
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
                                    apiName:"getZjTzDesignAdvistoryUnitStandardList"
                                },
                            },
                        },
                        {   
                            table: {
                                title: '资质等级',
                                dataIndex: 'correspondQualityName',
                                key: 'correspondQualityName'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '参与项目名称',
                                width:120,
                                dataIndex: 'projectName',
                                key: 'projectName'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '子项目名称',
                                dataIndex: 'subprojectName',
                                width:120,
                                key: 'subprojectName'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '设计单位选定方式',
                                width:140,
                                dataIndex: 'selectModeName',
                                key: 'selectModeName'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '参与设计阶段',
                                dataIndex: 'designStageName',
                                width:120,
                                key: 'designStageName'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '承担设计工作内容',
                                width:140,
                                dataIndex: 'content',
                                key: 'content'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '对应标段估算/概算<br>（万元）',
                                width:140,
                                dataIndex: 'amount1',
                                key: 'amount1'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '设计费<br>（万元）',
                                width:100,
                                dataIndex: 'amount2',
                                key: 'amount2'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '备注',
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
                                fileName: '设计单位统计查询表',
                                typeId: '1'
                            }
                            let URL = `${domain + "reportZjTzDesignAdvistoryUnitRecordTotalList"}`;
                            confirm({
                                content: '确定导出数据吗?',
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
                            //     Msg.warn('只能选择一条数据')
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