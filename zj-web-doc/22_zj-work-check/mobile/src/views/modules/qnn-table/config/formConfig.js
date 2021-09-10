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
  const { titleConDomHeight=0, forms } = this.state; 
  return {
    form: this.props.form,
    fetch: this.props.fetch,
    headers: this.props.headers,
    formConfig: forms,
    refresh: this.refresh,
    isInQnnTable: true,
    formItemLayout: formItemLayout,
    style: {
      height: window.innerHeight - titleConDomHeight,
      overflowY: "scroll",
      overflowX: "hidden",
      paddingBottom: "44px"
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
