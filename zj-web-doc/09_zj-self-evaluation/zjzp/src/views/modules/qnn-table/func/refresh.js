import { message as Msg } from "antd";

// 刷新表格数据
const refresh = function(cb) {
    let {
        fetchConfig: { apiName, params = {}, otherParams },
        limit, //每页显示条数
        curPage, //当前页
        searchParams = {} //搜索参数
    } = this.state;
    // let _params = this.getParams(params, otherParams);
    //每次请求除了带上用户搜索的参数为还会带上配置里的参数【params】
    // params 将自动从网址中取值
    let _params = {};
    const urlParams = this.props.match.params;
    for (const key in params) {
        _params[key] = urlParams[params[key]];
    }

    if (typeof otherParams === "function") {
        otherParams = otherParams({
            ...this.props,
            fetch: this.fetch,
            btnCallbackFn: this.btnCallbackFn
        });
    }

    if (typeof apiName === "function") {
        apiName = apiName({
            ...this.props,
            fetch: this.fetch,
            btnCallbackFn: this.btnCallbackFn
        });
    }

    const defaultSearchParams = {
        limit, //每页显示条数
        page: curPage, //当前页
        ...otherParams,
        ..._params,
        ...searchParams
    };
    this.setState({ loading: true });
    this.fetch(apiName, defaultSearchParams).then(res => {
        let { data, totalNumber, success, message } = res;
        if (success) {
            this.setState({
                totalNumber,
                data,
                loading: false
            });
            if (cb) {
                cb(res);
            }
        } else {
            Msg.error(message, 1, () => {
                this.setState({
                    loading: false
                });
            });
            if (cb) {
                cb(res);
            }
        }
    });
};
export default refresh;
