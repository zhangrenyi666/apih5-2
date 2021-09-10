import React, { Component } from 'react';
import s from './style.less';
import { Redirect } from 'react-router-dom';
class componentName extends Component {
    render() {
        const { myPublic: { appInfo: { mainModule } }} = this.props;
        let flag = true;
        this.props.routerInfo.routeTree.map((item) => {
            if (item.comKey === "HomeNew") {
                flag = false;
            }
            return item;
        })
        if (flag) {
            return (
                <div className={s.root}>
                    首页
                </div>
            )
        }else{
            return (
                <Redirect to={`${mainModule}HomeNew`}/>
            )
        }
    }
}
export default componentName