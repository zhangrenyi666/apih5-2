import React, { Component } from 'react';
import MGrid from '../common/MGrid';
import SvgIcon from '../common/svgIcon';
class Index extends Component {
    render() {
        const { myPublic: { appInfo: { loginType, helpHref } }, routerInfo: { routeData, curKey } } = this.props
        const { moduleName } = routeData[curKey]
        const data = [
            {
                text: "技术准备",
                bgColor: "blue",
                route: `${moduleName}reserve`,
                content: <SvgIcon size="md" type={"wenindex4"} />
            },
            {
                text: "工序质量实时报验",
                bgColor: "blue",
                route: `${moduleName}procedure`,
                content: <SvgIcon size="md" type={"wenindex2"} />
            },
            {
                text: "疑难问题问答",
                bgColor: "red",
                route: `${moduleName}enquiry/add/3`,
                content: <SvgIcon size="md" type={"wenindex1"} />
            },
            {
                text: "我的待办",
                bgColor: "orange",
                route: `${moduleName}enquiry/index`,
                content: <SvgIcon size="md" type={"wenindex3"} />
            },
            {
                text: "本平台使用手册",
                bgColor: "green",
                route: helpHref,
                content: <SvgIcon size="md" type={"wenindex3"} />
            }
        ];
        if (loginType === "3") {
            data.push({
                text: "部门人员管理",
                bgColor: "blue",
                route: `${moduleName}orgList`,
                content: <SvgIcon size="md" type={"wenindex3"} />
            })
        } else {
            data.push({
                text: "个人信息修改",
                bgColor: "blue",
                route: `${moduleName}orgRegister/0`,
                content: <SvgIcon size="md" type={"wenindex3"} />
            })
        }
        return <MGrid data={data} columnNum={2} {...this.props} />
    }
}
export default Index;
