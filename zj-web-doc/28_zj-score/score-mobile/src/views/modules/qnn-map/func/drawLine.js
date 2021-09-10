const drawLine = async function (coordinates,properties) {
    //去掉z坐标
    let _coordinates = coordinates.map(item => {
        return item.splice(0,2);
    })
    let path2s = await this.convertFrom(_coordinates);

    let Line3D = new window.AMap.Object3D.Line();
    //线条
    Line3D.transparent = true;
    let __geometry = Line3D.geometry;

    let _newPath = [];
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

        _newPath.push(origin);
    } 
    this.object3Dlayer.add(Line3D);

    this.lineNumberByRenderEd += 1;
    this.setState({ lineNumberByRenderEd: this.lineNumberByRenderEd })
}

export default drawLine;