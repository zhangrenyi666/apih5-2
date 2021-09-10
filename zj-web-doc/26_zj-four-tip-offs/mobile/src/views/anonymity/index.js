import React, { Component } from 'react';
import QnnForm from '../modules/qnn-table/qnn-form';
import { Form, Modal, message as Msg } from 'antd';
import s from './style.less';
import { NavBar, Icon, Toast } from 'antd-mobile';
import { push } from 'react-router-redux';
import $ from 'jquery';
const confirm = Modal.confirm;

class idnex extends Component {
    constructor() {
        super();
        this.state = {
            visible: false,
            file: null,
            data: []
        }
    }
    handleCancel = () => {
        $("meta[name='viewport']").attr('content', "width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no");
        this.setState({ visible: false, file: null });
    }
    componentDidMount() {
        const { wx, BMap } = window;
        wx.ready(() => {
            wx.hideOptionMenu();
        })
        let myCity = new BMap.LocalCity();
        myCity.get((city) => {
            let point = new BMap.Point(city.center.lng, city.center.lat);
            let gc = new BMap.Geocoder();
            gc.getLocation(point, (rs) => {
                let address = new BMap.Label(rs.address, { offset: new BMap.Size(0, 0) });
                this.props.form.setFieldsValue({ reportLocal: address.content })
            });
        });
    }
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        const { visible, file } = this.state;
        return (
            <div>
                <div className={s.root} style={{ display: visible ? "none" : 'block' }}>
                    <div className={s.top}>
                        <NavBar
                            style={{ width: "100%" }}
                            mode="dark"
                            icon={<Icon type="left" />}
                            onLeftClick={() => {
                                dispatch(push(`${mainModule}HomePage/3`));
                            }}
                        >{"匿名举报"}</NavBar>
                    </div>
                    <div className={s.center}>
                        <QnnForm
                            {...this.props} //使用QnnForm的页面必须使用rc-form插件包裹，并且将form传递给props
                            fetch={this.props.myFetch} //必须返回一个promise
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                            formConfig={[
                                {
                                    type: "string",
                                    label: "举报类型",
                                    field: "reportType", //唯一的字段名 ***必传
                                    initialValue: '0',
                                    hide: true
                                },
                                {//普通选择框
                                    type: 'string',
                                    label: '所属项目',
                                    field: 'itemId', //唯一的字段名 ***必传
                                    placeholder: '请选择',
                                    voice: true,
                                    required: true,
                                },
                                {
                                    type: "string",
                                    label: "地理位置",
                                    field: "reportLocal", //唯一的字段名 ***必传
                                    hide: true,
                                    placeholder: "请输入", //占位符
                                },
                                {
                                    type: "string",
                                    label: "工程位置",
                                    field: "reportProjectLocation", //唯一的字段名 ***必传
                                    voice: true,
                                    placeholder: "请输入", //占位符
                                },
                                {
                                    type: "textarea",
                                    label: "问题描述",
                                    field: "problemDescribe", //唯一的字段名 ***必传
                                    required: true,
                                    voice: true,
                                    placeholder: "请输入", //占位符
                                },
                                {
                                    type: 'camera',
                                    label: '图片附件',
                                    field: 'files', //唯一的字段名 ***必传
                                    cameraConfig: {
                                        type: "images",
                                    },
                                    fetchConfig: {//配置后将会去请求下拉选项数据
                                        apiName: window.configs.domain + 'upload',
                                    },
                                },
                                {
                                    type: 'files',
                                    label: '其它附件',
                                    field: 'otherFiles', //唯一的字段名 ***必传
                                    initialValue: [],
                                    onChange: (obj) => {
                                        if (obj.file && obj.file.status === "uploading" && obj.file.percent === 0) {
                                            Toast.loading('附件上传中...', 3000);
                                        } else if (obj.file && obj.file.status === "done" &&  obj.file.percent === 100) {
                                            Toast.hide();
                                        } else if(obj.file && obj.file.status === "error"){
                                            Toast.fail('上传附件失败！'); 
                                        }
                                    },
                                    onPreview: (file, fileList) => {
                                        if (/(image)/ig.test(file.type)) {
                                            $("meta[name='viewport']").attr('content', "width=600, minimum-scale=0.4, maximum-scale=3.0");
                                        } else {
                                            $("meta[name='viewport']").attr('content', "width=device-width");
                                        }

                                        this.setState({
                                            file: file.mobileUrl
                                        }, () => {
                                            this.setState({ visible: true })
                                        })
                                    },
                                    beforeUpload: (maxSize, file) => {
                                        if(file[0].name.indexOf('.') === -1){
                                            Msg.info('上传附件格式不正确！');
                                            return false;
                                        }
                                        if (file[0].size / 1024 / 1024 < 200) {
                                            return true;
                                        } else {
                                            Msg.info('文件过大,最大可上传200M！');
                                            return false;
                                        }
                                    },
                                    fetchConfig: {//配置后将会去请求下拉选项数据
                                        apiName: window.configs.domain + 'upload',
                                    },
                                },
                            ]}
                            btns={[
                                {
                                    label: '取消',
                                    isValidate: false,//是否验证表单 默认true
                                    onClick: function (obj) {
                                        dispatch(push(`${mainModule}HomePage/3`));
                                    },
                                },
                                {
                                    label: '提交',
                                    type: 'primary',
                                    isValidate: true,//是否验证表单 默认true
                                    onClick: (obj) => {
                                        const { myFetch, Msg } = obj.btnfns;
                                        confirm({
                                            centered:true,
                                            title: `确认要提交这条信息吗？`,
                                            okText: "确认",
                                            cancelText: "取消",
                                            onOk: () => {
                                                myFetch('addZjTechnicalQualityReportInfo', obj.values, function ({ data, success, message }) {
                                                    if (success) {
                                                        Msg.success(message);
                                                        dispatch(push(`${mainModule}HomePage/3`));
                                                    } else {
                                                        Msg.error(message);
                                                    }
                                                })
                                            },
                                        });
                                    }
                                }
                            ]}
                        />
                    </div>
                </div>
                {/* 这里不能使用display */}
                {
                    visible ? <div>
                        <div className={s.goback} onClick={this.handleCancel}> 返回</div>
                        <iframe id="iv" src={file} className={s.rootIV}></iframe>
                    </div> : null
                }
            </div>
        )
    }
}
export default Form.create()(idnex)