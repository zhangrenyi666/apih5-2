const get = (name, params) => {
    return new Promise((resolve, reject) => {
        let data, has = true
        switch (name) {
            case "login":
                data = { token: "000000" }
                break
            case "routerInfo":
                data = [
                    // {
                    //     name: "",
                    //     moduleName: "/zjquality",
                    //     pathName: "/",
                    //     roles: {
                    //         "admin": {
                    //             visible: true,
                    //             enable: true
                    //         },
                    //         "user": {
                    //             visible: false,
                    //             enable: true
                    //         }
                    //     },
                    //     redirect: "/home"
                    // },
                    // {
                    //     name: "",
                    //     moduleName: "/zjquality",
                    //     pathName: "/home",
                    //     roles: {},
                    //     comKey: "Home"
                    // },
                    // {
                    //     name: "",
                    //     moduleName: "/zjquality",
                    //     pathName: "/home/:code",
                    //     roles: {},
                    //     comKey: "Home"
                    // },
                ]
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