import LoginPage from "./login";
import HomePage from "./home";
//会议列表
import Principles from './Principles';
import signInss from './Principles/signIns';
import largeSignIns from './Principles/largeSignIn';
//确认
import affirm from './affirm';
import successAf from './affirm/success';
//签到
import signIn from './signIn';
import successIn from './signIn/success';
//报名
import signUp from './signUp';
import successUp from './signUp/success';
//会议室
import meetingRoom from './meetingRoom';
//列表
import Principlelist from './Principlelist';
import signInList from './Principlelist/signInList';
import largeSignList from './Principlelist/largeSignList';
import signInDetail from './Principles/signInDetail';
import signInsDetail from './Principles/signInsDetail';

import { basicHoc } from "apih5/modules/layouts";

//年会议审批列表
import worldYouthMeeting from './worldYouthMeeting';
import worldYouthMeetingDetail from './worldYouthMeeting/detail';
//引用basicHoc进行简单封装
const basic = (Component,props = {

    //切换项目或者获取项目列表的接口
    apiNames: {
        // getCompanyList: "getZxQrcodePermissionObjectListByProject",
        // changeCompany: "changeZxQrcodePermissionProject",

        //中交切换公司接口
        changeCompany: "changeCompany",
    },

    //项目数据的一些字段名字
    projectKeys: {
        list: "companyList",
        value: "companyId",//levelId  projectId
        label: "companyName",//levelShortName  projectShortName
    },


    //获取项目列表的方法 getCompanyListFn(cb)   
    //cb(当前项目, 项目列表) 必须执行
    // getCompanyListFn: null

    //中交切换项目案例 该方法存在getCompanyList配置将失去意义
    getCompanyListFn: (pageProps,cb) => {
        let {
            loginAndLogoutInfo: {
                loginInfo: {
                    userInfo: { companyList }
                }
            }
        } = pageProps;
        let _curCompany = null;


        companyList =
            companyList &&
            companyList.map(item => {
                const { companyName,companyId,companySelectFlag } = item;
                let _item = {
                    ...item,
                    value: companyId,
                    realLabel: companyName,
                    label: (companyName && companyName.length > 7) ? companyName.substr(0,7) + '...' : companyName
                }

                if (companySelectFlag === 1) {
                    //当前选择公司
                    _curCompany = _item;
                }
                return {
                    ..._item
                };
            });

        if (!_curCompany && companyList && companyList.length) {
            _curCompany = companyList[0];
        }
        cb(_curCompany,companyList);
    }

}) => {
    return basicHoc(props)(Component);
}

const pageComs = {
    /* 共通页面start */
    Login: {
        mustLogin: false,
        Com: LoginPage
    },
    Home: {
        mustLogin: true,
        Com: HomePage
    },
    Principles: {
        mustLogin: true,
        Com: Principles
    },
    signInss: {
        mustLogin: true,
        Com: signInss
    },
    largeSignIns: {
        mustLogin: true,
        Com: largeSignIns
    },
    signInDetail: {
        mustLogin: true,
        Com: signInDetail
    },
    signInsDetail: {
        mustLogin: true,
        Com: signInsDetail
    },
    signIn: {
        mustLogin: true,
        Com: signIn
    },
    successIn: {
        mustLogin: true,
        Com: successIn
    },
    affirm: {
        mustLogin: true,
        Com: affirm
    },
    successAf: {
        mustLogin: true,
        Com: successAf
    },
    signUp: {
        mustLogin: true,
        Com: signUp
    },
    successUp: {
        mustLogin: true,
        Com: successUp
    },
    meetingRoom: {
        mustLogin: true,
        Com: meetingRoom
    },
    Principlelist: {
        mustLogin: true,
        Com: Principlelist
    },
    signInList: {
        mustLogin: true,
        Com: signInList
    },
    largeSignList: {
        mustLogin: true,
        Com: largeSignList
    },
    worldYouthMeeting: {
        mustLogin: true,
        Com: worldYouthMeeting
    },
    worldYouthMeetingDetail: {
        mustLogin: true,
        Com: worldYouthMeetingDetail
    }

};

//遍历所有页面强制让页面包裹basic
//不需要包裹baise的页面key
let noIncs = ["Login"];
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
        label: "参会列表",
        defaultPath: "Principles",
        pathName: "Principles",
        comKey: "Principles",
    },
    {
        label: "人员签到",
        defaultPath: "signInss/0/0/0",
        pathName: "signInss/:reservationsId/:loginMobile/:flag",
        comKey: "signInss",
    },
    {
        label: "人员报名",
        defaultPath: "largeSignIns/0/0/0",
        pathName: "largeSignIns/:reservationsId/:loginMobile/:flag",
        comKey: "largeSignIns",
    },
    {
        label: "会议详情",
        defaultPath: "signInDetail/0/0/0/0",
        pathName: "signInDetail/:reservationsId/:loginMobile/:page/:flag",
        comKey: "signInDetail",
    },
    {
        label: "未参加",
        defaultPath: "signInsDetail/0/0/0/0",
        pathName: "signInsDetail/:reservationsId/:loginMobile/:page/:flag",
        comKey: "signInsDetail",
    },
    {
        label: "人员报名",
        defaultPath: "signUp/0/0",
        pathName: "signUp/:reservationsId/:flag",
        comKey: "signUp",
    },
    {
        label: "报名成功",
        defaultPath: "successUp",
        pathName: "successUp",
        comKey: "successUp",
    },
    {
        label: "会议确认",
        defaultPath: "affirm/0/0",
        pathName: "affirm/:reservationsId/:flag",
        comKey: "affirm",
    },
    {
        label: "确认成功",
        defaultPath: "successAf",
        pathName: "successAf",
        comKey: "successAf",
    },
    {
        label: "人员签到",
        defaultPath: "signIn/0/0",
        pathName: "signIn/:reservationsId/:flag",
        comKey: "signIn",
    },
    {
        label: "签到成功",
        defaultPath: "successIn",
        pathName: "successIn",
        comKey: "successIn",
    },
    {
        label: "会议室详情",
        defaultPath: "meetingRoom",
        pathName: "meetingRoom",
        comKey: "meetingRoom",
    },
    {
        label: "列表",
        defaultPath: "Principlelist/0",
        pathName: "Principlelist/:page",
        comKey: "Principlelist",
    },
    {
        label: "人员签到",
        defaultPath: "signInList/0/0/0/0",
        pathName: "signInList/:reservationsId/:loginMobile/:page/:flag",
        comKey: "signInList",
    },
    {
        label: "人员报名",
        defaultPath: "largeSignList/0/0/0/0",
        pathName: "largeSignList/:reservationsId/:loginMobile/:page/:flag",
        comKey: "largeSignList",
    },
    {
        label: "审批管理",
        defaultPath: "worldYouthMeeting",
        pathName: "worldYouthMeeting",
        comKey: "worldYouthMeeting",
    },
    {
        label: "审批管理详情",
        defaultPath: "worldYouthMeetingDetail/0/0/0",
        pathName: "worldYouthMeetingDetail/:fallInDeptId/:fallInYear/:approvalState",
        comKey: "worldYouthMeetingDetail",
    }
];
const reducers = {
    ft(state = {}, action) {
        if(action.type === 'FT'){
            return action
        }else{
            return state
        }
    }
}
const actions = {
    fs(fs) {
        return { type: 'FT', fs }
    }
}
const sagas = [];
const MyViews = {
    pageComs,
    routerInfo,
    reducers,
    sagas,
    actions
};
export default MyViews;
