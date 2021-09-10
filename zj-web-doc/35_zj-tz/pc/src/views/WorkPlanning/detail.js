import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { goBack } from "connected-react-router";
import s from "./style.less";
const config = {
    antd: {
        rowKey: function (row) {
            return row.runSchemeOpinionId
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
            runSchemeId: props.match.params.runSchemeId || ''
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
                    fetchConfig={{
                        apiName: 'getZjTzRunSchemeOpinionList',
                        otherParams: {
                            runSchemeId: this.state.runSchemeId
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                label: '主键id',
                                field: 'runSchemeOpinionId',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '评审次数',
                                dataIndex: 'reviewNumber',
                                width: 120,
                                key: 'reviewNumber',
                                render: (text, rowData, index) => {
                                    return {
                                        children: <div>第{text}次</div>,
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
                                title: '评审部门',
                                width: 160,
                                dataIndex: 'reviewerDeptName',
                                key: 'reviewerDeptName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '评审人',
                                width: 120,
                                dataIndex: 'reviewer',
                                key: 'reviewer'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '评审意见',
                                dataIndex: 'opinion',
                                tooltip: 80,
                                width: '30%',
                                key: 'opinion'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '评审发起时间',
                                dataIndex: 'reviewStartTime',
                                width: 140,
                                format: 'YYYY-MM-DD',
                                key: 'reviewStartTime'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '评审结束时间',
                                width: 140,
                                dataIndex: 'reviewEndTime',
                                format: 'YYYY-MM-DD',
                                key: 'reviewEndTime'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '附件',
                                dataIndex: 'zjTzFileOpinionList',
                                width: 100,
                                onClick: 'detail',
                                key: 'zjTzFileOpinionList',
                                render: (data) => {
                                    return <a>查看附件</a>
                                }
                            },
                            form: {
                                label: '附件',
                                field: 'zjTzFileOpinionList',
                                type: 'files',
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '运营方案'
                                    }
                                }
                            }
                        }
                    ]}
                    actionBtns={[
                        {
                            name: 'goback',
                            type: 'dashed',
                            label: '返回',
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