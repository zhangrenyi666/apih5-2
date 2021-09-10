import React,{ PureComponent } from "react";
import { message,Form,Modal } from "antd";
import {
    getParams,
    getTableFetchParams,
    getFromParams,
    getDeviceType
} from "./tool";
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
    leftDrawer as leftDrawerModal,
    topInfoAlert as TopInfoAlert
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
    isMobile,
    isNeedRefresh,
    setSelectedRows,
    getBackEndConfigFn,
    setBtnsLoading
} from "./func";
import {
    getFilterConfig,
    getFormConfig,
    getQnnFormModalConfig,
    formItemLayout,
    formItemLayoutBySearch,
    editTableConfig
} from "./config";
const version = (window.SuperTable_version = "test");

const confirm = Modal.confirm;
// const Msg = message;

class index extends PureComponent {

    //初始化为卸载状态
    isMounted = false; 

    static defaultProps = {
        tabs: [],
        formIsChangeedAlertTextContent: {
            title: <>温馨提示</>,
            body: <>当前内容已被编辑，您是否保存？</>,
        },
    };
    static getDerivedStateFromProps(props,state) {
        let obj = {
            ...state,
            ...props
        };

        const oFetchConfig = state.fetchConfig;
        const { fetchConfig,getBackEndConfig,formConfig } = props;
        //fetchConfig改变后需要刷新数据
        //进行比较 判断是否需要刷新
        if (
            !getBackEndConfig &&
            typeof fetchConfig !== "function" &&
            JSON.stringify(oFetchConfig) !== JSON.stringify(fetchConfig)
        ) {
            obj.curPage = 1; //刷新参数改后需要清空当前页数据
            obj.isNeedRefresh = true;
        }

        //开发者模式下不使用props中传入的配置
        if (props.edit) {
            obj = {
                ...obj,
                ...editTableConfig,
                // isNeedClassifyData: true
            };
        }

        //判断是否需要重新分类
        if (
            !getBackEndConfig &&
            typeof formConfig !== "function" &&
            props.formConfig && state.formConfig
        ) {
            if ((props.formConfig.length !== state.formConfig.length) && !obj.isNeedClassifyData) {
                obj.isNeedClassifyData = true;
            }
        }

        return obj;
    }

