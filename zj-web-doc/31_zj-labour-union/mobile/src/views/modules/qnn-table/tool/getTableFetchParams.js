// import { message as Msg } from "antd";

//需要绑定this async 
const getTableFetchParams = function (fetchConfig = {},isPage = true) {
    let {
        limit, //每页显示条数
        curPage, //当前页
        searchParams = {} //搜索参数
    } = this.state;
    let { apiName,params = {},otherParams } = fetchConfig;

    return new Promise(resolve => {
        if (!apiName) {
            // console.warn('未配置apiName,  ---此警告可忽略')
            resolve({
                apiName: false
            });
            return { apiName: false };
        }

        let _params = {};
        const urlParams = this.props.match.params;
        for (const key in params) {
            _params[key] = urlParams[params[key]];
            //如果在form块中发出
            if (!_params[key] && this.props.formProps) {
                const { form } = this.props.formProps.props;
                if (form) {
                    _params[key] = form.getFieldValue(key);
                }
            }
        }

        otherParams = this.bind(otherParams);
        if (typeof otherParams === "function") {
            otherParams = otherParams({ //await 
                ...this.props,
                fetch: this.fetch,
                btnCallbackFn: this.btnCallbackFn
            });
        }

        apiName = this.bind(apiName);
        if (typeof apiName === "function") {
            apiName = apiName({// await 
                ...this.props,
                fetch: this.fetch,
                btnCallbackFn: this.btnCallbackFn
            });
        }
        const defaultSearchParams = {
            ...otherParams,
            ..._params,
            ...searchParams
        };
        if (isPage) {
            defaultSearchParams.limit = limit || 10; //每页显示条数
            defaultSearchParams.page = curPage; //当前页
        }
        resolve({
            apiName: apiName,
            params: defaultSearchParams
        });
    });
};

export default getTableFetchParams;
