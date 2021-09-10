import { URI } from '../main';
import moment from 'moment';
import 'moment/locale/zh-cn';
export { default as localDB } from "./localDB"
export { default as myFetch } from "./myFetch"
export const getCurUri = () => {//获取当前url对象
    const uri = new URI()
    const curUri = {
        uri,
        domainStr: uri.clone().query("").hash("").toString(),//清空hash清空query
        noHashStr: uri.clone().hash("").toString(),//清空hash
        noQueryStr: uri.clone().query("").toString(),//清空query
        code: uri.query(true)["code"] || null,
        state: uri.query(true)["state"] || null,
    }
    return curUri
}
export const fromNow = (preDate) => {
    const curDate = new Date().getTime();
    const difDate = Math.max(curDate - preDate, 0);
    let textDate = ""
    let count = 0;
    if (difDate < 60 * 1000) {//小于一分钟
        textDate = "刚刚"
    } else if (difDate < 60 * 60 * 1000) {//小于一小时
        count = parseInt(difDate / (60 * 1000), 10)
        textDate = count + "分钟前"
    } else if (difDate < 24 * 60 * 60 * 1000) {//小于一天
        count = parseInt(difDate / (60 * 60 * 1000), 10)
        textDate = count + "小时前"
    } else if (difDate < 7 * 24 * 60 * 60 * 1000) {//小于一周
        count = parseInt(difDate / (24 * 60 * 60 * 1000), 10)
        textDate = count + "天前"
    } else {
        if (new Date(preDate).getFullYear() !== new Date(curDate).getFullYear) {
            textDate = moment(preDate).format("YYYY-MM-DD")
        } else {
            textDate = moment(preDate).format("MM-DD")
        }
    }
    return textDate
}
export const countDown = (toDate, curDate) => {
    curDate = curDate || new Date().getTime();
    const difDate = Math.max(toDate - curDate, 0);
    let textDate = ""
    let _dd = parseInt(difDate / (24 * 60 * 60 * 1000), 10)
    let dd = _dd
    let _hh = parseInt(difDate / (60 * 60 * 1000), 10)
    let hh = _hh - _dd * 24
    let _mm = parseInt(difDate / (60 * 1000), 10)
    let mm = _mm - _hh * 60
    let _ss = parseInt(difDate / (1000), 10)
    let ss = _ss - _mm * 60
    if (difDate === 0) {
        textDate = "到期"
    }
    else if (difDate < 60 * 1000) {//小于一分钟
        textDate = "马上到期"
    } else if (difDate < 60 * 60 * 1000) {//小于一小时
        textDate = "倒计时：" + zerofill(hh) + ":" + zerofill(mm) + ":" + zerofill(ss)
    } else if (difDate < 7 * 24 * 60 * 60 * 1000) {//小于一周
        textDate = "倒计时：" + dd + "天 " + zerofill(hh) + ":" + zerofill(mm) + ":" + zerofill(ss)
    } else {
        if (new Date(toDate).getFullYear() !== new Date(curDate).getFullYear) {
            textDate = moment(toDate).format("YYYY-MM-DD")
        } else {
            textDate = moment(toDate).format("MM-DD")
        }
    }
    return textDate
}
export const zerofill = (d, n = 2) => {
    let arr=d.toString().split("")
    let len=arr.length
    if (len === n) {
        return d
    } else {
        let newArr=[]
        for (let i = 0; i < n-len; i++) {
            newArr.push("0")
        }
        return newArr.concat(len).join("")
    }
}
