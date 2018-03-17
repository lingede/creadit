'use strict';

    angular.module('app')
        .controller('deptUserUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster', 
                                             function($scope, $rootScope, $state, $timeout ,toaster) {
        var title = "";
        if($state.includes('**.deptuser.update')){
            title="编辑用户";
            var id = $state.params.id;
            activate(id);
            validate(id);
        }else if($state.includes('**.deptuser.create')){
            title="添加用户";
            validate(null);
            setTimeout(function(){
                !$rootScope.$$phase && $scope.$apply();
            },300);

        }
        $scope.title = $rootScope.title = title;
        $scope.loading = true;
        $scope.submit= function(){
        	$scope.loading = true;
        	var m = $scope.record;
        	$scope.record.deptId = $rootScope.userInfo.deptId;
            if(m){
                $scope.isDisabled = true;//提交disabled
                $.ajax({
    				url : $scope.record.id ? '/deptuser/update' : 'deptuser/add',
    				data: $scope.record
    			}).then(callback);
            }
            function callback(result){
                if(result.httpCode ==200){//成功
                    toaster.clear('*');
                    toaster.pop('success', '', "保存成功");
                    $timeout(function(){
                        $state.go('main.platform.deptuser.list');
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
				url : '/user/read/detail',
				data: {'id': id}
			}).then(function(result) {
		        $scope.loading = false;
				if (result.httpCode == 200) {
					$scope.record = result.data.record;
					if(result.data.pBasicDeptBeans!=undefined&&result.data.pBasicDeptBeans.length>0){
						for(var i=0;i<result.data.pBasicDeptBeans.length; i++){
							if(result.data.pBasicDeptBeans[i].id==null||result.data.pBasicDeptBeans[i].id=="null"){
								//添加页面默认值为空
								result.data.pBasicDeptBeans[i].id = undefined;
							}
		                }
					}
					$scope.pBasicDeptBeans = result.data.pBasicDeptBeans;
				} else {
					$scope.msg = result.msg;
				}
				$scope.$apply();
	            $timeout(function(){
	            	if($scope.record != null){
	            		 $scope.myCroppedImage=$scope.record.avatar;
	            	}else{
	            		$scope.myCroppedImage=defaultAva;
	            	}
	            },300);
			});
        }


      //表单验证
        function validate(userId){
            //notEqual 规则
            $.validator.addMethod('notEqual', function(value, ele){
                return value != this.settings.rules[ele.name].notEqual;
            });
            jQuery('form').validate({
            	onkeyup : function(element){
            		if($(element).attr("name")!="account"){
            			$(element).valid();
            		}
            	},
                rules: {
                    account: {
                        required: true,
                        stringCheck:[],
                        maxlength:10,
                        isExist:["/user/checkName",userId]
                    },
                    userName: {
                        required: true
                    },
                    locked:{
                    	selected:[]
                    }
                },
                messages: {
                    account: {
                        required: '请填写帐号',
                        maxlength:"帐号不得超过{0}个字符",
                        isExist:"该帐号已存在"
                    },
                    userName: {
                        required: '请填写用户名'
                    },
                    locked:{
                    	selected:"请选择激活状态"
                    }
                },
                submitHandler: function() {
                    $scope.submit();
                }
            });
        }

    }]);