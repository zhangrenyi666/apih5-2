import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal, Drawer } from "antd";
import ProjectConstructionContractManagement from './indexGC';
import MaterialManagementContractManagement from './indexWZ';
import MachineryManagementContractManagement from './indexSB';
import OtherContractManagement from './indexQT';
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    pageSize:99999999,
    paginationConfig: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            formData: {
                queryComID:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
                queryOrgID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
                queryPeriod: moment(new Date()).format('YYYYMM')
            },
            visibleGC:false,
            visibleWZ:false,
            visibleSB:false,
            visibleQT:false,
            propsData:null
        }
    }
    onClose = () => {
        this.setState({
            visibleGC:false,
            visibleWZ:false,
            visibleSB:false,
            visibleQT:false
        })
    }
    render() {
        const { formData, visibleGC, visibleWZ, visibleSB, visibleQT, propsData } = this.state;
        return (
            <div>
                <QnnForm
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                        token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    wrappedComponentRef={(me) => {
                        this.form = me;
                    }}
                    data={formData}
                    formItemLayout={{
                        labelCol: {
                            xs: { span: 7 },
                            sm: { span: 7 }
                        },
                        wrapperCol: {
                            xs: { span: 17 },
                            sm: { span: 17 }
                        }
                    }}
                    formConfig={[
                        {
                            type: 'string',
                            label: '公司',
                            field: 'queryComID',
                            hide: true
                        },
                        {
                            label: '项目名称',
                            field: 'queryOrgID',
                            type: 'string',
                            hide: true
                        },
                        {
                            type: 'month',
                            label: '期次',
                            allowClear: false,
                            field: 'queryPeriod',
                            span: 8
                        },
                        {
                            type: 'component',
                            field: 'component',
                            Component: obj => {
                                return (
                                    <div style={{ padding: '10px' }}><Button type="primary" onClick={() => {
                                        let value = this.form.form.getFieldsValue();
                                        if (value?.queryPeriod) {
                                            value.queryPeriod = moment(value.queryPeriod).format('YYYYMM');
                                        }
                                        this.setState({
                                            formData: value
                                        })
                                    }}>查询</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            confirm({
                                                content: '确定导出数据吗?',
                                                onOk: () => {
                                                    let value = this.form.form.getFieldsValue();
                                                    if (value?.queryPeriod) {
                                                        value.queryPeriod = moment(value.queryPeriod).format('YYYYMM');
                                                    }
                                                    let stringData = '';
                                                    for (let key in value) {
                                                        if (value?.[key]) {
                                                            stringData += `&${key}=${value[key]}`;
                                                        }
                                                    }
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    let URL = `${ureport}excel?_u=minio:ZxSaFinishProjSettleInfo.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=完工项目查询报表`;
                                                    window.open(URL);
                                                }
                                            })
                                        }}>导出</Button></div>
                                );
                            },
                            span: 8
                        }
                    ]}
                />
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'exportZxSaFinishProjSettleInfo',
                        otherParams: { ...formData }
                    }}
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
                                title: '单位名称',
                                dataIndex: 'orgName',
                                width: 150,
                                key: 'orgName',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '实际开工日期',
                                dataIndex: 'realBeginDate',
                                width: 120,
                                key: 'realBeginDate',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '实际竣工日期',
                                dataIndex: 'realEndDate',
                                width: 120,
                                key: 'realEndDate',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '中标价(万元)',
                                dataIndex: 'contractCost',
                                width: 120,
                                key: 'contractCost',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '开工累计完成产值(万元)',
                                dataIndex: 'processAmt',
                                width: 120,
                                key: 'processAmt',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '全部合同',
                                dataIndex: 'title_1',
                                key: 'title_1',
                                width: 530,
                                children: [
                                    {
                                        title: '已签订合同份数',
                                        dataIndex: 'allNum',
                                        key: 'allNum',
                                        width: 120
                                    },
                                    {
                                        title: '应执行合同份数',
                                        dataIndex: 'allNeedSettleNum',
                                        key: 'allNeedSettleNum',
                                        width: 120
                                    },
                                    {
                                        title: '已最终结算合同份数',
                                        dataIndex: 'allFinishSettleNum',
                                        key: 'allFinishSettleNum',
                                        width: 150
                                    },
                                    {
                                        title: '最终合同执行率(%)',
                                        dataIndex: 'allRate',
                                        key: 'allRate',
                                        width: 140
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '工程类',
                                dataIndex: 'title_2',
                                key: 'title_2',
                                width: 530,
                                children: [
                                    {
                                        title: '已签订合同份数',
                                        dataIndex: 'num1',
                                        key: 'num1',
                                        width: 120,
                                        onClick:(obj) => {
                                            let rowData = obj.rowData;
                                            this.setState({
                                                visibleGC:true,
                                                propsData:{
                                                    contractType:'P2',
                                                    queryDataType:'1',
                                                    queryOpenType:rowData?.openType === 'jiaogong' ? '3' :  rowData?.openType === 'guidang' ? '4' : '',
                                                    queryPeriod:rowData?.period,
                                                    orgID:rowData?.id
                                                }
                                            })
                                        }
                                    },
                                    {
                                        title: '应执行合同份数',
                                        dataIndex: 'needSettleNum1',
                                        key: 'needSettleNum1',
                                        width: 120,
                                        onClick:(obj) => {
                                            let rowData = obj.rowData;
                                            this.setState({
                                                visibleGC:true,
                                                propsData:{
                                                    contractType:'P2',
                                                    queryDataType:'2',
                                                    queryOpenType:rowData?.openType === 'jiaogong' ? '3' :  rowData?.openType === 'guidang' ? '4' : '',
                                                    queryPeriod:rowData?.period,
                                                    orgID:rowData?.id
                                                }
                                            })
                                        }
                                    },
                                    {
                                        title: '已最终结算合同份数',
                                        dataIndex: 'finishSettleNum1',
                                        key: 'finishSettleNum1',
                                        width: 150,
                                        onClick:(obj) => {
                                            let rowData = obj.rowData;
                                            this.setState({
                                                visibleGC:true,
                                                propsData:{
                                                    contractType:'P2',
                                                    queryDataType:'3',
                                                    queryOpenType:rowData?.openType === 'jiaogong' ? '3' :  rowData?.openType === 'guidang' ? '4' : '',
                                                    queryPeriod:rowData?.period,
                                                    orgID:rowData?.id
                                                }
                                            })
                                        }
                                    },
                                    {
                                        title: '最终合同执行率(%)',
                                        dataIndex: 'rate1',
                                        key: 'rate1',
                                        width: 140
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '物资类',
                                dataIndex: 'title_3',
                                key: 'title_3',
                                width: 530,
                                children: [
                                    {
                                        title: '已签订合同份数',
                                        dataIndex: 'num2',
                                        key: 'num2',
                                        width: 120,
                                        onClick:(obj) => {
                                            let rowData = obj.rowData;
                                            this.setState({
                                                visibleWZ:true,
                                                propsData:{
                                                    contractType:'P5',
                                                    queryDataType:'1',
                                                    queryOpenType:rowData?.openType === 'jiaogong' ? '3' :  rowData?.openType === 'guidang' ? '4' : '',
                                                    queryPeriod:rowData?.period,
                                                    orgID:rowData?.id
                                                }
                                            })
                                        }
                                    },
                                    {
                                        title: '应执行合同份数',
                                        dataIndex: 'needSettleNum2',
                                        key: 'needSettleNum2',
                                        width: 120,
                                        onClick:(obj) => {
                                            let rowData = obj.rowData;
                                            this.setState({
                                                visibleWZ:true,
                                                propsData:{
                                                    contractType:'P5',
                                                    queryDataType:'2',
                                                    queryOpenType:rowData?.openType === 'jiaogong' ? '3' :  rowData?.openType === 'guidang' ? '4' : '',
                                                    queryPeriod:rowData?.period,
                                                    orgID:rowData?.id
                                                }
                                            })
                                        }
                                    },
                                    {
                                        title: '已最终结算合同份数',
                                        dataIndex: 'finishSettleNum2',
                                        key: 'finishSettleNum2',
                                        width: 150,
                                        onClick:(obj) => {
                                            let rowData = obj.rowData;
                                            this.setState({
                                                visibleWZ:true,
                                                propsData:{
                                                    contractType:'P5',
                                                    queryDataType:'3',
                                                    queryOpenType:rowData?.openType === 'jiaogong' ? '3' :  rowData?.openType === 'guidang' ? '4' : '',
                                                    queryPeriod:rowData?.period,
                                                    orgID:rowData?.id
                                                }
                                            })
                                        }
                                    },
                                    {
                                        title: '最终合同执行率(%)',
                                        dataIndex: 'rate2',
                                        key: 'rate2',
                                        width: 140
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '设备类',
                                dataIndex: 'title_4',
                                key: 'title_4',
                                width: 530,
                                children: [
                                    {
                                        title: '已签订合同份数',
                                        dataIndex: 'num3',
                                        key: 'num3',
                                        width: 120,
                                        onClick:(obj) => {
                                            let rowData = obj.rowData;
                                            this.setState({
                                                visibleSB:true,
                                                propsData:{
                                                    contractType:'P6',
                                                    queryDataType:'1',
                                                    queryOpenType:rowData?.openType === 'jiaogong' ? '3' :  rowData?.openType === 'guidang' ? '4' : '',
                                                    queryPeriod:rowData?.period,
                                                    orgID:rowData?.id
                                                }
                                            })
                                        }
                                    },
                                    {
                                        title: '应执行合同份数',
                                        dataIndex: 'needSettleNum3',
                                        key: 'needSettleNum3',
                                        width: 120,
                                        onClick:(obj) => {
                                            let rowData = obj.rowData;
                                            this.setState({
                                                visibleSB:true,
                                                propsData:{
                                                    contractType:'P6',
                                                    queryDataType:'2',
                                                    queryOpenType:rowData?.openType === 'jiaogong' ? '3' :  rowData?.openType === 'guidang' ? '4' : '',
                                                    queryPeriod:rowData?.period,
                                                    orgID:rowData?.id
                                                }
                                            })
                                        }
                                    },
                                    {
                                        title: '已最终结算合同份数',
                                        dataIndex: 'finishSettleNum3',
                                        key: 'finishSettleNum3',
                                        width: 150,
                                        onClick:(obj) => {
                                            let rowData = obj.rowData;
                                            this.setState({
                                                visibleSB:true,
                                                propsData:{
                                                    contractType:'P6',
                                                    queryDataType:'3',
                                                    queryOpenType:rowData?.openType === 'jiaogong' ? '3' :  rowData?.openType === 'guidang' ? '4' : '',
                                                    queryPeriod:rowData?.period,
                                                    orgID:rowData?.id
                                                }
                                            })
                                        }
                                    },
                                    {
                                        title: '最终合同执行率(%)',
                                        dataIndex: 'rate3',
                                        key: 'rate3',
                                        width: 140
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '其它类',
                                dataIndex: 'title_5',
                                key: 'title_5',
                                width: 530,
                                children: [
                                    {
                                        title: '已签订合同份数',
                                        dataIndex: 'num4',
                                        key: 'num4',
                                        width: 120,
                                        onClick:(obj) => {
                                            let rowData = obj.rowData;
                                            this.setState({
                                                visibleQT:true,
                                                propsData:{
                                                    contractType:'21',
                                                    queryDataType:'1',
                                                    queryOpenType:rowData?.openType === 'jiaogong' ? '3' :  rowData?.openType === 'guidang' ? '4' : '',
                                                    queryPeriod:rowData?.period,
                                                    orgID:rowData?.id
                                                }
                                            })
                                        }
                                    },
                                    {
                                        title: '应执行合同份数',
                                        dataIndex: 'needSettleNum4',
                                        key: 'needSettleNum4',
                                        width: 120,
                                        onClick:(obj) => {
                                            let rowData = obj.rowData;
                                            this.setState({
                                                visibleQT:true,
                                                propsData:{
                                                    contractType:'21',
                                                    queryDataType:'2',
                                                    queryOpenType:rowData?.openType === 'jiaogong' ? '3' :  rowData?.openType === 'guidang' ? '4' : '',
                                                    queryPeriod:rowData?.period,
                                                    orgID:rowData?.id
                                                }
                                            })
                                        }
                                    },
                                    {
                                        title: '已最终结算合同份数',
                                        dataIndex: 'finishSettleNum4',
                                        key: 'finishSettleNum4',
                                        width: 150,
                                        onClick:(obj) => {
                                            let rowData = obj.rowData;
                                            this.setState({
                                                visibleQT:true,
                                                propsData:{
                                                    contractType:'21',
                                                    queryDataType:'3',
                                                    queryOpenType:rowData?.openType === 'jiaogong' ? '3' :  rowData?.openType === 'guidang' ? '4' : '',
                                                    queryPeriod:rowData?.period,
                                                    orgID:rowData?.id
                                                }
                                            })
                                        }
                                    },
                                    {
                                        title: '最终合同执行率(%)',
                                        dataIndex: 'rate4',
                                        key: 'rate4',
                                        width: 140
                                    }
                                ]
                            },
                        },
                    ]}
                />
                {
                    visibleGC ? <Drawer
                        title={'工程类合同'}
                        placement="right"
                        onClose={this.onClose}
                        visible={visibleGC}
                        width={window.innerWidth * 0.8}
                        bodyStyle={{ height: window.innerHeight - 155 }}
                        className='DrawerQnnTable'
                        footer={<div style={{ textAlign: 'right', paddingRight: '20px' }}><Button type="dashed" onClick={() => {
                            this.setState({
                                visibleGC: false
                            })
                        }}>取消</Button></div>}
                    >
                        <ProjectConstructionContractManagement {...this.props} propsData={propsData}/>
                    </Drawer> : null
                }
                {
                    visibleWZ ? <Drawer
                        title={'物资类合同'}
                        placement="right"
                        onClose={this.onClose}
                        visible={visibleWZ}
                        width={window.innerWidth * 0.8}
                        bodyStyle={{ height: window.innerHeight - 155 }}
                        className='DrawerQnnTable'
                        footer={<div style={{ textAlign: 'right', paddingRight: '20px' }}><Button type="dashed" onClick={() => {
                            this.setState({
                                visibleWZ: false
                            })
                        }}>取消</Button></div>}
                    >
                        <MaterialManagementContractManagement {...this.props} propsData={propsData}/>
                    </Drawer> : null
                }
                {
                    visibleSB ? <Drawer
                        title={'设备类合同'}
                        placement="right"
                        onClose={this.onClose}
                        visible={visibleSB}
                        width={window.innerWidth * 0.8}
                        bodyStyle={{ height: window.innerHeight - 155 }}
                        className='DrawerQnnTable'
                        footer={<div style={{ textAlign: 'right', paddingRight: '20px' }}><Button type="dashed" onClick={() => {
                            this.setState({
                                visibleSB: false
                            })
                        }}>取消</Button></div>}
                    >
                        <MachineryManagementContractManagement {...this.props} propsData={propsData}/>
                    </Drawer> : null
                }
                {
                    visibleQT ? <Drawer
                        title={'其它类合同'}
                        placement="right"
                        onClose={this.onClose}
                        visible={visibleQT}
                        width={window.innerWidth * 0.8}
                        bodyStyle={{ height: window.innerHeight - 155 }}
                        className='DrawerQnnTable'
                        footer={<div style={{ textAlign: 'right', paddingRight: '20px' }}><Button type="dashed" onClick={() => {
                            this.setState({
                                visibleQT: false
                            })
                        }}>取消</Button></div>}
                    >
                        <OtherContractManagement {...this.props} propsData={propsData}/>
                    </Drawer> : null
                }
            </div>
        );
    }
}

export default index;