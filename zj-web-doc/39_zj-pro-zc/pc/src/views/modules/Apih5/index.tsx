/**
 * 给页面提供一些功能和对页面数据进行一些规范操作
 * 分为 class组件的 函数组件的
*/
import React, { Component } from "react";

interface Apih5aProps {
    actions: {
        [propName: string]: any
    },
    apiNames: {
        [propName: string]: any
    },
    corpInfo: {
        [propName: string]: any
    },
    history: {
        [propName: string]: any
    },
    location: {
        [propName: string]: any
    },
    loginAndLogoutInfo: {
        [propName: string]: any
    },
    match: {
        [propName: string]: any
    },
    myPublic: {
        [propName: string]: any
    },
    projectKeys?: {
        [propName: string]: any
    },
    routerInfo?: {
        [propName: string]: any
    },
    dispatch: (any) => void,
    myFetch: any,
    myUpload: any,
    myLogout: any,
    updateProjectListData: any;
    [propName: string]: any
}
interface Apih5State {
    [propName: string]: any
}

class Apih5 extends Component<Apih5Props, Apih5State> {
    constructor(...args) {
        super(...args);

        type LifeCycleType = (...args: any[]) => void;

        //确保父和子组件的 生命周期都可以执行
        const lifeCycles: Array<string> = ["componentDidMount", "componentWillUnmount"];
        lifeCycles.forEach((lifeCycleName: string) => {
            const lifeCycle: LifeCycleType = this[lifeCycleName];
            this[lifeCycleName] = (...args): LifeCycleType => {
                this[`parent_${lifeCycleName}`]?.bind?.(this)?.(...args);
                lifeCycle?.bind?.(this)?.(...args);
            }
        });

    }
    props: Apih5Props;
    state: Apih5State;
    _isMounted: boolean = false;

    public parent_componentDidMount() {
        this._isMounted = true;
        console.log('父组件componentDidMount')
    }


}

export default Apih5
