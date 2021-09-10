import React,{ Component } from "react";
import incTab from "../letBottomIncTabs";
import { Toast } from "antd-mobile";
import PullPersonMobile from "../modules/pullPersionMobile";
import s from './style.less'
class Index extends Component {
    constructor(...args) {
        super(...args);
        const { myFetch } = this.props;
        this.myFetch = myFetch;
        this.state = {
            loading: true,
            treeData: {}
        };
    }

    componentDidMount() {
        this.myFetch("getSysDepartmentUserAllTreeByZj",{}).then(
            ({ success,data,message }) => {
                if (success) {
                    this.setState({
                        loading: false,
                        treeData: data
                    });
                } else {
                    this.setState(
                        {
                            loading: false
                        },
                        () => {
                            Toast.fail(message);
                        }
                    );
                }
            }
        );
    }

    render() {
        const { loading,treeData } = this.state;
        return (
            // style={{position:'relative', paddingBottom:'50px',   }}
            <div className={s.container}>
                {/* 通讯录 */}
                {/* <PullPersonMobile  
                    height="90vh"
                    visible
                    noBar
                    bottomInfoField="showData"
                    bottomInfoStyle={{
                        bottom:"50px"
                    }} 
                    loading={loading}
                    treeData={treeData} /> */}
                <PullPersonMobile
                    visible
                    noBar
                    listConStyle={{ paddingBottom: '50px' }}
                    bottomInfoStyle={{ paddingBottom: '50px' }}
                    height={window.innerHeight - 50}
                    minHeight={window.innerHeight - 50}
                    bottomInfoField="showData"
                    loading={loading}
                    treeData={treeData}
                    modalClassName={"address-pullperson"}
                    wrapClassName={"address-pullperson-wrap"}
                />
            </div>
        );
    }
}

export default incTab(Index);
