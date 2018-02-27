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

		        		}).error(function(data, status, headers,config){
               				
               			});
						$scope.myVar = true;
		                  // alert('suuu')
		                   }
		            }
		        });

		    }
		    console.log("ajaaax")
		    timerhandler = setInterval($scope.CheckNow,50000);
					
		    $scope.ApproveReview = function(username,button,title,review) {
		    	var btn = document.getElementById(button);
					var data = {
							username : username,
							title : title,
							review : review
						};
						//Call the services
						var jsonData = JSON.stringify(data);
				    //Logic to delete the item
				    $http.post('ApproveEbookReview',jsonData).success(function(data,status,headers,config) {
				    	//alert("approve");
				    	console.log("approved");
					    location.reload();
				    	//var row = btn.parentNode.parentNode;
						 // row.parentNode.removeChild(row);
						//$scope.users = response.data;
						//$scope.statuscode = response.status;
					})
					.error(function(data,status,headers,config){
						//alert("failed");
						console.log("failed");
					});
					  
			}
			$scope.IgnoreReview = function(username,button,title,review) {
		    	var btn = document.getElementById(button);
					var data = {
							username : username,
							title : title,
							review : review
						};
						//Call the services
						var jsonData = JSON.stringify(data);
				    //Logic to delete the item

				    $http.post('IgnoreEbookReview',jsonData).success(function(data,status,headers,config) {
				    	//alert("ignored");
				    	console.log("ignored");
					    location.reload();

				    	//var row = btn.parentNode.parentNode;
						 // row.parentNode.removeChild(row);
						//$scope.users = response.data;
						//$scope.statuscode = response.status;
					})
					.error(function(data,status,headers,config){
						//alert("failed");
						console.log("failed");
					});
				 
			}
			$scope.logout = function(){
				$http.get("Logout").success(function(data,status,headers,config){
					//alert(response);
                    window.location.href='index.html';

				}).error(function(data, status, headers,config){
       				
       			});
			}
       		$http.get('GetAllUnapprovedReviews').success(function(data,status,headers,config) {
    			$scope.reviews = data;

    		}).error(function(data, status, headers,config){
   				
   			});
	});