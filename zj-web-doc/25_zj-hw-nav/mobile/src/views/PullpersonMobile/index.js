// 拉人移动端案列
import React, { Component } from 'react'
// import PullPersonMobile from 'pull-person-mobile'
import PullPersonMobile from '../modules/pullPersionMobile'
export default class index extends Component {
    render() {
        return (
            <div>
                <PullPersonMobile
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
