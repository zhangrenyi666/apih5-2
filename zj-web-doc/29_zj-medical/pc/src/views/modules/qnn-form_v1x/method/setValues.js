//需要绑定this
import moment,{ isMoment } from "moment";
import getDeviceType from "../tool/getDeviceType";
import { setSelectOptionData } from '../method'
let _isMobile = () => getDeviceType() === "mobile";

const formatData = function (
    params,
    formConfig,
    type = "set",
    isMobile = _isMobile(),
    // formBlocks
) {
    const data = { ...params };
    //set是设置为表单需要的 get是从表单取出来
    let newData = {};
    if (type === "set") {
        for (let i = 0; i < formConfig.length; i++) {
            let {
                type,
                field,
                dataIndex,
                defaultValue,
                initialValue,
                multiple,
                pullJoin = true,
                children,
                isUrlParams,
                qnnFormConfig
            } = formConfig[i];
            field = field || dataIndex;
            if (isUrlParams) {
                let _params = this.props.match.params[field];
                initialValue = _params ? _params : initialValue;
            }

            //某些属性可以是func
            if (typeof initialValue === "function") {
                initialValue = initialValue({
                    ...this.funcCallBackParams
                });
            }
            if (typeof field === "function") {
                field = field({
                    ...this.funcCallBackParams
                });
            }

            /*
             ***数据源是 data 不是 newData***
             */
            switch (type) {
                case "date":
                case "time":
                case "datetime":
                case "month":
                    if (!data[field]) {
                        // newData[field] = initialValue || defaultValue || null;
                        //进行第一次赋值
                        newData[field] = defaultValue || null;
                        if (!newData[field]) {
                            //直接打断因为没有默认值了
                            newData[field] = null;
                        } else {
                            if (!isMobile && !isMoment(newData[field])) {
                                newData[field] = moment(data[field]);
                            }
                        }
                    } else {
                        //将时间戳位数处理
                        if (
                            data[field] &&
                            data[field].toString().length === 10
                        ) {
                            //需要补0
                            newData[field] = data[field] * 1000;
                        }
                        if (isMobile === false) {
                            //pc端
                            newData[field] = moment(data[field]);
                        } else {
                            newData[field] = new Date(data[field]);
                        }
                    }
                    break;
                case "cascader":
                    if (data[field]) {
                        newData[field] = data[field].split(",");
                    } else {
                        newData[field] = defaultValue
                            ? defaultValue
                            : initialValue || [];
                    }
                    break;
                case "select":
                    if (multiple) {
                        //开启多选的数据需要转换为数组
                        if (data[field] && pullJoin) {
                            newData[field] =
                                data[field] && data[field].split(",");
                        } else {
                            newData[field] = data[field]
                                ? data[field]
                                : defaultValue
                                    ? defaultValue
                                    : initialValue || [];
                        }
                    } else {
                        newData[field] = data[field]
                            ? data[field]
                            : defaultValue
                                ? defaultValue
                                : initialValue;
                    }
                    break;
                case "item":
                    if (pullJoin) {
                        //开启多选的数据需要转换为数组
                        newData[field] = data[field] && data[field].split(",");
                    } else {
                        newData[field] = data[field]
                            ? data[field]
                            : defaultValue
                                ? defaultValue
                                : initialValue;
                    }
                    break;

                case "switch":
                    newData[field] = data[field];
                    break;
                case "linkage":
                    //联动赋值
                    const forLinkage = obj => {
                        newData[obj.form.field] =
                            data[obj.form.field] || defaultValue || "";
                        if (obj.children) {
                            forLinkage(obj.children);
                        }
                    };
                    forLinkage(children);
                    break;
                case "checkbox":
                    if (data[field]) {
                        newData[field] = data[field].split(",");
                    } else {
                        newData[field] = defaultValue
                            ? defaultValue
                            : initialValue || [];
                    }
                    break;

                case "number":
                    newData[field] = data[field] === 0 ? 0 : (data[field] ? data[field] : (defaultValue || initialValue));
                    break;
                case "files":
                case "upload":
                case "camera":
                    if (!data[field]) {
                        data[field] = [];
                    } else if (!Array.isArray(data[field])) {
                        console.error(
                            `files或者upload类型的控件返回数据必须是array,【${field}】返回数据为非array数据`
                        );
                        data[field] = [];
                        return;
                    }

                    if (isMobile) {
                        newData[field] = data[field].map((item,index) => {
                            let {
                                mobileUrl,
                                name,
                                fileUrl,
                                fileName,
                                url
                            } = item;
                            item.url = mobileUrl || fileUrl || url;
                            item.name = name || fileName;
                            item.uid = index;
                            return item;
                        });
                    } else {
                        newData[field] = data[field].map((item,index) => {
                            let { url,name,fileUrl,fileName } = item;
                            item.url = url || fileUrl;
                            item.name = name || fileName;
                            item.uid = index;
                            return item;
                        });
                    }

                    break;


                case "qnnForm":
                    newData[field] = data[field];

                    //设置表单块的值
                    //直接给相应的表单块设置值即可 因为form对象是分离的
                    if (this.formBlocks) {

                        if (newData[field]) {

                            //格式化值
                            let _newData = null;
                            if (Array.isArray(newData[field])) {
                                _newData = newData[field].map((item,index) => {
                                    let _vals = formatData.bind(this)(item,qnnFormConfig.formConfig,"set");
                                    for (const key in _vals) {
                                        if (_vals.hasOwnProperty(key)) {
                                            const element = _vals[key];
                                            _vals[`${field}_Block[${index}].${key}`] = element;
                                        }
                                    }
                                    return _vals
                                });

                                newData[field] = _newData;

                                //设置值后需要从新渲染 
                                this.setState({
                                    [`${field}InitialValue`]: _newData
                                })

                            } else {
                                //单纯的表单块
                                _newData = formatData.bind(this)(newData[field],qnnFormConfig.formConfig,"set");

                                //因为表单块的字段都是拼接成了`${父级field}_Block.${子级field}`的形式 所有需要改变
                                for (const key in _newData) {
                                    if (_newData.hasOwnProperty(key)) {
                                        const element = _newData[key];
                                        _newData[`${field}_Block.${key}`] = element;
                                    }
                                }
                                this.formBlocks[field].setValues(_newData)

                            }

                        }

                    } else {
                        console.error(`this.formBlocks未读取到，请检查！！！`)
                    }


                    break;
                default:
                    newData[field] = data[field]
                        ? data[field]
                        : defaultValue
                            ? defaultValue
                            : initialValue;
                    break;
            }
        }
    } else if (type === "get") {

        //获取表单的值
        for (let i = 0; i < formConfig.length; i++) {
            let {
                type,
                field,
                timestamp = 13,
                dataIndex,
                multiple,
                children,
                pushJoin = true,
                defaultValue,
                initialValue,
                qnnFormConfig
                // canAddForm
            } = formConfig[i];
            field = field || dataIndex;
            let arrayKey = field.split('.');
            if (arrayKey.length > 1 && arrayKey[0].indexOf('_Block') !== -1) {
                //去除第一个点前面部分  
                arrayKey.splice(0,1);
                field = arrayKey.join('.')
            }
            switch (type) {
                case "date":
                case "time":
                case "datetime":
                case "month":
                    if (data && data[field]) {
                        newData[field] = moment(data[field]).valueOf();
                        //如果需要十位数的时间戳加上timestamp:10的属性
                        if (timestamp === 10 && data[field]) {
                            let _strd = data[field].toString();
                            let _d = _strd.substr(0,10);
                            newData[field] = Number(_d);
                        }
                    } else {
                        newData[field] = "";
                    }
                    break;
                case "cascader":
                    if (pushJoin) {
                        //开启多选的数据需要转换为数组
                        newData[field] = data[field] && data[field].join(",");
                    } else {
                        newData[field] = data[field]
                            ? data[field]
                            : defaultValue
                                ? defaultValue
                                : initialValue;
                    }
                    break;
                case "checkbox":
                    if (pushJoin) {
                        //开启多选的数据需要转换为数组
                        newData[field] = data[field] && data[field].join(",");
                    } else {
                        newData[field] = data[field]
                            ? data[field]
                            : defaultValue
                                ? defaultValue
                                : initialValue;
                    }
                    break;
                case "select":
                    if (multiple && data[field] && pushJoin) {
                        //开启多选的数据需要转换为数组
                        newData[field] = data[field]
                            ? data[field].join(",")
                            : defaultValue;
                    } else {
                        newData[field] = data[field]
                            ? data[field]
                            : defaultValue
                                ? defaultValue
                                : initialValue;
                    }
                    break;
                case "item":
                    if (pushJoin) {
                        //开启多选的数据需要转换为数组
                        newData[field] = data[field] && data[field].join(",");
                    } else {
                        newData[field] = data[field]
                            ? data[field]
                            : defaultValue
                                ? defaultValue
                                : initialValue;
                    }
                    break;
                case "linkage":
                    const forLinkage = obj => {
                        newData[obj.form.field] =
                            data[obj.form.field] || defaultValue || "";
                        if (obj.children) {
                            forLinkage(obj.children);
                        }
                    };
                    forLinkage(children);
                    break;
                case "files":
                case "upload":
                    newData[field] =
                        data[field] &&
                        data[field].map((item,index) => {
                            let { fileUrl,url } = item;
                            item.url = fileUrl || url;
                            return item;
                        });
                    break;

                case "switch":
                    newData[field] = data[field];
                    break;
                case "qnnForm":

                    //这里需要注意的事form块使用的是独立的form对象
                    // console.log('qnnform元数据',data)
                    // newData[field] = formatData.bind(this)(data[field],qnnFormConfig.formConfig,"get");
                    // if (this.formBlocks) {
                    //     let _d = this.formBlocks[field].props.form.getFieldsValue();
                    //     newData[field] = _d[`${field}_Block`];

                    //     let _newData = null;
                    //     if (Array.isArray(newData[field])) {
                    //         //可增删改的表单块
                    //         _newData = newData[field].map(item => {
                    //             return formatData.bind(this)(item,qnnFormConfig.formConfig,"get");
                    //         });
                    //     } else {
                    //         //单纯的表单块
                    //         _newData = formatData.bind(this)(newData[field],qnnFormConfig.formConfig,"get");
                    //     }
                    //     newData[field] = _newData;
                    // } else {
                    //     console.error(`this.formBlocks未读取到，请检查！！！`)
                    // }


                    // newData[field] = data[`${field}_Block`];

                    let _newData = null;
                    if (Array.isArray(data[field])) {
                        //可增删改的表单块
                        _newData = data[field].map(item => {
                            return formatData.bind(this)(item,qnnFormConfig.formConfig,"get");
                        });
                    } else {
                        //单纯的表单块
                        _newData = formatData.bind(this)(data[field],qnnFormConfig.formConfig,"get");
                    }
                    newData[field] = _newData;

                    break;
                case "number":
                    newData[field] = data[field] === 0 ? 0 : (data[field] ? data[field] : defaultValue);
                    break;
                default:
                    newData[field] = data[field]
                        ? data[field]
                        : defaultValue
                            ? defaultValue
                            : initialValue;
                    break;
            }
        }
    }
    return newData;
};
const setValues = function (data) {
    //数据和是否return格式化好的值而不是直接设置进表单
    // const { setFieldsValue } = this.props.form;
    const { formConfig,tabs = [] } = this.state;
    let _formConfig = [...formConfig];
    let isMobile = this.isMobile();
    if (tabs.length) {

        //取所有为表单的tab页面的字段
        //赋值会给所有表单赋值
        _formConfig = [];
        tabs.map(item => {
            const { name,content } = item;
            if (name === "qnnForm") {
                _formConfig = _formConfig.concat(content.formConfig);
            }
            return item;
        });
    }
    let newData = formatData.bind(this,data,_formConfig,"set",isMobile)();
    // setFieldsValue(newData);

    //开发中...
    // console.log('设置值：',newData)
    // console.log('将设置的字段：',this.fieldsObj)
    //设置普通表单的值 
    for (const key in newData) {
        if (newData.hasOwnProperty(key)) {
            console.assert(this.fieldsObj,'fieldsObj对象不存在')
            if (this.fieldsObj && this.fieldsObj[key]) {
                const element = newData[key];
                if (element) {
                    this.fieldsObj[key].setValue(element);
                }
            }
        }
    }

    //请求下拉选项并且设置下拉选项
    setSelectOptionData.bind(this)()
};
export { formatData };
export default setValues;
