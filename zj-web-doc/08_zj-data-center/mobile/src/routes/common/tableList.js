import React, { PureComponent, Component } from 'react';
class TableRender extends Component {
    render() {
        let { tableData } = this.props.tableData
        return <form>
            <List>
                {
                    tableData.map((v, i) => {
                        let { type, name, label, isHide, onChange, defaultValue, required, errorMessage, disabled } = v;
                        let nisHide = isHide ? 'none' : 'block'
                        let nRequired = isHide === true ? false : (required ? true : false);
                        switch (type) {
                            case 'text'://普通文本
                                return <div key={i} className="list" style={{ display: nisHide }}>
                                    <InputItem
                                        labelNumber={10}
                                        type={type}
                                        {...getFieldProps(name, {
                                            initialValue: defaultValue || '',
                                            rules: [
                                                { required: nRequired || false, message: errorMessage || '' },
                                            ],
                                            onChange: onChange
                                        }) }
                                        clear
                                        error={!!getFieldError(name)}
                                        onErrorClick={() => {
                                            Toast.info(getFieldError(name).join('、'));
                                        }}
                                        disabled={disabled}
                                    >{label}:</InputItem>
                                </div>
                            case 'date':// 日期
                                return <div key={i} className="list" style={{ display: nisHide }}>
                                    <InputItem
                                        labelNumber={10}
                                        type={type}
                                        {...getFieldProps(name, {
                                            initialValue: defaultValue || '',
                                            rules: [
                                                { required: nRequired || false, message: errorMessage || '' },
                                            ],
                                            onChange: onChange
                                        }) }
                                        clear
                                        error={!!getFieldError(name)}
                                        onErrorClick={() => {
                                            alert(getFieldError(name).join('、'));
                                        }}
                                        disabled={disabled}
                                    >{label}:</InputItem>
                                </div>
                            case 'textarea':// 多行文本
                                return <div key={i} className="list" style={{ display: nisHide }}>
                                    <TextareaItem
                                        type={type}
                                        rows={3}
                                        labelNumber={10}
                                        title={label + ':'}
                                        {...getFieldProps(name, {
                                            initialValue: defaultValue || '',
                                            rules: [
                                                { required: nRequired || false, message: errorMessage || '' },
                                            ],
                                            onChange: onChange
                                        }) }
                                        clear
                                        error={!!getFieldError(name)}
                                        onErrorClick={() => {
                                            alert(getFieldError(name).join('、'));
                                        }}
                                        disabled={disabled}
                                    ></TextareaItem>
                                </div>
                            case 'image':// 图片
                                const { files } = this.state;
                                return <div key={i} className="list" style={{ display: nisHide }}>
                                    <ImagePicker
                                        files={files}
                                        onChange={this.onChangeImg}
                                        onImageClick={(index, fs) => console.log(index, fs[index])}
                                        selectable={files.length < 5}
                                    />
                                </div>
                            case 'oa':// oa拉人 
                                return <div key={i} className="list" style={{ display: nisHide }}>
                                    <Picker data={this.state.selectPerson} cols={2} value={this.state.person} onChange={this.onChangePerson}>
                                        <List.Item arrow="horizontal">指定人：</List.Item>
                                    </Picker>
                                </div>
                            default:
                                return <div key={i}><WhiteSpace /><WingBlank>不存在的字段类型：{type}</WingBlank></div>
                        }
                    })
                }
            </List>
        </form>
    }
}

export default TableRender;