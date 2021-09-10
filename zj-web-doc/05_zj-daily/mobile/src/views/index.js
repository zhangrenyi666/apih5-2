import React from 'react';
import WeeklyListPage from './weekly/WeeklyList'
import WeeklyDetailPage from './weekly/WeeklyDetail'
const pageComs = {
    "Home": {
        mustLogin: true,
        Com: () => <div />
    },
    "WeeklyList": {
        mustLogin: true,
        Com: WeeklyListPage
    },
    "WeeklyDetail": {
        mustLogin: true,
        Com: WeeklyDetailPage
    }
}




const routerInfo = [
    {
        name: "投资项目列表",
        pathName: "/",
        roles: {
            "admin": {
                visible: true,
                enable: true
            },
            "user": {
                visible: false,
                enable: true
            }
        },
        comKey: "Home"
    },
    {
        name: "投资项目列表",
        pathName: "weekly/:listType/:sendMsgId/:startDate/:endDate",
        roles: {
            "admin": {
                visible: true,
                enable: true
            },
            "user": {
                visible: false,
                enable: true
            }
        },
        comKey: "WeeklyList"
    },
    {
        name: "投资项目详情",
        pathName: "weeklyDetail/:companyId/:startDate/:endDate",
        roles: {
            "admin": {
                visible: true,
                enable: true
            },
            "user": {
                visible: false,
                enable: true
            }
        },
        comKey: "WeeklyDetail"
    },
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