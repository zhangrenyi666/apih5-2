import React, { Component } from 'react';
import { Flex, Modal } from 'antd-mobile';
import { Icon } from '../../index';
import { wx } from '../../../main';
import styles from './style.less';
/*
问题：
    播放语音的时候需要用的setState改变的动画 需要改***
    录音完成后没有删除功能
*/
var test = 0;
const myAlert = (confirmText, callback) => {
    if (typeof confirmText === 'string') {//直接就是一段文字提示
        Modal.alert(confirmText, '', [
            { text: '取消', onPress: () => callback('0'), style: 'default' },
            { text: '确认', onPress: () => callback('1') },
        ]);
    } else {//加上了描述的文字提示 {title:'', desc:''}
        let { title, desc } = confirmText
        Modal.alert(title, desc, [
            { text: '取消', onPress: () => callback('0'), style: 'default' },
            { text: '确认', onPress: () => callback('1') },
        ]);
    }
};
const voiceTime = 60;//录音时间 单位/s
const upPxDelete = 80;//上滑超过80即可删除
var _timer = null;
var _touchTimer = null;//用于长按删除录的音频
const svgs = {
    voice: require('./svg/yuyin.svg'),
    wYuyin: require('./svg/wYuyin.svg'), 
}


export default class Voice extends Component {
    constructor(props) {
        super(props);
        if (this.props.recordHide === undefined) {
            console.error('Voice组件需要传入recordHide属性 ---oldWang温馨提示')
        }
        this.state = {
            hide: this.props.recordHide, //false,//
            dev: this.props.dev || false,
            play: false,
            playId: '',//判断当前播放中的语音
            time: voiceTime,
            text: '按住录音',
            recording: false, //录音中
            localIdArr: [],
            // [
            //     {
            //         localId: "wxLocalResource://voiceLocalId1234567890123" || mp3
            //     }
            // ],//音频id
            minRecordind: 1,//最短录音时间 单位/s
            upPx: 0,//上滑距离删除使用 
        }

        //获取所有语音数据
        // this.getVoice = this.getVoice.bind(this);
        this.debug = this.debug.bind(this);
    }

    componentDidMount() {
        //设置默认值
        let voices = this.props.voices;
        if (voices === undefined) { console.log('语音未传入默认数据，视为新增。') }
        if (voices) {
            let { localIdArr } = this.state;
            voices.map((v, i) => {
                let _obj = {
                    //此字段值根据后台订的改就行
                    mp3: v['mp3'] || v['url'],
                    index: i,
                }
                return localIdArr.push(_obj);
            })
            this.onChange(localIdArr)
        }

        //监听录音等 
        let startY;

        const press = this.refs.press;
        const title = this.refs.title;
        // const content = this.refs.content;

        const _this = this;
        let timer = null;

        const vstop = (delateFlag) => {//delateFlag在上滑删除是传入
            let { time, minRecordind } = _this.state;
            let _time = voiceTime - time;
            if (_time >= minRecordind) {
                _this.setState({
                    text: '按住录音',
                    recording: false
                }, () => {
                    wx.stopRecord({
                        success: function (res) {
                            let localId = res.localId;
                            let { localIdArr } = _this.state;
                            if (!delateFlag) {
                                let _obj = {
                                    localId
                                }
                                //上传微信服务器
                                wx.uploadVoice({
                                    localId: localId, // 需要上传的音频的本地ID，由stopRecord接口获得
                                    isShowProgressTips: 1, // 默认为1，显示进度提示
                                    success: function (res) {
                                        _obj.serverId = res.serverId; // 返回音频的服务器端ID
                                        localIdArr.push(_obj);
                                        _this.setState({
                                            // localIdArr,
                                            time: voiceTime,
                                            text: '按住录音',
                                            recording: false
                                        })
                                        _this.onChange(localIdArr)
                                    }
                                });
                            }
                        }
                    });
                })
            } else {
                //如果时间录音时间小于设置的最短时间将不让提交
                _this.setState({
                    time: voiceTime,
                    text: '录音时间太短！',
                    recording: false
                })
                wx.stopRecord();
            }
        }

        //去掉title块上的默认事件防止出现右键菜单
        if (title) {
            title.addEventListener('touchstart', function (e) {
                e.preventDefault();
            }, false)
        }


        if (press) {
            //触摸按钮
            press.addEventListener('touchstart', function (e) {
                e.preventDefault();
                e.stopPropagation();
                wx.stopRecord();

                //停止语音播放
                if (_this.state.play) {
                    wx.stopVoice({
                        localId: _this.state.playId // 需要播放的音频的本地ID，由stopRecord接口获得
                    });
                }

                startY = e.changedTouches[0].clientY;
                let { time } = _this.state;
                if (time === 0) {
                    //时间为0的时候（不让这种情况存在）
                    _this.setState({
                        text: '是否重新录音？',
                        play: false
                    })
                } else {
                    //开始录音
                    clearInterval(timer);
                    _this.setState({
                        text: '录音中...',
                        recording: true,
                        upPx: 0,
                        play: false
                    }, () => {
                        wx.startRecord();
                    })

                    //倒计时 60s 后强制停止
                    timer = setInterval(() => {
                        let { time } = _this.state;
                        if (time === 0) {
                            clearInterval(timer);
                            //結束錄音
                            vstop();
                        } else {
                            _this.setState({
                                time: time - 1
                            })
                        }
                    }, 1000)
                }

            }, false)

            //结束录音
            press.addEventListener('touchend', function (e) {
                e.preventDefault();
                e.stopPropagation()

                let { time, recording, upPx } = _this.state;
                clearInterval(timer);

                if (time === voiceTime) {
                    //1、时间到了后没有这个事件 不计入录音
                    //2、当按住一秒时间不到的时候也是这种情况
                    _this.setState({
                        text: '按住录音',
                        recording: false
                    })
                } else {
                    if (upPx >= upPxDelete) {
                        //这是上滑删除操作
                        vstop(true);
                        _this.setState({
                            time: voiceTime
                        })
                    } else {
                        //正常情况
                        if (recording) {
                            vstop();
                        } else {
                            //这个不是在录音状态所有不能有结束
                            console.log('不是录音状态')
                            return;
                        }
                    }
                }
            }, false)

            //移动
            press.addEventListener('touchmove', function (e) {
                //上滑的距离  
                let _px = startY - e.changedTouches[0].clientY
                _this.setState({
                    upPx: _px
                })
            }, false)

        }

    }

