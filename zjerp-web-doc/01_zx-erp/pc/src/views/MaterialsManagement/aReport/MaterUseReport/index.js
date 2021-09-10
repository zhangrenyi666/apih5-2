import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import QnnForm from "../../../modules/qnn-table/qnn-form";
import { Modal, message as Msg, Row, Col } from "antd";
import { ExportOutlined, SearchOutlined, ReloadOutlined } from '@ant-design/icons';
import s from "./style.less";
const config = {
    antd: {
        rowKey: 'id',
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight * 0.5
        }
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: false,
    firstRowIsSearch: false,
    isShowRowSelect: false
};
const confirm = Modal.confirm;
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            formConfigConfig: [],
            orgID: '',
            period: '',
            resTypeID: '',
            lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : ''
        }
    }
    componentDidMount() {
        let obj = {
            orgID: '',
            period: '',
            resTypeID: ''
        };
        this.getTittle(obj);
    }
    getTittle(obj) {
        let formConfig = [
            {
                isInTable: false,
                form: {
                    field: 'id',
                    type: 'string',
                    hide: true,
                }
            },
        ];
        this.setState({
            formConfigConfig: []
        })
        this.props.myFetch('ureportZxSkResWasAdmListGetTitle', obj).then(({ success, message, data }) => {
            if (success) {
                if (data) {
                    let configData = data.table;
                    this.setState({
                        formConfigConfig: formConfig.concat(configData)
                    }, () => {
                        this.setState({
                            orgID: obj.orgID,
                            period: obj.period,
                            resTypeID: obj.resTypeID
                        })
                    });
                }
            } else {
                Msg.error(message);
            }
        });
    }
    render() {
        let { myPublic: { appInfo: { ureport } } } = this.props;
        const { ext1, departmentId, companyId, projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { lockProjectId } = this.state;
        let jurisdiction = departmentId;
        if (lockProjectId !== 'all' && lockProjectId !== '') {
            jurisdiction = lockProjectId;
        } else {
            if (ext1 === '1' || ext1 === '2') {
                jurisdiction = companyId;
            } else if (ext1 === '3' || ext1 === '4') {
                jurisdiction = projectId;
            } else { }
        }
        return (
            <div className={s.root}>
                <Row>
                    <Col span={24}>
                        <QnnForm
                            form={this.props.form}
                            history={this.props.history}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
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
                                    label: '项目名称',
                                    field: 'orgID',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysProjectBySelect',
                                        otherParams: {
                                            departmentId: jurisdiction
                                        }
                                    },
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    label: '材料类型',
                                    field: 'resTypeID',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'catName',
                                        value: 'id'
                                    },
                                    fetchConfig: {
                                        apiName: 'getZxSkResCategoryMaterialsListResource',
                                        otherParams: {
                                            "parentOrgID": "1"
                                        }
                                    },
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    label: '期次',
                                    field: 'period',
                                    type: 'month',
                                    placeholder: '请选择',
                                    span: 8
                                }
                            ]}
                            tailFormItemLayout={{
                                wrapperCol: {
                                    xs: {
                                        span: 15,
                                        offset: 18
                                    },
                                    sm: {
                                        span: 15,
                                        offset: 18
                                    }
                                }
                            }}
                            btns={[
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "导出",
                                    icon: <ExportOutlined />,
                                    onClick: (obj) => {
                                        let value = obj.form.getFieldsValue();
                                        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                        var URL = `${ureport}excel?_u=minio:zxSkResWasAdmList.xml&_n=物资耗用分配表&access_token=${access_token}&orgID=${value.orgID ? value.orgID : ''}&resTypeID=${value.resTypeID ? value.resTypeID : ''}&period=${value.period ? new Date(value.period._d).getTime() : ''}`;
                                        confirm({
                                            content: '确定导出报表吗?',
                                            onOk: () => {
                                                window.open(URL);
                                            }
                                        });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    icon: <SearchOutlined />,
                                    label: "搜索",
                                    onClick: (obj) => {
                                        this.getTittle(obj.form.getFieldsValue());
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "dashed",
                                    label: "重置",
                                    icon: <ReloadOutlined />,
                                    onClick: (obj) => {
                                        obj.form.setFieldsValue({
                                            orgID: '',
                                            resTypeID: '',
                                            period: ''
                                        });
                                        this.getTittle(obj.form.getFieldsValue());
                                    }
                                }
                            ]}
                        />
                    </Col>
                </Row>

                {(this.state.formConfigConfig.length > 0) ? <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'ureportZxSkResWasAdmListGetDataList',
                        otherParams: {
                            projectId: jurisdiction,
                            orgID: this.state.orgID,
                            period: this.state.period,
                            resTypeID: this.state.resTypeID
                        }
                    }}
                    formConfig={this.state.formConfigConfig}
                    actionBtns={[]}
                /> : null}

            </div>
        );
    }
}

export default index;