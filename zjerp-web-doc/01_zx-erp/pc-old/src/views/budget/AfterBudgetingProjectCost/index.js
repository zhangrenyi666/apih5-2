import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal, Row, Col} from 'antd';
import { push } from "react-router-redux";
import s from "./style.less";
import Tree from "../../modules/tree";
const confirm = Modal.confirm;
const configTop = {
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 10 },
            sm: { span: 10 }
        },
        wrapperCol: {
            xs: { span: 14 },
            sm: { span: 14 }
        }
    },
    isShowRowSelect: false,
};
const configBottom = {
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 10 },
            sm: { span: 10 }
        },
        wrapperCol: {
            xs: { span: 14 },
            sm: { span: 14 }
        }
    },
    isShowRowSelect: true,
    paginationConfig: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            defaultExpandedKeys: [],
            value: '',
            valuePid: '',
            treeData: null,
            orgID: props.match.params.orgID,
            zxBuBudgetBookId: props.match.params.zxBuBudgetBookId,
            activeIndex: -1,
            QDid: '',
            projectName: '',
            QDFlagData: null,
        }
    }
    componentDidMount() {
        const { orgID } = this.state
        this.props.myFetch('getZxCtWorksWorkNameTree', { orgID: orgID }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        treeData: data,
                        valuePid: data.length ? data[0].valuePid : '',
                        value: data.length ? data[0].value : '',
                        defaultExpandedKeys: data.length ? [data[0].value] : [],
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
        this.props.myFetch('getSysProjectDetail', {departmentId: orgID}).then(
            ({ data, success, message }) => {
                if(success){
                    this.setState({
                        projectName : data.projectName
                    })
                }else {
                    Msg.error(message)
                }
            }
        );
    }
    render() {
        const { orgID, projectName} = this.state;
        return (
            <div>
                <Row className={s.bor}>
                    <Col span={24}>
                        <h2>
                            ??????????????????-?????????????????????????????????-{projectName}
                        </h2>
                    </Col>
                </Row>
                <Row>
                    <div className={s.root}>
                        <div className={s.rootl}
                            ref={(me) => {
                                if (me) {
                                    this.leftDom = me;
                                }
                            }}>
                            {this.state.treeData ? <Tree
                                selectText={false}
                                modalType="common" //common  drawer  ?????????????????????????????????
                                //?????????????????????????????????
                                visible
                                selectModal="0" //0?????????  1??????(??????)  2????????????????????????
                                myFetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                btnShow={false} //????????????????????????
                                disabled={true}
                                draggable={false}
                                nodeRender={nodeData => {
                                    return (
                                        <span>
                                            {nodeData["label"]}
                                        </span>
                                    );
                                }}
                                treeProps={{
                                    showLine: true
                                }}
                                defaultExpandedKeys={this.state.defaultExpandedKeys}
                                rightMenuOption={[]}
                                nodeClick={(node) => {
                                    this.setState({
                                        valuePid: ''
                                    }, () => {
                                        this.setState({
                                            defaultExpandedKeys: [node.value],
                                            value: node.value,
                                            valuePid: node.valuePid,
                                            activeIndex: -1,
                                            QDid:''
                                        })
                                    })
                                }}
                                data={this.state.treeData}
                                //???????????? ??????{value:value,label:label,children:children}
                                keys={{
                                    label: "label",
                                    value: "value",
                                    children: "children"
                                }}
                            /> : null}
                        </div>
                        <div className={s.rootr}>
                            <div className={s.rootrTop}>
                                {this.state.valuePid ? <QnnTable
                                    {...this.props}
                                    fetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                    antd={{
                                        rowKey: 'id',
                                        size: 'small',
                                        scroll:{
                                            y: document.documentElement.clientHeight * 0.2
                                        },
                                        onRow: (record, index) => {
                                            return {
                                                onClick: () => {
                                                    this.setState({
                                                        activeIndex: index,
                                                        QDid: record.id
                                                    })
                                                }
                                            }
                                        },
                                        rowClassName: (record, index) => {
                                            return index === this.state.activeIndex ? s.highlight : '';
                                        }
                                    }}
                                    wrappedComponentRef={(me) => {
                                        this.tableQD = me;
                                    }}
                                    fetchConfig={{
                                        apiName: 'getZxCtWorksTreeList',
                                        otherParams: {
                                            parentID: this.state.valuePid === '-1' ? this.state.valuePid : this.state.value,
                                            orgID: orgID
                                        }
                                    }}
                                    actionBtns={[
                                        {
                                            name: 'goback',
                                            type: 'dashed',
                                            label: '??????',
                                            isValidate: false,
                                            onClick: (obj) => {
                                                const { mainModule } = this.props.myPublic.appInfo;
                                                const { orgID, zxBuBudgetBookId } = this.state;
                                                this.props.dispatch(
                                                    push(`${mainModule}AfterBudgetingDetial/${orgID}/${zxBuBudgetBookId}`)
                                                )
                                            }
                                        }
                                    ]}
                                    {...configTop}
                                    formConfig={[
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'id',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'workNo',
                                                key: 'workNo',
                                                align: 'center'
                                                // render: (data, rowData) => {
                                                //     return <div style={{ textIndent: (rowData.treeNode.length - 4) * 2 + 'px' }}>{data}</div>;
                                                // }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'workName',
                                                key: 'workName',
                                                align: 'center'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'unit',
                                                key: 'unit',
                                                align: 'center'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'contractPrice',
                                                key: 'contractPrice',
                                                align: 'center'

                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'contractQty',
                                                key: 'contractQty',
                                                align: 'center'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: "?????????",
                                                children: [
                                                    {
                                                        title: '????????????',
                                                        dataIndex: '',
                                                        key: '',
                                                        align: 'center',
                                                        render: ()=>{
                                                            return <span>0</span>
                                                        }
                                                    },
                                                    {
                                                        title: '??????',
                                                        dataIndex: '',
                                                        key: '',
                                                        align: 'center',
                                                        render: ()=>{
                                                            return <span>0</span>
                                                        }
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            table: {
                                                title: "?????????",
                                                children: [
                                                    {
                                                        title: '????????????',
                                                        dataIndex: '',
                                                        key: '',
                                                        align: 'center',
                                                        render: ()=>{
                                                            return <span>0</span>
                                                        }
                                                    },
                                                    {
                                                        title: '????????????',
                                                        dataIndex: '',
                                                        key: '',
                                                        align: 'center',
                                                        render: ()=>{
                                                            return <span>0</span>
                                                        }
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            table: {
                                                title: "???????????????",
                                                children: [
                                                    {
                                                        title: '??????',
                                                        dataIndex: '',
                                                        key: '',
                                                        align: 'center',
                                                        render: ()=>{
                                                            return <span>0</span>
                                                        }
                                                    },
                                                    {
                                                        title: '??????',
                                                        dataIndex: '',
                                                        key: '',
                                                        align: 'center',
                                                        render: ()=>{
                                                            return <span>0</span>
                                                        }
                                                    }
                                                ]
                                            }
                                        }
                                    ]}
                                /> : null}
                            </div>
                            <div className={s.rootrBottom}>
                                {<QnnTable
                                    //????????????id???????????????
                                    {...this.props}
                                    fetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                    antd={{
                                        rowKey: 'zxBuYgjResTechnicsId',
                                        size: 'small',
                                        scroll:{
                                            y: document.documentElement.clientHeight * 0.2
                                        }                                
                                    }}
                                    wrappedComponentRef={(me) => {
                                        this.tableGX = me;
                                    }}
                                    {...configBottom}
                                    fetchConfig={{
                                        apiName: 'getZxBuYgjResTechnicsAndQDList',
                                        otherParams: {
                                            billID: this.state.QDid,
                                            orgID: orgID
                                        }
                                    }}
                                    formConfig={[
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'zxBuYgjResTechnicsId',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                align: 'center',
                                                render:(val,rowData,index)=>{
                                                    return <span>{index+1}</span>
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '??????????????????',
                                                dataIndex: 'workName',
                                                key: 'workName',
                                                align: 'center'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'techNon',
                                                key: 'techNon',
                                                align: 'center'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'name',
                                                key: 'name',
                                                align: 'center'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'unit',
                                                key: 'unit',
                                                align: 'center'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'formulaStr',
                                                key: 'formulaStr',
                                                align: 'center'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'qty',
                                                key: 'qty',
                                                align: 'center'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '???????????????',
                                                dataIndex: 'price',
                                                key: 'price',
                                                align: 'center'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'taxRate',
                                                key: 'taxRate',
                                                align: 'center',
                                                render:()=>{
                                                    return <span>3%</span>
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'taxPrice',
                                                key: 'taxPrice',
                                                align: 'center'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'areaName',
                                                key: 'areaName',
                                                align: 'center',
                                                render:()=>{
                                                    return <span>?????????</span>
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '?????????',
                                                dataIndex: 'technicAmt',
                                                key: 'technicAmt',
                                                align: 'center'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '?????????',
                                                dataIndex: 'upAmt',
                                                key: 'upAmt',
                                                align: 'center'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '?????????',
                                                dataIndex: 'downAmt',
                                                key: 'downAmt',
                                                align: 'center'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '???????????????',
                                                dataIndex: 'pp1',
                                                key: 'pp1',
                                                align: 'center'
                                            },
                                            isInForm: false
                                        },
                                    ]}
                                    actionBtns={[
                                        {
                                            name: 'diydel',//??????add del
                                            icon: 'delete',
                                            type: 'danger',//??????  ?????? primary  [primary dashed danger]                                
                                            label: '??????',
                                            disabled:true,
                                            // disabled: (obj) => {
                                            //     if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                            //         return false;
                                            //     } else {
                                            //         return true;
                                            //     }
                                            // },
                                            onClick: (obj) => {
                                                confirm({
                                                    content: '?????????????????????????',
                                                    onOk: () => {
                                                        this.props.myFetch('batchDeleteUpdateZxBuYgjResTechnics', obj.selectedRows).then(({ success, message, data }) => {
                                                            if (success) {
                                                                obj.btnCallbackFn.clearSelectedRows();
                                                                obj.btnCallbackFn.refresh();
                                                            } else {
                                                                Msg.error(message);
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        },
                                        {
                                            name: 'diySubmit',
                                            field: 'diySubmit',
                                            type: 'primary',
                                            label: '??????',
                                            disabled:true,
                                            onClick: (obj) => {
                                                this.props.myFetch('updateZxBuYgjResTechnicsList', {}).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            Msg.success(message);
                                                            obj.btnCallbackFn.refresh();
                                                        } else {
                                                            Msg.error(message);
                                                        }
                                                    }
                                                );
                                            }
                                        }
                                    ]}
                                />}
                            </div>
                        </div>
                    </div>
                </Row>
            </div>
        )
    }
}
export default index;