//单元格被点击
const colClick = function({
    onClick,
    fetchConfig,
    formConfig,
    table,
    rows,
    data,
    drawerTitle,
    willExecute
}) { 
    switch (onClick) {
        case "add":
            this.action(
                {
                    willExecute,
                    drawerTitle,
                    name: "add", //内置add del
                    formBtns:
                        table.btns === false
                            ? []
                            : table.btns && table.btns.length
                            ? table.btns
                            : [
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
                    willExecute,
                    drawerTitle,
                    name: "edit",
                    formBtns: table.btns === false
                    ? []
                    : table.btns && table.btns.length
                    ? table.btns
                    : [
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
                    willExecute,
                    drawerTitle,
                    name: "detail"
                },
                rows
            );
            break;
        case "plus":
            this.action(
                {
                    willExecute,
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
                    willExecute,
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
