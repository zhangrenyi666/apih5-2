import MyPublic from "../Public"
import moment from "moment";
const { appInfo, storage } = MyPublic
const setItem = (name, data) => {
    return new Promise((resolve) => {
        if (data) {
            let _data = {}
            if (typeof data === "object") {
                _data = {
                    t: "object",
                    d: JSON.stringify(data),
                    m: moment().valueOf()
                }
            } else {
                _data = {
                    t: "string",
                    d: data.toString(),
                    m: moment().valueOf()
                }
            }
            removeItem(name).then(() => {
                localStorage.setItem(`${appInfo.id}_${name}`, JSON.stringify(_data))
                let timer = setInterval(() => {
                    if (!!localStorage.getItem(`${appInfo.id}_${name}`)) {
                        resolve(data)
                        clearInterval(timer)
                    }
                }, 10)
            })
        } else {
            removeItem(name).then(() => { 
                resolve(data)
            })
        }
    })
}
const getItem = (name) => {
    let _data = localStorage.getItem(`${appInfo.id}_${name}`)
    if (_data) {
        let { t, d, m } = JSON.parse(_data)
        let isTimeout = (storage.timeout + m) < moment().valueOf()
        let data = t === "object" ? JSON.parse(d) : d
        return {
            isTimeout,
            data
        }
    } else {
        return null
    }
}
const removeItem = (name) => {
    return new Promise((resolve) => {
        localStorage.removeItem(`${appInfo.id}_${name}`)
        let timer = setInterval(() => {
            if (!localStorage.getItem(`${appInfo.id}_${name}`)) {
                resolve()
                clearInterval(timer)
            }
        }, 10)
    })
}
const clear = localStorage.clear
const MyStorage = {
    setItem,
    getItem,
    removeItem,
    clear
}
export default MyStorage  