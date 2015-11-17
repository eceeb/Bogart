var mail    = require('./mail')
var emitter = require('./emitter')


module.exports = function() {

	function markAsFound (row) {
		emitter.emit('foundRow', row)
	}

	function log(row, found) {
		var txt = found ? 'Found something for: ' : 'Nothing found for: '
		console.log(txt + row.seek)
	}

	return {

		after : function (row, body) {

			var reg = new RegExp(row.seek, "i")
			if (~body.search(reg)) {
				log(row, true)
				row.found = true
				mail.send(row)
				markAsFound(row)
			}
			else {
				log(row, false)
			}
		},
	}
}()
