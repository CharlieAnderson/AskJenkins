;
(function() {
    function escapeId(id) {
        // replace all characters except numbers and letters
        // As soon as someone opens a bug that they have a problem with a name conflict
        // rethink the strategy
        return id.replace(/[^a-zA-Z0-9]/g,'-');
    }

    function getJobDiv(jobName) {
        return jQuery('#' + escapeId(jobName));
    }

    window.depview = {
        paper: jQuery("#paper"),
        colordep: '#FF0000', // red
        colorcopy: '#32CD32', // green
        init : function() {
            jQuery.getJSON('graph.json', function(data) {
		var thisProject = window.location.href.replace('/depgraph-view/details','');
		var jobIdx = thisProject.indexOf('job/');
		if(jobIdx != -1) {
			var baseURL = thisProject.substring(0, jobIdx);
			thisProject = thisProject.substring(jobIdx + 4);
			jQuery("<div><p><strong>"+thisProject+"</strong></p></div><hr>").appendTo(window.depview.paper);

			// Extract lastBuild, lastSuccessfulBuild, lastUnsuccessfulBuild from JSON API URLs
			jQuery.getJSON(baseURL+"job/"+thisProject+"/api/json", function(data) {
				// Last Build Information
				var lastBuild = data["lastBuild"];
				if(lastBuild != null) {
					var lastBuildNum = lastBuild["number"];
					jQuery.getJSON(baseURL+"job/"+thisProject+"/"+lastBuildNum+"/api/json", function(data) {
						jQuery("<div><p><strong>LAST BUILD</strong></p>").appendTo(window.depview.paper);
						var infoHTML = "<p><strong>Build Number:</strong> " + data["id"] + "</p>";
						var duration = data["duration"];
						var seconds = 1;
						seconds = (duration / 1000) % 60;
						minutes = seconds / 60;
						minutes = minutes % 60;
						if (minutes >= 1) {
							infoHTML = infoHTML + "<p><strong>Duration:</strong> " + minutes + "min " + 
								seconds + "sec</p>";
						} else {
							seconds = data["duration"] / 1000;
							infoHTML = infoHTML + "<p><strong>Duration:</strong> " + seconds + "sec</p>";
						}						
						infoHTML = infoHTML + "<p><strong>Result:</strong> " + data["result"] + "</p>";
						infoHTML = infoHTML + "<p><strong>Build Cause:</strong> " + data["actions"][0]["causes"][0]["shortDescription"] + 
							"</p>";
						infoHTML = infoHTML + "<p><strong>Build Triggered By:</strong> " + 
							"<a href=\"" + data["culprits"][0]["absoluteUrl"] + "\">" + data["culprits"][0]["fullName"] + "</a></p>";
						jQuery(infoHTML).appendTo(window.depview.paper);
						jQuery("</div><hr>").appendTo(window.depview.paper);
					});
				} else {
					jQuery("<div><p><strong>LAST BUILD</strong></p>").appendTo(window.depview.paper);
					jQuery("<p><i>No build has been triggered for this project.</i></p>").
						appendTo(window.depview.paper);
					jQuery("</div><hr>").appendTo(window.depview.paper);
				}

				// Last Successful Build Information
				var lastSBuild = data["lastSuccessfulBuild"];
				if(lastSBuild != null) {
					var lastSBuildNum = lastSBuild["number"];
					jQuery.getJSON(baseURL+"job/"+thisProject+"/"+lastSBuildNum+"/api/json", function(data) {
						jQuery("<div><p><strong>LAST SUCCESSFUL BUILD</strong></p>").appendTo(window.depview.paper);
						var infoHTML = "<p><strong>Build Number:</strong> " + data["id"] + "</p>";
						var duration = data["duration"];
						var seconds = 1;
						seconds = (duration / 1000) % 60;
						minutes = seconds / 60;
						minutes = minutes % 60;
						if (minutes >= 1) {
							infoHTML = infoHTML + "<p><strong>Duration:</strong> " + minutes + "min " + 
								seconds + "sec</p>";
						} else {
							infoHTML = infoHTML + "<p><strong>Duration:</strong> " + seconds + "sec</p>";
						}
						infoHTML = infoHTML + "<p><strong>Build Cause:</strong> " + data["actions"][0]["causes"][0]["shortDescription"] + 
							"</p>";
						infoHTML = infoHTML + "<p><strong>Build Triggered By:</strong> " + 
							"<a href=\"" + data["culprits"][0]["absoluteUrl"] + "\">" + data["culprits"][0]["fullName"] + "</a></p>";
						jQuery(infoHTML).appendTo(window.depview.paper);
						jQuery("</div><hr>").appendTo(window.depview.paper);
					});
				} else {
					jQuery("<div><p><strong>LAST SUCCESSFUL BUILD</strong></p>").appendTo(window.depview.paper);
					jQuery("<p><i>There have been no successful builds for this project.</i></p>").
						appendTo(window.depview.paper);
					jQuery("</div><hr>").appendTo(window.depview.paper);
				}

				// Last Unsuccessful Build Information
				var lastUBuild = data["lastUnsuccessfulBuild"];
				if(lastUBuild != null) {
					var lastUBuildNum = lastUBuild["number"];
					jQuery.getJSON(baseURL+"job/"+thisProject+"/"+lastUBuildNum+"/api/json", function(data) {
						jQuery("<div><p><strong>LAST UNSUCCESSFUL BUILD</strong></p>").appendTo(window.depview.paper);
						var infoHTML = "<p><strong>Build Number:</strong> " + data["id"] + "</p>";
						var duration = data["duration"];
						var seconds = 1;
						seconds = (duration / 1000) % 60;
						minutes = seconds / 60;
						minutes = minutes % 60;
						if (minutes >= 1) {
							infoHTML = infoHTML + "<p><strong>Duration:</strong> " + minutes + "min " + 
								seconds + "sec</p>";
						} else {
							infoHTML = infoHTML + "<p><strong>Duration:</strong> " + seconds + "sec</p>";
						}
						infoHTML = infoHTML + "<p><strong>Build Cause:</strong> " + data["actions"][0]["causes"][0]["shortDescription"] + 
							"</p>";
						infoHTML = infoHTML + "<p><strong>Build Triggered By:</strong> " + 
							"<a href=\"" + data["culprits"][0]["absoluteUrl"] + "\">" + data["culprits"][0]["fullName"] + "</a></p>";
						jQuery(infoHTML).appendTo(window.depview.paper);
						jQuery("</div><hr>").appendTo(window.depview.paper);
					});
				} else {
					jQuery("<div><p><strong>LAST UNSUCCESSFUL BUILD</strong></p>").appendTo(window.depview.paper);
					jQuery("<p><i>There have been no unsuccessful builds for this project.</i></p>").
						appendTo(window.depview.paper);
					jQuery("</div><hr>").appendTo(window.depview.paper);
				}
			});
			
		} else {
			alert("not a job");
		}
            });
        }
    };
})();

jsPlumb.bind("ready", function() {
	depview.init();
});
