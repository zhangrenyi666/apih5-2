import React from "react";
import { Button } from "antd";
import { editBtnsConfig } from "../config"

// import { push } from 'connected-react-router'; //,goBack

const index = function (s) {
    let { actionBtns = [],selectedRows,edit,history } = this.props;
    if (typeof actionBtns === "function") {
        actionBtns = actionBtns({
            ...this.props,
            btnCallbackFn: this.btnCallbackFn
        });
    } 
    //开发者模式下提供的按钮
    let _actionBtns = [...actionBtns,{
        //移动端上存在回顶按钮 
        icon: "vertical-align-top",
        name: "goTop",  
        onClick: () => {
            const listConDom = document.getElementsByClassName("am-list-view-scrollview")[0];
            if (listConDom) {
                let _num = listConDom.scrollTop; 
                window.clearInterval(window.goTopTimer);
                window.goTopTimer = setInterval(() => {
                    _num -= 30;
                    listConDom.scrollTop = _num;
                    if (_num < 2) {
                        window.clearInterval(window.goTopTimer);
                    }
                }, 6);
            }
        }
    }];
    if (edit) {
        _actionBtns = [..._actionBtns,...editBtnsConfig];
    }

    return (
        <div className={s.btnsContainer} ref="btnsContainer">
            {
                _actionBtns.map((item,index) => {
                    let { label,type,icon,name,formBtns,onClick, hide } = item;
                    let needHide = name === "del" || name === "edit"; //是否需要隐藏  
                    //移动端只显示新增删除
                    if (name !== 'add' && name !== 'del' && name !== 'goTop') {
                        return <div key={index} />
                    }
                    if ((typeof hide) === 'function') {
                        hide = hide({
                            btnInfo: item,
                            props: this.props,
                            btnCallbackFn: this.btnCallbackFn
                        });
                    }
                    if (hide) {
                        return null;
                    }
                    return <div key={index} className={s.btnItem}>
                        <Button
                            size="large" shape="circle"
                            type={type || "primary"}
                            onClick={() => {
                                switch (name) {
                                    case 'add':
                                        //跳转页面即可
                                        this.setState({
                                            formBtns: formBtns
                                        })
                                        history.push('add');
                                        break;
                                    default:
                                        if (onClick) {
                                            onClick({
                                                ...this.props,
                                                btnCallbackFn: this.btnCallbackFn
                                            })
                                        }
                                        break
                                }
                            }}
                            style={{
                                display: name === 'Component' || (needHide && selectedRows.length === 0) ? "none" : "",
                                marginBottom: 8
                            }}
                            icon={icon}
                        >

                            {icon ? null : label.substr(0,1)}
                        </Button>
                    </div>
                })
            }

        </div>

    );
};

export default index;
