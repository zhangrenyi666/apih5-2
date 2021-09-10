import React, { Component } from 'react';
import { NavBar, Icon, Button } from 'antd-mobile';
import { myFetch } from '../../tools';
import MyIcon from '../../components/Icon';
import * as $ from 'jquery';

import s from './style.less';

export default class Detail extends Component {
    state = {
        title: '',
        url: '',
        Sign: false,//是否能去签字
        fileUrl: '',//给后台的
    }
    clickFn = () => {
        let { fileId } = this.state;
        this.props.history.push(`/Sign/${fileId}`);
    }
    componentDidMount() {
        let fileId = this.props.match.params.fileId;
        myFetch('getFileDetailForWeChat', { fileId }).then(({ success, data, message }) => {
            if (success) { 
                // var url = 'http://weixin.fheb.cn:8088/w/d2VpeGluLmZoZWIuY24uODBc5Lit5Lqk56ys5LiA5YWs6Lev5bel56iL5bGA5pyJ6ZmQ5YWs5Y_45YWo6Z2i6aKE566X566h55CG5Yqe5rOVKOivleihjCk4MjQuZG9j?token=_8vUqzEEtivMS_vSxcSgOlZpagJ2moFU';

                this.setState({
                    title: data.title,
                    url: data.url || '',  
                    Sign: data.signState === '0',//是否能去签字
                    fileUrl: data.fileUrl,
                    fileId
                })
 
            }
        })
  
    }

    render() {
        let { title, Sign, url } = this.state;
        return (
            <div ref="Detail" className={s.Detail}>
                <NavBar
                    style={{ height: '100px' }}
                    mode="dark"
                    leftContent={<div onClick={() => {
                        this.props.history.goBack();//返回
                    }} style={{ display: 'flex', alignItems: 'center', fontSize: '35px', }}>
                        <Icon size="lg" type="left" />
                        <span>返回</span>
                    </div>}
                    rightContent={<div onClick={this.clickFn} style={{ display: Sign ? 'flex' : 'none', alignItems: 'center' }}>
                        <div><MyIcon size="lg" type={require('../../svgs/bi.svg')} /></div>
                        <div style={{ fontSize: '35px', marginLeft: '6px' }}>签名</div>
                    </div>}
                >
                    <span style={{ fontSize: '35px' }}>{title}</span>
                </NavBar>
                <iframe src={url} style={{ height: window.innerHeight + 'px', width: "850px" }} frameBorder="0"></iframe>
            </div>
        )
    }
}
