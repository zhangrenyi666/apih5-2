import React, { Component } from 'react';
import { Grid, Icon } from '../../components';
class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            gridData: [
                {
                    text: "公司工会主席审批",
                    name: "ChairmanOfTheCompanyUnion",
                    defaultNum: 0,
                    bgColor: "red",
                    route: `${this.props.myPublic.appInfo.mainModule}ChairmanOfTheCompanyUnion`,
                    content: <Icon size="md" type={require('./ghzxsp.svg')} />
                },
                {
                    text: "立案流程",
                    name: "FlowByApplication",
                    defaultNum: 0,
                    bgColor: "green",
                    route: `${this.props.myPublic.appInfo.mainModule}FlowByApplicationAwait`,
                    content: <Icon size="md" type={require('./lalc.svg')} />
                },
                {
                    text: "部门落实完成情况审批",
                    name: "FlowByProposalAwait",
                    defaultNum: 0,
                    bgColor: "blue",
                    route: `${this.props.myPublic.appInfo.mainModule}FlowByProposalAwait`,
                    content: <Icon size="md" type={require('./bmls.svg')} />
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
