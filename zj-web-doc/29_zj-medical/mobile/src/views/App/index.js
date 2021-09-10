import React,{ Component } from "react";
import incTab from "../letBottomIncTabs";
import {
    Toast,
    WhiteSpace,
    Grid,
    Flex,
    Modal,
    PickerView,
    Icon,
    NoticeBar,
    Badge,
    Carousel
} from "antd-mobile";
import { push } from "connected-react-router";
import { Spin } from "antd";
import s from "./style.less";
// import { isString } from 'util';
const imgs = {
    logo: require("../../imgs/logo.png"),
    sao_miao: require("../../imgs/app/sao_miao.svg"),
    gong_si: require("../../imgs/app/gong_si.svg")
    // head_bg: require("../../imgs/head_bg.png")
};
const myImg = name => {
    if (name.indexOf("http://") !== -1 || name.indexOf("https://") !== -1) {
        //cdn地址
        return name;
    } else {
        //底本地址
        return require(`../../imgs/app/blue/${name}.svg`);
    }
};

// const companyList = [
//     {
//         label: '中交一公局',
//         value: '001'
//     },
// ];

class Index extends Component {
    constructor(...args) {
        super(...args);
        let {
            myFetch,
            myPublic: {
                wx,
                appInfo: { mainModule }
            },
            loginAndLogoutInfo = {}
        } = this.props;

        const LoginAndLogoutInfo = { ...loginAndLogoutInfo };
        //其属性levelId为主键值
        const curCompany = LoginAndLogoutInfo.loginInfo ? LoginAndLogoutInfo.loginInfo.userInfo.curCompany : null;
        const projectList = LoginAndLogoutInfo.loginInfo ? LoginAndLogoutInfo.loginInfo.userInfo.projectList : [];

        //临时全局变量 请勿随便使用
        this.curCompany = curCompany;

        this.mainModule = mainModule;
        this.myFetch = myFetch;
        this.wx = wx;
        this.state = {
            loading: true,
            data: {
                todoCount: "", //待审批数量
                hasTodoCount: "", //已审批数量
                noticeCount: "", //公告数量
                homeHintInformationIcon: "" //提示信息图标
                // homeHintInformation: '天黑了注意休息。天黑了注意休息。天黑了注意休息。天黑了注意休息。', //提示信息
                // zjWoaLargeModuleList: [ //大模块列表
                //     {
                //         largeModuleType: '1', //1:审批模块；2:新闻模式
                //         largeModuleTitle: '审批管理', //大模块标题
                //         sysWoaSmallModuleList: [ //子集
                //             {
                //                 smallModuleType: '2', //类型  1：自己模块；2:第三方链接
                //                 smallModuleTitle: '公文处理', //标题
                //                 smallModuleLink: '123', //连接
                //                 smallModuleIcon: 'gong_wen_chu_li', //图标
                //                 smallModuleCount: '10', //图标
                //             },
                //         ]
                //     },
                //     {
                //         largeModuleType: '1', //1:审批模块；2:新闻模式
                //         largeModuleTitle: '其他', //大模块标题
                //         sysWoaSmallModuleList: [ //子集
                //             {
                //                 smallModuleType: '2', //类型  1：自己模块；2:第三方链接
                //                 smallModuleTitle: '知识库', //标题
                //                 smallModuleLink: 'http://www.baidu.com', //连接
                //                 smallModuleIcon: 'zhi_shi_ku', //图标
                //                 smallModuleCount: '10', //图标
                //             },
                //         ]
                //     }
                // ]
            },
            curCompany: this.curCompany, //当前公司
            companyList: projectList ? [projectList] : null, //全部公司
            showSelectCompany: false, //是否显示选择公司

            topModules: [],
        };
    }

    componentDidMount() {
        this.refresh();
    }

