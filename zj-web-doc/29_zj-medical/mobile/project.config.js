//项目结构配置  ---开发中
module.exports = {
    fileRootPath:'src/views',
    routeConfigPath:'src/views/index.js',
    pages: [
        {
            configFileName:'Test.js',
            fileName: "Test.js",
            routeInfo: {
                label: "测试页面",
                defaultPath: "Test/0/1",
                pathName: "Test/:id/:id1",
                comKey: "Test"
            },
            comsKeyObj:{
                diyComponent:"src/views/aaa.js"
            }
        }
    ]
};
