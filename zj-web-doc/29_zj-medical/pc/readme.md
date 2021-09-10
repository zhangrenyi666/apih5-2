### v0.0.xx

    新增code方式登录
        需要覆盖文件 lny_modules/index.js 文件 和  /views/login 文件夹


    编译打包时候加入加入babel-babel-polyfill

    更改chunk时将小于100kb的包合并了

    修复在路由跳转时候（router.js 290）时，路由跳转重报错问题




###   v0.0.1 


#### public/pages目录说明

    作用：
        1、没有pages文件夹的话，所有用到的页面配置都必须手动引入
        2、每次编译都会自动给pages文件夹下的文件后面追加?t=时间戳以防止缓存 

    ps：
        页面配置文件命名规则：大写字母开头
        下划线（_）开头的文件为内置模块需要
        下划线（_d_）开头的文件为dome配置，直接删除即可
        文件有所变动时在测试模式下会自动重载页面
            
#### public/cnds.js文件说明

    作用：
        只需将需要引用的cdn地址写到数组中，编译时将自动将cdn地址注入到html文件中
    
    配置格式
        [url, url, {path:xxx, type:xxx}, ...]


####  优化

    打包时自动会给build/index.html中的config.js引用后面加上?t=现在的时间戳以防止configjs缓存 
    无需在build.bat脚本中设置PUBLIC_URL=. 了，在编译打包时已经设置了。 
    将所有import的模块单独提出以减少main.js文件大小
    升级到最新的  webpak4 + babel7


####  更新步骤

    1、替换./public文件夹下的 index.html、index.css文件
    2、./public文件夹下新增cnds.js文件，新增pages文件夹 （注意增删cdn链接）
    3、替换config、scripts文件夹
    4、删除src/index.js中的  import 'babel-polyfill'; 这行代码
    5、替换项目目录下的/package.json 