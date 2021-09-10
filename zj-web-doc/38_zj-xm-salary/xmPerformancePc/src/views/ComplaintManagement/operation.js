import React, { Component } from "react";
import { Timeline, message as Msg } from 'antd';
import moment from 'moment';
class operation extends Component {
    constructor(props) {
        super(props);
        this.state = {
            detailedId:props.initialValues.detailedId || '',
            data: []
        }
    }
    componentDidMount() {
        let body = {
            detailedId: this.state.detailedId
        }
        this.props.myFetch('getZjXmJxTaskScoreDetailedRecordList', body).then(({ data, success, message }) => {
            if (success) {
                this.setState({
                    data
                })
            } else {
                Msg.error(message)
            }
        })
    }
    render() {
        const { data } = this.state;
        return (
            <div style={{ overflowY:'hidden',padding: '10px' }}>
                <Timeline>
                    {
                        data.length ? data.map((item, index) => {
                            return (
                                <Timeline.Item key={index}>
                                    <div><span>{item.appealStatus === '1' ? item.auditeeName : item.hrName}</span> <span>{moment(item.modifyTime).format('YYYY-MM-DD HH:mm:ss')}</span></div>
                                    <div>{item.appealStatus === '1' ? item.appealOpinion : item.hrOpinion}</div>
                                </Timeline.Item>
                            )
                        }) : <div style={{fontSize:'14px',fontWeight:'bold',padding:'10px 0',textAlign:'center'}}>暂无操作记录...</div>
                    }
                </Timeline>
            </div>
        );
    }
}
export default operation;
