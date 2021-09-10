//待办已办列表
import React from 'react';
import { Tabs, NavBar, Icon, InputItem } from 'antd-mobile';
import { Form } from '@ant-design/compatible';
import '@ant-design/compatible/assets/index.css';
import { FlowButtonGroup, clickFlowButton } from "../flow";
import { goBack,  replace } from 'connected-react-router';
import ListItem from "./listItem"
import styles from "./list.less";

class Demo extends React.Component {
    constructor(props) {
        super(props);
        this.clickFlowButton = clickFlowButton.bind(this)
        let { match: { params: { status } } } = this.props;
        this.state = {
            searchKeywords: "",
            searchShow: false,
            page: status === "0" || status === 'todo' ? 0 : 1,
            status: status === '0' ? 'todo' : status, //当前在待办还是已办
            formLink: this.props.formLink
        }
    }
    handleSubmit = (e) => {
        e.preventDefault();
        const { getFieldValue } = this.props.form
        const searchKeywords = getFieldValue("searchKeywords")
        this.setState({ searchShow: false, searchKeywords }, () => {
            this.refs["tab0"].onRefresh(searchKeywords)
            this.refs["tab1"].onRefresh(searchKeywords)
        })
    }
    backTop = () => {
        let { page } = this.state;
        const { match: { params: { status } } } = this.props
        if (status === 'todo') {
            page = 0;
        } else if (status === 'hastodo') {
            page = 1;
        } 
        this.refs["tab" + page].onBackTop()
    }
    clearSearchKeywords = ()=>{
        this.setState({
            searchKeywords:""
        }, ()=>{
            this.props.form.resetFields('searchKeywords');
        })
    }
    render() {
        let { page, searchShow, searchKeywords, formLink } = this.state
        const { dispatch, myPublic: { androidApi }, match: { params: { status } } } = this.props
        const { getFieldDecorator } = this.props.form;
        if (status === 'todo') {
            page = 0;
        } else if (status === 'hastodo') {
            page = 1;
        } else {
            page = 0;
        }
        return (
            <div className={styles.root}>
                <NavBar
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => {
                        if (androidApi) {
                            androidApi.closeActivity()
                        } else {
                            dispatch(goBack())
                        }
                    }}
                    rightContent={[
                        <Icon key="0" onClick={() => { this.setState({ searchShow: !searchShow }) }} type="search" />,
                    ]}
                >{"审批管理"}</NavBar>
                <Tabs
                    swipeable={false}
                    tabs={[{ title: "待办" }, { title: "已办" }]}
                    initialPage={page}
                    page={page}
                    onChange={(_, page) => {
                        this.setState({
                            searchKeywords:""
                        })
                        const status = page === 0 ? 'todo' : 'hastodo';
                        const { myPublic: { appInfo: { mainModule } }, match: { params: { flowId } } } = this.props;
                        this.props.dispatch(replace(`${mainModule}${formLink[flowId]}/${flowId}/${status}`))
                    }}
                >
                    <ListItem ref={"tab0"} {...this.props} tabBarHeight={88.5} clearSearchKeywords={this.clearSearchKeywords.bind(this)} />
                    <ListItem ref={"tab1"} {...this.props} tabBarHeight={88.5} clearSearchKeywords={this.clearSearchKeywords.bind(this)}/>
                </Tabs>
                <FlowButtonGroup {...this.props} clickFlowButton={this.clickFlowButton} backTop={this.backTop} flowButtons={[{ buttonClass: "backTop" }]} />
                {searchShow ?
                    <div className={styles.searchModal}>
                        <div className={styles.searchMark} onClick={() => {
                            this.setState({ searchShow: false })
                        }}></div>
                        <Form onSubmit={this.handleSubmit} className={styles.searchBox}>
                            {
                                getFieldDecorator('searchKeywords', {
                                    initialValue: searchKeywords,
                                })(<InputItem
                                    className={styles.searchInput}
                                    placeholder="请输入搜索关键词"
                                    extra={<Icon type="search" color="#108ee9" />}
                                    onExtraClick={this.handleSubmit}
                                    clear
                                ></InputItem>)
                            }
                        </Form>
                    </div>
                    : getFieldDecorator('searchKeywords', {
                        initialValue: "",
                    })(<div />)
                }
            </div>
        );
    }
}


export default Form.create()(Demo)