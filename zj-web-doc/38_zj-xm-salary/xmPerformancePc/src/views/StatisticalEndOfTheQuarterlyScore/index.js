import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import { message as Msg, Tabs, Spin, Modal } from 'antd';
import moment from 'moment';
import s from './style.less';
import downLoad from "../modules/download";
const { confirm } = Modal;
const { TabPane } = Tabs;
const config = {
    antd: {
        rowKey: 'detailsId',
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    incToForm: true,
    paginationConfig: false,
    pageSize: 9999,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            departmentId: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId || '',
            formConfig: [],
            loading: false,
            activeKey: '1',
            tableData: [],
            departmentData: [],
            quarterlyData: [],
            yearMonth: moment(new Date()).valueOf()
        }
    }
    componentDidMount() {
        this.refresh();
    }
    tabsCallback = (activeKey) => {
        const { departmentData, quarterlyData } = this.state;
        let index = Number(activeKey) - 1;
        this.setState({
            activeKey,
            formConfig: []
        }, () => {
            if (departmentData.length) {
                this.props.myFetch('getZjXmJxQuarterlyProjectDetailsDeptColumn', { deptId: departmentData[0].deptId, assessmentId: quarterlyData[index].assessmentId }).then(({ success, message, data }) => {
                    if (success) {
                        let formConfig = [
                            {
                                isInTable: false,
                                form: {
                                    field: 'detailsId',
                                    type: 'string',
                                    hide: true,
                                }
                            },
                            {
                                table: {
                                    title: '序号',
                                    dataIndex: 'index',
                                    key: 'index',
                                    width: 50,
                                    fixed: "left",
                                    render: (val, rowData, index) => {
                                        return index + 1;
                                    }
                                },
                                isInForm: false
                            },
                        ];
                        for (let i = 0; i < data.length; i++) {
                            if (Object.keys(data[i]).length === 1) {
                                formConfig.push({
                                    table: {
                                        title: Object.keys(data[i])[0],
                                        dataIndex: data[i][Object.keys(data[i])[0]],
                                        key: data[i][Object.keys(data[i])[0]],
                                        fixed: 'left',
                                        width: (i === 0 || i === 1 || i === 2) ? 150 : 120
                                    },
                                    isInForm: false
                                })
                            } else {
                                if (Object.keys(data[i])[1].split('~').length > 1) {
                                    formConfig.push({
                                        table: {
                                            title: `${Object.keys(data[i])[0]}(含加减分项)`,
                                            dataIndex: data[i][Object.keys(data[i])[0]],
                                            key: data[i][Object.keys(data[i])[0]],
                                            children: [
                                                {
                                                    title: Object.keys(data[i])[1],
                                                    dataIndex: data[i][Object.keys(data[i])[1]],
                                                    key: data[i][Object.keys(data[i])[1]],
                                                    children: [
                                                        {
                                                            title: Object.keys(data[i])[2],
                                                            dataIndex: data[i][Object.keys(data[i])[2]],
                                                            key: data[i][Object.keys(data[i])[2]]
                                                        }
                                                    ]
                                                }
                                            ]
                                        },
                                        isInForm: false
                                    })
                                } else {
                                    formConfig.push({
                                        table: {
                                            title: Object.keys(data[i])[0],
                                            dataIndex: data[i][Object.keys(data[i])[0]],
                                            key: data[i][Object.keys(data[i])[0]],
                                            children: [
                                                {
                                                    title: Object.keys(data[i])[1],
                                                    dataIndex: data[i][Object.keys(data[i])[1]],
                                                    key: data[i][Object.keys(data[i])[1]],
                                                    children: [
                                                        {
                                                            title: Object.keys(data[i])[2],
                                                            dataIndex: data[i][Object.keys(data[i])[2]],
                                                            key: data[i][Object.keys(data[i])[2]]
                                                        }
                                                    ]
                                                }
                                            ]
                                        },
                                        isInForm: false
                                    })
                                }

                            }
                        }
                        this.setState({
                            formConfig: formConfig,
                            loading: false
                        })
                    } else {
                        Msg.error(message);
                    }
                });
                this.props.myFetch('countZjXmJxQuarterlyProjectDetailsByDept', { deptId: departmentData[0].deptId, assessmentId: quarterlyData[index].assessmentId }).then((obj) => {
                    if (obj.success) {
                        this.setState({
                            tableData: obj.data
                        })
                    } else {
                        Msg.error(obj.message);
                    }
                });
            }
        })
    }
    refresh = () => {
        const { departmentId } = this.state;
        this.setState({
            loading: true
        })
        this.props.myFetch('getZjXmJxQuarterlyAssessmentList', { yearMonth: this.state.yearMonth }).then((quarterlyObj) => {
            if (quarterlyObj.success) {
                this.setState({
                    quarterlyData: quarterlyObj.data,
                    loading: quarterlyObj.data.length ? true : false
                }, () => {
                    if (quarterlyObj.data.length) {
                        this.props.myFetch('getZjXmJxQuarterlyAssessmentDeptList', { isClosed: '1', departmentId: departmentId }).then((departmentObj) => {
                            if (departmentObj.success) {
                                this.setState({
                                    departmentData: departmentObj.data,
                                    loading: departmentObj.data.length ? true : false
                                }, () => {
                                    if (departmentObj.data.length) {
                                        this.props.myFetch('getZjXmJxQuarterlyProjectDetailsDeptColumn', { deptId: departmentObj.data[0].deptId, assessmentId: quarterlyObj.data[0].assessmentId }).then(({ success, message, data }) => {
                                            if (success) {
                                                let formConfig = [
                                                    {
                                                        isInTable: false,
                                                        form: {
                                                            field: 'detailsId',
                                                            type: 'string',
                                                            hide: true,
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '序号',
                                                            dataIndex: 'index',
                                                            key: 'index',
                                                            width: 50,
                                                            fixed: "left",
                                                            render: (val, rowData, index) => {
                                                                return index + 1;
                                                            }
                                                        },
                                                        isInForm: false
                                                    },
                                                ];
                                                for (let i = 0; i < data.length; i++) {
                                                    if (Object.keys(data[i]).length === 1) {
                                                        formConfig.push({
                                                            table: {
                                                                title: Object.keys(data[i])[0],
                                                                dataIndex: data[i][Object.keys(data[i])[0]],
                                                                key: data[i][Object.keys(data[i])[0]],
                                                                fixed: 'left',
                                                                width: (i === 0 || i === 1 || i === 2) ? 150 : 120
                                                            },
                                                            isInForm: false
                                                        })
                                                    } else {
                                                        if (Object.keys(data[i])[1].split('~').length > 1) {
                                                            formConfig.push({
                                                                table: {
                                                                    title: `${Object.keys(data[i])[0]}(含加减分项)`,
                                                                    dataIndex: data[i][Object.keys(data[i])[0]],
                                                                    key: data[i][Object.keys(data[i])[0]],
                                                                    children: [
                                                                        {
                                                                            title: Object.keys(data[i])[1],
                                                                            dataIndex: data[i][Object.keys(data[i])[1]],
                                                                            key: data[i][Object.keys(data[i])[1]],
                                                                            children: [
                                                                                {
                                                                                    title: Object.keys(data[i])[2],
                                                                                    dataIndex: data[i][Object.keys(data[i])[2]],
                                                                                    key: data[i][Object.keys(data[i])[2]]
                                                                                }
                                                                            ]
                                                                        }
                                                                    ]
                                                                },
                                                                isInForm: false
                                                            })
                                                        } else {
                                                            formConfig.push({
                                                                table: {
                                                                    title: Object.keys(data[i])[0],
                                                                    dataIndex: data[i][Object.keys(data[i])[0]],
                                                                    key: data[i][Object.keys(data[i])[0]],
                                                                    children: [
                                                                        {
                                                                            title: Object.keys(data[i])[1],
                                                                            dataIndex: data[i][Object.keys(data[i])[1]],
                                                                            key: data[i][Object.keys(data[i])[1]],
                                                                            children: [
                                                                                {
                                                                                    title: Object.keys(data[i])[2],
                                                                                    dataIndex: data[i][Object.keys(data[i])[2]],
                                                                                    key: data[i][Object.keys(data[i])[2]]
                                                                                }
                                                                            ]
                                                                        }
                                                                    ]
                                                                },
                                                                isInForm: false
                                                            })
                                                        }

                                                    }
                                                }
                                                this.setState({
                                                    formConfig: formConfig,
                                                    loading: false
                                                })
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                        this.props.myFetch('countZjXmJxQuarterlyProjectDetailsByDept', { deptId: departmentObj.data[0].deptId, assessmentId: quarterlyObj.data[0].assessmentId }).then((obj) => {
                                            if (obj.success) {
                                                this.setState({
                                                    tableData: obj.data
                                                })
                                            } else {
                                                Msg.error(obj.message);
                                            }
                                        });
                                    }
                                })
                            } else {
                                Msg.error(departmentObj.message);
                            }
                        });
                    }
                })
            } else {
                Msg.error(quarterlyObj.message);
            }
        });
    }
    render() {
        const { formConfig, tableData, activeKey, loading, quarterlyData, departmentData, yearMonth } = this.state;
        return (
            <div className={s.root}>
                <Spin spinning={loading}>
                    <QnnForm
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload} //必须返回一个promise
                        //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                        headers={{
                            token: this.props.loginAndLogoutInfo.loginInfo.token
                        }}
                        formItemLayout={{
                            labelCol: {
                                xs: { span: 7 },
                                sm: { span: 7 }
                            },
                            wrapperCol: {
                                xs: { span: 17 },
                                sm: { span: 17 }
                            }
                        }}
                        formConfig={[
                            {
                                label: '考核年份',
                                field: 'yearMonth',
                                type: 'year',
                                initialValue: yearMonth,
                                onChange: (val) => {
                                    this.setState({
                                        yearMonth: moment(val).valueOf()
                                    }, () => {
                                        this.refresh();
                                    })
                                },
                                allowClear: false,
                                placeholder: '请选择',
                                span: 8
                            }
                        ]}
                    />
                    {
                        quarterlyData.length ? <Tabs activeKey={activeKey} onChange={this.tabsCallback}>
                            {
                                quarterlyData.map((item, index) => {
                                    let quarterName;
                                    if (item.quarter === '1') {
                                        quarterName = '一季度';
                                    } else if (item.quarter === '2') {
                                        quarterName = '二季度';
                                    } else if (item.quarter === '3') {
                                        quarterName = '三季度';
                                    } else if (item.quarter === '4') {
                                        quarterName = '四季度';
                                    }
                                    let yearMonthName = moment(item.yearMonth).format('YYYY');
                                    let assessmentTitle = item.assessmentTitle;
                                    return (
                                        <TabPane tab={quarterName} key={`${index + 1}`}>
                                            {
                                                departmentData.length ? formConfig.length && activeKey === `${index + 1}` ? <div><div style={{textAlign:'center',fontSize:'18px',fontWeight:'bold'}}>{yearMonthName}{quarterName}{assessmentTitle}评分统计表</div><QnnTable
                                                    {...this.props}
                                                    fetch={this.props.myFetch}
                                                    upload={this.props.myUpload}
                                                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                                    wrappedComponentRef={(me) => {
                                                        this.table = me;
                                                    }}
                                                    {...config}
                                                    data={tableData}
                                                    actionBtns={[
                                                        {
                                                            name: 'export',
                                                            type: 'primary',
                                                            label: '导出',
                                                            onClick: () => {
                                                                const {
                                                                    loginAndLogoutInfo: {
                                                                        loginInfo: { token }
                                                                    },
                                                                    myPublic: { domain }
                                                                } = this.props;
                                                                let body = {
                                                                    fileName:`${yearMonthName}${quarterName}${assessmentTitle}评分统计表`,
                                                                    isClosed:'1',
                                                                    deptId: departmentData[0].deptId, 
                                                                    assessmentId: quarterlyData[Number(activeKey) - 1].assessmentId,
                                                                    quarter:quarterlyData[Number(activeKey) - 1].quarter,
                                                                    yearMonth:this.state.yearMonth,
                                                                    year:this.state.yearMonth
                                                                }
                                                                let URL = `${domain + "exportZjXmJxQuarterlyProjectDetailsDeptExcel"}`;
                                                                confirm({
                                                                    title: `您确定要导出数据么?`,
                                                                    content: `取消导出请点击取消按钮。`,
                                                                    okText: "确认",
                                                                    cancelText: "取消",
                                                                    onOk: () => {
                                                                        downLoad(URL, body, { token });
                                                                    },
                                                                });
                                                            }
                                                        }
                                                    ]}
                                                    formConfig={formConfig}
                                                /></div> : null : <div style={{ textAlign: "center", paddingTop: "10vh", fontSize: "20px", fontWeight: 'bold' }}>您所在部门暂无本季度考核数据...</div>
                                            }
                                        </TabPane>
                                    )
                                })
                            }
                        </Tabs> : <div style={{ textAlign: "center", paddingTop: "20vh", fontSize: "20px", fontWeight: 'bold' }}>暂无季度考核数据...</div>
                    }
                </Spin>
            </div>
        );
    }
}

export default index;