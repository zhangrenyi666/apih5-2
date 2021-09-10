import React, { Component } from "react";
import { Tabs } from 'antd-mobile';
import s from "./index.less";
import { Table } from 'antd';
import { NavBar, Icon, Toast, Modal, List, Button, WhiteSpace, WingBlank, SearchBar, DatePicker, InputItem, Tag, Flex } from "antd-mobile"
import moment from 'moment'
const columns = [
    {
        title: () => { return <div style={{ fontWeight: 700 }}>年月</div> },
        dataIndex: 'checkDate',
        width: 150,
        align: 'center',
        render: (h) => {
            if (h) {
                return <div style={{ fontWeight: 700 }}>{`${h.slice(0, 4)}-${h.slice(4, 6)}`}</div>
            } else {
                return <div style={{ fontWeight: 700 }}>{''}</div>
            }
        }
    },
    {
        title: () => { return <div style={{ fontWeight: 700 }}>检查数</div> },
        dataIndex: 'totalNum',
        width: 90,
        align: 'center'
    },
    {
        title: () => { return <div style={{ fontWeight: 700 }}>整改数</div> },
        dataIndex: 'finishNum',
        width: 90,
        align: 'center'
    },
    {
        title: () => { return <div style={{ fontWeight: 700 }}>整改百分比</div> },
        dataIndex: 'totalPercent',
        width: 90,
        align: 'center',
        render: (h) => { return h + '%' }
    },
];

