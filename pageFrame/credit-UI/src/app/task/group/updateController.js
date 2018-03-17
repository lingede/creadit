'use strict';

    angular.module('app')
        .controller('groupUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
                                             function($scope, $rootScope, $state, $timeout, toaster) {
        var title = "";
        if($state.includes('**.group.update')){
            title="编辑任务组";
            var id = $state.params.id;
            activate(id);
            validate(id);
        }else if($state.includes('**.group.create')){
            title="添加任务组";
            validate(null);
            setTimeout(function(){
                $scope.myCroppedImage = defaultAva;
                !$rootScope.$$phase && $scope.$apply();
            },300);

        }
        $scope.title = $rootScope.title = title;
        $scope.loading = true;
        $scope.submit= function(){
            var m = $scope.record;
            if(m){
                $scope.isDisabled = true;//提交disabled
                $.ajax({
    				url : $scope.record.id ? '/task/update/group' : '/task/add/group',
    				data: $scope.record
    			}).then(callback);
            }
            function callback(result) {
                if(result.httpCode ==200){//成功
                    toaster.clear('*');
                    toaster.pop('success', '', "保存成功");
                    $timeout(function(){
                        $state.go('main.task.group.list');
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
				url : '/task/read/group',
				data: {'id': id}
			}).then(function(result) {
		        $scope.loading = false;
				if (result.httpCode == 200) {
					$scope.record = result.data;
				} else {
					$scope.msg = result.msg;
				}
				$scope.$apply();
			});
        }

        //表单验证
        function validate(taskGroupId){
            jQuery('form').validate({
                rules: {
                	groupName: {
                        required: true,
                        maxlength:50,
                        isExist:["/task/update/group/checkName",taskGroupId]
                    },
                    groupDesc: {
                        required: true,
                        maxlength:50
                    }
                },
                messages: {
                	groupName: {
                        required: '请填写任务组',
                        maxlength:"任务组不得超过{0}个字符",
                        isExist:"该任务组已存在"
                    },
                    groupDesc: {
                        required: '请填描述',
                        maxlength:"描述不得超过{0}个字符"
                    }
                },
                submitHandler: function() {
                    $scope.submit();
                }
            });
        }
    }]);