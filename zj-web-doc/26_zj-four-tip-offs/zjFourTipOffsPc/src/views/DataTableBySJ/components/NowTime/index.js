import React, { Component } from 'react';
import moment from 'moment';
class NowTime extends Component {
    state = {
        nowTime: moment().format('YYYY年MM月DD日 HH点mm分ss秒')
    }
    componentDidMount() {
        clearInterval(window.topTime)
        window.topTime = setInterval(this.getNowTime, 1000);
    }
    componentWillUnmount() {
        clearInterval(window.topTime)
    }
    getNowTime = () => {
        this.setState({
          nowTime: moment().format('YYYY年MM月DD日 HH点mm分ss秒')
        })
      }
    render() {
        const { nowTime } = this.state;
        return (
            <div>
                {nowTime}
            </div>
        )
    }
}
export { NowTime } 