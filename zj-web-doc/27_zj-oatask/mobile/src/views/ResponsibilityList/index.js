import React from 'react';
import { Tabs, NavBar, Icon } from 'antd-mobile';
import { Form } from 'antd';
import TodoMobile from "./TodoMobile"
import HasTodoMobile from "./HasTodoMobile"
import { push } from 'connected-react-router';
class Demo extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            page: props.match.params.page === '0' ? 0 : 1
        }
    }
    render() {
        const { page } = this.state;
        const { dispatch, myPublic: { androidApi, appInfo: { mainModule } } } = this.props;
        return (
            <div>
                <NavBar
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => {
                        if (androidApi) {
                            androidApi.closeActivity()
                        } else {
                            dispatch(push(`${mainModule}`));
                        }
                    }}
                >{"任务清单列表"}</NavBar>
                <Tabs
                    swipeable={false}
                    tabs={[{ title: "待办" },{ title: "已办" }]}
                    initialPage={page}
                    page={page}
                    onChange={(_, page) => {
                        this.setState({ page });
                    }} 
                >
                    <TodoMobile ref={"tab0"} {...this.props} tabBarHeight={88.5} />
                    <HasTodoMobile ref={"tab1"} {...this.props} tabBarHeight={88.5} />
                </Tabs>
            </div>
        );
    }
}


export default Form.create()(Demo)