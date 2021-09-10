import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import QnnForm from "../../../modules/qnn-table/qnn-form";
import { Button, message as Msg, Row, Col, Spin, Modal } from "antd";
import Tree from "../../../modules/tree";
import s from './style.less';
const config = {
    antd: {
        rowKey: function (row) {
            return row.id
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    isShowRowSelect: false
};
const configItem = {
    antd: {
        rowKey: 'zxCrJYearCreditEvaItemId',//???
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 12 },
            sm: { span: 12 }
        },
        wrapperCol: {
            xs: { span: 12 },
            sm: { span: 12 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            orgID: '',
            whOrgID: '',
            loading: false,
            loadingSearch: false,
            resourceId: '',
            visibleBjdh: '',
            loadingBjdh: '',
            kkkData: '0',
            defaultExpandedKeys: [],
            catName: '',
            treeData: [],
            QnnTableItemData:[]
        }
    }
    componentDidMount() {
        this.getTreeData();
    }
    // 获取树-数据
    getTreeData(val) {
        const { myFetch } = this.props;
        myFetch('getZxCrColCategoryTreeShu', {}).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        treeData: data
                    })
                } else {
                    Msg.warn(message)
                }
            }
        );
    }
    handleCancelBjdh = () => {
        this.setState({ visibleBjdh: false, loadingBjdh: false });
    }
    render() {
        const { orgID, whOrgID, resourceId, visibleBjdh, loadingBjdh, kkkData } = this.state;
        return (
            <div>
                <Spin tip="Loading..." spinning={this.state.loading}>
                    <Spin spinning={this.state.loadingSearch}>
                        <Row>
                            <Col span={24}>
                                <QnnForm
                                    fetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    headers={{
                                        token: this.props.loginAndLogoutInfo.loginInfo.token
                                    }}
                                    wrappedComponentRef={(me) => {
                                        this.formHasTicket = me;
                                    }}
                                    formItemLayout={{
                                        labelCol: {
                                            xs: { span: 7 },
                                            sm: { span: 6 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 17 },
                                            sm: { span: 18 }
                                        }
                                    }}
                                    formConfig={[
                                        {
                                            label: '选择年份',
                                            field: 'orgID',
                                            type: 'year',
                                            span: 5,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 7 },
                                                    sm: { span: 12 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 17 },
                                                    sm: { span: 12 }
                                                }
                                            }
                                        },
                                        {
                                            label: '单价类型',
                                            field: 'whOrgID',
                                            type: 'checkbox',
                                            initialValue: ['0', '1', '2'],
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value',
                                            },
                                            optionData: [
                                                {
                                                    label: '人工费',
                                                    value: '0'
                                                },
                                                {
                                                    label: '周转材料',
                                                    value: '1'
                                                },
                                                {
                                                    label: '机械设备',
                                                    value: '2'
                                                }
                                            ],
                                            placeholder: '请选择',
                                            span: 9
                                        },
                                        {
                                            label: '选择章节',
                                            type: 'string',
                                            field: 'ysdNo',
                                            disabled: true,
                                            span: 10,
                                            labelCanClick: function (obj) {
                                                return true
                                            },
                                            labelClick: (val) => {
                                                // console.log(this.formHasTicket.form.getFieldsValue());
                                                let outOrgIDData = this.formHasTicket.form.getFieldsValue().outOrgID;
                                                let whOrgIDData = this.formHasTicket.form.getFieldsValue().whOrgID;
                                                let resourceIDData = this.formHasTicket.form.getFieldsValue().resourceID;
                                                // if (outOrgIDData && whOrgIDData && resourceIDData && this.state.companyIDData) {
                                                this.setState({
                                                    visibleBjdh: true,
                                                    outOrgIDData: outOrgIDData,
                                                    whOrgIDData: whOrgIDData,
                                                    resourceIDData: resourceIDData
                                                })
                                                // } else {
                                                //     Msg.warn('没有单据数据！')
                                                // }

                                            }
                                        },
                                        {
                                            label: '选择模式',
                                            field: 'kkk',
                                            type: 'select',
                                            initialValue: '0',
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value',
                                            },
                                            optionData: [
                                                {
                                                    label: '按公司',
                                                    value: '0'
                                                },
                                                {
                                                    label: '按省份',
                                                    value: '1'
                                                },
                                                {
                                                    label: '按区域',
                                                    value: '2'
                                                }
                                            ],
                                            placeholder: '请选择',
                                            span: 8
                                        },
                                        {
                                            label: '数据类型',
                                            field: 'ghg',
                                            type: 'select',
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value',
                                            },
                                            optionData: [
                                                {
                                                    label: '无剔除数据',
                                                    value: '0'
                                                },
                                                {
                                                    label: '剔除数据',
                                                    value: '1'
                                                },
                                                {
                                                    label: '差值',
                                                    value: '2'
                                                }
                                            ],
                                            placeholder: '请选择',
                                            span: 8
                                        },
                                        {
                                            type: "component",
                                            field: "component5",
                                            Component: obj => {
                                                return (
                                                    <div style={{ padding: '12px 4px' }}>
                                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                            let value = this.formHasTicket.form.getFieldsValue();
                                                            if (value.kkk) {
                                                                this.setState({
                                                                    orgID: value.orgID,
                                                                    whOrgID: value.whOrgID,
                                                                    resourceId: value.resourceId,
                                                                    kkkData: value.kkk
                                                                }, () => {
                                                                    this.table.refresh();
                                                                })
                                                            } else {
                                                                Msg.warn('请选择模式！')
                                                            }
                                                        }}>查询</Button>
                                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {

                                                        }}>报表</Button>
                                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {

                                                        }}>更新当期数据</Button>
                                                    </div>
                                                );
                                            },
                                            span: 8
                                        }
                                    ]}
                                />
                            </Col>
                        </Row>
                    </Spin>
                    {kkkData === '0' ? <QnnTable //按公司
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => {
                            this.table = me;
                        }}
                        {...config}
                        fetchConfig={orgID && whOrgID ? {
                            apiName: 'getZxSkStockDataList',
                            otherParams: {
                                companyID: '1',
                                orgID: orgID,
                                whOrgID: whOrgID,
                                resourceId: resourceId
                            }
                        } : {}}
                        actionBtns={[]}
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
                                    title: '编码',
                                    width: 260,
                                    dataIndex: 'resCode',
                                    key: 'resCode',
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '标准工序名称',
                                    width: 260,
                                    dataIndex: 'resName',
                                    key: 'resName',
                                    filter: true,
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '单位',
                                    dataIndex: 'spec',
                                    key: 'spec'
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '施工内容',
                                    dataIndex: 'unit',
                                    key: 'unit'
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '局平均单价',
                                    dataIndex: 'stockQty',
                                    key: 'stockQty'
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '一公司',
                                    dataIndex: 'stockQty',
                                    key: 'stockQty'
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            }
                        ]}
                    /> : null}
                    {kkkData === '1' ? <QnnTable //按省份
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => {
                            this.table = me;
                        }}
                        {...config}
                        fetchConfig={orgID && whOrgID ? {
                            apiName: 'getZxSkStockDataList',
                            otherParams: {
                                companyID: '1',
                                orgID: orgID,
                                whOrgID: whOrgID,
                                resourceId: resourceId
                            }
                        } : {}}
                        actionBtns={[]}
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
                                    title: '编码',
                                    width: 260,
                                    dataIndex: 'resCode',
                                    key: 'resCode',
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '标准工序名称',
                                    width: 260,
                                    dataIndex: 'resName',
                                    key: 'resName',
                                    filter: true,
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '单位',
                                    dataIndex: 'spec',
                                    key: 'spec'
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '施工内容',
                                    dataIndex: 'unit',
                                    key: 'unit'
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '局平均单价',
                                    dataIndex: 'stockQty',
                                    key: 'stockQty'
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '北京',
                                    dataIndex: 'stockQty',
                                    key: 'stockQty'
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            }
                        ]}
                    /> : null}
                    {kkkData === '2' ? <QnnTable //按区域
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => {
                            this.table = me;
                        }}
                        {...config}
                        fetchConfig={orgID && whOrgID ? {
                            apiName: 'getZxSkStockDataList',
                            otherParams: {
                                companyID: '1',
                                orgID: orgID,
                                whOrgID: whOrgID,
                                resourceId: resourceId
                            }
                        } : {}}
                        actionBtns={[]}
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
                                    title: '编码',
                                    width: 260,
                                    dataIndex: 'resCode',
                                    key: 'resCode',
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '标准工序名称',
                                    width: 260,
                                    dataIndex: 'resName',
                                    key: 'resName',
                                    filter: true,
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '单位',
                                    dataIndex: 'spec',
                                    key: 'spec'
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },

                            {
                                table: {
                                    title: '施工内容',
                                    dataIndex: 'unit',
                                    key: 'unit'
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '局平均单价',
                                    dataIndex: 'stockQty',
                                    key: 'stockQty'
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '华北',
                                    dataIndex: 'stockQty',
                                    key: 'stockQty'
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            }
                        ]}
                    /> : null}
                </Spin>
                <Modal
                    width='1200px'
                    style={{ top: '0' }}
                    title="选择单据"
                    visible={visibleBjdh}
                    footer={null}
                    onCancel={this.handleCancelBjdh}
                    bodyStyle={{ width: '1200px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelBjdh}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingBjdh}>
                        <div style={{ height: document.documentElement.clientHeight * 0.75 }}>
                            <div className={s.root}>
                                <div className={s.rootl}
                                    ref={(me) => {
                                        if (me) {
                                            this.leftDom = me;
                                        }
                                    }}>
                                    <div
                                        className={s.hr}
                                        ref={(me) => {
                                            if (me) {
                                                let _this = this;
                                                function moveFn(e) {
                                                    let conDomLeft = document.getElementsByClassName('ant-drawer-content-wrapper')[0].offsetLeft;
                                                    _this.leftDom.style['flex-basis'] = e.pageX - conDomLeft + 'px'
                                                }
                                                me.addEventListener('mousedown', (e) => {
                                                    this.onDragStartPos = e.pageX;
                                                    document.addEventListener('mousemove', moveFn, false)
                                                }, false);
                                                document.addEventListener('mouseup', (e) => {
                                                    document.removeEventListener('mousemove', moveFn, false)
                                                }, false)
                                            }
                                        }}
                                    ></div>
                                    {this.state.treeData ? <Tree
                                        selectText={false}
                                        modalType="common" //common  drawer  抽屉出现方式或者普通的
                                        visible
                                        selectModal="0" //0不可选  1单选(默认)  2多选（暂不可用）
                                        myFetch={this.props.myFetch}
                                        upload={this.props.myUpload}
                                        btnShow={false}
                                        disabled={true}
                                        draggable={false}
                                        nodeRender={nodeData => {
                                            return (
                                                <span>
                                                    {nodeData["catName"]}
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
                                                catName: '',
                                                defaultExpandedKeys:[]
                                            }, () => {
                                                this.setState({
                                                    defaultExpandedKeys: node.bsid.split(','),
                                                    catName: node.catName
                                                })
                                                const { myFetch } = this.props;
                                                myFetch('getZxCrJYearCreditEvaItemInit', {
                                                    
                                                }).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            Msg.success(message);
                                                            this.setState({
                                                                QnnTableItemData: data
                                                            })
                                                        } else {
                                                            Msg.error(message)
                                                        }
                                                    }
                                                );
                                            })
                                        }}
                                        data={this.state.treeData}
                                        keys={{
                                            label: "catName",
                                            value: "id",
                                            children: "childrenList"
                                        }}
                                    /> : null}
                                </div>
                                <div className={s.rootr}>
                                    {this.state.catName ? <QnnTable
                                        history={this.props.history}
                                        match={this.props.match}
                                        fetch={this.props.myFetch}
                                        myFetch={this.props.myFetch}
                                        upload={this.props.myUpload}
                                        headers={{
                                            token: this.props.loginAndLogoutInfo.loginInfo.token
                                        }}
                                        data={this.state.QnnTableItemData}
                                        {...configItem}
                                        wrappedComponentRef={(me) => {
                                            this.tableSK = me;
                                        }}
                                        isShowRowSelect={false}
                                        formConfig={[
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '主键id',
                                                    field: 'zxCrJYearCreditEvaItemId',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '组织机构代码',
                                                    width: 150,
                                                    tooltip: 23,
                                                    dataIndex: 'orgCertificate',
                                                    key: 'orgCertificate'
                                                }
                                            }
                                        ]}
                                        actionBtns={[
                                            
                                        ]}
                                    /> : null}

                                </div>
                            </div>;
                        </div>
                    </Spin>
                </Modal>
            </div>
        );
    }
}

export default index;