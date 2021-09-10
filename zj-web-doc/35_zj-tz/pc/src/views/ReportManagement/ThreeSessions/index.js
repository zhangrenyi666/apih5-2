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
                                title: '序号',
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
                                title: "管理单位",
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
                                title: "项目名称",
                                tooltip:23,
                                dataIndex: 'projectName',
                                width: 180
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "投资额(万元)",
                                dataIndex: 'amount1',
                                width: 180
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "注册地",
                                dataIndex: 'company3',
                                width: 180
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "项目公司成立时间",
                                dataIndex: 'company2',
                                width: 180
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "项目公司名称",
                                tooltip:23,
                                dataIndex: 'company1',
                                width: 180
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "股比(%)",
                                dataIndex: 'company7',
                                width: 120
                            },
                            isInForm: false
                        },
                        // {
                        //     table: {
                        //         title: "控股情况",
                        //         dataIndex: 'aaaa',
                        //         width: 180
                        //     },
                        //     isInForm: false
                        // },
                        {
                            table: {
                                title: "股东会",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'meetNumberNameSha',
                                        title: "期次",
                                        width: 110
                                    },
                                    {
                                        title: '时间',
                                        dataIndex: 'meetDateSha',
                                        width: 130
                                    },
                                    {
                                        title: '会议地点',
                                        dataIndex: 'meetPlaceSha',
                                        width: 110
                                    },
                                    {
                                        title: '议案',
                                        dataIndex: 'billNameSha',
                                        width: 110
                                    },
                                    {
                                        title: '成果',
                                        dataIndex: 'resultNameSha',
                                        width: 230
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "董事会",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'meetNumberNameDir',
                                        title: "期次",
                                        width: 110
                                    },
                                    {
                                        title: '时间',
                                        dataIndex: 'meetDateDir',
                                        width: 130
                                    },
                                    {
                                        title: '会议地点',
                                        dataIndex: 'meetPlaceDir',
                                        width: 110
                                    },
                                    {
                                        title: '议案',
                                        dataIndex: 'billNameDir',
                                        width: 110
                                    },
                                    {
                                        title: '成果',
                                        dataIndex: 'resultNameDir',
                                        width: 230
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "监事会",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'meetNumberNameSup',
                                        title: "期次",
                                        width: 110
                                    },
                                    {
                                        title: '时间',
                                        dataIndex: 'meetDateSup',
                                        width: 130
                                    },
                                    {
                                        title: '会议地点',
                                        dataIndex: 'meetPlaceSup',
                                        width: 110
                                    },
                                    {
                                        title: '议案',
                                        dataIndex: 'billNameSup',
                                        width: 110
                                    },
                                    {
                                        title: '成果',
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
                            label: '导出',
                            isValidate: false,
                            onClick: (obj) => {
                                const { ext1,userId,curCompany } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
                                var URL = `${ureport}excel?_u=file:zjTzThreeBillListFinish.xml&url=${domain}&userId=${userId}&ext1=${ext1}&projectId=${curCompany.projectId}&_n=项目公司三会召开情况一览表_${moment(new Date()).format('YYYYMMDD')}&token=${this.props.loginAndLogoutInfo.loginInfo.token}`;
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