import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";
import { push } from "react-router-redux";
const config = {
    antd: {
        rowKey: function (row) {
            return row.sizeControlRecordId
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
    constructor(props){
        super(props);
        this.state = {
            noteInstructSugId: props.match.params.noteInstructSugId || '',
            // proNameVal: props.match.params.projectName || '',
            // proNameId: props.match.params.projectId || ''
        }
    }
    render() {
        const { noteInstructSugId } = this.state;
        return (
            <div className={s.root}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    fetchConfig = {{
                        apiName: 'getZjTzNoteInstructSugRecordList',
                        otherParams:{
                            noteInstructSugId:noteInstructSugId
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
                                    push(`${mainModule}AdviceInstructions`)
                                )
                            }
                        }
                    ]}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'sizeControlRecordId',
                                type: 'string',
                                hide:true,
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                // onClick:'detail',
                                dataIndex: 'personDeptName',
                                key: 'personDeptName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '推送时间',
                                dataIndex: 'sendTime',
                                format:'YYYY-MM-DD',
                                key: 'sendTime'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '接收人',
                                dataIndex: 'personName',
                                key: 'personName'
                            },
                            isInForm: false
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;