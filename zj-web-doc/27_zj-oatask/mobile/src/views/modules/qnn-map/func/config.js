export default {
    get(name) {
        switch (name) {
            case "map":
                return {
                    resizeEnable: true,
                    rotateEnable: true,
                    pitchEnable: true,
                    zoom: 12,
                    pitch: 55,
                    // rotation: -15,
                    viewMode: '3D',//开启3D视图,默认为关闭
                    buildingAnimation: true,//楼块出现是否带动画

                    expandZoomRange: true,
                    zooms: [3,18],
                    center: [108.56267732,28.27942411],
                    // features: ['bg','road','point'],
                    // mapStyle: 'amap://styles/light',
                    // layers: [
                    //     new window.AMap.TileLayer.Satellite(),

                    //     // 楼块图层
                    //     new window.AMap.Buildings({
                    //         zooms: [16,18],
                    //         zIndex: 10,
                    //         heightFactor: 2//2倍于默认高度，3D下有效
                    //     })
                    // ],
                }
            default:
                return {}
        }
    },
    set(name,config) {
        this[name] = config;
    }
}