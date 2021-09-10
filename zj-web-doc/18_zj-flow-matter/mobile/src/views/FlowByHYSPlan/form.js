import { Form } from "../modules/work-flow";
import React, { Component } from "react";
import s from './style.less';
const config = {
  //流程专属配置  --qnn-table中配了这也得配置
  workFlowConfig: {
    //流程特有配置
    title: ["meetingRoomTitle"], //标题字段
    apiNameByUpdate: "updateZjMeetingReservationsFlow",
    apiNameByGet: "getZjMeetingApplyFlowDetailByWorkId",
    flowId: "ZjMeetingPlanRoom",
    formLink: {
      // zjPartyFeeUse: "FlowByzjPartyFeeUse",
      // zjYongYin: "FlowByzjYongYin",
      // zjYyOutSeal: "FlowByzjYyOutSeal",
      ZjMeetingPlanRoom: "FlowByZjMeetingPlanRoom"
    },
    //待办已办切换路由
    todo: "todoByZjMeetingPlanRoom",
    hasTodo: "hasTodoByZjMeetingPlanRoom"
  }
};
class index extends Component {
  render() {
    return (
      <div className={s.root}>
        <Form
          {...this.props}
          {...config}
          {...this.props.workFlowConfig}
          {...config.workFlowConfig}
          formConfig={window.configs.appInfo.mainModule === '/flow-matter-mobile/' ? [
            {
              type: "string",
              label: "申请时间",
              field: "createTime",
              placeholder: "请输入",
              hide: true
            },
            {
              type: "string",
              label: "workId",
              field: "workId",
              hide: true,
              initialValue: function (obj) {
                return obj.match.params["workId"];
              }
            },
            {
              type: "string",
              label: "会议名称",
              field: "meetingRoomTitle",
              placeholder: "无",
            },
            {
              type: "datetime",
              label: "会议开始日期",
              field: "meetingStartTime",
              placeholder: "无",
            },
            {
              type: "datetime",
              label: "会议结束日期",
              field: "meetingEndTime",
              placeholder: "无"
            },
            {
              type: "string",
              label: "申请单位",
              field: "applyUnit",
              placeholder: "无"
            },
            {
              type: "string",
              label: "会议室摆设",
              field: "meetingDecoration",
              placeholder: "无"
            },
            {
              type: "string",
              label: "会议形式",
              field: "meetingForm",
              placeholder: "无"
            },
            {
              type: "datetime",
              label: "调试时间",
              field: "debuggingTime",
              placeholder: "无"
            },
            {
              type: "string",
              label: "会议室",
              field: "meetingRoomName",
              placeholder: "无"
            },
            {
              type: "string",
              label: "参会人数",
              field: "meetingPeopleNumber",
              placeholder: "无"
            },
            {
              type: "string",
              label: "参会(主会场)",
              field: "joinUnitHardVideo",
              placeholder: "无"
            },
            {
              type: "string",
              label: "参会(软视频)",
              field: "joinUnitSoftVideo",
              placeholder: "无"
            },
            {
              type: "string",
              label: "会议大堂屏幕",
              field: "meetingLobbyScreen",
              placeholder: "无"
            },
            {
              type: "string",
              label: "主席台麦克(只)",
              field: "rostrumMike",
              placeholder: "无",
              span:12,
            },
            {
              type: "string",
              label: "移动麦克风(只)",
              field: "moveMike",
              placeholder: "无",
              span:12,
            },
            {
              type: "select",
              label: "投影仪",
              field: "projector",
              placeholder: "无",
              span:12,
              optionData: [
                //默认选项数据
                {
                  name: "是",
                  id: "0"
                },
                {
                  name: "否",
                  id: "1"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "select",
              label: "录音",
              field: "soundRecording",
              placeholder: "无",
              span:12,
              optionData: [
                //默认选项数据
                {
                  name: "是",
                  id: "0"
                },
                {
                  name: "否",
                  id: "1"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "select",
              label: "录像",
              field: "videotape",
              placeholder: "无",
              span:12,
              optionData: [
                //默认选项数据
                {
                  name: "是",
                  id: "0"
                },
                {
                  name: "否",
                  id: "1"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "select",
              label: "音乐",
              field: "music",
              placeholder: "无",
              span:12,
              optionData: [
                //默认选项数据
                {
                  name: "是",
                  id: "0"
                },
                {
                  name: "否",
                  id: "1"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "select",
              label: "音乐",
              field: "music",
              placeholder: "无",
              span:12,
              optionData: [
                //默认选项数据
                {
                  name: "是",
                  id: "0"
                },
                {
                  name: "否",
                  id: "1"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "select",
              label: "PPT",
              field: "pPT",
              placeholder: "无",
              span:12,
              optionData: [
                //默认选项数据
                {
                  name: "是",
                  id: "0"
                },
                {
                  name: "否",
                  id: "1"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "select",
              label: "桌牌",
              field: "tableCard",
              placeholder: "无",
              span:12,
              optionData: [
                //默认选项数据
                {
                  name: "是",
                  id: "0"
                },
                {
                  name: "否",
                  id: "1"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "select",
              label: "茶水",
              field: "teaWater",
              placeholder: "无",
              span:12,
              optionData: [
                //默认选项数据
                {
                  name: "是",
                  id: "0"
                },
                {
                  name: "否",
                  id: "1"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "select",
              label: "讲台",
              field: "platform",
              placeholder: "无",
              span:12,
              optionData: [
                //默认选项数据
                {
                  name: "是",
                  id: "0"
                },
                {
                  name: "否",
                  id: "1"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "select",
              label: "视频会议",
              field: "vdeoconferencing",
              placeholder: "无",
              span:12,
              optionData: [
                //默认选项数据
                {
                  name: "是",
                  id: "0"
                },
                {
                  name: "否",
                  id: "1"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "select",
              label: "人工服务",
              field: "artificialServices",
              placeholder: "无",
              span:12,
              optionData: [
                //默认选项数据
                {
                  name: "是",
                  id: "0"
                },
                {
                  name: "否",
                  id: "1"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "select",
              label: "指示牌",
              field: "indicator",
              placeholder: "无",
              span:12,
              optionData: [
                //默认选项数据
                {
                  name: "是",
                  id: "0"
                },
                {
                  name: "否",
                  id: "1"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "string",
              label: "软视频参会单位数量",
              field: "softVideoParticipantsNum",
              placeholder: "无"
            },
            {
              type: "select",
              label: "直播需求",
              field: "liveDemand",
              placeholder: "无",
              optionData: [
                //默认选项数据
                {
                  name: "无需直播",
                  id: "0"
                },
                {
                  name: "交建通直播",
                  id: "1"
                },
                {
                  name: "好视通直播",
                  id: "2"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: "id" //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "textarea",
              label: "发言单位、发言顺序",
              field: "speakerSpeechOrder",
              placeholder: "无"
            },
            {
              type: "string",
              label: "单位联系人",
              field: "applyUnitContacts",
              placeholder: "无"
            },
            {
              type: "string",
              label: "联系方式",
              field: "applyUnitContactsWay",
              placeholder: "无"
            },
            {
              type: "textarea",
              label: "流程及需要",
              field: "meetingSpecialNeeds",
              placeholder: "无"
            },
            {
              type: "textarea",
              label: "公司办公室",
              field: "opinionField2",
              placeholder:'请输入...'
            },
            {
              type: "textarea",
              label: "部门领导意见",
              field: "opinionField3",
              placeholder:'请输入...'
            },
            {
              type: "textarea",
              label: "主要领导意见",
              field: "opinionField4",
              placeholder:'请输入...'
            }
          ] : [
            {
              type: "string",
              label: "申请时间",
              field: "createTime",
              placeholder: "请输入",
              hide: true
            },
            {
              type: "string",
              label: "workId",
              field: "workId",
              hide: true,
              initialValue: function (obj) {
                return obj.match.params["workId"];
              }
            },
            {
              type: "string",
              label: "会议名称",
              field: "meetingRoomTitle",
              placeholder: "无",
            },
            {
              type: "datetime",
              label: "会议开始日期",
              field: "meetingStartTime",
              placeholder: "无",
            },
            {
              type: "datetime",
              label: "会议结束日期",
              field: "meetingEndTime",
              placeholder: "无"
            },
            {
              type: "string",
              label: "申请单位",
              field: "applyUnit",
              placeholder: "无"
            },
            {
              type: "string",
              label: "会议形式",
              field: "meetingForm",
              placeholder: "无"
            },
            {
              type: "datetime",
              label: "调试时间",
              field: "debuggingTime",
              placeholder: "无"
            },
            {
              type: "string",
              label: "会议室",
              field: "meetingRoomName",
              placeholder: "无"
            },
            {
              type: "string",
              label: "参会人数",
              field: "meetingPeopleNumber",
              placeholder: "无"
            },
            {
              type: "string",
              label: "主会场",
              field: "joinUnitHardVideo",
              placeholder: "无"
            },
            {
              type: "string",
              label: "视频会场",
              field: "joinUnitSoftVideo",
              placeholder: "无"
            },
            {
              type: "string",
              label: "会议大堂屏幕",
              field: "meetingLobbyScreen",
              placeholder: "无"
            },
            {
              type: "string",
              label: "主席台麦克(只)",
              field: "rostrumMike",
              placeholder: "无",
              span: 12,
            },
            {
              type: "select",
              label: "录音",
              field: "soundRecording",
              placeholder: "无",
              span: 12,
              optionData: [
                //默认选项数据
                {
                  name: "是",
                  id: "0"
                },
                {
                  name: "否",
                  id: "1"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "select",
              label: "录像",
              field: "videotape",
              placeholder: "无",
              span: 12,
              optionData: [
                //默认选项数据
                {
                  name: "是",
                  id: "0"
                },
                {
                  name: "否",
                  id: "1"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "select",
              label: "音乐",
              field: "music",
              placeholder: "无",
              span: 12,
              optionData: [
                //默认选项数据
                {
                  name: "是",
                  id: "0"
                },
                {
                  name: "否",
                  id: "1"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "select",
              label: "PPT",
              field: "pPT",
              placeholder: "无",
              span: 12,
              optionData: [
                //默认选项数据
                {
                  name: "是",
                  id: "0"
                },
                {
                  name: "否",
                  id: "1"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "select",
              label: "桌牌",
              field: "tableCard",
              placeholder: "无",
              span: 12,
              optionData: [
                //默认选项数据
                {
                  name: "是",
                  id: "0"
                },
                {
                  name: "否",
                  id: "1"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "select",
              label: "茶水",
              field: "teaWater",
              placeholder: "无",
              span: 12,
              optionData: [
                //默认选项数据
                {
                  name: "是",
                  id: "0"
                },
                {
                  name: "否",
                  id: "1"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "select",
              label: "讲台",
              field: "platform",
              placeholder: "无",
              span: 12,
              optionData: [
                //默认选项数据
                {
                  name: "是",
                  id: "0"
                },
                {
                  name: "否",
                  id: "1"
                }
              ],
              optionConfig: {
                //下拉选项配置
                label: "name", //默认 label
                value: ["id"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
              }
            },
            {
              type: "string",
              label: "单位联系人",
              field: "applyUnitContacts",
              placeholder: "无"
            },
            {
              type: "string",
              label: "联系方式",
              field: "applyUnitContactsWay",
              placeholder: "无"
            },
            {
              type: "textarea",
              label: "流程及需要",
              field: "meetingSpecialNeeds",
              placeholder: "无"
            },
            {
              type: "textarea",
              label: "单位负责人意见",
              field: "opinionField1",
              placeholder: '请输入...'
            },
            {
              type: "textarea",
              label: "公司分管领导",
              field: "opinionField6",
              placeholder: '请输入...'
            },
            {
              type: "textarea",
              label: "公司办公室意见",
              field: "opinionField2",
              placeholder: '请输入...'
            },
            {
              type: "textarea",
              label: "办公室主任意见",
              field: "opinionField3",
              placeholder: '请输入...'
            },
            {
              type: "textarea",
              label: "主要领导意见",
              field: "opinionField4",
              placeholder: '请输入...'
            }
          ]}
        />
      </div>
    );
  }
}
export default index;
