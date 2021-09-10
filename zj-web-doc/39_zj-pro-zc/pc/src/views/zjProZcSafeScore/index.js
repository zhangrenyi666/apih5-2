import React,{ Component } from 'react';
import QnnTable from '../modules/qnn-table'; 
import { Tabs,Collapse,message as Msg } from 'antd';
const TabPane = Tabs.TabPane;
const configTab1 = {
    antd: {
        rowKey: 'safeScoreId',
        size: "small",
        scroll:{
            y:document.documentElement.clientHeight*0.6
        }
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 21 }
        }
    },
    paginationConfig: {
        position: 'bottom'
    },
    drawerConfig: {
        width: '1000px'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true,
};
const configTab2 = {
    antd: {
        rowKey: 'monthFormId',
        size: "small",
        scroll:{
            y:document.documentElement.clientHeight*0.6
        }
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 21 }
        }
    },
    paginationConfig: {
        position: 'bottom'
    },
    drawerConfig: {
        width: '1000px'
    },
    firstRowIsSearch: false,
    isShowRowSelect: false,
};
const configTab3 = {
    antd: {
        rowKey: 'monthFormId',
        size: "small",scroll:{
            y:document.documentElement.clientHeight*0.6
        }
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 21 }
        }
    },
    paginationConfig: {
        position: 'bottom'
    },
    drawerConfig: {
        width: '1000px'
    },
    firstRowIsSearch: false,
    isShowRowSelect: false,
};
class index extends Component { 
    constructor(props) {
        super(props);
        this.state = {
            key:'1'
        }
    }
    tabCallback = (key) => {
        this.setState({ key });
        if (key === '1') {
            if (this.table1) {
                this.table1.refresh();
            }
        } else if (key === '2') {
            if (this.table2) {
                this.table2.refresh();
            }
        } else{
            if (this.table3) {
                this.table3.refresh();
            }
        }
    }
    render() {
        const { key } = this.state;
        return (
            <Tabs activeKey={key} onChange={this.tabCallback}>
                <TabPane tab={'人员及积分管理'} key={1}>
                    <QnnTable
                        {...this.props}
                        firstRowIsSearch={true}
                        fetch={this.props.myFetch} 
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        upload={this.props.myUpload}
                        wrappedComponentRef={(me) => {
                            this.table1 = me;
                        }}
                        fetchConfig={{
                            apiName: 'getZjProZcSafeScoreList'
                        }}
                        {...configTab1}
                        formConfig={[
                            {
                                isInTable: false,
                                form: {
                                    label: '主键id',
                                    field: 'safeScoreId',
                                    hide: true,
                                },
                            },
                            {
                                table: {
                                    title: '头像',
                                    width:100,
                                    dataIndex: 'headUrl',
                                    key: 'headUrl',
                                    render: (data, rowData) => {
                                       return <div style={{width:'80px'}}><img  style={{width:'70px',margin:'5px'}} src={data}/></div>
                                        
                                    }
                                },
                                form:{
                                    type: 'images',
                                    label: '附件',
                                    field: 'zjProZcFileList',
                                    required: true,
                                    wrapperStyle:{},
                                    fetchConfig: {
                                        apiName: window.configs.domain + 'upload',
                                    },
                                    accept: 'image/jpeg',
                                    max: 1
                                }
                            },
                            {
                                table: {
                                    title: '人员编号',
                                    width:120,
                                    dataIndex: 'personNo',
                                    key: 'personNo'
                                },
                                form: {
                                    type: 'string',
                                    field: 'personNo',
                                    placeholder:"请输入以'00'开头或以'000'开头的4位数字",
                                    // addDisabled: true,
                                    // editDisabled:true
                                    diyRules: function (obj) {
                                        var message = obj.message;
                                        return [
                                            // 必填验证
                                            {
                                                required: true,
                                                message:message
                                            },
                                            // 输入规则验证
                                            {
                                                pattern: new RegExp(/^(0|[1-9][0-9]*){4}$/),
                                                message: "请输入正确的格式，如：0001,0011"
                                            }
                                        ];
                                    }
                                }
                            },
                            {
                                table: {
                                    title: '人员姓名',
                                    width:120,
                                    dataIndex: 'personName',
                                    key: 'personName'
                                },
                                form: {
                                    type: 'string',
                                    required:true,
                                    field: 'personName'
                                }
                            },
                            {
                                table: {
                                    title: '所属班组',
                                    width:160,
                                    dataIndex: 'teamId',
                                    key: 'teamId',
                                    type: 'select',
                                },
                                form: {
                                    type: 'select',
                                    field: 'teamId',
                                    required:true,
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",//???
                                        otherParams: {
                                            itemId: "suoShuBanZu"
                                        }
                                    },
                                    optionConfig: {
                                        label: "itemName",
                                        value: "itemId"
                                    }
                                }
                            },
                            {
                                table: {
                                    title: '当前积分',
                                    width:160,
                                    dataIndex: 'nowScore',
                                    key: 'nowScore',
                                    tdEdit: true,
                                    tdEditCb:(obj)=>{
                                        this.props.myFetch('updateZjProZcSafeScoreNumber',{"safeScoreId":obj.newRowData.safeScoreId,"nowScore":obj.newRowData.nowScore}).then(
                                            ({ success,message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.table1.refresh();
                                                } else {
                                                    Msg.error(message);
                                                    this.table1.refresh();
                                                }
                                            }
                                        );
                                    },
                                },
                                form: {
                                    type: 'number',
                                    field: 'nowScore',
                                    initialValue:100
                                }
                            },
                            {
                                table: {
                                    title: '备注',
                                    tooltip: 80,
                                    // width:200,
                                    dataIndex: 'remarks',
                                    key: 'remarks'
                                },
                                form: {
                                    type: 'textarea',
                                    field: 'remarks'
                                }
                            },
                        ]}
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
                                            apiName: 'addZjProZcSafeScore'
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
                                    this.table1.clearSelectedRows();
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
                                            apiName: 'updateZjProZcSafeScore'
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
                                    apiName: 'batchDeleteUpdateZjProZcSafeScore'
                                },
                            }
                        ]}
                    />
                </TabPane>
                <TabPane tab={'人员积分排名表'} key={2}>
                    <QnnTable
                        {...this.props}
                        firstRowIsSearch={true}
                        fetch={this.props.myFetch} 
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        upload={this.props.myUpload}
                        wrappedComponentRef={(me) => {
                            this.table2 = me;
                        }}
                        fetchConfig={{
                            apiName: 'getZjProZcSafeScoreListByNowScore'
                        }}
                        {...configTab2}
                        formConfig={[
                            {
                                isInTable: false,
                                form: {
                                    label: '主键id',
                                    field: 'safeScoreId',
                                    hide: true,
                                },
                            },
                            {
                                table: {
                                    title: '头像',
                                    width:100,
                                    dataIndex: 'headUrl',
                                    key: 'headUrl',
                                    render: (data, rowData) => {
                                       return <div style={{width:'80px'}}><img  style={{width:'70px',margin:'5px'}} src={data}/></div>
                                        
                                    }
                                },
                                form:{
                                    type: 'images',
                                    label: '附件',
                                    field: 'zjProZcFileList',
                                    required: true,
                                    wrapperStyle:{},
                                    fetchConfig: {
                                        apiName: window.configs.domain + 'upload',
                                    },
                                    accept: 'image/jpeg',
                                    max: 1
                                }
                            },
                            {
                                table: {
                                    title: '人员编号',
                                    width:120,
                                    dataIndex: 'personNo',
                                    key: 'personNo'
                                },
                                form: {
                                    type: 'string',
                                    field: 'personNo',
                                    addDisabled: true,
                                    editDisabled:true
                                }
                            },
                            {
                                table: {
                                    title: '人员姓名',
                                    width:120,
                                    dataIndex: 'personName',
                                    key: 'personName'
                                },
                                form: {
                                    type: 'string',
                                    field: 'personName'
                                }
                            },
                            {
                                table: {
                                    title: '所属班组',
                                    width:160,
                                    dataIndex: 'teamId',
                                    key: 'teamId',
                                    type: 'select',
                                },
                                form: {
                                    type: 'select',
                                    field: 'teamId',
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",//???
                                        otherParams: {
                                            itemId: "suoShuBanZu"
                                        }
                                    },
                                    optionConfig: {
                                        label: "itemName",
                                        value: "itemId"
                                    }
                                }
                            },
                            {
                                table: {
                                    title: '当前积分',
                                    width:160,
                                    dataIndex: 'nowScore',
                                    key: 'nowScore'
                                },
                                form: {
                                    type: 'number',
                                    field: 'nowScore',
                                    initialValue:100
                                }
                            },
                            {
                                table: {
                                    title: '备注',
                                    tooltip: 80,
                                    // width:200,
                                    dataIndex: 'remarks',
                                    key: 'remarks'
                                },
                                form: {
                                    type: 'textarea',
                                    field: 'remarks'
                                }
                            },
                        ]}
                    />
                </TabPane>
                <TabPane tab={'班组排名表'} key={3}>
                    <QnnTable
                        {...this.props}
                        firstRowIsSearch={true}
                        fetch={this.props.myFetch} 
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        upload={this.props.myUpload}
                        wrappedComponentRef={(me) => {
                            this.table3 = me;
                        }}
                        fetchConfig={{
                            apiName: 'getZjProZcSafeScoreListByTeamId'
                        }}
                        {...configTab3}
                        formConfig={[
                            {
                                isInTable: false,
                                form: {
                                    label: '主键id',//???
                                    field: 'videoId',
                                    hide: true,
                                },
                            },
                            {
                                table: {
                                    title: '班组名称',
                                    dataIndex: 'teamId',
                                    key: 'teamId',
                                    type: 'select',
                                },
                                form: {
                                    type: 'select',
                                    field: 'teamId',
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",//???
                                        otherParams: {
                                            itemId: "suoShuBanZu"
                                        }
                                    },
                                    optionConfig: {
                                        label: "itemName",
                                        value: "itemId"
                                    }
                                }
                            },
                            // {
                            //     table: {
                            //         title: '排名图标',
                            //         dataIndex: 'name',
                            //         key: 'name',
                            //         render: (data, rowData) => {
                            //             return <div style={{width:'80px'}}><img  style={{width:'70px',margin:'5px'}} src={data}/></div>
                                         
                            //          }
                            //     },
                            //     isInForm:false
                            // },
                            {
                                table: {
                                    title: '平均积分',
                                    dataIndex: 'averageScore',
                                    key: 'averageScore'
                                },
                                isInForm:false
                            }
                        ]}
                    />
                </TabPane>
            </Tabs>
        )
    }
}

export default index