    constructor(...args) {
        super(...args);
        //执行警告
        wran.bind(this)();

        //操作按钮可为func返回数据即可
        let { actionBtns = [],fetchConfig,formConfig } = this.props;
        if (typeof actionBtns === "function") {
            actionBtns = actionBtns({
                ...this.props,
                btnCallbackFn: this.btnCallbackFn
            });
        }
        //fetchConfig可为function
        if (fetchConfig && typeof fetchConfig === "function") {
            fetchConfig = fetchConfig({
                ...this.props,
                btnCallbackFn: this.btnCallbackFn
            });
        }
        //formConfig可为function
        if (formConfig && typeof formConfig === "function") {
            formConfig = formConfig({
                ...this.props,
                btnCallbackFn: this.btnCallbackFn
            });
        }
        this.state = {
            version: version,
            //统一接口配置
            apis: this.props.apis,
            //表格请求配置
            fetchConfig: fetchConfig,
            //所有字段配置
            formConfig: formConfig,
            //tabs配置
            tabs: this.props.tabs,
            //tabs的激活项索引
            tabsIndex: "0",
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
            paginationConfig: this.props.paginationConfig,

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
            //操作按钮位置
            actionBtnsPosition: this.props.actionBtnsPosition,

            //抽屉的配置
            drawerConfig: this.props.drawerConfig || {},

            //table行选择的配置 暂无使用
            rowSelection: this.props.rowSelection || {},

            //是否显示选择框
            isShowRowSelect:
                this.props.isShowRowSelect === false ? false : true,

            //右边抽屉是否出来状态
            DrawerShow: this.props.DrawerShow || false,

            //是否需要刷新 首次刷新不需要管这个数据是啥
            isNeedRefresh: false,

            //是否需要进行将传入的数据进行分类 
            isNeedClassifyData: false,

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
                : 0,
            disabled: this.props.disabled, //表格是否可被编辑
            clickCb: {}, //当前点击行的行数据 和一些其他行信息

            //表格是否是编辑状态
            edit: this.props.edit,
            //编辑的数据  最终导出的数据
            editData: {
                formConfig: []
            },

            //是否开启第一行搜索
            firstRowIsSearch: this.props.firstRowIsSearch,

            //获取后端配置
            getBackEndConfig: this.props.getBackEndConfig,

            //自定义渲染表格行
            diyTableRow: this.props.diyTableRow,

            //获取getRowSelection配置
            getRowSelection: this.props.getRowSelection,

            //点击阴影是否关闭抽屉
            maskClosable: true,

            //正在请求的按钮 包括抽屉和表格中的按钮
            //有正在请求的按钮就将按钮push到数组，请求完后就删除即可
            loadingBtnsByField: [],

            //同loadingBtnsByField一样只是这个是用于设置禁用
            disabledBtnsByField: [],

            //抽屜中的表单是否全部禁用
            formGlobalDisabledStatus: false,

            //抽屉中自定义页面的按钮
            drawerBtnsByComponent: [],

            //表单是否被输入过提示弹出样式 0（modal） | 1（顶部滑出）
            formIsChangeedAlertModalType: this.props.formIsChangeedAlertModalType || "0",

            //被输入过的的提示文字 reactDom
            formIsChangeedAlertTextContent: this.props.formIsChangeedAlertTextContent,

            //表单是否被输入过
            formIsChangeed: false,

            //当前被输入过的的提示文字 reactDom
            formIsChangeedAlertText: null
        };

        //内置方法
        this.fetch = this.props.fetch || this.props.myFetch;
        this.myFetch = (apiName,params,success) => {
            this.fetch(apiName,params).then(data => {
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
        this.getTableFetchParams = getTableFetchParams.bind(this);
        this.getFromParams = getFromParams.bind(this);
        this.action = action.bind(this);
        this.setTabsIndex = index => this.setState({ tabsIndex: index });
        this.isNeedRefresh = isNeedRefresh.bind(this);
        this.setSelectedRows = setSelectedRows.bind(this);
        this.getBackEndConfigFn = getBackEndConfigFn.bind(this);

        //设置按钮loading方法
        this.setBtnsLoading = setBtnsLoading.bind(this);

        //内置模块
        this.actionBtnsModel = actionBtnsModel.bind(this);
        this.search = search.bind(this);
        this.modalTree = modalTree.bind(this);
        this.modalForm = modalForm.bind(this);
        this.descModel = descModel.bind(this);
        this.infoAlertModal = infoAlertModal.bind(this);
        this.tableModal = tableModal.bind(this);
        this.leftDrawerModal = leftDrawerModal.bind(this);
        // this.topInfoAlert = topInfoAlert.bind(this); 

        //设置抽屉中自定义页面的按钮
        this.setDrawerBtnsByComponent = (btns,cb) => this.setState({ drawerBtnsByComponent: btns },cb);

        //设置抽屉中表单全局禁用
        this.setFormGlobalDisabledStatus = (disabled,cb) => this.setState({ formGlobalDisabledStatus: disabled },cb);

        //设置抽屉表单项
        this.setForms = (forms,cb) => this.setState({ forms: forms },cb);

        //设置抽屉中的按钮
        this.setDrawerBtns = (btns,cb) => this.setState({ drawerBtns: btns },cb);

        //置空已选择项
        this.clearSelectedRows = () => { this.setState({ selectedRows: [] }) };

        //设置按钮disabled状态
        this.setBtnsDisabled = (action,field) => { this.setBtnsLoading(action,field,'disabled') };

        //设置actionBtns数据
        this.setActionBtns = (actionBtns) => actionBtns && this.setState({ actionBtns });

        //设置actionBtns数据
        this.setFormIsChangeed = (status) => this.setState({ formIsChangeed: status });

        //获取actionBtns数据
        this.getFormIsChangeed = () => this.state.formIsChangeed;

        //绑定给某些按钮回调的
        this.btnCallbackFn = {
            getTableFetchParams: this.getTableFetchParams,
            setState: this.setState,
            fetch: this.myFetch,
            fetchByCb: this.myFetch,
            history: this.props.history,
            match: this.props.match,
            openTree: this.openTree, //打开树结构窗口
            msg: message,
            refresh: this.refresh,
            closeDrawer: this.closeDrawer, //打开/关闭右边抽屉的方法 传入true/false
            btnAction: this.action, //fn (rowInfo, rowData)
            props: { ...this.props },
            download: download,
            isMobile: this.isMobile(),
            confirm,
            state: this.state, //里面包含了一些细信息
            getActiveKey: () => this.state.tabsIndex, //获取tab激活项索引的方法
            setActiveKey: this.setTabsIndex, //获取tab激活项索引的方法
            setSelectedRows: this.setSelectedRows, //设置选中的数据
            formatData: this.sFormatData,
            qnnForm: this.qnnForm,
            setBtnsLoading: this.setBtnsLoading, //设置按钮状态
            setBtnsDisabled: this.setBtnsDisabled,
            setActionBtns: this.setActionBtns,
            clearSelectedRows: this.clearSelectedRows,
            setDrawerBtns: this.setDrawerBtns,
            setForms: this.setForms,
            setFormGlobalDisabledStatus: this.setFormGlobalDisabledStatus,
            setDrawerBtnsByComponent: this.setDrawerBtnsByComponent,
            setFormIsChangeed: this.setFormIsChangeed,
            getFormIsChangeed: this.getFormIsChangeed
        };
    }
    componentDidUpdate(prevProps,prevState,snapshot) {
        //需要判断fetchConfig中的参数变化进行刷新数据
        if (!prevState.isNeedRefresh && this.state.isNeedRefresh) {
            this.isNeedRefresh();
        }
        if (this.state.isNeedClassifyData) {
            this.formConfigClass();
        }
    }

    //async 
    componentDidMount() {
        this.isMounted = true;
        const { getBackEndConfig } = this.state;

        const setDataAndClassify = () => {
            //掉用一次方法需要去读取一次fetchConfig 注意 
            //刷新数据
            this.refresh();
            //将数据分类&处理渲染
            this.formConfigClass();
        };

        //如果配置是后台给
        if (getBackEndConfig) {
            //调用获取后台配置的方法 传入fetchConfig和回调
            this.getBackEndConfigFn(getBackEndConfig,setDataAndClassify);
        } else {
            //非后台配置 
            setDataAndClassify();
        }

        if (this.state.edit) {
            console.warn("您当前处于开发者模式！！！");
        }
    }

    componentWillUnmount(){
        this.isMounted = false;
    }

    render() {
        const { actionBtnsPosition = "top",firstRowIsSearch,formIsChangeedAlertText,formIsChangeedAlertModalType,formIsChangeedAlertTextContent } = this.state;
        if (this.isMobile()) {
            //移动端
            return <Mobile {...this.props} {...this.state} isInQnnTable={true} isMobile={true} />;
        } else {
            //pc
            return (
                <div className={`${s.superTable} ${firstRowIsSearch ? "table-firstRowIsSearch" : ""}`}>
                    {/* 检索 */}
                    {this.searchComponent()}

                    {/* 操作按钮 */}
                    {actionBtnsPosition === "top" ? this.actionBtnsModel() : null}

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

                    {/* 顶部提示信息弹出层 */}
                    {formIsChangeedAlertText && <TopInfoAlert closeDrawer={this.closeDrawer} formIsChangeedAlertTextContent={formIsChangeedAlertTextContent} type={formIsChangeedAlertModalType} setFormIsChangeedAlertText={(t) => this.setState({ formIsChangeedAlertText: t })}>{formIsChangeedAlertText}</TopInfoAlert>}

                </div>
            );
        }
    }
}

const qnnTable = Form.create({
    onValuesChange: (props,changedValues,allValues) => {
        if (props.fieldsValueChange) {
            props.fieldsValueChange(props,changedValues,allValues);
        }
    }
})(index);
export default qnnTable;
