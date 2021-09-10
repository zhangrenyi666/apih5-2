import React, { Component } from 'react';
import s from './style.less';
import { Spin,Empty } from 'antd';
import { Toast, PullToRefresh, Carousel } from 'antd-mobile';
const zw = require('../../../imgs/zw.jpg');
class Representative extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            refreshing: false,
            height: document.documentElement.clientHeight * 0.6,
            page: 1,
            limit: 10,
            data: [],
            content: ''
        };
    }
    componentDidMount() {
        this.refresh();
    }
    refresh = () => {
        this.setState({
            loading: true
        })
        const { myFetch } = this.props;
        const { page, limit } = this.state;
        myFetch('getTypicalExposureListByMove', { page: page, limit: limit }).then(({ data, success, message }) => {
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
        return (
            <div className={s.root}>
                <Spin spinning={loading}>
                    <PullToRefresh
                        ref={el => this.contentNode = el}
                        style={{
                            height: height,
                            padding:'10px',
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
                                    <div key={index} className={s.carousel}>
                                        <Carousel autoplay autoplayInterval={3000} infinite={true} dots={false}>
                                            {item.files && item.files.length ? item.files.map((items, indexs) => {
                                                return <div key={indexs}><img className={s.imgs} src={items.url} alt="" /></div>
                                            }) : <div><img className={s.imgs} src={zw} alt="" /></div>}
                                        </Carousel>
                                        <div style={{textAlign:'center',color:'#e98f10',fontSize:'16px'}}>{item.title}</div>
                                        <div style={{textAlign:'center',color:'#108ee9',fontSize:'16px'}}>{item.itemName}</div>
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

export default Representative;
