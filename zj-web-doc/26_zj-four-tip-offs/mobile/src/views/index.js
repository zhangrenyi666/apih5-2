import LoginPage from "./login";
import Home from "./home";

import HomePage from "./homePage";
import Me from "./me";
import Exposure from "./exposure";
import processeList from "./processeList";
import ProcesseDetail from "./processeList/processeDetail";
import ProcesseDetailA from "./processeList/processeDetailA";
import ProcesseDetailW from "./processeList/processeDetailW";
import RealName from "./realName";
import Anonymity from "./anonymity";
import RealNameW from "./realNameW";
import AnonymityW from "./anonymityW";
import RealNameA from "./realNameA";
import AnonymityA from "./anonymityA";
import Serve from "./serve";
import RealNameListJ from "./me/realNameListJ";
import AnonymityListJ from "./me/AnonymityListJ";
import RealNameListJDetail from "./me/realNameListJDetail";
import RealNameListW from "./me/realNameListW";
import AnonymityListW from "./me/AnonymityListW";
import RealNameListWDetail from "./me/realNameListWDetail";
import RealNameListA from "./me/realNameListA";
import AnonymityListA from "./me/AnonymityListA";
import RealNameListADetail from "./me/realNameListADetail";
import ServeList from "./me/serveList";
import ServeDetail from "./me/serveDetail";
import ServeListDetail from "./me/serveList/serveListDetail";
import BonusList from "./me/bonusList";
const pageComs = {
    /* 共通页面start */
    Login: {
        mustLogin: false,
        Com: LoginPage
    },
    Home: {
        mustLogin: true,
        Com: Home
    },
    HomePage: {
        mustLogin: true,
        Com: HomePage
    },
    Me: {
        mustLogin: true,
        Com: Me
    },
    processeList: {
        mustLogin: true,
        Com: processeList
    },
    ProcesseDetail: {
        mustLogin: true,
        Com: ProcesseDetail
    },
    ProcesseDetailA: {
        mustLogin: true,
        Com: ProcesseDetailA
    },
    ProcesseDetailW: {
        mustLogin: true,
        Com: ProcesseDetailW
    },
    Exposure: {
        mustLogin: true,
        Com: Exposure
    },
    RealName: {
        mustLogin: true,
        Com: RealName
    },
    Anonymity: {
        mustLogin: true,
        Com: Anonymity
    },
    RealNameW: {
        mustLogin: true,
        Com: RealNameW
    },
    AnonymityW: {
        mustLogin: true,
        Com: AnonymityW
    },
    RealNameA: {
        mustLogin: true,
        Com: RealNameA
    },
    AnonymityA: {
        mustLogin: true,
        Com: AnonymityA
    },
    Serve: {
        mustLogin: true,
        Com: Serve
    },
    RealNameListJ: {
        mustLogin: true,
        Com: RealNameListJ
    },
    AnonymityListJ: {
        mustLogin: true,
        Com: AnonymityListJ
    },
    RealNameListJDetail: {
        mustLogin: true,
        Com: RealNameListJDetail
    },
    RealNameListW: {
        mustLogin: true,
        Com: RealNameListW
    },
    AnonymityListW: {
        mustLogin: true,
        Com: AnonymityListW
    },
    RealNameListWDetail: {
        mustLogin: true,
        Com: RealNameListWDetail
    },
    RealNameListA: {
        mustLogin: true,
        Com: RealNameListA
    },
    AnonymityListA: {
        mustLogin: true,
        Com: AnonymityListA
    },
    RealNameListADetail: {
        mustLogin: true,
        Com: RealNameListADetail
    },
    ServeList: {
        mustLogin: true,
        Com: ServeList
    },
    ServeListDetail: {
        mustLogin: true,
        Com: ServeListDetail
    },
    ServeDetail: {
        mustLogin: true,
        Com: ServeDetail
    },
    BonusList: {
        mustLogin: true,
        Com: BonusList
    }
};
const routerInfo = [
    {
        label: "主页",
        children: [
            {
                label: "首页",
                defaultPath: "HomePage/0",
                pathName: "HomePage/:key",
                comKey: "HomePage",
                children:[
                    {
                        label: "解决页面",
                        defaultPath: "processeList/0",
                        pathName: "processeList/:page",
                        comKey: "processeList"
                    },
                    {
                        label: "技术质量解决详情",
                        defaultPath: "ProcesseDetail/0/0",
                        pathName: "ProcesseDetail/:reportId/:page",
                        comKey: "ProcesseDetail"
                    },
                    {
                        label: "违规违纪解决详情",
                        defaultPath: "ProcesseDetailW/0/0",
                        pathName: "ProcesseDetailW/:reportId/:page",
                        comKey: "ProcesseDetailW"
                    },
                    {
                        label: "安全隐患解决详情",
                        defaultPath: "ProcesseDetailA/0/0",
                        pathName: "ProcesseDetailA/:reportId/:page",
                        comKey: "ProcesseDetailA"
                    },
                    {
                        label: "曝光页面",
                        defaultPath: "Exposure",
                        pathName: "Exposure",
                        comKey: "Exposure"
                    },
                    {
                        label: "技术质量实名举报",
                        defaultPath: "RealName",
                        pathName: "RealName",
                        comKey: "RealName"
                    },
                    {
                        label: "技术质量匿名举报",
                        defaultPath: "Anonymity",
                        pathName: "Anonymity",
                        comKey: "Anonymity"
                    },
                    {
                        label: "违规违纪实名举报",
                        defaultPath: "RealNameW",
                        pathName: "RealNameW",
                        comKey: "RealNameW"
                    },
                    {
                        label: "违规违纪匿名举报",
                        defaultPath: "AnonymityW",
                        pathName: "AnonymityW",
                        comKey: "AnonymityW"
                    },
                    {
                        label: "安全隐患实名举报",
                        defaultPath: "RealNameA",
                        pathName: "RealNameA",
                        comKey: "RealNameA"
                    },
                    {
                        label: "安全隐患匿名举报",
                        defaultPath: "AnonymityA",
                        pathName: "AnonymityA",
                        comKey: "AnonymityA"
                    },
                    {
                        label: "服务",
                        defaultPath: "Serve",
                        pathName: "Serve",
                        comKey: "Serve"
                    }
                ]
            },
            {
                label: "个人中心",
                defaultPath: "Me",
                pathName: "Me",
                comKey: "Me",
                children:[
                    {
                        label: "技术质量举报列表",
                        defaultPath: "RealNameListJ",
                        pathName: "RealNameListJ",
                        comKey: "RealNameListJ",
                        children:[
                            {
                                label: "举报详情",
                                defaultPath: "RealNameListJDetail/0",
                                pathName: "RealNameListJDetail/:reportId",
                                comKey: "RealNameListJDetail"
                            },
                        ]
                    },
                    {
                        label: "技术质量举报列表",
                        defaultPath: "AnonymityListJ",
                        pathName: "AnonymityListJ",
                        comKey: "AnonymityListJ",
                    },
                    {
                        label: "违规违纪举报列表",
                        defaultPath: "RealNameListW",
                        pathName: "RealNameListW",
                        comKey: "RealNameListW",
                        children:[
                            {
                                label: "举报详情",
                                defaultPath: "RealNameListWDetail/0",
                                pathName: "RealNameListWDetail/:reportId",
                                comKey: "RealNameListWDetail"
                            },
                        ]
                    },
                    {
                        label: "违规违纪举报列表",
                        defaultPath: "AnonymityListW",
                        pathName: "AnonymityListW",
                        comKey: "AnonymityListW",
                    },
                    {
                        label: "安全隐患举报列表",
                        defaultPath: "RealNameListA",
                        pathName: "RealNameListA",
                        comKey: "RealNameListA",
                        children:[
                            {
                                label: "举报详情",
                                defaultPath: "RealNameListADetail/0",
                                pathName: "RealNameListADetail/:reportId",
                                comKey: "RealNameListADetail"
                            },
                        ]
                    },
                    {
                        label: "安全隐患举报列表",
                        defaultPath: "AnonymityListA",
                        pathName: "AnonymityListA",
                        comKey: "AnonymityListA",
                    },
                    {
                        label: "服务列表",
                        defaultPath: "ServeList",
                        pathName: "ServeList",
                        comKey: "ServeList",
                        children:[
                            {
                                label: "服务详情",
                                defaultPath: "ServeListDetail/0",
                                pathName: "ServeListDetail/:feedbackId",
                                comKey: "ServeListDetail"
                            },
                        ]
                    },
                    {
                        label: "奖金列表",
                        defaultPath: "BonusList",
                        pathName: "BonusList",
                        comKey: "BonusList",
                    },
                    {
                        label: "服务详情",
                        defaultPath: "ServeDetail/0",
                        pathName: "ServeDetail/:feedbackId",
                        comKey: "ServeDetail",
                    },
                ]
            },
        ]
    }
];
const reducers = {};
const actions = {};
const sagas = [];
const MyViews = {
    pageComs,
    routerInfo,
    reducers,
    sagas,
    actions
};
export default MyViews;
