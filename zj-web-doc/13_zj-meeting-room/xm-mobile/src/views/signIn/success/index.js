import React, { Component } from 'react';
const weixin = require('./weixin.png');
class idnex extends Component {
    componentDidMount(){
        document.getElementsByTagName('title')[0].innerHTML = "签到成功";
    }
    componentWillUnmount(){
        document.getElementsByTagName('title')[0].innerHTML = "";
    }
    render() {
        return (
            <div>
                <div style={{textAlign:'center',marginTop:"35%"}}>
                    <img style={{width:'80px',height:'80px'}} src={weixin} alt=""/>
                </div>
                <div style={{textAlign:'center',fontSize:'24px',fontWeight:'bold',marginTop:'5%'}}>签到成功!</div>
            </div>
        )
    }
}
export default idnex;