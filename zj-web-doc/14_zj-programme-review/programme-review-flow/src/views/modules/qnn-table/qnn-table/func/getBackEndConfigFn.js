//获取后台配置 
import { message as Msg } from "antd";
const getBackEndConfigFn = function (getBackEndConfigByFetchConfig,callback) {
    let fetchConfig = getBackEndConfigByFetchConfig;
    this.getTableFetchParams(
        { ...fetchConfig },
        false
    ).then(({ params,apiName }) => {
        if (!apiName) return;

        Msg.loading('loading....',0);
        this.fetch(
            apiName,
            params
        ).then(({ data,success,message }) => {
            if (fetchConfig.success) {
                fetchConfig.success({
                    res: { data,success,message },
                    props: {
                        ...this.props,
                        btnCallbackFn: this.btnCallbackFn
                    }
                })
            }
            Msg.destroy();
            if (success) {
                this.setState(
                    {
                        ...data,
                        loading: false
                    },
                    () => {
                        callback();
                    }
                );
            } else {
                Msg.error(message);
            }
        });

    });
};

export default getBackEndConfigFn;
