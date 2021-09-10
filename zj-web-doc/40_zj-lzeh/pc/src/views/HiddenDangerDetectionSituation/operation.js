import React, { Component } from "react";
import { Timeline } from 'antd';
import moment from 'moment';
import { message as Msg } from 'antd';
class operation extends Component {
    constructor(props) {
        super(props);
        this.state = {
            ContentH: '',
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
        const { workId } = this.props.clickCb.rowData;
        const { apiName } = this.props;
        if (workId) {
            this.props.myFetch(apiName, { apiName: 'getZxHwAqHiddenDangerDetails', apiType: 'POST', flowId: 'zxHwAqHiddenDanger', workId: workId }).then(({ success, message, data }) => {
                if (success) {
                    this.setState({
                        data: data.flowHistoryList,
                        flowWebUrl: data.flowWebUrl
                    });
                }else{
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
