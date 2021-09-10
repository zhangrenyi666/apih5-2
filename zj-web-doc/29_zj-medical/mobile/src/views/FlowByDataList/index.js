import React,{ Component } from "react";
import QnnTable from "../modules/qnn-table";
import FlowFormByYY from "../FlowByYY/form";

const listConfig = {
    antd: {
        rowKey: "id"
    },
    drawerConfig: {
        width: "800px",
        maskClosable: true
    },
    paginationConfig: {
        position: "bottom"
    },

    data: [
        {
            id: "1",
            sendUnitId: '中交一公局',
            sealUnitId: "中交三公局",
            content: "用印申请内容111",
            useNumber: 1,
            sealType:"1",
            applyUserPhone:18216811014,
            applyUserId:"测试张"
        },
        {
            id: "2",
            sendUnitId: '中交二公局',
            sealUnitId: "中交四公局",
            content: "用印申请内容222",
            useNumber: 2,
            sealType:"2",
            applyUserPhone:18216811014,
            applyUserId:"测试王"
        }
    ],

    actionBtns: [
        {
            label: "发起用印流程",
            name: "Component",
            icon: "plus",
            type: "primary",
            Component: function (obj) {

                //删除掉不需要的字段（每个流程都需要改的地）
                var delAttr = ["id"];

                //判断是否选中数据
                var selectedRows = obj.selectedRows;
                if (!selectedRows || !selectedRows.length || selectedRows.length > 1) {
                    obj.btnCallbackFn.closeDrawer();
                    obj.btnCallbackFn.msg.error('请选择一条数据！');
                    return <div />
                }

                //打开流程form
                return <FlowFormByYY
                    wrappedComponentRef={(me) => {
                        if (me) { 

                            //加上apiBody.
                            let flowData = {};
                            for (const key in selectedRows[0]) {
                                if (selectedRows[0].hasOwnProperty(key)) {
                                    if (!delAttr.includes(key)) {
                                        const element = selectedRows[0][key];
                                        flowData[`apiBody.${key.replace('apiBody.')}`] = element;
                                    }
                                }
                            }

                            setTimeout(function () {
                                me.props.form.setFieldsValue({
                                    ...flowData
                                })
                            },1)
                        }
                    }}
                    {...obj} />

            }
        },
    ],
    formConfig: [
        {
            table: {
                dataIndex: "sendUnitId",
                key: "sendUnitId",
                title: "用印单位",
            },
            form: {
                type: "string"
            }
        },
        {
            table: {
                dataIndex: "sealUnitId",
                key: "sealUnitId",
                title: "发往单位",
            },
            form: {
                type: "string"
            }
        },
        {
            table: {
                dataIndex: "content",
                key: "content",
                title: "用印申请内容",
            },
            form: {
                type: "string"
            }
        },
        {
            table: {
                dataIndex: "useNumber",
                key: "useNumber",
                title: "用印次数",
            },
            form: {
                type: "date"
            }
        },
        {
            table: {
                dataIndex: "applyUserId",
                key: "applyUserId",
                title: "经办人",
            },
            form: {
                type: "string"
            }
        }, 
        
        {
            table: {
                dataIndex: "applyUserPhone",
                key: "applyUserPhone",
                title: "电话",
            },
            form: {
                type: "phone"
            }
        }
    ]
};

class index extends Component {
    render() {
        return (
            <QnnTable
                {...this.props}
                fetch={this.props.myFetch}
                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                {...listConfig}
            />
        );
    }
}

export default index;
