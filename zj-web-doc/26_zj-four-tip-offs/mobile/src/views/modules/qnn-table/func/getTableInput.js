//获取table项被点击后可编辑的input dom
//注意  這個组件绑定的this是单元格的this
import React from "react";
import { Input,Form,message as Msg,DatePicker,Select } from "antd";
import moment from "moment";
// const loudouImg = require("../icon/loudou.svg");
const Option = Select.Option;
const FormItem = Form.Item;

class DatetimeCom extends React.Component {
    render() {
        const {
            isLouDou,
            // form: { setFieldsValue, getFieldValue },
            placeholder,
            onPressEnter,
            // field,
            onChange,
            value = {
                scope: "0"
            },
            showTime
        } = this.props;
        return (
            <div className="headerDatePicker">
                <DatePicker
                    ref={node => (this.input = node)}
                    format="YYYY-MM-DD HH:mm:ss"
                    showTime={showTime}
                    allowClear
                    placeholder={placeholder}
                    onPressEnter={onPressEnter}
                    onChange={val => {
                        onChange({
                            ...value,
                            value: moment(val).valueOf()
                        });
                    }}
                />
                {isLouDou ? (
                    <Select
                        value={value.scope}
                        style={{ width: "40%" }}
                        onChange={val => {
                            onChange({
                                ...value,
                                scope: val
                            });
                            // setFieldsValue({
                            //     [`${field}.scope`]: val
                            // });
                        }}
                    >
                        <Option value="0">等于</Option>
                        <Option value="1">小于</Option>
                        <Option value="2">大于</Option>
                    </Select>
                ) : null}
            </div>
        );
    }
}

const createTableInput = function (fieldsConfig,record) {
    const _optionConfig = {
        value: "value",
        label: "label"
    };
    const {
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
    let { disabled } = fieldsConfig;
    let { label,value } = optionConfig;
    let { apiName,otherParams = {},params = {} } = fetchConfig;
    const { Pprops,Pstate,setOptionData } = this.props;
    let _params = {};
    const urlParams = Pprops.match.params;
    for (const key in params) {
        _params[key] = urlParams[params[key]];
    }

    if (typeof otherParams === "function") {
        otherParams = otherParams({
            ...this.props,
            fetch: this.fetch,
            btnCallbackFn: this.btnCallbackFn
        });
    }

    if (typeof apiName === "function") {
        apiName = apiName({
            ...this.props,
            fetch: this.fetch,
            btnCallbackFn: this.btnCallbackFn
        });
    }


    if (typeof disabled === "function") {
        disabled = disabled({
            ...this.props,
            fetch: this.fetch,
            btnCallbackFn: this.btnCallbackFn
        });
    }


    const commRcFormProps = {
        rules: [
            {
                required: required,
                message: `必填！`
            }
        ],
        onChange: val => {
            let realValue = val;
            if (val && val.target) {
                realValue = val.target.value;
            } 
           
            if (this.onSearch) {
                this.onSearch(null,{
                    [field]: realValue
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
                    {this.form.getFieldDecorator(field,{
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
            if (record.firstRowIsSearch) {
                return (
                    <FormItem style={{ margin: 0 }}>
                        {this.form.getFieldDecorator(field,{
                            ...commRcFormProps,
                            initialValue: {
                                scope: "0"
                            },
                            // onChange: val => {
                            //     this.save &&
                            //         this.save(null, {
                            //             [field]: moment(val).valueOf()
                            //         });
                            //     commRcFormProps.onChange(moment(val).valueOf());
                            // }
                        })(
                            <DatetimeCom
                                isLouDou={record.firstRowIsSearch}
                                form={this.form}
                                field={field}
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
                        {this.form.getFieldDecorator(field,{
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

        case "select":
        case "radio":
            const _optionData = Pstate[`${field}-optionData`] || [];
            return (
                <FormItem style={{ margin: 0 }}>
                    {this.form.getFieldDecorator(field,{
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
                            onBlur={onPressEnter}
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

                                    Pprops.myFetch(apiName,{
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
export default createTableInput;
