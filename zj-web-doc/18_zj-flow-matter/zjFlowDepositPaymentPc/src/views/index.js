import { lazy } from "react"
import { basicHoc } from "apih5/modules/layouts";
const LoginPage = lazy(() => import("./login"));
const HomePage = lazy(() => import("./home"));
const ContactsPage = lazy(() => import("./contacts"));
const MenusPage = lazy(() => import("./menus"));
const RolesPage = lazy(() => import("./roles"));
const FlowType = lazy(() => import("./FlowType"));
const TrainList = lazy(() => import("./TrainList"));
const BaseCode = lazy(() => import("./BaseCode"));
const InterFaceManage = lazy(() => import('./InterFaceManage'));
const FlowByDepositPayment = lazy(() => import("./FlowByDepositPayment"));
const FlowByDepositPaymentForJg = lazy(() => import("./FlowByDepositPaymentForJg"));

const FlowByDepositPaymentForm = lazy(() => import("./FlowByDepositPayment/form"));
const FlowByDepositPaymentForJgForm = lazy(() => import("./FlowByDepositPaymentForJg/form"));

const MarginBook = lazy(() => import('./marginBook'));

const FlowBygszlhzApply = lazy(() => import("./FlowBygszlhzApply"));
const FlowByzlhzApply = lazy(() => import("./FlowByzlhzApply"));

const FlowBygszlhzApplyForm = lazy(() => import("./FlowBygszlhzApply"));
const FlowByzlhzApplyForm = lazy(() => import("./FlowByzlhzApply"));

const FlowBystCcPurchase = lazy(() => import("./FlowBystCcPurchase"));
const FlowBystCcPurchaseForm = lazy(() => import("./FlowBystCcPurchase/form"));
//引用basicHoc进行简单封装
const basic = (Component, props = {

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
    FlowType: {
        mustLogin: true,
        Com: FlowType
    },
    TrainList: {
        mustLogin: true,
        Com: TrainList
    },
    BaseCode: {
        mustLogin: true,
        Com: BaseCode
    },
    InterFaceManage: {
        mustLogin: true,
        Com: InterFaceManage
    },
    /* 共通页面over */
    FlowByDepositPayment: {
        mustLogin: true,
        Com: FlowByDepositPayment
    },
    FlowByDepositPaymentForm: {
        mustLogin: true,
        Com: FlowByDepositPaymentForm
    },
    FlowByDepositPaymentForJg: {
        mustLogin: true,
        Com: FlowByDepositPaymentForJg
    },
    FlowByDepositPaymentForJgForm: {
        mustLogin: true,
        Com: FlowByDepositPaymentForJgForm
    },
    MarginBook: {
        mustLogin: true,
        Com: MarginBook
    },
    FlowBygszlhzApply: {
        mustLogin: true,
        Com: FlowBygszlhzApply
    },
    FlowBygszlhzApplyForm: {
        mustLogin: true,
        Com: FlowBygszlhzApplyForm
    },
    FlowByzlhzApply: {
        mustLogin: true,
        Com: FlowByzlhzApply
    },
    FlowByzlhzApplyForm: {
        mustLogin: true,
        Com: FlowByzlhzApplyForm
    },
    FlowBystCcPurchase: {
        mustLogin: true,
        Com: FlowBystCcPurchase
    },
    FlowBystCcPurchaseForm: {
        mustLogin: true,
        Com: FlowBystCcPurchaseForm
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
        label: "案例",
        children: [
            {
                label: "保证金申请",
                defaultPath: "FlowByDepositPayment/DepositPayment/add/0",
                pathName: "FlowByDepositPayment/:flowId/:workId/:title",
                comKey: "FlowByDepositPaymentForm",
            },
            {
                label: "待办==保证金申请",
                defaultPath: "todoByDepositPayment",
                pathName: "todoByDepositPayment",
                comKey: "FlowByDepositPayment"
            },
            {
                label: "已办==保证金申请",
                defaultPath: "hasTodoByDepositPayment",
                pathName: "hasTodoByDepositPayment",
                comKey: "FlowByDepositPayment"
            },
            {
                label: "保证金台账列表",
                defaultPath: "MarginBook",
                pathName: "MarginBook",
                comKey: "MarginBook"
            }
        ]
    },
    // {
    //     label: "战略合作框架协议、联合体协议评审",
    //     children: [
    //         {
    //             label: "待办",
    //             defaultPath: "todoByzlhzApply",
    //             pathName: "todoByzlhzApply",
    //             comKey: "FlowByzlhzApply"
    //         },
    //         {
    //             label: "已办",
    //             defaultPath: "hasTodoByzlhzApply",
    //             pathName: "hasTodoByzlhzApply",
    //             comKey: "FlowByzlhzApply"
    //         },
    //     ]
    // },
    // {
    //     label: "出差申请",
    //     children: [
    //         {
    //             label: "待办",
    //             defaultPath: "todoBystCcPurchase",
    //             pathName: "todoBystCcPurchase",
    //             comKey: "FlowBystCcPurchase"
    //         },
    //         {
    //             label: "已办",
    //             defaultPath: "hasTodoBystCcPurchase",
    //             pathName: "hasTodoBystCcPurchase",
    //             comKey: "FlowBystCcPurchase"
    //         },
    //     ]
    // },
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
