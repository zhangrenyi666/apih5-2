
//多边形绘制
const drawMath = async function (obj) {
    let geometries = obj.geometry.geometries;
    if (!geometries || !geometries.length) return;
    // console.log(obj);
    //一个多边形  遍历每条边
    for (const item of geometries) {
        let { type,coordinates } = item;

        //每条边
        if (type === "LineString") {
            //多边形的每条边
            let _coordinates = coordinates.map(item => item.splice(0,2));
            let rgba = window.AMap.Util.color2RgbaArray(obj.properties.stroke);
            let rgba2 = window.AMap.Util.color2RgbaArray(obj.properties.stroke);

            if (_coordinates.length > 2) {
                // 多个坐标的线段
                let path = await this.convertFrom(_coordinates);
                let Line3D = new window.AMap.Object3D.Line();
                //线条
                Line3D.transparent = true;
                let __geometry = Line3D.geometry;

                for (let i = 2; i < (path.length - 1); i++) {
                    let pathItem = path[i];
                    let pathItem2 = path[i - 1];
                    let origin = this.map.lngLatToGeodeticCoord(pathItem);
                    let origin2 = this.map.lngLatToGeodeticCoord(pathItem2);
                    __geometry.vertices.push(origin2.x,origin2.y,0);
                    __geometry.vertices.push(origin.x,origin.y,0);
                    __geometry.vertexColors.push(rgba[0],rgba[1],rgba[2],rgba[3]);
                    __geometry.vertexColors.push(rgba2[0],rgba2[1],rgba2[2],rgba2[3]);
                }
                this.object3Dlayer.add(Line3D);
                this.mathNumberByRenderEd += _coordinates.length;
            } else if (_coordinates.length === 2) { 
                //只有两个坐标的线段 
                this.twoDotArr = this.twoDotArr.concat(_coordinates);  
            }

            //渲染只有两个点的线段
            if (this.twoDotArr.length === (this.twoDotArrNumber - _coordinates.length)) { 
                let _num = this.twoDotArr.length;
                let _index = 0;
                do { 
                    let _path = this.twoDotArr.splice(0,1200); 
                    await this.convertFrom(_path).then((path) => {
                        if (!path || !path.length) {
                            return;
                        }
                        let Line3D = new window.AMap.Object3D.Line();
                        //线条
                        Line3D.transparent = true;
                        let __geometry = Line3D.geometry; 

                        for (let i = 1; i < (path.length - 2); i += 2) {
                            let pathItem = path[i];
                            let pathItem2 = path[i - 1]; 
                            let origin = this.map.lngLatToGeodeticCoord(pathItem);
                            let origin2 = this.map.lngLatToGeodeticCoord(pathItem2);
                            __geometry.vertices.push(origin2.x,origin2.y,0);
                            __geometry.vertices.push(origin.x,origin.y,0);
                            __geometry.vertexColors.push(rgba[0],rgba[1],rgba[2],rgba[3]);
                            __geometry.vertexColors.push(rgba2[0],rgba2[1],rgba2[2],rgba2[3]);
                        } 
                        this.object3Dlayer.add(Line3D);

                        this.twoDotArrNumberByRenderEd += 1200;
                        this.setState({ mathNumberByRenderEd: this.mathNumberByRenderEd })
                    }); 

                    _index++;
                } while ((_index * 1200) < _num); 
            }
            
            this.setState({ mathNumberByRenderEd: this.mathNumberByRenderEd })
        } else {
            console.error('[多边形渲染]不支持类型',item.type)
        }
    }
}

export default drawMath;