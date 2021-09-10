import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";
import { push } from "react-router-redux";
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            policyReplyId: props.match.params.policyReplyId || ''
        }
    }
    render() {
        const { policyReplyId } = this.state;
        return (
            <div className={s.root}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    fetchConfig={{
                        apiName: 'getZjTzPolicyCountryReplyRecordList',
                        otherParams: {
                            policyReplyId: policyReplyId
                        }
                    }}
                    actionBtns={[
                        {
                            name: 'goback',
                            type: 'dashed',
                            label: '返回',
                            isValidate: false,
                            onClick: (obj) => {
                                const { mainModule } = obj.props.myPublic.appInfo;
                                obj.props.dispatch(
                                    push(`${mainModule}PolicyResponses`)
                                )
                            }
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'recordId',
                                type: 'string',
                                hide: true,
                            }
                        }, {
                            table: {
                                title: '回复者',
                                dataIndex: 'modifyUserName',
                                key: 'modifyUserName',
                                width: 150
                            },
                            isForm: false
                        }, {
                            table: {
                                title: '回复时间',
                                dataIndex: 'replyTime',
                                key: 'replyTime',
                                format: 'YYYY-MM-DD HH:mm:ss',
                                width: 150
                            },
                            isForm: false
                        },
                        {
                            table: {
                                title: '回复内容',
                                dataIndex: 'replyInfo',
                                key: 'replyInfo',
                                width: 200
                            },
                            isForm: false
                        },
                        {
                            table: {
                                title: '附件',
                                dataIndex: 'zjTzFileList',
                                key: 'zjTzFileList',
                                width: 400,
                                render: (data) => {
                                    return (
                                        <div style={{ width: '100%' }}>
                                            {
                                                data.map((item,index) => {
                                                    return (
                                                        <div key={index}>
                                                            <a href={item.mobileUrl} target='_blank' style={{ color: '#1890ff', cursor: 'pointer' }}>
                                                                {item.name}
                                                            </a>
                                                        </div>

                                                    )
                                                })
                                            }
                                        </div>
                                    );
                                }
                            },
                            isForm: false
                        }
                    ]}
                    {...window.PolicyResponsesDetailPage}
                />
            </div>
        );
    }
}

export default index;