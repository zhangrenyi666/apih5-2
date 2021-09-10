import React, { Component } from 'react';
import { myFetch } from '../../tools';
import { Result, Icon, WhiteSpace, WingBlank, Toast } from 'antd-mobile';
import styles from './style.less'

export default class MessageWeb extends Component {
    render() {
        let { id,approvalType } = this.props.match.params;
        let text = ''
        if(approvalType === '0'){
            text = '车辆年检'
        }else{
            text = '车辆保险' 
        }
        return (
            <div className={styles.con}>
                <WhiteSpace />
                <Result
                    buttonText="确定"
                    buttonType="primary"
                    onButtonClick={()=>{
                        let _body = {
                            id,
                            approvalType
                        }
                        myFetch('updateType', _body).then(({success, data, message})=>{
                            if(success){
                                Toast.success(message, 3, ()=>{
                                    this.props.history.push('/')
                                })
                            }else{
                                Toast.fail(message, 3)
                            }
                        }) 
                    }}
                    img={<img src={require('./clock.svg')} className={styles.spe} alt="" />}
                    title="等待处理"
                    message={`您有车辆需要线下申请${text}`}
                />
 
            </div>
        )
    }
};
