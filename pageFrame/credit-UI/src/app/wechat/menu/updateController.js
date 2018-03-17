'use strict';

    angular.module('app')
        .controller('wechatMenuUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
                                             function($scope, $rootScope, $state, $timeout, toaster) {
        var title = "";
        var defaultAva = $rootScope.defaultAvatar;
        $scope.myImage='';
        // $scope.myCroppedImage=$scope.myCroppedImage ;
        $scope.myCroppedImage = '';
        if($state.includes('**.menu.update')){
            title="编辑菜单";
            var id = $state.params.id;
            activate(id);
            validate(id);
        }else if($state.includes('**.menu.create')){
            title="添加菜单";
            activate(0);
            validate(null);
            setTimeout(function(){
                $scope.myCroppedImage = defaultAva;
                !$rootScope.$$phase && $scope.$apply();
            },300);

        }
        $scope.title = $rootScope.title = title;
        $scope.loading = true;
        //初始化验证
        //validate($scope);
        $scope.submit= function(){
            var m = $scope.record;
            if(m){
                $scope.isDisabled = true;//提交disabled
                $.ajax({
    				url : $scope.record.id ? '/wechat/menu/update' : '/wechat/menu/add',
    				data: $scope.record
    			}).then(callback);
            }
            function callback(result) {
                if(result.httpCode ==200){//成功
                    toaster.clear('*');
                    toaster.pop('success', '', "保存成功");
                    $timeout(function(){
                        $state.go('main.wechat.menu.list');
                    },2000);
                }else{
                    toaster.clear('*');
                    toaster.pop('error', '', result.msg);
                    $scope.isDisabled = false;
                }
            }
        };
        
        $("#type").change(function(){
        	var type = $("#type").val();
        	if(type.indexOf(":")!=-1){
        		type = type.split(":")[1];
        	}
        	if(type=="history"){
        		$('#url').removeAttr("readonly");
        		$("#url").val("http://mp.weixin.qq.com/mp/getmasssendmsg?__biz=MzI0MTM0OTM1Mw==#wechat_webview_type=1&wechat_redirect");
        		$('#url').attr("readonly","readonly");
        	}else{
        		$('#url').removeAttr("readonly");
        		if($("#url").val()=="http://mp.weixin.qq.com/mp/getmasssendmsg?__biz=MzI0MTM0OTM1Mw==#wechat_webview_type=1&wechat_redirect"){
        			$("#url").val("");
        		}
        	}
        });

        // 初始化页面
        function activate(id) {
	        $scope.loading = true;
        	$.ajax({
				url : '/wechat/menu/read/detail',
				data: {'id': id}
			}).then(function(result) {
		        $scope.loading = false;
				if (result.httpCode == 200) {
					$scope.record = result.data.record;
					$scope.fatherWechatMenus = result.data.fatherWechatMenus;
				} else {
					$scope.msg = result.msg;
				}
				$scope.$apply();
			});
        }

        //表单验证
        function validate(userId){
            jQuery('form').validate({
                rules: {
                	name: {
                        required: true,
                        stringCheck:[],
                        maxlength:8
                    },
                    sort: {
                        required: true
                    }
                },
                messages: {
                	name: {
                        required: '请填写菜单名称',
                        maxlength:"菜单名称不得超过{0}个字符"
                    },
                    sort: {
                        required: '请填写排序'
                    }
                },
                submitHandler: function() {
                    $scope.submit();
                }
            });
        }
    }]);