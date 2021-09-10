import React, { Component } from "react";
import { Comment, Avatar, Form, Button, List, Input } from 'antd';
import moment from 'moment';

const { TextArea } = Input;

const CommentList = ({ comments }) => (
    <List
        dataSource={comments}
        header={`共 ${comments.length} 条回复`}
        itemLayout="horizontal"
        renderItem={props => <Comment {...props} />}
    />
);

const Editor = ({ onChange, onSubmit, submitting, value }) => (
    <>
        <Form.Item>
            <TextArea rows={2} onChange={onChange} value={value} />
        </Form.Item>
        <Form.Item>
            <Button htmlType="submit" loading={submitting} onClick={onSubmit} type="primary">
                发布
      </Button>
        </Form.Item>
    </>
);

class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            comments: [],
            submitting: false,
            value: ''
        }
    }
    componentDidMount() {
        this.queryCommentDataFunc()
    }

    queryCommentDataFunc = () => {
        this.props.propVal.myFetch('getZjLzehTempTaskCommunicateList', {
            zjLzehTempTaskManageId: this.props.taskManageId
        }).then(({ success, data }) => {
            const list = []
            data.map(item => {
                list.push({
                    author: item.createUserName,
                    content: <p>{item.messageContent}</p>,
                    datetime: moment(item.createTime).format("YYYY-MM-DD h:mm:ss")
                })
            })
            this.setState({
                comments: list,
                submitting: false,
                value: '',
            })
        })
    }

    handleSubmit = () => {
        if (!this.state.value) {
            return;
        }

        this.setState({
            submitting: true,
        });
        setTimeout(() => {
            this.props.propVal.myFetch('addZjLzehTempTaskCommunicate', {
                zjLzehTempTaskManageId: this.props.taskManageId,
                sendPersonId: this.props.currentLogin.userId,
                sendPerson: this.props.currentLogin.realName,
                messageContent: this.state.value
            }).then(({ success, message, data }) => {
                this.queryCommentDataFunc()
            })
        }, 0);
    };

    handleChange = e => {
        this.setState({
            value: e.target.value,
        });
    };

    render() {
        const { comments, submitting, value } = this.state;

        return (
            <>
                {comments.length > 0 && <CommentList comments={comments} />}
                <Comment
                    content={
                        <Editor
                            onChange={this.handleChange}
                            onSubmit={this.handleSubmit}
                            submitting={submitting}
                            value={value}
                        />
                    }
                />
            </>
        );
    }
}
export default index