const formItemLayout = {
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
const getFilterConfig = function(args) {
    return {
        form: this.props.form,
        fetch: this.props.fetch,
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
const getFormConfig = function() {
    const { forms } = this.state;  
    let titleConDom = document.getElementsByClassName("ant-drawer-header");
    let titleConDomHeight = 0;
    if (titleConDom && titleConDom[0]) {
        titleConDomHeight = titleConDom[0].offsetHeight; 
    } 
    return {
        form: this.props.form,
        fetch: this.props.fetch,
        headers: this.props.headers,
        tabs: this.props.tabs,
        formConfig: forms,
        refresh: this.refresh,
        setTabsIndex: this.setTabsIndex, //设置tabs项激活索引
        isInQnnTable: true,
        formItemLayout: formItemLayout,
        style: {
            height: window.innerHeight - titleConDomHeight - 94, //94是按钮空间
            overflowY: "scroll",
            overflowX: "hidden", 
        }
    };
};

//弹出窗的form配置
const getQnnFormModalConfig = function(args) {
    return {
        form: this.props.form,
        fetch: this.props.fetch,
        headers: this.props.headers,
        formItemLayout: formItemLayout,
        refresh: this.refresh,
        isInQnnTable: true,
        ...args
    };
};

export {
    getFilterConfig,
    getFormConfig,
    getQnnFormModalConfig,
    formItemLayout,
    formItemLayoutBySearch
};
