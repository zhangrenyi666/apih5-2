import React, { Component } from 'react';
import { Toast } from 'antd-mobile';
import styles from './Org.less';
import {  goBack } from 'connected-react-router';
class OrgCard extends Component {
    constructor(props) {
        super(props);
        const { match: { params: { orgId } } } = this.props
        this.orgId = orgId;
        this.state = {
            orgName: "XXX",
            qrcodeUrl: ""
        }
    }
    componentDidMount() {
        const { myFetch, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = this.props
        const { pathName } = routeData[curKey]
        Toast.loading('Loading...', 0);
        const body = { orgId: this.orgId }
        myFetch('createZjBaseQrcode', body).then(({ data, success, code, message }) => {
            if (success) {
                const { orgName, qrcodeUrl } = data
                document.getElementsByTagName("title")[0].innerHTML = "中交一公局--" + orgName;
                Toast.hide()
                this.setState({ orgName, qrcodeUrl })
            } else if (code === "3003" || code === "3004") {
                dispatch(logout(pathName))
            } else {
                dispatch(goBack())
            }
        })
    }
    componentDidUpdate() {
        setTimeout(() => {
            if (this.refs.box) {
                this.refs.box.style.opacity = "1"
            }
        }, 10)
    }
    close = (e) => {
        const { dispatch } = this.props
        if (e.target.isBg) {
            dispatch(goBack())
        }
    }
    render() {
        const { orgName, qrcodeUrl } = this.state
        return (
            <div className={`${styles["lny-page"]} ${styles["flexBox"]} ${styles["center"]}`} ref={(e) => { if (e) { e.isBg = true } }} onClick={this.close} style={{ backgroundColor: "#666" }}>
                {qrcodeUrl ?
                    <div ref="box" style={{ opacity: 0.1, transition: "opacity 1.3s", borderRadius: "5px", padding: "40px 10px", boxSizing: "border-box", backgroundColor: "#fff", width: "80%" }}>
                        <div style={{ lineHeight: "20px", height: "20px", fontSize: "18px", color: "#333", textAlign: "center" }}>{`中交一公局--${orgName}`}</div>
                        <img alt="二维码" style={{ display: "block", border: "0 none", width: "100%" }} src={`${qrcodeUrl}`} />
                        <div style={{ fontSize: "12px", color: "#999", textAlign: "center" }}>{`扫一扫上面二维码图案，加本部门`}</div>
                    </div> :
                    ""}
            </div >
        )
    }
}

export default OrgCard;