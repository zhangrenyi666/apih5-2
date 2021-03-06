import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
const config = {
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    // paginationConfig: false,
    firstRowIsSearch: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            qiCi: '',
        }
    }
    componentDidMount() { }
    render() {
        let { myPublic: { domain, appInfo: { ureport } } } = this.props;
        const { qiCi } = this.state;
        const { ext1,userId,curCompany } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
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
                        apiName: 'getZjTzThreeShareholderBillListForReport',
                        otherParams: {
                            projectId:curCompany.projectId
                        }
                    }}
                    {...config}
                    antd={{
                        rowKey: 'designChangeStatisticsId',
                        size: 'small',
                        scroll: {
                            y: document.documentElement.clientHeight * 0.6
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'designChangeStatisticsId',
                                type: 'string',
                                hide: true,
                            }
                        },

                        {
                            table: {
                                title: '??????',
                                dataIndex: 'no',
                                key: 'no',
                                width: 60,
                                align: 'center',
                                render: (data, rows, index) => {
                                    return index + 1
                                }
                            }
                        },
                        {
                            table: {
                                title: "????????????",
                                dataIndex: 'companyName',
                                width: 180,
                                render: (text, rowData, index) => {
                                    return {
                                        children: <div>{text}</div>,
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
                                title: "????????????",
                                tooltip:23,
                                dataIndex: 'projectName',
                                width: 180
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "?????????(??????)",
                                dataIndex: 'amount1',
                                width: 180
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "?????????",
                                dataIndex: 'company3',
                                width: 180
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "????????????????????????",
                                dataIndex: 'company2',
                                width: 180
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "??????????????????",
                                tooltip:23,
                                dataIndex: 'company1',
                                width: 180
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "??????(%)",
                                dataIndex: 'company7',
                                width: 120
                            },
                            isInForm: false
                        },
                        // {
                        //     table: {
                        //         title: "????????????",
                        //         dataIndex: 'aaaa',
                        //         width: 180
                        //     },
                        //     isInForm: false
                        // },
                        {
                            table: {
                                title: "?????????",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'meetNumberNameSha',
                                        title: "??????",
                                        width: 110
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'meetDateSha',
                                        width: 130
                                    },
                                    {
                                        title: '????????????',
                                        dataIndex: 'meetPlaceSha',
                                        width: 110
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'billNameSha',
                                        width: 110
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'resultNameSha',
                                        width: 230
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "?????????",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'meetNumberNameDir',
                                        title: "??????",
                                        width: 110
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'meetDateDir',
                                        width: 130
                                    },
                                    {
                                        title: '????????????',
                                        dataIndex: 'meetPlaceDir',
                                        width: 110
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'billNameDir',
                                        width: 110
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'resultNameDir',
                                        width: 230
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "?????????",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'meetNumberNameSup',
                                        title: "??????",
                                        width: 110
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'meetDateSup',
                                        width: 130
                                    },
                                    {
                                        title: '????????????',
                                        dataIndex: 'meetPlaceSup',
                                        width: 110
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'billNameSup',
                                        width: 110
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'resultNameSup',
                                        width: 230
                                    }
                                ]
                            },
                            isInForm: false
                        }
                    ]}
                    actionBtns={[
                        {
                            name: 'goback',
                            type: 'primary',
                            label: '??????',
                            isValidate: false,
                            onClick: (obj) => {
                                const { ext1,userId,curCompany } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
                                var URL = `${ureport}excel?_u=file:zjTzThreeBillListFinish.xml&url=${domain}&userId=${userId}&ext1=${ext1}&projectId=${curCompany.projectId}&_n=???????????????????????????????????????_${moment(new Date()).format('YYYYMMDD')}&token=${this.props.loginAndLogoutInfo.loginInfo.token}`;
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