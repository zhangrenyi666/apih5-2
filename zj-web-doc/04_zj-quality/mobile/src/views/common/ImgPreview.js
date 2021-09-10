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
    render() {
        return (
            <div className="page flexBox flexCenter">
                <img alt="img" className="ImgPreview"  src={this.src} />
            </div>
        )

    }
}
export default ImgPreview;