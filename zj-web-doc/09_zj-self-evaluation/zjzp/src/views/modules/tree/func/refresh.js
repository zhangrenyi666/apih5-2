import { Toast } from "antd-mobile";

export default function(_params, cb, ecd) {
    const { apiName } = this;
    this.myFetch(apiName, _params).then(({ success, message, data }) => {
        Toast.hide()
        if (success) { 
            if (cb) {
                cb(data);
            }
        } else {
            Toast.fail(message);
            if (ecd) {
                ecd(message);
            }
        }
    });
}
