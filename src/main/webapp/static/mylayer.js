var mylayer = {
	alert : function(content) {
		layer.alert(
			content,
			{icon : 2}
		);
	},
	confirm : function(content, url) {
		layer.confirm(
			content,
			{icon : 3},
			function(index){//点击确认触发
				//console.log("点击了确认");
				location.href = url;
				layer.close(index);
			},
			function(index) {//点击取消触发
				//console.log("点击了取消");
			}
		);
	},
	// 表达成功的消息
	okMsg : function(content) {
		layer.msg(
			content,
			{icon : 1, time : 1500}
		);
	},
	// 表达失败的消息
	errorMsg : function(content) {
		layer.msg(
			content,
			{icon : 2, time : 1500}
		);
	},
	// 表达成功的msg之后,自动发送一个请求
	okUrl : function(content, url) {
		layer.msg(
			content,
			{icon : 1, time : 1500},
			function() {// msg消失之后触发的函数
				location.href = url;
			}
		);
	}
}