//获取参数需要绑定this
const getFromParams = function (type = "form",cb,isValidate) {
    let params = {};
    if (type === "search") {
        const { searchFroms } = this.state;

        if (isValidate === false) {
            // 不验证表单  
            const values = this.props.form.getFieldsValue();
            for (let i = 0; i < searchFroms.length; i++) {
                let { field } = searchFroms[i];
                params[field] = values[field] || "";
            }

            let _params = this.sFormatData(
                params,
                this.state.searchFroms,
                "get"
            );

            //特殊处理
            let _newParams = {};
            for (let key in _params) {
                if (_params.hasOwnProperty(key)) {
                    const element = _params[key];
                    //需要将search__去掉
                    key = key.substr(8,key.length);
                    _newParams[key] = element;
                }
            }
            cb(_newParams);

        } else {
            //验证表单  

            //获取搜索参数
            this.props.form.validateFieldsAndScroll((err,values) => {
                if (!err) {
                    for (let i = 0; i < searchFroms.length; i++) {
                        let { field } = searchFroms[i];
                        params[field] = values[field] || "";
                    }

                    let _params = this.sFormatData(
                        params,
                        this.state.searchFroms,
                        "get"
                    );

                    //特殊处理
                    let _newParams = {};
                    for (let key in _params) {
                        if (_params.hasOwnProperty(key)) {
                            const element = _params[key];
                            //需要将search__去掉
                            key = key.substr(8,key.length);
                            _newParams[key] = element;
                        }
                    }
                    cb(_newParams);
                }
            });
        }

    } else {
        //获取表单数据
        const { forms,tabs = [],tabsIndex } = this.state;
        let _forms = [...forms];
        if (tabs.length) {
            if (tabs[tabsIndex].name === "qnnForm") {
                _forms = tabs[tabsIndex].content.formConfig;
            }
        }

        //全部tabs表单数据
        let _allForms = [];
        if (tabs.length) {
            for (let i = 0; i < tabs.length; i++) {
                const name = tabs[i].name;
                if(name === "qnnForm"){
                    _allForms = [].concat(_allForms,tabs[i].content.formConfig)
                }
            }
        }

        //qnnForm类型的字段结合
        let qnnForms = [];

        let allQnnForms = [];

        //应该取值得字段名
        const _valArrs = [];

        const _allValArrs = [];

        //将联动组配置特殊处理
        _forms.map(item => {
            const { type,children } = item;
            if (type === "linkage") {
                //linkage类型没有唯一的field
                const forLinkage = obj => {
                    let _field = obj.form.field || obj.table.dataIndex;
                    //子集是对象
                    let _children = obj.children;
                    _valArrs.push(_field);
                    if (_children) {
                        forLinkage(_children);
                    }
                };
                forLinkage(children);
            } else if (type === "qnnForm") {
                qnnForms.push(item.field)
            } else {
                _valArrs.push(item.field);
            }

            return item;
        });

        _allForms.map(item => {
            const { type,children } = item;
            if (type === "linkage") {
                //linkage类型没有唯一的field
                const forLinkage = obj => {
                    let _field = obj.form.field || obj.table.dataIndex;
                    //子集是对象
                    let _children = obj.children;
                    _allValArrs.push(_field);
                    if (_children) {
                        forLinkage(_children);
                    }
                };
                forLinkage(children);
            } else if (type === "qnnForm") {
                allQnnForms.push(item.field)
            } else {
                _allValArrs.push(item.field);
            }

            return item;
        });



        if (isValidate === false) {

            // 不验证表单 
            const values = this.props.form.getFieldsValue(_valArrs);
            const valuesIncAllTabs = this.props.form.getFieldsValue(_allValArrs);
            const allValues = this.props.form.getFieldsValue();
            if (qnnForms.length) {
                for (let w = 0; w < qnnForms.length; w++) {
                    let _qnnFormItem = qnnForms[w];
                    //注意：sFormatData方法中只取_Block结尾的字段
                    values[_qnnFormItem] = allValues[`${_qnnFormItem}`];
                    values[`${_qnnFormItem}_Block`] = allValues[`${_qnnFormItem}_Block`];
                }
            }
            if (allQnnForms.length) {
                for (let w = 0; w < allQnnForms.length; w++) {
                    let _qnnFormItem = allQnnForms[w];
                    //注意：sFormatData方法中只取_Block结尾的字段
                    valuesIncAllTabs[_qnnFormItem] = allValues[`${_qnnFormItem}`];
                    valuesIncAllTabs[`${_qnnFormItem}_Block`] = allValues[`${_qnnFormItem}_Block`];
                }
            }
            let _paramsIncAllTabs = this.sFormatData({ ...valuesIncAllTabs },_allForms,"get");
            let _params = this.sFormatData({ ...values },_forms,"get");
            cb(_params,_paramsIncAllTabs);

        } else {

            //验证表单
            //不可再这里面做出错误后打断代码执行的操作  因为tabs页中会出问题(提交第一个tabs页数据是不需要验证第二个tabs页)
            this.props.form.validateFieldsAndScroll((err,allValues) => {
                let haveErr = false;
                const errs = this.props.form.getFieldsError(_valArrs);
                for (const key in errs) {
                    if (errs.hasOwnProperty(key)) {
                        const element = errs[key];
                        if (element) {
                            haveErr = true;
                            return;
                        }
                    }
                }

                if (err) {
                    //表单块不能给错误提示
                    //直接循环给每个错误字段设置一个错误
                    const foopErr = (error) => {
                        for (const key in error) {
                            if (error.hasOwnProperty(key)) {
                                let item = error[key];

                                //可能错误字段是个对象 也就是对象下才是error
                                if (!item.errors || !Array.isArray(item.errors)) {
                                    foopErr(item);
                                } else {
                                    const { errors = [] } = error[key];
                                    if (errors.length) {
                                        for (let i = 0; i < errors.length; i++) {
                                            let { field,message } = errors[i];
                                            const value = this.props.form.getFieldValue(field);

                                            //因为条件显隐的字段会隐藏时候回特殊设置hide===true
                                            const dom = document.getElementById(field);
                                            if (dom) {
                                                var domHideAttr = dom.getAttribute("hide");
                                                if (domHideAttr && domHideAttr === "true") {
                                                    //不需要验证该字段
                                                    delete error[key]
                                                }
                                            }

                                            haveErr = true;

                                            this.props.form.setFields({
                                                [field]: {
                                                    errors: [
                                                        {
                                                            field: [field],
                                                            message: message
                                                        }
                                                    ],
                                                    value: value
                                                }
                                            });

                                        }
                                        let field = field
                                    }

                                }

                            }
                        }
                    }
                    foopErr(err);
                }

                const values = this.props.form.getFieldsValue(_valArrs);
                const valuesIncAllTabs = this.props.form.getFieldsValue(_allValArrs);
                if (qnnForms.length) {
                    for (let w = 0; w < qnnForms.length; w++) {
                        let _qnnFormItem = qnnForms[w];
                        //注意：sFormatData方法中只取_Block结尾的字段
                        values[_qnnFormItem] = allValues[`${_qnnFormItem}`];
                        values[`${_qnnFormItem}_Block`] = allValues[`${_qnnFormItem}_Block`];
                    }
                }
                if (allQnnForms.length) {
                    for (let w = 0; w < allQnnForms.length; w++) {
                        let _qnnFormItem = allQnnForms[w];
                        //注意：sFormatData方法中只取_Block结尾的字段
                        valuesIncAllTabs[_qnnFormItem] = allValues[`${_qnnFormItem}`];
                        valuesIncAllTabs[`${_qnnFormItem}_Block`] = allValues[`${_qnnFormItem}_Block`];
                    }
                }
                if (haveErr) {
                    cb(false);
                } else {
                    let _params = this.sFormatData({ ...values },_forms,"get");
                    let _paramsIncAllTabs = this.sFormatData({ ...valuesIncAllTabs },_allForms,"get");
                    cb(_params,_paramsIncAllTabs);
                }
            });

        }


    }
};

export default getFromParams;
