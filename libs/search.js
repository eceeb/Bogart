var mail    = require('./mail')
var emitter = require('./emitter')


module.exports = function () {

	return {

		after : function (row, body) {

			var reg = new RegExp(row.seek, "i")

			if (~body.search(reg)) {
				row.found = true
				mail.send(row)
				emitter.emit('foundRow', row)
			}
		},
	}
}()
