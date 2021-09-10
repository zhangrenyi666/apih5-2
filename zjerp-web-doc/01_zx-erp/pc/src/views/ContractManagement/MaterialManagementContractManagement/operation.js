import React, { Component } from "react";
import { Timeline } from 'antd';
import moment from 'moment';
class operation extends Component {
    constructor(props) {
        super(props);
        this.state = {
            ContentH: '',
            data: props.flowData?.flowHistoryList || [],
            flowWebUrl: props.flowData?.flowWebUrl || ''
        }
    }
    setH = () => {
        this.setState({
            ContentH: window.innerHeight - 44 - 16 * 2 - 24 - 32 - 32 + 48
        })
    }
    componentDidMount() {
        this.setH();
        window.addEventListener('resize', this.setH, false);
    }
    componentWillUnmount() {
        window.removeEventListener('resize', this.setH)
    }
    render() {
        const { data, ContentH, flowWebUrl } = this.state;
        return (
            <div style={{ padding: '10px' }}>
                <div>
                    {data.length ? <div style={{ height: ContentH * 0.5, overflow: 'auto', padding: '20px' }}>
                        <Timeline>
                            {
                                data.map((item, index) => {
                                    return (
                                        <Timeline.Item key={index}>
                                            <div><span>{item.nodeName}：{item.realName}</span> <span>{item.actionTime && moment(Number(item.actionTime)).format('YYYY-MM-DD HH:mm:ss')}</span></div>
                                            <div>{item.comments}</div>
                                        </Timeline.Item>
                                    )
                                })
                            }
                        </Timeline>
                    </div> : null}
                    {
                        flowWebUrl ? <div>
                            <iframe width="100%" title="navigation" height={ContentH * 0.5} frameBorder={0} src={flowWebUrl}></iframe>
                        </div> : null
                    }
                </div>
            </div>
        );
    }
}
export default operation;
