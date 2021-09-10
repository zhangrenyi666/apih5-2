import React, { Component } from 'react'; 
import { SuperList } from '../../components';
import { carDetailData as formData } from '../formConfig';
import styles from './style.less';

class CarDetail extends Component {
    constructor(props) {
        super(props);
        this.state = {
            formData,
        } 
    } 
    componentDidMount() {
        document.getElementsByTagName('title')[0].innerHTML = '车辆详情';  
    }
    render() {
        let { formData } = this.state;
        return <div className={styles.applyadd}>
            <SuperList ref={el => this.SuperList = el} config={formData} />
        </div>
    }
}

export default CarDetail

