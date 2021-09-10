import React, { Component } from 'react';
import { Grid, Icon } from '../../components';
class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            gridData: [
                {
                    text: "任务清单列表",
                    name: "ResponsibilityList",
                    defaultNum: 0,
                    bgColor: "red",
                    route: `${this.props.myPublic.appInfo.mainModule}ResponsibilityList/0`,
                    content: <Icon size="md" type={require('./qd.svg')} />
                },
                {
                    text: "任务统计表",
                    name: "StatisticsList",
                    defaultNum: 0,
                    bgColor: "green",
                    route: `${this.props.myPublic.appInfo.mainModule}StatisticsList`,
                    content: <Icon size="md" type={require('./tj.svg')} />
                }
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
