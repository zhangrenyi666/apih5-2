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
                        apiName: 'getZjTzComplianceDetailListForReport',
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
                                title: "各项目合法合规性文件办理情况汇总",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'companyName',
                                        title: "管理单位",
                                        width: 130
                                    },
                                    {
                                        title: '项目名称',
                                        dataIndex: 'projectName',
                                        width: 260,
                                        tooltip:23,
                                    },
                                    {
                                        title: '项目类型',
                                        dataIndex: 'proTypeName',
                                        width: 130
                                    },
                                    {
                                        title: '项目公司成立时间',
                                        dataIndex: 'company2',
                                        width: 130
                                    },
                                    {
                                        title: '子项目',
                                        dataIndex: 'subprojectName',
                                        width: 260
                                    },
                                    {
                                        title: '工程可行性研究报告',
                                        dataIndex: 'e1',
                                        width: 130
                                    },
                                    {
                                        title: '工程批复或项目核准',
                                        dataIndex: 'e2',
                                        width: 130
                                    },
                                    {
                                        title: '初设批复',
                                        dataIndex: 'e3',
                                        width: 130
                                    },
                                    {
                                        title: '施工图设计批复',
                                        dataIndex: 'e4',
                                        width: 130
                                    },
                                    {
                                        title: '用地批复',
                                        dataIndex: 'e5',
                                        width: 130
                                    },
                                    {
                                        title: '施工许可证',
                                        dataIndex: 'e6',
                                        width: 130
                                    },
                                    {
                                        title: '融资协议',
                                        dataIndex: 'e7',
                                        width: 130
                                    },
                                    {
                                        title: '环评批复',
                                        dataIndex: 'e8',
                                        width: 130
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
                                var URL = `${ureport}excel?_u=file:zjTzComplianceDetailList.xml&url=${domain}&userId=${userId}&ext1=${ext1}&projectId=${curCompany.projectId}&_n=合法合规报表_${moment(new Date()).format('YYYYMMDD')}&token=${this.props.loginAndLogoutInfo.loginInfo.token}`;
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