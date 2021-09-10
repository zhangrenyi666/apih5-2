import React, { Component } from 'react';
import { ImagePicker } from 'antd-mobile';
class Image extends Component {
    constructor(props) {
        super(props);
        this.state = {
            files: [],//问题图片组
        }
    }
    componentDidMount() {
        const { imageInfoList } = this.props;
        this.updateImage(imageInfoList)
    }
    componentWillReceiveProps(nextProps) {
        const { imageInfoList } = nextProps;
        this.updateImage(imageInfoList)
    }
    updateImage = (imageInfoList) => {
        this.setState({ files: imageInfoList })
    }
    onImageClick = (index, files) => {//查看图片
        var fileImg = [];
        const { myPublic: { wx } } = this.props
        for (const item of files) {
            fileImg.push(item.url);
        }
        wx.previewImage({
            current: fileImg[index],
            urls: fileImg
        });
    }
    render() {
        const files = this.state.files.map((v, i) => {
            v["url"] = v.infoAddress
            return v
        })
        return (
            <ImagePicker className="noremove"
                files={files}
                onImageClick={this.onImageClick}
                selectable={false}
            />
        )
    }
}

export default Image;