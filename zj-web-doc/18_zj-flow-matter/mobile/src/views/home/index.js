import React, { Component } from 'react';
import { Grid, Icon } from '../../components';
class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            gridData: [
                {
                    text: "会议室流程",
                    name: "overdueSum",
                    defaultNum: 0,
                    bgColor: "red",
                    route: `${this.props.myPublic.appInfo.mainModule}todoByZjMeetingRoom`,
                    content: <Icon size="md" type={require('./lc.svg')} />
                },
                {
                    text: "会议室使用情况",
                    name: "index6",
                    defaultNum: 0,
                    bgColor: "green",
                    route: `${this.props.myPublic.appInfo.mainModule}meetingRoom`,
                    content: <Icon size="md" type={require('./qk.svg')} />
                },
                // {
                //     text: "年度会议计划审批",
                //     name: "index5",
                //     defaultNum: 0,
                //     bgColor: "blue",
                //     route: `${this.props.myPublic.appInfo.mainModule}worldYouthMeeting`,
                //     content: <Icon size="md" type={require('./ndhy.svg')} />
                // }
            ]
        }
    }
    render() {
        const { gridData } = this.state;
        return (
            <div style={{ flex: "1" }}>
                <Grid data={gridData} columnNum={2} {...this.props} />
            </div>
        )
    }
}
export default Home
