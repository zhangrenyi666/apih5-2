import React, { Component } from "react";
import { FullscreenOutlined, FullscreenExitOutlined, CloseOutlined } from '@ant-design/icons';
class Maximize extends Component {
    constructor(props) {
        super(props);
        this.state = {
            drawerDefaultWidth: 70,
            bigOrsmallWidtnStatus: false
        }
    }
    render() {
        return (
            <div>
                <div className="ant-col" style={{ float: 'left' }}>{this.props.title}</div>
                <span title="全屏切换" style={{ float: 'right', marginRight: '52px', cursor: 'pointer' }} onClick={(e) => {
                    e.preventDefault();
                    this.props.props.changeMaxWidthFunc(!this.state.bigOrsmallWidtnStatus ? 'max' : 'min', this.props.minWidth)
                    this.setState({
                        bigOrsmallWidtnStatus: !this.state.bigOrsmallWidtnStatus
                    })
                }}>
                    {this.state.bigOrsmallWidtnStatus ? <FullscreenExitOutlined style={{ fontSize: '20px' }} /> : <FullscreenOutlined style={{ fontSize: '20px' }} />}
                </span>
            </div>
        )
    }
}
export default Maximize;