//获取值 返回节点信息
const getValue = function() {
    const { value } = this.state;
    return value;
};

//设置值  params{[key]:[value], ...}
const setValue = function(value) {
    this.setValue({
        value,
        valueByTree: value[this.keys.value]
    });
};

const clearValue = function() {
    if (this.props.onChange) {
        this.props.onChange(null);
    }
    this.setState({
        value: null,
        valueByTree: null
    });
};
export { getValue, setValue, clearValue };
