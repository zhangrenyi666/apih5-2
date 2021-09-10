import React,{ Component } from "react";
import { Toast,Grid } from "antd-mobile";
import { Spin } from "antd";
import incTab from "../letBottomIncTabs";
const myImg = name => {
    if (name.indexOf("http://") !== -1 || name.indexOf("https://") !== -1) {
        //cdn地址
        return name;
    } else {
        //底本地址
        return require(`../../imgs/app/blue/${name}.svg`);
    }
};
class Index extends Component {
    state = {
        data: [
            // {
            //     moduleTitle: '用印申请',
            //     moduleLink: 'http://weixin.fheb.cn:99/flow-matter-mobile',
            //     moduleIcon: 'yong_yin_guan_li',
            // }
        ]
    };

    componentDidMount() {
        let { myFetch } = this.props;
        this.myFetch = myFetch;
        this.refresh();
    }

    refresh = () => {
        this.myFetch("getSysWoaAddFlowList",{}).then(
            ({ success,data = [],message }) => {
                if (success) {
                    this.setState({
                        data,
                        loading: false
                    });
                } else {
                    this.setState(
                        {
                            loading: false
                        },
                        () => {
                            Toast.fail(message);
                        }
                    );
                }
            }
        );
    };

    //模块被点击
    itemClick = ({ moduleLink,moduleType }) => {
        const {
            myPublic: {
                appInfo: { mainModule }
            }
        } = this.props;
        // console.log(moduleLink,moduleType)
        //类型  1：自己模块；2:第三方链接
        switch (moduleType) {
            case "1":
                this.props.history.push(`${mainModule}${moduleLink}`)
                break;
            case "2":
                window.location.href = moduleLink;
                break;
            case "99":
                Toast.info(moduleLink);
                break;
            default:
                break;
        } 
    };
    render() {
        const { data = [],loading } = this.state;
        return (
            <div style={{ minHeight: "100vh",background: "white" }}>
                {data.length === 0 && !loading ? (
                    <p
                        style={{
                            color: "#ccc",
                            marginTop: "30vh",
                            textAlign: "center"
                        }}
                    >
                        暂无数据
                    </p>
                ) : (
                        ""
                    )}
                <Spin spinning={loading}>
                    <Grid
                        data={data}
                        hasLine={false}
                        columnNum={4}
                        renderItem={dataItem => { 
                            const {
                                moduleTitle, 
                                moduleIcon, 
                            } = dataItem;
                            return (
                                <div
                                    onClick={() => {
                                        this.itemClick(dataItem);
                                    }}
                                >
                                    <img
                                        src={myImg(moduleIcon)}
                                        style={{
                                            width: "35px",
                                            height: "35px"
                                        }}
                                        alt=""
                                    />
                                    <div
                                        style={{
                                            color: "#888",
                                            fontSize: "12px",
                                            marginTop: "8px"
                                        }}
                                    >
                                        <span>{moduleTitle}</span>
                                    </div>
                                </div>
                            );
                        }}
                    />
                </Spin>
            </div>
        );
    }
}

export default incTab(Index);
