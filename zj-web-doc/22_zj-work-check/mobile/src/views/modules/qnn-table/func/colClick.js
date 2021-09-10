//单元格被点击
const colClick = function({
  onClick,
  fetchConfig,
  formConfig,
  table,
  rows,
  data,
  drawerTitle
}) {
  switch (onClick) {
    case "add":
      this.action(
        {
          drawerTitle,
          name: "add", //内置add del
          formBtns: [
            {
              name: "cancel", //关闭右边抽屉
              type: "dashed", //类型  默认 primary
              label: "取消"
            },
            {
              name: "submit", //内置add del
              type: "primary", //类型  默认 primary
              label: "提交", //提交数据并且关闭右边抽屉
              fetchConfig
            }
          ]
        },
        {}
      );
      break;
    case "edit":
      this.action(
        {
          drawerTitle,
          name: "edit",
          formBtns: [
            {
              name: "cancel", //关闭右边抽屉
              type: "dashed", //类型  默认 primary
              label: "取消"
            },
            {
              name: "submit", //内置add del
              type: "primary", //类型  默认 primary
              label: "提交", //提交数据并且关闭右边抽屉
              fetchConfig
            }
          ]
        },
        rows
      );
      break;
    case "detail":
      this.action(
        {
          drawerTitle,
          name: "detail"
        },
        rows
      );
      break;
    case "plus":
      this.action(
        {
          drawerTitle,
          name: "del",
          fetchConfig
        },
        rows
      );
      break;
    case "Component":
      this.action(
        {
          ...this.props,
          drawerTitle,
          name: "Component",
          Component: table.Component,
          btnCallbackFn: this.btnCallbackFn,
          data,
          rows,
          formConfig
        },
        rows
      );
      break;
    case "editCol":
      //编辑单元格
      console.log("编辑单元格");
      break;
    default:
      console.error("onClick属性和name只能为内置按钮name 或者为function");
      break;
  }
};

export default colClick;
