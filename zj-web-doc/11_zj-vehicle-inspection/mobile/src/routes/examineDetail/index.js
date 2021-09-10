import React, { Component } from 'react'; 
import { SuperList } from '../../components';
import { approvalDetailformData as formData } from '../formConfig';
import styles from './style.less';

class ApprovalDetail extends Component {
    constructor(props) {
        super(props);
        this.state = {
            formData,
        } 
    }
    componentDidMount() {
        document.getElementsByTagName('title')[0].innerHTML = '审批详情';  
    }
    render() {
        let { formData } = this.state;
        return <div className={styles.applyadd}>
            <SuperList ref={el => this.SuperList = el} config={formData} />
        </div>
    }
}

export default ApprovalDetail

