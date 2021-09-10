import React, { Component } from "react";
import {
    Grid,
    Badge,
} from "antd-mobile";
import App from "apih5/pages/App"
import { push } from "react-router-redux";

class AppPage extends Component {
    mainModule = this.props.myPublic.appInfo['mainModule'];
    data = [
        {
            title:'班组统计表',
            icon:'/Teamcore.png',
            link: `${this.mainModule}Teamcore/0`
        },
        {
            title:'经营计划查询',
            icon:'/BusinessPlan.png',
            link: `${this.mainModule}BusinessPlan/0`
        },
        {
            title:'年度计划进度',
            icon:'/AnnualSchedule.png',
            link: `${this.mainModule}AnnualSchedule/0`
        },
        {
            title:'任务创建',
            icon:'/TaskCreate.png',
            link: `${this.mainModule}TaskCreate/0`
        },
        {
            title:'任务接受',
            icon:'/TaskReceive.png',
            link: `${this.mainModule}TaskReceive/0`
        },
        {
            title:'任务人员统计表',
            icon:'/PeopleCore.png',
            link: `${this.mainModule}PeopleCore/0`
        },
        {
            title:'生产计划查询',
            icon:'/ProductionPlan.png',
            link: `${this.mainModule}ProductionPlan/0`
        },
        {
            title:'月度计划进度',
            icon:'/MonthlySchedule.png',
            link: `${this.mainModule}MonthlySchedule/0`
        },
        {
            title:'质量安全表格',
            icon:'/SafetyAndQuality.png',
            link: `${this.mainModule}SafetyAndQuality/0`
        },
    ]
    render() {
        return (
            <App {...this.props}
                content={() => {
                    return <div>
                        <Grid
                            data={this.data}
                            hasLine={false}
                            columnNum={4}
                            renderItem={dataItem => {
                                const {
                                    // smallModuleCount,
                                    icon,
                                    link,
                                    title
                                } = dataItem;
                                return (
                                    <div
                                        onClick={() => {
                                            // this.itemClick(
                                            //     smallModuleType,
                                            //     smallModuleLink
                                            // );
                                            this.props.dispatch(push(link));
                                        }}
                                    >
                                        <img
                                            src={require('./appHomeIcon'+icon)}
                                            style={{
                                                width: "35px",
                                                height: "35px"
                                            }}
                                            alt=""
                                        />
                                        <div
                                            style={{
                                                color: "#888",
                                                fontSize: "12px",
                                                marginTop: "8px"
                                            }}
                                        >
                                            {/* <Badge dot>
                                                <span>
                                                    {title}
                                                </span>
                                            </Badge> */}

                                            <span>
                                                {title}
                                            </span>
                                        </div>
                                    </div>
                                );
                            }}
                        />
                    </div>
                }}
            />
        );
    }
}

export default AppPage;
