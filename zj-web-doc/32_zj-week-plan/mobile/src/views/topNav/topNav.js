import React, { Component } from 'react';
import './topNav.less';
import { Badge } from 'antd-mobile';
class TopNav extends Component {
    constructor(props) {
        super(props);
        this.state = {
            index: this.props.index
        }
        this.initleft = 0;
        this.end = 0;
        this.dWidth = 0;
    }
    setInitLeft = (left) => {
        this.initleft = left
    }
    onClick = (index, left) => {
        this._left = 100 - left;
        if (this._left > 0) {
            this.end = 0;
        } else if (this._left < this.dWidth) {
            this.end = this.dWidth;
        } else {
            this.end = this._left
        }
        this.refs.list.style.transform = "translateX(" + (this.end) + "px)"
        this.refs.list.style['-webkit-transform'] = "translateX(" + (this.end) + "px)"
        this.setState({ index })
        this.props.onChange(index)
    }
    ontouchstart = (e) => {
        this.refs.list.className = "topnav_list";
        this.start = e.targetTouches[0].clientX;
    }
    ontouchmove = (e) => {
        e.preventDefault();
        this.refs.lisContain.className = "topnav_list_contain onscroll"
        this._left = e.targetTouches[0].clientX - this.start + this.end;
        if (this._left > 0 || this._left < this.dWidth) {
            const than = this._left - this.end
            this._left = this.width * Math.sin(than * Math.PI / (2 * this.width)) / 2 + this.end
        }
        this.refs.list.style.transform = "translateX(" + (this._left) + "px)"
        this.refs.list.style['-webkit-transform'] = "translateX(" + (this._left) + "px)"
    }
    ontouchend = (e) => {
        this.refs.list.className = "topnav_list ani";
        this.refs.lisContain.className = "topnav_list_contain"
        this.end = Math.max(Math.min(this._left, 0), this.dWidth);
        this.refs.list.style.transform = "translateX(" + (this.end) + "px)"
        this.refs.list.style['-webkit-transform'] = "translateX(" + (this.end) + "px)"
    }
    componentDidMount() {
        this.refs.list.addEventListener('touchstart', this.ontouchstart)
        this.refs.list.addEventListener('touchmove', this.ontouchmove)
        this.refs.list.addEventListener('touchend', this.ontouchend)
    }
    componentDidUpdate() {
        this.pWidth = this.refs.listWrap.offsetWidth
        this.width = Math.max(this.refs.list.offsetWidth, this.pWidth)
        this.dWidth = this.pWidth - this.width;
        this._left = 100 - this.initleft;
        if (this._left > 0) {
            this.end = 0;
        } else if (this._left < this.dWidth) {
            this.end = this.dWidth;
        } else {
            this.end = this._left
        }
        this.refs.list.style.transform = "translateX(" + (this.end) + "px)"
        this.refs.list.style['-webkit-transform'] = "translateX(" + (this.end) + "px)"
    }
    componentWillUnmount() {
        this.refs.list.removeEventListener('touchstart', this.ontouchstart)
        this.refs.list.removeEventListener('touchmove', this.ontouchmove)
        this.refs.list.removeEventListener('touchend', this.ontouchend)
    }
    render() {
        return (
            <div className="main_nav_topnav_wrap">
                <div ref="lisContain" className="topnav_list_contain">
                    <div ref="listWrap" className="topnav_list_scroll_wrap">
                        <div ref="list" className="topnav_list">
                            {
                                this.props.arr.map((v, i) => {
                                    return (<TopNavItem dot={v[this.props.dot]} setInitLeft={this.setInitLeft} key={i} name={v[this.props.valueName]} onClick={(l) => { this.onClick(i, l) }} cur={(i === this.props.index)} />)
                                })
                            }
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
class TopNavItem extends Component {
    render() {
        return (
            <div ref={(e) => { if (this.props.cur && e) { this.props.setInitLeft(e.offsetLeft) } }} onClick={(e) => { this.props.onClick(e.currentTarget.offsetLeft) }} className={this.props.cur ? "topnav_item current" : "topnav_item"}>
                <div className="topnav_item_box">
                    {
                        (!this.props.dot || this.props.dot === 0 || this.props.dot === '0') ? <span className="topnav_item_text">{this.props.name}</span> : <Badge dot>
                            <span className="topnav_item_text">{this.props.name}</span>
                        </Badge>
                    }
                </div>
            </div>
        )
    }
}

export default TopNav;