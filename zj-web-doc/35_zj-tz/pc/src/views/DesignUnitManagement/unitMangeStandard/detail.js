import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import s from "./style.less";
import { push } from "react-router-redux";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Row, Col, Button } from "antd";
const config = {
    antd: {
        rowKey: function (row) {
            return row.partManageId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1100px'
    },
    limit: 999999,
    curPage: 1,
    paginationConfig: false,
    firstRowIsSearch: false,
    isShowRowSelect: false,
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 21 }
        }
    }
};
class index extends Component {
    constructor(props){
        super(props);
        this.state = {
            designAdvistoryUnitStandardId: props.match.params.designAdvistoryUnitStandardId || '',
            libraryId: props.match.params.libraryId === 'suoshuku'? '':props.match.params.libraryId,
            visibleSend:false,
            loadingSend: false,
            partManageId: '',
            fujianLook: [],
            rowDataList:[]
        }
    }
    
    render() {
        const { designAdvistoryUnitStandardId,libraryId } = this.state;
        return (
            <div className={s.root}>
                <Row>
                    <Col style={{width:'400px'}}>
                        <QnnForm
                            {...this.props} 
                            match={this.props.match}
                            fetch={this.props.myFetch} 
                            upload={this.props.myUpload}
                            wrappedComponentRef={(me) => {
                                this.formHasTicket = me;
                            }}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            formConfig={[ 
                                {
                                    label: '????????????id',
                                    field: 'designAdvistoryUnitStandardId',
                                    initialValue: designAdvistoryUnitStandardId,
                                    hide:true
                                },
                                {
                                    label:'?????????????????????????????????????????????',
                                    field: 'libraryId',
                                    type: 'select',
                                    placeholder: '?????????',
                                    initialValue:libraryId,
                                    optionConfig: {
                                        label: 'itemName',
                                        value: 'itemId'
                                    },
                                    fetchConfig: {
                                        apiName:"getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'suoShuKu'
                                        }
                                    },
                                    span: 24,
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 20 },
                                            sm: { span: 14  }
                                        },
                                        wrapperCol: {
                                            xs: { span: 20 },
                                            sm: { span: 10 }
                                        }
                                    },
                                },
                            ]}
                            btns={[]}
                        /></Col>
                    <Col style={{width:'400px'}}>
                        <Button type="primary" style={{margin:'12px 22px'}} onClick={() => {
                            let value = this.formHasTicket.form.getFieldsValue();
                            this.props.myFetch("setZjTzDesignAdvistoryUnitStandardLibrary", value).then(({ success, data, message }) => {
                                if (success) {
                                    Msg.success(message);
                                    const { mainModule } = this.props.myPublic.appInfo;
                                    this.props.dispatch(
                                        push(`${mainModule}unitMangeStandard`)
                                    )
                                } else {
                                    Msg.error(message);
                                }
                            })
                            
                        }}>??????</Button>
                        <Button type="dashed" style={{margin:'12px 0 12px 0'}} onClick={() => {
                            const { mainModule } = this.props.myPublic.appInfo;
                            this.props.dispatch(
                                push(`${mainModule}unitMangeStandard`)
                            )
                        }}>??????</Button>
                    </Col>
                </Row>
                <Row>
                    <Col span={24}>
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            fetchConfig = {{
                                apiName: 'getZjTzDesignAdvistoryUnitRecordAllList',
                                otherParams:{
                                    designAdvistoryUnitStandardId:designAdvistoryUnitStandardId
                                }
                            }}
                            wrappedComponentRef={(me) => {
                                this.tableSSK = me;
                            }}
                            actionBtns={[]}
                            {...config}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'designAdvistoryUnitStandardId',
                                        type: 'string',
                                        hide:true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'libraryId',
                                        type: 'string',
                                        hide:true,
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'projectName',
                                        key: 'projectName'
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '???????????????',
                                        dataIndex: 'subprojectName',
                                        key: 'subprojectName'
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '????????????',//???
                                        dataIndex: 'designStageName',
                                        key: 'designStageName'
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '????????????',//???
                                        dataIndex: 'evaluatePerson',
                                        key: 'evaluatePerson'
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '????????????',//???
                                        format:'YYYY-MM-DD',
                                        dataIndex: 'evaluateDate',
                                        width:140,
                                        key: 'evaluateDate'
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '????????????',//???
                                        dataIndex: 'evaluateOrderName',
                                        key: 'evaluateOrderName'
                                    },
                                    isInForm: false
                                }
                            ]}
                        />
                    </Col>
                </Row>
                
            </div>
        );
    }
}

export default index;