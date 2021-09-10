import React,{ Component } from 'react'
import QnnTable from '../modules/qnn-table'; 
class index extends Component {
    render() {
        return (
            <div> 
                <QnnTable
                    history={this.props.history}
                    match={this.props.match}
                    fetch={this.props.myFetch} 		 upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    myPublic={this.props.myPublic}
                    {...window.StatisticAnalysis}
                />
            </div>
        )
    }
}

export default index
