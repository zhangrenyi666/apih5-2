import React, { Component } from "react";
import { WingBlank, NavBar, WhiteSpace, DatePicker, List } from "antd-mobile"; //SegmentedControl, Grid,
import "./style.less";
// const data1 = Array.from(new Array(9)).map(() => ({
//   icon: 'https://gw.alipayobjects.com/zos/rmsportal/WXoqXTHrSnRcUwEaQgXJ.png',
// }));
export class Select extends Component {
    constructor(props) {
        super(props);
        this.state = {
            changeActionText: new Date().getFullYear(),
            changeActionIndex: 0, //初始化是处在哪个选项
            date: new Date()
        };
    }
    onClick(i) {
        let year = this.state.changeActionText;
        let month = i;
        let { push } = this.props.history;
        push(`${year}/${month}/0`); //0工资详情  1绩效详情
    }
    render() {
        return (
            <div>
                {/* <div className="header">
          <NavBar mode="light">{new Date().getFullYear()}年</NavBar>
        </div> */}

                <DatePicker
                    mode="year"
                    title="请选择"
                    extra="Optional"
                    value={this.state.date}
                    onChange={date =>
                        this.setState({
                            changeActionText: new Date(date).getFullYear()
                        })
                    }
                >
                    <div className="header">
                        <NavBar mode="light">
                            <span style={{ color: "#108ee9" }}>
                                {this.state.changeActionText}年
                            </span>
                        </NavBar>
                    </div>
                    {/* <List.Item arrow="horizontal">Date</List.Item> */}
                </DatePicker>

                <WhiteSpace />
                <WingBlank size="xs">
                    <div className="slideCon">
                        <ul>
                            {Array.from(new Array(12)).map((v, i) => {
                                return (
                                    <li
                                        onClick={this.onClick.bind(this, i + 1)}
                                        style={{
                                            color:
                                                new Date().getMonth() === i &&
                                                Number(
                                                    this.state.changeActionText
                                                ) === new Date().getFullYear()
                                                    ? "#108ee9"
                                                    : ""
                                        }}
                                        key={i}
                                    >
                                        {i + 1}
                                        <span className="mothon">月</span>
                                    </li>
                                );
                            })}
                        </ul>
                        <div className="desc">
                            点击上面月份即可查看该月工资详情
                            <br />
                            点击上面年份可选择年份
                        </div>
                    </div>
                </WingBlank>
            </div>
        );
    }
}

export default Select;
