import React, { Component } from 'react';
import MGrid from '../common/MGrid';
import SvgIcon from '../common/svgIcon';
class Procedure extends Component {
    render() {
        const { routerInfo: { routeData, curKey } } = this.props
        const { moduleName } = routeData[curKey]
        const data = [
            {
                text: "关键工序管控",
                bgColor: "green",
                route: `${moduleName}enquiry/add/1`,
                content: <SvgIcon size="md" type={'wenindex4'} />
            },
            {
                text: "非关键工序管控",
                bgColor: "green",
                route: `${moduleName}enquiry/add/2`,
                content: <SvgIcon size="md" type={'wenindex2'} />
            }
        ];
        return <MGrid title={"工序质量实时报验"} columnNum={3} data={data} {...this.props} />
    }
}
export default Procedure;
