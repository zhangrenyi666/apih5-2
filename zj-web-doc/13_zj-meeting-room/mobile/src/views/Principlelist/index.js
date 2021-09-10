import React, { Component } from "react";
import s from "./style.less";
import { NavBar,Tabs } from "antd-mobile";
import HasTodo from './hasToto';
import Todo from './todo';
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page:props.match.params.page === '0' ? 0 : 1
        }
    }
    render() {
        const { page } = this.state;
        return (
            <div className={s.root}>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                    >
                        {"参会列表"}
                    </NavBar>
                </div>
                <div
                    style={{
                        height: window.innerHeight - 45
                    }}
                >
                    <HasTodo {...this.props} />
                    {/* <Tabs
                        swipeable={false}
                        tabs={[{ title: "回执" }, { title: "签到" }]}
                        initialPage={page}
                        page={page}
                        onChange={(_, page) => {
                            this.setState({ page });
                        }}
                    >
                        {page === 0 ? <HasTodo {...this.props} tabBarHeight={88.5} /> : <div/>}
                        {page === 1 ? <Todo {...this.props} tabBarHeight={88.5} /> : <div/>}
                    </Tabs> */}
                </div>
            </div>
        );
    }
}
export default Index;
