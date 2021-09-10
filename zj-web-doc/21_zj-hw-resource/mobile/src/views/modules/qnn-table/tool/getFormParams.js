//获取参数需要绑定this 
const getFromParams = function(type = "form", cb) {
    let params = {};
    if (type === "search") {
        const { searchFroms } = this.state;
        //获取搜索参数
        this.props.form.validateFieldsAndScroll((err, values) => {
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
                        key = key.substr(8, key.length);
                        _newParams[key] = element;
                    }
                }
                cb(_newParams);
            }
        });
    } else {
        //获取表单数据
        const { forms, tabs = [] } = this.state; 
        if (!tabs.length) {
            //普通頁面
            //应该取值得字段名
            const _valArrs = [];
            //将联动组配置特殊处理 
            forms.map(item => {
                const { type, children } = item;
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
                } else {
                    _valArrs.push(item.field);
                }

                return item.field;
            }); 
            const errs = this.props.form.getFieldsError(_valArrs);
            for (const key in errs) {
                if (errs.hasOwnProperty(key)) {
                    const element = errs[key];
                    if (element) {
                        return;
                    }
                }
            }
            const values = this.props.form.getFieldsValue(_valArrs);
            cb(values);
            return;
        }
        this.props.form.validateFieldsAndScroll((err, values) => {
            if (!err) { 
                for (let i = 0; i < forms.length; i++) {
                    let { field, type, children } = forms[i];
                    //联动组需要特殊处理
                    if (type === "linkage") {
                        //linkage类型没有唯一的field
                        const forLinkage = obj => {
                            params[obj.form.field] =
                                values[obj.form.field] || "";
                            if (obj.children) {
                                forLinkage(obj.children);
                            }
                        };
                        forLinkage(children);
                    } else {
                        params[field] = values[field] || "";
                    }
                }
                let _obj = { ...params };
                let _params = {};
                if (tabs.length) {
                    //tabs页面应该把所有name为qnnForm的tab项值取出来
                    _params = {};
                    for (let w = 0; w < tabs.length; w++) {
                        let item = tabs[w];
                        if (!item.field) {
                            console.error(
                                "tabs项必须配置field属性并且field属性不可重复！！！  ---来自qnn-table的警告"
                            );
                            return;
                        }
                        if (item.name === "qnnForm") {
                            _params[item.field] = this.sFormatData(
                                { ..._obj },
                                item.content.formConfig,
                                "get"
                            );
                        }
                    } 
                } else {
                    //普通页面
                    _params = this.sFormatData(
                        { ..._obj },
                        this.state.forms,
                        "get"
                    );
                }

                cb(_params);
            }
        });
    }
};

export default getFromParams;
