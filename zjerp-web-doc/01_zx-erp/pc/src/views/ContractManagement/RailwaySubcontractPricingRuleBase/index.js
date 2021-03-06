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
    }
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
                        apiName: 'getZxCtValuationSZRulesList',
                        otherParams:{
                            baseType:"tlgxk"
                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            let props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "RailwaySubcontractPricingRuleBase"
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
                                field: 'baseType',
                                type: 'string',
                                initialValue: 'tlgxk',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'orderNum',
                                key: 'orderNum',
                                filter: true,
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
                                title: '??????????????????',
                                dataIndex: 'ruleName',
                                key: 'ruleName',
                                filter: true
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'remark',
                                key: 'remark'
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '?????????'
                            }
                        },
                        // {
                        //     table: {
                        //         title: '???????????????',
                        //         dataIndex: '4',
                        //         key: '4',
                        //         render:(data) => {
                        //             if(data === '0'){
                        //                 return '???';
                        //             }else{
                        //                 return '???';
                        //             }
                        //         }
                        //     },
                        //     isInForm:false
                        // }
                    ]}
                />
            </div>
        );
    }
}

export default index;