const listen = (callback) => {
    window.addEventListener && window.addEventListener('message', callback)
}
const MyPostMessage = {
    listen
}


export default MyPostMessage