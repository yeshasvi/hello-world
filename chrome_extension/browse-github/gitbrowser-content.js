(function($) {

	var $sidebar = $('<a id="simple-menu" class="gitbrowser-action vertical-text" href="#sidr">Change</a>');


	var folders = {};

	var data = [];

	var add = function(nodes, root) {
		if (nodes.length === 0) {
			return 0;
		}
		if (!root[nodes[0]]) {
			root[nodes[0]] = {
				path: '#' + nodes.join('/'),
				child: {}
			}
		}
		arguments.callee(nodes.slice(1), root[nodes[0]].child)
	}

	var convertData = function(data, obj, link) {
		for (var key in obj) {
			if (!obj.hasOwnProperty(key)) continue;
			var item = obj[key];
			if(!link) {
				var href = '/yashprit/hello-world/tree/master/' + key;
			} else {
				var href = link + '/' + key
			}
			
			var _obj = {
				text: key,
				href: href
			}
			data.push(_obj);
			if (item.child) {
				_obj.children = []
				convertData(_obj.children, item.child, href)
			}
		}
	}

	var genrateTreeFromPath = function(tree) {
		tree.forEach(function(v) {
			if (v.type == 'tree') {
				if (v.path.indexOf('/') > -1) {
					var nodes = v.path.split('/');
					add(nodes, folders)
				}
			}
		});
	}

	var renderTree = function() {
		convertData(data,folders);
		$('body').append($sidebar);
		var x = $('#simple-menu').sidr({
			name: 'gitbrowser',
			displace: true,
			source: function(name) {

				return '<h1>' + name + ' menu</h1><p>Yes! You can use a callback too ;)</p> <div id="treeview"></div>';
			},
			onOpen: function() {
				$('#simple-menu').css('left', '260px');
			},
			onClose: function() {
				$('#simple-menu').css('left', '10px');
			}

		});

		var options = {
			data: data
		};


		$.sidr('open', 'gitbrowser', function() {
			$('#treeview').easytree(options);
		})
	}

		function injectDOM() {}

	$(document).ready(function() {
		
		

		var github = new Github({
			token: "0ced2bff07935ae47276cacb05737e82621d05f8",
			auth: "oauth"
		});

		var repo = github.getRepo("yashprit", "hello-world");



		var renderRepo = function(err, tree) {
			if (err) return false;
			genrateTreeFromPath(tree);
			renderTree()
		}

		repo.getTree('master?recursive=true', renderRepo);
		
		$(document).pjax('[data-pjax] a, a[data-pjax]', '#js-repo-pjax-container')
		
		$(document).on('click', '[data-pjax] a, a[data-pjax]', function(e){
			e.preventDefault()
		})

	});


})(jQuery)
