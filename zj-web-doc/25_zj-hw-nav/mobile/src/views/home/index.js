import React, { Component } from 'react'
import s from "./index.less"
import { push } from 'connected-react-router'
import { Toast } from 'antd-mobile';
class Home extends Component {
    constructor(props) {
        super(props)
        this.state = {
            looplist: []
        }
    }

    componentDidMount() {
        this.props.myFetch('getZjHwModuleList', {}).then(({ data, success, message }) => {
            if (success) {
                // //处理数据data
                this.setState({
                    looplist: data
               })
              
            } else {
                Toast.fail(message)
            }
        })

    }

    render() {
        console.log(this.state.looplist)
        return (
            <div className={s.home}>
                
                <div className={s.box}>
                    <ul>
                        {
                            this.state.looplist.map((item, index) => {
                                if (item.moduleType == 0) {
                                    return (
                                        <li key={index}>
                                            <div className={s.box}>
                                                <div className={s.box2}>
                                                    <img src={item.moduleImgUrl} alt='' onClick={() => {
                                                        window.location.href=`${item.webLink}`;
                                                    }}/>
                                            </div>
                                            </div>
                                            <p>{item.moduleTitle}</p>
                                        </li>
                                    )
                                }else{
                                    return (
                                        <li key={index}>
                                            <div className={s.box}>
                                                <div className={s.box2}>
                                                    <img src={item.moduleImgUrl} alt='' onClick={() => {
                                                        const { mainModule } = this.props.myPublic.appInfo
                                                        this.props.dispatch(push(`${mainModule}Detail/${item.moduleId}`));
                                                    }} />
                                                </div>
                                            </div>
                                            <p>{item.moduleTitle}</p>
                                        </li>
                                    )
                                }
                            })
                        }
                    </ul>
                </div>
                <div className={s.wire}></div>
                <div className={s.footer}>
                    <p>中交一公局集团·海威工程建设有限公司</p>                   
                </div>
            </div>
        )
    }
    handleClick
}
export default Home