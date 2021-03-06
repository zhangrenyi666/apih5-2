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
                <TabPane tab={'?????????????????????'} key={1}>
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
                                    label: '??????id',
                                    field: 'safeScoreId',
                                    hide: true,
                                },
                            },
                            {
                                table: {
                                    title: '??????',
                                    width:100,
                                    dataIndex: 'headUrl',
                                    key: 'headUrl',
                                    render: (data, rowData) => {
                                       return <div style={{width:'80px'}}><img  style={{width:'70px',margin:'5px'}} src={data}/></div>
                                        
                                    }
                                },
                                form:{
                                    type: 'images',
                                    label: '??????',
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
                                    title: '????????????',
                                    width:120,
                                    dataIndex: 'personNo',
                                    key: 'personNo'
                                },
                                form: {
                                    type: 'string',
                                    field: 'personNo',
                                    placeholder:"????????????'00'????????????'000'?????????4?????????",
                                    // addDisabled: true,
                                    // editDisabled:true
                                    diyRules: function (obj) {
                                        var message = obj.message;
                                        return [
                                            // ????????????
                                            {
                                                required: true,
                                                message:message
                                            },
                                            // ??????????????????
                                            {
                                                pattern: new RegExp(/^(0|[1-9][0-9]*){4}$/),
                                                message: "?????????????????????????????????0001,0011"
                                            }
                                        ];
                                    }
                                }
                            },
                            {
                                table: {
                                    title: '????????????',
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
                                    title: '????????????',
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
                                    title: '????????????',
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
                                    title: '??????',
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
                                label: '??????',
                                field: 'addOutBtn',
                                formBtns: [
                                    {
                                        name: 'cancel',
                                        type: 'dashed',
                                        label: '??????',
                                    },
                                    {
                                        name: 'submit',
                                        type: 'primary',
                                        label: '??????',
                                        fetchConfig: {
                                            apiName: 'addZjProZcSafeScore'
                                        }
                                    }
                                ]
                            },
                            {
                                name: 'edit',
                                type: 'primary',
                                label: '??????',
                                editDisabled:false,
                                onClick: (obj) => {
                                    this.table1.clearSelectedRows();
                                },
                                formBtns: [
                                    {
                                        name: 'cancel', 
                                        type: 'dashed',
                                        label: '??????',
                                    },
                                    {
                                        name: 'submit',
                                        type: 'primary',
                                        label: '??????',
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
                                label: '??????',
                                fetchConfig: {
                                    apiName: 'batchDeleteUpdateZjProZcSafeScore'
                                },
                            }
                        ]}
                    />
                </TabPane>
                <TabPane tab={'?????????????????????'} key={2}>
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
                                    label: '??????id',
                                    field: 'safeScoreId',
                                    hide: true,
                                },
                            },
                            {
                                table: {
                                    title: '??????',
                                    width:100,
                                    dataIndex: 'headUrl',
                                    key: 'headUrl',
                                    render: (data, rowData) => {
                                       return <div style={{width:'80px'}}><img  style={{width:'70px',margin:'5px'}} src={data}/></div>
                                        
                                    }
                                },
                                form:{
                                    type: 'images',
                                    label: '??????',
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
                                    title: '????????????',
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
                                    title: '????????????',
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
                                    title: '????????????',
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
                                    title: '????????????',
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
                                    title: '??????',
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
                <TabPane tab={'???????????????'} key={3}>
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
                                    label: '??????id',//???
                                    field: 'videoId',
                                    hide: true,
                                },
                            },
                            {
                                table: {
                                    title: '????????????',
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
                            //         title: '????????????',
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
                                    title: '????????????',
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