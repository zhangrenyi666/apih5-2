import React, { Component } from 'react';
import { List, NavBar, Toast } from 'antd-mobile';
import SvgIcon from '../common/svgIcon';
import styles from './Org.less';
import { push } from 'connected-react-router';
const Item = List.Item;
class OrgList extends Component {
    constructor(props) {
        super(props);
        this.iconUsers = "user-1"
        this.initData = []
        this.state = {
            subNodeList: this.initData,
        }
    }
    componentDidMount() {
        const { myFetch, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = this.props
        const { pathName } = routeData[curKey]
        Toast.loading('Loading...', 0);
        myFetch('getDepartmentListByUserKey').then(({ data, success, message, code }) => {
            Toast.hide()
            this.initData = data || []
            if (success) {
                const subNodeList = []
                this.initData.map((item, index) => {
                    if (item.name !== "质量-技术质量部") {
                        subNodeList.push({
                            value: item.departmentId,
                            label: item.departmentName,
                        })
                    }
                    return item
                })
                this.setState({ subNodeList })
            } else if (code === "3003" || code === "3004") {
                dispatch(logout(pathName))
            } else {
                Toast.offline(message, 2)
            }
        })
    }
    gotoDetail = (data) => {//跳转至部门详情
        const { value } = data
        const { dispatch, routerInfo: { routeData, curKey } } = this.props
        const { moduleName } = routeData[curKey]
        dispatch(push(`${moduleName}orgDetail/${value}`))
    }
    render() {
        const { subNodeList } = this.state
        return (
            <div className={`${styles["lny-page"]} ${styles["lny-Org"]}`}>
                <NavBar className={styles["lny-header"]} mode="light">{`我的部门`}</NavBar>
                <div className={styles["lny-body"]}>
                    <List className={styles["lny-boxShadow"]}>
                        {subNodeList && subNodeList.length > 0 ?
                            subNodeList.map((v, i) => {
                                return (
                                    <Item
                                        key={i}
                                        wrap={true}
                                        thumb={
                                            <div style={{ padding: "10px 0" }}>
                                                <SvgIcon size="lg" type={this.iconUsers} />
                                            </div>
                                        }
                                        arrow={"horizontal"}
                                        onClick={() => { this.gotoDetail(v) }}>
                                        {`${v.label}`}
                                    </Item>
                                )
                            }) : null}
                    </List>
                </div>
            </div>
        )
    }
}
export default OrgList;