//打开选人的方法
const openTree = function(obj = {}) {
    this.setState({
        pullpersonField: obj.field || "pullPerson",
        pullpersonChangeFn: obj.pullpersonChangeFn || function() {},
        openTree: obj.openTree === false ? obj.openTree : true,
        treeOption: obj
    });
};

//关闭右边抽屉
const closeDrawer = function(show = false, cb = () => {}) {
    const { MyComponent } = this.state;

    this.setState(
        {
            DrawerShow: show,
            MyComponent:MyComponent
        },
        cb
    );
    //延时清空  不然在关闭瞬间将会看到表单
    if (!show) {
        setTimeout(() => {
            this.setState({
                MyComponent: null
            });
        }, 500);
    } 
};
const selectKey = field => `${field}_optionData`;
const isMobile = function() {
    return this.getDeviceType() === "mobile";
};
export { openTree, closeDrawer, selectKey, isMobile };
