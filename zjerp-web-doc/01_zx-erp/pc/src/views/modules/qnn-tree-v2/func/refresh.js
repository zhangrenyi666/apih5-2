import { Toast } from "antd-mobile";

export default function (_params,cb,ecd) {
    const { apiName,props } = this;
    if (!apiName) {
        cb([]);
        return;
    }
    const { otherParams, params } = props.fetchConfig;
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
