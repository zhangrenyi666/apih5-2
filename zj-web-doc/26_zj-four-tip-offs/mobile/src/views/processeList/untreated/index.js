import React, { Component } from 'react';
import s from './style.less';
import { Spin, Empty } from 'antd';
import { Toast, PullToRefresh } from 'antd-mobile';
import moment from 'moment';
import { push } from 'react-router-redux';
class Untreated extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            refreshing: false,
            height: document.documentElement.clientHeight - 90,
            page: 1,
            limit: 10,
            data: [],
            content: ''
        };
    }
    componentDidMount() {
        const { wx } = window;
        wx.ready(() => {
            wx.hideOptionMenu();
        }) 
        this.refresh();
    }
    refresh = () => {
        this.setState({
            loading: true
        })
        const { myFetch } = this.props;
        const { page, limit } = this.state;
        myFetch('getZjHandlingSituationData', { page: page, limit: limit,type:'0' }).then(({ data, success, message }) => {
            if (success) {
                this.setState({
                    loading: false,
                    data: data,
                    content: '暂无数据...',
                    refreshing: false
                })
            } else {
                Toast.fail(message)
            }
        })
    }
    render() {
        const { loading, data, content, height } = this.state;
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root}>
                <Spin spinning={loading}>
                    <PullToRefresh
                        ref={el => this.contentNode = el}
                        style={{
                            height: height,
                            padding: '10px',
                            overflow: 'auto'
                        }}
                        direction='up'
                        refreshing={this.state.refreshing}
                        indicator={{ deactivate: '上拉刷新' }}
                        distanceToRefresh={window.devicePixelRatio * 25}
                        onRefresh={() => {
                            this.setState({
                                refreshing: true,
                                limit: this.state.limit + 10,
                                page: this.state.page
                            }, () => {
                                this.refresh();
                            });
                        }}
                    >
                        {
                            data && data != '' ? data.map((item, index) => {
                                return (
                                    <div key={index} className={s.center} style={{borderLeft:index % 2 === 0 ? '2px solid #ef0404' : '2px solid #efbc04'}} onClick={() => {
                                        if(item.type === '1'){
                                            dispatch(push(`${mainModule}processeDetail/${item.reportId}/${0}`));
                                        }else if(item.type === '2'){
                                            dispatch(push(`${mainModule}processeDetailA/${item.reportId}/${0}`));
                                        }else if(item.type === '3'){
                                            dispatch(push(`${mainModule}processeDetailW/${item.reportId}/${0}`));          
                                        }    
                                    }}>
                                        <div className={s.centert}>
                                            <div className={s.centertl}>【{item.type === '1' ? '技术质量' : (item.type === '2' ? '安环隐患' : '违规违纪')}】</div>
                                            <div className={s.centertr}>{moment(item.reportTime).format('YYYY-MM-DD HH:mm:ss')}</div>
                                        </div>
                                        <div className={s.centerc}>
                                            <div className={s.centertl}>{item.problemDescribe}</div>
                                        </div>
                                    </div>
                                )
                            }) : <Empty />
                        }
                    </PullToRefresh>
                </Spin>
            </div>
        )
    }
}

export default Untreated;
