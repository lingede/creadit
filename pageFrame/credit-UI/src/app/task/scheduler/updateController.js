'use strict';

    angular.module('app')
        .controller('schedulerUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
                                             function($scope, $rootScope, $state, $timeout, toaster) {
        var title = "";
        if($state.includes('**.scheduler.update')){
            title="编辑任务";
            var id = $state.params.id;
            activate(id);
            validate(id);
        }else if($state.includes('**.scheduler.create')){
            title="添加任务";
            activate(0);
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
    				url : $scope.record.id ? '/task/update/scheduler' : 'task/add/scheduler',
    				data: $scope.record
    			}).then(callback);
            }
            function callback(result) {
                if(result.httpCode ==200){//成功
                    toaster.clear('*');
                    toaster.pop('success', '', "保存成功");
                    $timeout(function(){
                        $state.go('main.task.scheduler.list');
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
				url : '/task/read/scheduler',
				data: {'id': id}
			}).then(function(result) {
		        $scope.loading = false;
				if (result.httpCode == 200) {
					$scope.record = result.data.record;
					$scope.taskGroups = result.data.taskGroups;
				} else {
					$scope.msg = result.msg;
				}
				$scope.$apply();
			});
        }

        //表单验证
        function validate(taskId){
            jQuery('form').validate({
                rules: {
                	groupId:{
                		selected:[]
                	},
                	taskName: {
                        required: true,
                        isExist:["/task/update/scheduler/checkName",taskId]
                    },
                    taskDesc: {
                        required: true
                    },
                    taskType: {
                        required: true
                    },
                    taskCron: {
                        required: true,
                        validTaskCron:[]
                    }
                },
                messages: {
                	groupId:{
                		selected:'请选择任务组'
                	},
                	taskName: {
                        required: '请填写任务',
                        isExist:"该任务已存在"
                    },
                    taskDesc: {
                        required: '请填写描述'
                    },
                    taskType: {
                        required: '请填写任务类型'
                    },
                    taskCron: {
                        required: '请填写执行周期',
                        validTaskCron:"请输入正确的Cron表达式"
                    }
                },
                submitHandler: function() {
                    $scope.submit();
                }
            });
        }

    }]);