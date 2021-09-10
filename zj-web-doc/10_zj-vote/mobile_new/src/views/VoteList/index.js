import React,{ Component } from 'react'
import MyList from '../modules/MList'
import moment from 'moment'
import s from './style.less'

class index extends Component {
    render() {
        return (
            <div style={{height:"100vh"}}>
                <MyList
                    myFetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    searchKey="voteTitle"
                    fetchConfig={{
                        apiName: 'getVoteListByVoterMobile',
                    }}
                    Item={(props) => {
                        const { rowData } = props;
                        const { startDate,endDate,voteTitle,corparation } = rowData;
                        return <div className={s.item} onClick={() => {
                            const { voteId,previewFlag = '',voteType } = rowData;
                            //跳转页面 
                            if (voteType === '1') {
                                //评选模式
                                window.location.href = window.configs.voteUrl + '/multiplayerVote.html?voteId=' + voteId + '&previewFlag=' + previewFlag;
                                // console.log('评选模式')
                            } else {
                                window.location.href = window.configs.voteUrl + '/vote.html?voteId=' + voteId + '&previewFlag=' + previewFlag;
                            }
                        }}>
                            <div className={s.time}>投票时间：[{moment(startDate).format('YYYY-MM-DD HH:mm:ss')}~{moment(endDate).format('YYYY-MM-DD HH:mm:ss')}]</div>
                            <div className={s.itemCon}>{voteTitle}</div>
                            <div className={s.itemCon}>{corparation}</div>
                        </div>
                    }
                    }
                />
            </div >
        )
    }
}
export default index