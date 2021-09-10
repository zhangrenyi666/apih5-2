import React, { Component } from 'react';
import { Grid, Icon } from '../../components';
class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            gridData: [
                {
                    text: "供应商",
                    name: "Supplier",
                    defaultNum: 0,
                    route: `${this.props.myPublic.appInfo.mainModule}Supplier`,
                    content: <Icon size="md" type={require('./Supplier.svg')} />
                },
                {
                    text: "方案库",
                    name: "Scheme",
                    defaultNum: 0,
                    route: `${this.props.myPublic.appInfo.mainModule}Scheme`,
                    content: <Icon size="md" type={require('./Scheme.svg')} />
                },
                {
                    text: "合作机构",
                    name: "Organization",
                    defaultNum: 0,
                    route: `${this.props.myPublic.appInfo.mainModule}Organization`,
                    content: <Icon size="md" type={require('./Organization.svg')} />
                },
                {
                    text: "管理清单",
                    name: "Inventory",
                    defaultNum: 0,
                    route: `${this.props.myPublic.appInfo.mainModule}Inventory`,
                    content: <Icon size="md" type={require('./Inventory.svg')} />
                },
                {
                    text: "人员库",
                    name: "Personnel",
                    defaultNum: 0,
                    route: `${this.props.myPublic.appInfo.mainModule}Personnel`,
                    content: <Icon size="md" type={require('./Personnel.svg')} />
                },
                {
                    text: "知识库",
                    name: "Courseware",
                    defaultNum: 0,
                    route: `${this.props.myPublic.appInfo.mainModule}Courseware`,
                    content: <Icon size="md" type={require('./Courseware.svg')} />
                },
                {
                    text: "配合比",
                    name: "MixProportion",
                    defaultNum: 0,
                    route: `${this.props.myPublic.appInfo.mainModule}MixProportion`,
                    content: <Icon size="md" type={require('./MixProportion.svg')} />
                },
                {
                    text: "材料消耗定额",
                    name: "Material",
                    defaultNum: 0,
                    route: `${this.props.myPublic.appInfo.mainModule}Material`,
                    content: <Icon size="md" type={require('./Material.svg')} />
                },
                {
                    text: "物资限价",
                    name: "Materials",
                    defaultNum: 0,
                    route: `${this.props.myPublic.appInfo.mainModule}Materials`,
                    content: <Icon size="md" type={require('./Materials.svg')} />
                },
                {
                    text: "分包限价",
                    name: "Subpackage",
                    defaultNum: 0,
                    route: `${this.props.myPublic.appInfo.mainModule}Subpackage`,
                    content: <Icon size="md" type={require('./Subpackage.svg')} />
                },
                {
                    text: "临建限价",
                    name: "Temp",
                    defaultNum: 0,
                    route: `${this.props.myPublic.appInfo.mainModule}Temp`,
                    content: <Icon size="md" type={require('./Temp.svg')} />
                }
            ]
        }
    }
    render() {
        const { gridData } = this.state;
        return (
            <div style={{ flex: "1" }}>
                <Grid data={gridData} columnNum={3} {...this.props} />
            </div>
        )
    }
}
export default Home
