import React, { Component } from 'react';
import SvgIcon from '../common/svgIcon';
import './voice.less';
class Voice extends Component {
    constructor(props) {
        super(props);
        this.src = "";
        this.state = {
            init: false,
            playing: false,
            yuyinIndex: 2,//语音图标  
        }
        this.yuyinIcons = [
            "yuyin3",
            'yuyin2',
            'yuyin3'
        ]
        this.timer = null;
    }
    componentDidMount() {
        const { voiceInfo } = this.props;
        this.updateVoice(voiceInfo);
    }
    componentDidUpdate(prevProps, prevState) {
        const me = this;
        const { state, yuyinIndex } = me.state
        if (state === 2) {
            if (prevState.state === 1) {
                me.setState({
                    yuyinIndex: 0
                })
            } else if (prevState.state === 2) {
                if (yuyinIndex > 1) {
                    setTimeout(() => {
                        me.setState({
                            yuyinIndex: 0
                        })
                    }, 750)
                } else {
                    setTimeout(() => {
                        me.setState({
                            yuyinIndex: yuyinIndex + 1
                        })
                    }, 150)
                }
            }
        }
    }
    componentWillReceiveProps(nextProps) {
        const { voiceInfo } = nextProps;
        this.updateVoice(voiceInfo);
    }
    updateVoice = (voiceInfo) => {
        const me = this;
        if (voiceInfo && voiceInfo.infoAddress) {
            me.setState({ state: 1 })
            me.src = voiceInfo.infoAddress
        }
    }
    onPlayVoice = () => {//列表里播放
        this.props.playFn(this)
    }
    render() {
        if (this.state.state === 2) {//正在播放
            return (
                <div className="Voice voice_img_svgBox" onClick={this.onPlayVoice}>
                    <SvgIcon type={this.yuyinIcons[this.state.yuyinIndex]} className="voice_img_svg" />
                </div>
            )
        } else if (this.state.state === 1) {
            return (
                <div className="Voice voice_img_svgBox" onClick={this.onPlayVoice}>
                    <SvgIcon type={this.yuyinIcons[2]} className="voice_img_svg" />
                </div>
            )
        } else {
            return (
                <div />
            )
        }
    }
}
export default Voice;