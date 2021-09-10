import React, { Component } from 'react'
import { Spin, message as Msg } from 'antd';
import s from './style.less';
import $ from 'jquery';
class ZLZN extends Component {
    constructor(props) {
        super(props);
        this.state = {
            aqH: (window.innerHeight - 165 - ((window.innerHeight - 165) / 2) - 27) * 0.65,
            tableData: [],
            dataScroll:[],
            loading: false,
            visible: false,
            year:props.year,
            scrollTops: 0,
            lwCol: [
                {
                    title: '序号',
                    dataIndex: 'number',
                    render: (data) => {
                        if (data != "" && data != null) {
                            return <center>{data}</center>
                        } else {
                            return <center>-</center>
                        }
                    }
                },
                {
                    title: '项目简称',
                    dataIndex: 'projectShortName',
                    render: (data) => {
                        if (data != "" && data != null) {
                            return <center>{data}</center>
                        } else {
                            return <center>-</center>
                        }
                    }
                },
                {
                    title: <span>年计划额</span>,
                    dataIndex: 'yearPlanMoney',
                    render: (data) => {
                        if (data != "" && data != null) {
                            return <center>{data.toFixed(0)}</center>
                        } else {
                            return <center>-</center>
                        }
                    }
                },
                {
                    title: <span>年完成额</span>,
                    dataIndex: 'currentYearOutSubtotal',
                    render: (data) => {
                        if (data != "" && data != null) {
                            return <center>{data.toFixed(0)}</center>
                        } else {
                            return <center>-</center>
                        }
                    }
                },
                {
                    title: <span>完成率</span>,
                    dataIndex: 'doneRate',
                    render: (data) => {
                        if (data != "" && data != null) {
                            return <center>{data}</center>
                        } else {
                            return <center>-</center>
                        }
                    }
                },
            ]
        }
    }
    
    componentDidMount() {
        this.refresh();
        this.timer = setInterval(() => {
            this.setState({
                scrollTops: this.state.scrollTops + 0.7
            }, () => {
                $('#tableContainerw').scrollTop(this.state.scrollTops);
                if (this.state.scrollTops >= $('#tableContainerw').get(0).scrollHeight - $('#tableContainerw').height() + 10) {
                    this.setState({
                        scrollTops: 0
                    })
                }
            })
        }, 50)
        window.addEventListener('resize', this.autoSize, false)
    }
    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.year !== this.state.year) {
            this.setState({ year: prevProps.year }, () => {
                this.refresh();
            })
        }
    }
    refresh = () => {
        const { year } = this.props;
        this.setState({
            loading: true
        })
        this.props.myFetch('getZtEndClaimDoneList', {changeYear:year}).then(({ success, data, message }) => {
            if (success) {
                let dataNoScroll = [];
                let dataScroll = [];
                for(var i = 0;i<data.length;i++){
                    if(i<= 4){
                        data[i].number = i+1;
                        dataNoScroll.push(data[i])
                    }else {
                        data[i].number = i+1;
                        dataScroll.push(data[i])
                    }
                }
                this.setState({
                    tableData:dataNoScroll,
                    dataScroll:dataScroll,
                    loading:false
                });
            } else {
                Msg.error(message);
            }
        });
        
    }
   
    autoSize = () => {
        this.setState({
            aqH: (window.innerHeight - 165 - ((window.innerHeight - 165) / 2) - 27) * 0.65,
        })
    }
    componentWillUnmount() {
        clearInterval(this.timer);
        window.removeEventListener('resize', this.autoSize)
    }
    render() {
        const { loading,lwCol } = this.state;
        return (
            <div className={s.ZLZN}>
                <div className={s.title}>本年项目完成变更索赔排名（万元）</div>
                <Spin spinning={loading}>
                        {/* 表格 */}
                        <div className={s.tableContainer} id='tableCon'>
                            <table className={s.table}>
                                <tbody className={s.tableHeaderr}>
                                    <tr>
                                        {
                                            lwCol.map((item, index) => {
                                                const { title } = item;
                                                return <th style={{ width: $('.thsq').eq(index).outerWidth(), height: '30px'}} key={index}>{title}</th>
                                            })
                                        }
                                    </tr>
                                </tbody>
                                <tbody>
                                    {
                                        this.state.tableData ? this.state.tableData.map((rowData = {}, index) => {
                                            return <tr key={index} style={{ width: $('.thsq').eq(index).outerWidth(), height: '30px' }}>
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
                        {/* 滚动 */}
                        <div className={s.tableContainers} id='tableContainerw'>
                            <table className={s.table} style={{marginTop:'-50px'}}>
                                <tbody className={s.tableHeaderr} style={{ opacity: '0' }}>
                                    <tr>
                                        {
                                            lwCol.map((item, index) => {
                                                const { title } = item;
                                                return <th style={{ width: $('.thsq').eq(index).outerWidth(), height: '30px' }} key={index}>{title}</th>
                                            })
                                        }
                                    </tr>
                                </tbody>
                                <tbody className={s.tableHeader} style={{ opacity: '0' }}>
                                    <tr>
                                        {
                                            lwCol.map((item, index) => {
                                                const { title } = item;
                                                return <th className='thsq' key={index}>{title}</th>
                                            })
                                        }
                                    </tr>
                                </tbody>
                                <tbody>
                                    {
                                        this.state.dataScroll ? this.state.dataScroll.map((rowData = {}, index) => {
                                            return <tr key={index} style={{height: '30px',textAlign:'center'}}>
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
export { ZLZN }