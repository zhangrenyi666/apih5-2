//当搜索条件多的时候添加的展开状态
const toggle = function() {
  const { expand } = this.state;
  this.setState({ expand: !expand });
};

//搜索
const search = function() {
  this.getFromParams("search", values => {
    this.setState(
      {
        searchParams: values
      },
      () => {
        this.refresh();
      }
    );
  });
};

//重置搜索条件
const handleReset = function() {
  this.props.form.resetFields();
  this.setState(
    {
      searchParams: {}
    },
    () => {
      this.refresh();
    }
  );
};

export { toggle, search, handleReset };
