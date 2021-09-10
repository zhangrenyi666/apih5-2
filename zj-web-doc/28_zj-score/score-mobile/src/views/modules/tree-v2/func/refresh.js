import { Toast } from "antd-mobile";

export default function (_params,cb,ecd) {
    const { apiName,props } = this;
    const { otherParams, params } = props.fetchConfig;
    if (!apiName) {
        cb([]);
        return;
    }
    // Toast.loading("请稍等...",0); 
    let body = {
        ...params,
        ...otherParams,
        ..._params
    } 
    this.myFetch(apiName,{ ...body }).then(({ success,message,data }) => {
        // Toast.hide()
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
