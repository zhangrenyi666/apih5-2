import React, { Component } from "react";
import s from './style.less';
import FinancialStatus from './FinancialStatus';
import OperationalData from './OperationalData';
import RepurchaseData from './RepurchaseData';
import TrendData from './TrendData';
import ManagementUnitStatistics from './ManagementUnitStatistics';
const managementUnitStatistics = require('../../../../src/imgs/managementUnitStatistics.png');
const financialStatus = require('../../../../src/imgs/financialStatus.png');
const operationalData = require('../../../../src/imgs/operationalData.png');
const bottom = {
    backgroundImage: `url(${managementUnitStatistics})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
const topL = {
    backgroundImage: `url(${financialStatus})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
const topCT = {
    backgroundImage: `url(${operationalData})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
class index extends Component {
    render() {
        return (
            <div className={s.root}>
                <div className={s.top}>
                    <div className={s.topL} style={topL}>
                        <FinancialStatus {...this.props}/>
                    </div>
                    <div className={s.topC}>
                        <div className={s.topCT} style={topCT}>
                            <OperationalData {...this.props}/>
                        </div>
                        <div className={s.topCB} style={topCT}>
                            <RepurchaseData {...this.props}/>
                        </div>
                    </div>
                    <div className={s.topR} style={topL}>
                        <TrendData {...this.props}/>
                    </div>
                </div>
                <div className={s.bottom} style={bottom}>
                    <ManagementUnitStatistics {...this.props}/>
                </div>
            </div>
        );
    }
}

export default index;