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
                        apiName: 'getZjTzExecutivePersonnelListForReport',
                        otherParams: {
                            projectId:curCompany.projectId
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
                                dataIndex: 'projectName',
                                width: 180,
                                tooltip:23,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "项目公司名称",
                                dataIndex: 'company1',
                                width: 180,
                                tooltip:23,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "项目公司信息",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'company4',
                                        title: "注册资金(万）",
                                        width: 110
                                    },
                                    {
                                        title: '注册地',
                                        dataIndex: 'company3',
                                        width: 260
                                    },
                                    {
                                        title: '法定代表人',
                                        dataIndex: 'legal',
                                        width: 110
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: "股东信息",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'shareType',
                                        title: "股东类型",
                                        width: 110
                                    },
                                    {
                                        title: '股东名称',
                                        dataIndex: 'shareholderName',
                                        width: 260
                                    },
                                    {
                                        title: '股比（%）',
                                        dataIndex: 'proportion',
                                        width: 110
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '董事长',
                                width: 130,
                                dataIndex: 'directorz'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '董事',
                                width: 130,
                                dataIndex: 'director'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '监事长',
                                width: 130,
                                dataIndex: 'supervisorz'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '监事',
                                width: 130,
                                dataIndex: 'supervisor'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '项目公司总经理',
                                width: 130,
                                dataIndex: 'manager'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '备注',
                                width: 130,
                                dataIndex: 'remarks'
                            },
                            isInForm: false
                        },
                    ]}
                    actionBtns={[
                        {
                            name: 'goback',
                            type: 'primary',
                            label: '导出',
                            isValidate: false,
                            onClick: (obj) => {
                                const { ext1,userId,curCompany } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
                                var URL = `${ureport}excel?_u=file:zjTzExecutivePersonnel.xml&url=${domain}&userId=${userId}&ext1=${ext1}&projectId=${curCompany.projectId}&_n=项目公司董监高人员况一览表_${moment(new Date()).format('YYYYMMDD')}&token=${this.props.loginAndLogoutInfo.loginInfo.token}`;
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