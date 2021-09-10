//打包时适配下qnn-table系列组件
const paths = require("./paths");
const fs = require("fs");
const chalk = require("react-dev-utils/chalk");
const cdnsUrlArr = require(paths.cdns);
var projectConfig = null;
const checkDir = fs.existsSync(paths.projectConfig);
//确认配置路径是否存在
if (checkDir) {
    projectConfig = require(paths.projectConfig);
}
//整理出需要插入得到html中的cdn地址和qnn组件配置
const insertProUrl = function() {
    //所有cdn文件
    let cdns = [...cdnsUrlArr];
    console.log(
        chalk.green(`
            使用cdn文件共有:${chalk.bold(cdns.length)}个\n
            数据如下：\n
            ${chalk.cyanBright(cdns.join(`\n`))}
        `)
    );
    //所有的配置页面
    let _pages = [];
    try {
        _pages = fs.readdirSync(paths.pages);
    } catch (err) {
        console.log(
            chalk.yellow(
                `读取public/pages文件夹出错：${JSON.stringify(
                    err
                )}，\n 所有cdn文件或者页面配置文件必须手动写入到html文件中。`
            )
        );
    }
    _pages = _pages.map(item => {
        return `${
            process.env.PUBLIC_URL
        }/pages/${item}?t=${new Date().getTime()}`;
    });
    console.log(
        chalk.green(`
            配置页面共有:${chalk.bold(_pages.length)}个\n
            数据如下：\n
            ${chalk.cyanBright(_pages.join(`\n`))}
        `)
    );
    const pages = [...cdns, ..._pages];
    return pages;
};

//根据qnn页面配置创建view下的js文件
const createQnnJsxPage = function() {
    if (projectConfig) {
        const { fileRootPath, routeConfigPath, pages = [] } = projectConfig;
        for (const item of pages) {
            let { fileName, configFileName, routeInfo = {} } = item;
            if (!routeInfo || !fileName) {
                console.log(chalk.red(`project.config配置有误！！！`));
                return;
            }
            let fileUrl = `${fileRootPath}/${fileName.replace(
                /\.(js|css)/,
                ""
            )}/${fileName}`;
            let dirUrl = `${fileRootPath}/${fileName.replace(
                /\.(js|css)/,
                ""
            )}`;
            const checkDir = fs.existsSync(dirUrl);
            if (!checkDir) {
                fs.mkdirSync(`${dirUrl}`, 0777);
            }
            //写入html模板
            fs.appendFileSync(
                fileUrl,
                `
                import React, { Component } from "react";
                import QnnTable from "../modules/qnn-table"; 
                const DIY_COMPONENT = obj => {
                    return <div>自定义组件byTable</div>;
                };
                const comsKeyObj = {
                    diyComponent: DIY_COMPONENT
                };
                class index extends Component {
                    state = {
                        otherParams: {}
                    };

                    componentDidMount() {
                        //更改fetchConfig后将动态请求
                        // setTimeout(() => {
                        //     this.setState({
                        //         otherParams:{
                        //             flowId:'zxHwAqHiddenDangerJl'
                        //         }
                        //     });
                        // }, 2000); 
                    }

                    render() {
                        return (
                            <QnnTable
                                {...this.props}
                                fetch={this.props.myFetch}
                                headers={{
                                    token: this.props.loginAndLogoutInfo.loginInfo.token
                                }}
                                {...window.${fileName.replace(/\.(js|css)/, "")}}
                                componentsKey={{
                                    ...comsKeyObj
                                }} 
                            />
                        );
                    }
                }

                export default index; 
            `,
                { flag: "w" }
            );

            // console.log(item);
        }
    }
};

module.exports = {
    insertProUrl: insertProUrl,
    createQnnJsxPage: createQnnJsxPage
};
