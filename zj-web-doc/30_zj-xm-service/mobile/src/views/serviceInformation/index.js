import React, { Component } from 'react';
import QnnForm from '../modules/qnn-table/qnn-form';
import s from "./style.less";
import { NavBar, Icon } from "antd-mobile";
import { push } from 'react-router-redux';
import { Form } from 'antd';
import XmdahzPage from './projectList.js';

class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            height: document.documentElement.clientHeight - 44 - 43 - 45 - 1,
            curTabs:[],
            tabs:[]
        };
    }
    componentDidMount (){
        let aa = [
            {
                name:'项目成立',
                key:0,
                lisiApiname:'getZjXmServerProjectEstablishmentList',
                updateApiname:'updateZjXmServerProjectEstablishment'
            },
            {
                name:'项目运行',
                key:1,
                lisiApiname:'getZjXmServerProjectOperationList',
                updateApiname:'updateZjXmServerProjectOperation'
            },
            {
                name:'项目收尾',
                key:2,
                lisiApiname:'getZjXmServerProjectConclusionList',
                updateApiname:'updateZjXmServerProjectConclusion'
            },
            {
                name:'综合事项',
                key:3,
                lisiApiname:'getZjXmServerComprehensiveMattersList',
                updateApiname:'updateZjXmServerComprehensiveMatters'
            }
        ];
        this.setState({
            curTabs: aa
        },() => {
            let tabs = []
            this.state.curTabs.map((item,index)=>{
                tabs.push (
                    {
                        field: "one" + index,
                        name: "diy" + index,
                        title: item.name,
                        content: props => {
                            return <div>
                                <XmdahzPage {...this.props} sdata={item} keyData={item.name}/>
                            </div>;
                        }
                    }
                )
                return item;
            })
            this.setState({tabs})
        });
        
    }
    handleCancel = () => {
        this.setState({ visible: false });
    }
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        const {tabs} = this.state;
        
        
        return (
            <div className={s.root}>
                <NavBar
                    style={{ width: "100%" }}
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => {
                        dispatch(push(`${mainModule}App`));
                    }}
                >
                    {"服务指南"}
                </NavBar>
                <div style={{height: document.documentElement.clientHeight - 45}}>
                    <QnnForm
                    style={{height: document.documentElement.clientHeight - 45}}
                        refresh={this.refresh2}
                        {...this.props} 
                        match={this.props.match}
                        fetch={this.props.myFetch} 
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        tabs= {tabs}
                    />
                </div>
        </div>
        )
    }
}
export default Form.create()(Index)