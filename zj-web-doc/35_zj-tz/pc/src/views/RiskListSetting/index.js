import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";
import { message as Msg,Modal } from "antd";
const confirm = Modal.confirm;
const config = {
    fetchConfig: {
        apiName: 'getZjTzRiskListBaseList',
    },
    antd: {
        rowKey: 'riskListBaseId',
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
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
            
        }
    }
    componentDidMount() {
    }
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
                                field: 'riskListBaseId',
                                type: 'string',
                                hide:true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '所属风险类别',
                                type: 'select',
                                field: 'typeId',
                                editDisabled:true,
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
                                required: true,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                              
                                }
                            }
                        },
                        {
                            table: {
                                title: '风险名称',
                                dataIndex: 'number',
                                tooltip:55,
                                key: 'number'
                            },
                            form:{
                                type: 'string',
                                field: 'riskName',
                                placeholder: '请输入',
                                required: true,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '管理层级',
                                onClick: 'detail',
                                tooltip:23,
                                width:260,
                                dataIndex: 'managLever',
                                key: 'managLever'
                            },
                            form: {
                                type: 'string',
                                field: 'managLever',
                                placeholder: '请输入',
                                // required: true,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '适用项目类型',
                                width: 180,
                                tooltip:23,
                                dataIndex: 'applicableItemType',
                                key: 'applicableItemType'
                            },
                            form: {
                                type: 'string',
                                field: 'applicableItemType',
                                placeholder: '请输入',
                                // required: true,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                width: 200,
                                tooltip:80,
                                dataIndex: 'remarks',
                                key: 'remarks'
                            },
                            form: {
                                type: 'textarea',
                                field: 'remarks',
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
                                }
                            }
                        },
                        {
                            table: {
                                title: '状态',
                                width:100,
                                dataIndex: 'lockFlag',
                                key: 'lockFlag',
                                render: (data) => {
                                    if (data) {
                                        if (data === '0') {
                                            return '未锁定'
                                        } else {
                                            return '锁定'
                                        }
                                    } else {
                                        return ''
                                    }
                                    
                                }
                            },
                            isInForm:false
                        }
                    ]}
                    method={{
                        editClick: (obj) => {
                            if (obj.selectedRows[0].lockFlag === '0') {
                                if (obj.selectedRows[0].mainFlag === 'main') {
                                    Msg.warn('请选择内层数据！');
                                    this.table.clearSelectedRows();
                                    obj.btnCallbackFn.closeDrawer();
                                } else {
                                    this.table.clearSelectedRows();
                                }
                            } else if(obj.selectedRows[0].lockFlag === '1') {
                                Msg.warn('锁定数据不允许修改！');
                                this.table.clearSelectedRows();
                                obj.btnCallbackFn.closeDrawer();
                            } else {
                                Msg.warn('请选择内层数据！');
                                this.table.clearSelectedRows();
                                obj.btnCallbackFn.closeDrawer();
                            }
                            
                        },
                        lockClick: (obj) => {
                            const { myFetch } = obj.props;
                            let unSameKindArry = [];
                            let SameKindArry = [];
                            let mainFlagArry = [];
                            let name = '';
                            let apiName = '';
                            if (obj.selectedRows.length > 0) {
                                for (var j = 0; j < obj.selectedRows.length; j++){
                                    if (obj.selectedRows[j].lockFlag === '0') {
                                        unSameKindArry.push(obj.selectedRows[j]);
                                        
                                    } else if (obj.selectedRows[j].mainFlag === 'main') { 
                                        mainFlagArry.push(obj.selectedRows[j]);
                                    }else {
                                        SameKindArry.push(obj.selectedRows[j]);
                                    }
                                }
                                if (unSameKindArry.length>0) {
                                    name = '确定锁定么？';
                                    apiName = 'batchLockUpdateZjTzRiskListBase';
                                }
                                if(SameKindArry.length>0){
                                    name = '确定解锁么？';
                                    apiName = 'batchClearUpdateZjTzRiskListBase';
                                }
                                if (mainFlagArry.length>0) {
                                    Msg.warn('请选择内层数据！');
                                } else {
                                    if ((obj.selectedRows.length === unSameKindArry.length) || (obj.selectedRows.length === SameKindArry.length)) {
                                        confirm({
                                            title: name,
                                            okText: "确认",
                                            cancelText: "取消",
                                            onOk: () => {
                                                myFetch(apiName,obj.selectedRows).then(
                                                    ({ success,message }) => {
                                                        if (success) {
                                                            Msg.success(message);
                                                            this.table.refresh();
                                                            this.table.clearSelectedRows();
                                                        } else {
                                                            Msg.error(message)
                                                        }
                                                    }
                                                );
                                            }
                                        })
                                    } else {
                                        Msg.warn('只能选择相同类型数据!');
                                    }
                                }
                                
                            } else {
                                Msg.warn('请选择数据!');
                            }
                        },
                        delClick: (obj) => {
                            const { myFetch } = obj.props;
                            let aa = [];
                            let lockArry = [];
                            if (obj.selectedRows.length>0) {
                                for (var i = 0; i < obj.selectedRows.length;i++){
                                    if (obj.selectedRows[i].mainFlag === 'main') {
                                        aa.push(obj.selectedRows[i].mainFlag);
                                    }
                                    if (obj.selectedRows[i].lockFlag === '1') {
                                        lockArry.push(obj.selectedRows[i].lockFlag);
                                    }
                                }
                                if (lockArry.length>0) {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('锁定数据不能删除!');
                                    this.table.clearSelectedRows();
                                } else {
                                    if (aa.length>0) {
                                        obj.btnCallbackFn.closeDrawer();
                                        Msg.warn('外层数据不能删除!');
                                        this.table.clearSelectedRows();
                                    } else {
                                        confirm({
                                            title: "确定删除么?",
                                            okText: "确认",
                                            cancelText: "取消",
                                            onOk: () => {
                                                myFetch('batchDeleteUpdateZjTzRiskListBase',obj.selectedRows).then(
                                                    ({ success,message }) => {
                                                        if (success) {
                                                            Msg.success(message);
                                                            this.table.refresh();
                                                            this.table.clearSelectedRows();
                                                        } else {
                                                            Msg.error(message)
                                                        }
                                                    }
                                                );
                                            }
                                        })
                                       
                                    }
                                }
                                
                            } else {
                                Msg.warn('请选择数据!');
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
                />
            </div>
        );
    }
}

export default index;