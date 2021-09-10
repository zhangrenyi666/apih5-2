const get = (name, params) => {
    return new Promise((resolve, reject) => {
        let data, has = true
        switch (name) {
            case "login":
                data = { token: "000000" }
                break
            case "routerInfo":
                data = []
                break
            default:
                has = false
                break;
        }
        if (has) {
            resolve(data)
        } else {
            reject()
        }
    })
}
const MyData = {
    get
}
export default MyData  