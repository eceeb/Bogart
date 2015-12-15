var cheerio = require('cheerio')
var mail    = require('./mail')
var emitter = require('./emitter')


module.exports = function () {

    function markAsFound (row) {
        emitter.emit('foundRow', row)
    }

    return {

        after : function (row, body) {
            $ = cheerio.load(body);
            var query = $('div.selected-price').text().trim()
            var price = query.replace(',', '.').replace('€', '').trim()
            console.log('Found price... ' + price)
            if (price <= row.seek) {
                row.found = true
                mail.send(row)
                markAsFound(row)
            }

            var txt = row.found ? 'Found something for: ' : 'Nothing found for: '
            console.log(txt + row.seek)
        },
    }
}()
