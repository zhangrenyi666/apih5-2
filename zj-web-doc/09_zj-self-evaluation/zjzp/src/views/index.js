import LoginPage from "./login";
import HomePage from "./home";
import ContactsPage from "./contacts";
import MenusPage from "./menus";
import RolesPage from "./roles";
import { basic } from "./modules/layouts";
import UserCountListByReviewer from "./userCountListByReviewer";
const pageComs = {
    /* 共通页面 */
    Login: {
        mustLogin: false,
        Com: LoginPage
    },
    Home: {
        mustLogin: true,
        Com: basic(UserCountListByReviewer)
    },
    Contacts: {
        mustLogin: true,
        Com: ContactsPage
    },
    Menus: {
        mustLogin: true,
        Com: MenusPage
    },
    Roles: {
        mustLogin: true,
        Com: RolesPage
    },
    UserCountListByReviewer: {
        mustLogin: true,
        Com: basic(UserCountListByReviewer)
    }
};
const routerInfo = [
    {
        label: "信息化考核统计列表",
        defaultPath: "/",
        pathName: "/",
        comKey: "UserCountListByReviewer" 
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
