import { PureComponent } from "react"; //React,
import PropTypes from "prop-types";
import { message, message as Msg } from "antd"; //Input,Button, , Divider, Row, Col Form,
import { withRouter } from "react-router-dom";
import {
    filterObjAttr,
    getDeviceType,
    getParams,
    createInput,
    createHideInput,
    createQnnForm,
    createTabsForm,
    createTabsContent,
    createQnnTable
} from "./tool";
import {
    myFetch,
    getValues,
    setValues,
    sFormatData,
    submit,
    confirm,
    help,
    wran,
    setSelectOptionData,
    getFormConfig,
    getFieldsInsertForm,
    startVoice,
    onCloseVoice,
    setFieldValueByVoice,
    tabsClick,
    setActiveKey
} from "./method";
import s from "./style.less"; //普通样式
import sByZJ from "./styleByZJ.less"; //中交样式
import download from "./tool/download";
import $ from "jquery";
import { tailFormItemLayout } from "./config";
const version = (window.QnnForm_version = "test");
//样式
let style = {};
class index extends PureComponent {
    static propTypes = {
        tabs: PropTypes.array,
        componentsKey: PropTypes.object
    };

    static getDerivedStateFromProps(props, state) {
        const _props = { ...props };
        if (
            !Array.isArray(props.formConfig) &&
            Array.isArray(state.formConfig)
        ) {
            //这种情况下是state中的formConfig是个对象
            _props.formConfig = state.formConfig;
        }
        let obj = {
            ...state,
            ..._props
        };
        return obj;
    }

    static sFormatData(data, formConfig, type) {
        return sFormatData(data, formConfig, type);
    }

    constructor(...args) {
        super(...args);
        help.bind(this)(version);
        wran.bind(this)();
        this.state = {
            fetchConfig: this.props.fetchConfig || {},
            formConfig: this.props.formConfig || [],
            btns: [],
            voiceEnterProps: {
                field: "", //当前输入表单的field
                show: false //出现状态还是未出现状态
            },
            tabs: [],
            loading: false,
            tabsActiveKey: this.props.tabsActiveKey || "0" //当前激活的tabs页
        };
        let { styleType = "0" } = this.props;

        //判断使用哪种样式
        if (styleType === "0") {
            style = sByZJ;
        } else if (styleType === "1") {
            style = s;
        }

        //按钮布局
        this.tailFormItemLayout =
            this.props.tailFormItemLayout || tailFormItemLayout;

        //外部传入的fetch方法返回必须是个promise
        this.fetch = this.props.fetch;
        this.headers = this.props.headers;

        //给一些方法绑定this
        this.createInput = createInput.bind(this);
        this.myFetch = myFetch.bind(this);
        this.getValues = getValues.bind(this);
        this.submit = submit.bind(this);
        this.confirm = confirm.bind(this);
        this.filterObjAttr = filterObjAttr;
        this.getDeviceType = getDeviceType;
        this.getParams = getParams.bind(this);
        this.setValues = setValues.bind(this);
        this.getFormConfig = getFormConfig.bind(this);
        this.getFieldsInsertForm = getFieldsInsertForm.bind(this);
        this.startVoice = startVoice.bind(this);
        this.onCloseVoice = onCloseVoice.bind(this);
        this.setFieldValueByVoice = setFieldValueByVoice.bind(this);
        this.createHideInput = createHideInput.bind(this);
        this.createQnnForm = createQnnForm.bind(this);
        this.createTabsForm = createTabsForm.bind(this);
        this.tabsClick = tabsClick.bind(this);
        this.setActiveKey = setActiveKey.bind(this);
        this.createTabsContent = createTabsContent.bind(this);
        this.createQnnTable = createQnnTable.bind(this);
        // this.setQnnFormBlockInitialValue = (name, values) => this[name] = values;

        //下选择的数据key名
        this.selectKey = field => `${field}_optionData`;

        //绑定给按钮点击后回调使用的方法
        this.btnfns = {
            getValues: this.getValues,
            setValues: this.setValues,
            tableRefresh: this.props.refresh, //刷新table方法
            myFetch: this.myFetch,
            Msg: message,
            confirm: this.confirm,
            formatData: sFormatData,
            match: this.props.match,
            history: this.props.history,
            props: { ...this.props },
            download,
            getSelectKey: this.selectKey,
            setActiveKey: this.setActiveKey,
            getActiveKey:()=>this.state.tabsActiveKey, //获取tab激活项索引的方法
        };

        //所有可为fun的属性执行时都能拿到这个对象
        this.funcCallBackParams = {
            ...this.props,
            fetch: this.fetch,
            btnfns: this.btnfns, //兼容写法
            btnCallbackFn: this.btnfns,
            isMobile: this.isMobile(),
            headers: this.headers,
            _formData: this.props.form.getFieldsValue(),
            state: this.state //里面包含了一些细信息（当前tabs的激活项索引等...）
        };
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        const { needRefreshField } = this.state;
        if (needRefreshField) { 
            //需要设置下form表单
            this.props.form.setFieldsValue({
                [`${needRefreshField}_Block`]: this[needRefreshField]
            });
            this.setState({
                needRefreshField: false
            });
        } 
    }

