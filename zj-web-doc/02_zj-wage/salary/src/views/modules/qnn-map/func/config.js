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
                    zooms: [3,20],
                    center: [108.56267732,28.27942411],
                }
            default:
                return {}
        }
    },
    set(name, config){
        this[name] = config;
    }
}