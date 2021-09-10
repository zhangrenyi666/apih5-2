// 拉人移动端案列
import React, { Component } from 'react'
// import PullPerson from 'pull-person' 
import PullPerson from '../modules/pullPersion' 
export default class index extends Component {
    render() {
        return (
            <div>
                <PullPerson
                    search
                    searchApi="searchApi"
                    visible 
                    selectType="0"
                    myFetch={this.props.myFetch}
                    treeData={[{ label: '测试', value: '0', type: '2' }]}
                />
            </div>
        )
    }
}
