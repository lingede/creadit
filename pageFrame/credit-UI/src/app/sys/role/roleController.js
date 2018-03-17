'use strict';

angular.module('app')
	.controller('roleController', [ '$rootScope', '$scope', '$http', '$state', 'toaster',
	                                function($rootScope, $scope, $http, $state, toaster) {
		$scope.title = '角色管理';
        $scope.param = { };
        $scope.loading = false;
        
		$scope.search = function () {
	        $scope.loading = true;
			$.ajax({
				url : '/role/read/list',
				data: $scope.param
			}).then(function(result) {
		        $scope.loading = false;
				if (result.httpCode == 200) {
					$scope.pageInfo = result.data;
				} else {
					$scope.msg = result.msg;
				}
				$scope.$apply();
			});
		};
		
		$scope.search();
		
		$scope.clearSearch = function() {
			$scope.param.keyword= null;
			$scope.search();
		};
		
		/**
		 * 数据状态
		 */
		$scope.disableItem = function(record,enable) {
			$.ajax({
				url : enable ? '/role/update' : '/role/delete',
				data: record
			}).then(function(result) {
		        if(result.httpCode ==200){//成功
		        	toaster.clear('*');
                    toaster.pop('success', '', "保存成功");
        			$.ajax({
        				url : '/role/read/list',
        				data: $scope.param
        			}).then(function(result) {
        		        $scope.loading = false;
        				if (result.httpCode == 200) {
        					$scope.pageInfo = result.data;
        				} else {
        					$scope.msg = result.msg;
        				}
        				$scope.$apply();
        			});
                }else{
                    toaster.clear('*');
                    toaster.pop('error', '', result.msg);
                }
			});
		};
		
		// 翻页
        $scope.pagination = function (page) {
            $scope.param.pageNum=page;
            $scope.search();
        };
} ]);