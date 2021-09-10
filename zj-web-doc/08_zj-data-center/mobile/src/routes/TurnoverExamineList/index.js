import React, { Component } from 'react';
import { List, NavBar, Toast, SegmentedControl } from 'antd-mobile';//WingBlank, WhiteSpace, 
import { MyFetch, getUserInfo } from '../../main'; //, 
import './style.less';
const Item = List.Item;

class TurnoverExamineList extends Component {//出入机房审批列表
    constructor(props) {
        super(props);
        document.getElementsByTagName('title')[0].innerHTML='出入机房审批列表'
        this.state = {
            title: '出入机房审批列表',
            selectedSegmentIndex:0,
            listData: [
                // {
                //     name: '修电脑',
                //     time: '2018-02-12',
                //     unit: '微订科技',
                //     person: '测试王'
                // },
                // {
                //     name: '增电脑',
                //     time: '2018-05-12',
                //     unit: '腾讯',
                //     person: '测试张'
                // }, 
            ]
        }
    }  

    listClick(approvalId) {
        this.props.history.push(`/page/TurnoverExamineDetail/${approvalId}/no`); 
    }

    applyTypeChange(e){ //切换审批类型 
        let params = {
            type:e.nativeEvent.selectedSegmentIndex === 0 ? 1 : (e.nativeEvent.selectedSegmentIndex === 1 ? 0 : '' ),
            userId:this.state.userId,
        }  
        console.log('切换审批类型', params);

        MyFetch('getZjJfApprovalList', params, ({ data, success, message }) => {  
            if (success) {
                console.log('getZjJfApprovalList',data)
                this.setState({
                    listData: data,
                    selectedSegmentIndex:params.type === 0 ? 1 : (params.type === 1 ? 0 : '' ),
                })
            } else {
                Toast.fail(message)
            }
        })
    }

    componentDidMount() {
        getUserInfo(({ userInfo }) => { 
            let userId = userInfo.userId;
            console.log('审批列表', { type:1, userId });
            this.setState({ userId })
            MyFetch('getZjJfApprovalList', { type:1, userId }, ({ data, success, message }) => {
                console.log(data);
                if (success) {
                    this.setState({
                        listData: data
                    })
                } else {
                    Toast.fail(message)
                }
            })
        })
    }

    render() {
        let { title, listData } = this.state
        return <div className="TurnoverExamineListContainer">
            <div className="header" style={{background:'white'}}> 
                <SegmentedControl style={{background:'white' }}  selectedIndex={this.state.selectedSegmentIndex} onChange={this.applyTypeChange.bind(this)} values={['已审批', '未审批']} />
            </div>
            <div className="content">
                {
                    listData.map((v, i) => {
                        let { approvalName, entryTime, applyUserName, subordinateUnit, approvalId } = v;
                        return <div className="list" onClick={this.listClick.bind(this, approvalId)} key={i}><Item>
                            <div className="item-con">
                                <div className="item-header">
                                    编号：{approvalName || '无'}
                                </div>
                                <div className="item-con">
                                    <span>申请人：{applyUserName || '无'}</span>
                                    <span>所属单位：{subordinateUnit || '无'}</span>
                                </div>
                                <div className="item-footer">
                                    <span>进入时间：{new Date(entryTime).toLocaleDateString() || ''}</span> 
                                </div>
                            </div>
                        </Item>
                        </div>
                    })
                }
                <div className="noData">
                    {
                        listData.length === 0 ? '暂无数据' : ''
                    }
                </div>
            </div>
        </div>
    }
}

export default TurnoverExamineList;