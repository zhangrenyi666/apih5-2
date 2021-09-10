import { basicHoc } from "apih5/modules/layouts";
 import LoginPage from './login'
import HomePage from './home'
import VoteList from './VoteList'
import { blank } from './modules/layouts'

//引用basicHoc进行简单封装
const basic = (Component,props = {
    isOpenTodoNum:false,
    //切换项目或者获取项目列表的接口
    apiNames: {
        getCompanyList: "getZxQrcodePermissionObjectListByProject",
        changeCompany: "changeZxQrcodePermissionProject",

        //中交切换公司接口
        // changeCompany: "changeCompany",
    },

    //项目数据的一些字段名字
    projectKeys: {
        list: "projectList",
        value: "levelId",//levelId  projectId
        label: "levelShortName",//levelShortName  projectShortName
    },


    //获取项目列表的方法 getCompanyListFn(cb)   
    //cb(当前项目, 项目列表) 必须执行
    // getCompanyListFn: null

    //中交切换项目案例 该方法存在getCompanyList配置将失去意义
    // getCompanyListFn: (pageProps,cb) => {
    //     let {
    //         loginAndLogoutInfo: {
    //             loginInfo: {
    //                 userInfo: { companyList }
    //             }
    //         }
    //     } = pageProps;
    //     let _curCompany = null;


    //     companyList =
    //         companyList &&
    //         companyList.map(item => {
    //             const { companyName,companyId,companySelectFlag } = item;
    //             let _item = {
    //                 ...item,
    //                 value: companyId,
    //                 realLabel: companyName,
    //                 label: (companyName && companyName.length > 7) ? companyName.substr(0,7) + '...' : companyName
    //             }

    //             if (companySelectFlag === 1) {
    //                 //当前选择公司
    //                 _curCompany = _item;
    //             }
    //             return {
    //                 ..._item
    //             };
    //         });

    //     if (!_curCompany && companyList && companyList.length) {
    //         _curCompany = companyList[0];
    //     }
    //     cb(_curCompany,companyList);
    // }

}) => {
    return basicHoc(props)(Component);
}
const pageComs = {
    "Login": {
        mustLogin: false,
        Com: LoginPage
    },
    "Home": {
        mustLogin: true,
        Com: HomePage
    },
    "VoteList": {
        mustLogin: true,
        Com: blank(VoteList)
    }
}

//遍历所有页面强制让页面包裹basic
let noIncs = ["Login"]; //不需要包裹baise的页面key
for (const key in pageComs) {
    if (pageComs.hasOwnProperty(key)) {
        const element = pageComs[key];
        if (!noIncs.includes(key) && element.mustLogin) {
            element.Com = basic(element.Com);
            pageComs[key] = element;
        }
    }
}
const routerInfo = [
    {
        label: "列表页面",
        defaultPath: "VoteList",
        pathName: "VoteList",
        comKey: "VoteList",
    }
]
const reducers = {

}
const actions = {

}
const sagas = [

]
const MyViews = {
    pageComs,
    routerInfo,
    reducers,
    sagas,
    actions,
}
export default MyViews