    refresh = () => {
        const {
            curCompany = {}
        } = this.state;
        const { value } = curCompany;
        //getZxHwHomeMobilIndex
        this.myFetch("getSysMobilIndex",{ companyId: value }).then(
            ({ success,data,message }) => {
                if (success) {

                    //top模块单独渲染 因为在顶部的
                    let topData = data.homeLargeModuleList.filter(item => {
                        return item.largeModuleType === "8";
                    });

                    let topModules = [];
                    if (topData && topData.length) {
                        topModules = topData[0].sysWoaSmallModuleList;
                    }

                    this.setState(
                        {
                            data,
                            loading: false,
                            topModules
                        },
                        () => {

                            //再去请求待办数量和小红点
                            this.myFetch("getSysMobilIndexByData",{
                                companyId: value
                            }).then(({ success,data,message }) => {
                                if (success) {
                                    this.setState({
                                        data2: data
                                    });
                                } else {
                                    Toast.fail(message);
                                }
                            });
                        }
                    );
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


    //切换项目方法
    changCompanyFn = async (companyInfo,projectList) => {
        const { dispatch,actions: { setLoginAndLogoutInfo } } = this.props;
        const { curCompany,companyList } = this.state;

        if (!companyList && projectList) {
            const LoginAndLogoutInfo = this.props.loginAndLogoutInfo;
            const oldCurCompany = LoginAndLogoutInfo.loginInfo.userInfo.curCompany
            if (!oldCurCompany) {
                LoginAndLogoutInfo.loginInfo.userInfo.curCompany = companyInfo;
                if (projectList) {
                    LoginAndLogoutInfo.loginInfo.userInfo.projectList = projectList;
                }
                dispatch(setLoginAndLogoutInfo({ ...LoginAndLogoutInfo }));
            }
            return;
        }

        if (curCompany && curCompany.value) {
            Toast.loading('请稍等...',0);
            await this.props.myFetch('changeZtEndProjectManagement',companyInfo).then(({ success,data,message }) => {
                Toast.hide()
                if (success) {
                    //将切换的公司信息存进userInfo中 
                    this.setState({
                        curCompany: companyInfo,
                    },() => {
                        const oldLoginAndLogoutInfo = this.props.loginAndLogoutInfo;
                        oldLoginAndLogoutInfo.loginInfo.userInfo.curCompany = companyInfo;
                        if (projectList) {
                            oldLoginAndLogoutInfo.loginInfo.userInfo.projectList = projectList;
                        }
                        dispatch(setLoginAndLogoutInfo({ ...oldLoginAndLogoutInfo }));
                    })
                } else {
                    Toast.fail(message);
                    this.setState({
                        curCompany: null,
                        companyList: null
                    })
                }
            });
        }
    }

    //二维码图片被点击
    qrcodeClick = () => {
        this.wx.ready(() => {
            this.wx.scanQRCode({
                needResult: 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
                success: function (res) {
                    // var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                },
                error: err => {
                    alert("错误：",err);
                }
            });
        });
    };

    //显示选择公司弹窗
    showCompanyModal = e => {
        e.preventDefault(); // 修复 Android 上点击穿透
        const { companyList } = this.state;
        if (companyList[0].length === 1) {
            Toast.info("当前公司列表只有一个，不可切换。");
        } else {
            this.setState({
                showSelectCompany: true
            });
        }
    };

    //切换公司
    changeCompany = company => {
        this.curCompany = company[0];
    };
    changeCompanyOk = () => {
        if (this.curCompany && !Array.isArray(this.curCompany)) {
            let curCompany = {};
            const { companyList = [] } = this.state;
            companyList[0].map(item => {
                if (item.value === this.curCompany) {
                    curCompany = item;
                }
                return item;
            });

            //调用切换公司
            this.changCompanyFn.bind(this)(curCompany);

        } else {
            this.setState({
                showSelectCompany: false
            });
        }
    };

    cancelChangeCompany = company => {
        this.setState({
            showSelectCompany: false
        });
    };

    onWrapTouchStart = e => {
        // fix touch to scroll background page on iOS
        if (!/iPhone|iPod|iPad/i.test(navigator.userAgent)) {
            return;
        }
        const pNode = closest(e.target,".am-modal-content");
        if (!pNode) {
            e.preventDefault();
        }
    };

    //模块被点击
    itemClick = (type,link) => {
        const {
            dispatch,
            myPublic: {
                appInfo: { mainModule }
            }
        } = this.props;
        switch (
        type //类型  1：自己模块；2:第三方链接
        ) {
            case "1":
                dispatch(push(`${mainModule}${link}`));
                break;
            case "2":
                window.location.href = link;
                break;
            case "99":
                Toast.info(link);
                break;
            default:
                break;
        }
    };

    //模块类型判断
    moduleTypeSwitch = (type,data = []) => {
        const {
            dispatch,
            myPublic: {
                appInfo: { mainModule }
            }
        } = this.props;
        const { imgHeight,slideIndex = 0,data2 = {} } = this.state;
        const { dataInfoiList = {},dataMsgList } = data2;
        switch (
        type //1:审批模块；2:新闻模式
        ) {
            case "1":
                return (
                    <div>
                        {!data || data.length === 0 ? (
                            <div style={{ paddingLeft: "10px",color: "#ccc" }}>
                                暂无数据
                            </div>
                        ) : (
                                <Grid
                                    data={data}
                                    hasLine={false}
                                    columnNum={4}
                                    renderItem={dataItem => {
                                        const {
                                            // smallModuleCount,
                                            smallModuleIcon,
                                            smallModuleLink,
                                            smallModuleTitle,
                                            smallModuleType, //类型  1：自己模块；2:第三方链接
                                            smallModuleId
                                        } = dataItem;
                                        return (
                                            <div
                                                onClick={() => {
                                                    this.itemClick(
                                                        smallModuleType,
                                                        smallModuleLink
                                                    );
                                                }}
                                            >
                                                <img
                                                    src={myImg(smallModuleIcon)}
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
                                                    {dataInfoiList[smallModuleId] &&
                                                        dataInfoiList[smallModuleId] >
                                                        0 ? (
                                                            <Badge dot>
                                                                <span>
                                                                    {smallModuleTitle}
                                                                </span>
                                                            </Badge>
                                                        ) : (
                                                            <span>
                                                                {smallModuleTitle}
                                                            </span>
                                                        )}
                                                </div>
                                            </div>
                                        );
                                    }}
                                />
                            )}
                    </div>
                );
            case "2":
                return (
                    <div>
                        {data && data.length ? (
                            <div style={{ background: "#fff" }}>
                                <Carousel
                                    style={{
                                        marginTop: "10px",
                                        overflow: "hidden"
                                    }}
                                    frameOverflow="visible"
                                    cellSpacing={10}
                                    autoplay
                                    infinite
                                    beforeChange={(from,to) => { }}
                                    afterChange={index =>
                                        this.setState({ slideIndex: index })
                                    }
                                >
                                    {data.map(({ fileUrl,viewId },index) => (
                                        <a
                                            key={index}
                                            onClick={() => {
                                                dispatch(
                                                    push(
                                                        `${mainModule}newsDetail/${viewId}`
                                                    )
                                                );
                                            }}
                                            style={{
                                                display: "block",
                                                position: "relative",
                                                top: 0,
                                                height: imgHeight,
                                                boxShadow:
                                                    "2px 1px 1px rgba(0, 0, 0, 0.2)"
                                            }}
                                        >
                                            <img
                                                src={fileUrl}
                                                alt=""
                                                style={{
                                                    width: "100%",
                                                    verticalAlign: "top"
                                                }}
                                                onLoad={() => {
                                                    // fire window resize event to change height
                                                    window.dispatchEvent(
                                                        new Event("resize")
                                                    );
                                                    this.setState({
                                                        imgHeight: "auto"
                                                    });
                                                }}
                                            />
                                        </a>
                                    ))}
                                </Carousel>
                                <div
                                    style={{
                                        padding: "10px",
                                        boxSizing: "border-box"
                                    }}
                                >
                                    <div
                                        style={{
                                            width: "100%",
                                            margin: "0 auto"
                                        }}
                                    >
                                        {data[slideIndex].viewSummary}
                                    </div>
                                </div>
                            </div>
                        ) : (
                                <div style={{ paddingLeft: "10px",color: "#ccc" }}>
                                    暂无数据
                            </div>
                            )}
                    </div>
                );
            case "3": //领导周计划
                if (data && data[0]) {
                    let {
                        smallModuleLink,
                        smallModuleTitle,
                        smallModuleIcon
                    } = data[0];
                    return (
                        <NoticeBar
                            mode="link"
                            style={{
                                background: "#fff",
                                color: "#666"
                            }}
                            icon={
                                <img
                                    width="20px"
                                    src={myImg(smallModuleIcon)}
                                    alt=""
                                />
                            }
                            onClick={() => {
                                window.location.href = smallModuleLink;
                            }}
                        >
                            {smallModuleTitle}
                        </NoticeBar>
                    );
                } else {
                    return (
                        <div style={{ paddingLeft: "10px",color: "#ccc" }}>
                            暂无数据
                        </div>
                    );
                }
            case "5": //温馨提示
                if (dataMsgList) {
                    data = dataMsgList;
                }
                if (data && data.length) {
                    return (
                        <Carousel
                            className="my-carousel"
                            vertical
                            dots={false}
                            dragging={false}
                            swiping={false}
                            autoplay
                            infinite
                        >
                            {/* <div className="v-item"></div>  */}
                            {data.map((item,index) => {
                                let {
                                    icon = "", //提示信息图标
                                    msg = "", //提示信息
                                    msgBgColor = "", //提示信息背景色
                                    msgColor = "" //提示信息字体色
                                } = item;
                                return (
                                    <div key={index}>
                                        {msg ? (
                                            <NoticeBar
                                                className={s.gg}
                                                style={{
                                                    background:
                                                        msgBgColor || "#fff",
                                                    color: msgColor || "#666"
                                                }}
                                                icon={
                                                    icon ? (
                                                        <img
                                                            width="20px"
                                                            src={myImg(icon)}
                                                            alt=""
                                                        />
                                                    ) : null
                                                }
                                                marqueeProps={{
                                                    loop: true,
                                                    style: { padding: "0 3px" }
                                                }}
                                            >
                                                {msg}
                                            </NoticeBar>
                                        ) : null}
                                    </div>
                                );
                            })}
                        </Carousel>
                    );
                } else {
                    return (
                        <div style={{ paddingLeft: "10px",color: "#ccc" }}>
                            暂无数据
                        </div>
                    );
                }
            default:

                //未知largeModuleType
                return <div style={{ display: "none" }} />;
        }
    };

    toTodoList = url => {
        if (url) {
            if (url.indexOf("http://") !== -1 || url.indexOf("https://") !== -1) {
                window.location.href = url;
                return;
            }
            const { mainModule } = this.props.myPublic.appInfo;
            const { push } = this.props.history;
            push(`${mainModule}${url}`);
        }
    };

    render() {
        const {
            loading,
            curCompany = {}, //当前公司
            companyList = [[]], //公司列表
            showSelectCompany, //选择公司的弹窗
            data = {},
            data2 = {},
            topModules
        } = this.state;
        // console.log(topModules)
        const {
            // todoCount = "0", //待审批数量
            // hasTodoCount = "0", //已审批数量
            // noticeCount = "0", //公告数量
            dataInfoiList = {},
        } = data2;
        const { homeLargeModuleList = [] } = data;
        return (
            <Spin spinning={loading}>
                <div className={s.root}>
                    <div className={s.header}>
                        <Flex justify="between" className={s.top}>
                            <Flex.Item className={s.company}>
                                <div className={s.companyCon}>
                                    <img
                                        style={{
                                            width: "30px",
                                            height: "30px",
                                            marginRight: "8px",
                                            verticalAlign: "top"
                                        }}
                                        src={imgs.logo}
                                        alt=""
                                    />
                                    <span onClick={this.showCompanyModal}>
                                        {curCompany.label}
                                    </span>
                                    {companyList && companyList[0] &&
                                        (companyList[0].length === 1 ||
                                            companyList[0].length === 0) ? null : (
                                            <Icon type="down" size={"md"} />
                                        )}
                                </div>
                            </Flex.Item>
                            <Flex.Item className={s.qrcode}>
                                <img
                                    onClick={this.qrcodeClick}
                                    src={imgs.sao_miao}
                                    alt="qrcode"
                                />
                            </Flex.Item>
                        </Flex>
                        <Flex justify="between" className={s.countCon}>

                            {
                                topModules.map((item,index) => {
                                    return (<Flex.Item
                                        key={index}
                                        className={s.headerItem}
                                        onClick={() => {
                                            this.toTodoList(item.smallModuleLink);
                                        }}
                                    >
                                        <div className={s.num}>{dataInfoiList[item.smallModuleId]}</div>
                                        <div>{item.smallModuleTitle}</div>
                                    </Flex.Item>)
                                })
                            }

                        </Flex>
                    </div>
                    {/* 模块 */}
                    <div className={s.moduesCon}>
                        {homeLargeModuleList.map(
                            (
                                {
                                    largeModuleType, //1:审批模块；3、2:新闻模式  5温馨提示
                                    largeModuleTitle, //大模块标题 
                                    sysWoaSmallModuleList //小模块数据
                                },
                                index
                            ) => {
                                //可以出现 8类型是顶部的数字模块 不能渲染只能固定写死
                                const haveType =
                                    largeModuleType === "1" ||
                                    largeModuleType === "2" ||
                                    largeModuleType === "3" ||
                                    largeModuleType === "5";
                                if (!haveType) {
                                    return <span key={index} />;
                                }

                                //公告提示索引 因为公告样式区别于顶部模快和下面的模块
                                let _i = 1;

                                return (
                                    <div key={index}>
                                        {index === _i ? null : <WhiteSpace />}
                                        <div
                                            className={`${s.moduleBlank}  ${
                                                largeModuleType === "5" &&
                                                    index === _i
                                                    ? s.hint
                                                    : null
                                                }`}
                                            style={{
                                                paddingTop:
                                                    (largeModuleTitle &&
                                                        largeModuleType !==
                                                        "3") ||
                                                        (largeModuleType === "5" &&
                                                            index === _i)
                                                        ? 0
                                                        : 8
                                            }}
                                        >
                                            {!largeModuleTitle ||
                                                largeModuleType === "3" ||
                                                largeModuleType === "5" ? null : (
                                                    <div className={s.tit}>
                                                        {largeModuleTitle}
                                                    </div>
                                                )}
                                            <div>
                                                {this.moduleTypeSwitch(
                                                    largeModuleType,
                                                    sysWoaSmallModuleList
                                                )}
                                            </div>
                                        </div>
                                    </div>
                                );
                            }
                        )}
                    </div>

                    {/* 选择公司的弹窗 */}
                    <Modal
                        visible={showSelectCompany}
                        transparent
                        onClose={this.cancelChangeCompany}
                        title="切换公司"
                        footer={[
                            { text: "取消",onPress: this.cancelChangeCompany },
                            { text: "确定",onPress: this.changeCompanyOk }
                        ]}
                        wrapProps={{ onTouchStart: this.onWrapTouchStart }}
                    >
                        <div style={{ overflow: "scroll" }}>
                            <PickerView
                                onScrollChange={this.changeCompany}
                                value={[curCompany && curCompany.value]}
                                data={companyList || []}
                                cascade={false}
                                cols={1}
                            />
                        </div>
                    </Modal>
                </div>
            </Spin>
        );
    }
}

function closest(el,selector) {
    const matchesSelector =
        el.matches ||
        el.webkitMatchesSelector ||
        el.mozMatchesSelector ||
        el.msMatchesSelector;
    while (el) {
        if (matchesSelector.call(el,selector)) {
            return el;
        }
        el = el.parentElement;
    }
    return null;
}

export default incTab(Index);
