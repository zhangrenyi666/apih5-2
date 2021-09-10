import React, { Component } from "react";
import s from './style.less';
import WarningInformation from './WarningInformation';
import DesignSchedule from './DesignSchedule';
import AssessmentRankings from './AssessmentRankings';
import AchievementOfAnnualTargets from './AchievementOfAnnualTargets';
import TimeLimitForAProjectEarlyWarning from './TimeLimitForAProjectEarlyWarning';
const warningInformation = require('../../../../src/imgs/warningInformation.png');
const designSchedule = require('../../../../src/imgs/designSchedule.png');
const achievementOfAnnualTargets = require('../../../../src/imgs/achievementOfAnnualTargets.png');
const left = {
    backgroundImage: `url(${warningInformation})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
const centerTop = {
    backgroundImage: `url(${designSchedule})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
const centerBottom = {
    backgroundImage: `url(${achievementOfAnnualTargets})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
class index extends Component {
    render() {
        return (
            <div className={s.root}>
                <div className={s.left} style={left}>
                    <WarningInformation {...this.props} />
                </div>
                <div className={s.center}>
                    <div className={s.centerTop} style={centerTop}>
                        <DesignSchedule {...this.props} />
                    </div>
                    <div className={s.centerBottom}>
                        <div className={s.centerBottomL} style={centerBottom}>
                            <AchievementOfAnnualTargets {...this.props} />
                        </div>
                        <div className={s.centerBottomR} style={centerBottom}>
                            <TimeLimitForAProjectEarlyWarning {...this.props} />
                        </div>
                    </div>
                </div>
                <div className={s.right} style={left}>
                    <AssessmentRankings {...this.props} />
                </div>
            </div>
        );
    }
}

export default index;