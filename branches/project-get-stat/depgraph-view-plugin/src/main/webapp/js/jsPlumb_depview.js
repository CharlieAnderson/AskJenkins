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
        colordep: '#FF0000', // redi
        colorcopy: '#32CD32', // green
        init : function() {
			//jsPlumb.setContainer($("body"));
			jsPlumb.Defaults.Container = jQuery("body");
            jsPlumb.importDefaults({
                Connector : 'Bezier',// Straight, Flowchart, Straight, Bezier
                // default drag options
                DragOptions : {
                    cursor : 'crosshair',
                    zIndex : 2000
                },
                // default to blue at one end and green at the other
                EndpointStyles : [ {
                    fillStyle : '#225588'
                }, {
                    fillStyle : '#558822'
                } ],

                // blue endpoints 7px; green endpoints 7px.
                Endpoints : [ [ "Dot", {
                    radius : 10
                } ], [ "Dot", {
                    radius : 10
                } ] ],

                // def for new connector (drag n' drop)
                // - line 2px
                PaintStyle : {
                    lineWidth : 3,
                    strokeStyle : window.depview.colordep//,
                //    joinstyle:"round"
				},

                // the overlays to decorate each connection with. note that the
                // label overlay uses a function to generate the label text; in
                // this case it returns the 'labelText' member that we set on each
                // connection in the 'init' method below.
                ConnectionOverlays : [ [ "Arrow", {
                    location : 0.5,
                    foldback:0.6	
                } ],
				 [ "Label", { label:"foo", location:0.25, id:"myLabel" } ]
                ]

            });
            jQuery.getJSON('graph.json', function(data) {

                var top = 3;
                var space = 150;
                var xOverall = 0;

                var clusters = data["clusters"];
                // iterate clusters
                jQuery.each(clusters, function(i, cluster) {
                    jQuery.each(cluster.nodes, function(i,node) {
                        var nodeString = '<div>'
						var stat = '<div>'         //Added for task, will have statistics
                        if (window.depview.editEnabled) {
                            nodeString = nodeString + '<div class="ep"/>';
                        }
                        nodeString = nodeString + '<a href="' + node.url + '">' + node.name + '</a></div>'
			stat = stat + ' Statistics for '+ node.name + '<br/>' +
			    'numSuccess: ' + node.numSuccess + '<br/>' +
			    'numUnstable: ' + node.numUnstable + '<br/>' +
			    'numFailed: ' + node.numFail + '</div>'
                        jQuery(nodeString).
                            addClass('window').
                            attr('id', escapeId(node.name)).
                            attr('data-jobname', node.fullName).
                            css('top', node.y + top).
                            css('left', node.x + xOverall).
                            appendTo(window.depview.paper);
						jQuery(stat).
							addClass('statWindow').
							attr('id', escapeId(node.name)).
							attr('data-jobname', node.fullName).
							css('top', node.y+top).
							css('left', node.x + xOverall + 125). //added the 10 in for testing
							appendTo(window.depview.paper);
							
                    })
                    top = top + cluster.vSize + space
                    //xOverall = xOverall + cluster.hSize + space
                });
                // definitions for drag/drop connections
                jQuery(".ep").each(function(idx, current) {
                    var p = jQuery(current).parent()
                    if(window.depview.editEnabled) {
	                    jsPlumb.makeSource(current, {
	                        anchor : "Continuous",
	                        parent: p
	                    });
                    }
                })
                jsPlumb.makeTarget(jsPlumb.getSelector('.window'), {
                    anchor : "Continuous"
                });

                var edges = data["edges"];
                jQuery.each(edges, function(i, edge) {
                    from = getJobDiv(edge["from"]);
                    to = getJobDiv(edge["to"]);
                    // creates/defines the look and feel of the loaded connections: red="dep", green="copy"
                    var connection;
                    var connOptions = {
                        source : from,
                        target : to,
                        scope: edge["type"],
                        paintStyle:{lineWidth : 3}
                    }
                    if("copy" == edge["type"]){
                        connOptions.paintStyle.strokeStyle = window.depview.colorcopy;
                        connOptions.overlays = [[ "Label", { label: "copy", id: from+'.'+to } ]];
                        connection = jsPlumb.connect(connOptions);
                    } else {
                        connOptions.paintStyle.strokeStyle = window.depview.colordep;
                        connection = jsPlumb.connect(connOptions);
                        // only allow deletion of "dep" connections
                        if(window.depview.editEnabled) {
	                        connection.bind("click", function(conn) {
	                            var sourceJobName = conn.source.attr('data-jobname');
	                            var targetJobName = conn.target.attr('data-jobname')
	                            if(confirm('delete connection: '+ sourceJobName +" -> "+ targetJobName +'?')){
	                                jQuery.ajax({
	                                    url : encodeURI('edge/' + sourceJobName + '/'    + targetJobName),
	                                    type : 'DELETE',
	                                    success : function(response) {
	                                        jsPlumb.detach(conn);
	                                    },
	                                    error: function (request, status, error) {
	                                        alert(status+": "+error);
	                                    }
	                                });
	                            }
	                        });
                        }
                    }
                });

                if(window.depview.editEnabled) {
	                jsPlumb.bind("jsPlumbConnection", function(info) {
	                    jQuery.ajax({
	                           url: encodeURI('edge/'+info.source.attr('data-jobname') +'/'+info.target.attr('data-jobname')),
	                           type: 'PUT',
	                           success: function( response ) {
//                                 alert('Load was performed.');
	                           },
	                           error: function (request, status, error) {
	                                alert(request.responseText);
	                           }
	                    });
	                    // allow deletion of newly created connection
	                    info.connection.bind("click", function(conn) {
	                        var sourceJobName = conn.source.attr('data-jobname');
	                        var targetJobName = conn.target.attr('data-jobname');
	                        if(confirm('delete connection: '+ sourceJobName +" -> "+ targetJobName +'?')){
	                            jQuery.ajax({
	                                url : encodeURI('edge/' + sourceJobName + '/'    + targetJobName),
	                                type : 'DELETE',
	                                success : function(response) {
	                                    jsPlumb.detach(conn);
	                                },
	                                error: function (request, status, error) {
	                                    alert(request.responseText);
	                                }
	                            });
	                        }
	                    });
	                });
                }

                // make all the window divs draggable
                jsPlumb.draggable(jsPlumb.getSelector(".window"));
            });
        }
    };
})();

// start jsPlumb
jsPlumb.bind("ready", function() {
    // chrome fix.
    document.onselectstart = function () { return false; };

    jsPlumb.setRenderMode(jsPlumb.SVG);
    depview.init();
});

