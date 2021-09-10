import React, { Component } from 'react';
import MGrid from '../common/MGrid';
import SvgIcon from '../common/svgIcon';
class Reserve extends Component {
    render() {
        const { routerInfo: { routeData, curKey } } = this.props
        const { moduleName } = routeData[curKey]
        const data1 = [
            {
                text: "参考资料",
                bgColor: "blue",
                route: `${moduleName}information/guidanceList`,
                content: <SvgIcon size="md" type={'wenindex4'} />
            },
            {
                text: "规范",
                bgColor: "orange",
                route: `${moduleName}information/specList/0`,
                content: <SvgIcon size="md" type={'wenindex2'} />
            },
            {
                text: "施工手册",
                bgColor: "red",
                route: `${moduleName}information/specList/1`,
                content: <SvgIcon size="md" type={'wenindex3'} />
            }
        ];
        const data2 = [
            {
                text: "技术交底",
                bgColor: "green",
                route: `${moduleName}enquiry/add/0`,
                content: <SvgIcon size="md" type={'wenindex4'} />
            }
        ]
        const data3 = [
            {
                text: "互动",
                bgColor: "green",
                route: `${moduleName}enquiry/add/4`,
                content: <SvgIcon size="md" type={'wenindex4'} />
            }
        ]
        return (
            <div>
                <MGrid title={"查阅资料"} data={data1} columnNum={3} {...this.props} />
                <MGrid title={"关键工序技术交底"} data={data2} columnNum={3} {...this.props} />
                <MGrid title={"互动"} data={data3} columnNum={3} {...this.props} />
            </div>
        )
    }
}
export default Reserve;
