import { Toast } from "antd-mobile";

export default function (_params,cb,ecd) {
    const { apiName,props } = this;
    const { otherParams } = props.fetchConfig;
    if (!apiName) {
        cb([]);
        return;
    }
    this.myFetch(apiName,{ ...otherParams,..._params }).then(({ success,message,data }) => {
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
