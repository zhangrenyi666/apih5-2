
import { List } from '../modules/work-flow'
import React, { Component } from 'react'
class index extends Component {
    render() {
        return (
            <div>
                <List formLink={{
                    zjPartyFeeUse: "FlowByDF",
                }} {...this.props} />
            </div>
        )
    }
}
export default index;