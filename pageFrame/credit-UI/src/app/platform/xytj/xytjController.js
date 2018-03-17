'use strict';

angular.module('app')
	.controller('xytjController', [ '$rootScope', '$scope', '$http', '$state', '$timeout', 'toaster',
	                                function($rootScope, $scope, $http, $state, $timeout, toaster) {
		$scope.title = '学院统计';

		$scope.show = 1;
        $scope.param_cardFlow = { };
        $scope.param_credit = { };
        $scope.param_cardFlowStatistics = { };
		$scope.param_act = { };
		$scope.param_detail = { };


        $scope.param_cardFlow.deptId = $rootScope.userInfo.deptId;
        $scope.param_credit.deptId = $rootScope.userInfo.deptId;
        $scope.param_cardFlowStatistics.deptId = $rootScope.userInfo.deptId;
        $scope.param_act.deptId = $rootScope.userInfo.deptId;

		//柱状图
        var options = {
			chart: {
				renderTo: 'Container', //DIV容器ID
				type: 'column'//报表类型
			},
			//报表名称
			title:{
				text:'活动情况'
			},
			yAxis: {
				min: 0,
				title:{
					text:'人数'
				}
			},
			//x轴显示内容
			xAxis: {
				categories: [ ]
			},
			series: [{
				name:'报名人数',
				data:[]
			},
				{
					name:'到场人数',
					data:[]
				}]
		};


        var options2 = {
			chart: {
				renderTo: 'Container2', //DIV容器ID
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
			},
			//报表名称
			title:{
				text:'学分统计情况(讲座)'
			},
			tooltip:{
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
			},
            plotOptions:{
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                }
			},
			series: [{
                type: 'pie',
                name: '学分统计情况',
                data: [
                    ['获得二学分', 0],
                    ['获得一学分', 0],
                    {name:'未获得学分',
						y:0,
						sliced:true,
						selected:true}
                ]
				}]
		};
		
		var options3 = {
			chart: {
				renderTo: 'Container3', //DIV容器ID
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
			},
			//报表名称
			title:{
				text:'学分统计情况(演出)'
			},
			tooltip:{
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
			},
            plotOptions:{
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                }
			},
			series: [{
                type: 'pie',
                name: '学分统计情况',
                data: [
                    ['获得二学分', 0],
                    ['获得一学分', 0],
                    {name:'未获得学分',
						y:0,
						sliced:true,
						selected:true}
                ]
				}]
		};




        $scope.loading = false;
		$scope.cardFlowSearch = function () {
	        $scope.loading = true;
            $scope.param_cardFlow.actStartDate = $scope.startDate;
            $scope.param_cardFlow.actEndDate = $scope.endDate;
			$.ajax({
				url : '/xytj/cardFlow/list',
				data: $scope.param_cardFlow
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

		$scope.cardFlowSearch();


		$scope.clearCardFlowSearch = function() {
			$scope.param_cardFlow.keyword= null;
			$scope.cardFlowSearch();
		};
        //活动情况报表导出
        $scope.cardFlowReport = function () {
			$http({
				url:'/xytj/cardFlow/report',
				method:'POST',
				params:{deptId:$rootScope.userInfo.deptId,actStartDate:$scope.startDate,actEndDate:$scope.endDate},
				responseType:'arraybuffer'
			}).success(function (result) {
				var blob = new Blob([result],{
					type:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
				});
				saveAs(blob,'活动情况报表'+'.xlsx');
            }).error(function(data){
            	alert(result.message);
			});
		};
		//活动报名人员报表导出
		$scope.enrollistReport = function(actId){
			$http({
				url:'/xytj/enrollist/report',
				method:'POST',
				params:{deptId:$rootScope.userInfo.deptId,actId:actId},
				responseType:'arraybuffer'
			}).success(function (result) {
				var blob = new Blob([result],{
					type:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
				});
				saveAs(blob,'报名列表'+'.xlsx');
            }).error(function(data){
            	alert(result.message);
			});
		}

		//日期选择器
		$('#startDate').datetimepicker({
			minView:'month',
         	format: 'yyyy-mm-dd',
			autoclose:true,
			language:'zh-CN',
			todayBtn:true
        });
        $('#endDate').datetimepicker({
        	minView:'month',
			format: 'yyyy-mm-dd',
            autoclose:true,
            language:'zh-CN',
            todayBtn:true
         });
         //获得学分情况
		$scope.creditSearch = function(){
			$scope.loading = true;
			$scope.param_credit.reviewDateStart = $scope.startDate;
			$scope.param_credit.reviewDateEnd = $scope.endDate;
			$.ajax({
				url : '/xytj/credit/list',
				data: $scope.param_credit
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

		$scope.clearCreditSearch = function() {
			$scope.param_credit.keyword= null;
			$scope.creditSearch();
		};
        $scope.credit1Report = function () {
			$http({
				url:'/xytj/credit/report/1',
				method:'POST',
				params:{deptId:$rootScope.userInfo.deptId,reviewDateStart:$scope.startDate,reviewDateEnd:$scope.endDate},
				responseType:'arraybuffer'
			}).success(function (result) {
				var blob = new Blob([result],{
					type:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
				});
				saveAs(blob,'1学分学号名单'+'.xlsx');
			}).error(function(data){
				alert(result.message);
			});
		};

        $scope.credit2Report = function () {
			$http({
				url:'/xytj/credit/report/2',
				method:'POST',
				params:{deptId:$rootScope.userInfo.deptId, reviewDateStart:$scope.startDate,reviewDateEnd:$scope.endDate},
				responseType:'arraybuffer'
			}).success(function (result) {
				var blob = new Blob([result],{
					type:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
				});
				saveAs(blob,'2学分学号名单'+'.xlsx');
			}).error(function(data){
				alert(result.message);
			});
		};


		$scope.cardFlowStatisticsSearch = function(){
            $scope.loading = true;
            $scope.param_cardFlow.actStartDate = $scope.startDate;
            $scope.param_cardFlow.actEndDate = $scope.endDate;
            $.ajax({
                url : '/xytj/cardFlow/Statistics',
                data: $scope.param_cardFlowStatistics
            }).then(function(result) {
                $scope.loading = false;
                if (result.httpCode == 200) {
                    $scope.pageInfo = result.data;

                    options.xAxis.categories = [];//重置
                    options.series[0].data = [];
                    options.series[1].data = [];

                    for(var i=0;i<$scope.pageInfo.list.length;i++){
                    	options.xAxis.categories.push($scope.pageInfo.list[i].actName);
                    	options.series[0].data.push($scope.pageInfo.list[i].enrollment);
                    	options.series[1].data.push($scope.pageInfo.list[i].arrival);
					}

                    var chart = new Highcharts.Chart(options);
				} else {
                    $scope.msg = result.msg;
                }
                $scope.$apply();
            });
		}

        $scope.creditStatisticsSearch = function(){
			$scope.loading = true;
			$.ajax({
				url : '/xytj/credit/Statistics',
				data: {deptId:$rootScope.userInfo.deptId,scoreCycleStartDate:$scope.startDate,scoreCycleEndDate:$scope.endDate}
			}).then(function(result) {
				$scope.loading = false;
				if (result.httpCode == 200) {
					$scope.pageInfo = result.data;

                    options2.series[0].data[0]=['获得二学分', 0];
                    options2.series[0].data[1]=['获得一学分', 0];
                    options2.series[0].data[2].y=0;


					var total = $scope.pageInfo.amountOf2Score + $scope.pageInfo.amountOf1Score + $scope.pageInfo.amountOf0Score;
					options2.series[0].data[0]=['获得二学分', $scope.pageInfo.amountOf2Score/total];
                    options2.series[0].data[1]=['获得一学分', $scope.pageInfo.amountOf1Score/total];
                    options2.series[0].data[2].y=$scope.pageInfo.amountOf0Score/total;
                    var chart = new Highcharts.Chart(options2);
                    
                    
                    
                    
                    options3.series[0].data[0]=['获得二学分', 0];
                    options3.series[0].data[1]=['获得一学分', 0];
                    options3.series[0].data[2].y=0;


					var t = $scope.pageInfo.amountOf2Score2 + $scope.pageInfo.amountOf1Score2 + $scope.pageInfo.amountOf0Score2;
					options3.series[0].data[0]=['获得二学分', $scope.pageInfo.amountOf2Score2/t];
                    options3.series[0].data[1]=['获得一学分', $scope.pageInfo.amountOf1Score2/t];
                    options3.series[0].data[2].y=$scope.pageInfo.amountOf0Score2/t;

                    var c = new Highcharts.Chart(options3);
				} else {
					$scope.msg = result.msg;
				}
				$scope.$apply();
			});
		}


        $scope.actSearch = function () {
            $scope.show = 1;
			$scope.loading = true;
			$.ajax({
				url : '/xytj/act/list',
				data: $scope.param_act
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

        $scope.clearCreditSearch = function() {
			$scope.param_act.keyword= null;
			$scope.actSearch();
		};

        $scope.detail = function (id) {
            $scope.show = 2;
            $scope.param_detail.actId = id;
            $scope.loading = true;
            $.ajax({
                url : '/xytj/actEnrol/list',
                data:$scope.param_detail
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



                                        //显示切换
                                        $scope.viewChange = function(){
                                            if($scope.view == 0){
                                                $scope.cardFlowSearch();
                                            }
                                            else if($scope.view ==1){
                                                $scope.cardFlowStatisticsSearch();
                                            }
                                            else if($scope.view ==2){
                                                $scope.creditSearch();
                                            }
                                            else if($scope.view ==3){
                                                $scope.creditStatisticsSearch();
                                            }
                                            else if($scope.view == 4){
                                            	$scope.actSearch();
											}
                                        };
		/**
		 * 数据状态
		 */
		$scope.disableItem = function(record,enable) {
			$.ajax({
				url : enable ? '/user/update' : '/user/delete',
				data: record
			}).then(function(result) {
		        if(result.httpCode ==200){//成功
		        	toaster.clear('*');
                    toaster.pop('success', '', "保存成功");
        			$.ajax({
        				url : '/user/read/list',
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
            $scope.param_cardFlow.pageNum=page;
            $scope.cardFlowSearch();
        };

        $scope.paginationCredit = function (page){
        	$scope.param_credit.pageNum=page;
        	$scope.creditSearch();
		}

		$scope.paginationCardFlowStatistics = function(page){
        	$scope.param_cardFlowStatistics.pageNum = page;
        	$scope.cardFlowStatisticsSearch();
		}

        $scope.paginationAct = function (page){
			$scope.param_act.pageNum=page;
			$scope.actSearch();
		}

		$scope.paginationDetail = function (page){
			$scope.param_detail.pageNum=page;
			$scope.detail();
		}
} ]);