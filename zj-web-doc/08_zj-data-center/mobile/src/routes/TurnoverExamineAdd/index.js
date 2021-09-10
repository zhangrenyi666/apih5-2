import React, { Component } from 'react';
import { Switch, List, DatePicker, Picker, TextareaItem, ImagePicker, InputItem, WhiteSpace, WingBlank, Button, Toast } from 'antd-mobile';
import { createForm } from 'rc-form';
import { TurnoverExamineAddData } from '../tableConfig'
// import Image from '../common/image'
import { MyFetch, getWxJsSdk, getUserInfo } from '../../main'; //, 
import './style.less';
const Item = List.Item;

class TurnoverExamineAdd extends Component {
    constructor(props) {
        super(props);
        document.getElementsByTagName('title')[0].innerHTML = '出入机房审批新增'
        this.state = {
            files: [
                // {
                //     url: 'https://zos.alipayobjects.com/rmsportal/PZUUCKTRIHWiZSY.jpeg',
                //     id: '2121',
                // }
            ],
            refreshing: false,
            initValue: {},//初始化数据 
            person: [],
            // unitName:'',//所属单位名
            userInfo: {}//用户信息
        };
        this.store = {//总数据仓库 
            ...TurnoverExamineAddData,
        }
    }

    componentDidMount() {
        const me = this; 
        getUserInfo(({userInfo}) => {//getOtherOrgMemberList
            console.log('userInfo:', userInfo);
            this.setState({
                userInfo: {
                    userId: userInfo.userId,
                    mobile: userInfo.other ? userInfo.other.mobile : '未获取到数据',
                    name: userInfo.other ? userInfo.other.name : '未获取到数据'
                }
            })
            const { setFieldsValue } = this.props.form;
            setFieldsValue({
                applyUserName: userInfo.other ? userInfo.other.name : '未获取到数据',
                applyUserTel: userInfo.other ? userInfo.other.mobile : '未获取到数据',
            })
            let { userId } = userInfo.userId; 
            MyFetch('getDepartmentMemberList', { userid: userId }, ({ data, success, message }) => {
                // getOtherOrgMemberList  getMember
                // console.log('getOtherOrgMemberList:', data)
                if (success) {
                    setFieldsValue({
                        subordinateUnit: data[0] ? (data[0].name ? data[0].name : '未获取到数据') : '未获取到数据',
                    })
                    if (success) {  
                        const selectPerson = data.map((item, index) => {
                            // console.log(item, index)  
                            return {
                                value: `${item.id},${item.name}`,
                                label: item.name,
                                children: item.oaMemberList.map((item, index) => {
                                    return {
                                        value: `${item.userid},${item.name}`,
                                        label: item.name,
                                    }
                                })
                            }
                        })
                        me.setState({ selectPerson })
                    }
                }
            })

            //获取编号
            MyFetch('getApplyNumber', {}, ({ data, success, message }) => {
                console.log(data);
                if (success) {
                    setFieldsValue({
                        approvalName: data ? (data.approvalName ? data.approvalName : '未获取到数据') : '未获取到数据',
                    })
                } else {
                    setFieldsValue({
                        approvalName: '未获取到数据',
                    })
                }
            })

        })
    }

    onChangePerson = (person) => {//改变问询对象
        this.setState({ person });
    }

    sendBtn() {//提交 
        let { files, person, userInfo } = this.state;
        if (person.length < 1) {//设置审批人为必选
            Toast.fail('请选择审批人！', 2)
            return
        };

        this.props.form.validateFields((error, value) => {//获取表单数据 
            if (error) {//如果有必填项未填写
                // console.log('error:',error);
                for (const key in error) {
                    Toast.fail(error[key].errors[0].message)
                    return;
                }
                return;
            }
            let applyExaminePer = {//获取选择的审批人的数据
                oaId: person[0].split(',')[0],
                oaName: person[0].split(',')[1],
                userid: person[1].split(',')[0],
                name: person[1].split(',')[1],
            }
  
            let otherData = { imageList: files, applyUserId: userInfo.userId }//图片数据加上申请名

            let params = Object.assign(value, otherData, { applyExaminePer });//提交的参数
            console.log('params:', params);
            Toast.loading('提交中');
            MyFetch('addZjJfApproval', params, (res) => {
                // console.log('addZjJfApproval:', res);
                if (res.success) {
                    this.props.history.push('/page/TurnoverApplyList');
                    Toast.hide()
                } else {
                    Toast.fail(res.errorMsg, 2, ()=>{
                        Toast.hide()
                    })  
                }
            })
        });
    }

    onImageClick = (index, files) => { //查看图片
        var fileImg = [];
        for (const item of files) {
            fileImg.push(item.url);
        }
        getWxJsSdk(({ wx, ready, error }) => {
            if (ready && !error) {
                wx.previewImage({
                    current: fileImg[index], // 
                    urls: fileImg // 
                });
            }
        })
    }


    onChangeImages = (files, type, index) => {
        this.setState({
            files,
        });
    };


