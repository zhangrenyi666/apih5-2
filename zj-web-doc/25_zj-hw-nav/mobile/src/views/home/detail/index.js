import React, { Component } from 'react'
import s from './index.less'
import { Toast } from 'antd-mobile';

class Detail extends Component{
    
    constructor(props){
        super(props)
        this.state={
            moduleId:this.props.match.params.id || '',
            data:[]
        }
    }
    componentDidMount(){
        this.props.myFetch('getZjHwModuleDetailByModuleId', {moduleId:this.state.moduleId}).then(({ data, success, message }) => {
            if (success) {
                // //处理数据data
                this.setState({
                    data: data
                })
           
            } else {
                Toast.fail(message)
            }
        })

    }
    render(){
        console.log(this.state.data);
        return(
            <div className={s.detail}>
            <h1>{this.state.data.detailTitle}</h1>
            <div className={s.content}>
                <img src={this.state.data.detailImgUrl}/>
                <br></br>
                <p>长按二维码图片关注</p>
            </div>
            <div className={s.wire}></div>
                <div className={s.footer}>
                    <p>版权所有：中交一公局海威工程建设有限公司</p>
                   
                </div>
            </div>
        )
    }
}
export default Detail;