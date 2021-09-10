//待办已办列表
import React from "react";
import { Tabs, NavBar, Icon, InputItem } from "antd-mobile";
import { Form } from "antd";
//使用的旧版流程的回顶按钮
import { FlowButtonGroup, clickFlowButton } from "../flow";
import { goBack, replace } from "connected-react-router";
import ListItem from "./listItem";
import styles from "./list.less";

class Demo extends React.Component {
    constructor(props) {
        super(props);
        this.clickFlowButton = clickFlowButton.bind(this);
        let {
            match: {
                url,
                params: { status }
            },
            isInQnnTable,
            workFlowConfig = {},
            myPublic: {
                appInfo: { mainModule }
            }
        } = this.props;

        if (isInQnnTable) {
            const { todo, hasTodo } = workFlowConfig;
            // console.log("todo：", todo);
            // console.log("url1：", url);
            // console.log("url2：",  `${mainModule}${todo}`);
            if (url === `${mainModule}${todo}`) {
                status = "todo";
            } else if (url === `${mainModule}${hasTodo}`) {
                status = "hasTodo";
            }
        } else {
            status = status === "0" ? "todo" : status; //当前在待办还是已办
        }
        this.state = {
            searchKeywords: "",
            searchShow: false,
            status: status,
            formLink: this.props.formLink
        };
    }
    getFlowName = flowId => {
        if (flowId.indexOf("zxHwGxProcess") !== -1) {
            return "隐蔽工程";
        } else if (flowId.indexOf("zxHwZlTrouble") !== -1) {
            return "质量检查";
        } else if (flowId.indexOf("zxHwAqHiddenDanger") !== -1) {
            return "隐患排查";
        }else{
            return false
        }
    };
    handleSubmit = e => {
        e.preventDefault();
        const { getFieldValue } = this.props.form;
        const searchKeywords = getFieldValue("searchKeywords");
        this.setState({ searchShow: false, searchKeywords }, () => {
            this.refs["tab0"].onRefresh(searchKeywords);
            this.refs["tab1"].onRefresh(searchKeywords);
        });
    };
    backTop = () => {
        let page = this.getPage();
        this.refs["tab" + page].onBackTop();
    };
    getPage() {
        let page;
        const { status } = this.state;
        if (status === "todo") {
            page = 0;
        } else if (status === "hasTodo") {
            page = 1;
        } else {
            page = 0;
        }
        return page;
    }
    clearSearchKeywords = () => {
        this.setState(
            {
                searchKeywords: ""
            },
            () => {
                this.props.form.resetFields("searchKeywords");
            }
        );
    };
    render() {
        let { page, searchShow, searchKeywords, formLink, status } = this.state;
        const {
            dispatch,
            myPublic: { androidApi }
        } = this.props;
        const { getFieldDecorator } = this.props.form;
        page = this.getPage();
        return (
            <div className={styles.root}>
                <NavBar
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => {
                        if (androidApi) {
                            androidApi.closeActivity();
                        } else {
                            dispatch(goBack());
                        }
                    }}
                    rightContent={[
                        <Icon
                            key="0"
                            onClick={() => {
                                this.setState({ searchShow: !searchShow });
                            }}
                            type="search"
                        />
                    ]}
                >
                    {"审批管理"}
                </NavBar>
                <Tabs
                    swipeable={false}
                    tabs={[{ title: "待办" }, { title: "已办" }]}
                    initialPage={page}
                    page={page}
                    onChange={(_, page) => {
                        this.setState({
                            searchKeywords: ""
                        });
                        const status = page === 0 ? "todo" : "hastodo";
                        const {
                            myPublic: {
                                appInfo: { mainModule }
                            },
                            match: {
                                params: { flowId }
                            }
                        } = this.props;

                        //做qnn-table适配
                        if (this.props.isInQnnTable) {
                            const { workFlowConfig = {} } = this.props;
                            const { todo, hasTodo } = workFlowConfig;
                            if (!todo || !hasTodo) {
                                console.error("未配置hasTodo | todo 路径");
                                return;
                            }
                            this.props.dispatch(
                                replace(
                                    `${mainModule}${
                                        status === "todo" ? todo : hasTodo
                                    }`
                                )
                            );
                        } else {
                            this.props.dispatch(
                                replace(
                                    `${mainModule}${
                                        formLink[flowId]
                                    }/${flowId}/${status}`
                                )
                            );
                        }
                    }}
                >
                    <ListItem
                        ref={"tab0"}
                        {...this.props}
                        getFlowName={this.getFlowName}
                        status={status}
                        tabBarHeight={88.5}
                        clearSearchKeywords={this.clearSearchKeywords.bind(
                            this
                        )}
                    />
                    <ListItem
                        ref={"tab1"}
                        {...this.props}
                        getFlowName={this.getFlowName}
                        status={status}
                        tabBarHeight={88.5}
                        clearSearchKeywords={this.clearSearchKeywords.bind(
                            this
                        )}
                    />
                </Tabs>
                <FlowButtonGroup
                    {...this.props}
                    clickFlowButton={this.clickFlowButton}
                    backTop={this.backTop}
                    flowButtons={[{ buttonClass: "backTop" }]}
                />
                {searchShow ? (
                    <div className={styles.searchModal}>
                        <div
                            className={styles.searchMark}
                            onClick={() => {
                                this.setState({ searchShow: false });
                            }}
                        />
                        <Form
                            onSubmit={this.handleSubmit}
                            className={styles.searchBox}
                        >
                            {getFieldDecorator("searchKeywords", {
                                initialValue: searchKeywords
                            })(
                                <InputItem
                                    className={styles.searchInput}
                                    placeholder="请输入搜索关键词"
                                    extra={
                                        <Icon type="search" color="#108ee9" />
                                    }
                                    onExtraClick={this.handleSubmit}
                                    clear
                                />
                            )}
                        </Form>
                    </div>
                ) : (
                    getFieldDecorator("searchKeywords", {
                        initialValue: ""
                    })(<div />)
                )}
            </div>
        );
    }
}

export default Form.create()(Demo);
