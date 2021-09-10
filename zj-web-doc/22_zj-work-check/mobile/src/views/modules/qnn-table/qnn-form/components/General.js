import React from "react";
// import s from '../../style.less'
import {
    Input,
    Form,
    Icon,
    Upload,
    InputNumber,
    DatePicker,
    TimePicker,
    Select,
    Spin,
    Cascader,
    message as Msg,
    Radio,
    Checkbox,
    Switch,
    Slider,
    Rate
} from "antd";
import { DatePicker as MobileDatePicker, List } from "antd-mobile";
import PullPerson from "../pullPersion";
import RichText from "../RichText";
import TreeNode from "../../../tree";
// import PullPerson from "pull-person";
import Item from "../Item";
import Camera from "../upload";
import PullPersonMobile from "../pullPersionMobile";
import getDeviceType from "../tool/getDeviceType";
let _isMobile = () => getDeviceType() === "mobile";
const RadioGroup = Radio.Group;
const CheckboxGroup = Checkbox.Group;
// import PullPersonMobile from "pull-person-mobile";
// import { Editor, EditorState } from "draft-js";
const { MonthPicker } = DatePicker;

const FormItem = Form.Item;
const Option = Select.Option;
//移动端文件上传之类的表单项用的布局
const mobileItem2FormItemLayout = {
    labelCol: {
        xs: { span: 24 },
        sm: { span: 24 }
    },
    wrapperCol: {
        xs: { span: 24 },
        sm: { span: 24 }
    }
};

//时间注组件本地化
const locale = {
    DatePickerLocale: {
        year: "年",
        month: "月",
        day: "日",
        hour: "时",
        minute: "分"
    },
    okText: "确定",
    dismissText: "取消"
};

//获取类型的验证规则
const getRules = ({ type, required, message, typeMessage }) => {
    switch (type) {
        case "radio":
        case "checkbox":
        case "switch":
        case "rate":
        case "slider":
            return [
                {
                    required: required,
                    message
                }
            ];
        default:
            return [
                {
                    required: required,
                    message
                },
                {
                    type,
                    message: typeMessage
                }
            ];
    }
};

//文件上传后转换
const normFile = e => {
    const { fileList } = e;
    let newFileList = fileList.map((item, index) => {
        //当失败时弹出失败信息。但是有时候成功需要弹出信息时也可以将success设置为false
        if (item && item.response && !item.response.success) {
            if (index === fileList.length - 1) {
                Msg.info(item.response.message);
            }
        }
        if (item.response && item.response.success && item.response.data) {
            if (!item.response.data.uid) {
                item.response.data.uid = index;
            }
            const res = item.response.data;
            //移动端上传时候需要将返回的url地址改为mobileUrl
            const obj = {
                ...res,
                url: _isMobile() ? res.mobileUrl : res.url,
                status: "done",
                fileName: res.name || res.fileName,
                fileUrl: res.url || res.fileUrl
            };
            return obj;
        }
        return { ...item };
    });

    return newFileList;
};

