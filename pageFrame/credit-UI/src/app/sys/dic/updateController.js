'use strict';
angular.module('app')
    .controller('dicUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
                                         function($scope, $rootScope, $state, $timeout, toaster) {
	    var title = "";
	    if($state.includes('**.dic.update')){
	        title="编辑字典";
	        var id = $state.params.id;
	        activate(id);
	        validate(id);
	    }else if($state.includes('**.dic.create')){
	        title="添加字典";
	        validate(null);
	    }
	    $scope.title = $rootScope.title = title;
	    $scope.loading = true;
	    //初始化验证
	    $scope.submit= function(){
	        var m = $scope.record;
	        if(m){
	            $scope.isDisabled = true;//提交disabled
	            $.ajax({
					url : $scope.record.id ? '/dicIndex/update' : '/dicIndex/add',
					data: $scope.record
				}).then(callback);
	        }
	        function callback(result) {
	            if(result.httpCode ==200){//成功
	                toaster.clear('*');
	                toaster.pop('success', '', "保存成功");
	                $timeout(function(){
	                    $state.go('main.sys.dic.list');
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
				url : '/dicIndex/read/detail',
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
	    function validate(dicIndexId){
	        jQuery('form').validate({
	            rules: {
	            	dicIndexName: {
	                    required: true,
	                    maxlength:20,
	                    isExist:["/dicIndex/update/checkDicIndexName",dicIndexId]
	                },
	                dicIndexKey: {
	                    required: true,
	                    isExist:["/dicIndex/update/checkDicIndexKey",dicIndexId]
	                }
	            },
	            messages: {
	            	dicIndexName: {
	                    required: '请填写字典名称',
	                    maxlength:"字典名称不得超过{0}个字符",
	                    isExist:"该字典名称已存在"
	                },
	                dicIndexKey: {
	                    required: '请填写字典关键字',
	                    isExist:"该字典关键字已存在"
	                }
	            },
	            submitHandler: function() {
	                $scope.submit();
	            }
	        });
	    }
}]);