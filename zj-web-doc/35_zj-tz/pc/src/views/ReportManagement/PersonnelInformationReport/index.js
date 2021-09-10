import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, message as Msg, Row, Col, Spin } from "antd";
import ProgressCheck from './reportSearch';
const config = {

    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
        }
    }
    componentDidMount() { }
    render() {
        let { myPublic: { domain, appInfo: { ureport } } } = this.props;
        const { ext1, userId, curCompany } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
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
                    fetchConfig={{
                        apiName: 'getZjTzComplianceDetailListForReport',
                        otherParams: {
                            projectId: curCompany.projectId
                        }
                    }}
                    {...config}
                    antd={{
                        rowKey: 'id',
                        size: 'small',
                        scroll: {
                            y: document.documentElement.clientHeight * 0.55
                        }
                    }}
                    topSearchExtendBtns={[
                        // {
                        //     name: 'czReport',
                        //     type: 'primary',
                        //     label: '重置',
                        //     isValidate: false,
                        //     onClick: (obj) => {

                        //     }
                        // },
                    ]}
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
                            isInSearch: true,
                            table: {
                                dataIndex: 'companyName',
                                title: "测试筛选",
                                width: 130
                            },
                            form: {
                                field: 'id',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                dataIndex: 'companyName',
                                title: "测试筛选222",
                                width: 130
                            },
                            form: {
                                field: 'id',
                                type: 'string',
                                hide: true,
                            }
                        },
                    ]}
                    actionBtns={[
                        {
                            drawerTitle: "生成报表",
                            name: 'Component',
                            type: 'primary',
                            label: '生成报表',
                            Component: (obj) => {
                                return <ProgressCheck {...obj} />
                            }
                        },
                        {
                            name: 'goback',
                            type: 'primary',
                            label: '导出',
                            isValidate: false,
                            onClick: (obj) => {
                                const { ext1, userId, curCompany } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
                                var URL = `${ureport}excel?_u=file:zjTzComplianceDetailList.xml&url=${domain}&userId=${userId}&ext1=${ext1}&projectId=${curCompany.projectId}`;
                                console.log(URL);
                                window.open(URL);
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;