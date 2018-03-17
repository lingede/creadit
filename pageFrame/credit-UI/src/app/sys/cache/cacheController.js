'use strict';

angular.module('app')
	.controller('cacheController', [ '$rootScope', '$scope', '$http', '$state', 'toaster',
	                                function($rootScope, $scope, $http, $state, toaster) {
		$scope.title = '清理缓存';
        $scope.param = { };
        $scope.loading = false;
        
		$scope.search = function () {
	        $scope.loading = true;
			$.ajax({
				url : '/cache/update',
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
		}
		
		$scope.search();
		
		$scope.clearSearch = function() {
			$scope.param.keyword= null;
			$scope.search();
		}
		
		$scope.disableItem = function(id, enable) {
			
		}
		
		// 翻页
        $scope.pagination = function (page) {
            $scope.param.pageNum=page;
            $scope.search();
        };
} ]);