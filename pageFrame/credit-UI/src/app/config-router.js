'use strict';
// 

var app = angular.module('app')
	.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
		// 默认地址
		$urlRouterProvider.otherwise('/access/login');
		// 状态配置
		$stateProvider
			.state('main', {
				abstract: true,
				url: '',
				templateUrl: 'src/tpl/app.html'
			})
			.state('access', {
				url: '/access',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('access.login', {
				url: '/login',
				templateUrl: 'src/app/sys/login/login.html',
				controller: 'loginController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/login/loginController.js');
					}]
				}
			})
			.state('main.sys', {
				url: '/sys',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			}) 
			.state('main.sys.blank', {
				url: '/blank',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.blank.list', {
				url: '/list',
				templateUrl: 'src/app/sys/blank/blank.html',
				controller: 'blankController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/blank/blankController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})// 用户
			.state('main.sys.user', {
				url: '/user',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.user.list', {
				url: '/list',
				templateUrl: 'src/app/sys/user/user.html',
				controller: 'userController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/user/userController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.user.create', {
				url: '/create',
				templateUrl: 'src/app/sys/user/update.html',
				controller: 'userUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/user/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.user.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/sys/user/update.html',
				controller: 'userUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/user/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) // 用户权限
			.state('main.sys.user.menu', {
				url: '/menu',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.user.menu.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/sys/user.menu/update.html',
				controller: 'userMenuUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/user.menu/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) // 用户角色
			.state('main.sys.user.role', {
				url: '/menu',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.user.role.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/sys/user.role/update.html',
				controller: 'userRoleUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/user.role/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) // 部门
			.state('main.sys.dept', {
				url: '/dept',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.dept.list', {
				url: '/list',
				templateUrl: 'src/app/sys/dept/dept.html',
				controller: 'deptController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dept/deptController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.dept.create', {
				url: '/create',
				templateUrl: 'src/app/sys/dept/update.html',
				controller: 'deptUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dept/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.dept.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/sys/dept/update.html',
				controller: 'deptUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dept/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) // 菜单
			.state('main.sys.menu', {
				url: '/menu',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.menu.list', {
				url: '/list',
				templateUrl: 'src/app/sys/menu/menu.html',
				controller: 'menuController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/menu/menuController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.menu.create', {
				url: '/create',
				templateUrl: 'src/app/sys/menu/update.html',
				controller: 'menuUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/menu/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.menu.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/sys/menu/update.html',
				controller: 'menuUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/menu/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) // 角色
			.state('main.sys.role', {
				url: '/role',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.role.list', {
				url: '/list',
				templateUrl: 'src/app/sys/role/role.html',
				controller: 'roleController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/role/roleController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.role.create', {
				url: '/create',
				templateUrl: 'src/app/sys/role/update.html',
				controller: 'roleUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/role/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.role.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/sys/role/update.html',
				controller: 'roleUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/role/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) // 角色权限
			.state('main.sys.role.menu', {
				url: '/menu',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.role.menu.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/sys/role.menu/update.html',
				controller: 'roleMenuUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/role.menu/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) // 会话
			.state('main.sys.session', {
				url: '/session',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.session.list', {
				url: '/list',
				templateUrl: 'src/app/sys/session/session.html',
				controller: 'sessionController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/session/sessionController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) // 字典
			.state('main.sys.dic', {
				url: '/dic',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.dic.list', {
				url: '/list',
				templateUrl: 'src/app/sys/dic/dic.html',
				controller: 'dicController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dic/dicController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.dic.create', {
				url: '/create',
				templateUrl: 'src/app/sys/dic/update.html',
				controller: 'dicUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dic/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.dic.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/sys/dic/update.html',
				controller: 'dicUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dic/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) // 参数
			.state('main.sys.param', {
				url: '/param',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.param.list', {
				url: '/list',
				templateUrl: 'src/app/sys/param/param.html',
				controller: 'paramController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/param/paramController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.param.create', {
				url: '/create',
				templateUrl: 'src/app/sys/param/update.html',
				controller: 'paramUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/param/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.param.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/sys/param/update.html',
				controller: 'paramUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/param/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) // 清除缓存
			.state('main.sys.cache', {
				url: '/cache',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.cache.update', {
				url: '/update',
				controller: 'cacheController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/cache/cacheController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) // 调度
			.state('main.task', {
				url: '/task',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.task.group', {
				url: '/group',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.task.group.list', {
				url: '/list',
				templateUrl: 'src/app/task/group/group.html',
				controller: 'taskGroupController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/task/group/groupController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.task.group.create', {
				url: '/create',
				templateUrl: 'src/app/task/group/update.html',
				controller: 'groupUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/task/group/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.task.group.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/task/group/update.html',
				controller: 'groupUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/task/group/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.task.scheduler', {
				url: '/scheduler',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.task.scheduler.list', {
				url: '/list',
				templateUrl: 'src/app/task/scheduler/scheduler.html',
				controller: 'taskSchedulerController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/task/scheduler/schedulerController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.task.scheduler.create', {
				url: '/create',
				templateUrl: 'src/app/task/scheduler/update.html',
				controller: 'schedulerUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/task/scheduler/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.task.scheduler.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/task/scheduler/update.html',
				controller: 'schedulerUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/task/scheduler/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.task.scheduled', {
				url: '/scheduled',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.task.scheduled.list', {
				url: '/list',
				templateUrl: 'src/app/task/scheduled/scheduled.html',
				controller: 'taskScheduledController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/task/scheduled/scheduledController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.task.log', {
				url: '/log',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.task.log.list', {
				url: '/list',
				templateUrl: 'src/app/task/scheduled/log.html',
				controller: 'scheduledLogController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/task/scheduled/logController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) //管理员
			.state('main.platform', {
				url: '/platform',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.platform.score', {
				url: '/score',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.platform.score.list', {
				url: '/list',
				templateUrl: 'src/app/platform/score/score.html',
				controller: 'scoreController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/score/scoreController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.platform.score.create', {
				url: '/create',
				templateUrl: 'src/app/platform/score/update.html',
				controller: 'scoreUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/score/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.platform.score.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/platform/score/update.html',
				controller: 'scoreUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/score/udateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) //审核处理(讲座)
			.state('main.platform.actround', {
				url: '/actround',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.platform.actround.list', {
				url: '/list',
				templateUrl: 'src/app/platform/actround/actround.html',
				controller: 'actroundController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/actround/actroundController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.platform.actround.create', {
				url: '/create',
				templateUrl: 'src/app/platform/actround/update.html',
				controller: 'actroundUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/actround/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.platform.actround.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/platform/actround/update.html',
				controller: 'actroundUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/actround/udateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//审核处理(演出)
			.state('main.platform.actround2', {
				url: '/actround2',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.platform.actround2.list', {
				url: '/list',
				templateUrl: 'src/app/platform/actround2/actround.html',
				controller: 'actroundController2',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/actround2/actroundController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//学院管理
			.state('main.platform.dept', {
				url: '/dept',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.platform.dept.list', {
				url: '/list',
				templateUrl: 'src/app/platform/dept/dept.html',
				controller: 'deptController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/dept/deptController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.platform.dept.create', {
				url: '/create',
				templateUrl: 'src/app/platform/dept/update.html',
				controller: 'deptUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/dept/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.platform.dept.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/platform/dept/update.html',
				controller: 'deptUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/dept/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//学院统计
			.state('main.platform.xytj', {
				url: '/xytj',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.platform.xytj.list', {
				url: '/list',
				templateUrl: 'src/app/platform/xytj/xytj.html',
				controller: 'xytjController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/xytj/xytjController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//学生管理
			.state('main.platform.stu', {
				url: '/stu',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.platform.stu.list', {
				url: '/list',
				templateUrl: 'src/app/platform/stu/stu.html',
				controller: 'stuController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/stu/stuController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.platform.stu.create', {
				url: '/create',
				templateUrl: 'src/app/platform/stu/update.html',
				controller: 'stuUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/stu/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.platform.stu.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/platform/stu/update.html',
				controller: 'stuUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/stu/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//活动管理
			.state('main.platform.act', {
				url: '/act',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.platform.act.list', {
				url: '/list',
				templateUrl: 'src/app/platform/act/act.html',
				controller: 'actController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/act/actController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.platform.act.create', {
				url: '/create',
				templateUrl: 'src/app/platform/act/update.html',
				controller: 'actUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/act/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.platform.act.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/platform/act/update.html',
				controller: 'actUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/act/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//部门普通用户
			.state('main.platform.deptuser', {
				url: '/deptuser',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.platform.deptuser.list', {
				url: '/list',
				templateUrl: 'src/app/platform/deptuser/deptuser.html',
				controller: 'deptUserController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/deptuser/deptUserController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.platform.deptuser.create', {
				url: '/create',
				templateUrl: 'src/app/platform/deptuser/update.html',
				controller: 'deptUserUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/deptuser/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.platform.deptuser.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/platform/deptuser/update.html',
				controller: 'deptUserUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/deptuser/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//学院普通用户菜单权限
			.state('main.platform.deptuser.menu', {
				url: '/menu',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.platform.deptuser.menu.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/platform/deptuser.menu/update.html',
				controller: 'deptUserMenuUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/deptuser.menu/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) 
			//管理统计
			.state('main.platform.gltj', {
				url: '/gltj',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.platform.gltj.list', {
				url: '/list',
				templateUrl: 'src/app/platform/gltj/gltj.html',
				controller: 'gltjController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/platform/gltj/gltjController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//微信自定义菜单
			.state('main.wechat', {
				url: '/wechat',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.wechat.menu', {
				url: '/menu',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.wechat.menu.list', {
				url: '/list',
				templateUrl: 'src/app/wechat/menu/menu.html',
				controller: 'wechatMenuController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/wechat/menu/menuController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.wechat.menu.create', {
				url: '/create',
				templateUrl: 'src/app/wechat/menu/update.html',
				controller: 'wechatMenuUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/wechat/menu/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.wechat.menu.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/wechat/menu/update.html',
				controller: 'wechatMenuUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/wechat/menu/updateController.js').then(function() {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			});
	}])
	.controller("navCtrl", function($rootScope, $state) {
		$.ajax({
			url: '/user/read/current',
			success: function(result) {
				if(result.httpCode == 200) {
					$rootScope.userInfo = result.user;
					$rootScope.menuList = result.menus;
					$rootScope.$apply();
				}
			}
		});
	})
	.run(['$rootScope', '$state', '$stateParams', '$timeout', '$templateCache',
		function($rootScope, $state, $stateParams, $timeout, $templateCache) {
			$rootScope.$state = $state;
			$rootScope.$stateParams = $stateParams;
			$rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
				var from = fromState.name,
					to = toState.name;
				if(from && to) { // 解决 相应模块从列表进入编辑后 状态丢失问题
					var s1 = from.substring(0, from.lastIndexOf(".")),
						g1 = from.substring(from.lastIndexOf(".") + 1),
						s2 = to.substring(0, to.lastIndexOf(".")),
						g2 = to.substring(to.lastIndexOf(".") + 1);
					if(s1 == s2) {
						if(g1 == 'list' && (g2 == 'update' || g2 == 'view')) { //进行编辑
							toParams['params'] = window.location.hash;
						}
						//返回列表
						if((g1 == "update" || g1 == 'view') && g2 == 'list') {
							var h = fromParams['params'];
							if(h) {
								$timeout(function() {
									window.location.hash = h;
								});
							}
						}
					}
				}
			});
		}
	]);