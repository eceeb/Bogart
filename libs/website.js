var request   = require('request')
var search    = require('./search')
var searchG2a = require('./searchG2a')

module.exports = function() {

	function searchWebsiteForEntries (searches, url) {

		request(url, function (error, response, body) {
			for(var i in searches) {
				if (!error && response.statusCode == 200) {
					if (~url.search('www.g2a.com'))
						searchG2a.after(searches[i], body)
					else
						search.after(searches[i], body)
				}
			}
		})

	}

	return {

		load : function(urlToSearches) {

			urlToSearches.forEach( function(values, key) {
				searchWebsiteForEntries(values, key)
			}, urlToSearches)
		},
	}
}()
