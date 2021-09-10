### 版本更新日志

    现存问题: 
        [未完善] 点击 name 为 form 类型需要完善 

        
### v0.xx.xx （2019.4.3）

    新增 点击按钮后将按钮置为loding状态（submit类型按钮， 需要为按钮配置field属性）

    修改 新增和编辑抽屉不可点击阴影关闭，只能通过点击按钮或者右上角x关闭
         移动端布局自动取前面几个字段显示到列表

    修复 编辑时某个字段的initialValue是func时报错问题***
         移动端编辑报错问题




###### v0.34.27 （2019.4.3）

    **拆分qnn-table和qnn-form（但是不影响以前的引用）

    qnn-form

        新增
            身份证类型、电话号码类型控件

        修复
            tab页中页面高度不对问题
            层叠选择回显值不对问题
            fetchConfig可为func并且可配置delParams参数
            移动端表单项最后一个的地边框删除

    qnn-table

        新增
            actionBtns配置中新增hide属性配置
            操作列新增条件显隐配置

        修复
            嵌入到 tabs 中的 qnn-table 配置必须配置 tabs 项 否则 tabs 项将和上级重复 的问题
            **点击编辑或者详情时候字段中某个字段配置了isUrlParams:true,会报错的问题
            小尺寸表格编辑存在的问题
            formConfig配置长度改变后不刷新问题
            单元格修改会晃动的问题
            第一列固定在左边时搜索值不对问题
            size:small 时的问题
            tab页面新增时不在去请求配置的fetchConfig
            点击详情按钮tab页面中表单还能被输入
            表头边框修复
            tab中loading修复
            修复一些其他样式

###### v0.33.13 可适配移动端版本初始版

        qnn-table

            新增
                单元格编辑可实现某个单元格不能被编辑（提供disabled设置）
                fieldsValueChange属性
                formFetchConfig 属性
                mobileListItem 属性
                layout 属性
                title 属性(移动端页面标题)
                filter 支持month类型
                表格右侧按钮项新增labelStyle（为了配合label使用）
                antd属性中的title属性可配置为string类型（并且可配置titleStyle配合使用）
                diyTableRow属性
                getRowSelection属性


            修复
                filter 时间日期组件选中后筛选图标不变色
                抽屉中的tabs项中的filter设置导致报错
                antd表头分组在组件中使用不可编辑的问题

            修改
                tdEdit属性 可为bool 或者 (rowData,colData,props)=>bool



        qnn-form

            新增
                data 属性(可设置死数据)
                传入的fetchConfig有变化将自动刷新数据
                colWrapperStyle、colWrapperClass属性
                ...

            修复
                联动组件设置值存在的问题
                自定义组件在表单块中的问题

            修改
                required配置可为func

###### v0.22.9 （2019.4.10）

        qnn-form  新增 fetchConfig 属性中可配置success回调函数
        qnn-table 新增 fetchConfig 属性中可配置success回调函数
        qnn-table 新增 fetchConfig 属性中可配置success回调函数
        qnn-table 新增 getBackEndConfig配置 （配置可从后台获取）
        qnn-table 新增 firstRowIsSearch配置（可设置表格第一行为搜索行）

###### v0.13.9 （2019.3.11）

        修复 tabs提交时候表单验证问题
        修复 tabs提交时获取表单值的问题
        修复 qnn-table中取消按钮点击后会验证表单问题
        修复 qnn-form中按钮宽的问题
        修复 qnn-tabel中配置filter后点击搜索limit没有了的问题
        修复一些其他问题

        新增 qnn-form  images配置的 desc、icon、wrapperStyle 属性
        新增 qnn-form colStyle属性
        新增 qnn-form  formContainerOnScroll属性
        新增 qnn-form  formContainerLayoutLeftAndRright属性
        新增 qnn-form  optionConfig配置的linkageFields属性

###### v0.8.3 （2019.2.21）

        优化 qnn-table的fetchConfig中属性更新或者data更新将会重新请求数据渲染或者从新根据数据渲染

        修复 百度富文本文件适配react

        新增 qnn-table、qnn-form组件新增componentsKey属性, 自定义组件即可设置key和配置文件关联
        新增 qnnForm 类型组件
        新增 qnn-table、qnn-form的tabs配置新增disabled属性（可用于控制当前tabs项是否能被点击）
        新增 qnn-table 抽屉中的formBtns配置可为func
        新增 qnn-table 抽屉中的formBtns配置中的每项数据可配置hide属性（控制显隐）

        修改 qnn-table中actionBtn中的提交按钮点击后提交的是当前tab页中的内容（以前提交的是所有tabs项的表单内容）

###### v0.3.0 （2019.1.28）

        新增qnn-form oldValue、oldValueKey属性   可在多文本框上渲染出列表数据
        新增qnn-form styleType属性 可切换表单样式  目前内置0 1样式
        增加qnn-form  formConfig属性可配置为后台返回数据
        修復qnn-table formConfig属性为func时的问题
        新增qnn-table、qnn-form的tabs属性
        新增qnn-table formConfig项tdEdit属性
        新增qnn-table formConfig项tdEditCb属性
        新增qnn-table formConfig项fetchCb属性
        新增qnn-table formConfig项fieldsConfig属性
        qnn-table 新增actionBtnsPosition属性配置（操作按钮位置可放到bottom|top）
        qnn-table btns项新增addRow类型的按钮
        新增qnn-form  qnnTable控件
        修复表单为tabs配置fetchConfig无效问题

###### v0.2.0 （2019.1.18）

        改版内容变化较大***

        修復内置拉人性能问题
        修复富文本编辑问题 ing...
        删除qnn-form中的拉人插件将直接使用mudules下的拉人组件
        修复一些其他问题...

###### v0.11.19 （2019.1.8）

        字符串字段（string, number, textarea, url, number）字段新增voice（语音输入属性）
        新增qnn-form cameraByNoName控件
        修复qnn-form hide为function的问题...(一些v0.11.15带来的问题)
        修改完善流程

###### v0.11.15 （2018.12.28）

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
