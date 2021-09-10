import React, { PureComponent } from "react";
import { Form, Input, Button, message, message as Msg, Row, Col } from "antd";
import { withRouter } from "react-router-dom";
import { createInput, filterObjAttr, getDeviceType, getParams } from "./tool";
import {
    myFetch,
    getValues,
    setValues,
    sFormatData,
    submit,
    confirm,
    help,
    wran,
    setSelectOptionData
} from "./method";
import s from "./style.less";
import download from "./tool/download";
import $ from "jquery";

const FormItem = Form.Item;
const version = (window.QnnForm_version = "test");

const tailFormItemLayout = {
    wrapperCol: {
        xs: {
            span: 24,
            offset: 0
        },
        sm: {
            span: 16,
            offset: 5
        }
    }
};

class index extends PureComponent {
    static getDerivedStateFromProps(props, state) {
        let obj = {
            ...state,
            ...props
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
            btns: []
        };

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

        //下选择的数据key名
        this.selectKey = field => `${field}_optionData`;

        //绑定给按钮点击后回调使用的方法
        this.btnfns = {
            getValues: this.getValues,
            setValues: this.setValues,
            tableRefresh: this.props.refresh, //刷新方法
            myFetch: this.myFetch,
            Msg: message,
            confirm: this.confirm,
            formatData: sFormatData,
            match: this.props.match,
            history: this.props.history,
            props: { ...this.props },
            download,
            getSelectKey: this.selectKey
        };

        //所有可为fun的属性执行时都能拿到这个对象
        this.funcCallBackParams = {
            ...this.props,
            fetch: this.fetch,
            btnfns: this.btnfns,
            isMobile: this.isMobile(),
            headers: this.headers,
            _formData: this.props.form.getFieldsValue()
        };
    }

    isMobile = () => this.getDeviceType() === "mobile";
    isPc = () => this.getDeviceType() === "pc";

    componentDidMount() {
        const { fetchConfig } = this.state;
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
        //设置所有的下拉选项
        setSelectOptionData.bind(this)();
        if (apiName) {
            let _params = this.getParams(params, otherParams);
            this.myFetch(apiName, _params, ({ success, data, message }) => {
                if (success) {
                    this.setValues(data);
                } else {
                    this.setValues({});
                    Msg.error(message);
                }
            });
        }
        $(function() {
            if (_this.isMobile()) {
                $("input,textarea").on("click", function() {
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
                $("input,textarea").on("blur", function() {
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
        const { formConfig = [], btns = [] } = this.state;
        const { getFieldValue, getFieldDecorator } = this.props.form;
        const { isInQnnTable } = this.props;
        const btnsDom = btns.map((item, index) => {
            const { type, label, condition } = item;
            //需要将item中的一些不需要的属性过滤掉然后直接传给Button属性
            let delArr = [
                "isValidate",
                "condition",
                "onClick",
                "fetchConfig",
                "affirmTitle",
                "affirmDesc",
                "affirmYes",
                "affirmNo"
            ];
            const _props = this.filterObjAttr(item, delArr, "del");
            let _defaultStyle = {
                marginRight: "8px"
            };

            //条件设置
            if (condition) {
                for (let i = 0; i < condition.length; i++) {
                    let { regex = {}, action } = condition[i];
                    let _pass = true; //是否满足条件
                    for (const key in regex) {
                        if (regex.hasOwnProperty(key)) {
                            const targetValue = regex[key]; //给的值
                            const fieldValue = getFieldValue(key); //获取的表单支
                            if (targetValue !== fieldValue) {
                                _pass = false;
                            }
                        }
                    }
                    if (_pass) {
                        if (typeof action === "function") {
                            action();
                        } else {
                            switch (action) {
                                case "disabled":
                                    _props.disabled = true;
                                    break;
                                case "hide":
                                    _defaultStyle.display = "none";
                                    break;
                                case "show":
                                    _defaultStyle.display = "inline-block";
                                    break;
                                default:
                                    console.log(`${action}动作无效`);
                                    break;
                            }
                        }
                    }
                }
            }
            return (
                <Button
                    style={{ ..._defaultStyle }}
                    key={index}
                    onClick={() => {
                        this.submit(item);
                    }}
                    type={type}
                    {..._props}
                >
                    {label}
                </Button>
            );
        });
        return (
            <div
                className={`${s.QnnForm} ${
                    isInQnnTable ? s.isInQnnTable : null
                }  ${this.isMobile() ? s.mobileForm : null} QnnForm`}
                style={{ ...this.props.style }}
            >
                <Form>
                    {
                        <Row gutter={24}>
                            {formConfig.map((item, index) => {
                                let _hide;
                                if (typeof item.hide === "function") {
                                    _hide = item.hide({
                                        ...this.funcCallBackParams
                                    });
                                } else {
                                    _hide = item.hide;
                                }
                                if (_hide) {
                                    let {
                                        isUrlParams,
                                        field,
                                        initialValue
                                    } = item;
                                    if (typeof initialValue === "function") {
                                        initialValue = initialValue({
                                            ...this.props,
                                            btnfns: this.btnfns, //下个大版本删除
                                            btnCallbackFn: this.btnfns
                                        });
                                    }
                                    if (isUrlParams) {
                                        let _params = this.props.match.params[
                                            field
                                        ];
                                        initialValue = _params
                                            ? _params
                                            : initialValue;
                                    }
                                    return getFieldDecorator(item.field, {
                                        initialValue: initialValue
                                    })(<Input type="hidden" key={index} />);
                                } else {
                                    return (
                                        <Col
                                            className={
                                                item.type === "Component"
                                                    ? "ComponentCol"
                                                    : ""
                                            }
                                            key={index}
                                            span={item.span || "24"}
                                            offset={item.offset || "0"}
                                        >
                                            {this.createInput(item, index)}
                                        </Col>
                                    );
                                }
                            })}
                        </Row>
                    }
                    {btns.length > 0 ? (
                        <Row gutter={24}>
                            <FormItem {...this.tailFormItemLayout}>
                                {this.isMobile() ? (
                                    <div className={s.btnCon}>{btnsDom}</div>
                                ) : (
                                    btnsDom
                                )}
                            </FormItem>
                        </Row>
                    ) : null}
                </Form>
            </div>
        );
    }
}
export default withRouter(index);
