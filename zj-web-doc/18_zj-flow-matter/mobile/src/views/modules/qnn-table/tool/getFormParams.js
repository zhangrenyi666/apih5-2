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
        const { forms, tabs = [], tabsIndex } = this.state;
        let _forms = [...forms];
        if (tabs.length) {
            if (tabs[tabsIndex].name === "qnnForm") {
                _forms = tabs[tabsIndex].content.formConfig;
            }
        }
        
        //qnnForm类型的字段结合
        let qnnForms = [];

        //应该取值得字段名
        const _valArrs = [];
        
        //将联动组配置特殊处理
        _forms.map(item => {
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
            } else if (type === "qnnForm") { 
                qnnForms.push(item.field)
            } else {
                _valArrs.push(item.field);
            }

            return item;
        });

        //验证表单
        //不可再这里面做出错误后打断代码执行的操作  因为tabs页中会出问题(提交第一个tabs页数据是不需要验证第二个tabs页)
        this.props.form.validateFieldsAndScroll((err, allValues) => { 
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
            const values = this.props.form.getFieldsValue(_valArrs);
            if(qnnForms.length){
                for(let w=0;w<qnnForms.length;w++){
                    let _qnnFormItem = qnnForms[w]; 
                    //注意：sFormatData方法中只取_Block结尾的字段
                    // values[_qnnFormItem] = allValues[`${_qnnFormItem}_Block`]; 
                    values[`${_qnnFormItem}_Block`] = allValues[`${_qnnFormItem}_Block`]; 
                }
            } 
            if (haveErr) {
                cb(false);
            } else {
                let _params = this.sFormatData({ ...values }, _forms, "get");
                cb(_params);
            }
        });

        // let haveErr = false;
        // const errs = this.props.form.getFieldsError(_valArrs);
        // for (const key in errs) {
        //     if (errs.hasOwnProperty(key)) {
        //         const element = errs[key];
        //         if (element) {
        //             haveErr = true;
        //             return;
        //         }
        //     }
        // }
        // const values = this.props.form.getFieldsValue(_valArrs);
        // if (haveErr) {
        //     cb(false);
        // } else {
        //     let _params = this.sFormatData({ ...values }, _forms, "get");
        //     cb(_params);
        // }

        // return;
        // if (!tabs.length) {
        //     //普通頁面
        //     //应该取值得字段名
        //     const _valArrs = [];
        //     //将联动组配置特殊处理
        //     forms.map(item => {
        //         const { type, children } = item;
        //         if (type === "linkage") {
        //             //linkage类型没有唯一的field
        //             const forLinkage = obj => {
        //                 let _field = obj.form.field || obj.table.dataIndex;
        //                 //子集是对象
        //                 let _children = obj.children;
        //                 _valArrs.push(_field);
        //                 if (_children) {
        //                     forLinkage(_children);
        //                 }
        //             };
        //             forLinkage(children);
        //         } else {
        //             _valArrs.push(item.field);
        //         }

        //         return item.field;
        //     });
        //     const haveErr = false;
        //     const errs = this.props.form.getFieldsError(_valArrs);
        //     for (const key in errs) {
        //         if (errs.hasOwnProperty(key)) {
        //             const element = errs[key];
        //             if (element) {
        //                 haveErr = true;
        //                 return;
        //             }
        //         }
        //     }
        //     const values = this.props.form.getFieldsValue(_valArrs);
        //     if (haveErr) {
        //         cb(false);
        //     } else {
        //         cb(values);
        //     }
        //     return;
        // } else {
        //     //tabs页面
        //     console.log(tabsIndex);

        //     this.props.form.validateFieldsAndScroll((err, values) => {
        //         if (!err) {
        //             if (tabs.length) {
        //                 for (let i = 0; i < tabs.length; i++) {
        //                     let { name, content } = tabs[i];
        //                     if (name === "qnnForm") {
        //                         let forms = content.formConfig;
        //                         for (let i = 0; i < forms.length; i++) {
        //                             let { field, type, children } = forms[i];
        //                             //联动组需要特殊处理
        //                             if (type === "linkage") {
        //                                 //linkage类型没有唯一的field
        //                                 const forLinkage = obj => {
        //                                     params[obj.form.field] =
        //                                         values[obj.form.field] || "";
        //                                     if (obj.children) {
        //                                         forLinkage(obj.children);
        //                                     }
        //                                 };
        //                                 forLinkage(children);
        //                             } else {
        //                                 params[field] = values[field] || "";
        //                             }
        //                         }
        //                     }
        //                 }
        //             } else {
        //                 for (let i = 0; i < forms.length; i++) {
        //                     let { field, type, children } = forms[i];
        //                     //联动组需要特殊处理
        //                     if (type === "linkage") {
        //                         //linkage类型没有唯一的field
        //                         const forLinkage = obj => {
        //                             params[obj.form.field] =
        //                                 values[obj.form.field] || "";
        //                             if (obj.children) {
        //                                 forLinkage(obj.children);
        //                             }
        //                         };
        //                         forLinkage(children);
        //                     } else {
        //                         params[field] = values[field] || "";
        //                     }
        //                 }
        //             }

        //             let _obj = { ...params };
        //             let _params = {};
        //             if (tabs.length) {
        //                 //tabs页面应该把所有name为qnnForm的tab项值取出来
        //                 _params = {};
        //                 for (let w = 0; w < tabs.length; w++) {
        //                     let item = tabs[w];
        //                     if (!item.field) {
        //                         console.error(
        //                             "tabs项必须配置field属性并且field属性不可重复！！！  ---来自qnn-table的警告"
        //                         );
        //                         return;
        //                     }
        //                     if (item.name === "qnnForm") {
        //                         _params[item.field] = this.sFormatData(
        //                             { ..._obj },
        //                             item.content.formConfig,
        //                             "get"
        //                         );
        //                         // console.log({ ..._obj }, item.content.formConfig);
        //                     }
        //                 }
        //             } else {
        //                 //普通页面
        //                 _params = this.sFormatData(
        //                     { ..._obj },
        //                     this.state.forms,
        //                     "get"
        //                 );
        //             }

        //             cb(_params);
        //         } else {
        //             cb(false);
        //         }
        //     });
        // }
    }
};

export default getFromParams;
