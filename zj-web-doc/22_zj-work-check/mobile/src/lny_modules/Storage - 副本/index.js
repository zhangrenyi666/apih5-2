import MyPublic from "../Public"
import moment from "moment";
import store from 'storejs';
const { appInfo, storage } = MyPublic
const setItem = (name, data) => {
    return new Promise((resolve) => {
        if (data) {
            let _data = {
                t: "object",
                d: data,
                m: moment().valueOf()
            }
            removeItem(name).then(() => {
                store.set(`${appInfo.id}_${name}`, _data);
                let timer = setInterval(() => {
                    if (!!store.get(`${appInfo.id}_${name}`)) {
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
    let _data = store.get(`${appInfo.id}_${name}`)

    if (_data) {
        let { d, m } = _data
        let isTimeout = (storage.timeout + m) < moment().valueOf()
        let data = d
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
        store.remove(`${appInfo.id}_${name}`)
        let timer = setInterval(() => {
            if (!store.get(`${appInfo.id}_${name}`)) {
                resolve()
                clearInterval(timer)
            }
        }, 10)
    })
}

const clear = () => {
    store.clear()
};



const MyStorage = {
    setItem,
    getItem,
    removeItem,
    clear
}
export default MyStorage  