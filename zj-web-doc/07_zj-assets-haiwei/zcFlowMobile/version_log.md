##框架代码改变日志


#### 2019.8.28

    1.加入升级小工具
    说明：
        目录里面多出来一个updateTool文件夹  复制到别的项目时候无需复制

        直接点击getUpdateEnv.bat脚本等待运行完毕后再执行 apih5 -h 查看帮助
        或者直接在任意地方打开命令行窗口后运行 apih5 target D://xxx  来升级该目录的项目


#### 2019.8.23
 
    1.将共通页面和组件单独抽离到node_modules文件夹下管理 
        需要更新替换的文件夹或者文件：

            替换/src/view下的所有文件夹

            /src/view/modules文件夹可直接删除  

            引用modules下文件夹方式改为apih5/modules/xxx




#### 2019.8.22
 
    1.修复【组织架构】页面切换表格页数量时系统异常问题
        需要更新替换的文件夹或者文件：
            /src/contacts  

    2.可直接在页面中调用props中的updateProjectListData方法更新项目列表

    

#### 2019.8.16

    1.新增弹出强制重置密码的弹窗
        需要更新替换的文件夹或者文件：
            /src/modules/layouts   
            

#### 2019.8.12

    1.修复【组织架构】页面深层节点新增时候回收起来的问题
         需要更新替换的文件夹或者文件： 
            /src/contacts  
                         

    2.修改菜单管理页面菜单json数据展示格式化
        需要更新替换的文件夹或者文件：   
            /src/menus  

            
    3.修改菜单管理页面菜单json数据中的iconType可配置http路径
        需要更新替换的文件夹或者文件：
            /src/contacts  

            
    4.配置菜单名字可换行
        需要更新替换的文件夹或者文件：
            /src/modules/layouts        
                           
                           



#### 2019.8.9

    1.修复【系统管理】菜单下[组织架构]和[角色管理]页面获取的树结构返回数组报错问题和一些其他问题。
        需要更新替换的文件夹或者文件：
            /src/views/App              [移动端切换项目]
            /src/modules/layouts        [pc端切换项目]
            /src/contacts               [部门数据换成数组问题]
            /src/roles                  [部门数据换成数组问题]

    2.修改网页左上角标题可换行
        需要更新替换的文件夹或者文件：
            /src/modules/layouts


####

    新增code方式登录
        需要覆盖文件 apih5_modules/index.js 文件 和  /views/login 文件夹


    编译打包时候加入加入babel-babel-polyfill

    更改chunk时将小于100kb的包合并了

    修复在路由跳转时候（router.js 290）时，路由跳转重报错问题