export default props => {
    let {
        field,
        type,
        required,
        message,
        initialValue,
        label,
        disabled,
        hide,
        antdProps = {},
        form: { getFieldDecorator, getFieldValue, resetFields },
        inputProps,
        typeMessage,
        formItemLayout,
        style = {},
        isMobile, //用于移动端和pc端差异插件的区别使用
        onChange,
        is24, //时间组件特有
        multiple, //是否支持多选
        showSearch,
        optionData = [], //下拉选项
        optionConfig = {}, //下拉选项配置
        fetchConfig = {},
        desc,
        subdesc,
        myFetch,
        setState,
        getState,
        headers, //父方法
        formatter,
        condition,
        treeSelectOption,
        selectKey,
        getSelectKey, //获取下拉选项key的方法
        btnfns,
        state,
        props: QnnformProps,
        match,
        //linkage类型特有
        // parentKey,//联动下拉特有
        // allSelectKeys,//联动下拉特有
        parentField, //父级字段名
        // allParentField,//父级字段名
        allChildrenField = [], //所有子集字段名
        Component, //自定义组件专用
        token,
        richTabBarOptions,
        ueditorConfig,
        funcCallBackParams
    } = props;
    if (typeof disabled === "function") {
        disabled = disabled({ ...funcCallBackParams });
    }

    let _optionData = getState(selectKey) || [];
    optionData =
        _optionData && _optionData.length > 0 ? _optionData : optionData;
    //处理initialValue
    if (formatter && initialValue) {
        initialValue = formatter(initialValue);
    }
    //通用的input props
    inputProps = {
        ...inputProps,
        style: {
            width: "100%",
            ...inputProps.style
        }
    };
    if (typeof inputProps === "function") {
        inputProps = inputProps({ ...funcCallBackParams });
    }

    //通用的rc-form规则 时间规则例外
    let rules = getRules({
        type,
        required,
        message,
        typeMessage
    });

    //通用的rc-form参数
    let rcFormParams = {
        initialValue,
        rules: [...rules],
        onChange: v => {
            if (allChildrenField.length) {
                //联动类型还需要将所有子集数据置空
                resetFields(allChildrenField);
                //设置子集
                let _children = allChildrenField ? allChildrenField[0] : "";
                if (_children) {
                    let _c = optionConfig.children;
                    let _v = optionConfig.value;
                    let _ck = getSelectKey(_children);
                    let _selected = _optionData.filter(item => {
                        return item[_v] === v;
                        // if (item[_v] === v) {
                        //   return item;
                        // }
                    });
                    setState({
                        [_ck]: _selected[0][_c]
                    });
                }
            }

            if (onChange) {
                let val = v.target ? v.target.value : v;
                onChange(val, btnfns);
            }
        },
        normalize: (value, prev, all) => {
            if (formatter) {
                return formatter(value, prev, all);
            }
            return value;
        }
    };

    //通用
    let { apiName, name, uploadUrl } = fetchConfig;
    //上传组件使用
    let max = inputProps.max;
    let fileList = getFieldValue && field ? getFieldValue(field) || [] : [];

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
                            inputProps.disabled = true;
                            break;
                        case "hide":
                            hide = true;
                            break;
                        case "show":
                            hide = false;
                            break;
                        default:
                            console.log(`${action}动作无效`);
                            break;
                    }
                }
            }
        }
    }
    let _style = {
        display: hide ? "none" : "block",
        ...style
    };

    // console.log(label, disabled)
    //通用Item props
    let commProps = {
        label,
        ...antdProps,
        ...formItemLayout,
        className: isMobile
            ? disabled
                ? "disabled mobileItem"
                : "mobileItem"
            : "",
        style: {
            ..._style
        }
    };

    //文件上传组件的props
    let uploadProps = {
        valuePropName: "fileList",
        getValueFromEvent: normFile,
        ...rcFormParams,
        rules: [
            {
                required: required,
                message
            }
        ]
    };

    ///下拉选项配置
    const _optionConfig = {
        label: "label", //默认 label
        value: ["value"], //最终的值使用逗号连接 默认值使用valueName 默认['value']
        children: ["children"], //最终的值使用逗号连接 默认值使用valueName 默认['value']
        ...optionConfig
    };

    const mobileItem2 = isMobile ? "mobileItem mobileItem2" : "";

    if (parentField && getFieldValue) {
        if (!getFieldValue(parentField)) {
            inputProps.disabled = true;
        }
    }
    if (!field) {
        return <div />;
    }
    switch (type) {
        case "string":
        case "email":
        case "url":
            return (
                <FormItem {...commProps}>
                    {getFieldDecorator(field, {
                        ...rcFormParams
                    })(<Input {...inputProps} />)}
                </FormItem>
            );
        case "password":
            return (
                <FormItem {...commProps}>
                    {getFieldDecorator(field, {
                        ...rcFormParams,
                        rules: [
                            {
                                required: required,
                                message
                            }
                        ]
                    })(<Input type="password" {...inputProps} />)}
                </FormItem>
            );
        case "textarea":
            if (isMobile) {
                commProps = {
                    ...commProps,
                    ...mobileItem2FormItemLayout
                };
            }
            return (
                // style={{ display: 'block' }}
                <FormItem {...commProps}>
                    {getFieldDecorator(field, {
                        ...rcFormParams,
                        rules: [
                            {
                                required: required,
                                message
                            }
                        ]
                    })(
                        <Input.TextArea
                            autosize={{ minRows: 3, maxRows: 12 }}
                            {...inputProps}
                        />
                    )}
                </FormItem>
            );
        case "number":
        case "integer":
            return (
                <FormItem {...commProps}>
                    {getFieldDecorator(field, {
                        ...rcFormParams
                    })(<InputNumber {...inputProps} />)}
                </FormItem>
            );
        case "datetime":
        case "date":
        case "time":
            if (isMobile) {
                if (!getFieldValue(field)) {
                    //是否是未选择状态  因为移动端值和placeholder放到了一个dom中所有需要区分
                    commProps.className = `${commProps.className}  placeholder`;
                }
                if (type === "datetime") {
                    return (
                        <FormItem {...commProps}>
                            {getFieldDecorator(field, {
                                ...rcFormParams,
                                rules: [
                                    {
                                        required: required,
                                        message
                                    }
                                ]
                            })(
                                <MobileDatePicker
                                    disabled={disabled}
                                    mode="datetime"
                                    extra={inputProps.placeholder}
                                    clear={true}
                                    locale={locale}
                                >
                                    <List.Item arrow="horizontal" />
                                </MobileDatePicker>
                            )}
                        </FormItem>
                    );
                } else if (type === "date") {
                    return (
                        <FormItem {...commProps}>
                            {getFieldDecorator(field, {
                                ...rcFormParams,
                                rules: [
                                    {
                                        required: required,
                                        message
                                    }
                                ]
                            })(
                                <MobileDatePicker
                                    mode="date"
                                    clear={true}
                                    disabled={disabled}
                                    extra={inputProps.placeholder}
                                    locale={locale}
                                >
                                    <List.Item arrow="horizontal" />
                                </MobileDatePicker>
                            )}
                        </FormItem>
                    );
                } else if (type === "time") {
                    return (
                        <FormItem {...commProps}>
                            {getFieldDecorator(field, {
                                ...rcFormParams,
                                rules: [
                                    {
                                        required: required,
                                        message
                                    }
                                ]
                            })(
                                <MobileDatePicker
                                    mode="time"
                                    clear={true}
                                    disabled={disabled}
                                    extra={inputProps.placeholder}
                                    locale={locale}
                                >
                                    <List.Item arrow="horizontal" />
                                </MobileDatePicker>
                            )}
                        </FormItem>
                    );
                } else {
                    return (
                        <div>
                            暂不支持该格式：{type}
                            。支持格式有：date、time、datetime
                        </div>
                    );
                }
            } else {
                return (
                    <FormItem {...commProps}>
                        {getFieldDecorator(field, {
                            ...rcFormParams,
                            rules: [
                                {
                                    required: required,
                                    message
                                },
                                {
                                    type: "object",
                                    message: typeMessage
                                }
                            ]
                        })(
                            type === "time" ? (
                                <TimePicker
                                    format={is24 ? "HH:mm:ss" : "h:mm:ss"}
                                    {...inputProps}
                                />
                            ) : type === "date" ? (
                                <DatePicker
                                    format={"YYYY-MM-DD"}
                                    showTime={true}
                                    {...inputProps}
                                />
                            ) : (
                                <DatePicker
                                    format={
                                        is24
                                            ? "YYYY-MM-DD HH:mm:ss"
                                            : "YYYY-MM-DD hh:mm:ss"
                                    }
                                    showTime={true}
                                    {...inputProps}
                                />
                            )
                        )}
                    </FormItem>
                );
            }

        case "month":
            return (
                <FormItem {...commProps}>
                    {getFieldDecorator(field, {
                        ...rcFormParams,
                        rules: [
                            {
                                required: required,
                                message
                            },
                            {
                                type: "object",
                                message: typeMessage
                            }
                        ]
                    })(<MonthPicker {...inputProps} />)}
                </FormItem>
            );
        case "radio":
            return (
                <FormItem {...commProps}>
                    {getFieldDecorator(field, {
                        ...rcFormParams,
                        valuePropName: "value"
                    })(
                        <RadioGroup
                            options={optionData}
                            disabled={disabled}
                            {...inputProps}
                        />
                    )}
                </FormItem>
            );

        case "checkbox":
            return (
                <FormItem {...commProps}>
                    {getFieldDecorator(field, {
                        ...rcFormParams,
                        valuePropName: "value"
                    })(
                        <CheckboxGroup
                            options={optionData}
                            disabled={disabled}
                            {...inputProps}
                        />
                    )}
                </FormItem>
            );
        case "switch":
            return (
                <FormItem {...commProps}>
                    {getFieldDecorator(field, {
                        ...rcFormParams,
                        valuePropName: "checked"
                    })(<Switch disabled={disabled} {...inputProps} />)}
                </FormItem>
            );

        case "rate":
            return (
                <FormItem {...commProps}>
                    {getFieldDecorator(field, {
                        ...rcFormParams
                    })(<Rate disabled={disabled} {...inputProps} />)}
                </FormItem>
            );

        case "slider":
            return (
                <FormItem {...commProps}>
                    {getFieldDecorator(field, {
                        ...rcFormParams
                    })(<Slider disabled={disabled} {...inputProps} />)}
                </FormItem>
            );
        case "select":
            let { label, value } = _optionConfig;
            //下拉类型全部改为array类型
            return (
                <FormItem {...commProps}>
                    {getFieldDecorator(field, {
                        ...rcFormParams,
                        rules: [
                            {
                                required: required,
                                message
                            }
                        ]
                    })(
                        <Select
                            onFocus={() => {
                                if (parentField) {
                                    //需要去获取父级的值
                                    const {
                                        apiName,
                                        otherParams = {},
                                        params,
                                        parentKey = parentField
                                    } = fetchConfig;
                                    if (!getState(selectKey) && apiName) {
                                        setState({
                                            selectLoading: true
                                        });

                                        // params 将自动从网址中取值
                                        let _Uparams = {};
                                        const urlParams = match.params;
                                        for (const key in params) {
                                            _Uparams[key] =
                                                urlParams[params[key]];
                                        }
                                        let _params = {
                                            ...otherParams,
                                            ..._Uparams,
                                            [parentKey]: getFieldValue(
                                                parentField
                                            )
                                        };
                                        if (parentKey)
                                            myFetch(apiName, _params).then(
                                                ({
                                                    data,
                                                    success,
                                                    message
                                                }) => {
                                                    if (success) {
                                                        setState({
                                                            selectLoading: false,
                                                            [selectKey]: data
                                                        });
                                                    } else {
                                                        Msg.error(message);
                                                    }
                                                }
                                            );
                                    }
                                }
                            }}
                            notFoundContent={
                                getState("selectLoading") ? (
                                    <Spin size="small" />
                                ) : null
                            }
                            allowClear={true}
                            showSearch={showSearch}
                            mode={multiple ? "multiple" : null}
                            optionFilterProp="children"
                            {...inputProps}
                        >
                            {optionData && optionData.length > 0
                                ? optionData.map((item, index) => {
                                      let _label = item[label];
                                      let _value = "";
                                      //需要重新设置值
                                      if (Array.isArray(value)) {
                                          _value = value.map(val => {
                                              return item[val];
                                          });
                                          _value = _value.join();
                                      } else {
                                          _value = item[value];
                                      }
                                      return (
                                          <Option key={index} value={_value}>
                                              {_label}
                                          </Option>
                                      );
                                  })
                                : null}
                        </Select>
                    )}
                </FormItem>
            );
        case "cascader":
            return (
                <FormItem {...commProps}>
                    {getFieldDecorator(field, {
                        ...rcFormParams,
                        rules: [
                            {
                                required: required,
                                message
                            }
                        ]
                    })(
                        <Cascader
                            notFoundContent={
                                getState("selectLoading") ? (
                                    <Spin size="small" />
                                ) : null
                            }
                            showSearch={showSearch}
                            // filedNames={_optionConfig}
                            fieldNames={_optionConfig}
                            options={optionData}
                            {...inputProps}
                        />
                    )}
                </FormItem>
            );
        case "files":
            inputProps.disabled =
                inputProps.disabled || fileList.length + 1 > max;
            if (isMobile) {
                commProps = {
                    ...commProps,
                    ...mobileItem2FormItemLayout
                };
            }
            commProps.className = `${commProps.className} ${mobileItem2}`;
            return (
                // className={mobileItem2}
                <FormItem {...commProps}>
                    <div className="dropbox" style={{ width: "100%" }}>
                        {getFieldDecorator(field, { ...uploadProps })(
                            <Upload.Dragger
                                name={name}
                                headers={{ ...headers }}
                                action={apiName}
                                {...inputProps}
                            >
                                <p className="ant-upload-drag-icon">
                                    <Icon type="inbox" />
                                </p>
                                <p className="ant-upload-text">
                                    {desc || "点击或者拖动上传"}
                                </p>
                                <p className="ant-upload-hint">
                                    {subdesc || ""}
                                </p>
                            </Upload.Dragger>
                        )}
                    </div>
                </FormItem>
            );
        case "images":
            if (isMobile) {
                commProps = {
                    ...commProps,
                    ...mobileItem2FormItemLayout
                };
            }
            return (
                <FormItem {...commProps} className={mobileItem2}>
                    <div className="dropbox">
                        {(inputProps.disabled && fileList.length) === 0 ? (
                            <span className="ant-upload-hint">无图片</span>
                        ) : null}
                        {getFieldDecorator(field, { ...uploadProps })(
                            <Upload
                                name={name}
                                listType="picture-card"
                                headers={{ ...headers }}
                                action={apiName}
                                {...inputProps}
                            >
                                {fileList.length >= max ||
                                inputProps.disabled ? null : (
                                    <div>
                                        <Icon type="plus" />
                                        <div className="ant-upload-text">
                                            上传
                                        </div>
                                    </div>
                                )}
                            </Upload>
                        )}
                    </div>
                </FormItem>
            );

        case "camera":
            if (isMobile) {
                commProps = {
                    ...commProps,
                    ...mobileItem2FormItemLayout
                };
            }
            return (
                <FormItem {...commProps} className={mobileItem2}>
                    {getFieldDecorator(field, {
                        rules: [
                            {
                                required: required,
                                message
                            }
                        ],
                        initialValue: initialValue || []
                    })(
                        <Camera
                            action={apiName}
                            headers={{ ...headers }}
                            edit={!inputProps.disabled}
                            {...inputProps}
                        />
                    )}
                </FormItem>
            );
        case "richtext":
        case "richText":
            if (isMobile) {
                commProps = {
                    ...commProps,
                    ...mobileItem2FormItemLayout
                };
            }
            return (
                <FormItem {...commProps} className={mobileItem2}>
                    {getFieldDecorator(field, {
                        initialValue: initialValue || "",
                        rules: [{ required: required }]
                    })(
                        <RichText
                            ueditorConfig={ueditorConfig}
                            richTabBarOptions={richTabBarOptions}
                            disabled={disabled}
                            token={token}
                            uploadUrl={uploadUrl}
                        />
                    )}
                </FormItem>
            );
        case "treeSelect":
            if (isMobile) {
                commProps = {
                    ...commProps,
                    ...mobileItem2FormItemLayout
                };
            }
            if (isMobile) {
                return (
                    <FormItem {...commProps}>
                        {getFieldDecorator(field, {
                            valuePropName: "defaultValue",
                            initialValue: initialValue || [],
                            rules: [
                                {
                                    required: required,
                                    message
                                }
                            ]
                        })(
                            <PullPersonMobile
                                edit={!inputProps.disabled}
                                myFetch={myFetch}
                                {...treeSelectOption}
                                {...inputProps}
                            />
                        )}
                    </FormItem>
                );
            }

            return (
                <FormItem {...commProps}>
                    {getFieldDecorator(field, {
                        valuePropName: "defaultValue",
                        initialValue: initialValue || [],
                        rules: [
                            {
                                required: required,
                                message
                            }
                        ]
                    })(
                        <PullPerson
                            edit={!inputProps.disabled}
                            treeData={[]}
                            myFetch={myFetch}
                            {...treeSelectOption}
                            {...inputProps}
                        />
                    )}
                </FormItem>
            );
        case "item": //
            return (
                <FormItem {...commProps}>
                    {getFieldDecorator(field, {
                        rules: [
                            {
                                required: required,
                                message
                            }
                        ],
                        initialValue: initialValue || []
                    })(<Item />)}
                </FormItem>
            );
        case "Component": //
            delete commProps.labelCol;
            delete commProps.wrapperCol;
            commProps.className = `${commProps.className}  Component`;
            return (
                <div {...commProps}>
                    {Component({
                        ...QnnformProps,
                        state,
                        match, //兼容写法  下个版本删除
                        props: QnnformProps, //兼容写法  下个版本删除
                        isInQnnForm: true //所有自定义组件中能根据isInQnnForm做适配
                    })}
                </div>
            );
        case "treeNode":
            if (isMobile) {
                commProps = {
                    ...commProps,
                    ...mobileItem2FormItemLayout
                };
            }
            return (
                <FormItem {...commProps}>
                    {getFieldDecorator(field, {
                        rules: [
                            {
                                required: required,
                                message
                            }
                        ],
                        initialValue: initialValue || {}
                    })(
                        <TreeNode
                            myFetch={myFetch}
                            {...inputProps.treeNodeOption}
                            {...inputProps}
                        />
                    )}
                </FormItem>
            );
        default:
            return <div>未知类型</div>;
    }
};
