//获取table项被点击后可编辑的input dom
//注意  這個组件绑定的this是单元格的this
import React from "react";
import { Input,Form,message as Msg,DatePicker,Select } from "antd";
import moment from "moment";
 
const { RangePicker } = DatePicker;
 
const Option = Select.Option;
const FormItem = Form.Item;
const { MonthPicker } = DatePicker;
class DatetimeCom extends React.Component {

    state = {
        yearOpen: false
    }

    render() {
        const {
            isLouDou,
            form: { setFieldsValue },
            placeholder,
            onPressEnter,
            field,
            onChange,
            value = {
                scope: "0",
            },
            showTime,
            format = "YYYY-MM-DD HH:mm:ss",
            type,
            ...other
        } = this.props;
        if (type === "year") {
            other.onOpenChange = (status) => {
                if (status) {
                    this.setState({ yearOpen: true })
                } else {
                    this.setState({ yearOpen: false })
                }
            }
            other.open = this.state.yearOpen
            other.onPanelChange = (val) => {
                this.setState({
                    yearOpen: false
                });
                setFieldsValue({
                    [field]: {
                        ...value,
                        value: moment(val)
                    }
                });

                onChange({
                    ...value,
                    value: moment(val).valueOf()
                });
            }
            other.mode = "year"
            other.format = "YYYY"
            other.showTime = false;
            other.value = value.value ? moment(value.value) : null;
        }
        return (
            <div className="headerDatePicker">
                {isLouDou ? (
                    <Select
                        // disabled={!value.value}
                        value={value.scope}
                        style={{ width: "60px",marginRight: '3px',textAlign: "center" }}
                        onChange={val => {
                            onChange({
                                ...value,
                                scope: val
                            });
                        }}
                    >
                        {/* 等于 */}
                        <Option value="0" style={{ textAlign: "center" }}>&#61;</Option>
                        {/* 小于等于 */}
                        <Option value="1">&#60;&#61;</Option>
                        {/* 大于等于 */}
                        <Option value="2">&#62;&#61;</Option>
                    </Select>
                ) : null}
                <DatePicker
                    ref={node => (this.input = node)}
                    format={format}
                    showTime={showTime}
                    allowClear
                    placeholder={placeholder}
                    onPressEnter={onPressEnter}
                    onChange={val => {
                        onChange({
                            ...value,
                            value: val ? moment(val).valueOf() : null
                        });
                    }}
                    {...other}
                />
            </div>
        );
    }
}

