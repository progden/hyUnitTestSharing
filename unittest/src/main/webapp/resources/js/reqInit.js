requirejs.config({
	baseUrl: "../js",
	paths: {
		reqInit: 	"reqInit",
		angular: 	'angular.min',
		bootstrap: 	'bootstrap.min',
		jquery: 	'jquery-1.9.1.min',
		masonry: 	'masonry.pkgd.min',
		evernote: 	'evernote-sdk-minified',
		jsOAuth:	'jsOAuth-1.3.7.min'
	},
	shim: {
		bootstrap: {
			deps: ["jquery"]
		},
		evernote: {
			deps: ["jsOAuth"]
		}
	}
});