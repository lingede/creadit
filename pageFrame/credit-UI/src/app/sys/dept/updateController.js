'use strict';

    angular.module('app')
        .controller('deptUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
                                             function($scope, $rootScope, $state, $timeout, toaster) {
        var title = "";
        if($state.includes('**.dept.update')){
            title="编辑部门";
            var id = $state.params.id;
            activate(id);
            validate(id);
        }else if($state.includes('**.dept.create')){
            title="添加部门";
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
    				url : $scope.record.id ? '/dept/update' : 'dept/add',
    				data: $scope.record
    			}).then(callback);
            }
            function callback(result) {
                if(result.httpCode ==200){//成功
                    toaster.clear('*');
                    toaster.pop('success', '', "保存成功");
                    $timeout(function(){
                        $state.go('main.sys.dept.list');
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
				url : '/dept/read/detail',
				data: {'id': id}
			}).then(function(result) {
		        $scope.loading = false;
				if (result.httpCode == 200) {
                    $scope.record = result.data.record;
                    $scope.fatherSysDepts = result.data.fatherSysDepts;
				} else {
					$scope.msg = result.msg;
				}
				$scope.$apply();
			});
        }

        //表单验证
        function validate(deptId){
            jQuery('form').validate({
                rules: {
                	deptName: {
                        required: true,
                        maxlength:20,
                        isExist:["/dept/checkName",deptId]
                    },
                    sortNo: {
                        required: true
                    }
                    
                },
                messages: {
                	deptName: {
                        required: '请填写部门名称',
                        maxlength:"部门名称不得超过{0}个字符",
                        isExist:"该部门已存在"
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
    }]);