    onChange = (voiceList) => {
        //暂时写死以为后台需要
        voiceList.map((item, index) => {
            return item.orderFlag = index, item.index = index, item.mediaId = item.serverId;
        })

        if (this.props.onChange) {
            this.props.onChange(voiceList)
        }

        this.setState({
            localIdArr: voiceList
        })
    }

    hideFn() {
        let { hide } = this.state;
        hide = !hide;
        this.setState({
            hide
        })
    }

    getVoice = () => {
        let _arr = [];
        for (let i = 0; i < this.state.localIdArr.length; i++) {
            let _o = this.state.localIdArr[i]
            _o.index = i;
            _arr.push(_o)
        }
        return _arr;
    }

    playVoice = (voice) => {
        console.log('执行playVoice')
        const _this = this;
        let { localId, index, mp3 } = voice;
        let { localIdArr, playId, play } = this.state;//play, playId,  
        let id = localId || mp3;//当前点击银屏的id 
        // this.debug(`当前id:${id}, 正在播放的id:${playId}`) 
        if (mp3) {
            //播放的是后台返回的数据
            let myAudio = this.refs.myAudio;
            if (myAudio) {
                myAudio.src = mp3;
                if (myAudio.paused) {
                    //暂停状态
                    myAudio.currentTime = 0;//设置播放位置为0就是重新开始播放
                    myAudio.play();
                } else {
                    //播放状态
                    myAudio.pause();
                }
            }
        } else {
            // 需要播放的音频的本地ID，由stopRecord接口获得  

            //如果点击的是正在播放的就直接暂停
            if (id === playId) {
                this.debug('停止播放', { color: 'yellow' })
                this.setState({
                    play: false,
                    playId: '',
                }, () => {
                    //暂停
                    wx.stopVoice({
                        localId: id // 需要播放的音频的本地ID，由stopRecord接口获得
                    });
                })

                return;
            } else {
                this.debug('播放')
                //设置状态
                this.setState({
                    play: true,
                    playId: id
                })

                wx.playVoice({
                    localId: id // 需要播放的音频的本地ID，由stopRecord接口获得
                });

                //监听语音停止
                wx.onVoicePlayEnd({
                    success: function (res) {
                        let localId = res.localId; // 返回音频的本地ID 
                        _this.setState({
                            play: false,
                            playId: '',
                        })
                    }
                });
            }

        }
    }

