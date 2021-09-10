import React from "react";
import s from "../style.less";
import QnnForm from "../qnn-form";
import { Icon,Row,Col,Button } from "antd";
const searchComponent = function () {
    const {
        searchFroms,
        expand,
        searchFormColNum,
        formItemLayoutSearch
    } = this.state;
    let _searchFroms = [...searchFroms];
    if (expand) {
        //设置所有_searchFroms的form hide为false
        _searchFroms = _searchFroms.map((item,i) => {
            item.hide = false;
            return item;
        });
    } else {
        //将索引大于2的_searchFroms的form hide为true
        _searchFroms = _searchFroms.map((item,i) => {
            if (searchFormColNum) {
                if (i > searchFormColNum - 1) {
                    item.hide = true;
                }
                return item;
            }
            if (i > 2) {
                item.hide = true;
            }
            return item;
        });
    }

    const filterConfig = this.getFilterConfig({
        history: this.props.history,
        match: this.props.match,
        formConfig: _searchFroms,
        formItemLayout: formItemLayoutSearch, 
        wrappedComponentRef:(me) => {
            this.searchForm = me;
            this.btnCallbackFn.searchForm = this.searchForm;
        }
    });

    if (searchFroms.length > 0) {
        return (
            <div className={s.searchFromContainer}>
                <QnnForm {...filterConfig} />
                <Row className={s.searchCtrl}>
                    <Col span={24} style={{ textAlign: "right" }}>
                        <Button type="primary" htmlType="submit" onClick={this.search}>
                            <Icon type="search" theme="outlined" />
                            搜索
            </Button>
                        <Button style={{ marginLeft: 8 }} onClick={this.handleReset}>
                            <Icon type="reload" theme="outlined" /> 重置
            </Button>
                        {searchFroms.length > 3 ? (
                            <a style={{ marginLeft: 8,fontSize: 12 }} onClick={this.toggle}>
                                {expand ? "收起" : "展开"}{" "}
                                <Icon type={expand ? "up" : "down"} />
                            </a>
                        ) : null}
                    </Col>
                </Row>
            </div>
        );
    } else {
        return null;
    }
};

export default searchComponent;
