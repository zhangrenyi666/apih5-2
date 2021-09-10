import React, { Component } from "react";
import MyList from "../modules/MZBList";
import { NavBar, Icon, Tabs, Toast } from "antd-mobile";
import { Drawer } from 'antd';
import { push } from 'connected-react-router';
import s from './style.less';
import QnnForm from '../modules/qnn-table/qnn-form';
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: Number(props.match.params.page) || 0,
            objectUserKey: props.match.params.objectUserKey || "",
            tabs: [],
            DrawerData:null,
            treeOpen:false
        }
    }
    componentDidMount() {
        this.props.myFetch('getTwoLinkAllList', {}).then(({ success, message, data }) => {
            if (success) {
                var tabs = [];
                data.map((item) => {
                    if (item.recordId) {
                        tabs.push({ title: item.referenceNumberName, value: item.recordId, currentList:item.currentList });
                    }
                    return item;
                })
                this.setState({
                    tabs
                })
            } else {
                Toast.fail(message);
            }
        });
    }
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        const { page, tabs, objectUserKey, treeOpen, DrawerData } = this.state;
        return (
            <div className={s.root}>
                <NavBar
                    style={{ width: "100%" }}
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => {
                        dispatch(push(`${mainModule}departmentList`));
                    }}
                >
                    规章制度列表
                </NavBar>
                <div>
                    {tabs.length ? <Tabs
                        tabs={tabs}
                        swipeable={false}
                        page={page}
                        onChange={(_, page) => {
                            this.setState({
                                page
                            })
                        }}
                    >
                        {
                            tabs.map((item, index) => {
                                if (index === page) {
                                    return (
                                        <div key={item.value} style={{ height: document.documentElement.clientHeight - 88.5 }}>
                                            <MyList
                                                loginAndLogoutInfo={this.props.loginAndLogoutInfo}
                                                myFetch={this.props.myFetch}
                                                upload={this.props.myUpload}
                                                searchKey="ruleName"
                                                fetchConfig={{
                                                    apiName: 'getRulesListByObjectUserKeyForWeChat',
                                                    otherParams: {
                                                        recordId: item.value,
                                                        objectUserKey: objectUserKey
                                                    }
                                                }}
                                                Item={props => {
                                                    const { rowData, rowID } = props;
                                                    const item = rowData;
                                                    const index = rowID;
                                                    return (
                                                        <div
                                                            className={s.center}
                                                            key={index}
                                                            style={{
                                                                borderLeft: `3px solid ${index % 2 === 0 ? "#ff4000" : "#ff9900"}`
                                                            }}
                                                            onClick={() => {
                                                                this.setState({
                                                                    treeOpen:true,
                                                                    DrawerData:item
                                                                })
                                                            }}
                                                        >
                                                            <div className={s.top}>{item.ruleName}</div>
                                                        </div>
                                                    );
                                                }}
                                            />
                                        </div>
                                    )
                                }else{
                                    return (
                                        <div key={item.value} style={{ height: document.documentElement.clientHeight - 88.5 }}></div>
                                    )
                                }
                            })
                        }
                    </Tabs> : null}
                </div>
                {treeOpen ? <Drawer
                    title='详情'
                    placement="right"
                    closable={false}
                    onClose={() => {
                        this.setState({
                            treeOpen: false
                        })
                    }}
                    visible={treeOpen}
                    width={window.innerWidth}
                    className='myListDrawer'
                >
                    <div style={{ height: window.innerHeight - 75 }}>
                        <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            data={DrawerData}
                            formConfig={[
                                {
                                    label: '制度名称',
                                    field: 'ruleName',
                                    type: 'string',
                                    disabled: true,
                                    placeholder: '请输入',
                                },
                                {
                                    label: '发文单位',
                                    field: 'referenceOne',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'title',
                                        value: 'value'
                                    },
                                    optionData: [tabs[page]],
                                    disabled: true,
                                    placeholder: '请选择',
                                },
                                {
                                    label: '发文部门',
                                    field: 'referenceTwo',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'referenceNumberName',
                                        value: 'recordId'
                                    },
                                    optionData: tabs[page].currentList,
                                    disabled: true,
                                    placeholder: '请选择',
                                },
                                {
                                    label: '年号',
                                    field: 'referenceThree',
                                    type: 'string',
                                    disabled: true,
                                    placeholder: '请输入',
                                },
                                {
                                    label: '编号',
                                    field: 'referenceFour',
                                    type: 'string',
                                    disabled: true,
                                    placeholder: '请输入',
                                },
                                {
                                    label: '部门',
                                    field: 'departmentName',
                                    type: 'string',
                                    disabled: true,
                                    placeholder: '请输入',
                                },
                                {
                                    label: '适用范围',
                                    field: 'tryScopeId',
                                    type: 'string',
                                    disabled: true,
                                    placeholder: '请输入',
                                },
                                {
                                    label: '制度要点',
                                    field: 'mainPoint',
                                    type: 'textarea',
                                    disabled: true,
                                    placeholder: '请输入',
                                },
                                {
                                    label: "附件",
                                    field: "ruleList",
                                    type: "files",
                                    initialValue: [],
                                    disabled: true,
                                    fetchConfig: {
                                        apiName: window.configs.domain + "upload"
                                    }
                                },
                                {
                                    type: "qnnForm",
                                    field: "flowList",
                                    label: "流程图及名称",
                                    disabled: true,
                                    canAddForm: true,
                                    textObj: {
                                        add: "添加",
                                        del: "删除"
                                    },      
                                    qnnFormConfig: {
                                        formConfig: [
                                            {
                                                label: '流程名称',
                                                field: 'flowName',
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                            {
                                                label: "流程图",
                                                field: "ruleFileFlowList",
                                                type: "files",
                                                initialValue: [],
                                                fetchConfig: {
                                                    apiName: window.configs.domain + "upload"
                                                }
                                            }
                                        ]
                                    }
                                },
                            ]}
                            btns={[
                                {
                                    label: '取消',
                                    field: 'cancel',
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({
                                            treeOpen: false
                                        })
                                    }
                                }
                            ]}
                        />
                    </div>
                </Drawer> : null}
            </div>
        )
    }
}
export default Index;