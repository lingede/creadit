'use strict';

    angular.module('app')
        .controller('deptUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
                                             function($scope, $rootScope, $state, $timeout, toaster) {
        var title = "";
        console.info("in upcontroler");
        if($state.includes('**.dept.update')){
        	console.info("in edit of controler");
            title="编辑学院";
            var id = $state.params.id;
            activate(id);
            validate(id);
        }else if($state.includes('**.dept.create')){
            title="添加学院";
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
    				url : $scope.record.id ? '/pdept/update' : 'pdept/add',
    				data: $scope.record
    			}).then(callback);
            }
            function callback(result) {
                if(result.httpCode ==200){//成功
                    toaster.clear('*');
                    toaster.pop('success', '', "保存成功");
                    $timeout(function(){
                        $state.go('main.platform.dept.list');
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
				url : '/pdept/read/detail',
				data: {'id': id}
			}).then(function(result) {
		        $scope.loading = false;
				if (result.httpCode == 200) {
                    $scope.record = result.data.record;
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
                        maxlength:10,
//                      isExist:["/pdept/checkName",deptId]
//                      ，isExist:["/pdept/cheakNo",deptId]
                    },
                    deptNo: {
                        required: true
                    },
                    deptAddress: {
                        required: true
                    },
                    deptManage:{
                    	required: true
                    },
                    deptPhone:{
                    	required: true,
                        isPhone:[]
                    },
                },
                messages: {
                    deptName: {
                        required: '请填写学院名',
                        maxlength:"帐号不得超过{0}个字符",
                        isExist:"该学院名称或编号已存在"
                    },
                    deptNo: {
                        required: '请填写学院编号'
                    },
                    deptAddress: {
                        required: '请填写学院地址'
                    },
                    deptManage:{
                    	required: '请填写学院负责人'
                    },
                    deptPhone:{
                    	required: '请填写负责人联系方式',
                        isPhone:'请填写有效的联系方式'
                    },
                },
                submitHandler: function() {
                    $scope.submit();
                }
            });
        }
    }]);