const getTableHeaderInput = function (fieldsConfig,record) {
    const _optionConfig = {
        value: "value",
        label: "label"
    };
    let {
        type,
        field,
        optionData,
        initialValue,
        fetchConfig = {}, //提交
        required,
        placeholder = "请输入...",
        optionConfig = { ..._optionConfig },
        ...otherArgs
    } = fieldsConfig;
    if (!field) {
        return <div />
    }
    field = `searchParams.${field.replace(/searchParams./g,'')}`;
    let { disabled } = fieldsConfig;
    let { label,value } = optionConfig;
    let { apiName,otherParams = {},params = {} } = fetchConfig;
    const { Pprops,Pstate,setOptionData } = this.props;
    let _params = {};
    const urlParams = Pprops.match.params;
    for (const key in params) {
        _params[key] = urlParams[params[key]];
    }

    otherParams = this.bind(otherParams);
    if (typeof otherParams === "function") {
        otherParams = otherParams({
            ...this.props,
            fetch: this.fetch,
            btnCallbackFn: this.btnCallbackFn
        });
    }

    apiName = this.bind(apiName);
    if (typeof apiName === "function") {
        apiName = apiName({
            ...this.props,
            fetch: this.fetch,
            btnCallbackFn: this.btnCallbackFn
        });
    }


    disabled = this.bind(disabled);
    if (typeof disabled === "function") {
        disabled = disabled({
            ...this.props,
            fetch: this.fetch,
            btnCallbackFn: this.btnCallbackFn
        });
    }


    const commRcFormProps = {
        onChange: val => {
            let realValue = val;
            if (val && val.target) {
                realValue = val.target.value;
            }
            if (this.onSearch) {
                this.onSearch(null,{
                    [field.replace(/searchParams./g,'')]: realValue
                });
            }
        }
    };
    const onPressEnter = e => {
        if (this.save) {
            this.save(e);
        } else if (this.onSearch) {
            this.onSearch(e,{
                [field]: e
            });
        }
    };
    switch (type) {
        case "string":
        case "textarea":
        case "number":
            return (
                <FormItem style={{ margin: 0 }}>
                    {this.props.Pprops.form.getFieldDecorator(field,{
                        ...commRcFormProps,
                        initialValue: record[field] || initialValue
                    })(
                        <Input
                            ref={node => (this.input = node)}
                            onPressEnter={onPressEnter}
                            placeholder={placeholder}
                            disabled={disabled}
                        />
                    )}
                </FormItem>
            );
        case "datetime":
        case "date":
        case "year":
            if (record.firstRowIsSearch) {
                return (
                    <FormItem style={{ margin: 0 }}>
                        {this.props.Pprops.form.getFieldDecorator(field,{
                            ...commRcFormProps,
                            initialValue: {
                                scope: "0"
                            },
                        })(
                            <DatetimeCom
                                isLouDou={record.firstRowIsSearch}
                                form={this.props.Pprops.form}
                                field={field}
                                type={type}
                                placeholder={placeholder}
                                onPressEnter={onPressEnter}
                                disabled={disabled}
                                {...otherArgs}
                            />
                        )}
                    </FormItem>
                );
            } else {
                let _initv = null;
                let _oInitv = record[field] || initialValue;
                if (_oInitv) {
                    _initv = moment(_oInitv);
                }
                return (
                    <FormItem style={{ margin: 0 }}>
                        {this.props.Pprops.form.getFieldDecorator(field,{
                            ...commRcFormProps,
                            initialValue: _initv,
                            onChange: val => {
                                this.save &&
                                    this.save(null,{
                                        [field]: moment(val).valueOf()
                                    });
                                commRcFormProps.onChange(moment(val).valueOf());
                            }
                        })(
                            <DatePicker
                                ref={node => (this.input = node)}
                                format="YYYY-MM-DD HH:mm:ss"
                                showTime
                                allowClear
                                placeholder={placeholder}
                                onPressEnter={onPressEnter}
                                disabled={disabled}
                            />
                        )}
                    </FormItem>
                );
            }
        case "rangeDate": 
            placeholder = ["开始日期","结束日期"];
            if (!Array.isArray(initialValue)) {
                initialValue = [];
            } else {
                initialValue[0] = moment(initialValue[0]);
                initialValue[1] = moment(initialValue[1]);
            }
    
            return (
                <FormItem style={{ margin: 0 }}>
                    {this.props.Pprops.form.getFieldDecorator(field,{
                        ...commRcFormProps,  
                        initialValue:initialValue
                    })(
                        <RangePicker disabled={disabled} {...otherArgs} />
                    )}
                </FormItem>
            )
        case "month":
            return (
                <FormItem style={{ margin: 0 }}>
                    {this.props.Pprops.form.getFieldDecorator(field,{
                        ...commRcFormProps,
                    })(
                        <MonthPicker
                            isLouDou={record.firstRowIsSearch}
                            form={this.props.Pprops.form}
                            field={field}
                            placeholder={placeholder}
                            onPressEnter={onPressEnter}
                            disabled={disabled}
                            {...otherArgs}
                        />
                    )}
                </FormItem>
            );
        case "select":
        case "radio":
            const _optionData = Pstate[`${field}-optionData`] || [];
            return (
                <FormItem style={{ margin: 0 }}>
                    {this.props.Pprops.form.getFieldDecorator(field,{
                        ...commRcFormProps,
                        initialValue: record[field] || initialValue,
                        onChange: val => {
                            this.save &&
                                this.save(null,{
                                    [field]: val
                                });
                            commRcFormProps.onChange(val);
                        }
                    })(
                        <Select
                            ref={node => (this.input = node)}
                            placeholder={placeholder}
                            onPressEnter={onPressEnter}
                            // onBlur={onPressEnter}  //下拉选择是在切换时候搜索的  不是在失去焦点时候
                            disabled={disabled}
                            allowClear
                            style={{
                                width: "100%"
                            }}
                            onFocus={() => {
                                if (Pstate[`${field}-optionData`] && Pstate[`${field}-optionData`].length) {
                                    return;
                                }
                                if (!apiName) {
                                    setOptionData({
                                        [`${field}-optionData`]:
                                            optionData || []
                                    });
                                } else {
                                    if (!apiName) {
                                        console.error(
                                            "select form属性的fetchConfig属性必须设置apiName"
                                        );
                                        return;
                                    }
                                    let _fetch = Pprops.myFetch || Pprops.fetch;
                                    _fetch(apiName,{
                                        ...params,
                                        ...otherParams
                                    }).then(res => {
                                        let { data,success,message } = res;
                                        if (success) {
                                            setOptionData({
                                                [`${field}-optionData`]: data
                                            });
                                        } else {
                                            Msg.error(message,1);
                                        }
                                    });
                                }
                            }}
                            {...otherArgs}
                        >
                            {_optionData.map((item,index) => {
                                return (
                                    <Option value={item[value]} key={index}>
                                        {item[label]}
                                    </Option>
                                );
                            })}
                        </Select>
                    )}
                </FormItem>
            );
        default:
            return <div />;
    }
};
export default getTableHeaderInput;
