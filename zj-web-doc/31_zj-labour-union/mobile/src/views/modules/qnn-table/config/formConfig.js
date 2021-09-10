const defaultFormItemLayout = {
    //布局
    labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
    },
    wrapperCol: {
        xs: { span: 24 },
        sm: { span: 20 }
    }
};
//搜索布局
const formItemLayoutBySearch = {
    //布局
    labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
    },
    wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
    }
};

//搜索框表单配置  不使用qnn-form的按钮
const getFilterConfig = function (args) {
    return {
        form: this.props.form,
        fetch: this.props.fetch || this.props.myFetch,
        headers: this.props.headers,
        style: {
            height: "auto",
            overflow: "hidden",
            paddingBottom: 0,
            background: "transparent"
        },
        ...args
    };
};

//右抽屉表单配置  不使用qnn-form的按钮
const getFormConfig = function () {
    const { forms,drawerBtns = [],formItemLayout,formGlobalDisabledStatus } = this.state;
    let titleConDom = document.getElementsByClassName("ant-drawer-header");
    let titleConDomHeight = 55; //新增不知道为啥拿不到dom高  所有先给个默认的  需要排查
    if (titleConDom && titleConDom[0]) {
        titleConDomHeight = titleConDom[0].offsetHeight;
    }
    let _formItemLayout = formItemLayout || defaultFormItemLayout;
    //没有底部按钮时候需要在加上个底部按钮的高度
    let _height = !drawerBtns.length ? window.innerHeight - titleConDomHeight + 53 : window.innerHeight - titleConDomHeight //- 53; //53是按钮空间  
    if (!this.isMobile()) {
        _height = _height - 41;//因为pc端底部是90多px
    } 
    return {
        form: this.props.form,
        fetch: this.props.fetch || this.props.myFetch,
        headers: this.props.headers,
        tabs: this.props.tabs,
        formConfig: [...forms].map(_item => {
            const item = { ..._item };
            if (formGlobalDisabledStatus) {
                item.disabled = true;
            }
            return item;
        }),
        refresh: this.refresh,
        setTabsIndex: this.setTabsIndex, //设置tabs项激活索引
        isInQnnTable: true,
        formItemLayout: _formItemLayout,
        style: {
            height: _height,
            overflowY: "scroll",
            overflowX: "hidden",
            paddingBottom: 94
        }
    };
};

//弹出窗的form配置
const getQnnFormModalConfig = function (args) {
    return {
        form: this.props.form,
        fetch: this.props.fetch || this.props.myFetch,
        headers: this.props.headers,
        formItemLayout: defaultFormItemLayout,
        refresh: this.refresh,
        isInQnnTable: true,
        ...args
    };
};

export {
    getFilterConfig,
    getFormConfig,
    getQnnFormModalConfig,
    defaultFormItemLayout as formItemLayout,
    formItemLayoutBySearch
};
