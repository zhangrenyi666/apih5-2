import React,{ Component } from 'react';
import s from './style.less'
import config from './func/config'
import $ from 'jquery'
// const toGeoJSON = require('./lib/toGeoJSON')

class index extends Component {
    state = {
        useMap: this.props.useMap,
        mapSrc: this.props.src,
        kmlSrc: this.props.kmlSrc,
        info: ''
    }

    ltime = 0;
    dtime = 0;
    info = '';

    async componentDidMount() {
        await this.initMap();
    }

    async initMap() {
        await this.insetUrl();
        await this.createMap();
    }

    async createMap() {
        window.onLoad = () => {
            const { AMap } = window;
            this.map = new AMap.Map('container',{
                ...config.get('map')
            });
            this.map.addControl(new AMap.ControlBar({
                showZoomBar: false,
                showControlButton: true,
                position: {
                    right: '10px',
                    top: '10px'
                }
            }))
            //加载kml文件
            this.loadKml()
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

    setInfo(text) {
        let { info = "" } = this.state;
        this.info += text;
        this.setState({ info: this.info.length < 500 ? <>{info}<p>{text}</p></> : text });
    }

    loadKml() {
        const { kmlSrc } = this.state;
        this.setInfo("正在加载kml数据...");

        $.ajax(kmlSrc).done((xml) => {
            this.setInfo("kml数据加载完毕。");

            let data = window.toGeoJSON.kml(xml);

            this.setInfo("正在渲染kml数据...");
            this.drawKml(data);

            window.ltimer = setInterval(() => {
                this.ltime += 1;
            },1000);
            window.dtimer = setInterval(() => {
                this.dtime += 1;
            },1000);
        });
    }

    async drawKml(data) {
        // console.log(data); 
        this.object3Dlayer = new window.AMap.Object3DLayer({ zIndex: 10 });
        this.map.add(this.object3Dlayer);

        const features = data.features;
        for (const item of features) {
            var type = item.type;
            var geometry = item.geometry;
            var properties = item.properties;
            var coordinates = item.coordinates;
            await this.forGeoFn(type,geometry,coordinates,properties,item);
        }
    }

    async forGeoFn(type,geometry,coordinates,properties,obj) {
        switch (type) {
            case "Feature":
                //线条样式只能通过父级传入
                await this.forGeoFn(geometry.type,geometry.geometry,geometry.coordinates,properties,obj)
                break;
            case "LineString":
                this.setInfo("路线渲染开始...");
                await this.drawLine(coordinates,properties);
                clearInterval(window.ltimer)
                this.setInfo("路线渲染结束");
                this.setInfo("");
                break;
            case "Point":
                // this.drawDot(coordinates,properties)
                break;
            case "GeometryCollection":
                this.setInfo("多边形图形渲染开始...");
                await this.drawMath(geometry,properties,obj);
                clearInterval(window.dtimer)
                this.setInfo("多边形图形渲染结束");
                this.setInfo("");
                break;
            default:
                console.log('其他',obj)
                break;
        }
    }

    async drawLine(coordinates,properties) {
        //去掉z坐标
        let _coordinates = coordinates.map(item => {
            return item.splice(0,2);
        })
        this.setInfo(`[线条渲染]需要转换坐标 ${_coordinates.length} 个`);
        let path2s = await this.convertFrom(_coordinates);

        let Line3D = new window.AMap.Object3D.Line();
        //线条
        Line3D.transparent = true;
        let __geometry = Line3D.geometry;

        this.setInfo("[线条渲染]路线绘制中...");
        for (let i = 0; i < path2s.length - 1; i++) {
            let path2 = path2s[i];
            let path1 = path2s[i + 1];
            let rgba = window.AMap.Util.color2RgbaArray(properties.stroke);
            let origin = this.map.lngLatToGeodeticCoord(path2);
            let origin1 = this.map.lngLatToGeodeticCoord(path1);
            __geometry.vertices.push(origin.x,origin.y,0);
            __geometry.vertices.push(origin1.x,origin1.y,0);
            __geometry.vertexColors.push(rgba[0],rgba[1],rgba[2],rgba[3]);
            __geometry.vertexColors.push(rgba[0],rgba[1],rgba[2],rgba[3]);
        }
        this.object3Dlayer.add(Line3D);
        this.setInfo("");
    }

    async drawMath(geometry,properties,obj) {
        let geometries = obj.geometry.geometries;
        if (!geometries || !geometries.length) return;

        for (const item of geometries) {
            let { type,coordinates } = item;
            if (type === "LineString") {
                //多边形的每条边
                let _coordinates = coordinates.map(item => item.splice(0,2));
                if (_coordinates.length) {
                    this.setInfo(`[多边形渲染] 需要转换坐标 ${geometries.length} 个`);
                    let path = await this.convertFrom(_coordinates);
                    let Line3D = new window.AMap.Object3D.Line();
                    //线条
                    Line3D.transparent = true;
                    let __geometry = Line3D.geometry;

                    for (let i = 0; i < path.length; i++) {
                        let pathItem = path[i];
                        let rgba = window.AMap.Util.color2RgbaArray(properties.stroke);
                        let origin = this.map.lngLatToGeodeticCoord(pathItem);
                        __geometry.vertices.push(origin.x,origin.y,0);
                        __geometry.vertexColors.push(rgba[0],rgba[1],rgba[2],rgba[3]);
                        if (path.length > 1 && i < path.length - 1) {
                            let pathItem2 = path[i + 1];
                            if (pathItem2) {
                                let origin2 = this.map.lngLatToGeodeticCoord(pathItem2);
                                __geometry.vertices.push(origin2.x,origin2.y,0);
                            }
                        }

                    }
                    this.object3Dlayer.add(Line3D);
                }
            } else {
                console.error('[多边形渲染]不支持类型',item.type)
            }
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
        const { info } = this.state;
        return (
            <div className={s.root}>
                {
                    info ? <div className={s.info}>
                        <div style={{ color: 'red' }}>线条耗时：{this.ltime}s</div>
                        <div style={{ color: 'red' }}>多边形耗时：{this.dtime}s</div>
                        {info}
                    </div> : null
                }
                <div className={s.container} id="container"></div>
            </div>
        );
    }
}

export default index;