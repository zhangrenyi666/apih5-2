
//theme: "dev" 模式下支持该自定义配置
//该文件暂不支持热更新（修改文件后需要重启服务）
//以下属性配置将自动注入全局less变量

//目前菜单颜色支持配置如下：
module.exports = {
    // 顶部菜单和左侧菜单文字颜色
    themeByMenuText: "#fff",
    // 顶部菜单和左边菜单背景色
    themeByMenuBg: "rgb(26,170,216)",
    //顶部菜单和左边菜单 选中项 背景色
    themeByMenuActionItem: "#1890ff",
    //三级菜单文字颜色（相对四级来说）
    themeByMenuThreeMenuTitle: "#1890ff",

    //左侧菜单颜色定义（定义后颜色会覆盖上面的颜色）
    //左侧菜单文字颜色 
    themeByLeftMenuText: "rgb(252, 252, 252, 0.72)",
    //左边菜单背景色
    themeByLeftMenuBg: "rgb(60,87,104)",
    //左边菜单 选中项 背景色
    themeByLeftMenuActionItem: "#1D2C33",
}