    debug(html, style) {
        if (this.state.dev) {
            if (document.getElementsByClassName('debug')[0]) {
                document.getElementsByClassName('debug')[0].innerHTML += `<div style={${style}}>${html}</div>`;
            }
        }
    }

    del = (id) => {
        myAlert('确认该语音删除吗？', (flag) => {
            if (flag === '1') {
                //执行删除 
                let { localIdArr } = this.state;
                this.debug(`执行删除操作：\n 删除前所有语音，${localIdArr.map((item) => { return item.serverId })} \n`);
                let _index = '';
                //localIdArr.indexOf(id);
                for (let i = 0; i < localIdArr.length; i++) {
                    let _id = localIdArr[i].localId || localIdArr[i].mp3 || localIdArr[i].url;
                    if (_id === id) {
                        _index = i;
                    }
                } 
                if (_index !== '') {
                    localIdArr.splice(_index, 1);
                    // console.log(`删除：${_index}`) 
                    this.debug(`\n 删除后所有语音，${localIdArr.map((item) => { return item.serverId })} \n`);
                    this.onChange(localIdArr)
                }
            }
        })
    }

    render() {
        let { hide, time, text, recording, localIdArr, upPx, play, playId, dev } = this.state;
        this.debug(`渲染一遍${test++}`)
        let upText = '上滑删除';
        if (upPx > upPxDelete) {
            upText = '松手即可删除'
        }

        return (
            <div className={styles.voice}>

                <div
                    className="debug"
                    ref={(me) => {
                        if (me) {
                            me.scrollTop = me.scrollHeight;
                        }
                    }}
                    style={{ display: dev ? 'block' : 'none', boxSizing: 'border-box', padding: '10px', width: '50vw', color: 'red', zIndex: '99', textAlign: 'left', height: '25vh', overflow: 'scroll', position: 'fixed', top: '0', left: '0', background: 'rgba(66, 134, 69, 0.5)' }}>
                </div>

                <Flex justify="end" align="center">
                    {
                        hide
                            ?
                            <Icon onClick={this.hideFn.bind(this)} type={svgs.voice} />
                            :
                            '录入中...'
                    }
                </Flex>
                <div ref="content" onClick={() => {
                    if (this.state.play) {
                        wx.stopVoice({
                            localId: this.state.playId // 需要播放的音频的本地ID，由stopRecord接口获得
                        });
                    }
                    this.setState({ hide: true, play: false })
                }} style={{ display: hide ? 'none' : 'block' }} className={styles.content}>
                    <div className={styles.whiteBlock}
                        onClick={(e) => {//大白快阻止冒泡
                            e.stopPropagation();
                        }}
                    >
                        <div style={{ display: !recording ? 'none' : 'block' }} className={styles.deleteText}>{upText}</div>
                        <div style={{ display: !recording ? 'none' : 'block' }} className={styles.time}>
                            <span>{time}</span><span>秒</span>
                        </div>
                        <h4 ref="title" className={styles.title}>{text}</h4>
                        <div className={styles.svg}>
                            <span ref="press" style={{ display: 'inline-block' }}>
                                <Icon size="lg"
                                    className={recording ? styles.recording : ''}
                                    style={{ width: '80px', height: '80px' }}
                                    type={svgs.wYuyin}
                                />
                            </span>
                        </div>
                        <div className={styles.voiceList}>
                            <audio ref="myAudio" src="" autoPlay></audio>
                            <ul style={{ display: localIdArr.length === 0 ? 'none' : 'flex' }}>
                                {
                                    localIdArr.map((v, i) => {
                                        let id = v.localId || v.mp3;
                                        return <li
                                            className={play && playId === id ? styles.playAni : styles.play}
                                            key={i} 
                                            onClick={(e) => {
                                                this.playVoice(v)
                                            }}
                                        >
                                            <span onClick={(e) => {
                                                e.stopPropagation();
                                                e.preventDefault();
                                                this.del(id);
                                            }} className={styles.delbtn}>删除</span>
                                        </li>
                                    })
                                }
                            </ul>
                        </div>
                    </div>
                </div>
            </div >
        )
    }
}
