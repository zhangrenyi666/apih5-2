-   log qnn-table && qnn-form 版本更新日志

    未修复问题： 
          name的form类型需要完善

#### v0.11.15 （2018.12.28）

        新增qnn-form treeNode控件
        新增qnn-form month控件
        新增qnn-form radio控件
        新增qnn-form checkbox控件
        新增qnn-form switch控件
        新增qnn-form rate控件
        新增qnn-form slider控件
        修改qnn-table infoAlert返回值可为 string | reactDom
        修改qnn-table render返回值可为 string | reactDom
        修改qnn-table onClick和render可同时使用
        修改qnn-form 所有字段optionData配置可为function (props)=array
        修改qnn-table showType属性到table属性下
        修复qnn-form pc端input框触焦页面滑动
        修复qnn-form 移动的输入时输入框会向左移动一点距离

###### v0.4.13

        优化代码
        搜索输入框按回车建将直接搜索
        新增单列搜索功能  filter属性

###### v0.3.13

        新增 hide属性可为function  ---qnn-form
        新增 移动端流程列表
        修复 移动端上传附件取值问题  ---qnn-form
        修复 一些老机型输入框会被键盘弹出会挡住  ---qnn-form
        修复 ios键盘出来后收起键盘是页面不自动弹回来  ---qnn-form
        修复 一些布局问题  ---qnn-form

###### v0.2.9

        qnn-table 新增 searchFormColNum  可直接设置搜索区域form一行有多少个字段
        修复自定义组件中包含新增 form 中的按钮

###### v0.1.7

        新增自定义组件可根据点击行数据字段设定
        qnn-table 自定义组件或者增删查改点击时候新增 drawerTitle 属性 如果点击后需要打开右边抽屉设置抽屉的标题 可为 function ()=>string
        删除 qnn-form 的固定高样式，也就是使用 qnn-form 需要为容器设置一个高
        修复 qnn-table 的 table 属性下 btns 里的按钮只能设置 render 不能设置 label 的问题，且在 render 参数中能获取到完整 props

###### v0.0.6

        每个字段可单独设置:
            formItemLayoutForm
            formItemLayoutSearch

###### v0.0.5

        新增富文本类型表单
        自定义组件中可以根据props中的isInQnnTable做适配
        修复qnn-table apiName为function时无效

###### v0.0.49

        移动端样式完善

###### v0.0.48

        点击按钮增加自定义组件类型
        新增富文本类型

###### v0.0.47

        增加desc属性
        修复表单取值错误
        table增加select类型
        增加表格单元格可点击配置
        ...

###### v0.0.463

        修复表格控制区edit name类型

###### v0.0.462

        修复无限联动下拉

###### v0.0.461

        修复新增修改详情隐藏

###### v0.0.46

        修复默认值问题
        新增linkage组件

###### v0.0.45

        新增item组件
        修改字段在新增和详情编辑时字段可针对该动作下单独出现或者隐藏
        修改下拉或者层级下拉多选时自动将值用逗号连接给后台，后台只需要存入即可
        按钮内置name新增form  可弹出qnn-form组件  ----暂未完善

###### v0.0.43

        添加camera组件 | 给时间类组件添加 timestamp 属性可将后台给的10位时间戳转为13位 将前台13位转为10位给后台
