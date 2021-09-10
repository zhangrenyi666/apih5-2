import './canvasRem.js';
class Chart {
    constructor() {
        this.getPixelRatio = function (context) {
            let backingStore = context.backingStorePixelRatio || context.webkitBackingStorePixelRatio || context.mozBackingStorePixelRatio || context.msBackingStorePixelRatio || context.oBackingStorePixelRatio || context.backingStorePixelRatio || 1;
            return (window.devicePixelRatio || 1) / backingStore;
        };
        window.requestAnimationFrame = window.requestAnimationFrame || window.mozRequestAnimationFrame || window.webkitRequestAnimationFrame || window.msRequestAnimationFrame;
        window.cancelAnimationFrame = window.cancelAnimationFrame || window.mozCancelAnimationFrame;
    }

    init(opt) {
        const canvasContainer = opt.ele || document.getElementById(opt.ele);
        if (!canvasContainer) {
            return;
        }
        const canvasConWidth = canvasContainer.offsetWidth;
        this.canvas = document.createElement("canvas");
        this.canvas.className = "canvas";
        this.canvas.width = canvasConWidth;
        this.canvas.height = canvasConWidth;

        canvasContainer.innerHTML = '';
        canvasContainer.appendChild(this.canvas);
        const ctx = this.canvas.getContext("2d");
        this.ratio = this.getPixelRatio(ctx);//画图片时解决模糊问题
        this.canvasWin = this.canvas.width / this.ratio;

        this.setAttr({//设置Chart类属性
            sDeg: opt.sDeg || 0, //仪盘表外框线条起点角度  指针的结束点角度    注意是反方向转动的
            eDeg: opt.eDeg || 180, //仪盘表外框线条结束点角度 指针的起始点角度  注意是反方向转动的
            speed: 5,//转动速度 目前只能填写整数
            targetNum: opt.targetNum || 1,//转动的角度 目标
            canvasBg: opt.canvasBg || "black",//canvas背景 为了实现运动效果不浪费额外性能
            unitValueBig: opt.unitValueBig || 30,//大格子单位
            unitValueSmart: opt.unitValueSmart || 10,//小格子单位
            _panelX: this.canvasWin / 2 || 120,//圆心X
            _panelY: this.canvasWin / 2 || 130,//圆心Y
            _panelR: opt.r * this.ratio - opt._panceBorderWidth || 100 - opt._panceBorderWidth,//圆半径
            _panceBorderWidth: opt._panceBorderWidth || 10,//圆盘border宽
            _paneBorderColor: opt.paneBorderColor || '#669966',//仪表盘 和 刻度(小) 和 圆心 颜色

            dotR: opt.dotR || 10,//小圆点半径 

            _panceKdColor: opt._panceKdColor || 'red',//刻度数字颜色  （大）
            _panceKdFontFamily: opt._panceKdFontFamily || '楷体',//刻度数字字体样式  （大）
            _panceKdFontSize: opt._panceKdFontSize || '22px',//刻度数字字体大小  （大）

            _panelMetreLineLenB: opt._panelMetreLineLenB || 13,//刻度长度（大）
            _panelMetreLineColorB: opt._panelMetreLineColorB || 'red',//刻度颜色（大）
            _panelMetreLineWidthB: opt._panelMetreLineWidthB || 5,//刻度格子线的width(粗)（大）

            panelNumcolor: opt.panelNumcolor || '#669966',//仪盘表上的数字颜色  不是刻度
            panelNumBorderColor: opt.panelNumBorderColor || 'green',//仪盘表上的数字框颜色  
            panelNumFontFamily: opt.panelNumFontFamily || '楷体',//仪盘表上的数字字体样式
            panelNumFontSize: opt.panelNumFontSize || '28px',//仪盘表上的数字字体大小

            _panelMetreLineLenS: opt._panelMetreLineLenS || 8,//刻度长度（小）
            _panelMetreLineColorS: opt._panelMetreLineColorS || '#ccc',//刻度颜色（小）
            _panelMetreLineWidthS: opt._panelMetreLineWidthS || 6,//刻度格子线的width(粗)（小）

            _pancePointerColor: opt.pancePointerColor || 'red',//指针颜色
            _pancePointerWidth: opt._pancePointerWidth || 3,//指针粗细  

            valueType: opt.valueType || 'angle',
            offAni: true,//开启动画

            txt: opt.txt,
            txtFontSize: opt.txtFontSize,
            txtFontFamily: opt.txtFontFamily,//
        })

        this.upDateFn = opt.upDateFn || function () { };
        this.allDeg = this.eDeg + 90;//总角度
        this.viewDeg = 360 - (this.eDeg - this.sDeg);
        this.isExtremity = false;//是否到达极限    
        this.PointerDeg = opt.targetNum;//指针的角度
        this.isUpdate = false; //是否是更新状态
        this.isAniIng = false; //是否正在动画
        this._pancePointerLen = (this._panelR - this._panelMetreLineLenB) * .5; //指针长度 为了性能 不能太长  
        var _this = this;

        ctx.lineWidth = 4;
        this.draw = (opt) => {//需要不停渲染的canvas

            let drawFn = () => {
                var { isExtremity, targetNum, allDeg, viewDeg, sDeg, eDeg, _panelX, _panelY, _pancePointerLen } = _this;
                if (targetNum >= 360 || isExtremity || targetNum < 0) {//所转动角度大于一圈 || 到达极限 || 小于0
                    _this.isUpdate = false;
                    _this.isExtremity = true;
                    _this.isAniIng = false;
                    _this.reachExtremity(targetNum);
                    window.cancelAnimationFrame(_this.ani);
                    return;
                }

                var infor = {
                    nThan: (allDeg - sDeg) / viewDeg * 180 / Math.PI,
                    newDeg: (_this.allDeg - (_this.eDeg + 90)) % 360,//现在所处角度
                    allAng: Math.PI / 360 * _this.viewDeg //全部的弧度
                }

                if (!(allDeg >= sDeg + 90 && allDeg <= eDeg + 90)) {//动画运动过程  
                    // ctx.beginPath();//覆盖指针区域实现运动效果
                    ctx.fillStyle = _this.canvasBg;
                    ctx.arc(_panelX, _panelY, _pancePointerLen + 23, 0, Math.PI * 2);
                    ctx.fill();

                    // ctx.lineWidth = 3;//小圈 暂时不需要了 就是中间的一个颜色圈
                    // ctx.strokeStyle = '#ccc';
                    // ctx.beginPath();
                    // ctx.lineCap = "round";
                    // ctx.arc(_this._panelX, _this._panelY, _this._panelR / 2.5, Math.PI / 180 * _this.sDeg, Math.PI / 180 * _this.eDeg, true);
                    // ctx.stroke();

                    ctx.beginPath();//仪盘的数字
                    let _tNum;
                    if (_this.targetNum - _this.speed <= 0) {//小于起始角度
                        _tNum = 0
                    } else if (infor.newDeg >= _this.targetNum) {//正常向前走
                        _tNum = (_this.valueType === '' ? (((_this.allDeg - 90 - eDeg) % 360) / 360 * (360 / _this.viewDeg) * 100).toFixed(0) : infor.nThan + 1);
                    } else {//向后转百分比值未调
                        _tNum = infor.newDeg <= 0 ? 0 : infor.newDeg;//目标数字
                    }

                    if (_tNum === '0') {//强制不等于0
                        _tNum = 0.01
                    }
                    if (_tNum >= _this.targetNum) { _tNum = _this.targetNum; }
                    let n_tNum = Number(_tNum).toFixed();
                    let _offT = parseInt(_this.panelNumFontSize, 10);//文字向上偏移
                    ctx.fillStyle = _this.panelNumcolor;
                    ctx.font = _this.panelNumFontSize + ' ' + _this.panelNumFontFamily;
                    ctx.fillText(_this.valueType === '%' ? n_tNum : _tNum.toFixed(0), _this._panelX - ctx.measureText(n_tNum).width / 2, _this._panelY + _this._pancePointerLen + _offT / 2);
                    // ctx.fill();

                    // ctx.beginPath();//仪盘的数字的 外框
                    // ctx.lineWidth = 3;
                    // ctx.strokeStyle = _this.panelNumBorderColor;
                    // let _width = this._panelR / 2;//可附上设置的值
                    // let _height = this._panelR / 5;//可附上设置的值
                    // ctx.rect(_panelX - _width / 2, this._panelR * 2 - _height - this._panelR / 3, _width, _height);
                    // ctx.stroke();

                    ctx.beginPath();//仪盘的指针根圆点
                    ctx.fillStyle = _this._paneBorderColor;
                    ctx.arc(_panelX, _panelY, _this.dotR * _this.ratio / 2, 0, Math.PI * 2);
                    ctx.fill();

                    // ctx.beginPath();//指针的指针根圆点
                    // ctx.fillStyle = _this._panceKdColor;
                    // ctx.arc(_panelX, _panelY, 5, 0, Math.PI * 2);
                    // ctx.fill();


                    //直接采用不加更新功能的代码
                    let _p = _this.getPos(allDeg, _panelX, _panelY, _pancePointerLen); //获取坐标
                    ctx.beginPath();//仪盘的指针                    
                    ctx.strokeStyle = _this._pancePointerColor;
                    ctx.lineCap = "round";
                    ctx.lineWidth = _this._pancePointerWidth;

                    let _r = _this.dotR * _this.ratio / 2;
                    let _p2 = _this.getPos(allDeg - 80, _panelX, _panelY, _r); //获取坐标
                    let _p3 = _this.getPos(allDeg + 80, _panelX, _panelY, _r); //获取坐标

                    ctx.moveTo(_p.x, _p.y);
                    ctx.lineTo(_p2.x, _p2.y);
                    ctx.lineTo(_p3.x, _p3.y);
                    ctx.closePath();
                    ctx.fill();



                    { // eslint-disable-line no-lone-blocks  
                        //能更新的代码 
                        // if (opt.isUpdate) {//更新状态
                        //     let meetRotate = targetNum - opt.newDeg;
                        //     _this.PointerDeg = opt.newDeg;
                        //     if (meetRotate <= 0) {//往前走
                        //         if (targetNum >= Math.abs(opt.newDeg)) {
                        //             window.cancelAnimationFrame(_this.ani);
                        //             _this.isUpdate = false;
                        //             _this.isAniIng = false;
                        //             _this.upDateCallBack(_this, function () { })
                        //             return;
                        //         } else {
                        //             window.requestAnimationFrame(_this.draw({
                        //                 isUpdate: true,
                        //                 newDeg: opt.newDeg
                        //             }));//执行动画
                        //             _this.targetNum += _this.speed;
                        //         }

                        //         ctx.beginPath();//仪盘的指针
                        //         let _p = _this.getPos(_this.allDeg, _panelX, _panelY, _pancePointerLen); //获取坐标

                        //         ctx.strokeStyle = _this._pancePointerColor;
                        //         ctx.lineCap = "round";
                        //         ctx.lineWidth = _this._pancePointerWidth;
                        //         ctx.moveTo(_panelX, _panelY);
                        //         ctx.lineTo(_p.x, _p.y);
                        //         ctx.stroke();
                        //     } else {//往后走
                        //         _this.allDeg -= _this.speed;
                        //         _this.targetNum -= _this.speed;

                        //         ctx.beginPath();//仪盘的指针
                        //         let _p = _this.getPos(_this.allDeg, _panelX, _panelY, _pancePointerLen); //获取坐标
                        //         // let curDeg = ((_this.allDeg - (eDeg + 90)) % 360 / 360 * ( 360 / _this.viewDeg) * 100 ).toFixed(0);//现在所在的百分比
                        //         let _p2 = _this.getPos(allDeg - 80, _panelX, _panelY, _this.dotR * _this.ratio / 2 / 2); //获取坐标
                        //         let _p3 = _this.getPos(allDeg + 80, _panelX, _panelY, _this.dotR * _this.ratio / 2 / 2); //获取坐标

                        //         ctx.moveTo(_p.x, _p.y);
                        //         ctx.lineTo(_p2.x, _p2.y);
                        //         ctx.lineTo(_p3.x, _p3.y);
                        //         ctx.closePath();
                        //         ctx.fill();

                        //         if (_this.targetNum <= opt.newDeg) {//小于给定角度
                        //             _this.isUpdate = false;
                        //             _this.isAniIng = false;
                        //             window.cancelAnimationFrame(_this.ani);
                        //             _this.upDateCallBack(_this, function () { });
                        //             return;
                        //         } else {
                        //             window.requestAnimationFrame(_this.draw({
                        //                 isUpdate: true,
                        //                 newDeg: opt.newDeg
                        //             }));//执行动画
                        //         }
                        //     }

                        // } else {//非更新状态直接走这

                        //     let _p = _this.getPos(allDeg, _panelX, _panelY, _pancePointerLen); //获取坐标
                        //     ctx.beginPath();//仪盘的指针                    
                        //     ctx.strokeStyle = _this._pancePointerColor;
                        //     ctx.lineCap = "round";
                        //     ctx.lineWidth = _this._pancePointerWidth;

                        //     let _p2 = _this.getPos(allDeg - 80, _panelX, _panelY, _this.dotR * _this.ratio / 2 / 2); //获取坐标
                        //     let _p3 = _this.getPos(allDeg + 80, _panelX, _panelY, _this.dotR * _this.ratio / 2 / 2); //获取坐标

                        //     ctx.moveTo(_p.x, _p.y);
                        //     ctx.lineTo(_p2.x, _p2.y);
                        //     ctx.lineTo(_p3.x, _p3.y);
                        //     ctx.closePath();
                        //     ctx.fill();
                        // }

                    }



                    let _kNum = 0;//刻度起始值
                    let _cNum = _this.unitValueBig;//相邻两数差值
                    ctx.beginPath();
                    ctx.font = _this._panceKdFontSize + ' ' + _this._panceKdFontFamily;
                    ctx.fillStyle = _this._panceKdColor;
                    for (let i = _this.eDeg + 90; i <= 360 + _this.sDeg + 90; i += _cNum) {//刻度数字（大）  先画刻度是为了仪盘表外壳能盖住他
                        let _nsNum = (_kNum / 360 * (360 / _this.viewDeg) * 100).toFixed(0);
                        let _fWidth = ctx.measureText(_kNum).width;
                        let _tH = parseInt(_this._panceKdFontSize, 10);
                        let _posT = _this.getPos(i, _this._panelX, _this._panelY, _this._panelR - _this._panelMetreLineLenB - _fWidth / 3 - _this._panceBorderWidth); //获取坐标
                        ctx.fillText(_this.valueType === '%' ? _nsNum : _kNum, _posT.x - _fWidth / 2, _posT.y + _tH / 2);
                        _kNum += _cNum;
                    }
                }


                if ((_this.allDeg % 360).toFixed(0) === _this.sDeg + 90) { //到达仪盘终点
                    _this.isExtremity = true;
                    _this.isUpdate = false;
                    _this.targetNum = _this.sDeg + 90;
                    _this.isAniIng = false;
                    window.cancelAnimationFrame(_this.ani);
                    _this.reachExtremity(sDeg + 90);
                    return;
                } else {//继续动画
                    let curAng = Math.PI / 360 * infor.newDeg;//现在所在的百分比 

                    let _startAniFn = () => {
                        if (_this.offAni) {//是否开启动画
                            window.requestAnimationFrame(_this.draw({
                                isUpdate: false
                            }));//执行动画
                        }
                        let _n = Number(((infor.allAng - curAng) * _this.speed / 2).toFixed(2)) < 0.2 ? 0.5 : Number(((infor.allAng - curAng) * _this.speed / 1.2));
                        _this.allDeg += _n;
                    }
                    let stopAniFn = () => {
                        if (_this.isUpdate) { return }//更新状态就不执行到达初始化数值的函数
                        _this.reachTargetNum();
                        window.cancelAnimationFrame(_this.ani);
                        _this.isAniIng = false;
                        return;
                    }

                    if (_this.valueType === '%') {//到达规定的目标点
                        (_this.allDeg - (_this.eDeg + 90)) % 360 >= _this.viewDeg * (_this.targetNum / 100) ? stopAniFn() : _startAniFn()
                    } else {//到达规定的目标点
                        (_this.allDeg - (_this.eDeg + 90)) % 360 >= _this.targetNum ? stopAniFn() : _startAniFn()
                    }

                };
            }
            return drawFn;
        }

        //不重新绘制的canvas ------
        ctx.fillStyle = _this._paneBorderColor;
        for (let i = _this.eDeg + 90; i < 360 + _this.sDeg + 90; i += _this.unitValueSmart) {//刻度（小）  先画刻度是为了仪盘表外壳能盖住他
            ctx.beginPath();
            let _pos = _this.getPos(i, _this._panelX, _this._panelY, _this._panelR); //获取开始坐标
            let _ePos = _this.getPos(i, _this._panelX, _this._panelY, _this._panelR - ctx.lineWidth - _this._panelMetreLineLenS); //获取结束坐标
            ctx.strokeStyle = _this._panelMetreLineColorS;
            ctx.lineCap = 'round';
            ctx.lineWidth = _this._panelMetreLineWidthS;
            ctx.moveTo(_pos.x, _pos.y);
            ctx.lineTo(_ePos.x, _ePos.y);
            ctx.stroke();
        }

        let _KdxNum = 0; // eslint-disable-line no-unused-vars
        ctx.fillStyle = _this._panceKdColor;
        for (let i = _this.eDeg + 90; i <= 360 + _this.sDeg + 90; i += _this.unitValueBig) {//刻度（大）  先画刻度是为了仪盘表外壳能盖住他
            ctx.beginPath();
            let _pos = _this.getPos(i, _this._panelX, _this._panelY, _this._panelR); //获取开始坐标
            let _ePos = _this.getPos(i, _this._panelX, _this._panelY, _this._panelR - ctx.lineWidth - _this._panelMetreLineLenB); //获取结束坐标
            ctx.strokeStyle = _this._panelMetreLineColorB;
            ctx.lineCap = 'round';
            ctx.lineWidth = _this._panelMetreLineWidthB;
            ctx.moveTo(_pos.x, _pos.y);
            ctx.lineTo(_ePos.x, _ePos.y);
            ctx.stroke();
            _KdxNum += 10;// eslint-disable-line no-unused-vars
        }



        var grd = ctx.createLinearGradient(0, 0, _this.canvasWin, 0);
        grd.addColorStop(0.25, "#FF3F01");
        grd.addColorStop(0.25, "#FFAF02");
        grd.addColorStop(0.75, "#FFAF02");
        grd.addColorStop(0.75, "#0099ff");

        ctx.beginPath();//背景 仪盘外框
        ctx.shadowBlur = "40";
        ctx.shadowColor = "rgba(52, 52, 52, 0.4)";
        ctx.lineWidth = _this._panceBorderWidth;
        ctx.strokeStyle = grd;
        ctx.lineCap = "round";
        ctx.arc(_this._panelX, _this._panelY, _this._panelR, Math.PI / 180 * _this.sDeg, Math.PI / 180 * _this.eDeg, true);
        ctx.stroke();
        ctx.shadowBlur = '0';

        ctx.beginPath();
        ctx.font = this.txtFontSize + ' ' + this.txtFontFamily;
        ctx.fillText(_this.txt, _this._panelX - ctx.measureText(_this.txt).width / 2, _this._panelX * 2 - parseInt(_this.txtFontSize, 10) + _this._panceBorderWidth);


        this.ani = requestAnimationFrame(this.draw({
            isUpdate: false
        }));//执行动画

    }

