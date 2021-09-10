import React, { Component } from 'react';
import { push } from 'react-router-redux';
import { Grid, Badge } from 'antd-mobile';
import styles from './index.less';
class MGrid extends Component {
    onClick = (dataItem) => {//第二块数据点击
        const { dispatch } = this.props
        dispatch(push(dataItem.route))
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
                        const { content, text, brColor, ftColor, bgColor, num = 0 } = dataItem
                        return (
                            <div className={styles["lny-mGrid-item"]}>
                                <Badge text={num} overflowCount={55} className={`${styles[`lny-mGrid-item-iconBox`]} lny-color-${bgColor || brColor || "blue"}-br lny-color-${ftColor || "blue"}-ft lny-color-${bgColor || "white"}-bg`}>
                                    {content}
                                </Badge>
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