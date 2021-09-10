import React, { Component } from 'react';
import { Icon } from 'antd-mobile';
import styles  from './index.less';
class Filter extends Component {
    render() {
        const { data, curValue, value, fixed, onChange } = this.props
        return (
            <div className={styles["lny-filterBox"]}>
                <div className={`${styles["lny-filter"]} ${curValue ?styles["cur"]:""}`} style={{ position: fixed ? "fixed" : "relative" }}>
                    {
                        data.map((item, index) =>
                            <div
                                key={index}
                                className={`${styles["lny-filter-btn"]} ${curValue === item["value"] ?styles["cur"]: ((value === item["value"]) ? styles["on"] :"")} `}
                                onClick={() => { onChange(item) }}>
                                <span>{item["text"]}</span>
                            </div>
                        )
                    }
                </div>
            </div>
        )
    }
}

class FilterDialog extends Component {
    constructor(props) {
        super(props);
        const { radio, valueName, textName } = this.props;
        this.radio = radio || false
        this.valueName = valueName || "value"
        this.textName = textName || "text"
    }
    onReset = () => {
        var { data } = this.props
        for (let i = 0; i <= data.length - 1; i++) {// 将点击二级选项ID状态取反
            data[i].checked = false;
        }
        this.props.onChange(data)
    }
    onChecked = (item) => {
        var { data, radio } = this.props
        for (let i = 0; i <= data.length - 1; i++) {// 将点击二级选项ID状态取反
            if (item[this.valueName] === data[i][this.valueName]) {
                data[i].checked = !data[i].checked;
            } else if (radio) {
                data[i].checked = false
            }
        }
        this.props.onChange(data)
    }
    onSave = () => {
        var { data } = this.props
        var checkedDatas = []
        for (let i = 0; i <= data.length - 1; i++) {// 将所有被选中的二级选中项筛选出
            if (data[i].checked) {
                checkedDatas.push({ ...data[i] })
            }
        }
        this.props.onSave(checkedDatas)
    }
    onClose = () => {
        this.props.onClose()
    }
    render() {
        const me = this;
        const { onSave, onReset, onChecked, onClose, textName } = me;
        const { data, show, topH } = this.props
        return (
            <div className={`${styles["lny-filter-dialog"]} ${show ?styles["show"]:""}`}>
                <div onClick={onClose} className={styles["lny-filter-dialog-hd"]} style={{ height: topH }}></div>
                <div className={styles["lny-filter-dialog-bd"]}>
                    <ul>
                        {data.map((item, index) =>
                            <li key={index} className={item["checked"] ? styles["on"] : ""} onClick={() => { onChecked(item) }}>
                                <span className={styles["lny-filter-dialog-bd-item"]}>{item[textName]}</span>
                                {item["checked"] ? <Icon style={{ top: ".15rem", position: "relative" }} size={"sm"} type={"check"} /> : ""}
                            </li>
                        )}
                    </ul>
                    <div className={styles["lny-filter-dialog-bd-btnBox"]}>
                        <div className={`${styles["lny-filter-dialog-bd-btn"]} ${styles["reset"]}`} onClick={onReset}>重置</div>
                        <div className={`${styles["lny-filter-dialog-bd-btn"]} ${styles["sure"]}`} onClick={onSave}>确定</div>
                    </div>
                </div>
                <div className={styles["lny-filter-dialog-ft"]} onClick={onClose}></div>
            </div>
        )
    }
}
export { FilterDialog, Filter };
