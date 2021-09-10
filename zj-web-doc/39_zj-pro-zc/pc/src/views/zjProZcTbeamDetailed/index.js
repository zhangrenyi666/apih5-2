import React,{ Component } from 'react';
import QnnTable from '../modules/qnn-table'; 
import { message as Msg, Modal,Spin } from "antd";
const  confirm = Modal.confirm;
const config = {
    //获取数据
    fetchConfig: {
        apiName: 'getZjProZcTbeamPrefabricateInformationList'
    },
    //checkbox
    antd: {
        rowKey: function (row) {
            return row.smartSprayId
        },
        size: 'small'
    },
    //drawer:抽屉
    drawerConfig: {
        width: '1100px'
    },
    //分页
    paginationConfig: {
        position: 'bottom'
    },
    
    firstRowIsSearch: false,
    isShowRowSelect: true
};
class index extends Component {
    render() {
        return (
            <div>
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
                                label: '主键id',
                                field: 'smartSprayId',
                                hide: true
                            },
                        },
                        {
                            table: {
                                title: '构建编号',
                                dataIndex: 'tbeamId',
                                key: 'tbeamId',
                                align: 'center',
                                width:100
                            },
                            form: {
                                label: '构建编号',
                                field: 'tbeamId',
                                type:'string',
                                required: true,
                                help:'梁体编号填写示例:  左线22-1T梁',
                            },
                        },
                        {
                            table: {
                                title: '梁长m',
                                dataIndex: 'beamSize',
                                key: 'beamSize',
                                align: 'center',
                            },
                            form: {
                                label: '梁长m',
                                field: 'beamSize',
                                type: 'number',
                                min:0,
                                precision: 2,
                            }
                        },
                        {
                            table: {
                                title: '混凝土标号',
                                dataIndex: 'concreteNumber',
                                key: 'concreteNumber',
                                align: 'center',
                            },
                            form: {
                                label: '混凝土标号',
                                type: 'string',
                                field: 'concreteNumber'
                            }
                        },
                        {
                            table: {
                                title: '混凝土方量m³',
                                dataIndex: 'concreteVolume',
                                key: 'concreteVolume',
                                align: 'center',
                            },
                            form: {
                                label: '混凝土方量m³',
                                type: 'number',
                                field: 'concreteVolume',
                                precision: 2,
                                min:0,
                            }
                        },
                        {
                            table: {
                                title: '钢筋绑扎时间',
                                dataIndex: 'colligationTime',
                                key: 'colligationTime',
                                format:"YYYY-MM-DD",
                                align: 'center',
                            },
                            form: {
                                label: '钢筋绑扎时间',
                                type: "date",
                                field: 'colligationTime',
                            }
                        },
                        {
                            table: {
                                title: '混凝土浇筑时间',
                                dataIndex: 'concretingTime',
                                key: 'concretingTime',
                                format:"YYYY-MM-DD",
                                align: 'center',
                            },
                            form: {
                                label: '混凝土浇筑时间',
                                type: "date",
                                field: 'concretingTime',
                            }
                        },
                        {
                            table: {
                                title: '张拉时间',
                                dataIndex: 'stretchDrawTime',
                                key: 'stretchDrawTime',
                                format:"YYYY-MM-DD",
                                align: 'center',
                            },
                            form: {
                                label: '张拉时间',
                                type: "date",
                                field: 'stretchDrawTime',
                                addShow:false
                            }
                        },
                        {
                            table: {
                                title: '压浆时间',
                                dataIndex: 'mudjackTime',
                                key: 'mudjackTime',
                                format:"YYYY-MM-DD",
                                align: 'center',
                            },
                            form: {
                                label: '压浆时间',
                                type: "date",
                                field: 'mudjackTime',
                                addShow:false
                            }
                        },
                        {
                            table: {
                                title: '架设时间',
                                dataIndex: 'setUpTime',
                                key: 'setUpTime',
                                format:"YYYY-MM-DD",
                                align: 'center',
                            },
                            form: {
                                label: '架设时间',
                                type: "date",
                                field: 'setUpTime',
                            }
                        },
                        {
                            table: {
                                title: '状态',
                                dataIndex: 'tbeamPrefabricateTypeId',
                                key: 'tbeamPrefabricateTypeId',
                                type: 'select',
                                align: 'center',
                            },
                            form: {
                                label: '状态',
                                type: "select",
                                // showTime:false,
                                // format:"YYYY-MM-DD"
                                field: 'tbeamPrefabricateTypeId',
                                required: true,
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",//???
                                    otherParams: {
                                        itemId: "tLiangMingXi"
                                    }
                                },
                                optionConfig: {
                                    label: "itemName",
                                    value: "itemId"
                                }
                            }
                        }
                        
                    ]}
                    method={{
                        getValue: (obj)=>{
                            const { fetch } = obj.props;
                            confirm({
                                title: "确定引入时间么?",
                                okText: "确认",
                                cancelText: "取消",
                                onOk: () => {
                                    fetch('getZjProZcTbeamPrefabricateInformationTime',obj.selectedRows).then(
                                        ({ success, message, data }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.table.refresh();
                                            } else {
                                                Msg.error(message)
                                            }
                                        }
                                    );
                                }
                            });
                        },
                    }}
                    actionBtns={[
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
                            field: 'addOutBtn',
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    fetchConfig: {
                                        apiName: 'addZjProZcTbeamPrefabricateInformation'
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',
                            type: 'primary',
                            label: '修改',
                            editDisabled:false,
                            onClick: (obj) => {
                                this.table.clearSelectedRows();
                            },
                            formBtns: [
                                {
                                    name: 'cancel', 
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    fetchConfig: {
                                        apiName: 'updateZjProZcTbeamPrefabricateInformation'
                                    }
                                }
                            ]
                        },
                        {
                            name: 'del',
                            icon: 'delete',
                            type: 'danger',
                            label: '删除',
                            fetchConfig: {
                                apiName: 'batchDeleteUpdateZjProZcTbeamPrefabricateInformation'
                            },
                        },
                        {
                            name: 'getValue',
                            type: 'primary',
                            label: '获取时间',
                            onClick: "bind:getValue",
                            disabled: "bind:_actionBtnNoSelected",
                            
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;