import React, { Component } from "react";
import s from './style.less';
import Macropolicy from './Macropolicy';
import Building from './Building';
import BulletinBoard from './BulletinBoard';
import StableCrossVideo from './StableCrossVideo';
import RegulatoryFramework from './RegulatoryFramework';
import OtherDetails from './OtherDetails';
import AchievementExhibition from './AchievementExhibition';
const managementUnitStatistics = require('../../../../src/imgs/managementUnitStatistics.png');
const mcropolicy = require('../../../../src/imgs/mcropolicy.png');
const bulletinBoard = require('../../../../src/imgs/bulletinBoard.png');
const stableCrossVideo = require('../../../../src/imgs/stableCrossVideo.png');
const bottom = {
    backgroundImage: `url(${managementUnitStatistics})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
const topLeftT = {
    backgroundImage: `url(${mcropolicy})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
const topCenterT = {
    backgroundImage: `url(${bulletinBoard})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
const topCenterB = {
    backgroundImage: `url(${stableCrossVideo})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
class index extends Component {
    render() {
        return (
            <div className={s.root}>
                <div className={s.top}>
                    <div className={s.topLeft}>
                        <div className={s.topLeftT} style={topLeftT}>
                            <Macropolicy {...this.props}/>
                        </div>
                        <div className={s.topLeftB} style={topLeftT}>
                            <Building {...this.props}/>
                        </div>
                    </div>
                    <div className={s.topCenter}>
                        <div className={s.topCenterT} style={topCenterT}>
                            <BulletinBoard {...this.props}/>
                        </div>
                        <div className={s.topCenterB} style={topCenterB}>
                            <StableCrossVideo {...this.props}/>
                        </div>
                    </div>
                    <div className={s.topRight}>
                        <div className={s.topRightT} style={topLeftT}>
                            <RegulatoryFramework {...this.props}/>
                        </div>
                        <div className={s.topRightB} style={topLeftT}>
                            <OtherDetails {...this.props}/>
                        </div>
                    </div>
                </div>
                <div className={s.bottom} style={bottom}>
                    <AchievementExhibition {...this.props}/>
                </div>
            </div>
        );
    }
}

export default index;