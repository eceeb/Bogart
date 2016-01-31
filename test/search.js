var should  = require('should')
var search  = require('../libs/search')


describe('search', function() {

    var row = {}
    var website = '<body>searchword<body>'

    describe('#after()', function () {
        it('should be positive when search term was found on website', function () {
            seek = 'searchword'
            search.after(seek, website).should.be.true()
        })
    })

    describe('#after()', function () {
        it('should be positive when search term was found on website', function () {
            seek = 'searchword'
            search.after(seek, website).should.be.true()
        })
    })

    describe('#after()', function () {
        it('should be negative when search term was not found on website', function () {
            seek = 'Kirk'
            search.after(seek, website).should.be.false()
        })
    })
})
