import LoginPage from './login'
import HomePage from './home'
import { basic } from './modules/layouts'

//流程
import FlowByDFList from './FlowByDF/list'
import FlowByDFForm from './FlowByDF/form'

// qnn-form
import QnnForm from './QnnForm'

//qnn-table
import QnnTable from './QnnTable'

//拉人
import Pullperson from './Pullperson'
import PullpersonMobile from './PullpersonMobile'
import Detail from './home/detail'

const pageComs = {
    "Login": {
        mustLogin: false,
        Com: LoginPage
    },
    "Home": {
        mustLogin: false,
        Com: HomePage
        // Com: HomePage
    },

    //流程页面
    "FlowByDFList": {
        mustLogin: true,
        Com: FlowByDFList
    },
    "FlowByDFForm": {
        mustLogin: true,
        Com: FlowByDFForm
    },

    //qnn-form
    "QnnForm": {
        mustLogin: true,
        Com: basic(QnnForm)
    },
    "QnnFormByMobile": {
        mustLogin: true,
        Com: QnnForm
    },

    //qnn-table
    "QnnTable": {
        mustLogin: true,
        Com: basic(QnnTable)
    },

    //Pullperson
    "Pullperson": {
        mustLogin: true,
        Com: basic(Pullperson)
    },

    //PullpersonMobile
    "PullpersonMobile": {
        mustLogin: true,
        Com: PullpersonMobile
    },
    //detail
    "Detail": {
        mustLogin: false,
        Com: Detail
    },


}
const routerInfo = [
    {
        label: "流程",
        children: [
            {
                label: "流程列表",
                defaultPath: "FlowByDF/zjPartyFeeUse/0",
                pathName: "FlowByDF/:flowId/:status", //流程id 流程类状态（待办已办）
                comKey: "FlowByDFList",
            },
            {
                label: "流程处理",
                defaultPath: "FlowByDF/zjPartyFeeUse/0/0",
                pathName: "FlowByDF/:flowId/:status/:workId/:title", //流程id 流程类状态（待办已办） workId（等于0是创建流程其他是处理流程）
                comKey: "FlowByDFForm",
            }
        ]
    },
    {
        label: "table/from",
        children: [
            {
                label: "表格",
                defaultPath: "qnnTable",
                pathName: "qnnTable",
                comKey: "QnnTable",
            },
            {
                label: "表单测试",
                defaultPath: "qnnForm/0",
                pathName: "qnnForm/:flag",
                comKey: "QnnForm",
            },
            {
                label: "表单测试/移动版",
                defaultPath: "QnnFormByMobile/0",
                pathName: "QnnFormByMobile/:flag",
                comKey: "QnnFormByMobile",
            }
        ]
    }, 
    {
        label: "拉人组件",
        children: [
            {
                label: "pc",
                defaultPath: "Pullperson",
                pathName: "Pullperson",
                comKey: "Pullperson",
            },
            {
                label: "mobile",
                defaultPath: "PullpersonMobile",
                pathName: "PullpersonMobile",
                comKey: "PullpersonMobile",
            }
        ]
    },
    {
        label: "二维码",
        defaultPath: "Detail/0",
        pathName: "Detail/:id",
        comKey: "Detail",
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