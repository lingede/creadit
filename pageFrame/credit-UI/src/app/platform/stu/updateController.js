'use strict';

    angular.module('app')
        .controller('stuUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster', 
                                             function($scope, $rootScope, $state, $timeout ,toaster) {
        var title = "";
        if($state.includes('**.stu.update')){
            title="编辑学生";
            var id = $state.params.id;
            activate(id);
            validate(id);
        }else if($state.includes('**.stu.create')){
            title="添加学生";
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
    				url : 'stu/add',
    				data: $scope.record
    			}).then(callback);
            }
            function callback(result) {
                if(result.httpCode ==200){//成功
                    toaster.clear('*');
                    toaster.pop('success', '', "保存成功");
                    $timeout(function(){
                        $state.go('main.platform.stu.list');
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
				url : '/stu/read/detail',
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
			});
        }

      //表单验证
        function validate(){
         jQuery('form').validate({
                rules: {
                    stuName: {
                        required: true,
                    },
                    stuID: {
                        required: true
                    },
                    deptNo: {
                        required: true
                    },
                    stuType:{
                    	selected:[]
                    },
                    stuMajor:{
                    	required: true,
                    },
                    stuPhone:{
                    	required: true,
                    },
                },
                messages: {
                    stuName: {
                        required: '请填写学生姓名',
                    },
                    stuID: {
                        required: '请填写学生学号'
                    },
                    deptNo: {
                        required: '请填写学院编号'
                    },
                    stuType:{
                    	selected:"请选择学生类型"
                    },
                    stuMajor:{
                    	required:'请填写学生专业'
                    },
                    stuPhone:{
                    	required: '请填写学生联系方式',
                    },
                },
                submitHandler: function() {
                    $scope.submit();
                }
            });
        }

    }]);