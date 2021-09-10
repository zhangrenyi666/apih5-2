import React, { Component } from 'react';
import { Grid } from 'antd-mobile';
import styles from './MGrid.less';
import { push } from 'connected-react-router';
class MGrid extends Component {
    onClick = (dataItem) => {//第二块数据点击
        if (dataItem.route.indexOf("http") === 0) {
            window.location.href = dataItem.route
        } else {
            const { dispatch } = this.props
            dispatch(push(dataItem.route))
        }
    }
    render() {
        return (
            <div className={styles["lny-mGrid"]}>
                {this.props.title ? <div className={styles["lny-mGrid-title"]}>{this.props.title}</div> : ""}
                <Grid
                    className={styles["lny-boxShadow"]}
                    data={this.props.data}
                    columnNum={this.props.columnNum}
                    onClick={this.onClick}
                    renderItem={(dataItem, index) => {
                        const { content, text, brColor, ftColor, bgColor } = dataItem
                        return (
                            <div className={styles["lny-mGrid-item"]}>
                                <div className={`${styles["lny-mGrid-item-iconBox"]} ${styles[`lny-color-${bgColor || brColor || "blue"}-br`]} ${styles[`lny-color-${ftColor || "blue"}-ft`]} ${styles[`lny-color-${bgColor || "white"}-bg`]}`}>
                                    {content}
                                </div>
                                <div className={styles["lny-mGrid-item-textBox"]}>
                                    {text}
                                </div>
                            </div>
                        )
                    }} />
            </div>)
    }
}
export default MGrid;