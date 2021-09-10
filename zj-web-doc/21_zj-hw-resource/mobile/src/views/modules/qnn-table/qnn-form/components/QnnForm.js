import React from "react";
import QnnForm from "../../qnn-form";
const qnnFormCom = function(props) {
    const {qnnFormConfig } = props;
    if(!qnnFormConfig || !qnnFormConfig.formConfig){
        console.error('qnnFormConfig未配置或者qnnFormConfig.formConfig未配置！！！  ---来自qnn-form的错误');
        return;
    } 
    const config = {
        headers: { token: props.props.loginAndLogoutInfo.loginInfo.token },
        fetch: props.myFetch,
        history: props.props.history,
        match: props.props.match,
        myPublic: props.props.myPublic,
        form: props.form,
        style: props.style,
        ...qnnFormConfig, 
    };
    // console.log(config)
    return (
        <div>
            <QnnForm {...config} />
        </div>
    );
};
export default qnnFormCom;
