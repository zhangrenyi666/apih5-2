//当搜索条件多的时候添加的展开状态
const toggle = function () {
    const { expand } = this.state;
    this.setState({ expand: !expand });
};

//搜索
const search = function () {
    const { fetchConfig = {} } = this.state;
    const { success } = fetchConfig;
    this.getFromParams("search",values => {
        this.setState(
            {
                curPage:1,
                searchParams: values
            },
            () => {
                this.refresh(success);
            }
        );
    });
};

//重置搜索条件
const handleReset = function () {
    const { fetchConfig = {} } = this.state;
    const { success } = fetchConfig;
    this.props.form.resetFields();
    this.setState(
        {
            searchParams: {}
        },
        () => {
            this.refresh(success);
        }
    );
};

export { toggle,search,handleReset };
