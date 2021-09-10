import React,{ Component } from 'react';
import { Form } from '@ant-design/compatible';
import '@ant-design/compatible/assets/index.css';
import InterFaceManage from 'apih5/pages/InterFaceManage';
class Page extends Component {
    render() {
        return (
            <div style={{ height: '100%' }}>
                <InterFaceManage
                    {...this.props}
                />
            </div>)
    }
}
export default Form.create()(Page);