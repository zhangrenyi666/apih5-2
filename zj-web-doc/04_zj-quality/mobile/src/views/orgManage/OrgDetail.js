import React, { Component } from 'react';
import { List, Checkbox, Badge, Switch, Modal, NavBar, Toast, Button, Flex } from 'antd-mobile';
import SvgIcon from '../common/svgIcon';
import styles from './Org.less';
import { push } from 'connected-react-router';
const alert = Modal.alert;
class OrgDetail extends Component {
    constructor(props) {
        super(props);
        const { match: { params: { orgId } } } = this.props
        this.orgId = orgId;
        this.iconCode = "visualization";
        this.initData = []
        this.modal = {}
        this.state = {
            subNodeList: this.initData,
        }
    }
    componentDidMount() {
        this.updateMemberList()
    }
    componentWillUnmount() {
        for (var key in this.modal) {
            if (this.modal.hasOwnProperty(key)) {
                this.modal[key].close()
            }
        }
    }
    updateMemberList = () => {//更新人员列表
        const { myFetch, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = this.props
        const { pathName } = routeData[curKey]
        Toast.loading('Loading...', 0);
        myFetch('getMemberListByDepartmentId', { departmentId: this.orgId }).then(({ code, data, success, message }) => {
            Toast.hide();
            if (code === "3003" || code === "3004") {
                dispatch(logout(pathName))
            } else {
                this.initData = data || []
                const subNodeList = this.initData.map((v, i) => {
                    const { realName, ext1, ext, userKey } = v
                    return {
                        userKey,
                        checked: false,
                        isLeader: ext === "1",//是否是总共
                        isOutsider: ext1 === "1",//是否是外聘人员
                        label: realName,
                    }
                })
                this.setState({ subNodeList })
            }
        })
    }
    setLeader = (data) => {//设置总工
        const { userKey, isLeader } = data
        const { myFetch, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = this.props
        const { pathName } = routeData[curKey]
        const body = {
            userKey,
            ext: isLeader ? "0" : "1"
        }
        Toast.loading('Loading...', 0);
        myFetch('updateMemberIsLeader', body).then(({ data, success, code, message }) => {
            Toast.hide();
            if (success) {
                this.updateMemberList()
            } else if (code === "3003" || code === "3004") {
                dispatch(logout(pathName))
            }

        })
    }
    onChange = (e) => {//人员选中状态改变回调
        let { subNodeList } = this.state
        subNodeList = subNodeList.map((v, i) => {
            if (v.userKey === e.userKey) {
                v.checked = e.checked
            }
            return v
        })
        this.setState({ subNodeList })
    }
    del = () => {//把选中的人员移出该部门
        let { subNodeList } = this.state
        const { myFetch, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = this.props
        const { pathName } = routeData[curKey]
        let sysUserList = []
        for (let i = 0; i < subNodeList.length; i++) {
            let { checked, userKey } = subNodeList[i];
            if (checked) {
                sysUserList.push({
                    userKey
                })
            }
        }
        if (sysUserList.length > 0) {
            this.modal.alert = alert('提醒', '确定要移出这些人员么?', [
                { text: '取消' },
                {
                    text: '确定', onPress: () => {
                        const body = {
                            sysUserList,
                            departmentId: this.orgId,
                        }
                        myFetch('batchDeleteByDepIdAndUserKey', body).then(({ data, code, success, message }) => {
                            if (success) {
                                Toast.success("删除成功", 1, () => {
                                    this.updateMemberList()
                                })
                            } else if (code === "3003" || code === "3004") {
                                dispatch(logout(pathName))
                            }
                        })
                    }
                },
            ])
        } else {
            this.modal.alert = alert('您没有选择任何人员！', "")
        }
    }
    render() {
        const { subNodeList } = this.state
        const { dispatch, routerInfo: { routeData, curKey } } = this.props
        const { moduleName } = routeData[curKey]
        return (
            <div className={`${styles["lny-page"]} ${styles["lny-Org"]}`}>
                <NavBar
                    className={styles["lny-header"]}
                    mode="light"
                    rightContent={[
                        <div key={1} style={{ marginRight: ".1rem" }}
                            onClick={() => {
                                dispatch(push(`${moduleName}orgCard/${this.orgId}`))
                            }}>{"二维码"}</div>,
                        <SvgIcon key={0} onClick={() => {
                            dispatch(push(`${moduleName}orgCard/${this.orgId}`))
                        }} size="sm" type={this.iconCode} />
                    ]}
                >{`部门人员管理`}</NavBar>
                <div className={styles["lny-body"]}>
                    <List className={styles["lny-boxShadow"]}>
                        {subNodeList && subNodeList.length > 0 ? subNodeList.map((v, i) => {
                            return (
                                <List.Item
                                    wrap={true}
                                    key={i}
                                    value={v.userKey}
                                    thumb={
                                        <Checkbox
                                            checked={v.checked}
                                            disabled={!v.isOutsider}
                                            onChange={(e) => {
                                                this.onChange({
                                                    ...v,
                                                    checked: e.target.checked
                                                })
                                            }}
                                        />
                                    }
                                    extra={
                                        <Switch
                                            checked={v.isLeader}
                                            color={"#0099dd"}
                                            onClick={() => { this.setLeader({ ...v }) }}
                                        />
                                    }
                                >
                                    {`${v.label}`}
                                    {v.isLeader ?//是否显示总工标签
                                        <Badge text="总工"
                                            style={{
                                                marginLeft: 12,
                                                padding: '0 .03rem',
                                                backgroundColor: '#0099dd',
                                                borderRadius: '.02rem',
                                                color: '#fff',
                                                border: '.01rem solid #0099dd',
                                            }}
                                        /> : null}
                                </List.Item>
                            )
                        }) : null}
                    </List>
                </div>
                <Flex className={styles["lny-footer"]}>
                    <Flex.Item><Button type="primary" onClick={this.del}>{"移出选中人员"}</Button></Flex.Item>
                </Flex>
            </div>
        )
    }
}

export default OrgDetail;