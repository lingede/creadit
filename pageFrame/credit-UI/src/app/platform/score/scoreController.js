'use strict';

angular.module('app')
	.controller('scoreController', ['$scope', '$rootScope', 'toaster', '$timeout', '$state',
		function($scope, $rootScope, toaster, $timeout, $state) {

			$scope.title = '学分设置';
			$scope.record = {};
			$scope.save = function() {
				var m = $scope.record;
				if(m) {
					$scope.isDisabled = true; //提交disabled
					$.ajax({
						url: 'Score/set',
						data: $scope.record
					}).then(callback);
				}

				function callback(result) {
					if(result.httpCode == 200) { //成功
						toaster.clear('*');
						toaster.pop('success', '', "保存成功");
						$timeout(function() {
							$state.go('main.platform.score.list');
						}, 2000);
					} else {
						toaster.clear('*');
						toaster.pop('error', '', result.msg);
						$scope.isDisabled = false;
					}
				}

			};

			$scope.$on('$viewContentLoaded', function() {
				$.ajax({
					url: 'Score/read',
					contentType: "application/json; charset=utf-8",
					dataType: "json",
					async: false,
					success: function(data) {
						$scope.record.scoreApplySetValue1 = data.data.scoreApplySetValue1;
						$scope.record.scoreApplySetValue3 = data.data.scoreApplySetValue3;
						$scope.record.scoreApplySetValue2 = data.data.scoreApplySetValue2;
						$scope.record.scoreApplySetValue4 = data.data.scoreApplySetValue4;
						$scope.record.scoreCycleStartTime = data.data.scoreCycleStartTime;
						$scope.record.scoreCycleEndTime = data.data.scoreCycleEndTime;
						$scope.record.scoreApplyStartTime = data.data.scoreApplyStartTime;
						$scope.record.scoreApplyEndTime = data.data.scoreApplyEndTime;
						$scope.record.scoreAffirmStartTime = data.data.scoreAffirmStartTime;
						$scope.record.scoreAffirmEndTime = data.data.scoreAffirmEndTime;
					}
				});

			});

		}
	])