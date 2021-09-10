//获取值 返回节点信息
const getValue = function () {
    const { value } = this.state;
    return value;
};

//设置值  params{[key]:[value], ...}
const setValue = function (value) {
    this.setValue({
        value,
        valueByTree: value[this.keys.value]
    });
};

const clearValue = function () {
    if (this.props.onChange) {
        this.props.onChange(null);
    }
    if (this.props.reset) {
        this.props.reset()
    }
    this.setState({
        value: null,
        valueByTree: null
    });
};
 
//设置树数据
const setTreeData = function (treeData,cb) {
    const { label,children } = this.keys;
    //必须格式化每个节点都包含有title、children属性 
    let loop = (item) => {

        item.title = item[label];
        item.children = item.children || item[children];

        if (item.children && item.children.length) {
            item[children] = item.children = item.children.map(loop);
        }
        return item;
    }
    treeData = treeData.map(loop);
    // console.log("设置值:", treeData)
    this.setState({
        data: [...treeData],
        treeData: [...treeData]
    },cb);
}

export { getValue,setValue,clearValue,setTreeData };
