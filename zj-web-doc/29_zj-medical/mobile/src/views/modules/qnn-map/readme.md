qnn-map说明

调用

    <QnnMap
        //地图地址
        src={`//webapi.amap.com/maps?v=1.4.14&key=8250dc9402ff7d7f96bd75ac6835ae51&plugin=AMap.ControlBar,Map3D`}

        // kml数据文件  可以是相对位置或者绝对位置
        kmlSrc={require('./data.kml')}

        onload={this.mapOnload}

        initMapConfig={{...初始化地图的参数配置}}
    />

    //map对象问地址实例化的对象 
    //AMap对象为高德地图提供的全局对象
    mapOnload = ({ map,AMap }) => {
        // 创建一个 Marker 实例：
        let marker = new AMap.Marker({
            position: new AMap.LngLat(108.505758,28.563917),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
            title: '标注点1'
        });
        map.add(marker);
    }
