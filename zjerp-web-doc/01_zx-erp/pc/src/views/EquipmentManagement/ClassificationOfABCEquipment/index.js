import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 3 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 21 },
            sm: { span: 21 }
        }
    },
    isShowRowSelect:true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {

        }
    }
    render() {
        // const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                    fetchConfig={{
                        apiName: 'getZxEqGlobalCodeList',
                        otherParams: {
                            categoryID: "category100203"
                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            let props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "ClassificationOfABCEquipment"
                            }
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'id',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'categoryID',
                                type: 'string',
                                initialValue:"category100203",
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'globalCode',
                                key: 'globalCode',
                                filter: true,
                                fixed:'left',
                                width:150,
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'globalDesc',
                                key: 'globalDesc',
                                width:150,
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'enable',
                                key: 'enable',
                                width:100,
                                render:(data) => {
                                    if(data === '0'){
                                        return '???';
                                    }else{
                                        return '???';
                                    }
                                }
                            },
                            form: {
                                type: 'radio',
                                optionData:[
                                    {
                                        label:'???',
                                        value:'0'
                                    },
                                    {
                                        label:'???',
                                        value:'1'
                                    }
                                ],
                                initialValue:'1',
                                required: true
                            }
                        },
                        // {
                        //     table: {
                        //         title: '??????????????????',
                        //         dataIndex: 'selected',
                        //         key: 'selected',
                        //         width:100,
                        //         render:(data) => {
                        //             if(data === '0'){
                        //                 return '???';
                        //             }else{
                        //                 return '???';
                        //             }
                        //         }
                        //     },
                        //     form: {
                        //         type: 'radio',
                        //         optionData:[
                        //             {
                        //                 label:'???',
                        //                 value:'0'
                        //             },
                        //             {
                        //                 label:'???',
                        //                 value:'1'
                        //             }
                        //         ],
                        //         required: true
                        //     }
                        // },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'remark',
                                key: 'remark',
                                width:150,
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: 'pp1',
                                dataIndex: 'pp1',
                                key: 'pp1',
                                width:100,
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: 'pp2',
                                dataIndex: 'pp2',
                                key: 'pp2',
                                width:100,
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: 'pp3',
                                dataIndex: 'pp3',
                                key: 'pp3',
                                width:100,
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: 'pp4',
                                dataIndex: 'pp4',
                                key: 'pp4',
                                width:100,
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;