class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            safetyData: [],
            QualityData: [],
            qualityTotalNumber: 0,
            safetyTotalNumber: 0,
            month: null,
            year: null,
            isShowTag: false,
            modalShow: false,
        }
    }
    //---------------状态区-----------------------------------------
    pageIndex = 0
    //-------------------------------------------------------------
    componentDidMount() {
        this.getSafetyData()
    }
    getSafetyData(page = 1) {
        this.reQueryDataFunc(page)
    }
    getQualityData(page) {
        this.reQueryDataFunc(page)
    }
    showModal = () => (e) => {
        e.preventDefault(); // 修复 Android 上点击穿透
        this.setState({
            modalShow: true,
        })
    }
    onClose = () => () => {
        this.setState({
            modalShow: false,
        })
    }
    reQueryDataFunc = async (page = 1) => {
        let params = {
            page,
            limit: 10,
        }
        let year = null
        let month = null

        if (this.state.year) {
            year = '' + new Date(this.state.year).getFullYear()
            params['year'] = year
        } else if (this.state.month) {
            month = ('' + new Date(this.state.month).getFullYear()) + (new Date(this.state.month).getMonth() < 10 ? '0' + (new Date(this.state.month).getMonth() + 1) : (new Date(this.state.month).getMonth() + 1))
            params['checkDate'] = month
        }

        if (this.pageIndex === 0) {
            const { data, success, message, totalNumber } = await this.props.myFetch('getDangerCountInfoList', params)
            if (success) {
                this.setState({
                    safetyData: data.map((item, index) => {
                        return {
                            ...item,
                            key: index
                        }
                    }),
                    safetyTotalNumber: totalNumber
                })
            } else {
                Toast.fail(message, 1);
            }
        } else {
            const { data, success, message, totalNumber } = await this.props.myFetch('getTroubleCountInfoList', params)
            if (success) {
                this.setState({
                    QualityData: data.map((item, index) => {
                        return {
                            ...item,
                            key: index
                        }
                    }),
                    qualityTotalNumber: totalNumber
                })
            } else {
                Toast.fail(message, 1);
            }
        }
    }

    render() {
        const tabs = [
            { title: '安全统计表' },
            { title: '质量统计表' },
        ];
        return (
            <div style={{ width: '100%', height: "100vh" }}>
                <div className={s.top}>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            this.props.myPublic?.androidApi?.closeActivity?.() || this.props.history.goBack()
                        }}
                        rightContent={
                            [
                                <Icon key="0" type="search" style={{ marginRight: '16px' }} onClick={this.showModal()} />,
                            ]
                        }
                    >
                        {"安全质量表格"}
                    </NavBar>
                </div>
                <Tabs tabs={tabs}
                    initialPage={0}
                    swipeable={false}
                    onChange={(tab, index) => {
                        this.pageIndex = index
                        this.setState({
                            month: null,
                            year: null,
                            isShowTag: false
                        }, () => {
                            this.reQueryDataFunc()
                        })
                    }}
                >
                    <div>
                        {this.state.isShowTag ? <div>
                            <WingBlank>
                                <WhiteSpace size="xl" />
                                {this.state.year ? <Tag closable
                                    onClose={() => {
                                        this.setState({
                                            year: null,
                                            isShowTag: false
                                        }, () => {
                                            this.reQueryDataFunc()
                                        })
                                    }}
                                >{`年份:${moment(this.state.year).format('YYYY')}`}</Tag> : null}
                                {this.state.month ? <Tag closable
                                    onClose={() => {
                                        this.setState({
                                            month: null,
                                            isShowTag: false
                                        }, () => {
                                            this.reQueryDataFunc()
                                        })
                                    }}
                                >{`月份:${moment(this.state.month).format('YYYY-MM')}`}</Tag> : null}
                                <WhiteSpace size="xl" />
                            </WingBlank>
                        </div> : null}
                        <Table
                            bordered={true} size={'small'} columns={columns}
                            dataSource={this.state.safetyData}
                            pagination={{
                                position: 'bottom',
                                total: this.state.safetyTotalNumber,
                                onChange: (data) => {
                                    this.getSafetyData(data)
                                }
                            }}
                            scroll={{ x: window.innerWidth }}
                        />
                    </div>
                    <div>
                        {this.state.isShowTag ? <div>
                            <WingBlank>
                                <WhiteSpace size="xl" />
                                {this.state.year ? <Tag closable
                                    onClose={() => {
                                        this.setState({
                                            year: null,
                                            isShowTag: false
                                        }, () => {
                                            this.reQueryDataFunc()
                                        })
                                    }}
                                >{`年份:${moment(this.state.year).format('YYYY')}`}</Tag> : null}
                                {this.state.month ? <Tag closable
                                    onClose={() => {
                                        this.setState({
                                            month: null,
                                            isShowTag: false
                                        }, () => {
                                            this.reQueryDataFunc()
                                        })
                                    }}
                                >{`月份:${moment(this.state.month).format('YYYY-MM')}`}</Tag> : null}
                                <WhiteSpace size="xl" />
                            </WingBlank>
                        </div> : null}
                        <Table
                            bordered={true} size={'small'} columns={columns}
                            dataSource={this.state.QualityData}
                            pagination={{
                                position: 'bottom',
                                total: this.state.qualityTotalNumber,
                                onChange: (page) => {
                                    this.getQualityData(page)
                                }
                            }}
                            scroll={{ x: window.innerWidth }}
                        />
                    </div>
                </Tabs>
                <Modal
                    popup
                    visible={this.state.modalShow}
                    onClose={this.onClose()}
                    animationType="slide-up"
                    afterClose={() => { }}
                >
                    <List renderHeader={() => <div>检索条件</div>} className="popup-list">
                        <List.Item>
                            <DatePicker
                                mode="year"
                                title="选择年份"
                                extra="请选择"
                                value={this.state.year}
                                disabled={this.state.month}
                                extra={this.state.year}
                                format={(value) => {
                                    return moment(value).format('YYYY')
                                }}
                                onChange={year => this.setState({ year })}
                            >
                                <List.Item arrow="horizontal">
                                    <span style={this.state.month ? { color: '#888888' } : {}}>年份</span>
                                </List.Item>
                            </DatePicker>
                            <DatePicker
                                mode="month"
                                title="选择月份"
                                extra="请选择"
                                value={this.state.month}
                                disabled={this.state.year}
                                format={(value) => {
                                    return moment(value).format('YYYY-MM')
                                }}
                                onChange={month => this.setState({ month })}
                            >
                                <List.Item arrow="horizontal">
                                    <span style={this.state.year ? { color: '#888888' } : {}}>月份</span>
                                </List.Item>
                            </DatePicker>
                        </List.Item>
                        <List.Item>
                            <Button type="primary" onClick={() => {
                                this.setState({
                                    isShowTag: true
                                })
                                this.reQueryDataFunc()
                                this.onClose()()
                            }}>查询</Button>
                        </List.Item>
                    </List>
                </Modal>
            </div>
        )
    }
}

export default index;