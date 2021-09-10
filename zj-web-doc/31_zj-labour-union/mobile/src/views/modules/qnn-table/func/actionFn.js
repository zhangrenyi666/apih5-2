/* 表格中按钮点击后的方法 */

const tools = {

    //格式化表单字段中的无限联动字段(目前只是给无限联动设置值)
    forLinkage: function (obj,rowData) {
        //将无限联动值都置于一个对象中
        let _vals = {};

        //需要设置下拉选项
        if (obj.children) {
            let _cVals = this.forLinkage(obj.children,rowData);
            _vals = {
                ..._vals,
                ..._cVals
            }
        }
        _vals[obj.form.field] = rowData[obj.form.field] || "";
        return _vals;
    },

    //格式化表单字段显隐、禁用状态 和 获取表单字段值的方法
    formatFForms: function (type) {
        switch (type) {
            case "detail":
                return (formsField,tabs,rowData) => {
                    //表单字段值
                    let formValues = {};
                    //表单字段项
                    let _form = [...formsField];
                    //新的tabs
                    let newTabs = [];

                    for (let i = 0; i < _form.length; i++) {
                        let { field,detailShow,hide,children,type } = _form[i];
                        formValues[field] = rowData[field];
                        if (detailShow === true) {
                            _form[i].hide = false;
                        } else if (detailShow === false) {
                            _form[i].hide = true;
                        } else if (hide === true) {
                            _form[i].hide = true;
                        }
                        if (type === "linkage") {
                            let _cVals = this.forLinkage(children,rowData);
                            formValues = {
                                ...formValues,
                                ..._cVals
                            }
                        }
                        _form[i].disabled = true; //设置禁用
                    }

                    //tab页面
                    if (tabs && tabs.length) {
                        newTabs = tabs.map(item => {
                            if (item.name === "qnnForm") {
                                item.content.fetchConfig = item.content.fetchConfig || item.content.fetchConfig_backUp;
                                item.content.formConfig = item.content.formConfig.map(iitem => {
                                    let { field,detailShow,hide,children,type } = iitem;
                                    formValues[field] = rowData[field];
                                    if (detailShow === true) {
                                        iitem.hide = false;
                                    } else if (detailShow === false) {
                                        iitem.hide = true;
                                    } else if (hide === true) {
                                        iitem.hide = true;
                                    }
                                    if (type === "linkage") {
                                        let _cVals = this.forLinkage(children,rowData);
                                        formValues = {
                                            ...formValues,
                                            ..._cVals
                                        }
                                    }
                                    iitem.disabled = true;
                                    return iitem;
                                })
                            }
                            return item;
                        })
                    }
 
                    return {
                        formsField: _form,
                        formValues,
                        newTabs: newTabs
                    };
                }

            default:
                return null
        }

    }
};


//新增
const addBtnClick = function (params) {

}

//新增行
const addRowBtnClick = function (params) {

}

//编辑
const editBtnClick = function (params) {

}

//删除
const delBtnClick = function (params) {

}

//详情
const detailBtnClick = function ({ forms,rowData,tabs }) {
    let { formsField,formValues,newTabs } = tools.formatFForms("detail")(forms,tabs,rowData);
    return {
        formsField,formValues,newTabs
    }
}

export { addBtnClick,addRowBtnClick,editBtnClick,delBtnClick,detailBtnClick }