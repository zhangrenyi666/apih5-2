import React,{ Component } from 'react';
import s from './style.less'
import { drawLine,config,drawMath,drawDot,drawKml } from "./func"
import $ from 'jquery' 

class index extends Component {

    constructor(...args) {
        super(...args);

        this.state = {
            useMap: this.props.useMap,
            mapSrc: this.props.src,
            kmlSrc: this.props.kmlSrc,

            //是否正在加载kml文件
            kmlLoading: true
        }

        //线条 多边形  点 绘制
        this.drawLine = drawLine.bind(this);
        this.drawMath = drawMath.bind(this);
        this.drawDot = drawDot.bind(this);

        //kml文件渲染
        this.drawKml = drawKml.bind(this);

    }

    async componentDidMount() {
        this.initMap();
    }

    async initMap() {
        this.insetUrl();
        this.createMap();
    }

    async createMap() {
        window.onLoad = () => {
            const { AMap } = window;
            const { initMapConfig={} } = this.props;
            this.map = new AMap.Map('qnn-map-container',{
                ...config.get('map'),
                ...initMapConfig
            });
            this.map.addControl(new AMap.ControlBar({
                showZoomBar: false,
                showControlButton: true,
                position: {
                    right: '10px',
                    top: '10px'
                }
            }));

            //加载kml文件
            this.loadKml();

            //执行onload事件
            if (this.props.onload) {
                this.props.onload({
                    AMap,
                    map: this.map
                });
            }
        }
    }

    async insetUrl() {
        //异步加载
        const { mapSrc } = this.state;
        var url = `${mapSrc}&callback=onLoad`;
        var jsapi = document.createElement('script');
        jsapi.charset = 'utf-8';
        jsapi.src = url;
        document.body.appendChild(jsapi);
    }

    loadKml() {
        const { kmlSrc } = this.state;
        this.setState({ kmlLoading: true });
        $.ajax(kmlSrc).done((xml) => {
            this.setState({ kmlLoading: false })
            let data = window.toGeoJSON.kml(xml);
            this.drawKml(data);
        });
    }

    async forGeoFn(type,geometry,coordinates,properties,obj) {
        switch (type) {
            case "Feature":
                //线条样式只能通过父级传入
                this.forGeoFn(geometry.type,geometry.geometry,geometry.coordinates,properties,obj)
                break;
            case "LineString":
                this.drawLine(coordinates,properties);
                break;
            case "Point":
                // this.drawDot(coordinates,properties)
                break;
            case "GeometryCollection": 
                this.drawMath(obj);
                break;
            default:
                console.log('其他',obj)
                break;
        }
    }


    //按照高德convertFrom方法传入参数
    convertFrom(coordinates,type = "gps") {
        return new Promise((resolve) => {
            window.AMap.convertFrom(coordinates,type,(status,result) => {
                if (result.info === 'ok') {
                    let path2s = result.locations;
                    resolve(path2s)
                } else {
                    resolve([])
                    console.error('线条解析出错',result)
                }
            });
        })
    }

    render() {
        const { kmlLoading } = this.state;
        return (
            <div className={s.root}>
                <div className={s.info}>
                    {/* 减10是因为可能会因为网络不稳造成某几个请求失败导致 */}
                    <div style={{ color: 'red',display: (this.lineNumberByRenderEd >= (this.lineNumber - 10)) ? "none" : null }}>正常路线渲染中：{this.lineNumberByRenderEd} / {this.lineNumber}</div>
                    <div style={{ color: 'red',display: (this.mathNumberByRenderEd >= (this.mathNumber - 10)) ? "none" : null }}>大于两个坐标的点多边形线段渲染中：{this.mathNumberByRenderEd} / {this.mathNumber}</div>
                    <div style={{ color: 'red',display: (this.twoDotArrNumberByRenderEd >= (this.twoDotArrNumber - 10)) ? "none" : null }}>只有两个坐标点的点多边形线段渲染中：{this.twoDotArrNumberByRenderEd} / {this.twoDotArrNumber}</div>
                </div>
                {
                    kmlLoading ? <div className={s.kmlLoading}>KML文件加载中...</div> : null
                }
                <div className={s.container} id="qnn-map-container"></div>
            </div>
        );
    }
}

export default index;