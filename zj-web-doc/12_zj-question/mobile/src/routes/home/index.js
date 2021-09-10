import React, { Component } from 'react';
import { Grid, Icon } from '../../components'
import { myFetch } from '../../tools';
const wenindex2 = require('../../svgs/wenindex2.svg');
const readme = require('../../svgs/readme.svg');
const ProblemAlist = require('../../svgs/ProblemAlist.svg');
const add = require('../../svgs/add.svg');
const gridData = [
    {
        // text: "待整改项",
        text: "待办项",
        name: "overdueSum",
        defaultNum: 0,
        bgColor: "red",
        route: `/problemList`,
        content: <img style={{ display: 'inline-block', width: '30px', height: '30px' }} src={wenindex2} />
    },
    {
        text: "检查清单查阅",
        name: "ProblemAlist",
        defaultNum: 0,
        bgColor: "orange",
        route: `/ProblemAlist`,
        content: <img style={{ display: 'inline-block', width: '30px', height: '30px' }} src={ProblemAlist} />
    }
];
let gridDatas = {
    text: "新增检查项",
    name: "carList",
    defaultNum: 0,
    bgColor: "blue",
    route: `/problemAdd/pullpersonroute`,
    content: <img style={{ display: 'inline-block', width: '30px', height: '30px' }} src={add} />
};
let gridDatat = {
    text: "实施检查",
    name: "/ProblemBlist",
    defaultNum: 0,
    bgColor: "blue",
    route: `/ProblemBlist`,
    content: <img style={{ display: 'inline-block', width: '30px', height: '30px' }} src={add} />
};
let gridDataa = {
    text: "本平台使用手册",
    name: "index6",
    defaultNum: 0,
    bgColor: "green",
    route: `/help`,
    content: <img style={{ display: 'inline-block', width: '30px', height: '30px' }} src={readme} />
};
class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: gridData
        }
    }
    componentDidMount() {
        // const { data: gridData } = this.state
        // myFetch("getSurrogateOverdueQuantity").then((json) => {
        //     const { success, data } = json;  
        //     if (success) {
        //         let _data = gridData.map((item, index) => {
        //             let { name } = item;
        //             item["num"] = data[name]
        //             return {
        //                 ...item
        //             }
        //         })
        //         this.setState({ data: _data })
        //     }
        // })
    }
    componentWillMount() {
        myFetch("getQuestionAddJurisdictionForWechat").then((json) => {
            const { success, data } = json;
            if (success && data == 0) {
                this.setState({
                    data: this.state.data.concat(gridDatas).concat(gridDatat).concat(gridDataa)
                })
            }else{
                this.setState({
                    data: this.state.data.concat(gridDataa)
                })
            }
        })
    }
    render() {
        const { data } = this.state
        return (
            <div style={{ flex: "1" }}>
                <Grid data={data} columnNum={2} {...this.props} />
            </div>
        )
    }
}
export default Home
