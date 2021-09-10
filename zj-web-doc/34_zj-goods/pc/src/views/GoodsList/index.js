import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import moment from 'moment';
import s from "./style.less";
const config = {
    antd: {
        rowKey: 'yearId',
        size: "small"
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: false
};

class index extends Component {
    constructor() {
        super();
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
                    fetchConfig={{
                        apiName: 'getZjGoodsList',
                        otherParams:function(){
                            return { sendFlag: 0 }
                        }
                    }}
                    {...config}
                    actionBtns={[]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'goodsId',
                                hide: true,
                                type: 'string'
                            }
                        },
                        {
                            table: {
                                title: '单位',
                                dataIndex: 'unit',
                                key: 'unit'
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '姓名',
                                dataIndex: 'name',
                                key: 'name'
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '年份',
                                dataIndex: 'yearName',
                                key: 'yearName'
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '金额',
                                dataIndex: 'money',
                                key: 'money'
                            },
                            isInForm:false
                        }, 
                       
                        {
                            table: {
                                title: '小组长',
                                dataIndex: 'header',
                                key: 'header'
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '用户名',
                                dataIndex: 'userName',
                                key: 'userName'
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '密码',
                                dataIndex: 'password',
                                key: 'password'
                            },
                            isInForm:false
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;