    setAttr(attrObjs) {//传入对象
        let _this = this;
        for (var key in attrObjs) {
            _this[key] = attrObjs[key]
        }
    }

    getPos(deg, x, y, r) {//获取 圆边上的 某个坐标 ( 角度(360), x(圆心x), y(圆心y), r(半径))
        var hudu = (Math.PI / 180) * deg;   //  根据角度算取弧度
        var _x = x + Math.sin(hudu) * r;
        var _y = y - Math.cos(hudu) * r;
        return { x: _x, y: _y };
    }

    canvasRem(rem) {//1rem = window / 10  返回一个canvas用的单位
        if (!rem) { return };
        let _num = parseInt(rem, 10);
        let winWinth = window.innerWidth;
        return winWinth / 10 * _num;
    }

    upDate(deg) {//更新数据
        if (deg !== this.targetNum) {
            this.isUpdate = true;//更新状态开启
            // eslint-disable-line no-unused-vars
            // let meetRotate = (this.targetNum - deg); //需要转动角度 整数倒着转 负数反之
            requestAnimationFrame(this.draw({
                isUpdate: true,
                newDeg: deg
            }));//执行动画
        }
    }

    upDateCallBack(me, cb) {//更新完数据后的回调
        this.upDateFn(me);
    }

    reachTargetNum() {//指针到达目标数字（目标角度）
        if (this.isUpdate) { return; };//更新过程不管目标
        // this.log(`到达初始化目标点：${this.targetNum}`, 'yellow');
    }

    reachExtremity(deg) {//指针到达极限  就是最后
        // clearInterval(tim);
        // this.log(`到达极限了：到达[极限角度：${deg}],[可视角度：${(this.allDeg - (this.eDeg + 90)) % 360}]`, 'red', '14px');
    }

}
export default Chart;
