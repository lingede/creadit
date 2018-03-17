'use strict';

//修改角色权限
angular.module('app')
    .controller('userRoleUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
                                         function($scope, $rootScope, $state, $timeout, toaster) {
    var title = "编辑用户角色";
    var id = $state.params.id;
    activate(id);
    validate();
    $scope.title = $rootScope.title = title;
    $scope.loading = true;
    $scope.submit= function(){
        $scope.isDisabled = true;//提交disabled
        $.ajax({
			url : '/user/role/update',
			data: {'userId':id,'roleId': $scope.sysRole_record.id}
		}).then(callback);
        function callback(result) {
            if(result.httpCode ==200){//成功
                toaster.clear('*');
                toaster.pop('success', '', "保存成功");
                $timeout(function(){
                    $state.go('main.sys.user.list');
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
			url : '/user/role/read/detail',
			data: {'id': id}
		}).then(function(result) {
	        $scope.loading = false;
			if (result.httpCode == 200) {
				$scope.sysRoles = result.data.sysRoles;
				$scope.sysRole_record = result.data.sysRole_record;
			} else {
				$scope.msg = result.msg;
			}
			$scope.$apply();
		});
    }
    
    //表单验证
    function validate(){
        jQuery('form').validate({
            rules: {
                roleId:{
                	selected:[]
                }
            },
            messages: {
            	roleId:{
                	selected:"请选择角色"
                }
            },
            submitHandler: function() {
                $scope.submit();
            }
        });
    }
}]);