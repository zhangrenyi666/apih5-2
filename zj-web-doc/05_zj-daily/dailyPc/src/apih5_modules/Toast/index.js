const loadingElement = document.getElementById("loading")
const Toast = {
    show: (callback) => {
        loadingElement.className = ""
        setTimeout(() => {
            typeof callback === "function" && callback()
        }, 300);
    },
    hide: (callback) => {
        loadingElement.className = "hide"
        setTimeout(() => {
            typeof callback === "function" && callback()
        }, 300);
    }
}




export default Toast