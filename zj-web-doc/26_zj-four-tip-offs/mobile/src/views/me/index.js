import React, { Component } from 'react';
import { Spin,Icon } from 'antd';
import { Toast } from 'antd-mobile';
import s from './style.less';
import incTab from '../letBottomIncTabs';
import { push } from 'react-router-redux';
const gr = require('../../imgs/bg.png');
const tx = require('../../imgs/tx.jpg');
const sectionsStyle = {
    backgroundImage: `url(${gr})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
class Me extends Component {
    state = {
        data: [],
        loading: false
    };
    componentDidMount(){
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
        myFetch('getPersonalInfoDetails', {}).then(({ data, success, message }) => {
            if (success) {
                this.setState({
                    loading: false,
                    data: data
                })
            } else {
                Toast.fail(message)
            }
        })
    }
    render() {
        const { loading, data } = this.state;
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <Spin spinning={loading}>
                <div className={s.root}>
                    <div className={s.top} style={sectionsStyle}>
                        <div className={s.top_c}>
                            <div className={s.top_cl}>
                                <img style={{width:'100%'}} src={data.imageUrl ? data.imageUrl : tx} alt="" />
                            </div>
                            <div className={s.top_cr}>
                                <div className={s.top_crl}>{data.nickname}</div>
                            </div>
                        </div>
                    </div>
                    <div>
                        <div className={s.center}><div className={s.centerl}><span style={{color:'#0066FF',borderLeft:'2px solid #0066FF',paddingLeft:'5%',fontWeight:'bold',fontSize:"16px"}}>????????????</span></div></div>
                        <div className={s.center} onClick={() => {
                                dispatch(push(`${mainModule}RealNameListJ`));
                            }}>
                            <div className={s.centerl}>
                                <Icon style={{fontSize:'26px',display:'inline-block',verticalAlign:'middle'}} type="safety-certificate" theme="twoTone"/>
                                <span style={{paddingLeft:'3%'}}>????????????({data.jszlsmjb == '' || data.jszlsmjb == null ? '0' : data.jszlsmjb})</span>
                            </div>
                            <div className={s.centerr}>
                                <span style={{paddingRight:'3%',color:'#0066FF'}}>????????????</span>
                                <Icon style={{fontSize:'16px',display:'inline-block',color:'#cccccc',verticalAlign:'middle'}} type="right"/>
                            </div>
                        </div>
                        <div className={s.center} onClick={() => {
                                dispatch(push(`${mainModule}AnonymityListJ`));
                            }}>
                            <div className={s.centerl}>
                                <Icon style={{fontSize:'26px',display:'inline-block',verticalAlign:'middle'}} type="lock" theme="twoTone"/>
                                <span style={{paddingLeft:'3%'}}>????????????({data.jszlnmjb == '' || data.jszlnmjb == null ? '0' : data.jszlnmjb})</span>
                            </div>
                            <div className={s.centerr}>
                                <span style={{paddingRight:'3%',color:'#0066FF'}}>????????????</span>
                                <Icon style={{fontSize:'16px',display:'inline-block',color:'#cccccc',verticalAlign:'middle'}} type="right"/>
                            </div>
                        </div>
                        <div className={s.center} onClick={() => {
                                dispatch(push(`${mainModule}ServeList`));
                            }}>
                            <div className={s.centerl} style={{width:'75%'}}>
                                <Icon style={{fontSize:'26px',display:'inline-block',verticalAlign:'middle'}} type="smile" theme="twoTone"/>
                                <span style={{paddingLeft:'3%'}}>?????????????????????????????????({data.fwlx == '' || data.fwlx == null ? '0' : data.fwlx})</span>
                            </div>
                            <div className={s.centerr} style={{width:'25%'}}>
                                <span style={{paddingRight:'3%',color:'#0066FF'}}>????????????</span>
                                <Icon style={{fontSize:'16px',display:'inline-block',color:'#cccccc',verticalAlign:'middle'}} type="right"/>
                            </div>
                        </div>
                        {/* <div className={s.center} onClick={() => {
                                dispatch(push(`${mainModule}BonusList`));
                            }}>
                            <div className={s.centerl}>
                                <Icon style={{fontSize:'26px',display:'inline-block',verticalAlign:'middle'}} type="trophy" theme="twoTone"/>
                                <span style={{paddingLeft:'3%'}}>??????({data.bonusNumberAll == '' || data.bonusNumberAll == null ? '0' : data.bonusNumberAll})</span>
                            </div>
                            <div className={s.centerr}>
                                <span style={{paddingRight:'3%',color:'#0066FF'}}>????????????</span>
                                <Icon style={{fontSize:'16px',display:'inline-block',color:'#cccccc',verticalAlign:'middle'}} type="right"/>
                            </div>
                        </div> */}
                        <div className={s.center}><div className={s.centerl}><span style={{color:'#0066FF',borderLeft:'2px solid #0066FF',paddingLeft:'5%',fontWeight:'bold',fontSize:"16px"}}>????????????</span></div></div>
                        <div className={s.center} onClick={() => {
                                dispatch(push(`${mainModule}RealNameListW`));
                            }}>
                            <div className={s.centerl}>
                                <Icon style={{fontSize:'26px',display:'inline-block',verticalAlign:'middle'}} type="safety-certificate" theme="twoTone"/>
                                <span style={{paddingLeft:'3%'}}>????????????({data.wgwjsmjb == '' || data.wgwjsmjb == null ? '0' : data.wgwjsmjb})</span>
                            </div>
                            <div className={s.centerr}>
                                <span style={{paddingRight:'3%',color:'#0066FF'}}>????????????</span>
                                <Icon style={{fontSize:'16px',display:'inline-block',color:'#cccccc',verticalAlign:'middle'}} type="right"/>
                            </div>
                        </div>
                        <div className={s.center} onClick={() => {
                                dispatch(push(`${mainModule}AnonymityListW`));
                            }}>
                            <div className={s.centerl}>
                                <Icon style={{fontSize:'26px',display:'inline-block',verticalAlign:'middle'}} type="lock" theme="twoTone"/>
                                <span style={{paddingLeft:'3%'}}>????????????({data.wgwjnmjb == '' || data.wgwjnmjb == null ? '0' : data.wgwjnmjb})</span>
                            </div>
                            <div className={s.centerr}>
                                <span style={{paddingRight:'3%',color:'#0066FF'}}>????????????</span>
                                <Icon style={{fontSize:'16px',display:'inline-block',color:'#cccccc',verticalAlign:'middle'}} type="right"/>
                            </div>
                        </div>
                        <div className={s.center}><div className={s.centerl}><span style={{color:'#0066FF',borderLeft:'2px solid #0066FF',paddingLeft:'5%',fontWeight:'bold',fontSize:"16px"}}>????????????</span></div></div>
                        <div className={s.center} onClick={() => {
                                dispatch(push(`${mainModule}RealNameListA`));
                            }}>
                            <div className={s.centerl}>
                                <Icon style={{fontSize:'26px',display:'inline-block',verticalAlign:'middle'}} type="safety-certificate" theme="twoTone"/>
                                <span style={{paddingLeft:'3%'}}>????????????({data.aqyhsmjb == '' || data.aqyhsmjb == null ? '0' : data.aqyhsmjb})</span>
                            </div>
                            <div className={s.centerr}>
                                <span style={{paddingRight:'3%',color:'#0066FF'}}>????????????</span>
                                <Icon style={{fontSize:'16px',display:'inline-block',color:'#cccccc',verticalAlign:'middle'}} type="right"/>
                            </div>
                        </div>
                        <div className={s.center} onClick={() => {
                                dispatch(push(`${mainModule}AnonymityListA`));
                            }}>
                            <div className={s.centerl}>
                                <Icon style={{fontSize:'26px',display:'inline-block',verticalAlign:'middle'}} type="lock" theme="twoTone"/>
                                <span style={{paddingLeft:'3%'}}>????????????({data.aqyhnmjb == '' || data.aqyhnmjb == null ? '0' : data.aqyhnmjb})</span>
                            </div>
                            <div className={s.centerr}>
                                <span style={{paddingRight:'3%',color:'#0066FF'}}>????????????</span>
                                <Icon style={{fontSize:'16px',display:'inline-block',color:'#cccccc',verticalAlign:'middle'}} type="right"/>
                            </div>
                        </div>
                        {/* <div className={s.center}><div className={s.centerl}><span style={{color:'#0066FF',borderLeft:'2px solid #0066FF',paddingLeft:'5%',fontWeight:'bold',fontSize:"16px"}}>????????????</span></div></div>
                        <div className={s.center} onClick={() => {
                                Toast.info('?????????...',1);
                            }}>
                            <div className={s.centerl}>
                                <Icon style={{fontSize:'26px',display:'inline-block',verticalAlign:'middle'}} type="info-circle" theme="twoTone"/>
                                <span style={{paddingLeft:'3%'}}>????????????</span>
                            </div>
                            <div className={s.centerr}>
                                <span style={{paddingRight:'3%',color:'#0066FF'}}>????????????</span>
                                <Icon style={{fontSize:'16px',display:'inline-block',color:'#cccccc',verticalAlign:'middle'}} type="right"/>
                            </div>
                        </div> */}
                    </div>
                </div>
            </Spin>
        )
    }
}

export default incTab(Me);