    isMobile = () => this.getDeviceType() === "mobile";
    isPc = () => this.getDeviceType() === "pc";

    componentDidMount() {
        const {
            fetchConfig,
            formConfig,
            tabs = [],
            tabsActiveKey
        } = this.state; //, formConfig
        const _this = this;
        let { apiName, params, otherParams } = fetchConfig;
        if (typeof otherParams === "function") {
            otherParams = otherParams({
                ...this.funcCallBackParams
            });
        }
        if (typeof apiName === "function") {
            apiName = apiName({
                ...this.funcCallBackParams
            });
        }
        if (formConfig.fetchConfig) {
            //需要去请求字段项然后渲染
            this.getFieldsInsertForm();
        }
        //设置所有的下拉选项
        setSelectOptionData.bind(this)();
        if (apiName) {
            this.setState({ loading: true });
            let _params = this.getParams(params, otherParams);
            this.myFetch(apiName, _params, ({ success, data, message }) => {
                this.setState({ loading: false });
                if (success) {
                    this.setValues(data);
                } else {
                    this.setValues({});
                    Msg.error(message);
                }
            });
        }
        //设置tabs点击
        if (tabs.length) {
            this.setActiveKey(tabsActiveKey);
        }
        $(function() {
            if (_this.isMobile()) {
                $(".QnnForm input, .QnnForm textarea").on("click", function(e) {
                    let target = this;
                    let thisType = $(this).attr("type");
                    if (thisType === "radio" || thisType === "checkbox") {
                        return;
                    }
                    setTimeout(function() {
                        target.scrollIntoView({
                            block: "center",
                            inline: "center"
                        });
                    }, 450);
                });
                $(".QnnForm input, .QnnForm textarea").on("blur", function() {
                    let thisType = $(this).attr("type");
                    if (thisType === "radio" || thisType === "checkbox") {
                        return;
                    }
                    setTimeout(() => {
                        window.scrollTo(0, document.body.scrollTop + 1);
                        document.body.scrollTop >= 1 &&
                            window.scrollTo(0, document.body.scrollTop - 1);
                    }, 10);
                });
            }
        });
    }

    render() {
        const {
            formConfig = [],
            btns = [],
            voiceEnterProps = {},
            tabs = []
        } = this.state;
        if (tabs.length) {
            //应该渲染tab页面
            return this.createTabsForm({
                style,
                tabs
            });
        } else {
            //正常渲染表单
            return this.createQnnForm(
                {
                    formConfig,
                    btns,
                    voiceEnterProps
                },
                style
            );
        }
    }
}
export default withRouter(index);
