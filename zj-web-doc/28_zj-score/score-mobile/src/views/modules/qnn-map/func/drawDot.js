//画点
const drawDot = async function (coordinates,properties) {
    //去掉z坐标
    console.log(coordinates);
    let path2s = await this.convertFrom([coordinates[0],coordinates[1]]);

    if (!this.dots) {
        this.dots = []
    }
    //将解析好的点 放入到点的数组中
    this.dots.push(path2s);
    // console.log(1)
    //解析完后统一渲染
    if (this.dots.length > 10) {
        // console.log(this.dots); 
        let points3D = new window.AMap.Object3D.RoundPoints();
        points3D.transparent = true;
        var pointsGeo = points3D.geometry;

        for (let i = 0; i < this.dots.length; i++) {
            // console.log(this.dots[i])
            // let origin = this.map.lngLatToGeodeticCoord(this.dots[i]);
            // console.log(origin)
            pointsGeo.vertices.push(this.dots[i].x,this.dots[i].y,0); // 尾部小点
            // pointsGeo.pointSizes.push(5);
            // pointsGeo.vertexColors.push(0,0,1,1);
        }
        this.object3Dlayer.add(points3D);
        this.dots = [];
    } 
} 

export default drawDot;