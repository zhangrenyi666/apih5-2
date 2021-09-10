import React, { Component } from "react";
import { Tabs, NavBar, Icon } from "antd-mobile";
import { Form } from "antd";
// import { goBack } from "connected-react-router";
// import HastodoMobile from "./hastodoMobile";
import { push } from "react-router-redux";
import TodoMobile from "./todoMobile";
import styles from "./style/index.less";
class Demo extends Component {
    constructor(props) {
        super(props);
        const { mainModule } = this.props.myPublic.appInfo;
        const { type } = this.props.match.params;
        const awaitUrl = `${mainModule}TabsListMobile/${type}/await`;
        this.state = {
            page: this.props.match.url === awaitUrl ? 0 : 1
        };
    }
    render() {
        const { page } = this.state;
        const {
            dispatch,
            myPublic: { androidApi },
            match: {
                params: { type }
            }
        } = this.props;
        const {
            mainModule
        } = this.props.myPublic.appInfo;
        return (
            <div className={styles.root}>
                <div className={styles.top}>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            if (androidApi) {
                                androidApi.closeActivity();
                            } else {
                                dispatch(push(`${mainModule}App`));
                            }
                        }}
                    >
                        {"审批管理"}
                    </NavBar>
                </div>
                <div style={{ marginTop: "45px" }}>
                    <Tabs
                        prerenderingSiblingsNumber={0}
                        swipeable={false}
                        tabs={[
                            { title: type === "all" ? "全部待办" : "待办公文" },
                            { title: type === "all" ? "全部已办" : "已办公文" }
                        ]}
                        initialPage={page}
                        page={page}
                        onChange={(_, page) => {
                            // const { type } = this.props.match.params;
                            if (page === 0) {
                                //去待办
                                
                                this.props.dispatch(
                                    push(
                                        `${mainModule}TabsListMobile/${type}/await`
                                    )
                                );
                                this.setState({
                                    page: 0
                                });
                            } else {
                                //去已办 
                                this.props.dispatch(
                                    push(
                                        `${mainModule}TabsListMobile/${type}/over`
                                    )
                                );
                                this.setState({
                                    page: 1
                                });
                            }
                        }}
                    >
                        <TodoMobile
                            {...this.props}
                            tabBarHeight={88.5}
                            page={this.state.page}
                        />
                        {/* <TodoMobile {...this.props} tabBarHeight={88.5} page={this.state.page}/> */}
                        {/* <HastodoMobile {...this.props} tabBarHeight={88.5} /> */}
                    </Tabs>
                </div>
            </div>
        );
    }
}

export default Form.create()(Demo);