    onAddImageClick = (e) => {
        e.preventDefault();
        const me = this;
        getWxJsSdk(({ wx, ready, error }) => {
            if (ready && !error) {
                wx.chooseImage({
                    count: 1, // 默认9
                    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
                    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
                    success: function (res) {
                        const localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                        wx.uploadImage({
                            localId: localIds[0], // 需要上传的图片的本地ID，由chooseImage接口获得
                            isShowProgressTips: 1, // 默认为1，显示进度提示
                            success: function (res2) {
                                const serverId = res2.serverId; // 返回图片的服务器端ID
                                var files = [];
                                for (const item of me.state.files) {
                                    files.push(item)
                                }
                                if (window.__wxjs_is_wkwebview) {
                                    wx.getLocalImgData({
                                        localId: localIds[0], // 图片的localID
                                        success: function (res3) {
                                            const localData = res3.localData;// localData是图片的base64数据，可以用img标签显示
                                            files.push({
                                                url: localData,
                                                mediaId: serverId
                                            })
                                            me.setState({ files });
                                        }
                                    });
                                } else {
                                    files.push({
                                        url: localIds[0],
                                        mediaId: serverId
                                    })
                                    me.setState({ files });
                                }
                            }
                        });
                    }
                });
            }
        })
    }

    render() {
        const { getFieldProps, getFieldError } = this.props.form;
        let { title, tableData } = this.store;
        let { initValue } = this.state
        return <div className="TurnoverExamineAddContainer">
            <div className="header colorList">
                <h3>{title}</h3>
            </div>

            <div className="content colorList">
                <form>
                    <List>
                        {
                            tableData.map((v, i) => {
                                let { type, name, label, isSelect, isHide, onChange, defaultValue, required, errorMessage, disabled } = v;
                                let nisHide = isHide ? 'none' : 'block';
                                let nRequired = isHide === true ? false : (required ? true : false);
                                switch (type) {
                                    case 'text'://普通文本  
                                        return <div key={i} className="list" style={{ display: nisHide }}>
                                            <InputItem
                                                className={disabled ? 'noOrangeBorder' : ''}
                                                labelNumber={10}
                                                type={type}
                                                // placeholder = {v.placeholder}
                                                {...getFieldProps(name, {
                                                    initialValue: isSelect ? (initValue[name] === '0' ? '已审批' : '未审批') : defaultValue || initValue[name] || '',
                                                    rules: [
                                                        { required: nRequired || false, message: errorMessage || '' },
                                                    ],
                                                    onChange: onChange
                                                })}
                                                clear
                                                error={!!getFieldError(name)}
                                                onErrorClick={() => {
                                                    Toast.info(getFieldError(name).join('、'));
                                                }}
                                                editable={!disabled}
                                            >{label}：</InputItem>
                                        </div>
                                    case 'date':// 日期
                                        return <div key={i} className="list mydate" style={{ display: nisHide }}>
                                            <DatePicker
                                                mode={v.mode}
                                                disabled={disabled}
                                                {...getFieldProps(name, {
                                                    initialValue: defaultValue || initValue[name] || '',
                                                    rules: [
                                                        { required: nRequired || false, message: errorMessage || '' }
                                                    ],
                                                })}
                                            >
                                                <List.Item arrow="horizontal">{label}：</List.Item>
                                            </DatePicker>
                                        </div>
                                    case 'textarea':// 多行文本
                                        return <div key={i} className="list" style={{ display: nisHide }}>
                                            <div className="textareaHeader" style={{ color: disabled ? '#bbb' : '#000' }}>{label + '：'}</div>
                                            <TextareaItem
                                                className={disabled ? 'noOrangeBorder' : ''}
                                                type={type}
                                                rows={3}
                                                placeholder=""
                                                labelNumber={10}
                                                {...getFieldProps(name, {
                                                    initialValue: defaultValue || initValue[name] || '',
                                                    rules: [
                                                        { required: nRequired || false, message: errorMessage || '' },
                                                    ],
                                                    onChange: onChange
                                                })}
                                                error={!!getFieldError(name)}
                                                onErrorClick={() => {
                                                    Toast.info(getFieldError(name).join('、'));
                                                }}
                                                disabled={disabled}
                                            ></TextareaItem>
                                        </div>
                                    case 'image':// 图片
                                        const { files } = this.state;
                                        return <div key={i} className="list" style={{ display: nisHide }}>
                                            <ImagePicker
                                                accept="image/gif,image/jpeg,image/jpg,image/png"
                                                files={files}
                                                onChange={this.onChangeImages}
                                                selectable={!disabled}
                                                onAddImageClick={this.onAddImageClick}
                                                onImageClick={this.onImageClick}
                                            />
                                        </div>
                                    case 'radio':// 单选
                                        return <div key={i} className="list" style={{ display: nisHide }}>
                                            <Item
                                                disabled={disabled}
                                                extra={<Switch
                                                    disabled={disabled}
                                                    {...getFieldProps(name, {
                                                        initialValue: false,
                                                        valuePropName: 'checked',
                                                    })}
                                                    platform="android"
                                                />}
                                            >{label}：</Item>
                                        </div>
                                    case 'oa':// oa拉人 
                                        return <div key={i} className="list" style={{ display: nisHide }}>
                                            <Picker data={this.state.selectPerson} cols={2} value={this.state.person} onChange={this.onChangePerson}>
                                                <List.Item arrow="horizontal">{label}：</List.Item>
                                            </Picker>
                                        </div>
                                    default:
                                        return <div key={i}><WhiteSpace /><WingBlank>不存在的字段类型：{type}</WingBlank></div>
                                }
                            })
                        }
                    </List>
                </form>

            </div>
            <WingBlank>
                <WhiteSpace /><Button type="primary" onClick={this.sendBtn.bind(this)}>提交</Button><WhiteSpace size='lg' />
            </WingBlank>
        </div>
    }
}

export default createForm()(TurnoverExamineAdd);
