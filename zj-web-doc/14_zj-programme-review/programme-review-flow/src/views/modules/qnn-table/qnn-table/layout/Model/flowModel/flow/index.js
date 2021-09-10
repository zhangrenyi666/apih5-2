import React from "react"; 
import { goBack } from "connected-react-router"; //, push
import {
  Modal,  
  SegmentedControl
} from "antd-mobile";
import { Form, Button } from "antd"; 
import styles from "./style/index.less"; 

new SegmentedControl({ selectedIndex: 0 }); 
const alert = Modal.alert;

const FormItem = Form.Item;

//正常使用时的按钮栅格比例
const formTailLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 8, offset: 6 }
};
const isMobile = () => {
  if (
    navigator.userAgent.match(/Android/i) ||
    navigator.userAgent.match(/webOS/i) ||
    navigator.userAgent.match(/iPhone/i) ||
    navigator.userAgent.match(/iPad/i) ||
    navigator.userAgent.match(/iPod/i) ||
    navigator.userAgent.match(/BlackBerry/i) ||
    navigator.userAgent.match(/Windows Phone/i)
  ) {
    return true;
  } else {
    return false;
  }
};

 
const FlowButtonGroup = props => {
  let {
    flowButtons,
    clickFlowButton = () => {},
    backTop = () => {},
    createOpenFlow = () => {},
    refresh = () => {},
    isInQnnTable
  } = props;
  flowButtons = flowButtons || [];
  const len = flowButtons.length || 0;
  const btns = flowButtons.map((item, index) => {
    let style = { right: 64 * (len - 1 - index) + 20 + "px" },
      label = "...",
      pcLabel = "...";
    const { buttonClass } = item;
    switch (buttonClass) {
      case "backTop":
        style["background"] = "#fff";
        style["color"] = "#108ee9";
        label = (
          <div
            style={{
              width: "28px",
              height: "28px",
              background:
                "url(http://cdn.apih5.com/icon/arrowup.svg) center center /  28px 28px no-repeat"
            }}
          />
        );
        break;
      case "refresh":
        style["background"] = "#fff";
        style["color"] = "#108ee9";
        label = "刷";
        pcLabel = "刷新";
        break;
      case "goBack":
        style["background"] = "#fff";
        style["color"] = "greenyellow";
        label = "返";
        pcLabel = "返回";
        break;
      case "createOpenFlow":
        style["background"] = "green";
        label = "创";
        pcLabel = "创建";
        break;
      case "com.horizon.wf.action.ActionReaded":
        label = "阅";
        pcLabel = "已阅";
        break;
      case "com.horizon.wf.action.ActionReject":
        style["background"] = "red";
        label = "退";
        pcLabel = "退回";
        break;
      case "com.horizon.wf.action.ActionSubmitForReject":
        style["background"] = "greenyellow";
        label = "回";
        pcLabel = "退回提交";
        break;
      case "com.horizon.wf.action.ActionSubmit":
        style["background"] = "orange";
        label = "提";
        pcLabel = "提交";
        break;
      case "com.horizon.wf.action.ActionSave":
        style["background"] = "#108ee9";
        label = "存";
        pcLabel = "保存";
        break;
      default:
        style["background"] = "darkgrey";
        label = "未";
        pcLabel = "取消";
        break;
    }
    if (isMobile()) {
      return (
        <div
          key={index}
          onClick={e => {
            e.preventDefault();
            clickFlowButton(item, {
              backTop,
              createOpenFlow,
              refresh
            });
          }}
          className={styles.moreBtn}
          style={style}
        >
          {label}
        </div>
      );
    } else {
      return (
        <Button
          key={index}
          style={{ marginLeft: "8px" }}
          type="primary"
          onClick={e => {
            e.preventDefault();
            clickFlowButton(item, {
              backTop,
              createOpenFlow,
              refresh
            });
          }}
        >
          {pcLabel}
        </Button>
      );
    }
  });
  if (isMobile()) {
    return btns;
  } else {
    //在qnn-table中布局会稍微不太一样
    const _formTailLayout = isInQnnTable ? {} : formTailLayout;
    return (
      <FormItem {..._formTailLayout} className={styles.qnnTableFlowBtn}>
        {btns}
      </FormItem>
    );
  }
};
const clickFlowButton = function(item = {}, option = {}) {
  const { dispatch } = this.props;
  const { buttonClass } = item;
  const {
    backTop = () => {}, 
  } = option; 
  switch (buttonClass) {
    case "backTop":
      backTop();
      break;
     
    case "goBack":
      dispatch(goBack());
      break;
    
    default:
      alert("未知");
      break;
  }
}; 
export { 
  clickFlowButton,
  FlowButtonGroup, 
};
