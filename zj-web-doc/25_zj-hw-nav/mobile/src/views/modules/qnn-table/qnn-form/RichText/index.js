import React, { Component } from "react";
import { EditorState, convertToRaw, ContentState } from "draft-js";
import { Editor } from "react-draft-wysiwyg";
import draftToHtml from "draftjs-to-html";
import htmlToDraft from "html-to-draftjs";
import { Toast } from "antd-mobile";
import "./react-draft-wysiwyg.css";
import s from "./style.less";
import $ from "jquery";

class index extends Component {
  constructor(props) {
    super(...props);

    console.assert(props.uploadUrl, "未传入uploadUrl,无法使用图片上传功能");
    console.assert(props.token, "未传入token,无法使用图片上传功能");

    const contentBlock = htmlToDraft(props.value);
    let editorState;
    if (contentBlock) {
      const contentState = ContentState.createFromBlockArray(
        contentBlock.contentBlocks
      );
      editorState = EditorState.createWithContent(contentState);
    }
    this.state = {
      uploadUrl: props.uploadUrl,
      token: props.token,
      editorState: editorState ? editorState : EditorState.createEmpty(),
      value: props.value || "",
      disabled: props.disabled,
      richTabBarOptions: props.richTabBarOptions
    };
  }

  //   componentDidMount() {
  //     console.log(this.props);
  //   }
  onEditorStateChange = editorState => {
    this.setState(
      {
        editorState
      },
      () => {
        let htmlStr = this.getValue();
        if (this.props.onChange) {
          this.props.onChange(htmlStr);
        }
        this.setState({
          value: htmlStr
        });
      }
    );
  };

  getValue = () => {
    const { editorState } = this.state;
    return draftToHtml(convertToRaw(editorState.getCurrentContent()));
  };

  //文件上传
  fileSelected = data => {
    return new Promise(resolve => {
      const _this = this;
      const { uploadUrl, token } = this.state;
      const fieldName = this.state.fieldName;
      var _formData = new FormData();
      _formData.append("myfile", data);
      $.ajax({
        url: uploadUrl,
        type: "post",
        dataType: "json",
        data: _formData,
        beforeSend: function(xhr) {
          xhr.setRequestHeader("token", token);
        },
        xhr: function() {
          var xhr = $.ajaxSettings.xhr();
          if (xhr.upload) {
            xhr.upload.addEventListener(
              "progress",
              function(event) {
                let percent = Math.floor((event.loaded / event.total) * 100);
                if (percent && percent <= 100) {
                  _this.setState({
                    percent
                  });
                }
              },
              false
            );
          }
          return xhr;
        },
        cache: false,
        processData: false, //告诉Jquery不要处理发送的数据
        contentType: false, //告诉Jquery不要去理contenet-type请求头
        success: function({ message, success, data }) {
          if (success && data) {
            resolve(data);
          } else {
            Toast.fail(message);
          }
        }
      });
    });
  };

  uploadCallback = data => {
    return new Promise(resolve => {
      this.fileSelected(data).then(fileInfo => {
        const data = { data: { link: fileInfo.url } };
        resolve(data);
      });
    });
  };

  render() {
    const { editorState, disabled, richTabBarOptions } = this.state;

    return (
      <div>
        <Editor
          editorState={editorState}
          wrapperClassName={s.wrapper}
          editorClassName={s.edit}
          onEditorStateChange={this.onEditorStateChange}
          readOnly={disabled}
          toolbar={{
            options: richTabBarOptions
              ? richTabBarOptions
              : [
                  "inline",
                  "blockType",
                  "fontSize",
                  "fontFamily",
                  "list",
                  "colorPicker",
                  "link",
                  "embedded",
                  "emoji",
                  "image",
                  "textAlign",
                  "remove",
                  "history"
                ],
            embedded: {
              defaultSize: {
                height: "auto",
                width: "100%"
              }
            },
            image: {
              className: undefined,
              component: undefined,
              popupClassName: undefined,
              urlEnabled: true,
              uploadEnabled: true,
              alignmentEnabled: true,
              uploadCallback: this.uploadCallback,
              previewImage: true,
              inputAccept: "image/gif,image/jpeg,image/jpg,image/png,image/svg",
              alt: { present: false, mandatory: false },
              defaultSize: {
                height: "auto",
                width: "100%"
              }
            }
          }}
        />
      </div>
    );
  }
}

export default index;
