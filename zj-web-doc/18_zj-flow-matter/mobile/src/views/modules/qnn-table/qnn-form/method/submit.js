//提交按钮
//需要绑定this
import QnnForm from "../index";

const submit = function(paramsObj) {
    const {
        onClick,
        fetchConfig = {},
        isValidate = true,
        affirmTitle = "",
        affirmDesc = "",
        affirmYes = "",
        affirmNo = ""
    } = paramsObj;
    const { apiName, otherParams = {} } = fetchConfig;
    const { tabsActiveKey, tabs = [] } = this.state;
    this.getValues(isValidate, values => {
        const postData = () => {
            if (apiName) {
                //需要去提交数据
                this.myFetch(
                    apiName,
                    { ...values, ...otherParams },
                    response => {
                        if (onClick) {
                            onClick({
                                response,
                                values,
                                btnfns: this.btnfns,
                                props: this.props
                            });
                        }
                    }
                );
            } else if (onClick) {
                //tabs页面配置时formConfig配置到了对应的tab项的content中的formConfig
                //普通页面直接配置到formConfig中的
                if (tabs.length) {
                    onClick({
                        values: QnnForm.sFormatData(
                            values,
                            tabs[tabsActiveKey].content.formConfig,
                            "get"
                        ),
                        btnfns: this.btnfns,
                        props: this.props
                    });
                } else {
                    onClick({
                        values: QnnForm.sFormatData(
                            values,
                            this.state.formConfig,
                            "get"
                        ),
                        btnfns: this.btnfns,
                        props: this.props
                    });
                }
            }
        };

        if (affirmTitle || affirmDesc) {
            //弹出提示
            this.confirm(
                affirmTitle,
                affirmDesc,
                postData,
                affirmYes,
                affirmNo
            );
        } else {
            postData();
        }
    });
};

export default submit;
