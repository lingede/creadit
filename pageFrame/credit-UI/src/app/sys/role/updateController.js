'use strict';

    angular.module('app')
        .controller('roleUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
                                             function($scope, $rootScope, $state, $timeout, toaster) {
        var title = "";
        if($state.includes('**.role.update')){
            title="编辑角色";
            var id = $state.params.id;
            activate(id);
            validate(id);
        }else if($state.includes('**.role.create')){
            title="添加角色";
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
    				url : $scope.record.id ? '/role/update' : 'role/add',
    				data: $scope.record
    			}).then(callback);
            }
            function callback(result) {
                if(result.httpCode ==200){//成功
                    toaster.clear('*');
                    toaster.pop('success', '', "保存成功");
                    $timeout(function(){
                        $state.go('main.sys.role.list');
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
				url : '/role/read/detail',
				data: {'id': id}
			}).then(function(result) {
		        $scope.loading = false;
				if (result.httpCode == 200) {
					$scope.record = result.data.record;
					$scope.sysDeptBeans = result.data.sysDeptBeans;
				} else {
					$scope.msg = result.msg;
				}
				$scope.$apply();
			});
        }

        //表单验证
        function validate(roleId){
            jQuery('form').validate({
                rules: {
                	roleName: {
                        required: true,
                        stringCheck:[],
                        maxlength:20,
                        isExist:["/role/checkName",roleId]
                    },
                    roleType: {
                    	selected:[]
                    },
                    deptId: {
                    	selected:[]
                    }
                },
                messages: {
                	roleName: {
                        required: '请填写角色名称',
                        maxlength:"部门名称不得超过{0}个字符",
                        isExist:"该角色已存在"
                    },
                    roleType: {
                    	selected: '请选择角色类型'
                    },
                    deptId: {
                    	selected: '请选择部门'
                    }
                },
                submitHandler: function() {
                    $scope.submit();
                }
            });
        }

    }]);