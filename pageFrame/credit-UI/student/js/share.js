 var useragent = navigator.userAgent;
	    if (useragent.match(/MicroMessenger/i) != 'MicroMessenger') {
        var opened = window.open('/student/stop.html', '_self');
        opened.opener = null;
        opened.close();
    }

//获取timestamp，nonceStr，signature
$.ajax({
	type: "get",
	url: "/sign",
	async: true,
	data:{
		'url': location.href.split('#')[0]
	},
	success: function(result) {
		wx.config({
	debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	appId: 'wx5bda8c260561016d', // 必填，企业号的唯一标识，此处填写企业号corpid
	timestamp: parseInt(result.timestamp), // 必填，生成签名的时间戳
	nonceStr: result.nonceStr, // 必填，生成签名的随机串
	signature: result.signature, // 必填，签名，见附录1
	jsApiList: [
		'checkJsApi',
		'onMenuShareTimeline',
		'onMenuShareAppMessage',
		'onMenuShareQQ',
		'onMenuShareWeibo',
		'onMenuShareQZone'
	] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
});
wx.ready(function() {
	wx.checkJsApi({
		jsApiList: ['onMenuShareAppMessage'] // 需要检测的JS接口列表，所有JS接口列表见附录2,
	});
	wx.onMenuShareTimeline({
		title: '学分认定首页',
		link: 'http://www.ge.uestc.edu.cn',
		imgUrl: 'http://qydev.weixin.qq.com/wiki/skins/common/images/weixin/weixin_wiki_logo.png', // 分享图标
		success: function() {
			// 用户确认分享后执行的回调函数
		},
		cancel: function() {
			// 用户取消分享后执行的回调函数
		}
	});
	wx.onMenuShareAppMessage({
		title: '学分认定',
		desc: '学分认定首页',
		link: 'http://www.ge.uestc.edu.cn',
		imgUrl: 'http://qydev.weixin.qq.com/wiki/skins/common/images/weixin/weixin_wiki_logo.png',
		trigger: function(res) {
			// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
			//alert('用户点击发送给朋友');
		},
		success: function(res) {
			alert('已分享给朋友');
		},
		cancel: function(res) {
			alert('已取消');
		},
		fail: function(res) {
			alert(JSON.stringify(res));
		}
	});
	wx.onMenuShareQQ({
		title: '学分认定',
		desc: '学分认定首页',
		link: 'http://www.ge.uestc.edu.cn',
		imgUrl: 'http://qydev.weixin.qq.com/wiki/skins/common/images/weixin/weixin_wiki_logo.png',
		success: function() {
			// 用户确认分享后执行的回调函数
			alert('分享成功');
		},
		cancel: function() {
			// 用户取消分享后执行的回调函数
			alert('取消分享');
		}
	});
	wx.onMenuShareWeibo({
		title: '学分认定',
		desc: '学分认定首页',
		link: 'http://www.ge.uestc.edu.cn',
		imgUrl: 'http://qydev.weixin.qq.com/wiki/skins/common/images/weixin/weixin_wiki_logo.png',
		success: function() {
			// 用户确认分享后执行的回调函数
		},
		cancel: function() {
			// 用户取消分享后执行的回调函数
		}
	});
	wx.onMenuShareWeibo({
		title: '学分认定',
		desc: '学分认定首页',
		link: 'http://www.ge.uestc.edu.cn',
		imgUrl: 'http://qydev.weixin.qq.com/wiki/skins/common/images/weixin/weixin_wiki_logo.png',
		success: function() {
			// 用户确认分享后执行的回调函数
		},
		cancel: function() {
			// 用户取消分享后执行的回调函数
		}
	});
	wx.onMenuShareQZone({
		title: '学分认定',
		desc: '学分认定首页',
		link: 'http://www.ge.uestc.edu.cn',
		imgUrl: 'http://qydev.weixin.qq.com/wiki/skins/common/images/weixin/weixin_wiki_logo.png',
		success: function() {
			// 用户确认分享后执行的回调函数
		},
		cancel: function() {
			// 用户取消分享后执行的回调函数
		}
	})
})
	},
});