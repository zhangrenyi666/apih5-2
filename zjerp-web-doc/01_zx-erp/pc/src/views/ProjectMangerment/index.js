import React from "react";
import QnnTable from "../modules/qnn-table";
import Apih5 from "qnn-apih5";
class index extends Apih5 {
    render() {
        // const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <div>
                <QnnTable
                    {...this.props}
                    wrappedComponentRef={(me) => this.myQnnTable = me}
                    getOrgId={this.apih5.getOrgId}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    {...window._ProjectManage}
                    formConfig={window._ProjectManage.formConfig.map((item) => {
                        if (item.form?.type === "treeSelect") {
                            item.form.treeSelectOption.getTableRef = () => this.myQnnTable;
                            if (item.form.treeSelectOption.fetchConfig) {
                                item.form.treeSelectOption.fetchConfig.params = {
                                    topId: this.apih5.getOrgId()
                                }
                            }
                        }
                        return item;
                    })}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            let props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "ProjectMangerment"
                            }
                        }
                    }}
                    componentsKey={{
                        component1: () => {
                            return (
                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                    项目基础信息
                                </div>
                            );
                        },
                        component2: () => {
                            return (
                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                    项目机构性质
                                </div>
                            );
                        },
                        component3: () => {
                            return (
                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                    项目关键时间
                                </div>
                            );
                        }
                    }}
                />
            </div>
        );
    }
}

export default index;