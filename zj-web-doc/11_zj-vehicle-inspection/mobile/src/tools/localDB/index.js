import { app, timeout } from "../../main"
const { id } = app
const getLocalDB = (key) => {
    const value = JSON.parse(localStorage.getItem(`${key}_${id}`) || JSON.stringify({ type: null, data: false, createTime: 0 }))
    const createTime = value["createTime"]
    const curTime = new Date().getTime()
    const data = (curTime - createTime > timeout) ? null : value["data"]
    return data
}
const setLocalDB = (key, value) => {
    let _value = {
        type: typeof value,
        data: value,
        createTime: new Date().getTime()
    }
    return localStorage.setItem(`${key}_${id}`, JSON.stringify(_value))
}
const removeLocalDB = (key) => {
    return localStorage.removeItem(`${key}_${id}`)
}
const updateLocalDB = (key, value) => {
    return setLocalDB(key, value)
}
const clearLocalDB = () => {
    localStorage.clear()
}
export default {
    get: getLocalDB,
    set: setLocalDB,
    update: updateLocalDB,
    remove: removeLocalDB,
    clear: clearLocalDB
}

