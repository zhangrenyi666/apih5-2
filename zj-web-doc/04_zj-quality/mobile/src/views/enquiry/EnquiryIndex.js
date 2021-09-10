import React, { Component } from 'react';
import { Toast } from 'antd-mobile';
import MGrid from '../common/MGrid';
class EnquiryIndex extends Component {
    constructor(props) {
        super(props);
        const { routerInfo: { routeData, curKey } } = this.props
        const { moduleName } = routeData[curKey]
        this.state = {
            gridData: [//第一块数据默认数据
                {
                    stateFlag: "0",
                    text: "首次提问",
                    route: `${moduleName}enquiry/list/0`,
                    content: "0"
                },
                {
                    stateFlag: "1",
                    text: "已被回答",
                    route: `${moduleName}enquiry/list/1`,
                    content: "0"
                },
                {
                    stateFlag: "2",
                    text: "再次提问",
                    route: `${moduleName}enquiry/list/2`,
                    content: "0"
                },
                {
                    stateFlag: "3",
                    text: "待回答",
                    route: `${moduleName}enquiry/list/3`,
                    content: "0"
                },
                {
                    stateFlag: "4",
                    text: "已回答",
                    route: `${moduleName}enquiry/list/4`,
                    content: "0"
                },
                {
                    stateFlag: "5",
                    text: "已结束",
                    route: `${moduleName}enquiry/list/5`,
                    content: "0"
                },
            ],
        }
    }
    componentWillUnmount = () => {
        this.setState = (state, callback) => {
            return;
        };
    }
    componentDidMount() {
        const me = this;
        const { gridData } = me.state
        const { myFetch, dispatch, actions: { logout }, routerInfo: { routeData, curKey } } = me.props
        const { pathName } = routeData[curKey]
        Toast.loading('Loading...', 0);
        myFetch('getStateCountList').then(({ success, data, code, message }) => {
            Toast.hide()
            if (success) {
                let _gridData = gridData.map((item, i) => {
                    for (let i = 0; i < data.length; i++) {
                        if (data[i].stateFlag === item.stateFlag) {
                            item.content = data[i].stateCount || 0;
                            break
                        }
                    }
                    return item
                });
                me.setState({ gridData: [..._gridData] })
            } else if (code === "3003" || code === "3004") {
                dispatch(logout(pathName))
            } else {
                Toast.offline(message, 2)
            }
        })
    }
    render() {
        const { gridData } = this.state
        return (<MGrid data={gridData} columnNum={3} {...this.props} />)
    }
}



export default EnquiryIndex;
