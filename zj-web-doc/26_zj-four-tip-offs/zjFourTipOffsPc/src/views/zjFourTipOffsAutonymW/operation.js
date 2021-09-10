import React, { Component } from "react";
import { Timeline, message as Msg } from 'antd';
import moment from 'moment';
class operation extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: []
        }
    }
    componentDidMount() {
        let body = {
            reportId: this.props.clickCb.rowData.reportId
        }
        this.props.myFetch('getZjOperationRecordList', body).then(({ data, success, message }) => {
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
            <div style={{ height:'85vh',overflowY:'scroll',padding: '10px' }}>
                <Timeline>
                    {
                        data.length ? data.map((item, index) => {
                            return (
                                <Timeline.Item key={index}>
                                    <div><span>{item.createUserName}</span> <span>{moment(item.operationTime).format('YYYY-MM-DD HH:mm:ss')}</span></div>
                                    <div>{item.operationInfo}</div>
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
