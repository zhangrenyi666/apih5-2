//获取table项被点击后可编辑的input dom
//注意  這個组件绑定的this是单元格的this
import React from "react";
import { Input, Form, message as Msg, DatePicker, Select } from "antd";
import moment from "moment";
const Option = Select.Option;
const FormItem = Form.Item;

const createTableInput = function(fieldsConfig, record) {
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
        optionConfig = { ..._optionConfig }
    } = fieldsConfig;
    let { label, value } = optionConfig;
    let { apiName, otherParams = {}, params = {} } = fetchConfig;
    const { Pprops, Pstate, setOptionData } = this.props; 
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
    // console.log(this.form);
    switch (type) {
        case "string":
        case "textarea":
        case "number":
            return (
                <FormItem style={{ margin: 0 }}>
                    {this.form.getFieldDecorator(field, {
                        rules: [
                            {
                                required: required,
                                message: `必填！`
                            }
                        ],
                        initialValue: record[field] || initialValue
                    })(
                        <Input
                            ref={node => (this.input = node)}
                            onPressEnter={this.save}
                            placeholder={placeholder}
                        />
                    )}
                </FormItem>
            );
        case "datetime":
            return (
                <FormItem style={{ margin: 0 }}>
                    {this.form.getFieldDecorator(field, {
                        rules: [
                            {
                                required: required,
                                message: `必填！`
                            }
                        ],
                        initialValue:
                            moment(record[field]) || moment(initialValue),
                        onChange: val => {
                            this.save(null, {
                                [field]: moment(val).valueOf()
                            });
                        }
                    })(
                        <DatePicker
                            ref={node => (this.input = node)}
                            format="YYYY-MM-DD HH:mm:ss"
                            showTime
                            allowClear
                            placeholder={placeholder}
                            onPressEnter={this.save}
                        />
                    )}
                </FormItem>
            );
        case "select":
            const _optionData = Pstate[`${field}-optionData`] || [];
            return (
                <FormItem style={{ margin: 0 }}>
                    {this.form.getFieldDecorator(field, {
                        rules: [
                            {
                                required: required,
                                message: `必填！`
                            }
                        ],
                        initialValue: record[field] || initialValue,
                        onChange: val => {
                            this.save(null, {
                                [field]: val
                            });
                        }
                    })(
                        <Select
                            ref={node => (this.input = node)}
                            placeholder={placeholder}
                            onPressEnter={this.save}
                            onBlur={this.save}
                            style={{
                                width:"100%", 
                            }}
                            onFocus={() => {
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
                                    
                                    Pprops.myFetch(apiName, {
                                        ...params,
                                        ...otherParams
                                    }).then(res => {
                                        let { data, success, message } = res;
                                        if (success) {
                                            setOptionData({
                                                [`${field}-optionData`]: data
                                            });
                                        } else {
                                            Msg.error(message, 1);
                                        }
                                    });
                                }
                            }}
                        >
                            {_optionData.map((item, index) => {
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
