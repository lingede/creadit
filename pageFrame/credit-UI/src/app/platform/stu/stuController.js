'use strict';

angular.module('app')
	.controller('stuController', [ '$rootScope', '$scope', '$http', '$state', '$timeout', 'toaster',
	                                function($rootScope, $scope, $http, $state, $timeout, toaster) {
		$scope.title = '学生管理';
        $scope.param = { };
        $scope.loading = false;
		$scope.search = function () {
	        $scope.loading = true;
			$.ajax({
				url : '/stu/read/list',
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
		
		
		// 翻页
        $scope.pagination = function (page) {
            $scope.param.pageNum=page;
            $scope.search();
        };
        $scope.delete=function(id){
				console.info(id);
				$scope.loading=true;
				$.ajax({
					url:"/stu/delete",
					data:{'id':id}
				}).then(function(result){					
					$scope.loading = false;
					if(result.httpCode == 200) {
						$scope.search();						
					} else {
						$scope.msg = result.msg;
					}
					$scope.$apply();				
				});
		}
} ]);