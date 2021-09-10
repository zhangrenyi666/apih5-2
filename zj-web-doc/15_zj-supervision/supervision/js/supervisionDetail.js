var saved = true;
var supervisionId = l.getUrlParam('supervisionId');

l.ajax('getZjDdSupervisionDetail', { supervisionId: supervisionId }, function (data, message, success) {
	if (success) {
		var tabBar = '<div class="tabBar cl">', tabCons = "";
		for (var index = 0; index < data.length; index++) {
			var item = data[index];
			tabBar = tabBar + '<span>' + item.companyName + '</span>'
			tabCons = tabCons + '<div class="tabCon"><div id="company' + item.supervisionCompanyId + '"></div></div>'
		}
		tabBar = tabBar + '</div>'
		$("#tab-system").html(tabBar + tabCons)
		for (var index = 0; index < data.length; index++) {
			var item = data[index];
			var companyForm = $('#company' + item.supervisionCompanyId).detailLayer({
				conditions: [
					{
						type: "hidden",//五种形式：text,select,date,hidden,textarea,
						name: "supervisionCompanyId",//输入字段名
					},
					{
						type: "text",//
						name: "title",//
						label: "标题",//
						placeholder: "请输入内容",
						immutableAdd: true
					},
					{
						type: "text",//
						name: "content",//
						label: "内容",//
						placeholder: "请输入内容",
						immutableAdd: true
					},
					{
						type: "upload",//
						name: "fileList1",//
						label: "附件",//
						btnName: "添加附件",
						projectName: "zj-supervision",
						uploadUrl:"uploadFiles2",
						maxLen: 1,
						fileType: ["docx"]
					},
					{
						type: "pickTree",
						name: "memberInfo1",
						label: "人员选择",
						pickType: {
							department: false,//部门列表对应接口字段名,false表示不开启该类
							member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
						}
					}
				],
				onSave: function (obj, _params) {
					l.ajax('addZjDdSupervisionDetail', _params, function (data, message, success) {
						if (success) {
							obj.close()
							parent.pager.page('remote')
						}
					})


				}
			})
			companyForm.close = function () {
				parent.layer.close(parent.myOpenLayer)
			}
			companyForm.setOpenData(item)


		}
		$("#tab-system").Huitab({
			index: 0
		});


	} else {
		layer.alert(message, { icon: 0 }, function (index) {
			layer.close(index);
		});
	}
})



