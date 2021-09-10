import React, { Component } from 'react';
// import { myFetch } from '../../tools';
// import { WhiteSpace, Toast } from 'antd-mobile';
// import { User } from '../../module';
import { SuperList } from '../../components';
import { insuranceformData as formData } from '../formConfig';
import styles from './style.less';

class InsuranceAdd extends Component {
    constructor(props) {
        super(props);
        this.state = {
            formData,
        } 
    }
    componentDidMount() {
        document.getElementsByTagName('title')[0].innerHTML = '车辆年险申请';  
    }
    render() {
        let { formData } = this.state;
        return <div className={styles.applyadd}>
            <SuperList ref={el => this.SuperList = el} config={formData} />
        </div>
    }
}

export default InsuranceAdd

