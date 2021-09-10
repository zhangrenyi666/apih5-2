import React, { Component } from 'react';
import './ImgPreview.css';

class ImgPreview extends Component {
    constructor(props) {
        super(props);
        this.src = window.decodeURIComponent(this.props.match.params.src);
        this.viewportContent = document.getElementsByName("viewport")[0].getAttribute("content")
    }
    componentDidMount() {
        document.getElementsByName("viewport")[0].setAttribute("content", "width=device-width,initial-scale=1")
    }
    componentWillUnmount() {
        document.getElementsByName("viewport")[0].setAttribute("content", this.viewportContent)
    }
    goBack(){
        this.props.history.go(-1);
    }
    render() {
        return (
            <div className="page flexBox flexCenter">
                <img alt="img" className="ImgPreview"  src={this.src} />
                
                <div className="goBack">
                    <span onClick={this.goBack.bind(this)}>返回</span>
                </div>
            </div>
        )

    }
}
export default ImgPreview;