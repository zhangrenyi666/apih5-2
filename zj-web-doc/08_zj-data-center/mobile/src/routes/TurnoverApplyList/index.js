import React, { Component } from 'react';
import { List, NavBar, WhiteSpace, Toast } from 'antd-mobile';
import { MyFetch, getUserInfo } from '../../main'; //,getUserInfo 
import './style.less';
const Item = List.Item;

class TurnoverApplyList extends Component {//出入机房申请列表
    constructor(props) {
        super(props);
        document.getElementsByTagName('title')[0].innerHTML='出入机房申请列表'
        this.state = {
            title: '出入机房申请列表',
            userId:'',
            listData: [
                // {
                //     name: '修电脑',
                //     time: '2018-02-12',
                //     unit: '微订科技',
                //     person: '测试王',
                //     state:'审批中'
                // }, 
            ]
        }
    }

    componentDidMount() {
        getUserInfo(({userInfo})=>{
            let userId = userInfo.userId
            this.setState({userId})
            MyFetch('getZjJfApplicationList', {userId}, ({ data, success, message }) => {
                // console.log('getZjJfApplicationList:',data)
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

    listClick(approvalId) {
        this.props.history.push(`/page/TurnoverExamineDetail/${approvalId}/no`); 
    }

    render() {
        let { title, listData } = this.state
        return <div className="TurnoverApplyListContainer">
            <div className="header">
                {
                    // <NavBar mode="light">{title}</NavBar>
                } 
            </div>
            <div className="addBtn" onClick={()=>{//浮层按钮
                this.props.history.push('/page/TurnoverExamineAdd'); 
            }}>+</div>
            <div className="content">
                {
                    listData.map((v, i) => {
                        let { approvalName, entryTime, applyUserName, approvalType, subordinateUnit, approvalId } = v;
                        let approvalText = "";
                        switch (approvalType) {
                            case '0':
                                approvalText = '未审批'
                                break;
                            case '1':
                                approvalText = '审批中'
                                break;
                            case '2':
                                approvalText = '通过'
                                break;
                            case '3':
                                approvalText = '驳回'
                                break;
                            default:
                                approvalText = ''
                                break;
                        }
                        return <div className="list" onClick={this.listClick.bind(this, approvalId)} key={i}>
                            <Item wrap>
                                <div className="item-con">
                                    <div className="item-header">
                                        编号：{approvalName || '-'}
                                    </div>
                                    <div className="item-con">
                                        <span>申请人：{applyUserName || '-'}</span>
                                        <span>所属单位：{subordinateUnit || '-'}</span>
                                    </div>
                                    <div className="item-footer">
                                        <span>进入时间：{new Date(entryTime).toLocaleDateString() || ''}</span>
                                        <span>审批状态：{approvalText || '-'}</span>
                                    </div>
                                </div>
                            </Item>
                        </div>
                    })
                }
                <WhiteSpace />

                <div className="noData">
                    {
                        listData.length === 0 ? '暂无数据' : ''
                    }
                </div>
                
            </div>
        </div>
    }
}

export default TurnoverApplyList;