'use strict';

    angular.module('app')
        .controller('menuUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
                                             function($scope, $rootScope, $state, $timeout, toaster) {
        var title = "";
        if($state.includes('**.menu.update')){
            title="编辑菜单";
            var id = $state.params.id;
            activate(id);
            validate(id);
        }else if($state.includes('**.menu.create')){
            title="添加菜单";
            activate(0);
            validate(null);
        }
        $scope.title = $rootScope.title = title;
        $scope.loading = true;
        $scope.submit= function(){
            var m = $scope.record;
            if(m){
                $scope.isDisabled = true;//提交disabled
                $.ajax({
    				url : $scope.record.id ? '/menu/update' : 'menu/add',
    				data: $scope.record
    			}).then(callback);
            }
            function callback(result) {
                if(result.httpCode ==200){//成功
                    toaster.clear('*');
                    toaster.pop('success', '', "保存成功");
                    $timeout(function(){
                        $state.go('main.sys.menu.list');
                    },2000);
                }else{
                    toaster.clear('*');
                    toaster.pop('error', '', result.msg);
                    $scope.isDisabled = false;
                }
            }
        };

        // 初始化页面
        function activate(id) {
	        $scope.loading = true;
        	$.ajax({
				url : '/menu/read/detail',
				data: {'id': id}
			}).then(function(result) {
		        $scope.loading = false;
				if (result.httpCode == 200) {
					$scope.record = result.data.record;
					$scope.fatherSysMenus = result.data.fatherSysMenus;
				} else {
					$scope.msg = result.msg;
				}
				$scope.$apply();
			});
        }

        //表单验证
        function validate(menuId){
            jQuery('form').validate({
                rules: {
                	menuName: {
                        required: true,
                        maxlength:50,
                        isExist:["/menu/checkName",menuId]
                    },
                    sortNo: {
                        required: true
                    }
                },
                messages: {
                	menuName: {
                        required: '请填写菜单名称',
                        maxlength:"菜单名称不得超过{0}个字符",
                        isExist:"该菜单已存在"
                    },
                    sortNo: {
                        required: '请填写排序'
                    }
                },
                submitHandler: function() {
                    $scope.submit();
                }
            });
        }
        
        //菜单图标样式选择方法注册
        $scope.selectIconCls = function(){
        	if($("#glyphicons_selector").is(":visible")){
        		$("#glyphicons_selector").hide();
        	}else{
        		$("#glyphicons_selector").show();
        	}
        	
        };
        
        //菜单图标样式选择点击
        $(".bs-glyphicons li").click(function(){
        	$("#iconcls").val($(this).find("span").eq(0).attr("class"));
        	$scope.record.iconcls = $(this).find("span").eq(0).attr("class");
        	$("#glyphicons_selector").hide();
        });

    }]);