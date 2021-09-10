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
      MyComponent: show ? MyComponent : null
    },
    cb
  );
};
const selectKey = field => `${field}_optionData`;
const isMobile = function() {
  return this.getDeviceType() === "mobile";
};
export { openTree, closeDrawer, selectKey, isMobile };
