import React, { Component } from "react";
import QnnForm from "../modules/qnn-form"; 
import { Button, message as Msg } from 'antd';
let itemId = '';
let itemName = '';
class index extends Component {
	constructor(props) {
		super(props);
		this.state = {
			designFlowId: props.match.params.designFlowId || '',
            codeId: ''
		}
    }
    refreshSAelect=(val) =>{
        this.setState({
            codeId:val
        })
    }
    render() {
        const { codeId, designFlowId} = this.state;
		return (
			<div style={{ height: "100%",position:'relative'}}> 
				<QnnForm
                    style={{ height: '91vh', paddingRight: '10px',paddingLeft: '10px'}}
					fetch={this.props.myFetch}
					upload={this.props.myUpload}
					headers={{
						token: this.props.loginAndLogoutInfo.loginInfo.token
					}}
					fetchConfig={{
						apiName: '',
						otherParams: {
						}
                    }}
                    drawerConfig={{
                        width: '600px'
                    }}
					wrappedComponentRef={(me) => {
						this.formHasTicket = me;
					}}
					formConfig={[
						{
                            title: "主键id",
                            field: "invoiceId",
                            type: 'string',
                            hide: true
                        },
                        {
                            title: "最外层主键id",
                            field: "designFlowId",
                            type: 'string',
                            initialValue:designFlowId,
                            hide: true
                        },
                        {
                            type: 'select',
                            label: '选择设计流程环节模板',
                            field: 'codeId', 
                            placeholder: '请选择',
                            required: true,
                            optionConfig: {
                                label: 'itemName',
                                value: 'codeId'
                            },
                            fetchConfig: {
                                apiName:"getZjTzBaseCodeList",
                                otherParams: {
                                    typeId: 'SheJiLiuChengHuanJieMuBan',
                                    interfaceFlag:'0'
                                }
                            },
                            onSelect: (val, rowData) => {
                                itemId = rowData.itemdata.itemId;
                                itemName = rowData.itemdata.itemName;
                                this.refreshSAelect(val);
                            }
                        },
                        {
                            type: "qnnTable",
                            field: "qnnTableKP", 
                            qnnTableConfig: {
                                fetchConfig:{
                                    apiName: 'getZjTzBaseCodeList',
                                    otherParams: {
                                        codePid: codeId,//传的是codeId，改名字为codePid,
                                        interfaceFlag:'1'
                                    }
                                },
                                drawerConfig: {
                                    width: "800px"
                                },
                                actionBtnsPosition:"top",
                                antd: {
                                    rowKey: function (row) {
                                        return row.designFlowId;
                                    },
                                    size: 'small'
                                },
                                paginationConfig: {
                                    position: 'bottom'
                                },
                                firstRowIsSearch: false,
                                isShowRowSelect: false,
                                wrappedComponentRef: (me) => {
                                    this.tableMB = me;
                                },
                                actionBtns: [],
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            title: "关联的主键id",
                                            field: "designFlowId",
                                            type: 'string',
                                            hide: true
                                        },
                                    },
                                    {
                                        table: {
                                            title: '设计环节名称',
                                            dataIndex: 'itemName',
                                            key: 'itemName'
                                        },
                                        isInForm: false,
                                    },
                                    {
                                        table: {
                                            title: '备注',
                                            dataIndex: 'ext1',
                                            key: 'ext1'
                                        },
                                        isInForm: false,
                                    }
                                ]
                            }
                        },
					]}
				/>
				<div style={{position:'absolute',background:'white',bottom:0,textAlign:'end',width: '100%',padding: '10px 16px',borderTop: '1px solid rgb(232, 232, 232)'}}>
					<Button type="dashed" style={{ marginRight: '8px' }} onClick={() => {
						this.props.btnCallbackFn.closeDrawer();
					}}>取消</Button>
					<Button type="primary" onClick={() => {
                        let value = this.formHasTicket.form.getFieldsValue();
                        value.designFlowTempId = itemId;
                        value.designFlowTempName = itemName;
						this.props.myFetch("addZjTzPartManage", value).then(({ success, data, message }) => {
							if (success) {
                                Msg.success(message);
                                this.props.refreshOut();
								this.props.btnCallbackFn.closeDrawer();
							} else {
								Msg.error(message);
							}
						})
						
					}}>保存</Button>
				</div>
            </div>
		);
	}
}
export default index;
