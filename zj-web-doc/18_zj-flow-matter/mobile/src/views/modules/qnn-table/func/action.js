import { message as Msg, Modal } from "antd";
import React from "react";
import _addField from "./_addField";

const confirm = Modal.confirm;
//需要绑定this
const action = function(rowInfo, rowData) {
    const {
        labelConfig,
        selectedRows,
        forms,
        data,
        tabs = [],
        tabsIndex,
        editData
    } = this.state;
    const { componentsKey = {} } = this.props;
    const {
        name,
        onClick,
        fetchConfig = {},
        addRowFetchConfig = {},
        // formBtns = [],
        formTitle,
        qnnFormOption,
        modalOption,
        //只有点击打开的是自定义组件才会用到
        Component,
        //将要执行前执行
        willExecute,
        drawerTitle,
        addRowDefaultData = {},
        addCb,
        paramsFormat
        // btns=[]
    } = rowInfo;
    let { formBtns } = rowInfo;
    let { apiName, otherParams = {} } = fetchConfig;
    if (typeof otherParams === "function") {
        otherParams = otherParams({
            ...this.props,
            fetch: this.fetch,
            btnCallbackFn: this.btnCallbackFn,
            state: this.state
        });
    }

    if (typeof formBtns === "function") {
        formBtns = formBtns({
            ...this.props,
            fetch: this.fetch,
            btnCallbackFn: this.btnCallbackFn,
            state: this.state
        });
    }

    if (typeof apiName === "function") {
        apiName = apiName({
            ...this.props,
            fetch: this.fetch,
            btnCallbackFn: this.btnCallbackFn,
            state: this.state
        });
    }
    let _date = {}; //详情或者编辑时候的数据
    let clickCb = {
        selectedRows,
        rowData,
        rowInfo,
        btnCallbackFn: this.btnCallbackFn,
        _formData: this.props.form.getFieldsValue(),
        props: this.props,
        state: this.state
    };
    this.getFromParams("form", values => {
        clickCb.formData = values;
    });
    this.getFromParams("search", values => {
        clickCb.searchData = values;
    });

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
        willExecute({
            ...clickCb
        });
    }
    let drawerDetailTitle = "";
    if (drawerTitle) {
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
                let { addShow, hide } = forms[i];
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
            this.closeDrawer(true); //打开抽屉
            this.setState({
                clickCb,
                forms,
                drawerDetailTitle: drawerDetailTitle
                    ? drawerDetailTitle
                    : "新增",
                drawerBtns: formBtns
            });
            break;
        case "addRow":
            const newRowData = { ...addRowDefaultData };
            if (addRowFetchConfig.apiName) {
                let { apiName, otherParams = {} } = addRowFetchConfig;
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
                this.fetch(apiName, { ...newRowData, ...otherParams }).then(
                    ({ success, message }) => {
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
                    data: [...data, newRowData]
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
            this.setState({
                clickCb,
                drawerDetailTitle: drawerDetailTitle
                    ? drawerDetailTitle
                    : "编辑",
                drawerBtns: formBtns
            });
            for (let i = 0; i < forms.length; i++) {
                // let { addShow = true, editShow = true, detailShow = true } = form;
                let { editShow, field, hide, children, type } = forms[i];
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
            setTimeout(() => {
                _date = this.sFormatData(_date, this.state.forms, "set");
                this.props.form.setFieldsValue({ ..._date });
            }, 0);
            break;
        case "detail":
            //打开抽屉并且设置默认值 目前只能使用列表里的数据
            for (let i = 0; i < forms.length; i++) {
                let { field, detailShow, hide, children, type } = forms[i];
                _date[field] = rowData[field];
                if (detailShow === true) {
                    forms[i].hide = false;
                } else if (detailShow === false) {
                    forms[i].hide = true;
                } else if (hide === true) {
                    forms[i].hide = true;
                }
                if (type === "linkage") {
                    forLinkage(children);
                }
                forms[i].disabled = true; //设置禁用
            }
            this.setState(
                {
                    clickCb,
                    drawerDetailTitle: drawerDetailTitle
                        ? drawerDetailTitle
                        : "详情",
                    drawerBtns: formBtns,
                    forms
                },
                () => {
                    this.closeDrawer(true); //打开抽屉
                    setTimeout(() => {
                        _date = this.sFormatData(
                            _date,
                            this.state.forms,
                            "set"
                        );
                        this.props.form.setFieldsValue({ ..._date });
                    }, 10);
                }
            );
            break;
        case "cancel":
            this.closeDrawer(false);
            break;
        case "submit":
            if (clickCb.formData) {
                let _body = { ...body, ...otherParams };
                if (paramsFormat) {
                    _body = paramsFormat({
                        ...clickCb,
                        params: _body
                    });
                }
                this.fetch(apiName, { ..._body }).then(
                    ({ data, success, message }) => {
                        if (success) {
                            this.refresh();
                            Msg.success(message);
                            this.closeDrawer(false);
                            if (onClick) {
                                onClick({
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
                    const { apiName, key } = fetchConfig;
                    let _pa = key
                        ? { [key]: _this.state.selectedRows }
                        : _this.state.selectedRows;
                    _this.fetch(apiName, _pa).then(({ success, message }) => {
                        if (success) {
                            Msg.success(message, 1, () => {
                                //删除后清空选中的数据
                                _this.setState({
                                    selectedRows: []
                                });
                            });
                        } else {
                            Msg.error(message);
                        }
                        _this.refresh();
                        if (onClick) {
                            onClick({
                                ...clickCb
                            });
                        }
                    });
                },
                onCancel() {}
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
            this.closeDrawer(true, () => {
                // this.setState(
                //     {
                //         clickCb,
                //         drawerDetailTitle: drawerDetailTitle
                //             ? drawerDetailTitle
                //             : "详情",
                //         MyComponent: <NewComponent {..._ComponentProps} />
                //     },
                //     () => {
                //         if (onClick) {
                //             onClick();
                //         }
                //     }
                // );
            });
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
                        onClick();
                    }
                }
            );
            break;

        case "_addField":
            _addField.bind(this)({ ...clickCb });
            break;
        case "_exportData":
            download("test.js", JSON.stringify(editData));
            break;
        case "_prev":
            confirm({ title: "暂不可用" });
            break; 
        case "allConfig":
            confirm({ title: "暂不可用" });
            break; 
        default:
            if (onClick && clickCb.formData) {
                onClick({
                    ...clickCb
                });
            }
            break;
    }
};

export default action;

//用于下载文件
function download(filename, text) {
    var element = document.createElement("a");
    element.setAttribute(
        "href",
        "data:text/plain;charset=utf-8," + encodeURIComponent(text)
    );
    element.setAttribute("download", filename);

    element.style.display = "none";
    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element);
}
