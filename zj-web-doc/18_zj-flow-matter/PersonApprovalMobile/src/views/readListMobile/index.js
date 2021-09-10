import React, { Component } from "react";
import { Tabs, NavBar, Icon } from "antd-mobile";
import { Form } from '@ant-design/compatible';
import '@ant-design/compatible/assets/index.css';
import { push } from "react-router-redux";
import TodoMobile from "./todoMobile";
import styles from "./style/index.less";
class Demo extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: 0
        };
    }
    render() {
        const { page } = this.state;
        const {
            dispatch,
            myPublic: { androidApi },
            match: {}
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
                        {"待阅已阅管理"}
                    </NavBar>
                </div>
                <div style={{ marginTop: "45px" }}>
                    <Tabs
                        prerenderingSiblingsNumber={0}
                        swipeable={false}
                        tabs={[
                            { title: "待阅"},
                            { title: "已阅"}
                        ]}
                        initialPage={page}
                        page={page}
                        onChange={(_, page) => {
                            this.setState({
                                page: page
                            });
                        }}
                    >
                        <TodoMobile
                            {...this.props}
                            tabBarHeight={88.5}
                            page={this.state.page}
                        />
                    </Tabs>
                </div>
            </div>
        );
    }
}

export default Form.create()(Demo);
