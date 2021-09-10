import React, { Component } from 'react';
import { Grid, Icon } from '../../components'
// import { myFetch } from '../../tools';
const gridData = [
    {
        text: "车辆列表",
        name: "carList",
        defaultNum: 0,
        bgColor: "blue",
        route: `/carList`,
        content: <Icon size="md" type={require('../../svgs/car.svg')} />
    },
    {
        text: "申请列表",
        name: "overdueSum",
        defaultNum: 0,
        bgColor: "red",
        route: `/applyList`,
        content: <Icon size="md" type={require('../../svgs/apply.svg')} />
    },
    {
        text: "审批列表",
        name: "ExamineList",
        defaultNum: 0,
        bgColor: "orange",
        route: `/examineList`,
        content: <Icon size="md" type={require('../../svgs/apply.svg')} />
    }, 
    // {
    //     text: "本平台使用手册",
    //     name: "index6",
    //     defaultNum: 0,
    //     bgColor: "green",
    //     route: `/help`,
    //     content: <Icon size="md" type={require('../../svgs/readme.svg')} />
    // }
];

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
