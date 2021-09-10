import React, { Component } from 'react';
import { push } from 'connected-react-router'
import { Toast, PullToRefresh } from 'antd-mobile';
import ReactDOM from 'react-dom';
import s from './style.less';
import moment from 'moment';
export default class Execute extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: [],
            height: document.documentElement.clientHeight,
            page: 1,
            limit: 20,
            content: "上拉刷新数据",
        }
    }
    componentDidMount() {
        this.loadTherr();
        const hei = this.state.height - ReactDOM.findDOMNode(this.ptr).offsetTop;
        this.setState({
            height: hei,
        })
    }
    loadTherr() {
        let body = {
            page: this.state.page,
            limit: this.state.limit,
            yearSupPlanId: this.props.supPlanId,
            supPlanExecutiveConditionId:this.props.supPlanExecutiveConditionId
        }
        this.props.myFetch("getMatterAndExecuteDetailList", body).then(({ success, message, data }) => {
            if (success) {
                this.setState({
                    data: data,
                    value: ""
                })
            } else {
                Toast.fail(message)
            }
        })
    }
    render() {
        return (
            <div className={s.word}>
                <div className={s.center}>
                    <PullToRefresh
                        ref={el => this.ptr = el}
                        style={{
                            height: this.state.height,
                            overflow: 'auto',
                            paddingBottom:'15%'
                        }}
                        direction='up'
                        refreshing={this.state.refreshing}
                        indicator={{ deactivate: this.state.content }}
                        distanceToRefresh={window.devicePixelRatio * 25}
                        onRefresh={() => {
                            this.setState({
                                refreshing: true,
                                limit: this.state.limit + 20,
                                page: this.state.page
                            }, () => {
                                const { myFetch } = this.props
                                let body = {
                                    page: this.state.page,
                                    limit: this.state.limit,
                                    yearSupPlanId:this.props.supPlanId,
                                    supPlanExecutiveConditionId:this.props.supPlanExecutiveConditionId
                                }
                                myFetch("getMatterAndExecuteDetailList", body).then(({ success, message, data }) => {
                                    if (success) {
                                        this.setState({
                                            data: this.state.data,
                                            refreshing: false,
                                        })
                                    } else {
                                        Toast.fail(message)
                                    }
                                })
                            });
                            setTimeout(() => {
                                this.setState({
                                    refreshing: false
                                });
                            }, 1000);
                        }}
                    >
                        {
                            this.state.data && this.state.data != '' ? this.state.data.map((item, index) => {
                                if (item.planFlag == 0) {
                                    item.planFlag = "是"
                                } else if (item.planFlag == 1) {
                                    item.planFlag = "否"
                                }
                                if (index % 2 == 0) {
                                    return (
                                        <div className={s.centers} key={index} style={{ borderLeft: '5px solid red' }}>
                                            <div className={s.head}>
                                                <div className={s.headl}><span style={{fontWeight:'bold'}}>监督责任部门：</span>{item.supDutyDepName}</div>
                                                <div className={s.headr}><span style={{fontWeight:'bold'}}>责任人：</span>{item.principal}</div>
                                            </div>
                                            <div className={s.setion}>
                                                <div className={s.setionl}><span style={{fontWeight:'bold'}}>填报人：</span>{item.informant}</div>
                                                <div className={s.setionr}><span style={{fontWeight:'bold'}}>配合部门：</span>{item.coopDept}</div>
                                            </div>
                                            <div className={s.setion}>
                                                <div className={s.setionl}><span style={{fontWeight:'bold'}}>是否计划内：</span>{item.planFlag}</div>
                                                <div className={s.setionr}><span style={{fontWeight:'bold'}}>完成时间：</span>{moment(item.scheduleTime).format('YYYY-MM-DD')}</div>
                                            </div>
                                            <div className={s.footer}>
                                                <div className={s.footert}><span style={{fontWeight:'bold'}}>监督事项：</span>{item.supMatter}</div>
                                                <div className={s.footerb}><span style={{fontWeight:'bold'}}>进展情况：</span>{item.progress}</div>
                                                <div className={s.footerb}><span style={{fontWeight:'bold'}}>事项备注：</span>{item.remarks}</div>
                                                {item.fileList && item.fileList[0] ? <div className={s.footers}><span style={{fontWeight:'bold'}}>附件：</span><a href={item.fileList[0].mobileUrl}>{item.fileList[0].fileName}</a></div> : <div className={s.footers}><span style={{fontWeight:'bold'}}>附件：</span></div>}
                                            </div>
                                        </div>
                                    )
                                } else {
                                    return (
                                        <div className={s.centers} key={index} style={{ borderLeft: '5px solid #f49100' }}>
                                            <div className={s.head}>
                                                <div className={s.headl}><span style={{fontWeight:'bold'}}>监督责任部门：</span>{item.supDutyDepName}</div>
                                                <div className={s.headr}><span style={{fontWeight:'bold'}}>责任人：</span>{item.principal}</div>
                                            </div>
                                            <div className={s.setion}>
                                                <div className={s.setionl}><span style={{fontWeight:'bold'}}>填报人：</span>{item.informant}</div>
                                                <div className={s.setionr}><span style={{fontWeight:'bold'}}>配合部门：</span>{item.coopDept}</div>
                                            </div>
                                            <div className={s.setion}>
                                                <div className={s.setionl}><span style={{fontWeight:'bold'}}>是否计划内：</span>{item.planFlag}</div>
                                                <div className={s.setionr}><span style={{fontWeight:'bold'}}>完成时间：</span>{moment(item.scheduleTime).format('YYYY-MM-DD')}</div>
                                            </div>
                                            <div className={s.footer}>
                                                <div className={s.footert}><span style={{fontWeight:'bold'}}>监督事项：</span>{item.supMatter}</div>
                                                <div className={s.footerb}><span style={{fontWeight:'bold'}}>进展情况：</span>{item.progress}</div>
                                                <div className={s.footerb}><span style={{fontWeight:'bold'}}>事项备注：</span>{item.remarks}</div>
                                                {item.fileList && item.fileList[0] ? <div className={s.footers}><span style={{fontWeight:'bold'}}>附件：</span><a href={item.fileList[0].mobileUrl}>{item.fileList[0].fileName}</a></div> : <div className={s.footers}><span style={{fontWeight:'bold'}}>附件：</span></div>}
                                            </div>
                                        </div>

                                    )
                                }

                            }) : <div style={{textAlign:'center',marginTop:'30vh',fontWeight:"bold"}}>暂无数据...</div>
                        }

                    </PullToRefresh>
                </div>
            </div>
        )
    }
} 