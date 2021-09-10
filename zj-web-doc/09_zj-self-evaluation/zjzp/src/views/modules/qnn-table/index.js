import React, { PureComponent } from "react";
import { message, Form } from "antd";
import { getParams, getFromParams, getDeviceType } from "./tool";
import QnnForm from "./qnn-form";
import s from "./style.less";
import download from "./tool/download";
import Mobile from "./layout";
import {
    searchComponent,
    modalTree,
    modalForm,
    actionBtns as actionBtnsModel,
    desc as descModel,
    infoAlert as infoAlertModal,
    table as tableModal,
    leftDrawer as leftDrawerModal
} from "./components";
import {
    wran,
    colClick,
    refresh,
    paginationChange,
    rowSelection,
    action,
    toggle,
    search,
    handleReset,
    formConfigClass,
    handleCancel,
    handleOk,
    modalBtnClick,
    openTree,
    closeDrawer,
    selectKey,
    isMobile
} from "./func";
import {
    getFilterConfig,
    getFormConfig,
    getQnnFormModalConfig,
    formItemLayout,
    formItemLayoutBySearch
} from "./config";
const version = (window.SuperTable_version = "test");

class index extends PureComponent {
    constructor(...args) {
        super(...args);
        //执行警告
        wran.bind(this)();

        //操作按钮可为func返回数据即可
        let { actionBtns = [], fetchConfig, formConfig } = this.props;
        if (typeof actionBtns === "function") {
            actionBtns = actionBtns({ ...this.props });
        }
        //fetchConfig可为function
        if (fetchConfig && typeof fetchConfig === "function") {
            fetchConfig = fetchConfig({ ...this.props });
        }
        //formConfig可为function
        if (formConfig && typeof formConfig === "function") {
            formConfig = fetchConfig({ ...this.props });
        }
        this.state = {
            version: version,
            //表格请求配置
            fetchConfig: fetchConfig,
            //所有字段配置
            formConfig: formConfig,
            //表格是否加载中
            loading: true,
            //table数据
            data:
                this.props.data && this.props.data.length > 0
                    ? this.props.data
                    : [],
            //总数据条数
            totalNumber: 0,
            //分页数据
            limit: this.props.pageSize || 10, //每页显示条数
            curPage: this.props.curPage || 1, //当前页

            //分页配置
            paginationConfig: this.props.paginationConfig || {},

            //antd样式配置
            antd: this.props.antd || {},

            //检索条件
            searchParams: {},

            //内置ajax请求时需要带上的
            headers: this.props.headers || {},

            //表头
            columns: [],
            //表单配置
            forms: [],
            //搜索配置
            searchFroms: [],

            //操作按钮
            actionBtns: actionBtns,

            //抽屉的配置
            drawerConfig: this.props.drawerConfig || {},

            //table行选择的配置 暂无使用
            rowSelection: this.props.rowSelection || {},

            //是否显示选择框
            isShowRowSelect:
                this.props.isShowRowSelect === false ? false : true,

            //右边抽屉是否出来状态
            DrawerShow: false,

            //全局文字配置
            labelConfig: this.props.labelConfig || {},
            drawerDetailTitle: "操作", //抽屉左上角文字

            selectedRows: [], //已经选择的行

            //每个表单项的布局 -- 表单
            formItemLayout: this.props.formItemLayout || formItemLayout,
            //每个表单项的布局 -- 搜索区域
            formItemLayoutSearch:
                this.props.formItemLayoutSearch || formItemLayoutBySearch,

            //表格的宽
            tableContentWidth: "100%",

            //全局提示
            infoAlert: this.props.infoAlert,

            //表格上面的描述
            desc: this.props.desc,

            //操作区form类型的form配置
            qnnFormOption: {},
            modalOption: {},

            //抽屉中的自定义组件
            MyComponent: null,

            //搜索form一行有多少个表单项
            searchFormColNum: this.props.searchFormColNum
                ? this.props.searchFormColNum
                : 0
        };

        //内置方法
        this.fetch = this.props.fetch;
        this.myFetch = (apiName, params, success) => {
            this.props.fetch(apiName, params).then(data => {
                success(data);
            });
        };
        this.getDeviceType = getDeviceType;
        this.sFormatData = QnnForm.sFormatData; //qnn-form静态方法

        //内部使用的一些方法
        this.getFilterConfig = getFilterConfig.bind(this);
        this.getFormConfig = getFormConfig.bind(this);
        this.getQnnFormModalConfig = getQnnFormModalConfig.bind(this);
        this.colClick = colClick.bind(this);
        this.refresh = refresh.bind(this);
        this.paginationChange = paginationChange.bind(this);
        this.rowSelection = rowSelection.bind(this);
        this.toggle = toggle.bind(this);
        this.handleReset = handleReset.bind(this);
        this.searchComponent = searchComponent.bind(this);
        this.formConfigClass = formConfigClass.bind(this);
        this.handleCancel = handleCancel.bind(this);
        this.handleOk = handleOk.bind(this);
        this.modalBtnClick = modalBtnClick.bind(this);
        this.openTree = openTree.bind(this);
        this.closeDrawer = closeDrawer.bind(this);
        this.selectKey = selectKey.bind(this);
        this.isMobile = isMobile.bind(this);
        this.getParams = getParams.bind(this);
        this.getFromParams = getFromParams.bind(this);
        this.action = action.bind(this);

        //内置模块
        this.actionBtnsModel = actionBtnsModel.bind(this);
        this.search = search.bind(this);
        this.modalTree = modalTree.bind(this);
        this.modalForm = modalForm.bind(this);
        this.descModel = descModel.bind(this);
        this.infoAlertModal = infoAlertModal.bind(this);
        this.tableModal = tableModal.bind(this);
        this.leftDrawerModal = leftDrawerModal.bind(this);

        //绑定给某些按钮回调的
        this.btnCallbackFn = {
            setState: this.setState,
            fetch: this.myFetch,
            history: this.props.history,
            match: this.props.match,
            openTree: this.openTree, //打开树结构窗口
            msg: message,
            refresh: this.refresh,
            closeDrawer: this.closeDrawer, //打开/关闭右边抽屉的方法 传入true/false
            btnAction: this.action, //fn (rowInfo, rowData)
            props: { ...this.props },
            download: download,
            isMobile: this.isMobile()
        };
    }

    componentDidMount() {
        //请求默认数据
        const { fetchConfig = {} } = this.state;
        const { apiName } = fetchConfig;
        if (fetchConfig && apiName) {
            //请求数据渲染表格 接口名存在才会请求数据渲染
            this.refresh();
        } else {
            this.setState({
                loading: false
            });
        }
        //将数据分类&处理渲染
        this.formConfigClass();
    }

    render() {
        if (this.isMobile()) {
            return <Mobile {...this.props} isInQnnTable={true} />;
        }
        return (
            <div className={s.superTable}>
                {/* 检索 */}
                {this.searchComponent()}

                {/* 操作按钮 */}
                {this.actionBtnsModel()}

                {/* 表格顶部描述 */}
                {this.descModel()}

                {/* 信息弹窗 */}
                {this.infoAlertModal()}

                {/* 数据表格 */}
                {this.tableModal()}

                {/* 右边抽屉 */}
                {this.leftDrawerModal()}

                {/* 选人插件 */}
                {this.modalTree()}

                {/* 弹出的qnn-form模块 */}
                {this.modalForm()}
            </div>
        );
    }
}

const qnnTable = Form.create()(index);
export default qnnTable;
