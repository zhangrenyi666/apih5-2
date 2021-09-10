import React, { Component } from 'react'
import { Spin, message as Msg } from 'antd';
import s from './style.less';
import $ from 'jquery';
class ZLBHZ extends Component {
    constructor(props) {
        super(props);
        this.state = {
            aqH: (window.innerHeight - 165 - ((window.innerHeight - 165) / 2) - 27) * 0.65,
            data: [],
            scrollTops:0,
            loading: false,
            year:props.year,
            lwCol: [
                {
                    title: '项目类型',
                    dataIndex: 'enginnerName',
                    render: (data) => {
                        if (data != "" && data != null) {
                            return <div>{data}</div>
                        } else {
                            return <div>-</div>
                        }
                    }
                },
                {
                    title: '合同内',
                    dataIndex: 'currentYearIn',
                    render: (data) => {
                        if (data != "" && data != null) {
                            return <div>{data.toFixed(0)}</div>
                        } else {
                            return <div>-</div>
                        }
                    }
                },
                {
                    title: '合同外',
                    dataIndex: 'currentYearOutSubtotal',
                    render: (data) => {
                        if (data != "" && data != null) {
                            return <div>{data.toFixed(0)}</div>
                        } else {
                            return <div>-</div>
                        }
                    }
                },
                {
                    title: '合计',
                    dataIndex: 'currentYearTotal',
                    render: (data) => {
                        if (data != "" && data != null) {
                            return <div>{data.toFixed(0)}</div>
                        } else {
                            return <div>-</div>
                        }
                    }
                },
                {
                    title: '确权完成率',
                    dataIndex: 'rightDoneRate',
                    render: (data) => {
                        if (data != "" && data != null) {
                            return <div>{data}</div>
                        } else {
                            return <div>-</div>
                        }
                    }
                },
            ],
        }
    }
    
    componentDidMount() {
        this.refresh();
        this.timer = setInterval(() => {
            this.setState({
                scrollTops: this.state.scrollTops + 0.7
            }, () => {
                $('#tableContainert').scrollTop(this.state.scrollTops);
                
                if (this.state.scrollTops >= $('#tableContainert').get(0).scrollHeight - $('#tableContainert').height() + 10) {
                    this.setState({
                        scrollTops: 0
                    })
                }
            })
        }, 30)
        window.addEventListener('resize', this.autoSize, false)
    }
    autoSize = () => {
        this.setState({
            aqH: (window.innerHeight - 165 - ((window.innerHeight - 165) / 2) - 27) * 0.65,
        })
    }
    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.year !== this.state.year) {
            this.setState({ year: prevProps.year }, () => {
                this.refresh();
            })
        }
    }
    refresh = () => {
        this.setState({ loading: true });
        const { myFetch,year } = this.props;
        myFetch('getZtEndMonthFormGroupList', {changeYear:year }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        loading: false,
                        data
                    });
                } else {
                    Msg.error(message);
                }
            }
        );


    }
    componentWillUnmount() {
        clearInterval(this.timer);
        window.removeEventListener('resize', this.autoSize)
    }
    handleCancel = () => {
        this.setState({
            visible: false,
        });
    }
    render() {
        const { loading, lwCol } = this.state;
        return (
            <div className={s.ZLBHZ}>
                <div className={s.title}>本年确权清收完成情况（万元）</div>
                <Spin spinning={loading}>
                <div className={s.tableContainer} id='tableContainert' onClick={() => {
                    this.setState({
                        visible: true,
                    });
                }}>
                    <table className={s.table}>
                        <tbody className={s.tableHeaderr}>
                            <tr>
                                {
                                    lwCol.map((item, index) => {
                                        const { title } = item;
                                        return <th style={{ width: $('.thst').eq(index).outerWidth(), height: '30px' }} key={index}>{title}</th>
                                    })
                                }
                            </tr>
                        </tbody>
                        <tbody className={s.tableHeader}
                            style={{ opacity: '0' }}
                        >
                            <tr>
                                {
                                    lwCol.map((item, index) => {
                                        const { title } = item;
                                        return <th className='thst' key={index} style={{ width: $('.thst').eq(index).outerWidth(), height: '30px'}}>{title}</th>
                                    })
                                }
                            </tr>
                        </tbody>
                        <tbody>
                            {
                                this.state.data ? this.state.data.map((rowData = {}, index) => {
                                    return <tr key={index} style={{ width: $('.thst').eq(index).outerWidth(), height: '30px'}}>
                                        {
                                            lwCol.map((item, ii) => {
                                                const { dataIndex, render, colspan = () => 1 } = item;
                                                for (const key in rowData) {//确定设置了行数据
                                                    if (key === dataIndex) {
                                                        return <td onClick={() => {
                                                        }} colSpan={colspan(rowData[dataIndex], rowData, index)} key={ii}>{render ? render(rowData[dataIndex], rowData, index) : rowData[dataIndex]}</td>
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
        )
    }
}
export { ZLBHZ }