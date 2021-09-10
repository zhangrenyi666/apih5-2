import React, { Component } from 'react';
import { WingBlank, WhiteSpace, Button } from 'antd-mobile';
import { myFetch } from '../../tools';
import s from './style.less'
export default class Sign extends Component {
    state = {
        drawState: '0', //0笔  1橡皮擦
        fileId: this.props.match.params.fileId
    }

    componentDidMount() {
        //禁止的滚动事件 
        document.querySelector('#noScroll').addEventListener('touchmove', function (evt) {
            if (!evt._isScroller) {
                evt.preventDefault();
            }
        });

        //获取像素比
        const bp = window.devicePixelRatio || 1;
        this.bp = bp;
        //单位转换
        this.unit = (num = 0) => num * bp;

        //写画布
        const canvasConta = document.querySelector('.canvas');
        const canvas = document.querySelector('canvas');
        const canvasW = canvas.offsetWidth;
        const canvasH = canvas.offsetHeight;
        const canvasOffsettop = canvasConta.offsetTop;
        const canvasOffsetLeft = canvasConta.offsetLeft;
        this.canvasOffsettop = canvasOffsettop;
        this.canvasOffsetLeft = canvasOffsetLeft;
        //绑定给this
        this.canvas = canvas;

        //设置画布大小
        canvas.width = canvasW * bp;
        canvas.height = canvasH * bp;

        this.canvasWidth = canvasW * bp;
        this.canvasHeight = canvasH * bp;

        this.ctx = canvas.getContext('2d');
        this.ctx.lineWidth = this.unit(1);
        this.ctx.lineJoin = 'round';

        canvas.addEventListener('touchstart', this.touchstart, false);
        canvas.addEventListener('touchmove', this.touchmove, false);
        canvas.addEventListener('touchend', this.touchend, false);

    }

    touchstart = (e) => {
        let { drawState } = this.state;
        let { clientX, clientY } = e.touches[0];
        this.ctx.beginPath();
        //橡皮擦
        if (drawState === '1') {
            this.ctx.strokeStyle = 'white';
            this.ctx.lineWidth = this.unit(15);
            //定义橡皮擦
            let eraser = this.refs.eraser;
            eraser.style.width = `${15}px`;
            eraser.style.height = `${15}px`;
            eraser.style.left = clientX - this.canvasOffsetLeft + 'px';
            eraser.style.top = clientY - this.canvasOffsettop + 'px';
        } else {
            this.ctx.strokeStyle = '#000000';
            this.ctx.lineWidth = this.unit(1);
        }

        //正常画
        this.ctx.moveTo(this.unit(clientX) - this.unit(this.canvasOffsetLeft), this.unit(clientY) - this.unit(this.canvasOffsettop));
    }

    touchmove = (e) => {
        let { clientX, clientY } = e.changedTouches[0];
        let { drawState } = this.state;
        //橡皮擦
        if (drawState === '1') {
            //定义橡皮擦pos
            let eraser = this.refs.eraser;
            eraser.style.left = clientX - this.canvasOffsetLeft + 'px';
            eraser.style.top = clientY - this.canvasOffsettop + 'px';
        }

        this.ctx.lineTo(this.unit(clientX) - this.unit(this.canvasOffsetLeft), this.unit(clientY) - this.unit(this.canvasOffsettop));
        this.ctx.stroke();
    }

    touchend = () => {
        this.ctx.closePath();
    }

    save = () => {
        let base64 = this.canvas.toDataURL();
        let params = {
            fileId: this.state.fileId,
            base64Image: base64
        }

        myFetch('addSignPictureToWord', params).then(({ success, data, message }) => {
            if (success) {
                // this.props.history.push('/list')
                this.props.history.goBack()
            }
        })
    }

    cancel = () => {//取消
        this.props.history.goBack();
    }

    change = (e, flag) => {// 0笔  1橡皮擦
        e.nativeEvent.stopImmediatePropagation();
        if (flag === '2') {//清空
            this.setState({
                drawState: '0',//变成默认的签字笔
            })
            this.ctx.clearRect(0, 0, this.unit(this.canvasWidth), this.unit(this.canvasHeight))
        } else {
            this.setState({
                drawState: flag
            })
        }
    }

    render() {
        let { drawState } = this.state;
        return (
            <div id="noScroll" className={s.sign}>
                <div className={s.container}>
                    <div className={s.tit}>请签字</div>
                    <div className={`${s.canvas} canvas`}>
                        <div className={s.tools}>
                            <div onClick={(e) => { this.change(e, '0') }} style={{ color: drawState === '0' ? '#108ee9' : '' }}>签字笔</div>
                            <div onClick={(e) => { this.change(e, '1') }} style={{ color: drawState === '1' ? '#108ee9' : '' }}>橡皮擦</div>
                            <div onClick={(e) => { this.change(e, '2') }}>清空</div>
                        </div>
                        <div ref="eraser" style={{ display: drawState === '1' ? 'block' : 'none' }} className={s.eraser}></div>
                        <canvas></canvas>
                    </div>
                    <div className={s.desc}>
                        <WingBlank>
                            <p>注意事项：</p>
                            第一、在签订合同以前，必须认真审查对方的资质和履约能力。<br />
                            第二、审查合同公章与签字人的身份，确保合同是有效的。<br />
                            第三、签订合同时应当严格审查合同的各项条款，有条件的不妨向专业人员咨询。<br />
                            第四、违约条款是明确约定违约的责任，为将来可能的诉讼与维权打下良好的基础。<br /><br />
                        </WingBlank>
                    </div>

                    <div className={`${s.footer}`}>
                        <div><Button type="primary" onClick={this.cancel}>取消</Button></div>
                        <div><Button type="primary" onClick={this.save}>确定</Button></div>
                    </div>
                </div>
            </div>
        )
    }
}
