
const drawKml = async function (data) {
    this.object3Dlayer = new window.AMap.Object3DLayer({ zIndex: 10 });
    this.map.add(this.object3Dlayer);

    const features = data.features;

    //统计数据
    this.featuresNumber = features.length;

    //点数量
    this.dotNumber = 0; 

    //线条数量 已渲染的线条数量
    this.lineNumber = 0;
    this.lineNumberByRenderEd = 0; 

    //只有两个坐标的点的线段
    this.twoDotArr = [];

    //只有两个坐标点的线段总数
    this.twoDotArrNumber = 0;
    this.twoDotArrNumberByRenderEd = 0;


    //多边形数量 已渲染的多边形数量
    this.mathNumber = 0;
    this.mathNumberByRenderEd = 0;

    let getNumber = () => {
        for (let i = 0; i < features.length; i++) {
            let _item = features[i];
            let { geometry } = _item;
            if (geometry.type === "Feature") {
                getNumber(_item.features);
                this.featuresNumber += 1;
            } else if (geometry.type === "LineString") {
                this.lineNumber += 1;
            } else if (geometry.type === "GeometryCollection") {
                //计算所有多边形线条
                for (let j = 0; j < geometry.geometries.length; j++) {

                    //计算大于两个点的坐标点
                    if(geometry.geometries[j].coordinates.length > 2){
                        this.mathNumber += geometry.geometries[j].coordinates.length; 
                    }
                    
                    //计算两个点的 
                    if (geometry.geometries[j].coordinates.length === 2) {
                        this.twoDotArrNumber += 2;
                    }
                }
            } else if (geometry.type === "Point") {
                this.dotNumber += 1;
            }
        }
    }

    //统计数字
    getNumber(features);

    // console.log('总数据块：',this.featuresNumber)
    // console.log('点数量：',this.dotNumber)
    // console.log('线条数量：',this.lineNumber)
    // console.log('多边形边数量：',this.mathNumber)
    // console.log('多边形边数量（只有两个坐标点的）：',this.twoDotArrNumber)


    for (const item of features) {
        var type = item.type;
        var geometry = item.geometry;
        var properties = item.properties;
        var coordinates = item.coordinates;
        this.forGeoFn(type,geometry,coordinates,properties,item);
    }
}

export default drawKml;