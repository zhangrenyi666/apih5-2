import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            listData: [
                {
                    id: '1',
                    pledgeMoney: '合同履约保证金'
                },
                {
                    id: '2',
                    pledgeMoney: '民工工资偿付保证金'
                },
                {
                    id: '3',
                    pledgeMoney: '安全生产保证金'
                },
                {
                    id: '4',
                    pledgeMoney: '工程奖励基金'
                }
            ],
            formData: props?.rowData || props?.selectedRows?.[0] || {},
            drawerDetailTitle: props?.drawerDetailTitle || ''
        }
    }
    contractOn = (obj) => {
        let rowData = obj.form.getFieldsValue();
        let projectProperty = '';
        let projType = '';
        let contractor = '';
        let biddingQualification = '';
        if (rowData?.contractor && !rowData?.biddingQualification) {
            biddingQualification = '()-';
        }
        let projectLocation = '';
        let shortName = '';
        let lotNo = '';
        if (rowData?.projectProperty) {
            projectProperty = rowData.projectProperty === 'T' ? 'T-' : 'X-';
        }
        if (rowData?.projTypeExt1) {
            projType = `${rowData.projTypeExt1}-`;
        }
        if (rowData?.contractor) {
            contractor = rowData.contractor;
        }
        if (rowData?.biddingQualification) {
            biddingQualification = `(${rowData.biddingQualification})-`;
        }
        if (rowData?.projectLocationExt1) {
            projectLocation = `${rowData.projectLocationExt1}-`;
        }
        if (rowData?.shortName) {
            shortName = rowData.shortName + '-';
        }
        if (rowData?.lotNo) {
            lotNo = rowData.lotNo;
        }
        if (contractor && biddingQualification && `(${contractor})-` === biddingQualification) {
            biddingQualification = '(T)-';
        }
        let contractNo = projectProperty + projType + contractor + biddingQualification + projectLocation + shortName + lotNo;
        obj.form.setFieldsValue({ contractNo: contractNo });
    }
    componentDidMount () {
        if (this.props.onRef) {
            this.props.onRef(this);
        }
    }
    render () {
        const { departmentId, listData, formData, drawerDetailTitle } = this.state;
        return (
            <div>
                <QnnForm
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    wrappedComponentRef={(qnnForm) => this.qnnForm = qnnForm}
                    data={formData}
                    formConfig={[
                        {
                            type: "string",
                            label: "主键ID",
                            field: "id",
                            hide: true
                        },
                        {
                            type: "string",
                            label: "审核状态",
                            field: "contrStatus",
                            hide: true
                        },
                        {
                            type: "component",
                            field: "component1",
                            Component: obj => {
                                return (
                                    <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                        项目基本信息
                                    </div>
                                );
                            }
                        },
                        {
                            type: "string",
                            field: "companyId", //唯一的字段名 ***必传
                            hide: true
                        },
                        {
                            type: "string",
                            field: "companyName", //唯一的字段名 ***必传
                            hide: true
                        },
                        {
                            type: "string",
                            field: "comID", //唯一的字段名 ***必传
                            hide: true
                        },
                        {
                            label: '项目名称',
                            field: 'orgID',
                            type: 'select',
                            optionConfig: {
                                label: 'departmentName',
                                value: 'departmentId',
                                linkageFields: {
                                    orgName: 'departmentName',
                                    companyId: 'companyId',
                                    companyName: 'companyName',
                                    comID: 'companyId'
                                }
                            },
                            fetchConfig: {
                                apiName: 'getSysProjectBySelect',
                                otherParams: {
                                    departmentId: departmentId
                                }
                            },
                            onChange: (val, obj) => {
                                if (obj?.itemData?.departmentId) {
                                    obj.itemData.orgName = obj.itemData.departmentName;
                                    obj.itemData.projectName = obj.itemData.departmentFullName;
                                    obj.itemData.shortName = obj.itemData.projectPinyin;
                                    obj.itemData.projQuality = obj.itemData.contractingType;
                                    obj.itemData.projDetail = obj.itemData.detail;
                                }
                                obj.form.setFieldsValue({ ...obj.itemData });
                            },
                            allowClear: false,
                            showSearch: true,
                            required: true,
                            hide: drawerDetailTitle !== '新增' ? true : false,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            type: "string",
                            label: "项目名称",
                            field: "orgName", //唯一的字段名 ***必传
                            placeholder: '请输入',
                            span: 8,
                            hide: drawerDetailTitle === '新增' ? true : false,
                            disabled: true
                        },
                        {
                            label: '项目全称',
                            field: 'projectName',
                            type: 'string',
                            required: true,
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            disabled: drawerDetailTitle === '新增' ? false : true,
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '项目性质',
                            field: 'projectProperty',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'xiangMuXingZhi'
                                }
                            },
                            allowClear: false,
                            showSearch: true,
                            required: true,
                            condition: [
                                {
                                    regex: {
                                        id: ['!', /(''|null|undefined)/ig]
                                    },
                                    action: 'disabled'
                                }
                            ],
                            onChange: (val, obj) => {
                                this.contractOn(obj);
                            },
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '项目简称',
                            field: 'shortName',
                            type: 'string',
                            required: true,
                            disabled: drawerDetailTitle === '新增' ? false : true,
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            onChange: (val, obj) => {
                                this.contractOn(obj);
                            },
                            diyRules: (obj) => {
                                var required = obj.required;
                                var message = obj.message;
                                return [
                                    {
                                        required: required,
                                        message: message
                                    },
                                    {
                                        pattern: new RegExp(/^[A-Z]+$/),
                                        message: "请输入英文大写"
                                    }
                                ];
                            },
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            type: "string",
                            field: "projTypeExt1", //唯一的字段名 ***必传
                            hide: true
                        },
                        {
                            label: '工程类别',
                            field: 'projType',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId',
                                linkageFields: {
                                    projTypeExt1: 'ext1'
                                }
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'gongChengLeiBie'
                                }
                            },
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            onChange: (val, obj) => {
                                this.contractOn(obj);
                            },
                            required: true,
                            disabled: drawerDetailTitle === '新增' ? false : true,
                            allowClear: false,
                            showSearch: true,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '项目施工单位',
                            field: 'locationInDeprFormula',
                            type: 'select',
                            optionConfig: {
                                label: 'companyName',
                                value: 'companyId',
                            },
                            fetchConfig: {
                                apiName: 'getSysCompanyBySelect',
                                otherParams: { departmentId:departmentId }
                            },
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            allowClear: false,
                            showSearch: true,
                            // required: true,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '承建单位简称',
                            field: 'contractor',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'chengJianDanWeiJianCheng'
                                }
                            },
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            onChange: (val, obj) => {
                                this.contractOn(obj);
                            },
                            allowClear: false,
                            showSearch: true,
                            required: true,
                            disabled: drawerDetailTitle === '新增' ? false : true,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '标段号',
                            field: 'lotNo',
                            type: 'string',
                            required: true,
                            disabled: drawerDetailTitle === '新增' ? false : true,
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            diyRules: (obj) => {
                                var required = obj.required;
                                var message = obj.message;
                                return [
                                    {
                                        required: required,
                                        message: message
                                    },
                                    {
                                        pattern: new RegExp(/^\d{2}$/),
                                        message: "请输入两位数字"
                                    }
                                ];
                            },
                            onChange: (val, obj) => {
                                this.contractOn(obj);
                            },
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '项目所在区域',
                            field: 'subArea',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'suoShuQuYu'
                                }
                            },
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            onChange: (val, obj) => {
                                obj.form.setFieldsValue({ projectLocation: null, provinceAbbreviation: null, cityName: null });
                            },
                            allowClear: false,
                            showSearch: true,
                            required: true,
                            disabled: drawerDetailTitle === '新增' ? false : true,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            type: "string",
                            field: "projectLocationExt1", //唯一的字段名 ***必传
                            hide: true
                        },
                        {
                            label: '项目所在地',
                            field: 'projectLocation',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId',
                                linkageFields: {
                                    provinceAbbreviation: 'itemAsName',
                                    projectLocationExt1: 'ext1'
                                }
                            },
                            dependencies: ["subArea"],
                            dependenciesReRender: true,
                            onChange: (val, obj) => {
                                this.contractOn(obj);
                                obj.form.setFieldsValue({ cityName: null });
                            },
                            fetchConfig: (obj) => {
                                if (obj.form.getFieldValue('subArea') === '10001') {
                                    return {
                                        apiName: 'getBaseCodeSelect',
                                        otherParams: {
                                            itemId: 'xingzhengquhuadaima'
                                        }
                                    }
                                } else if (obj.form.getFieldValue('subArea') === '10002') {
                                    return {
                                        apiName: 'getBaseCodeSelect',
                                        otherParams: {
                                            itemId: 'haiWaiXiangMuSuoZaiShengFenJianCheng'
                                        }
                                    }
                                } else {
                                    return {};
                                }
                            },
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            allowClear: false,
                            showSearch: true,
                            required: true,
                            disabled: drawerDetailTitle === '新增' ? false : true,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '项目所在省份简称',
                            field: 'provinceAbbreviation',
                            type: 'string',
                            allowClear: false,
                            showSearch: true,
                            disabled: true,
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '项目所在市级简称',
                            field: 'cityName',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            dependencies: ["projectLocation"],
                            dependenciesReRender: true,
                            fetchConfig: (obj) => {
                                if (obj.form.getFieldValue('projectLocation')) {
                                    return {
                                        apiName: 'getBaseCodeSelect',
                                        otherParams: {
                                            itemId: obj.form.getFieldValue('projectLocation')
                                        }
                                    }
                                }
                            },
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                },
                                {
                                    regex: {
                                        subArea: '10001'
                                    },
                                    action: 'required'
                                },
                                {
                                    regex: {
                                        subArea: '10002'
                                    },
                                    action: 'unRequired'
                                }
                            ],
                            allowClear: false,
                            showSearch: true,
                            disabled: drawerDetailTitle === '新增' ? false : true,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '项目经理姓名',
                            field: 'projectChiefName',
                            type: 'string',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '项目经理电话',
                            field: 'projectChiefTel',
                            type: 'phone',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '项目参建性质',
                            field: 'projQuality',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'xiangMuCanJianXingZhi'
                                }
                            },
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            allowClear: false,
                            showSearch: true,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '合同开工日期',
                            field: 'actualStartDate',
                            type: 'date',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            required: true,
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '合同竣工日期',
                            field: 'actualEndDate',
                            type: 'date',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            required: true,
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '所属事业部',
                            field: 'bizDep',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'suoShuShiYeBu'
                                }
                            },
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            allowClear: false,
                            showSearch: true,
                            required: true,
                            disabled: drawerDetailTitle === '新增' ? false : true,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '中标资质',
                            field: 'biddingQualification',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'zhongBiaoZiZhi'
                                }
                            },
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            onChange: (val, obj) => {
                                this.contractOn(obj);
                            },
                            allowClear: false,
                            showSearch: true,
                            required: true,
                            disabled: drawerDetailTitle === '新增' ? false : true,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '项目特征',
                            field: 'projFea',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'xiangMuTeZheng'
                                }
                            },
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            allowClear: false,
                            showSearch: true,
                            required: true,
                            disabled: drawerDetailTitle === '新增' ? false : true,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '是否参与指标考核',
                            field: 'isSettle',
                            type: 'checkbox',
                            optionData: [
                                {
                                    value: '1'
                                }
                            ],
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '项目开工日期',
                            field: 'realBeginDate',
                            type: 'date',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            required: true,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '主体完工日期',
                            field: 'mainEndDate',
                            type: 'date',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            required: true,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '项目交工日期',
                            field: 'deliveryDate',
                            type: 'date',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            required: true,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '项目竣工日期',
                            field: 'realEndDate',
                            type: 'date',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            required: true,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '项目归档日期',
                            field: 'docDate',
                            type: 'date',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            required: true,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            type: "component",
                            field: "component2",
                            Component: obj => {
                                return (
                                    <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                        合同基本信息
                                    </div>
                                );
                            }
                        },
                        {
                            label: '合同名称',
                            field: 'contractName',
                            type: 'string',
                            required: true,
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 3 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 21 },
                                    sm: { span: 21 }
                                }
                            }
                        },
                        {
                            label: '甲方单位',
                            field: 'firstName',
                            type: 'string',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '甲方代表',
                            field: 'firstPrincipal',
                            type: 'string',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '甲方联系电话',
                            field: 'firstUnitTel',
                            type: 'phone',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '甲方联系地址',
                            field: 'firstAddr',
                            type: 'string',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 3 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 21 },
                                    sm: { span: 21 }
                                }
                            }
                        },
                        {
                            label: '乙方单位',
                            field: 'secondName',
                            type: 'string',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '乙方代表',
                            field: 'secondPrincipal',
                            type: 'string',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '乙方联系电话',
                            field: 'secondUnitTel',
                            type: 'phone',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '乙方联系地址',
                            field: 'secondAddr',
                            type: 'string',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 3 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 21 },
                                    sm: { span: 21 }
                                }
                            }
                        },
                        {
                            type: "component",
                            field: "component3",
                            Component: obj => {
                                return (
                                    <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                        合同金额
                                    </div>
                                );
                            }
                        },
                        {
                            label: '含税合同金额(万元)',
                            field: 'contractCost',
                            type: 'number',
                            required: true,
                            onChange: (val, obj) => {
                                let formData = obj.form.getFieldsValue();
                                if (formData.contractCost && (Number(formData.taxRate) || Number(formData.taxRate) === 0)) {
                                    obj.form.setFieldsValue({
                                        contractCostNoTax: (formData.contractCost / (1 + Number(formData.taxRate) / 100)),
                                        contractCostTax: (formData.contractCost - formData.contractCost / (1 + Number(formData.taxRate) / 100))
                                    })
                                } else {
                                    obj.form.setFieldsValue({
                                        contractCostNoTax: 0,
                                        contractCostTax: 0
                                    })
                                }
                            },
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '税率(%)',
                            field: 'taxRate',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'shuiLv'
                                }
                            },
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            onChange: (val, obj) => {
                                let formData = obj.form.getFieldsValue();
                                if (formData.contractCost && (Number(formData.taxRate) || Number(formData.taxRate) === 0)) {
                                    obj.form.setFieldsValue({
                                        contractCostNoTax: (formData.contractCost / (1 + Number(formData.taxRate) / 100)),
                                        contractCostTax: (formData.contractCost - formData.contractCost / (1 + Number(formData.taxRate) / 100))
                                    })
                                } else {
                                    obj.form.setFieldsValue({
                                        contractCostNoTax: 0,
                                        contractCostTax: 0
                                    })
                                }
                            },
                            allowClear: false,
                            required: true,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '不含税合同金额(万元)',
                            field: 'contractCostNoTax',
                            type: 'number',
                            disabled: true,
                            precision: 2,
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '清单金额(万元)',
                            field: 'qdMoney',
                            type: 'number',
                            placeholder: '请输入',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            span: 8
                        },
                        {
                            label: '计税方法',
                            field: 'isEasyTax',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'shiFou'
                                }
                            },
                            allowClear: false,
                            required: true,
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '税金(万元)',
                            field: 'contractCostTax',
                            type: 'number',
                            disabled: true,
                            precision: 2,
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '当前合同总造价(万元)',
                            field: 'contractMoney',
                            type: 'number',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '对下标准合同编码',
                            field: 'contractNo',
                            type: 'string',
                            placeholder: '请输入',
                            disabled: true,
                            span: 16,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 5 },
                                    sm: { span: 5 }
                                },
                                wrapperCol: {
                                    xs: { span: 19 },
                                    sm: { span: 19 }
                                }
                            }
                        },
                        {
                            type: "component",
                            field: "component4",
                            Component: obj => {
                                return (
                                    <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                        合同工期
                                    </div>
                                );
                            }
                        },
                        {
                            label: '合同开始时间',
                            field: 'planStartDate',
                            type: 'date',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            required:true,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '合同结束时间',
                            field: 'planEndDate',
                            type: 'date',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            required:true,
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '合同工期(天)',
                            field: 'csTimeLimit',
                            type: 'string',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '签订日期',
                            field: 'signatureDate',
                            type: 'date',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            label: '合同状态',
                            field: 'contractStatus',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'heTongZhuangTai'
                                }
                            },
                            allowClear: false,
                            required: true,
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            initialValue: '1',
                            placeholder: '请选择',
                            span: 8
                        },
                        {
                            type: "component",
                            field: "component5",
                            Component: obj => {
                                return (
                                    <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                        外部单位信息
                                    </div>
                                );
                            }
                        },
                        {
                            label: '监理单位',
                            field: 'consultative',
                            type: 'string',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '监理单位联系人',
                            field: 'consultativeUser',
                            type: 'string',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '监理单位电话',
                            field: 'consultativeTel',
                            type: 'phone',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '监理单位地址',
                            field: 'consultativeAddr',
                            type: 'string',
                            placeholder: '请输入',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 3 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 21 },
                                    sm: { span: 21 }
                                }
                            }
                        },
                        {
                            label: '设计单位',
                            field: 'designUnit',
                            type: 'string',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '设计单位联系人',
                            field: 'designUser',
                            type: 'string',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '设计单位电话',
                            field: 'designPhone',
                            type: 'phone',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '设计单位地址',
                            field: 'designAddr',
                            type: 'string',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 3 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 21 },
                                    sm: { span: 21 }
                                }
                            }
                        },
                        {
                            type: "component",
                            field: "component6",
                            Component: obj => {
                                return (
                                    <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                        合同条款
                                    </div>
                                );
                            }
                        },
                        {
                            label: '动员预付款(元)',
                            field: 'dyyfkMoney',
                            type: 'number',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '动员预付款起扣点(元)',
                            field: 'dyyfkqkdMoney',
                            type: 'number',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '扣回动员预付款比例(%)',
                            field: 'khdyyfkPercent',
                            type: 'number',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '动员预付款全额扣回点(元)',
                            field: 'dyyfkqekhdMoney',
                            type: 'number',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '材料预付款比例(%)',
                            field: 'clyfkPercent',
                            type: 'number',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '材料预付款限额(元)',
                            field: 'clyfkxeMoney',
                            type: 'number',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '材料扣回款比例(%)',
                            field: 'clkhkblPercent',
                            type: 'number',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '迟扣款利息(%)',
                            field: 'ckklxPercent',
                            type: 'number',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '迟扣款利息限额(元)',
                            field: 'ckklxxeMoney',
                            type: 'number',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '违约金限额(元)',
                            field: 'wyjxeMoney',
                            type: 'number',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '索赔金限额(元)',
                            field: 'cpjexeMoney',
                            type: 'number',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '保留金扣款比例(%)',
                            field: 'bljblPercent',
                            type: 'number',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '保留金起扣点(元)',
                            field: 'bljqkdMoney',
                            type: 'number',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '累计保留金限额(元)',
                            field: 'ljkbljxeMoney',
                            type: 'number',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            type: 'qnnTable',
                            field: 'bondList',
                            incToForm: true,
                            initialValue: listData,
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            qnnTableConfig: {
                                actionBtnsPosition: "top",
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: false,
                                pageSize: 9999,
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '主键id',
                                            field: 'id',
                                            hide: true
                                        }
                                    },
                                    {
                                        table: {
                                            title: '保证金',
                                            dataIndex: 'pledgeMoney',
                                            key: 'pledgeMoney'
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                        },
                                    },
                                    {
                                        table: {
                                            title: '类型',
                                            dataIndex: 'type',
                                            key: 'type',
                                            tdEdit: true,
                                            render: (data, rowData) => {
                                                if (rowData.pledgeMoney === '合同履约保证金') {
                                                    if (data === '10001') {
                                                        return '现金';
                                                    } else if (data === '10002') {
                                                        return '现金加保函';
                                                    } else if (data === '10003') {
                                                        return '银行保函';
                                                    } else if (data === '10004') {
                                                        return '资产抵押';
                                                    }
                                                } else {
                                                    if (data === '10001') {
                                                        return '中期扣付计量款';
                                                    } else if (data === '10002') {
                                                        return '银行保函';
                                                    } else if (data === '10003') {
                                                        return '资产抵押';
                                                    }
                                                }
                                            }
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'itemName',
                                                value: 'itemId'
                                            },
                                            fetchConfig: {
                                                apiName: 'getBaseCodeSelect',
                                                otherParams: (obj) => {
                                                    if (obj.rowData.pledgeMoney === '合同履约保证金') {
                                                        return {
                                                            itemId: 'heTongLvYueBaoZhengJinLeiXing'
                                                        }
                                                    } else {
                                                        return {
                                                            itemId: 'nongMinGongAnQuanGongChengJiangLiLeiXing'
                                                        }
                                                    }
                                                }
                                            },
                                            dependencies: ['pledgeMoney'],
                                            dependenciesReRender: true,
                                            placeholder: '请选择'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '现金金额',
                                            dataIndex: 'money',
                                            key: 'money',
                                            tdEdit: true,
                                            render: (data, rowData) => {
                                                if (rowData.pledgeMoney !== '合同履约保证金' && rowData.type === '10001') {
                                                    return data + '%';
                                                } else {
                                                    return data;
                                                }
                                            }
                                        },
                                        form: {
                                            type: 'money',
                                            formatter: (value, props) => {
                                                if (props?.rowData?.pledgeMoney !== '合同履约保证金' && props?.rowData?.type === '10001') {
                                                    return value ? (`${value.toString().replace(/%/ig, '')}%`) : value;
                                                } else {
                                                    return value;
                                                }
                                            },
                                            parser: (value, props) => {
                                                return value ? `${value}`.replace(/%/ig, '') : value;
                                            },
                                            dependencies: ['type'],
                                            dependenciesReRender: true,
                                            placeholder: '请输入'
                                        },
                                    },
                                    {
                                        table: {
                                            title: '返还条件',
                                            dataIndex: 'backCondition',
                                            key: 'backCondition',
                                            tdEdit: true,
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                        },
                                    },
                                    {
                                        table: {
                                            title: '期限',
                                            dataIndex: 'timeLimit',
                                            key: 'timeLimit',
                                            tdEdit: true,
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                        },
                                    }
                                ],
                                actionBtns: [
                                    {
                                        name: 'del',
                                        icon: 'delete',
                                        type: 'danger',
                                        label: '删除'
                                    }
                                ]
                            }
                        },
                        {
                            type: "component",
                            field: "component7",
                            Component: obj => {
                                return (
                                    <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                        项目概况
                                    </div>
                                );
                            }
                        },
                        {
                            label: '主要工程项目工程数量',
                            field: 'projDetail',
                            type: 'textarea',
                            required: true,
                            placeholder: '请输入',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 24 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 24 }
                                }
                            }
                        },
                        {
                            label: '业主节点工期',
                            field: 'ownerNodeDeys',
                            type: 'textarea',
                            required: true,
                            placeholder: '请输入',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 24 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 24 }
                                }
                            }
                        },
                        {
                            label: '业主调差、变更定价原则等主要专用条款',
                            field: 'ownerSpecialClause',
                            type: 'textarea',
                            required: true,
                            placeholder: '请输入',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 24 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 24 }
                                }
                            }
                        },
                        {
                            label: '合同内容概述',
                            field: 'summaryOfContractContents',
                            type: 'textarea',
                            placeholder: '请输入',
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 3 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 21 },
                                    sm: { span: 21 }
                                }
                            }
                        },
                        {
                            label: '附件',
                            field: 'attachment',
                            type: 'files',
                            required: true,
                            fetchConfig: {
                                apiName: 'upload'
                            },
                            condition: [
                                {
                                    regex: {
                                        contrStatus: '1'
                                    },
                                    action: 'disabled'
                                }
                            ],
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 3 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 21 },
                                    sm: { span: 21 }
                                }
                            }
                        }
                    ]}
                    formItemLayout={{
                        labelCol: {
                            xs: { span: 10 },
                            sm: { span: 10 }
                        },
                        wrapperCol: {
                            xs: { span: 14 },
                            sm: { span: 14 }
                        }
                    }}
                />
            </div>
        );
    }
}
export default index;
