import React, { Component } from 'react';
import incTab from '../letBottomIncTabs'

class Index extends Component {
    render() {
        console.log(this.props)
        return (
            <div>
                <p style={{color:'#ccc', paddingTop:'30vh', textAlign:'center'}}>开发中...</p>
                 
            </div>
        )
    }
}


export default incTab(Index);