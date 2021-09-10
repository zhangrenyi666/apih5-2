import LoginPage from './login'
import GualityPage from './quality/Index'
import ProcedurePage from './quality/Procedure'
import ReservePage from './quality/Reserve'

import EnquiryIndexPage from './enquiry/EnquiryIndex'
import EnquiryEditPage from './enquiry/EnquiryEdit'
import EnquiryListPage from './enquiry/EnquiryList'
import EnquiryDetailPage from './enquiry/EnquiryDetail'

import GuidanceListPage from './information/GuidanceList'
import GuidanceDetailPage from './information/GuidanceDetail'
import SpecListPage from './information/SpecList'


import OrgListPage from './orgManage/OrgList'
import OrgDetailPage from './orgManage/OrgDetail'
import OrgCardPage from './orgManage/OrgCard'
import OrgRegisterPage from './orgManage/OrgRegister'


const pageComs = {
    "Login": {
        mustLogin: false,
        Com: LoginPage
    },
    "GualityPage": {
        mustLogin: true,
        Com: GualityPage
    },
    "Procedure": {
        mustLogin: true,
        Com: ProcedurePage
    },
    "Reserve": {
        mustLogin: true,
        Com: ReservePage
    },
    "EnquiryEdit": {
        mustLogin: true,
        Com: EnquiryEditPage
    },
    "EnquiryIndex": {
        mustLogin: true,
        Com: EnquiryIndexPage
    },
    "EnquiryList": {
        mustLogin: true,
        Com: EnquiryListPage
    },
    "EnquiryDetail": {
        mustLogin: true,
        Com: EnquiryDetailPage
    },
    "GuidanceList": {
        mustLogin: true,
        Com: GuidanceListPage
    },
    "GuidanceDetail": {
        mustLogin: true,
        Com: GuidanceDetailPage
    },
    "SpecList": {
        mustLogin: true,
        Com: SpecListPage
    },
    "OrgList": {
        mustLogin: true,
        Com: OrgListPage
    },
    "OrgDetail": {
        mustLogin: true,
        Com: OrgDetailPage
    },
    "OrgCard": {
        mustLogin: true,
        Com: OrgCardPage
    },
    "OrgRegister": {
        mustLogin: true,
        Com: OrgRegisterPage
    }, 
}




const routerInfo = [
    {
        name: "技术质量支撑平台",
        pathName: "/",
        children: [
            {
                name: "首页",
                pathName: "quality",
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
                comKey: "GualityPage"
            },
            {
                name: "实时报验",
                pathName: "procedure",
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
                comKey: "Procedure"
            },
            {
                name: "技术准备",
                pathName: "reserve",
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
                comKey: "Reserve"
            },
        ]
    },
    {
        name: "我的问询",
        pathName: "enquiry",
        children: [
            {
                name: "我的待办",
                pathName: "enquiry/index",
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
                comKey: "EnquiryIndex"
            },
            {
                name: "问询新增",
                pathName: "enquiry/add/:enquiryDiff",
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
                comKey: "EnquiryEdit"
            },
            {
                name: "问询列表",
                pathName: "enquiry/list/:stateFlag",
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
                comKey: "EnquiryList"
            },
            {
                name: "问询详情",
                pathName: "enquiry/detail/:enquiryId/:stateFlag",
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
                comKey: "EnquiryDetail"
            }
        ]
    },
    {
        name: "资料",
        pathName: "information",
        children: [
            {
                name: "参考资料",
                pathName: "information/guidanceList",
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
                comKey: "GuidanceList"
            },
            {
                name: "参考资料详情",
                pathName: "information/guidanceDetail/:infoId",
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
                comKey: "GuidanceDetail"
            },
            {
                name: "规范/施工手册",
                pathName: "information/specList/:specType",
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
                comKey: "SpecList"
            }

        ]
    },
    {
        name: "部门管理",
        children: [
            {
                name: "部门列表",
                pathName: "orgList",
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
                comKey: "OrgList"
            },
            {
                name: "部门详情",
                pathName: "orgDetail/:orgId",
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
                comKey: "OrgDetail"
            },
            {
                name: "部门二维码",
                pathName: "orgCard/:orgId",
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
                comKey: "OrgCard"
            },
            {
                name: "注册",
                pathName: "orgRegister/:isInit",
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
                comKey: "OrgRegister"
            },
            
            
        ]
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