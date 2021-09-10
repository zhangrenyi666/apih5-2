### v0.0.1


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
