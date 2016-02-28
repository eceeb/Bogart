var searchG2a     = require('./searchG2a')
var searchGeneric = require('./searchGeneric')

module.exports = function () {

    return {

        after : function (doc, body) {
            if (~doc.url.search('www.g2a.com'))
                return searchG2a.after(doc.seek, body)
            else
                return searchGeneric.after(doc.seek, body)
        }
    }
}()
