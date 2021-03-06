import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { goBack } from "connected-react-router";
import s from "./style.less";
import { message as Msg } from "antd";
const config = {
    antd: {
        rowKey: function (row) {
            return row.lifeCycleOpinionId
        },
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight * 0.65
        }
    },
    drawerConfig: {
        width: '700px'
    },
    limit: 99999,
    curPage: 1,
    paginationConfig: false,
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
        this.state = {
            lifeCycleId: props.match.params.lifeCycleId || '',
            number: props.match.params.number || '',
            apiName: props.match.params.number === '0' ? 'getZjTzLifeCycleChangeOpinionAndProIdList' : 'getZjTzLifeCycleOpinionList'
        }
    }
    componentDidMount() {

    }
    tdEditCb(obj) {
        const { myFetch } = this.props;
        myFetch('updateZjTzPppTermReply', obj.newRowData).then(
            ({ success, message }) => {
                if (success) {
                    Msg.success(message);
                    this.table.refresh();
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    render() {
        const { lifeCycleId } = this.state;

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
                    fetchConfig={{
                        apiName: this.state.apiName,
                        otherParams: {
                            lifeCycleId: lifeCycleId
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                label: '??????id',
                                field: 'lifeCycleOpinionId',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'reviewNumber',
                                width: 120,
                                key: 'reviewNumber',
                                render: (text, rowData, index) => {
                                    return {
                                        children: <div>???{text}???</div>,
                                        props: {
                                            rowSpan: Number(rowData.count)
                                        },
                                    };
                                },
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 160,
                                dataIndex: 'reviewerDeptName',
                                key: 'reviewerDeptName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '?????????',
                                width: 120,
                                dataIndex: 'reviewer',
                                key: 'reviewer'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'opinion',
                                tooltip: 80,
                                width: '30%',
                                key: 'opinion'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'reviewStartTime',
                                width: 140,
                                format: 'YYYY-MM-DD',
                                key: 'reviewStartTime'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????',
                                width: 140,
                                dataIndex: 'reviewEndTime',
                                format: 'YYYY-MM-DD',
                                key: 'reviewEndTime'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'zjTzFileOpinionList',
                                width: 100,
                                onClick: 'detail',
                                key: 'zjTzFileOpinionList',
                                render: (data) => {
                                    return <a>????????????</a>
                                }
                            },
                            form: {
                                label: '??????',
                                field: 'zjTzFileOpinionList',
                                type: 'files',
                                showDownloadIcon: true,//????????????????????????
                                onPreview: "bind:_docFilesByOfficeUrl",//365??????
                                
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '?????????????????????'
                                    }
                                }
                            }
                        }
                    ]}
                    actionBtns={[
                        {
                            name: 'goback',
                            type: 'dashed',
                            label: '??????',
                            isValidate: false,
                            onClick: (obj) => {
                                const { dispatch } = this.props;
                                dispatch(goBack());
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;