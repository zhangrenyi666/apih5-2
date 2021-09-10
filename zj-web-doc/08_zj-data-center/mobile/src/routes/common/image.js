import React, { Component } from 'react';
import { ImagePicker } from 'antd-mobile';
import { createHashHistory } from 'history'
import { getWxJsSdk } from '../../main';
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
        const { notWx } = this.props
        const history = createHashHistory()
        var fileImg = [];
        if (notWx) {
            for (const item of files) {
                fileImg.push(window.encodeURIComponent(item.url));
            }
            history.push(`/imgPreview/${fileImg[index]}`)
        } else {
            for (const item of files) {
                fileImg.push(item.url);
            }
            getWxJsSdk(({ wx, ready, error }) => {
                if (ready && !error) {
                    wx.previewImage({
                        current: fileImg[index],
                        urls: fileImg
                    });
                }
            })
        }
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