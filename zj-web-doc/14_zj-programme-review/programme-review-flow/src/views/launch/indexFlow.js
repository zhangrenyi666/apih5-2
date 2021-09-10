import React, { Component } from "react";
import { Timeline } from 'antd';
import moment from 'moment';
import { message as Msg } from 'antd';
class operation extends Component {
    constructor(props) {
        super(props);
        this.state = {
            ContentH: '',
            title: props.rowData && props.rowData.schemeName ? props.rowData.schemeName : '',
            workId: props.rowData && props.rowData.workId ? props.rowData.workId : '',
            trackId: props.rowData && props.rowData.trackId ? props.rowData.trackId : '',
            data: [],
            flowWebUrl: ''
        }
    }
    setH = () => {
        this.setState({
            ContentH: window.innerHeight - 44 - 16 * 2 - 24 - 32 - 32 + 48
        })
    }
    componentDidMount() {
        const { title, workId, trackId } = this.state;
        if (workId) {
            this.props.myFetch('openFlowByReport', { apiName:'getZjPrProgrammeLaunchFlowDetailByWorkId',apiType: 'POST', flowId: 'zjProLaunchFlowByTwo', title: title, workId: workId, trackId:trackId }).then(({ success, message, data }) => {
                if (success) {
                    this.setState({
                        data: data.flowHistoryList,
                        flowWebUrl: data.flowWebUrl
                    });
                } else {
                    Msg.error(message);
                }
            });
        }
        this.setH();
        window.addEventListener('resize', this.setH, false);
    }
    componentWillUnmount() {
        window.removeEventListener('resize', this.setH)
    }
    render() {
        const { data, ContentH, flowWebUrl, workId } = this.state;
        return (
            <div style={{ padding: '10px' }}>
                <div>
                    {data.length ? <div style={{ height: ContentH * 0.5, overflow: 'auto', padding: '20px' }}>
                        <Timeline>
                            {
                                data.map((item, index) => {
                                    return (
                                        <Timeline.Item key={index}>
                                            <div><span>{item.nodeName}：{item.realName}</span> <span>{moment(item.actionTime).format('YYYY-MM-DD HH:mm:ss')}</span></div>
                                            <div>{item.comments}</div>
                                        </Timeline.Item>
                                    )
                                })
                            }
                        </Timeline>
                    </div> : null}
                    {
                        flowWebUrl ? <div>
                            <iframe width="100%" height={ContentH * 0.5} frameBorder={0} src={flowWebUrl}></iframe>
                        </div> : null
                    }
                </div>
            </div>
        );
    }
}
export default operation;
