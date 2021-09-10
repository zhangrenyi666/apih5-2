import React,{ Component } from 'react';
import QnnForm from '../modules/qnn-table/qnn-form'; 

class index extends Component { 
    
    render() {
        return (
            <QnnForm
                fetch={this.props.myFetch}
                upload={this.props.myUpload}
                wrappedComponentRef={(me) => this.qnnForm = me}
                {...window.zjProZcProIntroduce}
            />
        )
    }
}

export default index