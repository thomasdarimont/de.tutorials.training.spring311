	var refreshUsersList = function(users) {
		var $userEntryTemplate = $("#userentryTemplate").template();
		$("#userlistBody").empty();
		$.tmpl($userEntryTemplate, users).appendTo("#userlistBody");
	};

	var useradminAPI = {

		createRequest : function(type, urlSuffix, data) {
			var request = {
				url : "dispatch/" + urlSuffix,
				type : type,
				data : data ? JSON.stringify(data) : null,
				contentType : "application/json",
				dataType : "json"
			};
			return request;
		}

		,
		registerUser : function(user) {
			var request = useradminAPI.createRequest("POST",
					"useradmin/registeruser", user);

			$.ajax(request).done(function(reponse) {
				useradminAPI.findAllUsers();
			});
		},
		findAllUsers : function() {
			var request = useradminAPI.createRequest("GET",
					"useradmin/listusers");

			$.ajax(request).done(function(users) {
				refreshUsersList(users);
			});

		}
	};

	var init = function() {
		$("#addUserForm").submit(function() {

			var user = {
				name : $("#txtUsername").val(),
				email : $("#txtEmail").val()
			};

			useradminAPI.registerUser(user);

			return false;
		});

		useradminAPI.findAllUsers();

	};

	$(init);