import LoginPage from "./login";
import HomePage from "./home";

const pageComs = {
    /* 共通页面start */
    Login: {
        mustLogin: false,
        Com: LoginPage
    },
    Home: {
        mustLogin: true,
        Com: HomePage
    }
};
const routerInfo = [
    {
        label: "首页",
        children: [
            {
                label: "首页",
                defaultPath: "Home",
                pathName: "Home",
                comKey: "Home"
            }
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
