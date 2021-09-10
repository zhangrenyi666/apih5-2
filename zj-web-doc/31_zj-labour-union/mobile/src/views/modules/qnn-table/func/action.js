import { message as Msg,Modal } from "antd";
import React from "react";
import _addField from "./_addField";
import _delField from "./_delField";
import { detailBtnClick } from "./actionFn"

//整个js待重构----(按照详情按钮写即可)

const confirm = Modal.confirm;
//需要绑定this
const action = function (rowInfo,rowData) {
    const {
        labelConfig,
        selectedRows,
        forms,
        data,
        tabsIndex,
        editData,
    } = this.state;
    let { tabs = [] } = this.state;
    const { componentsKey = {} } = this.props;
    const {
        name,
        onClick,
        addRowFetchConfig = {},
        formTitle,
        qnnFormOption,
        modalOption,
        //只有点击打开的是自定义组件才会用到
        Component,
        //将要执行前执行
        willExecute,
        addRowDefaultData = {},
        addCb,
        paramsFormat,
        field,
        isCanSubmit,
        isValidate
        // btns=[]
        // formBtns = [],
    } = rowInfo;


    let clickCb = {
        selectedRows,
        rowData,
        rowInfo,
        btnCallbackFn: this.btnCallbackFn,
        _formData: this.props.form.getFieldsValue(),
        props: this.props,
        state: this.state
    };

    let { drawerTitle,formBtns,fetchConfig = {} } = rowInfo;

    fetchConfig = this.bind(fetchConfig);
    if (fetchConfig && (typeof fetchConfig === "function")) {
        fetchConfig = fetchConfig({
            ...this.props,
            fetch: this.fetch,
            btnCallbackFn: this.btnCallbackFn,
            state: this.state,
            clickCb
        });
    }

    let { apiName,otherParams = {},delParams } = fetchConfig;

    otherParams = this.bind(otherParams);
    if (typeof otherParams === "function") {
        otherParams = otherParams({
            ...this.props,
            fetch: this.fetch,
            btnCallbackFn: this.btnCallbackFn,
            state: this.state,
            clickCb
        });
    }

    apiName = this.bind(apiName);
    if (typeof apiName === "function") {
        apiName = apiName({
            ...this.props,
            fetch: this.fetch,
            btnCallbackFn: this.btnCallbackFn,
            state: this.state,
            clickCb
        });
    }
    let _date = {}; //详情或者编辑时候的数据


    formBtns = this.bind(formBtns);
    if (typeof formBtns === "function") {
        formBtns = formBtns({
            ...this.props,
            fetch: this.fetch,
            btnCallbackFn: this.btnCallbackFn,
            state: this.state,
            rowData: rowData,
            clickCb: clickCb
        });
    }

    if (formBtns && formBtns.length) {
        formBtns = formBtns.map((item,index) => {
            if (!item.field) {
                item.field = `${item.name}_${index}`
            }
            return item;
        })
    }


    this.getFromParams("form",(values,formDataIncAllTabs) => {
        clickCb.formData = values;
        clickCb.formDataIncAllTabs = formDataIncAllTabs;
    },isValidate);
    this.getFromParams("search",values => {
        clickCb.searchData = values;
    },isValidate);

    //tabs配置存在的话需要去除当前页面的表单数据
    if (tabs.length && clickCb.formData && clickCb.formData[tabs[tabsIndex]]) {
        if (tabs[tabsIndex].field) {
            clickCb.curFormData = clickCb.formData[tabs[tabsIndex].field];
        } else {
            console.error(
                "tabs项必须配置唯一的field属性，否则无法取到curFormData数据"
            );
        }
    }
    //提交的数据
    const body = clickCb.curFormData ? clickCb.curFormData : clickCb.formData;
    //将要执行前 可使用该方法给改变路由参数
    if (willExecute) {
        this.bind(willExecute)({
            ...clickCb
        });
    }
    let drawerDetailTitle = "";
    if (drawerTitle) {
        drawerTitle = this.bind(drawerTitle);
        if (typeof drawerTitle === "function") {
            drawerDetailTitle = drawerTitle({
                ...clickCb,
                isInQnnTable: true
            });
        } else {
            drawerDetailTitle = drawerTitle;
        }
    } else if (labelConfig[name]) {
        drawerDetailTitle = labelConfig[name];
    }
    const forLinkage = obj => {
        //需要设置下拉选项
        _date[obj.form.field] = rowData[obj.form.field] || "";
        if (obj.children) {
            forLinkage(obj.children);
        }
    };


    switch (name) {
        case "add":
            for (let i = 0; i < forms.length; i++) {
                let { addShow,hide } = forms[i];
                if (addShow === true) {
                    forms[i].hide = false;
                } else if (addShow === false) {
                    forms[i].hide = true;
                } else if (hide === true) {
                    forms[i].hide = true;
                }

                forms[i].disabled =
                    forms[i].addDisabled === true ? true : false; //设置不禁用
            }

            //tab页面
            if (tabs && tabs.length) {
                tabs = tabs.map(item => {
                    if (item.name === "qnnForm") {
                        item.content.fetchConfig_backUp = item.content.fetchConfig;
                        item.content.fetchConfig = null;
                        item.content.formConfig = item.content.formConfig.map(iitem => {
                            let { addShow,hide } = iitem;
                            if (addShow === true) {
                                iitem.hide = false;
                            } else if (addShow === false) {
                                iitem.hide = true;
                            } else if (hide === true) {
                                iitem.hide = true;
                            }
                            iitem.disabled =
                                iitem.addDisabled === true ? true : false; //设置不禁用
                            return iitem;
                        })
                    }
                    return item;
                })
            }

            this.closeDrawer(true); //打开抽屉 
            this.setState({
                clickCb,
                forms,
                drawerDetailTitle: drawerDetailTitle
                    ? drawerDetailTitle
                    : "新增",
                drawerBtns: formBtns,
                maskClosable: false,
                tabs
            },() => {
                if (onClick) {
                    this.bind(onClick)({
                        ...clickCb,
                        isInQnnTable: true
                    })
                }
            });
            break;
        case "addRow":
            const newRowData = { ...addRowDefaultData };
            if (addRowFetchConfig.apiName) {
                let { apiName,otherParams = {} } = addRowFetchConfig;

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
                this.fetch(apiName,{ ...newRowData,...otherParams }).then(
                    ({ success,message }) => {
                        if (success) {
                            this.refresh();
                            Msg.success(message);
                        } else {
                            Msg.error(message);
                        }
                    }
                );
            } else if (addCb) {
                addCb({
                    newRowData,
                    ...clickCb
                });
            } else {
                console.error(
                    "未配置addCb或者fetchConfig  该数据只能本地测试使用"
                );
                this.setState({
                    data: [...data,newRowData]
                });
            }
            break;
        case "edit":
            if (!rowData && selectedRows && !selectedRows.length) {
                Msg.error("请选择一条数据");
                return;
            }
            if (!rowData) {
                if (selectedRows.length > 1) {
                    Msg.error("编辑时只能选择一条数据");
                    return;
                }
                rowData = selectedRows[0];
            }

            //打开抽屉并且设置默认值 
            this.closeDrawer(true); //打开抽屉
            // return;
            this.setState({
                clickCb,
                drawerDetailTitle: drawerDetailTitle
                    ? drawerDetailTitle
                    : "编辑",
                drawerBtns: formBtns.map((item,index) => {
                    if (!item.field) {
                        item.field = `${item.name}_${index}`
                    }
                    return item;
                }),
                maskClosable: false,
            });


            if (tabs && tabs.length) {
                //tab页面
                tabs = tabs.map(item => {
                    if (item.name === "qnnForm") {
                        item.content.fetchConfig = item.content.fetchConfig || item.content.fetchConfig_backUp;
                        item.content.formConfig = item.content.formConfig.map(iitem => {
                            let { editShow,field,hide,children,type } = iitem;
                            _date[field] = rowData[field];

                            if (type === "linkage") {
                                forLinkage(children);
                            }

                            if (editShow === true) {
                                iitem.hide = false;
                            } else if (editShow === false) {
                                iitem.hide = true;
                            } else if (hide === true) {
                                iitem.hide = true;
                            }

                            iitem.disabled =
                                iitem.editDisabled === true ? true : false; //设置不禁用
                            return iitem;
                        })
                    }
                    return item;
                })
            } else {
                //普通页面
                for (let i = 0; i < forms.length; i++) {
                    let { editShow,field,hide,children,type } = forms[i];
                    _date[field] = rowData[field];

                    if (type === "linkage") {
                        forLinkage(children);
                    }

                    if (editShow === true) {
                        forms[i].hide = false;
                    } else if (editShow === false) {
                        forms[i].hide = true;
                    } else if (hide === true) {
                        forms[i].hide = true;
                    }

                    forms[i].disabled =
                        forms[i].editDisabled === true ? true : false; //设置不禁用
                }
            }


            setTimeout(() => {
                this.qnnForm.setValues(_date);
                if (onClick) {
                    this.bind(onClick)({
                        ...clickCb,
                        isInQnnTable: true
                    })
                }
            },0);
            break;
        case "detail":
            //格式化出一些值
            let { formsField,formValues,newTabs } = detailBtnClick.bind(this)({
                forms: [...forms],
                rowData: { ...rowData },
                tabs
            });
            this.setState(
                {
                    clickCb,
                    drawerDetailTitle: drawerDetailTitle ? drawerDetailTitle : "详情",
                    drawerBtns: formBtns,
                    forms: formsField,
                    maskClosable: true,
                    tabs: newTabs
                },
                () => {
                    this.closeDrawer(true); //打开抽屉 
                    setTimeout(() => {
                        this.qnnForm.setValues(formValues);
                        if (onClick) {
                            onClick({
                                ...clickCb,
                                isInQnnTable: true
                            })
                        }
                    },10);
                }
            );

            break;
        case "cancel":
            this.closeDrawer(false);
            break;
        case "submit":
            if (clickCb.formData) {
                let _body = { ...body,...otherParams };
                let _newParams = {};
                if (paramsFormat) {
                    _body = this.bind(paramsFormat)({
                        ...clickCb,
                        params: _body
                    });
                }

                //删除一些参数
                if (delParams) {
                    for (const key in _body) {
                        if (key) {
                            const element = _body[key];
                            if (!delParams.includes(key)) {
                                _newParams[key] = element;
                            }
                        }
                    }
                    _body = _newParams;
                }

                let submitFn = () => {
                    //设置按钮loading状态
                    this.setBtnsLoading('add',field);

                    this.fetch(apiName,{ ..._body }).then(
                        ({ data,success,message }) => {

                            //请求完成后删除按钮 
                            this.setBtnsLoading('remove',field);
                            if (success) {
                                this.refresh();
                                Msg.success(message);
                                this.closeDrawer(false);
                                if (onClick) {
                                    this.bind(onClick)({
                                        data,
                                        success,
                                        message,
                                        ...clickCb
                                    });
                                }
                            } else {
                                Msg.error(message);
                            }
                        }
                    );
                }

                //是否可以提交
                if (isCanSubmit) {
                    this.bind(isCanSubmit)({
                        ...clickCb,
                        params: _body
                    },(can) => {
                        if (can) { submitFn() }
                    });
                } else {
                    submitFn()
                }

            }

            break;
        case "del":
            const _this = this;
            if (_this.state.selectedRows.length === 0) {
                Msg.error("请选择需要删除的数据");
                return;
            }
            confirm({
                title: "确认删除吗?",
                content: "",
                onOk() {
                    const { apiName,key } = fetchConfig;
                    let _pa = key
                        ? { [key]: _this.state.selectedRows }
                        : _this.state.selectedRows;

                    let delFn = () => {
                        _this.fetch(apiName,_pa).then(({ success,message }) => {
                            if (success) {
                                Msg.success(message,1,() => {
                                    //删除后清空选中的数据
                                    _this.setState({
                                        selectedRows: []
                                    });
                                });
                            } else {
                                Msg.error(message);
                            }

                            //一定要在刷新完后 回调否则会出现 一些问题
                            //比如删除完后直接调用 更新右上角项目信息 页面  将重新渲染，刷新完成后 将报错
                            _this.refresh(() => {
                                if (onClick) {
                                    _this.bind(onClick)({
                                        ...clickCb
                                    });
                                }
                            })

                        });
                    }

                    //是否可以提交
                    if (isCanSubmit) {
                        _this.bind(isCanSubmit)({
                            ...clickCb,
                            params: _pa
                        },(can) => {
                            if (can) { delFn() }
                        });
                    } else {
                        delFn()
                    }


                },
                onCancel() { }
            });
            break;
        case "form": //弹出form表单
            let btns = qnnFormOption.btns;
            delete qnnFormOption.btns;
            this.setState({
                modalVisible: true,
                modalTitle: formTitle,
                qnnFormOption,
                modalBtns: btns,
                modalOption,
                clickCb
            });
            break;
        case "Component": //弹出form表单
            delete rowInfo.form;
            let _ComponentProps = {
                ...clickCb,
                ...rowInfo,
                rowData,
                isInQnnTable: true
            };
            let NewComponent;

            //组件如果是对象的话说明是会根据行数据展现组件
            if (typeof Component === "object") {
                for (const key in Component) {
                    let comObj = Component[key];
                    let curVal = rowData[key];
                    if (curVal && comObj[curVal]) {
                        NewComponent = comObj[curVal];
                    }
                }
                if (!NewComponent) {
                    console.error(
                        "未有行值和组件对应上，请检查   ---来自qnn-table的警告"
                    );
                    return <div />;
                }
            } else {
                NewComponent = Component;
            }
            //设置自定义组件可用组件key
            if (typeof NewComponent === "string") {
                NewComponent = componentsKey[NewComponent];
                console.assert(
                    NewComponent,
                    `${NewComponent}自定义组件未传入到componentsKey对象中   ---来自qnn-table的错误`
                );
            }
            //打开抽屉
            this.closeDrawer(true);
            this.setState(
                {
                    clickCb,
                    drawerDetailTitle: drawerDetailTitle
                        ? drawerDetailTitle
                        : "详情",
                    MyComponent: <NewComponent {..._ComponentProps} />
                },
                () => {
                    if (onClick) {
                        this.bind(onClick)({ ...clickCb });
                    }
                }
            );
            break;

        case "_addField":
            _addField.bind(this)({ ...clickCb });
            break;
        case "_delField":
            _delField.bind(this)({ ...clickCb });
            break;
        case "_exportData":
            download("test.js",JSON.stringify(editData));
            break;
        case "_prev":
            confirm({ title: "暂不可用" });
            break;
        case "allConfig":
            confirm({ title: "暂不可用" });
            break;
        default:
            if (onClick && clickCb.formData) {
                for (let i = 0; i < forms.length; i++) {
                    forms[i].disabled =
                        forms[i].editDisabled === true ? true : false; //设置不禁用
                }
                //tab页面
                if (tabs && tabs.length) {
                    tabs = tabs.map(item => {
                        if (item.name === "qnnForm") {
                            item.content.fetchConfig_backUp = item.content.fetchConfig;
                            item.content.fetchConfig = null;
                            item.content.formConfig = item.content.formConfig.map(iitem => {
                                iitem.disabled =
                                    iitem.editDisabled === true ? true : false; //设置不禁用
                                return iitem;
                            })
                        }
                        return item;
                    })
                }
                this.setState({
                    clickCb,
                    drawerDetailTitle: drawerDetailTitle ? drawerDetailTitle : "修改",
                },() => {
                    this.bind(onClick)({
                        ...clickCb
                    });
                })
            }
            break;
    }
};

export default action;

//用于下载文件
function download(filename,text) {
    var element = document.createElement("a");
    element.setAttribute(
        "href",
        "data:text/plain;charset=utf-8," + encodeURIComponent(text)
    );
    element.setAttribute("download",filename);

    element.style.display = "none";
    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element);
}
