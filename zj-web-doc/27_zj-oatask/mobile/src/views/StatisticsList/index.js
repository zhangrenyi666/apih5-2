import React, { Component } from 'react';
import { Spin, message as Msg } from 'antd'
import { NavBar, Icon } from 'antd-mobile';
import s from './style.less';
import { push } from 'connected-react-router';
const apis = {
    getLWTableD: "getZjOataskStatisticAnalysisList",
}

export default class index extends Component {
    state = {
        loading: true,
        data: [],
        redFlag: '0',
        lwCol: [
            {
                title: <span>状态/室部</span>,
                dataIndex: 'undertakeDeptName',
                render: (data) => {
                    return <center className={s.oneCol}>{data}</center>
                }
            },
            {
                title: <span>总任务数</span>,
                dataIndex: 'totalNum',
                render: (data) => {
                    if (data) {
                        return <center>{data}</center>
                    } else {
                        return <center>0</center>
                    }
                }
            },
            {
                title: <span>按期完成数</span>,
                dataIndex: 'finishedNum',
                render: (data) => {
                    if (data) {
                        return <center>{data}</center>
                    } else {
                        return <center>0</center>
                    }
                }
            },
            {
                title: <span style={{ color: '#e8ec2d' }}>未完成数</span>,
                dataIndex: 'unfinishedNum',
                render: (data) => {
                    if (data) {
                        return <center style={{ color: '#e8ec2d' }}>{data}</center>
                    } else {
                        return <center style={{ color: '#e8ec2d' }}>0</center>
                    }
                }
            },
            {
                title: <span style={{ color: '#f31c1c' }}>逾期未完成数</span>,
                dataIndex: 'overdueNum',
                render: (data) => {
                    if (data) {
                        return <center style={{ color: '#f31c1c' }}>{data}</center>
                    } else {
                        return <center style={{ color: '#f31c1c' }}>0</center>
                    }
                }
            },
            {
                title: <span style={{ color: '#ec7409' }}>逾期完成数</span>,
                dataIndex: 'unoverdueNum',
                render: (data) => {
                    if (data) {
                        return <center style={{ color: '#ec7409' }}>{data}</center>
                    } else {
                        return <center style={{ color: '#ec7409' }}>0</center>
                    }
                }
            }
        ],
        lwCols: [
            {
                title: <span>责任人</span>,
                dataIndex: 'leaderName',
                render: (data) => {
                    return <center className={s.oneCol}>{data}</center>
                }
            },
            {
                title: <span>总任务数</span>,
                dataIndex: 'totalNum',
                render: (data) => {
                    if (data) {
                        return <center>{data}</center>
                    } else {
                        return <center>0</center>
                    }
                }
            },
            {
                title: <span>按期完成数</span>,
                dataIndex: 'finishedNum',
                render: (data) => {
                    if (data) {
                        return <center>{data}</center>
                    } else {
                        return <center>0</center>
                    }
                }
            },
            {
                title: <span style={{ color: '#e8ec2d' }}>未完成数</span>,
                dataIndex: 'unfinishedNum',
                render: (data) => {
                    if (data) {
                        return <center style={{ color: '#e8ec2d' }}>{data}</center>
                    } else {
                        return <center style={{ color: '#e8ec2d' }}>0</center>
                    }
                }
            },
            {
                title: <span style={{ color: '#f31c1c' }}>逾期未完成数</span>,
                dataIndex: 'overdueNum',
                render: (data) => {
                    if (data) {
                        return <center style={{ color: '#f31c1c' }}>{data}</center>
                    } else {
                        return <center style={{ color: '#f31c1c' }}>0</center>
                    }
                }
            },
            {
                title: <span style={{ color: '#ec7409' }}>逾期完成数</span>,
                dataIndex: 'unoverdueNum',
                render: (data) => {
                    if (data) {
                        return <center style={{ color: '#ec7409' }}>{data}</center>
                    } else {
                        return <center style={{ color: '#ec7409' }}>0</center>
                    }
                }
            }
        ],
    }

    componentDidMount() {
        this.refresh();
    }

    refresh = () => {
        this.props.myFetch(apis.getLWTableD, {}).then(({ data, success, message }) => {
            if (success) {
                this.setState({
                    redFlag: data && data.length ? data[0].redFlag : '',
                    loading: false,
                    data: data,
                })
            } else {
                Msg.error(message)
            }
        });

    }
    render() {
        const { redFlag, lwCol, lwCols, loading, data } = this.state;
        const { dispatch, myPublic: { androidApi, appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root}>
                <div ref="con" className={s.con}>
                    <Spin spinning={loading}>
                        <div
                            style={{
                                width: "100%",
                                height: "45px",
                                position: "fixed",
                                left: "0",
                                top: "0"
                            }}
                        >
                            <NavBar
                                style={{ width: "100%" }}
                                mode="dark"
                                icon={<Icon type="left" />}
                                onLeftClick={() => {
                                    if (androidApi) {
                                        androidApi.closeActivity()
                                    } else {
                                        dispatch(push(`${mainModule}`));
                                    }
                                }}
                            >{"任务统计表"}</NavBar>
                        </div>
                        <div className={s.tableContainer}>
                            <table className={s.table}>
                                <tbody className={s.tableHeader}>
                                    <tr>
                                        <th colSpan={6}>部门月任务统计表</th>
                                    </tr>
                                    <tr>
                                        {
                                            redFlag === '0' ? lwCol.map((item, index) => {
                                                const { title } = item;
                                                return <th key={index}>{title}</th>
                                            }) : lwCols.map((item, index) => {
                                                const { title } = item;
                                                return <th key={index}>{title}</th>
                                            })
                                        }
                                    </tr>
                                </tbody>
                                <tbody>
                                    {
                                        data && data.length ? data.map((rowData = {}, index) => {
                                            return <tr key={index}>
                                                {
                                                    redFlag === '0' ? lwCol.map((item, ii) => {
                                                        const { dataIndex, render, colspan = () => 1, title } = item;
                                                        for (const key in rowData) {//确定设置了行数据
                                                            if (key === dataIndex) {
                                                                return <td colSpan={colspan(rowData[dataIndex], rowData, index)} key={ii}>{render ? render(rowData[dataIndex], rowData, index) : rowData[dataIndex]}</td>
                                                            }
                                                        }
                                                    }) : lwCols.map((item, ii) => {
                                                        const { dataIndex, render, colspan = () => 1, title } = item;
                                                        for (const key in rowData) {//确定设置了行数据
                                                            if (key === dataIndex) {
                                                                return <td colSpan={colspan(rowData[dataIndex], rowData, index)} key={ii}>{render ? render(rowData[dataIndex], rowData, index) : rowData[dataIndex]}</td>
                                                            }
                                                        }
                                                    })
                                                }
                                            </tr>
                                        }) : null
                                    }
                                </tbody>
                            </table>
                        </div>
                    </Spin>
                </div>
            </div>
        )
    }
}
