import React, { Component } from 'react';
import { SuperList } from '../../components';
import { problemAddDatat as formData } from '../formConfig';

export default class ProblemAdd extends Component {
    render() {
        return (
            <div>
                <SuperList ref={el => this.SuperList = el} config={formData} />
            </div>
        )
    }
}
