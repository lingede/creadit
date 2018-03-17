'use strict';

angular.module('app')
	.controller('deptUserController', ['$rootScope', '$scope', '$http', '$state', '$timeout', 'toaster',
		function($rootScope, $scope, $http, $state, $timeout, toaster) {
			$scope.title = '学院用户管理';
			$scope.param = {};
			$scope.loading = false;
			$scope.search = function() {
				$scope.loading = true;
				$.ajax({
					url: '/deptuser/read/list',
					data: {
						deptid: $rootScope.userInfo.deptId,
						keyword: $scope.param.keyword
					}
				}).then(function(result) {
					$scope.loading = false;
					if(result.httpCode == 200) {
						$scope.pageInfo = result.data;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$apply();
				});
			};
			/**
			 * 数据状态
			 */
			$scope.disableItem = function(record, enable) {
				$.ajax({
					url: enable ? '/deptuser/update' : '/deptuser/delete',
					data: record
				}).then(function(result) {
					if(result.httpCode == 200) { //成功
						toaster.clear('*');
						toaster.pop('success', '', "保存成功");
						$.ajax({
							url: '/deptuser/read/list',
							data: {
								deptid: $rootScope.userInfo.deptId,
								keyword: $scope.param.keyword 
							}
						}).then(function(result) {
							$scope.loading = false;
							if(result.httpCode == 200) {
								$scope.pageInfo = result.data;
							} else {
								$scope.msg = result.msg;
							}
							$scope.$apply();
						});
					} else {
						toaster.clear('*');
						toaster.pop('error', '', result.msg);
					}
				});
			};

			/**
			 * 锁定状态
			 */
			$scope.lockedItem = function(record, locked) {
				var id = record.id;
				$.ajax({
					url: '/deptuser/update/locked',
					data: {
						id: id,
						locked: locked
					}
				}).then(function(result) {
					if(result.httpCode == 200) { //成功
						toaster.clear('*');
						toaster.pop('success', '', "保存成功");
						$.ajax({
							url: '/deptuser/read/list',
							data: {
								deptid: $rootScope.userInfo.deptId
							}
						}).then(function(result) {
							$scope.loading = false;
							if(result.httpCode == 200) {
								$scope.pageInfo = result.data;
							} else {
								$scope.msg = result.msg;
							}
							$scope.$apply();
						});
					} else {
						toaster.clear('*');
						toaster.pop('error', '', result.msg);
					}
				});
			};

			$scope.search();

			$scope.clearSearch = function() {
				$scope.param.keyword = null;
				$scope.search();
			};

			// 翻页
			$scope.pagination = function(page) {
				$scope.param.pageNum = page;
				$scope.search();
			};
		}
	]);