import React,{ Component } from "react";
import { Divider } from "antd"; //Spin,
import { push } from "react-router-redux";
import MyList from "../../modules/MList";
import s from "./style.less";
const flowIds = window.configs.flowIds || [];
class Index extends Component {
    constructor(props) {
        super(props);
        const { page } = this.props;
        let apiName = (page === 0) ? "getTodoList" : "getHasTodoList";
        this.state = {
            data: [],
            page: 1,
            limit: 10,
            height: document.documentElement.clientHeight - 44 - 43 - 45 - 1,
            refreshing: false,
            loading: false,
            content: "",
            title: "",
            loadOver: false,
            apiName
        };
    }

    render() {
        const { apiName } = this.state;
        const { match = {} } = this.props;
        // const {
        //     params: {  }
        // } = match;

        return (
            <div
                style={{
                    height: window.innerHeight - 43 - 45 - 1
                }}
            >
                <MyList
                    myFetch={this.props.myFetch}
                    upload={this.props.myUpload} //ajax方法必须返回一个promise
                    searchKey="title" //搜索时的key
                    fetchConfig={{
                        //ajax配置
                        apiName: apiName, //后台api
                        otherParams: { flowId:"hwfgApply,hwfgApplyOut" } //参数
                    }}
                    Item={props => {
                        //列表模板 props里有所有数据
                        const { rowData,rowID } = props;
                        const item = rowData;
                        const index = rowID;
                        return (
                            <div
                                className={s.center}
                                style={{
                                    borderLeft: `3px solid ${index % 2 === 0 ? "#ff4000" : "#ff9900"}`
                                }}
                                key={index}
                                onClick={() => {
                                    // const { page } = this.props;
                                    const {
                                        mainModule
                                    } = this.props.myPublic.appInfo;
                                    this.props.dispatch(
                                        push(
                                            `${mainModule}TabsListMobileDetail/${item.trackId}/${item.workId}/${item.flowId}`
                                        )
                                    );
                                }}
                            >
                                <div className={s.top}>
                                    【{item.flowName}】
                                    {/* <span>{item.title}</span> */}
                                </div>
                                <Divider style={{ margin: "8px 0px" }} />
                                <div
                                    style={{
                                        fontSize: "13px",
                                        color: item.cssx === "1" ? "red" : ""
                                    }}
                                >
                                    {/* {item.gwbt} */}
                                    <span>{item.title}</span>
                                </div>
                            </div>
                        );
                    }}
                />
            </div>
        );
    }
}
export default Index;
