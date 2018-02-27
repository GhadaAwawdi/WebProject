var app = angular.module('myApp', []);
	app.controller('ebooksCtrl', function($scope, $http,$window) {		
		
			var timerhandler;

		    $scope.CheckNow = function()
		    {
//	        	alert('success');

		        $.ajax(
		        {
		            type: 'GET',
		            url: 'GetAllLikesNumOrdered',
		            success: function(data) 
		            {
		     //       	alert('success');
		                var allcookies = document.cookie;
		                cookiearray = allcookies.split(';');
		                  // username = cookiearray[0].split('=')[1]; 
		                   type = cookiearray[1].split('=')[1];  
		                   console.log(type)
		                   if(type=="user"){
		                       window.location.href='homeUser.html';
		                   }
		                   else{
				           		$http.get('GetAllEbooks').success(function(data, status, headers,config) {
				           			$scope.allEbooks = data;
								})
								.error(function(data, status, headers,config) {
									console.log(data);
				        		});
								$scope.myVar = true;
				                  // alert('suuu')
		                   }
		            }
		        });

		    }
		    console.log("ajaaax")
		    timerhandler = setInterval($scope.CheckNow,50000);
					
			$http.get('GetAllEbookUsers').success(function(data, status, headers,config) {
				$scope.users = data;
				$scope.statuscode = status;
			})
			.error(function(data, status, headers,config){
				console.log(data);
			});
			
			$scope.RemoveUser = function(username,button) {
		    	var btn = document.getElementById(button);
				console.log(btn);
				var result = confirm("Are you sure you want to delete the user "+username+"?" );
				if (result) {
					var data = {
							username : username,
						};
					 //var row = btn.parentNode.parentNode;
					  //row.parentNode.removeChild(row);
						//Call the services
						var jsonData = JSON.stringify(data);
				    //Logic to delete the item
				    $http.post('RemoveEbookUser',jsonData).success(function(data, status, headers,config) {
						$scope.users = data;
						$scope.statuscode = status;
					}).error(function(data, status, headers,config){
						console.log(data);
					});
				    location.reload();
					 
		//			document.getElementById("selectedUser").innerHTML = id;
			//		document.getElementById("selectedUser").style.visibility = " none";
				}
		
				
			}
			
			$scope.logout = function(){
				$http.get("Logout").success(function(data, status, headers,config){
                    window.location.href='index.html';

				})
				.error(function(data, status, headers,config){
					console.log(data);
				});
			}
	});